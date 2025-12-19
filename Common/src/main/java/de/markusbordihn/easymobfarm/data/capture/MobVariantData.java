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

import de.markusbordihn.easymobfarm.compat.CompatConstants;
import java.util.Locale;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.CatVariants;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.villager.Villager;

public class MobVariantData {

  public static final String VARIANT_TAG = "Variant";
  public static final String LARGE_VARIANT = "large";
  public static final String MEDIUM_VARIANT = "medium";
  public static final String SMALL_VARIANT = "small";
  public static final String TINY_VARIANT = "tiny";

  private MobVariantData() {}

  public static String getVariant(final EntityType<?> entityType) {
    return "";
  }

  public static String getVariant(final LivingEntity livingEntity) {
    if (livingEntity instanceof Cat cat) {
      return (cat.getVariant().unwrapKey().orElse(CatVariants.BLACK))
          .identifier()
          .toString()
          .replace("minecraft:", "");
    } else if (livingEntity instanceof Villager villager) {
      return villager.getVillagerData().profession().getRegisteredName();
    } else if (livingEntity instanceof MagmaCube magmaCube) {
      return getSizeVariant(magmaCube.getSize());
    } else if (livingEntity instanceof Slime slime) {
      return getSizeVariant(slime.getSize());
    } else if (livingEntity instanceof Frog frog) {
      String frogVariant =
          (frog.getVariant().unwrapKey().orElse(FrogVariants.TEMPERATE))
              .identifier()
              .toString()
              .replace("minecraft:", "");
      if (CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
        frogVariant =
            frogVariant
                .replace(CompatConstants.MOD_SWAMPIER_SWAMPS_ID + ":", "")
                .replace("frog_", "")
                .replace("_variant", "");
      }
      return frogVariant;
    }
    return "";
  }

  public static String getVariant(final CompoundTag compoundTag) {
    if (compoundTag == null) {
      return "";
    }
    if (compoundTag.contains(VARIANT_TAG)) {
      return compoundTag.getString(VARIANT_TAG).orElse("");
    }
    if (compoundTag.contains(VARIANT_TAG.toLowerCase(Locale.ROOT))) {
      return compoundTag.getString(VARIANT_TAG.toLowerCase(Locale.ROOT)).orElse("");
    }
    return "";
  }

  public static String getSizeVariant(float size) {
    if (size <= 1.0f) {
      return TINY_VARIANT;
    }
    if (size <= 2.0f) {
      return SMALL_VARIANT;
    }
    if (size < 4.0f) {
      return MEDIUM_VARIANT;
    }
    if (size >= 4.0f) {
      return LARGE_VARIANT;
    }
    return "";
  }
}
