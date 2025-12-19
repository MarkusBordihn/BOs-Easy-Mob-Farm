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

package de.markusbordihn.easymobfarm.block;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.CreativeMobFarmBlockEntity;
import de.markusbordihn.easymobfarm.block.entity.CreativeMobFarmBlockEntityWrapper;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntityWrapper;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModBlocks {

  public static final Block TIER_0_MOB_FARM_TEMPLATE =
      new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_0);
  public static final Block TIER_1_MOB_FARM_TEMPLATE =
      new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_1);
  public static final Block TIER_2_MOB_FARM_TEMPLATE =
      new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_2);
  public static final Block TIER_3_MOB_FARM_TEMPLATE =
      new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_3);
  public static final Block CREATIVE_MOB_FARM = new CreativeMobFarmBlockWrapper();
  public static final Block ANIMAL_PLAINS_FARM =
      new MobFarmBlockWrapper(MobFarmType.ANIMAL_PLAINS_FARM);
  public static final Block BEE_HIVE_FARM = new MobFarmBlockWrapper(MobFarmType.BEE_HIVE_FARM);
  public static final Block DESERT_FARM = new MobFarmBlockWrapper(MobFarmType.DESERT_FARM);
  public static final Block IRON_GOLEM_FARM = new MobFarmBlockWrapper(MobFarmType.IRON_GOLEM_FARM);
  public static final Block JUNGLE_FARM = new MobFarmBlockWrapper(MobFarmType.JUNGLE_FARM);
  public static final Block LUCKY_DROP_FARM = new MobFarmBlockWrapper(MobFarmType.LUCKY_DROP_FARM);
  public static final Block MONSTER_PLAINS_CAVE_FARM =
      new MobFarmBlockWrapper(MobFarmType.MONSTER_PLAINS_CAVE_FARM);
  public static final Block NETHER_FORTRESS_FARM =
      new MobFarmBlockWrapper(MobFarmType.NETHER_FORTRESS_FARM);
  public static final Block OCEAN_FARM = new MobFarmBlockWrapper(MobFarmType.OCEAN_FARM);
  public static final Block SWAMP_FARM = new MobFarmBlockWrapper(MobFarmType.SWAMP_FARM);
  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private ModBlocks() {}

  public static void registerModBlocks() {
    log.info("{} Mob Farm Template blocks ...", Constants.LOG_REGISTER_PREFIX);
    registerBlock(MobFarmTemplateBlock.ID_TIER_0, TIER_0_MOB_FARM_TEMPLATE);
    registerBlock(MobFarmTemplateBlock.ID_TIER_1, TIER_1_MOB_FARM_TEMPLATE);
    registerBlock(MobFarmTemplateBlock.ID_TIER_2, TIER_2_MOB_FARM_TEMPLATE);
    registerBlock(MobFarmTemplateBlock.ID_TIER_3, TIER_3_MOB_FARM_TEMPLATE);

    log.info("{} Mob Farm blocks ...", Constants.LOG_REGISTER_PREFIX);
    registerBlock(MobFarmType.CREATIVE_MOB_FARM.getId(), CREATIVE_MOB_FARM);
    registerBlock(MobFarmType.ANIMAL_PLAINS_FARM.getId(), ANIMAL_PLAINS_FARM);
    registerBlock(MobFarmType.BEE_HIVE_FARM.getId(), BEE_HIVE_FARM);
    registerBlock(MobFarmType.DESERT_FARM.getId(), DESERT_FARM);
    registerBlock(MobFarmType.IRON_GOLEM_FARM.getId(), IRON_GOLEM_FARM);
    registerBlock(MobFarmType.JUNGLE_FARM.getId(), JUNGLE_FARM);
    registerBlock(MobFarmType.LUCKY_DROP_FARM.getId(), LUCKY_DROP_FARM);
    registerBlock(MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId(), MONSTER_PLAINS_CAVE_FARM);
    registerBlock(MobFarmType.NETHER_FORTRESS_FARM.getId(), NETHER_FORTRESS_FARM);
    registerBlock(MobFarmType.OCEAN_FARM.getId(), OCEAN_FARM);
    registerBlock(MobFarmType.SWAMP_FARM.getId(), SWAMP_FARM);
  }

  public static void registerModBlockEntities() {
    log.info("{} Mob Farm block entities ...", Constants.LOG_REGISTER_PREFIX);
    Registry.register(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, CreativeMobFarmBlockEntity.ID),
        CREATIVE_MOB_FARM_ENTITY);
    Registry.register(
        BuiltInRegistries.BLOCK_ENTITY_TYPE,
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, MobFarmBlockEntity.ID),
        MOB_FARM_ENTITY);
  }

  private static void registerBlock(String id, Block block) {
    Registry.register(
        BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MOD_ID, id), block);
  }

  public static final BlockEntityType<CreativeMobFarmBlockEntityWrapper> CREATIVE_MOB_FARM_ENTITY =
      FabricBlockEntityTypeBuilder.create(CreativeMobFarmBlockEntityWrapper::new, CREATIVE_MOB_FARM)
          .build();

  public static final BlockEntityType<MobFarmBlockEntityWrapper> MOB_FARM_ENTITY =
      FabricBlockEntityTypeBuilder.create(
              MobFarmBlockEntityWrapper::new,
              ANIMAL_PLAINS_FARM,
              BEE_HIVE_FARM,
              DESERT_FARM,
              IRON_GOLEM_FARM,
              JUNGLE_FARM,
              LUCKY_DROP_FARM,
              MONSTER_PLAINS_CAVE_FARM,
              NETHER_FORTRESS_FARM,
              OCEAN_FARM,
              SWAMP_FARM)
          .build();
}
