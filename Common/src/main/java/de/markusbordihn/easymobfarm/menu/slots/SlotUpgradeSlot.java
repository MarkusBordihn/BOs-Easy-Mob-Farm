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

package de.markusbordihn.easymobfarm.menu.slots;

import de.markusbordihn.easymobfarm.item.upgrade.SlotUpgradeItem;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.menu.MobFarmSlot;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class SlotUpgradeSlot extends MobFarmSlot {

  private final MobFarmMenu menu;

  public SlotUpgradeSlot(MobFarmMenu menu, Container container, int index, int x, int y) {
    super(container, index, x, y);
    this.menu = menu;
  }

  @Override
  public void setChanged() {
    super.setChanged();
    this.menu.slotUpgradeChanged(this);
  }

  @Override
  public boolean mayPlace(ItemStack itemStack) {
    if (itemStack.isEmpty() || hasItem()) {
      return false;
    }

    Item item = itemStack.getItem();
    if (item instanceof SlotUpgradeItem) {
      return true;
    }

    log.debug(
        "Item {} {} is not supported for slot upgrade slot.",
        itemStack,
        itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY));
    return false;
  }

  @Override
  public int getMaxStackSize() {
    return 1;
  }

  @Override
  public Component getTooltip() {
    return TextComponent.getTooltipText("slot_upgrade_slot");
  }
}
