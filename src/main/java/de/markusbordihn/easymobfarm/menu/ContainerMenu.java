package de.markusbordihn.easymobfarm.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.farm.AnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.MonsterPlainsCaveFarmMenu;

public class ContainerMenu {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ContainerMenu() {}

  public static void registerContainerMenu(RegistryEvent.Register<MenuType<?>> event) {
    log.info("{} Container Menu ...", Constants.LOG_REGISTER_PREFIX);

    // Mob Farm Container Menu
    event.getRegistry().registerAll(
        new MenuType<>(MonsterPlainsCaveFarmMenu::new).setRegistryName(Constants.MONSTER_PLAINS_CAVE_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(AnimalPlainsFarmMenu::new).setRegistryName(Constants.ANIMAL_PLAINS_FARM));
    event.getRegistry()
        .registerAll(new MenuType<>(MobFarmMenu::new).setRegistryName(Constants.MOB_FARM));
  }

}
