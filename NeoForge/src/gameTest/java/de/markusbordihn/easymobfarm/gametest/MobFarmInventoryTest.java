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

import de.markusbordihn.easymobfarm.block.ModBlocks;
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings("unused")
public class MobFarmInventoryTest {

  public void testSaveAndLoadKeepsFarmState(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testSaveAndLoadKeepsFarmState(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  public void testFullOutputBuffersLootWithoutLoss(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testFullOutputBuffersLootWithoutLoss(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  public void testPartialBufferTransferDoesNotDuplicate(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testPartialBufferTransferDoesNotDuplicate(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  public void testBreakingFarmDropsBufferedItems(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testBreakingFarmDropsBufferedItems(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  public void testBrokenFarmKeepsTierLevel(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testBrokenFarmKeepsTierLevel(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  public void testRightClickInsertsSingleItems(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testRightClickInsertsSingleItems(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  public void testHopperBelowPullsOnlyResults(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testHopperBelowPullsOnlyResults(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  public void testHopperAboveCannotInsert(GameTestHelper helper) {
    MobFarmInventoryTestHelper.testHopperAboveCannotInsert(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }
}
