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

import de.markusbordihn.easymobfarm.item.Items;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

@SuppressWarnings("unused")
public class ModItemsTest {

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testBlankMobCaptureCardItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.BLANK_MOB_CAPTURE_CARD);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testCreativeMobCaptureCardItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.CREATIVE_MOB_CAPTURE_CARD);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testMobCaptureCardItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.MOB_CAPTURE_CARD);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testCreativeSpeedEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.CREATIVE_SPEED_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testEggCollectorEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.EGG_COLLECTOR_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testExperienceEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.EXPERIENCE_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testHoneyExtractorEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.HONEY_EXTRACTOR_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testHoneyHarvesterFrameEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.HONEY_HARVESTER_FRAME_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testLootEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.LOOT_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testLuckEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.LUCK_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testPollenTrapEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.POLLEN_TRAP_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testSheepEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.SHEEP_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testSpeedEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.SPEED_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testSwordEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.SWORD_ENHANCEMENT);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testNoFlowersFilterItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.NO_FLOWERS_FILTER);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testNoMeatFilterItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.NO_MEAT_FILTER);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testBigSlotUpgradeItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.BIG_SLOT_UPGRADE);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testSmallSlotUpgradeItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.SMALL_SLOT_UPGRADE);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testCreativeMobCatcherItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.CREATIVE_MOB_CATCHER);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testEnduringCaptureNetItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.ENDURING_CAPTURE_NET);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testIronboundContainmentCageItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.IRONBOUND_CONTAINMENT_CAGE);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testMysticBindingCrystalItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.MYSTIC_BINDING_CRYSTAL);
    helper.succeed();
  }

  @GameTest(template = "easy_mob_farm:gametest.1x1x1")
  public void testVoidBindingChainItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, Items.VOID_BINDING_CHAIN);
    helper.succeed();
  }
}
