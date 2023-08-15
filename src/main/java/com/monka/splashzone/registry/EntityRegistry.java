package com.monka.splashzone.registry;

import com.monka.splashzone.Splashzone;
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
            () -> EntityType.Builder.<UggEntity>of(UggEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 1.0f)
                    .build(new ResourceLocation(Splashzone.MODID, "ugg_entity").toString())
    );
}
