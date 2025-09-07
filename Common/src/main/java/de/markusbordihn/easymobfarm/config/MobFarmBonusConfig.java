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

package de.markusbordihn.easymobfarm.config;

import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MobFarmBonusConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_farm_bonus.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Mob Farm Bonus Configuration

 This configuration file allows you to define the bonus drops for the Mob Farms.

 Configuration Format:
 --------------------
 <mob_farm_name>::<tier_level>::<entity_type> = <item_name>::<amount>::<chance 1 of x>

 Available Mob Farm Types:
 ------------------------
 - animal_plains_farm: For animals like cows, sheep, chickens, pigs
 - bee_hive_farm: For bees and honey production
 - desert_farm: For desert mobs like husks, rabbits, camels
 - iron_golem_farm: For iron golems and poppy drops
 - jungle_farm: For jungle mobs like parrots, pandas, ocelots
 - monster_plains_cave_farm: For common monsters like zombies, skeletons, spiders
 - nether_fortress_farm: For nether mobs like blazes, magma cubes, wither skeletons
 - ocean_farm: For ocean mobs like cod, salmon, squid, guardians
 - swamp_farm: For swamp mobs like frogs, slimes, witches

 Tier Levels (better farms = better bonus chances):
 -------------------------------------------------
 - 0: Basic tier (lowest bonus chance)
 - 1: Improved tier (better bonus chance)
 - 2: Advanced tier (good bonus chance)
 - 3: Elite tier (highest bonus chance)

 Configuration Examples:
 ----------------------
 Basic bee farm with 1 in 20 chance for honeycomb:
   bee_hive_farm::0::minecraft:bee = minecraft:honeycomb::1::20

 Elite bee farm with 1 in 5 chance for honeycomb:
   bee_hive_farm::3::minecraft:bee = minecraft:honeycomb::1::5

 Iron golem farm with bonus iron ingots:
   iron_golem_farm::2::minecraft:iron_golem = minecraft:iron_ingot::2::8

 Multiple bonus items for the same mob (different lines):
   ocean_farm::1::minecraft:cod = minecraft:cod::1::10
   ocean_farm::1::minecraft:cod = minecraft:bone_meal::1::25

 Important Notes:
 ---------------
 - Lower chance numbers = higher drop probability (1 = always, 100 = 1% chance)
 - To disable a bonus drop, set the amount to 0
 - Each mob farm type targets specific biome-appropriate mobs
 - Higher tier farms have better default bonus chances

""";
  public static final String LOG_PREFIX = "[MobFarmBonusConfig]";

  private static final Random random = new Random();
  private static final HashMap<String, HashMap<Integer, ItemStack>> mobFarmBonusMap =
      new HashMap<>();
  private static final HashMap<String, HashMap<Integer, ItemStack>> defaultMobFarmBonusMap =
      new HashMap<>();

  static {
    // Animal Plains Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:cow",
        new HashMap<>(Map.of(20, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:cow",
        new HashMap<>(Map.of(15, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:cow",
        new HashMap<>(Map.of(10, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:cow",
        new HashMap<>(Map.of(5, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:sheep",
        new HashMap<>(Map.of(20, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:sheep",
        new HashMap<>(Map.of(15, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:sheep",
        new HashMap<>(Map.of(10, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:sheep",
        new HashMap<>(Map.of(5, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:chicken",
        new HashMap<>(Map.of(20, new ItemStack(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:chicken",
        new HashMap<>(Map.of(15, new ItemStack(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:chicken",
        new HashMap<>(Map.of(10, new ItemStack(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:chicken",
        new HashMap<>(Map.of(5, new ItemStack(Items.EGG, 1))));

    // Bee Hive Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::0::minecraft:bee",
        new HashMap<>(Map.of(20, new ItemStack(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::1::minecraft:bee",
        new HashMap<>(Map.of(15, new ItemStack(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::2::minecraft:bee",
        new HashMap<>(Map.of(10, new ItemStack(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::3::minecraft:bee",
        new HashMap<>(Map.of(5, new ItemStack(Items.HONEYCOMB, 1))));

    // Desert Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::0::minecraft:husk",
        new HashMap<>(Map.of(20, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::1::minecraft:husk",
        new HashMap<>(Map.of(15, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::2::minecraft:husk",
        new HashMap<>(Map.of(10, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::3::minecraft:husk",
        new HashMap<>(Map.of(5, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::0::minecraft:rabbit",
        new HashMap<>(Map.of(20, new ItemStack(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::1::minecraft:rabbit",
        new HashMap<>(Map.of(15, new ItemStack(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::2::minecraft:rabbit",
        new HashMap<>(Map.of(10, new ItemStack(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::3::minecraft:rabbit",
        new HashMap<>(Map.of(5, new ItemStack(Items.RABBIT_HIDE, 1))));

    // Iron Golem Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::0::minecraft:iron_golem",
        new HashMap<>(Map.of(20, new ItemStack(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::1::minecraft:iron_golem",
        new HashMap<>(Map.of(15, new ItemStack(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::2::minecraft:iron_golem",
        new HashMap<>(Map.of(10, new ItemStack(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::3::minecraft:iron_golem",
        new HashMap<>(Map.of(5, new ItemStack(Items.IRON_INGOT, 1))));

    // Jungle Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::0::minecraft:parrot",
        new HashMap<>(Map.of(20, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::1::minecraft:parrot",
        new HashMap<>(Map.of(15, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::2::minecraft:parrot",
        new HashMap<>(Map.of(10, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::3::minecraft:parrot",
        new HashMap<>(Map.of(5, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::0::minecraft:panda",
        new HashMap<>(Map.of(20, new ItemStack(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::1::minecraft:panda",
        new HashMap<>(Map.of(15, new ItemStack(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::2::minecraft:panda",
        new HashMap<>(Map.of(10, new ItemStack(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::3::minecraft:panda",
        new HashMap<>(Map.of(5, new ItemStack(Items.BAMBOO, 1))));

    // Monster Plains Cave Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::0::minecraft:zombie",
        new HashMap<>(Map.of(20, new ItemStack(Items.ROTTEN_FLESH, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::1::minecraft:zombie",
        new HashMap<>(Map.of(15, new ItemStack(Items.ROTTEN_FLESH, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::2::minecraft:zombie",
        new HashMap<>(Map.of(10, new ItemStack(Items.ROTTEN_FLESH, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::3::minecraft:zombie",
        new HashMap<>(Map.of(5, new ItemStack(Items.ROTTEN_FLESH, 1))));

    // Nether Fortress Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::0::minecraft:blaze",
        new HashMap<>(Map.of(20, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::1::minecraft:blaze",
        new HashMap<>(Map.of(15, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::2::minecraft:blaze",
        new HashMap<>(Map.of(10, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::3::minecraft:blaze",
        new HashMap<>(Map.of(5, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::0::minecraft:magma_cube",
        new HashMap<>(Map.of(20, new ItemStack(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::1::minecraft:magma_cube",
        new HashMap<>(Map.of(15, new ItemStack(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::2::minecraft:magma_cube",
        new HashMap<>(Map.of(10, new ItemStack(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::3::minecraft:magma_cube",
        new HashMap<>(Map.of(5, new ItemStack(Items.MAGMA_CREAM, 1))));

    // Ocean Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::0::minecraft:cod",
        new HashMap<>(Map.of(20, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::1::minecraft:cod",
        new HashMap<>(Map.of(15, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::2::minecraft:cod",
        new HashMap<>(Map.of(10, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::3::minecraft:cod",
        new HashMap<>(Map.of(5, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::0::minecraft:squid",
        new HashMap<>(Map.of(20, new ItemStack(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::1::minecraft:squid",
        new HashMap<>(Map.of(15, new ItemStack(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::2::minecraft:squid",
        new HashMap<>(Map.of(10, new ItemStack(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::3::minecraft:squid",
        new HashMap<>(Map.of(5, new ItemStack(Items.INK_SAC, 1))));

    // Swamp Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:frog",
        new HashMap<>(Map.of(20, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:frog",
        new HashMap<>(Map.of(15, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:frog",
        new HashMap<>(Map.of(10, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:frog",
        new HashMap<>(Map.of(5, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:slime",
        new HashMap<>(Map.of(20, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:slime",
        new HashMap<>(Map.of(15, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:slime",
        new HashMap<>(Map.of(10, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:slime",
        new HashMap<>(Map.of(5, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:witch",
        new HashMap<>(Map.of(20, new ItemStack(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:witch",
        new HashMap<>(Map.of(15, new ItemStack(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:witch",
        new HashMap<>(Map.of(10, new ItemStack(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:witch",
        new HashMap<>(Map.of(5, new ItemStack(Items.REDSTONE, 1))));
  }

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Add default values to config file, if not present.
    defaultMobFarmBonusMap.forEach(
        (mobFarmName, bonusMap) -> {
          if (!properties.containsKey(mobFarmName)) {
            bonusMap.forEach(
                (chance, itemStack) -> {
                  String itemName = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString();
                  String value = itemName + "::" + itemStack.getCount() + "::" + chance;
                  properties.setProperty(mobFarmName, value);
                });
          }
        });

    // Parse config file
    properties.forEach(
        (key, value) -> {
          // Parse key to extract mob farm name, tier level and entity type
          String[] keyParts = parseKey((String) key);
          if (keyParts == null) {
            return;
          }
          String mobFarmName = keyParts[0];
          int tierLevel = Integer.parseInt(keyParts[1]);
          String entityType = keyParts[2];

          // Parse value to extract item name, amount and chance
          String[] valueParts = parseValue((String) value);
          if (valueParts == null || valueParts[0].isEmpty()) {
            return;
          }
          String itemName = valueParts[0];
          int amount = Integer.parseInt(valueParts[1]);
          int chance = Integer.parseInt(valueParts[2]);

          addBonusDropEntry(mobFarmName, tierLevel, entityType, chance, itemName, amount);
        });

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static String getMobFarmKey(String mobFarmName, int tierLevel, String entityType) {
    return mobFarmName + "::" + tierLevel + "::" + entityType;
  }

  public static void addBonusDropEntry(
      String mobFarmName,
      int tierLevel,
      String entityType,
      int chance,
      String itemName,
      int amount) {

    // Check if item name is valid
    Optional<Item> item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(itemName));
    if (item.isEmpty() || item.get() == Items.AIR) {
      log.error(
          "{} Invalid item name {} in config file {}", LOG_PREFIX, itemName, CONFIG_FILE_NAME);
      return;
    }

    try {
      MobFarmType mobFarmType = MobFarmType.valueOf(mobFarmName.toUpperCase(Locale.ROOT));
      addBonusDropEntry(
          mobFarmType, tierLevel, entityType, chance, new ItemStack(item.get(), amount));
    } catch (IllegalArgumentException e) {
      log.error(
          "{} Invalid mob farm name {} in config file {}",
          LOG_PREFIX,
          mobFarmName,
          CONFIG_FILE_NAME);
    }
  }

  public static void addBonusDropEntry(
      MobFarmType mobFarmType, int tierLevel, String entityType, int chance, ItemStack itemStack) {
    String mobFarmKey = getMobFarmKey(mobFarmType.getId(), tierLevel, entityType);

    // Check if item stack amount is valid
    if (itemStack.isEmpty()) {
      log.error(
          "{} Invalid item stack {} in config file {}", LOG_PREFIX, itemStack, CONFIG_FILE_NAME);
      return;
    }

    // Check if entity type is valid
    if (BuiltInRegistries.ENTITY_TYPE
        .getOptional(ResourceLocation.tryParse(entityType))
        .isEmpty()) {
      log.error(
          "{} Invalid entity type {} in config file {}", LOG_PREFIX, entityType, CONFIG_FILE_NAME);
      return;
    }

    log.info(
        "{} Add {} with a chance of 1 of {} for {}.", LOG_PREFIX, mobFarmKey, chance, itemStack);
    mobFarmBonusMap.computeIfAbsent(mobFarmKey, k -> new HashMap<>()).put(chance, itemStack);
  }

  public static ItemStack getBonusDropEntry(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    return getBonusDropEntry(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static ItemStack getBonusDropEntry(String mobFarmName, int tierLevel, String entityType) {
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return ItemStack.EMPTY;
    }
    return mobFarmBonusMap
        .get(getMobFarmKey(mobFarmName, tierLevel, entityType))
        .entrySet()
        .stream()
        .map(Map.Entry::getValue)
        .findFirst()
        .orElse(ItemStack.EMPTY);
  }

  public static ItemStack getBonusDrop(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    return getBonusDrop(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static ItemStack getBonusDrop(String mobFarmName, int tierLevel, String entityType) {
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return ItemStack.EMPTY;
    }
    return mobFarmBonusMap
        .get(getMobFarmKey(mobFarmName, tierLevel, entityType))
        .entrySet()
        .stream()
        .filter(entry -> random.nextInt(entry.getKey()) == 0)
        .map(Map.Entry::getValue)
        .findFirst()
        .orElse(ItemStack.EMPTY);
  }

  public static boolean hasBonusDrop(String mobFarmName, int tierLevel, EntityType<?> entityType) {
    return hasBonusDrop(
        mobFarmName, tierLevel, String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static boolean hasBonusDrop(String mobFarmName, int tierLevel, String entityType) {
    return mobFarmBonusMap.containsKey(getMobFarmKey(mobFarmName, tierLevel, entityType));
  }

  private static String[] parseKey(String key) {
    String[] keyParts = key.split("::");
    if (keyParts.length != 3) {
      log.error("Invalid key format in config file: {}", key);
      return null;
    }
    return keyParts;
  }

  private static String[] parseValue(String value) {
    String[] valueParts = value.split("::");
    if (valueParts.length != 3) {
      log.error("Invalid value format in config file: {}", value);
      return null;
    }
    return valueParts;
  }
}
