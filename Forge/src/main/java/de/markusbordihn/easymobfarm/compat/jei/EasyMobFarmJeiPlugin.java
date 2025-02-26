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

package de.markusbordihn.easymobfarm.compat.jei;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import de.markusbordihn.easymobfarm.item.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class EasyMobFarmJeiPlugin implements IModPlugin {

  private static final ResourceLocation pluginId =
      new ResourceLocation(Constants.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid() {
    return pluginId;
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registration) {
    registration.useNbtForSubtypes(ModItems.MOB_CAPTURE_CARD.get());
    registration.useNbtForSubtypes(ModBlockItems.ANIMAL_PLAINS_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.BEE_HIVE_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.DESERT_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.IRON_GOLEM_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.JUNGLE_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.LUCKY_DROP_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.MONSTER_PLAINS_CAVE_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.NETHER_FORTRESS_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.OCEAN_FARM.get());
    registration.useNbtForSubtypes(ModBlockItems.SWAMP_FARM.get());
  }
}
