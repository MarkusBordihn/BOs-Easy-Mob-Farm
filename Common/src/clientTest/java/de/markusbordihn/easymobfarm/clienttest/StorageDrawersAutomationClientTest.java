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

@EnabledIfModLoaded("storagedrawers")
class StorageDrawersAutomationClientTest extends AutomationClientTestBase {

  private static final int WALL_TOP_Y = GROUND_Y + 2;

  @Test
  @DisplayName("Hopper fills a Storage Drawers wall with farm results")
  void hopperFillsDrawerWall() {
    placeStageAtSavedOrigin(STORAGE_DRAWERS_STAGE);
    placeWorkingFarm("animal_plains_farm", "chicken", 0, WALL_TOP_Y, SCENE_Z);

    awaitAllResults(
        "automation_storage_drawers",
        0,
        WALL_TOP_Y,
        SCENE_Z,
        comparatorPowered(0, GROUND_Y, SCENE_Z + 1));
    captureShowcase("automation_storage_drawers");
  }
}
