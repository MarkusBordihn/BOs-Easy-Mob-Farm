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
import de.markusbordihn.easymobfarm.item.ModItems;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@PrefixGameTestTemplate(value = false)
@GameTestHolder(Constants.MOD_ID)
public class ModItemsTest {

  @GameTest(template = "gametest.1x1x1")
  public void testBlankMobCaptureCardItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.BLANK_MOB_CAPTURE_CARD.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testCreativeMobCaptureCardItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.CREATIVE_MOB_CAPTURE_CARD.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testMobCaptureCardItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.MOB_CAPTURE_CARD.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testCreativeSpeedEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.CREATIVE_SPEED_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testEggCollectorEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.EGG_COLLECTOR_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testExperienceEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.EXPERIENCE_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testHoneyExtractorEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.HONEY_EXTRACTOR_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testHoneyHarvesterFrameEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.HONEY_HARVESTER_FRAME_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testLootEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.LOOT_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testLuckEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.LUCK_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testPollenTrapEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.POLLEN_TRAP_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testSheepEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.SHEEP_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testSpeedEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.SPEED_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testSwordEnhancementItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.SWORD_ENHANCEMENT.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testNoFlowersFilterItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.NO_FLOWERS_FILTER.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testNoMeatFilterItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.NO_MEAT_FILTER.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testBigSlotUpgradeItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.BIG_SLOT_UPGRADE.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testSmallSlotUpgradeItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.SMALL_SLOT_UPGRADE.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testCreativeMobCatcherItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.CREATIVE_MOB_CATCHER.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testEnduringCaptureNetItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.ENDURING_CAPTURE_NET.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testIronboundContainmentCageItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.IRONBOUND_CONTAINMENT_CAGE.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testMysticBindingCrystalItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.MYSTIC_BINDING_CRYSTAL.get());
    helper.succeed();
  }

  @GameTest(template = "gametest.1x1x1")
  public void testVoidBindingChainItem(GameTestHelper helper) {
    ModItemsTestHelper.testModItem(helper, ModItems.VOID_BINDING_CHAIN.get());
    helper.succeed();
  }
}
