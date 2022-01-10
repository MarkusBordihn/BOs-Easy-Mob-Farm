
package de.markusbordihn.easymobfarm.menu.farm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ObjectHolder;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class CreativeMobFarmMenu extends MobFarmMenu {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @ObjectHolder("easy_mob_farm:creative_mob_farm")
  public static MenuType<CreativeMobFarmMenu> TYPE;

  public CreativeMobFarmMenu(int windowIdIn, Inventory inventory) {
    super(windowIdIn, inventory);
  }

  public CreativeMobFarmMenu(final int windowId, final Inventory playerInventory, final Container container,
      final ContainerData containerData) {
    super(windowId, playerInventory, container, containerData, TYPE);
  }

  @Override
  public boolean mayPlaceCapturedMobType(String mobType) {
    return true;
  }

}
