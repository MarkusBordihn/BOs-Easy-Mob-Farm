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
import de.markusbordihn.easymobfarm.client.renderer.farm.AnimalPlainsFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.BeeHiveFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.CreativeMobFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.DesertFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.IronGolemFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.JungleFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.MonsterPlainsCaveFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.NetherFortressFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.OceanFarmRenderer;
import de.markusbordihn.easymobfarm.client.renderer.farm.SwampFarmRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientRenderer {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ClientRenderer() {}

  public static void registerBlockEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
    log.info("{} Block Entity Renderers ...", Constants.LOG_REGISTER_PREFIX);

    // Mob Farm Renderer - Copper
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_ANIMAL_PLAINS_FARM_ENTITY.get(), AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_BEE_HIVE_FARM_ENTITY.get(), BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_DESERT_FARM_ENTITY.get(), DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_JUNGLE_FARM_ENTITY.get(), JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(), MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_NETHER_FORTRESS_FARM_ENTITY.get(), NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_OCEAN_FARM_ENTITY.get(), OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.COPPER_SWAMP_FARM_ENTITY.get(), SwampFarmRenderer::new);

    // Mob Farm Renderer - Iron
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_ANIMAL_PLAINS_FARM_ENTITY.get(), AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_BEE_HIVE_FARM_ENTITY.get(), BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_DESERT_FARM_ENTITY.get(), DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_JUNGLE_FARM_ENTITY.get(), JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(), MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_NETHER_FORTRESS_FARM_ENTITY.get(), NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_OCEAN_FARM_ENTITY.get(), OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_SWAMP_FARM_ENTITY.get(), SwampFarmRenderer::new);

    // Mob Farm Renderer - Gold
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_ANIMAL_PLAINS_FARM_ENTITY.get(), AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_BEE_HIVE_FARM_ENTITY.get(), BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_DESERT_FARM_ENTITY.get(), DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_JUNGLE_FARM_ENTITY.get(), JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(), MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_NETHER_FORTRESS_FARM_ENTITY.get(), NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_OCEAN_FARM_ENTITY.get(), OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.GOLD_SWAMP_FARM_ENTITY.get(), SwampFarmRenderer::new);

    // Mob Farm Renderer - Netherite
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_ANIMAL_PLAINS_FARM_ENTITY.get(), AnimalPlainsFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_BEE_HIVE_FARM_ENTITY.get(), BeeHiveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_DESERT_FARM_ENTITY.get(), DesertFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_JUNGLE_FARM_ENTITY.get(), JungleFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
        MonsterPlainsCaveFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_NETHER_FORTRESS_FARM_ENTITY.get(), NetherFortressFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_OCEAN_FARM_ENTITY.get(), OceanFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.NETHERITE_SWAMP_FARM_ENTITY.get(), SwampFarmRenderer::new);

    // Special Mob Farm Renderer
    event.registerBlockEntityRenderer(
        ModBlocks.CREATIVE_MOB_FARM_ENTITY.get(), CreativeMobFarmRenderer::new);
    event.registerBlockEntityRenderer(
        ModBlocks.IRON_GOLEM_FARM_ENTITY.get(), IronGolemFarmRenderer::new);
  }
}
