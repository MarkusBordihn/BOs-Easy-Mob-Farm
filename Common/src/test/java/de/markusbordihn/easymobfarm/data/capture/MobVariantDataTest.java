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

import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.animal.FrogVariant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MobVariantDataTest {

  @BeforeAll
  static void bootstrap() {
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
  }

  @Test
  void sizeOneIsTiny() {
    Assertions.assertEquals(MobVariantData.TINY_VARIANT, MobVariantData.getSizeVariant(1.0f));
  }

  @Test
  void sizeTwoIsSmall() {
    Assertions.assertEquals(MobVariantData.SMALL_VARIANT, MobVariantData.getSizeVariant(2.0f));
  }

  @Test
  void sizeThreeIsMedium() {
    Assertions.assertEquals(MobVariantData.MEDIUM_VARIANT, MobVariantData.getSizeVariant(3.0f));
  }

  @Test
  void sizeFourIsLarge() {
    Assertions.assertEquals(MobVariantData.LARGE_VARIANT, MobVariantData.getSizeVariant(4.0f));
  }

  @Test
  void sizeZeroIsTiny() {
    Assertions.assertEquals(MobVariantData.TINY_VARIANT, MobVariantData.getSizeVariant(0.0f));
  }

  @Test
  void sizeAboveFourIsLarge() {
    Assertions.assertEquals(MobVariantData.LARGE_VARIANT, MobVariantData.getSizeVariant(16.0f));
  }

  @Test
  void nullVariantReturnsTemperateFrog() {
    Assertions.assertEquals(FrogVariant.TEMPERATE, MobVariantData.getFrogVariant(null));
  }

  @Test
  void knownVariantReturnsMatchingFrogVariant() {
    Assertions.assertEquals(FrogVariant.COLD, MobVariantData.getFrogVariant("cold"));
  }

  @Test
  void nullTagReturnsNull() {
    Assertions.assertNull(MobVariantData.getVariant((CompoundTag) null));
  }

  @Test
  void emptyTagReturnsNull() {
    Assertions.assertNull(MobVariantData.getVariant(new CompoundTag()));
  }

  @Test
  void tagWithVariantReturnsVariant() {
    CompoundTag tag = new CompoundTag();
    tag.putString(MobVariantData.VARIANT_TAG, "leader");
    Assertions.assertEquals("leader", MobVariantData.getVariant(tag));
  }

  @Test
  void tagWithLowercaseVariantKeyReturnsVariant() {
    CompoundTag tag = new CompoundTag();
    tag.putString("variant", "cold");
    Assertions.assertEquals("cold", MobVariantData.getVariant(tag));
  }
}
