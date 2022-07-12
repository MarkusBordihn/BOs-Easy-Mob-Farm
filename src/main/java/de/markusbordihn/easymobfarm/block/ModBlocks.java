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

package de.markusbordihn.easymobfarm.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.easymobfarm.Annotations.TemplateEntryPoint;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.farm.AnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.CreativeMobFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.DesertFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.MonsterPlainsCaveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.OceanFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.SwampFarmEntity;

public class ModBlocks {

  protected ModBlocks() {}

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

  @TemplateEntryPoint("Register Blocks")

  // Mob Farm Templates
  public static final RegistryObject<Block> IRON_MOB_FARM_TEMPLATE =
      BLOCKS.register("iron_mob_farm_template",
          () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
              .strength(3.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));

  // Mob Farms
  public static final RegistryObject<Block> ANIMAL_PLAINS_FARM =
      BLOCKS.register(AnimalPlainsFarm.NAME,
          () -> new AnimalPlainsFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(AnimalPlainsFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> CREATIVE_MOB_FARM =
      BLOCKS
          .register(CreativeMobFarm.NAME,
              () -> new CreativeMobFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(CreativeMobFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> DESERT_FARM = BLOCKS.register(DesertFarm.NAME,
      () -> new DesertFarm(
          BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
              .strength(2.0F, 2.0F).lightLevel(DesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> MONSTER_PLAINS_CAVE_FARM = BLOCKS.register(
      MonsterPlainsCaveFarm.NAME,
      () -> new MonsterPlainsCaveFarm(BlockBehaviour.Properties.of(Material.STONE)
          .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
          .lightLevel(MonsterPlainsCaveFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> OCEAN_FARM = BLOCKS.register(OceanFarm.NAME,
      () -> new OceanFarm(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
          .strength(2.0F, 2.0F).lightLevel(OceanFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> SWAMP_FARM = BLOCKS.register(SwampFarm.NAME,
      () -> new SwampFarm(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
          .strength(2.0F, 2.0F).lightLevel(SwampFarm::getLightLevel).noOcclusion()));

  @TemplateEntryPoint("Register Entity")

  // Mob Farms Block Entity
  public static final RegistryObject<BlockEntityType<AnimalPlainsFarmEntity>> ANIMAL_PLAINS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(AnimalPlainsFarm.NAME, () -> BlockEntityType.Builder
          .of(AnimalPlainsFarmEntity::new, ANIMAL_PLAINS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<CreativeMobFarmEntity>> CREATIVE_MOB_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CreativeMobFarm.NAME, () -> BlockEntityType.Builder
          .of(CreativeMobFarmEntity::new, CREATIVE_MOB_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<DesertFarmEntity>> DESERT_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(DesertFarm.NAME,
          () -> BlockEntityType.Builder.of(DesertFarmEntity::new, DESERT_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<MonsterPlainsCaveFarmEntity>> MONSTER_PLAINS_CAVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(MonsterPlainsCaveFarm.NAME, () -> BlockEntityType.Builder
          .of(MonsterPlainsCaveFarmEntity::new, MONSTER_PLAINS_CAVE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<OceanFarmEntity>> OCEAN_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(OceanFarm.NAME,
          () -> BlockEntityType.Builder.of(OceanFarmEntity::new, OCEAN_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<SwampFarmEntity>> SWAMP_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(SwampFarm.NAME,
          () -> BlockEntityType.Builder.of(SwampFarmEntity::new, SWAMP_FARM.get()).build(null));
}
