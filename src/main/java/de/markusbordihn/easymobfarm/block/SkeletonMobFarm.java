
package de.markusbordihn.easymobfarm.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import de.markusbordihn.easymobfarm.block.entity.SkeletonMobFarmEntity;
import de.markusbordihn.easymobfarm.item.CapturedMobItem;

public class SkeletonMobFarm extends MobFarmBlock {

  public static final String NAME = "skeleton_mob_farm";

  public SkeletonMobFarm(BlockBehaviour.Properties properties) {
    super(properties);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new SkeletonMobFarmEntity(ModBlocks.SKELETON_MOB_FARM_ENTITY.get(), blockPos,
        blockState);
  }

  @Override
  public boolean canConsumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, Player player, ItemStack itemStack) {
    if (itemStack.getItem() instanceof CapturedMobItem capturedMobItem) {
      String capturedMobType = capturedMobItem.getCapturedMobType(itemStack);
      if (capturedMobType.equals("minecraft:skeleton")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public InteractionResult consumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, ItemStack itemStack, UseOnContext context) {
    SkeletonMobFarmEntity skeletonMobFarmEntity = (SkeletonMobFarmEntity) blockEntity;
    skeletonMobFarmEntity.updateLevel(level);
    if (!skeletonMobFarmEntity.hasMob()) {
      skeletonMobFarmEntity.setMob(itemStack);
      context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
      return InteractionResult.CONSUME;
    }
    return InteractionResult.PASS;
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
      BlockEntityType<T> blockEntityType) {
    return level.isClientSide ? null
        : createTickerHelper(blockEntityType, ModBlocks.SKELETON_MOB_FARM_ENTITY.get(),
            SkeletonMobFarmEntity::serverTick);
  }

}
