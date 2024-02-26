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
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

  public static final DeferredRegister<MenuType<?>> MENU_TYPES =
      DeferredRegister.create(ForgeRegistries.MENU_TYPES, Constants.MOD_ID);
  // Copper Mob Farm Container Menu
  public static final RegistryObject<MenuType<CopperAnimalPlainsFarmMenu>> COPPER_ANIMAL_PLAINS_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_ANIMAL_PLAINS_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new CopperAnimalPlainsFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CopperBeeHiveFarmMenu>> COPPER_BEE_HIVE_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_BEE_HIVE_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new CopperBeeHiveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CopperDesertFarmMenu>> COPPER_DESERT_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_DESERT_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new CopperDesertFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CopperJungleFarmMenu>> COPPER_JUNGLE_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_JUNGLE_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new CopperJungleFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CopperMonsterPlainsCaveFarmMenu>> COPPER_MONSTER_PLAINS_CAVE_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_MONSTER_PLAINS_CAVE_FARM,
          () -> IForgeMenuType.create((windowIdIn, inventory,
              data) -> new CopperMonsterPlainsCaveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CopperNetherFortressFarmMenu>> COPPER_NETHER_FORTRESS_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_NETHER_FORTRESS_FARM,
          () -> IForgeMenuType.create((windowIdIn, inventory,
              data) -> new CopperNetherFortressFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CopperOceanFarmMenu>> COPPER_OCEAN_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_OCEAN_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new CopperOceanFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<CopperSwampFarmMenu>> COPPER_SWAMP_FARM_MENU =
      MENU_TYPES.register(Constants.COPPER_SWAMP_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new CopperSwampFarmMenu(windowIdIn, inventory)));
  // Gold Mob Farm Container Menu
  public static final RegistryObject<MenuType<GoldAnimalPlainsFarmMenu>> GOLD_ANIMAL_PLAINS_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_ANIMAL_PLAINS_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new GoldAnimalPlainsFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<GoldBeeHiveFarmMenu>> GOLD_BEE_HIVE_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_BEE_HIVE_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new GoldBeeHiveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<GoldDesertFarmMenu>> GOLD_DESERT_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_DESERT_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new GoldDesertFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<GoldJungleFarmMenu>> GOLD_JUNGLE_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_JUNGLE_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new GoldJungleFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<GoldMonsterPlainsCaveFarmMenu>> GOLD_MONSTER_PLAINS_CAVE_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_MONSTER_PLAINS_CAVE_FARM,
          () -> IForgeMenuType.create((windowIdIn, inventory,
              data) -> new GoldMonsterPlainsCaveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<GoldNetherFortressFarmMenu>> GOLD_NETHER_FORTRESS_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_NETHER_FORTRESS_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new GoldNetherFortressFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<GoldOceanFarmMenu>> GOLD_OCEAN_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_OCEAN_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new GoldOceanFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<GoldSwampFarmMenu>> GOLD_SWAMP_FARM_MENU =
      MENU_TYPES.register(Constants.GOLD_SWAMP_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new GoldSwampFarmMenu(windowIdIn, inventory)));
  // Iron Mob Farm Container Menu
  public static final RegistryObject<MenuType<IronAnimalPlainsFarmMenu>> IRON_ANIMAL_PLAINS_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_ANIMAL_PLAINS_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new IronAnimalPlainsFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronBeeHiveFarmMenu>> IRON_BEE_HIVE_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_BEE_HIVE_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new IronBeeHiveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronDesertFarmMenu>> IRON_DESERT_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_DESERT_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new IronDesertFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronJungleFarmMenu>> IRON_JUNGLE_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_JUNGLE_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new IronJungleFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronMonsterPlainsCaveFarmMenu>> IRON_MONSTER_PLAINS_CAVE_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_MONSTER_PLAINS_CAVE_FARM,
          () -> IForgeMenuType.create((windowIdIn, inventory,
              data) -> new IronMonsterPlainsCaveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronNetherFortressFarmMenu>> IRON_NETHER_FORTRESS_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_NETHER_FORTRESS_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new IronNetherFortressFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronOceanFarmMenu>> IRON_OCEAN_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_OCEAN_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new IronOceanFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronSwampFarmMenu>> IRON_SWAMP_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_SWAMP_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new IronSwampFarmMenu(windowIdIn, inventory)));
  // Netherite Mob Farm Container Menu
  public static final RegistryObject<MenuType<NetheriteAnimalPlainsFarmMenu>> NETHERITE_ANIMAL_PLAINS_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_ANIMAL_PLAINS_FARM,
          () -> IForgeMenuType.create((windowIdIn, inventory,
              data) -> new NetheriteAnimalPlainsFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<NetheriteBeeHiveFarmMenu>> NETHERITE_BEE_HIVE_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_BEE_HIVE_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new NetheriteBeeHiveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<NetheriteDesertFarmMenu>> NETHERITE_DESERT_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_DESERT_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new NetheriteDesertFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<NetheriteJungleFarmMenu>> NETHERITE_JUNGLE_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_JUNGLE_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new NetheriteJungleFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<NetheriteMonsterPlainsCaveFarmMenu>> NETHERITE_MONSTER_PLAINS_CAVE_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_MONSTER_PLAINS_CAVE_FARM,
          () -> IForgeMenuType.create((windowIdIn, inventory,
              data) -> new NetheriteMonsterPlainsCaveFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<NetheriteNetherFortressFarmMenu>> NETHERITE_NETHER_FORTRESS_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_NETHER_FORTRESS_FARM,
          () -> IForgeMenuType.create((windowIdIn, inventory,
              data) -> new NetheriteNetherFortressFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<NetheriteOceanFarmMenu>> NETHERITE_OCEAN_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_OCEAN_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new NetheriteOceanFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<NetheriteSwampFarmMenu>> NETHERITE_SWAMP_FARM_MENU =
      MENU_TYPES.register(Constants.NETHERITE_SWAMP_FARM, () -> IForgeMenuType.create(
          (windowIdIn, inventory, data) -> new NetheriteSwampFarmMenu(windowIdIn, inventory)));
  // Special Mob Farm
  public static final RegistryObject<MenuType<CreativeMobFarmMenu>> CREATIVE_FARM_MENU =
      MENU_TYPES.register(Constants.CREATIVE_MOB_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new CreativeMobFarmMenu(windowIdIn, inventory)));
  public static final RegistryObject<MenuType<IronGolemFarmMenu>> IRON_GOLEM_FARM_MENU =
      MENU_TYPES.register(Constants.IRON_GOLEM_FARM, () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new IronGolemFarmMenu(windowIdIn, inventory)));
  // Other
  public static final RegistryObject<MenuType<MobFarmMenu>> MOB_FARM_MENU =
      MENU_TYPES.register("mob_farm", () -> IForgeMenuType
          .create((windowIdIn, inventory, data) -> new MobFarmMenu(windowIdIn, inventory)));

  protected ModMenuTypes() {

  }
}
