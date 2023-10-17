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
import de.markusbordihn.easymobfarm.client.screen.farm.BeeHiveFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.CreativeMobFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.DesertFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.SpecialFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.JungleFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.MonsterPlainsCaveFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.NetherFortressFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.OceanFarmScreen;
import de.markusbordihn.easymobfarm.client.screen.farm.SwampFarmScreen;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;
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
import de.markusbordihn.easymobfarm.menu.farm.special.IronGolemFarmMenu;

public class ClientScreens {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientScreens() {}

  public static void registerScreens(final FMLClientSetupEvent event) {
    log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);

    event.enqueueWork(() -> {
      // Copper Mob Farm UI screen
      MenuScreens.register(ModMenuTypes.COPPER_ANIMAL_PLAINS_FARM_MENU.get(),
          AnimalPlainsFarmScreen<CopperAnimalPlainsFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.COPPER_BEE_HIVE_FARM_MENU.get(),
          BeeHiveFarmScreen<CopperBeeHiveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.COPPER_DESERT_FARM_MENU.get(),
          DesertFarmScreen<CopperDesertFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.COPPER_JUNGLE_FARM_MENU.get(),
          JungleFarmScreen<CopperJungleFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.COPPER_MONSTER_PLAINS_CAVE_FARM_MENU.get(),
          MonsterPlainsCaveFarmScreen<CopperMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.COPPER_NETHER_FORTRESS_FARM_MENU.get(),
          NetherFortressFarmScreen<CopperNetherFortressFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.COPPER_OCEAN_FARM_MENU.get(),
          OceanFarmScreen<CopperOceanFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.COPPER_SWAMP_FARM_MENU.get(),
          SwampFarmScreen<CopperSwampFarmMenu>::new);

      // Gold Mob Farm UI screen
      MenuScreens.register(ModMenuTypes.GOLD_ANIMAL_PLAINS_FARM_MENU.get(),
          AnimalPlainsFarmScreen<GoldAnimalPlainsFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.GOLD_BEE_HIVE_FARM_MENU.get(),
          BeeHiveFarmScreen<GoldBeeHiveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.GOLD_DESERT_FARM_MENU.get(),
          DesertFarmScreen<GoldDesertFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.GOLD_JUNGLE_FARM_MENU.get(),
          JungleFarmScreen<GoldJungleFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.GOLD_MONSTER_PLAINS_CAVE_FARM_MENU.get(),
          MonsterPlainsCaveFarmScreen<GoldMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.GOLD_NETHER_FORTRESS_FARM_MENU.get(),
          NetherFortressFarmScreen<GoldNetherFortressFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.GOLD_OCEAN_FARM_MENU.get(),
          OceanFarmScreen<GoldOceanFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.GOLD_SWAMP_FARM_MENU.get(),
          SwampFarmScreen<GoldSwampFarmMenu>::new);

      // Iron Mob Farm UI screen
      MenuScreens.register(ModMenuTypes.IRON_ANIMAL_PLAINS_FARM_MENU.get(),
          AnimalPlainsFarmScreen<IronAnimalPlainsFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.IRON_BEE_HIVE_FARM_MENU.get(),
          BeeHiveFarmScreen<IronBeeHiveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.IRON_DESERT_FARM_MENU.get(),
          DesertFarmScreen<IronDesertFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.IRON_JUNGLE_FARM_MENU.get(),
          JungleFarmScreen<IronJungleFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.IRON_MONSTER_PLAINS_CAVE_FARM_MENU.get(),
          MonsterPlainsCaveFarmScreen<IronMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.IRON_NETHER_FORTRESS_FARM_MENU.get(),
          NetherFortressFarmScreen<IronNetherFortressFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.IRON_OCEAN_FARM_MENU.get(),
          OceanFarmScreen<IronOceanFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.IRON_SWAMP_FARM_MENU.get(),
          SwampFarmScreen<IronSwampFarmMenu>::new);

      // Netherite Mob Farm UI screen
      MenuScreens.register(ModMenuTypes.NETHERITE_ANIMAL_PLAINS_FARM_MENU.get(),
          AnimalPlainsFarmScreen<NetheriteAnimalPlainsFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.NETHERITE_BEE_HIVE_FARM_MENU.get(),
          BeeHiveFarmScreen<NetheriteBeeHiveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.NETHERITE_DESERT_FARM_MENU.get(),
          DesertFarmScreen<NetheriteDesertFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.NETHERITE_JUNGLE_FARM_MENU.get(),
          JungleFarmScreen<NetheriteJungleFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.NETHERITE_MONSTER_PLAINS_CAVE_FARM_MENU.get(),
          MonsterPlainsCaveFarmScreen<NetheriteMonsterPlainsCaveFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.NETHERITE_NETHER_FORTRESS_FARM_MENU.get(),
          NetherFortressFarmScreen<NetheriteNetherFortressFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.NETHERITE_OCEAN_FARM_MENU.get(),
          OceanFarmScreen<NetheriteOceanFarmMenu>::new);
      MenuScreens.register(ModMenuTypes.NETHERITE_SWAMP_FARM_MENU.get(),
          SwampFarmScreen<NetheriteSwampFarmMenu>::new);

      // Special Mob Farm UI screen
      MenuScreens.register(ModMenuTypes.CREATIVE_FARM_MENU.get(), CreativeMobFarmScreen::new);
      MenuScreens.register(ModMenuTypes.IRON_GOLEM_FARM_MENU.get(),
          SpecialFarmScreen<IronGolemFarmMenu>::new);

    });
  }
}
