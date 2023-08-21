package com.monka.splashzone.events;

import com.monka.splashzone.Splashzone;
import com.monka.splashzone.entity.UggEggEntity;
import com.monka.splashzone.entity.UggEntity;
import com.monka.splashzone.registry.EntityRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Splashzone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.UGG_ENTITY.get(), UggEntity.createUggAttributes());
        event.put(EntityRegistry.UGG_EGG_ENTITY.get(), UggEggEntity.createUggEggAttributes());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(
                EntityRegistry.UGG_ENTITY.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                UggEntity::canSpawn,
                SpawnPlacementRegisterEvent.Operation.OR
        );

    }

    @Mod.EventBusSubscriber(modid = Splashzone.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {
        @SubscribeEvent
        public static void slugBreed(BabyEntitySpawnEvent event) {
            if (event.getParentA() instanceof UggEntity && event.getParentB() instanceof UggEntity) {
                UggEggEntity entity = EntityRegistry.UGG_EGG_ENTITY.get().create(event.getParentA().level());
                entity.moveTo(event.getParentA().position());

                entity.level().addFreshEntity(new ExperienceOrb(entity.level(), entity.getX(), entity.getY(), entity.getZ(), RandomSource.create().nextInt(7) + 1));

                event.getParentA().level().addFreshEntity(entity);

                if (entity.level() instanceof ServerLevel) {
                    double d0 = entity.getRandom().nextGaussian() * 0.02D;
                    double d1 = entity.getRandom().nextGaussian() * 0.02D;
                    double d2 = entity.getRandom().nextGaussian() * 0.02D;
                    ((ServerLevel) entity.level()).sendParticles((ParticleOptions) ParticleTypes.HEART, entity.getRandomX(1.0D), entity.getRandomY() + 0.5D, entity.getRandomZ(1.0D), 10, d0, d1, d2, 0.05D);
                }

                event.setCanceled(true);
            }
        }
    }

}
