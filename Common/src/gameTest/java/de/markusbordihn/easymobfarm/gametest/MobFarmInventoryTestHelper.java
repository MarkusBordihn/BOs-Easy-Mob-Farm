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

import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlot;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlots;
import de.markusbordihn.easymobfarm.data.mobfarm.RedstoneMode;
import de.markusbordihn.easymobfarm.item.MobFarmBlockItem;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class MobFarmInventoryTestHelper {

  private static final BlockPos FARM_POSITION = new BlockPos(1, 2, 1);
  private static final BlockPos SECOND_FARM_POSITION = new BlockPos(0, 2, 0);
  private static final BlockPos LOWER_POSITION = new BlockPos(1, 1, 1);
  private static final BlockPos PLACEMENT_POSITION = new BlockPos(0, 1, 0);
  private static final int BUFFER_PROCESSING_DELAY = 25;
  private static final int HOPPER_TRANSFER_DELAY = 40;
  private static final int TIER_LEVEL = 2;

  private MobFarmInventoryTestHelper() {}

  public static void testSaveAndLoadKeepsFarmState(GameTestHelper helper, Block block) {
    MobFarmBlockEntity mobFarmBlockEntity = placeMobFarm(helper, block, FARM_POSITION);
    loadItemBuffer(helper, mobFarmBlockEntity, new ItemStack(Items.DIAMOND, 3));
    mobFarmBlockEntity.setItem(
        MobFarmSlot.CAPTURED_MOB.index(),
        MobCaptureManager.getMobCaptureCardItem(EntityTypes.CHICKEN, helper.getLevel()));
    mobFarmBlockEntity.setItem(
        MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.get(0).index(),
        new ItemStack(de.markusbordihn.easymobfarm.item.Items.LOOT_ENHANCEMENT));
    mobFarmBlockEntity.setItem(
        MobFarmSlots.RESULT_SLOTS.get(0).index(), new ItemStack(Items.FEATHER, 12));
    mobFarmBlockEntity.setRedstoneMode(RedstoneMode.IGNORE);
    mobFarmBlockEntity.setOwner(helper.makeMockPlayer(GameType.SURVIVAL));
    mobFarmBlockEntity.setFarmTierLevel(TIER_LEVEL);

    MobFarmBlockEntity loadedMobFarmBlockEntity = placeMobFarm(helper, block, SECOND_FARM_POSITION);
    HolderLookup.Provider provider = helper.getLevel().registryAccess();
    loadedMobFarmBlockEntity.loadAdditional(
        TagValueInput.create(
            ProblemReporter.DISCARDING,
            provider,
            mobFarmBlockEntity.saveWithoutMetadata(provider)));

    for (int slotIndex = 0; slotIndex < mobFarmBlockEntity.getContainerSize(); slotIndex++) {
      helper.assertTrue(
          ItemStack.matches(
              mobFarmBlockEntity.getItem(slotIndex), loadedMobFarmBlockEntity.getItem(slotIndex)),
          "Slot "
              + slotIndex
              + " changed from "
              + mobFarmBlockEntity.getItem(slotIndex)
              + " to "
              + loadedMobFarmBlockEntity.getItem(slotIndex));
    }
    helper.assertTrue(
        loadedMobFarmBlockEntity.getRedstoneMode() == RedstoneMode.IGNORE,
        "Redstone mode changed to " + loadedMobFarmBlockEntity.getRedstoneMode());
    helper.assertTrue(
        mobFarmBlockEntity.getOwner().equals(loadedMobFarmBlockEntity.getOwner()),
        "Owner changed to " + loadedMobFarmBlockEntity.getOwner());
    helper.assertTrue(
        loadedMobFarmBlockEntity.getFarmTierLevel() == TIER_LEVEL,
        "Tier level changed to " + loadedMobFarmBlockEntity.getFarmTierLevel());
    helper.assertTrue(
        getBufferedItemCount(helper, loadedMobFarmBlockEntity, Items.DIAMOND) == 3,
        "Buffered diamonds changed to "
            + getBufferedItemCount(helper, loadedMobFarmBlockEntity, Items.DIAMOND));
    helper.succeed();
  }

  public static void testFullOutputBuffersLootWithoutLoss(GameTestHelper helper, Block block) {
    MobFarmBlockEntity mobFarmBlockEntity = placeMobFarm(helper, block, FARM_POSITION);
    fillResultSlots(mobFarmBlockEntity, new ItemStack(Items.STONE, 64));
    mobFarmBlockEntity.setItem(
        MobFarmSlot.CAPTURED_MOB.index(),
        MobCaptureManager.getMobCaptureCardItem(EntityTypes.CHICKEN, helper.getLevel()));
    mobFarmBlockEntity.processingResults(List.of());
    mobFarmBlockEntity.takeItem(MobFarmSlot.CAPTURED_MOB.index());

    int bufferedChickenCount = getBufferedItemCount(helper, mobFarmBlockEntity, Items.CHICKEN);
    helper.assertTrue(
        bufferedChickenCount > 0, "Loot of a full farm was not buffered: no raw chicken found");
    helper.assertTrue(
        countItemEntities(helper, FARM_POSITION, Items.CHICKEN) == 0,
        "Loot of a full farm was dropped into the world instead of buffered");

    fillResultSlots(mobFarmBlockEntity, ItemStack.EMPTY);
    helper.succeedWhen(
        () -> {
          helper.assertTrue(
              mobFarmBlockEntity.getBufferSize() == 0,
              "Buffer still holds " + mobFarmBlockEntity.getBufferSize() + " stacks");
          helper.assertTrue(
              getResultItemCount(mobFarmBlockEntity, Items.CHICKEN) == bufferedChickenCount,
              "Expected "
                  + bufferedChickenCount
                  + " raw chicken from the buffer, but found "
                  + getResultItemCount(mobFarmBlockEntity, Items.CHICKEN));
        });
  }

  public static void testPartialBufferTransferDoesNotDuplicate(GameTestHelper helper, Block block) {
    MobFarmBlockEntity mobFarmBlockEntity = placeMobFarm(helper, block, FARM_POSITION);
    loadItemBuffer(helper, mobFarmBlockEntity, new ItemStack(Items.FEATHER, 10));
    fillResultSlots(mobFarmBlockEntity, new ItemStack(Items.STONE, 64));
    mobFarmBlockEntity.setItem(
        MobFarmSlots.RESULT_SLOTS.get(0).index(), new ItemStack(Items.FEATHER, 60));

    helper.runAfterDelay(
        BUFFER_PROCESSING_DELAY,
        () -> {
          int resultFeatherCount = getResultItemCount(mobFarmBlockEntity, Items.FEATHER);
          int bufferedFeatherCount =
              getBufferedItemCount(helper, mobFarmBlockEntity, Items.FEATHER);
          helper.assertTrue(
              resultFeatherCount == 64,
              "Expected the feather slot to be filled up to 64, but found " + resultFeatherCount);
          helper.assertTrue(
              resultFeatherCount + bufferedFeatherCount == 70,
              "Expected 70 feathers in total, but found "
                  + resultFeatherCount
                  + " in the result slots and "
                  + bufferedFeatherCount
                  + " in the buffer");
          helper.succeed();
        });
  }

  public static void testBreakingFarmDropsBufferedItems(GameTestHelper helper, Block block) {
    MobFarmBlockEntity mobFarmBlockEntity = placeMobFarm(helper, block, FARM_POSITION);
    loadItemBuffer(helper, mobFarmBlockEntity, new ItemStack(Items.DIAMOND, 5));

    helper.destroyBlock(FARM_POSITION);

    int droppedDiamondCount = countItemEntities(helper, FARM_POSITION, Items.DIAMOND);
    helper.assertTrue(
        droppedDiamondCount == 5,
        "Expected 5 buffered diamonds to drop, but found " + droppedDiamondCount);
    helper.succeed();
  }

  public static void testBrokenFarmKeepsTierLevel(GameTestHelper helper, Block block) {
    helper.setBlock(
        FARM_POSITION, block.defaultBlockState().setValue(MobFarmBlock.TIER_LEVEL, TIER_LEVEL));
    BlockState blockState = helper.getBlockState(FARM_POSITION);
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    List<ItemStack> drops =
        Block.getDrops(
            blockState,
            helper.getLevel(),
            helper.absolutePos(FARM_POSITION),
            helper.getLevel().getBlockEntity(helper.absolutePos(FARM_POSITION)),
            player,
            ItemStack.EMPTY);
    ItemStack mobFarmItemStack =
        drops.stream()
            .filter(itemStack -> itemStack.is(block.asItem()))
            .findFirst()
            .orElse(ItemStack.EMPTY);
    helper.assertTrue(!mobFarmItemStack.isEmpty(), "Broken farm dropped no farm item: " + drops);
    helper.assertTrue(
        MobFarmBlockItem.getTierLevel(mobFarmItemStack).getTierLevel() == TIER_LEVEL,
        "Dropped farm item lost its tier level: " + mobFarmItemStack.getComponents());

    MobFarmBlockItemTestHelper.useMobFarmBlockItem(helper, mobFarmItemStack, PLACEMENT_POSITION);
    helper.assertBlockPresent(block, PLACEMENT_POSITION);

    int placedTierLevel = MobFarmBlock.getTierLevel(helper.getBlockState(PLACEMENT_POSITION));
    helper.assertTrue(
        placedTierLevel == TIER_LEVEL,
        "Placed farm has tier level " + placedTierLevel + " instead of " + TIER_LEVEL);
    helper.succeed();
  }

  public static void testRightClickInsertsSingleItems(GameTestHelper helper, Block block) {
    MobFarmBlockEntity mobFarmBlockEntity = placeMobFarm(helper, block, FARM_POSITION);
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    ItemStack captureCards =
        MobCaptureManager.getMobCaptureCardItem(EntityTypes.CHICKEN, helper.getLevel());
    captureCards.setCount(3);
    player.setItemInHand(InteractionHand.MAIN_HAND, captureCards);

    useMobFarm(helper, player);
    helper.assertTrue(
        mobFarmBlockEntity.getCapturedMob().getCount() == 1
            && player.getMainHandItem().getCount() == 2,
        "Expected 1 card in the farm and 2 in the hand, but found "
            + mobFarmBlockEntity.getCapturedMob()
            + " and "
            + player.getMainHandItem());

    player.setItemInHand(
        InteractionHand.MAIN_HAND,
        new ItemStack(de.markusbordihn.easymobfarm.item.Items.LOOT_ENHANCEMENT, 2));
    useMobFarm(helper, player);
    ItemStack enhancement =
        mobFarmBlockEntity.getItem(MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.get(0).index());
    helper.assertTrue(
        enhancement.getCount() == 1 && player.getMainHandItem().getCount() == 1,
        "Expected 1 enhancement in the farm and 1 in the hand, but found "
            + enhancement
            + " and "
            + player.getMainHandItem());

    player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
    player.setShiftKeyDown(true);
    useMobFarm(helper, player);
    helper.assertTrue(
        !mobFarmBlockEntity.hasCapturedMob()
            && MobCaptureManager.getEntityType(player.getMainHandItem(), helper.getLevel())
                == EntityTypes.CHICKEN,
        "Shift-click should return the card to the hand, but found "
            + player.getMainHandItem()
            + " in the hand and "
            + mobFarmBlockEntity.getCapturedMob()
            + " in the farm");
    helper.succeed();
  }

  public static void testHopperBelowPullsOnlyResults(GameTestHelper helper, Block block) {
    MobFarmBlockEntity mobFarmBlockEntity = placeMobFarm(helper, block, FARM_POSITION);
    helper.setBlock(LOWER_POSITION, Blocks.HOPPER);
    mobFarmBlockEntity.setItem(
        MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.get(0).index(),
        new ItemStack(de.markusbordihn.easymobfarm.item.Items.LOOT_ENHANCEMENT));
    mobFarmBlockEntity.setItem(
        MobFarmSlots.RESULT_SLOTS.get(0).index(), new ItemStack(Items.FEATHER));

    helper.succeedWhen(
        () -> {
          HopperBlockEntity hopperBlockEntity =
              helper.getBlockEntity(LOWER_POSITION, HopperBlockEntity.class);
          helper.assertTrue(
              hopperBlockEntity.countItem(Items.FEATHER) == 1,
              "Hopper below the farm did not pull the result");
          helper.assertTrue(
              hopperBlockEntity.countItem(de.markusbordihn.easymobfarm.item.Items.LOOT_ENHANCEMENT)
                  == 0,
              "Hopper below the farm pulled an enhancement");
        });
  }

  public static void testHopperAboveCannotInsert(GameTestHelper helper, Block block) {
    MobFarmBlockEntity mobFarmBlockEntity = placeMobFarm(helper, block, LOWER_POSITION);
    helper.setBlock(FARM_POSITION, Blocks.HOPPER);
    HopperBlockEntity hopperBlockEntity =
        helper.getBlockEntity(FARM_POSITION, HopperBlockEntity.class);
    hopperBlockEntity.setItem(0, new ItemStack(Items.FEATHER));

    helper.runAfterDelay(
        HOPPER_TRANSFER_DELAY,
        () -> {
          helper.assertTrue(
              hopperBlockEntity.countItem(Items.FEATHER) == 1 && mobFarmBlockEntity.isEmpty(),
              "Hopper above the farm inserted an item into the farm");
          helper.succeed();
        });
  }

  private static void useMobFarm(GameTestHelper helper, Player player) {
    BlockPos absolutePosition = helper.absolutePos(FARM_POSITION);
    helper
        .getBlockState(FARM_POSITION)
        .useItemOn(
            player.getMainHandItem(),
            helper.getLevel(),
            player,
            InteractionHand.MAIN_HAND,
            new BlockHitResult(
                Vec3.atCenterOf(absolutePosition), Direction.UP, absolutePosition, false));
  }

  private static MobFarmBlockEntity placeMobFarm(
      GameTestHelper helper, Block block, BlockPos blockPos) {
    helper.setBlock(blockPos, block);
    return helper.getBlockEntity(blockPos, MobFarmBlockEntity.class);
  }

  private static void loadItemBuffer(
      GameTestHelper helper, MobFarmBlockEntity mobFarmBlockEntity, ItemStack bufferedItem) {
    HolderLookup.Provider provider = helper.getLevel().registryAccess();
    TagValueOutput valueOutput =
        TagValueOutput.createWithContext(ProblemReporter.DISCARDING, provider);
    valueOutput.list(MobFarmBlockEntity.ITEM_BUFFER_TAG, ItemStack.CODEC).add(bufferedItem);
    mobFarmBlockEntity.loadAdditional(
        TagValueInput.create(ProblemReporter.DISCARDING, provider, valueOutput.buildResult()));
  }

  private static void fillResultSlots(MobFarmBlockEntity mobFarmBlockEntity, ItemStack itemStack) {
    for (int slot = 0; slot < mobFarmBlockEntity.getNumberOfOutputSlots(); slot++) {
      mobFarmBlockEntity.setItem(MobFarmSlots.RESULT_SLOTS.get(slot).index(), itemStack.copy());
    }
  }

  private static int getResultItemCount(MobFarmBlockEntity mobFarmBlockEntity, Item item) {
    int count = 0;
    for (MobFarmSlot resultSlot : MobFarmSlots.RESULT_SLOTS) {
      ItemStack itemStack = mobFarmBlockEntity.getItem(resultSlot);
      if (itemStack.is(item)) {
        count += itemStack.getCount();
      }
    }
    return count;
  }

  private static int getBufferedItemCount(
      GameTestHelper helper, MobFarmBlockEntity mobFarmBlockEntity, Item item) {
    HolderLookup.Provider provider = helper.getLevel().registryAccess();
    ValueInput valueInput =
        TagValueInput.create(
            ProblemReporter.DISCARDING, provider, mobFarmBlockEntity.saveWithoutMetadata(provider));
    int count = 0;
    for (ItemStack bufferedItem :
        valueInput.listOrEmpty(MobFarmBlockEntity.ITEM_BUFFER_TAG, ItemStack.CODEC)) {
      if (bufferedItem.is(item)) {
        count += bufferedItem.getCount();
      }
    }
    return count;
  }

  private static int countItemEntities(GameTestHelper helper, BlockPos blockPos, Item item) {
    return helper
        .getLevel()
        .getEntitiesOfClass(
            ItemEntity.class,
            new AABB(helper.absolutePos(blockPos)).inflate(2),
            itemEntity -> itemEntity.getItem().is(item))
        .stream()
        .mapToInt(itemEntity -> itemEntity.getItem().getCount())
        .sum();
  }
}
