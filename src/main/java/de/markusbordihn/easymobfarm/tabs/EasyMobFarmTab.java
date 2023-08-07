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

package de.markusbordihn.easymobfarm.tabs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.Output;

import net.minecraftforge.event.CreativeModeTabEvent;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.ModItems;

public class EasyMobFarmTab {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected EasyMobFarmTab() {}

  protected static CreativeModeTab MOB_FARM;

  protected static CreativeModeTab TOOLS;

  public static void handleCreativeModeTabRegister(CreativeModeTabEvent.Register event) {

    log.info("{} creative mod tabs ...", Constants.LOG_REGISTER_PREFIX);

    MOB_FARM = event.registerCreativeModeTab(new ResourceLocation(Constants.MOD_ID, "mob_farm"),
        builder -> {
          builder.icon(() -> new ItemStack(ModItems.IRON_ANIMAL_PLAINS_FARM.get()))
              .displayItems(EasyMobFarmTab::addMobFarmTabItems)
              .title(Component.translatable("itemGroup.easy_mob_farm.mob_farm")).build();
        });

    TOOLS =
        event.registerCreativeModeTab(new ResourceLocation(Constants.MOD_ID, "tools"), builder -> {
          builder.icon(() -> new ItemStack(ModItems.COLLAR_SMALL.get()))
              .displayItems(EasyMobFarmTab::addToolsTabItems)
              .title(Component.translatable("itemGroup.easy_mob_farm.tools")).build();
        });
  }

  public static void handleCreativeModeTab(CreativeModeTabEvent.BuildContents event) {
    if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
      // Mob Farms Templates
      event.accept(ModItems.COPPER_MOB_FARM_TEMPLATE.get());
      event.accept(ModItems.IRON_MOB_FARM_TEMPLATE.get());
      event.accept(ModItems.GOLD_MOB_FARM_TEMPLATE.get());
      event.accept(ModItems.NETHERITE_MOB_FARM_TEMPLATE.get());
    }
  }

  private static void addMobFarmTabItems(FeatureFlagSet featureFlagSet, Output outputTab,
      boolean hasPermissions) {
    // Mob Farms - Tier Copper
    outputTab.accept(ModItems.COPPER_ANIMAL_PLAINS_FARM.get());
    outputTab.accept(ModItems.COPPER_BEE_HIVE_FARM.get());
    outputTab.accept(ModItems.COPPER_DESERT_FARM.get());
    outputTab.accept(ModItems.COPPER_JUNGLE_FARM.get());
    outputTab.accept(ModItems.COPPER_MONSTER_PLAINS_CAVE_FARM.get());
    outputTab.accept(ModItems.COPPER_NETHER_FORTRESS_FARM.get());
    outputTab.accept(ModItems.COPPER_OCEAN_FARM.get());
    outputTab.accept(ModItems.COPPER_SWAMP_FARM.get());

    // Mob Farms - Tier Iron
    outputTab.accept(ModItems.IRON_ANIMAL_PLAINS_FARM.get());
    outputTab.accept(ModItems.IRON_BEE_HIVE_FARM.get());
    outputTab.accept(ModItems.IRON_DESERT_FARM.get());
    outputTab.accept(ModItems.IRON_JUNGLE_FARM.get());
    outputTab.accept(ModItems.IRON_MONSTER_PLAINS_CAVE_FARM.get());
    outputTab.accept(ModItems.IRON_NETHER_FORTRESS_FARM.get());
    outputTab.accept(ModItems.IRON_OCEAN_FARM.get());
    outputTab.accept(ModItems.IRON_SWAMP_FARM.get());

    // Mob Farms - Tier Gold
    outputTab.accept(ModItems.GOLD_ANIMAL_PLAINS_FARM.get());
    outputTab.accept(ModItems.GOLD_BEE_HIVE_FARM.get());
    outputTab.accept(ModItems.GOLD_DESERT_FARM.get());
    outputTab.accept(ModItems.GOLD_JUNGLE_FARM.get());
    outputTab.accept(ModItems.GOLD_MONSTER_PLAINS_CAVE_FARM.get());
    outputTab.accept(ModItems.GOLD_NETHER_FORTRESS_FARM.get());
    outputTab.accept(ModItems.GOLD_OCEAN_FARM.get());
    outputTab.accept(ModItems.GOLD_SWAMP_FARM.get());

    // Mob Farms - Tier Netherite
    outputTab.accept(ModItems.NETHERITE_ANIMAL_PLAINS_FARM.get());
    outputTab.accept(ModItems.NETHERITE_BEE_HIVE_FARM.get());
    outputTab.accept(ModItems.NETHERITE_DESERT_FARM.get());
    outputTab.accept(ModItems.NETHERITE_JUNGLE_FARM.get());
    outputTab.accept(ModItems.NETHERITE_MONSTER_PLAINS_CAVE_FARM.get());
    outputTab.accept(ModItems.NETHERITE_NETHER_FORTRESS_FARM.get());
    outputTab.accept(ModItems.NETHERITE_OCEAN_FARM.get());
    outputTab.accept(ModItems.NETHERITE_SWAMP_FARM.get());

    // Mob Farms - Special
    outputTab.accept(ModItems.CREATIVE_MOB_FARM.get());
  }

  private static void addToolsTabItems(FeatureFlagSet featureFlagSet, Output outputTab,
      boolean hasPermissions) {
    // Mob Capture Items
    outputTab.accept(ModItems.CAPTURE_NET.get());
    outputTab.accept(ModItems.CATCH_CAGE.get());
    outputTab.accept(ModItems.CATCH_CAGE_SMALL.get());
    outputTab.accept(ModItems.COLLAR_SMALL.get());
    outputTab.accept(ModItems.CREATIVE_MOB_CATCHER.get());
    outputTab.accept(ModItems.ENDER_LASSO.get());
    outputTab.accept(ModItems.FISHING_BOWL_SMALL.get());
    outputTab.accept(ModItems.FISHING_NET_SMALL.get());
    outputTab.accept(ModItems.GOLDEN_LASSO.get());
    outputTab.accept(ModItems.INSECT_NET.get());
    outputTab.accept(ModItems.NETHERITE_LASSO.get());
    outputTab.accept(ModItems.URN_SMALL.get());
    outputTab.accept(ModItems.WITCH_BOTTLE.get());
  }
}
