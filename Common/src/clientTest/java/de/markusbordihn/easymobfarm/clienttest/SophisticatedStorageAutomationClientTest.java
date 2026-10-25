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

@EnabledIfModLoaded("sophisticatedstorage")
class SophisticatedStorageAutomationClientTest extends AutomationClientTestBase {

  private static final int BARREL_SPACING = 3;

  @Test
  @DisplayName("Sophisticated Storage hopper upgrade pulls results from three farm types")
  void hopperUpgradePullsResultsFromThreeFarmTypes() {
    placeStageAtSavedOrigin(SOPHISTICATED_STORAGE_STAGE);
    placeWorkingFarm("animal_plains_farm", "chicken", -BARREL_SPACING, GROUND_Y + 1, SCENE_Z);
    placeWorkingFarm("ocean_farm", "salmon", 0, GROUND_Y + 1, SCENE_Z);
    placeWorkingFarm("iron_golem_farm", "iron_golem", BARREL_SPACING, GROUND_Y + 1, SCENE_Z);

    awaitAllResults(
        "automation_sophisticated_storage",
        0,
        GROUND_Y + 1,
        SCENE_Z,
        comparatorPowered(-BARREL_SPACING, GROUND_Y, SCENE_Z + 1),
        comparatorPowered(0, GROUND_Y, SCENE_Z + 1),
        comparatorPowered(BARREL_SPACING, GROUND_Y, SCENE_Z + 1));
    captureShowcase("automation_sophisticated_storage");
  }
}
