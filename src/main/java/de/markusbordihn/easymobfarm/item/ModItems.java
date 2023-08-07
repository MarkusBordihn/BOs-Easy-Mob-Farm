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

package de.markusbordihn.easymobfarm.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.CreativeMobFarm;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteSwampFarm;
import de.markusbordihn.easymobfarm.item.mobcatcher.FishingBowl;
import de.markusbordihn.easymobfarm.item.mobcatcher.CatchCage;
import de.markusbordihn.easymobfarm.item.mobcatcher.CatchCageSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.CollarSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.CreativeMobCatcher;
import de.markusbordihn.easymobfarm.item.mobcatcher.EnderLasso;
import de.markusbordihn.easymobfarm.item.mobcatcher.FishingNetSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.GoldenLasso;
import de.markusbordihn.easymobfarm.item.mobcatcher.InsectNet;
import de.markusbordihn.easymobfarm.item.mobcatcher.Net;
import de.markusbordihn.easymobfarm.item.mobcatcher.NetheriteLasso;
import de.markusbordihn.easymobfarm.item.mobcatcher.UrnSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.WitchBottle;
import de.markusbordihn.easymobfarm.Annotations.TemplateEntryPoint;

public class ModItems {

  protected ModItems() {

  }

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  @TemplateEntryPoint("Register Items")

  // Mob Capture Items
  public static final RegistryObject<Item> CAPTURE_NET = ITEMS.register("capture_net",
      () -> new Net(new Item.Properties().stacksTo(1).durability(1000)));
  public static final RegistryObject<Item> CATCH_CAGE = ITEMS.register("catch_cage",
      () -> new CatchCage(new Item.Properties().stacksTo(1).durability(15)));
  public static final RegistryObject<Item> CATCH_CAGE_SMALL = ITEMS.register("catch_cage_small",
      () -> new CatchCageSmall(new Item.Properties().stacksTo(1).durability(20)));
  public static final RegistryObject<Item> COLLAR_SMALL = ITEMS.register("collar_small",
      () -> new CollarSmall(new Item.Properties().stacksTo(1).durability(20)));
  public static final RegistryObject<Item> CREATIVE_MOB_CATCHER =
      ITEMS.register("creative_mob_catcher",
          () -> new CreativeMobCatcher(new Item.Properties().stacksTo(1).durability(1000)));
  public static final RegistryObject<Item> ENDER_LASSO = ITEMS.register("ender_lasso",
      () -> new EnderLasso(new Item.Properties().stacksTo(1).durability(10)));
  public static final RegistryObject<Item> FISHING_BOWL_SMALL = ITEMS.register("fishing_bowl",
      () -> new FishingBowl(new Item.Properties().stacksTo(1).durability(20)));
  public static final RegistryObject<Item> FISHING_NET_SMALL = ITEMS.register("fishing_net_small",
      () -> new FishingNetSmall(new Item.Properties().stacksTo(1).durability(20)));
  public static final RegistryObject<Item> GOLDEN_LASSO = ITEMS.register("golden_lasso",
      () -> new GoldenLasso(new Item.Properties().stacksTo(1).durability(10)));
  public static final RegistryObject<Item> INSECT_NET = ITEMS.register("insect_net",
      () -> new InsectNet(new Item.Properties().stacksTo(1).durability(20)));
  public static final RegistryObject<Item> NETHERITE_LASSO = ITEMS.register("netherite_lasso",
      () -> new NetheriteLasso(new Item.Properties().stacksTo(1).durability(40)));
  public static final RegistryObject<Item> URN_SMALL = ITEMS.register("urn_small",
      () -> new UrnSmall(new Item.Properties().stacksTo(1).durability(15)));
  public static final RegistryObject<Item> WITCH_BOTTLE = ITEMS.register("witch_bottle",
      () -> new WitchBottle(new Item.Properties().stacksTo(1).durability(15)));

  @TemplateEntryPoint("Register Block Items")

  // Mob Farm Templates
  public static final RegistryObject<Item> COPPER_MOB_FARM_TEMPLATE =
      ITEMS.register("copper_mob_farm_template",
          () -> new MobFarmTemplateItem(ModBlocks.COPPER_MOB_FARM_TEMPLATE.get(),
              new Item.Properties()));
  public static final RegistryObject<Item> IRON_MOB_FARM_TEMPLATE = ITEMS.register(
      "iron_mob_farm_template",
      () -> new MobFarmTemplateItem(ModBlocks.IRON_MOB_FARM_TEMPLATE.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_MOB_FARM_TEMPLATE = ITEMS.register(
      "gold_mob_farm_template",
      () -> new MobFarmTemplateItem(ModBlocks.GOLD_MOB_FARM_TEMPLATE.get(), new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_MOB_FARM_TEMPLATE =
      ITEMS.register("netherite_mob_farm_template",
          () -> new MobFarmTemplateItem(ModBlocks.NETHERITE_MOB_FARM_TEMPLATE.get(),
              new Item.Properties()));

  // Mob Farms - Tier Copper
  public static final RegistryObject<Item> COPPER_ANIMAL_PLAINS_FARM =
      ITEMS.register(CopperAnimalPlainsFarm.NAME,
          () -> new BlockItem(ModBlocks.COPPER_ANIMAL_PLAINS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> COPPER_BEE_HIVE_FARM =
      ITEMS.register(CopperBeeHiveFarm.NAME,
          () -> new BlockItem(ModBlocks.COPPER_BEE_HIVE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> COPPER_DESERT_FARM =
      ITEMS.register(CopperDesertFarm.NAME,
          () -> new BlockItem(ModBlocks.COPPER_DESERT_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> COPPER_JUNGLE_FARM =
      ITEMS.register(CopperJungleFarm.NAME,
          () -> new BlockItem(ModBlocks.COPPER_JUNGLE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> COPPER_MONSTER_PLAINS_CAVE_FARM = ITEMS.register(
      CopperMonsterPlainsCaveFarm.NAME,
      () -> new BlockItem(ModBlocks.COPPER_MONSTER_PLAINS_CAVE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> COPPER_NETHER_FORTRESS_FARM =
      ITEMS.register(CopperNetherFortressFarm.NAME,
          () -> new BlockItem(ModBlocks.COPPER_NETHER_FORTRESS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> COPPER_OCEAN_FARM = ITEMS.register(CopperOceanFarm.NAME,
      () -> new BlockItem(ModBlocks.COPPER_OCEAN_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> COPPER_SWAMP_FARM = ITEMS.register(CopperSwampFarm.NAME,
      () -> new BlockItem(ModBlocks.COPPER_SWAMP_FARM.get(), new Item.Properties()));

  // Mob Farms - Tier Iron
  public static final RegistryObject<Item> IRON_ANIMAL_PLAINS_FARM =
      ITEMS.register(IronAnimalPlainsFarm.NAME,
          () -> new BlockItem(ModBlocks.IRON_ANIMAL_PLAINS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> IRON_BEE_HIVE_FARM = ITEMS.register(IronBeeHiveFarm.NAME,
      () -> new BlockItem(ModBlocks.IRON_BEE_HIVE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> IRON_DESERT_FARM = ITEMS.register(IronDesertFarm.NAME,
      () -> new BlockItem(ModBlocks.IRON_DESERT_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> IRON_JUNGLE_FARM = ITEMS.register(IronJungleFarm.NAME,
      () -> new BlockItem(ModBlocks.IRON_JUNGLE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> IRON_MONSTER_PLAINS_CAVE_FARM = ITEMS.register(
      IronMonsterPlainsCaveFarm.NAME,
      () -> new BlockItem(ModBlocks.IRON_MONSTER_PLAINS_CAVE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> IRON_NETHER_FORTRESS_FARM =
      ITEMS.register(IronNetherFortressFarm.NAME,
          () -> new BlockItem(ModBlocks.IRON_NETHER_FORTRESS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> IRON_OCEAN_FARM = ITEMS.register(IronOceanFarm.NAME,
      () -> new BlockItem(ModBlocks.IRON_OCEAN_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> IRON_SWAMP_FARM = ITEMS.register(IronSwampFarm.NAME,
      () -> new BlockItem(ModBlocks.IRON_SWAMP_FARM.get(), new Item.Properties()));

  // Mob Farms - Tier Gold
  public static final RegistryObject<Item> GOLD_ANIMAL_PLAINS_FARM =
      ITEMS.register(GoldAnimalPlainsFarm.NAME,
          () -> new BlockItem(ModBlocks.GOLD_ANIMAL_PLAINS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_BEE_HIVE_FARM = ITEMS.register(GoldBeeHiveFarm.NAME,
      () -> new BlockItem(ModBlocks.GOLD_BEE_HIVE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_DESERT_FARM = ITEMS.register(GoldDesertFarm.NAME,
      () -> new BlockItem(ModBlocks.GOLD_DESERT_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_JUNGLE_FARM = ITEMS.register(GoldJungleFarm.NAME,
      () -> new BlockItem(ModBlocks.GOLD_JUNGLE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_MONSTER_PLAINS_CAVE_FARM = ITEMS.register(
      GoldMonsterPlainsCaveFarm.NAME,
      () -> new BlockItem(ModBlocks.GOLD_MONSTER_PLAINS_CAVE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_NETHER_FORTRESS_FARM =
      ITEMS.register(GoldNetherFortressFarm.NAME,
          () -> new BlockItem(ModBlocks.GOLD_NETHER_FORTRESS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_OCEAN_FARM = ITEMS.register(GoldOceanFarm.NAME,
      () -> new BlockItem(ModBlocks.GOLD_OCEAN_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> GOLD_SWAMP_FARM = ITEMS.register(GoldSwampFarm.NAME,
      () -> new BlockItem(ModBlocks.GOLD_SWAMP_FARM.get(), new Item.Properties()));

  // Mob Farms - Tier Netherite
  public static final RegistryObject<Item> NETHERITE_ANIMAL_PLAINS_FARM =
      ITEMS.register(NetheriteAnimalPlainsFarm.NAME,
          () -> new BlockItem(ModBlocks.NETHERITE_ANIMAL_PLAINS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_BEE_HIVE_FARM =
      ITEMS.register(NetheriteBeeHiveFarm.NAME,
          () -> new BlockItem(ModBlocks.NETHERITE_BEE_HIVE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_DESERT_FARM =
      ITEMS.register(NetheriteDesertFarm.NAME,
          () -> new BlockItem(ModBlocks.NETHERITE_DESERT_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_JUNGLE_FARM =
      ITEMS.register(NetheriteJungleFarm.NAME,
          () -> new BlockItem(ModBlocks.NETHERITE_JUNGLE_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_MONSTER_PLAINS_CAVE_FARM =
      ITEMS.register(NetheriteMonsterPlainsCaveFarm.NAME,
          () -> new BlockItem(ModBlocks.NETHERITE_MONSTER_PLAINS_CAVE_FARM.get(),
              new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_NETHER_FORTRESS_FARM = ITEMS.register(
      NetheriteNetherFortressFarm.NAME,
      () -> new BlockItem(ModBlocks.NETHERITE_NETHER_FORTRESS_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_OCEAN_FARM =
      ITEMS.register(NetheriteOceanFarm.NAME,
          () -> new BlockItem(ModBlocks.NETHERITE_OCEAN_FARM.get(), new Item.Properties()));
  public static final RegistryObject<Item> NETHERITE_SWAMP_FARM =
      ITEMS.register(NetheriteSwampFarm.NAME,
          () -> new BlockItem(ModBlocks.NETHERITE_SWAMP_FARM.get(), new Item.Properties()));

  // Mob Farms - Creative
  public static final RegistryObject<Item> CREATIVE_MOB_FARM = ITEMS.register(CreativeMobFarm.NAME,
      () -> new BlockItem(ModBlocks.CREATIVE_MOB_FARM.get(), new Item.Properties()));
}
