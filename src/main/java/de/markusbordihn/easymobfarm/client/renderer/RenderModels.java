package de.markusbordihn.easymobfarm.client.renderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import de.markusbordihn.easymobfarm.Constants;

public class RenderModels {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected Minecraft minecraft;
  protected Level level = null;

  private Entity customEntity = null;
  private float customEntityScale = 0F;
  private EntityRenderDispatcher entityRenderDispatcher = null;

  private CaveSpider caveSpider = null;
  private CaveSpiderRenderer caveSpiderRenderer = null;

  private Chicken chicken = null;
  private ChickenRenderer chickenRenderer = null;

  private Creeper creeper = null;
  private CreeperRenderer creeperRenderer = null;

  private Cow cow = null;
  private CowRenderer cowRenderer = null;

  private Sheep sheep = null;
  private SheepRenderer sheepRenderer = null;

  private Pig pig = null;
  private PigRenderer pigRenderer = null;

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

  public float getEntityScale(Entity entity) {
    if (entity != null) {
      AABB boundingBox = entity.getBoundingBox();
      float modelLength = (float) Math.max(
          Math.max(boundingBox.maxX - boundingBox.minX, boundingBox.maxY - boundingBox.minY),
          boundingBox.maxZ - boundingBox.minZ);
      return 0.5F / modelLength;
    }
    return 0.5F;
  }

  public Entity getCustomEntity(EntityType<?> entityType) {
    if (this.customEntity == null && entityType != null) {
      this.customEntity = entityType.create(getLevel());
    }
    return this.customEntity;
  }

  public float getCustomEntityScale() {
    if (this.customEntityScale == 0F) {
      if (this.customEntity != null) {
        this.customEntityScale = getEntityScale(this.customEntity);
      } else {
        return 0.3F;
      }
    }
    return this.customEntityScale;
  }

  public EntityRenderDispatcher getEntityRendererDispatcher() {
    if (this.entityRenderDispatcher == null) {
      this.entityRenderDispatcher = minecraft.getEntityRenderDispatcher();
    }
    return this.entityRenderDispatcher;
  }

  public ItemRenderer getItemRenderer() {
    return minecraft.getItemRenderer();
  }

  public CaveSpider getCaveSpider() {
    if (this.caveSpider == null) {
      this.caveSpider = new CaveSpider(EntityType.CAVE_SPIDER, getLevel());
    }
    return this.caveSpider;
  }

  public CaveSpiderRenderer getCaveSpiderRenderer() {
    if (this.caveSpiderRenderer == null) {
      this.caveSpiderRenderer = new CaveSpiderRenderer(getEntityRendererContext());
    }
    return this.caveSpiderRenderer;
  }

  public Chicken getChicken() {
    if (this.chicken == null) {
      this.chicken = new Chicken(EntityType.CHICKEN, getLevel());
    }
    return this.chicken;
  }

  public ChickenRenderer getChickenRenderer() {
    if (this.chickenRenderer == null) {
      this.chickenRenderer = new ChickenRenderer(getEntityRendererContext());
    }
    return this.chickenRenderer;
  }

  public Creeper getCreeper() {
    if (this.creeper == null) {
      this.creeper = new Creeper(EntityType.CREEPER, getLevel());
    }
    return this.creeper;
  }

  public CreeperRenderer getCreeperRenderer() {
    if (this.creeperRenderer == null) {
      this.creeperRenderer = new CreeperRenderer(getEntityRendererContext());
    }
    return this.creeperRenderer;
  }

  public Cow getCow() {
    if (this.cow == null) {
      this.cow = new Cow(EntityType.COW, getLevel());
    }
    return this.cow;
  }

  public CowRenderer getCowRenderer() {
    if (this.cowRenderer == null) {
      this.cowRenderer = new CowRenderer(getEntityRendererContext());
    }
    return this.cowRenderer;
  }

  public Sheep getSheep(DyeColor color) {
    if (this.sheep == null) {
      this.sheep = new Sheep(EntityType.SHEEP, getLevel());
      if (color != null) {
        this.sheep.setColor(color);
      }
    }
    return this.sheep;
  }

  public Sheep getSheep() {
    if (this.sheep == null) {
      this.sheep = new Sheep(EntityType.SHEEP, getLevel());
    }
    return this.sheep;
  }

  public SheepRenderer getSheepRenderer() {
    if (this.sheepRenderer == null) {
      this.sheepRenderer = new SheepRenderer(getEntityRendererContext());
    }
    return this.sheepRenderer;
  }

  public Pig getPig() {
    if (this.pig == null) {
      this.pig = new Pig(EntityType.PIG, getLevel());
    }
    return this.pig;
  }

  public PigRenderer getPigRenderer() {
    if (this.pigRenderer == null) {
      this.pigRenderer = new PigRenderer(getEntityRendererContext());
    }
    return this.pigRenderer;
  }

  public Skeleton getSkeleton() {
    if (this.skeleton == null) {
      this.skeleton = new Skeleton(EntityType.SKELETON, getLevel());
    }
    return this.skeleton;
  }

  public SkeletonRenderer getSkeletonRenderer() {
    if (this.skeletonRenderer == null) {
      this.skeletonRenderer = new SkeletonRenderer(getEntityRendererContext());
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
      this.zombieRenderer = new ZombieRenderer(getEntityRendererContext());
    }
    return this.zombieRenderer;
  }

  public EntityRendererProvider.Context getEntityRendererContext() {
    return new EntityRendererProvider.Context(minecraft.getEntityRenderDispatcher(),
        minecraft.getItemRenderer(), minecraft.getResourceManager(), minecraft.getEntityModels(),
        minecraft.font);
  }

}
