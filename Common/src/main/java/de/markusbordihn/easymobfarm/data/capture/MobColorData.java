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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.npc.Villager;

public class MobColorData {

  public static final String COLOR_TAG = "Color";

  private MobColorData() {}

  public static MobColor getColor(final EntityType<?> entityType) {
    return MobColor.NONE;
  }

  public static MobColor getColor(final LivingEntity livingEntity) {
    if (livingEntity instanceof Sheep sheep) {
      return MobColor.byDyeColor(sheep.getColor());
    } else if (livingEntity instanceof Villager villager) {
      return MobColor.byVillagerType(villager.getVillagerData().type().unwrapKey().orElse(null));
    }
    return MobColor.NONE;
  }

  public static MobColor getColor(final CompoundTag compoundTag) {
    if (compoundTag == null || !compoundTag.contains(COLOR_TAG)) {
      return MobColor.NONE;
    }
    return MobColor.byName(compoundTag.getString(COLOR_TAG).orElse(""));
  }
}
