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

package de.markusbordihn.easymobfarm.data.mobfarm;

import io.netty.buffer.ByteBuf;
import java.util.Locale;
import java.util.function.IntFunction;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.StringRepresentable;

public enum MobFarmTierLevel implements StringRepresentable {
  TIER_LEVEL_0(0),
  TIER_LEVEL_1(1),
  TIER_LEVEL_2(2),
  TIER_LEVEL_3(3);

  public static final StringRepresentable.EnumCodec<MobFarmTierLevel> CODEC =
      StringRepresentable.fromEnum(MobFarmTierLevel::values);
  private static final IntFunction<MobFarmTierLevel> BY_ID =
      ByIdMap.continuous(MobFarmTierLevel::getTierLevel, values(), OutOfBoundsStrategy.ZERO);
  public static final StreamCodec<ByteBuf, MobFarmTierLevel> STREAM_CODEC =
      ByteBufCodecs.idMapper(BY_ID, MobFarmTierLevel::getTierLevel);
  private final int tierLevel;
  private final String name;

  MobFarmTierLevel(int tierLevel) {
    this.tierLevel = tierLevel;
    this.name = this.name().toLowerCase(Locale.ROOT);
  }

  public static MobFarmTierLevel getTierLevel(int tierLevel) {
    for (MobFarmTierLevel mobFarmTierLevel : MobFarmTierLevel.values()) {
      if (mobFarmTierLevel.tierLevel == tierLevel) {
        return mobFarmTierLevel;
      }
    }
    return MobFarmTierLevel.TIER_LEVEL_0;
  }

  public int getTierLevel() {
    return this.tierLevel;
  }

  @Override
  public String getSerializedName() {
    return this.name;
  }
}
