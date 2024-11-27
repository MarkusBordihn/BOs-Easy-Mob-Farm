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

public class EntityScalingManager {

  private static final float MAX_BLOCK_SCALE = 0.85f;
  private static final float MAX_UI_WIDTH_PIXELS = 47.0f;
  private static final float MAX_UI_HEIGHT_PIXELS = 71.0f;
  private static final float DEFAULT_SCALE_BLOCK = 0.40f;
  private static final float DEFAULT_SCALE_UI = 0.40f;

  private static final Map<Class<? extends Entity>, Float> blockScaleCache = new HashMap<>();
  private static final Map<Class<? extends Entity>, Integer> uiScaleCache = new HashMap<>();

  private EntityScalingManager() {}

  public static float getBlockScale(Entity entity) {
    return blockScaleCache.computeIfAbsent(
        entity.getClass(),
        cls -> {
          // Get entity width and height from bounding box or dimensions.
          float entityWidth;
          float entityHeight;
          if (entity.getBoundingBox().getXsize() > 0 && entity.getBoundingBox().getYsize() > 0) {
            entityWidth = (float) entity.getBoundingBox().getXsize();
            entityHeight = (float) entity.getBoundingBox().getYsize();
          } else {
            entityWidth = entity.getDimensions(entity.getPose()).width();
            entityHeight = entity.getDimensions(entity.getPose()).height();
          }

          // Return default scale if entity width or height is not available.
          if (entityWidth == 0 || entityHeight == 0) {
            return DEFAULT_SCALE_BLOCK;
          }

          // Calculate scale factor for block.
          if ((entityWidth < MAX_BLOCK_SCALE && entityHeight < MAX_BLOCK_SCALE)
              || (entityWidth * DEFAULT_SCALE_BLOCK < MAX_BLOCK_SCALE
                  && entityHeight * DEFAULT_SCALE_BLOCK < MAX_BLOCK_SCALE)) {
            return DEFAULT_SCALE_BLOCK;
          }
          float scaleFactor = Math.max(entityWidth, entityHeight) / MAX_BLOCK_SCALE;
          return scaleFactor > 1.0f ? MAX_BLOCK_SCALE / scaleFactor : MAX_BLOCK_SCALE;
        });
  }

  public static int getUIScale(Entity entity) {
    return uiScaleCache.computeIfAbsent(
        entity.getClass(),
        cls -> {

          // Get entity width and height from bounding box or dimensions.
          float entityWidth;
          float entityHeight;
          if (entity.getBoundingBox().getXsize() > 0 && entity.getBoundingBox().getYsize() > 0) {
            entityWidth = (float) entity.getBoundingBox().getXsize();
            entityHeight = (float) entity.getBoundingBox().getYsize();
          } else {
            entityWidth = entity.getDimensions(entity.getPose()).width();
            entityHeight = entity.getDimensions(entity.getPose()).height();
          }

          // Return default scale if entity width or height is not available.
          if (entityWidth == 0 || entityHeight == 0) {
            return Math.round(DEFAULT_SCALE_UI * 9);
          }

          // Calculate scale factor for UI.
          if ((entityWidth < MAX_UI_WIDTH_PIXELS && entityHeight < MAX_UI_HEIGHT_PIXELS)
              || (entityWidth * DEFAULT_SCALE_UI < MAX_UI_WIDTH_PIXELS
                  && entityHeight * DEFAULT_SCALE_UI < MAX_UI_HEIGHT_PIXELS)) {
            return (int) (DEFAULT_SCALE_UI * Math.min(MAX_UI_WIDTH_PIXELS, MAX_UI_HEIGHT_PIXELS));
          }
          float scaleFactor =
              Math.min(
                  MAX_UI_WIDTH_PIXELS / (entityWidth * 16),
                  MAX_UI_HEIGHT_PIXELS / (entityHeight * 16));
          return Math.round(scaleFactor * 9);
        });
  }
}
