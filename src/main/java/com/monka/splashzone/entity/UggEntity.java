package com.monka.splashzone.entity;

import com.monka.splashzone.entity.variant.UggVariant;
import com.monka.splashzone.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.Nullable;


public class UggEntity extends Animal {

    protected final WaterBoundPathNavigation waterNavigation;
    protected final GroundPathNavigation groundNavigation;

    public UggEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setMaxUpStep(1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.waterNavigation = new WaterBoundPathNavigation(this, pLevel);
        this.groundNavigation = new GroundPathNavigation(this, pLevel);
    }

    public UggEntity(Level level, double x, double y, double z) {
        this(EntityRegistry.UGG_ENTITY.get(), level);
        setPos(x, y, z);
    }

    public UggEntity(Level level, BlockPos position) {
        this(level, position.getX(), position.getY(), position.getZ());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.25F, Ingredient.of(Items.BROWN_MUSHROOM), false));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
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

    public static AttributeSupplier createUggAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.15D).build();
    }

    public boolean isPushedByFluid() {
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    public int getAmbientSoundInterval() {
        return 200;
    }

    public static boolean canSpawn(EntityType<UggEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
    }

    /* VARIANTS */

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(UggEntity.class, EntityDataSerializers.INT);

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getTypeVariant());
    }


    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        Holder<Biome> holder = pLevel.getBiome(this.blockPosition());
        if (holder.is(BiomeTags.IS_FOREST)) {
            this.setVariant(UggVariant.RUDDY);
        } else if (holder.is(BiomeTags.IS_TAIGA)) {
            this.setVariant(UggVariant.UMBER);
        } else if (holder.is(BiomeTags.IS_BADLANDS)) {
            this.setVariant(UggVariant.CHARCOAL);
        } else if (holder.is(Tags.Biomes.IS_SWAMP)) {
            this.setVariant(UggVariant.LICHEN);
        } else {
            this.setVariant(UggVariant.BANANA);
        }


        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    public UggVariant getVariant() {
        return UggVariant.byId(this.getTypeVariant() & 255);
    }

    private int getTypeVariant() {
        return this.entityData.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(UggVariant variant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_TYPE_VARIANT, 0);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.getItem() == Items.BROWN_MUSHROOM;
    }

    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? SoundEvents.COD_AMBIENT : SoundEvents.HONEY_BLOCK_PLACE;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return this.isInWater() ? SoundEvents.COD_HURT : SoundEvents.SLIME_HURT;
    }

    protected SoundEvent getDeathSound() {
        return this.isInWater() ? SoundEvents.COD_DEATH : SoundEvents.SLIME_DEATH;
    }
}
