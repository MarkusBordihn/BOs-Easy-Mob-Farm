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

import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.MobFarmTemplateBlock;
import de.markusbordihn.easymobfarm.component.DataComponents;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmTierLevel;
import de.markusbordihn.easymobfarm.item.MobFarmBlockItem;
import de.markusbordihn.easymobfarm.item.MobFarmTemplateItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MobFarmBlockItemTestHelper {

  private MobFarmBlockItemTestHelper() {}

  public static void testMobFarmBlockItem(GameTestHelper helper, Item item, Block block) {
    testMobFarmBlockItem(helper, item, null, block);
  }

  public static void testMobFarmBlockItem(
      GameTestHelper helper, Item item, MobFarmTierLevel tierLevel, Block block) {
    BlockPos blockPos = new BlockPos(0, 1, 0);
    ItemStack itemStack = new ItemStack(item);

    // Optional: Set tier level and custom model data
    if (tierLevel != null) {
      MobFarmData mobFarmData = MobFarmData.EMPTY.withTierLevel(tierLevel);
      itemStack.set(DataComponents.MOB_FARM_DATA, mobFarmData);
    }

    // Place block and check if it is present
    if (itemStack.getItem() instanceof MobFarmBlockItem
        && block instanceof MobFarmBlock mobFarmBlock) {
      useAndTestMobFarmBlockItem(helper, itemStack, mobFarmBlock, blockPos);
    } else if (itemStack.getItem() instanceof MobFarmTemplateItem
        && block instanceof MobFarmTemplateBlock mobFarmTemplateBlock) {
      useAndTestMobFarmBlockItem(helper, itemStack, mobFarmTemplateBlock, blockPos);
    } else {
      helper.fail(
          "Item "
              + itemStack.getItem()
              + " is not a MobFarmBlockItem or Block "
              + block
              + " is not a MobFarmBlock");
      return;
    }

    // Check if the placed block has the correct tier level
    if (tierLevel != null) {
      BlockState blockState = helper.getBlockState(blockPos.above());
      GameTestHelpers.assertTrue(
          helper,
          "Expected tier level "
              + tierLevel
              + " but found "
              + MobFarmBlock.getTierLevel(blockState),
          MobFarmBlock.getTierLevel(blockState) == tierLevel.getTierLevel());
    }
  }

  public static void useMobFarmBlockItem(GameTestHelper helper, Item item, BlockPos blockPos) {
    useMobFarmBlockItem(helper, new ItemStack(item), blockPos);
  }

  public static void useMobFarmBlockItem(
      GameTestHelper helper, ItemStack mobFarmBlockItemStack, BlockPos blockPos) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    player.setItemInHand(player.getUsedItemHand(), mobFarmBlockItemStack);
    UseOnContext useOnContext =
        new UseOnContext(
            player,
            player.getUsedItemHand(),
            new BlockHitResult(
                helper.absoluteVec(new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ())),
                Direction.UP,
                helper.absolutePos(blockPos),
                false));
    mobFarmBlockItemStack.useOn(useOnContext);
  }

  public static void useAndTestMobFarmBlockItem(
      GameTestHelper helper,
      ItemStack mobFarmBlockItemStack,
      Block mobFarmBlock,
      BlockPos blockPos) {
    useMobFarmBlockItem(helper, mobFarmBlockItemStack, blockPos);
    helper.assertBlockPresent(mobFarmBlock, blockPos.above());
  }
}
