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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.Parameters;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.api.Entity;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.api.EntityBounds;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.api.ItemStack;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.api.Position;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobCaptureCardDropClientTest extends ClientTestBase {

  private static final String MOB_CAPTURE_CARD = MOD_NAMESPACE + "mob_capture_card";
  private static final String CHICKEN = "minecraft:chicken";
  private static final String RAW_CHICKEN = "minecraft:chicken";
  private static final String FISHING_BOBBER = "minecraft:fishing_bobber";
  private static final String NETHERITE_SWORD = "minecraft:netherite_sword";
  private static final String LURE_FISHING_ROD =
      "minecraft:fishing_rod[enchantments={levels:{\"minecraft:lure\":3}}]";
  private static final List<String> FISH =
      List.of(
          "minecraft:cod", "minecraft:salmon", "minecraft:pufferfish", "minecraft:tropical_fish");
  private static final int MAXIMUM_ATTACKS = 5;
  private static final long DEATH_ANIMATION_TICKS = 25;
  private static final long PICKUP_TICKS = 40;
  private static final Duration PICKUP_TIMEOUT = Duration.ofSeconds(10);
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

  private static void killChickenByPlayer(double x, double z) {
    command("summon " + CHICKEN + " " + x + " " + FARM_Y + " " + z + " {NoAI:1b}");
    Entity chicken =
        client.awaitEntity(
            Parameters.of("type", CHICKEN), entity -> isAbove(entity.position(), x, z));
    EntityBounds bounds = chicken.boundingBox();
    client
        .api()
        .camera()
        .look(
            Parameters.of()
                .with(
                    "lookAtPoint",
                    Parameters.of()
                        .with("x", chicken.position().x())
                        .with("y", (bounds.min().y() + bounds.max().y()) / 2)
                        .with("z", chicken.position().z())));
    client.await(Until.ticksElapsed(2));
    for (int attack = 0; attack < MAXIMUM_ATTACKS && isPresent(chicken); attack++) {
      client.api().input().mouseButton(Parameters.of("button", "left"));
      client.await(Until.ticksElapsed(DEATH_ANIMATION_TICKS));
    }
    assertFalse(isPresent(chicken), "The player could not kill the chicken at " + x + ", " + z);
  }

  private static boolean isAbove(Position position, double x, double z) {
    return Math.abs(position.x() - x) < 0.5 && Math.abs(position.z() - z) < 0.5;
  }

  private static boolean isPresent(Entity entity) {
    return !client
        .api()
        .state()
        .entitiesGet(Parameters.of("uuid", entity.uuid()))
        .entities()
        .isEmpty();
  }

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
    return client
        .api()
        .state()
        .entitiesGet(Parameters.of("type", FISHING_BOBBER))
        .entities()
        .stream()
        .findFirst();
  }

  private static boolean isInPool(Position position) {
    return position.x() >= POOL_MINIMUM_X
        && position.x() < POOL_MAXIMUM_X + 1
        && position.z() >= POOL_MINIMUM_Z
        && position.z() < POOL_MAXIMUM_Z + 1;
  }

  // 1.21 item predicates cannot partially match a custom component, so the card's name is checked.
  private static void assertCaptureCardsHoldOneOf(List<String> entityTypes, String failureMessage) {
    List<String> entityNames =
        entityTypes.stream().map(MobCaptureCardDropClientTest::englishEntityName).toList();
    List<ItemStack> cardStacks = stacksOf(MOB_CAPTURE_CARD);
    assertTrue(
        cardStacks.stream()
            .allMatch(
                cardStack -> entityNames.stream().anyMatch(cardStack.displayName()::contains)),
        failureMessage + ": " + cardStacks);
  }

  private static String englishEntityName(String entityType) {
    return Arrays.stream(entityType.substring(entityType.indexOf(':') + 1).split("_"))
        .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
        .collect(Collectors.joining(" "));
  }

  private static void collectDroppedItems() {
    command("tp @e[type=item] @s");
  }

  private static void awaitItemCount(String item, long expectedCount) {
    awaitItemCount(item, count -> count >= expectedCount, "Expected " + expectedCount + " " + item);
  }

  private static void awaitItemCount(
      String item, LongPredicate isExpectedCount, String failureMessage) {
    long deadline = System.nanoTime() + PICKUP_TIMEOUT.toNanos();
    while (!isExpectedCount.test(countOf(item)) && System.nanoTime() < deadline) {
      client.await(Until.ticksElapsed(5));
    }
    assertTrue(
        isExpectedCount.test(countOf(item)), failureMessage + " in " + client.inventory().slots());
  }

  private static long countOf(String item) {
    return stacksOf(item).stream().mapToLong(ItemStack::count).sum();
  }

  private static List<ItemStack> stacksOf(String item) {
    return client.inventory().slots().stream()
        .filter(itemStack -> item.equals(itemStack.item()))
        .toList();
  }

  @AfterEach
  void clearPlayerInventoryAndChickens() {
    command("clear @s");
    command("kill @e[type=" + CHICKEN + "]");
  }

  @Test
  @DisplayName("Chickens killed by the player drop capture cards that stack")
  void playerKillDropsStackingCaptureCards() throws IOException {
    enterTestArea(0);
    command("item replace entity @s weapon.mainhand with " + NETHERITE_SWORD);
    killChickenByPlayer(-0.5, 2.5);
    killChickenByPlayer(1.5, 2.5);
    captureScreen("chicken_kill_drops");

    collectDroppedItems();
    awaitItemCount(MOB_CAPTURE_CARD, 2);
    List<ItemStack> cardStacks = stacksOf(MOB_CAPTURE_CARD);
    assertEquals(1, cardStacks.size(), "Capture cards did not stack: " + cardStacks);
    assertEquals(2, cardStacks.get(0).count());

    assertCaptureCardsHoldOneOf(List.of(CHICKEN), "Capture cards do not hold the chicken");
  }

  @Test
  @DisplayName("Chickens killed by a command drop no capture card")
  void commandKillDropsNoCaptureCard() {
    enterTestArea(0);
    command("summon " + CHICKEN + " 0.5 " + FARM_Y + " 1.5 {NoAI:1b}");
    client.awaitEntity(Parameters.of("type", CHICKEN), entity -> true);
    command("kill @e[type=" + CHICKEN + "]");

    collectDroppedItems();
    awaitItemCount(RAW_CHICKEN, 1);
    client.await(Until.ticksElapsed(PICKUP_TICKS));
    assertEquals(0, countOf(MOB_CAPTURE_CARD), "A command kill dropped a capture card.");
  }

  @Test
  @DisplayName("Fishing with a rod drops the capture card of the caught fish")
  void fishingDropsCaptureCardOfCaughtFish() throws IOException {
    enterTestArea(FISHING_PITCH);
    command(
        "fill "
            + POOL_MINIMUM_X
            + " "
            + (PLATFORM_Y - 1)
            + " "
            + POOL_MINIMUM_Z
            + " "
            + POOL_MAXIMUM_X
            + " "
            + (PLATFORM_Y - 1)
            + " "
            + POOL_MAXIMUM_Z
            + " minecraft:stone");
    command(
        "fill "
            + POOL_MINIMUM_X
            + " "
            + PLATFORM_Y
            + " "
            + POOL_MINIMUM_Z
            + " "
            + POOL_MAXIMUM_X
            + " "
            + PLATFORM_Y
            + " "
            + POOL_MAXIMUM_Z
            + " minecraft:water");
    command("item replace entity @s weapon.mainhand with " + LURE_FISHING_ROD);

    StringBuilder fishingLog = new StringBuilder();
    for (int cast = 0; cast < MAXIMUM_CASTS && countOf(MOB_CAPTURE_CARD) == 0; cast++) {
      useHeldItem();
      fishingLog.append("cast ").append(cast + 1).append(": ").append(awaitBite()).append('\n');
      useHeldItem();
      client.await(Until.ticksElapsed(PICKUP_TICKS));
    }
    captureScreen("fishing_catch");
    assertTrue(
        countOf(MOB_CAPTURE_CARD) > 0,
        "No capture card after " + MAXIMUM_CASTS + " casts:\n" + fishingLog);

    List<String> caughtFish = FISH.stream().filter(fish -> countOf(fish) > 0).toList();
    assertCaptureCardsHoldOneOf(caughtFish, "Capture cards do not match the caught fish");
  }
}
