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

import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.item.mobcatcher.MobCatcherItem;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class MobCatcherTestHelper {

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
}
