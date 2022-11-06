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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.farm.AnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.CreativeMobFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.DesertFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.JungleFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.MonsterPlainsCaveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.NetherFortressFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.OceanFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.SwampFarmMenu;

public class ContainerMenu {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ContainerMenu() {}

  public static void registerContainerMenu(RegistryEvent.Register<MenuType<?>> event) {
    log.info("{} Container Menu ...", Constants.LOG_REGISTER_PREFIX);

    // Mob Farm Container Menu
    event.getRegistry()
        .registerAll(new MenuType<>(OceanFarmMenu::new).setRegistryName(Constants.OCEAN_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(AnimalPlainsFarmMenu::new).setRegistryName(Constants.ANIMAL_PLAINS_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(CreativeMobFarmMenu::new).setRegistryName(Constants.CREATIVE_MOB_FARM));
    event.getRegistry()
        .registerAll(new MenuType<>(DesertFarmMenu::new).setRegistryName(Constants.DESERT_FARM));
    event.getRegistry()
        .registerAll(new MenuType<>(JungleFarmMenu::new).setRegistryName(Constants.JUNGLE_FARM));
    event.getRegistry().registerAll(new MenuType<>(MonsterPlainsCaveFarmMenu::new)
        .setRegistryName(Constants.MONSTER_PLAINS_CAVE_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetherFortressFarmMenu::new)
        .setRegistryName(Constants.NETHER_FORTRESS_FARM));
    event.getRegistry()
        .registerAll(new MenuType<>(SwampFarmMenu::new).setRegistryName(Constants.SWAMP_FARM));
    event.getRegistry()
        .registerAll(new MenuType<>(MobFarmMenu::new).setRegistryName(Constants.MOB_FARM));
  }

}
