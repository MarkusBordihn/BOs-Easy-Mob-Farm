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

package de.markusbordihn.easymobfarm.block.entity.farm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.menu.farm.SwampFarmMenu;

@EventBusSubscriber
public class SwampFarmEntity extends MobFarmBlockEntity {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  // Config settings
  private static int swampFarmProcessTime = COMMON.swampFarmProcessTime.get();
  private static int farmProcessingTime = swampFarmProcessTime * 20;

  public SwampFarmEntity(BlockPos blockPos, BlockState blockState) {
    super(ModBlocks.SWAMP_FARM_ENTITY.get(), blockPos, blockState);
  }

  public SwampFarmEntity(BlockEntityType<?> blockEntity, BlockPos blockPos,
      BlockState blockState) {
    super(blockEntity, blockPos, blockState);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    swampFarmProcessTime = COMMON.swampFarmProcessTime.get();
    farmProcessingTime = swampFarmProcessTime * 20;
    log.info("{}: SwampFarm Entity with drops every {}s", Constants.LOG_MOB_FARM_PREFIX,
        swampFarmProcessTime);
  }

  @Override
  protected Component getDefaultName() {
    return new TranslatableComponent("container.easy_mob_farm.swamp_farm");
  }

  @Override
  protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
    return new SwampFarmMenu(windowId, inventory, this, this.dataAccess);
  }

  @Override
  public int getFarmProcessingTime() {
    return farmProcessingTime;
  }

  @Override
  public void processAdditionalEffects(Level level, BlockPos blockPos,
      MobFarmBlockEntity blockEntity, ItemStack capturedMob) {
    super.processAdditionalEffects(level, blockPos, blockEntity, capturedMob);
    level.playSound(null, blockPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1.0F, 1.0F);
  }

}
