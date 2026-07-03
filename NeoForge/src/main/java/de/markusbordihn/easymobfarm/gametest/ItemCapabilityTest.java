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

import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlots;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

@SuppressWarnings("unused")
public class ItemCapabilityTest {

  public static void testItemCapabilityExtraction(GameTestHelper helper) {

    BlockPos mobFarmPos = new BlockPos(1, 1, 1);

    // Set mob farm block
    MobFarmBlockItemTestHelper.useMobFarmBlockItem(
        helper, ModBlockItems.ANIMAL_PLAINS_FARM.get().asItem(), mobFarmPos);
    GameTestHelpers.assertTrue(
        helper,
        "Mob farm block should be placed.",
        helper.getBlockState(mobFarmPos).is(ModBlocks.ANIMAL_PLAINS_FARM.get()));

    // Place loot into the first result slot
    MobFarmBlockEntity mobFarmBlockEntity =
        helper.getBlockEntity(mobFarmPos, MobFarmBlockEntity.class);
    int resultSlotIndex = MobFarmSlots.RESULT_SLOTS.get(0).index();
    mobFarmBlockEntity.setItem(resultSlotIndex, new ItemStack(Items.BONE, 8));

    // Query the item capability from the side, like Pipez, RS2 or Sophisticated Storage do
    ResourceHandler<ItemResource> itemHandler =
        helper
            .getLevel()
            .getCapability(
                Capabilities.Item.BLOCK, helper.absolutePos(mobFarmPos), Direction.WEST);
    GameTestHelpers.assertTrue(
        helper, "Item capability should be exposed on the side.", itemHandler != null);
    GameTestHelpers.assertTrue(
        helper,
        "Item capability should only expose the result slots.",
        itemHandler.size() == MobFarmSlots.RESULT_SLOTS.size());

    // Extract one item through the capability
    ItemResource itemResource = itemHandler.getResource(0);
    GameTestHelpers.assertTrue(
        helper, "Loot should be visible through the item capability.", !itemResource.isEmpty());
    try (Transaction transaction = Transaction.openRoot()) {
      int extractedAmount = itemHandler.extract(0, itemResource, 1, transaction);
      GameTestHelpers.assertTrue(
          helper,
          "One item should be extractable through the item capability.",
          extractedAmount == 1);
      transaction.commit();
    }
    GameTestHelpers.assertTrue(
        helper,
        "Extraction should reduce the result slot content.",
        mobFarmBlockEntity.getItem(resultSlotIndex).getCount() == 7);

    // Inserting items from the side should not be possible
    try (Transaction transaction = Transaction.openRoot()) {
      int insertedAmount =
          itemHandler.insert(ItemResource.of(new ItemStack(Items.DIRT)), 1, transaction);
      GameTestHelpers.assertTrue(
          helper,
          "Inserting items from the side should not be possible.",
          insertedAmount == 0);
    }

    helper.succeed();
  }
}
