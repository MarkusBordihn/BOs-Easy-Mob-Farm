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

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;

public class MobEntityTypeData {

  public static final String TYPE_TAG = "Type";
  public static final String ENTITY_TYPE_TAG = "EntityTag";
  public static final String ID_TAG = "id";

  private MobEntityTypeData() {}

  public static String getEntityTypeName(LivingEntity livingEntity) {
    return getEntityTypeName(livingEntity.getType());
  }

  public static String getEntityTypeName(EntityType<?> entityType) {
    return Registry.ENTITY_TYPE.getKey(entityType).toString();
  }

  public static String getEntityTypeName(ItemStack itemStack, CompoundTag compoundTag) {
    if (itemStack == null || itemStack.isEmpty()) {
      return getEntityTypeName(compoundTag);
    }

    Item item = itemStack.getItem();
    if (item instanceof SpawnEggItem spawnEggItem) {
      return getEntityTypeName(spawnEggItem.getType(itemStack.getOrCreateTag()));
    }

    return getEntityTypeName(compoundTag);
  }

  public static String getEntityTypeName(CompoundTag compoundTag) {
    if (compoundTag == null) {
      return null;
    }

    if (compoundTag.contains(TYPE_TAG)) {
      return compoundTag.getString(TYPE_TAG);
    }
    if (compoundTag.contains(ENTITY_TYPE_TAG, 10)) {
      CompoundTag entityTypeTag = compoundTag.getCompound(ENTITY_TYPE_TAG);
      if (entityTypeTag.contains(ID_TAG, 8)) {
        return entityTypeTag.getString(ID_TAG);
      }
    }
    return null;
  }

  public static EntityType<?> getEntityType(ItemStack itemStack, CompoundTag compoundTag) {
    if (itemStack == null || itemStack.isEmpty()) {
      return getEntityType(compoundTag);
    }

    Item item = itemStack.getItem();
    if (item instanceof SpawnEggItem spawnEggItem) {
      return getEntityType(spawnEggItem.getType(itemStack.getOrCreateTag()));
    }

    return getEntityType(compoundTag);
  }

  public static EntityType<?> getEntityType(EntityType<?> entityType) {
    return entityType;
  }

  public static EntityType<?> getEntityType(LivingEntity livingEntity) {
    return livingEntity.getType();
  }

  public static EntityType<?> getEntityType(CompoundTag compoundTag) {
    String entityTypeName = getEntityTypeName(compoundTag);
    if (entityTypeName != null) {
      return EntityType.byString(entityTypeName).orElse(null);
    }
    return null;
  }
}
