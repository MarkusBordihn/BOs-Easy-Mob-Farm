/**
 * Copyright 2022 Markus Bordihn
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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.config.MobTypeManager;
import de.markusbordihn.easymobfarm.data.FarmTier;
import de.markusbordihn.easymobfarm.item.CapturedMob;
import de.markusbordihn.easymobfarm.item.CapturedMobVirtual;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.text.TranslatableText;

public class MobFarmBlock extends BaseEntityBlock implements CapturedMobCompatible {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  public static final String NAME = "mob_farm";
  public static final String SUPPORTED_MOBS_TEXT = "supported_mobs";

  public static final BooleanProperty POWERED = BooleanProperty.create("powered");
  public static final BooleanProperty WORKING = BooleanProperty.create("working");
  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

  public static final Set<String> ACCEPTED_MOB_TYPES = Collections.emptySet();
  public static final Set<String> DENIED_MOB_TYPES = Collections.emptySet();

  protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

  public MobFarmBlock(BlockBehaviour.Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
        .setValue(WORKING, Boolean.valueOf(false)).setValue(POWERED, Boolean.valueOf(false)));
  }

  protected void openContainer(Level level, BlockPos blockPos, Player player) {
    if (level.getBlockEntity(blockPos) instanceof MobFarmBlockEntity mobFarm) {
      player.openMenu(mobFarm);
    }
  }

  public Set<String> getGeneralAllowedMobTypes() {
    return MobTypeManager.getGeneralAllowedMobTypes();
  }

  public Set<String> getGeneralDeniedMobTypes() {
    return MobTypeManager.getGeneralDeniedMobTypes();
  }

  public static int getLightLevel(BlockState blockState) {
    return Boolean.TRUE.equals(blockState.getValue(MobFarmBlock.WORKING)) ? 15 : 8;
  }

  public Set<String> getAcceptedMobTypes() {
    return MobTypeManager.getAcceptedMobTypes(getFarmName());
  }

  public Set<String> getDeniedMobTypes() {
    return MobTypeManager.getDeniedMobTypes(getFarmName());
  }

  public boolean isAcceptedMobType(String mobType) {
    if (mobType == null || mobType.isBlank()) {
      return false;
    }
    if (getGeneralDeniedMobTypes().contains(mobType)) {
      return false;
    }
    if (getGeneralAllowedMobTypes().contains(mobType)) {
      return true;
    }
    if (getDeniedMobTypes().contains(mobType)) {
      return false;
    }
    return getAcceptedMobTypes().isEmpty() || getAcceptedMobTypes().contains(mobType);
  }

  public boolean isDeniedMobType(String mobType) {
    return !isAcceptedMobType(mobType);
  }

  public String getFarmDescriptionId() {
    return SUPPORTED_MOBS_TEXT;
  }

  public String getFarmName() {
    return NAME;
  }

  public FarmTier getFarmTier() {
    return FarmTier.DEFAULT;
  }

  public void appendHoverTextAcceptedMobs(List<Component> tooltipList) {
    Set<String> acceptedMobTypes = getAcceptedMobTypes();
    if (acceptedMobTypes != null && !acceptedMobTypes.isEmpty()) {
      // List each single possible mob types (incl. modded mobs types).
      MutableComponent mobTypeOverview = Component.literal("").withStyle(ChatFormatting.DARK_GREEN);
      for (String acceptedMob : acceptedMobTypes) {
        Component acceptedMobName = TranslatableText.getEntityName(acceptedMob);
        if (!acceptedMobName.getString().isBlank()) {
          mobTypeOverview.append(acceptedMobName).append(", ").withStyle(ChatFormatting.DARK_GREEN);
        }
      }
      if (!mobTypeOverview.getString().isBlank()) {
        MutableComponent supportedMobsOverview =
            Component.translatable(Constants.TEXT_PREFIX + getFarmDescriptionId()).append(" ")
                .withStyle(ChatFormatting.GREEN);
        supportedMobsOverview.append(mobTypeOverview).append("...");
        tooltipList.add(supportedMobsOverview);
      }
    } else {
      tooltipList.add(Component.translatable(Constants.TEXT_PREFIX + "supported_all")
          .withStyle(ChatFormatting.GREEN));
    }
  }

  @Override
  @SuppressWarnings("java:S1874")
  public void onPlace(BlockState blockState, Level level, BlockPos blockPos,
      BlockState formerBlockState, boolean notify) {
    super.onPlace(blockState, level, blockPos, formerBlockState, notify);
    if (!level.isClientSide()) {
      level.setBlock(blockPos, blockState.setValue(POWERED, level.hasNeighborSignal(blockPos)),
          Block.UPDATE_CLIENTS);
    }
  }

  @Override
  @SuppressWarnings("java:S1874")
  public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block,
      BlockPos neighborBlockPos, boolean isMoving) {
    super.onNeighborChange(blockState, level, blockPos, neighborBlockPos);
    if (!level.isClientSide()) {
      level.setBlock(blockPos, blockState.setValue(POWERED, level.hasNeighborSignal(blockPos)),
          Block.UPDATE_CLIENTS);
    }
  }

  @Override
  @SuppressWarnings("java:S1874")
  public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos,
      CollisionContext collisionContext) {
    return SHAPE;
  }

  @Override
  @SuppressWarnings("java:S1874")
  public RenderShape getRenderShape(BlockState blockState) {
    return RenderShape.MODEL;
  }

  @Override
  public boolean canConnectRedstone(BlockState blockState, BlockGetter blockGetter,
      BlockPos blockPos, Direction direction) {
    return true;
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockState) {
    blockState.add(FACING, WORKING, POWERED);
  }

  @Override
  @Nullable
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    return this.defaultBlockState().setValue(FACING,
        context.getHorizontalDirection().getOpposite());
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return null;
  }

  @Override
  @SuppressWarnings("java:S1874")
  public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player,
      InteractionHand hand, BlockHitResult hitResult) {
    ItemStack itemStack = player.getItemInHand(hand);
    BlockEntity blockEntity = level.getBlockEntity(blockPos);

    // Ignore event, if the stored mob could be consumed.
    if ((itemStack.getItem() instanceof CapturedMob)
        && blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity
        && !mobFarmBlockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      return InteractionResult.PASS;
    }

    if (level.isClientSide) {
      return InteractionResult.SUCCESS;
    }

    // Take virtual captured mob from player inventory and place it in the farm.
    if (CapturedMobVirtual.hasCapturedMob(itemStack)
        && blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity
        && !mobFarmBlockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)
        && isAcceptedMobType(CapturedMobVirtual.getCapturedMobType(itemStack))) {
      mobFarmBlockEntity.takePlayerItem(MobFarmMenu.CAPTURED_MOB_SLOT, player, hand);
      return InteractionResult.CONSUME;
    }

    // Remove existing mob item from farm, if player is sneaking.
    if (player.isShiftKeyDown() && blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity
        && mobFarmBlockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      mobFarmBlockEntity.givePlayerItem(MobFarmMenu.CAPTURED_MOB_SLOT, level, player, hand,
          blockPos);
      return InteractionResult.CONSUME;
    }

    // Open Container
    this.openContainer(level, blockPos, player);
    return InteractionResult.CONSUME;
  }

  @Override
  public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState,
      @Nullable LivingEntity livingEntity, ItemStack itemStack) {
    BlockEntity blockEntity = level.getBlockEntity(blockPos);
    if (blockEntity instanceof MobFarmBlockEntity blockEntityInstance) {
      blockEntityInstance.updateLevel(level);
      blockEntityInstance.setOwner(livingEntity);
    }
  }

  @Override
  @SuppressWarnings("deprecation")
  public void onRemove(BlockState blockState, Level level, BlockPos blockPos,
      BlockState blockState2, boolean flag) {
    if (!blockState.is(blockState2.getBlock())) {
      BlockEntity blockEntity = level.getBlockEntity(blockPos);
      if (blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity) {
        if (level instanceof ServerLevel) {
          Containers.dropContents(level, blockPos, mobFarmBlockEntity);
        }
        level.updateNeighbourForOutputSignal(blockPos, this);
      }
      super.onRemove(blockState, level, blockPos, blockState2, flag);
    }
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
      BlockEntityType<T> blockEntityType) {
    return null;
  }

  @Override
  public boolean canConsumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, Player player, ItemStack itemStack) {
    if (itemStack.getItem() instanceof CapturedMob) {
      String capturedMobType = CapturedMob.getCapturedMobType(itemStack);
      return isAcceptedMobType(capturedMobType);
    } else if (CapturedMobVirtual.isSupported(itemStack)) {
      String capturedMobType = CapturedMobVirtual.getCapturedMobType(itemStack);
      return isAcceptedMobType(capturedMobType);
    }
    return false;
  }

  @Override
  public InteractionResult consumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, ItemStack itemStack, UseOnContext context) {
    return null;
  }

  @Override
  public void appendHoverText(ItemStack itemStack, @Nullable BlockGetter blockGetter,
      List<Component> tooltipList, TooltipFlag tooltipFlag) {
    super.appendHoverText(itemStack, blockGetter, tooltipList, tooltipFlag);

    // Display possible accepted mobs.
    appendHoverTextAcceptedMobs(tooltipList);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  @SuppressWarnings("java:S1874")
  public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
    return 1F;
  }

  public static void logAcceptedMobTypes(String name, Set<String> acceptedMobTypes) {
    if (acceptedMobTypes == null || acceptedMobTypes.isEmpty()) {
      log.info("The {} will accept all mobs.", name);
    } else {
      log.info("The {} will accept only the following mobs: {}", name, acceptedMobTypes);
    }
  }

}
