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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

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
  private static final HashMap<String, ModelResourceLocation> mobCaptureCardModels =
      new HashMap<>();
  private static final String MOB_CAPTURE_CARD_PREFIX =
      Constants.MOD_ID + ":item/mob_capture_card/minecraft/";

  private static final HashMap<String, String> defaultModels = new HashMap<>();

  static {
    // Minecraft default models
    defaultModels.put("minecraft:bee", MOB_CAPTURE_CARD_PREFIX + "bee");
    defaultModels.put("minecraft:blaze", MOB_CAPTURE_CARD_PREFIX + "blaze");
    defaultModels.put("minecraft:cat::all_black", MOB_CAPTURE_CARD_PREFIX + "cat_all_black");
    defaultModels.put("minecraft:cat::black", MOB_CAPTURE_CARD_PREFIX + "cat_black");
    defaultModels.put(
        "minecraft:cat::british_shorthair", MOB_CAPTURE_CARD_PREFIX + "cat_british_shorthair");
    defaultModels.put("minecraft:cat::calico", MOB_CAPTURE_CARD_PREFIX + "cat_calico");
    defaultModels.put("minecraft:cat::jellie", MOB_CAPTURE_CARD_PREFIX + "cat_jellie");
    defaultModels.put("minecraft:cat::persian", MOB_CAPTURE_CARD_PREFIX + "cat_persian");
    defaultModels.put("minecraft:cat::ragdoll", MOB_CAPTURE_CARD_PREFIX + "cat_ragdoll");
    defaultModels.put("minecraft:cat::red", MOB_CAPTURE_CARD_PREFIX + "cat_red");
    defaultModels.put("minecraft:cat::siamese", MOB_CAPTURE_CARD_PREFIX + "cat_siamese");
    defaultModels.put("minecraft:cat::tabby", MOB_CAPTURE_CARD_PREFIX + "cat_tabby");
    defaultModels.put("minecraft:cat::white", MOB_CAPTURE_CARD_PREFIX + "cat_white");
    defaultModels.put("minecraft:chicken", MOB_CAPTURE_CARD_PREFIX + "chicken");
    defaultModels.put("minecraft:cod", MOB_CAPTURE_CARD_PREFIX + "cod");
    defaultModels.put("minecraft:cow", MOB_CAPTURE_CARD_PREFIX + "cow");
    defaultModels.put("minecraft:creeper", MOB_CAPTURE_CARD_PREFIX + "creeper");
    defaultModels.put("minecraft:drowned", MOB_CAPTURE_CARD_PREFIX + "drowned");
    defaultModels.put("minecraft:enderman", MOB_CAPTURE_CARD_PREFIX + "enderman");
    defaultModels.put("minecraft:evoker", MOB_CAPTURE_CARD_PREFIX + "evoker");
    defaultModels.put("minecraft:frog", MOB_CAPTURE_CARD_PREFIX + "frog");
    defaultModels.put("minecraft:frog::cold", MOB_CAPTURE_CARD_PREFIX + "frog_cold");
    defaultModels.put("minecraft:frog::temperate", MOB_CAPTURE_CARD_PREFIX + "frog_temperate");
    defaultModels.put("minecraft:frog::warm", MOB_CAPTURE_CARD_PREFIX + "frog_warm");
    defaultModels.put("minecraft:glow_squid", MOB_CAPTURE_CARD_PREFIX + "glow_squid");
    defaultModels.put("minecraft:guardian", MOB_CAPTURE_CARD_PREFIX + "guardian");
    defaultModels.put("minecraft:husk", MOB_CAPTURE_CARD_PREFIX + "husk");
    defaultModels.put("minecraft:iron_golem", MOB_CAPTURE_CARD_PREFIX + "iron_golem");
    defaultModels.put("minecraft:magma_cube", MOB_CAPTURE_CARD_PREFIX + "magma_cube");
    defaultModels.put("minecraft:phantom", MOB_CAPTURE_CARD_PREFIX + "phantom");
    defaultModels.put("minecraft:pig", MOB_CAPTURE_CARD_PREFIX + "pig");
    defaultModels.put("minecraft:piglin", MOB_CAPTURE_CARD_PREFIX + "piglin");
    defaultModels.put("minecraft:piglin_brute", MOB_CAPTURE_CARD_PREFIX + "piglin_brute");
    defaultModels.put("minecraft:pillager", MOB_CAPTURE_CARD_PREFIX + "pillager");
    defaultModels.put("minecraft:rabbit", MOB_CAPTURE_CARD_PREFIX + "rabbit");
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
    defaultModels.put("minecraft:spider", MOB_CAPTURE_CARD_PREFIX + "spider");
    defaultModels.put("minecraft:squid", MOB_CAPTURE_CARD_PREFIX + "squid");
    defaultModels.put("minecraft:turtle", MOB_CAPTURE_CARD_PREFIX + "turtle");
    defaultModels.put("minecraft:villager", MOB_CAPTURE_CARD_PREFIX + "villager");
    defaultModels.put("minecraft:warden", MOB_CAPTURE_CARD_PREFIX + "warden");
    defaultModels.put("minecraft:witch", MOB_CAPTURE_CARD_PREFIX + "witch");
    defaultModels.put("minecraft:wither", MOB_CAPTURE_CARD_PREFIX + "wither");
    defaultModels.put("minecraft:wither_skeleton", MOB_CAPTURE_CARD_PREFIX + "wither_skeleton");
    defaultModels.put("minecraft:zombie", MOB_CAPTURE_CARD_PREFIX + "zombie");
    defaultModels.put("minecraft:zombie_villager", MOB_CAPTURE_CARD_PREFIX + "zombie_villager");
    defaultModels.put("minecraft:zombified_piglin", MOB_CAPTURE_CARD_PREFIX + "zombified_piglin");
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

      // Add model resource location
      mobCaptureCardModels.put(
          propertyKey,
          new ModelResourceLocation(ResourceLocation.parse(propertyValue), "standalone"));
    }

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static ModelResourceLocation getModelResourceLocation(final String entityName) {
    return mobCaptureCardModels.get(entityName);
  }

  public static ModelResourceLocation getModelResourceLocation(
      String entityName, String variant, MobColor color) {
    return mobCaptureCardModels.getOrDefault(
        getEntityKey(entityName, variant, color), mobCaptureCardModels.get(entityName));
  }

  public static Set<String> getMobCaptureCardModels() {
    return mobCaptureCardModels.keySet();
  }

  public static String getEntityKey(
      final String entityName, final String variant, final MobColor color) {
    KEY_BUILDER.setLength(0);
    KEY_BUILDER.append(entityName.trim().toLowerCase(Locale.ROOT));

    if (color == null && (variant == null || variant.isEmpty())) {
      return KEY_BUILDER.toString();
    }

    if (variant != null && !variant.isEmpty()) {
      KEY_BUILDER.append(KEY_SEPARATOR).append(variant.trim().toLowerCase(Locale.ROOT));
    }

    if (color != null && color != MobColor.NONE) {
      KEY_BUILDER.append(KEY_SEPARATOR).append(color.getName().toLowerCase(Locale.ROOT));
    }

    return KEY_BUILDER.toString();
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
