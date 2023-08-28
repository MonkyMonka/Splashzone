package com.monka.splashzone.item;

import com.monka.splashzone.entity.UggEggEntity;
import com.monka.splashzone.registry.EntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class UggEggItem extends Item {
    public UggEggItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pContext.getItemInHand();
            BlockPos blockpos = pContext.getClickedPos();
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            Entity uggegg = EntityRegistry.UGG_EGG_ENTITY.get().create((ServerLevel) level, null, null, blockpos, MobSpawnType.SPAWN_EGG, false, false);
            if (uggegg != null) {
                double d0 = (double) blockpos1.getX() + this.getRandomUggEggOffset(level.getRandom());
                double d1 = (double) blockpos1.getZ() + this.getRandomUggEggOffset(level.getRandom());
                int k = level.getRandom().nextInt(1, 361);
                uggegg.moveTo(d0, blockpos1.getY(), d1, (float) k, 0.0F);
                level.addFreshEntity(uggegg);
            }

            itemstack.shrink(1);
            level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            level.playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.SNIFFER_EGG_PLOP, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            return InteractionResult.CONSUME;
        }
    }

    private double getRandomUggEggOffset(RandomSource pRandom) {
        double d0 = (UggEggEntity.HITBOX_WIDTH / 2.0F);
        return Mth.clamp(pRandom.nextDouble(), d0, 1.0D - d0);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        } else if (!(pLevel instanceof ServerLevel)) {
            return InteractionResultHolder.success(stack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            if (!(pLevel.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                return InteractionResultHolder.pass(stack);
            } else if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos, blockhitresult.getDirection(), stack)) {
                Entity uggegg = EntityRegistry.UGG_EGG_ENTITY.get().spawn((ServerLevel) pLevel, stack, pPlayer, blockpos, MobSpawnType.SPAWN_EGG, false, false);
                if (uggegg == null) {
                    return InteractionResultHolder.pass(stack);
                } else {
                    if (!pPlayer.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    pPlayer.awardStat(Stats.ITEM_USED.get(this));
                    pLevel.gameEvent(pPlayer, GameEvent.ENTITY_PLACE, uggegg.position());
                    pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNIFFER_EGG_PLOP, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                    return InteractionResultHolder.consume(stack);
                }
            } else {
                return InteractionResultHolder.fail(stack);
            }
        }
    }
}
