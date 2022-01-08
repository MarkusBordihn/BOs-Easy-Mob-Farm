package de.markusbordihn.easymobfarm.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

public class RenderModels {

  protected Minecraft minecraft;
  protected Level level = null;

  private Chicken chicken = null;
  private ChickenRenderer chickenRenderer = null;

  private Cow cow = null;
  private CowRenderer cowRenderer = null;

  private Sheep sheep = null;
  private SheepRenderer sheepRenderer = null;

  private Skeleton skeleton = null;
  private SkeletonRenderer skeletonRenderer = null;

  private Zombie zombie = null;
  private ZombieRenderer zombieRenderer = null;

  public RenderModels(Minecraft minecraft) {
    this.minecraft = minecraft;
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

  public Cow getCow() {
    if (this.cow == null) {
      this.cow = new Cow(EntityType.COW, getLevel());
    }
    return this.cow;
  }

  public CowRenderer getCowRenderer() {
    if (this.cowRenderer == null) {
      this.cowRenderer = new CowRenderer(getEntityRenderer());
    }
    return this.cowRenderer;
  }

  public Sheep getSheep() {
    if (this.sheep == null) {
      this.sheep = new Sheep(EntityType.SHEEP, getLevel());
    }
    return this.sheep;
  }

  public SheepRenderer getSheepRenderer() {
    if (this.sheepRenderer == null) {
      this.sheepRenderer = new SheepRenderer(getEntityRenderer());
    }
    return this.sheepRenderer;
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

  public EntityRendererProvider.Context getEntityRenderer() {
    return new EntityRendererProvider.Context(minecraft.getEntityRenderDispatcher(),
        minecraft.getItemRenderer(), minecraft.getResourceManager(), minecraft.getEntityModels(),
        minecraft.font);
  }

}
