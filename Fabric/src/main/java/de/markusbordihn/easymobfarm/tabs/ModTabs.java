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
import de.markusbordihn.easymobfarm.compat.CompatConstants;
import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import java.util.Set;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModTabs {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  public static CreativeModeTab TAB_MOB_CAPTURE_CARDS;
  public static CreativeModeTab TAB_MOB_FARMS;
  public static CreativeModeTab TAB_MOB_FARM_UPGRADES;
  public static CreativeModeTab TAB_MOB_CATCHER;
  public static CreativeModeTab TAB_MOB_FARM_CONSUMABLES;

  private ModTabs() {}

  public static void registerModTabs() {
    log.info("{} Registering Mod Tabs ...", Constants.LOG_REGISTER_PREFIX);

    TAB_MOB_CAPTURE_CARDS =
        FabricItemGroupBuilder.create(new ResourceLocation(Constants.MOD_ID, "mob_capture_cards"))
            .icon(Items.BLANK_MOB_CAPTURE_CARD::getDefaultInstance)
            .appendItems(
                stack -> {
                  // Default mob capture cards
                  stack.add(Items.BLANK_MOB_CAPTURE_CARD.getDefaultInstance());
                  stack.add(Items.CREATIVE_MOB_CAPTURE_CARD.getDefaultInstance());

                  // Add custom mob capture cards
                  Set<ItemStack> mobCaptureCards =
                      CustomMobCaptureCards.getCustomMobCaptureCards(Items.MOB_CAPTURE_CARD);
                  stack.addAll(mobCaptureCards);
                })
            .build();

    TAB_MOB_FARMS =
        FabricItemGroupBuilder.create(new ResourceLocation(Constants.MOD_ID, "mob_farms"))
            .icon(ModBlockItems.CREATIVE_MOB_FARM::getDefaultInstance)
            .appendItems(
                stack -> {
                  // Default mob farm items
                  stack.add(ModBlockItems.CREATIVE_MOB_FARM.getDefaultInstance());

                  // Add custom mob farm tier items
                  stack.addAll(
                      CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.ANIMAL_PLAINS_FARM));
                  stack.addAll(CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.BEE_HIVE_FARM));
                  stack.addAll(CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.DESERT_FARM));
                  stack.addAll(CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.IRON_GOLEM_FARM));
                  stack.addAll(CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.JUNGLE_FARM));
                  stack.addAll(CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.LUCKY_DROP_FARM));
                  stack.addAll(
                      CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.MONSTER_PLAINS_CAVE_FARM));
                  stack.addAll(
                      CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.NETHER_FORTRESS_FARM));
                  stack.addAll(CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.OCEAN_FARM));
                  stack.addAll(CustomMobFarmBlocks.getMobFarmTiers(ModBlockItems.SWAMP_FARM));
                })
            .build();

    TAB_MOB_FARM_UPGRADES =
        FabricItemGroupBuilder.create(new ResourceLocation(Constants.MOD_ID, "mob_farm_upgrades"))
            .icon(Items.SMALL_SLOT_UPGRADE::getDefaultInstance)
            .appendItems(
                stack -> {
                  // Default mob farm upgrades
                  stack.add(Items.BIG_SLOT_UPGRADE.getDefaultInstance());
                  stack.add(Items.CREATIVE_SPEED_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.EGG_COLLECTOR_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.EXPERIENCE_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.FROG_CATALYST_COLD_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.FROG_CATALYST_TEMPERATE_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.FROG_CATALYST_WARM_ENHANCEMENT.getDefaultInstance());

                  if (CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
                    stack.add(Items.FROG_CATALYST_WHITE_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_ORANGE_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_MAGENTA_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_LIGHT_BLUE_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_YELLOW_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_LIME_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_PINK_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_GRAY_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_LIGHT_GRAY_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_CYAN_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_PURPLE_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_BLUE_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_BROWN_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_GREEN_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_RED_ENHANCEMENT.getDefaultInstance());
                    stack.add(Items.FROG_CATALYST_BLACK_ENHANCEMENT.getDefaultInstance());
                  }

                  stack.add(Items.HONEY_EXTRACTOR_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.HONEY_HARVESTER_FRAME_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.LOOT_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.LUCK_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.MILK_EXTRACTOR_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.NO_FLOWERS_FILTER.getDefaultInstance());
                  stack.add(Items.NO_MEAT_FILTER.getDefaultInstance());
                  stack.add(Items.POLLEN_TRAP_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.SHEEP_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.SMALL_SLOT_UPGRADE.getDefaultInstance());
                  stack.add(Items.SPEED_ENHANCEMENT.getDefaultInstance());
                  stack.add(Items.SWORD_ENHANCEMENT.getDefaultInstance());
                })
            .build();

    TAB_MOB_CATCHER =
        FabricItemGroupBuilder.create(new ResourceLocation(Constants.MOD_ID, "mob_catcher"))
            .icon(Items.CREATIVE_MOB_CATCHER::getDefaultInstance)
            .appendItems(
                stack -> {
                  // Default mob capture
                  stack.add(Items.CREATIVE_MOB_CATCHER.getDefaultInstance());
                  stack.add(Items.ENDURING_CAPTURE_NET.getDefaultInstance());
                  stack.add(Items.IRONBOUND_CONTAINMENT_CAGE.getDefaultInstance());
                  stack.add(Items.MYSTIC_BINDING_CRYSTAL.getDefaultInstance());
                  stack.add(Items.VOID_BINDING_CHAIN.getDefaultInstance());
                })
            .build();

    TAB_MOB_FARM_CONSUMABLES =
        FabricItemGroupBuilder.create(
                new ResourceLocation(Constants.MOD_ID, "mob_farm_consumables"))
            .icon(Items.MILK_BOTTLE::getDefaultInstance)
            .appendItems(
                stack -> {
                  // Default mob farm consumables
                  stack.add(Items.MILK_BOTTLE.getDefaultInstance());
                })
            .build();
  }
}
