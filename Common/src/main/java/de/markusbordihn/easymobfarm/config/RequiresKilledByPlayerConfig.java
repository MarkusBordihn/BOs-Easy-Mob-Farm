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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import net.minecraft.world.entity.EntityType;

public class RequiresKilledByPlayerConfig extends Config {

  public static final String CONFIG_FILE_NAME = "requires_killed_by_player.cfg";
  public static final String CONFIG_FILE_HEADER =
      """
 Requires Killed By Player Configuration

 This configuration file lists the known entities which requires "killed_by_player" for looting.

  The format is as follows:
  - <entity_name> : true

""";
  public static final Set<EntityType<?>> requiresKilledByPlayerEntities = new HashSet<>();
  private static final Set<String> knownRequiresKilledByPlayerEntities =
      new HashSet<>(
          List.of(
              "minecraft:blaze",
              "minecraft:cave_spider",
              "minecraft:drowned",
              "minecraft:elder_guardian",
              "minecraft:evoker",
              "minecraft:guardian",
              "minecraft:husk",
              "minecraft:phantom",
              "minecraft:rabbit",
              "minecraft:spider",
              "minecraft:stray",
              "minecraft:vindicator",
              "minecraft:wither_skeleton",
              "minecraft:zombie",
              "minecraft:zombie_villager",
              "minecraft:zombified_piglin"));

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Adding default values to config file, if not present.
    for (String entityType : knownRequiresKilledByPlayerEntities) {
      parseConfigValue(properties, entityType, true);
    }

    // Parse the rest of the configuration file.
    for (String entityTypeName : properties.stringPropertyNames()) {
      // Remove invalid entity type names from the configuration file.
      if (entityTypeName == null || !entityTypeName.contains(":")) {
        log.error("Remove invalid entity type name {} from {}.", entityTypeName, CONFIG_FILE_NAME);
        properties.remove(entityTypeName);
        continue;
      }

      // Check if entity type is known, resolve it and add it to the list, if needed.
      Optional<EntityType<?>> entityType = EntityType.byString(entityTypeName);
      if (entityType.isPresent() && !requiresKilledByPlayerEntities.contains(entityType.get())) {
        if (parseConfigValue(properties, entityTypeName, false)) {
          requiresKilledByPlayerEntities.add(entityType.get());
        } else {
          requiresKilledByPlayerEntities.remove(entityType.get());
        }
      }
    }

    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static boolean requiresKilledByPlayer(EntityType<?> entityType) {
    return requiresKilledByPlayerEntities.contains(entityType);
  }
}
