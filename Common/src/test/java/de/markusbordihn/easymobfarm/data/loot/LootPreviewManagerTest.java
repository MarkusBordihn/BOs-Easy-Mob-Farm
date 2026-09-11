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

package de.markusbordihn.easymobfarm.data.loot;

import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import java.util.List;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LootPreviewManagerTest {

  private static final int FARM_SAMPLE_ROLLS = 3;

  @BeforeAll
  static void bootstrap() {
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
  }

  @AfterEach
  void clearCache() {
    LootPreviewManager.reset();
  }

  @Test
  void cachedPreviewIsReturnedWithoutLevel() {
    LootPreviewCache.setServerCachedPreview(
        EntityTypes.COW, List.of(new ItemStack(Items.LEATHER)), FARM_SAMPLE_ROLLS);

    List<ItemStack> preview =
        LootPreviewManager.getOrCompute(new MobCaptureData(EntityTypes.COW), null);

    Assertions.assertEquals(1, preview.size());
    Assertions.assertEquals(Items.LEATHER, preview.get(0).getItem());
  }

  @Test
  void missingCaptureDataReturnsEmptyPreview() {
    Assertions.assertTrue(LootPreviewManager.getOrCompute(null, null).isEmpty());
  }

  @Test
  void resetClearsCachedPreviews() {
    LootPreviewCache.setServerCachedPreview(
        EntityTypes.COW, List.of(new ItemStack(Items.LEATHER)), FARM_SAMPLE_ROLLS);

    LootPreviewManager.reset();

    Assertions.assertFalse(LootPreviewCache.isServerCacheValid(EntityTypes.COW, FARM_SAMPLE_ROLLS));
  }
}
