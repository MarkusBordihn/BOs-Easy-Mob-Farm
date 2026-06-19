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

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MobEntityTypeData {

  public static final String TYPE_TAG = "Type";
  public static final String ENTITY_TYPE_TAG = "EntityTag";
  public static final String ID_TAG = "id";

  private MobEntityTypeData() {}

  public static String getEntityTypeName(LivingEntity livingEntity) {
    return getEntityTypeName(livingEntity.getType());
  }

  public static String getEntityTypeName(EntityType<?> entityType) {
    return BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
  }

  public static String getEntityTypeName(
      ItemStack itemStack, CompoundTag compoundTag, Level level) {
    if (itemStack == null || itemStack.isEmpty()) {
      return getEntityTypeName(compoundTag);
    }

    // Use MobCaptureDataSupport to get the entity type name
    if (MobCaptureDataSupport.isSupported(itemStack)) {
      String entityTypeName = MobCaptureDataSupport.getEntityTypeName(itemStack, level);
      if (entityTypeName != null) {
        return entityTypeName;
      }
    }

    return getEntityTypeName(compoundTag);
  }

  public static String getEntityTypeName(CompoundTag compoundTag) {
    if (compoundTag == null) {
      return "";
    }

    if (compoundTag.contains(TYPE_TAG) && compoundTag.getString(TYPE_TAG).isPresent()) {
      return compoundTag.getString(TYPE_TAG).get();
    }
    if (compoundTag.contains(ENTITY_TYPE_TAG)
        && compoundTag.getCompound(ENTITY_TYPE_TAG).isPresent()) {
      CompoundTag entityTypeTag = compoundTag.getCompound(ENTITY_TYPE_TAG).get();
      if (entityTypeTag.contains(ID_TAG) && entityTypeTag.getString(ID_TAG).isPresent()) {
        return entityTypeTag.getString(ID_TAG).get();
      }
    }

    return "";
  }

  public static EntityType<?> getEntityType(
      ItemStack itemStack, CompoundTag compoundTag, Level level) {
    if (itemStack == null || itemStack.isEmpty()) {
      return getEntityType(compoundTag);
    }

    // Use MobCaptureDataSupport to get the entity type
    if (MobCaptureDataSupport.isSupported(itemStack)) {
      EntityType<?> entityType = MobCaptureDataSupport.getEntityType(itemStack, level);
      if (entityType != null) {
        return entityType;
      }
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
    if (entityTypeName == null || entityTypeName.isEmpty()) {
      return null;
    }
    return BuiltInRegistries.ENTITY_TYPE
        .getOptional(Identifier.tryParse(entityTypeName))
        .orElse(null);
  }
}
