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
import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModTabs {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private ModTabs() {}

  public static void registerModTabs() {
    log.debug("{} Registering Mod Tabs ...", Constants.LOG_REGISTER_PREFIX);

    Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        Constants.MOD_ID + ":mob_capture_cards",
        FabricItemGroup.builder()
            .icon(() -> Items.BLANK_MOB_CAPTURE_CARD.asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.easy_mob_farm.mob_capture_cards"))
            .displayItems(new MobCaptureCards())
            .build());

    Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        Constants.MOD_ID + ":mob_farms",
        FabricItemGroup.builder()
            .icon(() -> ModBlockItems.CREATIVE_MOB_FARM.asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.easy_mob_farm.mob_farms"))
            .displayItems(new MobFarms())
            .build());

    Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        Constants.MOD_ID + ":mob_farm_upgrades",
        FabricItemGroup.builder()
            .icon(() -> Items.SMALL_SLOT_UPGRADE.asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.easy_mob_farm.mob_farm_upgrades"))
            .displayItems(new MobFarmUpgrades())
            .build());

    Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        Constants.MOD_ID + ":mob_catcher",
        FabricItemGroup.builder()
            .icon(() -> Items.CREATIVE_MOB_CATCHER.asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.easy_mob_farm.mob_catcher"))
            .displayItems(new MobCatcher())
            .build());

    Registry.register(
        BuiltInRegistries.CREATIVE_MODE_TAB,
        Constants.MOD_ID + ":mob_farm_consumables",
        FabricItemGroup.builder()
            .icon(() -> Items.MILK_BOTTLE.asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.easy_mob_farm.mob_farm_consumables"))
            .displayItems(new MobFarmConsumables())
            .build());
  }
}
