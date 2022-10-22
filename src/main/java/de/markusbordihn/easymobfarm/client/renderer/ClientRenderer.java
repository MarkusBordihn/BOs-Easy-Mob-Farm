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

package de.markusbordihn.easymobfarm.client.renderer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.client.event.EntityRenderersEvent;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.client.renderer.farm.AnimalPlainsFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.CreativeMobFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.DesertFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.JungleFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.MonsterPlainsCaveFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.OceanFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.SwampFarmRenderer;

public class ClientRenderer {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientRenderer() {}

  public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    log.info("{} Block Entity Renderers ...", Constants.LOG_REGISTER_PREFIX);

    // @TemplateEntryPoint("Register Entity Renderer")
    event.registerBlockEntityRenderer(ModBlocks.ANIMAL_PLAINS_FARM_ENTITY.get(),
        AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.CREATIVE_MOB_FARM_ENTITY.get(),
        CreativeMobFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.DESERT_FARM_ENTITY.get(), DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.JUNGLE_FARM_ENTITY.get(), JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
        MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.OCEAN_FARM_ENTITY.get(), OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.SWAMP_FARM_ENTITY.get(), SwampFarmRenderer::new);
  }

}
