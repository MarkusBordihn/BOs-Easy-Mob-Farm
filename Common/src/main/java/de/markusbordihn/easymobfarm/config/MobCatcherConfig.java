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
import java.util.Properties;
import java.util.Set;

public class MobCatcherConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_catcher.cfg";
  public static final String CONFIG_FILE_HEADER =
      """
 Mob Catcher Configuration

 This configuration file allows you to define the general settings for the Mob Catcher.

 Allow and Deny list
 -------------------
 If you want to allow or deny specific mobs, you can use the allow and deny list.
 Keep in mind that if you add a mob to the allow list, all other mobs will be denied by default.
 Additionally mobs on the allow list will be catchable even if their size is bigger than the maximum size.

 Each Mob Catcher has it own allow and deny list, so you can define different settings for each Mob Catcher.

  Example for allow only specific mobs:
    EnduringCaptureNet:AllowList=minecraft:cow,minecraft:sheep,minecraft:chicken

  Example for deny specific mobs:
    VoidBindingChain:DenyList=minecraft:endermite,minecraft:ender_dragon,minecraft:wither


""";

  // Enduring Capture Net
  public static boolean ENDURING_CAPTURE_NET_ENABLED = true;
  public static int ENDURING_CAPTURE_NET_MAX_DURABILITY = 64;
  public static float ENDURING_CAPTURE_NET_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE = 0.5f;
  public static float ENDURING_CAPTURE_NET_MAX_ENTITY_HEIGHT_TO_CAPTURE = 1.4f;
  public static float ENDURING_CAPTURE_NET_MAX_ENTITY_WIDTH_TO_CAPTURE = 0.9f;
  public static Set<String> ENDURING_CAPTURE_NET_ALLOW_LIST = Set.of();
  public static Set<String> ENDURING_CAPTURE_NET_DENY_LIST = Set.of();

  // Ironbound Containment Cage
  public static boolean IRONBOUND_CONTAINMENT_CAGE_ENABLED = true;
  public static int IRONBOUND_CONTAINMENT_CAGE_MAX_DURABILITY = 32;
  public static float IRONBOUND_CONTAINMENT_CAGE_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE = 0.25f;
  public static float IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_HEIGHT_TO_CAPTURE = 2.0f;
  public static float IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_WIDTH_TO_CAPTURE = 1.5f;
  public static Set<String> IRONBOUND_CONTAINMENT_CAGE_ALLOW_LIST = Set.of();
  public static Set<String> IRONBOUND_CONTAINMENT_CAGE_DENY_LIST = Set.of();

  // Mystic Binding Crystal
  public static boolean MYSTIC_BINDING_CRYSTAL_ENABLED = true;
  public static int MYSTIC_BINDING_CRYSTAL_MAX_DURABILITY = 32;
  public static float MYSTIC_BINDING_CRYSTAL_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE = 0.25f;
  public static float MYSTIC_BINDING_CRYSTAL_MAX_ENTITY_HEIGHT_TO_CAPTURE = 4f;
  public static float MYSTIC_BINDING_CRYSTAL_MAX_ENTITY_WIDTH_TO_CAPTURE = 3f;
  public static Set<String> MYSTIC_BINDING_CRYSTAL_ALLOW_LIST = Set.of();
  public static Set<String> MYSTIC_BINDING_CRYSTAL_DENY_LIST = Set.of();

  // Void Binding Chain
  public static boolean VOID_BINDING_CHAIN_ENABLED = true;
  public static int VOID_BINDING_CHAIN_MAX_DURABILITY = 16;
  public static float VOID_BINDING_CHAIN_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE = 0.20f;
  public static float VOID_BINDING_CHAIN_MAX_ENTITY_HEIGHT_TO_CAPTURE = 6f;
  public static float VOID_BINDING_CHAIN_MAX_ENTITY_WIDTH_TO_CAPTURE = 4f;
  public static Set<String> VOID_BINDING_CHAIN_ALLOW_LIST = Set.of();
  public static Set<String> VOID_BINDING_CHAIN_DENY_LIST = Set.of();

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Enduring Capture Net
    ENDURING_CAPTURE_NET_ENABLED =
        parseConfigValue(properties, "EnduringCaptureNet:Enabled", ENDURING_CAPTURE_NET_ENABLED);
    ENDURING_CAPTURE_NET_MAX_DURABILITY =
        parseConfigValue(
            properties, "EnduringCaptureNet:MaxDurability", ENDURING_CAPTURE_NET_MAX_DURABILITY);
    ENDURING_CAPTURE_NET_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE =
        parseConfigValue(
            properties,
            "EnduringCaptureNet:RequiredHealthPercentageToCapture",
            ENDURING_CAPTURE_NET_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE);
    ENDURING_CAPTURE_NET_MAX_ENTITY_HEIGHT_TO_CAPTURE =
        parseConfigValue(
            properties,
            "EnduringCaptureNet:MaxEntityHeightToCapture",
            ENDURING_CAPTURE_NET_MAX_ENTITY_HEIGHT_TO_CAPTURE);
    ENDURING_CAPTURE_NET_MAX_ENTITY_WIDTH_TO_CAPTURE =
        parseConfigValue(
            properties,
            "EnduringCaptureNet:MaxEntityWidthToCapture",
            ENDURING_CAPTURE_NET_MAX_ENTITY_WIDTH_TO_CAPTURE);
    ENDURING_CAPTURE_NET_ALLOW_LIST =
        parseConfigValue(
            properties, "EnduringCaptureNet:AllowList", ENDURING_CAPTURE_NET_ALLOW_LIST);
    ENDURING_CAPTURE_NET_DENY_LIST =
        parseConfigValue(properties, "EnduringCaptureNet:DenyList", ENDURING_CAPTURE_NET_DENY_LIST);

    // Ironbound Containment Cage
    IRONBOUND_CONTAINMENT_CAGE_ENABLED =
        parseConfigValue(
            properties, "IronboundContainmentCage:Enabled", IRONBOUND_CONTAINMENT_CAGE_ENABLED);
    IRONBOUND_CONTAINMENT_CAGE_MAX_DURABILITY =
        parseConfigValue(
            properties,
            "IronboundContainmentCage:MaxDurability",
            IRONBOUND_CONTAINMENT_CAGE_MAX_DURABILITY);
    IRONBOUND_CONTAINMENT_CAGE_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE =
        parseConfigValue(
            properties,
            "IronboundContainmentCage:RequiredHealthPercentageToCapture",
            IRONBOUND_CONTAINMENT_CAGE_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE);
    IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_HEIGHT_TO_CAPTURE =
        parseConfigValue(
            properties,
            "IronboundContainmentCage:MaxEntityHeightToCapture",
            IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_HEIGHT_TO_CAPTURE);
    IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_WIDTH_TO_CAPTURE =
        parseConfigValue(
            properties,
            "IronboundContainmentCage:MaxEntityWidthToCapture",
            IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_WIDTH_TO_CAPTURE);
    IRONBOUND_CONTAINMENT_CAGE_ALLOW_LIST =
        parseConfigValue(
            properties,
            "IronboundContainmentCage:AllowList",
            IRONBOUND_CONTAINMENT_CAGE_ALLOW_LIST);
    IRONBOUND_CONTAINMENT_CAGE_DENY_LIST =
        parseConfigValue(
            properties, "IronboundContainmentCage:DenyList", IRONBOUND_CONTAINMENT_CAGE_DENY_LIST);

    // Mystic Binding Crystal
    MYSTIC_BINDING_CRYSTAL_ENABLED =
        parseConfigValue(
            properties, "MysticBindingCrystal:Enabled", MYSTIC_BINDING_CRYSTAL_ENABLED);
    MYSTIC_BINDING_CRYSTAL_MAX_DURABILITY =
        parseConfigValue(
            properties,
            "MysticBindingCrystal:MaxDurability",
            MYSTIC_BINDING_CRYSTAL_MAX_DURABILITY);
    MYSTIC_BINDING_CRYSTAL_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE =
        parseConfigValue(
            properties,
            "MysticBindingCrystal:RequiredHealthPercentageToCapture",
            MYSTIC_BINDING_CRYSTAL_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE);
    MYSTIC_BINDING_CRYSTAL_MAX_ENTITY_HEIGHT_TO_CAPTURE =
        parseConfigValue(
            properties,
            "MysticBindingCrystal:MaxEntityHeightToCapture",
            MYSTIC_BINDING_CRYSTAL_MAX_ENTITY_HEIGHT_TO_CAPTURE);
    MYSTIC_BINDING_CRYSTAL_MAX_ENTITY_WIDTH_TO_CAPTURE =
        parseConfigValue(
            properties,
            "MysticBindingCrystal:MaxEntityWidthToCapture",
            MYSTIC_BINDING_CRYSTAL_MAX_ENTITY_WIDTH_TO_CAPTURE);
    MYSTIC_BINDING_CRYSTAL_ALLOW_LIST =
        parseConfigValue(
            properties, "MysticBindingCrystal:AllowList", MYSTIC_BINDING_CRYSTAL_ALLOW_LIST);
    MYSTIC_BINDING_CRYSTAL_DENY_LIST =
        parseConfigValue(
            properties, "MysticBindingCrystal:DenyList", MYSTIC_BINDING_CRYSTAL_DENY_LIST);

    // Void Binding Chain
    VOID_BINDING_CHAIN_ENABLED =
        parseConfigValue(properties, "VoidBindingChain:Enabled", VOID_BINDING_CHAIN_ENABLED);
    VOID_BINDING_CHAIN_MAX_DURABILITY =
        parseConfigValue(
            properties, "VoidBindingChain:MaxDurability", VOID_BINDING_CHAIN_MAX_DURABILITY);
    VOID_BINDING_CHAIN_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE =
        parseConfigValue(
            properties,
            "VoidBindingChain:RequiredHealthPercentageToCapture",
            VOID_BINDING_CHAIN_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE);
    VOID_BINDING_CHAIN_MAX_ENTITY_HEIGHT_TO_CAPTURE =
        parseConfigValue(
            properties,
            "VoidBindingChain:MaxEntityHeightToCapture",
            VOID_BINDING_CHAIN_MAX_ENTITY_HEIGHT_TO_CAPTURE);
    VOID_BINDING_CHAIN_MAX_ENTITY_WIDTH_TO_CAPTURE =
        parseConfigValue(
            properties,
            "VoidBindingChain:MaxEntityWidthToCapture",
            VOID_BINDING_CHAIN_MAX_ENTITY_WIDTH_TO_CAPTURE);
    VOID_BINDING_CHAIN_ALLOW_LIST =
        parseConfigValue(properties, "VoidBindingChain:AllowList", VOID_BINDING_CHAIN_ALLOW_LIST);
    VOID_BINDING_CHAIN_DENY_LIST =
        parseConfigValue(properties, "VoidBindingChain:DenyList", VOID_BINDING_CHAIN_DENY_LIST);

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }
}
