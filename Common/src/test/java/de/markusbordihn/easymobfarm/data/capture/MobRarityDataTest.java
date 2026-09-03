/*
 * Copyright 2026 Markus Bordihn
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
import net.minecraft.world.item.Rarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MobRarityDataTest {

  private static CompoundTag tagWithRarity(String rarity) {
    CompoundTag compoundTag = new CompoundTag();
    compoundTag.putString(MobRarityData.RARITY_TAG, rarity);
    return compoundTag;
  }

  @Test
  void knownRarityIsParsed() {
    Assertions.assertEquals(Rarity.EPIC, MobRarityData.getRarity(tagWithRarity("EPIC")));
  }

  @Test
  void unknownRarityFallsBackToCommon() {
    Assertions.assertEquals(Rarity.COMMON, MobRarityData.getRarity(tagWithRarity("LEGENDARY")));
  }

  @Test
  void lowerCaseRarityFallsBackToCommon() {
    Assertions.assertEquals(Rarity.COMMON, MobRarityData.getRarity(tagWithRarity("epic")));
  }

  @Test
  void emptyRarityFallsBackToCommon() {
    Assertions.assertEquals(Rarity.COMMON, MobRarityData.getRarity(tagWithRarity("")));
  }

  @Test
  void missingTagReturnsCommon() {
    Assertions.assertEquals(Rarity.COMMON, MobRarityData.getRarity(new CompoundTag()));
  }

  @Test
  void nullTagReturnsCommon() {
    Assertions.assertEquals(Rarity.COMMON, MobRarityData.getRarity((CompoundTag) null));
  }
}
