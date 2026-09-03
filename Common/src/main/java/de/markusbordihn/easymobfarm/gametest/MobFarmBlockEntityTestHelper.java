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

import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.capture.MobColor;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlot;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MobFarmBlockEntityTestHelper {

  private static final BlockPos FARM_POSITION = new BlockPos(1, 2, 1);
  private static final int PROCESSING_TICKS_DELAY = 30;

  private MobFarmBlockEntityTestHelper() {}

  public static void testUnknownFarmTypeTagKeepsBlockFarmType(GameTestHelper helper, Block block) {
    MobFarmBlockEntity blockEntity = placeMobFarm(helper, block);
    if (blockEntity == null) {
      helper.fail("Unable to place mob farm " + block + " for the farm type test.");
      return;
    }

    MobFarmType expectedFarmType = blockEntity.getFarmType();
    CompoundTag compoundTag = new CompoundTag();
    compoundTag.putString(MobFarmBlockEntity.FARM_TYPE_TAG, "not_a_known_farm_type");
    blockEntity.loadAdditional(compoundTag, helper.getLevel().registryAccess());

    GameTestHelpers.assertTrue(
        helper,
        "Unknown farm type should keep "
            + expectedFarmType
            + ", but found "
            + blockEntity.getFarmType(),
        blockEntity.getFarmType() == expectedFarmType);
  }

  public static void testNonLivingCaptureCardHasNoExperience(GameTestHelper helper, Block block) {
    MobFarmBlockEntity blockEntity = placeMobFarm(helper, block);
    if (blockEntity == null) {
      helper.fail("Unable to place mob farm " + block + " for the capture card test.");
      return;
    }

    blockEntity.setItem(MobFarmSlot.CAPTURED_MOB.index(), createNonLivingCaptureCard());

    GameTestHelpers.assertTrue(
        helper,
        "Capture card for a non-living entity should have no experience, but found "
            + blockEntity.getCapturedMobExperience(),
        blockEntity.getCapturedMobExperience() == 0);
  }

  public static void testNonLivingCaptureCardLeavesNoEntity(GameTestHelper helper, Block block) {
    MobFarmBlockEntity blockEntity = placeMobFarm(helper, block);
    if (blockEntity == null) {
      helper.fail("Unable to place mob farm " + block + " for the capture card test.");
      return;
    }

    blockEntity.setItem(MobFarmSlot.CAPTURED_MOB.index(), createNonLivingCaptureCard());

    helper.assertEntityNotPresent(EntityType.ARROW);
    helper.succeed();
  }

  public static void testEmptyCaptureItemKeepsFarmIdle(GameTestHelper helper, Block block) {
    MobFarmBlockEntity blockEntity = placeMobFarm(helper, block);
    if (blockEntity == null) {
      helper.fail("Unable to place mob farm " + block + " for the empty capture item test.");
      return;
    }

    blockEntity.setItem(
        MobFarmSlot.CAPTURED_MOB.index(),
        new ItemStack(MobCaptureCardItem.getMobCaptureCardItem()));

    helper.runAfterDelay(
        PROCESSING_TICKS_DELAY,
        () -> {
          assertUnusableCapturedMob(helper, blockEntity, MobFarmStatus.IDLE);
          helper.succeed();
        });
  }

  public static void testUnknownEntityCaptureCardSetsErrorStatus(
      GameTestHelper helper, Block block) {
    MobFarmBlockEntity blockEntity = placeMobFarm(helper, block);
    if (blockEntity == null) {
      helper.fail("Unable to place mob farm " + block + " for the unknown entity card test.");
      return;
    }

    blockEntity.setItem(MobFarmSlot.CAPTURED_MOB.index(), createUnknownEntityCaptureCard());

    helper.runAfterDelay(
        PROCESSING_TICKS_DELAY,
        () -> {
          assertUnusableCapturedMob(helper, blockEntity, MobFarmStatus.ERROR);
          helper.succeed();
        });
  }

  private static void assertUnusableCapturedMob(
      GameTestHelper helper, MobFarmBlockEntity blockEntity, int expectedFarmStatus) {
    GameTestHelpers.assertTrue(
        helper,
        "Unusable captured mob should set farm status "
            + expectedFarmStatus
            + ", but found "
            + blockEntity.getFarmStatus(),
        blockEntity.getFarmStatus() == expectedFarmStatus);
    GameTestHelpers.assertTrue(
        helper,
        "Unusable captured mob should not add farm progress, but found "
            + blockEntity.getFarmProgress(),
        blockEntity.getFarmProgress() == 0);
    GameTestHelpers.assertTrue(
        helper,
        "Unusable captured mob should stay inside the farm, but the slot was empty.",
        blockEntity.hasCapturedMob());
  }

  private static ItemStack createUnknownEntityCaptureCard() {
    return createCaptureCard("Unknown", "easy_mob_farm:does_not_exist", null);
  }

  private static ItemStack createNonLivingCaptureCard() {
    return createCaptureCard("Arrow", "minecraft:arrow", EntityType.ARROW);
  }

  private static ItemStack createCaptureCard(
      String name, String type, EntityType<?> entityType) {
    MobCaptureData mobCaptureData =
        new MobCaptureData(
            name,
            type,
            entityType,
            new CompoundTag(),
            MobColor.NONE,
            "",
            Rarity.COMMON,
            false);
    return MobCaptureManager.createMobCaptureCard(
        MobCaptureCardItem.getMobCaptureCardItem(), mobCaptureData);
  }

  private static MobFarmBlockEntity placeMobFarm(GameTestHelper helper, Block block) {
    helper.setBlock(FARM_POSITION, block);
    BlockEntity blockEntity = helper.getBlockEntity(FARM_POSITION);
    if (blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity) {
      return mobFarmBlockEntity;
    }

    return null;
  }
}
