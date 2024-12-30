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
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.item.MobFarmBlockItem;
import de.markusbordihn.easymobfarm.item.MobFarmTemplateItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MobFarmBlockItemTestHelper {

  private MobFarmBlockItemTestHelper() {}

  public static void testMobFarmBlockItem(GameTestHelper helper, Item item, Block block) {
    useMobFarmBlockItem(helper, new ItemStack(item), block, new BlockPos(0, 1, 0));
  }

  public static void useMobFarmBlockItem(
      GameTestHelper helper, Item item, int tierLevel, Block block) {
    BlockPos blockPos = new BlockPos(0, 1, 0);
    ItemStack itemStack = new ItemStack(item);

    // Set tier level and custom model data
    CompoundTag tag =
        itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getUnsafe();
    tag.putInt(MobFarmBlockEntity.TIER_LEVEL_TAG, tierLevel);
    CustomData.set(DataComponents.CUSTOM_DATA, itemStack, tag);

    // Place block and check if it is present
    useMobFarmBlockItem(helper, itemStack, block, blockPos);

    // Check if the placed block has the correct tier level
    BlockState blockState = helper.getBlockState(blockPos.above());
    GameTestHelpers.assertTrue(
        helper,
        "Expected tier level " + tierLevel + " but found " + MobFarmBlock.getTierLevel(blockState),
        MobFarmBlock.getTierLevel(blockState) == tierLevel);
  }

  public static void useMobFarmBlockItem(
      GameTestHelper helper, ItemStack itemStack, Block block, BlockPos blockPos) {
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
    }
  }

  public static void useAndTestMobFarmBlockItem(
      GameTestHelper helper,
      ItemStack mobFarmBlockItemStack,
      Block mobFarmBlock,
      BlockPos blockPos) {
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

    helper.assertBlockPresent(mobFarmBlock, blockPos.above());
  }
}
