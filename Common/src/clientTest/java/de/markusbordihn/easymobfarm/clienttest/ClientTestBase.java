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
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
  private static final int MAXIMUM_SESSION_LABEL_LENGTH = 64;
  private static final String LAYOUT_REPORT_FILE_NAME = "layout-issues.txt";
  private static final Duration WORLD_TIMEOUT = Duration.ofMinutes(3);
  private static final long NOON = 6000L;
  private static final String TEST_AREA =
      "-10 " + (PLATFORM_Y - 1) + " -6 10 " + (PLATFORM_Y + 8) + " 14";
  private static final String GAME_DIRECTORY_PROPERTY = "clientruntime.gameDirectory";
  private static final Path CONFIGURATION_DIRECTORY = Path.of("config", "easy_mob_farm");
  private static final Map<String, List<String>> TEST_CONFIGURATION =
      Map.of(
          "mob_capture_card.cfg",
          List.of(
              "mobCaptureCardKillDropChance=1.0",
              "mobCaptureCardFishingDropChance=1.0",
              "mobCaptureCardFoilDropChance=0.0"),
          "mob_farm.cfg",
          List.of("farmProgressingTime=1000"));

  // GameClientExtension.shared invokes configureLaunch during static initialization.
  @RegisterExtension
  static final GameClientExtension client =
      GameClientExtension.shared(ClientTestBase::configureLaunch);

  private static GameClientBuilder configureLaunch(GameClientBuilder builder) {
    writeTestConfiguration();
    return builder
        .withSuiteName(SUITE_NAME)
        .withWindowSize(1280, 720)
        .withGuiScale(2)
        .withConfiguration("command_allowlist", "setblock,fill,tp,gamerule,item,kill,summon,clear");
  }

  private static void writeTestConfiguration() {
    String gameDirectory = System.getProperty(GAME_DIRECTORY_PROPERTY, "");
    if (gameDirectory.isBlank()) {
      return;
    }

    Path configurationDirectory = Path.of(gameDirectory).resolve(CONFIGURATION_DIRECTORY);
    try {
      Files.createDirectories(configurationDirectory);
      for (Map.Entry<String, List<String>> configurationFile : TEST_CONFIGURATION.entrySet()) {
        Files.write(
            configurationDirectory.resolve(configurationFile.getKey()),
            configurationFile.getValue(),
            StandardCharsets.UTF_8);
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @BeforeAll
  static void loadWorld() {
    if (!client.checkState(Until.worldLoaded(true)).passed()) {
      client.await(WORLD_TIMEOUT, Until.resourcesLoaded());
      client.api().world().create(Parameters.of("name", "Easy Mob Farm Client Test"));
    }
    client.await(WORLD_TIMEOUT, Until.worldLoaded(true), Until.playerAvailable(true));
    command("gamerule sendCommandFeedback false");
  }

  static void command(String command) {
    client.api().command().run(Parameters.of("command", command));
  }

  static void enterTestArea(int pitch) {
    command("fill -7 " + PLATFORM_Y + " -2 7 " + PLATFORM_Y + " 8 minecraft:stone");
    command("tp @s 0.5 " + FARM_Y + " 0.5 0 " + pitch);
  }

  static void placeFarm(String farmId, int x, int z) {
    command("setblock " + x + " " + FARM_Y + " " + z + " " + MOD_NAMESPACE + farmId);
  }

  static void openFarm(String farmId) {
    enterTestArea(0);
    placeFarm(farmId, 0, 2);
    lookAt(0, FARM_Y, 2);
    useHeldItem();
    client.await(Until.screen(MOB_FARM_SCREEN_ID));
  }

  static void lookAt(int x, int y, int z) {
    client
        .api()
        .camera()
        .look(
            Parameters.of().with("lookAt", Parameters.of().with("x", x).with("y", y).with("z", z)));
    client.await(Until.targetBlockAt(x, y, z));
  }

  static void useHeldItem() {
    client.api().input().mouseButton(Parameters.of("button", "right"));
  }

  static void captureScreen(String label) throws IOException {
    Path screenshot = client.saveScreenshot(label);
    List<String> layoutIssues =
        client
            .checkState(
                Until.layoutClean(LAYOUT_CHECKS_IGNORING_ICON_BUTTON_TEXT_OVERFLOW, List.of()))
            .failures()
            .stream()
            .flatMap(outcome -> outcome.actual().lines())
            .toList();
    if (layoutIssues.isEmpty()) {
      return;
    }

    StringBuilder report =
        new StringBuilder(client.screenshotDirectory().relativize(screenshot) + "\n");
    layoutIssues.forEach(layoutIssue -> report.append("  ").append(layoutIssue).append('\n'));
    Files.writeString(
        client.screenshotDirectory().resolve(LAYOUT_REPORT_FILE_NAME),
        report,
        StandardCharsets.UTF_8,
        StandardOpenOption.CREATE,
        StandardOpenOption.APPEND);
  }

  @BeforeEach
  void labelSessionWithTest() {
    String title = client.currentTest().title();
    client
        .api()
        .session()
        .label(
            Parameters.of(
                "name",
                title.substring(0, Math.min(title.length(), MAXIMUM_SESSION_LABEL_LENGTH))));
  }

  @BeforeEach
  void renderWithoutHudAtNoon() {
    client
        .api()
        .render()
        .profile(
            Parameters.of("preset", "deterministic")
                .with("dayTime", NOON)
                .with("weather", "clear")
                .with("hud", "hidden"));
  }

  @AfterEach
  void clearTestArea() {
    if (!Until.NO_SCREEN_ID.equals(client.screenId())) {
      client.closeScreen();
    }
    command("fill " + TEST_AREA + " minecraft:air");
    command("kill @e[type=item]");
    command("item replace entity @s weapon.mainhand with minecraft:air");
  }
}
