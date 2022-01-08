package de.markusbordihn.easymobfarm.client.screen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.AnimalPlainsFarmMenu;

public class AnimalPlainsFarmScreen extends MobFarmScreen<AnimalPlainsFarmMenu> {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ResourceLocation TEXTURE =
      new ResourceLocation(Constants.MOD_ID, "textures/container/animal_plans_farm_gui.png");

  public AnimalPlainsFarmScreen(AnimalPlainsFarmMenu menu, Inventory inventory,
      Component component) {
    super(menu, inventory, component);
    log.info("Open Animal Plains Farm Screen");
  }

  @Override
  public void init() {
    super.init();
    this.backgroundTexture = TEXTURE;
  }
}
