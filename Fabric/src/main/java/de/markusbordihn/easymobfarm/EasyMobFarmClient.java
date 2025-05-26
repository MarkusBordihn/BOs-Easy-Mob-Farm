/*
 * Copyright 2024 Markus Bordihn
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

import de.markusbordihn.easymobfarm.client.ClientEventHandler;
import de.markusbordihn.easymobfarm.client.renderer.ClientRenderer;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.ModItemProperties;
import de.markusbordihn.easymobfarm.client.screen.ClientScreens;
import de.markusbordihn.easymobfarm.network.ClientNetworkHandler;
import de.markusbordihn.easymobfarm.tabs.ModTabs;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyMobFarmClient implements ClientModInitializer {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @Override
  public void onInitializeClient() {
    log.info("Initializing {} (Fabric-Client) ...", Constants.MOD_NAME);

    log.info("{} Tabs ...", Constants.LOG_REGISTER_PREFIX);
    ModTabs.registerModTabs();

    log.info("{} Block Entity Renderers ...", Constants.LOG_REGISTER_PREFIX);
    ClientRenderer.registerBlockEntityRenderers();

    log.info("{} Render Layers ...", Constants.LOG_REGISTER_PREFIX);
    ClientRenderer.registerRenderLayers();

    log.info("{} Client Screens ...", Constants.LOG_REGISTER_PREFIX);
    ClientScreens.registerScreens();

    log.info("{} Client Event Handler ...", Constants.LOG_REGISTER_PREFIX);
    ClientEventHandler.registerClientEvents();

    log.info("{} Item Properties ...", Constants.LOG_REGISTER_PREFIX);
    ModItemProperties.registerItemProperties();

    log.info("{} Client Network Handler ...", Constants.LOG_REGISTER_PREFIX);
    ClientNetworkHandler.registerClientNetworkMessageHandler();
  }
}
