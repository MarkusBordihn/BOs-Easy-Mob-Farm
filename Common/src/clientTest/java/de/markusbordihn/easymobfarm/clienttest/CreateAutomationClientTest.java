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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreateAutomationClientTest extends AutomationClientTestBase {

  private static final StageStyle WORKSHOP =
      new StageStyle(
          "minecraft:polished_andesite",
          "create:andesite_casing",
          "create:andesite_casing",
          "create:industrial_iron_block",
          "create:andesite_pillar",
          "minecraft:lantern",
          "minecraft:spruce_sign");
  private static final int BELT_START_X = -3;
  private static final int BELT_END_X = 3;
  private static final int CHUTE_Y = GROUND_Y + 1;
  private static final int ARM_Y = GROUND_Y + 1;
  private static final int ARM_CHEST_X = BELT_END_X + 3;
  private static final int BELT_FARM_Y = GROUND_Y + 2;

  private static void decorateWorkshop() {
    fill(-6, GROUND_Y, STAGE_MAX_Z - 2, -5, GROUND_Y, STAGE_MAX_Z - 2, "create:item_vault[axis=x]");
    fill(6, GROUND_Y, STAGE_MAX_Z - 2, 6, GROUND_Y + 1, STAGE_MAX_Z - 2, "create:fluid_tank");
    setblock(5, GROUND_Y, STAGE_MAX_Z - 2, "create:fluid_tank");
    setblock(-2, GROUND_Y, STAGE_MAX_Z - 1, "create:andesite_casing");
    setblock(-2, GROUND_Y + 1, STAGE_MAX_Z - 1, "create:large_cogwheel[axis=z]");
    setblock(2, GROUND_Y, STAGE_MAX_Z - 1, "create:brass_casing");
    setblock(2, GROUND_Y + 1, STAGE_MAX_Z - 1, "create:cogwheel[axis=z]");
    setblock(0, GROUND_Y, STAGE_MAX_Z - 1, "create:andesite_alloy_block");
    setblock(-6, GROUND_Y, 2, "create:depot");
    setblock(6, GROUND_Y, 2, "create:depot");
  }

  private static void connectBelt() {
    command("tp @s 0.5 " + GROUND_Y + " " + (SCENE_Z - 2.5) + " 0 0");
    command("item replace entity @s weapon.mainhand with create:belt_connector");
    lookAt(BELT_START_X, GROUND_Y, SCENE_Z);
    useHeldItem();
    client.await(Until.ticksElapsed(5));
    lookAt(BELT_END_X, GROUND_Y, SCENE_Z);
    useHeldItem();
    client.await(Until.blockState(BELT_START_X, GROUND_Y, SCENE_Z, "create:belt"));
    command("item replace entity @s weapon.mainhand with minecraft:air");
  }

  private static void placeArmUnloadingBeltIntoChest() {
    setblock(BELT_END_X + 1, GROUND_Y, SCENE_Z, "create:andesite_casing");
    setblock(
        BELT_END_X + 1, GROUND_Y, SCENE_Z + 1, "create:creative_motor[facing=up]{ScrollValue:64}");
    setblock(BELT_END_X + 1, ARM_Y, SCENE_Z + 1, "create:cogwheel[axis=y]");
    setblock(ARM_CHEST_X, GROUND_Y, SCENE_Z, "minecraft:chest[facing=north]");
    setblock(ARM_CHEST_X, ARM_Y, SCENE_Z, "create:andesite_funnel[facing=up]");
    setblock(ARM_CHEST_X, GROUND_Y, SCENE_Z - 1, "minecraft:comparator[facing=south]");
    setblock(ARM_CHEST_X, GROUND_Y, SCENE_Z - 2, "minecraft:redstone_lamp");
    setblock(
        BELT_END_X + 1,
        ARM_Y,
        SCENE_Z,
        "create:mechanical_arm{InteractionPoints:["
            + armInteractionPoint("create:belt", -1, -1, "TAKE")
            + ","
            + armInteractionPoint("create:funnel", ARM_CHEST_X - BELT_END_X - 1, 0, "DEPOSIT")
            + "]}");
  }

  private static String armInteractionPoint(String type, int offsetX, int offsetY, String mode) {
    return "{Type:\""
        + type
        + "\",Pos:{X:"
        + offsetX
        + ",Y:"
        + offsetY
        + ",Z:0},Mode:\""
        + mode
        + "\"}";
  }

  @Test
  @DisplayName("Create mechanical arm moves belt results of three farms into a chest")
  void beltCarriesResultsOfThreeFarms() throws IOException {
    assumeRuntimeMod("Create");
    buildStage(WORKSHOP, "Create", "Belt and Arm");
    decorateWorkshop();
    setblock(BELT_START_X, GROUND_Y, SCENE_Z, "create:shaft[axis=z]");
    setblock(BELT_END_X, GROUND_Y, SCENE_Z, "create:shaft[axis=z]");
    connectBelt();
    setblock(BELT_START_X, GROUND_Y, SCENE_Z + 1, "create:creative_motor[facing=north]");
    for (int x = -2; x <= 2; x += 2) {
      setblock(x, CHUTE_Y, SCENE_Z, "create:chute");
    }
    placeArmUnloadingBeltIntoChest();
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
  void chutesDropResultsIntoChest() throws IOException {
    assumeRuntimeMod("Create");
    buildStage(WORKSHOP, "Create", "Chute Tower");
    decorateWorkshop();
    setblock(0, GROUND_Y, SCENE_Z, "minecraft:chest[facing=north]");
    fill(0, GROUND_Y + 1, SCENE_Z, 0, GROUND_Y + 2, SCENE_Z, "create:chute");
    setblock(1, GROUND_Y, SCENE_Z, "minecraft:comparator[facing=west]");
    setblock(2, GROUND_Y, SCENE_Z, "minecraft:redstone_lamp");
    fill(-1, GROUND_Y, SCENE_Z, -1, GROUND_Y + 2, SCENE_Z, "create:andesite_casing");
    setblock(-1, GROUND_Y + 3, SCENE_Z, "minecraft:lantern");
    placeWorkingFarm("animal_plains_farm", "chicken", 0, GROUND_Y + 3, SCENE_Z);

    awaitAllResults(
        "automation_create_chute",
        0,
        GROUND_Y + 3,
        SCENE_Z,
        comparatorPowered(1, GROUND_Y, SCENE_Z));
    captureShowcase("automation_create_chute");
  }
}
