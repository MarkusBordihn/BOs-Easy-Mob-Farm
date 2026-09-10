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

package de.markusbordihn.easymobfarm.client.screen.theme;

import de.markusbordihn.easymobfarm.client.renderer.manager.EntityScalingManager;
import de.markusbordihn.easymobfarm.client.screen.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;

public record EntityPreview(
    int x, int y, int width, int height, int anchorX, int anchorY, float scaleFactor) {

  private static final float MIN_ENTITY_SIZE = 0.1F;
  private static final float FIT_MARGIN = 0.9F;
  private static final float LOOK_TARGET_OFFSET_X = 2.0F;

  public static EntityPreview centered(
      int x, int y, int width, int height, int bottomPadding, float scaleFactor) {
    return new EntityPreview(
        x, y, width, height, x + width / 2, y + height - bottomPadding, scaleFactor);
  }

  public float fitScale(float baseScale, float entityWidth, float entityHeight) {
    float availableWidth = 2 * Math.min(this.anchorX - this.x, this.x + this.width - this.anchorX);
    float availableHeight = this.anchorY - this.y;
    float widthScale = availableWidth / Math.max(MIN_ENTITY_SIZE, entityWidth);
    float heightScale = availableHeight / Math.max(MIN_ENTITY_SIZE, entityHeight);
    return Math.min(baseScale * this.scaleFactor, Math.min(widthScale, heightScale) * FIT_MARGIN);
  }

  public void render(
      GuiGraphics guiGraphics, Entity entity, int leftPos, int topPos, float mouseX, float mouseY) {
    float entityWidth =
        (float) Math.max(entity.getBoundingBox().getXsize(), entity.getBoundingBox().getZsize());
    float scale =
        this.fitScale(EntityScalingManager.getUIScale(entity), entityWidth, entity.getBbHeight());
    int entityX = leftPos + this.anchorX;
    int entityY = topPos + this.anchorY;
    guiGraphics.enableScissor(
        leftPos + this.x,
        topPos + this.y,
        leftPos + this.x + this.width,
        topPos + this.y + this.height);
    try {
      ScreenHelper.renderEntity(
          entityX,
          entityY,
          entityX - LOOK_TARGET_OFFSET_X - mouseX,
          topPos + this.y + this.height / 2.0F - mouseY,
          scale,
          entity);
    } finally {
      guiGraphics.disableScissor();
    }
  }
}
