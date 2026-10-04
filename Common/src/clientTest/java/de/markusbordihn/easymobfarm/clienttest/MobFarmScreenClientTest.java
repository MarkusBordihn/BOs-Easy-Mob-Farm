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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.By;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MobFarmScreenClientTest extends ClientTestBase {

  private static final String TOOLTIP_FARM_PREFIX = "tooltip.easy_mob_farm.farm.";
  private static final List<String> REDSTONE_MODES_IN_CYCLE_ORDER =
      List.of("disable_on_signal", "enable_on_signal", "ignore");
  private static final By THEME_BUTTON = By.translationKey(TOOLTIP_FARM_PREFIX + "theme");
  private static final int MAXIMUM_THEME_CLICKS = 32;
  private static final Duration SERVER_SYNC_TIMEOUT = Duration.ofSeconds(5);

  private static By redstoneModeButton(String redstoneMode) {
    return By.translationKey(TOOLTIP_FARM_PREFIX + "redstone_mode_" + redstoneMode);
  }

  private static String themeButtonText() {
    return client.widget(THEME_BUTTON).string(By.FIELD_TEXT);
  }

  @ParameterizedTest(name = "{0}")
  @ValueSource(
      strings = {
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
        "swamp_farm"
      })
  @DisplayName("Mob farm screen opens with a clean layout")
  void mobFarmScreenOpensWithCleanLayout(String farmId) throws IOException {
    openFarm(farmId);

    client.assertState(
        Until.layoutClean(LAYOUT_CHECKS_IGNORING_ICON_BUTTON_TEXT_OVERFLOW, List.of()));
    captureScreen(farmId);
  }

  @Test
  @DisplayName("Redstone mode button cycles through all modes and back")
  void redstoneModeButtonCyclesThroughAllModes() {
    openFarm("animal_plains_farm");

    for (int step = 0; step < REDSTONE_MODES_IN_CYCLE_ORDER.size(); step++) {
      String nextRedstoneMode =
          REDSTONE_MODES_IN_CYCLE_ORDER.get((step + 1) % REDSTONE_MODES_IN_CYCLE_ORDER.size());
      client.click(redstoneModeButton(REDSTONE_MODES_IN_CYCLE_ORDER.get(step)));
      client.widget(redstoneModeButton(nextRedstoneMode), SERVER_SYNC_TIMEOUT);
    }
  }

  @Test
  @DisplayName("Theme button cycles through distinct themes back to the first one")
  void themeButtonCyclesThroughDistinctThemes() throws IOException {
    openFarm("animal_plains_farm");
    String initialTheme = themeButtonText();
    Set<String> shownThemes = new LinkedHashSet<>();

    String shownTheme = initialTheme;
    while (shownThemes.add(shownTheme) && shownThemes.size() <= MAXIMUM_THEME_CLICKS) {
      captureScreen("theme_" + shownThemes.size());
      client.click(THEME_BUTTON);
      shownTheme = themeButtonText();
    }

    assertTrue(shownThemes.size() > 1, "Theme button did not switch the theme");
    assertEquals(initialTheme, shownTheme, "Theme cycle did not return to the first theme");
  }
}
