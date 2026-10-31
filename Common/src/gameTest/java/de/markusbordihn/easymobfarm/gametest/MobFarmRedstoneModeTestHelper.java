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
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.data.mobfarm.RedstoneMode;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class MobFarmRedstoneModeTestHelper {

  private static final BlockPos MOB_FARM_POS = new BlockPos(0, 1, 0);
  private static final int STATUS_CHECK_DELAY = 3;

  private MobFarmRedstoneModeTestHelper() {}

  public static void testRedstoneMode(
      GameTestHelper helper,
      Item item,
      Block block,
      RedstoneMode redstoneMode,
      boolean powered,
      boolean expectDisabled) {
    MobFarmBlockItemTestHelper.useMobFarmBlockItem(helper, item, MOB_FARM_POS);
    helper.assertBlockPresent(block, MOB_FARM_POS);

    if (!(helper.getLevel().getBlockEntity(helper.absolutePos(MOB_FARM_POS))
        instanceof MobFarmBlockEntity mobFarmBlockEntity)) {
      helper.fail("Mob farm block entity should be present.");
      return;
    }

    mobFarmBlockEntity.setRedstoneMode(redstoneMode);
    int initialFarmStatus = expectDisabled ? MobFarmStatus.IDLE : MobFarmStatus.DISABLED;
    mobFarmBlockEntity.setFarmStatus(initialFarmStatus);

    if (powered) {
      helper.setBlock(MOB_FARM_POS.west(), Blocks.REDSTONE_BLOCK.defaultBlockState());
    }

    String failureMessage =
        "Mob farm with redstone mode "
            + redstoneMode.getId()
            + " and redstone signal "
            + powered
            + " should have disabled status "
            + expectDisabled;

    helper.runAfterDelay(
        STATUS_CHECK_DELAY,
        () ->
            GameTestHelpers.assertTrue(
                helper,
                failureMessage + ", but status is " + mobFarmBlockEntity.getFarmStatus(),
                (mobFarmBlockEntity.getFarmStatus() == MobFarmStatus.DISABLED) == expectDisabled));
  }
}
