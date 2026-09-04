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

import de.markusbordihn.easymobfarm.item.ModBlockItems;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings("unused")
public class ModBlockItemsTest {

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testTier0MobFarmTemplateItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.TIER_0_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testTier1MobFarmTemplateItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.TIER_1_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testTier2MobFarmTemplateItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.TIER_2_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testTier3MobFarmTemplateItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.TIER_3_MOB_FARM_TEMPLATE);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testCreativeMobFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.CREATIVE_MOB_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testAnimalPlainsFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.ANIMAL_PLAINS_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testBeeHiveFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.BEE_HIVE_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testDesertFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.DESERT_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testIronGolemFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.IRON_GOLEM_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testJungleFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.JUNGLE_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testLuckyDropFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.LUCKY_DROP_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testMonsterPlainsCaveFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.MONSTER_PLAINS_CAVE_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testNetherFortressFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.NETHER_FORTRESS_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testOceanFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.OCEAN_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testSwampFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.SWAMP_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testEndFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.END_FARM);
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1", maxTicks = 100)
  public void testNetherWastesFarmItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModBlockItems.NETHER_WASTES_FARM);
    helper.succeed();
  }
}
