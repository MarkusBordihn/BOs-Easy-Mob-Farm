/*
 * Copyright 2026 Markus Bordihn
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

package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobFarmBonusConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.loot.LootManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootManagerTestHelper {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final int FARM_LOOT_RUNS = 32;
  private static final int SPECIAL_DROP_RUNS = 200;
  private static final int LOOT_ENHANCEMENT_COUNT = 4;
  private static final int KEY_DROP_RUNS = 64;
  private static final int EXPERIENCE_DROP_RUNS = 100;
  private static final int MAX_TIER_LEVEL = 3;
  private static final Map<EntityType<?>, Item> KEY_DROPS = createKeyDrops();
  private static final List<EntityType<?>> ENHANCEMENT_TEST_ENTITY_TYPES =
      List.of(
          EntityType.BEE,
          EntityType.BLAZE,
          EntityType.CHICKEN,
          EntityType.COW,
          EntityType.FROG,
          EntityType.GOAT,
          EntityType.MAGMA_CUBE,
          EntityType.MOOSHROOM,
          EntityType.PIG,
          EntityType.SHEEP,
          EntityType.SKELETON,
          EntityType.SLIME,
          EntityType.SNOW_GOLEM,
          EntityType.TURTLE,
          EntityType.WITHER,
          EntityType.ZOMBIE);

  private LootManagerTestHelper() {}

  public static void testHoneyExtractorConvertsBonusHoneycomb(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));
    List<EnhancementItem> enhancements =
        List.of(
            (EnhancementItem) de.markusbordihn.easymobfarm.item.Items.HONEY_EXTRACTOR_ENHANCEMENT);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityType.BEE, null, null);

    GameTestHelpers.assertTrue(
        helper,
        "Honey Extractor should convert bonus honeycomb to honey bottle, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEY_BOTTLE));
  }

  public static void testBonusHoneycombPassesThroughWithoutExtractor(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.BEE, null, null);

    GameTestHelpers.assertTrue(
        helper,
        "Bonus honeycomb should pass through without extractor, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEYCOMB));
  }

  public static void testHoneyExtractorDoesNotConvertForNonBee(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));
    List<EnhancementItem> enhancements =
        List.of(
            (EnhancementItem) de.markusbordihn.easymobfarm.item.Items.HONEY_EXTRACTOR_ENHANCEMENT);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityType.COW, null, null);

    GameTestHelpers.assertTrue(
        helper,
        "Honey Extractor should not convert honeycombs for non-bee entities, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEYCOMB));
  }

  public static void testWitherSpecialDropsAreNotMultiplied(GameTestHelper helper) {
    ServerLevel serverLevel = helper.getLevel();
    Entity wither = EntityType.WITHER.create(serverLevel, EntitySpawnReason.EVENT);
    if (wither == null) {
      helper.fail("Unable to create a Wither for the special drop test.");
      return;
    }

    List<EnhancementItem> enhancements =
        Collections.nCopies(
            LOOT_ENHANCEMENT_COUNT,
            (EnhancementItem) de.markusbordihn.easymobfarm.item.Items.LOOT_ENHANCEMENT);
    int maxNetherStars = 0;
    int maxWitherRoses = 0;
    int runsWithNetherStar = 0;
    try {
      for (int run = 0; run < SPECIAL_DROP_RUNS; run++) {
        NonNullList<ItemStack> drops = LootManager.getEntityLoot(wither, enhancements, serverLevel);
        int netherStars = countItems(drops, Items.NETHER_STAR);
        maxNetherStars = Math.max(maxNetherStars, netherStars);
        maxWitherRoses = Math.max(maxWitherRoses, countItems(drops, Items.WITHER_ROSE));
        if (netherStars > 0) {
          runsWithNetherStar++;
        }
      }
    } finally {
      wither.discard();
    }

    GameTestHelpers.assertTrue(
        helper,
        "Wither special drops must happen but not scale with loot enhancements, got up to "
            + maxNetherStars
            + " nether stars and "
            + maxWitherRoses
            + " wither roses in "
            + runsWithNetherStar
            + " of "
            + SPECIAL_DROP_RUNS
            + " runs",
        runsWithNetherStar > 0 && maxNetherStars <= 1 && maxWitherRoses <= 1);
  }

  public static void testMalformedCaptureDataDoesNotThrow(GameTestHelper helper) {
    CompoundTag entityData = new CompoundTag();
    entityData.putString("id", "minecraft:cow");
    entityData.put("Pos", invalidPositionTag());
    MobCaptureData mobCaptureData =
        new MobCaptureData(
            "Cow", "minecraft:cow", EntityType.COW, entityData, null, null, Rarity.COMMON, false);

    NonNullList<ItemStack> drops =
        LootManager.getEntityLoot(mobCaptureData, List.of(), helper.getLevel());

    GameTestHelpers.assertTrue(
        helper,
        "Malformed mob capture data must return empty loot instead of throwing.",
        drops != null && drops.isEmpty());
  }

  public static void testCapturedMobsDropTheirKeyItems(GameTestHelper helper) {
    List<EnhancementItem> enhancements =
        List.of((EnhancementItem) de.markusbordihn.easymobfarm.item.Items.SWORD_ENHANCEMENT);
    List<String> missingDrops = new ArrayList<>();
    for (Map.Entry<EntityType<?>, Item> keyDrop : KEY_DROPS.entrySet()) {
      MobCaptureData mobCaptureData =
          MobCaptureManager.getMobCaptureData(
              MobCaptureManager.getMobCaptureCardItem(keyDrop.getKey(), helper.getLevel()));
      if (!dropsItem(helper, mobCaptureData, enhancements, keyDrop.getValue())) {
        missingDrops.add(keyDrop.getKey().toShortString() + " -> " + keyDrop.getValue());
      }
    }

    helper.assertTrue(
        missingDrops.isEmpty(),
        "Captured mobs never dropped their key item in "
            + KEY_DROP_RUNS
            + " runs: "
            + missingDrops);
    helper.succeed();
  }

  public static void testEveryDefinedMobProducesFarmLoot(GameTestHelper helper) {
    List<EnhancementItem> enhancements =
        List.of((EnhancementItem) de.markusbordihn.easymobfarm.item.Items.SWORD_ENHANCEMENT);
    List<String> mobsWithoutCard = new ArrayList<>();
    List<String> mobsWithoutLoot = new ArrayList<>();
    for (EntityType<?> entityType : GameTestHelpers.definedMobCaptureCardEntityTypes(helper)) {
      MobCaptureData mobCaptureData =
          MobCaptureManager.getMobCaptureData(
              MobCaptureManager.getMobCaptureCardItem(entityType, helper.getLevel()));
      if (mobCaptureData == null) {
        mobsWithoutCard.add(entityType.toShortString());
      } else if (!producesLoot(helper, mobCaptureData, enhancements)) {
        mobsWithoutLoot.add(entityType.toShortString());
      }
    }

    if (!mobsWithoutLoot.isEmpty()) {
      log.warn(
          "[Game Test] Captured mobs produced no farm loot in {} runs: {}",
          FARM_LOOT_RUNS,
          mobsWithoutLoot);
    }
    helper.assertTrue(
        mobsWithoutCard.isEmpty(), "No mob capture card could be created for: " + mobsWithoutCard);
    helper.succeed();
  }

  private static boolean producesLoot(
      GameTestHelper helper, MobCaptureData mobCaptureData, List<EnhancementItem> enhancements) {
    for (int i = 0; i < FARM_LOOT_RUNS; i++) {
      if (!LootManager.getEntityLoot(mobCaptureData, enhancements, helper.getLevel()).isEmpty()) {
        return true;
      }
    }
    return false;
  }

  public static void testCapturedMagmaCubeKeepsSizeForMagmaCream(GameTestHelper helper) {
    MagmaCube magmaCube = EntityType.MAGMA_CUBE.create(helper.getLevel(), EntitySpawnReason.EVENT);
    helper.assertTrue(magmaCube != null, "Unable to create a Magma Cube.");
    magmaCube.setSize(2, true);
    MobCaptureData mobCaptureData =
        MobCaptureManager.getMobCaptureData(MobCaptureManager.getMobCaptureCardItem(magmaCube));
    magmaCube.discard();

    helper.assertTrue(
        dropsItem(helper, mobCaptureData, List.of(), Items.MAGMA_CREAM),
        "Captured big Magma Cube never dropped Magma Cream in " + KEY_DROP_RUNS + " runs");
    helper.succeed();
  }

  public static void testCapturedRedSheepDropsRedWool(GameTestHelper helper) {
    Sheep sheep = EntityType.SHEEP.create(helper.getLevel(), EntitySpawnReason.EVENT);
    helper.assertTrue(sheep != null, "Unable to create a Sheep.");
    sheep.setColor(DyeColor.RED);
    MobCaptureData mobCaptureData =
        MobCaptureManager.getMobCaptureData(MobCaptureManager.getMobCaptureCardItem(sheep));
    sheep.discard();

    NonNullList<ItemStack> drops =
        LootManager.getEntityLoot(mobCaptureData, List.of(), helper.getLevel());
    LootManager.addBonusDrops(
        drops,
        MobFarmBonusConfig.getBonusDropEntries(
            MobFarmType.ANIMAL_PLAINS_FARM, MAX_TIER_LEVEL, EntityType.SHEEP),
        List.of(),
        EntityType.SHEEP,
        mobCaptureData.hasColor() ? mobCaptureData.color().getDyeColor() : null,
        mobCaptureData.variant());

    helper.assertTrue(
        countItems(drops, Items.RED_WOOL) > 0 && countItems(drops, Items.WHITE_WOOL) == 0,
        "Captured red sheep should only drop red wool, got: " + drops);
    helper.succeed();
  }

  public static void testExperienceEnhancementDropsExperienceBottles(GameTestHelper helper) {
    MobCaptureData mobCaptureData =
        MobCaptureManager.getMobCaptureData(
            MobCaptureManager.getMobCaptureCardItem(EntityType.ZOMBIE, helper.getLevel()));
    List<EnhancementItem> enhancements =
        List.of((EnhancementItem) de.markusbordihn.easymobfarm.item.Items.EXPERIENCE_ENHANCEMENT);

    int experienceBottles = 0;
    for (int run = 0; run < EXPERIENCE_DROP_RUNS; run++) {
      experienceBottles +=
          countItems(
              LootManager.getEntityLoot(mobCaptureData, enhancements, helper.getLevel()),
              Items.EXPERIENCE_BOTTLE);
    }

    helper.assertTrue(
        experienceBottles > 0,
        "Experience enhancement never dropped an experience bottle for a zombie in "
            + EXPERIENCE_DROP_RUNS
            + " runs");
    helper.succeed();
  }

  public static void testAllEnhancementsWorkForProblemMobs(GameTestHelper helper) {
    List<EnhancementItem> allEnhancements =
        BuiltInRegistries.ITEM.stream()
            .filter(EnhancementItem.class::isInstance)
            .map(EnhancementItem.class::cast)
            .toList();
    List<List<EnhancementItem>> enhancementCombinations = new ArrayList<>();
    allEnhancements.forEach(enhancement -> enhancementCombinations.add(List.of(enhancement)));
    enhancementCombinations.add(allEnhancements);

    List<String> failures = new ArrayList<>();
    for (EntityType<?> entityType : ENHANCEMENT_TEST_ENTITY_TYPES) {
      Entity entity = entityType.create(helper.getLevel(), EntitySpawnReason.EVENT);
      if (!(entity instanceof LivingEntity)) {
        failures.add(entityType.toShortString() + ": unable to create entity");
        continue;
      }

      try {
        for (List<EnhancementItem> enhancements : enhancementCombinations) {
          collectLootFailure(helper, entity, enhancements, failures);
        }
      } finally {
        entity.discard();
      }
    }

    helper.assertTrue(
        failures.isEmpty(), "Loot calculation failed for " + failures.size() + ": " + failures);
    helper.succeed();
  }

  private static void collectLootFailure(
      GameTestHelper helper,
      Entity entity,
      List<EnhancementItem> enhancements,
      List<String> failures) {
    try {
      NonNullList<ItemStack> drops =
          LootManager.getEntityLoot(entity, enhancements, helper.getLevel());
      for (MobFarmType mobFarmType : MobFarmType.values()) {
        LootManager.addBonusDrops(
            drops,
            MobFarmBonusConfig.getBonusDrop(mobFarmType, MAX_TIER_LEVEL, entity.getType()),
            enhancements,
            entity.getType(),
            null,
            null);
      }
    } catch (Exception e) {
      failures.add(entity.getType().toShortString() + " with " + enhancements + ": " + e);
    }
  }

  private static boolean dropsItem(
      GameTestHelper helper,
      MobCaptureData mobCaptureData,
      List<EnhancementItem> enhancements,
      Item item) {
    for (int run = 0; run < KEY_DROP_RUNS; run++) {
      NonNullList<ItemStack> drops =
          LootManager.getEntityLoot(mobCaptureData, enhancements, helper.getLevel());
      if (countItems(drops, item) > 0) {
        return true;
      }
    }
    return false;
  }

  private static Map<EntityType<?>, Item> createKeyDrops() {
    Map<EntityType<?>, Item> keyDrops = new LinkedHashMap<>();
    keyDrops.put(EntityType.BLAZE, Items.BLAZE_ROD);
    keyDrops.put(EntityType.CHICKEN, Items.CHICKEN);
    keyDrops.put(EntityType.COW, Items.BEEF);
    keyDrops.put(EntityType.CREEPER, Items.GUNPOWDER);
    keyDrops.put(EntityType.DROWNED, Items.ROTTEN_FLESH);
    keyDrops.put(EntityType.ENDERMAN, Items.ENDER_PEARL);
    keyDrops.put(EntityType.EVOKER, Items.TOTEM_OF_UNDYING);
    keyDrops.put(EntityType.GHAST, Items.GHAST_TEAR);
    keyDrops.put(EntityType.GUARDIAN, Items.PRISMARINE_SHARD);
    keyDrops.put(EntityType.HOGLIN, Items.PORKCHOP);
    keyDrops.put(EntityType.IRON_GOLEM, Items.IRON_INGOT);
    keyDrops.put(EntityType.PHANTOM, Items.PHANTOM_MEMBRANE);
    keyDrops.put(EntityType.PIG, Items.PORKCHOP);
    keyDrops.put(EntityType.RABBIT, Items.RABBIT_HIDE);
    keyDrops.put(EntityType.SHEEP, Items.MUTTON);
    keyDrops.put(EntityType.SHULKER, Items.SHULKER_SHELL);
    keyDrops.put(EntityType.SKELETON, Items.BONE);
    keyDrops.put(EntityType.SLIME, Items.SLIME_BALL);
    keyDrops.put(EntityType.SNOW_GOLEM, Items.SNOWBALL);
    keyDrops.put(EntityType.SPIDER, Items.STRING);
    keyDrops.put(EntityType.SQUID, Items.INK_SAC);
    keyDrops.put(EntityType.WITHER_SKELETON, Items.BONE);
    keyDrops.put(EntityType.ZOMBIE, Items.ROTTEN_FLESH);
    return keyDrops;
  }

  private static ListTag invalidPositionTag() {
    ListTag positionTag = new ListTag();
    positionTag.add(DoubleTag.valueOf(Double.NaN));
    positionTag.add(DoubleTag.valueOf(Double.NaN));
    positionTag.add(DoubleTag.valueOf(Double.NaN));
    return positionTag;
  }

  private static int countItems(NonNullList<ItemStack> drops, Item item) {
    int count = 0;
    for (ItemStack itemStack : drops) {
      if (itemStack.is(item)) {
        count += itemStack.getCount();
      }
    }
    return count;
  }
}
