/*
 * Copyright 2024 Markus Bordihn
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

package de.markusbordihn.easymobfarm.client.screen;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.screen.components.Graphics;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerScreen<T extends MobFarmMenu> extends AbstractContainerScreen<T> {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  protected static final Identifier TEXTURE_DEMO_BACKGROUND =
      Identifier.withDefaultNamespace("textures/gui/demo_background.png");
  protected final T menu;
  protected final Minecraft minecraftInstance;

  public ContainerScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component, 243, 256);

    // Get menu and screen data
    this.menu = menu;

    // Get Minecraft instance and network message handler
    this.minecraftInstance = Minecraft.getInstance();
  }

  @Override
  protected void init() {

    // Core Positions
    this.topPos = ((this.height - this.imageHeight) / 2) + 2;
    this.leftPos = (this.width - this.imageWidth) / 2;
  }

  @Override
  public void extractRenderState(
      GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
    super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
  }

  protected void renderBg(
      GuiGraphicsExtractor guiGraphics, float partialTicks, int mouseX, int mouseY) {
    Graphics.blit(guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos, this.topPos, 210, 160, 0, 0);
    Graphics.blit(guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos, this.topPos, 210, 160, 0, 0);
    Graphics.blit(
        guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos + 203, this.topPos, 120, 160, 132, 0);
    Graphics.blit(
        guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos, this.topPos + 77, 210, 170, 0, 5);
    Graphics.blit(
        guiGraphics,
        TEXTURE_DEMO_BACKGROUND,
        this.leftPos + 203,
        this.topPos + 77,
        120,
        170,
        132,
        5);
  }
}
