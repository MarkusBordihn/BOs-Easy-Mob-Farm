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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.npc.Villager;

public class MobVariantData {

  public static final String VARIANT_TAG = "Variant";

  private MobVariantData() {}

  public static String getVariant(final EntityType<?> entityType) {
    return null;
  }

  public static String getVariant(final LivingEntity livingEntity) {
    if (livingEntity instanceof Cat cat) {
      return ((ResourceKey) cat.getVariant().unwrapKey().orElse(CatVariant.BLACK))
          .location()
          .toString()
          .replace("minecraft:", "");
    } else if (livingEntity instanceof Villager villager) {
      return villager.getVillagerData().getProfession().name();
    }
    return null;
  }

  public static String getVariant(final CompoundTag compoundTag) {
    if (compoundTag == null) {
      return null;
    }
    if (compoundTag.contains(VARIANT_TAG)) {
      return compoundTag.getString(VARIANT_TAG);
    }
    if (compoundTag.contains(VARIANT_TAG.toLowerCase())) {
      return compoundTag.getString(VARIANT_TAG.toLowerCase());
    }
    return null;
  }
}
