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
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlot;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlots;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Block;

public class MobFarmMenuTestHelper {

  private static final BlockPos FARM_POSITION = new BlockPos(1, 2, 1);
  private static final int INVENTORY_SLOT = 9;
  private static final int HOTBAR_SLOT = 0;
  private static final int LEFT_MOUSE_BUTTON = 0;

  private MobFarmMenuTestHelper() {}

  public static void testQuickMoveCaptureCardFillsCapturedMobSlotOnce(
      GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    player
        .getInventory()
        .setItem(INVENTORY_SLOT, new ItemStack(MobCaptureCardItem.getMobCaptureCardItem(), 2));

    quickMoveInventorySlot(menu, player, INVENTORY_SLOT);

    GameTestHelpers.assertTrue(
        helper,
        "Shift-click should move exactly one capture card into the captured mob slot, but found "
            + mobFarm.getItem(MobFarmSlot.CAPTURED_MOB.index())
            + " in the farm and "
            + player.getInventory().getItem(INVENTORY_SLOT)
            + " in the inventory",
        mobFarm.getItem(MobFarmSlot.CAPTURED_MOB.index()).getCount() == 1
            && player.getInventory().getItem(INVENTORY_SLOT).getCount() == 1);
  }

  public static void testQuickMoveNeverFillsOutputSlots(GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    player.getInventory().setItem(INVENTORY_SLOT, new ItemStack(Items.BONE, 64));

    quickMoveInventorySlot(menu, player, INVENTORY_SLOT);

    assertOutputSlotsEmpty(helper, mobFarm, "Shift-click from the player inventory");
  }

  public static void testQuickMoveTakesResultIntoPlayerInventory(
      GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    int resultSlot = MobFarmSlots.RESULT_SLOTS.get(0).index();
    mobFarm.setItem(resultSlot, new ItemStack(Items.BONE, 16));

    menu.clicked(
        GameTestHelpers.menuSlotIndex(menu, mobFarm, resultSlot),
        LEFT_MOUSE_BUTTON,
        ContainerInput.QUICK_MOVE,
        player);

    assertOutputSlotsEmpty(helper, mobFarm, "Shift-click on a result");
    GameTestHelpers.assertTrue(
        helper,
        "Shift-click on a result should move all 16 bones into the player inventory, but found "
            + player.getInventory().countItem(Items.BONE),
        player.getInventory().countItem(Items.BONE) == 16);
  }

  public static void testQuickMoveSlotUpgradeAddsOutputSlots(GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    player
        .getInventory()
        .setItem(
            INVENTORY_SLOT,
            new ItemStack(de.markusbordihn.easymobfarm.item.Items.SMALL_SLOT_UPGRADE));

    quickMoveInventorySlot(menu, player, INVENTORY_SLOT);

    GameTestHelpers.assertTrue(
        helper,
        "Shift-click should move the slot upgrade into the first slot upgrade slot, but found "
            + mobFarm.getItem(MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS.get(0).index()),
        mobFarm
            .getItem(MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS.get(0).index())
            .is(de.markusbordihn.easymobfarm.item.Items.SMALL_SLOT_UPGRADE));
    GameTestHelpers.assertTrue(
        helper,
        "Slot upgrade should add output slots, but the menu still shows "
            + menu.getMobFarmNumberOfOutputSlots(),
        menu.getMobFarmNumberOfOutputSlots() > MobFarmMenu.MIN_NUMBER_OF_OUTPUT_SLOTS);
  }

  public static void testHotbarSwapIntoOutputSlotIsRejected(GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    player.getInventory().setItem(HOTBAR_SLOT, new ItemStack(Items.BONE));

    menu.clicked(
        GameTestHelpers.menuSlotIndex(menu, mobFarm, MobFarmSlots.RESULT_SLOTS.get(0).index()),
        HOTBAR_SLOT,
        ContainerInput.SWAP,
        player);

    assertOutputSlotsEmpty(helper, mobFarm, "Hotbar swap");
    GameTestHelpers.assertTrue(
        helper,
        "Rejected hotbar swap should keep the bone in the hotbar, but found "
            + player.getInventory().getItem(HOTBAR_SLOT),
        player.getInventory().getItem(HOTBAR_SLOT).is(Items.BONE));
  }

  public static void testHotbarSwapTakesResult(GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    int resultSlot = MobFarmSlots.RESULT_SLOTS.get(0).index();
    mobFarm.setItem(resultSlot, new ItemStack(Items.BONE, 16));

    menu.clicked(
        GameTestHelpers.menuSlotIndex(menu, mobFarm, resultSlot),
        HOTBAR_SLOT,
        ContainerInput.SWAP,
        player);

    assertOutputSlotsEmpty(helper, mobFarm, "Hotbar swap on a result");
    GameTestHelpers.assertTrue(
        helper,
        "Hotbar swap on a result should move all 16 bones into the hotbar, but found "
            + player.getInventory().getItem(HOTBAR_SLOT),
        player.getInventory().getItem(HOTBAR_SLOT).is(Items.BONE)
            && player.getInventory().getItem(HOTBAR_SLOT).getCount() == 16);
  }

  public static void testHotbarSwapCaptureCardFillsCapturedMobSlotOnce(
      GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    player
        .getInventory()
        .setItem(HOTBAR_SLOT, new ItemStack(MobCaptureCardItem.getMobCaptureCardItem(), 2));

    menu.clicked(
        GameTestHelpers.menuSlotIndex(menu, mobFarm, MobFarmSlot.CAPTURED_MOB.index()),
        HOTBAR_SLOT,
        ContainerInput.SWAP,
        player);

    GameTestHelpers.assertTrue(
        helper,
        "Hotbar swap should move exactly one capture card into the captured mob slot, but found "
            + mobFarm.getItem(MobFarmSlot.CAPTURED_MOB.index())
            + " in the farm and "
            + player.getInventory().getItem(HOTBAR_SLOT)
            + " in the hotbar",
        mobFarm.getItem(MobFarmSlot.CAPTURED_MOB.index()).getCount() == 1
            && player.getInventory().getItem(HOTBAR_SLOT).getCount() == 1);
  }

  public static void testMousePlacementRespectsSlotRestrictions(
      GameTestHelper helper, Block block) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    MobFarmBlockEntity mobFarm = placeMobFarm(helper, block);
    MobFarmMenu menu = openMenu(mobFarm, player);
    menu.setCarried(new ItemStack(Items.BONE, 4));

    placeCarried(menu, player, mobFarm, MobFarmSlots.RESULT_SLOTS.get(0).index());
    placeCarried(menu, player, mobFarm, MobFarmSlot.CAPTURED_MOB.index());
    placeCarried(menu, player, mobFarm, MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.get(0).index());
    placeCarried(menu, player, mobFarm, MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS.get(0).index());
    placeCarried(menu, player, mobFarm, MobFarmSlots.FILTER_ITEM_SLOTS.get(0).index());

    assertOutputSlotsEmpty(helper, mobFarm, "Placing a bone with the mouse");
    GameTestHelpers.assertTrue(
        helper,
        "A bone should only be accepted by the filter slot, and only once, but the farm holds "
            + mobFarm.getItem(MobFarmSlot.CAPTURED_MOB.index())
            + ", "
            + mobFarm.getItem(MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.get(0).index())
            + ", "
            + mobFarm.getItem(MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS.get(0).index())
            + ", "
            + mobFarm.getItem(MobFarmSlots.FILTER_ITEM_SLOTS.get(0).index())
            + " while carrying "
            + menu.getCarried(),
        mobFarm.getItem(MobFarmSlot.CAPTURED_MOB.index()).isEmpty()
            && mobFarm.getItem(MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.get(0).index()).isEmpty()
            && mobFarm.getItem(MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS.get(0).index()).isEmpty()
            && mobFarm.getItem(MobFarmSlots.FILTER_ITEM_SLOTS.get(0).index()).getCount() == 1
            && menu.getCarried().getCount() == 3);
  }

  private static void assertOutputSlotsEmpty(
      GameTestHelper helper, MobFarmBlockEntity mobFarm, String action) {
    for (MobFarmSlot resultSlot : MobFarmSlots.RESULT_SLOTS) {
      if (!mobFarm.getItem(resultSlot.index()).isEmpty()) {
        helper.fail(
            action
                + " should leave the output slots empty, but "
                + resultSlot
                + " holds "
                + mobFarm.getItem(resultSlot.index()));
      }
    }
  }

  private static void quickMoveInventorySlot(
      MobFarmMenu menu, Player player, int inventorySlotIndex) {
    Inventory inventory = player.getInventory();
    menu.clicked(
        GameTestHelpers.menuSlotIndex(menu, inventory, inventorySlotIndex),
        LEFT_MOUSE_BUTTON,
        ContainerInput.QUICK_MOVE,
        player);
  }

  private static void placeCarried(
      MobFarmMenu menu, Player player, MobFarmBlockEntity mobFarm, int containerSlotIndex) {
    menu.clicked(
        GameTestHelpers.menuSlotIndex(menu, mobFarm, containerSlotIndex),
        LEFT_MOUSE_BUTTON,
        ContainerInput.PICKUP,
        player);
  }

  private static MobFarmMenu openMenu(MobFarmBlockEntity mobFarm, Player player) {
    return (MobFarmMenu) mobFarm.createMenu(0, player.getInventory(), player);
  }

  private static MobFarmBlockEntity placeMobFarm(GameTestHelper helper, Block block) {
    helper.setBlock(FARM_POSITION, block);
    if (helper.getLevel().getBlockEntity(helper.absolutePos(FARM_POSITION))
        instanceof MobFarmBlockEntity mobFarmBlockEntity) {
      return mobFarmBlockEntity;
    }

    throw new IllegalStateException("Unable to place mob farm " + block + " for the menu test.");
  }
}
