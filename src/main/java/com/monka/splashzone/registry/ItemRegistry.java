package com.monka.splashzone.registry;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.item.UggEggItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Splashzone.MODID);

    public static final RegistryObject<Item> UGG_EGG = ITEMS.register("ugg_egg",
            () -> new UggEggItem(new Item.Properties()));

    public static final RegistryObject<Item> UGG_SPAWN_EGG = ITEMS.register("ugg_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityRegistry.UGG_ENTITY, 0xE1C58E, 0xA87D56, new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}