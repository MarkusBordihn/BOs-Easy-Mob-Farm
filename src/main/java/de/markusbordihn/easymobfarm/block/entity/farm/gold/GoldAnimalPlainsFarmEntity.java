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

package de.markusbordihn.easymobfarm.block.entity.farm.gold;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.data.FarmTier;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldAnimalPlainsFarmMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class GoldAnimalPlainsFarmEntity extends MobFarmBlockEntity {

  private static int farmProcessingTime = 60 * 20;
  private static SoundEvent farmDropSound;

  public GoldAnimalPlainsFarmEntity(BlockPos blockPos, BlockState blockState) {
    this(ModBlocks.GOLD_ANIMAL_PLAINS_FARM_ENTITY.get(), blockPos, blockState);
  }

  public GoldAnimalPlainsFarmEntity(BlockEntityType<?> blockEntity, BlockPos blockPos,
      BlockState blockState) {
    super(blockEntity, blockPos, blockState);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    farmProcessingTime = COMMON.goldAnimalPlainsFarmProcessTime.get() * 20;
    log.info("{}: Gold Animal Plains Farm Entity with drops every {}s",
        Constants.LOG_MOB_FARM_PREFIX, COMMON.goldAnimalPlainsFarmProcessTime.get());
    String farmDropSoundName = COMMON.goldAnimalPlainsFarmDropSound.get();
    if (Boolean.TRUE.equals(COMMON.playDropSound.get()) && farmDropSoundName != null
        && !farmDropSoundName.isEmpty()) {
      farmDropSound =
          ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(farmDropSoundName));
      if (farmDropSound != null) {
        log.info("{}: Gold Animal Plains Farm Entity will play drop sound: {}",
            Constants.LOG_MOB_FARM_PREFIX, farmDropSound.getRegistryName());
      }
    }
  }

  @Override
  protected Component getDefaultName() {
    return new TranslatableComponent("container.easy_mob_farm.animal_plains_farm");
  }

  @Override
  protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
    return new GoldAnimalPlainsFarmMenu(windowId, inventory, this, this.dataAccess);
  }

  @Override
  public int getFarmProcessingTime() {
    return farmProcessingTime;
  }

  @Override
  public SoundEvent getFarmDropSound() {
    return farmDropSound;
  }

  @Override
  public FarmTier getFarmTier() {
    return FarmTier.GOLD;
  }

}
