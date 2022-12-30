/**
 * Copyright 2022 Markus Bordihn
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntityData;
import de.markusbordihn.easymobfarm.item.CapturedMob;
import de.markusbordihn.easymobfarm.item.CapturedMobVirtual;
import de.markusbordihn.easymobfarm.menu.slots.CapturedMobSlot;
import de.markusbordihn.easymobfarm.menu.slots.ExperienceSlot;
import de.markusbordihn.easymobfarm.menu.slots.LockedResultSlot;
import de.markusbordihn.easymobfarm.menu.slots.WeaponSlot;

public class MobFarmMenu extends AbstractContainerMenu {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  // Define Slot index for easier access
  public static final int CAPTURED_MOB_SLOT = 0;
  public static final int RESULT_1_SLOT = 1;
  public static final int RESULT_2_SLOT = 2;
  public static final int RESULT_3_SLOT = 3;
  public static final int RESULT_4_SLOT = 4;
  public static final int RESULT_5_SLOT = 5;
  public static final int WEAPON_SLOT = 6;
  public static final int EXPERIENCE_SLOT = 7;

  public static final int PLAYER_SLOT_START = 9;
  public static final int PLAYER_INVENTORY_SLOT_START = PLAYER_SLOT_START;
  public static final int PLAYER_SLOT_STOP = 3 * 9 + PLAYER_INVENTORY_SLOT_START + 8;

  // Storing slot position statically to able to access them from other UI parts.
  public static final int CAPTURED_MOB_SLOT_LEFT = 81;
  public static final int CAPTURED_MOB_SLOT_TOP = 51;
  public static final int RESULT_SLOTS_LEFT = 44;
  public static final int RESULT_SLOTS_TOP = 100;
  public static final int WEAPON_SLOT_LEFT = 131;
  public static final int WEAPON_SLOT_TOP = 51;
  public static final int EXPERIENCE_SLOT_LEFT = 152;
  public static final int EXPERIENCE_SLOT_TOP = 100;

  // Defining basic layout options
  private static int containerSize = 8;
  private static int slotSize = 18;
  private static int slotSpacing = 8;

  // Define containers
  private final Container container;
  private final ContainerData data;

  // Define cache
  private ItemStack mobFarmCapturedMob = ItemStack.EMPTY;
  private ItemStack mobFarmExperienceItem = ItemStack.EMPTY;
  private ItemStack mobFarmWeaponItem = ItemStack.EMPTY;
  private String mobFarmName = "- unknown -";
  private String mobFarmTotalTimeText = "";
  private String mobFarmType = "";
  private int mobFarmProgress;
  private int mobFarmProgressImage;
  private int mobFarmRemainingTime;
  private int mobFarmStatus;
  private int mobFarmTotalTime;

  // Misc
  protected final Level level;

  public MobFarmMenu(int windowIdIn, Inventory inventory) {
    this(windowIdIn, inventory, new SimpleContainer(containerSize),
        new SimpleContainerData(MobFarmBlockEntityData.DATA_SIZE),
        ModMenuTypes.MOB_FARM_MENU.get());
  }

  public MobFarmMenu(final int windowId, final Inventory playerInventory, final Container container,
      final ContainerData containerData) {
    this(windowId, playerInventory, container, containerData, ModMenuTypes.MOB_FARM_MENU.get());
  }

  public MobFarmMenu(final int windowId, final Inventory playerInventory, final Container container,
      final ContainerData containerData, MenuType<?> menuType) {
    super(menuType, windowId);

    // Make sure the passed container matched the expected sizes
    checkContainerSize(container, containerSize);
    checkContainerDataCount(containerData, MobFarmBlockEntityData.DATA_SIZE);
    this.container = container;
    this.data = containerData;
    this.level = playerInventory.player.level;

    // Define slots and position on UI (note: order sensitive)
    this.addSlot(new CapturedMobSlot(container, CAPTURED_MOB_SLOT, CAPTURED_MOB_SLOT_LEFT,
        CAPTURED_MOB_SLOT_TOP, this));
    this.addSlot(
        new LockedResultSlot(container, RESULT_1_SLOT, RESULT_SLOTS_LEFT, RESULT_SLOTS_TOP));
    this.addSlot(new LockedResultSlot(container, RESULT_2_SLOT, RESULT_SLOTS_LEFT + 1 * 18,
        RESULT_SLOTS_TOP));
    this.addSlot(new LockedResultSlot(container, RESULT_3_SLOT, RESULT_SLOTS_LEFT + 2 * 18,
        RESULT_SLOTS_TOP));
    this.addSlot(new LockedResultSlot(container, RESULT_4_SLOT, RESULT_SLOTS_LEFT + 3 * 18,
        RESULT_SLOTS_TOP));
    this.addSlot(new LockedResultSlot(container, RESULT_5_SLOT, RESULT_SLOTS_LEFT + 4 * 18,
        RESULT_SLOTS_TOP));
    this.addSlot(new WeaponSlot(container, WEAPON_SLOT, WEAPON_SLOT_LEFT, WEAPON_SLOT_TOP));
    this.addSlot(
        new ExperienceSlot(container, EXPERIENCE_SLOT, EXPERIENCE_SLOT_LEFT, EXPERIENCE_SLOT_TOP));

    // Player Inventory Slots
    int playerInventoryStartPositionY = 140;
    for (int inventoryRow = 0; inventoryRow < 3; ++inventoryRow) {
      for (int inventoryColumn = 0; inventoryColumn < 9; ++inventoryColumn) {
        this.addSlot(new Slot(playerInventory,
            inventoryColumn + inventoryRow * 9 + PLAYER_INVENTORY_SLOT_START,
            slotSpacing + inventoryColumn * slotSize,
            playerInventoryStartPositionY + inventoryRow * slotSize));
      }
    }

    // Player Hotbar
    int hotbarStartPositionY = 198;
    for (int playerInventorySlot = 0; playerInventorySlot < 9; ++playerInventorySlot) {
      this.addSlot(new Slot(playerInventory, playerInventorySlot,
          slotSpacing + playerInventorySlot * slotSize, hotbarStartPositionY));
    }

    // Define container data
    this.addDataSlots(containerData);
  }

  public void updateMobFarmDataCache() {
    // Get additional data if captured mob has changed.
    ItemStack currentItemStack = this.container.getItem(CAPTURED_MOB_SLOT);
    if (!this.mobFarmCapturedMob.is(currentItemStack.getItem())) {
      if (currentItemStack.isEmpty()) {
        this.mobFarmName = "- unknown -";
        this.mobFarmType = "";
      } else if (currentItemStack.getItem() instanceof CapturedMob) {
        this.mobFarmName = CapturedMob.getCapturedMob(currentItemStack);
        this.mobFarmType = CapturedMob.getCapturedMobType(currentItemStack);
      } else if (CapturedMobVirtual.isSupported(currentItemStack)) {
        this.mobFarmName = CapturedMobVirtual.getCapturedMob(currentItemStack);
        this.mobFarmType = CapturedMobVirtual.getCapturedMobType(currentItemStack);
      }
      this.mobFarmCapturedMob = currentItemStack;
    }

    // Check if there is an experience or weapon item.
    this.mobFarmExperienceItem = this.container.getItem(EXPERIENCE_SLOT);
    this.mobFarmWeaponItem = this.container.getItem(WEAPON_SLOT);

    // Process other data if there is an captured mob item.
    this.mobFarmProgress = this.data.get(MobFarmBlockEntityData.FARM_PROGRESS_DATA);
    this.mobFarmTotalTime = this.data.get(MobFarmBlockEntityData.FARM_TOTAL_TIME_DATA);
    this.mobFarmTotalTimeText = this.mobFarmTotalTime / 20 + "s";
    this.mobFarmStatus = this.data.get(MobFarmBlockEntityData.FARM_STATUS_DATA);
    if (!this.mobFarmCapturedMob.isEmpty()) {
      int adaptiveTotalPixelHeight = this.mobFarmTotalTime > 200 ? 15 : 16;
      this.mobFarmProgressImage = this.mobFarmTotalTime != 0 && this.mobFarmProgress != 0
          ? this.mobFarmProgress * adaptiveTotalPixelHeight / this.mobFarmTotalTime
          : 0;
      this.mobFarmRemainingTime = (this.mobFarmTotalTime - this.mobFarmProgress) / 20;
    }
  }

  public int getMobFarmProgress() {
    return this.mobFarmProgress;
  }

  public int getMobFarmTotalTime() {
    return this.mobFarmTotalTime;
  }

  public String getMobFarmTotalTimeText() {
    return this.mobFarmTotalTimeText;
  }

  public int getMobFarmStatus() {
    return this.mobFarmStatus;
  }

  public int getMobFarmProgressImage() {
    return this.mobFarmProgressImage;
  }

  public int getMobFarmRemainingTime() {
    return this.mobFarmRemainingTime;
  }

  public String getMobFarmName() {
    return this.mobFarmName;
  }

  public String getMobFarmType() {
    return this.mobFarmType;
  }

  public boolean hasMobFarmCapturedMob() {
    return this.mobFarmCapturedMob != null && !this.mobFarmCapturedMob.isEmpty();
  }

  public boolean hasMobFarmExperienceItem() {
    return this.mobFarmExperienceItem != null && !this.mobFarmExperienceItem.isEmpty();
  }

  public boolean hasMobFarmWeaponItem() {
    return this.mobFarmWeaponItem != null && !this.mobFarmWeaponItem.isEmpty();
  }

  public boolean stillValid(Player player) {
    return this.container.stillValid(player);
  }

  public boolean mayPlaceCapturedMob(ItemStack itemStack) {
    if (itemStack.getItem() instanceof CapturedMob) {
      return CapturedMob.hasCapturedMob(itemStack);
    } else if (CapturedMobVirtual.isSupported(itemStack)) {
      return CapturedMobVirtual.hasCapturedMob(itemStack);
    }
    return false;
  }

  public boolean mayPlaceCapturedMobType(String mobType) {
    return !mobType.isBlank();
  }

  @Override
  public ItemStack quickMoveStack(Player player, int slotIndex) {
    Slot slot = this.slots.get(slotIndex);
    if (!slot.hasItem()) {
      return ItemStack.EMPTY;
    }

    // Get itemStack we need to handle.
    ItemStack itemStack = slot.getItem();

    // Handle CaptureMob items for moving in and out.
    if (itemStack.getItem() instanceof CapturedMob) {
      if (slotIndex == CAPTURED_MOB_SLOT) {
        if (!this.moveItemStackTo(itemStack, 6, 42, false)) {
          return ItemStack.EMPTY;
        }
      } else if (slotIndex >= 6 && !this.moveItemStackTo(itemStack, 0, 1, false)) {
        return ItemStack.EMPTY;
      }
    }

    // Move result items to the player inventory.
    else if ((slotIndex == RESULT_1_SLOT || slotIndex == RESULT_2_SLOT || slotIndex == RESULT_3_SLOT
        || slotIndex == RESULT_4_SLOT || slotIndex == RESULT_5_SLOT)
        && this.moveItemStackTo(itemStack, 6, 42, false)) {
      return ItemStack.EMPTY;
    }

    // Store changes if itemStack is not empty.
    if (itemStack.isEmpty()) {
      slot.set(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }

    slot.onTake(player, itemStack);

    return ItemStack.EMPTY;
  }

}
