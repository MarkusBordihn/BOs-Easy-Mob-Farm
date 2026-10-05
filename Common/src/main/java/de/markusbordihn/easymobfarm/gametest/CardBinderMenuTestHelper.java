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
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import de.markusbordihn.easymobfarm.menu.CardBinderMenu;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;

public class CardBinderMenuTestHelper {

  private static final int BINDER_HOTBAR_SLOT = 0;
  private static final int INVENTORY_SLOT = 9;
  private static final int LEFT_MOUSE_BUTTON = 0;
  private static final int CARD_COUNT = 3;

  private CardBinderMenuTestHelper() {}

  public static void testQuickMoveCardsFillOneBinderSlotEach(GameTestHelper helper) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    CardBinderMenu menu = openBinder(player);
    player.getInventory().setItem(INVENTORY_SLOT, createCards(CARD_COUNT));

    quickMoveInventorySlot(menu, player, INVENTORY_SLOT);

    assertBinderSlotsHoldOneCardEach(helper, menu, CARD_COUNT);
  }

  public static void testQuickMoveCardsNeverStackInBinderSlot(GameTestHelper helper) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    CardBinderMenu menu = openBinder(player);
    menu.getSlot(0).set(createCards(1));
    player.getInventory().setItem(INVENTORY_SLOT, createCards(CARD_COUNT - 1));

    quickMoveInventorySlot(menu, player, INVENTORY_SLOT);

    assertBinderSlotsHoldOneCardEach(helper, menu, CARD_COUNT);
  }

  public static void testQuickMoveIgnoresNonCards(GameTestHelper helper) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    CardBinderMenu menu = openBinder(player);
    player
        .getInventory()
        .setItem(INVENTORY_SLOT, new ItemStack(net.minecraft.world.item.Items.BONE, 16));

    quickMoveInventorySlot(menu, player, INVENTORY_SLOT);

    GameTestHelpers.assertTrue(
        helper,
        "Shift-click should keep items other than capture cards in the inventory, but found "
            + player.getInventory().getItem(INVENTORY_SLOT),
        player.getInventory().getItem(INVENTORY_SLOT).getCount() == 16);
    assertBinderSlotsHoldOneCardEach(helper, menu, 0);
  }

  public static void testQuickMoveTakesCardOut(GameTestHelper helper) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    CardBinderMenu menu = openBinder(player);
    menu.getSlot(0).set(createCards(1));

    menu.clicked(0, LEFT_MOUSE_BUTTON, ClickType.QUICK_MOVE, player);

    assertBinderSlotsHoldOneCardEach(helper, menu, 0);
    GameTestHelpers.assertTrue(
        helper,
        "Shift-click on a binder card should move it into the player inventory, but found "
            + player.getInventory().countItem(MobCaptureCardItem.getMobCaptureCardItem()),
        player.getInventory().countItem(MobCaptureCardItem.getMobCaptureCardItem()) == 1);
  }

  public static void testOpenBinderCannotBeMoved(GameTestHelper helper) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    CardBinderMenu menu = openBinder(player);
    ItemStack binderStack = player.getInventory().getItem(BINDER_HOTBAR_SLOT);
    int binderMenuSlot =
        GameTestHelpers.menuSlotIndex(menu, player.getInventory(), BINDER_HOTBAR_SLOT);

    menu.clicked(binderMenuSlot, LEFT_MOUSE_BUTTON, ClickType.QUICK_MOVE, player);
    menu.clicked(binderMenuSlot, LEFT_MOUSE_BUTTON, ClickType.PICKUP, player);
    menu.clicked(0, BINDER_HOTBAR_SLOT, ClickType.SWAP, player);

    GameTestHelpers.assertTrue(
        helper,
        "The open binder should stay in its hotbar slot, but found "
            + player.getInventory().getItem(BINDER_HOTBAR_SLOT)
            + " while carrying "
            + menu.getCarried(),
        player.getInventory().getItem(BINDER_HOTBAR_SLOT) == binderStack
            && menu.getCarried().isEmpty());
    assertBinderSlotsHoldOneCardEach(helper, menu, 0);
  }

  private static void assertBinderSlotsHoldOneCardEach(
      GameTestHelper helper, CardBinderMenu menu, int expectedCards) {
    for (int i = 0; i < CardBinderMenu.CONTAINER_SIZE; i++) {
      int expectedCount = i < expectedCards ? 1 : 0;
      if (menu.getSlot(i).getItem().getCount() != expectedCount) {
        helper.fail(
            "Binder slot "
                + i
                + " should hold "
                + expectedCount
                + " card(s), but holds "
                + menu.getSlot(i).getItem());
      }
    }
  }

  private static ItemStack createCards(int count) {
    return new ItemStack(MobCaptureCardItem.getMobCaptureCardItem(), count);
  }

  private static void quickMoveInventorySlot(
      CardBinderMenu menu, Player player, int inventorySlotIndex) {
    Inventory inventory = player.getInventory();
    menu.clicked(
        GameTestHelpers.menuSlotIndex(menu, inventory, inventorySlotIndex),
        LEFT_MOUSE_BUTTON,
        ClickType.QUICK_MOVE,
        player);
  }

  private static CardBinderMenu openBinder(Player player) {
    ItemStack binderStack = new ItemStack(Items.CARD_BINDER);
    player.getInventory().setItem(BINDER_HOTBAR_SLOT, binderStack);
    player.getInventory().setSelectedSlot(BINDER_HOTBAR_SLOT);
    return new CardBinderMenu(
        CardBinderMenu.MENU_TYPE_SUPPLIER.get(), 0, player.getInventory(), binderStack);
  }
}
