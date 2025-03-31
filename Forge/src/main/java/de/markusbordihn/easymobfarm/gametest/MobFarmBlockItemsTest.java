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
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmTierLevel;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTest;

@SuppressWarnings("unused")
public class MobFarmBlockItemsTest {

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testAnimalPlainsFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testAnimalPlainsFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testAnimalPlainsFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testAnimalPlainsFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testAnimalPlainsFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testBeeHiveFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper, ModBlockItems.BEE_HIVE_FARM.get().asItem(), ModBlocks.BEE_HIVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testBeeHiveFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.BEE_HIVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.BEE_HIVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testBeeHiveFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.BEE_HIVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.BEE_HIVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testBeeHiveFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.BEE_HIVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.BEE_HIVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testBeeHiveFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.BEE_HIVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.BEE_HIVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testDesertFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper, ModBlockItems.DESERT_FARM.get().asItem(), ModBlocks.DESERT_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testDesertFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.DESERT_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.DESERT_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testDesertFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.DESERT_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.DESERT_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testDesertFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.DESERT_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.DESERT_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testDesertFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.DESERT_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.DESERT_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testIronGolemFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper, ModBlockItems.IRON_GOLEM_FARM.get().asItem(), ModBlocks.IRON_GOLEM_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testIronGolemFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.IRON_GOLEM_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.IRON_GOLEM_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testIronGolemFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.IRON_GOLEM_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.IRON_GOLEM_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testIronGolemFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.IRON_GOLEM_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.IRON_GOLEM_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testIronGolemFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.IRON_GOLEM_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.IRON_GOLEM_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testJungleFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper, ModBlockItems.JUNGLE_FARM.get().asItem(), ModBlocks.JUNGLE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testJungleFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.JUNGLE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.JUNGLE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testJungleFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.JUNGLE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.JUNGLE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testJungleFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.JUNGLE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.JUNGLE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testJungleFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.JUNGLE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.JUNGLE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testLuckyDropFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper, ModBlockItems.LUCKY_DROP_FARM.get().asItem(), ModBlocks.LUCKY_DROP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testLuckyDropFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.LUCKY_DROP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.LUCKY_DROP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testLuckyDropFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.LUCKY_DROP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.LUCKY_DROP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testLuckyDropFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.LUCKY_DROP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.LUCKY_DROP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testLuckyDropFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.LUCKY_DROP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.LUCKY_DROP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testMonsterPlainsCaveFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.MONSTER_PLAINS_CAVE_FARM.get().asItem(),
        ModBlocks.MONSTER_PLAINS_CAVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testMonsterPlainsCaveFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.MONSTER_PLAINS_CAVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.MONSTER_PLAINS_CAVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testMonsterPlainsCaveFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.MONSTER_PLAINS_CAVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.MONSTER_PLAINS_CAVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testMonsterPlainsCaveFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.MONSTER_PLAINS_CAVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.MONSTER_PLAINS_CAVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testMonsterPlainsCaveFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.MONSTER_PLAINS_CAVE_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.MONSTER_PLAINS_CAVE_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testNetherFortressFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.NETHER_FORTRESS_FARM.get().asItem(),
        ModBlocks.NETHER_FORTRESS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testNetherFortressFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.NETHER_FORTRESS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.NETHER_FORTRESS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testNetherFortressFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.NETHER_FORTRESS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.NETHER_FORTRESS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testNetherFortressFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.NETHER_FORTRESS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.NETHER_FORTRESS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testNetherFortressFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.NETHER_FORTRESS_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.NETHER_FORTRESS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testOceanFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper, ModBlockItems.OCEAN_FARM.get().asItem(), ModBlocks.OCEAN_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testOceanFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.OCEAN_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.OCEAN_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testOceanFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.OCEAN_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.OCEAN_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testOceanFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.OCEAN_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.OCEAN_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testOceanFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.OCEAN_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.OCEAN_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testSwampFarm(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper, ModBlockItems.SWAMP_FARM.get().asItem(), ModBlocks.SWAMP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testSwampFarmTier0(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.SWAMP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_0,
        ModBlocks.SWAMP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testSwampFarmTier1(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.SWAMP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_1,
        ModBlocks.SWAMP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testSwampFarmTier2(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.SWAMP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_2,
        ModBlocks.SWAMP_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.1x1x1")
  public void testSwampFarmTier3(GameTestHelper helper) {
    MobFarmBlockItemTestHelper.testMobFarmBlockItem(
        helper,
        ModBlockItems.SWAMP_FARM.get().asItem(),
        MobFarmTierLevel.TIER_3,
        ModBlocks.SWAMP_FARM.get());
    helper.succeed();
  }
}
