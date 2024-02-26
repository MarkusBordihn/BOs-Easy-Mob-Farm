/*
 * Copyright 2023 Markus Bordihn
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

import de.markusbordihn.easymobfarm.item.ModItems;
import net.minecraft.world.item.CreativeModeTab.DisplayItemsGenerator;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;
import net.minecraft.world.item.CreativeModeTab.Output;

public class MobFarmItems implements DisplayItemsGenerator {

  protected MobFarmItems() {
  }

  @Override
  public void accept(ItemDisplayParameters itemDisplayParameters, Output output) {
    // Mob Farms - Tier Copper
    output.accept(ModItems.COPPER_ANIMAL_PLAINS_FARM.get());
    output.accept(ModItems.COPPER_BEE_HIVE_FARM.get());
    output.accept(ModItems.COPPER_DESERT_FARM.get());
    output.accept(ModItems.COPPER_JUNGLE_FARM.get());
    output.accept(ModItems.COPPER_MONSTER_PLAINS_CAVE_FARM.get());
    output.accept(ModItems.COPPER_NETHER_FORTRESS_FARM.get());
    output.accept(ModItems.COPPER_OCEAN_FARM.get());
    output.accept(ModItems.COPPER_SWAMP_FARM.get());

    // Mob Farms - Tier Iron
    output.accept(ModItems.IRON_ANIMAL_PLAINS_FARM.get());
    output.accept(ModItems.IRON_BEE_HIVE_FARM.get());
    output.accept(ModItems.IRON_DESERT_FARM.get());
    output.accept(ModItems.IRON_JUNGLE_FARM.get());
    output.accept(ModItems.IRON_MONSTER_PLAINS_CAVE_FARM.get());
    output.accept(ModItems.IRON_NETHER_FORTRESS_FARM.get());
    output.accept(ModItems.IRON_OCEAN_FARM.get());
    output.accept(ModItems.IRON_SWAMP_FARM.get());

    // Mob Farms - Tier Gold
    output.accept(ModItems.GOLD_ANIMAL_PLAINS_FARM.get());
    output.accept(ModItems.GOLD_BEE_HIVE_FARM.get());
    output.accept(ModItems.GOLD_DESERT_FARM.get());
    output.accept(ModItems.GOLD_JUNGLE_FARM.get());
    output.accept(ModItems.GOLD_MONSTER_PLAINS_CAVE_FARM.get());
    output.accept(ModItems.GOLD_NETHER_FORTRESS_FARM.get());
    output.accept(ModItems.GOLD_OCEAN_FARM.get());
    output.accept(ModItems.GOLD_SWAMP_FARM.get());

    // Mob Farms - Tier Netherite
    output.accept(ModItems.NETHERITE_ANIMAL_PLAINS_FARM.get());
    output.accept(ModItems.NETHERITE_BEE_HIVE_FARM.get());
    output.accept(ModItems.NETHERITE_DESERT_FARM.get());
    output.accept(ModItems.NETHERITE_JUNGLE_FARM.get());
    output.accept(ModItems.NETHERITE_MONSTER_PLAINS_CAVE_FARM.get());
    output.accept(ModItems.NETHERITE_NETHER_FORTRESS_FARM.get());
    output.accept(ModItems.NETHERITE_OCEAN_FARM.get());
    output.accept(ModItems.NETHERITE_SWAMP_FARM.get());

    // Mob Farms - Special
    output.accept(ModItems.CREATIVE_MOB_FARM.get());
    output.accept(ModItems.IRON_GOLEM_FARM.get());
  }
}
