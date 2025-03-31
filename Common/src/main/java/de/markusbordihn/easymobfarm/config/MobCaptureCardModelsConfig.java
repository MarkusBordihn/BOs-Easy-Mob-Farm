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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.capture.MobColor;
import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

public class MobCaptureCardModelsConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_capture_card_models.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Mob Capture Card Models Configuration (deprecated with 1.21.4)

 The format is as follows:
 - <entity_name> = <resource_location>
 - <entity_name>::<variant> = <resource_location>
 - <entity_name>::<color> = <resource_location>
 - <entity_name>::<variant>::<color> = <resource_location>

 Example:
 - minecraft:zombie = easy_mob_farm:item/mob_capture_card/minecraft/zombie
 - minecraft:zombie::blue = easy_mob_farm:item/mob_capture_card/minecraft/zombie_blue
 - minecraft:zombie::old::blue = easy_mob_farm:item/mob_capture_card/minecraft/zombie_old_blue

""";

  private static final StringBuilder KEY_BUILDER = new StringBuilder();
  private static final String KEY_SEPARATOR = "::";
  private static final HashSet<String> mobCaptureCardModels = new HashSet<>();
  private static final String MOB_CAPTURE_CARD_PREFIX =
      Constants.MOD_ID + ":item/mob_capture_card/minecraft/";

  private static final HashSet<String> defaultModels = new HashSet<>();

  static {
    // Minecraft default models
    defaultModels.add("minecraft:bee");
    defaultModels.add("minecraft:blaze");
    defaultModels.add("minecraft:cat::all_black");
    defaultModels.add("minecraft:cat::black");
    defaultModels.add("minecraft:cat::british_shorthair");
    defaultModels.add("minecraft:cat::calico");
    defaultModels.add("minecraft:cat::jellie");
    defaultModels.add("minecraft:cat::persian");
    defaultModels.add("minecraft:cat::ragdoll");
    defaultModels.add("minecraft:cat::red");
    defaultModels.add("minecraft:cat::siamese");
    defaultModels.add("minecraft:cat::tabby");
    defaultModels.add("minecraft:cat::white");
    defaultModels.add("minecraft:chicken");
    defaultModels.add("minecraft:cod");
    defaultModels.add("minecraft:cow");
    defaultModels.add("minecraft:creeper");
    defaultModels.add("minecraft:drowned");
    defaultModels.add("minecraft:enderman");
    defaultModels.add("minecraft:evoker");
    defaultModels.add("minecraft:frog");
    defaultModels.add("minecraft:frog::cold");
    defaultModels.add("minecraft:frog::temperate");
    defaultModels.add("minecraft:frog::warm");
    defaultModels.add("minecraft:glow_squid");
    defaultModels.add("minecraft:guardian");
    defaultModels.add("minecraft:husk");
    defaultModels.add("minecraft:iron_golem");
    defaultModels.add("minecraft:magma_cube");
    defaultModels.add("minecraft:phantom");
    defaultModels.add("minecraft:pig");
    defaultModels.add("minecraft:piglin");
    defaultModels.add("minecraft:piglin_brute");
    defaultModels.add("minecraft:pillager");
    defaultModels.add("minecraft:rabbit");
    defaultModels.add("minecraft:sheep");
    defaultModels.add("minecraft:sheep::black");
    defaultModels.add("minecraft:sheep::blue");
    defaultModels.add("minecraft:sheep::brown");
    defaultModels.add("minecraft:sheep::gray");
    defaultModels.add("minecraft:sheep::light_gray");
    defaultModels.add("minecraft:sheep::pink");
    defaultModels.add("minecraft:sheep::red");
    defaultModels.add("minecraft:sheep::white");
    defaultModels.add("minecraft:skeleton");
    defaultModels.add("minecraft:slime");
    defaultModels.add("minecraft:spider");
    defaultModels.add("minecraft:squid");
    defaultModels.add("minecraft:turtle");
    defaultModels.add("minecraft:villager");
    defaultModels.add("minecraft:warden");
    defaultModels.add("minecraft:witch");
    defaultModels.add("minecraft:wither");
    defaultModels.add("minecraft:wither_skeleton");
    defaultModels.add("minecraft:zombie");
    defaultModels.add("minecraft:zombie_villager");
    defaultModels.add("minecraft:zombified_piglin");
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
    for (String propertyKey : defaultModels) {
      String propertyValue = "";
      if (!properties.containsKey(propertyKey)) {
        properties.setProperty(propertyKey, propertyValue);
      }
    }

    // Parse config file
    Set<String> propertyKeys = properties.stringPropertyNames();
    for (String propertyKey : propertyKeys) {
      parseConfigValue(properties, propertyKey, "");
      if (propertyKey.isEmpty()) {
        continue;
      }

      // Add model resource location
      mobCaptureCardModels.add(propertyKey);
    }

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static Set<String> getMobCaptureCardModels() {
    return mobCaptureCardModels;
  }

  public static Object[] extractEntityKeyData(final String entityKey) {
    String[] parts = entityKey.split(KEY_SEPARATOR);
    String entityName = parts[0];

    // Check entity name
    if (entityName.isEmpty()) {
      log.error("Unknown entity type {} for mob capture card!", entityName);
      return new Object[] {"Unknown", "", MobColor.NONE};
    }

    // Check for variant and color
    String variant = "";
    MobColor color = MobColor.NONE;
    if (parts.length == 3) {
      variant = parts[1];
      try {
        color = MobColor.valueOf(parts[2].toUpperCase(Locale.ROOT));
      } catch (IllegalArgumentException e) {
        // No color found
      }
    } else if (parts.length == 2) {
      if (entityName.equals("minecraft:cat")) {
        variant = parts[1];
      } else {
        // Check if we have a valid mob color, otherwise use it as variant.
        try {
          MobColor mobColor = MobColor.valueOf(parts[1].toUpperCase(Locale.ROOT));
          if (mobColor != MobColor.NONE) {
            color = mobColor;
          } else {
            variant = parts[1];
          }
        } catch (IllegalArgumentException e) {
          variant = parts[1];
        }
      }
    }

    return new Object[] {entityName, variant, color};
  }
}
