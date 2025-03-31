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

package de.markusbordihn.easymobfarm.data.capture;

import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.MobCatcherItem;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class MobCaptureDataSupport {

  // Supported mod mob catcher items
  public static final String CREATE_BLAZE_BURNER = "create:blaze_burner";
  public static final String MOB_CATCHER_DIAMOND = "mob_catcher:diamond_mob_catcher";
  public static final String MOB_CATCHER_NETHERITE = "mob_catcher:netherite_mob_catcher";
  public static final String MOB_CAPTURING_TOOL = "mobcapturingtool:mob_capturing_tool";
  public static final String SUPPLEMENTARIES_CAGE = "supplementaries:cage";
  public static final String SUPPLEMENTARIES_JAR = "supplementaries:jar";
  public static final String PRODUCTIVE_BEES_BEE_CAGE = "productivebees:bee_cage";
  public static final String PRODUCTIVE_BEES_BEE_JAR = "productivebees:bee_jar";

  // Tags
  private static final String MOD_DATA_TAG = "mob_data";
  private static final String ID_TAG = "id";
  private static final String CAPTURED_ENTITY_TAG = "CapturedEntity";
  private static final String ENTITY_TYPE_TAG = "EntityType";
  private static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
  private static final String MOB_HOLDER_TAG = "MobHolder";
  private static final String ENTITY_DATA_TAG = "EntityData";
  private static final String ENTITY_TAG = "Entity";
  private static final String ENTITY__TAG = ENTITY_TAG.toLowerCase(Locale.ROOT);

  public static boolean isSupported(ItemStack itemStack) {
    if (!isValidItemStack(itemStack)) {
      return false;
    }

    // Early return for default mob capture items.
    Item item = itemStack.getItem();
    if (item instanceof MobCaptureCardItem
        || item instanceof SpawnEggItem
        || item instanceof MobCatcherItem) {
      return true;
    }

    // More complex check for custom items.
    String itemRegistryName = getItemRegistryName(item);
    if (itemRegistryName.isEmpty()) {
      return false;
    }

    // Check for supported mod items.
    return CREATE_BLAZE_BURNER.equals(itemRegistryName)
        || MOB_CATCHER_DIAMOND.equals(itemRegistryName)
        || MOB_CATCHER_NETHERITE.equals(itemRegistryName)
        || MOB_CAPTURING_TOOL.equals(itemRegistryName)
        || SUPPLEMENTARIES_CAGE.equals(itemRegistryName)
        || SUPPLEMENTARIES_JAR.equals(itemRegistryName)
        || PRODUCTIVE_BEES_BEE_CAGE.equals(itemRegistryName)
        || PRODUCTIVE_BEES_BEE_JAR.equals(itemRegistryName);
  }

  public static EntityType<?> getEntityType(ItemStack itemStack, Level level) {
    if (!isValidItemStack(itemStack)) {
      return null;
    }

    // Early return for default mob capture items.
    Item item = itemStack.getItem();
    if (item instanceof SpawnEggItem spawnEggItem && level != null) {
      return spawnEggItem.getType(level.registryAccess(), itemStack);
    }

    // Use registry name to identify the entity type.
    String itemRegistryName = getItemRegistryName(item);
    if (CREATE_BLAZE_BURNER.equals(itemRegistryName)) {
      return EntityType.BLAZE;
    }

    // Use compound tag to identify the entity type.
    CompoundTag compoundTag =
        itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getUnsafe();
    if (MOB_CATCHER_DIAMOND.equals(itemRegistryName)
        || MOB_CATCHER_NETHERITE.equals(itemRegistryName)) {
      if (compoundTag.contains(MOD_DATA_TAG)
          && compoundTag.getCompound(MOD_DATA_TAG).isPresent()
          && compoundTag.getCompound(MOD_DATA_TAG).get().contains(ID_TAG)
          && compoundTag.getCompound(MOD_DATA_TAG).get().getString(ID_TAG).isPresent()) {
        return getEntityType(compoundTag.getCompound(MOD_DATA_TAG).get().getString(ID_TAG).get());
      }
    }
    if (MOB_CAPTURING_TOOL.equals(itemRegistryName)) {
      if (compoundTag.contains(CAPTURED_ENTITY_TAG)
          && compoundTag.getCompound(CAPTURED_ENTITY_TAG).isPresent()
          && compoundTag.getCompound(CAPTURED_ENTITY_TAG).get().contains(ENTITY_TYPE_TAG)
          && compoundTag
              .getCompound(CAPTURED_ENTITY_TAG)
              .get()
              .getString(ENTITY_TYPE_TAG)
              .isPresent()) {
        return getEntityType(
            compoundTag.getCompound(CAPTURED_ENTITY_TAG).get().getString(ENTITY_TYPE_TAG).get());
      }
    }
    if (SUPPLEMENTARIES_CAGE.equals(itemRegistryName)
        || SUPPLEMENTARIES_JAR.equals(itemRegistryName)) {
      if (compoundTag.contains(BLOCK_ENTITY_TAG)
          && compoundTag.getCompound(BLOCK_ENTITY_TAG).isPresent()
          && compoundTag.getCompound(BLOCK_ENTITY_TAG).get().contains(MOB_HOLDER_TAG)
          && compoundTag.getCompound(BLOCK_ENTITY_TAG).get().getCompound(MOB_HOLDER_TAG).isPresent()
          && compoundTag
              .getCompound(BLOCK_ENTITY_TAG)
              .get()
              .getCompound(MOB_HOLDER_TAG)
              .get()
              .contains(ENTITY_DATA_TAG)
          && compoundTag
              .getCompound(BLOCK_ENTITY_TAG)
              .get()
              .getCompound(MOB_HOLDER_TAG)
              .get()
              .getCompound(ENTITY_DATA_TAG)
              .isPresent()) {
        return getEntityType(
            compoundTag
                .getCompound(BLOCK_ENTITY_TAG)
                .get()
                .getCompound(MOB_HOLDER_TAG)
                .get()
                .getCompound(ENTITY_DATA_TAG)
                .get()
                .getString(ID_TAG)
                .get());
      }
    }
    if (PRODUCTIVE_BEES_BEE_CAGE.equals(itemRegistryName)
        || PRODUCTIVE_BEES_BEE_JAR.equals(itemRegistryName)) {
      if (compoundTag.contains(ENTITY__TAG) && compoundTag.getString(ENTITY__TAG).isPresent()) {
        return getEntityType(compoundTag.getString(ENTITY__TAG).get());
      }
    }

    return null;
  }

  public static EntityType<?> getEntityType(String entityName) {
    if (entityName == null || entityName.isEmpty()) {
      return null;
    }
    Optional<EntityType<?>> entityTypeHolder =
        BuiltInRegistries.ENTITY_TYPE.getOptional(ResourceLocation.parse(entityName));
    return entityTypeHolder.orElse(null);
  }

  public static String getEntityTypeName(ItemStack itemStack, Level level) {
    EntityType<?> entityType = getEntityType(itemStack, level);
    if (entityType != null) {
      return BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
    }
    return "";
  }

  public static String getItemRegistryName(Item item) {
    if (item == null) {
      return "";
    }
    return BuiltInRegistries.ITEM.getKey(item).toString();
  }

  private static boolean isValidItemStack(ItemStack itemStack) {
    return itemStack != null && !itemStack.isEmpty() && itemStack.getCount() == 1;
  }
}
