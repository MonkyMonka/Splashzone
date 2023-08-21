package com.monka.splashzone.entity;

import com.monka.splashzone.registry.EntityRegistry;
import com.monka.splashzone.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class UggEggEntity extends Mob {
    public UggEggEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.skipDropExperience();
    }

    public UggEggEntity(Level level, double x, double y, double z) {
        this(EntityRegistry.UGG_EGG_ENTITY.get(), level);
        setPos(x, y, z);
    }

    public UggEggEntity(Level level, BlockPos position) {
        this(level, position.getX(), position.getY(), position.getZ());
    }


    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        for (Player player : level().getEntitiesOfClass(Player.class, this.getBoundingBox())) {
            if (!player.onGround() && EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, player) == 0 && !this.hasCustomName() && !player.isSteppingCarefully()) {
                this.hurt(damageSources().playerAttack(player), 5.0F);
            }
        }
    }

    public static AttributeSupplier createUggEggAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).build();
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = new ItemStack(ItemRegistry.UGG_EGG.get());

            pPlayer.getInventory().add(itemstack);
            SoundEvent soundevent;
            soundevent = SoundEvents.ITEM_PICKUP;
            this.playSound(soundevent, 1.0F, 1.0F);
            this.kill();
            return InteractionResult.sidedSuccess(this.level().isClientSide);

    }

    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }


    private void ageUp() {
        Level $$1 = this.level();
        if ($$1 instanceof ServerLevel serverlevel) {
            UggEntity ugg = EntityRegistry.UGG_ENTITY.get().create(this.level());
            if (ugg != null) {
                ugg.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                ugg.finalizeSpawn(serverlevel, this.level().getCurrentDifficultyAt(ugg.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData) null, (CompoundTag) null);
                ugg.setNoAi(this.isNoAi());
                if (this.hasCustomName()) {
                    ugg.setCustomName(this.getCustomName());
                    ugg.setCustomNameVisible(this.isCustomNameVisible());
                }

                ugg.setPersistenceRequired();
                this.playSound(SoundEvents.TADPOLE_GROW_UP, 0.15F, 1.0F);
                serverlevel.addFreshEntityWithPassengers(ugg);
                this.discard();
            }
        }

    }
}
