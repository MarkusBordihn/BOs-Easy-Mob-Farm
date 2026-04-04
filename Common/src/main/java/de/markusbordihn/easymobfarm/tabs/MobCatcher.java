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

public class MobCatcher {

  private MobCatcher() {}

  public static List<ItemStack> createTabItems() {
    List<ItemStack> items = new ArrayList<>();
    items.add(Items.CREATIVE_MOB_CATCHER.getDefaultInstance());
    items.add(Items.ENDURING_CAPTURE_NET.getDefaultInstance());
    items.add(Items.IRONBOUND_CONTAINMENT_CAGE.getDefaultInstance());
    items.add(Items.MYSTIC_BINDING_CRYSTAL.getDefaultInstance());
    items.add(Items.VOID_BINDING_CHAIN.getDefaultInstance());
    return items;
  }
}
