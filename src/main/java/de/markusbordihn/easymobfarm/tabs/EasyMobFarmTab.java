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

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.CreativeModeTabEvent;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.ModItems;

public class EasyMobFarmTab {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected EasyMobFarmTab() {}

  protected static CreativeModeTab MOB_FARM;

  protected static CreativeModeTab TOOLS;

  public static void handleCreativeModeTabRegister(CreativeModeTabEvent.Register event) {

    log.info("{} creative mod tabs ...", Constants.LOG_REGISTER_PREFIX);

    MOB_FARM = event.registerCreativeModeTab(new ResourceLocation(Constants.MOD_ID, "mob_farm"),
        builder -> {
          builder.icon(() -> new ItemStack(ModItems.ANIMAL_PLAINS_FARM.get()))
              .displayItems(new MobFarmItems())
              .title(Component.translatable("itemGroup.easy_mob_farm.mob_farm")).build();
        });

    TOOLS =
        event.registerCreativeModeTab(new ResourceLocation(Constants.MOD_ID, "tools"), builder -> {
          builder.icon(() -> new ItemStack(ModItems.COLLAR_SMALL.get()))
              .displayItems(new MobCaptureItems())
              .title(Component.translatable("itemGroup.easy_mob_farm.tools")).build();
        });
  }

  public static void handleCreativeModeTab(CreativeModeTabEvent.BuildContents event) {
    if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
      event.accept(ModItems.IRON_MOB_FARM_TEMPLATE.get());
      event.accept(ModItems.STEEL_MOB_FARM_TEMPLATE.get());
    }
  }

}
