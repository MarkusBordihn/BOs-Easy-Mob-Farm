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
import de.markusbordihn.easymobfarm.tabs.ModTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  public static final RegistryObject<Item> TIER_0_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_0,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_0,
                  ModBlocks.TIER_0_MOB_FARM_TEMPLATE.get(),
                  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
  public static final RegistryObject<Item> TIER_1_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_1,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_1,
                  ModBlocks.TIER_1_MOB_FARM_TEMPLATE.get(),
                  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
  public static final RegistryObject<Item> TIER_2_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_2,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_2,
                  ModBlocks.TIER_2_MOB_FARM_TEMPLATE.get(),
                  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
  public static final RegistryObject<Item> TIER_3_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_3,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_3,
                  ModBlocks.TIER_3_MOB_FARM_TEMPLATE.get(),
                  new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

  public static final RegistryObject<Item> CREATIVE_MOB_FARM =
      ITEMS.register(
          MobFarmType.CREATIVE_MOB_FARM.getId(),
          () ->
              new BlockItem(
                  ModBlocks.CREATIVE_MOB_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> ANIMAL_PLAINS_FARM =
      ITEMS.register(
          MobFarmType.ANIMAL_PLAINS_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.ANIMAL_PLAINS_FARM.getId(),
                  ModBlocks.ANIMAL_PLAINS_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> BEE_HIVE_FARM =
      ITEMS.register(
          MobFarmType.BEE_HIVE_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.BEE_HIVE_FARM.getId(),
                  ModBlocks.BEE_HIVE_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> DESERT_FARM =
      ITEMS.register(
          MobFarmType.DESERT_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.DESERT_FARM.getId(),
                  ModBlocks.DESERT_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> IRON_GOLEM_FARM =
      ITEMS.register(
          MobFarmType.IRON_GOLEM_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.IRON_GOLEM_FARM.getId(),
                  ModBlocks.IRON_GOLEM_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> JUNGLE_FARM =
      ITEMS.register(
          MobFarmType.JUNGLE_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.JUNGLE_FARM.getId(),
                  ModBlocks.JUNGLE_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> LUCKY_DROP_FARM =
      ITEMS.register(
          MobFarmType.LUCKY_DROP_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.LUCKY_DROP_FARM.getId(),
                  ModBlocks.LUCKY_DROP_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> MONSTER_PLAINS_CAVE_FARM =
      ITEMS.register(
          MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId(),
                  ModBlocks.MONSTER_PLAINS_CAVE_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> NETHER_FORTRESS_FARM =
      ITEMS.register(
          MobFarmType.NETHER_FORTRESS_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.NETHER_FORTRESS_FARM.getId(),
                  ModBlocks.NETHER_FORTRESS_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> OCEAN_FARM =
      ITEMS.register(
          MobFarmType.OCEAN_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.OCEAN_FARM.getId(),
                  ModBlocks.OCEAN_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> SWAMP_FARM =
      ITEMS.register(
          MobFarmType.SWAMP_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.SWAMP_FARM.getId(),
                  ModBlocks.SWAMP_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  protected ModBlockItems() {}
}
