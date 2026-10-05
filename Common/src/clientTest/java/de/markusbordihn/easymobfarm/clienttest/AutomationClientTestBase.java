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

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.ConditionNotMetException;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.Parameters;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assumptions;

abstract class AutomationClientTestBase extends ClientTestBase {

  static final int GROUND_Y = PLATFORM_Y + 1;
  static final int SCENE_Z = 6;
  static final int STAGE_MIN_X = -8;
  static final int STAGE_MAX_X = 8;
  static final int STAGE_MIN_Z = 0;
  static final int STAGE_MAX_Z = 12;
  private static final String SPEED_ENHANCEMENT = MOD_NAMESPACE + "creative_speed_enhancement";
  private static final int CAPTURED_MOB_SLOT = 0;
  private static final int FIRST_ENHANCEMENT_SLOT = 4;
  private static final int EFFECTIVE_SPEED_ENHANCEMENTS = 4;
  private static final Duration FIRST_OUTPUT_TIMEOUT = Duration.ofSeconds(90);
  private static final int SHOWCASE_SETTLE_TICKS = 40;
  private static final int VANTAGE_Y = GROUND_Y + 3;
  private static final int VANTAGE_Z = STAGE_MIN_Z - 4;

  static void assumeRuntimeMod(String modName, String runtimeModKey) {
    String loader = System.getProperty("clientruntime.loader");
    List<String> runtimeMods =
        List.of(System.getProperty("clientruntime.runtimeMods", "").split(","));
    Assumptions.assumeTrue(
        runtimeMods.contains(runtimeModKey),
        modName + " has no " + loader + "_" + runtimeModKey + "_mod version for this runtime");
  }

  static void setblock(int x, int y, int z, String block) {
    command("setblock " + x + " " + y + " " + z + " " + block);
  }

  static void fill(int fromX, int fromY, int fromZ, int toX, int toY, int toZ, String block) {
    command(
        "fill " + fromX + " " + fromY + " " + fromZ + " " + toX + " " + toY + " " + toZ + " "
            + block);
  }

  static void buildStage(StageStyle style, String title, String subtitle) {
    standAtVantage();
    fill(STAGE_MIN_X, PLATFORM_Y, STAGE_MIN_Z, STAGE_MAX_X, PLATFORM_Y, STAGE_MAX_Z, style.floor());
    fill(
        STAGE_MIN_X,
        PLATFORM_Y,
        STAGE_MIN_Z,
        STAGE_MAX_X,
        PLATFORM_Y,
        STAGE_MIN_Z,
        style.borderAlongX());
    fill(
        STAGE_MIN_X,
        PLATFORM_Y,
        STAGE_MAX_Z,
        STAGE_MAX_X,
        PLATFORM_Y,
        STAGE_MAX_Z,
        style.borderAlongX());
    fill(
        STAGE_MIN_X,
        PLATFORM_Y,
        STAGE_MIN_Z + 1,
        STAGE_MIN_X,
        PLATFORM_Y,
        STAGE_MAX_Z - 1,
        style.borderAlongZ());
    fill(
        STAGE_MAX_X,
        PLATFORM_Y,
        STAGE_MIN_Z + 1,
        STAGE_MAX_X,
        PLATFORM_Y,
        STAGE_MAX_Z - 1,
        style.borderAlongZ());
    fill(
        STAGE_MIN_X + 1,
        GROUND_Y,
        STAGE_MAX_Z,
        STAGE_MAX_X - 1,
        GROUND_Y,
        STAGE_MAX_Z,
        style.backdrop());
    for (int x : new int[] {STAGE_MIN_X, STAGE_MAX_X}) {
      for (int z : new int[] {STAGE_MIN_Z, STAGE_MAX_Z}) {
        fill(x, GROUND_Y, z, x, GROUND_Y + 1, z, style.post());
        setblock(x, GROUND_Y + 2, z, style.light());
      }
    }
    setblock(
        STAGE_MIN_X + 2,
        GROUND_Y,
        STAGE_MIN_Z + 1,
        style.sign()
            + "[rotation=8]{front_text:{messages:['\"\"','\""
            + title
            + "\"','\""
            + subtitle
            + "\"','\"\"']}}");
  }

  static void placeWorkingFarm(String farmId, String mobName, int x, int y, int z) {
    setblock(x, y, z, MOD_NAMESPACE + farmId);
    for (int i = 0; i < EFFECTIVE_SPEED_ENHANCEMENTS; i++) {
      fillContainerSlot(x, y, z, FIRST_ENHANCEMENT_SLOT + i, SPEED_ENHANCEMENT);
    }
    fillContainerSlot(x, y, z, CAPTURED_MOB_SLOT, mobCaptureCard(mobName));
  }

  static Until comparatorPowered(int x, int y, int z) {
    return Until.blockState(x, y, z, "minecraft:comparator", Map.of("powered", "true"));
  }

  static void awaitAllResults(
      String screenshotLabel, int farmX, int farmY, int farmZ, Until... arrivals)
      throws IOException {
    try {
      client.await(FIRST_OUTPUT_TIMEOUT, arrivals);
    } catch (ConditionNotMetException e) {
      captureFailure(screenshotLabel, farmX, farmY, farmZ);
      throw e;
    }
  }

  static void captureShowcase(String label) throws IOException {
    standAtVantage();
    client.api().render().settingsSet(Parameters.of().with("hand", false).with("crosshair", false));
    try {
      client.await(Until.ticksElapsed(SHOWCASE_SETTLE_TICKS));
      captureScreen(label);
    } finally {
      client.api().render().settingsSet(Parameters.of().with("hand", true).with("crosshair", true));
    }
  }

  private static void standAtVantage() {
    setblock(0, VANTAGE_Y - 1, VANTAGE_Z, "minecraft:barrier");
    command(
        "tp @s 0.5 "
            + VANTAGE_Y
            + " "
            + (VANTAGE_Z + 0.5)
            + " facing 0.5 "
            + (GROUND_Y + 0.5)
            + " "
            + (SCENE_Z + 0.5));
  }

  private static void captureFailure(String screenshotLabel, int farmX, int farmY, int farmZ)
      throws IOException {
    captureShowcase(screenshotLabel + "_failed");
    command("tp @s " + (farmX + 0.5) + " " + GROUND_Y + " " + (farmZ - 2.5) + " 0 0");
    lookAt(farmX, farmY, farmZ);
    useHeldItem();
    client.await(Until.screen(MOB_FARM_SCREEN_ID));
    captureScreen(screenshotLabel + "_failed_farm");
    client.closeScreen();
  }

  private static void fillContainerSlot(int x, int y, int z, int slot, String item) {
    command("item replace block " + x + " " + y + " " + z + " container." + slot + " with " + item);
  }

  private static String mobCaptureCard(String mobName) {
    return MOD_NAMESPACE
        + "mob_capture_card[easy_mob_farm:mob_capture_data={name:\"entity.minecraft."
        + mobName
        + "\",type:\"minecraft:"
        + mobName
        + "\",entityType:\"minecraft:"
        + mobName
        + "\"}]";
  }

  record StageStyle(
      String floor,
      String borderAlongX,
      String borderAlongZ,
      String backdrop,
      String post,
      String light,
      String sign) {}
}
