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

package de.markusbordihn.easymobfarm.data.capture;

import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class MobEntityData {

  public static final String DATA_TAG = "Data";
  protected static final String HEALTH_TAG = "Health";
  protected static final Set<String> SAFE_TO_REMOVE_BASE_TAGS =
      Set.of(
          "Air",
          "Dead",
          "DeathTime",
          "FallDistance",
          "FallFlying",
          "Fire",
          "HurtByTimestamp",
          "HurtTime",
          "InWaterTime",
          "Motion",
          "OnGround",
          "PortalCooldown",
          "Pos",
          "Rotation");
  protected static final Set<String> UNSAFE_TO_REMOVE_BASE_TAGS = Set.of("UUID", "Attributes");

  private MobEntityData() {}

  public static CompoundTag getMobEntityData(EntityType<?> entityType) {
    return new CompoundTag();
  }

  public static CompoundTag getMobEntityData(LivingEntity livingEntity) {
    CompoundTag compoundTag = new CompoundTag();
    livingEntity.saveWithoutId(compoundTag);

    // Check if entity is dead or dying and restore health.
    if (livingEntity.isDeadOrDying()) {
      compoundTag.putFloat(HEALTH_TAG, livingEntity.getMaxHealth());
    }

    // Clean up compound tag for better stackability, if it has no custom name.
    compoundTag = removeSafeToRemoveBaseTags(compoundTag);
    if (!livingEntity.hasCustomName()) {
      compoundTag = removeUnsafeToRemoveBaseTags(compoundTag);
    }

    return compoundTag;
  }

  public static CompoundTag getMobEntityData(CompoundTag compoundTag) {
    if (compoundTag == null) {
      return new CompoundTag();
    }

    if (compoundTag.contains(DATA_TAG)) {
      compoundTag = compoundTag.getCompound(DATA_TAG);
    }
    return removeSafeToRemoveBaseTags(compoundTag);
  }

  private static CompoundTag removeSafeToRemoveBaseTags(CompoundTag compoundTag) {
    CompoundTag cleanedCompoundTag = new CompoundTag();
    for (String key : compoundTag.getAllKeys()) {
      if (!SAFE_TO_REMOVE_BASE_TAGS.contains(key)) {
        cleanedCompoundTag.put(key, compoundTag.get(key));
      }
    }
    return cleanedCompoundTag;
  }

  private static CompoundTag removeUnsafeToRemoveBaseTags(CompoundTag compoundTag) {
    CompoundTag cleanedCompoundTag = new CompoundTag();
    for (String key : compoundTag.getAllKeys()) {
      if (!UNSAFE_TO_REMOVE_BASE_TAGS.contains(key)) {
        cleanedCompoundTag.put(key, compoundTag.get(key));
      }
    }
    return cleanedCompoundTag;
  }
}
