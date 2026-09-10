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

package de.markusbordihn.easymobfarm.menu;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.loot.LootPreviewManager;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmDataEntry;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlot;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlots;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.data.mobfarm.RedstoneMode;
import de.markusbordihn.easymobfarm.item.upgrade.slot.BigSlotUpgradeItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.SmallSlotUpgradeItem;
import de.markusbordihn.easymobfarm.menu.slots.CapturedMobSlot;
import de.markusbordihn.easymobfarm.menu.slots.EnhancementSlot;
import de.markusbordihn.easymobfarm.menu.slots.FilterSlot;
import de.markusbordihn.easymobfarm.menu.slots.OutputSlot;
import de.markusbordihn.easymobfarm.menu.slots.SlotUpgradeSlot;
import de.markusbordihn.easymobfarm.network.message.client.SyncLootPreviewMessage;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmMenu extends AbstractContainerMenu {

  public static final String ID = "mob_farm_menu";
  public static final int CAPTURED_MOB_SLOT_X = 121;
  public static final int CAPTURED_MOB_SLOT_Y = 39;
  public static final int UPGRADE_SLOT_X = 156;
  public static final int UPGRADE_SLOT_Y = 32;
  public static final int FILTER_SLOT_X = 219;
  public static final int FILTER_SLOT_Y = 14;
  public static final int SLOT_UPGRADE_SLOT_X = 20;
  public static final int SLOT_UPGRADE_SLOT_Y = 89;
  public static final int RESULT_SLOT_X = 48;
  public static final int RESULT_SLOT_Y = 89;
  public static final int PLAYER_INVENTORY_SLOT_X = 48;
  public static final int PLAYER_INVENTORY_SLOT_Y = 157;
  public static final int PLAYER_HOTBAR_SLOT_X = 48;
  public static final int PLAYER_HOTBAR_SLOT_Y = 215;
  public static final int CONTAINER_SIZE = 52;
  public static final int CONTAINER_DATA_SIZE = MobFarmDataEntry.getLastSlotIndex() + 1;
  public static final int MIN_NUMBER_OF_OUTPUT_SLOTS = 6;
  public static final int MAX_NUMBER_OF_OUTPUT_SLOTS = 27;
  public static final int TOGGLE_REDSTONE_MODE_BUTTON = 0;

  private static final int CAPTURED_MOB_SLOT_COUNT = 1;
  private static final int MOB_FARM_INPUT_SLOT_COUNT =
      CAPTURED_MOB_SLOT_COUNT
          + MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.size()
          + MobFarmSlots.FILTER_ITEM_SLOTS.size()
          + MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS.size();
  private static final int MOB_FARM_SLOT_COUNT =
      MOB_FARM_INPUT_SLOT_COUNT + MobFarmSlots.RESULT_SLOTS.size();

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private final Container container;
  private final ContainerData data;
  private final Inventory playerInventory;
  private EntityType<?> lastSentEntityType = null;

  public MobFarmMenu(
      final MenuType<?> menuType, final int containerId, final Inventory playerInventory) {
    this(
        menuType,
        containerId,
        playerInventory,
        new SimpleContainer(CONTAINER_SIZE),
        new SimpleContainerData(CONTAINER_DATA_SIZE));
  }

  public MobFarmMenu(
      final MenuType<?> menuType,
      final int containerId,
      final Inventory playerInventory,
      final Container container,
      final ContainerData containerData) {
    super(menuType, containerId);

    checkContainerSize(container, CONTAINER_SIZE);
    checkContainerDataCount(containerData, CONTAINER_DATA_SIZE);

    this.container = container;
    this.data = containerData;
    this.playerInventory = playerInventory;
    this.defineMobFarmSlots();
    this.defineUpgradeSlots();
    this.defineFilterSlots();
    this.defineSlotUpgradeSlots();
    this.defineOuputSlots();
    this.definePlayerInventorySlots();
    this.definePlayerHotbarSlots();

    if (this.container instanceof MobFarmBlockEntity mobFarmBlockEntity) {
      // Update block position
      BlockPos blockPos = mobFarmBlockEntity.getBlockPos();
      if (getMobFarmBlockPos() == null || !getMobFarmBlockPos().equals(blockPos)) {
        log.debug("Update Block pose {} for {}", blockPos, mobFarmBlockEntity);
        setMobFarmBlockPos(blockPos);
      }

      // Update number of output slots
      if (updateNumberOfOutputSlots()) {
        log.debug(
            "Update number of output slots {} for {}",
            getMobFarmNumberOfOutputSlots(),
            mobFarmBlockEntity);
      }
    }

    // Define container data
    this.addDataSlots(containerData);
  }

  public BlockPos getMobFarmBlockPos() {
    return new BlockPos(
        this.data.get(MobFarmDataEntry.BLOCK_POS_X),
        this.data.get(MobFarmDataEntry.BLOCK_POS_Y),
        this.data.get(MobFarmDataEntry.BLOCK_POS_Z));
  }

  public void setMobFarmBlockPos(final BlockPos blockPos) {
    this.data.set(MobFarmDataEntry.BLOCK_POS_X, blockPos.getX());
    this.data.set(MobFarmDataEntry.BLOCK_POS_Y, blockPos.getY());
    this.data.set(MobFarmDataEntry.BLOCK_POS_Z, blockPos.getZ());
  }

  public int getMobFarmNumberOfOutputSlots() {
    return this.data.get(MobFarmDataEntry.NUMBER_OF_OUTPUT_SLOTS);
  }

  public void setMobFarmNumberOfOutputSlots(final int numberOfOutputSlots) {
    this.data.set(MobFarmDataEntry.NUMBER_OF_OUTPUT_SLOTS, numberOfOutputSlots);
  }

  public int getMobFarmProgress() {
    return this.data.get(MobFarmDataEntry.FARM_PROGRESS);
  }

  public int getMobFarmProgressionSpeed() {
    return this.data.get(MobFarmDataEntry.FARM_PROGRESSION_SPEED);
  }

  public int getMobFarmProgressionSpeedBonus() {
    return this.data.get(MobFarmDataEntry.FARM_PROGRESSION_SPEED_BONUS);
  }

  public int getMobFarmStatus() {
    return this.data.get(MobFarmDataEntry.FARM_STATUS);
  }

  public int getMobFarmTierLevel() {
    return this.data.get(MobFarmDataEntry.FARM_TIER_LEVEL);
  }

  public MobFarmType getMobFarmType() {
    int mobFarmTypeIndex = this.data.get(MobFarmDataEntry.FARM_TYPE);
    if (mobFarmTypeIndex < 0 || mobFarmTypeIndex >= MobFarmType.values().length) {
      return null;
    }

    return MobFarmType.values()[mobFarmTypeIndex];
  }

  public int getCapturedMobExperience() {
    return this.data.get(MobFarmDataEntry.CAPTURED_MOB_EXPERIENCE);
  }

  public int getBufferSize() {
    return this.data.get(MobFarmDataEntry.BUFFER_SIZE);
  }

  public int getBufferMaxSize() {
    return this.data.get(MobFarmDataEntry.BUFFER_MAX_SIZE);
  }

  public RedstoneMode getRedstoneMode() {
    return RedstoneMode.byOrdinal(this.data.get(MobFarmDataEntry.REDSTONE_MODE));
  }

  @Override
  public boolean clickMenuButton(final Player player, final int buttonId) {
    if (buttonId != TOGGLE_REDSTONE_MODE_BUTTON
        || !this.stillValid(player)
        || !(this.container instanceof MobFarmBlockEntity mobFarmBlockEntity)) {
      return false;
    }

    mobFarmBlockEntity.setRedstoneMode(mobFarmBlockEntity.getRedstoneMode().next());
    this.broadcastChanges();
    return true;
  }

  private void defineMobFarmSlots() {
    this.addSlot(
        new CapturedMobSlot(
            this.container,
            MobFarmSlot.CAPTURED_MOB.index(),
            CAPTURED_MOB_SLOT_X,
            CAPTURED_MOB_SLOT_Y));
  }

  private void defineUpgradeSlots() {
    int slotId = 0;
    for (int row = 0; row < 2; ++row) {
      for (int column = 0; column < 2; ++column) {
        this.addSlot(
            new EnhancementSlot(
                this.container,
                MobFarmSlots.ENHANCEMENT_ITEM_SLOTS.get(slotId).index(),
                UPGRADE_SLOT_X + column * 18,
                UPGRADE_SLOT_Y + row * 18));
        slotId++;
      }
    }
  }

  private void defineFilterSlots() {
    int slotId = 0;
    for (int row = 0; row < MobFarmSlots.FILTER_ITEM_SLOTS.size(); ++row) {
      this.addSlot(
          new FilterSlot(
              this.container,
              MobFarmSlots.FILTER_ITEM_SLOTS.get(slotId).index(),
              FILTER_SLOT_X,
              FILTER_SLOT_Y + row * 18));
      slotId++;
    }
  }

  private void defineSlotUpgradeSlots() {
    int slotId = 0;
    for (int row = 0; row < 3; ++row) {
      this.addSlot(
          new SlotUpgradeSlot(
              this,
              this.container,
              MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS.get(slotId).index(),
              SLOT_UPGRADE_SLOT_X,
              SLOT_UPGRADE_SLOT_Y + row * 18));
      slotId++;
    }
  }

  private void defineOuputSlots() {
    int slotId = 0;
    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 9; ++column) {
        this.addSlot(
            new OutputSlot(
                this.container,
                MobFarmSlots.RESULT_SLOTS.get(slotId).index(),
                RESULT_SLOT_X + column * 18,
                RESULT_SLOT_Y + row * 18));
        slotId++;
      }
    }
  }

  private void definePlayerInventorySlots() {
    for (int row = 0; row < 3; ++row) {
      for (int column = 0; column < 9; ++column) {
        this.addSlot(
            new Slot(
                this.playerInventory,
                column + row * 9 + 9,
                PLAYER_INVENTORY_SLOT_X + column * 18,
                PLAYER_INVENTORY_SLOT_Y + row * 18));
      }
    }
  }

  private void definePlayerHotbarSlots() {
    for (int column = 0; column < 9; ++column) {
      this.addSlot(
          new Slot(
              this.playerInventory,
              column,
              PLAYER_HOTBAR_SLOT_X + column * 18,
              PLAYER_HOTBAR_SLOT_Y));
    }
  }

  private boolean updateNumberOfOutputSlots() {
    int numberOfOutputSlots = MIN_NUMBER_OF_OUTPUT_SLOTS;
    for (Slot slot : this.slots) {
      if (slot instanceof SlotUpgradeSlot && slot.hasItem() && !slot.getItem().isEmpty()) {
        if (slot.getItem().getItem() instanceof SmallSlotUpgradeItem smallSlotUpgradeItem) {
          numberOfOutputSlots += smallSlotUpgradeItem.numberOfUpgradeSlots();
        } else if (slot.getItem().getItem() instanceof BigSlotUpgradeItem bigSlotUpgradeItem) {
          numberOfOutputSlots += bigSlotUpgradeItem.numberOfUpgradeSlots();
        }
      }
    }
    int currentNumberOfOutputSlots = this.getMobFarmNumberOfOutputSlots();
    int newNumberOfOutputSlots =
        Math.min(
            Math.max(MIN_NUMBER_OF_OUTPUT_SLOTS, numberOfOutputSlots), MAX_NUMBER_OF_OUTPUT_SLOTS);
    if (currentNumberOfOutputSlots != newNumberOfOutputSlots) {
      this.setMobFarmNumberOfOutputSlots(newNumberOfOutputSlots);
      this.adjustOutputSlots(newNumberOfOutputSlots);
      return true;
    }
    return false;
  }

  private void adjustOutputSlots(final int maxNumberOfOutputSlots) {
    int currentNumberOfOutputSlots = 0;
    for (Slot slot : this.slots) {
      if (slot instanceof OutputSlot outputSlot) {
        outputSlot.setActive(currentNumberOfOutputSlots++ < maxNumberOfOutputSlots);
      }
    }
  }

  public void slotUpgradeChanged(final SlotUpgradeSlot slot) {
    this.updateNumberOfOutputSlots();
  }

  @Override
  public void broadcastChanges() {
    super.broadcastChanges();
    if (this.container instanceof MobFarmBlockEntity mobFarmBlockEntity
        && this.playerInventory.player instanceof ServerPlayer serverPlayer) {
      syncLootPreviewIfChanged(mobFarmBlockEntity, serverPlayer);
    }
  }

  private void syncLootPreviewIfChanged(
      final MobFarmBlockEntity mobFarmBlockEntity, final ServerPlayer serverPlayer) {
    ItemStack capturedMob = this.container.getItem(MobFarmSlot.CAPTURED_MOB.index());
    MobCaptureData captureData = MobCaptureManager.getMobCaptureData(capturedMob);
    EntityType<?> currentEntityType = captureData != null ? captureData.entityType() : null;
    if (currentEntityType == lastSentEntityType) {
      return;
    }
    lastSentEntityType = currentEntityType;
    if (currentEntityType == null || mobFarmBlockEntity.getLevel() == null) {
      return;
    }
    List<ItemStack> preview =
        LootPreviewManager.getOrCompute(captureData, mobFarmBlockEntity.getLevel());
    SyncLootPreviewMessage.sendToPlayer(
        serverPlayer, mobFarmBlockEntity.getBlockPos(), currentEntityType, preview);
  }

  @Override
  public boolean stillValid(final Player player) {
    if (this.container instanceof MobFarmBlockEntity mobFarmBlockEntity) {
      return mobFarmBlockEntity.stillValid(player);
    }

    return player.isAlive();
  }

  @Override
  public ItemStack quickMoveStack(final Player player, final int slotIndex) {
    Slot slot = this.slots.get(slotIndex);
    if (!slot.hasItem()) {
      return ItemStack.EMPTY;
    }

    ItemStack itemStack = slot.getItem();
    ItemStack itemStackCopy = itemStack.copy();

    // Handle moving items between different slot groups
    if (slot.container == this.container) {
      // Move from Mob Farm (container) to Player Inventory or Hotbar
      if (!this.moveItemStackTo(itemStack, MOB_FARM_SLOT_COUNT, this.slots.size(), true)) {
        return ItemStack.EMPTY;
      }
    } else if (slot.container == this.playerInventory) {
      // Prevent moving items to Output Slots
      for (Slot targetSlot : this.slots.subList(0, MOB_FARM_INPUT_SLOT_COUNT)) {
        // Skip Output Slots and ensure the target slot is empty or below max stack size
        if (!(targetSlot instanceof OutputSlot)
            && !targetSlot.hasItem()
            && targetSlot.mayPlace(itemStack)) {
          ItemStack singleItem = itemStack.split(1);
          targetSlot.set(singleItem);
          targetSlot.setChanged();
          break;
        }
      }
    }

    // Handle stack updates
    if (itemStack.isEmpty()) {
      slot.set(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }

    if (itemStack.getCount() == itemStackCopy.getCount()) {
      return ItemStack.EMPTY;
    }

    slot.onTake(player, itemStack);
    return itemStackCopy;
  }
}
