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

import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.client.renderer.blockentity.MobFarmBlockEntityRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

public class ClientRenderer {

  private ClientRenderer() {}

  public static void registerBlockEntityRenderers() {
    BlockEntityRenderers.register(
        ModBlocks.CREATIVE_MOB_FARM_ENTITY, MobFarmBlockEntityRenderer::new);
    BlockEntityRenderers.register(ModBlocks.MOB_FARM_ENTITY, MobFarmBlockEntityRenderer::new);
  }

  public static void registerRenderLayers() {
    BlockRenderLayerMap.putBlock(ModBlocks.TIER_0_MOB_FARM_TEMPLATE, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.TIER_1_MOB_FARM_TEMPLATE, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.TIER_2_MOB_FARM_TEMPLATE, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.TIER_3_MOB_FARM_TEMPLATE, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.CREATIVE_MOB_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.ANIMAL_PLAINS_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.BEE_HIVE_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.DESERT_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.IRON_GOLEM_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.JUNGLE_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.LUCKY_DROP_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.MONSTER_PLAINS_CAVE_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.NETHER_FORTRESS_FARM, ChunkSectionLayer.CUTOUT);
    BlockRenderLayerMap.putBlock(ModBlocks.OCEAN_FARM, ChunkSectionLayer.TRANSLUCENT);
    BlockRenderLayerMap.putBlock(ModBlocks.SWAMP_FARM, ChunkSectionLayer.CUTOUT);
  }
}
