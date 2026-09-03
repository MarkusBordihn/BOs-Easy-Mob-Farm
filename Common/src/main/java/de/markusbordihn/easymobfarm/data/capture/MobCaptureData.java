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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.markusbordihn.easymobfarm.network.codec.ModStreamCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public record MobCaptureData(
    String name,
    String type,
    EntityType<?> entityType,
    CompoundTag data,
    MobColor color,
    String variant,
    Rarity rarity,
    boolean isFoil) {

  public static final String TYPE_SEPARATOR = ":";
  public static final String ID = "mob_capture_data";
  public static final MobCaptureData EMPTY =
      new MobCaptureData(
          "",
          "",
          EntityType.ARMOR_STAND,
          new CompoundTag(),
          MobColor.NONE,
          "",
          Rarity.COMMON,
          false);
  public static final Codec<MobCaptureData> CODEC =
      RecordCodecBuilder.create(
          instance ->
              instance
                  .group(
                      Codec.STRING.fieldOf("name").forGetter(MobCaptureData::name),
                      Codec.STRING.fieldOf("type").forGetter(MobCaptureData::type),
                      BuiltInRegistries.ENTITY_TYPE
                          .byNameCodec()
                          .fieldOf("entityType")
                          .forGetter(MobCaptureData::entityType),
                      CompoundTag.CODEC
                          .optionalFieldOf("data", new CompoundTag())
                          .forGetter(MobCaptureData::data),
                      MobColor.CODEC
                          .optionalFieldOf("color", MobColor.NONE)
                          .forGetter(MobCaptureData::color),
                      Codec.STRING
                          .optionalFieldOf("variant", "")
                          .forGetter(MobCaptureData::variant),
                      Rarity.CODEC
                          .optionalFieldOf("rarity", Rarity.COMMON)
                          .forGetter(MobCaptureData::rarity),
                      Codec.BOOL.optionalFieldOf("isFoil", false).forGetter(MobCaptureData::isFoil))
                  .apply(instance, MobCaptureData::new));
  public static final StreamCodec<RegistryFriendlyByteBuf, MobCaptureData> STREAM_CODEC =
      ModStreamCodec.composite(
          ByteBufCodecs.STRING_UTF8,
          MobCaptureData::name,
          ByteBufCodecs.STRING_UTF8,
          MobCaptureData::type,
          ByteBufCodecs.registry(Registries.ENTITY_TYPE),
          MobCaptureData::entityType,
          ByteBufCodecs.COMPOUND_TAG,
          MobCaptureData::data,
          MobColor.STREAM_CODEC,
          MobCaptureData::color,
          ByteBufCodecs.STRING_UTF8,
          MobCaptureData::variant,
          Rarity.STREAM_CODEC,
          MobCaptureData::rarity,
          ByteBufCodecs.BOOL,
          MobCaptureData::isFoil,
          MobCaptureData::new);
  private static final int MAX_ID_LIMIT = 16777216;

  public MobCaptureData {
    variant = variant != null ? variant : "";
  }

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
    int namespaceHash = 0;
    int pathHash = 0;
    if (this.type != null) {
      String[] parts = this.type.split(":");
      if (parts.length > 0) {
        namespaceHash = parts[0].hashCode();
      }
      if (parts.length > 1) {
        pathHash = parts[1].hashCode();
      }
    }
    int colorHash = (this.color != null) ? this.color.getName().hashCode() : 0;
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

  public MobCaptureData withColor(final MobColor color) {
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

  public boolean isEmpty() {
    return this.equals(EMPTY);
  }

  public boolean hasColor() {
    return this.color != null && this.color != MobColor.NONE;
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
