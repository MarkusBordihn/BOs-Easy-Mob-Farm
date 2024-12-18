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

package de.markusbordihn.easymobfarm.capture;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.capture.MobEntityData;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobCaptureManager {

  public static final String MOB_CAPTURE_DATA_TAG = "MobCaptureData";
  public static final String CAT_VARIANT_TAG = "variant";
  public static final String COLOR_TAG = "Color";

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final String LOG_PREFIX = "[MobCaptureManager]";

  private MobCaptureManager() {}

  public static ItemStack getMobCaptureCardItem(EntityType<?> entityType, Level level) {
    if (entityType == null || level == null) {
      return null;
    }

    Entity entity = entityType.create(level);
    if (entity == null) {
      log.error("{} Unable to create entity {}!", LOG_PREFIX, entityType);
      return null;
    }

    if (!(entity instanceof LivingEntity livingEntity)) {
      log.error("{} Entity {} is not a living entity!", LOG_PREFIX, entityType);
      entity.discard();
      return null;
    }

    ItemStack itemStack = getMobCaptureCardItem(livingEntity);
    log.debug("{} Captured mob {} with data:{}.", LOG_PREFIX, entityType, itemStack);
    entity.discard();
    return itemStack;
  }

  public static ItemStack getMobCaptureCardItem(LivingEntity livingEntity) {
    if (livingEntity == null
        || livingEntity instanceof Player
        || livingEntity.level().isClientSide) {
      return null;
    }

    // Get mob capture card item.
    MobCaptureCardItem mobCaptureCardItem = MobCaptureCardItem.getMobCaptureCardItem();
    if (mobCaptureCardItem == null) {
      log.error("{} Unable to find mob capture card item!", LOG_PREFIX);
      return null;
    }

    // Get mob capture data from living entity.
    MobCaptureData mobCaptureData = new MobCaptureData(livingEntity);
    if (mobCaptureData.entityType() == null || mobCaptureData.data() == null) {
      log.debug(
          "{} Unable to get mob capture data from living entity {}.", LOG_PREFIX, livingEntity);
      return null;
    }

    // Cleanup Mob Data for allowing better stackability.
    mobCaptureData =
        mobCaptureData.withData(
            MobEntityData.removeSafeToRemoveMobCaptureCardTags(mobCaptureData.data()));

    // Set mob capture data to mob capture card item.
    ItemStack itemStack = new ItemStack(mobCaptureCardItem);
    writeMobCaptureData(itemStack, mobCaptureData);

    log.debug(
        "{} Captured mob {} with data:{}",
        LOG_PREFIX,
        mobCaptureData.entityType(),
        mobCaptureData.data());
    return itemStack;
  }

  public static boolean releaseMob(
      MobCaptureData mobCaptureData, BlockPos blockPos, ServerLevel serverLevel) {
    if (mobCaptureData == null || mobCaptureData.entityType() == null) {
      return false;
    }

    // Validate EntityType
    EntityType<?> entityType = mobCaptureData.entityType();
    if (entityType == null) {
      return false;
    }

    // Create entity from EntityType
    Entity entity = entityType.create(serverLevel);
    if (entity == null) {
      log.error("{} Unable to create entity {}!", LOG_PREFIX, entityType);
      return false;
    }

    // Copy data from mobCaptureData to entity
    if (entity instanceof LivingEntity livingEntity) {
      livingEntity.readAdditionalSaveData(mobCaptureData.data());
    }

    // Check if entity could be spawned at block position or above
    BlockState blockState = serverLevel.getBlockState(blockPos);
    BlockPos finalBlockPos = null;
    if ((blockState.is(Blocks.GRASS) || blockState.is(Blocks.SEAGRASS))) {
      finalBlockPos = blockPos;
    } else {
      BlockState blockStateBlockAbove = serverLevel.getBlockState(blockPos.above());
      if ((blockStateBlockAbove.isAir() || blockStateBlockAbove.is(Blocks.WATER))) {
        finalBlockPos = blockPos.above();
      }
    }

    // Spawn entity at block position if possible
    if (finalBlockPos != null) {
      entity.moveTo(
          blockPos.getX() + 0.5D, blockPos.getY() + 1.0D, blockPos.getZ() + 0.5D, 0.0F, 0.0F);
      serverLevel.addFreshEntity(entity);
      log.debug(
          "{} Released mob {} with data:{} at block position {}.",
          LOG_PREFIX,
          entityType,
          mobCaptureData.data(),
          blockPos);
      return true;
    }

    log.warn(
        "{} Unable to release mob {} with data:{} at block position {}.",
        LOG_PREFIX,
        entityType,
        mobCaptureData.data(),
        blockPos);
    return true;
  }

  public static ItemStack createMobCaptureCard(ItemLike itemLike, MobCaptureData mobCaptureData) {
    if (itemLike == null) {
      return null;
    }
    ItemStack itemStack = new ItemStack(itemLike);
    writeMobCaptureData(itemStack, mobCaptureData);
    return itemStack;
  }

  public static ItemStack createMobCaptureCard(
      ItemLike itemLike, EntityType<?> entityType, String variant, DyeColor dyeColor) {
    if (itemLike == null || entityType == null) {
      return null;
    }
    MobCaptureData mobCaptureData = new MobCaptureData(entityType).withFoil(false);
    CompoundTag compoundTag = new CompoundTag();

    // Set variant and corresponding data.
    if (variant != null) {
      mobCaptureData = mobCaptureData.withVariant(variant);
      if (entityType == EntityType.CAT) {
        compoundTag.putString(CAT_VARIANT_TAG, variant);
      }
    }

    // Set dye color and corresponding data.
    if (dyeColor != null) {
      mobCaptureData = mobCaptureData.withColor(dyeColor);
      compoundTag.putInt(COLOR_TAG, dyeColor.getId());
    }

    // Return item stack with mob capture data.
    return createMobCaptureCard(itemLike, mobCaptureData.withData(compoundTag));
  }

  public static void writeMobCaptureData(ItemStack itemStack, MobCaptureData mobCaptureData) {
    if (itemStack == null || itemStack.isEmpty() || mobCaptureData == null) {
      return;
    }
    CompoundTag compoundTag = itemStack.getOrCreateTag();
    compoundTag.put(MOB_CAPTURE_DATA_TAG, mobCaptureData.createTag());
    itemStack.setTag(compoundTag);
  }

  public static boolean hasMobCaptureData(ItemStack itemStack) {
    return itemStack != null
        && !itemStack.isEmpty()
        && itemStack.hasTag()
        && itemStack.getTag().contains(MOB_CAPTURE_DATA_TAG);
  }

  public static MobCaptureData getMobCaptureData(ItemStack itemStack) {
    if (itemStack == null || itemStack.isEmpty()) {
      return null;
    }

    // Get mob capture data from item stack.
    CompoundTag tag = itemStack.getTag();

    // Mob capture card compatible data.
    if (tag != null && tag.contains(MOB_CAPTURE_DATA_TAG)) {
      return new MobCaptureData(tag.getCompound(MOB_CAPTURE_DATA_TAG));
    }

    // Try to get mob capture data from item stack.
    return new MobCaptureData(itemStack, tag);
  }
}
