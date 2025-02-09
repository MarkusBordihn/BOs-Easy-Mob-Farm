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

package de.markusbordihn.easymobfarm.tabs;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import de.markusbordihn.easymobfarm.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModTabs {

  public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_MOB_CAPTURE_CARDS =
      CREATIVE_TABS.register(
          "mob_capture_cards",
          () ->
              CreativeModeTab.builder()
                  .icon(() -> ModItems.CREATIVE_MOB_CAPTURE_CARD.get().getDefaultInstance())
                  .displayItems(new MobCaptureCards())
                  .title(Component.translatable("itemGroup.easy_mob_farm.mob_capture_cards"))
                  .build());
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_MOB_FARMS =
      CREATIVE_TABS.register(
          "mob_farms",
          () ->
              CreativeModeTab.builder()
                  .icon(() -> ModBlockItems.CREATIVE_MOB_FARM.get().getDefaultInstance())
                  .displayItems(new MobFarms())
                  .title(Component.translatable("itemGroup.easy_mob_farm.mob_farms"))
                  .build());
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_MOB_FARM_UPGRADES =
      CREATIVE_TABS.register(
          "mob_farm_upgrades",
          () ->
              CreativeModeTab.builder()
                  .icon(() -> ModItems.SMALL_SLOT_UPGRADE.get().getDefaultInstance())
                  .displayItems(new MobFarmUpgrades())
                  .title(Component.translatable("itemGroup.easy_mob_farm.mob_farm_upgrades"))
                  .build());
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_MOB_CATCHER =
      CREATIVE_TABS.register(
          "mob_catcher",
          () ->
              CreativeModeTab.builder()
                  .icon(() -> ModItems.CREATIVE_MOB_CATCHER.get().getDefaultInstance())
                  .displayItems(new MobCatcher())
                  .title(Component.translatable("itemGroup.easy_mob_farm.mob_catcher"))
                  .build());
  public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_MOB_FARM_CONSUMABLES =
      CREATIVE_TABS.register(
          "mob_farm_consumables",
          () ->
              CreativeModeTab.builder()
                  .icon(() -> ModItems.MILK_BOTTLE.get().getDefaultInstance())
                  .displayItems(new MobFarmConsumables())
                  .title(Component.translatable("itemGroup.easy_mob_farm.mob_farm_consumables"))
                  .build());

  private ModTabs() {}
}
