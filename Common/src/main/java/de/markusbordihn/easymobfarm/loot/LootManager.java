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
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.capture.MobVariantData;
import de.markusbordihn.easymobfarm.data.enhancement.FrogCatalystType;
import de.markusbordihn.easymobfarm.data.loot.LootTablePriority;
import de.markusbordihn.easymobfarm.experience.ExperienceManager;
import de.markusbordihn.easymobfarm.item.consumables.MilkBottleItem;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.EggCollectorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.FrogCatalystEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyHarvesterFrameEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.KnifeEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LootEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LuckEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.MilkExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.PollenTrapEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import de.markusbordihn.easymobfarm.server.player.FakePlayer;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Random random = new Random();
  private static final Set<ResourceLocation> loggedLegacyPaths = new HashSet<>();
  private static final Set<String> loggedLootFailures = new HashSet<>();
  private static final Map<String, ResourceLocation> FROG_CATALYST_RESOURCES =
      Map.ofEntries(
          Map.entry(
              "cold",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_COLD)),
          Map.entry(
              "temperate",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_TEMPERATE)),
          Map.entry(
              "warm",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_WARM)),
          Map.entry(
              "white",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_WHITE)),
          Map.entry(
              "orange",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_ORANGE)),
          Map.entry(
              "magenta",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_MAGENTA)),
          Map.entry(
              "light_blue",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_LIGHT_BLUE)),
          Map.entry(
              "yellow",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_YELLOW)),
          Map.entry(
              "lime",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_LIME)),
          Map.entry(
              "pink",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_PINK)),
          Map.entry(
              "gray",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_GRAY)),
          Map.entry(
              "light_gray",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_LIGHT_GRAY)),
          Map.entry(
              "cyan",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_CYAN)),
          Map.entry(
              "purple",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_PURPLE)),
          Map.entry(
              "blue",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_BLUE)),
          Map.entry(
              "brown",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_BROWN)),
          Map.entry(
              "green",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_GREEN)),
          Map.entry(
              "red",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_RED)),
          Map.entry(
              "black",
              ResourceLocation.fromNamespaceAndPath(
                  Constants.MOD_ID, FrogCatalystEnhancementItem.ID_BLACK)));
  private static final EnumMap<FrogCatalystType, ResourceLocation> FROGLIGHT_MAP =
      new EnumMap<>(FrogCatalystType.class);
  private static FakePlayer fakePlayer;

  static {
    if (CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
      FROGLIGHT_MAP.put(
          FrogCatalystType.WHITE,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "white_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.ORANGE,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "orange_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.MAGENTA,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "magenta_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.LIGHT_BLUE,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "light_blue_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.YELLOW,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "yellow_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.LIME,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "lime_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.PINK,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "pink_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.GRAY,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "gray_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.LIGHT_GRAY,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "light_gray_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.CYAN,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "cyan_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.PURPLE,
          ResourceLocation.fromNamespaceAndPath("minecraft", "pearlescent_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.BLUE,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "blue_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.BROWN,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "brown_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.GREEN,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "green_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.RED,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "red_froglight"));
      FROGLIGHT_MAP.put(
          FrogCatalystType.BLACK,
          ResourceLocation.fromNamespaceAndPath(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "black_froglight"));
    }
  }

  private LootManager() {}

  public static NonNullList<ItemStack> getEntityLoot(
      final MobCaptureData mobCaptureData,
      final List<EnhancementItem> enhancements,
      final Level level) {
    EntityType<?> entityType = mobCaptureData.entityType();
    if (entityType == null) {
      logLootFailure("Unable to get entity type from Mob Capture data", mobCaptureData);
      return NonNullList.create();
    }
    Entity entity = entityType.create(level);
    if (entity == null) {
      logLootFailure("Unable to create entity for Mob Capture data", mobCaptureData);
      return NonNullList.create();
    }

    try {
      entity.load(mobCaptureData.data());

      if (entity instanceof Sheep sheepEntity) {
        sheepEntity.setSheared(false);
        if (mobCaptureData.hasColor()) {
          sheepEntity.setColor(mobCaptureData.color().getDyeColor());
        }
      }
      if (entity instanceof Pillager pillager
          && MobVariantData.LEADER_VARIANT.equals(mobCaptureData.variant())) {
        pillager.setPatrolLeader(true);
      }

      return getEntityLoot(entity, enhancements, level);
    } catch (Exception e) {
      logLootFailure("Unable to get loot for Mob Capture data", mobCaptureData, e);
      return NonNullList.create();
    } finally {
      entity.discard();
    }
  }

  public static List<ItemStack> getEntityLootPreview(
      final MobCaptureData mobCaptureData, final Level level) {
    if (!(level instanceof ServerLevel)
        || mobCaptureData == null
        || mobCaptureData.entityType() == null) {
      return List.of();
    }
    EntityType<?> entityType = mobCaptureData.entityType();
    Entity entity = entityType.create(level);
    if (entity == null) {
      return List.of();
    }
    try {
      entity.load(mobCaptureData.data());
      if (entity instanceof Sheep sheepEntity) {
        sheepEntity.setSheared(false);
        if (mobCaptureData.hasColor()) {
          sheepEntity.setColor(mobCaptureData.color().getDyeColor());
        }
      }
      if (entity instanceof Pillager pillager
          && MobVariantData.LEADER_VARIANT.equals(mobCaptureData.variant())) {
        pillager.setPatrolLeader(true);
      }
      Map<String, ItemStack> uniqueItems = new LinkedHashMap<>();
      for (int roll = 0; roll < 3; roll++) {
        NonNullList<ItemStack> loot = getEntityLoot(entity, List.of(), level);
        for (ItemStack stack : loot) {
          String key = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
          uniqueItems.putIfAbsent(key, stack.copy());
        }
      }
      return new ArrayList<>(uniqueItems.values());
    } catch (Exception e) {
      logLootFailure("Unable to get loot preview for Mob Capture data", mobCaptureData, e);
      return List.of();
    } finally {
      entity.discard();
    }
  }

  private static void logLootFailure(final String reason, final MobCaptureData mobCaptureData) {
    if (loggedLootFailures.add(reason + ":" + mobCaptureData.entityType())) {
      log.error("{} {}!", reason, mobCaptureData);
      return;
    }

    log.debug("{} {}!", reason, mobCaptureData);
  }

  private static void logLootFailure(
      final String reason, final MobCaptureData mobCaptureData, final Exception exception) {
    String failureKey = mobCaptureData.entityType() + ":" + exception.getClass().getName();
    if (loggedLootFailures.add(failureKey)) {
      log.error("{} {}:", reason, mobCaptureData, exception);
      return;
    }

    log.debug("{} {}: {}", reason, mobCaptureData, exception.getMessage());
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
    LootParams.Builder lootContextBuilder = createLootContextBuilder(serverLevel, livingEntity);
    float additionalLuck = 0;
    int additionalRolls = 0;
    for (EnhancementItem enhancement : enhancements) {
      if (enhancement instanceof SwordEnhancementItem
          && MobFarmConfig.isSwordEnhancementEnabled()) {
        setSwordEnhancementParameters(lootContextBuilder, fakePlayer, serverLevel);
        additionalLuck += MobFarmConfig.swordEnhancementAdditionalLuck;
      }
      if (enhancement instanceof KnifeEnhancementItem
          && MobFarmConfig.isKnifeEnhancementEnabled()) {
        setKnifeEnhancementParameters(lootContextBuilder, fakePlayer, serverLevel);
        additionalLuck += MobFarmConfig.knifeEnhancementAdditionalLuck;
      }
      if (enhancement instanceof LootEnhancementItem && MobFarmConfig.isLootEnhancementEnabled()) {
        additionalRolls += MobFarmConfig.lootEnhancementAdditionalRolls;
      }
      if (enhancement instanceof LuckEnhancementItem && MobFarmConfig.isLuckEnhancementEnabled()) {
        additionalLuck += MobFarmConfig.luckEnhancementAdditionalLuck;
      }
    }

    // Add additional luck, if available.
    if (additionalLuck > 0f) {
      lootContextBuilder.withLuck(additionalLuck);
    }

    // Define loot context and loot table.
    ResourceKey<LootTable> lootTableLocation = getLootTableLocation(livingEntity, enhancements);
    LootParams lootParams = lootContextBuilder.create(LootContextParamSets.ENTITY);

    // 1. Try to use overwrite loot table first.
    if (addLootFromCustomTable(
        LootTablePriority.OVERWRITE,
        livingEntity,
        serverLevel,
        lootParams,
        additionalRolls,
        drops)) {
      handlePostEnhancements(enhancements, livingEntity, serverLevel, fakePlayer, drops);
      handleKnifeEnhancementLoot(enhancements, livingEntity, serverLevel, lootParams, drops);
      return drops;
    }

    // 2. Then use priority loot table.
    addLootFromCustomTable(
        LootTablePriority.PRIORITY, livingEntity, serverLevel, lootParams, additionalRolls, drops);

    // 3. Then use vanilla loot table.
    if (lootTableLocation != null) {
      LootTable lootTable =
          serverLevel.getServer().reloadableRegistries().getLootTable(lootTableLocation);
      for (int i = 0; i <= additionalRolls; i++) {
        lootTable.getRandomItems(lootParams).stream()
            .filter(itemStack -> !itemStack.isEmpty())
            .forEach(drops::add);
      }
      handleSpecialEntityDrops(livingEntity, drops);
    }

    // 4. Then use bonus loot table.
    addLootFromCustomTable(
        LootTablePriority.BONUS, livingEntity, serverLevel, lootParams, additionalRolls, drops);

    // 5. Finally use fallback loot table, if no drops are available yet.
    if (drops.isEmpty()
        && !addLootFromCustomTable(
            LootTablePriority.FALLBACK,
            livingEntity,
            serverLevel,
            lootParams,
            additionalRolls,
            drops)) {
      ResourceKey<LootTable> legacyLocation =
          getCustomLootTableLocation(livingEntity, LootTablePriority.LEGACY, false);
      LootTable legacyTable =
          serverLevel.getServer().reloadableRegistries().getLootTable(legacyLocation);
      if (legacyTable != LootTable.EMPTY) {
        if (loggedLegacyPaths.add(legacyLocation.location())) {
          log.warn(
              "Using legacy loot table path {} - please move to entities/fallback/",
              legacyLocation);
        }
        for (int i = 0; i <= additionalRolls; i++) {
          legacyTable.getRandomItems(lootParams).stream()
              .filter(itemStack -> !itemStack.isEmpty())
              .forEach(drops::add);
        }
      }
    }

    handlePostEnhancements(enhancements, livingEntity, serverLevel, fakePlayer, drops);
    handleKnifeEnhancementLoot(enhancements, livingEntity, serverLevel, lootParams, drops);

    return drops;
  }

  public static NonNullList<ItemStack> getLuckyLoot(
      final MobCaptureData mobCaptureData, final BlockPos blockPos, final Level level) {
    NonNullList<ItemStack> drops = NonNullList.create();
    if (!(level instanceof ServerLevel serverLevel)) {
      return drops;
    }

    // Use different loot tables based on rarity of mob capture data.
    ResourceKey lootTableLocation =
        ResourceKey.create(
            Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(
                "minecraft",
                switch (mobCaptureData.rarity()) {
                  case COMMON -> "chests/simple_dungeon";
                  case UNCOMMON -> "chests/village/village_toolsmith";
                  case RARE -> "chests/stronghold_library";
                  case EPIC -> "chests/end_city_treasure";
                }));
    FakePlayer fakePlayer = getFakePlayer(serverLevel, blockPos);
    LootTable lootTable =
        serverLevel.getServer().reloadableRegistries().getLootTable(lootTableLocation);
    LootParams.Builder lootContextBuilder = createLootChestContextBuilder(serverLevel, fakePlayer);
    lootContextBuilder.withLuck(
        switch (mobCaptureData.rarity()) {
          case COMMON -> 0.0f;
          case UNCOMMON -> 0.5f;
          case RARE -> 1.0f;
          case EPIC -> 1.5f;
        });
    LootParams lootContext = lootContextBuilder.create(LootContextParamSets.CHEST);
    lootTable.getRandomItems(lootContext).stream()
        .filter(itemStack -> !itemStack.isEmpty())
        .forEach(drops::add);
    return drops;
  }

  public static void addBonusDrops(
      NonNullList<ItemStack> drops,
      List<ItemStack> bonusDrops,
      List<EnhancementItem> enhancements,
      EntityType<?> entityType,
      DyeColor color) {
    for (ItemStack drop : bonusDrops) {
      if (drop.isEmpty()) {
        continue;
      }
      if (entityType == EntityType.SHEEP && color != null && drop.is(Items.WHITE_WOOL)) {
        drops.add(new ItemStack(getWoolItem(color), drop.getCount()));
      } else if (entityType == EntityType.BEE
          && drop.is(Items.HONEYCOMB)
          && enhancements.stream()
              .anyMatch(
                  enhancement ->
                      enhancement instanceof HoneyExtractorEnhancementItem
                          && MobFarmConfig.isEnhancementEnabled(enhancement))) {
        drops.add(new ItemStack(Items.HONEY_BOTTLE));
      } else {
        drops.add(drop.copy());
      }
    }
  }

  private static Item getWoolItem(final DyeColor color) {
    Item woolItem =
        BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace(color.getName() + "_wool"));
    if (woolItem == Items.AIR) {
      return Items.WHITE_WOOL;
    }

    return woolItem;
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

  private static LootParams.Builder createLootContextBuilder(
      ServerLevel serverLevel, LivingEntity livingEntity) {
    return new LootParams.Builder(serverLevel)
        .withParameter(LootContextParams.DAMAGE_SOURCE, serverLevel.damageSources().generic())
        .withParameter(LootContextParams.ORIGIN, livingEntity.position())
        .withParameter(LootContextParams.THIS_ENTITY, livingEntity);
  }

  private static LootParams.Builder createLootChestContextBuilder(
      ServerLevel serverLevel, LivingEntity livingEntity) {
    return new LootParams.Builder(serverLevel)
        .withParameter(LootContextParams.ORIGIN, livingEntity.position())
        .withParameter(LootContextParams.THIS_ENTITY, livingEntity);
  }

  private static ResourceKey<LootTable> getLootTableLocation(
      LivingEntity livingEntity, List<EnhancementItem> enhancements) {
    ResourceKey<LootTable> lootTableLocation = livingEntity.getType().getDefaultLootTable();
    for (EnhancementItem enhancement : enhancements) {
      if (enhancement instanceof SheepEnhancementItem
          && MobFarmConfig.isEnhancementEnabled(enhancement)
          && livingEntity instanceof Sheep sheep) {
        DyeColor color = sheep.getColor();
        lootTableLocation =
            ResourceKey.create(
                Registries.LOOT_TABLE,
                ResourceLocation.fromNamespaceAndPath(
                    "minecraft", "entities/sheep/" + color.getName()));
      }
    }

    return lootTableLocation;
  }

  private static ResourceKey<LootTable> getCustomLootTableLocation(
      final LivingEntity livingEntity,
      final LootTablePriority priority,
      final boolean withVariant) {
    ResourceLocation entityTypeResourceLocation =
        BuiltInRegistries.ENTITY_TYPE.getKey(livingEntity.getType());
    String entityPath = entityTypeResourceLocation.getPath();
    if (withVariant) {
      String variant = MobVariantData.getVariant(livingEntity);
      if (variant != null && !variant.isEmpty()) {
        entityPath = entityPath + "_" + variant;
      }
    }
    String path =
        priority.getPath().isEmpty() ? "entities/" : "entities/" + priority.getPath() + "/";
    return ResourceKey.create(
        Registries.LOOT_TABLE,
        ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, path + entityTypeResourceLocation.getNamespace() + "/" + entityPath));
  }

  private static boolean addLootFromCustomTable(
      final LootTablePriority priority,
      final LivingEntity livingEntity,
      final ServerLevel serverLevel,
      final LootParams lootParams,
      final int additionalRolls,
      final NonNullList<ItemStack> drops) {
    ResourceKey<LootTable> variantLocation =
        getCustomLootTableLocation(livingEntity, priority, true);
    LootTable customTable =
        serverLevel.getServer().reloadableRegistries().getLootTable(variantLocation);
    if (customTable == LootTable.EMPTY) {
      ResourceKey<LootTable> baseLocation =
          getCustomLootTableLocation(livingEntity, priority, false);
      if (!baseLocation.equals(variantLocation)) {
        customTable = serverLevel.getServer().reloadableRegistries().getLootTable(baseLocation);
      }
    }
    if (customTable != LootTable.EMPTY) {
      for (int i = 0; i <= additionalRolls; i++) {
        customTable.getRandomItems(lootParams).stream()
            .filter(itemStack -> !itemStack.isEmpty())
            .forEach(drops::add);
      }
      return true;
    }
    return false;
  }

  private static void setSwordEnhancementParameters(
      LootParams.Builder lootParamsBuilder, FakePlayer fakePlayer, ServerLevel serverLevel) {
    ItemStack swordItem = new ItemStack(Items.IRON_SWORD);
    fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, swordItem);
    lootParamsBuilder
        .withParameter(
            LootContextParams.DAMAGE_SOURCE, serverLevel.damageSources().playerAttack(fakePlayer))
        .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer)
        .withParameter(LootContextParams.ATTACKING_ENTITY, fakePlayer)
        .withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, fakePlayer);
  }

  private static void setKnifeEnhancementParameters(
      LootParams.Builder lootParamsBuilder, FakePlayer fakePlayer, ServerLevel serverLevel) {
    ItemStack knifeItem = getKnifeTool();
    if (!knifeItem.isEmpty()) {
      fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, knifeItem);
    }
    lootParamsBuilder
        .withParameter(
            LootContextParams.DAMAGE_SOURCE, serverLevel.damageSources().playerAttack(fakePlayer))
        .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer)
        .withParameter(LootContextParams.ATTACKING_ENTITY, fakePlayer)
        .withParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, fakePlayer);
  }

  private static ItemStack getKnifeTool() {
    if (CompatConstants.MOD_FARMERS_DELIGHT_LOADED) {
      Item farmersDelightKnife =
          BuiltInRegistries.ITEM.get(
              ResourceLocation.fromNamespaceAndPath("farmersdelight", "iron_knife"));
      if (farmersDelightKnife != Items.AIR) {
        return new ItemStack(farmersDelightKnife);
      }
    }
    return ItemStack.EMPTY;
  }

  private static void handlePostEnhancements(
      List<EnhancementItem> enhancements,
      LivingEntity livingEntity,
      ServerLevel serverLevel,
      FakePlayer fakePlayer,
      NonNullList<ItemStack> drops) {
    for (EnhancementItem enhancement : enhancements) {

      // Handle Experience enhancement
      if (enhancement instanceof ExperienceEnhancementItem experienceEnhancementItem
          && MobFarmConfig.isEnhancementEnabled(enhancement)
          && random.nextInt(experienceEnhancementItem.experienceDropChance()) == 0
          && ExperienceManager.shouldDropExperience(livingEntity)) {
        int experience = ExperienceManager.getExperienceReward(serverLevel, livingEntity);
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

      // Handle Mob specific enhancements
      if (livingEntity instanceof Bee) {
        if (enhancement instanceof HoneyHarvesterFrameEnhancementItem
            && MobFarmConfig.isEnhancementEnabled(enhancement)
            && random.nextInt(4) == 0) {
          drops.add(new ItemStack(Items.HONEYCOMB));
        } else if (enhancement instanceof HoneyExtractorEnhancementItem
            && MobFarmConfig.isEnhancementEnabled(enhancement)
            && random.nextInt(4) == 0) {
          drops.add(new ItemStack(Items.HONEY_BOTTLE));
        } else if (enhancement instanceof PollenTrapEnhancementItem
            && MobFarmConfig.isEnhancementEnabled(enhancement)
            && random.nextInt(5) == 0) {
          if (random.nextFloat() < 0.3f) {
            drops.add(getRandomFlower());
          } else {
            drops.add(getRandomDye());
          }
        }
      } else if (livingEntity instanceof Cow) {
        // Handle MilkExtractor enhancement (50% chance)
        if (enhancement instanceof MilkExtractorEnhancementItem
            && MobFarmConfig.isEnhancementEnabled(enhancement)
            && random.nextInt(2) == 0) {
          Item milkBottle;
          if (CompatConstants.MOD_FARMERS_DELIGHT_LOADED) {
            milkBottle =
                BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(
                        CompatConstants.MOD_FARMERS_DELIGHT_ID,
                        CompatConstants.MOD_FARMERS_DELIGHT_MILK_BOTTLE));
          } else {
            milkBottle =
                BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, MilkBottleItem.ID));
          }
          drops.add(new ItemStack(milkBottle));
        }
      }

      // Handle egg drops for chicken entities with Egg Collector enhancement (50% chance)
      if (livingEntity instanceof Chicken) {
        if (enhancement instanceof EggCollectorEnhancementItem
            && MobFarmConfig.isEnhancementEnabled(enhancement)
            && random.nextInt(2) == 0) {
          drops.add(new ItemStack(Items.EGG));
        }
      } else if (livingEntity instanceof Frog) {
        // Dropping frog catalyst with a 2.5% change for the corresponding variant.
        if (random.nextInt(40) == 0) {
          String frogVariant = MobVariantData.getVariant(livingEntity);
          ResourceLocation frogCatalystResourceLocation = FROG_CATALYST_RESOURCES.get(frogVariant);
          if (frogCatalystResourceLocation != null) {
            Item frogCatalystItem = BuiltInRegistries.ITEM.get(frogCatalystResourceLocation);
            if (frogCatalystItem
                instanceof FrogCatalystEnhancementItem frogCatalystEnhancementItem) {
              drops.add(new ItemStack(frogCatalystEnhancementItem));
            } else {
              log.warn(
                  "Frog Catalyst item {} is not an instance of FrogCatalystEnhancementItem!",
                  frogCatalystItem);
            }
          } else {
            log.warn("No Frog Catalyst resource found for variant {}!", frogVariant);
          }
        }
      } else if (livingEntity instanceof MagmaCube) {
        // Adding additional Magma cream drop with 12.5% chance
        if (random.nextInt(8) == 0) {
          drops.add(new ItemStack(Items.MAGMA_CREAM));
        }

        // Adding additional Frog Light drop with a 50% chance with frog catalyst enhancement.
        if (enhancement instanceof FrogCatalystEnhancementItem frogCatalystEnhancementItem
            && MobFarmConfig.isEnhancementEnabled(enhancement)
            && random.nextInt(2) == 0) {
          FrogCatalystType frogCatalystType = frogCatalystEnhancementItem.getFrogCatalystType();
          // Handle basic frog catalyst drops based on type.
          switch (frogCatalystType) {
            case COLD -> drops.add(new ItemStack(Items.VERDANT_FROGLIGHT));
            case TEMPERATE -> drops.add(new ItemStack(Items.OCHRE_FROGLIGHT));
            case WARM -> drops.add(new ItemStack(Items.PEARLESCENT_FROGLIGHT));
            default -> {
              if (!CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
                log.error("Unknown Frog Catalyst type {}", frogCatalystType);
              }
            }
          }

          // Handle Swampier Swamps mod support
          if (CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
            ResourceLocation frogCatalystResourceLocation = FROGLIGHT_MAP.get(frogCatalystType);
            if (frogCatalystResourceLocation != null) {
              Item frogCatalystItem = BuiltInRegistries.ITEM.get(frogCatalystResourceLocation);
              if (frogCatalystItem != Items.AIR) {
                drops.add(new ItemStack(frogCatalystItem));
              } else {
                log.warn(
                    "Swampier Swamps: Frog Catalyst item {} is not available!", frogCatalystType);
              }
            }
          }
        }
      }
    }
  }

  private static void handleKnifeEnhancementLoot(
      List<EnhancementItem> enhancements,
      LivingEntity livingEntity,
      ServerLevel serverLevel,
      LootParams lootParams,
      NonNullList<ItemStack> drops) {
    boolean hasKnifeEnhancement = false;
    for (EnhancementItem enhancement : enhancements) {
      if (enhancement instanceof KnifeEnhancementItem
          && MobFarmConfig.isEnhancementEnabled(enhancement)) {
        hasKnifeEnhancement = true;
        break;
      }
    }

    if (!hasKnifeEnhancement) {
      return;
    }

    ResourceLocation entityTypeResourceLocation =
        BuiltInRegistries.ENTITY_TYPE.getKey(livingEntity.getType());
    ResourceKey<LootTable> knifeLootTableLocation =
        ResourceKey.create(
            Registries.LOOT_TABLE,
            ResourceLocation.fromNamespaceAndPath(
                Constants.MOD_ID,
                "enhancement/knife/"
                    + entityTypeResourceLocation.getNamespace()
                    + "/"
                    + entityTypeResourceLocation.getPath()));

    LootTable knifeLootTable =
        serverLevel.getServer().reloadableRegistries().getLootTable(knifeLootTableLocation);
    if (knifeLootTable != LootTable.EMPTY) {
      knifeLootTable.getRandomItems(lootParams).stream()
          .filter(itemStack -> !itemStack.isEmpty())
          .forEach(drops::add);
    }
  }

  private static FakePlayer getFakePlayer(ServerLevel level, BlockPos blockPos) {
    if (FakePlayer.isInvalidFakePlayer(fakePlayer)) {
      fakePlayer = new FakePlayer(level, blockPos);
    } else {
      fakePlayer.updatePosition(level, blockPos);
    }
    fakePlayer.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
    return fakePlayer;
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
