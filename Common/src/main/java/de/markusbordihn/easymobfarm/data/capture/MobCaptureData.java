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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public record MobCaptureData(
    String name,
    String type,
    EntityType<?> entityType,
    CompoundTag data,
    DyeColor color,
    String variant,
    Rarity rarity,
    boolean isFoil) {

  private static final int MAX_ID_LIMIT = 16777216;

  public MobCaptureData(final String name, final EntityType<?> entityType, final Rarity rarity) {
    this(
        name,
        MobEntityTypeData.getEntityTypeName(entityType),
        MobEntityTypeData.getEntityType(entityType),
        MobEntityData.getMobEntityData(entityType),
        MobColorData.getColor(entityType),
        MobVariantData.getVariant(entityType),
        rarity,
        MobFoilData.getRandomFoil());
  }

  public MobCaptureData(final EntityType<?> entityType) {
    this(
        MobNameData.getName(entityType),
        MobEntityTypeData.getEntityTypeName(entityType),
        MobEntityTypeData.getEntityType(entityType),
        MobEntityData.getMobEntityData(entityType),
        MobColorData.getColor(entityType),
        MobVariantData.getVariant(entityType),
        MobRarityData.getRarity(entityType),
        MobFoilData.getRandomFoil());
  }

  public MobCaptureData(final LivingEntity livingEntity) {
    this(
        MobNameData.getName(livingEntity),
        MobEntityTypeData.getEntityTypeName(livingEntity),
        MobEntityTypeData.getEntityType(livingEntity),
        MobEntityData.getMobEntityData(livingEntity),
        MobColorData.getColor(livingEntity),
        MobVariantData.getVariant(livingEntity),
        MobRarityData.getRarity(livingEntity),
        MobFoilData.getRandomFoil());
  }

  public MobCaptureData(final ItemStack itemStack, final CompoundTag compoundTag) {
    this(
        MobNameData.getName(compoundTag),
        MobEntityTypeData.getEntityTypeName(itemStack, compoundTag),
        MobEntityTypeData.getEntityType(itemStack, compoundTag),
        MobEntityData.getMobEntityData(compoundTag),
        MobColorData.getColor(compoundTag),
        MobVariantData.getVariant(compoundTag),
        MobRarityData.getRarity(compoundTag),
        MobFoilData.getFoil(compoundTag));
  }

  public MobCaptureData(final CompoundTag compoundTag) {
    this(
        MobNameData.getName(compoundTag),
        MobEntityTypeData.getEntityTypeName(compoundTag),
        MobEntityTypeData.getEntityType(compoundTag),
        MobEntityData.getMobEntityData(compoundTag),
        MobColorData.getColor(compoundTag),
        MobVariantData.getVariant(compoundTag),
        MobRarityData.getRarity(compoundTag),
        MobFoilData.getFoil(compoundTag));
  }

  public int getCardId() {
    int namespaceHash = (this.type != null) ? this.type.split(":")[0].hashCode() : 0;
    int pathHash = (this.type != null) ? this.type.split(":")[1].hashCode() : 0;
    int colorHash = (this.color != null) ? this.color.hashCode() : 0;
    int variantHash = (this.variant != null) ? this.variant.hashCode() : 0;

    int result = 17;
    result = 31 * result + namespaceHash;
    result = 31 * result + pathHash;
    result = 31 * result + colorHash;
    result = 31 * result + variantHash;
    result = 31 * result + (this.isFoil ? 1 : 0);
    result = result ^ (result >>> 16);

    return (result & 0x7FFFFFFF) % MAX_ID_LIMIT;
  }

  public MobCaptureData withColor(final DyeColor color) {
    return new MobCaptureData(name, type, entityType, data, color, variant, rarity, isFoil);
  }

  public MobCaptureData withVariant(final String variant) {
    return new MobCaptureData(name, type, entityType, data, color, variant, rarity, isFoil);
  }

  public MobCaptureData withRarity(final Rarity rarity) {
    return new MobCaptureData(name, type, entityType, data, color, variant, rarity, isFoil);
  }

  public MobCaptureData withFoil(final boolean isFoil) {
    return new MobCaptureData(name, type, entityType, data, color, variant, rarity, isFoil);
  }

  public MobCaptureData withData(final CompoundTag data) {
    return new MobCaptureData(name, type, entityType, data, color, variant, rarity, isFoil);
  }

  public boolean hasColor() {
    return this.color != null;
  }

  public boolean hasData() {
    return this.data != null && !this.data.isEmpty();
  }

  public boolean hasVariant() {
    return this.variant != null && !this.variant.isEmpty();
  }

  public boolean hasRarity() {
    return this.rarity != null;
  }

  public CompoundTag write(final CompoundTag compoundTag) {
    compoundTag.putString(MobNameData.NAME_TAG, this.name);
    compoundTag.putString(
        MobEntityTypeData.TYPE_TAG,
        BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType).toString());
    compoundTag.put(MobEntityData.DATA_TAG, this.data);
    if (this.hasColor()) {
      compoundTag.putString(MobColorData.COLOR_TAG, this.color.getName());
    }
    if (this.hasVariant()) {
      compoundTag.putString(MobVariantData.VARIANT_TAG, this.variant);
    }
    compoundTag.putString(MobRarityData.RARITY_TAG, this.rarity.name());
    compoundTag.putBoolean(MobFoilData.FOIL_TAG, this.isFoil);
    return compoundTag;
  }

  public CompoundTag createTag() {
    return write(new CompoundTag());
  }

  @Override
  public String toString() {
    return "MobCaptureData{"
        + ", name='"
        + name
        + ", entityType="
        + entityType
        + ", color="
        + color
        + ", variant="
        + variant
        + ", rarity="
        + rarity
        + ", isFoil="
        + isFoil
        + ", data="
        + data
        + '}';
  }
}
