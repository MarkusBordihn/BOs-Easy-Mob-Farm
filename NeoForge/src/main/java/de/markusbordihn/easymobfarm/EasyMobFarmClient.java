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

import de.markusbordihn.easymobfarm.client.model.ModModelManager;
import de.markusbordihn.easymobfarm.client.model.ModelManager;
import de.markusbordihn.easymobfarm.client.renderer.ClientRenderer;
import de.markusbordihn.easymobfarm.client.screen.ClientScreens;
import de.markusbordihn.easymobfarm.tabs.ModTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class EasyMobFarmClient {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @SuppressWarnings("java:S1118")
  public EasyMobFarmClient(IEventBus modEventBus) {
    log.info("Initializing {} (NeoForge-Client) ...", Constants.MOD_NAME);

    log.debug("{} Model Manager ...", Constants.LOG_REGISTER_PREFIX);
    ModelManager.registerModelManager(new ModModelManager());

    modEventBus.addListener(ClientRenderer::registerBlockEntityRenderers);
    modEventBus.addListener(ClientScreens::registerScreens);
    ModTabs.CREATIVE_TABS.register(modEventBus);
  }
}
