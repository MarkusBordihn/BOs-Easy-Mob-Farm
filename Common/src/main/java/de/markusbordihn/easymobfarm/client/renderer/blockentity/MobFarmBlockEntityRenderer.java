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

package de.markusbordihn.easymobfarm.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.client.renderer.manager.EntityScalingManager;
import de.markusbordihn.easymobfarm.client.renderer.manager.RendererManager;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Bee;

public class MobFarmBlockEntityRenderer<T extends MobFarmBlockEntity>
    implements BlockEntityRenderer<T> {

  public MobFarmBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

  @Override
  public void render(
      T blockEntity,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int combinedLight,
      int combinedOverlay) {
    if (!blockEntity.hasCapturedMob()) {
      RendererManager.removeEntity(blockEntity);
      return;
    }

    // Get entity from cache or create new entity.
    Entity entity = RendererManager.getOrCreateEntity(blockEntity);
    if (entity == null) {
      return;
    }

    // Get entity render dispatcher.
    EntityRenderDispatcher entityRenderDispatcher =
        Minecraft.getInstance().getEntityRenderDispatcher();

    // Prepare entity rendering.
    EntityRenderer<Entity, LivingEntityRenderState> entityRenderer =
        (EntityRenderer<Entity, LivingEntityRenderState>)
            entityRenderDispatcher.getRenderer(entity);
    LivingEntityRenderState livingEntityRenderState = entityRenderer.createRenderState();

    // Animation support
    entity.tickCount = (int) blockEntity.getLevel().getGameTime();

    // Get Mob Farm Type for render adjustments like entity scaling and position.
    MobFarmType mobFarmType = blockEntity.getFarmType();

    // Move entity to center of block.
    poseStack.pushPose();
    if (mobFarmType != null) {
      if (mobFarmType == MobFarmType.LUCKY_DROP_FARM) {
        poseStack.translate(0.5, 0.19, 0.5);
      } else {
        poseStack.translate(0.5, 0.08, 0.5);
      }
    } else {
      poseStack.translate(0.5, 0.08, 0.5);
    }

    // Scale entity to fit into block.
    float entityScaling = EntityScalingManager.getBlockScale(entity);
    if (mobFarmType != null) {
      if (mobFarmType == MobFarmType.LUCKY_DROP_FARM) {
        entityScaling *= 0.75f;
      }
    }
    poseStack.scale(entityScaling, entityScaling, entityScaling);

    // Rotate entity based on block facing direction.
    float rotationDegrees =
        switch (blockEntity.getBlockState().getValue(MobFarmBlock.FACING)) {
          case NORTH -> 180f;
          case SOUTH -> 0f;
          case WEST -> -90f;
          case EAST -> 90f;
          default -> 0f;
        };
    poseStack.mulPose(Axis.YP.rotationDegrees(rotationDegrees));

    // Rotate entity based on entity type.
    if (entity instanceof AbstractSchoolingFish) {
      poseStack.translate(-0.1, 0.5, 0.1);
      poseStack.mulPose(Axis.XP.rotationDegrees(2.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(15.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
    } else if (entity instanceof Bee) {
      poseStack.translate(0, 0.5, 0);
    }

    // Render entity.
    entityRenderer.render(livingEntityRenderState, poseStack, buffer, combinedLight);

    poseStack.popPose();
  }
}
