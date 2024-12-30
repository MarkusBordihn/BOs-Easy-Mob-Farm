/*
 * Copyright 2023 Markus Bordihn
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

import com.mojang.authlib.GameProfile;
import de.markusbordihn.easymobfarm.Constants;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableTestHelper {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private LootTableTestHelper() {}

  public static void testBlockDropItem(GameTestHelper helper, Block block, Item item) {
    testBlockDropItem(helper, block, item, Items.IRON_PICKAXE);
  }

  public static void testAxeBlockDropItem(GameTestHelper helper, Block block, Item item) {
    testBlockDropItem(helper, block, item, Items.IRON_AXE);
  }

  public static void testBlockDropItem(
      GameTestHelper helper, Block block, Item item, Item miningTool) {
    BlockPos blockPos = new BlockPos(0, 1, 0);
    testBlockDropItem(helper, block, item, miningTool, blockPos);
  }

  public static void testBlockDropItem(
      GameTestHelper helper, Block block, Item item, Item miningTool, BlockPos blockPos) {
    // Define itemStack and mock player
    ItemStack itemStack = new ItemStack(miningTool);
    Player player =
        new Player(
            helper.getLevel(),
            BlockPos.ZERO,
            0.0F,
            new GameProfile(UUID.randomUUID(), "test-mock-player")) {
          public boolean isSpectator() {
            return false;
          }

          public boolean isCreative() {
            return false;
          }
        };
    player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);

    // Set block and prepare for drop
    helper.setBlock(blockPos, block);
    helper.assertBlockPresent(block, blockPos);
    BlockState blockState = helper.getBlockState(blockPos);
    GameTestHelpers.assertTrue(
        helper,
        "Expected block " + block + " but found " + blockState.getBlock(),
        blockState.getBlock() == block);

    // Check if the item can mine the block
    GameTestHelpers.assertTrue(
        helper,
        itemStack.getItem() + " can't mine " + blockState.getBlock(),
        itemStack
            .getItem()
            .mineBlock(
                itemStack, helper.getLevel(), blockState, helper.absolutePos(blockPos), player));
    GameTestHelpers.assertTrue(
        helper,
        player
            + " has not correct Tool "
            + player.getMainHandItem()
            + " for drops "
            + blockState.getBlock(),
        player.hasCorrectToolForDrops(blockState));

    // Destroy block and check for item drop
    blockState
        .getBlock()
        .playerDestroy(
            helper.getLevel(),
            player,
            helper.absolutePos(blockPos),
            blockState,
            null,
            player.getMainHandItem());
    helper.assertItemEntityPresent(item, blockPos, 2D);
    helper.destroyBlock(blockPos);
    helper.assertBlockNotPresent(block, blockPos);
  }
}
