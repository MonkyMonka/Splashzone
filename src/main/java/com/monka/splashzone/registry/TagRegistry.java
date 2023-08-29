package com.monka.splashzone.registry;

import com.monka.splashzone.Splashzone;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TagRegistry {
    //    public static final TagKey<Item> INSECT_ITEMS = registerItemTag("insect_items");
    //    public static final TagKey<Block> GRIZZLY_BEEHIVE = registerBlockTag("grizzly_beehive");
    //    public static final TagKey<EntityType<?>> SNOW_LEOPARD_TARGETS = registerEntityTag("snow_leopard_targets");
    //    public static final TagKey<Biome> SPAWNS_DESERT_CROCODILES = registerBiomeTag("spawns_desert_crocodiles");
    //public static final TagKey<Structure> SPAWNS_UNDERMINERS = registerStructureTag("spawns_underminers");

    public static final TagKey<Biome> SPAWNS_UGGS = registerBiomeTag("spawns_uggs");

    private static TagKey<EntityType<?>> registerEntityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(Splashzone.MODID, name));
    }

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(Splashzone.MODID, name));
    }

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(Splashzone.MODID, name));
    }

    private static TagKey<Biome> registerBiomeTag(String name) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(Splashzone.MODID, name));
    }

    private static TagKey<Structure> registerStructureTag(String name) {
        return TagKey.create(Registries.STRUCTURE, new ResourceLocation(Splashzone.MODID, name));
    }
}