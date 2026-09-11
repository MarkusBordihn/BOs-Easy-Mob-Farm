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

import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;

public interface MobFarmScreenThemeRenderer {

  EntityPreview DEFAULT_ENTITY_PREVIEW = new EntityPreview(48, 22, 48, 64, 72, 80, 1.0F);

  void renderBackground(
      GuiGraphicsExtractor guiGraphics, int leftPos, int topPos, boolean idle, List<Slot> slots);

  void renderProgress(GuiGraphicsExtractor guiGraphics, int leftPos, int topPos, float progress);

  void renderLabels(GuiGraphicsExtractor guiGraphics, Font font, Component title);

  boolean isHoveringProgress(double mouseX, double mouseY);

  default EntityPreview entityPreview() {
    return DEFAULT_ENTITY_PREVIEW;
  }
}
