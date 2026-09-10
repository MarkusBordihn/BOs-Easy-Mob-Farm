/*
 * Copyright 2026 Markus Bordihn
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

public class ClientConfig extends Config {

  public static final String CONFIG_FILE_NAME = "client.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Client Configuration

 This configuration file contains client-side settings that do not affect the game logic.
 The mob farm screen also offers a button to switch the theme, which updates this file.

 Configuration Options:
 ----------------------
 mobFarmScreenTheme: Theme of the mob farm screen
   (technology, technical [Original], classic, rustic, dark, steampunk, create;
    default: technology)
""";

  public static String mobFarmScreenTheme = "technology";

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    mobFarmScreenTheme = parseConfigValue(properties, "mobFarmScreenTheme", mobFarmScreenTheme);

    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);
  }

  public static void setMobFarmScreenTheme(String themeId) {
    mobFarmScreenTheme = themeId;
    updateConfigValue(CONFIG_FILE_NAME, CONFIG_FILE_HEADER, "mobFarmScreenTheme", themeId);
  }
}
