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
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.fish.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmBlockEntityRenderer
    implements BlockEntityRenderer<MobFarmBlockEntity, MobFarmRenderState> {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Set<EntityType<?>> loggedRenderFailures = new HashSet<>();
  private final EntityRenderDispatcher entityRenderer;

  public MobFarmBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    this.entityRenderer = context.entityRenderer();
  }

  @Override
  public MobFarmRenderState createRenderState() {
    return new MobFarmRenderState();
  }

  @Override
  public void extractRenderState(
      MobFarmBlockEntity blockEntity,
      MobFarmRenderState renderState,
      float partialTicks,
      Vec3 cameraPos,
      ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
    BlockEntityRenderer.super.extractRenderState(
        blockEntity, renderState, partialTicks, cameraPos, crumblingOverlay);

    if (!blockEntity.hasCapturedMob() || blockEntity.getLevel() == null) {
      RendererManager.removeEntity(blockEntity);
      renderState.entityRenderState = null;
      return;
    }

    // Get entity from cache or create new entity.
    Entity entity = RendererManager.getOrCreateEntity(blockEntity);
    if (entity == null) {
      renderState.entityRenderState = null;
      return;
    }

    // Extract entity render state
    try {
      @SuppressWarnings("unchecked")
      EntityRenderer<Entity, EntityRenderState> renderer =
          (EntityRenderer<Entity, EntityRenderState>) this.entityRenderer.getRenderer(entity);
      EntityRenderState entityRenderState = renderer.createRenderState(entity, partialTicks);

      // Animation support
      entity.tickCount = (int) blockEntity.getLevel().getGameTime();
      MobCaptureCardDefinition mobCaptureCardDefinition =
          MobCaptureCardDefinitionManager.get(entity.getType());
      if ((mobCaptureCardDefinition != null
              && mobCaptureCardDefinition.requiresAnimationTick()
              && entity.tickCount % 2 == 0)
          || entity.tickCount % 200 == 0) {
        entity.tick();
      }

      // Extract render data and pass to rendering state
      renderer.extractRenderState(entity, entityRenderState, partialTicks);

      // Transfer light coords from block entity render state to entity render state
      entityRenderState.lightCoords = renderState.lightCoords;

      renderState.entityRenderState = entityRenderState;
      renderState.facing = blockEntity.getBlockState().getValue(MobFarmBlock.FACING);
      renderState.isLuckyDropFarm = blockEntity.getFarmType() == MobFarmType.LUCKY_DROP_FARM;
      renderState.entityScaling = EntityScalingManager.getEntityScale(entity);
      if (renderState.isLuckyDropFarm) {
        renderState.entityScaling *= 0.75f;
      }

      // Determine entity type for positioning
      renderState.entityType = determineEntityType(entity);

      // Calculate rotation based on facing direction
      renderState.rotationDegrees =
          switch (renderState.facing) {
            case NORTH -> 180f;
            case SOUTH -> 0f;
            case WEST -> -90f;
            case EAST -> 90f;
            default -> 0f;
          };

    } catch (Exception exception) {
      if (loggedRenderFailures.add(entity.getType())) {
        log.error(
            "Failed to extract render state for entity {} at block entity {}: {}",
            entity.getType(),
            blockEntity.getBlockPos(),
            exception.getMessage());
      }
      renderState.entityRenderState = null;
    }
  }

  @Override
  public void submit(
      MobFarmRenderState renderState,
      PoseStack poseStack,
      SubmitNodeCollector submitNodeCollector,
      CameraRenderState cameraRenderState) {

    if (renderState.entityRenderState == null) {
      return;
    }

    poseStack.pushPose();

    // Apply position and scale
    if (renderState.isLuckyDropFarm) {
      poseStack.translate(0.5, 0.19, 0.5);
    } else {
      poseStack.translate(0.5, 0.08, 0.5);
    }

    poseStack.scale(
        renderState.entityScaling, renderState.entityScaling, renderState.entityScaling);

    // Apply rotation based on facing direction
    poseStack.mulPose(Axis.YP.rotationDegrees(renderState.rotationDegrees));

    // Apply entity-specific transformations
    applyEntitySpecificTransformations(renderState, poseStack);

    // Submit entity for rendering
    this.entityRenderer.submit(
        renderState.entityRenderState,
        cameraRenderState,
        0.0,
        0.0,
        0.0,
        poseStack,
        submitNodeCollector);

    poseStack.popPose();
  }

  private MobFarmRenderState.EntityType determineEntityType(Entity entity) {
    if (entity instanceof AbstractSchoolingFish) {
      return MobFarmRenderState.EntityType.SCHOOLING_FISH;
    } else if (entity instanceof Bee) {
      return MobFarmRenderState.EntityType.BEE;
    } else if (entity instanceof Squid) {
      return MobFarmRenderState.EntityType.SQUID;
    } else if (entity instanceof Phantom) {
      return MobFarmRenderState.EntityType.PHANTOM;
    } else if (entity instanceof EnderDragon) {
      return MobFarmRenderState.EntityType.ENDER_DRAGON;
    } else if ((entity instanceof FlyingAnimal flyingAnimal && flyingAnimal.isFlying())
        || entity instanceof Guardian) {
      return MobFarmRenderState.EntityType.FLYING_ANIMAL;
    }
    return MobFarmRenderState.EntityType.GENERIC;
  }

  private void applyEntitySpecificTransformations(
      MobFarmRenderState renderState, PoseStack poseStack) {
    switch (renderState.entityType) {
      case SCHOOLING_FISH:
        poseStack.translate(-0.1, 0.5, 0.1);
        poseStack.mulPose(Axis.XP.rotationDegrees(2.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(15.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-90.0F));
        break;
      case BEE:
      case PHANTOM:
        poseStack.translate(0, 0.5, 0);
        break;
      case SQUID:
        poseStack.translate(0, 1.30, 0);
        break;
      case FLYING_ANIMAL:
        poseStack.translate(0, 0.3 / renderState.entityScaling, 0);
        break;
      case ENDER_DRAGON:
        poseStack.mulPose(Axis.XP.rotationDegrees(0.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(0.0F));
        break;
      case GENERIC:
      default:
        // No special transformations
        break;
    }
  }
}
