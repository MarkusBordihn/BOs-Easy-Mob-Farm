/**
 * Copyright 2022 Markus Bordihn
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
import net.minecraft.world.inventory.AbstractContainerMenu;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntityData;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class MobFarmScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ResourceLocation TEXTURE =
      new ResourceLocation(Constants.MOD_ID, "textures/container/mob_farm_gui.png");

  public static final int SNAP_WITH = 34;
  public static final int SNAP_HEIGHT = 53;

  private int ticker = 0;
  private int totalTimeLabelX;
  private int totalTimeLabelY;
  private float totalTimeLabelScale = 0.75F;
  private TranslatableComponent warningFullText;
  private MobFarmMenu mobFarmMenu;

  public ResourceLocation backgroundTexture = TEXTURE;

  public MobFarmScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
    this.mobFarmMenu = (MobFarmMenu) menu;
  }

  protected void renderSnapshot(PoseStack poseStack, ResourceLocation mobFarmTypeSnapshot) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, mobFarmTypeSnapshot);

    // Scale snap image (34x53)
    blit(poseStack, leftPos + 6, topPos + 22, 0, 0, SNAP_WITH, SNAP_HEIGHT, SNAP_WITH, SNAP_HEIGHT);
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
    if ((this.ticker++ & (40 - 1)) == 0) {
      this.mobFarmMenu.updateMobFarmDataCache();
      if (ticker >= 40) {
        this.ticker = 0;
      }
    }
    this.renderBackground(poseStack);
    super.render(poseStack, x, y, partialTicks);

    // Render Mob Farm Snapshot, if available.
    String mobFarmType = this.mobFarmMenu.getMobFarmType();
    if (!mobFarmType.isBlank()
        && this.mobFarmMenu.getMobFarmStatus() != MobFarmBlockEntityData.FARM_STATUS_WAITING) {
      ResourceLocation mobFarmTypeSnapshot = Snapshots.getSnapshot(mobFarmType);
      if (mobFarmTypeSnapshot != null) {
        this.renderSnapshot(poseStack, mobFarmTypeSnapshot);
      }
    }
    this.renderTooltip(poseStack, x, y);
  }

  @Override
  protected void renderLabels(PoseStack poseStack, int x, int y) {
    super.renderLabels(poseStack, x, y);
    int mobFarmStatus = this.mobFarmMenu.getMobFarmStatus();

    // Show Farm Details
    poseStack.pushPose();
    font.draw(poseStack, "Drop Time:", 65, 45, Constants.FONT_COLOR_BLACK);
    font.draw(poseStack, this.mobFarmMenu.getMobFarmTotalTimeText(), 118, 45,
        Constants.FONT_COLOR_GRAY);
    poseStack.popPose();

    // Show Mob Details, if available.
    if (mobFarmStatus != MobFarmBlockEntityData.FARM_STATUS_WAITING) {
      poseStack.pushPose();
      font.draw(poseStack, "Mob:", 65, 25, Constants.FONT_COLOR_BLACK);
      font.draw(poseStack, this.mobFarmMenu.getMobFarmName(), 86, 25,
          Constants.FONT_COLOR_DARK_GREEN);
      poseStack.popPose();

      poseStack.pushPose();
      poseStack.scale(0.65f, 0.65f, 0.65f);
      font.draw(poseStack, this.mobFarmMenu.getMobFarmType(), 100, 52, Constants.FONT_COLOR_GRAY);
      poseStack.popPose();
    }

    // Handle different kind of status messages and process status.
    switch (mobFarmStatus) {
      case MobFarmBlockEntityData.FARM_STATUS_DONE:
      case MobFarmBlockEntityData.FARM_STATUS_WORKING:
        poseStack.pushPose();
        poseStack.scale(totalTimeLabelScale, totalTimeLabelScale, totalTimeLabelScale);
        font.draw(poseStack,
            new TranslatableComponent(Constants.TEXT_PREFIX + "next_drop_secs",
                this.mobFarmMenu.getMobFarmRemainingTime()),
            totalTimeLabelX, totalTimeLabelY, Constants.FONT_COLOR_GRAY);
        poseStack.popPose();
        break;
      case MobFarmBlockEntityData.FARM_STATUS_FULL:
        poseStack.pushPose();
        poseStack.scale(totalTimeLabelScale, totalTimeLabelScale, totalTimeLabelScale);
        font.draw(poseStack, this.warningFullText, totalTimeLabelX, totalTimeLabelY,
            Constants.FONT_COLOR_WARNING);
        poseStack.popPose();
        break;
      default:
    }
  }

  @Override
  protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, this.backgroundTexture);

    // Cut main screen
    this.blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

    switch (this.mobFarmMenu.getMobFarmStatus()) {
      case MobFarmBlockEntityData.FARM_STATUS_DONE:
      case MobFarmBlockEntityData.FARM_STATUS_WORKING:
        int mobFarmProgress = this.mobFarmMenu.getMobFarmProgressImage();
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
      case MobFarmBlockEntityData.FARM_STATUS_WAITING:
        int mobFarmHintLeftPos = leftPos + 65;
        int mobFarmHintTopPos = topPos + 21;
        this.blit(poseStack, mobFarmHintLeftPos, mobFarmHintTopPos, 220, 14, 20, 15);
        break;
      default:
    }
  }

}
