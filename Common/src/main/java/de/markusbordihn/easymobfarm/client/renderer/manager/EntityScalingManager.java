/*
 * Copyright 2023 Markus Bordihn
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

package de.markusbordihn.easymobfarm.client.renderer.manager;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;

public class EntityScalingManager {

  private static final float MAX_BLOCK_SCALE = 0.92f;
  private static final float MAX_UI_WIDTH_PIXELS = 50.0f;
  private static final float MAX_UI_HEIGHT_PIXELS = 80.0f;
  private static final float DEFAULT_SCALE_BLOCK = 0.40f;
  private static final float DEFAULT_SCALE_UI = 0.45f;

  private static final Map<Class<? extends Entity>, Float> blockScaleCache = new HashMap<>();
  private static final Map<Class<? extends Entity>, Integer> uiScaleCache = new HashMap<>();

  private EntityScalingManager() {}

  public static float getBlockScale(Entity entity) {
    return blockScaleCache.computeIfAbsent(
        entity.getClass(),
        cls -> {
          EntityDimensions dimensions = entity.getDimensions(entity.getPose());
          if ((dimensions.width() < MAX_BLOCK_SCALE && dimensions.height() < MAX_BLOCK_SCALE)
              || (dimensions.width() * DEFAULT_SCALE_BLOCK < MAX_BLOCK_SCALE
                  && dimensions.height() * DEFAULT_SCALE_BLOCK < MAX_BLOCK_SCALE)) {
            return DEFAULT_SCALE_BLOCK;
          }
          float scaleFactor = Math.max(dimensions.width(), dimensions.height()) / MAX_BLOCK_SCALE;
          return scaleFactor > 1.0f ? MAX_BLOCK_SCALE / scaleFactor : MAX_BLOCK_SCALE;
        });
  }

  public static int getUIScale(Entity entity) {
    return uiScaleCache.computeIfAbsent(
        entity.getClass(),
        cls -> {
          EntityDimensions dimensions = entity.getDimensions(entity.getPose());
          if ((dimensions.width() < MAX_UI_WIDTH_PIXELS
                  && dimensions.height() < MAX_UI_HEIGHT_PIXELS)
              || (dimensions.width() * DEFAULT_SCALE_UI < MAX_UI_WIDTH_PIXELS
                  && dimensions.height() * DEFAULT_SCALE_UI < MAX_UI_HEIGHT_PIXELS)) {
            return (int) (DEFAULT_SCALE_UI * Math.min(MAX_UI_WIDTH_PIXELS, MAX_UI_HEIGHT_PIXELS));
          }
          float scaleFactor =
              Math.min(
                  MAX_UI_WIDTH_PIXELS / (dimensions.width() * 16),
                  MAX_UI_HEIGHT_PIXELS / (dimensions.height() * 16));
          return Math.round(scaleFactor * 10);
        });
  }
}
