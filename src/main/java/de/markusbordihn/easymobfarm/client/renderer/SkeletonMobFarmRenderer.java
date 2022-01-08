
package de.markusbordihn.easymobfarm.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import de.markusbordihn.easymobfarm.block.entity.SkeletonMobFarmEntity;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class SkeletonMobFarmRenderer extends MobFarmRendererBase<SkeletonMobFarmEntity> {

  public SkeletonMobFarmRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public void render(SkeletonMobFarmEntity blockEntity, float partialTicks, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    super.render(blockEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);

    // Render Chicken
    if (blockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(getBlockRotation(blockEntity));
      poseStack.translate(0D, 0D, 2D / 16D);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-20));
      poseStack.scale(0.3F, 0.3F, 0.3F);
      renderSkeleton(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

  }

}
