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
public class MenuSlotTest {

  public void testQuickMoveCaptureCardFillsCapturedMobSlotOnce(GameTestHelper helper) {
    MobFarmMenuTestHelper.testQuickMoveCaptureCardFillsCapturedMobSlotOnce(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testQuickMoveNeverFillsOutputSlots(GameTestHelper helper) {
    MobFarmMenuTestHelper.testQuickMoveNeverFillsOutputSlots(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testQuickMoveTakesResultIntoPlayerInventory(GameTestHelper helper) {
    MobFarmMenuTestHelper.testQuickMoveTakesResultIntoPlayerInventory(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testQuickMoveSlotUpgradeAddsOutputSlots(GameTestHelper helper) {
    MobFarmMenuTestHelper.testQuickMoveSlotUpgradeAddsOutputSlots(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testHotbarSwapIntoOutputSlotIsRejected(GameTestHelper helper) {
    MobFarmMenuTestHelper.testHotbarSwapIntoOutputSlotIsRejected(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testHotbarSwapTakesResult(GameTestHelper helper) {
    MobFarmMenuTestHelper.testHotbarSwapTakesResult(helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testHotbarSwapCaptureCardFillsCapturedMobSlotOnce(GameTestHelper helper) {
    MobFarmMenuTestHelper.testHotbarSwapCaptureCardFillsCapturedMobSlotOnce(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testMousePlacementRespectsSlotRestrictions(GameTestHelper helper) {
    MobFarmMenuTestHelper.testMousePlacementRespectsSlotRestrictions(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  public void testCardBinderQuickMoveCardsFillOneBinderSlotEach(GameTestHelper helper) {
    CardBinderMenuTestHelper.testQuickMoveCardsFillOneBinderSlotEach(helper);
    helper.succeed();
  }

  public void testCardBinderQuickMoveCardsNeverStackInBinderSlot(GameTestHelper helper) {
    CardBinderMenuTestHelper.testQuickMoveCardsNeverStackInBinderSlot(helper);
    helper.succeed();
  }

  public void testCardBinderQuickMoveIgnoresNonCards(GameTestHelper helper) {
    CardBinderMenuTestHelper.testQuickMoveIgnoresNonCards(helper);
    helper.succeed();
  }

  public void testCardBinderQuickMoveTakesCardOut(GameTestHelper helper) {
    CardBinderMenuTestHelper.testQuickMoveTakesCardOut(helper);
    helper.succeed();
  }

  public void testCardBinderOpenBinderCannotBeMoved(GameTestHelper helper) {
    CardBinderMenuTestHelper.testOpenBinderCannotBeMoved(helper);
    helper.succeed();
  }
}
