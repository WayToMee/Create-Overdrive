package com.waymee.overdrive.item;

import com.waymee.overdrive.Overdrive;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Overdrive.MOD_ID);

    public static final DeferredItem<Item> ANALOG_CONTROL_CIRCUIT = ITEMS.register("analog_control_circuit",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> STEERING_WHEEL = ITEMS.register("steering_wheel",
            () -> new Item(new Item.Properties()));

    public static void  register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
