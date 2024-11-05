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

public class MobCaptureCardConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_capture_card.cfg";
  public static final String CONFIG_FILE_HEADER =
      """
 Mob Capture Card Configuration

 This configuration file allows you to define the general settings for the Mob Capture Cards.

""";

  public static boolean dropMobCaptureCardOnKill = true;
  public static boolean dropMobCaptureCardOnFishing = true;

  public static boolean requirePlayerKill = true;
  public static float mobCaptureCardKillDropChance = 10 / 100f;
  public static float mobCaptureCardFishingDropChance = 15 / 100f;
  public static float mobCaptureCardFoilDropChance = 1 / 100f;

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
        Boolean.parseBoolean(
            properties.getProperty(
                "dropMobCaptureCardOnKill", Boolean.toString(dropMobCaptureCardOnKill)));
    dropMobCaptureCardOnFishing =
        Boolean.parseBoolean(
            properties.getProperty(
                "dropMobCaptureCardOnFishing", Boolean.toString(dropMobCaptureCardOnFishing)));

    requirePlayerKill =
        Boolean.parseBoolean(
            properties.getProperty("requirePlayerKill", Boolean.toString(requirePlayerKill)));
    mobCaptureCardKillDropChance =
        Float.parseFloat(
            properties.getProperty(
                "mobCaptureCardKillDropChance", Float.toString(mobCaptureCardKillDropChance)));
    mobCaptureCardFishingDropChance =
        Float.parseFloat(
            properties.getProperty(
                "mobCaptureCardFishingDropChance",
                Float.toString(mobCaptureCardFishingDropChance)));
    mobCaptureCardFoilDropChance =
        Float.parseFloat(
            properties.getProperty(
                "mobCaptureCardFoilDropChance", Float.toString(mobCaptureCardFoilDropChance)));

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }
}
