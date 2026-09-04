/*
 * Copyright 2025 Markus Bordihn
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

package de.markusbordihn.easymobfarm.config;

import de.markusbordihn.easymobfarm.config.MobFarmBonusConfig.BonusDrop;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import java.util.List;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobFarmBonusConfigTest {

  private static final int[] EXPECTED_CHANCES = {20, 15, 10, 5};

  @BeforeAll
  static void bootstrap() {
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
  }

  private static void assertDefaultBonusDropsForAllTiers(
      MobFarmType mobFarmType, String entityId, Item expectedItem) {
    for (int tierLevel = 0; tierLevel < EXPECTED_CHANCES.length; tierLevel++) {
      List<BonusDrop> bonusDrops =
          MobFarmBonusConfig.getDefaultBonusDrops(mobFarmType.getId(), tierLevel, entityId);
      Assertions.assertEquals(1, bonusDrops.size(), mobFarmType + " tier " + tierLevel);
      Assertions.assertEquals(EXPECTED_CHANCES[tierLevel], bonusDrops.get(0).chance());
      Assertions.assertSame(expectedItem, bonusDrops.get(0).itemStack().item().value());
      Assertions.assertEquals(1, bonusDrops.get(0).itemStack().count());
    }
  }

  @Test
  @DisplayName("End Farm has bonus drops for its End mobs on every tier")
  void endFarmDefaults() {
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.END_FARM, "minecraft:enderman", Items.ENDER_PEARL);
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.END_FARM, "minecraft:shulker", Items.SHULKER_SHELL);
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.END_FARM, "minecraft:endermite", Items.CHORUS_FRUIT);
  }

  @Test
  @DisplayName("Nether Wastes Farm has bonus drops for its Nether mobs on every tier")
  void netherWastesFarmDefaults() {
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.NETHER_WASTES_FARM, "minecraft:piglin", Items.GOLD_NUGGET);
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.NETHER_WASTES_FARM, "minecraft:zombified_piglin", Items.GOLD_NUGGET);
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.NETHER_WASTES_FARM, "minecraft:hoglin", Items.LEATHER);
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.NETHER_WASTES_FARM, "minecraft:strider", Items.STRING);
    assertDefaultBonusDropsForAllTiers(
        MobFarmType.NETHER_WASTES_FARM, "minecraft:ghast", Items.GUNPOWDER);
  }

  @Test
  @DisplayName("Existing Swamp Farm defaults are unchanged")
  void swampFarmDefaultsUnchanged() {
    assertDefaultBonusDropsForAllTiers(MobFarmType.SWAMP_FARM, "minecraft:witch", Items.REDSTONE);
  }

  @Test
  @DisplayName("Unknown mob or tier yields no bonus drops")
  void unknownMobOrTierYieldsEmptyList() {
    Assertions.assertTrue(
        MobFarmBonusConfig.getDefaultBonusDrops(
                MobFarmType.END_FARM.getId(), 0, "minecraft:creeper")
            .isEmpty());
    Assertions.assertTrue(
        MobFarmBonusConfig.getDefaultBonusDrops(
                MobFarmType.NETHER_WASTES_FARM.getId(), 4, "minecraft:piglin")
            .isEmpty());
  }
}
