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
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings("unused")
public class ModBlocksTest {

  public void testTier0MobFarmTemplate(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.TIER_0_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  public void testTier1MobFarmTemplate(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.TIER_1_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  public void testTier2MobFarmTemplate(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.TIER_2_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  public void testTier3MobFarmTemplate(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.TIER_3_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  public void testCreativeMobFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.CREATIVE_MOB_FARM.get());
    helper.succeed();
  }

  public void testAnimalPlainsFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testBeeHiveFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.BEE_HIVE_FARM.get());
    helper.succeed();
  }

  public void testDesertFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.DESERT_FARM.get());
    helper.succeed();
  }

  public void testIronGolemFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.IRON_GOLEM_FARM.get());
    helper.succeed();
  }

  public void testJungleFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.JUNGLE_FARM.get());
    helper.succeed();
  }

  public void testLuckyDropFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.LUCKY_DROP_FARM.get());
    helper.succeed();
  }

  public void testMonsterPlainsCaveFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.MONSTER_PLAINS_CAVE_FARM.get());
    helper.succeed();
  }

  public void testNetherFortressFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.NETHER_FORTRESS_FARM.get());
    helper.succeed();
  }

  public void testOceanFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.OCEAN_FARM.get());
    helper.succeed();
  }

  public void testSwampFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.SWAMP_FARM.get());
    helper.succeed();
  }

  public void testEndFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.END_FARM.get());
    helper.succeed();
  }

  public void testNetherWastesFarm(GameTestHelper helper) {
    ModBlocksTestHelper.testModBlock(helper, ModBlocks.NETHER_WASTES_FARM.get());
    helper.succeed();
  }
}
