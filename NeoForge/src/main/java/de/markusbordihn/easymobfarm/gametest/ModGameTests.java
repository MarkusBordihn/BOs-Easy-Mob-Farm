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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers the game test functions of this mod and turns each of them into a test instance.
 *
 * <p>NeoForge has no annotation based game test discovery, so every test method is registered
 * manually here and paired with the structure it should run in.
 */
@EventBusSubscriber
public final class ModGameTests {

  private static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
      DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, Constants.MOD_ID);
  private static final List<TestEntry> TEST_ENTRIES = new ArrayList<>();
  private static final int DEFAULT_MAX_TICKS = 100;
  private static final Identifier STRUCTURE_1X1X1 =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gametest.1x1x1");
  private static final Identifier STRUCTURE_3X3X3 =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gametest.3x3x3");

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
        LOOT_MANAGER_TESTS::testHoneyExtractorConvertsBonusHoneycomb,
        STRUCTURE_3X3X3);
    register(
        "loot_manager_bonus_honeycomb_passes_through_without_extractor",
        LOOT_MANAGER_TESTS::testBonusHoneycombPassesThroughWithoutExtractor,
        STRUCTURE_3X3X3);
    register(
        "loot_manager_honey_extractor_does_not_convert_for_non_bee",
        LOOT_MANAGER_TESTS::testHoneyExtractorDoesNotConvertForNonBee,
        STRUCTURE_3X3X3);
    register(
        "loot_manager_wither_special_drops_are_not_multiplied",
        LOOT_MANAGER_TESTS::testWitherSpecialDropsAreNotMultiplied,
        STRUCTURE_3X3X3);
    register(
        "loot_manager_malformed_capture_data_does_not_throw",
        LOOT_MANAGER_TESTS::testMalformedCaptureDataDoesNotThrow,
        STRUCTURE_3X3X3);
    register(
        "loot_tables_tier0mob_farm_template_drop",
        LOOT_TABLES_TESTS::testTier0MobFarmTemplateDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_tier1mob_farm_template_drop",
        LOOT_TABLES_TESTS::testTier1MobFarmTemplateDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_tier2mob_farm_template_drop",
        LOOT_TABLES_TESTS::testTier2MobFarmTemplateDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_tier3mob_farm_template_drop",
        LOOT_TABLES_TESTS::testTier3MobFarmTemplateDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_creative_mob_farm_drop",
        LOOT_TABLES_TESTS::testCreativeMobFarmDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_animal_plains_farm_drop",
        LOOT_TABLES_TESTS::testAnimalPlainsFarmDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_bee_hive_farm_drop", LOOT_TABLES_TESTS::testBeeHiveFarmDrop, STRUCTURE_1X1X1);
    register(
        "loot_tables_desert_farm_drop", LOOT_TABLES_TESTS::testDesertFarmDrop, STRUCTURE_1X1X1);
    register(
        "loot_tables_iron_golem_farm_drop",
        LOOT_TABLES_TESTS::testIronGolemFarmDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_jungle_farm_drop", LOOT_TABLES_TESTS::testJungleFarmDrop, STRUCTURE_1X1X1);
    register(
        "loot_tables_lucky_drop_farm_drop",
        LOOT_TABLES_TESTS::testLuckyDropFarmDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_monster_plains_cave_farm_drop",
        LOOT_TABLES_TESTS::testMonsterPlainsCaveFarmDrop,
        STRUCTURE_1X1X1);
    register(
        "loot_tables_nether_fortress_farm_drop",
        LOOT_TABLES_TESTS::testNetherFortressFarmDrop,
        STRUCTURE_1X1X1);
    register("loot_tables_ocean_farm_drop", LOOT_TABLES_TESTS::testOceanFarmDrop, STRUCTURE_1X1X1);
    register("loot_tables_swamp_farm_drop", LOOT_TABLES_TESTS::testSwampFarmDrop, STRUCTURE_1X1X1);
    register("loot_tables_end_farm_drop", LOOT_TABLES_TESTS::testEndFarmDrop, STRUCTURE_1X1X1);
    register(
        "loot_tables_nether_wastes_farm_drop",
        LOOT_TABLES_TESTS::testNetherWastesFarmDrop,
        STRUCTURE_1X1X1);
    register(
        "mob_capture_card_drop_no_card_drop_with_zero_chance",
        MOB_CAPTURE_CARD_DROP_TESTS::testNoCardDropWithZeroChance,
        STRUCTURE_3X3X3);
    register(
        "mob_capture_card_drop_single_card_drop_with_full_chance",
        MOB_CAPTURE_CARD_DROP_TESTS::testSingleCardDropWithFullChance,
        STRUCTURE_3X3X3);
    register(
        "mob_catcher_rejected_capture_keeps_durability",
        MOB_CATCHER_TESTS::testRejectedCaptureKeepsDurability,
        STRUCTURE_3X3X3);
    register(
        "mob_catcher_successful_capture_uses_durability",
        MOB_CATCHER_TESTS::testSuccessfulCaptureUsesDurability,
        STRUCTURE_3X3X3);
    register(
        "mob_farm_block_entity_unknown_farm_type_tag_keeps_block_farm_type",
        MOB_FARM_BLOCK_ENTITY_TESTS::testUnknownFarmTypeTagKeepsBlockFarmType,
        STRUCTURE_3X3X3);
    register(
        "mob_farm_block_entity_non_living_capture_card_has_no_experience",
        MOB_FARM_BLOCK_ENTITY_TESTS::testNonLivingCaptureCardHasNoExperience,
        STRUCTURE_3X3X3);
    register(
        "mob_farm_block_entity_non_living_capture_card_leaves_no_entity",
        MOB_FARM_BLOCK_ENTITY_TESTS::testNonLivingCaptureCardLeavesNoEntity,
        STRUCTURE_3X3X3);
    register(
        "mob_farm_block_entity_empty_capture_item_keeps_farm_idle",
        MOB_FARM_BLOCK_ENTITY_TESTS::testEmptyCaptureItemKeepsFarmIdle,
        STRUCTURE_3X3X3);
    register(
        "mob_farm_block_entity_unknown_entity_capture_card_sets_error_status",
        MOB_FARM_BLOCK_ENTITY_TESTS::testUnknownEntityCaptureCardSetsErrorStatus,
        STRUCTURE_3X3X3);
    register(
        "mob_farm_block_items_animal_plains_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_animal_plains_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_animal_plains_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_animal_plains_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_animal_plains_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_bee_hive_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_bee_hive_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_bee_hive_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_bee_hive_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_bee_hive_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testBeeHiveFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_desert_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_desert_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_desert_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_desert_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_desert_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testDesertFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_iron_golem_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_iron_golem_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_iron_golem_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_iron_golem_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_iron_golem_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testIronGolemFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_jungle_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_jungle_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_jungle_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_jungle_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_jungle_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testJungleFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_lucky_drop_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_lucky_drop_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testLuckyDropFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_monster_plains_cave_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_monster_plains_cave_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_fortress_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_fortress_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherFortressFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_ocean_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_ocean_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_ocean_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_ocean_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_ocean_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testOceanFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_swamp_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_swamp_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_swamp_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_swamp_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_swamp_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testSwampFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_end_farm", MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarm, STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_end_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_end_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_end_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_end_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testEndFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_wastes_farm",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarm,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier0",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier0,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier1",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier1,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier2",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier2,
        STRUCTURE_1X1X1);
    register(
        "mob_farm_block_items_nether_wastes_farm_tier3",
        MOB_FARM_BLOCK_ITEMS_TESTS::testNetherWastesFarmTier3,
        STRUCTURE_1X1X1);
    register(
        "mob_variant_data_pillager_variant_detection",
        MOB_VARIANT_DATA_TESTS::testPillagerVariantDetection,
        STRUCTURE_3X3X3);
    register(
        "mod_block_items_tier0mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier0MobFarmTemplateItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_tier1mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier1MobFarmTemplateItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_tier2mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier2MobFarmTemplateItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_tier3mob_farm_template_item",
        MOD_BLOCK_ITEMS_TESTS::testTier3MobFarmTemplateItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_creative_mob_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testCreativeMobFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_animal_plains_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testAnimalPlainsFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_bee_hive_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testBeeHiveFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_desert_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testDesertFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_iron_golem_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testIronGolemFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_jungle_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testJungleFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_lucky_drop_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testLuckyDropFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_monster_plains_cave_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testMonsterPlainsCaveFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_nether_fortress_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testNetherFortressFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_ocean_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testOceanFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_swamp_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testSwampFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_block_items_end_farm_item", MOD_BLOCK_ITEMS_TESTS::testEndFarmItem, STRUCTURE_1X1X1);
    register(
        "mod_block_items_nether_wastes_farm_item",
        MOD_BLOCK_ITEMS_TESTS::testNetherWastesFarmItem,
        STRUCTURE_1X1X1);
    register(
        "mod_blocks_tier0mob_farm_template",
        MOD_BLOCKS_TESTS::testTier0MobFarmTemplate,
        STRUCTURE_1X1X1);
    register(
        "mod_blocks_tier1mob_farm_template",
        MOD_BLOCKS_TESTS::testTier1MobFarmTemplate,
        STRUCTURE_1X1X1);
    register(
        "mod_blocks_tier2mob_farm_template",
        MOD_BLOCKS_TESTS::testTier2MobFarmTemplate,
        STRUCTURE_1X1X1);
    register(
        "mod_blocks_tier3mob_farm_template",
        MOD_BLOCKS_TESTS::testTier3MobFarmTemplate,
        STRUCTURE_1X1X1);
    register(
        "mod_blocks_creative_mob_farm", MOD_BLOCKS_TESTS::testCreativeMobFarm, STRUCTURE_1X1X1);
    register(
        "mod_blocks_animal_plains_farm", MOD_BLOCKS_TESTS::testAnimalPlainsFarm, STRUCTURE_1X1X1);
    register("mod_blocks_bee_hive_farm", MOD_BLOCKS_TESTS::testBeeHiveFarm, STRUCTURE_1X1X1);
    register("mod_blocks_desert_farm", MOD_BLOCKS_TESTS::testDesertFarm, STRUCTURE_1X1X1);
    register("mod_blocks_iron_golem_farm", MOD_BLOCKS_TESTS::testIronGolemFarm, STRUCTURE_1X1X1);
    register("mod_blocks_jungle_farm", MOD_BLOCKS_TESTS::testJungleFarm, STRUCTURE_1X1X1);
    register("mod_blocks_lucky_drop_farm", MOD_BLOCKS_TESTS::testLuckyDropFarm, STRUCTURE_1X1X1);
    register(
        "mod_blocks_monster_plains_cave_farm",
        MOD_BLOCKS_TESTS::testMonsterPlainsCaveFarm,
        STRUCTURE_1X1X1);
    register(
        "mod_blocks_nether_fortress_farm",
        MOD_BLOCKS_TESTS::testNetherFortressFarm,
        STRUCTURE_1X1X1);
    register("mod_blocks_ocean_farm", MOD_BLOCKS_TESTS::testOceanFarm, STRUCTURE_1X1X1);
    register("mod_blocks_swamp_farm", MOD_BLOCKS_TESTS::testSwampFarm, STRUCTURE_1X1X1);
    register("mod_blocks_end_farm", MOD_BLOCKS_TESTS::testEndFarm, STRUCTURE_1X1X1);
    register(
        "mod_blocks_nether_wastes_farm", MOD_BLOCKS_TESTS::testNetherWastesFarm, STRUCTURE_1X1X1);
    register(
        "mod_items_blank_mob_capture_card_item",
        MOD_ITEMS_TESTS::testBlankMobCaptureCardItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_creative_mob_capture_card_item",
        MOD_ITEMS_TESTS::testCreativeMobCaptureCardItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_mob_capture_card_item",
        MOD_ITEMS_TESTS::testMobCaptureCardItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_creative_speed_enhancement_item",
        MOD_ITEMS_TESTS::testCreativeSpeedEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_egg_collector_enhancement_item",
        MOD_ITEMS_TESTS::testEggCollectorEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_experience_enhancement_item",
        MOD_ITEMS_TESTS::testExperienceEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_frog_catalyst_enhancement_item",
        MOD_ITEMS_TESTS::testFrogCatalystEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_honey_extractor_enhancement_item",
        MOD_ITEMS_TESTS::testHoneyExtractorEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_honey_harvester_frame_enhancement_item",
        MOD_ITEMS_TESTS::testHoneyHarvesterFrameEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_loot_enhancement_item",
        MOD_ITEMS_TESTS::testLootEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_luck_enhancement_item",
        MOD_ITEMS_TESTS::testLuckEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_milk_extractor_enhancement_item",
        MOD_ITEMS_TESTS::testMilkExtractorEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_pollen_trap_enhancement_item",
        MOD_ITEMS_TESTS::testPollenTrapEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_sheep_enhancement_item",
        MOD_ITEMS_TESTS::testSheepEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_speed_enhancement_item",
        MOD_ITEMS_TESTS::testSpeedEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_sword_enhancement_item",
        MOD_ITEMS_TESTS::testSwordEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_knife_enhancement_item",
        MOD_ITEMS_TESTS::testKnifeEnhancementItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_no_flowers_filter_item",
        MOD_ITEMS_TESTS::testNoFlowersFilterItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_no_meat_filter_item", MOD_ITEMS_TESTS::testNoMeatFilterItem, STRUCTURE_1X1X1);
    register(
        "mod_items_big_slot_upgrade_item",
        MOD_ITEMS_TESTS::testBigSlotUpgradeItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_small_slot_upgrade_item",
        MOD_ITEMS_TESTS::testSmallSlotUpgradeItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_creative_mob_catcher_item",
        MOD_ITEMS_TESTS::testCreativeMobCatcherItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_enduring_capture_net_item",
        MOD_ITEMS_TESTS::testEnduringCaptureNetItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_ironbound_containment_cage_item",
        MOD_ITEMS_TESTS::testIronboundContainmentCageItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_mystic_binding_crystal_item",
        MOD_ITEMS_TESTS::testMysticBindingCrystalItem,
        STRUCTURE_1X1X1);
    register(
        "mod_items_void_binding_chain_item",
        MOD_ITEMS_TESTS::testVoidBindingChainItem,
        STRUCTURE_1X1X1);
    register("mod_items_milk_bottle_item", MOD_ITEMS_TESTS::testMilkBottleItem, STRUCTURE_1X1X1);
    register("mod_items_card_binder_item", MOD_ITEMS_TESTS::testCardBinderItem, STRUCTURE_1X1X1);
    register(
        "redstone_signal_redstone_signal_with_lever",
        REDSTONE_SIGNAL_TESTS::testRedstoneSignalWithLever,
        STRUCTURE_3X3X3);
    register(
        "redstone_signal_redstone_signal_with_redstone_block",
        REDSTONE_SIGNAL_TESTS::testRedstoneSignalWithRedstoneBlock,
        STRUCTURE_3X3X3);
    register("smoke_mod_registered", SMOKE_TESTS::testModRegistered, STRUCTURE_3X3X3);
    register(
        "item_capability_extraction",
        ItemCapabilityTest::testItemCapabilityExtraction,
        STRUCTURE_3X3X3);
  }

  private ModGameTests() {}

  public static void register(IEventBus modEventBus) {
    if (FMLEnvironment.isProduction()) {
      return;
    }

    TEST_FUNCTIONS.register(modEventBus);
  }

  private static void register(
      String name, Consumer<GameTestHelper> testFunction, Identifier structure) {
    TEST_ENTRIES.add(
        new TestEntry(
            TEST_FUNCTIONS.register(name, () -> testFunction), structure, DEFAULT_MAX_TICKS));
  }

  @SubscribeEvent
  public static void registerGameTests(RegisterGameTestsEvent event) {
    Holder<TestEnvironmentDefinition<?>> environment =
        event.registerEnvironment(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "default"));

    for (TestEntry testEntry : TEST_ENTRIES) {
      event.registerTest(
          testEntry.testFunction().getId(),
          new FunctionGameTestInstance(
              testEntry.testFunction().getKey(),
              new TestData<>(environment, testEntry.structure(), testEntry.maxTicks(), 0, true)));
    }
  }

  private record TestEntry(
      DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>> testFunction,
      Identifier structure,
      int maxTicks) {}
}
