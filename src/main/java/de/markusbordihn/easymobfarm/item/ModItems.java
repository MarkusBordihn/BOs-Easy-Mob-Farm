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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.AnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.CreativeMobFarm;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.MonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.OceanFarm;
import de.markusbordihn.easymobfarm.item.mobcatcher.CollarSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.CreativeMobCatcher;
import de.markusbordihn.easymobfarm.item.mobcatcher.Net;
import de.markusbordihn.easymobfarm.item.mobcatcher.UrnSmall;
import de.markusbordihn.easymobfarm.tabs.EasyMobFarmTab;
import de.markusbordihn.easymobfarm.Annotations.TemplateEntryPoint;

public class ModItems {

  protected ModItems() {

  }

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  @TemplateEntryPoint("Register Items")

  // Mob Capture Items
  public static final RegistryObject<Item> CREATIVE_MOB_CATCHER =
      ITEMS.register("creative_mob_catcher", () -> new CreativeMobCatcher(
          new Item.Properties().stacksTo(1).durability(1000).tab(EasyMobFarmTab.TOOLS)));
  public static final RegistryObject<Item> CAPTURE_NET = ITEMS.register("capture_net",
      () -> new Net(new Item.Properties().stacksTo(1).durability(11).tab(EasyMobFarmTab.TOOLS)));
  public static final RegistryObject<Item> COLLAR_SMALL =
      ITEMS.register("collar_small", () -> new CollarSmall(
          new Item.Properties().stacksTo(1).durability(11).tab(EasyMobFarmTab.TOOLS)));
  public static final RegistryObject<Item> URN_SMALL =
      ITEMS.register("urn_small", () -> new UrnSmall(
          new Item.Properties().stacksTo(1).durability(11).tab(EasyMobFarmTab.TOOLS)));

  @TemplateEntryPoint("Register Block Items")

  public static final RegistryObject<Item> OCEAN_FARM =
      ITEMS.register(OceanFarm.NAME, () -> new BlockItem(ModBlocks.OCEAN_FARM.get(),
          new Item.Properties().tab(EasyMobFarmTab.MOB_FARM)));

  // Mob Farm Templates
  public static final RegistryObject<Item> IRON_MOB_FARM_TEMPLATE =
      ITEMS.register("iron_mob_farm_template",
          () -> new MobFarmTemplateItem(ModBlocks.IRON_MOB_FARM_TEMPLATE.get(),
              new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

  // Mob Farms
  public static final RegistryObject<Item> CREATIVE_MOB_FARM =
      ITEMS.register(CreativeMobFarm.NAME, () -> new BlockItem(ModBlocks.CREATIVE_MOB_FARM.get(),
          new Item.Properties().tab(EasyMobFarmTab.MOB_FARM)));
  public static final RegistryObject<Item> ANIMAL_PLAINS_FARM =
      ITEMS.register(AnimalPlainsFarm.NAME, () -> new BlockItem(ModBlocks.ANIMAL_PLAINS_FARM.get(),
          new Item.Properties().tab(EasyMobFarmTab.MOB_FARM)));
  public static final RegistryObject<Item> MONSTER_PLAINS_CAVE_FARM = ITEMS.register(
      MonsterPlainsCaveFarm.NAME, () -> new BlockItem(ModBlocks.MONSTER_PLAINS_CAVE_FARM.get(),
          new Item.Properties().tab(EasyMobFarmTab.MOB_FARM)));
}
