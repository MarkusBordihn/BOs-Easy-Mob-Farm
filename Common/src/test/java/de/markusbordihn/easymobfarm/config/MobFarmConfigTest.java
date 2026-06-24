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

package de.markusbordihn.easymobfarm.config;

import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.CreativeSpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LootEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LuckEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SpeedEnhancementItem;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MobFarmConfigTest {

  @BeforeEach
  void resetConfig() {
    MobFarmConfig.speedEnhancementUpgradeSpeed = 6;
    MobFarmConfig.lootEnhancementAdditionalRolls = 1;
    MobFarmConfig.luckEnhancementAdditionalLuck = 1.0f;
    MobFarmConfig.enableSpeedEnhancement = true;
    MobFarmConfig.enableLootEnhancement = true;
    MobFarmConfig.enableLuckEnhancement = true;
    MobFarmConfig.tier0MaxSpeedEnhancements = 4;
    MobFarmConfig.tier1MaxSpeedEnhancements = 4;
    MobFarmConfig.tier2MaxSpeedEnhancements = 4;
    MobFarmConfig.tier3MaxSpeedEnhancements = 4;
    MobFarmConfig.tier0MaxLootEnhancements = 4;
    MobFarmConfig.tier1MaxLootEnhancements = 4;
    MobFarmConfig.tier2MaxLootEnhancements = 4;
    MobFarmConfig.tier3MaxLootEnhancements = 4;
    MobFarmConfig.tier0MaxLuckEnhancements = 4;
    MobFarmConfig.tier1MaxLuckEnhancements = 4;
    MobFarmConfig.tier2MaxLuckEnhancements = 4;
    MobFarmConfig.tier3MaxLuckEnhancements = 4;
  }

  @Test
  void zeroPowerDisablesPowerBasedEnhancements() {
    MobFarmConfig.speedEnhancementUpgradeSpeed = 0;
    MobFarmConfig.lootEnhancementAdditionalRolls = 0;
    MobFarmConfig.luckEnhancementAdditionalLuck = 0.0f;

    Assertions.assertFalse(MobFarmConfig.isSpeedEnhancementEnabled());
    Assertions.assertFalse(MobFarmConfig.isLootEnhancementEnabled());
    Assertions.assertFalse(MobFarmConfig.isLuckEnhancementEnabled());
  }

  @Test
  void enableFlagsDisablePowerBasedEnhancements() {
    MobFarmConfig.enableSpeedEnhancement = false;
    MobFarmConfig.enableLootEnhancement = false;
    MobFarmConfig.enableLuckEnhancement = false;

    Assertions.assertFalse(MobFarmConfig.isSpeedEnhancementEnabled());
    Assertions.assertFalse(MobFarmConfig.isLootEnhancementEnabled());
    Assertions.assertFalse(MobFarmConfig.isLuckEnhancementEnabled());
  }

  @Test
  void tierSpecificDuplicateLimitsAreReturned() {
    MobFarmConfig.tier0MaxSpeedEnhancements = 1;
    MobFarmConfig.tier1MaxSpeedEnhancements = 2;
    MobFarmConfig.tier2MaxSpeedEnhancements = 3;
    MobFarmConfig.tier3MaxSpeedEnhancements = 4;

    Assertions.assertEquals(1, MobFarmConfig.getMaxSpeedEnhancements(0));
    Assertions.assertEquals(2, MobFarmConfig.getMaxSpeedEnhancements(1));
    Assertions.assertEquals(3, MobFarmConfig.getMaxSpeedEnhancements(2));
    Assertions.assertEquals(4, MobFarmConfig.getMaxSpeedEnhancements(3));
  }

  @Test
  void canAddEnhancementRejectsDuplicateOverTierLimit() {
    MobFarmConfig.tier0MaxSpeedEnhancements = 1;

    Assertions.assertFalse(
        MobFarmConfig.canAddEnhancementType(
            SpeedEnhancementItem.class, List.of(SpeedEnhancementItem.class), 0));
  }

  @Test
  void speedEnhancementLimitCountsCreativeSpeedEnhancement() {
    MobFarmConfig.tier0MaxSpeedEnhancements = 1;

    Assertions.assertFalse(
        MobFarmConfig.canAddEnhancementType(
            SpeedEnhancementItem.class, List.of(CreativeSpeedEnhancementItem.class), 0));
  }

  @Test
  void effectiveEnhancementsApplySeparateDuplicateLimits() {
    MobFarmConfig.tier0MaxSpeedEnhancements = 1;
    MobFarmConfig.tier0MaxLootEnhancements = 2;
    MobFarmConfig.tier0MaxLuckEnhancements = 1;

    List<Class<? extends EnhancementItem>> effectiveEnhancementTypes =
        MobFarmConfig.getEffectiveEnhancementTypes(
            List.of(
                SpeedEnhancementItem.class,
                SpeedEnhancementItem.class,
                LootEnhancementItem.class,
                LootEnhancementItem.class,
                LootEnhancementItem.class,
                LuckEnhancementItem.class,
                LuckEnhancementItem.class),
            0);

    Assertions.assertEquals(
        1, effectiveEnhancementTypes.stream().filter(SpeedEnhancementItem.class::equals).count());
    Assertions.assertEquals(
        2, effectiveEnhancementTypes.stream().filter(LootEnhancementItem.class::equals).count());
    Assertions.assertEquals(
        1, effectiveEnhancementTypes.stream().filter(LuckEnhancementItem.class::equals).count());
  }

  @Test
  void zeroDuplicateLimitBlocksPlacementAndEffect() {
    MobFarmConfig.tier0MaxSpeedEnhancements = 0;

    Assertions.assertFalse(
        MobFarmConfig.canAddEnhancementType(SpeedEnhancementItem.class, List.of(), 0));
    Assertions.assertTrue(
        MobFarmConfig.getEffectiveEnhancementTypes(List.of(SpeedEnhancementItem.class), 0)
            .isEmpty());
  }
}
