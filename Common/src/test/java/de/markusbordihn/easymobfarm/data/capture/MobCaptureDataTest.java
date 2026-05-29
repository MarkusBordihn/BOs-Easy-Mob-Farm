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

import net.minecraft.world.item.Rarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MobCaptureDataTest {

  private static final int MAX_ID_LIMIT = 16777216;

  private static MobCaptureData card(String type, String variant, boolean isFoil) {
    return new MobCaptureData(null, type, null, null, null, variant, Rarity.COMMON, isFoil);
  }

  @Test
  void cardIdIsConsistentForSameInputs() {
    MobCaptureData data = card("minecraft:bee", null, false);
    Assertions.assertEquals(data.getCardId(), data.getCardId());
  }

  @Test
  void cardIdIsInValidRange() {
    Assertions.assertTrue(card("minecraft:bee", null, false).getCardId() >= 0);
    Assertions.assertTrue(card("minecraft:bee", null, false).getCardId() < MAX_ID_LIMIT);
  }

  @Test
  void differentTypesProduceDifferentIds() {
    Assertions.assertNotEquals(
        card("minecraft:bee", null, false).getCardId(),
        card("minecraft:cow", null, false).getCardId());
  }

  @Test
  void foilChangesCardId() {
    Assertions.assertNotEquals(
        card("minecraft:bee", null, false).getCardId(),
        card("minecraft:bee", null, true).getCardId());
  }

  @Test
  void variantChangesCardId() {
    Assertions.assertNotEquals(
        card("minecraft:pillager", null, false).getCardId(),
        card("minecraft:pillager", "leader", false).getCardId());
  }

  @Test
  void nullTypeProducesStableId() {
    MobCaptureData data = card(null, null, false);
    int id = data.getCardId();
    Assertions.assertTrue(id >= 0 && id < MAX_ID_LIMIT);
    Assertions.assertEquals(id, data.getCardId());
  }
}
