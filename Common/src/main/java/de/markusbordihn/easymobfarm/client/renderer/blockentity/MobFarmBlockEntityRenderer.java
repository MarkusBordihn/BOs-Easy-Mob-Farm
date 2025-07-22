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
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.client.renderer.manager.EntityScalingManager;
import de.markusbordihn.easymobfarm.client.renderer.manager.RendererManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmBlockEntityRenderer<T extends MobFarmBlockEntity>
    implements BlockEntityRenderer<T> {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public MobFarmBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

  @Override
  public void render(
      T blockEntity,
      float partialTicks,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int combinedLight,
      int combinedOverlay,
      Vec3 vec3) {

    if (!blockEntity.hasCapturedMob()) {
      RendererManager.removeEntity(blockEntity);
      return;
    }

    // Get entity from cache or create new entity.
    Entity entity = RendererManager.getOrCreateEntity(blockEntity);
    if (entity == null) {
      return;
    }

    // Try to render entity, if it fails, try to render generic entity.
    try {
      if (entity instanceof LivingEntity livingEntity) {
        renderLivingEntity(blockEntity, livingEntity, poseStack, buffer, combinedLight);
      } else if (entity instanceof Entity) {
        renderGenericEntity(blockEntity, entity, poseStack, buffer, combinedLight);
      }
    } catch (Exception livingException) {
      try {
        renderGenericEntity(blockEntity, entity, poseStack, buffer, combinedLight);
      } catch (Exception genericException) {
        log.error(
            "Failed to render entity {} for block entity {} with exception: {}",
            entity.getType(),
            blockEntity.getBlockPos(),
            genericException.getMessage());
      }
    }
  }

  private void renderLivingEntity(
      T blockEntity,
      LivingEntity entity,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int combinedLight) {

    EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
    EntityRenderer<LivingEntity, LivingEntityRenderState> renderer =
        (EntityRenderer<LivingEntity, LivingEntityRenderState>) dispatcher.getRenderer(entity);
    LivingEntityRenderState state = renderer.createRenderState(entity, 0);

    // Animation support
    entity.tickCount = (int) blockEntity.getLevel().getGameTime();
    MobCaptureCardDefinition mobCaptureCardDefinition =
        MobCaptureCardDefinitionManager.get(entity.getType());
    if (mobCaptureCardDefinition != null
        && mobCaptureCardDefinition.requiresAnimationTick()
        && entity.tickCount % 2 == 0) {
      entity.tick();
    }

    poseStack.pushPose();
    prepareEntityPose(blockEntity, entity, poseStack);
    renderer.render(state, poseStack, buffer, combinedLight);
    poseStack.popPose();
  }

  private void renderGenericEntity(
      T blockEntity,
      Entity entity,
      PoseStack poseStack,
      MultiBufferSource buffer,
      int combinedLight) {

    EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
    EntityRenderer renderer = dispatcher.getRenderer(entity);
    Object stateRaw = renderer.createRenderState(entity, 0);

    if (!(stateRaw instanceof EntityRenderState state)) {
      return;
    }

    entity.tickCount = (int) blockEntity.getLevel().getGameTime();

    poseStack.pushPose();
    prepareEntityPose(blockEntity, entity, poseStack);
    renderer.render(state, poseStack, buffer, combinedLight);
    poseStack.popPose();
  }

  private void prepareEntityPose(T blockEntity, Entity entity, PoseStack poseStack) {

    // Get Mob Farm Type for render adjustments like entity scaling and position.
    MobFarmType mobFarmType = blockEntity.getFarmType();
    if (mobFarmType == MobFarmType.LUCKY_DROP_FARM) {
      poseStack.translate(0.5, 0.19, 0.5);
    } else {
      poseStack.translate(0.5, 0.08, 0.5);
    }

    // Scale entity to fit into block.
    float entityScaling = EntityScalingManager.getEntityScale(entity);
    if (mobFarmType == MobFarmType.LUCKY_DROP_FARM) {
      entityScaling *= 0.75f;
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

    // Rotate and move entity based on entity type.
    if (entity instanceof AbstractSchoolingFish) {
      poseStack.translate(-0.1, 0.5, 0.1);
      poseStack.mulPose(Axis.XP.rotationDegrees(2.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(15.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
    } else if (entity instanceof Bee) {
      poseStack.translate(0, 0.5, 0);
    } else if (entity instanceof Squid) {
      poseStack.translate(0, 1.30, 0);
    } else if (entity instanceof Phantom) {
      poseStack.translate(0, 0.5, 0);
    } else if ((entity instanceof FlyingAnimal flyingAnimal && flyingAnimal.isFlying())
        || entity instanceof Guardian) {
      poseStack.translate(0, 0.3 / entityScaling, 0);
    } else if (entity instanceof EnderDragon) {
      poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
      poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
    }
  }
}
