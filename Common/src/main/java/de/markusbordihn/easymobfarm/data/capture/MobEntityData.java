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

import de.markusbordihn.easymobfarm.config.MobCaptureCardConfig;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.TagValueOutput;

public class MobEntityData {

  public static final String DATA_TAG = "Data";
  protected static final String AGE_TAG = "Age";
  protected static final String HEALTH_TAG = "Health";
  protected static final String INVENTORY_TAG = "Inventory";
  protected static final int NORMALIZED_BABY_AGE = -24000;
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
  protected static final Set<String> SAFE_TO_REMOVE_MOB_CAPTURE_CARD_TAGS =
      Set.of(
          "AbsorptionAmount",
          "ActiveEffects",
          "AngerTime",
          "Brain",
          "CanBreakDoors",
          "CanPickUpLoot",
          "CanUpdate",
          "CurativeItems",
          "DrownedConversionTime",
          "Health",
          "Invulnerable",
          "LeftHanded",
          "PersistenceRequired",
          "TimeInOverworld",
          "Attributes",
          "Bred",
          "EatingHaystack",
          "ForcedAge",
          "Glowing",
          "InLove",
          "Leash",
          "LoveCause",
          "NoAI",
          "NoGravity",
          "Owner",
          "Silent",
          "Temper",
          "TicksFrozen",
          "UUID");

  private MobEntityData() {}

  public static CompoundTag getMobEntityData(EntityType<?> entityType) {
    return new CompoundTag();
  }

  public static CompoundTag getMobEntityData(LivingEntity livingEntity) {

    TagValueOutput valueOutput =
        TagValueOutput.createWithContext(
            ProblemReporter.DISCARDING, livingEntity.level().registryAccess());

    livingEntity.saveWithoutId(valueOutput);

    CompoundTag compoundTag = valueOutput.buildResult();

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

    if (compoundTag.contains(DATA_TAG) && compoundTag.getCompound(DATA_TAG).isPresent()) {
      compoundTag = compoundTag.getCompound(DATA_TAG).get();
    }
    return removeSafeToRemoveBaseTags(compoundTag);
  }

  private static CompoundTag removeSafeToRemoveBaseTags(CompoundTag compoundTag) {
    CompoundTag cleanedCompoundTag = new CompoundTag();
    for (String key : compoundTag.keySet()) {
      if (!SAFE_TO_REMOVE_BASE_TAGS.contains(key)) {
        cleanedCompoundTag.put(key, compoundTag.get(key));
      }
    }
    return cleanedCompoundTag;
  }

  private static CompoundTag removeUnsafeToRemoveBaseTags(CompoundTag compoundTag) {
    CompoundTag cleanedCompoundTag = new CompoundTag();
    for (String key : compoundTag.keySet()) {
      if (!UNSAFE_TO_REMOVE_BASE_TAGS.contains(key)) {
        cleanedCompoundTag.put(key, compoundTag.get(key));
      }
    }
    return cleanedCompoundTag;
  }

  public static CompoundTag removeSafeToRemoveMobCaptureCardTags(CompoundTag compoundTag) {
    CompoundTag cleanedCompoundTag = new CompoundTag();
    for (String key : compoundTag.keySet()) {
      if (SAFE_TO_REMOVE_MOB_CAPTURE_CARD_TAGS.contains(key)
          || MobCaptureCardConfig.additionalCardTagsToRemove.contains(key)) {
        continue;
      }

      if (key.equals(INVENTORY_TAG)) {
        if (compoundTag.getList(key).filter(listTag -> !listTag.isEmpty()).isPresent()) {
          cleanedCompoundTag.put(key, compoundTag.get(key));
        }
      } else if (key.equals(AGE_TAG)) {
        if (compoundTag.getIntOr(AGE_TAG, 0) < 0) {
          cleanedCompoundTag.putInt(AGE_TAG, NORMALIZED_BABY_AGE);
        }
      } else {
        cleanedCompoundTag.put(key, compoundTag.get(key));
      }
    }
    return cleanedCompoundTag;
  }
}
