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

import com.google.common.collect.ImmutableMap;
import de.markusbordihn.easymobfarm.Constants;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModDataFixer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final Map<String, String> missingBlocksMap = ImmutableMap.<String, String>builder()

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
      ImmutableMap.<String, String>builder()

          .build();

  private static final Map<String, String> missingItemsMap = ImmutableMap.<String, String>builder()

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
  public static void onMissingBlocks(
      RegistryEvent.MissingMappings<Block> missingBlockMappingEvent) {
    for (RegistryEvent.MissingMappings.Mapping<Block> missingBlock : missingBlockMappingEvent
        .getMappings(Constants.MOD_ID)) {
      log.warn("Missing block {} with key {}", missingBlock.registry, missingBlock.key.getPath());
      String blockReplacement = missingBlocksMap.get(missingBlock.key.getPath());
      if (blockReplacement == null) {
        continue;
      }
      Block block =
          ForgeRegistries.BLOCKS.getValue(new ResourceLocation(Constants.MOD_ID, blockReplacement));
      if (block != null) {
        log.info("Remapped missing block {} to {}", missingBlock.registry, block);
        missingBlock.remap(block);
      }
    }
  }

  @SubscribeEvent
  public static void onMissingBlockEntityTypes(
      RegistryEvent.MissingMappings<BlockEntityType<?>> missingBlockEntityTypeMappingEvent) {
    for (RegistryEvent.MissingMappings.Mapping<BlockEntityType<?>> missingBlockEntityType : missingBlockEntityTypeMappingEvent
        .getMappings(Constants.MOD_ID)) {
      log.warn("Missing block entity type {} with key {}", missingBlockEntityType.registry,
          missingBlockEntityType.key.getPath());
      String blockEntityTypeReplacement =
          missingBlockEntityTypesMap.get(missingBlockEntityType.key.getPath());
      if (blockEntityTypeReplacement == null) {
        continue;
      }
      BlockEntityType<?> blockEntityType = ForgeRegistries.BLOCK_ENTITIES
          .getValue(new ResourceLocation(Constants.MOD_ID, blockEntityTypeReplacement));
      if (blockEntityType != null) {
        log.info("Remapped missing block entity type {} to {}", missingBlockEntityType.registry,
            blockEntityType);
        missingBlockEntityType.remap(blockEntityType);
      }
    }
  }

  @SubscribeEvent
  public static void onMissingItems(RegistryEvent.MissingMappings<Item> missingItemMappingEvent) {
    for (RegistryEvent.MissingMappings.Mapping<Item> missingItem : missingItemMappingEvent
        .getMappings(Constants.MOD_ID)) {
      log.warn("Missing item {} with key {}", missingItem.registry, missingItem.key.getPath());
      String itemReplacement = missingItemsMap.get(missingItem.key.getPath());
      if (itemReplacement == null) {
        continue;
      }
      Item item =
          ForgeRegistries.ITEMS.getValue(new ResourceLocation(Constants.MOD_ID, itemReplacement));
      if (item != null) {
        log.info("Remapped missing item {} to {}", missingItem.registry, item);
        missingItem.remap(item);
      }
    }
  }

}
