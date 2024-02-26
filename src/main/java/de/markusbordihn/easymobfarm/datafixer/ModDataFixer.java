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

package de.markusbordihn.easymobfarm.datafixer;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import de.markusbordihn.easymobfarm.Constants;

public class ModDataFixer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final Map<String, String> missingBlocksMap =
      ImmutableMap.<String, String>builder()

          // Steel Mob Farms -> Iron Mob Farms
          .put("animal_plains_farm", "iron_animal_plains_farm")
          .put("bee_hive_farm", "iron_bee_hive_farm")
          .put("desert_farm", "iron_desert_farm")
          .put("jungle_farm", "iron_jungle_farm")
          .put("monster_plains_cave_farm", "iron_monster_plains_cave_farm")
          .put("nether_fortress_farm", "iron_nether_fortress_farm")
          .put("ocean_farm", "iron_ocean_farm")
          .put("swamp_farm", "iron_swamp_farm")
          .build();

  private static final Map<String, String> missingBlockEntityTypesMap =
      ImmutableMap.<String, String>builder().build();

  private static final Map<String, String> missingItemsMap =
      ImmutableMap.<String, String>builder()

          // Steel Mob Farms -> Iron Mob Farms
          .put("animal_plains_farm", "iron_animal_plains_farm")
          .put("bee_hive_farm", "iron_bee_hive_farm")
          .put("desert_farm", "iron_desert_farm")
          .put("jungle_farm", "iron_jungle_farm")
          .put("monster_plains_cave_farm", "iron_monster_plains_cave_farm")
          .put("nether_fortress_farm", "iron_nether_fortress_farm")
          .put("ocean_farm", "iron_ocean_farm")
          .put("swamp_farm", "iron_swamp_farm")
          .build();

  @SubscribeEvent
  public static void onMissingMapping(MissingMappingsEvent missingBlockMappingEvent) {

    // Mapping for missing blocks
    for (MissingMappingsEvent.Mapping<Block> missingBlock :
        missingBlockMappingEvent.getMappings(ForgeRegistries.Keys.BLOCKS, Constants.MOD_ID)) {
      log.warn(
          "Missing block {} with key {}",
          missingBlock.getRegistry(),
          missingBlock.getKey().getPath());
      String blockReplacement = missingBlocksMap.get(missingBlock.getKey().getPath());
      if (blockReplacement == null) {
        continue;
      }
      Block block =
          ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Constants.MOD_ID, blockReplacement));
      if (block != null) {
        log.info("Remapped missing block {} to {}", missingBlock.getRegistry(), block);
        missingBlock.remap(block);
      }
    }

    // Mapping for missing block entity types
    for (MissingMappingsEvent.Mapping<BlockEntityType<?>> missingBlockEntityType :
        missingBlockMappingEvent.getMappings(
            ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, Constants.MOD_ID)) {
      log.warn(
          "Missing block entity type {} with key {}",
          missingBlockEntityType.getRegistry(),
          missingBlockEntityType.getKey().getPath());
      String blockEntityTypeReplacement =
          missingBlockEntityTypesMap.get(missingBlockEntityType.getKey().getPath());
      if (blockEntityTypeReplacement == null) {
        continue;
      }
      BlockEntityType<?> blockEntityType =
          ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(
              new ResourceLocation(Constants.MOD_ID, blockEntityTypeReplacement));
      if (blockEntityType != null) {
        log.info(
            "Remapped missing block entity type {} to {}",
            missingBlockEntityType.getRegistry(),
            blockEntityType);
        missingBlockEntityType.remap(blockEntityType);
      }
    }

    // Mapping for missing items
    for (MissingMappingsEvent.Mapping<Item> missingItem :
        missingBlockMappingEvent.getMappings(ForgeRegistries.Keys.ITEMS, Constants.MOD_ID)) {
      log.warn(
          "Missing item {} with key {}", missingItem.getRegistry(), missingItem.getKey().getPath());
      String itemReplacement = missingItemsMap.get(missingItem.getKey().getPath());
      if (itemReplacement == null) {
        continue;
      }
      Item item =
          ForgeRegistries.ITEMS.getValue(new ResourceLocation(Constants.MOD_ID, itemReplacement));
      if (item != null) {
        log.info("Remapped missing item {} to {}", missingItem.getRegistry(), item);
        missingItem.remap(item);
      }
    }
  }
}
