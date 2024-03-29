/*
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

package de.markusbordihn.easymobfarm.block.farm.iron;

import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronSwampFarmEntity;
import de.markusbordihn.easymobfarm.data.FarmTier;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
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

public class IronSwampFarm extends MobFarmBlock {

  public static final String NAME = "iron_swamp_farm";
  public static final String LEGACY_NAME = "swamp_farm";

  public IronSwampFarm(BlockBehaviour.Properties properties) {
    super(properties);
  }

  @Override
  public FarmTier getFarmTier() {
    return FarmTier.IRON;
  }

  @Override
  public String getFarmName() {
    return NAME;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new IronSwampFarmEntity(ModBlocks.IRON_SWAMP_FARM_ENTITY.get(), blockPos, blockState);
  }

  @Override
  protected void openContainer(Level level, BlockPos blockPos, Player player) {
    if (level.getBlockEntity(blockPos) instanceof IronSwampFarmEntity swampFarmEntity) {
      player.openMenu(swampFarmEntity);
    }
  }

  @Override
  public InteractionResult consumeCapturedMob(
      Level level,
      BlockPos blockPos,
      BlockState blockState,
      BlockEntity blockEntity,
      ItemStack itemStack,
      UseOnContext context) {
    IronSwampFarmEntity swampMobFarmEntity = (IronSwampFarmEntity) blockEntity;
    swampMobFarmEntity.updateLevel(level);
    if (!swampMobFarmEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      swampMobFarmEntity.setItem(MobFarmMenu.CAPTURED_MOB_SLOT, itemStack);
      Player player = context.getPlayer();
      if (player != null) {
        player.setItemInHand(context.getHand(), ItemStack.EMPTY);
      }
      return InteractionResult.CONSUME;
    }
    return InteractionResult.PASS;
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
      Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
    return level.isClientSide
        ? null
        : createTickerHelper(
            blockEntityType,
            ModBlocks.IRON_SWAMP_FARM_ENTITY.get(),
            IronSwampFarmEntity::serverTick);
  }
}
