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
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.ChickenMobFarmEntity;

public class ChickenMobFarmRenderer extends MobFarmRendererBase<ChickenMobFarmEntity> {

  public ChickenMobFarmRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public void render(ChickenMobFarmEntity blockEntity, float partialTicks, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

    Direction direction = blockEntity.getBlockState().getValue(MobFarmBlock.FACING);

    // Render Chicken
    if (blockEntity.hasMob()) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-direction.toYRot()));
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(0.6F, 0.6F, 0.6F);
      renderChicken(0F, poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

  }

}
