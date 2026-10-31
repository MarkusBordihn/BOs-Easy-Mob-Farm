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

import de.markusbordihn.easymobfarm.config.MobCatcherConfig;
import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.item.mobcatcher.MobCatcherItem;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MobCatcherTestHelper {

  private static final BlockPos RELEASE_GROUND_POSITION = new BlockPos(1, 1, 1);
  private static final BlockPos RELEASE_POSITION = new BlockPos(1, 2, 1);

  private MobCatcherTestHelper() {}

  public static void testRejectedCaptureKeepsDurability(GameTestHelper helper) {
    ItemStack mobCatcher = new ItemStack(Items.ENDURING_CAPTURE_NET);
    MobCatcherItem mobCatcherItem = (MobCatcherItem) mobCatcher.getItem();
    Cow cow = helper.spawn(EntityType.COW, 1, 2, 1);
    cow.setHealth(cow.getMaxHealth());

    InteractionResult result =
        mobCatcherItem.interactLivingEntity(
            mobCatcher, helper.makeMockPlayer(GameType.SURVIVAL), cow, InteractionHand.MAIN_HAND);
    cow.discard();

    GameTestHelpers.assertTrue(
        helper,
        "A rejected capture should fail without using durability, but got "
            + result
            + " with damage "
            + mobCatcher.getDamageValue(),
        result == InteractionResult.FAIL && mobCatcher.getDamageValue() == 0);
  }

  public static void testSuccessfulCaptureUsesDurability(GameTestHelper helper) {
    ItemStack mobCatcher = new ItemStack(Items.ENDURING_CAPTURE_NET);
    MobCatcherItem mobCatcherItem = (MobCatcherItem) mobCatcher.getItem();
    Cow cow = helper.spawn(EntityType.COW, 1, 2, 1);
    cow.setHealth(1.0f);
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);

    InteractionResult result =
        mobCatcherItem.interactLivingEntity(mobCatcher, player, cow, InteractionHand.MAIN_HAND);
    ItemStack usedMobCatcher = player.getItemInHand(InteractionHand.MAIN_HAND);

    GameTestHelpers.assertTrue(
        helper,
        "A successful capture should consume durability, but got "
            + result
            + " with damage "
            + usedMobCatcher.getDamageValue(),
        result == InteractionResult.CONSUME && usedMobCatcher.getDamageValue() > 0);
    GameTestHelpers.assertTrue(
        helper,
        "A successful capture should store the mob capture data on the used mob catcher.",
        mobCatcherItem.hasMobCaptureData(usedMobCatcher));
  }

  public static void testDenyListRejectsCapture(GameTestHelper helper) {
    Set<String> previousDenyList = MobCatcherConfig.ENDURING_CAPTURE_NET_DENY_LIST;
    MobCatcherConfig.ENDURING_CAPTURE_NET_DENY_LIST = Set.of("minecraft:cow");
    try {
      assertCowCaptureRejected(helper, "A cow on the deny list should not be captured");
    } finally {
      MobCatcherConfig.ENDURING_CAPTURE_NET_DENY_LIST = previousDenyList;
    }
    helper.succeed();
  }

  public static void testAllowListRejectsOtherMobs(GameTestHelper helper) {
    Set<String> previousAllowList = MobCatcherConfig.ENDURING_CAPTURE_NET_ALLOW_LIST;
    MobCatcherConfig.ENDURING_CAPTURE_NET_ALLOW_LIST = Set.of("minecraft:pig");
    try {
      assertCowCaptureRejected(helper, "A cow missing from the allow list should not be captured");
    } finally {
      MobCatcherConfig.ENDURING_CAPTURE_NET_ALLOW_LIST = previousAllowList;
    }
    helper.succeed();
  }

  public static void testCapturedMobCanBeReleased(GameTestHelper helper) {
    helper.setBlock(RELEASE_GROUND_POSITION, Blocks.STONE);
    ItemStack mobCatcher = new ItemStack(Items.ENDURING_CAPTURE_NET);
    MobCatcherItem mobCatcherItem = (MobCatcherItem) mobCatcher.getItem();
    Cow cow = helper.spawn(EntityType.COW, RELEASE_POSITION);
    cow.setHealth(1.0f);
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    player.setItemInHand(InteractionHand.MAIN_HAND, mobCatcher);
    mobCatcherItem.interactLivingEntity(mobCatcher, player, cow, InteractionHand.MAIN_HAND);
    helper.assertTrue(cow.isRemoved(), "The cow should be captured before it can be released.");

    BlockPos absoluteGroundPosition = helper.absolutePos(RELEASE_GROUND_POSITION);
    InteractionResult result =
        player
            .getItemInHand(InteractionHand.MAIN_HAND)
            .useOn(
                new UseOnContext(
                    player,
                    InteractionHand.MAIN_HAND,
                    new BlockHitResult(
                        Vec3.atCenterOf(absoluteGroundPosition),
                        Direction.UP,
                        absoluteGroundPosition,
                        false)));
    List<Cow> releasedCows =
        helper
            .getLevel()
            .getEntitiesOfClass(
                Cow.class, new AABB(helper.absolutePos(RELEASE_POSITION)).inflate(2));

    helper.assertTrue(
        result.consumesAction() && releasedCows.size() == 1,
        "The captured cow should be released, but got " + result + " with " + releasedCows);
    helper.assertFalse(
        mobCatcherItem.hasMobCaptureData(player.getItemInHand(InteractionHand.MAIN_HAND)),
        "The mob catcher should be empty after releasing the cow.");
    helper.succeed();
  }

  private static void assertCowCaptureRejected(GameTestHelper helper, String message) {
    ItemStack mobCatcher = new ItemStack(Items.ENDURING_CAPTURE_NET);
    MobCatcherItem mobCatcherItem = (MobCatcherItem) mobCatcher.getItem();
    Cow cow = helper.spawn(EntityType.COW, RELEASE_POSITION);
    cow.setHealth(1.0f);

    InteractionResult result =
        mobCatcherItem.interactLivingEntity(
            mobCatcher, helper.makeMockPlayer(GameType.SURVIVAL), cow, InteractionHand.MAIN_HAND);
    boolean cowCaptured = cow.isRemoved();
    cow.discard();

    helper.assertTrue(
        result == InteractionResult.FAIL && !cowCaptured,
        message + ", but got " + result + " with captured " + cowCaptured);
  }
}
