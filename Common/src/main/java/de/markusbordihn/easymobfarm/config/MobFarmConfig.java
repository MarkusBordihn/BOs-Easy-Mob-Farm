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

import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.EggCollectorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.FrogCatalystEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyHarvesterFrameEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.KnifeEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LootEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LuckEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.MilkExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.PollenTrapEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
 speedEnhancementUpgradeSpeed: Speed bonus from speed enhancement upgrades (default: 6, set 0 to disable)
 swordEnhancementAdditionalLuck: Additional luck from sword enhancement (default: 0.5)
 knifeEnhancementAdditionalLuck: Additional luck from knife enhancement (default: 0.25)

 Duplicate Enhancement Limits:
 tier0MaxSpeedEnhancements: Maximum speed enhancements for tier 0 farms (default: 4)
 tier1MaxSpeedEnhancements: Maximum speed enhancements for tier 1 farms (default: 4)
 tier2MaxSpeedEnhancements: Maximum speed enhancements for tier 2 farms (default: 4)
 tier3MaxSpeedEnhancements: Maximum speed enhancements for tier 3 farms (default: 4)
 tier0MaxLootEnhancements: Maximum loot enhancements for tier 0 farms (default: 4)
 tier1MaxLootEnhancements: Maximum loot enhancements for tier 1 farms (default: 4)
 tier2MaxLootEnhancements: Maximum loot enhancements for tier 2 farms (default: 4)
 tier3MaxLootEnhancements: Maximum loot enhancements for tier 3 farms (default: 4)
 tier0MaxLuckEnhancements: Maximum luck enhancements for tier 0 farms (default: 4)
 tier1MaxLuckEnhancements: Maximum luck enhancements for tier 1 farms (default: 4)
 tier2MaxLuckEnhancements: Maximum luck enhancements for tier 2 farms (default: 4)
 tier3MaxLuckEnhancements: Maximum luck enhancements for tier 3 farms (default: 4)

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

  public static int tier0MaxSpeedEnhancements = 4;
  public static int tier1MaxSpeedEnhancements = 4;
  public static int tier2MaxSpeedEnhancements = 4;
  public static int tier3MaxSpeedEnhancements = 4;
  public static int tier0MaxLootEnhancements = 4;
  public static int tier1MaxLootEnhancements = 4;
  public static int tier2MaxLootEnhancements = 4;
  public static int tier3MaxLootEnhancements = 4;
  public static int tier0MaxLuckEnhancements = 4;
  public static int tier1MaxLuckEnhancements = 4;
  public static int tier2MaxLuckEnhancements = 4;
  public static int tier3MaxLuckEnhancements = 4;

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

    // Enhancement limits
    tier0MaxSpeedEnhancements =
        parseConfigValue(properties, "tier0MaxSpeedEnhancements", tier0MaxSpeedEnhancements);
    tier1MaxSpeedEnhancements =
        parseConfigValue(properties, "tier1MaxSpeedEnhancements", tier1MaxSpeedEnhancements);
    tier2MaxSpeedEnhancements =
        parseConfigValue(properties, "tier2MaxSpeedEnhancements", tier2MaxSpeedEnhancements);
    tier3MaxSpeedEnhancements =
        parseConfigValue(properties, "tier3MaxSpeedEnhancements", tier3MaxSpeedEnhancements);
    tier0MaxLootEnhancements =
        parseConfigValue(properties, "tier0MaxLootEnhancements", tier0MaxLootEnhancements);
    tier1MaxLootEnhancements =
        parseConfigValue(properties, "tier1MaxLootEnhancements", tier1MaxLootEnhancements);
    tier2MaxLootEnhancements =
        parseConfigValue(properties, "tier2MaxLootEnhancements", tier2MaxLootEnhancements);
    tier3MaxLootEnhancements =
        parseConfigValue(properties, "tier3MaxLootEnhancements", tier3MaxLootEnhancements);
    tier0MaxLuckEnhancements =
        parseConfigValue(properties, "tier0MaxLuckEnhancements", tier0MaxLuckEnhancements);
    tier1MaxLuckEnhancements =
        parseConfigValue(properties, "tier1MaxLuckEnhancements", tier1MaxLuckEnhancements);
    tier2MaxLuckEnhancements =
        parseConfigValue(properties, "tier2MaxLuckEnhancements", tier2MaxLuckEnhancements);
    tier3MaxLuckEnhancements =
        parseConfigValue(properties, "tier3MaxLuckEnhancements", tier3MaxLuckEnhancements);

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

  public static boolean isSpeedEnhancementEnabled() {
    return enableSpeedEnhancement && speedEnhancementUpgradeSpeed > 0;
  }

  public static boolean isLootEnhancementEnabled() {
    return enableLootEnhancement && lootEnhancementAdditionalRolls > 0;
  }

  public static boolean isLuckEnhancementEnabled() {
    return enableLuckEnhancement && luckEnhancementAdditionalLuck > 0;
  }

  public static boolean isSwordEnhancementEnabled() {
    return enableSwordEnhancement;
  }

  public static boolean isKnifeEnhancementEnabled() {
    return enableKnifeEnhancement;
  }

  public static boolean isEnhancementEnabled(EnhancementItem enhancementItem) {
    return isEnhancementTypeEnabled(enhancementItem.getClass());
  }

  static boolean isEnhancementTypeEnabled(Class<? extends EnhancementItem> enhancementType) {
    if (SpeedEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return isSpeedEnhancementEnabled();
    }
    if (LootEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return isLootEnhancementEnabled();
    }
    if (LuckEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return isLuckEnhancementEnabled();
    }
    if (SwordEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return isSwordEnhancementEnabled();
    }
    if (KnifeEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return isKnifeEnhancementEnabled();
    }
    if (ExperienceEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enableExperienceEnhancement;
    }
    if (EggCollectorEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enableEggCollectorEnhancement;
    }
    if (HoneyExtractorEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enableHoneyExtractorEnhancement;
    }
    if (HoneyHarvesterFrameEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enableHoneyHarvesterFrameEnhancement;
    }
    if (MilkExtractorEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enableMilkExtractorEnhancement;
    }
    if (PollenTrapEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enablePollenTrapEnhancement;
    }
    if (SheepEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enableSheepEnhancement;
    }
    if (FrogCatalystEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return enableFrogCatalystEnhancement;
    }
    return true;
  }

  public static int getMaxSpeedEnhancements(int tierLevel) {
    return switch (tierLevel) {
      case 0 -> Math.max(0, tier0MaxSpeedEnhancements);
      case 1 -> Math.max(0, tier1MaxSpeedEnhancements);
      case 2 -> Math.max(0, tier2MaxSpeedEnhancements);
      case 3 -> Math.max(0, tier3MaxSpeedEnhancements);
      default -> Math.max(0, tier0MaxSpeedEnhancements);
    };
  }

  public static int getMaxLootEnhancements(int tierLevel) {
    return switch (tierLevel) {
      case 0 -> Math.max(0, tier0MaxLootEnhancements);
      case 1 -> Math.max(0, tier1MaxLootEnhancements);
      case 2 -> Math.max(0, tier2MaxLootEnhancements);
      case 3 -> Math.max(0, tier3MaxLootEnhancements);
      default -> Math.max(0, tier0MaxLootEnhancements);
    };
  }

  public static int getMaxLuckEnhancements(int tierLevel) {
    return switch (tierLevel) {
      case 0 -> Math.max(0, tier0MaxLuckEnhancements);
      case 1 -> Math.max(0, tier1MaxLuckEnhancements);
      case 2 -> Math.max(0, tier2MaxLuckEnhancements);
      case 3 -> Math.max(0, tier3MaxLuckEnhancements);
      default -> Math.max(0, tier0MaxLuckEnhancements);
    };
  }

  public static int getMaxDuplicateEnhancements(EnhancementItem enhancementItem, int tierLevel) {
    return getMaxDuplicateEnhancements(enhancementItem.getClass(), tierLevel);
  }

  static int getMaxDuplicateEnhancements(
      Class<? extends EnhancementItem> enhancementType, int tierLevel) {
    if (SpeedEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return getMaxSpeedEnhancements(tierLevel);
    }
    if (LootEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return getMaxLootEnhancements(tierLevel);
    }
    if (LuckEnhancementItem.class.isAssignableFrom(enhancementType)) {
      return getMaxLuckEnhancements(tierLevel);
    }
    return Integer.MAX_VALUE;
  }

  public static boolean canAddEnhancement(
      EnhancementItem enhancementItem, List<EnhancementItem> existingEnhancements, int tierLevel) {
    return canAddEnhancementType(
        enhancementItem.getClass(),
        existingEnhancements.stream().map(EnhancementItem::getClass).toList(),
        tierLevel);
  }

  static boolean canAddEnhancementType(
      Class<? extends EnhancementItem> enhancementType,
      List<Class<? extends EnhancementItem>> existingEnhancementTypes,
      int tierLevel) {
    if (!isEnhancementTypeEnabled(enhancementType)) {
      return false;
    }
    int maxEnhancements = getMaxDuplicateEnhancements(enhancementType, tierLevel);
    if (maxEnhancements == Integer.MAX_VALUE) {
      return true;
    }
    return countMatchingEnhancementTypes(existingEnhancementTypes, enhancementType, maxEnhancements)
        < maxEnhancements;
  }

  public static List<EnhancementItem> getEffectiveEnhancements(
      List<EnhancementItem> enhancements, int tierLevel) {
    List<EnhancementItem> effectiveEnhancements = new ArrayList<>();
    List<Class<? extends EnhancementItem>> effectiveEnhancementTypes =
        getEffectiveEnhancementTypes(
            enhancements.stream().map(EnhancementItem::getClass).toList(), tierLevel);
    for (EnhancementItem enhancement : enhancements) {
      if (effectiveEnhancementTypes.remove(enhancement.getClass())) {
        effectiveEnhancements.add(enhancement);
      }
    }
    return effectiveEnhancements;
  }

  static List<Class<? extends EnhancementItem>> getEffectiveEnhancementTypes(
      List<Class<? extends EnhancementItem>> enhancementTypes, int tierLevel) {
    List<Class<? extends EnhancementItem>> effectiveEnhancementTypes = new ArrayList<>();
    int speedEnhancements = 0;
    int lootEnhancements = 0;
    int luckEnhancements = 0;
    int maxSpeedEnhancements = getMaxSpeedEnhancements(tierLevel);
    int maxLootEnhancements = getMaxLootEnhancements(tierLevel);
    int maxLuckEnhancements = getMaxLuckEnhancements(tierLevel);
    for (Class<? extends EnhancementItem> enhancementType : enhancementTypes) {
      if (!isEnhancementTypeEnabled(enhancementType)) {
        continue;
      }
      if (SpeedEnhancementItem.class.isAssignableFrom(enhancementType)) {
        if (speedEnhancements++ >= maxSpeedEnhancements) {
          continue;
        }
      } else if (LootEnhancementItem.class.isAssignableFrom(enhancementType)) {
        if (lootEnhancements++ >= maxLootEnhancements) {
          continue;
        }
      } else if (LuckEnhancementItem.class.isAssignableFrom(enhancementType)) {
        if (luckEnhancements++ >= maxLuckEnhancements) {
          continue;
        }
      }
      effectiveEnhancementTypes.add(enhancementType);
    }
    return effectiveEnhancementTypes;
  }

  public static int countMatchingEnhancements(
      List<EnhancementItem> enhancements, EnhancementItem enhancementItem, int maxCount) {
    return countMatchingEnhancementTypes(
        enhancements.stream().map(EnhancementItem::getClass).toList(),
        enhancementItem.getClass(),
        maxCount);
  }

  static int countMatchingEnhancementTypes(
      List<Class<? extends EnhancementItem>> enhancementTypes,
      Class<? extends EnhancementItem> enhancementType,
      int maxCount) {
    int count = 0;
    for (Class<? extends EnhancementItem> existingEnhancementType : enhancementTypes) {
      if (isSameLimitedEnhancementType(existingEnhancementType, enhancementType)
          && ++count >= maxCount) {
        return count;
      }
    }
    return count;
  }

  private static boolean isSameLimitedEnhancementType(
      Class<? extends EnhancementItem> firstEnhancementType,
      Class<? extends EnhancementItem> secondEnhancementType) {
    if (SpeedEnhancementItem.class.isAssignableFrom(firstEnhancementType)
        && SpeedEnhancementItem.class.isAssignableFrom(secondEnhancementType)) {
      return true;
    }
    if (LootEnhancementItem.class.isAssignableFrom(firstEnhancementType)
        && LootEnhancementItem.class.isAssignableFrom(secondEnhancementType)) {
      return true;
    }
    if (LuckEnhancementItem.class.isAssignableFrom(firstEnhancementType)
        && LuckEnhancementItem.class.isAssignableFrom(secondEnhancementType)) {
      return true;
    }
    return firstEnhancementType.equals(secondEnhancementType);
  }
}
