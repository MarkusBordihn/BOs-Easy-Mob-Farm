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

package de.markusbordihn.easymobfarm.client.screen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.gui.screens.MenuScreens;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.screen.farm.AnimalPlainsFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.CreativeMobFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.DesertFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.JungleFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.MonsterPlainsCaveFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.NetherFortressFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.OceanFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.SwampFarmScreen;
import de.markusbordihn.easymobfarm.menu.farm.AnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.CreativeMobFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.DesertFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.JungleFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.MonsterPlainsCaveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.NetherFortressFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.OceanFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.SwampFarmMenu;

public class ClientScreens {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientScreens() {}

  public static void registerScreens(final FMLClientSetupEvent event) {
    log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);

    event.enqueueWork(() -> {
      // Mob Farm UI screen
      MenuScreens.register(AnimalPlainsFarmMenu.TYPE, AnimalPlainsFarmScreen::new);
      MenuScreens.register(CreativeMobFarmMenu.TYPE, CreativeMobFarmScreen::new);
      MenuScreens.register(DesertFarmMenu.TYPE, DesertFarmScreen::new);
      MenuScreens.register(JungleFarmMenu.TYPE, JungleFarmScreen::new);
      MenuScreens.register(MonsterPlainsCaveFarmMenu.TYPE, MonsterPlainsCaveFarmScreen::new);
      MenuScreens.register(NetherFortressFarmMenu.TYPE, NetherFortressFarmScreen::new);
      MenuScreens.register(OceanFarmMenu.TYPE, OceanFarmScreen::new);
      MenuScreens.register(SwampFarmMenu.TYPE, SwampFarmScreen::new);
    });
  }
}
