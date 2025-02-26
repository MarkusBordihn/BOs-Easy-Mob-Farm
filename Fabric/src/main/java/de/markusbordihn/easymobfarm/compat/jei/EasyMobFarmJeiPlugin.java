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
import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

public class EasyMobFarmJeiPlugin implements IModPlugin {

  private static final ResourceLocation pluginId =
      ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid() {
    return pluginId;
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registration) {
    registration.registerSubtypeInterpreter(
        Items.MOB_CAPTURE_CARD, MobCaptureCardSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.ANIMAL_PLAINS_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.BEE_HIVE_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.DESERT_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.IRON_GOLEM_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.JUNGLE_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.LUCKY_DROP_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.MONSTER_PLAINS_CAVE_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.NETHER_FORTRESS_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.OCEAN_FARM, MobFarmSubtypeInterpreter.INSTANCE);
    registration.registerSubtypeInterpreter(
        ModBlockItems.SWAMP_FARM, MobFarmSubtypeInterpreter.INSTANCE);
  }
}
