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
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;

public class MobFarmUpgrades implements DisplayItemsGenerator {

  protected MobFarmUpgrades() {}

  @Override
  public void accept(ItemDisplayParameters itemDisplayParameters, Output output) {
    // Default mob farm upgrades
    output.accept(Items.EXPERIENCE_ENHANCEMENT.getDefaultInstance());
    output.accept(Items.LOOT_ENHANCEMENT.getDefaultInstance());
    output.accept(Items.LUCK_ENHANCEMENT.getDefaultInstance());
    output.accept(Items.SHEEP_ENHANCEMENT.getDefaultInstance());
    output.accept(Items.SPEED_ENHANCEMENT.getDefaultInstance());
    output.accept(Items.SWORD_ENHANCEMENT.getDefaultInstance());
    output.accept(Items.NO_FLOWERS_FILTER.getDefaultInstance());
    output.accept(Items.NO_MEAT_FILTER.getDefaultInstance());
    output.accept(Items.SMALL_SLOT_UPGRADE.getDefaultInstance());
    output.accept(Items.BIG_SLOT_UPGRADE.getDefaultInstance());
  }
}
