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
import net.minecraft.world.entity.EntityType;

import de.markusbordihn.easymobfarm.block.entity.AnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class AnimalFarmRenderer extends MobFarmRendererBase<AnimalPlainsFarmEntity> {

  public AnimalFarmRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public void render(AnimalPlainsFarmEntity blockEntity, float partialTicks, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    super.render(blockEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);

    // Get unique farm id for caching, the renderer itself is a single instance.
    int farmId = blockEntity.getFarmId();

    if (!blockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      resetRenderHelper(farmId);
      return;
    }

    // Get Render Helper for better performance and more FPS for other things.
    RenderHelper renderHelper = getRenderHelper(farmId, blockEntity);
    String farmMobType = blockEntity.getFarmMobType();

    // Render individual mob type if possible, because custom entity renderer is not optimized.
    // This makes a huge different with up to 20% more fps with a larger farm.

    // Render Chicken
    if (farmMobType.equals(PassiveAnimal.CHICKEN)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.7F, 0.7F, 0.7F);
      renderHelper.renderChicken(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Cow
    else if (farmMobType.equals(PassiveAnimal.COW)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.35F, 0.35F, 0.35F);
      renderHelper.renderCow(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Pig
    else if (farmMobType.equals(PassiveAnimal.PIG)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.38F, 0.38F, 0.38F);
      renderHelper.renderPig(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Sheep
    else if (farmMobType.equals(PassiveAnimal.SHEEP)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.38F, 0.38F, 0.38F);
      renderHelper.renderSheep(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Unknown entity (needs more performance)
    else if (blockEntity.getFarmMobEntityType() != null) {
      EntityType<?> entityType = blockEntity.getFarmMobEntityType();
      float customEntityScale = renderHelper.getCustomEntityScale();
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, 2D / 16D);
      poseStack.scale(customEntityScale, customEntityScale, customEntityScale);
      renderHelper.renderCustomEntity(entityType, poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

  }

}
