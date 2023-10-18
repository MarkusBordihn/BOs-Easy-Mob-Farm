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

import org.joml.Quaternionf;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import de.markusbordihn.easymobfarm.Constants;

public class MobFarmScreenHelper {

  public static void renderEntity(int x, int y, float yRot, float xRot, LivingEntity livingEntity) {
    renderEntity(x, y, yRot, xRot, 19, livingEntity);
  }

  public static void renderEntity(int x, int y, float yRot, float xRot, int scale,
      LivingEntity livingEntity) {
    float f = (float) Math.atan(yRot / 40.0F);
    float f1 = (float) Math.atan(xRot / 40.0F);
    PoseStack poseStack = RenderSystem.getModelViewStack();
    poseStack.pushPose();
    poseStack.translate(x, y, 1050.0D);
    poseStack.scale(1.0F, 1.0F, -1.0F);
    RenderSystem.applyModelViewMatrix();
    PoseStack poseStack1 = new PoseStack();
    poseStack1.translate(0.0D, 0.0D, 1000.0D);
    poseStack1.scale(scale, scale, scale);
    Quaternionf quaternionf = (new Quaternionf()).rotateZ((float) Math.PI);
    Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * Constants.PI_180DEG);
    quaternionf.mul(quaternionf1);
    poseStack1.mulPose(quaternionf);
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
    quaternionf1.conjugate();
    entityRenderDispatcher.overrideCameraOrientation(quaternionf1);
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

}
