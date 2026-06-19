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

import java.util.Locale;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class MobNameData {

  public static final String NAME_TAG = "Name";
  public static final String TYPE_TAG = "Type";
  public static final String ID_TAG = "id";

  private MobNameData() {}

  public static String getName(final EntityType<?> entityType) {
    return entityType.getDescriptionId();
  }

  public static String getName(final LivingEntity livingEntity) {
    return livingEntity.getType().getDescriptionId();
  }

  public static String getName(final CompoundTag compoundTag) {
    if (compoundTag == null) {
      return "";
    }

    // Check if we have a valid name tag.
    String name = compoundTag.getString(NAME_TAG).orElse("");
    if (name.startsWith("entity.") && name.equals(name.toLowerCase(Locale.ROOT))) {
      return name;
    }

    // Check if we have a valid type tag.
    String entityTypeName = compoundTag.getString(TYPE_TAG).orElse("");
    if (!compoundTag.contains(TYPE_TAG)) {
      entityTypeName = compoundTag.getString(ID_TAG).orElse("");
    }
    if (!entityTypeName.contains(":")
        || !entityTypeName.equals(entityTypeName.toLowerCase(Locale.ROOT))) {
      return "";
    }

    // Get Name over entity type, if possible.
    EntityType<?> entityType =
        BuiltInRegistries.ENTITY_TYPE.getOptional(Identifier.tryParse(entityTypeName)).orElse(null);
    return entityType != null ? entityType.getDescriptionId() : "";
  }
}
