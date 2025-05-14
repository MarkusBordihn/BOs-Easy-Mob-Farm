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

package de.markusbordihn.easymobfarm.loot;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.compat.CompatConstants;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.experience.ExperienceManager;
import de.markusbordihn.easymobfarm.item.consumables.MilkBottleItem;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.EggCollectorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyHarvesterFrameEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LootEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LuckEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.MilkExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.PollenTrapEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import de.markusbordihn.easymobfarm.server.player.FakePlayer;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Random random = new Random();
  private static FakePlayer fakePlayer;

  private LootManager() {}

  public static NonNullList<ItemStack> getEntityLoot(
      final MobCaptureData mobCaptureData,
      final List<EnhancementItem> enhancements,
      final Level level) {
    EntityType<?> entityType = mobCaptureData.entityType();
    if (entityType == null) {
      log.error("Unable to get entity type from Mob Capture data: {}", mobCaptureData);
      return NonNullList.create();
    }
    Entity entity = entityType.create(level);
    if (entity == null) {
      log.error("Unable to create entity {}!", entityType);
      return NonNullList.create();
    }

    // Load entity data from Mob Capture data
    entity.load(mobCaptureData.data());

    // Set additional entity properties for sheep entities
    if (entity instanceof Sheep sheepEntity) {
      sheepEntity.setSheared(false);
      if (mobCaptureData.hasColor()) {
        sheepEntity.setColor(mobCaptureData.color());
      }
    }

    // Get loot drops for entity
    NonNullList<ItemStack> drops = getEntityLoot(entity, enhancements, level);
    entity.discard();
    return drops;
  }

  public static NonNullList<ItemStack> getEntityLoot(
      final Entity entity, final List<EnhancementItem> enhancements, final Level level) {
    NonNullList<ItemStack> drops = NonNullList.create();
    if (!(entity instanceof LivingEntity livingEntity)
        || !(level instanceof ServerLevel serverLevel)) {
      return drops;
    }

    // Get fake player and loot context builder and add additional luck and roles.
    FakePlayer fakePlayer = getFakePlayer(serverLevel, entity.blockPosition());
    LootContext.Builder lootContextBuilder = createLootContextBuilder(serverLevel, livingEntity);
    float additionalLuck = 0;
    int additionalRolls = 0;
    for (EnhancementItem enhancement : enhancements) {
      if (enhancement instanceof SwordEnhancementItem) {
        setSwordEnhancementParameters(lootContextBuilder, fakePlayer);
        additionalLuck += 0.5f;
      }
      if (enhancement instanceof LootEnhancementItem) {
        additionalRolls += 1;
      }
      if (enhancement instanceof LuckEnhancementItem) {
        additionalLuck += 1f;
      }
    }

    // Add additional luck, if available.
    if (additionalLuck > 0f) {
      lootContextBuilder.withLuck(additionalLuck);
    }

    // Define loot context and loot table.
    ResourceLocation lootTableLocation = getLootTableLocation(livingEntity, enhancements);
    LootContext lootContext = lootContextBuilder.create(LootContextParamSets.ENTITY);

    // Get loot items from default loot table.
    if (lootTableLocation != null) {
      LootTable lootTable = serverLevel.getServer().getLootTables().get(lootTableLocation);
      for (int i = 0; i <= additionalRolls; i++) {
        lootTable.getRandomItems(lootContext).stream()
            .filter(itemStack -> !itemStack.isEmpty())
            .forEach(drops::add);
        handleSpecialEntityDrops(livingEntity, drops);
      }
    }

    // If no loot drops are available, try to get custom loot table.
    if (drops.isEmpty()) {
      ResourceLocation customLootTableLocation =
          getCustomLootTableLocation(livingEntity, enhancements);
      LootTable customLootTable =
          serverLevel.getServer().getLootTables().get(customLootTableLocation);
      if (customLootTable != LootTable.EMPTY) {
        log.debug(
            "No loot drops for {} trying custom loot table {}!",
            livingEntity,
            customLootTableLocation);
        for (int i = 0; i <= additionalRolls; i++) {
          customLootTable.getRandomItems(lootContext).stream()
              .filter(itemStack -> !itemStack.isEmpty())
              .forEach(drops::add);
        }
      } else {
        log.debug(
            "No loot drops for {} and no custom loot table {}!",
            livingEntity,
            customLootTableLocation);
      }
    }

    // Handle post drop enhancements.
    handlePostEnhancements(enhancements, livingEntity, fakePlayer, drops);

    return drops;
  }

  public static NonNullList<ItemStack> getLuckyLoot(
      final MobCaptureData mobCaptureData, final BlockPos blockPos, final Level Level) {
    NonNullList<ItemStack> drops = NonNullList.create();
    if (!(Level instanceof ServerLevel serverLevel)) {
      return drops;
    }

    // Use different loot tables based on rarity of mob capture data.
    ResourceLocation lootTableLocation =
        new ResourceLocation(
            "minecraft",
            switch (mobCaptureData.rarity()) {
              case COMMON -> "chests/simple_dungeon";
              case UNCOMMON -> "chests/village/village_toolsmith";
              case RARE -> "chests/stronghold_library";
              case EPIC -> "chests/end_city_treasure";
            });
    FakePlayer fakePlayer = getFakePlayer(serverLevel, blockPos);
    LootTable lootTable = serverLevel.getServer().getLootTables().get(lootTableLocation);
    LootContext.Builder lootContextBuilder = createLootChestContextBuilder(serverLevel, fakePlayer);
    lootContextBuilder.withLuck(
        switch (mobCaptureData.rarity()) {
          case COMMON -> 0.0f;
          case UNCOMMON -> 0.5f;
          case RARE -> 1.0f;
          case EPIC -> 1.5f;
        });
    LootContext lootContext = lootContextBuilder.create(LootContextParamSets.CHEST);
    lootTable.getRandomItems(lootContext).stream()
        .filter(itemStack -> !itemStack.isEmpty())
        .forEach(drops::add);
    return drops;
  }

  private static void handleSpecialEntityDrops(
      final LivingEntity livingEntity, final NonNullList<ItemStack> drops) {
    if (livingEntity instanceof WitherBoss) {
      if (random.nextInt(2) == 0) {
        drops.add(new ItemStack(Items.NETHER_STAR));
      }
      if (random.nextInt(2) == 0) {
        drops.add(new ItemStack(Items.WITHER_ROSE));
      }
    }
  }

  private static LootContext.Builder createLootContextBuilder(
      ServerLevel serverLevel, LivingEntity livingEntity) {
    return new LootContext.Builder(serverLevel)
        .withRandom(random)
        .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.GENERIC)
        .withParameter(LootContextParams.ORIGIN, livingEntity.position())
        .withParameter(LootContextParams.THIS_ENTITY, livingEntity);
  }

  private static LootContext.Builder createLootChestContextBuilder(
      ServerLevel serverLevel, LivingEntity livingEntity) {
    return new LootContext.Builder(serverLevel)
        .withRandom(random)
        .withParameter(LootContextParams.ORIGIN, livingEntity.position())
        .withParameter(LootContextParams.THIS_ENTITY, livingEntity);
  }

  private static ResourceLocation getLootTableLocation(
      LivingEntity livingEntity, List<EnhancementItem> enhancements) {
    ResourceLocation lootTableLocation = livingEntity.getType().getDefaultLootTable();
    for (EnhancementItem enhancement : enhancements) {
      if (enhancement instanceof SheepEnhancementItem && livingEntity instanceof Sheep sheep) {
        DyeColor color = sheep.getColor();
        lootTableLocation = new ResourceLocation("minecraft", "entities/sheep/" + color.getName());
      }
    }

    return lootTableLocation;
  }

  private static ResourceLocation getCustomLootTableLocation(
      LivingEntity livingEntity, List<EnhancementItem> enhancements) {
    ResourceLocation entityTypeResourceLocation =
        Registry.ENTITY_TYPE.getKey(livingEntity.getType());

    return new ResourceLocation(
        Constants.MOD_ID,
        "entities/"
            + entityTypeResourceLocation.getNamespace()
            + "/"
            + entityTypeResourceLocation.getPath());
  }

  private static void setSwordEnhancementParameters(
      LootContext.Builder lootContextBuilder, FakePlayer fakePlayer) {
    lootContextBuilder
        .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer)
        .withParameter(LootContextParams.KILLER_ENTITY, fakePlayer)
        .withParameter(LootContextParams.DIRECT_KILLER_ENTITY, fakePlayer);
  }

  private static void handlePostEnhancements(
      List<EnhancementItem> enhancements,
      LivingEntity livingEntity,
      FakePlayer fakePlayer,
      NonNullList<ItemStack> drops) {
    for (EnhancementItem enhancement : enhancements) {

      // Handle Experience enhancement
      if (enhancement instanceof ExperienceEnhancementItem experienceEnhancementItem
          && random.nextInt(experienceEnhancementItem.experienceDropChance()) == 0
          && ExperienceManager.shouldDropExperience(livingEntity)) {
        int experience = ExperienceManager.getExperienceReward(livingEntity, fakePlayer);
        if (experience >= experienceEnhancementItem.minExperienceForDrop()) {
          drops.add(new ItemStack(Items.EXPERIENCE_BOTTLE));
        } else {
          log.debug(
              "Experience drop of {} is below minimum threshold of {} for {}",
              experience,
              experienceEnhancementItem.minExperienceForDrop(),
              livingEntity);
        }
      }

      // Handle Bee specific enhancements
      if (livingEntity instanceof Bee) {
        if (enhancement instanceof HoneyHarvesterFrameEnhancementItem && random.nextInt(4) == 0) {
          drops.add(new ItemStack(Items.HONEYCOMB));
        } else if (enhancement instanceof HoneyExtractorEnhancementItem
            && random.nextInt(10) == 0) {
          drops.add(new ItemStack(Items.HONEY_BOTTLE));
        } else if (enhancement instanceof PollenTrapEnhancementItem && random.nextInt(5) == 0) {
          if (random.nextFloat() < 0.3f) {
            drops.add(getRandomFlower());
          } else {
            drops.add(getRandomDye());
          }
        }
      }

      // Handle Cow specific enhancements
      if (livingEntity instanceof Cow) {

        // Handle MilkExtractor enhancement (50% chance)
        if (enhancement instanceof MilkExtractorEnhancementItem && random.nextInt(2) == 0) {
          Item milkBottle;
          if (CompatConstants.MOD_FARMERS_DELIGHT_LOADED) {
            milkBottle =
                Registry.ITEM.get(
                    new ResourceLocation(
                        CompatConstants.MOD_FARMERS_DELIGHT_PREFIX
                            + CompatConstants.MOD_FARMERS_DELIGHT_MILK_BOTTLE));
          } else {
            milkBottle =
                Registry.ITEM.get(
                    new ResourceLocation(Constants.MOD_ID_PREFIX + MilkBottleItem.ID));
          }
          drops.add(new ItemStack(milkBottle));
        }
      }

      // Handle egg drops for chicken entities with Egg Collector enhancement (50% chance)
      if (livingEntity instanceof Chicken) {
        if (enhancement instanceof EggCollectorEnhancementItem && random.nextInt(2) == 0) {
          drops.add(new ItemStack(Items.EGG));
        }
      }
    }
  }

  private static FakePlayer getFakePlayer(ServerLevel level, BlockPos blockPos) {
    if (FakePlayer.isInvalidFakePlayer(fakePlayer)) {
      fakePlayer = new FakePlayer(level, blockPos);
      return fakePlayer;
    }
    return fakePlayer.updatePosition(level, blockPos);
  }

  private static ItemStack getRandomFlower() {
    List<Item> flowers =
        List.of(
            Items.DANDELION,
            Items.POPPY,
            Items.BLUE_ORCHID,
            Items.ALLIUM,
            Items.AZURE_BLUET,
            Items.RED_TULIP,
            Items.ORANGE_TULIP,
            Items.WHITE_TULIP,
            Items.PINK_TULIP,
            Items.OXEYE_DAISY,
            Items.CORNFLOWER,
            Items.LILY_OF_THE_VALLEY);
    return new ItemStack(flowers.get(new Random().nextInt(flowers.size())));
  }

  private static ItemStack getRandomDye() {
    List<Item> dyes =
        List.of(
            Items.YELLOW_DYE,
            Items.RED_DYE,
            Items.BLUE_DYE,
            Items.ORANGE_DYE,
            Items.PINK_DYE,
            Items.WHITE_DYE,
            Items.BLACK_DYE);
    return new ItemStack(dyes.get(new Random().nextInt(dyes.size())));
  }
}
