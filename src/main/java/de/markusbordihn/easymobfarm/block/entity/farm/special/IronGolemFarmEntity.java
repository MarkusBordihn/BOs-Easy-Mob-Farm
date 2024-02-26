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

package de.markusbordihn.easymobfarm.block.entity.farm.special;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.data.FarmTier;
import de.markusbordihn.easymobfarm.menu.farm.special.IronGolemFarmMenu;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber
public class IronGolemFarmEntity extends MobFarmBlockEntity {

  private static int farmProcessingTime = 60 * 20;
  private static SoundEvent farmDropSound;

  public IronGolemFarmEntity(BlockPos blockPos, BlockState blockState) {
    this(ModBlocks.IRON_GOLEM_FARM_ENTITY.get(), blockPos, blockState);
  }

  public IronGolemFarmEntity(
      BlockEntityType<?> blockEntity, BlockPos blockPos, BlockState blockState) {
    super(blockEntity, blockPos, blockState);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    farmProcessingTime = COMMON.ironGolemFarmProcessTime.get() * 20;
    log.info(
        "{}: Iron Golem Plains Farm Entity with drops every {}s",
        Constants.LOG_MOB_FARM_PREFIX,
        COMMON.ironGolemFarmProcessTime.get());
    String farmDropSoundName = COMMON.ironGolemFarmDropSound.get();
    if (Boolean.TRUE.equals(COMMON.playDropSound.get())
        && farmDropSoundName != null
        && !farmDropSoundName.isEmpty()) {
      farmDropSound =
          ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(farmDropSoundName));
      if (farmDropSound != null) {
        log.info(
            "{}: Iron Golem Plains Farm Entity will play drop sound: {}",
            Constants.LOG_MOB_FARM_PREFIX,
            farmDropSound.getLocation());
      }
    }
  }

  @Override
  protected List<ItemStack> processLootDrop(
      ItemStack capturedMob, ItemStack weaponItem, Level level) {
    List<ItemStack> lootDrops = super.processLootDrop(capturedMob, weaponItem, level);
    if (Boolean.TRUE.equals(COMMON.ironGolemFarmFilterPoppy.get())) {
      lootDrops.removeIf(itemStack -> itemStack.getItem().equals(Items.POPPY));
    }
    return lootDrops;
  }

  @Override
  protected Component getDefaultName() {
    return Component.translatable("container.easy_mob_farm.iron_golem_farm");
  }

  @Override
  protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
    return new IronGolemFarmMenu(windowId, inventory, this, this.dataAccess);
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
    return FarmTier.IRON;
  }
}
