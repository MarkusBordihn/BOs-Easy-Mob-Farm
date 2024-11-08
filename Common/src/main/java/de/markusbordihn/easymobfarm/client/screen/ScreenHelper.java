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

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import org.joml.Quaternionf;

public class ScreenHelper {

  private ScreenHelper() {}

  public static void renderEntity(
      GuiGraphics guiGraphics, int x, int y, float yRot, float xRot, int scale, Entity entity) {
    if (entity instanceof LivingEntity livingEntity) {
      renderEntity(guiGraphics, x, y, yRot, xRot, scale, livingEntity);
    }
  }

  public static void renderEntity(
      GuiGraphics guiGraphics,
      int x,
      int y,
      float yRot,
      float xRot,
      int scale,
      LivingEntity livingEntity) {
    // Prepare Renderer
    boolean isDead = livingEntity.isDeadOrDying();
    Minecraft minecraft = Minecraft.getInstance();
    float rotationY = (float) Math.atan((isDead ? 25F : yRot) / 40.0F);
    float rotationX = (float) Math.atan((isDead ? -25F : xRot) / 40.0F);
    Quaternionf quaternionfZ = (new Quaternionf()).rotateZ(3.1415927F);
    Quaternionf quaternionfX = (new Quaternionf()).rotateX(rotationX * 20.0F * 0.017453292F);
    quaternionfZ.mul(quaternionfX);

    // Backup entity information
    Component entityCustomName = livingEntity.getCustomName();
    boolean entityShouldShowName = livingEntity.shouldShowName();
    float entityXRot = livingEntity.getXRot();
    float entityYBodyRot = livingEntity.yBodyRot;
    float entityYHeadRot = livingEntity.yHeadRot;
    float entityYHeadRotO = livingEntity.yHeadRotO;
    float entityYRot = livingEntity.getYRot();
    boolean entityInvisible = livingEntity.isInvisible();
    Team entityTeam = livingEntity.getTeam();

    // Adjust entity information for rendering
    livingEntity.setInvisible(false);
    livingEntity.yBodyRot = 180.0F + rotationY * 20.0F;
    livingEntity.setYRot(180.0F + rotationY * 40.0F);
    livingEntity.setXRot(-rotationX * 20.0F);
    livingEntity.yHeadRot = livingEntity.getYRot();
    livingEntity.yHeadRotO = livingEntity.getYRot();
    if (entityTeam instanceof PlayerTeam playerTeam) {
      livingEntity
          .level()
          .getScoreboard()
          .removePlayerFromTeam(livingEntity.getScoreboardName(), playerTeam);
    }

    // Hide gui elements or remove custom name
    boolean minecraftHideGui = false;
    if (minecraft != null) {
      minecraftHideGui = minecraft.options.hideGui;
      minecraft.options.hideGui = true;
    } else {
      livingEntity.setCustomName(null);
      livingEntity.setCustomNameVisible(false);
    }

    // Render Entity
    guiGraphics.pose().pushPose();
    guiGraphics.pose().translate(x, y, 1050.0D);
    guiGraphics.pose().scale(scale, scale, -scale);
    guiGraphics.pose().mulPose(quaternionfZ);
    if (livingEntity instanceof AbstractSchoolingFish) {
      guiGraphics.pose().translate(-0.1, 0.5, 0.1);
      guiGraphics.pose().mulPose(Axis.XP.rotationDegrees(2.0F));
      guiGraphics.pose().mulPose(Axis.YP.rotationDegrees(15.0F));
      guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-90.0F));
    }
    Lighting.setupForEntityInInventory();
    EntityRenderDispatcher entityRenderDispatcher =
        Minecraft.getInstance().getEntityRenderDispatcher();
    quaternionfX.conjugate();
    entityRenderDispatcher.overrideCameraOrientation(quaternionfX);
    entityRenderDispatcher.setRenderShadow(false);
    entityRenderDispatcher.render(
        livingEntity,
        0.0D,
        0.0D,
        0.0D,
        0.0F,
        1.0F,
        guiGraphics.pose(),
        guiGraphics.bufferSource(),
        15728880);
    guiGraphics.flush();
    entityRenderDispatcher.setRenderShadow(true);
    guiGraphics.pose().popPose();
    Lighting.setupFor3DItems();

    // Restore entity information
    livingEntity.setInvisible(entityInvisible);
    livingEntity.yBodyRot = entityYBodyRot;
    livingEntity.setYRot(entityYRot);
    livingEntity.setXRot(entityXRot);
    livingEntity.yHeadRot = entityYHeadRot;
    livingEntity.yHeadRotO = entityYHeadRotO;
    if (entityTeam instanceof PlayerTeam playerTeam) {
      livingEntity
          .level()
          .getScoreboard()
          .addPlayerToTeam(livingEntity.getScoreboardName(), playerTeam);
    }

    // Restore gui elements or custom name
    if (minecraft != null) {
      minecraft.options.hideGui = minecraftHideGui;
    } else {
      livingEntity.setCustomName(entityCustomName);
      livingEntity.setCustomNameVisible(entityShouldShowName);
    }
  }
}
