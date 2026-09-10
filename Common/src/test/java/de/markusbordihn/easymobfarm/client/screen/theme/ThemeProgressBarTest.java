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

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

class ThemeProgressBarTest {

  private static final ResourceLocation TEXTURE =
      new ResourceLocation("easy_mob_farm", "textures/gui/progress_test.png");

  @ParameterizedTest
  @CsvSource({"-1, 0", "0, 0", "0.25, 22", "0.5, 44", "0.75, 66", "1, 88", "2, 88"})
  void horizontalFillClampsProgressAndKeepsItsOrigin(float progress, int width) {
    GuiGraphics graphics = Mockito.mock(GuiGraphics.class);
    ThemeProgressBar bar = new ThemeProgressBar.Fill(TEXTURE, 119, 75, 88, 7, 0, 244, false);

    bar.render(graphics, 10, 20, progress);

    if (width == 0) {
      Mockito.verifyNoInteractions(graphics);
      return;
    }

    Mockito.verify(graphics).blit(TEXTURE, 129, 95, 0.0F, 244.0F, width, 7, 256, 256);
  }

  @ParameterizedTest
  @CsvSource({"-1, 0", "0, 0", "0.25, 12", "0.5, 24", "0.75, 36", "1, 48", "2, 48"})
  void verticalFillKeepsBottomEdgeAndCropsMatchingTextureRows(float progress, int height) {
    GuiGraphics graphics = Mockito.mock(GuiGraphics.class);
    ThemeProgressBar bar = new ThemeProgressBar.Fill(TEXTURE, 198, 32, 7, 48, 246, 0, true);

    bar.render(graphics, 10, 20, progress);

    if (height == 0) {
      Mockito.verifyNoInteractions(graphics);
      return;
    }

    Mockito.verify(graphics)
        .blit(TEXTURE, 208, 100 - height, 246.0F, 48.0F - height, 7, height, 256, 256);
  }

  @ParameterizedTest
  @CsvSource({
    "-1, 0, 0", "0, 0, 0", "0.21875, 224, 0", "0.25, 0, 28",
    "0.5, 0, 56", "0.75, 0, 84", "1, 0, 112", "2, 0, 112"
  })
  void gaugeSelectsFramesAcrossAtlasRowsIncludingZeroProgress(float progress, int u, int v) {
    GuiGraphics graphics = Mockito.mock(GuiGraphics.class);
    ThemeProgressBar bar = new ThemeProgressBar.Frames(TEXTURE, 116, 60, 28, 26, 33, 8, 32, 28);

    bar.render(graphics, 10, 20, progress);

    Mockito.verify(graphics).blit(TEXTURE, 126, 80, (float) u, (float) v, 28, 26, 256, 256);
  }
}
