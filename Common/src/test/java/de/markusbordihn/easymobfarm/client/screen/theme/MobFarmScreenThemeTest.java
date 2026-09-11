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
import de.markusbordihn.easymobfarm.TestBootstrap;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.joml.Matrix3x2fStack;
import org.mockito.Mockito;

class MobFarmScreenThemeTest {

  @BeforeAll
  static void setupMinecraft() {
    TestBootstrap.bootstrapWithBoundItemComponents();
  }

  private static BufferedImage readTexture(Identifier texture) throws Exception {
    String path = "/assets/" + texture.getNamespace() + "/" + texture.getPath();
    try (InputStream stream = MobFarmScreenThemeTest.class.getResourceAsStream(path)) {
      Assertions.assertNotNull(stream, path);
      BufferedImage image = ImageIO.read(stream);
      Assertions.assertEquals(256, image.getWidth());
      Assertions.assertEquals(256, image.getHeight());
      return image;
    }
  }

  private static void assertVisible(BufferedImage image, int x, int y, int width, int height) {
    Assertions.assertTrue(x >= 0 && y >= 0);
    Assertions.assertTrue(x + width <= image.getWidth() && y + height <= image.getHeight());
    for (int row = y; row < y + height; row++) {
      for (int column = x; column < x + width; column++) {
        if ((image.getRGB(column, row) >>> 24) != 0) {
          return;
        }
      }
    }
    Assertions.fail("Empty progress sprite at " + x + ", " + y);
  }

  @ParameterizedTest
  @EnumSource(MobFarmScreenTheme.class)
  void onlyClassicDrawsTitle(MobFarmScreenTheme theme) {
    GuiGraphicsExtractor graphics = Mockito.mock(GuiGraphicsExtractor.class);
    Mockito.when(graphics.pose()).thenReturn(new Matrix3x2fStack(4));
    Font font = Mockito.mock(Font.class);
    Component title = Component.literal("Mob Farm");

    theme.getRenderer().renderLabels(graphics, font, title);

    if (theme == MobFarmScreenTheme.CLASSIC) {
      Mockito.verify(graphics).text(font, title, 48, 10, Constants.FONT_COLOR_DEFAULT, false);
    } else {
      Mockito.verifyNoInteractions(graphics);
    }
  }

  @ParameterizedTest
  @EnumSource(MobFarmScreenTheme.class)
  void configuredTexturesAndEveryProgressFrameExist(MobFarmScreenTheme theme) throws Exception {
    if (!(theme.getRenderer() instanceof TextureThemeRenderer renderer)) {
      return;
    }

    readTexture(renderer.workingTexture());
    readTexture(renderer.idleTexture());
    ThemeProgressBar progress = renderer.progressBar();
    BufferedImage image = readTexture(progress.texture());
    if (progress instanceof ThemeProgressBar.Frames frames) {
      for (int frame = 0; frame < frames.frameCount(); frame++) {
        assertVisible(
            image,
            frame % frames.columns() * frames.strideX(),
            frame / frames.columns() * frames.strideY(),
            frames.width(),
            frames.height());
      }
    } else if (progress instanceof ThemeProgressBar.Fill fill) {
      assertVisible(image, fill.textureU(), fill.textureV(), fill.width(), fill.height());
    }
  }

  @ParameterizedTest
  @EnumSource(MobFarmScreenTheme.class)
  void wideAndTallEntitiesFitTheirThemeWindow(MobFarmScreenTheme theme) {
    EntityPreview preview = theme.getRenderer().entityPreview();
    Assertions.assertTrue(preview.anchorX() > preview.x());
    Assertions.assertTrue(preview.anchorX() < preview.x() + preview.width());
    Assertions.assertTrue(preview.anchorY() > preview.y());
    Assertions.assertTrue(preview.anchorY() < preview.y() + preview.height());
    for (float[] size : new float[][] {{0.5F, 0.5F}, {0.6F, 2.0F}, {20, 3}, {3, 20}}) {
      float scale = preview.fitScale(45, size[0], size[1]);
      Assertions.assertTrue(scale > 0);
      Assertions.assertTrue(size[0] * scale / 2 <= preview.anchorX() - preview.x());
      Assertions.assertTrue(
          size[0] * scale / 2 <= preview.x() + preview.width() - preview.anchorX());
      Assertions.assertTrue(size[1] * scale <= preview.anchorY() - preview.y());
    }
  }

  @Test
  void themedEntityPreviewsAreCenteredWithBottomClearance() {
    for (MobFarmScreenTheme theme :
        new MobFarmScreenTheme[] {
          MobFarmScreenTheme.TECHNOLOGY,
          MobFarmScreenTheme.RUSTIC,
          MobFarmScreenTheme.DARK,
          MobFarmScreenTheme.STEAMPUNK,
          MobFarmScreenTheme.CREATE
        }) {
      EntityPreview preview = theme.getRenderer().entityPreview();
      Assertions.assertEquals(preview.x() + preview.width() / 2, preview.anchorX(), theme.getId());
      int bottomPadding = preview.y() + preview.height() - preview.anchorY();
      Assertions.assertTrue(bottomPadding >= 4 && bottomPadding <= 5, theme.getId());
    }
  }

  @Test
  void technologyIsFallbackAndSavedThemeIdsKeepTheirMeaning() {
    Assertions.assertEquals(MobFarmScreenTheme.TECHNOLOGY, MobFarmScreenTheme.fromId(null));
    Assertions.assertEquals(MobFarmScreenTheme.TECHNOLOGY, MobFarmScreenTheme.fromId("unknown"));
    Assertions.assertEquals(MobFarmScreenTheme.TECHNOLOGY, MobFarmScreenTheme.fromId("technology"));
    Assertions.assertEquals(MobFarmScreenTheme.TECHNICAL, MobFarmScreenTheme.fromId("technical"));
    for (MobFarmScreenTheme theme : MobFarmScreenTheme.values()) {
      Assertions.assertEquals(theme, MobFarmScreenTheme.fromId(theme.getId()));
    }
  }

  @Test
  void technologyHasTransparencyAndOnlyOneProgressSprite() throws Exception {
    TextureThemeRenderer renderer =
        (TextureThemeRenderer) MobFarmScreenTheme.TECHNOLOGY.getRenderer();
    BufferedImage active = readTexture(renderer.workingTexture());
    BufferedImage idle = readTexture(renderer.idleTexture());
    Assertions.assertEquals(0, active.getRGB(0, 0) >>> 24);
    Assertions.assertEquals(0, idle.getRGB(0, 0) >>> 24);
    for (int y = 244; y < 251; y++) {
      for (int x = 0; x < 33; x++) {
        Assertions.assertEquals(255, active.getRGB(x, y) >>> 24);
        Assertions.assertEquals(0, idle.getRGB(x, y) >>> 24);
      }
    }
    for (int row = 0; row < 3; row++) {
      for (int column = 0; column < 9; column++) {
        int slotX = 47 + column * 18;
        int slotY = 88 + row * 18;
        for (int y = 0; y < 18; y++) {
          for (int x = 0; x < 18; x++) {
            Assertions.assertEquals(
                active.getRGB(slotX + x, slotY + y), idle.getRGB(slotX + x, slotY + y));
          }
        }
      }
    }
    Assertions.assertTrue(renderer.isHoveringProgress(113, 78));
    Assertions.assertFalse(renderer.isHoveringProgress(146, 78));
    Assertions.assertFalse(renderer.isHoveringProgress(113, 85));
  }

  @Test
  void hoverAreasFollowTheSelectedThemeAndGaugeShape() {
    Assertions.assertTrue(MobFarmScreenTheme.CREATE.getRenderer().isHoveringProgress(120, 76));
    Assertions.assertTrue(MobFarmScreenTheme.RUSTIC.getRenderer().isHoveringProgress(206, 81));
    Assertions.assertFalse(MobFarmScreenTheme.RUSTIC.getRenderer().isHoveringProgress(207, 81));
    Assertions.assertTrue(MobFarmScreenTheme.DARK.getRenderer().isHoveringProgress(201, 33));
    Assertions.assertFalse(MobFarmScreenTheme.DARK.getRenderer().isHoveringProgress(120, 76));
    Assertions.assertTrue(MobFarmScreenTheme.STEAMPUNK.getRenderer().isHoveringProgress(130, 73));
    Assertions.assertFalse(MobFarmScreenTheme.STEAMPUNK.getRenderer().isHoveringProgress(116, 60));
    Assertions.assertTrue(MobFarmScreenTheme.CLASSIC.getRenderer().isHoveringProgress(129, 60));
    Assertions.assertFalse(MobFarmScreenTheme.CLASSIC.getRenderer().isHoveringProgress(124, 60));
    Assertions.assertTrue(MobFarmScreenTheme.TECHNICAL.getRenderer().isHoveringProgress(114, 79));
  }
}
