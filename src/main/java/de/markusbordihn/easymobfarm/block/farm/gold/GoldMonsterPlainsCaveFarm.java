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

package de.markusbordihn.easymobfarm.block.farm.gold;

import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldMonsterPlainsCaveFarmEntity;
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

public class GoldMonsterPlainsCaveFarm extends MobFarmBlock {

  public static final String NAME = "gold_monster_plains_cave_farm";

  public GoldMonsterPlainsCaveFarm(BlockBehaviour.Properties properties) {
    super(properties);
  }

  @Override
  public String getFarmDescriptionId() {
    return "supported_monster";
  }

  @Override
  public FarmTier getFarmTier() {
    return FarmTier.GOLD;
  }

  @Override
  public String getFarmName() {
    return NAME;
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new GoldMonsterPlainsCaveFarmEntity(ModBlocks.GOLD_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
        blockPos, blockState);
  }

  @Override
  protected void openContainer(Level level, BlockPos blockPos, Player player) {
    if (level.getBlockEntity(
        blockPos) instanceof GoldMonsterPlainsCaveFarmEntity monsterPlainsCaveFarmEntity) {
      player.openMenu(monsterPlainsCaveFarmEntity);
    }
  }

  @Override
  public InteractionResult consumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, ItemStack itemStack, UseOnContext context) {
    GoldMonsterPlainsCaveFarmEntity monsterPlainsCaveMobFarmEntity =
        (GoldMonsterPlainsCaveFarmEntity) blockEntity;
    monsterPlainsCaveMobFarmEntity.updateLevel(level);
    if (!monsterPlainsCaveMobFarmEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      monsterPlainsCaveMobFarmEntity.setItem(MobFarmMenu.CAPTURED_MOB_SLOT, itemStack);
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
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
      BlockEntityType<T> blockEntityType) {
    return level.isClientSide ? null
        : createTickerHelper(blockEntityType, ModBlocks.GOLD_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
            GoldMonsterPlainsCaveFarmEntity::serverTick);
  }

}
