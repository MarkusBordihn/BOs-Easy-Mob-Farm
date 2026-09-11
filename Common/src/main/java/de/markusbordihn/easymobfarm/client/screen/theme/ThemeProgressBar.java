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

import de.markusbordihn.easymobfarm.client.screen.components.Graphics;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public interface ThemeProgressBar {

  static Fill horizontal(
      String textureName, int x, int y, int width, int height, int textureU, int textureV) {
    return new Fill(
        TextureThemeRenderer.guiTexture(textureName),
        x,
        y,
        width,
        height,
        textureU,
        textureV,
        false);
  }

  static Fill vertical(
      String textureName, int x, int y, int width, int height, int textureU, int textureV) {
    return new Fill(
        TextureThemeRenderer.guiTexture(textureName),
        x,
        y,
        width,
        height,
        textureU,
        textureV,
        true);
  }

  static Frames frames(
      String textureName,
      int x,
      int y,
      int width,
      int height,
      int frameCount,
      int columns,
      int strideX,
      int strideY) {
    return new Frames(
        TextureThemeRenderer.guiTexture(textureName),
        x,
        y,
        width,
        height,
        frameCount,
        columns,
        strideX,
        strideY);
  }

  void render(GuiGraphicsExtractor guiGraphics, int leftPos, int topPos, float progress);

  Identifier texture();

  int x();

  int y();

  int width();

  int height();

  default boolean contains(double mouseX, double mouseY) {
    return mouseX >= this.x()
        && mouseX < this.x() + this.width()
        && mouseY >= this.y()
        && mouseY < this.y() + this.height();
  }

  record Fill(
      Identifier texture,
      int x,
      int y,
      int width,
      int height,
      int textureU,
      int textureV,
      boolean bottomToTop)
      implements ThemeProgressBar {

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int leftPos, int topPos, float progress) {
      float clampedProgress = Math.max(0.0F, Math.min(1.0F, progress));
      int filledWidth = this.bottomToTop ? this.width : Math.round(this.width * clampedProgress);
      int filledHeight = this.bottomToTop ? Math.round(this.height * clampedProgress) : this.height;
      if (filledWidth <= 0 || filledHeight <= 0) {
        return;
      }

      int offsetY = this.height - filledHeight;
      Graphics.blit(
          guiGraphics,
          this.texture,
          leftPos + this.x,
          topPos + this.y + offsetY,
          this.textureU,
          this.textureV + offsetY,
          filledWidth,
          filledHeight);
    }
  }

  record Frames(
      Identifier texture,
      int x,
      int y,
      int width,
      int height,
      int frameCount,
      int columns,
      int strideX,
      int strideY)
      implements ThemeProgressBar {

    @Override
    public boolean contains(double mouseX, double mouseY) {
      double dx = (mouseX - this.x - this.width / 2.0) / (this.width / 2.0);
      double dy = (mouseY - this.y - this.height / 2.0) / (this.height / 2.0);
      return dx * dx + dy * dy < 1.0;
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int leftPos, int topPos, float progress) {
      float clampedProgress = Math.max(0.0F, Math.min(1.0F, progress));
      int frame = Math.round(clampedProgress * (this.frameCount - 1));
      Graphics.blit(
          guiGraphics,
          this.texture,
          leftPos + this.x,
          topPos + this.y,
          (frame % this.columns) * this.strideX,
          (frame / this.columns) * this.strideY,
          this.width,
          this.height);
    }
  }
}
