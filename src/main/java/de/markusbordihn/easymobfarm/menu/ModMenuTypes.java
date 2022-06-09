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

package de.markusbordihn.easymobfarm.menu;

import net.minecraft.world.inventory.MenuType;

import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.farm.AnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.CreativeMobFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.DesertFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.MonsterPlainsCaveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.OceanFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.SwampFarmMenu;

public class ModMenuTypes {

  protected ModMenuTypes() {

  }

  public static final DeferredRegister<MenuType<?>> MENU_TYPES =
      DeferredRegister.create(ForgeRegistries.CONTAINERS, Constants.MOD_ID);

  public static final RegistryObject<MenuType<AnimalPlainsFarmMenu>> ANIMAL_PLAINS_FARM_MENU =
      MENU_TYPES.register("animal_plains_farm", () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new AnimalPlainsFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CreativeMobFarmMenu>> CREATIVE_MOB_FARM_MENU =
      MENU_TYPES.register("creative_mob_farm", () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new CreativeMobFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<DesertFarmMenu>> DESERT_FARM_MENU =
      MENU_TYPES.register("desert_farm", () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new DesertFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<MobFarmMenu>> MOB_FARM_MENU =
      MENU_TYPES.register("mob_farm", () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new MobFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<MonsterPlainsCaveFarmMenu>> MONSTER_PLAINS_CAVE_FARM_MENU =
      MENU_TYPES.register("monster_plains_cave_farm", () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new MonsterPlainsCaveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<OceanFarmMenu>> OCEAN_FARM_MENU =
      MENU_TYPES.register("ocean_farm", () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new OceanFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<SwampFarmMenu>> SWAMP_FARM_MENU =
      MENU_TYPES.register("swamp_farm", () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new SwampFarmMenu(windowIdIn, inventory)));
}
