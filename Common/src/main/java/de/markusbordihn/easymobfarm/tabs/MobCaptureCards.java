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

package de.markusbordihn.easymobfarm.tabs;

import de.markusbordihn.easymobfarm.item.Items;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class MobCaptureCards {

  private MobCaptureCards() {}

  public static List<ItemStack> createTabItems() {
    List<ItemStack> items = new ArrayList<>();

    // Default mob capture cards
    items.add(Items.BLANK_MOB_CAPTURE_CARD.getDefaultInstance());
    items.add(Items.CREATIVE_MOB_CAPTURE_CARD.getDefaultInstance());

    // Add custom mob capture cards
    items.addAll(CustomMobCaptureCards.getCustomMobCaptureCards(Items.MOB_CAPTURE_CARD));

    // Add card binder for better organization of mob capture cards
    items.add(Items.CARD_BINDER.getDefaultInstance());
    return items;
  }
}
