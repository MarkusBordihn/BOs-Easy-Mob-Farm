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

package de.markusbordihn.easymobfarm.item;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;

import de.markusbordihn.easymobfarm.Constants;

public class CapturedMobVirtual {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  // Compound Tags
  private static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
  private static final String CAPTURED_ENTITY_TAG = "CapturedEntity";
  private static final String ENTITY_HOLDER_TAG = "entity_holder";
  private static final String ENTITY_TAG = "entity";
  private static final String ENTITY_TYPE_TAG = "EntityType";
  private static final String ID_TAG = "id";
  private static final String MOD_DATA_TAG = "mob_data";
  private static final String NAME_TAG = "name";
  private static final String SPAWN_DATA_TAG = "SpawnData";

  // Supported mod mob catcher items
  private static final String CREATE_BLAZE_BURNER = "create:blaze_burner";
  private static final String MOBCATCHER_NET = "mobcatcher:net";
  private static final String MOB_CAPTURING_TOOL = "mobcapturingtool:mob_capturing_tool";
  private static final String MOB_CATCHER_DIAMOND = "mob_catcher:diamond_mob_catcher";
  private static final String MOB_CATCHER_NETHERITE = "mob_catcher:netherite_mob_catcher";
  private static final String MONSTER_BALL = "cyclic:mob_container";
  private static final String QUANTUM_CATCHER = "forbidden_arcanus:quantum_catcher";

  protected CapturedMobVirtual() {}

  public static boolean isSupported(ItemStack itemStack) {
    if (itemStack == null || itemStack.isEmpty()) {
      return false;
    }

    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return true;
    }

    // Check for supported items from other mods.
    ResourceLocation itemRegistryName = item.getRegistryName();
    String itemName = itemRegistryName != null ? itemRegistryName.toString() : "";
    if (Constants.MOB_CATCHER_LOADED
        && (itemName.equals(MOB_CATCHER_DIAMOND) || itemName.equals(MOB_CATCHER_NETHERITE))) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      if (compoundTag.contains(MOD_DATA_TAG)
          && compoundTag.getCompound(MOD_DATA_TAG).contains(ID_TAG)) {
        return !compoundTag.getCompound(MOD_DATA_TAG).getString(ID_TAG).isEmpty();
      }
    } else if (Constants.CREATE_LOADED && itemName.equals(CREATE_BLAZE_BURNER)) {
      return true;
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && itemName.equals(MOB_CAPTURING_TOOL)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      if (compoundTag.contains(CAPTURED_ENTITY_TAG)
          && compoundTag.getCompound(CAPTURED_ENTITY_TAG).contains(ENTITY_TYPE_TAG)) {
        return !compoundTag.getCompound(CAPTURED_ENTITY_TAG).getString(ENTITY_TYPE_TAG).isEmpty();
      }
    } else if (Constants.MOBCATCHER_LOADED && itemName.equals(MOBCATCHER_NET)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      if (compoundTag.contains(ENTITY_HOLDER_TAG)
          && compoundTag.getCompound(ENTITY_HOLDER_TAG).contains(ID_TAG)) {
        return !compoundTag.getCompound(ENTITY_HOLDER_TAG).getString(ID_TAG).isEmpty();
      }
    } else if (Constants.CORAIL_SPAWNERS_LOADED && item.equals(Items.SPAWNER)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      if (compoundTag.contains(BLOCK_ENTITY_TAG)
          && compoundTag.getCompound(BLOCK_ENTITY_TAG).contains(SPAWN_DATA_TAG)
          && compoundTag.getCompound(BLOCK_ENTITY_TAG).getCompound(SPAWN_DATA_TAG)
              .contains(ENTITY_TAG)
          && compoundTag.getCompound(BLOCK_ENTITY_TAG).getCompound(SPAWN_DATA_TAG)
              .getCompound(ENTITY_TAG).contains(ID_TAG)) {
        return !compoundTag.getCompound(BLOCK_ENTITY_TAG).getCompound(SPAWN_DATA_TAG)
            .getCompound(ENTITY_TAG).getString(ID_TAG).isEmpty();
      }
    } else if (Constants.FORBIDDEN_ARCANUS_LOADED && itemName.equals(QUANTUM_CATCHER)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      if (compoundTag.contains(ENTITY_TAG)
          && compoundTag.getCompound(ENTITY_TAG).contains(ID_TAG)) {
        return !compoundTag.getCompound(ENTITY_TAG).getString(ID_TAG).isEmpty();
      }
    } else if (Constants.CYCLIC_LOADED && itemName.equals(MONSTER_BALL)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return !compoundTag.getString(ID_TAG).isEmpty();
    } else if (item instanceof SpawnEggItem) {
      return true;
    } else if (!(item instanceof AirItem)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      log.debug("Unsupported mob catching Item {} {}: {}", item, itemName, compoundTag);
    }
    return false;
  }

  public static boolean hasCapturedMob(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return false;
    }

    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.hasCapturedMob(itemStack);
    }

    // Check for supported items from other mods.
    ResourceLocation itemRegistryName = item.getRegistryName();
    String itemName = itemRegistryName != null ? itemRegistryName.toString() : "";
    if (Constants.MOB_CATCHER_LOADED
        && (itemName.equals(MOB_CATCHER_DIAMOND) || itemName.equals(MOB_CATCHER_NETHERITE))) {
      return !getCapturedMobType(itemStack).isBlank() && !getCapturedMobType(itemStack).isEmpty();
    } else if (Constants.CREATE_LOADED && itemName.equals(CREATE_BLAZE_BURNER)) {
      return true;
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && itemName.equals(MOB_CAPTURING_TOOL)) {
      return !getCapturedMobType(itemStack).isBlank() && !getCapturedMobType(itemStack).isEmpty();
    } else if (Constants.MOBCATCHER_LOADED && itemName.equals(MOBCATCHER_NET)) {
      return !getCapturedMobType(itemStack).isBlank() && !getCapturedMobType(itemStack).isEmpty();
    } else if (Constants.CORAIL_SPAWNERS_LOADED && item.equals(Items.SPAWNER)) {
      return !getCapturedMobType(itemStack).isBlank() && !getCapturedMobType(itemStack).isEmpty();
    } else if (Constants.FORBIDDEN_ARCANUS_LOADED && itemName.equals(QUANTUM_CATCHER)) {
      return !getCapturedMobType(itemStack).isBlank() && !getCapturedMobType(itemStack).isEmpty();
    } else if (Constants.CYCLIC_LOADED && itemName.equals(MONSTER_BALL)) {
      return !getCapturedMobType(itemStack).isBlank() && !getCapturedMobType(itemStack).isEmpty();
    } else if (item instanceof SpawnEggItem) {
      return true;
    }
    return false;
  }

  public static String getCapturedMob(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return "";
    }
    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMob(itemStack);
    }

    // Check for supported items from other mods.
    ResourceLocation itemRegistryName = item.getRegistryName();
    String itemName = itemRegistryName != null ? itemRegistryName.toString() : "";
    if (Constants.MOB_CATCHER_LOADED
        && (itemName.equals(MOB_CATCHER_DIAMOND) || itemName.equals(MOB_CATCHER_NETHERITE))) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getString(NAME_TAG);
    } else if (Constants.CREATE_LOADED && itemName.equals(CREATE_BLAZE_BURNER)) {
      return "Blaze";
    } else if ((Constants.MOB_CAPTURING_TOOL_LOADED && itemName.equals(MOB_CAPTURING_TOOL))
        || (Constants.MOBCATCHER_LOADED && itemName.equals(MOBCATCHER_NET))
        || (item instanceof SpawnEggItem)
        || (Constants.CORAIL_SPAWNERS_LOADED && item.equals(Items.SPAWNER))
        || (Constants.FORBIDDEN_ARCANUS_LOADED && itemName.equals(QUANTUM_CATCHER))
        || (Constants.CYCLIC_LOADED && itemName.equals(MONSTER_BALL))) {
      EntityType<?> entityType = getCapturedMobEntityType(itemStack);
      String descriptionId = entityType != null ? entityType.getDescriptionId() : "";
      return !descriptionId.isBlank() ? new TranslatableComponent(descriptionId).getString()
          : descriptionId;
    }
    return "";
  }

  public static EntityType<?> getCapturedMobEntityType(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return null;
    }

    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMobEntityType(itemStack);
    }

    // Check for supported items from other mods.
    ResourceLocation itemRegistryName = item.getRegistryName();
    String itemName = itemRegistryName != null ? itemRegistryName.toString() : "";
    if ((Constants.MOB_CATCHER_LOADED
        && (itemName.equals(MOB_CATCHER_DIAMOND) || itemName.equals(MOB_CATCHER_NETHERITE)))
        || (Constants.MOB_CAPTURING_TOOL_LOADED && itemName.equals(MOB_CAPTURING_TOOL))
        || (Constants.MOBCATCHER_LOADED && itemName.equals(MOBCATCHER_NET))
        || (Constants.CREATE_LOADED && itemName.equals(CREATE_BLAZE_BURNER))
        || (Constants.CORAIL_SPAWNERS_LOADED && item.equals(Items.SPAWNER))
        || (Constants.FORBIDDEN_ARCANUS_LOADED && itemName.equals(QUANTUM_CATCHER))
        || (Constants.CYCLIC_LOADED && itemName.equals(MONSTER_BALL))) {
      String capturedMobType = getCapturedMobType(itemStack);
      if (capturedMobType.contains(":")) {
        ResourceLocation resourceLocation = new ResourceLocation(capturedMobType);
        return Registry.ENTITY_TYPE.get(resourceLocation);
      } else {
        log.warn("Unable to get capture mob entity type for {}", capturedMobType);
      }
    } else if (item instanceof SpawnEggItem spawnEggItem) {
      return spawnEggItem.getType(itemStack.getOrCreateTag());
    }
    return null;
  }

  public static String getCapturedMobType(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return "";
    }

    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMobType(itemStack);
    }

    // Check for supported items from other mods.
    ResourceLocation itemRegistryName = item.getRegistryName();
    String itemName = itemRegistryName != null ? itemRegistryName.toString() : "";
    if (Constants.MOB_CATCHER_LOADED
        && (itemName.equals(MOB_CATCHER_DIAMOND) || itemName.equals(MOB_CATCHER_NETHERITE))) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getCompound(MOD_DATA_TAG).getString(ID_TAG);
    } else if (Constants.CREATE_LOADED && itemName.equals(CREATE_BLAZE_BURNER)) {
      return "minecraft:blaze";
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && itemName.equals(MOB_CAPTURING_TOOL)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getCompound(CAPTURED_ENTITY_TAG).getString(ENTITY_TYPE_TAG);
    } else if (Constants.MOBCATCHER_LOADED && itemName.equals(MOBCATCHER_NET)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getCompound(ENTITY_HOLDER_TAG).getString(ID_TAG);
    } else if (Constants.CORAIL_SPAWNERS_LOADED && item.equals(Items.SPAWNER)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getCompound(BLOCK_ENTITY_TAG).getCompound(SPAWN_DATA_TAG)
          .getCompound(ENTITY_TAG).getString(ID_TAG);
    } else if (Constants.FORBIDDEN_ARCANUS_LOADED && itemName.equals(QUANTUM_CATCHER)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getCompound(ENTITY_TAG).getString(ID_TAG);
    } else if (Constants.CYCLIC_LOADED && itemName.equals(MONSTER_BALL)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getString(ID_TAG);
    } else if (item instanceof SpawnEggItem) {
      EntityType<?> entityType = getCapturedMobEntityType(itemStack);
      String descriptionId = entityType != null ? entityType.getDescriptionId() : "";
      if (descriptionId != null && descriptionId.contains("entity.")) {
        String[] descriptionIdParts = descriptionId.split("\\.");
        if (descriptionIdParts.length == 3) {
          return descriptionIdParts[1] + ":" + descriptionIdParts[2];
        }
      }
    }
    return "";
  }

  public static String getLootTable(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return "";
    }

    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getLootTable(itemStack);
    }

    // Check for supported items from other mods.
    ResourceLocation itemRegistryName = item.getRegistryName();
    String itemName = itemRegistryName != null ? itemRegistryName.toString() : "";
    if ((Constants.MOB_CATCHER_LOADED
        && (itemName.equals(MOB_CATCHER_DIAMOND) || itemName.equals(MOB_CATCHER_NETHERITE)))
        || (Constants.CREATE_LOADED && itemName.equals(CREATE_BLAZE_BURNER))
        || (Constants.MOB_CAPTURING_TOOL_LOADED && itemName.equals(MOB_CAPTURING_TOOL))
        || (Constants.MOBCATCHER_LOADED && itemName.equals(MOBCATCHER_NET))
        || (item instanceof SpawnEggItem)
        || (Constants.CORAIL_SPAWNERS_LOADED && item.equals(Items.SPAWNER))
        || (Constants.FORBIDDEN_ARCANUS_LOADED && itemName.equals(QUANTUM_CATCHER))
        || (Constants.CYCLIC_LOADED && itemName.equals(MONSTER_BALL))) {
      String capturedMobType = getCapturedMobType(itemStack);
      if (capturedMobType != null && capturedMobType.contains(":")) {
        String[] mobTypeParts = capturedMobType.split("\\:");
        return mobTypeParts[0] + ":entities/" + mobTypeParts[1];
      }
    }
    return "";
  }

  public static DyeColor getCapturedMobColor(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return null;
    }

    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMobColor(itemStack);
    }
    return null;
  }

  public static boolean getCapturedMobShearedStatus(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return false;
    }

    // Early exit for captured mob items.
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMobShearedStatus(itemStack);
    }
    return false;
  }

}
