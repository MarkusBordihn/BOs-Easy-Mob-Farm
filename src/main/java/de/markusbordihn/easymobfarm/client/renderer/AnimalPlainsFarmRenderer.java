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

package de.markusbordihn.easymobfarm.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import de.markusbordihn.easymobfarm.block.entity.AnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class AnimalPlainsFarmRenderer extends MobFarmRendererBase<AnimalPlainsFarmEntity> {

  public AnimalPlainsFarmRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public void render(AnimalPlainsFarmEntity blockEntity, float partialTicks, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    super.render(blockEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);

    log.info("{} {}", blockEntity.getFarmId(), blockEntity.getFarmMobColor());
    if (!blockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      return;
    }

    String farmMobType = blockEntity.getFarmMobType();

    // Render Chicken
    if (farmMobType.equals("minecraft:chicken")) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(getBlockRotation(blockEntity));
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.6F, 0.6F, 0.6F);
      renderChicken(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Cow
    else if (farmMobType.equals("minecraft:cow")) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(getBlockRotation(blockEntity));
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.5F, 0.5F, 0.5F);
      renderCow(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Sheep
    else if (farmMobType.equals("minecraft:sheep")) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(getBlockRotation(blockEntity));
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.5F, 0.5F, 0.5F);
      renderSheep(poseStack, buffer, combinedLight, blockEntity.getFarmMobColor());
      poseStack.popPose();
    }

  }

}
