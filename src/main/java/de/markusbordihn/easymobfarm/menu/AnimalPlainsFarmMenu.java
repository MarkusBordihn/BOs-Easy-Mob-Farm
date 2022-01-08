package de.markusbordihn.easymobfarm.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ObjectHolder;

import de.markusbordihn.easymobfarm.Constants;

public class AnimalPlainsFarmMenu extends MobFarmMenu {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @ObjectHolder("easy_mob_farm:animal_plains_farm")
  public static MenuType<AnimalPlainsFarmMenu> TYPE;

  public AnimalPlainsFarmMenu(int windowIdIn, Inventory inventory) {
    super(windowIdIn, inventory);
  }

  public AnimalPlainsFarmMenu(final int windowId, final Inventory playerInventory, final Container container,
      final ContainerData containerData) {
    super(windowId, playerInventory, container, containerData, TYPE);
  }

}
