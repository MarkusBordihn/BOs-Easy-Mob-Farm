/*
 * Copyright 2026 Markus Bordihn
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

package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.Constants;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Registers the game test functions of this mod.
 *
 * <p>Forge does not scan game test annotations, so every function is registered here and paired
 * with a {@code data/<mod id>/test_instance} entry.
 */
public final class ModGameTests {

  private static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
      DeferredRegister.create(Registries.TEST_FUNCTION, Constants.MOD_ID);

  private static final LootManagerTest LOOT_MANAGER_TESTS = new LootManagerTest();
  private static final LootTablesTest LOOT_TABLES_TESTS = new LootTablesTest();
  private static final MobCaptureCardDropTest MOB_CAPTURE_CARD_DROP_TESTS =
      new MobCaptureCardDropTest();
  private static final MobCatcherTest MOB_CATCHER_TESTS = new MobCatcherTest();
  private static final MobFarmBlockEntityTest MOB_FARM_BLOCK_ENTITY_TESTS =
      new MobFarmBlockEntityTest();
  private static final MobFarmBlockItemsTest MOB_FARM_BLOCK_ITEMS_TESTS =
      new MobFarmBlockItemsTest();
  private static final MobVariantDataTest MOB_VARIANT_DATA_TESTS = new MobVariantDataTest();
  private static final ModBlockItemsTest MOD_BLOCK_ITEMS_TESTS = new ModBlockItemsTest();
  private static final ModBlocksTest MOD_BLOCKS_TESTS = new ModBlocksTest();
  private static final ModItemsTest MOD_ITEMS_TESTS = new ModItemsTest();
  private static final RedstoneSignalTest REDSTONE_SIGNAL_TESTS = new RedstoneSignalTest();
  private static final SmokeTest SMOKE_TESTS = new SmokeTest();

  static {
    register(
        "loot_manager_honey_extractor_converts_bonus_honeycomb",
        LOOT_MANAGER_TESTS::testHoneyExtractorConvertsBonusHoneycomb);
    register(
        "loot_manager_bonus_honeycomb_passes_through_without_extractor",
        LOOT_MANAGER_TESTS::testBonusHoneycombPassesThroughWithoutExtractor);
    register(
        "loot_manager_honey_extractor_does_not_convert_for_non_bee",
        LOOT_MANAGER_TESTS::testHoneyExtractorDoesNotConvertForNonBee);
    register(
        "loot_manager_wither_special_drops_are_not_multiplied",
        LOOT_MANAGER_TESTS::testWitherSpecialDropsAreNotMultiplied);
    register(
        "loot_manager_malformed_capture_data_does_not_throw",
        LOOT_MANAGER_TESTS::testMalformedCaptureDataDoesNotThrow);
    register(
        "loot_tables_tier0mob_farm_template_drop", LOOT_TABLES_TESTS::testTier0MobFarmTemplateDrop);
    register(
        "loot_tables_tier1mob_farm_template_drop", LOOT_TABLES_TESTS::testTier1MobFarmTemplateDrop);
    register(
        "loot_tables_tier2mob_farm_template_drop", LOOT_TABLES_TESTS::testTier2MobFarmTemplateDrop);
    register(
        "loot_tables_tier3mob_farm_template_drop", LOOT_TABLES_TESTS::testTier3MobFarmTemplateDrop);
    register("loot_tables_creative_mob_farm_drop", LOOT_TABLES_TESTS::testCreativeMobFarmDrop);
    register("loot_tables_animal_plains_farm_drop", LOOT_TABLES_TESTS::testAnimalPlainsFarmDrop);
    register("loot_tables_bee_hive_farm_drop", LOOT_TABLES_TESTS::testBeeHiveFarmDrop);
    register("loot_tables_desert_farm_drop", LOOT_TABLES_TESTS::testDesertFarmDrop);
    register("loot_tables_iron_golem_farm_drop", LOOT_TABLES_TESTS::testIronGolemFarmDrop);
    register("loot_tables_jungle_farm_drop", LOOT_TABLES_TESTS::testJungleFarmDrop);
    register("loot_tables_lucky_drop_farm_drop", LOOT_TABLES_TESTS::testLuckyDropFarmDrop);
    register(
        "loot_tables_monster_plains_cave_farm_drop",
        LOOT_TABLES_TESTS::testMonsterPlainsCaveFarmDrop);
    register(
        "loot_tables_nether_fortress_farm_drop", LOOT_TABLES_TESTS::testNetherFortressFarmDrop);
    register("loot_tables_ocean_farm_drop", LOOT_TABLES_TESTS::testOceanFarmDrop);
    register("loot_tables_swamp_farm_drop", LOOT_TABLES_TESTS::testSwampFarmDrop);
    register("loot_tables_end_farm_drop", LOOT_TABLES_TESTS::testEndFarmDrop);
    register("loot_tables_nether_wastes_farm_drop", LOOT_TABLES_TESTS::testNetherWastesFarmDrop);
    register(
        "mob_capture_card_drop_no_card_drop_with_zero_chance",
        MOB_CAPTURE_CARD_DROP_TESTS::testNoCardDropWithZeroChance);
    register(
        "mob_capture_card_drop_single_card_drop_with_full_chance",
        MOB_CAPTURE_CARD_DROP_TESTS::testSingleCardDropWithFullChance);
    register(
        "mob_catcher_rejected_capture_keeps_durability",
        MOB_CATCHER_TESTS::testRejectedCaptureKeepsDurability);
    register(
        "mob_catcher_successful_capture_uses_durability",
        MOB_CATCHER_TESTS::testSuccessfulCaptureUsesDurability);
    register(
        "mob_farm_block_entity_unknown_farm_type_tag_keeps_block_farm_type",
        MOB_FARM_BLOCK_ENTITY_TESTS::testUnknownFarmTypeTagKeepsBlockFarmType);
    register(
        "mob_farm_block_entity_non_living_capture_card_has_no_experience",
        MOB_FARM_BLOCK_ENTITY_TESTS::testNonLivingCaptureCardHasNoExperience);
    register(
        "mob_farm_block_entity_non_living_capture_card_leaves_no_entity",
        MOB_FARM_BLOCK_ENTITY_TESTS::testNonLivingCaptureCardLeavesNoEntity);
    register(
        "mob_farm_block_entity_empty_capture_item_keeps_farm_idle",
        MOB_FARM_BLOCK_ENTITY_TESTS::testEmptyCaptureItemKeepsFarmIdle);
    register(
        "mob_farm_block_entity_unknown_entity_capture_card_sets_error_status",
        MOB_FARM_BLOCK_ENTITY_TESTS::testUnknownEntityCaptureCardSetsErrorStatus);
    register(
        "mob_farm_block_items_animal_plains_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarm);
    register(
        "mob_farm_block_items_animal_plains_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier0);
    register(
        "mob_farm_block_items_animal_plains_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier1);
    register(
        "mob_farm_block_items_animal_plains_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier2);
    register(
        "mob_farm_block_items_animal_plains_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier3);
    register("mob_farm_block_items_bee_hive_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarm);
    register(
        "mob_farm_block_items_bee_hive_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier0);
    register(
        "mob_farm_block_items_bee_hive_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier1);
    register(
        "mob_farm_block_items_bee_hive_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier2);
    register(
        "mob_farm_block_items_bee_hive_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier3);
    register("mob_farm_block_items_desert_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarm);
    register(
        "mob_farm_block_items_desert_farm_tier0", MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier0);
    register(
        "mob_farm_block_items_desert_farm_tier1", MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier1);
    register(
        "mob_farm_block_items_desert_farm_tier2", MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier2);
    register(
        "mob_farm_block_items_desert_farm_tier3", MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier3);
    register("mob_farm_block_items_iron_golem_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarm);
    register(
        "mob_farm_block_items_iron_golem_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier0);
    register(
        "mob_farm_block_items_iron_golem_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier1);
    register(
        "mob_farm_block_items_iron_golem_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier2);
    register(
        "mob_farm_block_items_iron_golem_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier3);
    register("mob_farm_block_items_jungle_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarm);
    register(
        "mob_farm_block_items_jungle_farm_tier0", MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier0);
    register(
        "mob_farm_block_items_jungle_farm_tier1", MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier1);
    register(
        "mob_farm_block_items_jungle_farm_tier2", MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier2);
    register(
        "mob_farm_block_items_jungle_farm_tier3", MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier3);
    register("mob_farm_block_items_lucky_drop_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarm);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier0);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier1);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier2);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier3);
    register(
        "mob_farm_block_items_monster_plains_cave_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarm);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier0);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier1);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier2);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier3);
    register(
        "mob_farm_block_items_nether_fortress_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarm);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier0);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier1);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier2);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier3);
    register("mob_farm_block_items_ocean_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarm);
    register(
        "mob_farm_block_items_ocean_farm_tier0", MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier0);
    register(
        "mob_farm_block_items_ocean_farm_tier1", MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier1);
    register(
        "mob_farm_block_items_ocean_farm_tier2", MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier2);
    register(
        "mob_farm_block_items_ocean_farm_tier3", MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier3);
    register("mob_farm_block_items_swamp_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarm);
    register(
        "mob_farm_block_items_swamp_farm_tier0", MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier0);
    register(
        "mob_farm_block_items_swamp_farm_tier1", MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier1);
    register(
        "mob_farm_block_items_swamp_farm_tier2", MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier2);
    register(
        "mob_farm_block_items_swamp_farm_tier3", MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier3);
    register("mob_farm_block_items_end_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarm);
    register("mob_farm_block_items_end_farm_tier0", MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier0);
    register("mob_farm_block_items_end_farm_tier1", MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier1);
    register("mob_farm_block_items_end_farm_tier2", MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier2);
    register("mob_farm_block_items_end_farm_tier3", MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier3);
    register(
        "mob_farm_block_items_nether_wastes_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarm);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier0);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier1);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier2);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier3);
    register(
        "mob_variant_data_pillager_variant_detection",
        MOB_VARIANT_DATA_TESTS::testPillagerVariantDetection);
    register(
        "mod_block_items_tier0mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier0MobFarmTemplateItem);
    register(
        "mod_block_items_tier1mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier1MobFarmTemplateItem);
    register(
        "mod_block_items_tier2mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier2MobFarmTemplateItem);
    register(
        "mod_block_items_tier3mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier3MobFarmTemplateItem);
    register(
        "mod_block_items_creative_mob_farm_item", MOD_BLOCK_ITEMS_TESTS::testCreativeMobFarmItem);
    register(
        "mod_block_items_animal_plains_farm_item", MOD_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmItem);
    register("mod_block_items_bee_hive_farm_item", MOD_BLOCK_ITEMS_TESTS::testBeeHiveFarmItem);
    register("mod_block_items_desert_farm_item", MOD_BLOCK_ITEMS_TESTS::testDesertFarmItem);
    register("mod_block_items_iron_golem_farm_item", MOD_BLOCK_ITEMS_TESTS::testIronGolemFarmItem);
    register("mod_block_items_jungle_farm_item", MOD_BLOCK_ITEMS_TESTS::testJungleFarmItem);
    register("mod_block_items_lucky_drop_farm_item", MOD_BLOCK_ITEMS_TESTS::testLuckyDropFarmItem);
    register(
        "mod_block_items_monster_plains_cave_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmItem);
    register(
        "mod_block_items_nether_fortress_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testNetherFortressFarmItem);
    register("mod_block_items_ocean_farm_item", MOD_BLOCK_ITEMS_TESTS::testOceanFarmItem);
    register("mod_block_items_swamp_farm_item", MOD_BLOCK_ITEMS_TESTS::testSwampFarmItem);
    register("mod_block_items_end_farm_item", MOD_BLOCK_ITEMS_TESTS::testEndFarmItem);
    register(
        "mod_block_items_nether_wastes_farm_item", MOD_BLOCK_ITEMS_TESTS::testNetherWastesFarmItem);
    register("mod_blocks_tier0mob_farm_template", MOD_BLOCKS_TESTS::testTier0MobFarmTemplate);
    register("mod_blocks_tier1mob_farm_template", MOD_BLOCKS_TESTS::testTier1MobFarmTemplate);
    register("mod_blocks_tier2mob_farm_template", MOD_BLOCKS_TESTS::testTier2MobFarmTemplate);
    register("mod_blocks_tier3mob_farm_template", MOD_BLOCKS_TESTS::testTier3MobFarmTemplate);
    register("mod_blocks_creative_mob_farm", MOD_BLOCKS_TESTS::testCreativeMobFarm);
    register("mod_blocks_animal_plains_farm", MOD_BLOCKS_TESTS::testAnimalPlainsFarm);
    register("mod_blocks_bee_hive_farm", MOD_BLOCKS_TESTS::testBeeHiveFarm);
    register("mod_blocks_desert_farm", MOD_BLOCKS_TESTS::testDesertFarm);
    register("mod_blocks_iron_golem_farm", MOD_BLOCKS_TESTS::testIronGolemFarm);
    register("mod_blocks_jungle_farm", MOD_BLOCKS_TESTS::testJungleFarm);
    register("mod_blocks_lucky_drop_farm", MOD_BLOCKS_TESTS::testLuckyDropFarm);
    register("mod_blocks_monster_plains_cave_farm", MOD_BLOCKS_TESTS::testMonsterPlainsCaveFarm);
    register("mod_blocks_nether_fortress_farm", MOD_BLOCKS_TESTS::testNetherFortressFarm);
    register("mod_blocks_ocean_farm", MOD_BLOCKS_TESTS::testOceanFarm);
    register("mod_blocks_swamp_farm", MOD_BLOCKS_TESTS::testSwampFarm);
    register("mod_blocks_end_farm", MOD_BLOCKS_TESTS::testEndFarm);
    register("mod_blocks_nether_wastes_farm", MOD_BLOCKS_TESTS::testNetherWastesFarm);
    register("mod_items_blank_mob_capture_card_item", MOD_ITEMS_TESTS::testBlankMobCaptureCardItem);
    register(
        "mod_items_creative_mob_capture_card_item",
        MOD_ITEMS_TESTS::testCreativeMobCaptureCardItem);
    register("mod_items_mob_capture_card_item", MOD_ITEMS_TESTS::testMobCaptureCardItem);
    register(
        "mod_items_creative_speed_enhancement_item",
        MOD_ITEMS_TESTS::testCreativeSpeedEnhancementItem);
    register(
        "mod_items_egg_collector_enhancement_item",
        MOD_ITEMS_TESTS::testEggCollectorEnhancementItem);
    register(
        "mod_items_experience_enhancement_item", MOD_ITEMS_TESTS::testExperienceEnhancementItem);
    register(
        "mod_items_frog_catalyst_enhancement_item",
        MOD_ITEMS_TESTS::testFrogCatalystEnhancementItem);
    register(
        "mod_items_honey_extractor_enhancement_item",
        MOD_ITEMS_TESTS::testHoneyExtractorEnhancementItem);
    register(
        "mod_items_honey_harvester_frame_enhancement_item",
        MOD_ITEMS_TESTS::testHoneyHarvesterFrameEnhancementItem);
    register("mod_items_loot_enhancement_item", MOD_ITEMS_TESTS::testLootEnhancementItem);
    register("mod_items_luck_enhancement_item", MOD_ITEMS_TESTS::testLuckEnhancementItem);
    register(
        "mod_items_milk_extractor_enhancement_item",
        MOD_ITEMS_TESTS::testMilkExtractorEnhancementItem);
    register(
        "mod_items_pollen_trap_enhancement_item", MOD_ITEMS_TESTS::testPollenTrapEnhancementItem);
    register("mod_items_sheep_enhancement_item", MOD_ITEMS_TESTS::testSheepEnhancementItem);
    register("mod_items_speed_enhancement_item", MOD_ITEMS_TESTS::testSpeedEnhancementItem);
    register("mod_items_sword_enhancement_item", MOD_ITEMS_TESTS::testSwordEnhancementItem);
    register("mod_items_knife_enhancement_item", MOD_ITEMS_TESTS::testKnifeEnhancementItem);
    register("mod_items_no_flowers_filter_item", MOD_ITEMS_TESTS::testNoFlowersFilterItem);
    register("mod_items_no_meat_filter_item", MOD_ITEMS_TESTS::testNoMeatFilterItem);
    register("mod_items_big_slot_upgrade_item", MOD_ITEMS_TESTS::testBigSlotUpgradeItem);
    register("mod_items_small_slot_upgrade_item", MOD_ITEMS_TESTS::testSmallSlotUpgradeItem);
    register("mod_items_creative_mob_catcher_item", MOD_ITEMS_TESTS::testCreativeMobCatcherItem);
    register("mod_items_enduring_capture_net_item", MOD_ITEMS_TESTS::testEnduringCaptureNetItem);
    register(
        "mod_items_ironbound_containment_cage_item",
        MOD_ITEMS_TESTS::testIronboundContainmentCageItem);
    register(
        "mod_items_mystic_binding_crystal_item", MOD_ITEMS_TESTS::testMysticBindingCrystalItem);
    register("mod_items_void_binding_chain_item", MOD_ITEMS_TESTS::testVoidBindingChainItem);
    register("mod_items_milk_bottle_item", MOD_ITEMS_TESTS::testMilkBottleItem);
    register("mod_items_card_binder_item", MOD_ITEMS_TESTS::testCardBinderItem);
    register(
        "redstone_signal_redstone_signal_with_lever",
        REDSTONE_SIGNAL_TESTS::testRedstoneSignalWithLever);
    register(
        "redstone_signal_redstone_signal_with_redstone_block",
        REDSTONE_SIGNAL_TESTS::testRedstoneSignalWithRedstoneBlock);
    register(
        "redstone_signal_redstone_mode_disable_on_signal",
        REDSTONE_SIGNAL_TESTS::testRedstoneModeDisableOnSignal);
    register(
        "redstone_signal_redstone_mode_enable_on_signal",
        REDSTONE_SIGNAL_TESTS::testRedstoneModeEnableOnSignal);
    register(
        "redstone_signal_redstone_mode_enable_on_signal_without_signal",
        REDSTONE_SIGNAL_TESTS::testRedstoneModeEnableOnSignalWithoutSignal);
    register(
        "redstone_signal_redstone_mode_ignore", REDSTONE_SIGNAL_TESTS::testRedstoneModeIgnore);
    register("smoke_mod_registered", SMOKE_TESTS::testModRegistered);
  }

  private ModGameTests() {}

  public static void register(BusGroup modBusGroup) {
    if (FMLLoader.isProduction()) {
      return;
    }

    TEST_FUNCTIONS.register(modBusGroup);
  }

  private static void register(String name, Consumer<GameTestHelper> testFunction) {
    TEST_FUNCTIONS.register(name, () -> testFunction);
  }
}
