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

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;

public class MobFarmRendererBase<T extends MobFarmBlockEntity> implements BlockEntityRenderer<T> {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected Minecraft minecraft;
  protected Level level = null;
  protected BlockEntityRendererProvider.Context context = null;

  private LocalPlayer player;

  private RenderModels renderModels;

  private Random random = new Random();

  private int ticker = 1;
  private int headRotationGoal = random.nextInt(-1250, 1250);
  private int headRotation = 0;
  private float headRotationFloat = 0;

  public MobFarmRendererBase(BlockEntityRendererProvider.Context context) {
    this.context = context;
    this.minecraft = Minecraft.getInstance();
    this.renderModels = new RenderModels(this.minecraft);
  }

  @Override
  public void render(T blockEntity, float partialTicks, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    if (ticker++ % 400 == 0) {
      // Change head rotation for some simple animations.
      if (this.headRotation > this.headRotationGoal) {
        headRotation = headRotation - 1;
      } else if (this.headRotation < this.headRotationGoal) {
        headRotation = headRotation + 1;
      } else {
        headRotationGoal = random.nextInt(-1250, 1250);
      }
      headRotationFloat = headRotation / 100.0F;
      ticker = 1;
    }
  }

  public LocalPlayer getPlayer() {
    if (this.player == null) {
      this.player = this.minecraft.player;
    }
    return this.player;
  }

  public Quaternion getBlockRotation(BlockEntity blockEntity) {
    return Vector3f.YP
        .rotationDegrees(-blockEntity.getBlockState().getValue(MobFarmBlock.FACING).toYRot());
  }

  public void renderCow(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getCowRenderer().render(this.renderModels.getCow(), 0F,
        this.headRotationFloat, poseStack, buffer, combinedLight);
  }

  public void renderChicken(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getChickenRenderer().render(this.renderModels.getChicken(), 0F,
        this.headRotationFloat, poseStack, buffer, combinedLight);
  }

  public void renderChicken(float headRotation, PoseStack poseStack, MultiBufferSource buffer,
      int combinedLight) {
    this.renderModels.getChickenRenderer().render(this.renderModels.getChicken(), 0F, headRotation,
        poseStack, buffer, combinedLight);
  }

  public void renderSheep(PoseStack poseStack, MultiBufferSource buffer, int combinedLight,
      DyeColor color) {
    Sheep sheep = this.renderModels.getSheep();
    if (color != null) {
      sheep.setColor(color);
    }
    this.renderModels.getSheepRenderer().render(sheep, 0F, this.headRotationFloat, poseStack,
        buffer, combinedLight);
  }

  public void renderSkeleton(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getSkeletonRenderer().render(this.renderModels.getSkeleton(), 0F,
        this.headRotationFloat, poseStack, buffer, combinedLight);
  }

  public void renderSkeleton(float headRotation, PoseStack poseStack, MultiBufferSource buffer,
      int combinedLight) {
    this.renderModels.getSkeletonRenderer().render(this.renderModels.getSkeleton(), 0F,
        headRotation, poseStack, buffer, combinedLight);
  }

}
