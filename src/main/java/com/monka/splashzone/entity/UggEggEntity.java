package com.monka.splashzone.entity;

import com.monka.splashzone.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class UggEggEntity extends AgeableMob {
    public UggEggEntity(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public UggEggEntity(Level level, double x, double y, double z) {
        this(EntityRegistry.UGG_EGG_ENTITY.get(), level);
        setPos(x, y, z);
    }
    public UggEggEntity(Level level, BlockPos position) {
        this(level, position.getX(), position.getY(), position.getZ());
    }

    public static AttributeSupplier createUggEggAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).build();
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }
}
