/*
 * Copyright 2024 Markus Bordihn
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.markusbordihn.easymobfarm.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.component.DataComponents;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmBlock extends BaseEntityBlock {

  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
  public static final BooleanProperty WORKING = BooleanProperty.create("working");
  public static final IntegerProperty TIER_LEVEL = IntegerProperty.create("tier_level", 0, 3);
  public static final EnumProperty<MobFarmType> FARM_TYPE =
      EnumProperty.create("farm_type", MobFarmType.class);
  public static final MapCodec<MobFarmBlock> CODEC =
      RecordCodecBuilder.mapCodec(
          instance ->
              instance
                  .group(
                      MobFarmType.CODEC
                          .fieldOf("mobFarmType")
                          .forGetter(block -> getFarmType(block.defaultBlockState())))
                  .apply(instance, MobFarmBlock::new));
  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public MobFarmBlock(final MobFarmType mobFarmType) {
    this(
        Properties.of()
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(5.0f)
            .lightLevel(MobFarmBlock::getLightLevel)
            .sound(SoundType.METAL)
            .noOcclusion(),
        mobFarmType);
  }

  public MobFarmBlock(final Properties properties, final MobFarmType mobFarmType) {
    this(properties, 0, mobFarmType);
  }

  public MobFarmBlock(final Properties properties, int tierLevel, final MobFarmType mobFarmType) {
    super(properties);
    this.registerDefaultState(
        this.stateDefinition
            .any()
            .setValue(FACING, Direction.NORTH)
            .setValue(WORKING, Boolean.FALSE)
            .setValue(TIER_LEVEL, tierLevel)
            .setValue(FARM_TYPE, mobFarmType));
  }

  public static int getLightLevel(final BlockState blockState) {
    return Boolean.TRUE.equals(blockState.getValue(MobFarmBlock.WORKING)) ? 15 : 8;
  }

  public static int getTierLevel(final BlockState blockState) {
    return blockState.getValue(MobFarmBlock.TIER_LEVEL);
  }

  public static MobFarmType getFarmType(final BlockState blockState) {
    return blockState.getValue(MobFarmBlock.FARM_TYPE);
  }

  public BlockEntity newBlockEntity(
      final BlockPos blockPos, final BlockState blockState, MobFarmType mobFarmType) {
    throw new UnsupportedOperationException("This method must be overridden by a subclass!");
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  public void onRemove(
      BlockState state, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
    if (!state.is(newState.getBlock())) {
      BlockEntity blockEntity = level.getBlockEntity(blockPos);
      if (blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity) {
        mobFarmBlockEntity.dropInventoryContents();
        level.updateNeighbourForOutputSignal(blockPos, this);
      }
      super.onRemove(state, level, blockPos, newState, isMoving);
    }
  }

  @Override
  public BlockEntity newBlockEntity(final BlockPos blockPos, final BlockState blockState) {
    return newBlockEntity(blockPos, blockState, getFarmType(blockState));
  }

  @Override
  @SuppressWarnings("java:S1874")
  public RenderShape getRenderShape(final BlockState blockState) {
    return RenderShape.MODEL;
  }

  @Override
  protected void createBlockStateDefinition(
      final StateDefinition.Builder<Block, BlockState> blockState) {
    blockState.add(FACING, WORKING, TIER_LEVEL, FARM_TYPE);
  }

  @Override
  public BlockState getStateForPlacement(final BlockPlaceContext context) {
    return this.defaultBlockState()
        .setValue(FACING, context.getHorizontalDirection().getOpposite());
  }

  @Override
  public void setPlacedBy(
      final Level level,
      final BlockPos blockPos,
      final BlockState blockState,
      final LivingEntity livingEntity,
      final ItemStack itemStack) {
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if (blockEntity instanceof MobFarmBlockEntity blockEntityInstance
        && level instanceof ServerLevel serverLevel) {
      if (livingEntity instanceof ServerPlayer serverPlayer) {
        blockEntityInstance.setOwner(serverPlayer);
      }
      MobFarmData mobFarmData =
          itemStack.getOrDefault(DataComponents.MOB_FARM_DATA, MobFarmData.EMPTY);
      int tierLevel = mobFarmData.tierLevel().getTierLevel();
      if (tierLevel >= 0) {
        BlockState newBlockState = blockState.setValue(TIER_LEVEL, tierLevel);
        serverLevel.setBlock(blockPos, newBlockState, 3);
        blockEntityInstance.setFarmTierLevel(tierLevel);
        blockEntity.setChanged();
      }
    }
  }

  @Override
  protected InteractionResult useWithoutItem(
      BlockState blockState,
      Level level,
      BlockPos blockPos,
      Player player,
      BlockHitResult hitResult) {
    if (level.isClientSide) {
      return InteractionResult.SUCCESS;
    }

    // Open Mob Farm GUI
    this.openMenu(level, blockPos, player);
    return InteractionResult.CONSUME;
  }

  @Override
  public ItemInteractionResult useItemOn(
      ItemStack itemStack,
      final BlockState blockState,
      final Level level,
      final BlockPos blockPos,
      final Player player,
      final InteractionHand interactionHand,
      final BlockHitResult blockHitResult) {
    if (level.isClientSide) {
      return ItemInteractionResult.SUCCESS;
    }

    // Confirm that block is a mob farm block entity
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if (!(blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity)) {
      return ItemInteractionResult.FAIL;
    }

    // Check if item in hand could be consumed
    if (!itemStack.isEmpty()
        && (mobFarmBlockEntity.takeMobCaptureItem(player, interactionHand)
            || mobFarmBlockEntity.takeEnhancementItem(player, interactionHand)
            || mobFarmBlockEntity.takeSlotUpgradeItem(player, interactionHand)
            || mobFarmBlockEntity.takeFilterItem(player, interactionHand))) {
      return ItemInteractionResult.CONSUME;
    }

    // Check if mob capture items could be extracted
    if (player.isShiftKeyDown() && mobFarmBlockEntity.hasCapturedMob()) {
      mobFarmBlockEntity.giveMobCaptureItem(player, interactionHand);
      return ItemInteractionResult.CONSUME;
    }

    // Open Mob Farm GUI
    this.openMenu(level, blockPos, player);
    return ItemInteractionResult.CONSUME;
  }

  protected void openMenu(final Level level, final BlockPos blockPos, final Player player) {
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if (blockEntity instanceof MobFarmBlockEntity blockEntityInstance
        && player instanceof ServerPlayer serverPlayer) {
      serverPlayer.openMenu(blockEntityInstance);
    }
  }
}
