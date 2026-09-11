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

package de.markusbordihn.easymobfarm.client.screen.components;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class Graphics {

  private Graphics() {}

  public static void blit(
      GuiGraphicsExtractor guiGraphics,
      Identifier texture,
      int x,
      int y,
      int textureX,
      int textureY,
      int width,
      int height) {
    blit(guiGraphics, texture, x, y, textureX, textureY, width, height, 256, 256);
  }

  public static void blit(
      GuiGraphicsExtractor guiGraphics,
      Identifier texture,
      int x,
      int y,
      int textureX,
      int textureY,
      int width,
      int height,
      int textureWidth,
      int textureHeight) {
    guiGraphics.blit(
        RenderPipelines.GUI_TEXTURED,
        texture,
        x,
        y,
        textureX,
        textureY,
        width,
        height,
        textureWidth,
        textureHeight);
  }

  public static void blitNineSliced(
      GuiGraphicsExtractor guiGraphics,
      Identifier texture,
      int x,
      int y,
      int width,
      int height,
      int sliceSize,
      int textureX,
      int textureY,
      int textureWidth,
      int textureHeight,
      int bodyTextureX,
      int bodyTextureY) {
    int sliceWidth = Math.min(sliceSize, width / 2);
    int sliceHeight = Math.min(sliceSize, height / 2);
    int innerWidth = width - 2 * sliceWidth;
    int innerHeight = height - 2 * sliceHeight;
    int rightTextureX = textureX + textureWidth - sliceSize;
    int bottomTextureY = textureY + textureHeight - sliceSize;
    int rightX = x + width - sliceWidth;
    int bottomY = y + height - sliceHeight;

    blitStretched(
        guiGraphics,
        texture,
        x,
        y,
        sliceWidth,
        sliceHeight,
        textureX,
        textureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        rightX,
        y,
        sliceWidth,
        sliceHeight,
        rightTextureX,
        textureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        x,
        bottomY,
        sliceWidth,
        sliceHeight,
        textureX,
        bottomTextureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        rightX,
        bottomY,
        sliceWidth,
        sliceHeight,
        rightTextureX,
        bottomTextureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        x + sliceWidth,
        y,
        innerWidth,
        sliceHeight,
        bodyTextureX,
        textureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        x + sliceWidth,
        bottomY,
        innerWidth,
        sliceHeight,
        bodyTextureX,
        bottomTextureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        x,
        y + sliceHeight,
        sliceWidth,
        innerHeight,
        textureX,
        bodyTextureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        rightX,
        y + sliceHeight,
        sliceWidth,
        innerHeight,
        rightTextureX,
        bodyTextureY,
        sliceSize,
        sliceSize);
    blitStretched(
        guiGraphics,
        texture,
        x + sliceWidth,
        y + sliceHeight,
        innerWidth,
        innerHeight,
        bodyTextureX,
        bodyTextureY,
        sliceSize,
        sliceSize);
  }

  private static void blitStretched(
      GuiGraphicsExtractor guiGraphics,
      Identifier texture,
      int x,
      int y,
      int width,
      int height,
      int textureX,
      int textureY,
      int sourceWidth,
      int sourceHeight) {
    if (width <= 0 || height <= 0 || sourceWidth <= 0 || sourceHeight <= 0) {
      return;
    }
    guiGraphics.blit(
        RenderPipelines.GUI_TEXTURED,
        texture,
        x,
        y,
        textureX,
        textureY,
        width,
        height,
        sourceWidth,
        sourceHeight,
        256,
        256);
  }
}
