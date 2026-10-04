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

class HopperAutomationClientTest extends AutomationClientTestBase {

  private static final StageStyle FARMYARD =
      new StageStyle(
          "minecraft:grass_block",
          "minecraft:stripped_oak_log[axis=x]",
          "minecraft:stripped_oak_log[axis=z]",
          "minecraft:oak_fence",
          "minecraft:oak_fence",
          "minecraft:lantern",
          "minecraft:oak_sign");
  private static final int CHEST_X = 4;

  private static void decorateFarmyard() {
    fill(0, PLATFORM_Y, STAGE_MIN_Z + 1, 0, PLATFORM_Y, SCENE_Z - 2, "minecraft:dirt_path");
    fill(-3, PLATFORM_Y, SCENE_Z - 1, 7, PLATFORM_Y, SCENE_Z + 1, "minecraft:stone_bricks");
    fill(-6, GROUND_Y, STAGE_MAX_Z - 2, -5, GROUND_Y, STAGE_MAX_Z - 2, "minecraft:hay_block");
    setblock(-6, GROUND_Y + 1, STAGE_MAX_Z - 2, "minecraft:hay_block");
    setblock(-4, GROUND_Y, STAGE_MAX_Z - 2, "minecraft:composter[level=5]");
    setblock(4, GROUND_Y, STAGE_MAX_Z - 2, "minecraft:water_cauldron[level=3]");
    setblock(5, GROUND_Y, STAGE_MAX_Z - 2, "minecraft:barrel[facing=up]");
    setblock(6, GROUND_Y, STAGE_MAX_Z - 2, "minecraft:barrel[facing=north]");
    setblock(-7, GROUND_Y, STAGE_MAX_Z - 1, "minecraft:oak_leaves[persistent=true]");
    setblock(7, GROUND_Y, STAGE_MAX_Z - 1, "minecraft:oak_leaves[persistent=true]");
    setblock(-4, GROUND_Y, 2, "minecraft:poppy");
    setblock(-2, GROUND_Y, 3, "minecraft:dandelion");
    setblock(3, GROUND_Y, 2, "minecraft:cornflower");
    setblock(5, GROUND_Y, 3, "minecraft:oxeye_daisy");
    setblock(6, GROUND_Y, 1, "minecraft:azure_bluet");
  }

  @Test
  @DisplayName("Hopper chain collects the results of three farms in a chest")
  void hopperChainCollectsResultsOfThreeFarms() throws IOException {
    buildStage(FARMYARD, "Hopper Chain", "Farms to Chest");
    decorateFarmyard();
    fill(-2, GROUND_Y, SCENE_Z, CHEST_X - 1, GROUND_Y, SCENE_Z, "minecraft:hopper[facing=east]");
    setblock(CHEST_X, GROUND_Y, SCENE_Z, "minecraft:chest[facing=north]");
    setblock(CHEST_X + 1, GROUND_Y, SCENE_Z, "minecraft:comparator[facing=west]");
    setblock(CHEST_X + 2, GROUND_Y, SCENE_Z, "minecraft:redstone_lamp");
    placeWorkingFarm("animal_plains_farm", "chicken", -2, GROUND_Y + 1, SCENE_Z);
    placeWorkingFarm("animal_plains_farm", "cow", 0, GROUND_Y + 1, SCENE_Z);
    placeWorkingFarm("animal_plains_farm", "sheep", 2, GROUND_Y + 1, SCENE_Z);

    awaitAllResults(
        "automation_hopper_chain",
        0,
        GROUND_Y + 1,
        SCENE_Z,
        comparatorPowered(CHEST_X + 1, GROUND_Y, SCENE_Z));
    captureShowcase("automation_hopper_chain");
  }
}
