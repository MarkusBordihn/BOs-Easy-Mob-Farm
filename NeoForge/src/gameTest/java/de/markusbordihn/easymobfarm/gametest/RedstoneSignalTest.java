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
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.data.mobfarm.RedstoneMode;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.properties.AttachFace;

@SuppressWarnings("unused")
public class RedstoneSignalTest {

  public void testRedstoneSignalWithLever(GameTestHelper helper) {

    BlockPos mobFarmPos = new BlockPos(0, 1, 0);
    BlockPos leverPos = mobFarmPos.west();

    // Set mob farm block
    MobFarmBlockItemTestHelper.useMobFarmBlockItem(
        helper, ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(), mobFarmPos);
    GameTestHelpers.assertTrue(
        helper,
        "Mob farm block should be placed.",
        helper.getBlockState(mobFarmPos).is(ModBlocks.ANIMAL_PLAINS_FARM.get()));
    GameTestHelpers.assertFalse(
        helper,
        "Mob farm should not be powered by redstone signal.",
        helper.getBlockState(mobFarmPos).getValue(MobFarmBlock.POWERED));

    // Place a lever next to the mob farm
    helper.setBlock(
        leverPos,
        Blocks.LEVER
            .defaultBlockState()
            .setValue(LeverBlock.FACING, Direction.WEST)
            .setValue(LeverBlock.FACE, AttachFace.WALL)
            .setValue(LeverBlock.POWERED, true));
    GameTestHelpers.assertTrue(
        helper, "Lever should be placed.", helper.getBlockState(leverPos).is(Blocks.LEVER));
    helper.pullLever(leverPos);

    // Activate redstone signal
    helper.runAfterDelay(
        1,
        () -> {
          GameTestHelpers.assertTrue(
              helper,
              "Mob farm should be powered by redstone signal.",
              helper.getBlockState(mobFarmPos).getValue(MobFarmBlock.POWERED));
          helper.succeed();
        });
  }

  public void testRedstoneSignalWithRedstoneBlock(GameTestHelper helper) {

    BlockPos mobFarmPos = new BlockPos(0, 1, 0);
    BlockPos redstoneBlockPos = mobFarmPos.west();

    // Set mob farm block
    MobFarmBlockItemTestHelper.useMobFarmBlockItem(
        helper, ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(), mobFarmPos);
    GameTestHelpers.assertTrue(
        helper,
        "Mob farm block should be placed.",
        helper.getBlockState(mobFarmPos).is(ModBlocks.ANIMAL_PLAINS_FARM.get()));
    GameTestHelpers.assertFalse(
        helper,
        "Mob farm should not be powered by redstone signal.",
        helper.getBlockState(mobFarmPos).getValue(MobFarmBlock.POWERED));

    // Place a red stone block next to the mob farm
    helper.setBlock(redstoneBlockPos, Blocks.REDSTONE_BLOCK.defaultBlockState());
    GameTestHelpers.assertTrue(
        helper,
        "Redstone block should be placed.",
        helper.getBlockState(redstoneBlockPos).is(Blocks.REDSTONE_BLOCK));

    // Activate redstone signal
    helper.runAfterDelay(
        1,
        () -> {
          GameTestHelpers.assertTrue(
              helper,
              "Mob farm should be powered by redstone signal.",
              helper.getBlockState(mobFarmPos).getValue(MobFarmBlock.POWERED));
          helper.succeed();
        });
  }

  public void testRedstoneModeDisableOnSignal(GameTestHelper helper) {
    MobFarmRedstoneModeTestHelper.testRedstoneMode(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        ModBlocks.ANIMAL_PLAINS_FARM.get(),
        RedstoneMode.DISABLE_ON_SIGNAL,
        true,
        true);
  }

  public void testRedstoneModeEnableOnSignal(GameTestHelper helper) {
    MobFarmRedstoneModeTestHelper.testRedstoneMode(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        ModBlocks.ANIMAL_PLAINS_FARM.get(),
        RedstoneMode.ENABLE_ON_SIGNAL,
        true,
        false);
  }

  public void testRedstoneModeEnableOnSignalWithoutSignal(GameTestHelper helper) {
    MobFarmRedstoneModeTestHelper.testRedstoneMode(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        ModBlocks.ANIMAL_PLAINS_FARM.get(),
        RedstoneMode.ENABLE_ON_SIGNAL,
        false,
        true);
  }

  public void testRedstoneModeIgnore(GameTestHelper helper) {
    MobFarmRedstoneModeTestHelper.testRedstoneMode(
        helper,
        ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(),
        ModBlocks.ANIMAL_PLAINS_FARM.get(),
        RedstoneMode.IGNORE,
        true,
        false);
  }
}
