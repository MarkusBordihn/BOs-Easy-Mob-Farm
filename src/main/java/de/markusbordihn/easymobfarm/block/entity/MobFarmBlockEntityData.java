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

import de.markusbordihn.easymobfarm.data.FarmTier;
import de.markusbordihn.easymobfarm.data.RedstoneMode;
import de.markusbordihn.easymobfarm.item.CapturedMobVirtual;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;

public class MobFarmBlockEntityData extends BaseContainerBlockEntity {

  // Data Access Names
  public static final int FARM_TIME_DATA = 0;
  public static final int FARM_DURATION_DATA = 1;
  public static final int FARM_PROGRESS_DATA = 2;
  public static final int FARM_TOTAL_TIME_DATA = 3;
  public static final int FARM_STATUS_DATA = 4;
  public static final int FARM_MOB_NAME_DATA = 5;
  public static final int FARM_REDSTONE_MODE_DATA = 6;
  public static final int FARM_BLOCK_POS_X_DATA = 7;
  public static final int FARM_BLOCK_POS_Y_DATA = 8;
  public static final int FARM_BLOCK_POS_Z_DATA = 9;
  public static final int DATA_SIZE = 10;

  // Tags
  public static final String FARM_DURATION_TAG = "FarmDuration";
  public static final String FARM_TIME_TAG = "FarmTime";
  public static final String FARM_PROGRESS_TAG = "FarmProgress";
  public static final String FARM_TIME_TOTAL_TAG = "FarmTimeTotal";
  public static final String FARM_OWNER_TAG = "FarmOwner";
  public static final String FARM_REDSTONE_MODE_TAG = "FarmRedstoneMode";

  // Farm Status
  public static final int FARM_STATUS_DONE = 0;
  public static final int FARM_STATUS_FULL = 1;
  public static final int FARM_STATUS_WAITING = 2;
  public static final int FARM_STATUS_WORKING = 3;
  public static final int FARM_STATUS_DISABLED = 4;

  // Internal data states shared
  public UUID farmOwner;
  public int farmDuration;
  public int farmProgress;
  public int farmStatus;
  public int farmTime = -1;
  public int farmTotalTime;

  // Internal data states (cache)
  public int farmId;
  public String farmMobName = "";
  public String farmMobType = "";
  public String farmMobSubType = "";
  public DyeColor farmMobColor = null;
  public EntityType<?> farmMobEntityType = null;
  public boolean farmMobShearedStatus = false;
  public int farmMobSize = 1;
  public RedstoneMode farmRedstoneMode = RedstoneMode.DISABLED;
  // Data container shared between all instances like GUI, Block, Entity.
  protected final ContainerData dataAccess =
      new ContainerData() {

        public int get(int index) {
          switch (index) {
            case FARM_TIME_DATA:
              return farmTime;
            case FARM_DURATION_DATA:
              return farmDuration;
            case FARM_PROGRESS_DATA:
              return farmProgress;
            case FARM_TOTAL_TIME_DATA:
              return farmTotalTime;
            case FARM_STATUS_DATA:
              return farmStatus;
            case FARM_REDSTONE_MODE_DATA:
              return farmRedstoneMode.ordinal();
            case FARM_BLOCK_POS_X_DATA:
              return getBlockPos().getX();
            case FARM_BLOCK_POS_Y_DATA:
              return getBlockPos().getY();
            case FARM_BLOCK_POS_Z_DATA:
              return getBlockPos().getZ();
            default:
              return 0;
          }
        }

        public void set(int index, int value) {
          switch (index) {
            case FARM_TIME_DATA:
              farmTime = value;
              break;
            case FARM_DURATION_DATA:
              farmDuration = value;
              break;
            case FARM_PROGRESS_DATA:
              farmProgress = value;
              break;
            case FARM_TOTAL_TIME_DATA:
              farmTotalTime = value;
              break;
            case FARM_STATUS_DATA:
              farmStatus = value;
              break;
            case FARM_REDSTONE_MODE_DATA:
              farmRedstoneMode = RedstoneMode.values()[value];
              break;
            default:
          }
        }

        @Override
        public int getCount() {
          return DATA_SIZE;
        }
      };
  // Item Storage
  public NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

  public MobFarmBlockEntityData(
      BlockEntityType<?> blockEntity, BlockPos blockPos, BlockState blockState) {
    super(blockEntity, blockPos, blockState);
    this.farmId = blockPos.hashCode();
  }

  public UUID getOwner() {
    return this.farmOwner;
  }

  public void setOwner(LivingEntity livingEntity) {
    this.farmOwner = livingEntity.getUUID();
    this.setChanged();
  }

  public int getFarmId() {
    return this.farmId;
  }

  public String getFarmMobType() {
    return this.farmMobType;
  }

  public String getFarmMobSubType() {
    return this.farmMobSubType;
  }

  public DyeColor getFarmMobColor() {
    return this.farmMobColor;
  }

  public EntityType<?> getFarmMobEntityType() {
    return this.farmMobEntityType;
  }

  public boolean getFarmMobShearedStatus() {
    return this.farmMobShearedStatus;
  }

  public int getFarmMobSize() {
    return this.farmMobSize;
  }

  public int getFarmProcessingTime() {
    return 0;
  }

  public SoundEvent getFarmDropSound() {
    return null;
  }

  public FarmTier getFarmTier() {
    return FarmTier.DEFAULT;
  }

  public RedstoneMode getRedstoneMode() {
    return this.farmRedstoneMode;
  }

  public void setRedstoneMode(RedstoneMode redstoneMode) {
    this.farmRedstoneMode = redstoneMode;
    this.syncData();
  }

  public boolean hasItem(int index) {
    return !this.items.get(index).isEmpty();
  }

  public ItemStack takeItem(int index) {
    if (index < 0 || index >= this.items.size()) {
      return ItemStack.EMPTY;
    }
    ItemStack itemStack = getItem(index);
    setItem(index, ItemStack.EMPTY);
    return itemStack;
  }

  public void syncData() {
    setChanged();
    if (level instanceof ServerLevel serverLevel) {
      LevelChunk chunk = serverLevel.getChunkAt(getBlockPos());
      if (chunk.getLevel().getChunkSource() instanceof ServerChunkCache serverChunkCache) {
        serverChunkCache.chunkMap.getPlayers(chunk.getPos(), false).forEach(this::syncContents);
      }
    }
  }

  public void syncContents(ServerPlayer player) {
    player.connection.send(getUpdatePacket());
  }

  @Override
  protected Component getDefaultName() {
    return Component.translatable("container.easy_mob_farm");
  }

  @Override
  public boolean stillValid(Player player) {
    Level level = this.level;
    return (level != null && level.getBlockEntity(this.worldPosition) == this)
        && player.distanceToSqr(
                this.worldPosition.getX() + 0.5D,
                this.worldPosition.getY() + 0.5D,
                this.worldPosition.getZ() + 0.5D)
            <= 64.0D;
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
  public ItemStack getItem(int index) {
    return this.items.get(index);
  }

  @Override
  public void setItem(int index, ItemStack itemStack) {
    ItemStack itemStackFromIndex = this.items.get(index);
    if (itemStack.is(itemStackFromIndex.getItem())) {
      return;
    }
    this.items.set(index, itemStack);
  }

  @Override
  public int getContainerSize() {
    return this.items.size();
  }

  @Override
  public void clearContent() {
    this.items.clear();
  }

  @Override
  public ItemStack removeItem(int index, int count) {
    return ContainerHelper.removeItem(this.items, index, count);
  }

  @Override
  public ItemStack removeItemNoUpdate(int index) {
    return ContainerHelper.takeItem(this.items, index);
  }

  @Override
  protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
    return new MobFarmMenu(windowId, inventory, this, this.dataAccess);
  }

  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket dataPacket) {
    handleUpdateTag(dataPacket.getTag());
  }

  @Override
  public CompoundTag getUpdateTag() {
    CompoundTag updateCompoundTag = new CompoundTag();
    saveAdditional(updateCompoundTag);
    return updateCompoundTag;
  }

  @Override
  public void load(CompoundTag compoundTag) {
    super.load(compoundTag);
    this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    ContainerHelper.loadAllItems(compoundTag, this.items);
    this.farmDuration = compoundTag.getInt(FARM_DURATION_TAG);
    this.farmTime = compoundTag.getInt(FARM_TIME_TAG);
    this.farmProgress = compoundTag.getInt(FARM_PROGRESS_TAG);
    this.farmTotalTime = compoundTag.getInt(FARM_TIME_TOTAL_TAG);

    // Overwrites farmTotalTime, if process time was adjusted.
    if (this.getFarmProcessingTime() > 0
        && this.farmTotalTime > 0
        && this.getFarmProcessingTime() != this.farmTotalTime) {
      this.farmTotalTime = this.getFarmProcessingTime();
      if (this.farmProgress > this.farmTotalTime) {
        this.farmProgress = this.farmTotalTime;
      }
    }

    // Mob Farm Owner
    if (compoundTag.hasUUID(FARM_OWNER_TAG)) {
      this.farmOwner = compoundTag.getUUID(FARM_OWNER_TAG);
    }

    // Redstone Mode
    if (compoundTag.contains(FARM_REDSTONE_MODE_TAG)) {
      this.farmRedstoneMode = RedstoneMode.values()[compoundTag.getInt(FARM_REDSTONE_MODE_TAG)];
    }

    // Restore additional meta data
    ItemStack capturedMob = this.items.get(MobFarmMenu.CAPTURED_MOB_SLOT);
    if (CapturedMobVirtual.isSupported(capturedMob)) {
      this.farmMobName = CapturedMobVirtual.getCapturedMob(capturedMob);
      this.farmMobType = CapturedMobVirtual.getCapturedMobType(capturedMob);
      this.farmMobSubType = CapturedMobVirtual.getCapturedMobSubType(capturedMob);
      this.farmMobColor = CapturedMobVirtual.getCapturedMobColor(capturedMob);
      this.farmMobShearedStatus = CapturedMobVirtual.getCapturedMobShearedStatus(capturedMob);
      this.farmMobSize = CapturedMobVirtual.getCapturedMobSize(capturedMob);
      this.farmMobEntityType = CapturedMobVirtual.getCapturedMobEntityType(capturedMob);
    }
  }

  @Override
  public void saveAdditional(CompoundTag compoundTag) {
    super.saveAdditional(compoundTag);
    compoundTag.putInt(FARM_DURATION_TAG, this.farmDuration);
    compoundTag.putInt(FARM_TIME_TAG, this.farmTime);
    compoundTag.putInt(FARM_PROGRESS_TAG, this.farmProgress);
    compoundTag.putInt(FARM_TIME_TOTAL_TAG, this.farmTotalTime);
    if (this.farmOwner != null) {
      compoundTag.putUUID(FARM_OWNER_TAG, this.farmOwner);
    }
    compoundTag.putInt(FARM_REDSTONE_MODE_TAG, this.farmRedstoneMode.ordinal());
    ContainerHelper.saveAllItems(compoundTag, this.items);
  }
}
