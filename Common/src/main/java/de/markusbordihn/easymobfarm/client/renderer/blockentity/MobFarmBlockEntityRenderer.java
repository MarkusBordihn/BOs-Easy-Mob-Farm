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
import com.mojang.math.Vector3f;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.client.renderer.manager.EntityScalingManager;
import de.markusbordihn.easymobfarm.client.renderer.manager.RendererManager;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmBlockEntityRenderer<T extends MobFarmBlockEntity>
    implements BlockEntityRenderer<T> {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  protected final Minecraft minecraft;
  private final Map<EntityType<?>, Entity> entityCache = new HashMap<>();
  protected BlockEntityRendererProvider.Context context = null;

  public MobFarmBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    this.context = context;
    this.minecraft = Minecraft.getInstance();
  }

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
    EntityRenderer<Entity> entityRenderer =
        (EntityRenderer<Entity>) entityRenderDispatcher.getRenderer(entity);

    // Animation support
    entity.tickCount = (int) blockEntity.getLevel().getGameTime();

    // Move entity to center of block.
    poseStack.pushPose();
    poseStack.translate(0.5, 0.05, 0.5);

    // Scale entity to fit into block.
    float entityScaling = EntityScalingManager.getBlockScale(entity);
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
    poseStack.mulPose(Vector3f.YP.rotationDegrees(rotationDegrees));

    // Rotate entity based on entity type.
    if (entity instanceof AbstractSchoolingFish) {
      poseStack.translate(-0.1, 0.5, 0.1);
      poseStack.mulPose(Vector3f.XP.rotationDegrees(2.0F));
      poseStack.mulPose(Vector3f.YP.rotationDegrees(15.0F));
      poseStack.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
    }

    // Render entity.
    entityRenderer.render(entity, 0, 0, poseStack, buffer, combinedLight);

    poseStack.popPose();
  }
}
