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

package de.markusbordihn.easymobfarm.client.screen.components;

import de.markusbordihn.easymobfarm.TestBootstrap;
import de.markusbordihn.easymobfarm.client.screen.components.SlotHintRenderer.HintGroup;
import de.markusbordihn.easymobfarm.menu.slots.EnhancementSlot;
import de.markusbordihn.easymobfarm.menu.slots.FilterSlot;
import de.markusbordihn.easymobfarm.menu.slots.SlotUpgradeSlot;
import java.util.List;
import java.util.Set;
import net.minecraft.world.inventory.Slot;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SlotHintRendererTest {

  @BeforeAll
  static void setupMinecraft() {
    TestBootstrap.bootstrapWithBoundItemComponents();
  }

  @Test
  void hintsChangeOnlyTwiceAndThenStayStill() {
    SlotHintCycle cycle = new SlotHintCycle(1000, 3);
    Assertions.assertEquals(3, cycle.index(0, 6, 1000));
    Assertions.assertEquals(3, cycle.index(0, 6, 4999));
    Assertions.assertEquals(4, cycle.index(0, 6, 5000));
    Assertions.assertEquals(4, cycle.index(0, 6, 8999));
    Assertions.assertEquals(5, cycle.index(0, 6, 9000));
    Assertions.assertEquals(5, cycle.index(0, 6, 3600000));
    Assertions.assertEquals(0, cycle.index(1, 6, 3600000));
  }

  @Test
  void randomSeedsAlwaysProduceValidIndices() {
    for (int seed : new int[] {Integer.MIN_VALUE, -1, 0, Integer.MAX_VALUE}) {
      SlotHintCycle cycle = new SlotHintCycle(0, seed);
      for (int slot = 0; slot < 75; slot++) {
        int index = cycle.index(slot, 6, 100000);
        Assertions.assertTrue(index >= 0 && index < 6);
      }
    }
  }

  @Test
  void fillingOneSlotSuppressesOnlyItsGroupAndRemovingItRestoresHints() {
    EnhancementSlot enhancement = Mockito.mock(EnhancementSlot.class);
    EnhancementSlot emptyEnhancement = Mockito.mock(EnhancementSlot.class);
    FilterSlot filter = Mockito.mock(FilterSlot.class);
    SlotUpgradeSlot upgrade = Mockito.mock(SlotUpgradeSlot.class);
    Mockito.when(enhancement.hasItem()).thenReturn(true);
    Mockito.when(upgrade.hasItem()).thenReturn(true);
    List<Slot> slots = List.of(enhancement, emptyEnhancement, filter, upgrade);

    Assertions.assertEquals(
        Set.of(HintGroup.ENHANCEMENT, HintGroup.SLOT_UPGRADE), HintGroup.occupiedGroups(slots));

    Mockito.when(enhancement.hasItem()).thenReturn(false);
    Assertions.assertEquals(Set.of(HintGroup.SLOT_UPGRADE), HintGroup.occupiedGroups(slots));
  }
}
