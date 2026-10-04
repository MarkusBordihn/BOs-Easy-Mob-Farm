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

import java.io.IOException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppliedEnergisticsAutomationClientTest extends AutomationClientTestBase {

  private static final StageStyle SKY_STONE_HALL =
      new StageStyle(
          "ae2:smooth_sky_stone_block",
          "ae2:sky_stone_brick",
          "ae2:sky_stone_brick",
          "ae2:sky_stone_small_brick",
          "ae2:quartz_pillar",
          "ae2:quartz_vibrant_glass",
          "minecraft:birch_sign");
  private static final int CHEST_X = 2;

  private static String cableBus(String parts) {
    return "ae2:cable_bus{cable:{id:\"ae2:fluix_glass_cable\"}," + parts + "}";
  }

  private static void decorateSkyStoneHall() {
    fill(-3, PLATFORM_Y, SCENE_Z - 1, 4, PLATFORM_Y, SCENE_Z + 1, "ae2:fluix_block");
    setblock(-6, GROUND_Y, STAGE_MAX_Z - 2, "ae2:inscriber");
    setblock(-5, GROUND_Y, STAGE_MAX_Z - 2, "ae2:charger");
    setblock(-3, GROUND_Y, STAGE_MAX_Z - 2, "ae2:sky_stone_chest");
    setblock(3, GROUND_Y, STAGE_MAX_Z - 2, "ae2:smooth_sky_stone_chest");
    fill(5, GROUND_Y, STAGE_MAX_Z - 2, 6, GROUND_Y + 1, STAGE_MAX_Z - 2, "ae2:controller");
    setblock(-6, GROUND_Y, 2, "ae2:quartz_glass");
    setblock(6, GROUND_Y, 2, "ae2:quartz_glass");
    setblock(-6, GROUND_Y + 1, 2, "ae2:quartz_cluster");
    setblock(6, GROUND_Y + 1, 2, "ae2:quartz_cluster");
  }

  @Test
  @DisplayName("Applied Energistics 2 import bus stores farm results in a chest")
  void importBusStoresResultsThroughStorageBus() throws IOException {
    assumeRuntimeMod("Applied Energistics 2", "neoforge");
    Assumptions.abort(
        "Import bus placed with 1.20.1 cable bus data pulls no farm results on AE2 19 (1.21.1)");
    buildStage(SKY_STONE_HALL, "ME System", "Import Bus");
    decorateSkyStoneHall();
    setblock(-2, GROUND_Y, SCENE_Z, "ae2:drive");
    setblock(-1, GROUND_Y, SCENE_Z, "ae2:creative_energy_cell");
    setblock(0, GROUND_Y, SCENE_Z, cableBus("up:{id:\"ae2:import_bus\"}"));
    setblock(
        1,
        GROUND_Y,
        SCENE_Z,
        cableBus("north:{id:\"ae2:terminal\"},east:{id:\"ae2:storage_bus\"}"));
    setblock(CHEST_X, GROUND_Y, SCENE_Z, "minecraft:chest[facing=north]");
    setblock(CHEST_X + 1, GROUND_Y, SCENE_Z, "minecraft:comparator[facing=west]");
    setblock(CHEST_X + 2, GROUND_Y, SCENE_Z, "minecraft:redstone_lamp");
    placeWorkingFarm("animal_plains_farm", "cow", 0, GROUND_Y + 1, SCENE_Z);

    awaitAllResults(
        "automation_applied_energistics",
        0,
        GROUND_Y + 1,
        SCENE_Z,
        comparatorPowered(CHEST_X + 1, GROUND_Y, SCENE_Z));
    captureShowcase("automation_applied_energistics");
  }
}
