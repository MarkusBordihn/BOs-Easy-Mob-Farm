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

package de.markusbordihn.easymobfarm.client.renderer.manager;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.capture.MobVariantData;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.fish.AbstractFish;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RendererManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Map<BlockPos, Entity> entityMap = new HashMap<>();
  private static final Map<BlockPos, ItemStack> unusableCapturedMobs = new HashMap<>();
  private static int validationCounter = 0;

  private RendererManager() {}

  public static Entity getOrCreateEntity(final MobFarmBlockEntity mobFarmBlockEntity) {
    if (mobFarmBlockEntity == null) {
      return null;
    }

    BlockPos blockPos = mobFarmBlockEntity.getBlockPos();
    if (!mobFarmBlockEntity.hasCapturedMob()) {
      entityMap.remove(blockPos);
      unusableCapturedMobs.remove(blockPos);
      return null;
    }

    // Check for cached entity and validate it every 100 calls.
    Entity cachedEntity = entityMap.get(blockPos);
    if (cachedEntity != null) {
      if (++validationCounter >= 100) {
        validationCounter = 0;
        MobCaptureData mobCaptureData = mobFarmBlockEntity.getMobCaptureData();
        if (mobCaptureData != null && cachedEntity.getType() != mobCaptureData.entityType()) {
          entityMap.remove(blockPos);
          cachedEntity = null;
        }
      }
      if (cachedEntity != null) {
        return cachedEntity;
      }
    }

    ItemStack capturedMob = mobFarmBlockEntity.getCapturedMob();
    if (wasAlreadyRejected(blockPos, capturedMob)) {
      return null;
    }

    Entity entity = createEntity(mobFarmBlockEntity);
    if (entity != null) {
      entityMap.put(blockPos, entity);
      unusableCapturedMobs.remove(blockPos);
    } else {
      unusableCapturedMobs.put(blockPos, capturedMob);
    }
    return entity;
  }

  private static boolean wasAlreadyRejected(final BlockPos blockPos, final ItemStack capturedMob) {
    return unusableCapturedMobs.get(blockPos) == capturedMob;
  }

  public static Entity getEntity(final BlockPos blockPos) {
    return entityMap.get(blockPos);
  }

  public static void removeEntity(final MobFarmBlockEntity mobFarmBlockEntity) {
    removeEntity(mobFarmBlockEntity.getBlockPos());
  }

  public static void removeEntity(final BlockPos blockPos) {
    entityMap.remove(blockPos);
    unusableCapturedMobs.remove(blockPos);
  }

  private static Entity createEntity(final MobFarmBlockEntity mobFarmBlockEntity) {
    if (mobFarmBlockEntity == null || mobFarmBlockEntity.getLevel() == null) {
      log.error("Unable to create entity for Mob Farm Block Entity {}", mobFarmBlockEntity);
      return null;
    }

    // Get Mob Capture data from Mob Farm Block Entity
    MobCaptureData mobCaptureData = mobFarmBlockEntity.getMobCaptureData();
    if (mobCaptureData == null) {
      log.error("Unable to get Mob Capture data from Mob Farm Block Entity {}", mobFarmBlockEntity);
      return null;
    }

    // Get and validate entity type
    EntityType<?> entityType = mobCaptureData.entityType();
    if (entityType == null) {
      log.debug("Unable to get entity type from Mob Capture data {}", mobCaptureData);
      return null;
    }

    // Create entity
    Entity entity = entityType.create(mobFarmBlockEntity.getLevel(), EntitySpawnReason.EVENT);
    if (entity == null) {
      log.error("Unable to create entity for entity type {}", entityType);
      return null;
    }

    // Reset entity position and movement to prevent unwanted animations.
    entity.tick();
    entity.setPos(0, 0, 0);
    entity.setDeltaMovement(Vec3.ZERO);
    entity.xOld = 0;
    entity.yOld = 0;
    entity.zOld = 0;
    entity.setOnGround(true);
    entity.flyDist = 0;
    entity.tick();

    // Set random head and body rotation
    entity.setYHeadRot(mobFarmBlockEntity.getLevel().random.nextFloat() * 60.0F);
    entity.setYBodyRot(mobFarmBlockEntity.getLevel().random.nextFloat() * 10.0F);
    entity.xRotO = entity.getXRot();
    entity.yRotO = entity.getYRot();

    // Load entity data from Mob Capture data
    CompoundTag mobDataTag = mobCaptureData.data();
    ValueInput valueInput =
        TagValueInput.create(
            ProblemReporter.DISCARDING, entity.level().registryAccess(), mobDataTag);
    entity.load(valueInput);
    entity.tick();

    // Set additional entity properties for fish entities
    if (entity instanceof AbstractFish fishEntity) {
      fishEntity.setNoGravity(true);
      fishEntity.setSwimming(true);
    }

    // Set additional entity properties for sheep entities
    if (entity instanceof Sheep sheepEntity) {
      sheepEntity.setSheared(false);
      if (mobCaptureData.hasColor()) {
        sheepEntity.setColor(mobCaptureData.color().getDyeColor());
      }
    }

    // Ensure Pillager Leaders always display with the Ominous Banner.
    if (entity instanceof Pillager pillager
        && MobVariantData.LEADER_VARIANT.equals(mobCaptureData.variant())
        && pillager.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
      pillager.setItemSlot(
          EquipmentSlot.HEAD,
          Raid.getOminousBannerInstance(
              mobFarmBlockEntity
                  .getLevel()
                  .registryAccess()
                  .lookupOrThrow(Registries.BANNER_PATTERN)));
    }

    // Disable AI for the entity and other performance improvements
    if (entity instanceof PathfinderMob newPathfinderMob) {
      newPathfinderMob.setNoAi(true);
      newPathfinderMob.setSilent(true);
      newPathfinderMob.noPhysics = true;
    }
    entity.noPhysics = true;

    return entity;
  }
}
