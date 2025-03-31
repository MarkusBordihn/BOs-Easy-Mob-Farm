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

import io.netty.buffer.ByteBuf;
import java.util.Locale;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.DyeColor;

public enum MobColor implements StringRepresentable {
  NONE(0, null),
  BLACK(1, DyeColor.BLACK),
  BLUE(2, DyeColor.BLUE),
  BROWN(3, DyeColor.BROWN),
  CYAN(4, DyeColor.CYAN),
  GRAY(5, DyeColor.GRAY),
  GREEN(6, DyeColor.GREEN),
  LIGHT_BLUE(7, DyeColor.LIGHT_BLUE),
  LIGHT_GRAY(8, DyeColor.LIGHT_GRAY),
  LIME(9, DyeColor.LIME),
  MAGENTA(10, DyeColor.MAGENTA),
  ORANGE(11, DyeColor.ORANGE),
  PINK(12, DyeColor.PINK),
  PURPLE(13, DyeColor.PURPLE),
  RED(14, DyeColor.RED),
  WHITE(15, DyeColor.WHITE),
  YELLOW(16, DyeColor.YELLOW);

  public static final StringRepresentable.EnumCodec<MobColor> CODEC =
      StringRepresentable.fromEnum(MobColor::values);
  private static final IntFunction<MobColor> BY_ID =
      ByIdMap.continuous(MobColor::getId, values(), OutOfBoundsStrategy.ZERO);
  public static final StreamCodec<ByteBuf, MobColor> STREAM_CODEC =
      ByteBufCodecs.idMapper(BY_ID, MobColor::getId);
  private final int id;
  private final String name;
  private final DyeColor dyeColor;

  MobColor(int id, DyeColor dyeColor) {
    this.id = id;
    this.name = this.name().toLowerCase(Locale.ROOT);
    this.dyeColor = dyeColor;
  }

  public static MobColor byName(String colorName) {
    if (colorName == null || colorName.isEmpty()) {
      return MobColor.NONE;
    }
    try {
      return MobColor.valueOf(colorName.toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException e) {
      return MobColor.NONE;
    }
  }

  public static MobColor byDyeColor(DyeColor dyeColor) {
    if (dyeColor == null) {
      return MobColor.NONE;
    }
    return switch (dyeColor) {
      case BLACK -> MobColor.BLACK;
      case BLUE -> MobColor.BLUE;
      case BROWN -> MobColor.BROWN;
      case CYAN -> MobColor.CYAN;
      case GRAY -> MobColor.GRAY;
      case GREEN -> MobColor.GREEN;
      case LIGHT_BLUE -> MobColor.LIGHT_BLUE;
      case LIGHT_GRAY -> MobColor.LIGHT_GRAY;
      case LIME -> MobColor.LIME;
      case MAGENTA -> MobColor.MAGENTA;
      case ORANGE -> MobColor.ORANGE;
      case PINK -> MobColor.PINK;
      case PURPLE -> MobColor.PURPLE;
      case RED -> MobColor.RED;
      case WHITE -> MobColor.WHITE;
      case YELLOW -> MobColor.YELLOW;
    };
  }

  public static MobColor byVillagerType(ResourceKey<VillagerType> villagerType) {
    if (villagerType == null) {
      return MobColor.NONE;
    }
    if (villagerType == VillagerType.PLAINS) {
      return MobColor.LIGHT_GRAY;
    } else if (villagerType == VillagerType.DESERT) {
      return MobColor.YELLOW;
    } else if (villagerType == VillagerType.SAVANNA) {
      return MobColor.ORANGE;
    } else if (villagerType == VillagerType.TAIGA) {
      return MobColor.BROWN;
    } else if (villagerType == VillagerType.SNOW) {
      return MobColor.WHITE;
    } else if (villagerType == VillagerType.JUNGLE) {
      return MobColor.GREEN;
    } else if (villagerType == VillagerType.SWAMP) {
      return MobColor.PURPLE;
    }
    return MobColor.NONE;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public DyeColor getDyeColor() {
    return this.dyeColor;
  }

  public String getSerializedName() {
    return this.name;
  }

  public String toString() {
    return this.name;
  }
}
