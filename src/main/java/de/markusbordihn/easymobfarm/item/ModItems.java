/**
 * Copyright 2021 Markus Bordihn
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
import de.markusbordihn.easymobfarm.block.AnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.SkeletonMobFarm;
import de.markusbordihn.easymobfarm.item.capture.CollarSmallItem;
import de.markusbordihn.easymobfarm.tabs.EasyMobFarmTab;
import de.markusbordihn.easymobfarm.Annotations.TemplateEntryPoint;

public class ModItems {

  protected ModItems() {

  }

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  @TemplateEntryPoint("Register Items")
  public static final RegistryObject<Item> CAPTURE_NET =
      ITEMS.register("capture_net", () -> new CaptureNetItem(
          new Item.Properties().stacksTo(1).durability(10).tab(EasyMobFarmTab.TOOLS)));

  // Collars Items
  public static final RegistryObject<Item> COLLAR_SMALL =
      ITEMS.register("collar_small", () -> new CollarSmallItem(
          new Item.Properties().stacksTo(1).durability(10).tab(EasyMobFarmTab.TOOLS)));

  @TemplateEntryPoint("Register Block Items")

  // Mob Farms
  public static final RegistryObject<Item> ANIMAL_PLAINS_FARM =
      ITEMS.register(AnimalPlainsFarm.NAME, () -> new BlockItem(ModBlocks.ANIMAL_PLAINS_FARM.get(),
          new Item.Properties().tab(EasyMobFarmTab.MOB_FARM)));
  public static final RegistryObject<Item> SKELETON_MOB_FARM =
      ITEMS.register(SkeletonMobFarm.NAME, () -> new BlockItem(ModBlocks.SKELETON_MOB_FARM.get(),
          new Item.Properties().tab(EasyMobFarmTab.MOB_FARM)));

  String test = "";
}
