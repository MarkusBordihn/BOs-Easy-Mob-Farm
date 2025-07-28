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
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerScreen<T extends MobFarmMenu> extends AbstractContainerScreen<T> {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  protected static final ResourceLocation TEXTURE_DEMO_BACKGROUND =
      ResourceLocation.withDefaultNamespace("textures/gui/demo_background.png");

  protected final T menu;
  protected final Minecraft minecraftInstance;

  public ContainerScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);

    // Get menu and screen data
    this.menu = menu;

    // Get Minecraft instance and network message handler
    this.minecraftInstance = Minecraft.getInstance();
  }

  @Override
  protected void init() {
    super.init();

    // Default stats
    this.imageHeight = 243;
    this.imageWidth = 256;

    // Core Positions
    this.topPos = ((this.height - this.imageHeight) / 2) + 2;
    this.leftPos = (this.width - this.imageWidth) / 2;
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
    Graphics.blit(guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos, this.topPos, 0, 0, 210, 160);
    Graphics.blit(guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos, this.topPos, 0, 0, 210, 160);
    Graphics.blit(
        guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos + 203, this.topPos, 132, 0, 120, 160);
    Graphics.blit(
        guiGraphics, TEXTURE_DEMO_BACKGROUND, this.leftPos, this.topPos + 77, 0, 5, 210, 170);
    Graphics.blit(
        guiGraphics,
        TEXTURE_DEMO_BACKGROUND,
        this.leftPos + 203,
        this.topPos + 77,
        132,
        5,
        120,
        170);
  }
}
