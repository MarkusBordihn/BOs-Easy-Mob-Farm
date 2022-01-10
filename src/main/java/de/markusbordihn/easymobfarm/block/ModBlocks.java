/**
 * Copyright 2021 Markus Bordihn
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
import de.markusbordihn.easymobfarm.Annotations.TemplateEntryPoint;
import de.markusbordihn.easymobfarm.block.entity.farm.CreativeMobFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.AnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.farm.MonsterPlainsCaveFarmEntity;

public class ModBlocks {

  protected ModBlocks() {}

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

  public static final DeferredRegister<BlockEntityType<?>> ENTITIES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Constants.MOD_ID);

  @TemplateEntryPoint("Register Blocks")

  public static final RegistryObject<Block> EMPTY_MOB_FARM = BLOCKS.register("empty_mob_farm",
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
  public static final RegistryObject<Block> MONSTER_PLAINS_CAVE_FARM = BLOCKS.register(
      MonsterPlainsCaveFarm.NAME,
      () -> new MonsterPlainsCaveFarm(BlockBehaviour.Properties.of(Material.STONE)
          .requiresCorrectToolForDrops().strength(2.0F, 2.0F)
          .lightLevel(MonsterPlainsCaveFarm::getLightLevel).sound(SoundType.METAL).noOcclusion()));
  public static final RegistryObject<Block> MOB_FARM =
      BLOCKS.register(MobFarmBlock.NAME, () -> new MobFarmBlock(BlockBehaviour.Properties
          .of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 2.0F)));

  @TemplateEntryPoint("Register Entity")

  public static final RegistryObject<BlockEntityType<CreativeMobFarmEntity>> CREATIVE_MOB_FARM_ENTITY =
      ENTITIES.register(CreativeMobFarm.NAME, () -> BlockEntityType.Builder
          .of(CreativeMobFarmEntity::new, CREATIVE_MOB_FARM.get()).build(null));

  // Mob Farms Block Entity
  public static final RegistryObject<BlockEntityType<AnimalPlainsFarmEntity>> ANIMAL_PLAINS_FARM_ENTITY =
      ENTITIES.register(AnimalPlainsFarm.NAME, () -> BlockEntityType.Builder
          .of(AnimalPlainsFarmEntity::new, ANIMAL_PLAINS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<MonsterPlainsCaveFarmEntity>> MONSTER_PLAINS_CAVE_FARM_ENTITY =
      ENTITIES.register(MonsterPlainsCaveFarm.NAME, () -> BlockEntityType.Builder
          .of(MonsterPlainsCaveFarmEntity::new, MONSTER_PLAINS_CAVE_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<MobFarmBlockEntity>> MOB_FARM_ENTITY =
      ENTITIES.register(MobFarmBlock.NAME,
          () -> BlockEntityType.Builder.of(MobFarmBlockEntity::new, MOB_FARM.get()).build(null));
}
