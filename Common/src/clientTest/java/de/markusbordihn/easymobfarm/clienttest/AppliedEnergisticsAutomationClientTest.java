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

@EnabledIfModLoaded("ae2")
class AppliedEnergisticsAutomationClientTest extends AutomationClientTestBase {

  private static final int COMPARATOR_X = 3;

  @Test
  @DisplayName("Applied Energistics 2 import bus stores farm results in a chest")
  void importBusStoresResultsThroughStorageBus() {
    placeStageAtSavedOrigin(APPLIED_ENERGISTICS_STAGE);
    placeWorkingFarm("animal_plains_farm", "cow", 0, GROUND_Y + 1, SCENE_Z);

    awaitAllResults(
        "automation_applied_energistics",
        0,
        GROUND_Y + 1,
        SCENE_Z,
        comparatorPowered(COMPARATOR_X, GROUND_Y, SCENE_Z));
    captureShowcase("automation_applied_energistics");
  }
}
