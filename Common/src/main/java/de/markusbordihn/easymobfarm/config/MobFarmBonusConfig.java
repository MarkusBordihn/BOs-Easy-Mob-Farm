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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.StringJoiner;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
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

 Multiple bonus items for the same mob (using list syntax):
   ocean_farm::1::minecraft:cod = [minecraft:cod::1::10, minecraft:bone_meal::1::25]

 Important Notes:
 ---------------
 - Lower chance numbers = higher drop probability (1 = always, 100 = 1% chance)
 - To disable a bonus drop, set the amount to 0
 - Each mob farm type targets specific biome-appropriate mobs
 - Higher tier farms have better default bonus chances

""";
  public static final String LOG_PREFIX = "[MobFarmBonusConfig]";

  private static final Random random = new Random();
  private static final HashMap<String, List<BonusDrop>> mobFarmBonusMap = new HashMap<>();
  private static final HashMap<String, List<DefaultDrop>> defaultMobFarmBonusMap = new HashMap<>();

  static {
    // Animal Plains Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:cow",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:cow",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:cow",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:cow",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:sheep",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.WOOL.white(), 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:sheep",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.WOOL.white(), 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:sheep",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.WOOL.white(), 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:sheep",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.WOOL.white(), 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:chicken",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:chicken",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:chicken",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:chicken",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.EGG, 1))));

    // Bee Hive Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::0::minecraft:bee",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::1::minecraft:bee",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::2::minecraft:bee",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::3::minecraft:bee",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.HONEYCOMB, 1))));

    // Desert Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::0::minecraft:husk",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::1::minecraft:husk",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::2::minecraft:husk",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::3::minecraft:husk",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::0::minecraft:rabbit",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::1::minecraft:rabbit",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::2::minecraft:rabbit",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::3::minecraft:rabbit",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.RABBIT_HIDE, 1))));

    // Iron Golem Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::0::minecraft:iron_golem",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::1::minecraft:iron_golem",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::2::minecraft:iron_golem",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::3::minecraft:iron_golem",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.IRON_INGOT, 1))));

    // Jungle Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::0::minecraft:parrot",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::1::minecraft:parrot",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::2::minecraft:parrot",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::3::minecraft:parrot",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::0::minecraft:panda",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::1::minecraft:panda",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::2::minecraft:panda",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::3::minecraft:panda",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.BAMBOO, 1))));

    // Monster Plains Cave Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::0::minecraft:zombie",
        List.of(
            new DefaultDrop(20, new ItemStackTemplate(Items.ROTTEN_FLESH, 1)),
            new DefaultDrop(40, new ItemStackTemplate(Items.IRON_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::1::minecraft:zombie",
        List.of(
            new DefaultDrop(15, new ItemStackTemplate(Items.ROTTEN_FLESH, 1)),
            new DefaultDrop(25, new ItemStackTemplate(Items.IRON_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::2::minecraft:zombie",
        List.of(
            new DefaultDrop(10, new ItemStackTemplate(Items.ROTTEN_FLESH, 1)),
            new DefaultDrop(15, new ItemStackTemplate(Items.IRON_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::3::minecraft:zombie",
        List.of(
            new DefaultDrop(5, new ItemStackTemplate(Items.ROTTEN_FLESH, 1)),
            new DefaultDrop(10, new ItemStackTemplate(Items.IRON_NUGGET, 1))));

    // Nether Fortress Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::0::minecraft:blaze",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::1::minecraft:blaze",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::2::minecraft:blaze",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::3::minecraft:blaze",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::0::minecraft:magma_cube",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::1::minecraft:magma_cube",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::2::minecraft:magma_cube",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::3::minecraft:magma_cube",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.MAGMA_CREAM, 1))));

    // Ocean Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::0::minecraft:cod",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::1::minecraft:cod",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::2::minecraft:cod",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::3::minecraft:cod",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::0::minecraft:squid",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::1::minecraft:squid",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::2::minecraft:squid",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::3::minecraft:squid",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.INK_SAC, 1))));

    // Swamp Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:frog",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:frog",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:frog",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:frog",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:slime",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:slime",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:slime",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:slime",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:witch",
        List.of(new DefaultDrop(20, new ItemStackTemplate(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:witch",
        List.of(new DefaultDrop(15, new ItemStackTemplate(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:witch",
        List.of(new DefaultDrop(10, new ItemStackTemplate(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:witch",
        List.of(new DefaultDrop(5, new ItemStackTemplate(Items.REDSTONE, 1))));
  }

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    mobFarmBonusMap.clear();

    // Add default values to config file, handling the new List syntax
    defaultMobFarmBonusMap.forEach(
        (mobFarmName, bonusList) -> {
          if (!properties.containsKey(mobFarmName)) {
            if (bonusList.size() == 1) {
              DefaultDrop drop = bonusList.getFirst();
              String itemName =
                  BuiltInRegistries.ITEM.getKey(drop.template().item().value()).toString();
              String value = itemName + "::" + drop.template().count() + "::" + drop.chance();
              properties.setProperty(mobFarmName, value);
            } else {
              StringJoiner joiner = new StringJoiner(", ", "[", "]");
              for (DefaultDrop drop : bonusList) {
                String itemName =
                    BuiltInRegistries.ITEM.getKey(drop.template().item().value()).toString();
                joiner.add(itemName + "::" + drop.template().count() + "::" + drop.chance());
              }
              properties.setProperty(mobFarmName, joiner.toString());
            }
          }
        });

    // Parse config file supporting both plain string and list [item1, item2] syntax
    properties.forEach(
        (key, value) -> {
          String[] keyParts = parseKey((String) key);
          if (keyParts == null) return;

          String valStr = ((String) value).trim();

          if (valStr.startsWith("[") && valStr.endsWith("]")) {
            valStr = valStr.substring(1, valStr.length() - 1).trim();
            for (String itemStr : valStr.split("\\s*,\\s*")) {
              parseAndAddDrop(keyParts, itemStr);
            }
          } else {
            parseAndAddDrop(keyParts, valStr);
          }
        });

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);

    log.info(
        "{} Loaded {} with {} bonus drop entries.",
        LOG_PREFIX,
        CONFIG_FILE_NAME,
        mobFarmBonusMap.values().stream().mapToInt(List::size).sum());
  }

  public static String getMobFarmKey(String mobFarmName, int tierLevel, String entityType) {
    return mobFarmName + "::" + tierLevel + "::" + entityType;
  }

  private static void parseAndAddDrop(String[] keyParts, String valueStr) {
    String[] valueParts = parseValue(valueStr);
    if (valueParts == null) {
      return;
    }

    String itemName = valueParts[0];
    if (itemName.isEmpty()) {
      log.error("{} Missing item name in config entry: {}", LOG_PREFIX, valueStr);
      return;
    }

    try {
      addBonusDropEntry(
          keyParts[0],
          Integer.parseInt(keyParts[1]),
          keyParts[2],
          Integer.parseInt(valueParts[2]),
          itemName,
          Integer.parseInt(valueParts[1]));
    } catch (NumberFormatException e) {
      log.error(
          "{} Invalid number format in config file {}: key={}, value={}",
          LOG_PREFIX,
          CONFIG_FILE_NAME,
          String.join("::", keyParts),
          valueStr);
    }
  }

  public static void addBonusDropEntry(
      String mobFarmName,
      int tierLevel,
      String entityType,
      int chance,
      String itemName,
      int amount) {

    Optional<Item> item = BuiltInRegistries.ITEM.getOptional(Identifier.tryParse(itemName));
    if (item.isEmpty() || item.get() == Items.AIR) {
      log.error(
          "{} Invalid item name {} in config file {}", LOG_PREFIX, itemName, CONFIG_FILE_NAME);
      return;
    }

    try {
      MobFarmType mobFarmType = MobFarmType.valueOf(mobFarmName.toUpperCase(Locale.ROOT));
      addBonusDropEntry(
          mobFarmType, tierLevel, entityType, chance, new ItemStackTemplate(item.get(), amount));
    } catch (IllegalArgumentException e) {
      log.error(
          "{} Invalid mob farm name {} in config file {}",
          LOG_PREFIX,
          mobFarmName,
          CONFIG_FILE_NAME);
    }
  }

  public static void addBonusDropEntry(
      final MobFarmType mobFarmType,
      int tierLevel,
      final String entityType,
      int chance,
      final ItemStackTemplate itemStack) {

    if (itemStack == null || itemStack.count() < 1) {
      log.error("{} Invalid item stack in config file {}", LOG_PREFIX, CONFIG_FILE_NAME);
      return;
    }

    if (BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.tryParse(entityType)).isEmpty()) {
      log.error(
          "{} Invalid entity type {} in config file {}", LOG_PREFIX, entityType, CONFIG_FILE_NAME);
      return;
    }

    if (tierLevel < 0 || tierLevel > 3) {
      log.warn(
          "{} Tier level {} is outside valid range (0-3) for {} in config file {}",
          LOG_PREFIX,
          tierLevel,
          entityType,
          CONFIG_FILE_NAME);
      tierLevel = 0;
    }

    if (chance < 1) {
      log.warn(
          "{} Invalid chance value {} (must be >= 1) for {} in config file {}, using default of 5",
          LOG_PREFIX,
          chance,
          entityType,
          CONFIG_FILE_NAME);
      chance = 5;
    }

    String mobFarmKey = getMobFarmKey(mobFarmType.getId(), tierLevel, entityType);
    log.debug(
        "{} Add {} with a chance of 1 of {} for {}.", LOG_PREFIX, mobFarmKey, chance, itemStack);
    mobFarmBonusMap
        .computeIfAbsent(mobFarmKey, k -> new ArrayList<>())
        .add(new BonusDrop(chance, itemStack));
  }

  public static List<ItemStack> getBonusDrop(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    return getBonusDrop(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static List<ItemStack> getBonusDrop(String mobFarmName, int tierLevel, String entityType) {
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return List.of();
    }
    List<ItemStack> drops = new ArrayList<>();
    for (BonusDrop drop : mobFarmBonusMap.get(getMobFarmKey(mobFarmName, tierLevel, entityType))) {
      if (random.nextInt(drop.chance()) == 0) {
        drops.add(drop.itemStack().create().copy());
      }
    }
    return drops;
  }

  public static List<ItemStack> getBonusDropEntries(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    return getBonusDropEntries(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static List<ItemStack> getBonusDropEntries(
      String mobFarmName, int tierLevel, String entityType) {
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return List.of();
    }
    return mobFarmBonusMap.get(getMobFarmKey(mobFarmName, tierLevel, entityType)).stream()
        .map(drop -> drop.itemStack().create().copy())
        .toList();
  }

  public static List<BonusDrop> getConfiguredBonusDrops(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    return getConfiguredBonusDrops(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static List<BonusDrop> getConfiguredBonusDrops(
      String mobFarmName, int tierLevel, String entityType) {
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return List.of();
    }
    return List.copyOf(mobFarmBonusMap.get(getMobFarmKey(mobFarmName, tierLevel, entityType)));
  }

  public static boolean hasBonusDrop(String mobFarmName, int tierLevel, EntityType<?> entityType) {
    return hasBonusDrop(
        mobFarmName, tierLevel, String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static boolean hasBonusDrop(String mobFarmName, int tierLevel, String entityType) {
    return mobFarmBonusMap.containsKey(getMobFarmKey(mobFarmName, tierLevel, entityType));
  }

  private static String[] parseKey(String key) {
    String[] keyParts = key.split("\\s*::\\s*");
    if (keyParts.length != 3) {
      log.error("Invalid key format in config file: {}", key);
      return null;
    }
    return keyParts;
  }

  private static String[] parseValue(String value) {
    String[] valueParts = value.split("\\s*::\\s*");
    if (valueParts.length != 3) {
      log.error("Invalid value format in config file: {}", value);
      return null;
    }
    return valueParts;
  }

  public record BonusDrop(int chance, ItemStackTemplate itemStack) {}

  private record DefaultDrop(int chance, ItemStackTemplate template) {}
}
