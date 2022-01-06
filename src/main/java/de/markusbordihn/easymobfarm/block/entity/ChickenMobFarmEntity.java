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

package de.markusbordihn.easymobfarm.block.entity;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.config.CommonConfig;

@EventBusSubscriber
public class ChickenMobFarmEntity extends MobFarmBlockEntity {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  // Config settings
  private static int chickenFarmProcessTime = COMMON.chickenFarmProcessTime.get();
  private static boolean chickenFarmDropEggs = COMMON.chickenFarmDropEggs.get();
  private static boolean chickenFarmDropRawChicken = COMMON.chickenFarmDropRawChicken.get();
  private static int farmProcessingTime = 0;

  public ChickenMobFarmEntity(BlockPos blockPos, BlockState blockState) {
    super(ModBlocks.CHICKEN_MOB_FARM_ENTITY.get(), blockPos, blockState);
  }

  public ChickenMobFarmEntity(BlockEntityType<?> blockEntity, BlockPos blockPos,
      BlockState blockState) {
    super(blockEntity, blockPos, blockState);
  }

  @Override
  protected Component getDefaultName() {
    return new TranslatableComponent("container.easy_mob_farm.chicken_mob_farm");
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    chickenFarmProcessTime = COMMON.chickenFarmProcessTime.get();
    chickenFarmDropEggs = COMMON.chickenFarmDropEggs.get();
    chickenFarmDropRawChicken = COMMON.chickenFarmDropRawChicken.get();
    farmProcessingTime = chickenFarmProcessTime * 20;
    log.info("{}: Chicken Farm with drops every {}s (eggs:{}, raw chicken: {})",
        Constants.LOG_MOB_FARM_PREFIX, chickenFarmProcessTime,
        chickenFarmDropEggs, chickenFarmDropRawChicken);
  }

  @Override
  public List<ItemStack> getLootDrops(ResourceLocation lootTableLocation, Level level) {
    List<ItemStack> lootDrops = super.getLootDrops(lootTableLocation, level);

    // Add eggs to loot drops if enabled.
    if (chickenFarmDropEggs) {
      lootDrops.add(new ItemStack(Items.EGG));
    }
    return lootDrops;
  }

  @Override
  public boolean allowLootDropItem(ItemStack itemStack) {
    return !chickenFarmDropRawChicken && !itemStack.is(Items.CHICKEN);
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
