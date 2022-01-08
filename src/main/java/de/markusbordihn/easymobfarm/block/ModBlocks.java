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
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.Annotations.TemplateEntryPoint;
import de.markusbordihn.easymobfarm.block.entity.AnimalPlainsFarmEntity;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.block.entity.SkeletonMobFarmEntity;

public class ModBlocks {

  protected ModBlocks() {

  }

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

  public static final DeferredRegister<BlockEntityType<?>> ENTITIES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Constants.MOD_ID);

  @TemplateEntryPoint("Register Blocks")

  // Mob Farms
  public static final RegistryObject<Block> ANIMAL_PLAINS_FARM = BLOCKS.register(AnimalPlainsFarm.NAME,
      () -> new AnimalPlainsFarm(
          BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
              .strength(2.0F, 2.0F).lightLevel(AnimalPlainsFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> SKELETON_MOB_FARM =
      BLOCKS.register(SkeletonMobFarm.NAME,
          () -> new SkeletonMobFarm(
              BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()
                  .strength(2.0F, 2.0F).lightLevel(SkeletonMobFarm::getLightLevel).noOcclusion()));
  public static final RegistryObject<Block> MOB_FARM =
      BLOCKS.register(MobFarmBlock.NAME, () -> new MobFarmBlock(BlockBehaviour.Properties
          .of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F, 2.0F)));

  @TemplateEntryPoint("Register Entity")

  // Mob Farms Block Entity
  public static final RegistryObject<BlockEntityType<AnimalPlainsFarmEntity>> ANIMAL_PLAINS_FARM_ENTITY =
      ENTITIES.register(AnimalPlainsFarm.NAME, () -> BlockEntityType.Builder
          .of(AnimalPlainsFarmEntity::new, ANIMAL_PLAINS_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<SkeletonMobFarmEntity>> SKELETON_MOB_FARM_ENTITY =
      ENTITIES.register(SkeletonMobFarm.NAME, () -> BlockEntityType.Builder
          .of(SkeletonMobFarmEntity::new, SKELETON_MOB_FARM.get()).build(null));
  public static final RegistryObject<BlockEntityType<MobFarmBlockEntity>> MOB_FARM_ENTITY =
      ENTITIES.register(MobFarmBlock.NAME,
          () -> BlockEntityType.Builder.of(MobFarmBlockEntity::new, MOB_FARM.get()).build(null));
}
