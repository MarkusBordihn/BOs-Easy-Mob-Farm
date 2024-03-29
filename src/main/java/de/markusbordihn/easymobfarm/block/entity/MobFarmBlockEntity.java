/*
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

package de.markusbordihn.easymobfarm.block.entity;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.data.RedstoneMode;
import de.markusbordihn.easymobfarm.item.CapturedMob;
import de.markusbordihn.easymobfarm.item.CapturedMobVirtual;
import de.markusbordihn.easymobfarm.loot.LootManager;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ExperienceBottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
public class MobFarmBlockEntity extends MobFarmBlockEntityData implements WorldlyContainer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected static final CommonConfig.Config COMMON = CommonConfig.COMMON;
  private static final int DEFAULT_FARM_PROCESSING_TIME = 6000;
  protected final Random random = new Random();
  LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
      SidedInvWrapper.create(this, Direction.DOWN, Direction.SOUTH);

  public MobFarmBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(null, blockPos, blockState);
  }

  public MobFarmBlockEntity(
      BlockEntityType<?> blockEntity, BlockPos blockPos, BlockState blockState) {
    super(blockEntity, blockPos, blockState);
  }

  @SuppressWarnings("java:S1172")
  public static void serverTick(
      Level level, BlockPos blockPos, BlockState blockState, MobFarmBlockEntity blockEntity) {

    // Check if there is something to progress...
    ItemStack capturedMob = blockEntity.items.get(MobFarmMenu.CAPTURED_MOB_SLOT);
    if (capturedMob.isEmpty() || !blockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      blockEntity.farmProgress = 0;
      blockEntity.farmStatus = FARM_STATUS_WAITING;
      return;
    }

    // Check redstone mode and power state
    if ((blockEntity.getRedstoneMode() == RedstoneMode.ON
            && !blockState.getValue(MobFarmBlock.POWERED))
        || (blockEntity.getRedstoneMode() == RedstoneMode.OFF
            && blockState.getValue(MobFarmBlock.POWERED))) {
      blockEntity.farmProgress = 0;
      blockEntity.farmStatus = FARM_STATUS_DISABLED;
      return;
    }

    // Make sure we have space to store the result items.
    ItemStack resultItem1 = blockEntity.items.get(MobFarmMenu.RESULT_1_SLOT);
    ItemStack resultItem2 = blockEntity.items.get(MobFarmMenu.RESULT_2_SLOT);
    ItemStack resultItem3 = blockEntity.items.get(MobFarmMenu.RESULT_3_SLOT);
    ItemStack resultItem4 = blockEntity.items.get(MobFarmMenu.RESULT_4_SLOT);
    ItemStack resultItem5 = blockEntity.items.get(MobFarmMenu.RESULT_5_SLOT);
    if (resultItem1.getCount() >= resultItem1.getMaxStackSize()
        && resultItem2.getCount() >= resultItem2.getMaxStackSize()
        && resultItem3.getCount() >= resultItem3.getMaxStackSize()
        && resultItem4.getCount() >= resultItem4.getMaxStackSize()
        && resultItem5.getCount() >= resultItem5.getMaxStackSize()) {
      if (blockEntity.farmStatus != FARM_STATUS_FULL) {
        blockEntity.farmStatus = FARM_STATUS_FULL;
      }
      return;
    }

    // Processing mob farm
    if (blockEntity.farmProgress >= blockEntity.farmTotalTime) {
      if (capturedMob.getItem() instanceof CapturedMob
          || CapturedMobVirtual.isSupported(capturedMob)) {
        ItemStack weaponItem = blockEntity.items.get(MobFarmMenu.WEAPON_SLOT);
        ItemStack experienceItem = blockEntity.items.get(MobFarmMenu.EXPERIENCE_SLOT);
        blockEntity.processResult(capturedMob, weaponItem, experienceItem, blockEntity);
        blockEntity.processAdditionalEffects(level, blockPos, blockEntity, capturedMob);
      }
      blockEntity.farmProgress = 0;
      blockEntity.farmStatus = FARM_STATUS_DONE;
    } else {
      blockEntity.farmProgress++;
      blockEntity.farmStatus = FARM_STATUS_WORKING;
    }
  }

  public void updateLevel(Level level) {
    if (this.level == null && this.level != level && !level.isClientSide()) {
      this.level = level;
    }
  }

  public boolean allowLootDropItem(ItemStack itemStack) {
    return itemStack != null && !itemStack.isEmpty();
  }

  public void givePlayerItem(
      int index, Level level, Player player, InteractionHand hand, BlockPos blockPos) {
    ItemStack itemStack = takeItem(index);
    if (itemStack.isEmpty()
        || (itemStack.isDamageableItem()
            && itemStack.getDamageValue() >= itemStack.getMaxDamage())) {
      return;
    }
    ItemStack handItemStack = player.getItemInHand(hand);
    if (handItemStack.isEmpty()) {
      player.setItemInHand(hand, itemStack);
    } else if (!player.getInventory().add(itemStack) && level != null) {
      level.addFreshEntity(
          new ItemEntity(
              level,
              blockPos.getX() + 0.5D,
              blockPos.getY() + 0.5D,
              blockPos.getZ() + 0.5D,
              itemStack));
    }
    syncData();
  }

  public void takePlayerItem(int index, Player player, InteractionHand hand) {
    ItemStack handItemStack = player.getItemInHand(hand);
    if (handItemStack.isEmpty()
        || (handItemStack.isDamageableItem()
            && handItemStack.getDamageValue() >= handItemStack.getMaxDamage())) {
      return;
    }
    if (CapturedMob.hasCapturedMob(handItemStack)
        || CapturedMobVirtual.hasCapturedMob(handItemStack)) {
      ItemStack itemStack = handItemStack.copy();
      itemStack.setCount(1);
      setItem(index, itemStack);
      handItemStack.shrink(1);
      syncData();
    }
  }

  protected List<ItemStack> processLootDrop(
      ItemStack capturedMob, ItemStack weaponItem, Level level) {
    return LootManager.getFilteredRandomLootDrop(capturedMob, weaponItem, level);
  }

  @SuppressWarnings("java:S135")
  private void processResult(
      ItemStack capturedMob,
      ItemStack weaponItem,
      ItemStack experienceItem,
      MobFarmBlockEntity blockEntity) {

    // Get corresponding loot drops.
    List<ItemStack> lootDrops =
        this.processLootDrop(capturedMob, weaponItem, blockEntity.getLevel());
    List<ItemStack> unstoredLootDrops = new ArrayList<>();

    // Check if weapon should be damaged, if any.
    boolean hasWeaponItem = weaponItem != null && !weaponItem.isEmpty();
    if (hasWeaponItem
        && weaponItem.isDamageableItem()
        && Boolean.TRUE.equals(COMMON.damageWeaponItem.get())) {
      int unbreakingLevel = weaponItem.getEnchantmentLevel(Enchantments.UNBREAKING);
      // Check if weapon has unbreaking enchantment and decrease damage value.
      // - Unbreaking 0: 0-10 damage
      // - Unbreaking 1: 0-6 damage
      // - Unbreaking 2: 0-3 damage
      // - Unbreaking 3: 0-2 damage
      int randomDamageValue = this.random.nextInt(11);
      int damageValue = Math.max(randomDamageValue - (unbreakingLevel * 2), 0);
      if (damageValue > 0) {
        if (weaponItem.getDamageValue() + damageValue < weaponItem.getMaxDamage()) {
          weaponItem.setDamageValue(weaponItem.getDamageValue() + damageValue);
        } else {
          blockEntity.items.set(MobFarmMenu.WEAPON_SLOT, ItemStack.EMPTY);
        }
      }
    }

    // Check if we should store experience, exp drop is increased with weapon and enchantments.
    // - No weapon: 10% chance
    // - Weapon: 20% chance
    // - Weapon with sharpness or looting enchantment: 33% chance
    int experienceDropChance = 0;
    if (hasWeaponItem) {
      if (weaponItem.getEnchantmentLevel(Enchantments.SHARPNESS) > 0
          || weaponItem.getEnchantmentLevel(Enchantments.MOB_LOOTING) > 0) {
        experienceDropChance =
            this.random.nextInt(COMMON.experienceDropChanceWithEnchantedWeapon.get());
      } else {
        experienceDropChance = this.random.nextInt(COMMON.experienceDropChanceWithWeapon.get());
      }
    } else {
      experienceDropChance = this.random.nextInt(COMMON.experienceDropChanceNoWeapon.get());
    }

    // Check if we should repair mending items with experience or convert glass bottles to
    // experience bottles.
    boolean hasExperienceItem = experienceItem != null && !experienceItem.isEmpty();
    if ((hasWeaponItem || hasExperienceItem) && experienceDropChance == 0) {

      // Repair mending items with experience, if damaged.
      if (hasWeaponItem
          && weaponItem.isDamageableItem()
          && weaponItem.getDamageValue() > 0
          && weaponItem.getEnchantmentLevel(Enchantments.MENDING) > 0) {
        int repairAmount = this.random.nextInt(COMMON.experienceDropMendingRepairAmount.get());
        if (repairAmount > 0) {
          weaponItem.setDamageValue(weaponItem.getDamageValue() - repairAmount);
        }
      }

      // Convert glass bottles to experience bottles.
      else if (hasExperienceItem && experienceItem.is(Items.GLASS_BOTTLE)) {
        blockEntity.items.set(MobFarmMenu.EXPERIENCE_SLOT, new ItemStack(Items.EXPERIENCE_BOTTLE));
      }

      // Increase experience bottle stack size, if possible.
      else if (hasExperienceItem
          && experienceItem.getItem() instanceof ExperienceBottleItem
          && experienceItem.getCount() < experienceItem.getMaxStackSize()) {
        experienceItem.setCount(experienceItem.getCount() + 1);
        blockEntity.items.set(MobFarmMenu.EXPERIENCE_SLOT, experienceItem);
      }
    }

    for (ItemStack lootDrop : lootDrops) {
      // Ignore empty stack with air and blacklisted items if needed.
      if (lootDrop.isEmpty() || !allowLootDropItem(lootDrop)) {
        continue;
      }

      // Stack existing items and place new items in empty slots, if any.
      ItemStack resultItem1 = blockEntity.items.get(MobFarmMenu.RESULT_1_SLOT);
      if (!resultItem1.isEmpty()
          && resultItem1.is(lootDrop.getItem())
          && resultItem1.getCount() + lootDrop.getCount() <= resultItem1.getMaxStackSize()) {
        resultItem1.grow(lootDrop.getCount());
        continue;
      }

      ItemStack resultItem2 = blockEntity.items.get(MobFarmMenu.RESULT_2_SLOT);
      if (!resultItem2.isEmpty()
          && resultItem2.is(lootDrop.getItem())
          && resultItem2.getCount() + lootDrop.getCount() <= resultItem2.getMaxStackSize()) {
        resultItem2.grow(lootDrop.getCount());
        continue;
      }

      ItemStack resultItem3 = blockEntity.items.get(MobFarmMenu.RESULT_3_SLOT);
      if (!resultItem3.isEmpty()
          && resultItem3.is(lootDrop.getItem())
          && resultItem3.getCount() + lootDrop.getCount() <= resultItem3.getMaxStackSize()) {
        resultItem3.grow(lootDrop.getCount());
        continue;
      }

      ItemStack resultItem4 = blockEntity.items.get(MobFarmMenu.RESULT_4_SLOT);
      if (!resultItem4.isEmpty()
          && resultItem4.is(lootDrop.getItem())
          && resultItem4.getCount() + lootDrop.getCount() <= resultItem4.getMaxStackSize()) {
        resultItem4.grow(lootDrop.getCount());
        continue;
      }

      ItemStack resultItem5 = blockEntity.items.get(MobFarmMenu.RESULT_5_SLOT);
      if (!resultItem5.isEmpty()
          && resultItem5.is(lootDrop.getItem())
          && resultItem5.getCount() + lootDrop.getCount() <= resultItem5.getMaxStackSize()) {
        resultItem5.grow(lootDrop.getCount());
        continue;
      }

      if (resultItem1.isEmpty()) {
        blockEntity.setItem(MobFarmMenu.RESULT_1_SLOT, lootDrop);
      } else if (resultItem2.isEmpty()) {
        blockEntity.setItem(MobFarmMenu.RESULT_2_SLOT, lootDrop);
      } else if (resultItem3.isEmpty()) {
        blockEntity.setItem(MobFarmMenu.RESULT_3_SLOT, lootDrop);
      } else if (resultItem4.isEmpty()) {
        blockEntity.setItem(MobFarmMenu.RESULT_4_SLOT, lootDrop);
      } else if (resultItem5.isEmpty()) {
        blockEntity.setItem(MobFarmMenu.RESULT_5_SLOT, lootDrop);
      } else {
        unstoredLootDrops.add(lootDrop);
      }
    }
    // Checked the combined unstored loot drops to avoid spamming!
    if (!unstoredLootDrops.isEmpty()) {
      processFullStorage(blockEntity, unstoredLootDrops);
    }
    syncData();
  }

  public void processAdditionalEffects(
      Level level, BlockPos blockPos, MobFarmBlockEntity blockEntity, ItemStack capturedMob) {
    // Additional effects like sound or particles on mob drop.
    SoundEvent farmDropSound = getFarmDropSound();
    if (Boolean.TRUE.equals(COMMON.playDropSound.get())
        && farmDropSound != null
        && farmDropSound.getLocation() != null
        && level != null
        && blockPos != null) {
      level.playSound(null, blockPos, farmDropSound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }
  }

  public void processFullStorage(MobFarmBlockEntity blockEntity, List<ItemStack> lootDrop) {
    if (Boolean.TRUE.equals(!COMMON.informOwnerAboutFullStorage.get())
        && Boolean.TRUE.equals(!COMMON.logFullStorage.get())) {
      return;
    }
    UUID ownerUUID = blockEntity.getOwner();
    BlockPos blockPos = blockEntity.getBlockPos();

    if (Boolean.TRUE.equals(COMMON.logFullStorage.get())) {
      log.warn(
          "Unable to store loot drop {} for mob farm {} at {} for owner {}!",
          lootDrop,
          this.farmMobName,
          blockPos,
          ownerUUID);
    }
    Level level = blockEntity.level;
    if (Boolean.TRUE.equals(COMMON.informOwnerAboutFullStorage.get())
        && ownerUUID != null
        && blockEntity.level != null
        && level != null) {
      Player owner = level.getPlayerByUUID(ownerUUID);
      if (owner != null && owner.isAlive()) {
        MutableComponent message =
            Component.translatable(
                    Constants.MESSAGE_PREFIX + "warning_full", this.farmMobName, blockPos, lootDrop)
                .withStyle(ChatFormatting.YELLOW);
        owner.sendSystemMessage(message);
      }
    }
  }

  @Override
  public void setItem(int index, ItemStack itemStack) {
    super.setItem(index, itemStack);
    if (index == MobFarmMenu.CAPTURED_MOB_SLOT) {

      // Update and cache data based on captured mob
      if (itemStack.getItem() instanceof CapturedMob) {
        this.farmMobColor = CapturedMob.getCapturedMobColor(itemStack);
        this.farmMobEntityType = CapturedMob.getCapturedMobEntityType(itemStack);
        this.farmMobName = CapturedMob.getCapturedMob(itemStack);
        this.farmMobShearedStatus = CapturedMob.getCapturedMobShearedStatus(itemStack);
        this.farmMobSize = CapturedMob.getCapturedMobSize(itemStack);
        this.farmMobSubType = CapturedMob.getCapturedMobSubType(itemStack);
        this.farmMobType = CapturedMob.getCapturedMobType(itemStack);
      } else if (CapturedMobVirtual.isSupported(itemStack)) {
        this.farmMobColor = CapturedMobVirtual.getCapturedMobColor(itemStack);
        this.farmMobEntityType = CapturedMobVirtual.getCapturedMobEntityType(itemStack);
        this.farmMobName = CapturedMobVirtual.getCapturedMob(itemStack);
        this.farmMobShearedStatus = CapturedMobVirtual.getCapturedMobShearedStatus(itemStack);
        this.farmMobSize = CapturedMobVirtual.getCapturedMobSize(itemStack);
        this.farmMobSubType = CapturedMobVirtual.getCapturedMobSubType(itemStack);
        this.farmMobType = CapturedMobVirtual.getCapturedMobType(itemStack);
      } else {
        this.farmMobColor = null;
        this.farmMobEntityType = null;
        this.farmMobName = "";
        this.farmMobShearedStatus = false;
        this.farmMobSize = 1;
        this.farmMobSubType = "";
        this.farmMobType = "";
      }

      // Set Farm processing time, if there is any mob type.
      if (this.farmMobType != null && !this.farmMobType.isBlank()) {
        if (getFarmProcessingTime() > 0) {
          this.farmTotalTime = getFarmProcessingTime();
        } else {
          this.farmTotalTime = DEFAULT_FARM_PROCESSING_TIME;
        }
      }

      // Update Block state
      BlockState blockState = getBlockState();
      BlockPos blockPos = getBlockPos();
      Level level = this.level;
      if (blockState != null && blockPos != null && level != null) {
        BlockState newBlockState = blockState.setValue(MobFarmBlock.WORKING, !itemStack.isEmpty());
        level.setBlock(blockPos, newBlockState, Block.UPDATE_ALL);
        setChanged(level, blockPos, newBlockState);
      }

      syncData();
    }
  }

  @Override
  public ItemStack removeItem(int index, int count) {
    ItemStack itemStack = super.removeItem(index, count);
    if (index == MobFarmMenu.CAPTURED_MOB_SLOT) {
      syncData();
    }
    return itemStack;
  }

  @Override
  public int[] getSlotsForFace(Direction direction) {
    switch (direction) {
      case DOWN:
        return new int[] {
          MobFarmMenu.RESULT_1_SLOT,
          MobFarmMenu.RESULT_2_SLOT,
          MobFarmMenu.RESULT_3_SLOT,
          MobFarmMenu.RESULT_4_SLOT,
          MobFarmMenu.RESULT_5_SLOT,
          MobFarmMenu.EXPERIENCE_SLOT
        };
      case SOUTH:
        return new int[] {
          MobFarmMenu.CAPTURED_MOB_SLOT, MobFarmMenu.WEAPON_SLOT, MobFarmMenu.EXPERIENCE_SLOT
        };
      default:
        return new int[] {};
    }
  }

  @Override
  public boolean canPlaceItemThroughFace(
      int slotIndex, ItemStack itemStack, @Nullable Direction direction) {
    switch (slotIndex) {
      case MobFarmMenu.CAPTURED_MOB_SLOT:
        return !this.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)
            && (CapturedMob.hasCapturedMob(itemStack)
                || CapturedMobVirtual.hasCapturedMob(itemStack));
      case MobFarmMenu.WEAPON_SLOT:
        return !this.hasItem(MobFarmMenu.WEAPON_SLOT) && itemStack.isDamageableItem();
      case MobFarmMenu.EXPERIENCE_SLOT:
        return !this.hasItem(MobFarmMenu.EXPERIENCE_SLOT) && itemStack.is(Items.GLASS_BOTTLE);
      default:
        return false;
    }
  }

  @Override
  public boolean canTakeItemThroughFace(int slotIndex, ItemStack itemStack, Direction direction) {
    // Only allow the down direction and only for the result slot.
    if (direction != Direction.DOWN) {
      return false;
    }

    switch (slotIndex) {
      case MobFarmMenu.RESULT_1_SLOT:
      case MobFarmMenu.RESULT_2_SLOT:
      case MobFarmMenu.RESULT_3_SLOT:
      case MobFarmMenu.RESULT_4_SLOT:
      case MobFarmMenu.RESULT_5_SLOT:
        return true;
      case MobFarmMenu.EXPERIENCE_SLOT:
        return itemStack.is(Items.EXPERIENCE_BOTTLE);
      default:
        return false;
    }
  }

  @Override
  public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(
      net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
    if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
      Direction blockFacing = this.getBlockState().getValue(MobFarmBlock.FACING);
      if (facing == Direction.DOWN) {
        return handlers[0].cast();
      } else if (facing == Direction.NORTH && blockFacing == Direction.SOUTH
          || facing == Direction.SOUTH && blockFacing == Direction.NORTH
          || facing == Direction.EAST && blockFacing == Direction.WEST
          || facing == Direction.WEST && blockFacing == Direction.EAST) {
        return handlers[1].cast();
      }
      return LazyOptional.empty();
    }
    return super.getCapability(capability, facing);
  }
}
