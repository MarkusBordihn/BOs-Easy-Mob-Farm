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

import de.markusbordihn.easymobfarm.item.ModBlockItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class MobFarms {

  private MobFarms() {}

  public static List<ItemStack> createTabItems() {
    List<ItemStack> items = new ArrayList<>();
    items.add(ModBlockItems.CREATIVE_MOB_FARM.getDefaultInstance());
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.ANIMAL_PLAINS_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.BEE_HIVE_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.DESERT_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.IRON_GOLEM_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.JUNGLE_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.LUCKY_DROP_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.MONSTER_PLAINS_CAVE_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.NETHER_FORTRESS_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.OCEAN_FARM.getDefaultInstance().getItem()));
    items.addAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.SWAMP_FARM.getDefaultInstance().getItem()));
    return items;
  }
}
