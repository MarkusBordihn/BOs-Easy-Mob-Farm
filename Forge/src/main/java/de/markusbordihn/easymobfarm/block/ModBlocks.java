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
import java.util.Set;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);
  public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
      DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MOD_ID);

  public static final RegistryObject<Block> TIER_0_MOB_FARM_TEMPLATE =
      BLOCKS.register(
          MobFarmTemplateBlock.ID_TIER_0,
          () -> new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_0));
  public static final RegistryObject<Block> TIER_1_MOB_FARM_TEMPLATE =
      BLOCKS.register(
          MobFarmTemplateBlock.ID_TIER_1,
          () -> new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_1));
  public static final RegistryObject<Block> TIER_2_MOB_FARM_TEMPLATE =
      BLOCKS.register(
          MobFarmTemplateBlock.ID_TIER_2,
          () -> new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_2));
  public static final RegistryObject<Block> TIER_3_MOB_FARM_TEMPLATE =
      BLOCKS.register(
          MobFarmTemplateBlock.ID_TIER_3,
          () -> new MobFarmTemplateBlock(MobFarmTemplateBlock.ID_TIER_3));

  public static final RegistryObject<Block> CREATIVE_MOB_FARM =
      BLOCKS.register(MobFarmType.CREATIVE_MOB_FARM.getId(), CreativeMobFarmBlockWrapper::new);
  public static final RegistryObject<Block> ANIMAL_PLAINS_FARM =
      BLOCKS.register(
          MobFarmType.ANIMAL_PLAINS_FARM.getId(),
          () -> new MobFarmBlockWrapper(MobFarmType.ANIMAL_PLAINS_FARM));
  public static final RegistryObject<Block> BEE_HIVE_FARM =
      BLOCKS.register(
          MobFarmType.BEE_HIVE_FARM.getId(),
          () -> new MobFarmBlockWrapper(MobFarmType.BEE_HIVE_FARM));
  public static final RegistryObject<Block> DESERT_FARM =
      BLOCKS.register(
          MobFarmType.DESERT_FARM.getId(), () -> new MobFarmBlockWrapper(MobFarmType.DESERT_FARM));
  public static final RegistryObject<Block> IRON_GOLEM_FARM =
      BLOCKS.register(
          MobFarmType.IRON_GOLEM_FARM.getId(),
          () -> new MobFarmBlockWrapper(MobFarmType.IRON_GOLEM_FARM));
  public static final RegistryObject<Block> JUNGLE_FARM =
      BLOCKS.register(
          MobFarmType.JUNGLE_FARM.getId(), () -> new MobFarmBlockWrapper(MobFarmType.JUNGLE_FARM));
  public static final RegistryObject<Block> LUCKY_DROP_FARM =
      BLOCKS.register(
          MobFarmType.LUCKY_DROP_FARM.getId(),
          () -> new MobFarmBlockWrapper(MobFarmType.LUCKY_DROP_FARM));
  public static final RegistryObject<Block> MONSTER_PLAINS_CAVE_FARM =
      BLOCKS.register(
          MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId(),
          () -> new MobFarmBlockWrapper(MobFarmType.MONSTER_PLAINS_CAVE_FARM));
  public static final RegistryObject<Block> NETHER_FORTRESS_FARM =
      BLOCKS.register(
          MobFarmType.NETHER_FORTRESS_FARM.getId(),
          () -> new MobFarmBlockWrapper(MobFarmType.NETHER_FORTRESS_FARM));
  public static final RegistryObject<Block> OCEAN_FARM =
      BLOCKS.register(
          MobFarmType.OCEAN_FARM.getId(), () -> new MobFarmBlockWrapper(MobFarmType.OCEAN_FARM));
  public static final RegistryObject<Block> SWAMP_FARM =
      BLOCKS.register(
          MobFarmType.SWAMP_FARM.getId(), () -> new MobFarmBlockWrapper(MobFarmType.SWAMP_FARM));
  protected ModBlocks() {}  public static final RegistryObject<BlockEntityType<CreativeMobFarmBlockEntityWrapper>>
      CREATIVE_MOB_FARM_ENTITY =
          BLOCK_ENTITIES.register(
              CreativeMobFarmBlockEntity.ID,
              () ->
                  new BlockEntityType<>(
                      CreativeMobFarmBlockEntityWrapper::new, Set.of(CREATIVE_MOB_FARM.get())));



  public static final RegistryObject<BlockEntityType<MobFarmBlockEntityWrapper>> MOB_FARM_ENTITY =
      BLOCK_ENTITIES.register(
          MobFarmBlockEntity.ID,
          () ->
              new BlockEntityType<>(
                  MobFarmBlockEntityWrapper::new,
                  Set.of(
                      ANIMAL_PLAINS_FARM.get(),
                      BEE_HIVE_FARM.get(),
                      DESERT_FARM.get(),
                      IRON_GOLEM_FARM.get(),
                      JUNGLE_FARM.get(),
                      LUCKY_DROP_FARM.get(),
                      MONSTER_PLAINS_CAVE_FARM.get(),
                      NETHER_FORTRESS_FARM.get(),
                      OCEAN_FARM.get(),
                      SWAMP_FARM.get())));
}
