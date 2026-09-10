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

public class MobCaptureCardConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_capture_card.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Mob Capture Card Configuration

 This configuration file allows you to define the general settings for the Mob Capture Cards.

 Configuration Options:
 ----------------------
 dropMobCaptureCardOnKill: Enable mob capture card drops when killing mobs (default: true)
 dropMobCaptureCardOnFishing: Enable mob capture card drops when fishing (default: true)

 requirePlayerKill: Only drop cards when player kills the mob (default: true)
 mobCaptureCardKillDropChance: Drop chance for kill drops (0.0-1.0, default: 0.1 = 10%)
 mobCaptureCardFishingDropChance: Drop chance for fishing drops (0.0-1.0, default: 0.15 = 15%)
 mobCaptureCardFoilDropChance: Drop chance for foil cards (0.0-1.0, default: 0.01 = 1%)

 Card stacking
 -------------
 Cards only stack when their stored mob data is identical, so per mob data like the age or the
 owner is removed when a card is created. Existing cards are cleaned up once when they are held
 in an inventory.

 upgradeExistingMobCaptureCards: Clean up cards from older versions on load (default: true)
 additionalCardTagsToRemove: Extra mob data tags to remove, for mobs from other mods

  Example:
    additionalCardTagsToRemove=SomeModTimer,SomeModRandomSeed

 Allow and Deny list
 -------------------
 If you want to allow or deny specific mobs, you can use the allow and deny list.
 Keep in mind that if you add a mob to the allow list, all other mobs will be denied by default.
 There are two separate lists for kill and fishing drops.

  Example for allow only specific mobs:
    mobCaptureCardKillDropAllowList=minecraft:zombie,minecraft:skeleton
    mobCaptureCardKillDropDenyList=
    mobCaptureCardFishingDropAllowList=minecraft:cod,minecraft:salmon
    mobCaptureCardFishingDropDenyList=

  Example for deny specific mobs:
    mobCaptureCardKillDropAllowList=
    mobCaptureCardKillDropDenyList=minecraft:ender_dragon,minecraft:wither
    mobCaptureCardFishingDropAllowList=
    mobCaptureCardFishingDropDenyList=minecraft:squid,minecraft:turtle


""";

  public static boolean dropMobCaptureCardOnKill = true;
  public static boolean dropMobCaptureCardOnFishing = true;

  public static boolean requirePlayerKill = true;
  public static float mobCaptureCardKillDropChance = 10 / 100f;
  public static float mobCaptureCardFishingDropChance = 15 / 100f;
  public static float mobCaptureCardFoilDropChance = 1 / 100f;

  public static boolean upgradeExistingMobCaptureCards = true;
  public static Set<String> additionalCardTagsToRemove = Set.of();

  public static Set<String> mobCaptureCardKillDropAllowList = Set.of();
  public static Set<String> mobCaptureCardKillDropDenyList = Set.of();

  public static Set<String> mobCaptureCardFishingDropAllowList = Set.of();
  public static Set<String> mobCaptureCardFishingDropDenyList = Set.of();

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Config entries
    dropMobCaptureCardOnKill =
        parseConfigValue(properties, "dropMobCaptureCardOnKill", dropMobCaptureCardOnKill);
    dropMobCaptureCardOnFishing =
        parseConfigValue(properties, "dropMobCaptureCardOnFishing", dropMobCaptureCardOnFishing);

    requirePlayerKill = parseConfigValue(properties, "requirePlayerKill", requirePlayerKill);
    mobCaptureCardKillDropChance =
        parseConfigValue(
            properties, "mobCaptureCardKillDropChance", mobCaptureCardKillDropChance, 0.0f, 1.0f);

    mobCaptureCardFishingDropChance =
        parseConfigValue(
            properties,
            "mobCaptureCardFishingDropChance",
            mobCaptureCardFishingDropChance,
            0.0f,
            1.0f);
    mobCaptureCardFoilDropChance =
        parseConfigValue(
            properties, "mobCaptureCardFoilDropChance", mobCaptureCardFoilDropChance, 0.0f, 1.0f);

    upgradeExistingMobCaptureCards =
        parseConfigValue(
            properties, "upgradeExistingMobCaptureCards", upgradeExistingMobCaptureCards);
    additionalCardTagsToRemove =
        parseConfigValue(properties, "additionalCardTagsToRemove", additionalCardTagsToRemove);

    mobCaptureCardKillDropAllowList =
        parseConfigValue(
            properties, "mobCaptureCardKillDropAllowList", mobCaptureCardKillDropAllowList);
    mobCaptureCardKillDropDenyList =
        parseConfigValue(
            properties, "mobCaptureCardKillDropDenyList", mobCaptureCardKillDropDenyList);

    mobCaptureCardFishingDropAllowList =
        parseConfigValue(
            properties, "mobCaptureCardFishingDropAllowList", mobCaptureCardFishingDropAllowList);
    mobCaptureCardFishingDropDenyList =
        parseConfigValue(
            properties, "mobCaptureCardFishingDropDenyList", mobCaptureCardFishingDropDenyList);

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }
}
