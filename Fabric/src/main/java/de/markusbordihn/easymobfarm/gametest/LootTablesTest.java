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

package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings("unused")
public class LootTablesTest {

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testTier0MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.TIER_0_MOB_FARM_TEMPLATE, ModBlockItems.TIER_0_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testTier1MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.TIER_1_MOB_FARM_TEMPLATE, ModBlockItems.TIER_1_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testTier2MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.TIER_2_MOB_FARM_TEMPLATE, ModBlockItems.TIER_2_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testTier3MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.TIER_3_MOB_FARM_TEMPLATE, ModBlockItems.TIER_3_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testCreativeMobFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.CREATIVE_MOB_FARM, ModBlockItems.CREATIVE_MOB_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testAnimalPlainsFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.ANIMAL_PLAINS_FARM, ModBlockItems.ANIMAL_PLAINS_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testBeeHiveFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.BEE_HIVE_FARM, ModBlockItems.BEE_HIVE_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testDesertFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(helper, ModBlocks.DESERT_FARM, ModBlockItems.DESERT_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testIronGolemFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.IRON_GOLEM_FARM, ModBlockItems.IRON_GOLEM_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testJungleFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(helper, ModBlocks.JUNGLE_FARM, ModBlockItems.JUNGLE_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testLuckyDropFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.LUCKY_DROP_FARM, ModBlockItems.LUCKY_DROP_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testMonsterPlainsCaveFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.MONSTER_PLAINS_CAVE_FARM, ModBlockItems.MONSTER_PLAINS_CAVE_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testNetherFortressFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.NETHER_FORTRESS_FARM, ModBlockItems.NETHER_FORTRESS_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testOceanFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(helper, ModBlocks.OCEAN_FARM, ModBlockItems.OCEAN_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testSwampFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(helper, ModBlocks.SWAMP_FARM, ModBlockItems.SWAMP_FARM);
    helper.succeed();
  }
}
