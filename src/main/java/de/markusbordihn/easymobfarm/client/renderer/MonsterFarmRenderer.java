
package de.markusbordihn.easymobfarm.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.EntityType;

import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class MonsterFarmRenderer extends MobFarmRendererBase<MobFarmBlockEntity> {

  public MonsterFarmRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public void render(MobFarmBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
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

    // Render individual mob types, if possible, because custom entity renderer is not optimized.
    // This makes a huge different with up to 20% more fps with a larger farm.

    // Render Cave Spider
    if (farmMobType.equals(HostileMonster.CAVE_SPIDER)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 15.5D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, 0D);
      poseStack.mulPose(Vector3f.XP.rotationDegrees(-180));
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-180));
      poseStack.scale(0.35F, 0.35F, 0.35F);
      renderHelper.renderCaveSpider(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Creeper
    else if (farmMobType.equals(HostileMonster.CREEPER)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, 2D / 16D);
      poseStack.scale(0.25F, 0.25F, 0.25F);
      renderHelper.renderCreeper(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Skeleton
    else if (farmMobType.equals(HostileMonster.SKELETON)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, 2D / 16D);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-20));
      poseStack.scale(0.25F, 0.25F, 0.25F);
      renderHelper.renderSkeleton(poseStack, buffer, combinedLight);
      poseStack.popPose();
    }

    // Render Zombie
    else if (farmMobType.equals(HostileMonster.ZOMBIE)) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(renderHelper.getBlockRotation());
      poseStack.translate(0D, 0D, 2D / 16D);
      poseStack.mulPose(Vector3f.YP.rotationDegrees(-20));
      poseStack.scale(0.25F, 0.25F, 0.25F);
      renderHelper.renderZombie(poseStack, buffer, combinedLight);
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
