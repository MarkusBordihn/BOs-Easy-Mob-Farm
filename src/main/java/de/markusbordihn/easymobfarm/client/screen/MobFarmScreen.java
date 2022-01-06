/**
 * Copyright 2021 Markus Bordihn
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.DyeColor;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntityData;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class MobFarmScreen extends AbstractContainerScreen<MobFarmMenu> {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ResourceLocation TEXTURE =
      new ResourceLocation(Constants.MOD_ID, "textures/container/mob_farm_gui.png");
  private static final int FONT_COLOR_BLACK = DyeColor.BLACK.getTextColor();
  private static final int FONT_COLOR_GRAY = DyeColor.GRAY.getTextColor();
  private static final int FONT_COLOR_WARNING = DyeColor.RED.getTextColor();

  private int ticker = 0;
  private int totalTimeLabelX;
  private int totalTimeLabelY;
  private float totalTimeLabelScale = 0.75F;
  private TranslatableComponent warningFullText;

  public MobFarmScreen(MobFarmMenu mobFarmMenu, Inventory inventory, Component component) {
    super(mobFarmMenu, inventory, component);
  }

  @Override
  public void init() {
    super.init();
    this.ticker = 0;
    this.imageHeight = 180;
    this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    this.topPos = (this.height - this.imageHeight) / 2;
    this.inventoryLabelY = this.imageHeight - 93;
    this.totalTimeLabelX = Math.round(45 / totalTimeLabelScale);
    this.totalTimeLabelY = Math.round(78 / totalTimeLabelScale);
    this.warningFullText = new TranslatableComponent(Constants.TEXT_PREFIX + "warning_full");
  }

  @Override
  public void render(PoseStack poseStack, int x, int y, float partialTicks) {
    // Update data cache only every 40 ticks to avoid expensive operations on hight fps.
    if ((this.ticker++ & (40-1)) == 0) {
      this.menu.updateMobFarmDataCache();
      if (ticker >= 40) {
        this.ticker = 0;
      }
    }
    this.renderBackground(poseStack);
    super.render(poseStack, x, y, partialTicks);
    this.renderTooltip(poseStack, x, y);
  }

  @Override
  protected void renderLabels(PoseStack matrixStack, int x, int y) {
    super.renderLabels(matrixStack, x, y);
    int mobFarmStatus = this.menu.getMobFarmStatus();

    // Show Mob Details
    if (mobFarmStatus != MobFarmBlockEntityData.FARM_STATUS_WAITING) {
      matrixStack.pushPose();
      font.draw(matrixStack, "Mob:", 65, 22, FONT_COLOR_BLACK);
      font.draw(matrixStack, this.menu.getMobFarmName(), 88, 22, FONT_COLOR_GRAY);
      font.draw(matrixStack, "Drop Time:", 65, 32, FONT_COLOR_BLACK);
      font.draw(matrixStack, this.menu.getMobFarmTotalTimeText(), 118, 32, FONT_COLOR_GRAY);
      matrixStack.popPose();
    }

    // Handle different kind of status messages and process status.
    switch (mobFarmStatus) {
      case MobFarmBlockEntityData.FARM_STATUS_DONE:
      case MobFarmBlockEntityData.FARM_STATUS_WORKING:
        matrixStack.pushPose();
        matrixStack.scale(totalTimeLabelScale, totalTimeLabelScale, totalTimeLabelScale);
        font.draw(matrixStack,
            new TranslatableComponent(Constants.TEXT_PREFIX + "next_drop_secs",
                this.menu.getMobFarmRemainingTime()),
            totalTimeLabelX, totalTimeLabelY, FONT_COLOR_GRAY);
        matrixStack.popPose();
        break;
      case MobFarmBlockEntityData.FARM_STATUS_FULL:
        matrixStack.scale(totalTimeLabelScale, totalTimeLabelScale, totalTimeLabelScale);
        font.draw(matrixStack, this.warningFullText, totalTimeLabelX, totalTimeLabelY,
            FONT_COLOR_WARNING);
        break;
    }
  }

  @Override
  protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, TEXTURE);

    // Cut main screen
    this.blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

    int mobFarmStatus = this.menu.getMobFarmStatus();

    switch (mobFarmStatus) {
      case MobFarmBlockEntityData.FARM_STATUS_DONE:
      case MobFarmBlockEntityData.FARM_STATUS_WORKING:
        int mobFarmProgress = this.menu.getMobFarmProgressImage();
        int mobFarmProgressLeftPos = leftPos + 43;
        int mobFarmProgressTopPos = topPos + 41;
        this.blit(poseStack, mobFarmProgressLeftPos, mobFarmProgressTopPos, 194, 14, 18,
            mobFarmProgress);
        break;
      case MobFarmBlockEntityData.FARM_STATUS_FULL:
        int mobFarmFullLeftPos = leftPos + 43;
        int mobFarmFullTopPos = topPos + 41;
        this.blit(poseStack, mobFarmFullLeftPos, mobFarmFullTopPos, 176, 14, 18, 15);
        break;
    }
  }

}
