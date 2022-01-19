
package de.markusbordihn.easymobfarm.client.screen.farm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.screen.MobFarmScreen;
import de.markusbordihn.easymobfarm.menu.farm.DesertFarmMenu;

public class DesertFarmScreen extends MobFarmScreen<DesertFarmMenu> {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public DesertFarmScreen(DesertFarmMenu menu, Inventory inventory,
      Component component) {
    super(menu, inventory, component);
  }

  @Override
  public void init() {
    super.init();
  }
}
