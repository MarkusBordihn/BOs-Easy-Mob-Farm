package de.markusbordihn.easymobfarm.client.renderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;

public class RenderHelper {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected Minecraft minecraft;
  protected BlockEntity blockEntity;
  protected int renderId;

  private Random random = new Random();

  // Random head position
  private float headRotation = random.nextFloat(-12.0F, 12.0F);
  private float bodyRotation = random.nextFloat(-12.0F, 12.0F);

  // Internal Cache
  private Quaternion blockRotation;
  private RenderModels renderModels;
  private DyeColor entityColor;

  public RenderHelper(int renderId, Minecraft minecraft, BlockEntity blockEntity) {
    this.minecraft = minecraft;
    this.blockEntity = blockEntity;
    this.renderId = renderId;
    this.renderModels = new RenderModels(minecraft);
  }

  public Quaternion getBlockRotation() {
    if (this.blockRotation == null) {
      this.blockRotation = Vector3f.YP
          .rotationDegrees(-this.blockEntity.getBlockState().getValue(MobFarmBlock.FACING).toYRot()
              + this.bodyRotation);
    }
    return this.blockRotation;
  }

  public DyeColor getEntityColor() {
    if (this.entityColor == null
        && this.blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity) {
      this.entityColor = mobFarmBlockEntity.getFarmMobColor();
    }
    return this.entityColor;
  }

  public void renderCustomEntity(EntityType<?> entityType, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight) {
    Entity entity = this.renderModels.getCustomEntity(entityType);
    if (entity != null) {
      this.renderModels.getEntityRendererDispatcher().render(entity, 0, 0, 0, 1F, this.headRotation,
          poseStack, buffer, combinedLight);
    }
  }

  public float getCustomEntityScale() {
    return this.renderModels.getCustomEntityScale();
  }

  public void renderCaveSpider(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getCaveSpiderRenderer().render(this.renderModels.getCaveSpider(), 0F,
        this.headRotation, poseStack, buffer, combinedLight);
  }

  public void renderCow(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getCowRenderer().render(this.renderModels.getCow(), 0F, this.headRotation,
        poseStack, buffer, combinedLight);
  }

  public void renderCreeper(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getCreeperRenderer().render(this.renderModels.getCreeper(), 0F,
        this.headRotation, poseStack, buffer, combinedLight);
  }

  public void renderChicken(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getChickenRenderer().render(this.renderModels.getChicken(), 0F,
        this.headRotation, poseStack, buffer, combinedLight);
  }

  public void renderPig(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getPigRenderer().render(this.renderModels.getPig(), 0F, this.headRotation,
        poseStack, buffer, combinedLight);
  }

  public void renderSheep(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    Sheep sheep = this.renderModels.getSheep(getEntityColor());
    this.renderModels.getSheepRenderer().render(sheep, 0F, this.headRotation, poseStack, buffer,
        combinedLight);
  }

  public void renderSkeleton(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getSkeletonRenderer().render(this.renderModels.getSkeleton(), 0F,
        this.headRotation, poseStack, buffer, combinedLight);
  }

  public void renderZombie(PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
    this.renderModels.getZombieRenderer().render(this.renderModels.getZombie(), 0F,
        this.headRotation, poseStack, buffer, combinedLight);
  }

}
