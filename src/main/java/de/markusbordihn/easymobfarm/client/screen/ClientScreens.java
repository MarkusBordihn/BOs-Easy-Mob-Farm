/*
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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.screen.farm.AnimalPlainsFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.BeeHiveFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.CreativeMobFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.DesertFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.JungleFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.MonsterPlainsCaveFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.NetherFortressFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.OceanFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.SpecialFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.SwampFarmScreen;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperAnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperBeeHiveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperDesertFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperJungleFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperMonsterPlainsCaveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperNetherFortressFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperOceanFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.copper.CopperSwampFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldAnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldBeeHiveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldDesertFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldJungleFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldMonsterPlainsCaveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldNetherFortressFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldOceanFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.gold.GoldSwampFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronAnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronBeeHiveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronDesertFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronJungleFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronMonsterPlainsCaveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronNetherFortressFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronOceanFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.iron.IronSwampFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteAnimalPlainsFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteBeeHiveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteDesertFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteJungleFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteMonsterPlainsCaveFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteNetherFortressFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteOceanFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.netherite.NetheriteSwampFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.special.CreativeMobFarmMenu;
import de.markusbordihn.easymobfarm.menu.farm.special.IronGolemFarmMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientScreens {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientScreens() {
  }

  public static void registerScreens(final FMLClientSetupEvent event) {
    log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);

    event.enqueueWork(() -> {

      // Copper Mob Farm UI screen
      MenuScreens.register(CopperAnimalPlainsFarmMenu.TYPE,
          AnimalPlainsFarmScreen<CopperAnimalPlainsFarmMenu>::new);
      MenuScreens.register(CopperBeeHiveFarmMenu.TYPE,
          BeeHiveFarmScreen<CopperBeeHiveFarmMenu>::new);
      MenuScreens.register(CopperDesertFarmMenu.TYPE, DesertFarmScreen<CopperDesertFarmMenu>::new);
      MenuScreens.register(CopperJungleFarmMenu.TYPE, JungleFarmScreen<CopperJungleFarmMenu>::new);
      MenuScreens.register(CopperMonsterPlainsCaveFarmMenu.TYPE,
          MonsterPlainsCaveFarmScreen<CopperMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(CopperNetherFortressFarmMenu.TYPE,
          NetherFortressFarmScreen<CopperNetherFortressFarmMenu>::new);
      MenuScreens.register(CopperOceanFarmMenu.TYPE, OceanFarmScreen<CopperOceanFarmMenu>::new);
      MenuScreens.register(CopperSwampFarmMenu.TYPE, SwampFarmScreen<CopperSwampFarmMenu>::new);

      // Gold Mob Farm UI screen
      MenuScreens.register(GoldAnimalPlainsFarmMenu.TYPE,
          AnimalPlainsFarmScreen<GoldAnimalPlainsFarmMenu>::new);
      MenuScreens.register(GoldBeeHiveFarmMenu.TYPE, BeeHiveFarmScreen<GoldBeeHiveFarmMenu>::new);
      MenuScreens.register(GoldDesertFarmMenu.TYPE, DesertFarmScreen<GoldDesertFarmMenu>::new);
      MenuScreens.register(GoldJungleFarmMenu.TYPE, JungleFarmScreen<GoldJungleFarmMenu>::new);
      MenuScreens.register(GoldMonsterPlainsCaveFarmMenu.TYPE,
          MonsterPlainsCaveFarmScreen<GoldMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(GoldNetherFortressFarmMenu.TYPE,
          NetherFortressFarmScreen<GoldNetherFortressFarmMenu>::new);
      MenuScreens.register(GoldOceanFarmMenu.TYPE, OceanFarmScreen<GoldOceanFarmMenu>::new);
      MenuScreens.register(GoldSwampFarmMenu.TYPE, SwampFarmScreen<GoldSwampFarmMenu>::new);

      // Iron Mob Farm UI screen
      MenuScreens.register(IronAnimalPlainsFarmMenu.TYPE,
          AnimalPlainsFarmScreen<IronAnimalPlainsFarmMenu>::new);
      MenuScreens.register(IronBeeHiveFarmMenu.TYPE, BeeHiveFarmScreen<IronBeeHiveFarmMenu>::new);
      MenuScreens.register(IronDesertFarmMenu.TYPE, DesertFarmScreen<IronDesertFarmMenu>::new);
      MenuScreens.register(IronJungleFarmMenu.TYPE, JungleFarmScreen<IronJungleFarmMenu>::new);
      MenuScreens.register(IronMonsterPlainsCaveFarmMenu.TYPE,
          MonsterPlainsCaveFarmScreen<IronMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(IronNetherFortressFarmMenu.TYPE,
          NetherFortressFarmScreen<IronNetherFortressFarmMenu>::new);
      MenuScreens.register(IronOceanFarmMenu.TYPE, OceanFarmScreen<IronOceanFarmMenu>::new);
      MenuScreens.register(IronSwampFarmMenu.TYPE, SwampFarmScreen<IronSwampFarmMenu>::new);

      // Netherite Mob Farm UI screen
      MenuScreens.register(NetheriteAnimalPlainsFarmMenu.TYPE,
          AnimalPlainsFarmScreen<NetheriteAnimalPlainsFarmMenu>::new);
      MenuScreens.register(NetheriteBeeHiveFarmMenu.TYPE,
          BeeHiveFarmScreen<NetheriteBeeHiveFarmMenu>::new);
      MenuScreens.register(NetheriteDesertFarmMenu.TYPE,
          DesertFarmScreen<NetheriteDesertFarmMenu>::new);
      MenuScreens.register(NetheriteJungleFarmMenu.TYPE,
          JungleFarmScreen<NetheriteJungleFarmMenu>::new);
      MenuScreens.register(NetheriteMonsterPlainsCaveFarmMenu.TYPE,
          MonsterPlainsCaveFarmScreen<NetheriteMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(NetheriteNetherFortressFarmMenu.TYPE,
          NetherFortressFarmScreen<NetheriteNetherFortressFarmMenu>::new);
      MenuScreens.register(NetheriteOceanFarmMenu.TYPE,
          OceanFarmScreen<NetheriteOceanFarmMenu>::new);
      MenuScreens.register(NetheriteSwampFarmMenu.TYPE,
          SwampFarmScreen<NetheriteSwampFarmMenu>::new);

      // Special Mob Farm UI screen
      MenuScreens.register(CreativeMobFarmMenu.TYPE, CreativeMobFarmScreen::new);
      MenuScreens.register(IronGolemFarmMenu.TYPE, SpecialFarmScreen<IronGolemFarmMenu>::new);

    });
  }
}
