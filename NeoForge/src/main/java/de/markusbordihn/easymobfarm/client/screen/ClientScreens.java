package de.markusbordihn.easymobfarm.client.screen;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientScreens {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientScreens() {}

  public static void registerScreens(final RegisterMenuScreensEvent event) {
    log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);

    event.register(ModMenuTypes.MOB_FARM_MENU.get(), MobFarmScreenWrapper::new);
    event.register(ModMenuTypes.CARD_BINDER_MENU.get(), CardBinderScreenWrapper::new);
  }
}
