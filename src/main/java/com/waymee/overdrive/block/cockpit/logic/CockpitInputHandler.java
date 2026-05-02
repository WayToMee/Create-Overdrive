package com.waymee.overdrive.block.cockpit.logic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

@EventBusSubscriber(modid = "overdrive", value = Dist.CLIENT)
public class CockpitInputHandler {

    private static boolean CockpitInputActive = false;

    public static void setActive(boolean active) {
        CockpitInputActive = active;
        if (active) {
            // Reset all movement keys immediately when activating
            for (KeyMapping kb : getMovementControls()) {
                kb.setDown(false);
            }
        }
    }

    public static boolean isActive() {
        return CockpitInputActive;
    }

    public static List<KeyMapping> getMovementControls() {
        Options opts = Minecraft.getInstance().options;
        return List.of(
                opts.keyUp,      // W
                opts.keyDown,    // S
                opts.keyLeft,    // A
                opts.keyRight,   // D
                opts.keyJump,    // Space
                opts.keyShift    // Shift
        );
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.InteractionKeyMappingTriggered event) {
        if (CockpitInputActive && getMovementControls().contains(event.getKeyMapping())) {
            event.setCanceled(true);
            // Immediately reset this specific key
            event.getKeyMapping().setDown(false);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        if (CockpitInputActive && event.getEntity() instanceof LocalPlayer player) {
            // Reset all movement key states
            for (KeyMapping kb : getMovementControls()) {
                kb.setDown(false);
            }

            // Create a new Input object with zeroed values
            Input input = player.input;
            input.forwardImpulse = 0;
            input.leftImpulse = 0;
            input.jumping = false;
            input.shiftKeyDown = false;
        }
    }

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        if (CockpitInputActive) {
            CockpitInputActive = false;
        }
    }
}
