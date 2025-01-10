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
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.Villager;

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
      return (cat.getVariant().unwrapKey().orElse(CatVariant.BLACK))
          .location()
          .toString()
          .replace("minecraft:", "");
    } else if (livingEntity instanceof Villager villager) {
      return villager.getVillagerData().getProfession().name();
    } else if (livingEntity instanceof MagmaCube magmaCube) {
      return getSizeVariant(magmaCube.getSize());
    } else if (livingEntity instanceof Slime slime) {
      return getSizeVariant(slime.getSize());
    }
    return "";
  }

  public static String getVariant(final CompoundTag compoundTag) {
    if (compoundTag == null) {
      return "";
    }
    if (compoundTag.contains(VARIANT_TAG)) {
      return compoundTag.getString(VARIANT_TAG);
    }
    if (compoundTag.contains(VARIANT_TAG.toLowerCase(Locale.ROOT))) {
      return compoundTag.getString(VARIANT_TAG.toLowerCase(Locale.ROOT));
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
