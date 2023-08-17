/**
 * Copyright 2022 Markus Bordihn
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

package de.markusbordihn.easymobfarm.client.renderer.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.config.mobs.AmbientWaterAnimal;
import de.markusbordihn.easymobfarm.config.mobs.BeeAnimal;
import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileNetherMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileWaterMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import de.markusbordihn.easymobfarm.config.mobs.PassiveWaterAnimal;

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
  private boolean entitySheared;

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

  public float getRandomBodyRotation() {
    return this.bodyRotation;
  }

  public float getRandomHeadRotation() {
    return this.headRotation;
  }

  public DyeColor getEntityColor() {
    if (this.entityColor == null
        && this.blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity) {
      this.entityColor = mobFarmBlockEntity.getFarmMobColor();
    }
    return this.entityColor;
  }

  public boolean getEntityShearedStatus() {
    if (!this.entitySheared && this.blockEntity instanceof MobFarmBlockEntity mobFarmBlockEntity) {
      this.entitySheared = mobFarmBlockEntity.getFarmMobShearedStatus();
    }
    return this.entitySheared;
  }

  public void renderCustomEntity(EntityType<?> entityType, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight) {
    Entity entity = this.renderModels.getCustomEntity(entityType);
    if (entity != null) {
      this.renderModels.getEntityRendererDispatcher().render(entity, 0, 0, 0, 1F, this.headRotation,
          poseStack, buffer, combinedLight);
    }
  }

  public void renderCustomEntity(Entity entity, PoseStack poseStack, MultiBufferSource buffer,
      int combinedLight) {
    if (entity != null) {
      this.renderModels.getEntityRendererDispatcher().render(entity, 0, 0, 0, 1F, this.headRotation,
          poseStack, buffer, combinedLight);
    }
  }

  public float getCustomEntityScale() {
    return this.renderModels.getCustomEntityScale();
  }

  public void renderBee(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getBeeRenderer(), this.renderModels.getBee());
  }

  public void renderProductiveBee(PoseStack poseStack, MultiBufferSource buffer, float scale,
      double x, double y, double z, int combinedLight, String beeType, EntityType<?> entityType) {
    CompoundTag compoundTag = new CompoundTag();
    compoundTag.putString("type", beeType);
    Entity entity = this.renderModels.getCustomEntity(entityType, compoundTag);
    renderCustomModel(poseStack, buffer, scale, x, y, z, combinedLight, entity);
  }

  public void renderBlaze(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getBlazeRenderer(), this.renderModels.getBlaze());
  }

  public void renderCaveSpider(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, -180, -180, x, y, z, combinedLight,
        this.renderModels.getCaveSpiderRenderer(), this.renderModels.getCaveSpider());
  }

  public void renderCod(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, -90, -90, x, y, z, combinedLight,
        this.renderModels.getCodRenderer(), this.renderModels.getCod());
  }

  public void renderCow(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getCowRenderer(), this.renderModels.getCow());
  }

  public void renderCreeper(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getCreeperRenderer(), this.renderModels.getCreeper());
  }

  public void renderChicken(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getChickenRenderer(), this.renderModels.getChicken());
  }

  public void renderDrowned(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getDrownedRenderer(), this.renderModels.getDrowned());
  }

  public void renderEnderman(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getEndermanRenderer(), this.renderModels.getEnderman());
  }

  public void renderPanda(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getPandaRenderer(), this.renderModels.getPanda());
  }

  public void renderPig(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getPigRenderer(), this.renderModels.getPig());
  }

  public void renderRabbit(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getRabbitRenderer(), this.renderModels.getRabbit());
  }

  public void renderSalmon(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, -90, -90, x, y, z, combinedLight,
        this.renderModels.getSalmonRenderer(), this.renderModels.getSalmon());
  }

  public void renderSheep(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getSheepRenderer(),
        this.renderModels.getSheep(getEntityColor(), getEntityShearedStatus()));
  }

  public void renderSkeleton(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getSkeletonRenderer(), this.renderModels.getSkeleton());
  }

  @SuppressWarnings("unchecked")
  public void renderSpider(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, -180, -180, x, y, z, combinedLight,
        (LivingEntityRenderer<? super Spider, ?>) this.renderModels.getSpiderRenderer(),
        this.renderModels.getSpider());
  }

  @SuppressWarnings("unchecked")
  public void renderSquid(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        (LivingEntityRenderer<? super Squid, ?>) this.renderModels.getSquidRenderer(),
        this.renderModels.getSquid());
  }

  public void renderGlowSquid(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getGlowSquidRenderer(), this.renderModels.getGlowSquid());
  }

  public void renderZombie(PoseStack poseStack, MultiBufferSource buffer, float scale, double x,
      double y, double z, int combinedLight) {
    renderModel(poseStack, buffer, scale, x, y, z, combinedLight,
        this.renderModels.getZombieRenderer(), this.renderModels.getZombie());
  }

  public boolean renderAnimal(PoseStack poseStack, MultiBufferSource buffer, int combinedLight,
      String farmMobType) {
    // Render Animals using their specific Renderer and predefined scaling and position.
    switch (farmMobType) {
      case BeeAnimal.BEE:
        renderBee(poseStack, buffer, 0.5F, 0D, 0.25D, 2D / 16D, combinedLight);
        break;
      case PassiveAnimal.CHICKEN:
        renderChicken(poseStack, buffer, 0.5F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case PassiveAnimal.COW:
        renderCow(poseStack, buffer, 0.35F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case PassiveAnimal.PIG:
        renderPig(poseStack, buffer, 0.38F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case PassiveAnimal.RABBIT:
        renderRabbit(poseStack, buffer, 0.45F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case PassiveAnimal.SHEEP:
        renderSheep(poseStack, buffer, 0.38F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case PassiveAnimal.PANDA:
        renderPanda(poseStack, buffer, 0.35F, 0D, 0D, -1.5D / 16D, combinedLight);
        break;
      default:
        return false;
    }
    return true;
  }

  public boolean renderBee(PoseStack poseStack, MultiBufferSource buffer, int combinedLight,
      String farmMobType, String farmMobSubType, EntityType<?> entityType) {
    // Render Bee using their specific Renderer and predefined scaling and position.
    switch (farmMobType) {
      case BeeAnimal.BEE:
        renderBee(poseStack, buffer, 0.5F, 0D, 0.25D, 2D / 16D, combinedLight);
        break;
      case BeeAnimal.PRODUCTIVE_BEES_CONFIGURABLE:
        renderProductiveBee(poseStack, buffer, 0.5F, 0D, 0.25D, 2D / 16D, combinedLight,
            farmMobSubType, entityType);
        break;
      default:
        return false;
    }
    return true;
  }

  public boolean renderMonster(PoseStack poseStack, MultiBufferSource buffer, int combinedLight,
      String farmMobType) {
    // Render Monster using their specific Renderer and predefined scaling and position.
    switch (farmMobType) {
      case HostileNetherMonster.BLAZE:
        renderBlaze(poseStack, buffer, 0.35F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case HostileMonster.CAVE_SPIDER:
        renderCaveSpider(poseStack, buffer, 0.35F, 0D, 15D / 16D, 2D / 16D, combinedLight);
        break;
      case HostileMonster.CREEPER:
        renderCreeper(poseStack, buffer, 0.25F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case HostileMonster.ENDERMAN:
        renderEnderman(poseStack, buffer, 0.25F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case HostileMonster.SKELETON:
        renderSkeleton(poseStack, buffer, 0.25F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      case HostileMonster.SPIDER:
        renderSpider(poseStack, buffer, 0.35F, 0D, 15D / 16D, 2D / 16D, combinedLight);
        break;
      case HostileMonster.ZOMBIE:
        renderZombie(poseStack, buffer, 0.25F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      default:
        return false;
    }
    return true;
  }

  public boolean renderWaterEntity(PoseStack poseStack, MultiBufferSource buffer, int combinedLight,
      String farmMobType) {
    // Render Water using their specific Renderer and predefined scaling and position.
    switch (farmMobType) {
      case AmbientWaterAnimal.COD:
        renderCod(poseStack, buffer, 0.3F, 0D, 5D / 16D, 2D / 16D, combinedLight);
        break;
      case AmbientWaterAnimal.SALMON:
        renderSalmon(poseStack, buffer, 0.3F, 0D, 5D / 16D, 2D / 16D, combinedLight);
        break;
      case PassiveWaterAnimal.SQUID:
        renderSquid(poseStack, buffer, 0.3F, 0D, 7D / 16D, 2D / 16D, combinedLight);
        break;
      case PassiveWaterAnimal.GLOW_SQUID:
        renderGlowSquid(poseStack, buffer, 0.3F, 0D, 7D / 16D, 2D / 16D, combinedLight);
        break;
      case HostileWaterMonster.DROWNED:
        renderDrowned(poseStack, buffer, 0.25F, 0D, 0D, 2D / 16D, combinedLight);
        break;
      default:
        return false;
    }
    return true;
  }

  public boolean renderLivingEntity(PoseStack poseStack, MultiBufferSource buffer,
      int combinedLight, EntityType<?> entityType) {
    if (entityType != null) {
      renderCustomModel(poseStack, buffer, 0D, 0D, 1D / 16D, combinedLight, entityType);
    } else {
      return false;
    }
    return true;
  }

  @SuppressWarnings("java:S107")
  public boolean renderLivingEntity(PoseStack poseStack, MultiBufferSource buffer, float scale,
      double x, double y, double z, int combinedLight, EntityType<?> entityType) {
    if (entityType != null) {
      poseStack.pushPose();
      poseStack.scale(scale, scale, scale);
      renderCustomModel(poseStack, buffer, x, y, z, combinedLight, entityType);
      poseStack.popPose();
    } else {
      return false;
    }
    return true;
  }

  @SuppressWarnings("java:S107")
  public <T extends LivingEntity, M extends EntityModel<T>> void renderModel(PoseStack poseStack,
      MultiBufferSource buffer, float scale, float rotationX, float rotationY, double x, double y,
      double z, int combinedLight, LivingEntityRenderer<T, M> renderer, T livingEntity) {
    poseStack.pushPose();
    poseStack.translate(0.5D, 1D / 16D, 0.5D);
    poseStack.mulPose(getBlockRotation());
    poseStack.translate(x, y, z);
    poseStack.mulPose(Vector3f.XP.rotationDegrees(rotationX));
    poseStack.mulPose(Vector3f.YP.rotationDegrees(rotationY));
    poseStack.scale(scale, scale, scale);
    renderer.render(livingEntity, 0F, getRandomHeadRotation(), poseStack, buffer, combinedLight);
    poseStack.popPose();
  }

  @SuppressWarnings("java:S107")
  public <T extends LivingEntity, M extends EntityModel<T>> void renderModel(PoseStack poseStack,
      MultiBufferSource buffer, float scale, double x, double y, double z, int combinedLight,
      LivingEntityRenderer<T, M> renderer, T livingEntity) {
    poseStack.pushPose();
    poseStack.translate(0.5D, 1D / 16D, 0.5D);
    poseStack.mulPose(getBlockRotation());
    poseStack.translate(x, y, z);
    poseStack.scale(scale, scale, scale);
    renderer.render(livingEntity, 0F, getRandomHeadRotation(), poseStack, buffer, combinedLight);
    poseStack.popPose();
  }

  @SuppressWarnings("java:S107")
  public void renderCustomModel(PoseStack poseStack, MultiBufferSource buffer, float scale,
      float rotationX, float rotationY, double x, double y, double z, int combinedLight,
      EntityType<?> entityType) {
    if (entityType != null) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(getBlockRotation());
      poseStack.translate(x, y, z);
      poseStack.mulPose(Vector3f.XP.rotationDegrees(rotationX));
      poseStack.mulPose(Vector3f.YP.rotationDegrees(rotationY));
      poseStack.scale(scale, scale, scale);
      renderCustomEntity(entityType, poseStack, buffer, combinedLight);
      poseStack.popPose();
    }
  }

  @SuppressWarnings("java:S107")
  public void renderCustomModel(PoseStack poseStack, MultiBufferSource buffer, float scale,
      double x, double y, double z, int combinedLight, Entity entity) {
    if (entity != null) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(getBlockRotation());
      poseStack.translate(x, y, z);
      poseStack.scale(scale, scale, scale);
      renderCustomEntity(entity, poseStack, buffer, combinedLight);
      poseStack.popPose();
    }
  }

  @SuppressWarnings("java:S107")
  public void renderCustomModel(PoseStack poseStack, MultiBufferSource buffer, float scale,
      double x, double y, double z, int combinedLight, EntityType<?> entityType) {
    if (entityType != null) {
      poseStack.pushPose();
      poseStack.translate(0.5D, 1D / 16D, 0.5D);
      poseStack.mulPose(getBlockRotation());
      poseStack.translate(x, y, z);
      poseStack.scale(scale, scale, scale);
      renderCustomEntity(entityType, poseStack, buffer, combinedLight);
      poseStack.popPose();
    }
  }

  public void renderCustomModel(PoseStack poseStack, MultiBufferSource buffer, double x, double y,
      double z, int combinedLight, EntityType<?> entityType) {
    float customEntityScale = getCustomEntityScale();
    renderCustomModel(poseStack, buffer, customEntityScale, x, y, z, combinedLight, entityType);
  }

}
