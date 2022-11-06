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

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.CaveSpiderRenderer;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EndermanRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.GlowSquidRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.PandaRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.client.renderer.entity.SheepRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cod;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Panda;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Salmon;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.mobs.HostileNetherMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;

public class RenderModels {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected Minecraft minecraft;
  protected Level level = null;

  private Entity customEntity = null;
  private float customEntityScale = 0F;
  private EntityRenderDispatcher entityRenderDispatcher = null;

  private CaveSpider caveSpider = null;
  private CaveSpiderRenderer caveSpiderRenderer = null;

  private Cod cod = null;
  private CodRenderer codRenderer = null;

  private Chicken chicken = null;
  private ChickenRenderer chickenRenderer = null;

  private Creeper creeper = null;
  private CreeperRenderer creeperRenderer = null;

  private Cow cow = null;
  private CowRenderer cowRenderer = null;

  private Blaze blaze = null;
  private BlazeRenderer blazeRenderer = null;

  private Drowned drowned = null;
  private DrownedRenderer drownedRenderer = null;

  private EnderMan enderman = null;
  private EndermanRenderer endermanRenderer = null;

  private Sheep sheep = null;
  private SheepRenderer sheepRenderer = null;

  private Panda panda = null;
  private PandaRenderer pandaRenderer = null;

  private Pig pig = null;
  private PigRenderer pigRenderer = null;

  private Rabbit rabbit = null;
  private RabbitRenderer rabbitRenderer = null;

  private Salmon salmon = null;
  private SalmonRenderer salmonRenderer = null;

  private Skeleton skeleton = null;
  private SkeletonRenderer skeletonRenderer = null;

  private Spider spider = null;
  private SpiderRenderer<?> spiderRenderer = null;

  private Squid squid = null;
  private SquidRenderer<?> squidRenderer = null;

  private GlowSquid glowSquid = null;
  private GlowSquidRenderer glowSquidRenderer = null;

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

  public LivingEntity getEntityTypeModel(String entityType) {
    switch (entityType) {
      case HostileNetherMonster.BLAZE:
        return getBlaze();
      case PassiveAnimal.RABBIT:
        return getRabbit();
      case PassiveAnimal.PANDA:
        return getPanda();
      default:
        return null;
    }
  }

  public Blaze getBlaze() {
    if (this.blaze == null) {
      this.blaze = new Blaze(EntityType.BLAZE, getLevel());
    }
    return this.blaze;
  }

  public BlazeRenderer getBlazeRenderer() {
    if (this.blazeRenderer == null) {
      this.blazeRenderer = new BlazeRenderer(getEntityRendererContext());
    }
    return this.blazeRenderer;
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

  public Cod getCod() {
    if (this.cod == null) {
      this.cod = new Cod(EntityType.COD, getLevel());
    }
    return this.cod;
  }

  public CodRenderer getCodRenderer() {
    if (this.codRenderer == null) {
      this.codRenderer = new CodRenderer(getEntityRendererContext());
    }
    return this.codRenderer;
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

  public Drowned getDrowned() {
    if (this.drowned == null) {
      this.drowned = new Drowned(EntityType.DROWNED, getLevel());
    }
    return this.drowned;
  }

  public DrownedRenderer getDrownedRenderer() {
    if (this.drownedRenderer == null) {
      this.drownedRenderer = new DrownedRenderer(getEntityRendererContext());
    }
    return this.drownedRenderer;
  }

  public EnderMan getEnderman() {
    if (this.enderman == null) {
      this.enderman = new EnderMan(EntityType.ENDERMAN, getLevel());
    }
    return this.enderman;
  }

  public EndermanRenderer getEndermanRenderer() {
    if (this.endermanRenderer == null) {
      this.endermanRenderer = new EndermanRenderer(getEntityRendererContext());
    }
    return this.endermanRenderer;
  }

  public Salmon getSalmon() {
    if (this.salmon == null) {
      this.salmon = new Salmon(EntityType.SALMON, getLevel());
    }
    return this.salmon;
  }

  public SalmonRenderer getSalmonRenderer() {
    if (this.salmonRenderer == null) {
      this.salmonRenderer = new SalmonRenderer(getEntityRendererContext());
    }
    return this.salmonRenderer;
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

  public Panda getPanda() {
    if (this.panda == null) {
      this.panda = new Panda(EntityType.PANDA, getLevel());
    }
    return this.panda;
  }

  public PandaRenderer getPandaRenderer() {
    if (this.pandaRenderer == null) {
      this.pandaRenderer = new PandaRenderer(getEntityRendererContext());
    }
    return this.pandaRenderer;
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

  public Rabbit getRabbit() {
    if (this.rabbit == null) {
      this.rabbit = new Rabbit(EntityType.RABBIT, getLevel());
    }
    return this.rabbit;
  }

  public RabbitRenderer getRabbitRenderer() {
    if (this.rabbitRenderer == null) {
      this.rabbitRenderer = new RabbitRenderer(getEntityRendererContext());
    }
    return this.rabbitRenderer;
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

  public Spider getSpider() {
    if (this.spider == null) {
      this.spider = new Spider(EntityType.SPIDER, getLevel());
    }
    return this.spider;
  }

  public SpiderRenderer getSpiderRenderer() {
    if (this.spiderRenderer == null) {
      this.spiderRenderer = new SpiderRenderer<>(getEntityRendererContext());
    }
    return this.spiderRenderer;
  }

  public Squid getSquid() {
    if (this.squid == null) {
      this.squid = new Squid(EntityType.SQUID, getLevel());
    }
    return this.squid;
  }

  public SquidRenderer getSquidRenderer() {
    if (this.squidRenderer == null) {
      this.squidRenderer = new SquidRenderer<>(getEntityRendererContext(),
          new SquidModel<>(getEntityRendererContext().bakeLayer(ModelLayers.SQUID)));
    }
    return this.squidRenderer;
  }

  public GlowSquid getGlowSquid() {
    if (this.glowSquid == null) {
      this.glowSquid = new GlowSquid(EntityType.GLOW_SQUID, getLevel());
    }
    return this.glowSquid;
  }

  public GlowSquidRenderer getGlowSquidRenderer() {
    if (this.glowSquidRenderer == null) {
      this.glowSquidRenderer = new GlowSquidRenderer(getEntityRendererContext(),
          new SquidModel<>(getEntityRendererContext().bakeLayer(ModelLayers.GLOW_SQUID)));
    }
    return this.glowSquidRenderer;
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
