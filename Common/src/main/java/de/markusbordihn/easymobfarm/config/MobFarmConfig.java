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

""";

  public static int tier0progressionUpgradeSpeed = 0;
  public static int tier1progressionUpgradeSpeed = 3;
  public static int tier2progressionUpgradeSpeed = 6;
  public static int tier3progressionUpgradeSpeed = 9;

  public static int experienceDropChance = 5;
  public static int speedEnhancementUpgradeSpeed = 6;

  public static boolean processingRequiresOwnerToBeOnline = false;

  public static int luckyDropFarmLuckPercentage = 95;

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    // Config entries
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

    processingRequiresOwnerToBeOnline =
        parseConfigValue(
            properties, "progressingRequiresOwnerToBeOnline", processingRequiresOwnerToBeOnline);

    luckyDropFarmLuckPercentage =
        parseConfigValue(properties, "luckyDropFarmLuckPercentage", luckyDropFarmLuckPercentage);

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
