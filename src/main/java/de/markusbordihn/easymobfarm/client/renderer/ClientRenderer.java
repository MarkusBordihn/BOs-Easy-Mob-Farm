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

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.client.renderer.farm.AnimalPlainsFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.BeeHiveFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.CreativeMobFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.DesertFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.JungleFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.MonsterPlainsCaveFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.NetherFortressFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.OceanFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.SwampFarmRenderer;

public class ClientRenderer {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientRenderer() {}

  public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    log.info("{} Block Entity Renderers ...", Constants.LOG_REGISTER_PREFIX);

    // Mob Farm Renderer - Copper
    event.registerBlockEntityRenderer(ModBlocks.COPPER_ANIMAL_PLAINS_FARM_ENTITY.get(),
        AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.COPPER_BEE_HIVE_FARM_ENTITY.get(),
        BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.COPPER_DESERT_FARM_ENTITY.get(),
        DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.COPPER_JUNGLE_FARM_ENTITY.get(),
        JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.COPPER_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
        MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.COPPER_NETHER_FORTRESS_FARM_ENTITY.get(),
        NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.COPPER_OCEAN_FARM_ENTITY.get(),
        OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.COPPER_SWAMP_FARM_ENTITY.get(),
        SwampFarmRenderer::new);

    // Mob Farm Renderer - Iron
    event.registerBlockEntityRenderer(ModBlocks.IRON_ANIMAL_PLAINS_FARM_ENTITY.get(),
        AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.IRON_BEE_HIVE_FARM_ENTITY.get(),
        BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.IRON_DESERT_FARM_ENTITY.get(),
        DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.IRON_JUNGLE_FARM_ENTITY.get(),
        JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.IRON_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
        MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.IRON_NETHER_FORTRESS_FARM_ENTITY.get(),
        NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.IRON_OCEAN_FARM_ENTITY.get(),
        OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.IRON_SWAMP_FARM_ENTITY.get(),
        SwampFarmRenderer::new);

    // Mob Farm Renderer - Gold
    event.registerBlockEntityRenderer(ModBlocks.GOLD_ANIMAL_PLAINS_FARM_ENTITY.get(),
        AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.GOLD_BEE_HIVE_FARM_ENTITY.get(),
        BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.GOLD_DESERT_FARM_ENTITY.get(),
        DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.GOLD_JUNGLE_FARM_ENTITY.get(),
        JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.GOLD_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
        MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.GOLD_NETHER_FORTRESS_FARM_ENTITY.get(),
        NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.GOLD_OCEAN_FARM_ENTITY.get(),
        OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.GOLD_SWAMP_FARM_ENTITY.get(),
        SwampFarmRenderer::new);

    // Mob Farm Renderer - Netherite
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_ANIMAL_PLAINS_FARM_ENTITY.get(),
        AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_BEE_HIVE_FARM_ENTITY.get(),
        BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_DESERT_FARM_ENTITY.get(),
        DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_JUNGLE_FARM_ENTITY.get(),
        JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
        MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_NETHER_FORTRESS_FARM_ENTITY.get(),
        NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_OCEAN_FARM_ENTITY.get(),
        OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(ModBlocks.NETHERITE_SWAMP_FARM_ENTITY.get(),
        SwampFarmRenderer::new);

    // Mob Farm Renderer - Creative
    event.registerBlockEntityRenderer(ModBlocks.CREATIVE_MOB_FARM_ENTITY.get(),
        CreativeMobFarmRenderer::new);

  }

  public static void registerRenderLayers(final FMLClientSetupEvent event) {
    log.info("{} Render Layers ...", Constants.LOG_REGISTER_PREFIX);

    event.enqueueWork(() -> {
      // Mob Farm Templates
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_MOB_FARM_TEMPLATE.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_MOB_FARM_TEMPLATE.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_MOB_FARM_TEMPLATE.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_MOB_FARM_TEMPLATE.get(),
          RenderType.cutoutMipped());

      // Mob Farm Blocks - Copper
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_MONSTER_PLAINS_CAVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_ANIMAL_PLAINS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_BEE_HIVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_DESERT_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_JUNGLE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_NETHER_FORTRESS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_OCEAN_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.COPPER_SWAMP_FARM.get(),
          RenderType.cutoutMipped());

      // Mob Farm Blocks - Iron
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_MONSTER_PLAINS_CAVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_ANIMAL_PLAINS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_BEE_HIVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_DESERT_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_JUNGLE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_NETHER_FORTRESS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_OCEAN_FARM.get(),
          RenderType.translucent());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.IRON_SWAMP_FARM.get(),
          RenderType.cutoutMipped());

      // Mob Farm Blocks - Gold
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_MONSTER_PLAINS_CAVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_ANIMAL_PLAINS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_BEE_HIVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_DESERT_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_JUNGLE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_NETHER_FORTRESS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_OCEAN_FARM.get(),
          RenderType.translucent());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.GOLD_SWAMP_FARM.get(),
      RenderType.cutoutMipped());

      // Mob Farm Blocks - Netherite
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_MONSTER_PLAINS_CAVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_ANIMAL_PLAINS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_BEE_HIVE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_DESERT_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_JUNGLE_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_NETHER_FORTRESS_FARM.get(),
          RenderType.cutoutMipped());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_OCEAN_FARM.get(),
          RenderType.translucent());
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.NETHERITE_SWAMP_FARM.get(),
          RenderType.cutoutMipped());

      // Mob Farm Blocks - Creative
      ItemBlockRenderTypes.setRenderLayer(ModBlocks.CREATIVE_MOB_FARM.get(),
          RenderType.cutoutMipped());
    });
  }

}
