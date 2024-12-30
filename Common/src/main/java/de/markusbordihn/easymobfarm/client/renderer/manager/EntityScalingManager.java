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
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.monster.ElderGuardian;

public class EntityScalingManager {

  private static final float MAX_BLOCK_SCALE = 0.80f;
  private static final float DEFAULT_SCALE_BLOCK = 0.30f;

  private static final Map<Class<? extends Entity>, Float> entityScaleCache = new HashMap<>();

  private EntityScalingManager() {}

  public static float getEntityScale(Entity entity) {
    return entityScaleCache.computeIfAbsent(
        entity.getClass(),
        cls -> {
          // Get entity width and height from bounding box or dimensions.
          float entityWidth;
          float entityHeight;
          if (entity.getBoundingBox().getXsize() > 0 && entity.getBoundingBox().getYsize() > 0) {
            entityWidth = (float) entity.getBoundingBox().getXsize();
            entityHeight = (float) entity.getBoundingBox().getYsize();
          } else {
            entityWidth = entity.getDimensions(entity.getPose()).width;
            entityHeight = entity.getDimensions(entity.getPose()).height;
          }

          // Return default scale if entity width or height is not available.
          if (entityWidth == 0 || entityHeight == 0) {
            return DEFAULT_SCALE_BLOCK;
          }

          // Add extra scale numbers for specific entities to consider additional
          // space requirements for body parts like wings or tails.
          if (entity instanceof ElderGuardian) {
            entityWidth *= 1.8f;
          }

          // Calculate scale factor for block.
          if ((entityWidth < MAX_BLOCK_SCALE && entityHeight < MAX_BLOCK_SCALE)
              || (entityWidth * DEFAULT_SCALE_BLOCK < MAX_BLOCK_SCALE
                  && entityHeight * DEFAULT_SCALE_BLOCK < MAX_BLOCK_SCALE)) {
            return DEFAULT_SCALE_BLOCK;
          }
          float scaleFactor = Math.max(entityWidth, entityHeight) / MAX_BLOCK_SCALE;
          if (scaleFactor > 1.0f) {
            if (entity instanceof FlyingMob
                || (entity instanceof FlyingAnimal flyingAnimal && flyingAnimal.isFlying())) {
              return MAX_BLOCK_SCALE / scaleFactor * 0.60f;
            }
            return MAX_BLOCK_SCALE / scaleFactor;
          }
          return MAX_BLOCK_SCALE;
        });
  }

  public static float getUIScale(Entity entity) {
    float entityScale = getEntityScale(entity);
    return entityScale * 45;
  }
}
