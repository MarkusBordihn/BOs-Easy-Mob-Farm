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
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.GameClientBuilder;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.Parameters;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.TestArea;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Timeout;

@Timeout(value = 2, unit = TimeUnit.MINUTES)
abstract class AutomationClientTestBase extends ClientTestBase {

  static final int GROUND_Y = PLATFORM_Y + 1;
  static final int SCENE_Z = 6;
  static final int STAGE_MIN_X = -8;
  static final int STAGE_MAX_X = 8;
  static final int STAGE_MIN_Z = 0;
  static final int STAGE_MAX_Z = 12;
  static final TestArea STAGE =
      TestArea.between(
          STAGE_MIN_X, PLATFORM_Y, STAGE_MIN_Z, STAGE_MAX_X, GROUND_Y + 3, STAGE_MAX_Z);
  static final String CREATE_BELT_STAGE = "create_belt";
  static final String CREATE_CHUTE_STAGE = "create_chute";
  static final String APPLIED_ENERGISTICS_STAGE = "applied_energistics";
  static final String SOPHISTICATED_STORAGE_STAGE = "sophisticated_storage";
  static final String STORAGE_DRAWERS_STAGE = "storage_drawers";
  private static final String SPEED_ENHANCEMENT = MOD_NAMESPACE + "creative_speed_enhancement";
  private static final int CAPTURED_MOB_SLOT = 0;
  private static final int FIRST_ENHANCEMENT_SLOT = 4;
  private static final int EFFECTIVE_SPEED_ENHANCEMENTS = 4;
  private static final Duration FIRST_OUTPUT_TIMEOUT = Duration.ofSeconds(90);
  private static final int SHOWCASE_SETTLE_TICKS = 40;
  private static final int VANTAGE_Y = GROUND_Y + 3;
  private static final int VANTAGE_Z = STAGE_MIN_Z - 4;
  private static final double PLAYER_EYE_HEIGHT = 1.62;
  private static final float VANTAGE_PITCH =
      (float)
          Math.toDegrees(
              Math.atan2(VANTAGE_Y + PLAYER_EYE_HEIGHT - (GROUND_Y + 0.5), SCENE_Z - VANTAGE_Z));

  static void placeStageAtSavedOrigin(String stageName) {
    standAtVantage();
    Path stageFile =
        Path.of(
            System.getProperty(GameClientBuilder.PROJECT_DIRECTORY_PROPERTY, "."),
            "Common",
            "src",
            "clientTest",
            "resources",
            "structures",
            stageName + ".nbt");
    client.placeStructure(stageFile, STAGE.minX(), STAGE.minY(), STAGE.minZ());
  }

  static void placeWorkingFarm(String farmId, String mobName, int x, int y, int z) {
    client.setBlock(x, y, z, MOD_NAMESPACE + farmId);
    for (int i = 0; i < EFFECTIVE_SPEED_ENHANCEMENTS; i++) {
      fillContainerSlot(x, y, z, FIRST_ENHANCEMENT_SLOT + i, SPEED_ENHANCEMENT);
    }
    fillContainerSlot(x, y, z, CAPTURED_MOB_SLOT, mobCaptureCard(mobName));
  }

  static Until comparatorPowered(int x, int y, int z) {
    return Until.blockState(x, y, z, "minecraft:comparator", Map.of("powered", "true"));
  }

  static void awaitAllResults(
      String screenshotLabel, int farmX, int farmY, int farmZ, Until... arrivals) {
    try {
      client.await(FIRST_OUTPUT_TIMEOUT, arrivals);
    } catch (ConditionNotMetException e) {
      captureFailure(screenshotLabel, farmX, farmY, farmZ);
      throw e;
    }
  }

  static void captureShowcase(String label) {
    standAtVantage();
    client.renderSettings(Parameters.of().with("hand", false).with("crosshair", false));
    client.await(Until.ticksElapsed(SHOWCASE_SETTLE_TICKS));
    client.saveScreenshot(label);
  }

  static void standAtVantage() {
    client.setBlock(0, VANTAGE_Y - 1, VANTAGE_Z, "minecraft:barrier");
    client.teleport(0.5, VANTAGE_Y, VANTAGE_Z + 0.5, 0f, VANTAGE_PITCH);
  }

  private static void captureFailure(String screenshotLabel, int farmX, int farmY, int farmZ) {
    captureShowcase(screenshotLabel + "_failed");
    client.teleport(farmX + 0.5, GROUND_Y, farmZ - 2.5);
    client.openBlockScreen(farmX, farmY, farmZ, Until.screen(MOB_FARM_SCREEN_ID));
    client.saveScreenshot(screenshotLabel + "_failed_farm");
  }

  private static void fillContainerSlot(int x, int y, int z, int slot, String item) {
    command("item replace block " + x + " " + y + " " + z + " container." + slot + " with " + item);
  }

  private static String mobCaptureCard(String mobName) {
    return MOD_NAMESPACE
        + "mob_capture_card{MobCaptureData:{Name:\"entity.minecraft."
        + mobName
        + "\",Type:\"minecraft:"
        + mobName
        + "\",Rarity:\"COMMON\",Foil:0b},CardDataVersion:2}";
  }
}
