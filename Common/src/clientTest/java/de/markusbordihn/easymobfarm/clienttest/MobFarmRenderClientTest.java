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

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobFarmRenderClientTest extends ClientTestBase {

  private static final List<String> FARM_IDS =
      List.of(
          "animal_plains_farm",
          "bee_hive_farm",
          "creative_mob_farm",
          "desert_farm",
          "end_farm",
          "iron_golem_farm",
          "jungle_farm",
          "lucky_drop_farm",
          "monster_plains_cave_farm",
          "nether_fortress_farm",
          "nether_wastes_farm",
          "ocean_farm",
          "swamp_farm");
  private static final int FARM_ROW_Z = 6;
  private static final int VIEW_PITCH = 20;
  private static final long RENDER_TICKS = 40;

  @Test
  @DisplayName("All mob farm blocks render in view without crashing the client")
  void mobFarmBlocksRenderInView() throws IOException {
    enterTestArea(VIEW_PITCH);
    int firstFarmX = -FARM_IDS.size() / 2;
    for (int i = 0; i < FARM_IDS.size(); i++) {
      placeFarm(FARM_IDS.get(i), firstFarmX + i, FARM_ROW_Z);
    }

    client.await(Until.ticksElapsed(RENDER_TICKS), Until.worldLoaded(true));
    captureScreen("mob_farms");
  }
}
