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

package de.markusbordihn.easymobfarm.item;

import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import de.markusbordihn.easymobfarm.tabs.CustomMobCaptureCards;
import de.markusbordihn.easymobfarm.tabs.ModTabs;
import java.util.Set;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MobCaptureCardItemWrapper extends MobCaptureCardItem {

  public MobCaptureCardItemWrapper() {
    super();
  }

  @Override
  protected boolean allowedIn(CreativeModeTab group) {
    return ModTabs.TAB_MOB_CAPTURE_CARDS.equals(group);
  }

  @Override
  public void fillItemCategory(CreativeModeTab creativeTab, NonNullList<ItemStack> items) {
    if (!this.allowedIn(creativeTab)) {
      return;
    }
    Set<ItemStack> mobCaptureCards = CustomMobCaptureCards.getCustomMobCaptureCards(this);
    items.addAll(mobCaptureCards);
  }
}
