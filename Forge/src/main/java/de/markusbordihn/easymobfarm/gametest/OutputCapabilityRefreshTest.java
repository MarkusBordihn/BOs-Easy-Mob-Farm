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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlots;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings("unused")
@GameTestHolder(Constants.MOD_ID)
public class OutputCapabilityRefreshTest {

  private static final BlockPos FARM_POSITION = new BlockPos(1, 2, 1);

  @GameTest(
      template = "easy_mob_farm:gametest.3x3x3",
      timeoutTicks = MobFarmBlockEntity.DEFAULT_RECHECK_TICKS * 2)
  public void testStalledOutputRefreshHandsListenersUsableHandler(GameTestHelper helper) {
    helper.setBlock(FARM_POSITION, ModBlocks.ANIMAL_PLAINS_FARM.get());
    MobFarmBlockEntity mobFarmBlockEntity =
        (MobFarmBlockEntity) helper.getBlockEntity(FARM_POSITION);
    mobFarmBlockEntity.setItem(
        MobFarmSlots.RESULT_SLOTS.get(0).index(), new ItemStack(Items.FEATHER));

    AtomicReference<LazyOptional<IItemHandler>> requeriedItemHandler = new AtomicReference<>();
    mobFarmBlockEntity
        .getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.DOWN)
        .addListener(
            invalidatedItemHandler ->
                requeriedItemHandler.set(
                    mobFarmBlockEntity.getCapability(
                        ForgeCapabilities.ITEM_HANDLER, Direction.DOWN)));

    helper.succeedWhen(
        () -> {
          helper.assertTrue(requeriedItemHandler.get() != null, "Stalled output was not refreshed");
          helper.assertTrue(
              requeriedItemHandler.get().isPresent(),
              "Listener re-querying on invalidation received an invalidated item handler");
        });
  }
}
