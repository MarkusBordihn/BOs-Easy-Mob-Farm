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
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

public class MobCaptureCardModelsConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_capture_card_models.cfg";
  public static final String CONFIG_FILE_HEADER =
      """
 Mob Capture Card Models Configuration

 This configuration file allows you to define the models for the Mob Capture Cards.

 The format is as follows:
 - <entity_name> = <resource_location>
 - <entity_name>:<variant> = <resource_location>
 - <entity_name>:<color> = <resource_location>
 - <entity_name>:<variant>:<color> = <resource_location>

 Example:
 - minecraft:zombie = easy_mob_farm:item/mob_capture_card/minecraft/zombie
 - minecraft:zombie:blue = easy_mob_farm:item/mob_capture_card/minecraft/zombie_blue
 - minecraft:zombie:old:blue = easy_mob_farm:item/mob_capture_card/minecraft/zombie_old_blue

 The mods tries to load and bake the models for the Mob Capture Cards based on the configuration.
 However, if the model is not found, the default model will be used.

""";

  private static final StringBuilder KEY_BUILDER = new StringBuilder();
  private static final String KEY_SEPARATOR = "::";
  private static final HashMap<String, ResourceLocation> mobCaptureCardResourceLocations =
      new HashMap<>();
  private static final HashMap<String, ModelResourceLocation> mobCaptureCardModelResourceLocations =
      new HashMap<>();
  private static final String MOB_CAPTURE_CARD_PREFIX =
      Constants.MOD_ID + ":item/mob_capture_card/minecraft/";

  private static final HashMap<String, String> defaultModels = new HashMap<>();

  static {
    // Minecraft default models
    defaultModels.put("minecraft:cat::all_black", MOB_CAPTURE_CARD_PREFIX + "cat_black");
    defaultModels.put("minecraft:cat::white", MOB_CAPTURE_CARD_PREFIX + "cat_white");
    defaultModels.put("minecraft:chicken", MOB_CAPTURE_CARD_PREFIX + "chicken");
    defaultModels.put("minecraft:cod", MOB_CAPTURE_CARD_PREFIX + "cod");
    defaultModels.put("minecraft:cow", MOB_CAPTURE_CARD_PREFIX + "cow");
    defaultModels.put("minecraft:drowned", MOB_CAPTURE_CARD_PREFIX + "drowned");
    defaultModels.put("minecraft:iron_golem", MOB_CAPTURE_CARD_PREFIX + "iron_golem");
    defaultModels.put("minecraft:pig", MOB_CAPTURE_CARD_PREFIX + "pig");
    defaultModels.put("minecraft:sheep", MOB_CAPTURE_CARD_PREFIX + "sheep");
    defaultModels.put("minecraft:sheep::black", MOB_CAPTURE_CARD_PREFIX + "sheep_black");
    defaultModels.put("minecraft:sheep::blue", MOB_CAPTURE_CARD_PREFIX + "sheep_blue");
    defaultModels.put("minecraft:sheep::brown", MOB_CAPTURE_CARD_PREFIX + "sheep_brown");
    defaultModels.put("minecraft:sheep::gray", MOB_CAPTURE_CARD_PREFIX + "sheep_gray");
    defaultModels.put("minecraft:sheep::light_gray", MOB_CAPTURE_CARD_PREFIX + "sheep_light_gray");
    defaultModels.put("minecraft:sheep::pink", MOB_CAPTURE_CARD_PREFIX + "sheep_pink");
    defaultModels.put("minecraft:sheep::red", MOB_CAPTURE_CARD_PREFIX + "sheep_red");
    defaultModels.put("minecraft:sheep::white", MOB_CAPTURE_CARD_PREFIX + "sheep_white");
    defaultModels.put("minecraft:skeleton", MOB_CAPTURE_CARD_PREFIX + "skeleton");
    defaultModels.put("minecraft:slime", MOB_CAPTURE_CARD_PREFIX + "slime");
    defaultModels.put("minecraft:villager", MOB_CAPTURE_CARD_PREFIX + "villager");
    defaultModels.put("minecraft:zombie", MOB_CAPTURE_CARD_PREFIX + "zombie");
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
    for (Map.Entry<String, String> entry : defaultModels.entrySet()) {
      String propertyKey = entry.getKey();
      String propertyValue = entry.getValue();
      if (!properties.containsKey(propertyKey)) {
        properties.setProperty(propertyKey, propertyValue);
      }
    }

    // Parse config file
    Set<String> propertyKeys = properties.stringPropertyNames();
    for (String propertyKey : propertyKeys) {
      String propertyValue = parseConfigValue(properties, propertyKey, "");
      if (propertyValue.isEmpty()) {
        continue;
      }
      ResourceLocation resourceLocation = ResourceLocation.parse(propertyValue);
      mobCaptureCardResourceLocations.put(propertyKey, resourceLocation);

      // Add model resource location
      mobCaptureCardModelResourceLocations.put(
          propertyKey,
          new ModelResourceLocation(
              ResourceLocation.fromNamespaceAndPath(
                  resourceLocation.getNamespace(), resourceLocation.getPath()),
              "standalone"));
    }

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static ResourceLocation getResourceLocation(final String entityName) {
    return mobCaptureCardResourceLocations.get(entityName);
  }

  public static ResourceLocation getResourceLocation(
      String entityName, String variant, DyeColor color) {
    return mobCaptureCardResourceLocations.getOrDefault(
        getEntityKey(entityName, variant, color), mobCaptureCardResourceLocations.get(entityName));
  }

  public static ModelResourceLocation getModelResourceLocation(final String entityName) {
    return mobCaptureCardModelResourceLocations.get(entityName);
  }

  public static ModelResourceLocation getModelResourceLocation(
      String entityName, String variant, DyeColor color) {
    return mobCaptureCardModelResourceLocations.getOrDefault(
        getEntityKey(entityName, variant, color),
        mobCaptureCardModelResourceLocations.get(entityName));
  }

  public static Set<String> getMobCaptureCardModels() {
    return mobCaptureCardResourceLocations.keySet();
  }

  public static String getEntityKey(
      final String entityName, final String variant, final DyeColor color) {
    KEY_BUILDER.setLength(0);
    KEY_BUILDER.append(entityName.trim().toLowerCase());

    if (color == null && (variant == null || variant.isEmpty())) {
      return KEY_BUILDER.toString();
    }

    if (variant != null && !variant.isEmpty()) {
      KEY_BUILDER.append(KEY_SEPARATOR).append(variant.trim().toLowerCase());
    }

    if (color != null) {
      KEY_BUILDER.append(KEY_SEPARATOR).append(color.getName().toLowerCase());
    }

    return KEY_BUILDER.toString();
  }

  public static Object[] extractEntityKeyData(final String entityKey) {
    String[] parts = entityKey.split(KEY_SEPARATOR);
    String entityName = parts[0];

    // Check entity name
    if (entityName.isEmpty()) {
      return new Object[] {null, null, null};
    }

    // Check for variant and color
    String variant = null;
    DyeColor color = null;
    if (parts.length == 3) {
      variant = parts[1];
      try {
        color = DyeColor.valueOf(parts[2].toUpperCase());
      } catch (IllegalArgumentException e) {
        // No color found
      }
    } else if (parts.length == 2) {
      if (entityName.equals("minecraft:cat")) {
        variant = parts[1];
      } else {
        try {
          color = DyeColor.valueOf(parts[1].toUpperCase());
        } catch (IllegalArgumentException e) {
          variant = parts[1];
        }
      }
    }

    return new Object[] {entityName, variant, color};
  }
}
