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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SophisticatedStorageAutomationClientTest extends AutomationClientTestBase {

  private static final StageStyle WAREHOUSE =
      new StageStyle(
          "minecraft:dark_oak_planks",
          "minecraft:stripped_dark_oak_log[axis=x]",
          "minecraft:stripped_dark_oak_log[axis=z]",
          "minecraft:barrel[facing=north]",
          "minecraft:dark_oak_fence",
          "minecraft:lantern",
          "minecraft:birch_sign");
  // Every field of the stored contents is required, so an upgrade-only setblock NBT is discarded.
  private static final String UPGRADE_STACKS_WITH_HOPPER_UPGRADE =
      "storageWrapper.contents.upgrades.stacks set value "
          + "[{id:\"sophisticatedstorage:hopper_upgrade\",count:1}]";
  private static final int BARREL_SPACING = 3;

  private static void decorateWarehouse() {
    fill(-4, PLATFORM_Y, SCENE_Z - 1, 4, PLATFORM_Y, SCENE_Z + 1, "minecraft:polished_deepslate");
    setblock(-6, GROUND_Y, STAGE_MAX_Z - 2, "sophisticatedstorage:chest");
    setblock(-5, GROUND_Y, STAGE_MAX_Z - 2, "sophisticatedstorage:gold_barrel");
    setblock(5, GROUND_Y, STAGE_MAX_Z - 2, "sophisticatedstorage:limited_barrel_4");
    setblock(6, GROUND_Y, STAGE_MAX_Z - 2, "sophisticatedstorage:iron_barrel");
    setblock(6, GROUND_Y + 1, STAGE_MAX_Z - 2, "sophisticatedstorage:barrel");
    setblock(-6, GROUND_Y, 2, "minecraft:potted_azalea_bush");
    setblock(6, GROUND_Y, 2, "minecraft:potted_azalea_bush");
  }

  private static void placeBarrelFarm(String farmId, String mobName, int x) {
    setblock(x, GROUND_Y, SCENE_Z, "sophisticatedstorage:barrel[ticking=true]");
    command(
        "data modify block " + x + " " + GROUND_Y + " " + SCENE_Z + " "
            + UPGRADE_STACKS_WITH_HOPPER_UPGRADE);
    setblock(x, GROUND_Y, SCENE_Z + 1, "minecraft:comparator[facing=north]");
    placeWorkingFarm(farmId, mobName, x, GROUND_Y + 1, SCENE_Z);
  }

  @Test
  @DisplayName("Sophisticated Storage hopper upgrade pulls results from three farm types")
  void hopperUpgradePullsResultsFromThreeFarmTypes() throws IOException {
    assumeRuntimeMod("Sophisticated Storage");
    buildStage(WAREHOUSE, "Sophisticated", "Hopper Upgrade");
    decorateWarehouse();
    placeBarrelFarm("animal_plains_farm", "chicken", -BARREL_SPACING);
    placeBarrelFarm("ocean_farm", "salmon", 0);
    placeBarrelFarm("iron_golem_farm", "iron_golem", BARREL_SPACING);

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
