/**
 * Copyright 2021 Markus Bordihn
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

import de.markusbordihn.easymobfarm.block.entity.ChickenMobFarmEntity;
import de.markusbordihn.easymobfarm.item.CapturedMobItem;

public class ChickenMobFarm extends MobFarmBlock {

  public static final String NAME = "chicken_mob_farm";

  public ChickenMobFarm(BlockBehaviour.Properties properties) {
    super(properties);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new ChickenMobFarmEntity(ModBlocks.CHICKEN_MOB_FARM_ENTITY.get(), blockPos, blockState);
  }

  @Override
  public boolean canConsumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, Player player, ItemStack itemStack) {
    if (itemStack.getItem() instanceof CapturedMobItem capturedMobItem) {
      String capturedMobType = capturedMobItem.getCapturedMobType(itemStack);
      if (capturedMobType.equals("minecraft:chicken")) {
        return true;
      }
    }
    return false;
  }

  @Override
  public InteractionResult consumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, ItemStack itemStack, UseOnContext context) {
    ChickenMobFarmEntity chickenMobFarmEntity = (ChickenMobFarmEntity) blockEntity;
    chickenMobFarmEntity.updateLevel(level);
    if (!chickenMobFarmEntity.hasMob()) {
      chickenMobFarmEntity.setMob(itemStack);
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
        : createTickerHelper(blockEntityType, ModBlocks.CHICKEN_MOB_FARM_ENTITY.get(),
            ChickenMobFarmEntity::serverTick);
  }

}
