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

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.EnabledIfModLoaded;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnabledIfModLoaded("create")
class CreateAutomationClientTest extends AutomationClientTestBase {

  private static final int BELT_FARM_Y = GROUND_Y + 2;
  private static final int ARM_CHEST_X = 6;
  private static final int CHUTE_FARM_Y = GROUND_Y + 3;

  @Test
  @DisplayName("Create mechanical arm moves belt results of three farms into a chest")
  void beltCarriesResultsOfThreeFarms() {
    placeStageAtSavedOrigin(CREATE_BELT_STAGE);
    placeWorkingFarm("animal_plains_farm", "chicken", -2, BELT_FARM_Y, SCENE_Z);
    placeWorkingFarm("animal_plains_farm", "cow", 0, BELT_FARM_Y, SCENE_Z);
    placeWorkingFarm("animal_plains_farm", "sheep", 2, BELT_FARM_Y, SCENE_Z);

    awaitAllResults(
        "automation_create_belt",
        0,
        BELT_FARM_Y,
        SCENE_Z,
        comparatorPowered(ARM_CHEST_X, GROUND_Y, SCENE_Z - 1));
    captureShowcase("automation_create_belt");
  }

  @Test
  @DisplayName("Create chutes drop farm results into a chest")
  void chutesDropResultsIntoChest() {
    placeStageAtSavedOrigin(CREATE_CHUTE_STAGE);
    placeWorkingFarm("animal_plains_farm", "chicken", 0, CHUTE_FARM_Y, SCENE_Z);

    awaitAllResults(
        "automation_create_chute",
        0,
        CHUTE_FARM_Y,
        SCENE_Z,
        comparatorPowered(1, GROUND_Y, SCENE_Z));
    captureShowcase("automation_create_chute");
  }
}
