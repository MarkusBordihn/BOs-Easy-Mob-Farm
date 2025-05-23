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

import java.util.Locale;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Rarity;

public class MobRarityData {

  public static final String RARITY_TAG = "Rarity";

  private MobRarityData() {}

  public static Rarity getRarity(final EntityType<?> entityType) {
    if (entityType == null) {
      return Rarity.COMMON;
    }

    // Check for rarity based on entity type over the config file.
    MobCaptureCardDefinition mobCaptureCardDefinition =
        MobCaptureCardDefinitionManager.get(entityType);
    if (mobCaptureCardDefinition != null) {
      return mobCaptureCardDefinition.rarity();
    }

    // Get entity name and check for rarity.
    String entityName = entityType.getDescriptionId().toLowerCase(Locale.ROOT);
    if (entityName.contains("epic")) {
      return Rarity.EPIC;
    } else if (entityName.contains("rare")) {
      return Rarity.RARE;
    } else if (entityName.contains("uncommon")) {
      return Rarity.UNCOMMON;
    }

    return Rarity.COMMON;
  }

  public static Rarity getRarity(final LivingEntity livingEntity) {
    if (livingEntity == null) {
      return Rarity.COMMON;
    }
    return getRarity(livingEntity.getType());
  }

  public static Rarity getRarity(final CompoundTag compoundTag) {
    if (compoundTag == null || !compoundTag.contains(RARITY_TAG)) {
      return Rarity.COMMON;
    }
    return Rarity.valueOf(compoundTag.getString(RARITY_TAG));
  }
}
