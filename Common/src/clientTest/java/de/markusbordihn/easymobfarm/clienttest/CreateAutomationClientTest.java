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
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnabledIfModLoaded("create")
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
    client.setBlock(5, GROUND_Y, STAGE_MAX_Z - 2, "create:fluid_tank");
    client.setBlock(-2, GROUND_Y, STAGE_MAX_Z - 1, "create:andesite_casing");
    client.setBlock(-2, GROUND_Y + 1, STAGE_MAX_Z - 1, "create:large_cogwheel[axis=z]");
    client.setBlock(2, GROUND_Y, STAGE_MAX_Z - 1, "create:brass_casing");
    client.setBlock(2, GROUND_Y + 1, STAGE_MAX_Z - 1, "create:cogwheel[axis=z]");
    client.setBlock(0, GROUND_Y, STAGE_MAX_Z - 1, "create:andesite_alloy_block");
    client.setBlock(-6, GROUND_Y, 2, "create:depot");
    client.setBlock(6, GROUND_Y, 2, "create:depot");
  }

  private static void connectBelt() {
    client.teleport(0.5, GROUND_Y, SCENE_Z - 2.5, 0f, 0f);
    client.equip("create:belt_connector");
    client.useOnBlock(BELT_START_X, GROUND_Y, SCENE_Z);
    client.await(Until.ticksElapsed(5));
    client.useOnBlock(BELT_END_X, GROUND_Y, SCENE_Z);
    client.await(Until.blockState(BELT_START_X, GROUND_Y, SCENE_Z, "create:belt"));
    client.clearInventory();
  }

  private static void placeArmUnloadingBeltIntoChest() {
    client.setBlock(BELT_END_X + 1, GROUND_Y, SCENE_Z, "create:andesite_casing");
    setBlockWithData(
      BELT_END_X + 1, GROUND_Y, SCENE_Z + 1, "create:creative_motor[facing=up]{ScrollValue:64}");
    client.setBlock(BELT_END_X + 1, ARM_Y, SCENE_Z + 1, "create:cogwheel[axis=y]");
    client.setBlock(ARM_CHEST_X, GROUND_Y, SCENE_Z, "minecraft:chest[facing=north]");
    client.setBlock(ARM_CHEST_X, ARM_Y, SCENE_Z, "create:andesite_funnel[facing=up]");
    client.setBlock(ARM_CHEST_X, GROUND_Y, SCENE_Z - 1, "minecraft:comparator[facing=south]");
    client.setBlock(ARM_CHEST_X, GROUND_Y, SCENE_Z - 2, "minecraft:redstone_lamp");
    setBlockWithData(
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
  void beltCarriesResultsOfThreeFarms() {
    buildStage(WORKSHOP, "Create", "Belt and Arm");
    decorateWorkshop();
    client.setBlock(BELT_START_X, GROUND_Y, SCENE_Z, "create:shaft[axis=z]");
    client.setBlock(BELT_END_X, GROUND_Y, SCENE_Z, "create:shaft[axis=z]");
    connectBelt();
    client.setBlock(BELT_START_X, GROUND_Y, SCENE_Z + 1, "create:creative_motor[facing=north]");
    for (int x = -2; x <= 2; x += 2) {
      client.setBlock(x, CHUTE_Y, SCENE_Z, "create:chute");
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
  void chutesDropResultsIntoChest() {
    buildStage(WORKSHOP, "Create", "Chute Tower");
    decorateWorkshop();
    client.setBlock(0, GROUND_Y, SCENE_Z, "minecraft:chest[facing=north]");
    fill(0, GROUND_Y + 1, SCENE_Z, 0, GROUND_Y + 2, SCENE_Z, "create:chute");
    client.setBlock(1, GROUND_Y, SCENE_Z, "minecraft:comparator[facing=west]");
    client.setBlock(2, GROUND_Y, SCENE_Z, "minecraft:redstone_lamp");
    fill(-1, GROUND_Y, SCENE_Z, -1, GROUND_Y + 2, SCENE_Z, "create:andesite_casing");
    client.setBlock(-1, GROUND_Y + 3, SCENE_Z, "minecraft:lantern");
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
