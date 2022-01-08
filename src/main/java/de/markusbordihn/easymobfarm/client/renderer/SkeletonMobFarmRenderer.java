
package de.markusbordihn.easymobfarm.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.EntityType;
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

    if (!blockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      return;
    }

    // Get unique farm id for caching, the renderer itself is a single instance.
    int farmId = blockEntity.getFarmId();
    RenderHelper renderHelper = getRenderHelper(farmId, blockEntity);
    String farmMobType = blockEntity.getFarmMobType();

    // Render Skeleton
    if (farmMobType.equals("minecraft:skeleton")) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, 2D / 16D);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-20));
      poseStack.scale(0.25F, 0.25F, 0.25F);
      renderHelper.renderSkeleton(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Unknown entity (needs more performance)
    else if (blockEntity.getFarmMobEntityType() != null) {
      EntityType<?> entityType = blockEntity.getFarmMobEntityType();
      float customEntityScale = renderHelper.getCustomEntityScale();
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, -1D / 16D);
      poseStack.scale(customEntityScale, customEntityScale, customEntityScale);
      renderHelper.renderCustomEntity(entityType, poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

  }

}
