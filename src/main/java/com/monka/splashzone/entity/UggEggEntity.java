package com.monka.splashzone.entity;

import com.monka.splashzone.registry.EntityRegistry;
import com.monka.splashzone.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class UggEggEntity extends Mob {
    
    private int age;

    public static float HITBOX_WIDTH = 0.4F;
    public static float HITBOX_HEIGHT = 0.4F;

    private static int minHatchTickDelay = Math.abs(-3600);
    private static int maxHatchTickDelay = Math.abs(-12000);

    public UggEggEntity(EntityType<? extends Mob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    public static AttributeSupplier createUggEggAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).build();
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            this.setAge(this.age + 1);
        }
        for (Player player : level().getEntitiesOfClass(Player.class, this.getBoundingBox())) {
            if (!player.onGround() && EnchantmentHelper.getEnchantmentLevel(Enchantments.FALL_PROTECTION, player) == 0 && !this.hasCustomName() && !player.isSteppingCarefully()) {
                this.hurt(damageSources().playerAttack(player), 5.0F);
            }
        }
    }


    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Age", this.age);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setAge(pCompound.getInt("Age"));
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

    private static int getUggEggHatchDelay(RandomSource pRandom) {
        return pRandom.nextInt(minHatchTickDelay, maxHatchTickDelay);
    }

    private int getAge() {
        return this.age;
    }

    private void ageUp(int pOffset) {
        this.setAge(this.age + pOffset * 20);
    }

    private void setAge(int pAge) {
        this.age = pAge;
        if (this.age >= getUggEggHatchDelay(level().getRandom())) {
            this.ageUp();
        }

    }

    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }


    private void ageUp() {
        Level level = this.level();
        if (level instanceof ServerLevel serverlevel) {
            UggEntity ugg = EntityRegistry.UGG_ENTITY.get().create(this.level());
            if (ugg != null) {
                ugg.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                ugg.finalizeSpawn(serverlevel, this.level().getCurrentDifficultyAt(ugg.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData) null, (CompoundTag) null);
                ugg.setNoAi(this.isNoAi());
                ugg.setBaby(true);
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

    public boolean shouldDropExperience() {
        return false;
    }
}
