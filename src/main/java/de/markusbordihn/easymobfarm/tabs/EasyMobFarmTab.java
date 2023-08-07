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

package de.markusbordihn.easymobfarm.tabs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.ModItems;

public class EasyMobFarmTab {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected EasyMobFarmTab() {}

  public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

  public static final RegistryObject<CreativeModeTab> MOB_FARM = CREATIVE_TABS.register("mob_farm",
      () -> CreativeModeTab.builder()
          .icon(() -> ModItems.IRON_ANIMAL_PLAINS_FARM.get().getDefaultInstance())
          .displayItems(new MobFarmItems())
          .title(Component.translatable("itemGroup.easy_mob_farm.mob_farm")).build());

  public static final RegistryObject<CreativeModeTab> TOOLS = CREATIVE_TABS.register("tools",
      () -> CreativeModeTab.builder().icon(() -> ModItems.COLLAR_SMALL.get().getDefaultInstance())
          .displayItems(new MobCaptureItems())
          .title(Component.translatable("itemGroup.easy_mob_farm.tools")).build());

  public static void handleCreativeModeTab(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
      // Mob Farms Templates
      event.accept(ModItems.COPPER_MOB_FARM_TEMPLATE.get());
      event.accept(ModItems.IRON_MOB_FARM_TEMPLATE.get());
      event.accept(ModItems.GOLD_MOB_FARM_TEMPLATE.get());
      event.accept(ModItems.NETHERITE_MOB_FARM_TEMPLATE.get());
    }
  }

}
