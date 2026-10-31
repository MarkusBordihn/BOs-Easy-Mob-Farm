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
import net.minecraftforge.gametest.GameTest;

@SuppressWarnings("unused")
public class MobFarmBlockEntityTest {

  @GameTest(structure = "easy_mob_farm:gametest.3x3x3")
  public void testUnknownFarmTypeTagKeepsBlockFarmType(GameTestHelper helper) {
    MobFarmBlockEntityTestHelper.testUnknownFarmTypeTagKeepsBlockFarmType(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.3x3x3")
  public void testNonLivingCaptureCardHasNoExperience(GameTestHelper helper) {
    MobFarmBlockEntityTestHelper.testNonLivingCaptureCardHasNoExperience(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.3x3x3")
  public void testNonLivingCaptureCardLeavesNoEntity(GameTestHelper helper) {
    MobFarmBlockEntityTestHelper.testNonLivingCaptureCardLeavesNoEntity(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
    helper.succeed();
  }

  @GameTest(structure = "easy_mob_farm:gametest.3x3x3", maxTicks = 100)
  public void testEmptyCaptureItemKeepsFarmIdle(GameTestHelper helper) {
    MobFarmBlockEntityTestHelper.testEmptyCaptureItemKeepsFarmIdle(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }

  @GameTest(structure = "easy_mob_farm:gametest.3x3x3", maxTicks = 100)
  public void testUnknownEntityCaptureCardSetsErrorStatus(GameTestHelper helper) {
    MobFarmBlockEntityTestHelper.testUnknownEntityCaptureCardSetsErrorStatus(
        helper, ModBlocks.ANIMAL_PLAINS_FARM.get());
  }
}
