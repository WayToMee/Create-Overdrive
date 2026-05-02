package com.waymee.overdrive.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.KeyMapping;
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
                opts.keyUp,
                opts.keyDown,
                opts.keyLeft,
                opts.keyRight,
                opts.keyJump,
                opts.keyShift
        );
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.InteractionKeyMappingTriggered event) {
        if (CockpitInputActive && getMovementControls().contains(event.getKeyMapping())) {
            event.setCanceled(true);
            event.getKeyMapping().setDown(false);
        }
    }

    @SubscribeEvent
    public static void onPlayerTickPre(PlayerTickEvent.Pre event) {
        if (CockpitInputActive && event.getEntity() instanceof net.minecraft.client.player.LocalPlayer player) {
            for (KeyMapping kb : getMovementControls()) {
                kb.setDown(false);
            }
            player.input.forwardImpulse = 0;
            player.input.leftImpulse = 0;
            player.input.jumping = false;
            player.input.shiftKeyDown = false;
        }
    }

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        if (CockpitInputActive) {
            CockpitInputActive = false;
        }
    }
}
