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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.kwpugh.mob_catcher.ItemMobCatcher;
import com.kwpugh.mob_catcher.ItemMobCatcherHostile;

import com.matyrobbrt.mobcapturingtool.item.CapturingToolItem;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlockItem;

import de.markusbordihn.easymobfarm.Constants;

public class CapturedMobVirtual {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final String ID_TAG = "id";
  private static final String MOD_DATA_TAG = "mob_data";
  private static final String NAME_TAG = "name";

  protected CapturedMobVirtual() {}

  public static boolean isSupported(ItemStack itemStack) {
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return true;
    } else if (Constants.MOB_CATCHER_LOADED
        && (item instanceof ItemMobCatcher || item instanceof ItemMobCatcherHostile)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      if (compoundTag.contains(MOD_DATA_TAG)
          && compoundTag.getCompound(MOD_DATA_TAG).contains(ID_TAG)) {
        return !compoundTag.getCompound(MOD_DATA_TAG).getString(ID_TAG).isEmpty();
      }
    } else if (Constants.CREATE_LOADED
        && item instanceof BlazeBurnerBlockItem blazeBurnerBlockItem) {
      return blazeBurnerBlockItem.hasCapturedBlaze();
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && item instanceof CapturingToolItem) {
      return CapturingToolItem.getEntityType(itemStack) != null;
    } else if (!(item instanceof AirItem)) {
      log.debug("Unsupported mob catching Item {}", item);
    }
    return false;
  }

  public static boolean hasCapturedMob(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return false;
    }
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.hasCapturedMob(itemStack);
    } else if (Constants.MOB_CATCHER_LOADED
        && (item instanceof ItemMobCatcher || item instanceof ItemMobCatcherHostile)) {
      return !getCapturedMobType(itemStack).isBlank() && !getCapturedMobType(itemStack).isEmpty();
    } else if (Constants.CREATE_LOADED
        && item instanceof BlazeBurnerBlockItem blazeBurnerBlockItem) {
      return blazeBurnerBlockItem.hasCapturedBlaze();
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && item instanceof CapturingToolItem) {
      return CapturingToolItem.getEntityType(itemStack) != null;
    }
    return false;
  }

  public static String getCapturedMob(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return "";
    }
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMob(itemStack);
    } else if (Constants.MOB_CATCHER_LOADED
        && (item instanceof ItemMobCatcher || item instanceof ItemMobCatcherHostile)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getString(NAME_TAG);
    } else if (Constants.CREATE_LOADED && item instanceof BlazeBurnerBlockItem) {
      return "Blaze";
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && item instanceof CapturingToolItem) {
      EntityType<?> entityType = getCapturedMobEntityType(itemStack);
      String descriptionId = entityType != null ? entityType.getDescriptionId() : "";
      return !descriptionId.isBlank() ? new TranslatableComponent(descriptionId).getString()
          : descriptionId;
    }
    return "";
  }

  public static DyeColor getCapturedMobColor(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return null;
    }
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMobColor(itemStack);
    }
    return null;
  }

  public static Entity getCapturedMobEntity(ItemStack itemStack, Level level) {
    CompoundTag compoundTag = itemStack.getOrCreateTag();

    // Recreated captured mob
    EntityType<?> entityType = getCapturedMobEntityType(itemStack);
    if (entityType == null) {
      return null;
    }

    Entity entity = entityType.create(level);
    if (entity != null) {
      // Remove custom compoundTag data from mob entity
      compoundTag.remove(CapturedMob.ENTITY_ID_TAG);
      compoundTag.remove(CapturedMob.ENTITY_LOOT_TABLE_TAG);
      compoundTag.remove(CapturedMob.ENTITY_NAME_TAG);
      compoundTag.remove(CapturedMob.ENTITY_POSSIBLE_LOOT_TAG);
      compoundTag.remove(CapturedMob.ENTITY_TYPE_TAG);
      compoundTag.remove(CapturedMob.ENTITY_COLOR_TAG);
      entity.load(compoundTag);

      // Remove compoundTag data from item.
      int damagedValue = itemStack.getDamageValue();
      itemStack.setTag(new CompoundTag());
      itemStack.setDamageValue(damagedValue);
    }
    return entity;
  }

  public static EntityType<?> getCapturedMobEntityType(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return null;
    }
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMobEntityType(itemStack);
    } else if ((Constants.MOB_CATCHER_LOADED
        && (item instanceof ItemMobCatcher || item instanceof ItemMobCatcherHostile))
        || (Constants.CREATE_LOADED && item instanceof BlazeBurnerBlockItem)) {
      String[] mobTypeParts = getCapturedMobType(itemStack).split(":");
      String entityTypeName = "entity." + mobTypeParts[0] + "." + mobTypeParts[1];
      ResourceLocation resourceLocation = new ResourceLocation(entityTypeName);
      return Registry.ENTITY_TYPE.get(resourceLocation);
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && item instanceof CapturingToolItem) {
      return CapturingToolItem.getEntityType(itemStack);
    }
    return null;
  }

  public static String getCapturedMobType(ItemStack itemStack) {
    if (!isSupported(itemStack)) {
      return "";
    }
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getCapturedMobType(itemStack);
    } else if (Constants.MOB_CATCHER_LOADED
        && (item instanceof ItemMobCatcher || item instanceof ItemMobCatcherHostile)) {
      CompoundTag compoundTag = itemStack.getOrCreateTag();
      return compoundTag.getCompound(MOD_DATA_TAG).getString(ID_TAG);
    } else if (Constants.CREATE_LOADED && item instanceof BlazeBurnerBlockItem) {
      return "minecraft:blaze";
    } else if (Constants.MOB_CAPTURING_TOOL_LOADED && item instanceof CapturingToolItem) {
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
    Item item = itemStack.getItem();
    if (item instanceof CapturedMob) {
      return CapturedMob.getLootTable(itemStack);
    } else if ((Constants.MOB_CATCHER_LOADED
        && (item instanceof ItemMobCatcher || item instanceof ItemMobCatcherHostile))
        || (Constants.CREATE_LOADED && item instanceof BlazeBurnerBlockItem)
        || (Constants.MOB_CAPTURING_TOOL_LOADED && item instanceof CapturingToolItem)) {
      String capturedMobType = getCapturedMobType(itemStack);
      if (capturedMobType != null && capturedMobType.contains(":")) {
        String[] mobTypeParts = capturedMobType.split(":");
        return mobTypeParts[0] + ":entities/" + mobTypeParts[1];
      }
    }
    return "";
  }

}
