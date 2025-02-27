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
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;

public class MobFarms implements DisplayItemsGenerator {

  protected MobFarms() {}

  @Override
  public void accept(ItemDisplayParameters itemDisplayParameters, Output output) {

    // Default mob farm items
    output.accept(ModBlockItems.CREATIVE_MOB_FARM.getDefaultInstance());
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.ANIMAL_PLAINS_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.BEE_HIVE_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.DESERT_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.IRON_GOLEM_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.JUNGLE_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.LUCKY_DROP_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.MONSTER_PLAINS_CAVE_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.NETHER_FORTRESS_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.OCEAN_FARM.getDefaultInstance().getItem()));
    output.acceptAll(
        CustomMobFarmBlocks.getMobFarmTiers(
            ModBlockItems.SWAMP_FARM.getDefaultInstance().getItem()));
  }
}
