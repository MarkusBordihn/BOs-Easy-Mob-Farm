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
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import net.minecraft.world.entity.EntityType;

public class EntityScalingConfig extends Config {

  public static final String CONFIG_FILE_NAME = "entity_scaling.cfg";
  public static final String CONFIG_FILE_HEADER =
      """
 Entity Scaling Configuration

 This configuration overwrites the automatic calculated scaling factors for entity types.
 The scaling factors are used to adjust the size of the entity to match the size of mob farm.
 This is useful for entities with extra large wings or other body parts.

  The format is as follows:
  - <entity_name> : <scaling_factor>

""";
  private static final Map<EntityType<?>, Float> entityScalingFactor = new HashMap<>();
  private static final Map<String, Float> knownEntityScalingFactor = new HashMap<>();

  static {
    knownEntityScalingFactor.put("iceandfire:fire_dragon", 3.5f);
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
    for (Map.Entry<String, Float> entry : knownEntityScalingFactor.entrySet()) {
      parseConfigValue(properties, entry.getKey(), entry.getValue());
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
      if (entityType.isPresent() && !entityScalingFactor.containsKey(entityType)) {
        float scalingFactor = Float.parseFloat(properties.getProperty(entityTypeName));
        log.info("Adding scaling factor {} for {}.", scalingFactor, entityTypeName);
        entityScalingFactor.put(entityType.get(), scalingFactor);
      } else {
        log.error("Remove unknown entity type {} from {}.", entityTypeName, CONFIG_FILE_NAME);
        properties.remove(entityTypeName);
      }
    }

    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static float getEntityScalingFactor(EntityType<?> entityType) {
    return entityScalingFactor.getOrDefault(entityType, 0f);
  }
}
