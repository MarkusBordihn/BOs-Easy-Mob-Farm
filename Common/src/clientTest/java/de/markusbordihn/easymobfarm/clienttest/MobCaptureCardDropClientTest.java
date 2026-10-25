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

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.Parameters;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.TestArea;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.api.Entity;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.api.Position;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MobCaptureCardDropClientTest extends ClientTestBase {

  private static final String MOB_CAPTURE_CARD = MOD_NAMESPACE + "mob_capture_card";
  private static final String FISHING_BOBBER = "minecraft:fishing_bobber";
  private static final String LURE_FISHING_ROD =
      "minecraft:fishing_rod{Enchantments:[{id:\"minecraft:lure\",lvl:3s}]}";
  private static final List<String> FISH =
      List.of(
          "minecraft:cod", "minecraft:salmon", "minecraft:pufferfish", "minecraft:tropical_fish");
  private static final long PICKUP_TICKS = 40;
  private static final int FISHING_PITCH = 20;
  private static final int POOL_MINIMUM_X = -4;
  private static final int POOL_MAXIMUM_X = 4;
  private static final int POOL_MINIMUM_Z = 2;
  private static final int POOL_MAXIMUM_Z = 7;
  private static final int MAXIMUM_CASTS = 6;
  private static final Duration BITE_TIMEOUT = Duration.ofSeconds(45);
  private static final int SETTLED_BOBBER_POLLS = 10;
  private static final double SETTLED_BOBBER_MOTION = 0.05;
  private static final double BITE_DIP = 0.2;

  private static String awaitBite() {
    long deadline = System.nanoTime() + BITE_TIMEOUT.toNanos();
    double previousY = Double.NaN;
    int settledPolls = 0;
    double floatingY = Double.NEGATIVE_INFINITY;
    while (System.nanoTime() < deadline) {
      client.await(Until.ticksElapsed(1));
      Optional<Entity> bobber = findBobber();
      if (bobber.isEmpty()) {
        return "bobber vanished";
      }

      Position position = bobber.get().position();
      if (settledPolls < SETTLED_BOBBER_POLLS) {
        if (Math.abs(position.y() - previousY) < SETTLED_BOBBER_MOTION) {
          settledPolls++;
        } else {
          settledPolls = 0;
        }
        previousY = position.y();
        if (settledPolls == SETTLED_BOBBER_POLLS && !isInPool(position)) {
          return "bobber landed outside the pool at " + position;
        }
        continue;
      }

      floatingY = Math.max(floatingY, position.y());
      if (position.y() < floatingY - BITE_DIP) {
        return "bite, bobber dipped to y=" + position.y() + " from y=" + floatingY;
      }
    }
    return "no bite within " + BITE_TIMEOUT.toSeconds() + " s, floating at y=" + floatingY;
  }

  private static Optional<Entity> findBobber() {
    return client.state().entitiesGet(Parameters.of("type", FISHING_BOBBER)).entities().stream()
        .findFirst();
  }

  private static boolean isInPool(Position position) {
    return position.x() >= POOL_MINIMUM_X
        && position.x() < POOL_MAXIMUM_X + 1
        && position.z() >= POOL_MINIMUM_Z
        && position.z() < POOL_MAXIMUM_Z + 1;
  }

  private static void buildFishingPool() {
    client.fill(
        TestArea.between(
            POOL_MINIMUM_X,
            PLATFORM_Y - 1,
            POOL_MINIMUM_Z,
            POOL_MAXIMUM_X,
            PLATFORM_Y - 1,
            POOL_MAXIMUM_Z),
        "minecraft:stone");
    client.fill(
        TestArea.between(
            POOL_MINIMUM_X, PLATFORM_Y, POOL_MINIMUM_Z, POOL_MAXIMUM_X, PLATFORM_Y, POOL_MAXIMUM_Z),
        "minecraft:water");
  }

  private static void clearCaptureCardsOf(String entityType) {
    command("clear @s " + MOB_CAPTURE_CARD + "{MobCaptureData:{Type:\"" + entityType + "\"}}");
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.MINUTES)
  @DisplayName("Fishing with a rod drops the capture card of the caught fish")
  void fishingDropsCaptureCardOfCaughtFish() {
    enterTestArea(FISHING_PITCH);
    buildFishingPool();
    client.equip(LURE_FISHING_ROD);

    StringBuilder fishingLog = new StringBuilder();
    for (int cast = 0; cast < MAXIMUM_CASTS && client.itemCount(MOB_CAPTURE_CARD) == 0; cast++) {
      client.useHeldItem();
      fishingLog.append("cast ").append(cast + 1).append(": ").append(awaitBite()).append('\n');
      client.useHeldItem();
      client.await(Until.ticksElapsed(PICKUP_TICKS));
    }
    client.saveScreenshot("fishing_catch");
    assertTrue(
        client.itemCount(MOB_CAPTURE_CARD) > 0,
        "No capture card after " + MAXIMUM_CASTS + " casts:\n" + fishingLog);

    for (String fish : FISH) {
      if (client.itemCount(fish) > 0) {
        clearCaptureCardsOf(fish);
      }
    }
    assertEquals(
        0, client.itemCount(MOB_CAPTURE_CARD), "Capture cards do not match the caught fish");
  }
}
