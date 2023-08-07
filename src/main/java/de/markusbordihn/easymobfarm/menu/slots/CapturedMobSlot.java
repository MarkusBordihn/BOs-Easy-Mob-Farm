/**
 * Copyright 2022 Markus Bordihn
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

package de.markusbordihn.easymobfarm.menu.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import de.markusbordihn.easymobfarm.item.CapturedMob;
import de.markusbordihn.easymobfarm.item.CapturedMobVirtual;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class CapturedMobSlot extends Slot {

  private MobFarmMenu menu;

  public CapturedMobSlot(Container container, int index, int x, int y, MobFarmMenu menu) {
    super(container, index, x, y);
    this.menu = menu;
  }
  @Override
  public boolean mayPlace(ItemStack itemStack) {
    if (!this.menu.mayPlaceCapturedMob(itemStack)) {
      return false;
    }
    if (itemStack.getItem() instanceof CapturedMob) {
      return this.menu.mayPlaceCapturedMobType(CapturedMob.getCapturedMobType(itemStack));
    } else if (CapturedMobVirtual.isSupported(itemStack)) {
      return this.menu.mayPlaceCapturedMobType(CapturedMobVirtual.getCapturedMobType(itemStack));
    }
    return false;
  }

}
