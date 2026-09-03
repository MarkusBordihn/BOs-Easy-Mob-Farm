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

package de.markusbordihn.easymobfarm.item;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModBlockItems {

  public static final Item TIER_0_MOB_FARM_TEMPLATE =
      new MobFarmTemplateItem(MobFarmTemplateItem.ID_TIER_0, ModBlocks.TIER_0_MOB_FARM_TEMPLATE);
  public static final Item TIER_1_MOB_FARM_TEMPLATE =
      new MobFarmTemplateItem(MobFarmTemplateItem.ID_TIER_1, ModBlocks.TIER_1_MOB_FARM_TEMPLATE);
  public static final Item TIER_2_MOB_FARM_TEMPLATE =
      new MobFarmTemplateItem(MobFarmTemplateItem.ID_TIER_2, ModBlocks.TIER_2_MOB_FARM_TEMPLATE);
  public static final Item TIER_3_MOB_FARM_TEMPLATE =
      new MobFarmTemplateItem(MobFarmTemplateItem.ID_TIER_3, ModBlocks.TIER_3_MOB_FARM_TEMPLATE);

  public static final Item CREATIVE_MOB_FARM =
      new BlockItem(
          ModBlocks.CREATIVE_MOB_FARM,
          new Item.Properties()
              .useBlockDescriptionPrefix()
              .setId(
                  ResourceKey.create(
                      Registries.ITEM,
                      Identifier.fromNamespaceAndPath(
                          Constants.MOD_ID, MobFarmType.CREATIVE_MOB_FARM.getId()))));
  public static final Item ANIMAL_PLAINS_FARM =
      new MobFarmBlockItem(MobFarmType.ANIMAL_PLAINS_FARM, ModBlocks.ANIMAL_PLAINS_FARM);
  public static final Item BEE_HIVE_FARM =
      new MobFarmBlockItem(MobFarmType.BEE_HIVE_FARM, ModBlocks.BEE_HIVE_FARM);
  public static final Item DESERT_FARM =
      new MobFarmBlockItem(MobFarmType.DESERT_FARM, ModBlocks.DESERT_FARM);
  public static final Item IRON_GOLEM_FARM =
      new MobFarmBlockItem(MobFarmType.IRON_GOLEM_FARM, ModBlocks.IRON_GOLEM_FARM);
  public static final Item JUNGLE_FARM =
      new MobFarmBlockItem(MobFarmType.JUNGLE_FARM, ModBlocks.JUNGLE_FARM);
  public static final Item LUCKY_DROP_FARM =
      new MobFarmBlockItem(MobFarmType.LUCKY_DROP_FARM, ModBlocks.LUCKY_DROP_FARM);
  public static final Item MONSTER_PLAINS_CAVE_FARM =
      new MobFarmBlockItem(
          MobFarmType.MONSTER_PLAINS_CAVE_FARM, ModBlocks.MONSTER_PLAINS_CAVE_FARM);
  public static final Item NETHER_FORTRESS_FARM =
      new MobFarmBlockItem(MobFarmType.NETHER_FORTRESS_FARM, ModBlocks.NETHER_FORTRESS_FARM);
  public static final Item OCEAN_FARM =
      new MobFarmBlockItem(MobFarmType.OCEAN_FARM, ModBlocks.OCEAN_FARM);
  public static final Item SWAMP_FARM =
      new MobFarmBlockItem(MobFarmType.SWAMP_FARM, ModBlocks.SWAMP_FARM);
  public static final Item END_FARM =
      new MobFarmBlockItem(MobFarmType.END_FARM, ModBlocks.END_FARM);
  public static final Item NETHER_WASTES_FARM =
      new MobFarmBlockItem(MobFarmType.NETHER_WASTES_FARM, ModBlocks.NETHER_WASTES_FARM);

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private ModBlockItems() {}

  public static void registerModBlockItems() {
    log.debug("{} Mob Farm Templates items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(MobFarmTemplateItem.ID_TIER_0, TIER_0_MOB_FARM_TEMPLATE);
    registerItem(MobFarmTemplateItem.ID_TIER_1, TIER_1_MOB_FARM_TEMPLATE);
    registerItem(MobFarmTemplateItem.ID_TIER_2, TIER_2_MOB_FARM_TEMPLATE);
    registerItem(MobFarmTemplateItem.ID_TIER_3, TIER_3_MOB_FARM_TEMPLATE);
    ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS)
        .register(
            entries -> {
              entries.accept(TIER_0_MOB_FARM_TEMPLATE);
              entries.accept(TIER_1_MOB_FARM_TEMPLATE);
              entries.accept(TIER_2_MOB_FARM_TEMPLATE);
              entries.accept(TIER_3_MOB_FARM_TEMPLATE);
            });

    log.debug("{} Mob Farms items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(MobFarmType.CREATIVE_MOB_FARM.getId(), CREATIVE_MOB_FARM);
    registerItem(MobFarmType.ANIMAL_PLAINS_FARM.getId(), ANIMAL_PLAINS_FARM);
    registerItem(MobFarmType.BEE_HIVE_FARM.getId(), BEE_HIVE_FARM);
    registerItem(MobFarmType.DESERT_FARM.getId(), DESERT_FARM);
    registerItem(MobFarmType.IRON_GOLEM_FARM.getId(), IRON_GOLEM_FARM);
    registerItem(MobFarmType.JUNGLE_FARM.getId(), JUNGLE_FARM);
    registerItem(MobFarmType.LUCKY_DROP_FARM.getId(), LUCKY_DROP_FARM);
    registerItem(MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId(), MONSTER_PLAINS_CAVE_FARM);
    registerItem(MobFarmType.NETHER_FORTRESS_FARM.getId(), NETHER_FORTRESS_FARM);
    registerItem(MobFarmType.OCEAN_FARM.getId(), OCEAN_FARM);
    registerItem(MobFarmType.SWAMP_FARM.getId(), SWAMP_FARM);
    registerItem(MobFarmType.END_FARM.getId(), END_FARM);
    registerItem(MobFarmType.NETHER_WASTES_FARM.getId(), NETHER_WASTES_FARM);
  }

  private static void registerItem(final String id, final Item item) {
    Registry.register(BuiltInRegistries.ITEM, Constants.MOD_ID + ":" + id, item);
  }
}
