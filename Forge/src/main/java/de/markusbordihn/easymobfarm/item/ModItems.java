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
import de.markusbordihn.easymobfarm.item.mobcapturecard.BlankMobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.CreativeBlankMobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.CreativeMobCatcherItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.EnduringCaptureNetItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.IronboundContainmentCageItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.MysticBindingCrystalItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.VoidBindingChainItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.CreativeSpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.EggCollectorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyHarvesterFrameEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LootEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LuckEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.PollenTrapEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoFlowersFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoMeatFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.BigSlotUpgradeItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.SmallSlotUpgradeItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  public static final RegistryObject<Item> BLANK_MOB_CAPTURE_CARD =
      ITEMS.register(BlankMobCaptureCardItem.ID, () -> Items.BLANK_MOB_CAPTURE_CARD);

  public static final RegistryObject<Item> CREATIVE_MOB_CAPTURE_CARD =
      ITEMS.register(CreativeBlankMobCaptureCardItem.ID, () -> Items.CREATIVE_MOB_CAPTURE_CARD);

  public static final RegistryObject<Item> MOB_CAPTURE_CARD =
      ITEMS.register(MobCaptureCardItem.ID, () -> Items.MOB_CAPTURE_CARD);

  public static final RegistryObject<Item> CREATIVE_SPEED_ENHANCEMENT =
      ITEMS.register(CreativeSpeedEnhancementItem.ID, () -> Items.CREATIVE_SPEED_ENHANCEMENT);

  public static final RegistryObject<Item> EGG_COLLECTOR_ENHANCEMENT =
      ITEMS.register(EggCollectorEnhancementItem.ID, () -> Items.EGG_COLLECTOR_ENHANCEMENT);

  public static final RegistryObject<Item> EXPERIENCE_ENHANCEMENT =
      ITEMS.register(ExperienceEnhancementItem.ID, () -> Items.EXPERIENCE_ENHANCEMENT);

  public static final RegistryObject<Item> HONEY_EXTRACTOR_ENHANCEMENT =
      ITEMS.register(HoneyExtractorEnhancementItem.ID, () -> Items.HONEY_EXTRACTOR_ENHANCEMENT);

  public static final RegistryObject<Item> HONEY_HARVESTER_FRAME_ENHANCEMENT =
      ITEMS.register(
          HoneyHarvesterFrameEnhancementItem.ID, () -> Items.HONEY_HARVESTER_FRAME_ENHANCEMENT);

  public static final RegistryObject<Item> LOOT_ENHANCEMENT =
      ITEMS.register(LootEnhancementItem.ID, () -> Items.LOOT_ENHANCEMENT);

  public static final RegistryObject<Item> LUCK_ENHANCEMENT =
      ITEMS.register(LuckEnhancementItem.ID, () -> Items.LUCK_ENHANCEMENT);

  public static final RegistryObject<Item> POLLEN_TRAP_ENHANCEMENT =
      ITEMS.register(PollenTrapEnhancementItem.ID, () -> Items.POLLEN_TRAP_ENHANCEMENT);

  public static final RegistryObject<Item> SHEEP_ENHANCEMENT =
      ITEMS.register(SheepEnhancementItem.ID, () -> Items.SHEEP_ENHANCEMENT);

  public static final RegistryObject<Item> SPEED_ENHANCEMENT =
      ITEMS.register(SpeedEnhancementItem.ID, () -> Items.SPEED_ENHANCEMENT);

  public static final RegistryObject<Item> SWORD_ENHANCEMENT =
      ITEMS.register(SwordEnhancementItem.ID, () -> Items.SWORD_ENHANCEMENT);

  public static final RegistryObject<Item> NO_FLOWERS_FILTER =
      ITEMS.register(NoFlowersFilterItem.ID, () -> Items.NO_FLOWERS_FILTER);

  public static final RegistryObject<Item> NO_MEAT_FILTER =
      ITEMS.register(NoMeatFilterItem.ID, () -> Items.NO_MEAT_FILTER);

  public static final RegistryObject<Item> BIG_SLOT_UPGRADE =
      ITEMS.register(BigSlotUpgradeItem.ID, () -> Items.BIG_SLOT_UPGRADE);

  public static final RegistryObject<Item> SMALL_SLOT_UPGRADE =
      ITEMS.register(SmallSlotUpgradeItem.ID, () -> Items.SMALL_SLOT_UPGRADE);

  public static final RegistryObject<Item> CREATIVE_MOB_CATCHER =
      ITEMS.register(CreativeMobCatcherItem.ID, () -> Items.CREATIVE_MOB_CATCHER);

  public static final RegistryObject<Item> ENDURING_CAPTURE_NET =
      ITEMS.register(EnduringCaptureNetItem.ID, () -> Items.ENDURING_CAPTURE_NET);

  public static final RegistryObject<Item> IRONBOUND_CONTAINMENT_CAGE =
      ITEMS.register(IronboundContainmentCageItem.ID, () -> Items.IRONBOUND_CONTAINMENT_CAGE);

  public static final RegistryObject<Item> MYSTIC_BINDING_CRYSTAL =
      ITEMS.register(MysticBindingCrystalItem.ID, () -> Items.MYSTIC_BINDING_CRYSTAL);

  public static final RegistryObject<Item> VOID_BINDING_CHAIN =
      ITEMS.register(VoidBindingChainItem.ID, () -> Items.VOID_BINDING_CHAIN);

  public static final RegistryObject<Item> TIER_0_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_0,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_0, ModBlocks.TIER_0_MOB_FARM_TEMPLATE.get()));
  public static final RegistryObject<Item> TIER_1_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_1,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_1, ModBlocks.TIER_1_MOB_FARM_TEMPLATE.get()));
  public static final RegistryObject<Item> TIER_2_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_2,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_2, ModBlocks.TIER_2_MOB_FARM_TEMPLATE.get()));
  public static final RegistryObject<Item> TIER_3_MOB_FARM_TEMPLATE =
      ITEMS.register(
          MobFarmTemplateItem.ID_TIER_3,
          () ->
              new MobFarmTemplateItem(
                  MobFarmTemplateItem.ID_TIER_3, ModBlocks.TIER_3_MOB_FARM_TEMPLATE.get()));

  public static final RegistryObject<Item> CREATIVE_MOB_FARM =
      ITEMS.register(
          MobFarmType.CREATIVE_MOB_FARM.getId(),
          () ->
              new BlockItem(
                  ModBlocks.CREATIVE_MOB_FARM.get(),
                  new Item.Properties()
                      .useBlockDescriptionPrefix()
                      .setId(
                          ResourceKey.create(
                              Registries.ITEM,
                              ResourceLocation.fromNamespaceAndPath(
                                  Constants.MOD_ID, MobFarmType.CREATIVE_MOB_FARM.getId())))));

  public static final RegistryObject<Item> ANIMAL_PLAINS_FARM =
      ITEMS.register(
          MobFarmType.ANIMAL_PLAINS_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.ANIMAL_PLAINS_FARM.getId(), ModBlocks.ANIMAL_PLAINS_FARM.get()));

  public static final RegistryObject<Item> BEE_HIVE_FARM =
      ITEMS.register(
          MobFarmType.BEE_HIVE_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.BEE_HIVE_FARM.getId(), ModBlocks.BEE_HIVE_FARM.get()));

  public static final RegistryObject<Item> DESERT_FARM =
      ITEMS.register(
          MobFarmType.DESERT_FARM.getId(),
          () -> new MobFarmBlockItem(MobFarmType.DESERT_FARM.getId(), ModBlocks.DESERT_FARM.get()));

  public static final RegistryObject<Item> IRON_GOLEM_FARM =
      ITEMS.register(
          MobFarmType.IRON_GOLEM_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.IRON_GOLEM_FARM.getId(), ModBlocks.IRON_GOLEM_FARM.get()));

  public static final RegistryObject<Item> JUNGLE_FARM =
      ITEMS.register(
          MobFarmType.JUNGLE_FARM.getId(),
          () -> new MobFarmBlockItem(MobFarmType.JUNGLE_FARM.getId(), ModBlocks.JUNGLE_FARM.get()));

  public static final RegistryObject<Item> LUCKY_DROP_FARM =
      ITEMS.register(
          MobFarmType.LUCKY_DROP_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.LUCKY_DROP_FARM.getId(), ModBlocks.LUCKY_DROP_FARM.get()));

  public static final RegistryObject<Item> MONSTER_PLAINS_CAVE_FARM =
      ITEMS.register(
          MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId(),
                  ModBlocks.MONSTER_PLAINS_CAVE_FARM.get()));

  public static final RegistryObject<Item> NETHER_FORTRESS_FARM =
      ITEMS.register(
          MobFarmType.NETHER_FORTRESS_FARM.getId(),
          () ->
              new MobFarmBlockItem(
                  MobFarmType.NETHER_FORTRESS_FARM.getId(), ModBlocks.NETHER_FORTRESS_FARM.get()));

  public static final RegistryObject<Item> OCEAN_FARM =
      ITEMS.register(
          MobFarmType.OCEAN_FARM.getId(),
          () -> new MobFarmBlockItem(MobFarmType.OCEAN_FARM.getId(), ModBlocks.OCEAN_FARM.get()));

  public static final RegistryObject<Item> SWAMP_FARM =
      ITEMS.register(
          MobFarmType.SWAMP_FARM.getId(),
          () -> new MobFarmBlockItem(MobFarmType.SWAMP_FARM.getId(), ModBlocks.SWAMP_FARM.get()));

  protected ModItems() {}
}
