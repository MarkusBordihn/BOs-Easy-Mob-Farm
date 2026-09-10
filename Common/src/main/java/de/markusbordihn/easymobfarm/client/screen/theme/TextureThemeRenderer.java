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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.screen.components.Graphics;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

public record TextureThemeRenderer(
    ResourceLocation workingTexture,
    ResourceLocation idleTexture,
    ThemeProgressBar progressBar,
    EntityPreview entityPreview)
    implements MobFarmScreenThemeRenderer {

  private static final int LAYOUT_WIDTH = 244;
  private static final int LAYOUT_HEIGHT = 243;

  public static ResourceLocation guiTexture(String textureName) {
    return ResourceLocation.fromNamespaceAndPath(
        Constants.MOD_ID, "textures/gui/" + textureName + ".png");
  }

  public static TextureThemeRenderer of(
      String textureName, ThemeProgressBar progressBar, EntityPreview entityPreview) {
    return new TextureThemeRenderer(
        guiTexture(textureName), guiTexture(textureName + "_idle"), progressBar, entityPreview);
  }

  @Override
  public void renderBackground(
      GuiGraphics guiGraphics, int leftPos, int topPos, boolean idle, List<Slot> slots) {
    Graphics.blit(
        guiGraphics,
        idle ? this.idleTexture : this.workingTexture,
        leftPos,
        topPos,
        0,
        0,
        LAYOUT_WIDTH,
        LAYOUT_HEIGHT);
  }

  @Override
  public void renderProgress(GuiGraphics guiGraphics, int leftPos, int topPos, float progress) {
    this.progressBar.render(guiGraphics, leftPos, topPos, progress);
  }

  @Override
  public boolean isHoveringProgress(double mouseX, double mouseY) {
    return this.progressBar.contains(mouseX, mouseY);
  }

  @Override
  public void renderLabels(GuiGraphics guiGraphics, Font font, Component title) {}
}
