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

public class MobFarmConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_farm.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Mob Farm Configuration

 This configuration file allows you to define the general settings for the Mob Farms.

 Configuration Options:
 ----------------------
 farmProgressingTime: Time in ticks for farm progression (default: 6000 = 5 minutes)

 tier0progressionUpgradeSpeed: Speed bonus for tier 0 farms (default: 0)
 tier1progressionUpgradeSpeed: Speed bonus for tier 1 farms (default: 2)
 tier2progressionUpgradeSpeed: Speed bonus for tier 2 farms (default: 4)
 tier3progressionUpgradeSpeed: Speed bonus for tier 3 farms (default: 6)

 experienceDropChance: Chance for experience drops (1 in x chance, default: 5)
 speedEnhancementUpgradeSpeed: Speed bonus from speed enhancement upgrades (default: 6)

 Enhancement Power Configuration:
 lootEnhancementAdditionalRolls: Additional loot rolls from loot enhancement (default: 1, set 0 to disable)
 luckEnhancementAdditionalLuck: Additional luck from luck enhancement (default: 1.0, set 0 to disable)
 swordEnhancementAdditionalLuck: Additional luck from sword enhancement (default: 0.5, set 0 to disable)
 knifeEnhancementAdditionalLuck: Additional luck from knife enhancement (default: 0.25, set 0 to disable)

 Enhancement Enable/Disable (set to false to disable the enhancement and hide its recipe):
 enableSpeedEnhancement: Enable speed enhancement (default: true)
 enableLootEnhancement: Enable loot enhancement (default: true)
 enableLuckEnhancement: Enable luck enhancement (default: true)
 enableSwordEnhancement: Enable sword enhancement (default: true)
 enableKnifeEnhancement: Enable knife enhancement (default: true)
 enableExperienceEnhancement: Enable experience enhancement (default: true)
 enableEggCollectorEnhancement: Enable egg collector enhancement (default: true)
 enableHoneyExtractorEnhancement: Enable honey extractor enhancement (default: true)
 enableHoneyHarvesterFrameEnhancement: Enable honey harvester frame enhancement (default: true)
 enableMilkExtractorEnhancement: Enable milk extractor enhancement (default: true)
 enablePollenTrapEnhancement: Enable pollen trap enhancement (default: true)
 enableSheepEnhancement: Enable sheep enhancement (default: true)
 enableFrogCatalystEnhancement: Enable frog catalyst enhancement (default: true)

 progressingRequiresOwnerToBeOnline: Whether farms only work when owner is online (default: false)
 luckyDropFarmLuckPercentage: Luck percentage for lucky drop farms (default: 95)
 enforceLogicalTierProgression: Whether farms must be upgraded in order (default: false)
 enableMobFarmUpgradeRecipes: Enable recipes to upgrade existing mob farms (default: true)

 enableItemBuffer: Enable temporary item buffer when output slots are full (default: true)
 maxBufferSize: Maximum number of items in buffer (default: 128)
 bufferProcessInterval: Ticks between buffer processing attempts (default: 20)
 maxBonusDropMultiplier: Maximum multiplier for bonus drop amounts vs stack size (default: 100)
 dropItemsToWorldWhenBufferFull: Drop items to world when buffer is full (default: false)

""";

  public static int farmProgressingTime = 6000; // 5 minutes in seconds
  public static int tier0progressionUpgradeSpeed = 0;
  public static int tier1progressionUpgradeSpeed = 2;
  public static int tier2progressionUpgradeSpeed = 4;
  public static int tier3progressionUpgradeSpeed = 6;

  public static int experienceDropChance = 5;
  public static int speedEnhancementUpgradeSpeed = 6;

  // Enhancement power configuration
  public static int lootEnhancementAdditionalRolls = 1;
  public static float luckEnhancementAdditionalLuck = 1.0f;
  public static float swordEnhancementAdditionalLuck = 0.5f;
  public static float knifeEnhancementAdditionalLuck = 0.25f;

  // Per-enhancement enable/disable flags
  public static boolean enableSpeedEnhancement = true;
  public static boolean enableLootEnhancement = true;
  public static boolean enableLuckEnhancement = true;
  public static boolean enableSwordEnhancement = true;
  public static boolean enableKnifeEnhancement = true;
  public static boolean enableExperienceEnhancement = true;
  public static boolean enableEggCollectorEnhancement = true;
  public static boolean enableHoneyExtractorEnhancement = true;
  public static boolean enableHoneyHarvesterFrameEnhancement = true;
  public static boolean enableMilkExtractorEnhancement = true;
  public static boolean enablePollenTrapEnhancement = true;
  public static boolean enableSheepEnhancement = true;
  public static boolean enableFrogCatalystEnhancement = true;

  public static boolean processingRequiresOwnerToBeOnline = false;

  public static int luckyDropFarmLuckPercentage = 95;

  // New config option for logical tier progression
  public static boolean enforceLogicalTierProgression = false;

  // Config option to enable/disable mob farm upgrade recipes
  public static boolean enableMobFarmUpgradeRecipes = true;

  // Buffer system configuration
  public static boolean enableItemBuffer = true;
  public static int maxBufferSize = 128;
  public static int bufferProcessInterval = 20;
  public static int maxBonusDropMultiplier = 100;
  public static boolean dropItemsToWorldWhenBufferFull = false;

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Config entries
    farmProgressingTime = parseConfigValue(properties, "farmProgressingTime", farmProgressingTime);

    tier0progressionUpgradeSpeed =
        parseConfigValue(properties, "tier0progressionUpgradeSpeed", tier0progressionUpgradeSpeed);
    tier1progressionUpgradeSpeed =
        parseConfigValue(properties, "tier1progressionUpgradeSpeed", tier1progressionUpgradeSpeed);
    tier2progressionUpgradeSpeed =
        parseConfigValue(properties, "tier2progressionUpgradeSpeed", tier2progressionUpgradeSpeed);
    tier3progressionUpgradeSpeed =
        parseConfigValue(properties, "tier3progressionUpgradeSpeed", tier3progressionUpgradeSpeed);

    experienceDropChance =
        parseConfigValue(properties, "experienceDropChance", experienceDropChance);
    speedEnhancementUpgradeSpeed =
        parseConfigValue(properties, "speedEnhancementUpgradeSpeed", speedEnhancementUpgradeSpeed);

    // Enhancement power values
    lootEnhancementAdditionalRolls =
        parseConfigValue(
            properties, "lootEnhancementAdditionalRolls", lootEnhancementAdditionalRolls);
    luckEnhancementAdditionalLuck =
        parseConfigValue(
            properties, "luckEnhancementAdditionalLuck", luckEnhancementAdditionalLuck);
    swordEnhancementAdditionalLuck =
        parseConfigValue(
            properties, "swordEnhancementAdditionalLuck", swordEnhancementAdditionalLuck);
    knifeEnhancementAdditionalLuck =
        parseConfigValue(
            properties, "knifeEnhancementAdditionalLuck", knifeEnhancementAdditionalLuck);

    // Per-enhancement enable/disable
    enableSpeedEnhancement =
        parseConfigValue(properties, "enableSpeedEnhancement", enableSpeedEnhancement);
    enableLootEnhancement =
        parseConfigValue(properties, "enableLootEnhancement", enableLootEnhancement);
    enableLuckEnhancement =
        parseConfigValue(properties, "enableLuckEnhancement", enableLuckEnhancement);
    enableSwordEnhancement =
        parseConfigValue(properties, "enableSwordEnhancement", enableSwordEnhancement);
    enableKnifeEnhancement =
        parseConfigValue(properties, "enableKnifeEnhancement", enableKnifeEnhancement);
    enableExperienceEnhancement =
        parseConfigValue(properties, "enableExperienceEnhancement", enableExperienceEnhancement);
    enableEggCollectorEnhancement =
        parseConfigValue(
            properties, "enableEggCollectorEnhancement", enableEggCollectorEnhancement);
    enableHoneyExtractorEnhancement =
        parseConfigValue(
            properties, "enableHoneyExtractorEnhancement", enableHoneyExtractorEnhancement);
    enableHoneyHarvesterFrameEnhancement =
        parseConfigValue(
            properties,
            "enableHoneyHarvesterFrameEnhancement",
            enableHoneyHarvesterFrameEnhancement);
    enableMilkExtractorEnhancement =
        parseConfigValue(
            properties, "enableMilkExtractorEnhancement", enableMilkExtractorEnhancement);
    enablePollenTrapEnhancement =
        parseConfigValue(properties, "enablePollenTrapEnhancement", enablePollenTrapEnhancement);
    enableSheepEnhancement =
        parseConfigValue(properties, "enableSheepEnhancement", enableSheepEnhancement);
    enableFrogCatalystEnhancement =
        parseConfigValue(
            properties, "enableFrogCatalystEnhancement", enableFrogCatalystEnhancement);

    processingRequiresOwnerToBeOnline =
        parseConfigValue(
            properties, "progressingRequiresOwnerToBeOnline", processingRequiresOwnerToBeOnline);

    luckyDropFarmLuckPercentage =
        parseConfigValue(properties, "luckyDropFarmLuckPercentage", luckyDropFarmLuckPercentage);

    enforceLogicalTierProgression =
        parseConfigValue(
            properties, "enforceLogicalTierProgression", enforceLogicalTierProgression);

    enableMobFarmUpgradeRecipes =
        parseConfigValue(properties, "enableMobFarmUpgradeRecipes", enableMobFarmUpgradeRecipes);

    enableItemBuffer = parseConfigValue(properties, "enableItemBuffer", enableItemBuffer);
    maxBufferSize = parseConfigValue(properties, "maxBufferSize", maxBufferSize);
    bufferProcessInterval =
        parseConfigValue(properties, "bufferProcessInterval", bufferProcessInterval);
    maxBonusDropMultiplier =
        parseConfigValue(properties, "maxBonusDropMultiplier", maxBonusDropMultiplier);
    dropItemsToWorldWhenBufferFull =
        parseConfigValue(
            properties, "dropItemsToWorldWhenBufferFull", dropItemsToWorldWhenBufferFull);

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static int getFarmTierProgressionUpgradeSpeed(int tierLevel) {
    return switch (tierLevel) {
      case 0 -> MobFarmConfig.tier0progressionUpgradeSpeed;
      case 1 -> MobFarmConfig.tier1progressionUpgradeSpeed;
      case 2 -> MobFarmConfig.tier2progressionUpgradeSpeed;
      case 3 -> MobFarmConfig.tier3progressionUpgradeSpeed;
      default -> 0;
    };
  }
}
