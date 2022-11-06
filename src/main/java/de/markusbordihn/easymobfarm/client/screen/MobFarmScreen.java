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

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntityData;
import de.markusbordihn.easymobfarm.client.renderer.helper.RenderModels;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class MobFarmScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ResourceLocation TEXTURE =
      new ResourceLocation(Constants.MOD_ID, "textures/container/mob_farm_gui.png");
  private static final ResourceLocation CUSTOM =
      new ResourceLocation(Constants.MOD_ID, "textures/container/custom.png");
  private static final ResourceLocation CUSTOM_SHADOW =
      new ResourceLocation(Constants.MOD_ID, "textures/container/custom_shadow.png");

  public static final int SNAP_WITH = 34;
  public static final int SNAP_HEIGHT = 53;

  private MobFarmMenu mobFarmMenu;
  private RenderModels renderModels;
  private TranslatableComponent warningFullText;
  private float dropTimeLabelScale = 0.75F;
  private float nextDropTimeLabelScale = 1.0F;
  private int dropTimeLabelX;
  private int dropTimeLabelY;
  private int nextDropTimeLabelX;
  private int nextDropTimeLabelY;
  private int ticker = 0;

  public ResourceLocation backgroundTexture = TEXTURE;

  public MobFarmScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
    this.mobFarmMenu = (MobFarmMenu) menu;
  }

  protected void renderSnapshot(PoseStack poseStack, ResourceLocation mobFarmTypeSnapshot) {
    if (mobFarmTypeSnapshot != null) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, mobFarmTypeSnapshot);

      // Scale snap image (34x53)
      blit(poseStack, leftPos + 6, topPos + 22, 0, 0, SNAP_WITH, SNAP_HEIGHT, SNAP_WITH,
          SNAP_HEIGHT);
    }
  }

  private void renderEntityType(PoseStack poseStack, int x, int y) {
    // Render Mob Farm Snapshot or try to render entity.
    String mobFarmType = this.mobFarmMenu.getMobFarmType();
    if (!mobFarmType.isBlank()
        && this.mobFarmMenu.getMobFarmStatus() != MobFarmBlockEntityData.FARM_STATUS_WAITING) {
      ResourceLocation mobFarmTypeSnapshot = Snapshots.getSnapshot(mobFarmType);
      if (mobFarmTypeSnapshot != null) {
        // Render snapshot, if available.
        this.renderSnapshot(poseStack, mobFarmTypeSnapshot);
      } else {
        // Render model, if supported.
        LivingEntity livingEntity = renderModels.getEntityTypeModel(mobFarmType);
        if (livingEntity != null) {
          this.renderSnapshot(poseStack, CUSTOM_SHADOW);
          renderEntity(leftPos + 23, topPos + 67, leftPos + 23f - x, topPos + 67f - y,
              livingEntity);
        } else {
          this.renderSnapshot(poseStack, CUSTOM);
        }
      }
    }
  }

  private void renderEntity(int x, int y, float yRot, float xRot, LivingEntity livingEntity) {
    float f = (float) Math.atan(yRot / 40.0F);
    float f1 = (float) Math.atan(xRot / 40.0F);
    int scale = 19;
    PoseStack poseStack = RenderSystem.getModelViewStack();
    poseStack.pushPose();
    poseStack.translate(x, y, 1050.0D);
    poseStack.scale(1.0F, 1.0F, -1.0F);
    RenderSystem.applyModelViewMatrix();
    PoseStack poseStack1 = new PoseStack();
    poseStack1.translate(0.0D, 0.0D, 1000.0D);
    poseStack1.scale(scale, scale, scale);
    Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
    Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
    quaternion.mul(quaternion1);
    poseStack1.mulPose(quaternion);
    float entityYBodyRot = livingEntity.yBodyRot;
    float entityYRot = livingEntity.getYRot();
    float entityXRot = livingEntity.getXRot();
    float entityYHeadRotO = livingEntity.yHeadRotO;
    float entityYHeadRot = livingEntity.yHeadRot;
    livingEntity.yBodyRot = 180.0F + f * 20.0F;
    livingEntity.setYRot(180.0F + f * 40.0F);
    livingEntity.setXRot(-f1 * 20.0F);
    livingEntity.yHeadRot = livingEntity.getYRot();
    Component customName = livingEntity.getCustomName();
    livingEntity.setCustomName(null);
    Lighting.setupForEntityInInventory();
    EntityRenderDispatcher entityRenderDispatcher =
        Minecraft.getInstance().getEntityRenderDispatcher();
    quaternion1.conj();
    entityRenderDispatcher.overrideCameraOrientation(quaternion1);
    entityRenderDispatcher.setRenderShadow(false);
    MultiBufferSource.BufferSource multiBuffer =
        Minecraft.getInstance().renderBuffers().bufferSource();
    entityRenderDispatcher.render(livingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, poseStack1,
        multiBuffer, 15728880);
    multiBuffer.endBatch();
    entityRenderDispatcher.setRenderShadow(true);
    livingEntity.yBodyRot = entityYBodyRot;
    livingEntity.setYRot(entityYRot);
    livingEntity.setXRot(entityXRot);
    livingEntity.yHeadRot = entityYHeadRot;
    livingEntity.yHeadRotO = entityYHeadRotO;
    livingEntity.setCustomName(customName);
    poseStack.popPose();
    RenderSystem.applyModelViewMatrix();
    Lighting.setupFor3DItems();
  }

  @Override
  public void init() {
    super.init();
    this.ticker = 0;
    this.imageHeight = 180;
    this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    this.topPos = (this.height - this.imageHeight) / 2;
    this.inventoryLabelY = this.imageHeight - 93;
    this.dropTimeLabelX = Math.round(45 / dropTimeLabelScale);
    this.dropTimeLabelY = Math.round(78 / dropTimeLabelScale);
    this.nextDropTimeLabelX = Math.round(65 / nextDropTimeLabelScale);
    this.nextDropTimeLabelY = Math.round(45 / nextDropTimeLabelScale);
    this.warningFullText = new TranslatableComponent(Constants.TEXT_PREFIX + "warning_full");
    this.renderModels = new RenderModels(this.minecraft);
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

    // Render screen in three parts.
    this.renderBackground(poseStack);
    super.render(poseStack, x, y, partialTicks);
    this.renderEntityType(poseStack, x, y);
    this.renderTooltip(poseStack, x, y);
  }

  @Override
  protected void renderLabels(PoseStack poseStack, int x, int y) {
    super.renderLabels(poseStack, x, y);
    int mobFarmStatus = this.mobFarmMenu.getMobFarmStatus();

    // Show Farm Details
    if (mobFarmStatus != MobFarmBlockEntityData.FARM_STATUS_FULL) {
      poseStack.pushPose();
      poseStack.scale(dropTimeLabelScale, dropTimeLabelScale, dropTimeLabelScale);
      font.draw(poseStack,
          new TranslatableComponent(Constants.TEXT_PREFIX + "drop_time_secs",
              this.mobFarmMenu.getMobFarmTotalTime() / 20),
          this.dropTimeLabelX, this.dropTimeLabelY, Constants.FONT_COLOR_GRAY);
      poseStack.popPose();
    }

    // Show Mob Details, if available.
    if (mobFarmStatus != MobFarmBlockEntityData.FARM_STATUS_WAITING) {
      poseStack.pushPose();
      font.draw(poseStack, this.mobFarmMenu.getMobFarmName(), 65, 23,
          Constants.FONT_COLOR_DARK_GREEN);
      poseStack.popPose();

      poseStack.pushPose();
      poseStack.scale(0.65f, 0.65f, 0.65f);
      font.draw(poseStack, this.mobFarmMenu.getMobFarmType(), 100, 50, Constants.FONT_COLOR_GRAY);
      poseStack.popPose();
    }

    // Handle different kind of status messages and process status.
    switch (mobFarmStatus) {
      case MobFarmBlockEntityData.FARM_STATUS_DONE:
      case MobFarmBlockEntityData.FARM_STATUS_WORKING:
        poseStack.pushPose();
        poseStack.scale(nextDropTimeLabelScale, nextDropTimeLabelScale, nextDropTimeLabelScale);
        font.draw(poseStack,
            new TranslatableComponent(Constants.TEXT_PREFIX + "next_drop_secs",
                this.mobFarmMenu.getMobFarmRemainingTime()),
            nextDropTimeLabelX, nextDropTimeLabelY, Constants.FONT_COLOR_BLACK);
        poseStack.popPose();
        break;
      case MobFarmBlockEntityData.FARM_STATUS_FULL:
        poseStack.pushPose();
        poseStack.scale(dropTimeLabelScale, dropTimeLabelScale, dropTimeLabelScale);
        font.draw(poseStack, this.warningFullText, this.dropTimeLabelX, this.dropTimeLabelY,
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

    // Render Mob Farm Status
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
