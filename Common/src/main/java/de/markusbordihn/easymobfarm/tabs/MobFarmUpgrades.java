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

import de.markusbordihn.easymobfarm.compat.CompatConstants;
import de.markusbordihn.easymobfarm.item.Items;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class MobFarmUpgrades {

  private MobFarmUpgrades() {}

  public static List<ItemStack> createTabItems() {
    List<ItemStack> items = new ArrayList<>();
    items.add(Items.BIG_SLOT_UPGRADE.getDefaultInstance());
    items.add(Items.CREATIVE_SPEED_ENHANCEMENT.getDefaultInstance());
    items.add(Items.EGG_COLLECTOR_ENHANCEMENT.getDefaultInstance());
    items.add(Items.EXPERIENCE_ENHANCEMENT.getDefaultInstance());
    items.add(Items.FROG_CATALYST_COLD_ENHANCEMENT.getDefaultInstance());
    items.add(Items.FROG_CATALYST_TEMPERATE_ENHANCEMENT.getDefaultInstance());
    items.add(Items.FROG_CATALYST_WARM_ENHANCEMENT.getDefaultInstance());

    if (CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
      items.add(Items.FROG_CATALYST_WHITE_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_ORANGE_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_MAGENTA_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_LIGHT_BLUE_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_YELLOW_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_LIME_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_PINK_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_GRAY_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_LIGHT_GRAY_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_CYAN_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_PURPLE_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_BLUE_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_BROWN_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_GREEN_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_RED_ENHANCEMENT.getDefaultInstance());
      items.add(Items.FROG_CATALYST_BLACK_ENHANCEMENT.getDefaultInstance());
    }

    items.add(Items.HONEY_EXTRACTOR_ENHANCEMENT.getDefaultInstance());
    items.add(Items.HONEY_HARVESTER_FRAME_ENHANCEMENT.getDefaultInstance());
    items.add(Items.KNIFE_ENHANCEMENT.getDefaultInstance());
    items.add(Items.LOOT_ENHANCEMENT.getDefaultInstance());
    items.add(Items.LUCK_ENHANCEMENT.getDefaultInstance());
    items.add(Items.MILK_EXTRACTOR_ENHANCEMENT.getDefaultInstance());
    items.add(Items.NO_FLOWERS_FILTER.getDefaultInstance());
    items.add(Items.NO_MEAT_FILTER.getDefaultInstance());
    items.add(Items.POLLEN_TRAP_ENHANCEMENT.getDefaultInstance());
    items.add(Items.SHEEP_ENHANCEMENT.getDefaultInstance());
    items.add(Items.SMALL_SLOT_UPGRADE.getDefaultInstance());
    items.add(Items.SPEED_ENHANCEMENT.getDefaultInstance());
    items.add(Items.SWORD_ENHANCEMENT.getDefaultInstance());
    return items;
  }
}
