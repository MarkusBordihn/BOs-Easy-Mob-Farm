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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperAnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperBeeHiveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperDesertFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperJungleFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperMonsterPlainsCaveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperNetherFortressFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperOceanFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.copper.CopperSwampFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldAnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldBeeHiveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldDesertFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldJungleFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldMonsterPlainsCaveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldNetherFortressFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldOceanFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.gold.GoldSwampFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronAnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronBeeHiveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronDesertFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronJungleFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronMonsterPlainsCaveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronNetherFortressFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronOceanFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.iron.IronSwampFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteAnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteBeeHiveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteDesertFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteJungleFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteMonsterPlainsCaveFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteNetherFortressFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteOceanFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.netherite.NetheriteSwampFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.special.CreativeMobFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.special.IronGolemFarmEntity;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.special.CreativeMobFarm;
import de.markusbordihn.easymobfarm.block.farm.special.IronGolemFarm;

public class ModBlocks {

  protected ModBlocks() {}

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

  // Mob Farm Templates
  public static final RegistryObject<Block> COPPER_MOB_FARM_TEMPLATE =
      BLOCKS.register("copper_mob_farm_template",
          () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
              .strength(3.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> IRON_MOB_FARM_TEMPLATE =
      BLOCKS.register("iron_mob_farm_template",
          () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
              .strength(3.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> GOLD_MOB_FARM_TEMPLATE =
      BLOCKS.register("gold_mob_farm_template",
          () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
              .strength(3.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_MOB_FARM_TEMPLATE =
      BLOCKS.register("netherite_mob_farm_template",
          () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
              .strength(3.0F, 6.0F).sound(SoundType.METAL).noOcclusion()));

  // Mob Farms - Tier Copper
  public static final RegistryObject<Block> COPPER_ANIMAL_PLAINS_FARM = BLOCKS.register(
      CopperAnimalPlainsFarm.NAME,
      () -> new CopperAnimalPlainsFarm(BlockBehaviour.Properties.of(Material.STONE)
          .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
          .lightLevel(CopperAnimalPlainsFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> COPPER_BEE_HIVE_FARM =
      BLOCKS.register(CopperBeeHiveFarm.NAME,
          () -> new CopperBeeHiveFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(CopperBeeHiveFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> COPPER_DESERT_FARM =
      BLOCKS
          .register(CopperDesertFarm.NAME,
              () -> new CopperDesertFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(CopperDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> COPPER_JUNGLE_FARM =
      BLOCKS
          .register(CopperJungleFarm.NAME,
              () -> new CopperJungleFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(CopperDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> COPPER_MONSTER_PLAINS_CAVE_FARM =
      BLOCKS.register(CopperMonsterPlainsCaveFarm.NAME,
          () -> new CopperMonsterPlainsCaveFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(CopperMonsterPlainsCaveFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> COPPER_NETHER_FORTRESS_FARM =
      BLOCKS.register(CopperNetherFortressFarm.NAME,
          () -> new CopperNetherFortressFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(CopperNetherFortressFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> COPPER_OCEAN_FARM =
      BLOCKS
          .register(CopperOceanFarm.NAME,
              () -> new CopperOceanFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(CopperOceanFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> COPPER_SWAMP_FARM =
      BLOCKS
          .register(CopperSwampFarm.NAME,
              () -> new CopperSwampFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(CopperSwampFarm::getLightLevel).noOcclusion()));

  // Mob Farms - Tier Iron
  public static final RegistryObject<Block> IRON_ANIMAL_PLAINS_FARM = BLOCKS.register(
      IronAnimalPlainsFarm.NAME,
      () -> new IronAnimalPlainsFarm(BlockBehaviour.Properties.of(Material.STONE)
          .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
          .lightLevel(IronAnimalPlainsFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> IRON_BEE_HIVE_FARM =
      BLOCKS.register(IronBeeHiveFarm.NAME,
          () -> new IronBeeHiveFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(IronBeeHiveFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> IRON_DESERT_FARM =
      BLOCKS
          .register(IronDesertFarm.NAME,
              () -> new IronDesertFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(IronDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> IRON_JUNGLE_FARM =
      BLOCKS
          .register(IronJungleFarm.NAME,
              () -> new IronJungleFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(IronDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> IRON_MONSTER_PLAINS_CAVE_FARM =
      BLOCKS.register(IronMonsterPlainsCaveFarm.NAME,
          () -> new IronMonsterPlainsCaveFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(IronMonsterPlainsCaveFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> IRON_NETHER_FORTRESS_FARM =
      BLOCKS.register(IronNetherFortressFarm.NAME,
          () -> new IronNetherFortressFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(IronNetherFortressFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> IRON_OCEAN_FARM =
      BLOCKS
          .register(IronOceanFarm.NAME,
              () -> new IronOceanFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(IronOceanFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> IRON_SWAMP_FARM =
      BLOCKS
          .register(IronSwampFarm.NAME,
              () -> new IronSwampFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(IronSwampFarm::getLightLevel).noOcclusion()));

  // Mob Farms - Tier Gold
  public static final RegistryObject<Block> GOLD_ANIMAL_PLAINS_FARM = BLOCKS.register(
      GoldAnimalPlainsFarm.NAME,
      () -> new GoldAnimalPlainsFarm(BlockBehaviour.Properties.of(Material.STONE)
          .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
          .lightLevel(GoldAnimalPlainsFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> GOLD_BEE_HIVE_FARM =
      BLOCKS.register(GoldBeeHiveFarm.NAME,
          () -> new GoldBeeHiveFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(GoldBeeHiveFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> GOLD_DESERT_FARM =
      BLOCKS
          .register(GoldDesertFarm.NAME,
              () -> new GoldDesertFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(GoldDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> GOLD_JUNGLE_FARM =
      BLOCKS
          .register(GoldJungleFarm.NAME,
              () -> new GoldJungleFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(GoldDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> GOLD_MONSTER_PLAINS_CAVE_FARM =
      BLOCKS.register(GoldMonsterPlainsCaveFarm.NAME,
          () -> new GoldMonsterPlainsCaveFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(GoldMonsterPlainsCaveFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> GOLD_NETHER_FORTRESS_FARM =
      BLOCKS.register(GoldNetherFortressFarm.NAME,
          () -> new GoldNetherFortressFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(GoldNetherFortressFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> GOLD_OCEAN_FARM =
      BLOCKS
          .register(GoldOceanFarm.NAME,
              () -> new GoldOceanFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(GoldOceanFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> GOLD_SWAMP_FARM =
      BLOCKS
          .register(GoldSwampFarm.NAME,
              () -> new GoldSwampFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(GoldSwampFarm::getLightLevel).noOcclusion()));

  // Netherite Mob Farms
  public static final RegistryObject<Block> NETHERITE_ANIMAL_PLAINS_FARM =
      BLOCKS.register(NetheriteAnimalPlainsFarm.NAME,
          () -> new NetheriteAnimalPlainsFarm(
              BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
                  .strength(2.0F, 2.0F).lightLevel(NetheriteAnimalPlainsFarm::getLightLevel)
                  .sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_BEE_HIVE_FARM = BLOCKS.register(
      NetheriteBeeHiveFarm.NAME,
      () -> new NetheriteBeeHiveFarm(BlockBehaviour.Properties.of(Material.STONE)
          .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
          .lightLevel(NetheriteBeeHiveFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_MONSTER_PLAINS_CAVE_FARM =
      BLOCKS.register(NetheriteMonsterPlainsCaveFarm.NAME,
          () -> new NetheriteMonsterPlainsCaveFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(NetheriteMonsterPlainsCaveFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_DESERT_FARM =
      BLOCKS.register(NetheriteDesertFarm.NAME,
          () -> new NetheriteDesertFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(NetheriteDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_JUNGLE_FARM =
      BLOCKS.register(NetheriteJungleFarm.NAME,
          () -> new NetheriteJungleFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(NetheriteDesertFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_NETHER_FORTRESS_FARM =
      BLOCKS.register(NetheriteNetherFortressFarm.NAME,
          () -> new NetheriteNetherFortressFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(NetheriteNetherFortressFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_OCEAN_FARM =
      BLOCKS.register(NetheriteOceanFarm.NAME,
          () -> new NetheriteOceanFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(NetheriteOceanFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> NETHERITE_SWAMP_FARM =
      BLOCKS.register(NetheriteSwampFarm.NAME,
          () -> new NetheriteSwampFarm(BlockBehaviour.Properties.of(Material.STONE)
              .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
              .lightLevel(NetheriteSwampFarm::getLightLevel).noOcclusion()));

  // Special Mob Farms
  public static final RegistryObject<Block> CREATIVE_MOB_FARM =
      BLOCKS
          .register(CreativeMobFarm.NAME,
              () -> new CreativeMobFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(CreativeMobFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> IRON_GOLEM_FARM =
      BLOCKS
          .register(IronGolemFarm.NAME,
              () -> new IronGolemFarm(BlockBehaviour.Properties.of(Material.STONE)
                  .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
                  .lightLevel(IronGolemFarm::getLightLevel).noOcclusion()));

  // Mob Farms Block Entity - Copper
  public static final RegistryObject<BlockEntityType<CopperAnimalPlainsFarmEntity>> COPPER_ANIMAL_PLAINS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperAnimalPlainsFarm.NAME, () -> BlockEntityType.Builder
          .of(CopperAnimalPlainsFarmEntity::new, COPPER_ANIMAL_PLAINS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<CopperBeeHiveFarmEntity>> COPPER_BEE_HIVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperBeeHiveFarm.NAME, () -> BlockEntityType.Builder
          .of(CopperBeeHiveFarmEntity::new, COPPER_BEE_HIVE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<CopperDesertFarmEntity>> COPPER_DESERT_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperDesertFarm.NAME, () -> BlockEntityType.Builder
          .of(CopperDesertFarmEntity::new, COPPER_DESERT_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<CopperJungleFarmEntity>> COPPER_JUNGLE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperJungleFarm.NAME, () -> BlockEntityType.Builder
          .of(CopperJungleFarmEntity::new, COPPER_JUNGLE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<CopperMonsterPlainsCaveFarmEntity>> COPPER_MONSTER_PLAINS_CAVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperMonsterPlainsCaveFarm.NAME,
          () -> BlockEntityType.Builder
              .of(CopperMonsterPlainsCaveFarmEntity::new, COPPER_MONSTER_PLAINS_CAVE_FARM.get())
              .build(null));
  public static final RegistryObject<BlockEntityType<CopperNetherFortressFarmEntity>> COPPER_NETHER_FORTRESS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperNetherFortressFarm.NAME, () -> BlockEntityType.Builder
          .of(CopperNetherFortressFarmEntity::new, COPPER_NETHER_FORTRESS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<CopperOceanFarmEntity>> COPPER_OCEAN_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperOceanFarm.NAME, () -> BlockEntityType.Builder
          .of(CopperOceanFarmEntity::new, COPPER_OCEAN_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<CopperSwampFarmEntity>> COPPER_SWAMP_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CopperSwampFarm.NAME, () -> BlockEntityType.Builder
          .of(CopperSwampFarmEntity::new, COPPER_SWAMP_FARM.get()).build(null));

  // Mob Farms Block Entity - Iron
  // Important keep old name for backwards compatibility with old worlds.
  public static final RegistryObject<BlockEntityType<IronAnimalPlainsFarmEntity>> IRON_ANIMAL_PLAINS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronAnimalPlainsFarm.LEGACY_NAME, () -> BlockEntityType.Builder
          .of(IronAnimalPlainsFarmEntity::new, IRON_ANIMAL_PLAINS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<IronBeeHiveFarmEntity>> IRON_BEE_HIVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronBeeHiveFarm.LEGACY_NAME, () -> BlockEntityType.Builder
          .of(IronBeeHiveFarmEntity::new, IRON_BEE_HIVE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<IronDesertFarmEntity>> IRON_DESERT_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronDesertFarm.LEGACY_NAME, () -> BlockEntityType.Builder
          .of(IronDesertFarmEntity::new, IRON_DESERT_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<IronJungleFarmEntity>> IRON_JUNGLE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronJungleFarm.LEGACY_NAME, () -> BlockEntityType.Builder
          .of(IronJungleFarmEntity::new, IRON_JUNGLE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<IronMonsterPlainsCaveFarmEntity>> IRON_MONSTER_PLAINS_CAVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronMonsterPlainsCaveFarm.LEGACY_NAME,
          () -> BlockEntityType.Builder
              .of(IronMonsterPlainsCaveFarmEntity::new, IRON_MONSTER_PLAINS_CAVE_FARM.get())
              .build(null));
  public static final RegistryObject<BlockEntityType<IronNetherFortressFarmEntity>> IRON_NETHER_FORTRESS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronNetherFortressFarm.LEGACY_NAME, () -> BlockEntityType.Builder
          .of(IronNetherFortressFarmEntity::new, IRON_NETHER_FORTRESS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<IronOceanFarmEntity>> IRON_OCEAN_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronOceanFarm.LEGACY_NAME, () -> BlockEntityType.Builder
          .of(IronOceanFarmEntity::new, IRON_OCEAN_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<IronSwampFarmEntity>> IRON_SWAMP_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronSwampFarm.LEGACY_NAME, () -> BlockEntityType.Builder
          .of(IronSwampFarmEntity::new, IRON_SWAMP_FARM.get()).build(null));

  // Mob Farms Block Entity - Gold
  public static final RegistryObject<BlockEntityType<GoldAnimalPlainsFarmEntity>> GOLD_ANIMAL_PLAINS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldAnimalPlainsFarm.NAME, () -> BlockEntityType.Builder
          .of(GoldAnimalPlainsFarmEntity::new, GOLD_ANIMAL_PLAINS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<GoldBeeHiveFarmEntity>> GOLD_BEE_HIVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldBeeHiveFarm.NAME, () -> BlockEntityType.Builder
          .of(GoldBeeHiveFarmEntity::new, GOLD_BEE_HIVE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<GoldDesertFarmEntity>> GOLD_DESERT_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldDesertFarm.NAME, () -> BlockEntityType.Builder
          .of(GoldDesertFarmEntity::new, GOLD_DESERT_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<GoldJungleFarmEntity>> GOLD_JUNGLE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldJungleFarm.NAME, () -> BlockEntityType.Builder
          .of(GoldJungleFarmEntity::new, GOLD_JUNGLE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<GoldMonsterPlainsCaveFarmEntity>> GOLD_MONSTER_PLAINS_CAVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldMonsterPlainsCaveFarm.NAME,
          () -> BlockEntityType.Builder
              .of(GoldMonsterPlainsCaveFarmEntity::new, GOLD_MONSTER_PLAINS_CAVE_FARM.get())
              .build(null));
  public static final RegistryObject<BlockEntityType<GoldNetherFortressFarmEntity>> GOLD_NETHER_FORTRESS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldNetherFortressFarm.NAME, () -> BlockEntityType.Builder
          .of(GoldNetherFortressFarmEntity::new, GOLD_NETHER_FORTRESS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<GoldOceanFarmEntity>> GOLD_OCEAN_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldOceanFarm.NAME, () -> BlockEntityType.Builder
          .of(GoldOceanFarmEntity::new, GOLD_OCEAN_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<GoldSwampFarmEntity>> GOLD_SWAMP_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(GoldSwampFarm.NAME, () -> BlockEntityType.Builder
          .of(GoldSwampFarmEntity::new, GOLD_SWAMP_FARM.get()).build(null));

  // Mob Farms Block Entity - Netherite
  public static final RegistryObject<BlockEntityType<NetheriteAnimalPlainsFarmEntity>> NETHERITE_ANIMAL_PLAINS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteAnimalPlainsFarm.NAME,
          () -> BlockEntityType.Builder
              .of(NetheriteAnimalPlainsFarmEntity::new, NETHERITE_ANIMAL_PLAINS_FARM.get())
              .build(null));
  public static final RegistryObject<BlockEntityType<NetheriteBeeHiveFarmEntity>> NETHERITE_BEE_HIVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteBeeHiveFarm.NAME, () -> BlockEntityType.Builder
          .of(NetheriteBeeHiveFarmEntity::new, NETHERITE_BEE_HIVE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<NetheriteDesertFarmEntity>> NETHERITE_DESERT_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteDesertFarm.NAME, () -> BlockEntityType.Builder
          .of(NetheriteDesertFarmEntity::new, NETHERITE_DESERT_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<NetheriteJungleFarmEntity>> NETHERITE_JUNGLE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteJungleFarm.NAME, () -> BlockEntityType.Builder
          .of(NetheriteJungleFarmEntity::new, NETHERITE_JUNGLE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<NetheriteMonsterPlainsCaveFarmEntity>> NETHERITE_MONSTER_PLAINS_CAVE_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteMonsterPlainsCaveFarm.NAME, () -> BlockEntityType.Builder
          .of(NetheriteMonsterPlainsCaveFarmEntity::new, NETHERITE_MONSTER_PLAINS_CAVE_FARM.get())
          .build(null));
  public static final RegistryObject<BlockEntityType<NetheriteNetherFortressFarmEntity>> NETHERITE_NETHER_FORTRESS_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteNetherFortressFarm.NAME,
          () -> BlockEntityType.Builder
              .of(NetheriteNetherFortressFarmEntity::new, NETHERITE_NETHER_FORTRESS_FARM.get())
              .build(null));
  public static final RegistryObject<BlockEntityType<NetheriteOceanFarmEntity>> NETHERITE_OCEAN_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteOceanFarm.NAME, () -> BlockEntityType.Builder
          .of(NetheriteOceanFarmEntity::new, NETHERITE_OCEAN_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<NetheriteSwampFarmEntity>> NETHERITE_SWAMP_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(NetheriteSwampFarm.NAME, () -> BlockEntityType.Builder
          .of(NetheriteSwampFarmEntity::new, NETHERITE_SWAMP_FARM.get()).build(null));

  // Special Mob Farms Entity
  public static final RegistryObject<BlockEntityType<CreativeMobFarmEntity>> CREATIVE_MOB_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(CreativeMobFarm.NAME, () -> BlockEntityType.Builder
          .of(CreativeMobFarmEntity::new, CREATIVE_MOB_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<IronGolemFarmEntity>> IRON_GOLEM_FARM_ENTITY =
      BLOCK_ENTITY_TYPES.register(IronGolemFarm.NAME, () -> BlockEntityType.Builder
          .of(IronGolemFarmEntity::new, IRON_GOLEM_FARM.get()).build(null));

}
