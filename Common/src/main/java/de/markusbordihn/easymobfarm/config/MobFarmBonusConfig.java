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
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MobFarmBonusConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_farm_bonus.cfg";
  public static final String CONFIG_FILE_HEADER =
      """
 Mob Farm Bonus Configuration

 This configuration file allows you to define the bonus drops for the Mob Farms.

The format is as follows:
- <mob_farm_name>::<tier_level>::<entity_type> = <item_name>::<amount>::<chance 1 of x>

Example:
- bee_hive_farm::0::minecraft:bee = minecraft:honeycomb::1::20
- bee_hive_farm::1::minecraft:bee = minecraft:honeycomb::1::15
- bee_hive_farm::2::minecraft:bee = minecraft:honeycomb::1::10
- bee_hive_farm::3::minecraft:bee = minecraft:honeycomb::1::5

To disable a bonus drop for a default definition, set the amount to 0.

""";

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

          // We will ignore entries with amount <= 0
          if (amount <= 0) {
            log.error("Invalid amount in config file: {}", value);
            return;
          }

          BuiltInRegistries.ITEM
              .getOptional(new ResourceLocation(itemName))
              .ifPresent(
                  item -> {
                    ItemStack itemStack = new ItemStack(item, amount);
                    if (itemStack.isEmpty()) {
                      log.error("Invalid item in config file ({}): {}", key, value);
                      return;
                    }
                    mobFarmBonusMap
                        .computeIfAbsent(
                            mobFarmName + "::" + tierLevel + "::" + entityType,
                            k -> new HashMap<>())
                        .put(chance, itemStack);
                  });
        });

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
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
        .get(mobFarmName + "::" + tierLevel + "::" + entityType)
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
        .get(mobFarmName + "::" + tierLevel + "::" + entityType)
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
    return mobFarmBonusMap.containsKey(mobFarmName + "::" + tierLevel + "::" + entityType);
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
