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
import de.markusbordihn.easymobfarm.component.DataComponents;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.capture.MobColor;
import de.markusbordihn.easymobfarm.data.capture.MobEntityData;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobCaptureManager {

  public static final String COLOR_TAG = "Color";
  public static final String VARIANT_TAG = "variant";

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final String LOG_PREFIX = "[MobCaptureManager]";

  private MobCaptureManager() {}

  public static ItemStack getMobCaptureCardItem(EntityType<?> entityType, Level level) {
    if (entityType == null || level == null) {
      return null;
    }

    Entity entity = entityType.create(level, EntitySpawnReason.SPAWN_ITEM_USE);
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
        || livingEntity.level().isClientSide()) {
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
    Entity entity = entityType.create(serverLevel, EntitySpawnReason.SPAWN_ITEM_USE);
    if (entity == null) {
      log.error("{} Unable to create entity {}!", LOG_PREFIX, entityType);
      return false;
    }

    // Copy data from mobCaptureData to entity
    CompoundTag mobDataTag = mobCaptureData.data();
    ValueInput valueInput =
        TagValueInput.create(
            ProblemReporter.DISCARDING, entity.level().registryAccess(), mobDataTag);
    entity.load(valueInput);

    // Get safe spawn position.
    BlockPos safeSpawnBlockPos = getSafeSpawnPos(serverLevel, blockPos);
    if (safeSpawnBlockPos == null) {
      log.warn(
          "{} Unable to release mob {} with data:{} at block position {}.",
          LOG_PREFIX,
          entityType,
          mobCaptureData.data(),
          blockPos);
      return false;
    }

    // Spawn entity at safe spawn position.
    entity.snapTo(
        safeSpawnBlockPos.getX() + 0.5D,
        safeSpawnBlockPos.getY(),
        safeSpawnBlockPos.getZ() + 0.5D,
        0.0F,
        0.0F);
    serverLevel.addFreshEntity(entity);
    log.debug(
        "{} Released mob {} with data:{} at block position {}.",
        LOG_PREFIX,
        entityType,
        mobCaptureData.data(),
        safeSpawnBlockPos);

    return true;
  }

  public static ItemStack createMobCaptureCard(ItemLike itemLike, EntityType<?> entityType) {
    if (itemLike == null || entityType == null) {
      return null;
    }
    return createMobCaptureCard(itemLike, new MobCaptureData(entityType).withFoil(false));
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
      ItemLike itemLike, EntityType<?> entityType, String variant, MobColor mobColor) {
    if (itemLike == null || entityType == null) {
      return null;
    }
    MobCaptureData mobCaptureData = new MobCaptureData(entityType).withFoil(false);
    CompoundTag compoundTag = new CompoundTag();

    // Set variant and corresponding data.
    if (variant != null && !variant.isEmpty()) {
      mobCaptureData = mobCaptureData.withVariant(variant);
      if (entityType == EntityTypes.CAT || entityType == EntityTypes.FROG) {
        compoundTag.putString(VARIANT_TAG, variant);
      }
    }

    // Set dye color and corresponding data.
    if (mobColor != null && mobColor != MobColor.NONE) {
      mobCaptureData = mobCaptureData.withColor(mobColor);
      compoundTag.putInt(COLOR_TAG, mobColor.getDyeColor().getId());
    }

    // Return item stack with mob capture data.
    return createMobCaptureCard(itemLike, mobCaptureData.withData(compoundTag));
  }

  public static void writeMobCaptureData(ItemStack itemStack, MobCaptureData mobCaptureData) {
    if (itemStack == null || itemStack.isEmpty() || mobCaptureData == null) {
      return;
    }

    // Guard against a null entity type. Storing such a component would throw later during NBT
    // serialization (byNameCodec encode -> NPE) and abort the whole block entity save, wiping all
    // slots. Skip writing and log enough context to diagnose the source.
    if (mobCaptureData.entityType() == null) {
      log.error(
          "{} Refusing to write mob capture data with null entity type to {} (type='{}', data={}). "
              + "This would corrupt saving - please report this with the surrounding actions.",
          LOG_PREFIX,
          itemStack,
          mobCaptureData.type(),
          mobCaptureData.data());
      return;
    }

    itemStack.set(DataComponents.MOB_CAPTURE_DATA, mobCaptureData);
  }

  public static boolean hasMobCaptureData(ItemStack itemStack) {
    return itemStack != null
        && !itemStack.isEmpty()
        && itemStack.has(DataComponents.MOB_CAPTURE_DATA)
        && !itemStack.getOrDefault(DataComponents.MOB_CAPTURE_DATA, MobCaptureData.EMPTY).isEmpty();
  }

  public static MobCaptureData getMobCaptureData(ItemStack itemStack, Level level) {
    if (itemStack == null || itemStack.isEmpty()) {
      return null;
    }

    // Use MobCaptureData from item stack, if available.
    if (itemStack.has(DataComponents.MOB_CAPTURE_DATA)) {
      return itemStack.get(DataComponents.MOB_CAPTURE_DATA);
    }

    // Try to get mob capture data from item stack like spawn eggs or similar.
    return new MobCaptureData(
        itemStack,
        itemStack
            .getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, CustomData.EMPTY)
            .copyTag(),
        level);
  }

  public static boolean isSafeSpawnPos(Level level, BlockPos blockPos) {
    // Target position must be passable (not solid)
    if (!level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty()) {
      return false;
    }

    // Surface directly below must be solid enough to stand on
    BlockState surfaceState = level.getBlockState(blockPos.below());
    VoxelShape surfaceShape = surfaceState.getCollisionShape(level, blockPos.below());
    double surfaceHeight = surfaceShape.isEmpty() ? 0.0 : surfaceShape.bounds().maxY;

    return !surfaceState.isAir() && surfaceHeight >= 0.5;
  }

  public static BlockPos getSafeSpawnPos(Level level, BlockPos blockPos) {
    if (isSafeSpawnPos(level, blockPos)) {
      return blockPos;
    }

    for (int dx = -1; dx <= 1; dx++) {
      for (int dz = -1; dz <= 1; dz++) {
        if (dx == 0 && dz == 0) continue;

        BlockPos nearbyPos = blockPos.offset(dx, 0, dz);
        if (isSafeSpawnPos(level, nearbyPos)) {
          return nearbyPos;
        }
      }
    }

    return null;
  }
}
