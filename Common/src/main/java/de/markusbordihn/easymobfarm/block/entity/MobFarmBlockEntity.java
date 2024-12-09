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

package de.markusbordihn.easymobfarm.block.entity;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobFarmBonusConfig;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureDataSupport;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmContainerData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmDataEntry;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlot;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmSlots;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.experience.ExperienceManager;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.FilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.SlotUpgradeItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoFlowersFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoMeatFilterItem;
import de.markusbordihn.easymobfarm.loot.LootManager;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import de.markusbordihn.easymobfarm.tags.ModItemTags;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {

  public static final String ID = "mob_farm_entity";
  public static final int DEFAULT_FARM_PROCESSING_TIME = 6000;
  public static final int DEFAULT_PROCESSING_TICKS = 20;
  public static final int DEFAULT_RECHECK_TICKS = 200;
  public static final String TIER_LEVEL_TAG = "TierLevel";
  public static final String FARM_TYPE_TAG = "FarmType";
  public static final String CAPTURED_MOB_EXPERIENCE_TAG = "CapturedMobExperience";
  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final int[] RESULT_SLOTS =
      MobFarmSlots.RESULT_SLOTS.stream().mapToInt(MobFarmSlot::index).toArray();
  private static final Random random = new Random();
  protected final NonNullList<ItemStack> items =
      NonNullList.withSize(MobFarmMenu.CONTAINER_SIZE, ItemStack.EMPTY);
  private final ContainerData data;
  private final int processingDelay;
  private MobFarmType mobFarmType;
  private int farmTierLevel;
  private int numberOfOutputSlots = MobFarmMenu.MIN_NUMBER_OF_OUTPUT_SLOTS;
  private int farmProgress = 0;
  private int farmProgressionSpeed = DEFAULT_PROCESSING_TICKS;
  private int farmStatus = MobFarmStatus.IDLE;
  private int capturedMobExperience = -1;

  public MobFarmBlockEntity(
      final BlockEntityType<?> blockEntityType,
      final BlockPos blockPos,
      final BlockState blockState) {
    this(
        blockEntityType,
        blockPos,
        blockState,
        MobFarmBlock.getTierLevel(blockState),
        MobFarmBlock.getFarmType(blockState));
  }

  public MobFarmBlockEntity(
      final BlockEntityType<?> blockEntityType,
      final BlockPos blockPos,
      final BlockState blockState,
      final int farmTierLevel,
      final MobFarmType mobFarmType) {
    super(blockEntityType, blockPos, blockState);
    this.data = new MobFarmContainerData(this);
    this.setFarmTierLevel(farmTierLevel);
    this.mobFarmType = mobFarmType;

    // Add random delay (0 - DEFAULT_PROCESSING_TICKS) to avoid processing at the same time.
    this.processingDelay =
        Math.min(
            Math.max(
                (Math.abs(blockPos.getX() * 31 + blockPos.getZ() * 17) % DEFAULT_PROCESSING_TICKS)
                    + new Random().nextInt(5),
                0),
            DEFAULT_PROCESSING_TICKS - 1);

    this.setChanged();
  }

  public static void serverTick(
      final Level level,
      final BlockPos blockPos,
      final BlockState blockState,
      final MobFarmBlockEntity blockEntity) {
    // Check if slot is filled.
    if (!blockEntity.hasCapturedMob() || blockEntity.farmStatus == MobFarmStatus.DISABLED) {
      if (blockEntity.farmStatus != MobFarmStatus.IDLE) {
        blockEntity.farmStatus = MobFarmStatus.IDLE;
      }
      return;
    }

    // If farm was full, check if it can process again normally after 200 ticks.
    if (blockEntity.farmStatus == MobFarmStatus.FULL) {
      if (level.getGameTime() % DEFAULT_RECHECK_TICKS == 0 && blockEntity.canProcessingResults()) {
        log.debug(
            "Mob farm block entity at {} is no longer full and can process results again",
            blockEntity.getBlockPos());
        blockEntity.farmStatus = MobFarmStatus.IDLE;
      } else {
        return;
      }
    }

    // Checks only run every 20 ticks, for performance reasons.
    if (level.getGameTime() % DEFAULT_PROCESSING_TICKS != blockEntity.processingDelay) {
      return;
    }

    // Increase farm progress
    if (blockEntity.farmProgress < DEFAULT_FARM_PROCESSING_TIME) {
      if (blockEntity.farmProgress % 200 == 0) {
        log.debug(
            "Mob farm block entity at {} with farm progress {} / {}",
            blockPos,
            blockEntity.farmProgress,
            DEFAULT_FARM_PROCESSING_TIME);
      }

      // Calculate farm progression speed and increase farm progress
      int farmProgressionSpeed = getEffectiveFarmProgressionSpeed(blockEntity);
      blockEntity.farmProgress =
          Math.min(blockEntity.farmProgress + farmProgressionSpeed, DEFAULT_FARM_PROCESSING_TIME);
      blockEntity.farmStatus = MobFarmStatus.WORKING;
      return;
    }

    // Processing results
    if (blockEntity.canProcessingResults()) {
      blockEntity.processingResults();
    } else {
      log.warn(
          "Mob farm block entity at {} is full and can't process results",
          blockEntity.getBlockPos());
      blockEntity.farmStatus = MobFarmStatus.FULL;
    }

    // Reset farm progress
    blockEntity.farmProgress = 0;
  }

  private static int getEffectiveFarmProgressionSpeed(MobFarmBlockEntity blockEntity) {
    return blockEntity.getFarmProgressionSpeed() + blockEntity.getFarmProgressionSpeedBonus();
  }

  public int getFarmProgressionSpeed() {
    return this.farmProgressionSpeed;
  }

  public int getFarmProgressionSpeedBonus() {
    int farmProgressionSpeedBonus = 0;
    for (EnhancementItem enhancementItem : this.getEnchantmentItems()) {
      if (enhancementItem instanceof SpeedEnhancementItem speedEnhancementItem) {
        farmProgressionSpeedBonus += speedEnhancementItem.getUpgradeSpeed();
      }
    }
    return farmProgressionSpeedBonus;
  }

  public List<EnhancementItem> getEnchantmentItems() {
    List<EnhancementItem> enchantmentItems = new ArrayList<>();
    for (MobFarmSlot upgradeSlot : MobFarmSlots.ENHANCEMENT_ITEM_SLOTS) {
      ItemStack itemStack = this.getItem(upgradeSlot.index());
      if (!itemStack.isEmpty() && itemStack.getItem() instanceof EnhancementItem enhancementItem) {
        enchantmentItems.add(enhancementItem);
      }
    }
    return enchantmentItems;
  }

  public Set<FilterItem> getFilterItems() {
    Set<FilterItem> filterItems = new HashSet<>();
    for (MobFarmSlot filterSlot : MobFarmSlots.FILTER_ITEM_SLOTS) {
      ItemStack itemStack = this.getItem(filterSlot.index());
      if (!itemStack.isEmpty() && itemStack.getItem() instanceof FilterItem filterItem) {
        filterItems.add(filterItem);
      }
    }
    return filterItems;
  }

  public Set<SlotUpgradeItem> getSlotUpgradeItems() {
    Set<SlotUpgradeItem> slotUpgradeItems = new HashSet<>();
    for (MobFarmSlot upgradeSlot : MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS) {
      ItemStack itemStack = this.getItem(upgradeSlot.index());
      if (!itemStack.isEmpty() && itemStack.getItem() instanceof SlotUpgradeItem slotUpgradeItem) {
        slotUpgradeItems.add(slotUpgradeItem);
      }
    }
    return slotUpgradeItems;
  }

  public boolean canProcessingResults() {
    int startSlotIndex = MobFarmSlots.RESULT_SLOTS.get(0).index();
    for (int slotIndex = startSlotIndex;
        slotIndex < startSlotIndex + numberOfOutputSlots;
        slotIndex++) {
      if (this.getItem(slotIndex).isEmpty()
          || this.getItem(slotIndex).getCount() < this.getItem(slotIndex).getMaxStackSize()) {
        return true;
      }
    }
    return false;
  }

  public void processingResults() {
    MobCaptureData mobCaptureData = this.getMobCaptureData();
    if (mobCaptureData == null) {
      return;
    }

    // Update farm status to processing for visual feedback and other mechanics.
    this.farmStatus = MobFarmStatus.PROCESSING;

    // Get loot drops for captured mob over loot manager and their loot tables.
    NonNullList<ItemStack> lootDrops =
        LootManager.getEntityLoot(mobCaptureData, this.getEnchantmentItems(), level);

    // Check if lucky drop farm is active and add additional loot drops.
    if (this.getFarmType() == MobFarmType.LUCKY_DROP_FARM) {
      int luckRoll = random.nextInt(100);
      if (luckRoll < 95) {
        log.debug(
            "Adding lucky drop for {} (tier: {}) block entity at {} with captured mob {}",
            this.getFarmType(),
            this.getFarmTierLevel(),
            this.getBlockPos(),
            mobCaptureData.entityType());

        // Add lucky loot drop based on the mob farm, tier level and captured mob.
        if (random.nextInt(2) == 0) {
          NonNullList<ItemStack> luckyDrop =
              LootManager.getLuckyLoot(mobCaptureData, this.getBlockPos(), level);
          if (!luckyDrop.isEmpty()) {
            lootDrops.addAll(luckyDrop);
          }
        }

        // Damage mob capture item
        ItemStack capturedMob = this.getItem(MobFarmSlot.CAPTURED_MOB);
        if (capturedMob != null && capturedMob.isDamageableItem()) {
          int damageValue = capturedMob.getDamageValue();
          int damageAmount =
              random.nextInt(
                      switch (this.getFarmTierLevel()) {
                        case 0 -> 5;
                        case 1 -> 4;
                        case 2 -> 3;
                        case 3 -> 2;
                        default -> 2;
                      })
                  + 1;
          if (damageValue + damageAmount >= capturedMob.getMaxDamage()) {
            this.removeItem(MobFarmSlot.CAPTURED_MOB.index(), 1);
            this.handleLootDrops(lootDrops);
            this.farmStatus = MobFarmStatus.IDLE;
            return;
          }
          capturedMob.setDamageValue(damageValue + damageAmount);
        }
      } else {
        // 5% Bad lucky drop, spawn captured mob and remove item.
        log.debug(
            "Bad lucky drop for {} (tier: {}) block entity at {} with captured mob {}",
            this.getFarmType(),
            this.getFarmTierLevel(),
            this.getBlockPos(),
            mobCaptureData.entityType());
        this.spawnEntity(mobCaptureData.entityType(), this.level, this.getBlockPos().above());
        this.removeItem(MobFarmSlot.CAPTURED_MOB.index(), 1);
        this.farmStatus = MobFarmStatus.IDLE;
        return;
      }
    }

    // Add optional bonus loot drop based on the mob farm, tier level and captured mob.
    ItemStack bonusLootDrop =
        MobFarmBonusConfig.getBonusDrop(
            this.getFarmType(), this.getFarmTierLevel(), mobCaptureData.entityType());
    if (!bonusLootDrop.isEmpty()) {
      log.debug(
          "Adding bonus loot drop {} for {} (tier: {}) block entity at {} with captured mob {}",
          bonusLootDrop,
          this.getFarmType(),
          this.getFarmTierLevel(),
          this.getBlockPos(),
          mobCaptureData.entityType());
      lootDrops.add(bonusLootDrop.copy());
    }

    // Handle loot drops
    this.handleLootDrops(lootDrops);

    // Set farm status to working
    this.farmStatus = MobFarmStatus.IDLE;
  }

  private void handleLootDrops(NonNullList<ItemStack> lootDrops) {
    if (lootDrops == null || lootDrops.isEmpty()) {
      return;
    }
    log.debug(
        "Processing loot drops for mob farm block entity at {} with {} loot drops",
        this.getBlockPos(),
        lootDrops);

    // Handle loot drops
    for (ItemStack lootDrop : lootDrops) {
      if (lootDrop.isEmpty()) {
        continue;
      }

      // Handle filter slots items.
      for (FilterItem filterItem : this.getFilterItems()) {
        if (filterItem instanceof NoMeatFilterItem && lootDrop.is(ModItemTags.MEAT)) {
          lootDrop.setCount(0);
        }
        if (filterItem instanceof NoFlowersFilterItem && lootDrop.is(ModItemTags.FLOWERS)) {
          lootDrop.setCount(0);
        }
      }

      // Handle individual filter items.
      for (MobFarmSlot filterSlot : MobFarmSlots.FILTER_ITEM_SLOTS) {
        ItemStack filterItem = this.getItem(filterSlot.index());
        if (filterItem.isEmpty() || !filterItem.sameItem(lootDrop)) {
          continue;
        }
        log.debug("Filter slot {} matches loot drop {}", filterSlot, lootDrop);
        lootDrop.setCount(0);
        break;
      }

      // Handle output slots
      storeItemInOutputSlot(lootDrop);
    }
  }

  private void storeItemInOutputSlot(final ItemStack itemStack) {
    int startSlotIndex = MobFarmSlots.RESULT_SLOTS.get(0).index();
    for (int slotIndex = startSlotIndex;
        slotIndex < startSlotIndex + numberOfOutputSlots;
        slotIndex++) {
      ItemStack outputSlot = this.getItem(slotIndex);

      if (outputSlot.isEmpty()) {
        setItemInSlot(slotIndex, itemStack);
        break;
      }

      if (canGrowOutputSlot(outputSlot, itemStack)) {
        growOutputSlot(outputSlot, itemStack);
        if (itemStack.isEmpty()) {
          break;
        }
      }
    }
  }

  private void setItemInSlot(int slotIndex, ItemStack itemStack) {
    this.setItem(slotIndex, itemStack);
  }

  private boolean canGrowOutputSlot(ItemStack outputSlot, ItemStack itemStack) {
    return outputSlot.sameItem(itemStack) && outputSlot.getCount() < outputSlot.getMaxStackSize();
  }

  private void growOutputSlot(ItemStack outputSlot, ItemStack itemStack) {
    int amountToGrow =
        Math.min(itemStack.getCount(), outputSlot.getMaxStackSize() - outputSlot.getCount());
    outputSlot.grow(amountToGrow);
    itemStack.shrink(amountToGrow);
  }

  public MobCaptureData getMobCaptureData() {
    return MobCaptureManager.getMobCaptureData(this.getItem(MobFarmSlot.CAPTURED_MOB));
  }

  public boolean hasCapturedMob() {
    return !this.getItem(MobFarmSlot.CAPTURED_MOB).isEmpty();
  }

  public ItemStack takeItem(final int index) {
    if (index < 0 || index >= this.items.size()) {
      return ItemStack.EMPTY;
    }
    ItemStack itemStack = getItem(index);
    setItem(index, ItemStack.EMPTY);
    return itemStack;
  }

  public void giveMobCaptureItem(final Player player, final InteractionHand hand) {
    ItemStack capturedMob = getCapturedMob();
    if (capturedMob.isEmpty()) {
      return;
    }
    givePlayerItem(
        MobFarmSlot.CAPTURED_MOB.index(), player.level, player, hand, player.blockPosition());
  }

  public void givePlayerItem(
      final int index,
      final Level level,
      final Player player,
      final InteractionHand hand,
      final BlockPos blockPos) {
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
    this.syncChanges();
  }

  private boolean isInvalidHandItem(ItemStack handItemStack) {
    return handItemStack.isEmpty()
        || (handItemStack.isDamageableItem()
            && handItemStack.getDamageValue() >= handItemStack.getMaxDamage());
  }

  public boolean takeMobCaptureItem(final Player player, final InteractionHand hand) {
    ItemStack handItemStack = player.getItemInHand(hand);
    if (isInvalidHandItem(handItemStack)) {
      return false;
    }

    if (MobCaptureDataSupport.isSupported(handItemStack)
        && this.getItem(MobFarmSlot.CAPTURED_MOB).isEmpty()) {
      return takePlayerItem(MobFarmSlot.CAPTURED_MOB.index(), player, hand);
    }

    return false;
  }

  public boolean takeEnhancementItem(final Player player, final InteractionHand hand) {
    ItemStack handItemStack = player.getItemInHand(hand);
    if (isInvalidHandItem(handItemStack)) {
      return false;
    }

    Item handItem = handItemStack.getItem();
    if (handItem instanceof EnhancementItem) {
      for (MobFarmSlot upgradeSlot : MobFarmSlots.ENHANCEMENT_ITEM_SLOTS) {
        ItemStack itemStack = this.getItem(upgradeSlot.index());
        if (itemStack.isEmpty()) {
          return takePlayerItem(upgradeSlot.index(), player, hand);
        }
      }
    }
    return false;
  }

  public boolean takeSlotUpgradeItem(final Player player, final InteractionHand hand) {
    ItemStack handItemStack = player.getItemInHand(hand);
    if (isInvalidHandItem(handItemStack)) {
      return false;
    }

    Item handItem = handItemStack.getItem();
    if (handItem instanceof SlotUpgradeItem) {
      for (MobFarmSlot upgradeSlot : MobFarmSlots.SLOT_UPGRADE_ITEM_SLOTS) {
        ItemStack itemStack = this.getItem(upgradeSlot.index());
        if (itemStack.isEmpty()) {
          return takePlayerItem(upgradeSlot.index(), player, hand);
        }
      }
    }
    return false;
  }

  public boolean takeFilterItem(final Player player, final InteractionHand hand) {
    ItemStack handItemStack = player.getItemInHand(hand);
    if (isInvalidHandItem(handItemStack)) {
      return false;
    }

    Item handItem = handItemStack.getItem();
    if (handItem instanceof FilterItem) {
      for (MobFarmSlot filterSlot : MobFarmSlots.FILTER_ITEM_SLOTS) {
        ItemStack itemStack = this.getItem(filterSlot.index());
        if (itemStack.sameItem(handItemStack)) {
          return false;
        }
        if (itemStack.isEmpty()) {
          return takePlayerItem(filterSlot.index(), player, hand);
        }
      }
    }
    return false;
  }

  public boolean takePlayerItem(final int index, final Player player, final InteractionHand hand) {
    ItemStack handItemStack = player.getItemInHand(hand);
    if (handItemStack.isEmpty()
        || (handItemStack.isDamageableItem()
            && handItemStack.getDamageValue() >= handItemStack.getMaxDamage())) {
      return false;
    }

    ItemStack itemStack = handItemStack.copy();
    itemStack.setCount(1);
    setItem(index, itemStack);
    handItemStack.shrink(1);
    this.syncChanges();
    return true;
  }

  public int getNumberOfOutputSlots() {
    return this.numberOfOutputSlots;
  }

  public void setNumberOfOutputSlots(int numberOfOutputSlots) {
    this.numberOfOutputSlots = numberOfOutputSlots;
  }

  public int getFarmProgress() {
    return this.farmProgress;
  }

  public void setFarmProgress(int farmProgress) {
    this.farmProgress = farmProgress;
  }

  public int getFarmStatus() {
    return this.farmStatus;
  }

  public void setFarmStatus(int farmStatus) {
    this.farmStatus = farmStatus;
  }

  public int getFarmTierLevel() {
    return this.farmTierLevel;
  }

  public void setFarmTierLevel(int farmTierLevel) {
    if (this.data.get(MobFarmDataEntry.FARM_TIER_LEVEL) == farmTierLevel) {
      return;
    }

    // Handle tier progression speed
    this.farmProgressionSpeed = DEFAULT_PROCESSING_TICKS;
    this.farmProgressionSpeed += MobFarmConfig.getFarmTierProgressionUpgradeSpeed(farmTierLevel);

    log.debug("Set mob farm tier level to {}", farmTierLevel);
    this.farmTierLevel = farmTierLevel;
  }

  public MobFarmType getFarmType() {
    return this.mobFarmType;
  }

  public ItemStack getCapturedMob() {
    return this.getItem(MobFarmSlot.CAPTURED_MOB);
  }

  public int getCapturedMobExperience() {
    return this.capturedMobExperience;
  }

  public ItemStack getItem(final MobFarmSlot mobFarmSlot) {
    return this.items.get(mobFarmSlot.index());
  }

  public void syncChanges() {
    this.setChanged();

    // Force block update
    if (this.level != null && !this.level.isClientSide) {
      this.level.sendBlockUpdated(
          this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
  }

  public void setOwner(final Player player) {
    log.info("Set owner for mob farm block entity to {}", player);
  }

  public ContainerData getContainerData() {
    return this.data;
  }

  public void dropInventoryContents() {
    if (!this.level.isClientSide && !this.items.isEmpty()) {
      for (ItemStack stack : this.items) {
        if (!stack.isEmpty()) {
          Containers.dropItemStack(
              this.level,
              this.worldPosition.getX(),
              this.worldPosition.getY(),
              this.worldPosition.getZ(),
              stack);
        }
      }
    }
  }

  private void spawnEntity(EntityType<?> entityType, Level level, BlockPos position) {
    if (entityType == null || level == null || position == null) {
      return;
    }
    Entity entity = entityType.create(level);
    if (entity != null) {
      log.debug(
          "Spawn entity {} at position {} for mob farm block entity at {}",
          entity,
          position,
          this.getBlockPos());
      entity.setPos(position.getX() + 0.5, position.getY() + 1, position.getZ() + 0.5);
      level.addFreshEntity(entity);
    }
  }

  private void setsMobCaptureItem(ItemStack itemStack) {
    log.debug(
        "Sets mob capture item {} in mob farm block entity at {}", itemStack, this.getBlockPos());

    // Create mob entity to get experience reward and other additional data.
    MobCaptureData mobCaptureData = this.getMobCaptureData();
    EntityType<?> entityType = mobCaptureData != null ? mobCaptureData.entityType() : null;
    LivingEntity livingEntity =
        entityType != null ? (LivingEntity) entityType.create(this.level) : null;
    if (livingEntity != null) {
      this.capturedMobExperience = ExperienceManager.getExperienceReward(livingEntity);
    } else {
      this.capturedMobExperience = 0;
    }
  }

  private void removedMobCaptureItem(ItemStack itemStack) {
    log.debug(
        "Removed mob capture item {} from mob farm block entity at {}",
        itemStack,
        this.getBlockPos());
    capturedMobExperience = -1;
  }

  @Override
  protected Component getDefaultName() {
    return TextComponent.getTranslatedTextRaw("container.easy_mob_farm.mob_farm");
  }

  @Override
  protected AbstractContainerMenu createMenu(final int windowId, final Inventory inventory) {
    return null;
  }

  @Override
  public int getContainerSize() {
    return this.items.size();
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack itemStack : this.items) {
      if (!itemStack.isEmpty()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ItemStack getItem(final int index) {
    return this.items.get(index);
  }

  @Override
  public ItemStack removeItem(final int index, final int count) {
    ItemStack itemStack = ContainerHelper.removeItem(this.items, index, count);
    if (index == MobFarmSlot.CAPTURED_MOB.index() && !itemStack.isEmpty()) {
      this.removedMobCaptureItem(itemStack);
      this.syncChanges();
    }
    return itemStack;
  }

  @Override
  public ItemStack removeItemNoUpdate(final int index) {
    ItemStack itemStack = ContainerHelper.takeItem(this.items, index);
    if (index == MobFarmSlot.CAPTURED_MOB.index() && !itemStack.isEmpty()) {
      this.removedMobCaptureItem(itemStack);
    }
    return itemStack;
  }

  @Override
  public void setItem(final int index, final ItemStack itemStack) {
    this.items.set(index, itemStack);
    if (itemStack.getCount() > this.getMaxStackSize()) {
      itemStack.setCount(this.getMaxStackSize());
    }
    if (index == MobFarmSlot.CAPTURED_MOB.index() && !itemStack.isEmpty()) {
      this.setsMobCaptureItem(itemStack);
    }
    this.syncChanges();
  }

  @Override
  public CompoundTag getUpdateTag() {
    CompoundTag tag = super.getUpdateTag();
    ContainerHelper.saveAllItems(tag, this.items);
    return tag;
  }

  @Override
  public ClientboundBlockEntityDataPacket getUpdatePacket() {
    CompoundTag tag = new CompoundTag();
    this.saveAdditional(tag);
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public boolean stillValid(final Player player) {
    return player.isAlive();
  }

  @Override
  public void clearContent() {
    this.items.clear();
  }

  @Override
  public int[] getSlotsForFace(final Direction direction) {
    if (direction == Direction.DOWN
        || direction == Direction.NORTH
        || direction == Direction.EAST
        || direction == Direction.SOUTH
        || direction == Direction.WEST) {
      return RESULT_SLOTS;
    } else if (direction == Direction.UP) {
      return new int[] {};
    } else {
      return new int[] {};
    }
  }

  @Override
  public boolean canPlaceItemThroughFace(
      final int face, final ItemStack itemStack, final Direction direction) {
    return direction == Direction.UP;
  }

  @Override
  public boolean canTakeItemThroughFace(
      final int slot, final ItemStack itemStack, final Direction direction) {
    if (direction == Direction.UP) {
      return false;
    }

    for (int resultSlot : RESULT_SLOTS) {
      if (slot == resultSlot) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void load(final CompoundTag compoundTag) {
    super.load(compoundTag);

    // Load items
    this.items.clear();
    ContainerHelper.loadAllItems(compoundTag, this.items);

    // Load additional data
    if (compoundTag.contains(TIER_LEVEL_TAG)) {
      this.setFarmTierLevel(compoundTag.getInt(TIER_LEVEL_TAG));
    }
    if (compoundTag.contains(FARM_TYPE_TAG)) {
      this.mobFarmType = MobFarmType.valueOf(compoundTag.getString(FARM_TYPE_TAG));
    }
    if (compoundTag.contains(CAPTURED_MOB_EXPERIENCE_TAG)
        && compoundTag.getInt(CAPTURED_MOB_EXPERIENCE_TAG) >= 0) {
      this.capturedMobExperience = compoundTag.getInt(CAPTURED_MOB_EXPERIENCE_TAG);
    }
  }

  @Override
  public void saveAdditional(final CompoundTag compoundTag) {
    super.saveAdditional(compoundTag);

    // Save items
    ContainerHelper.saveAllItems(compoundTag, this.items);

    // Save additional data
    compoundTag.putInt(TIER_LEVEL_TAG, this.farmTierLevel);
    compoundTag.putString(FARM_TYPE_TAG, this.mobFarmType.name());
    if (this.capturedMobExperience >= 0) {
      compoundTag.putInt(CAPTURED_MOB_EXPERIENCE_TAG, this.capturedMobExperience);
    }
  }
}
