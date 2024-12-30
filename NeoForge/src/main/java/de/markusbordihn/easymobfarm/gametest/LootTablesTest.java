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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@PrefixGameTestTemplate(value = false)
@GameTestHolder(Constants.MOD_ID)
public class LootTablesTest {

  @GameTest(template = "gametest.1x1x1")
  public void testTier0MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper,
        ModBlocks.TIER_0_MOB_FARM_TEMPLATE.get(),
        ModBlockItems.TIER_0_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testTier1MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper,
        ModBlocks.TIER_1_MOB_FARM_TEMPLATE.get(),
        ModBlockItems.TIER_1_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testTier2MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper,
        ModBlocks.TIER_2_MOB_FARM_TEMPLATE.get(),
        ModBlockItems.TIER_2_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testTier3MobFarmTemplateDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper,
        ModBlocks.TIER_3_MOB_FARM_TEMPLATE.get(),
        ModBlockItems.TIER_3_MOB_FARM_TEMPLATE.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testCreativeMobFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.CREATIVE_MOB_FARM.get(), ModBlockItems.CREATIVE_MOB_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testAnimalPlainsFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get(), ModBlockItems.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testBeeHiveFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.BEE_HIVE_FARM.get(), ModBlockItems.BEE_HIVE_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testDesertFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.DESERT_FARM.get(), ModBlockItems.DESERT_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testIronGolemFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.IRON_GOLEM_FARM.get(), ModBlockItems.IRON_GOLEM_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testJungleFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.JUNGLE_FARM.get(), ModBlockItems.JUNGLE_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testLuckyDropFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.LUCKY_DROP_FARM.get(), ModBlockItems.LUCKY_DROP_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testMonsterPlainsCaveFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper,
        ModBlocks.MONSTER_PLAINS_CAVE_FARM.get(),
        ModBlockItems.MONSTER_PLAINS_CAVE_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testNetherFortressFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.NETHER_FORTRESS_FARM.get(), ModBlockItems.NETHER_FORTRESS_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testOceanFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.OCEAN_FARM.get(), ModBlockItems.OCEAN_FARM.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testSwampFarmDrop(GameTestHelper helper) {
    LootTableTestHelper.testBlockDropItem(
        helper, ModBlocks.SWAMP_FARM.get(), ModBlockItems.SWAMP_FARM.get());
    helper.succeed();
  }
}
