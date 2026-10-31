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

package de.markusbordihn.easymobfarm.clienttest;

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.GameClientBuilder;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.GameClientExtension;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.Parameters;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.TestArea;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.RegisterExtension;

abstract class ClientTestBase {

  static final String MOD_NAMESPACE = "easy_mob_farm:";
  static final String MOB_FARM_SCREEN_ID =
      "de.markusbordihn.easymobfarm.client.screen.mobfarmscreenwrapper";
  static final String CARD_BINDER_SCREEN_ID =
      "de.markusbordihn.easymobfarm.client.screen.cardbinderscreenwrapper";
  static final int PLATFORM_Y = 100;
  static final int FARM_Y = PLATFORM_Y + 1;
  static final List<String> LAYOUT_CHECKS_IGNORING_ICON_BUTTON_TEXT_OVERFLOW =
      List.of("outside", "overlap", "rawTranslationKey");
  private static final String SUITE_NAME = "Easy Mob Farm";
  private static final long NOON = 6000L;
  private static final TestArea ARRIVAL_PLATFORM =
      TestArea.between(-7, PLATFORM_Y, -2, 7, PLATFORM_Y, 8);
  private static final String MOD_CONFIGURATION_DIRECTORY = "easy_mob_farm";

  @RegisterExtension
  static final GameClientExtension client =
      GameClientExtension.shared(ClientTestBase::configureLaunch);

  private static GameClientBuilder configureLaunch(GameClientBuilder builder) {
    writeTestConfiguration();
    return builder
        .withSuiteName(SUITE_NAME)
        .withWindowSize(1280, 720)
        .withGuiScale(2)
        .withAllowedCommands("setblock", "tp", "item", "kill", "clear")
        .withWorld()
        .withRenderProfile(
            Parameters.of("preset", "deterministic")
                .with("dayTime", NOON)
                .with("weather", "clear")
                .with("hud", "hidden"))
        .withLayoutChecksOnScreenshot(
            LAYOUT_CHECKS_IGNORING_ICON_BUTTON_TEXT_OVERFLOW.toArray(String[]::new));
  }

  private static void writeTestConfiguration() {
    String gameDirectory = System.getProperty(GameClientBuilder.GAME_DIRECTORY_PROPERTY, "");
    if (gameDirectory.isBlank()) {
      return;
    }

    Map<String, List<String>> testConfiguration =
        Map.of(
            "mob_capture_card.cfg",
            List.of(
                "mobCaptureCardKillDropChance=1.0",
                "mobCaptureCardFishingDropChance=1.0",
                "mobCaptureCardFoilDropChance=0.0"),
            "mob_farm.cfg",
            List.of("farmProgressingTime=1000"));
    Path configurationDirectory = Path.of(gameDirectory, "config", MOD_CONFIGURATION_DIRECTORY);
    try {
      Files.createDirectories(configurationDirectory);
      for (Map.Entry<String, List<String>> configurationFile : testConfiguration.entrySet()) {
        Files.write(
            configurationDirectory.resolve(configurationFile.getKey()),
            configurationFile.getValue(),
            StandardCharsets.UTF_8);
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  static void command(String command) {
    client.runCommand(command);
  }

  static void enterTestArea(int pitch) {
    client.fill(ARRIVAL_PLATFORM, "minecraft:stone");
    client.teleport(0.5, FARM_Y, 0.5, 0f, pitch);
  }

  static void placeFarm(String farmId, int x, int z) {
    client.setBlock(x, FARM_Y, z, MOD_NAMESPACE + farmId);
  }

  static void openFarm(String farmId) {
    enterTestArea(0);
    placeFarm(farmId, 0, 2);
    client.openBlockScreen(0, FARM_Y, 2, Until.screen(MOB_FARM_SCREEN_ID));
  }

  @AfterEach
  void clearDroppedItemsAndInventory() {
    command("kill @e[type=item]");
    client.clearInventory();
  }
}
