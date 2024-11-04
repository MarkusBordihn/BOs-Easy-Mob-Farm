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

package de.markusbordihn.easymobfarm.client.renderer;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.client.renderer.blockentity.MobFarmBlockEntityRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientRenderer {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientRenderer() {}

  public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    log.info("{} Block Entity Renderers ...", Constants.LOG_REGISTER_PREFIX);

    event.registerBlockEntityRenderer(
        ModBlocks.CREATIVE_MOB_FARM_ENTITY.get(), MobFarmBlockEntityRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.MOB_FARM_ENTITY.get(), MobFarmBlockEntityRenderer::new);
  }

  public static void registerRenderLayers(final FMLClientSetupEvent event) {
    log.info("{} Render Layers ...", Constants.LOG_REGISTER_PREFIX);

    event.enqueueWork(
        () -> {
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.TIER_0_MOB_FARM_TEMPLATE.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.TIER_1_MOB_FARM_TEMPLATE.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.TIER_2_MOB_FARM_TEMPLATE.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.TIER_3_MOB_FARM_TEMPLATE.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.CREATIVE_MOB_FARM.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.ANIMAL_PLAINS_FARM.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.BEE_HIVE_FARM.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.DESERT_FARM.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.JUNGLE_FARM.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.MONSTER_PLAINS_CAVE_FARM.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.NETHER_FORTRESS_FARM.get(), RenderType.cutoutMipped());
          ItemBlockRenderTypes.setRenderLayer(ModBlocks.OCEAN_FARM.get(), RenderType.translucent());
          ItemBlockRenderTypes.setRenderLayer(
              ModBlocks.SWAMP_FARM.get(), RenderType.cutoutMipped());
        });
  }
}
