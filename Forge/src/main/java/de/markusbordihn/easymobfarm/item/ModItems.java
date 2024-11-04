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
import de.markusbordihn.easymobfarm.block.CreativeMobFarmBlock;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.item.mobcapturecard.BlankMobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.CreativeBlankMobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.CreativeMobCatcherItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.EnduringCaptureNetItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.IronboundContainmentCageItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.MysticBindingCrystalItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LootEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LuckEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoFlowersFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoMeatFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.BigSlotUpgradeItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.SmallSlotUpgradeItem;
import de.markusbordihn.easymobfarm.tabs.ModTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  public static final RegistryObject<Item> BLANK_MOB_CAPTURE_CARD =
      ITEMS.register(
          BlankMobCaptureCardItem.ID,
          () ->
              new BlankMobCaptureCardItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_CAPTURE_CARDS)));

  public static final RegistryObject<Item> CREATIVE_MOB_CAPTURE_CARD =
      ITEMS.register(
          CreativeBlankMobCaptureCardItem.ID,
          () ->
              new CreativeBlankMobCaptureCardItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_CAPTURE_CARDS).stacksTo(1)));

  public static final RegistryObject<Item> MOB_CAPTURE_CARD =
      ITEMS.register(MobCaptureCardItem.ID, MobCaptureCardItemWrapper::new);

  public static final RegistryObject<Item> EXPERIENCE_ENHANCEMENT =
      ITEMS.register(
          ExperienceEnhancementItem.ID,
          () ->
              new ExperienceEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> LOOT_ENHANCEMENT =
      ITEMS.register(
          LootEnhancementItem.ID,
          () -> new LootEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> LUCK_ENHANCEMENT =
      ITEMS.register(
          LuckEnhancementItem.ID,
          () -> new LuckEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SHEEP_ENHANCEMENT =
      ITEMS.register(
          SheepEnhancementItem.ID,
          () -> new SheepEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SPEED_ENHANCEMENT =
      ITEMS.register(
          SpeedEnhancementItem.ID,
          () -> new SpeedEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SWORD_ENHANCEMENT =
      ITEMS.register(
          SwordEnhancementItem.ID,
          () -> new SwordEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> NO_FLOWERS_FILTER =
      ITEMS.register(
          NoFlowersFilterItem.ID,
          () -> new NoFlowersFilterItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> NO_MEAT_FILTER =
      ITEMS.register(
          NoMeatFilterItem.ID,
          () -> new NoMeatFilterItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> BIG_SLOT_UPGRADE =
      ITEMS.register(
          BigSlotUpgradeItem.ID,
          () -> new BigSlotUpgradeItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SMALL_SLOT_UPGRADE =
      ITEMS.register(
          SmallSlotUpgradeItem.ID,
          () -> new SmallSlotUpgradeItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> CREATIVE_MOB_CATCHER =
      ITEMS.register(
          CreativeMobCatcherItem.ID,
          () ->
              new CreativeMobCatcherItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER).stacksTo(1)));
  public static final RegistryObject<Item> ENDURING_CAPTURE_NET =
      ITEMS.register(
          EnduringCaptureNetItem.ID,
          () -> new EnduringCaptureNetItem(new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER)));
  public static final RegistryObject<Item> IRONBOUND_CONTAINMENT_CAGE =
      ITEMS.register(
          IronboundContainmentCageItem.ID,
          () ->
              new IronboundContainmentCageItem(new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER)));
  public static final RegistryObject<Item> MYSTIC_BINDING_CRYSTAL =
      ITEMS.register(
          MysticBindingCrystalItem.ID,
          () -> new MysticBindingCrystalItem(new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER)));

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
          CreativeMobFarmBlock.ID,
          () ->
              new BlockItem(
                  ModBlocks.CREATIVE_MOB_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> ANIMAL_PLAINS_FARM =
      ITEMS.register(
          MobFarmBlock.ID_ANIMAL_PLAINS_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_ANIMAL_PLAINS_FARM,
                  ModBlocks.ANIMAL_PLAINS_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> BEE_HIVE_FARM =
      ITEMS.register(
          MobFarmBlock.ID_BEE_HIVE_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_BEE_HIVE_FARM,
                  ModBlocks.BEE_HIVE_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> DESERT_FARM =
      ITEMS.register(
          MobFarmBlock.ID_DESERT_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_DESERT_FARM,
                  ModBlocks.DESERT_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> JUNGLE_FARM =
      ITEMS.register(
          MobFarmBlock.ID_JUNGLE_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_JUNGLE_FARM,
                  ModBlocks.JUNGLE_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> MONSTER_PLAINS_CAVE_FARM =
      ITEMS.register(
          MobFarmBlock.ID_MONSTER_PLAINS_CAVE_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_MONSTER_PLAINS_CAVE_FARM,
                  ModBlocks.MONSTER_PLAINS_CAVE_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> NETHER_FORTRESS_FARM =
      ITEMS.register(
          MobFarmBlock.ID_NETHER_FORTRESS_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_NETHER_FORTRESS_FARM,
                  ModBlocks.NETHER_FORTRESS_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> OCEAN_FARM =
      ITEMS.register(
          MobFarmBlock.ID_OCEAN_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_OCEAN_FARM,
                  ModBlocks.OCEAN_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  public static final RegistryObject<Item> SWAMP_FARM =
      ITEMS.register(
          MobFarmBlock.ID_SWAMP_FARM,
          () ->
              new MobFarmBlockItem(
                  MobFarmBlock.ID_SWAMP_FARM,
                  ModBlocks.SWAMP_FARM.get(),
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARMS)));

  protected ModItems() {}
}
