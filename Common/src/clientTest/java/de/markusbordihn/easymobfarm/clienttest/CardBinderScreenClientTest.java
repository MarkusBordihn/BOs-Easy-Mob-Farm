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

import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.By;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.Until;
import de.markusbordihn.clientruntimeinterfacetoolkit.testrunner.data.Parameters;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardBinderScreenClientTest extends ClientTestBase {

  private static final By PREVIOUS_PAGE_BUTTON = By.text("<");
  private static final By NEXT_PAGE_BUTTON = By.text(">");
  private static final String MOB_CAPTURE_CARD = MOD_NAMESPACE + "mob_capture_card";
  private static final int FIRST_PLAYER_INVENTORY_MENU_SLOT = 250;
  private static final List<String> CARD_MOB_NAMES = List.of("chicken", "cow", "sheep");
  private static final List<Integer> CARD_STACK_SIZES = List.of(20, 10, 10);

  private static String mobCaptureCard(String mobName) {
    return MOB_CAPTURE_CARD
        + "[easy_mob_farm:mob_capture_data={name:\"entity.minecraft."
        + mobName
        + "\",type:\"minecraft:"
        + mobName
        + "\",entityType:\"minecraft:"
        + mobName
        + "\"}]";
  }

  private static boolean shiftClickSlot(int slot) {
    return client
        .api()
        .gui()
        .click(
            Parameters.of()
                .with("target", By.slot(slot).asTarget())
                .withStrings("modifiers", List.of("shift")))
        .accepted();
  }

  @Test
  @DisplayName("Card binder pages forward and enables the previous page button")
  void cardBinderPagesForward() throws IOException {
    enterTestArea(-90);
    for (int i = 0; i < CARD_MOB_NAMES.size(); i++) {
      command(
          "item replace entity @s inventory."
              + i
              + " with "
              + mobCaptureCard(CARD_MOB_NAMES.get(i))
              + " "
              + CARD_STACK_SIZES.get(i));
    }
    command("item replace entity @s weapon.mainhand with " + MOD_NAMESPACE + "card_binder");
    client.await(Until.itemInSlot(0, MOD_NAMESPACE + "card_binder"));
    useHeldItem();
    client.await(Until.screen(CARD_BINDER_SCREEN_ID));

    for (int i = 0; i < CARD_MOB_NAMES.size(); i++) {
      assertTrue(
          shiftClickSlot(FIRST_PLAYER_INVENTORY_MENU_SLOT + i),
          "Shift-click on card stack " + CARD_MOB_NAMES.get(i) + " was rejected");
    }
    client.await(Until.ticksElapsed(5));
    assertEquals(
        MOB_CAPTURE_CARD,
        client.api().state().guiGet().slots().stream()
            .filter(slot -> slot.index() == 0)
            .findFirst()
            .orElseThrow()
            .item()
            .item(),
        "First card slot was not filled by the shift-click");

    assertFalse(
        client.checkState(Until.widgetEnabled(PREVIOUS_PAGE_BUTTON)).passed(),
        "Previous page button is enabled on the first page");
    captureScreen("card_binder_first_page");

    client.click(NEXT_PAGE_BUTTON);
    client.await(Until.widgetEnabled(PREVIOUS_PAGE_BUTTON));
    captureScreen("card_binder_second_page");
  }
}
