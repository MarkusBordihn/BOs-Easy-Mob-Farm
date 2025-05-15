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

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Rarity;

public class MobCaptureCardRarityConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_capture_card_rarity.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Mob Capture Card Rarity Configuration

 This configuration file allows you to define the rarity for the Mob Capture Cards.

 The format is as follows:
 - <entity_name> = Rarity

 Example:
 - minecraft:zombie = COMMON

 Supported rarities are:
  - COMMON
  - UNCOMMON
  - RARE
  - EPIC

""";

  private static final HashMap<String, Rarity> mobCaptureCardRarity = new HashMap<>();
  private static final HashMap<String, Rarity> defaultRarity = new HashMap<>();

  static {
    // COMMON mobs
    defaultRarity.put("minecraft:bat", Rarity.COMMON);
    defaultRarity.put("minecraft:cat", Rarity.COMMON);
    defaultRarity.put("minecraft:chicken", Rarity.COMMON);
    defaultRarity.put("minecraft:cow", Rarity.COMMON);
    defaultRarity.put("minecraft:creeper", Rarity.COMMON);
    defaultRarity.put("minecraft:drowned", Rarity.COMMON);
    defaultRarity.put("minecraft:horse", Rarity.COMMON);
    defaultRarity.put("minecraft:pig", Rarity.COMMON);
    defaultRarity.put("minecraft:pillager", Rarity.COMMON);
    defaultRarity.put("minecraft:sheep", Rarity.COMMON);
    defaultRarity.put("minecraft:skeleton", Rarity.COMMON);
    defaultRarity.put("minecraft:spider", Rarity.COMMON);
    defaultRarity.put("minecraft:squid", Rarity.COMMON);
    defaultRarity.put("minecraft:villager", Rarity.COMMON);
    defaultRarity.put("minecraft:witch", Rarity.COMMON);
    defaultRarity.put("minecraft:wolf", Rarity.COMMON);
    defaultRarity.put("minecraft:zombie", Rarity.COMMON);

    // UNCOMMON mobs
    defaultRarity.put("minecraft:cave_spider", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:enderman", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:evoker", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:fox", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:husk", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:llama", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:panda", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:polar_bear", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:slime", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:stray", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:zombified_piglin", Rarity.UNCOMMON);
    defaultRarity.put("minecraft:zombified_villager", Rarity.UNCOMMON);

    // RARE mobs
    defaultRarity.put("minecraft:blaze", Rarity.RARE);
    defaultRarity.put("minecraft:ghast", Rarity.RARE);
    defaultRarity.put("minecraft:guardian", Rarity.RARE);
    defaultRarity.put("minecraft:magma_cube", Rarity.RARE);
    defaultRarity.put("minecraft:phantom", Rarity.RARE);
    defaultRarity.put("minecraft:ravager", Rarity.RARE);
    defaultRarity.put("minecraft:shulker", Rarity.RARE);
    defaultRarity.put("minecraft:strider", Rarity.RARE);
    defaultRarity.put("minecraft:vindicator", Rarity.RARE);

    // EPIC mobs
    defaultRarity.put("minecraft:elder_guardian", Rarity.EPIC);
    defaultRarity.put("minecraft:ender_dragon", Rarity.EPIC);
    defaultRarity.put("minecraft:illusioner", Rarity.EPIC);
    defaultRarity.put("minecraft:piglin_brute", Rarity.EPIC);
    defaultRarity.put("minecraft:warden", Rarity.EPIC);
    defaultRarity.put("minecraft:wither", Rarity.EPIC);
    defaultRarity.put("minecraft:wither_skeleton", Rarity.EPIC);
  }

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Adding default values to config file, if not present.
    for (Map.Entry<String, Rarity> entry : defaultRarity.entrySet()) {
      String propertyKey = entry.getKey();
      Rarity propertyValue = entry.getValue();
      if (!properties.containsKey(propertyKey)) {
        properties.setProperty(propertyKey, propertyValue.name());
      }
    }

    // Parse config file
    Set<String> propertyKeys = properties.stringPropertyNames();
    for (String propertyKey : propertyKeys) {
      Rarity propertyValue = parseConfigValue(properties, propertyKey, Rarity.COMMON);
      mobCaptureCardRarity.put(propertyKey.trim().toLowerCase(Locale.ROOT), propertyValue);
    }

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static boolean hasRarity(final EntityType<?> entityType) {
    return hasRarity(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
  }

  public static boolean hasRarity(final String entityName) {
    return mobCaptureCardRarity.containsKey(entityName.trim().toLowerCase(Locale.ROOT));
  }

  public static Rarity getRarity(final EntityType<?> entityType) {
    return getRarity(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString());
  }

  public static Rarity getRarity(final String entityName) {
    return mobCaptureCardRarity.getOrDefault(
        entityName.trim().toLowerCase(Locale.ROOT), Rarity.COMMON);
  }
}
