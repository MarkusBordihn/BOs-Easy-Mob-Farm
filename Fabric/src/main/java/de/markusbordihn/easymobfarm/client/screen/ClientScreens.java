package de.markusbordihn.easymobfarm.client.screen;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientScreens {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientScreens() {}

  public static void registerScreens() {
    MenuScreens.register(ModMenuTypes.MOB_FARM_MENU, MobFarmScreenWrapper::new);
  }
}
