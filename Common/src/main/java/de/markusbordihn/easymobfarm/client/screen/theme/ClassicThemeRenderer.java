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
import de.markusbordihn.easymobfarm.client.screen.components.Text;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;

public final class ClassicThemeRenderer implements MobFarmScreenThemeRenderer {

  public static final ClassicThemeRenderer INSTANCE = new ClassicThemeRenderer();

  private static final int PANEL_SLICE_SIZE = 4;
  private static final int PANEL_TEXTURE_WIDTH = 248;
  private static final int PANEL_TEXTURE_HEIGHT = 166;
  private static final int SLOT_FRAME_TEXTURE_X = 7;
  private static final int SLOT_FRAME_TEXTURE_Y = 17;
  private static final int SLOT_FRAME_SIZE = 18;
  private static final int SLOT_FRAME_BORDER = 1;
  private static final int COLOR_FRAME = 0xFF373737;
  private static final int COLOR_ENTITY_WINDOW_WORKING = 0xFF000000;
  private static final int COLOR_ENTITY_WINDOW_IDLE = 0xFF2B2B2B;
  private static final int COLOR_ARROW_TRACK = 0xFF8B8B8B;
  private static final int COLOR_ARROW_FILL = 0xFFFFFFFF;
  private static final int ARROW_X = 124;
  private static final int ARROW_Y = 60;
  private static final int ARROW_WIDTH = 11;
  private static final int ARROW_SHAFT_WIDTH = 5;
  private static final int ARROW_SHAFT_HEIGHT = 17;
  private static final int ARROW_HEIGHT = 23;
  private static final int ENTITY_WINDOW_X = 47;
  private static final int ENTITY_WINDOW_Y = 21;
  private static final int ENTITY_WINDOW_WIDTH = 50;
  private static final int ENTITY_WINDOW_HEIGHT = 66;
  private static final int ENTITY_WINDOW_BORDER = 1;

  private ClassicThemeRenderer() {}

  private static void renderGroupLabel(
      GuiGraphics guiGraphics, Font font, String label, int centerX, int y, int maxWidth) {
    Component text = Component.translatable(Constants.TEXT_PREFIX + "gui." + label);
    int width = font.width(text);
    float scale = Math.min(0.75F, (float) maxWidth / Math.max(1, width));
    guiGraphics.pose().pushPose();
    try {
      guiGraphics.pose().translate(centerX, y, 0);
      guiGraphics.pose().scale(scale, scale, 1.0F);
      Text.drawString(guiGraphics, font, text, -width / 2, 0);
    } finally {
      guiGraphics.pose().popPose();
    }
  }

  private static int arrowHalfWidth(int row) {
    if (row < ARROW_SHAFT_HEIGHT) {
      return ARROW_SHAFT_WIDTH / 2;
    }

    return ARROW_WIDTH / 2 - (row - ARROW_SHAFT_HEIGHT);
  }

  private static void renderArrow(GuiGraphics guiGraphics, int x, int y, int rows, int color) {
    int centerX = x + ARROW_WIDTH / 2;
    for (int row = 0; row < rows; row++) {
      int halfWidth = arrowHalfWidth(row);
      if (halfWidth < 0) {
        break;
      }
      guiGraphics.fill(centerX - halfWidth, y + row, centerX + halfWidth + 1, y + row + 1, color);
    }
  }

  private static void renderPanel(GuiGraphics guiGraphics, int x, int y, int width, int height) {
    Graphics.blitNineSliced(
        guiGraphics,
        Constants.TEXTURE_DEMO_BACKGROUND,
        x,
        y,
        width,
        height,
        PANEL_SLICE_SIZE,
        PANEL_TEXTURE_WIDTH,
        PANEL_TEXTURE_HEIGHT,
        0,
        0);
  }

  private static void renderSlotFrame(GuiGraphics guiGraphics, int x, int y) {
    Graphics.blit(
        guiGraphics,
        Constants.TEXTURE_GENERIC_54,
        x,
        y,
        SLOT_FRAME_TEXTURE_X,
        SLOT_FRAME_TEXTURE_Y,
        SLOT_FRAME_SIZE,
        SLOT_FRAME_SIZE);
  }

  @Override
  public void renderBackground(
      GuiGraphics guiGraphics, int leftPos, int topPos, boolean idle, List<Slot> slots) {
    renderPanel(guiGraphics, leftPos + 40, topPos + 148, 176, 91);
    renderPanel(guiGraphics, leftPos + 10, topPos + 5, 234, 147);
    for (Slot slot : slots) {
      renderSlotFrame(
          guiGraphics, leftPos + slot.x - SLOT_FRAME_BORDER, topPos + slot.y - SLOT_FRAME_BORDER);
    }
    guiGraphics.fill(
        leftPos + ENTITY_WINDOW_X,
        topPos + ENTITY_WINDOW_Y,
        leftPos + ENTITY_WINDOW_X + ENTITY_WINDOW_WIDTH,
        topPos + ENTITY_WINDOW_Y + ENTITY_WINDOW_HEIGHT,
        COLOR_FRAME);
    guiGraphics.fill(
        leftPos + ENTITY_WINDOW_X + ENTITY_WINDOW_BORDER,
        topPos + ENTITY_WINDOW_Y + ENTITY_WINDOW_BORDER,
        leftPos + ENTITY_WINDOW_X + ENTITY_WINDOW_WIDTH - ENTITY_WINDOW_BORDER,
        topPos + ENTITY_WINDOW_Y + ENTITY_WINDOW_HEIGHT - ENTITY_WINDOW_BORDER,
        idle ? COLOR_ENTITY_WINDOW_IDLE : COLOR_ENTITY_WINDOW_WORKING);

    renderArrow(guiGraphics, leftPos + ARROW_X, topPos + ARROW_Y, ARROW_HEIGHT, COLOR_ARROW_TRACK);
  }

  @Override
  public void renderProgress(GuiGraphics guiGraphics, int leftPos, int topPos, float progress) {
    int filledRows = Math.round(ARROW_HEIGHT * Math.max(0.0F, Math.min(1.0F, progress)));
    renderArrow(guiGraphics, leftPos + ARROW_X, topPos + ARROW_Y, filledRows, COLOR_ARROW_FILL);
  }

  @Override
  public boolean isHoveringProgress(double mouseX, double mouseY) {
    if (mouseY < ARROW_Y || mouseY >= ARROW_Y + ARROW_HEIGHT) {
      return false;
    }

    int halfWidth = arrowHalfWidth((int) (mouseY - ARROW_Y));
    int centerX = ARROW_X + ARROW_WIDTH / 2;
    return halfWidth >= 0 && mouseX >= centerX - halfWidth && mouseX < centerX + halfWidth + 1;
  }

  @Override
  public void renderLabels(GuiGraphics guiGraphics, Font font, Component title) {
    Text.drawString(guiGraphics, font, title, 48, 10);
    renderGroupLabel(guiGraphics, font, "enhancements", 173, 72, 54);
    renderGroupLabel(guiGraphics, font, "filters", 227, 89, 26);
  }
}
