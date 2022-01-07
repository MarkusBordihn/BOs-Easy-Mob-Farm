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

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;

public class MobFarmRendererBase<T extends MobFarmBlockEntity> implements BlockEntityRenderer<T> {

  protected Minecraft minecraft;
  protected Level level = null;
  protected BlockEntityRendererProvider.Context context = null;

  private Chicken chicken = null;
  private ChickenRenderer chickenRenderer = null;

  private Skeleton skeleton = null;
  private SkeletonRenderer skeletonRenderer = null;

  private Zombie zombie = null;
  private ZombieRenderer zombieRenderer = null;

  public MobFarmRendererBase(BlockEntityRendererProvider.Context context) {
    this.context = context;
    this.minecraft = Minecraft.getInstance();
  }

  @Override
  public void render(T blockEntity, float partialTicks, PoseStack matrixStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
  }

  public BlockRenderDispatcher getBlockRenderer() {
    return minecraft.getBlockRenderer();
  }

  public BlockEntityRenderDispatcher getBlockEntityRenderer() {
    return minecraft.getBlockEntityRenderDispatcher();
  }

  public EntityRendererProvider.Context getEntityRenderer() {
    return new EntityRendererProvider.Context(minecraft.getEntityRenderDispatcher(),
        minecraft.getItemRenderer(), minecraft.getResourceManager(), minecraft.getEntityModels(),
        minecraft.font);
  }

  public ItemRenderer getItemRenderer() {
    return minecraft.getItemRenderer();
  }

  public Level getLevel() {
    if (this.level == null) {
      this.level = this.minecraft.level;
    }
    return this.level;
  }

  public Chicken getChicken() {
    if (this.chicken == null) {
      this.chicken = new Chicken(EntityType.CHICKEN, getLevel());
    }
    return this.chicken;
  }

  public ChickenRenderer getChickenRenderer() {
    if (this.chickenRenderer == null) {
      this.chickenRenderer = new ChickenRenderer(getEntityRenderer());
    }
    return this.chickenRenderer;
  }

  public void renderChicken(float headRotation, PoseStack poseStack, MultiBufferSource buffer,
      int combinedLight) {
    getChickenRenderer().render(getChicken(), 0F, headRotation, poseStack, buffer, combinedLight);
  }

  public Skeleton getSkeleton() {
    if (this.skeleton == null) {
      this.skeleton = new Skeleton(EntityType.SKELETON, getLevel());
    }
    return this.skeleton;
  }

  public SkeletonRenderer getSkeletonRenderer() {
    if (this.skeletonRenderer == null) {
      this.skeletonRenderer = new SkeletonRenderer(getEntityRenderer());
    }
    return this.skeletonRenderer;
  }

  public void renderSkeleton(float headRotation, PoseStack poseStack, MultiBufferSource buffer,
      int combinedLight) {
    getSkeletonRenderer().render(getSkeleton(), 0F, headRotation, poseStack, buffer, combinedLight);
  }

  public Zombie getZombie() {
    if (this.zombie == null) {
      this.zombie = new Zombie(getLevel());
    }
    return this.zombie;
  }

  public ZombieRenderer getZombieRenderer() {
    if (this.zombieRenderer == null) {
      this.zombieRenderer = new ZombieRenderer(getEntityRenderer());
    }
    return this.zombieRenderer;
  }

}
