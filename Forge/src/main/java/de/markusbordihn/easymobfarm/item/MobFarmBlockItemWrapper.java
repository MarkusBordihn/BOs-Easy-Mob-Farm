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

import de.markusbordihn.easymobfarm.tabs.CustomMobFarmBlocks;
import de.markusbordihn.easymobfarm.tabs.ModTabs;
import java.util.Set;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class MobFarmBlockItemWrapper extends MobFarmBlockItem {

  public MobFarmBlockItemWrapper(String farmName, Block block, Item.Properties properties) {
    super(farmName, block, properties);
  }

  @Override
  public boolean allowdedIn(CreativeModeTab group) {
    return ModTabs.TAB_MOB_FARMS.equals(group);
  }

  @Override
  public void fillItemCategory(CreativeModeTab creativeTab, NonNullList<ItemStack> items) {
    if (!this.allowdedIn(creativeTab)) {
      return;
    }
    Set<ItemStack> test = CustomMobFarmBlocks.getMobFarmTiers(this);
    items.addAll(test);
  }
}
