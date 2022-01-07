
package de.markusbordihn.easymobfarm.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.SkeletonMobFarmEntity;

public class SkeletonMobFarmRenderer extends MobFarmRendererBase<SkeletonMobFarmEntity> {

  public SkeletonMobFarmRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public void render(SkeletonMobFarmEntity blockEntity, float partialTicks, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

    Direction direction = blockEntity.getBlockState().getValue(MobFarmBlock.FACING);

    // Render Chicken
    if (blockEntity.hasMob()) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-direction.toYRot()));
      poseStack.translate(0D, 0D, 2D / 16D);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-20));
      poseStack.scale(0.3F, 0.3F, 0.3F);
      renderSkeleton(0F, poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

  }

}
