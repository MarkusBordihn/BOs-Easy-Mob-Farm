/*
 * Copyright 2024 Markus Bordihn
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
import static org.junit.jupiter.api.Assertions.assertFalse;

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.EnabledIfModLoaded;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.GameLog;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@EnabledIfModLoaded("jei")
class JeiMobFarmLootClientTest extends ClientTestBase {

  private static final Pattern LOOT_ENTRY_COUNT =
    Pattern.compile("Mob Farm loot category shows (\\d+) entries");
  private static final GameLog.Mark GAME_START = new GameLog.Mark(0);
  private static final long ENTRY_COUNT_BEFORE_CARD_DEFINITIONS_SYNC = 0L;
  private static final int SYNC_SETTLE_TICKS = 100;

  private static Set<Long> loggedLootEntryCounts() {
    return client.gameLog().linesSince(GAME_START).stream()
      .map(LOOT_ENTRY_COUNT::matcher)
      .filter(Matcher::find)
      .map(matcher -> Long.parseLong(matcher.group(1)))
      .filter(entryCount -> entryCount != ENTRY_COUNT_BEFORE_CARD_DEFINITIONS_SYNC)
      .collect(Collectors.toSet());
  }

  @Test
  @DisplayName("Mob Farm loot entries keep their count after rejoining the world")
  void lootEntriesKeepTheirCountAfterRejoiningTheWorld() {
    GameLog.Mark beforeRejoin = client.gameLog().mark();
    client.leaveWorld();
    client.ensureWorld();
    client.awaitLogLine(beforeRejoin, LOOT_ENTRY_COUNT);
    client.await(Until.ticksElapsed(SYNC_SETTLE_TICKS));

    Set<Long> lootEntryCounts = loggedLootEntryCounts();
    assertFalse(lootEntryCounts.isEmpty(), "JEI never showed any Mob Farm loot entry");
    assertEquals(1, lootEntryCounts.size(),
      "Mob Farm loot entry count changed: " + lootEntryCounts);
  }
}
