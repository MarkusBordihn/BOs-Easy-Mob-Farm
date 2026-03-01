/*
 * Copyright 2025 Markus Bordihn
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

package de.markusbordihn.easymobfarm.menu;

import de.markusbordihn.easymobfarm.item.cardbinder.CardBinderItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import java.util.function.Supplier;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CardBinderMenu extends AbstractContainerMenu {

  public static final String ID = "card_binder_menu";
  public static final int CONTAINER_SIZE = CardBinderItem.CONTAINER_SIZE;
  public static final int CARDS_PER_PAGE = 25;
  public static final int CARD_COLS = 5;
  private static final int GRID_START_X = 22;
  private static final int GRID_START_Y = 24;
  private static final int SLOT_SPACING = 20;
  private static final int PLAYER_INV_X = 59;
  private static final int PLAYER_INV_Y = 168;
  private static final int PLAYER_HOTBAR_Y = 226;
  public static MenuType<?> MENU_TYPE = null;
  public static Supplier<MenuType<?>> MENU_TYPE_SUPPLIER = () -> MENU_TYPE;
  private final Container cardContainer;
  private final ItemStack binderStack;
  private int lockedSlotIndex = -1;
  private int currentPage = 0;

  public CardBinderMenu(
      final MenuType<?> menuType,
      final int containerId,
      final Inventory playerInventory,
      final ItemStack binderStack) {
    this(
        menuType, containerId, playerInventory, CardBinderItem.loadCards(binderStack), binderStack);
  }

  public CardBinderMenu(
      final MenuType<?> menuType, final int containerId, final Inventory playerInventory) {
    this(
        menuType,
        containerId,
        playerInventory,
        new SimpleContainer(CONTAINER_SIZE),
        ItemStack.EMPTY);
  }

  public CardBinderMenu(
      final MenuType<?> menuType,
      final int containerId,
      final Inventory playerInventory,
      final Container container,
      final ItemStack binderStack) {
    super(menuType, containerId);
    this.cardContainer = container;
    this.binderStack = binderStack;

    for (int i = 0; i < CONTAINER_SIZE; i++) {
      int posOnPage = i % CARDS_PER_PAGE;
      this.addSlot(
          new CardSlot(
              this,
              container,
              i,
              GRID_START_X + (posOnPage % CARD_COLS) * SLOT_SPACING,
              GRID_START_Y + (posOnPage / CARD_COLS) * SLOT_SPACING));
    }

    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 9; col++) {
        this.addSlot(
            new Slot(
                playerInventory,
                col + row * 9 + 9,
                PLAYER_INV_X + col * 18,
                PLAYER_INV_Y + row * 18));
      }
    }

    for (int col = 0; col < 9; col++) {
      if (playerInventory.getItem(col) == binderStack) {
        this.lockedSlotIndex = CONTAINER_SIZE + 27 + col;
        this.addSlot(
            new Slot(playerInventory, col, PLAYER_INV_X + col * 18, PLAYER_HOTBAR_Y) {
              @Override
              public boolean mayPickup(Player player) {
                return false;
              }

              @Override
              public boolean mayPlace(ItemStack stack) {
                return false;
              }
            });
      } else {
        this.addSlot(new Slot(playerInventory, col, PLAYER_INV_X + col * 18, PLAYER_HOTBAR_Y));
      }
    }
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int page) {
    this.currentPage = Math.max(0, Math.min(page, getMaxPage()));
  }

  public int getMaxPage() {
    return Math.max(0, (CONTAINER_SIZE - 1) / CARDS_PER_PAGE);
  }

  @Override
  public boolean stillValid(Player player) {
    if (binderStack.isEmpty()) {
      return true;
    }
    return player.getMainHandItem() == binderStack || player.getOffhandItem() == binderStack;
  }

  @Override
  public void removed(Player player) {
    super.removed(player);
    if (!binderStack.isEmpty()) {
      CardBinderItem.saveCards(binderStack, (SimpleContainer) cardContainer);
    }
  }

  @Override
  public ItemStack quickMoveStack(Player player, int index) {
    if (index == lockedSlotIndex) {
      return ItemStack.EMPTY;
    }
    Slot slot = this.slots.get(index);
    if (!slot.hasItem()) {
      return ItemStack.EMPTY;
    }
    ItemStack slotStack = slot.getItem();
    ItemStack result = slotStack.copy();
    if (index < CONTAINER_SIZE) {
      if (!this.moveItemStackTo(slotStack, CONTAINER_SIZE, this.slots.size(), true)) {
        return ItemStack.EMPTY;
      }
    } else if (slotStack.getItem() instanceof MobCaptureCardItem) {
      if (!this.moveItemStackTo(slotStack, 0, CONTAINER_SIZE, false)) {
        return ItemStack.EMPTY;
      }
    } else {
      return ItemStack.EMPTY;
    }
    if (slotStack.getCount() == result.getCount()) {
      return ItemStack.EMPTY;
    }
    if (slotStack.isEmpty()) {
      slot.set(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }
    return result;
  }

  private static class CardSlot extends Slot {
    private final CardBinderMenu menu;
    private final int slotIndex;

    CardSlot(CardBinderMenu menu, Container container, int index, int x, int y) {
      super(container, index, x, y);
      this.menu = menu;
      this.slotIndex = index;
    }

    @Override
    public boolean isActive() {
      int start = menu.getCurrentPage() * CARDS_PER_PAGE;
      return slotIndex >= start && slotIndex < start + CARDS_PER_PAGE;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
      return itemStack.getItem() instanceof MobCaptureCardItem;
    }

    @Override
    public int getMaxStackSize() {
      return 1;
    }
  }
}
