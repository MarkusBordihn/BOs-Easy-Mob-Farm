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

import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.DyeColor;

public class MobColorData {

  public static final String COLOR_TAG = "Color";

  private static final Map<VillagerType, DyeColor> VILLAGER_COLOR_MAP = new HashMap<>();

  static {
    // Villager color map
    VILLAGER_COLOR_MAP.put(VillagerType.PLAINS, DyeColor.LIGHT_GRAY);
    VILLAGER_COLOR_MAP.put(VillagerType.DESERT, DyeColor.YELLOW);
    VILLAGER_COLOR_MAP.put(VillagerType.SAVANNA, DyeColor.ORANGE);
    VILLAGER_COLOR_MAP.put(VillagerType.TAIGA, DyeColor.BROWN);
    VILLAGER_COLOR_MAP.put(VillagerType.SNOW, DyeColor.WHITE);
    VILLAGER_COLOR_MAP.put(VillagerType.JUNGLE, DyeColor.GREEN);
    VILLAGER_COLOR_MAP.put(VillagerType.SWAMP, DyeColor.PURPLE);
  }

  private MobColorData() {}

  public static DyeColor getColor(final EntityType<?> entityType) {
    return null;
  }

  public static DyeColor getColor(final LivingEntity livingEntity) {
    if (livingEntity instanceof Sheep sheep) {
      return sheep.getColor();
    } else if (livingEntity instanceof Villager villager) {
      return VILLAGER_COLOR_MAP.get(villager.getVillagerData().getType());
    }
    return null;
  }

  public static DyeColor getColor(final CompoundTag compoundTag) {
    if (compoundTag == null || !compoundTag.contains(COLOR_TAG)) {
      return null;
    }
    return getColor(compoundTag.getString(COLOR_TAG));
  }

  public static DyeColor getColor(final String color) {
    return DyeColor.byName(color, null);
  }
}
