package com.monka.splashzone.item;

import com.monka.splashzone.Splashzone;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Splashzone.MODID);
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}