package com.monka.splashzone.entity;

import com.monka.splashzone.registry.EntityRegistry;
import com.monka.splashzone.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
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

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {

        if (pPlayer.getItemInHand(pHand).isEmpty()) {
            pPlayer.setItemInHand(pHand, new ItemStack(ItemRegistry.UGG_EGG.get()));
            SoundEvent soundevent;
            soundevent = SoundEvents.ITEM_PICKUP;
            this.playSound(soundevent, 1.0F, 1.0F);
            this.showBreakingParticles();
            this.kill();
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        } else {
            return super.mobInteract(pPlayer, pHand);
        }

    }

    private void showBreakingParticles() {
        if (this.level() instanceof ServerLevel) {
            ((ServerLevel) this.level()).sendParticles((ParticleOptions) ParticleTypes.POOF, this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, (double) (this.getBbWidth() / 4.0F), (double) (this.getBbHeight() / 4.0F), (double) (this.getBbWidth() / 4.0F), 0.05D);
        }
    }

    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }
}
