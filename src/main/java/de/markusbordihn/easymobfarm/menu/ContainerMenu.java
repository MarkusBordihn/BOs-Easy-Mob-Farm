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

package de.markusbordihn.easymobfarm.menu;

import de.markusbordihn.easymobfarm.Constants;
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
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.event.RegistryEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerMenu {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ContainerMenu() {
  }

  public static void registerContainerMenu(RegistryEvent.Register<MenuType<?>> event) {
    log.info("{} Container Menu ...", Constants.LOG_REGISTER_PREFIX);

    // Copper Mob Farm Container Menu
    event.getRegistry().registerAll(new MenuType<>(CopperAnimalPlainsFarmMenu::new)
        .setRegistryName(Constants.COPPER_ANIMAL_PLAINS_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(CopperBeeHiveFarmMenu::new).setRegistryName(Constants.COPPER_BEE_HIVE_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(CopperDesertFarmMenu::new).setRegistryName(Constants.COPPER_DESERT_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(CopperJungleFarmMenu::new).setRegistryName(Constants.COPPER_JUNGLE_FARM));
    event.getRegistry().registerAll(new MenuType<>(CopperMonsterPlainsCaveFarmMenu::new)
        .setRegistryName(Constants.COPPER_MONSTER_PLAINS_CAVE_FARM));
    event.getRegistry().registerAll(new MenuType<>(CopperNetherFortressFarmMenu::new)
        .setRegistryName(Constants.COPPER_NETHER_FORTRESS_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(CopperOceanFarmMenu::new).setRegistryName(Constants.COPPER_OCEAN_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(CopperSwampFarmMenu::new).setRegistryName(Constants.COPPER_SWAMP_FARM));

    // Gold Mob Farm Container Menu
    event.getRegistry().registerAll(new MenuType<>(GoldAnimalPlainsFarmMenu::new)
        .setRegistryName(Constants.GOLD_ANIMAL_PLAINS_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(GoldBeeHiveFarmMenu::new).setRegistryName(Constants.GOLD_BEE_HIVE_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(GoldDesertFarmMenu::new).setRegistryName(Constants.GOLD_DESERT_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(GoldJungleFarmMenu::new).setRegistryName(Constants.GOLD_JUNGLE_FARM));
    event.getRegistry().registerAll(new MenuType<>(GoldMonsterPlainsCaveFarmMenu::new)
        .setRegistryName(Constants.GOLD_MONSTER_PLAINS_CAVE_FARM));
    event.getRegistry().registerAll(new MenuType<>(GoldNetherFortressFarmMenu::new)
        .setRegistryName(Constants.GOLD_NETHER_FORTRESS_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(GoldOceanFarmMenu::new).setRegistryName(Constants.GOLD_OCEAN_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(GoldSwampFarmMenu::new).setRegistryName(Constants.GOLD_SWAMP_FARM));

    // Iron Mob Farm Container Menu
    event.getRegistry().registerAll(new MenuType<>(IronAnimalPlainsFarmMenu::new)
        .setRegistryName(Constants.IRON_ANIMAL_PLAINS_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(IronBeeHiveFarmMenu::new).setRegistryName(Constants.IRON_BEE_HIVE_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(IronDesertFarmMenu::new).setRegistryName(Constants.IRON_DESERT_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(IronJungleFarmMenu::new).setRegistryName(Constants.IRON_JUNGLE_FARM));
    event.getRegistry().registerAll(new MenuType<>(IronMonsterPlainsCaveFarmMenu::new)
        .setRegistryName(Constants.IRON_MONSTER_PLAINS_CAVE_FARM));
    event.getRegistry().registerAll(new MenuType<>(IronNetherFortressFarmMenu::new)
        .setRegistryName(Constants.IRON_NETHER_FORTRESS_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(IronOceanFarmMenu::new).setRegistryName(Constants.IRON_OCEAN_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(IronSwampFarmMenu::new).setRegistryName(Constants.IRON_SWAMP_FARM));

    // Netherite Mob Farm Container Menu
    event.getRegistry().registerAll(new MenuType<>(NetheriteAnimalPlainsFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_ANIMAL_PLAINS_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetheriteBeeHiveFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_BEE_HIVE_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetheriteDesertFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_DESERT_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetheriteJungleFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_JUNGLE_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetheriteMonsterPlainsCaveFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_MONSTER_PLAINS_CAVE_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetheriteNetherFortressFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_NETHER_FORTRESS_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetheriteOceanFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_OCEAN_FARM));
    event.getRegistry().registerAll(new MenuType<>(NetheriteSwampFarmMenu::new)
        .setRegistryName(Constants.NETHERITE_SWAMP_FARM));

    // Special Mob Farm
    event.getRegistry().registerAll(
        new MenuType<>(CreativeMobFarmMenu::new).setRegistryName(Constants.CREATIVE_MOB_FARM));
    event.getRegistry().registerAll(
        new MenuType<>(IronGolemFarmMenu::new).setRegistryName(Constants.IRON_GOLEM_FARM));

    event.getRegistry()
        .registerAll(new MenuType<>(MobFarmMenu::new).setRegistryName(Constants.MOB_FARM));
  }

}
