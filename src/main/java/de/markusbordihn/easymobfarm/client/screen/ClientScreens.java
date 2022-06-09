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
import de.markusbordihn.easymobfarm.client.screen.farm.MonsterPlainsCaveFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.OceanFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.SwampFarmScreen;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;

public class ClientScreens {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientScreens() {}

  public static void registerScreens(final FMLClientSetupEvent event) {
    log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);

    event.enqueueWork(() -> {
      // Mob Farm UI screen
      MenuScreens.register(ModMenuTypes.ANIMAL_PLAINS_FARM_MENU.get(), AnimalPlainsFarmScreen::new);
      MenuScreens.register(ModMenuTypes.CREATIVE_MOB_FARM_MENU.get(), CreativeMobFarmScreen::new);
      MenuScreens.register(ModMenuTypes.DESERT_FARM_MENU.get(), DesertFarmScreen::new);
      MenuScreens.register(ModMenuTypes.MONSTER_PLAINS_CAVE_FARM_MENU.get(),
          MonsterPlainsCaveFarmScreen::new);
      MenuScreens.register(ModMenuTypes.OCEAN_FARM_MENU.get(), OceanFarmScreen::new);
      MenuScreens.register(ModMenuTypes.SWAMP_FARM_MENU.get(), SwampFarmScreen::new);
    });
  }
}
