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

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum MobFarmType implements StringRepresentable {
  ANIMAL_PLAINS_FARM,
  BEE_HIVE_FARM,
  CREATIVE_MOB_FARM,
  DESERT_FARM,
  IRON_GOLEM_FARM,
  LUCKY_DROP_FARM,
  JUNGLE_FARM,
  MONSTER_PLAINS_CAVE_FARM,
  NETHER_FORTRESS_FARM,
  OCEAN_FARM,
  SWAMP_FARM,
  END_FARM,
  NETHER_WASTES_FARM;

  private final String name;

  MobFarmType() {
    this.name = this.name().toLowerCase(Locale.ROOT);
  }

  public String getId() {
    return this.name;
  }

  @Override
  public String getSerializedName() {
    return this.name;
  }
}
