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

class StorageDrawersAutomationClientTest extends AutomationClientTestBase {

  private static final StageStyle STOREROOM =
      new StageStyle(
          "minecraft:spruce_planks",
          "storagedrawers:dark_oak_trim",
          "storagedrawers:dark_oak_trim",
          "minecraft:bookshelf",
          "minecraft:spruce_fence",
          "minecraft:lantern",
          "minecraft:birch_sign");
  private static final String DRAWERS_WITH_REDSTONE_UPGRADE =
      "storagedrawers:oak_full_drawers_4[facing=north]{Upgrades:"
          + "[{Slot:0b,id:\"storagedrawers:redstone_upgrade\",Count:1b}]}";
  private static final int WALL_HALF_WIDTH = 3;
  private static final int WALL_TOP_Y = GROUND_Y + 2;

  private static void buildDrawerWall() {
    fill(
        -WALL_HALF_WIDTH,
        GROUND_Y,
        SCENE_Z,
        WALL_HALF_WIDTH,
        GROUND_Y,
        SCENE_Z,
        "storagedrawers:oak_full_drawers_2[facing=north]");
    fill(
        -WALL_HALF_WIDTH,
        GROUND_Y + 1,
        SCENE_Z,
        WALL_HALF_WIDTH,
        GROUND_Y + 1,
        SCENE_Z,
        "storagedrawers:spruce_full_drawers_4[facing=north]");
    fill(
        -WALL_HALF_WIDTH,
        WALL_TOP_Y,
        SCENE_Z,
        WALL_HALF_WIDTH,
        WALL_TOP_Y,
        SCENE_Z,
        "storagedrawers:oak_trim");
    setblock(
        -WALL_HALF_WIDTH, GROUND_Y, SCENE_Z, "storagedrawers:compacting_drawers_3[facing=north]");
    setblock(WALL_HALF_WIDTH, GROUND_Y, SCENE_Z, "storagedrawers:oak_full_drawers_1[facing=north]");
    setblock(-WALL_HALF_WIDTH - 1, GROUND_Y, SCENE_Z, "storagedrawers:controller[facing=north]");
    setblock(WALL_HALF_WIDTH + 1, GROUND_Y, SCENE_Z, "storagedrawers:controller_io");
    setblock(-WALL_HALF_WIDTH - 1, GROUND_Y + 1, SCENE_Z, "minecraft:lantern");
    setblock(WALL_HALF_WIDTH + 1, GROUND_Y + 1, SCENE_Z, "minecraft:lantern");
  }

  private static void decorateStoreroom() {
    fill(
        -6,
        GROUND_Y,
        STAGE_MAX_Z - 2,
        -4,
        GROUND_Y,
        STAGE_MAX_Z - 2,
        "minecraft:barrel[facing=up]");
    setblock(-5, GROUND_Y + 1, STAGE_MAX_Z - 2, "minecraft:barrel[facing=up]");
    setblock(4, GROUND_Y, STAGE_MAX_Z - 2, "minecraft:crafting_table");
    setblock(5, GROUND_Y, STAGE_MAX_Z - 2, "storagedrawers:spruce_half_drawers_2[facing=north]");
    setblock(6, GROUND_Y, STAGE_MAX_Z - 2, "storagedrawers:spruce_half_drawers_1[facing=north]");
    setblock(-6, GROUND_Y, 2, "minecraft:potted_fern");
    setblock(6, GROUND_Y, 2, "minecraft:potted_fern");
  }

  @Test
  @DisplayName("Hopper fills a Storage Drawers wall with farm results")
  void hopperFillsDrawerWall() throws IOException {
    assumeRuntimeMod("Storage Drawers");
    buildStage(STOREROOM, "Storage Drawers", "Hopper to Drawer");
    decorateStoreroom();
    buildDrawerWall();
    setblock(0, GROUND_Y, SCENE_Z, DRAWERS_WITH_REDSTONE_UPGRADE);
    setblock(0, GROUND_Y + 1, SCENE_Z, "minecraft:hopper[facing=down]");
    setblock(0, GROUND_Y, SCENE_Z + 1, "minecraft:comparator[facing=north]");
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
