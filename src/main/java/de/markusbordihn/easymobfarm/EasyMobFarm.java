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

package de.markusbordihn.easymobfarm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.world.inventory.MenuType;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.client.item.ModItemProperties;
import de.markusbordihn.easymobfarm.client.renderer.ClientRenderer;
import de.markusbordihn.easymobfarm.client.screen.ClientScreens;
import de.markusbordihn.easymobfarm.datafixer.ModDataFixer;
import de.markusbordihn.easymobfarm.item.ModItems;
import de.markusbordihn.easymobfarm.menu.ContainerMenu;

@Mod(Constants.MOD_ID)
public class EasyMobFarm {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public EasyMobFarm() {
    final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    log.info("{} Data Fixer ...", Constants.LOG_REGISTER_PREFIX);
    MinecraftForge.EVENT_BUS.register(ModDataFixer.class);

    log.info("{} Container Menus ...", Constants.LOG_REGISTER_PREFIX);
    modEventBus.addGenericListener(MenuType.class, ContainerMenu::registerContainerMenu);

    log.info("{} Items ...", Constants.LOG_REGISTER_PREFIX);
    ModItems.ITEMS.register(modEventBus);

    log.info("{} Blocks ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.BLOCKS.register(modEventBus);

    log.info("{} Block Entities ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.ENTITIES.register(modEventBus);

    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
      modEventBus.addListener(ClientRenderer::registerBlockEntityRenderers);
      modEventBus.addListener(ClientRenderer::registerRenderLayers);
      modEventBus.addListener(ClientScreens::registerScreens);
      modEventBus.addListener(ModItemProperties::registerItemProperties);
    });
  }

}
