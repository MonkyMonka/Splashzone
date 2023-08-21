package com.monka.splashzone.registry;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.entity.UggEggEntity;
import com.monka.splashzone.entity.UggEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Splashzone.MODID);

    public static final RegistryObject<EntityType<UggEntity>> UGG_ENTITY = ENTITIES.register("ugg_entity",
            () -> EntityType.Builder.<UggEntity>of(UggEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.7f, 0.3f)
                    .build(new ResourceLocation(Splashzone.MODID, "ugg_entity").toString())
    );

    public static final RegistryObject<EntityType<UggEggEntity>> UGG_EGG_ENTITY = ENTITIES.register("ugg_egg_entity",
            () -> EntityType.Builder.<UggEggEntity>of(UggEggEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.4f, 0.4f)
                    .build(new ResourceLocation(Splashzone.MODID, "ugg_egg_entity").toString())
    );
}
