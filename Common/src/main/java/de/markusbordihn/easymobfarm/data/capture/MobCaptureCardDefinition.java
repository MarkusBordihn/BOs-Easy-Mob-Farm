/*
 * Copyright 2025 Markus Bordihn
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

import de.markusbordihn.easymobfarm.Constants;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Rarity;

public record MobCaptureCardDefinition(
    Identifier entity,
    EntityType<?> entityType,
    Identifier model,
    Rarity rarity,
    float scale,
    boolean requiresKilledByPlayer,
    boolean requiresAnimationTick,
    boolean supportsKnifeEnhancement,
    Map<String, Variant> variants,
    Map<String, Color> colors) {

  public static final int MAXIMUM_COLORS = 64;
  public static final int MAXIMUM_VARIANTS = 64;
  private static final StreamCodec<ByteBuf, Map<String, Color>> COLORS_STREAM_CODEC =
      ByteBufCodecs.map(
          HashMap::new,
          ByteBufCodecs.STRING_UTF8,
          Identifier.STREAM_CODEC.map(Color::new, Color::model),
          MAXIMUM_COLORS);
  private static final StreamCodec<ByteBuf, Map<String, Variant>> VARIANTS_STREAM_CODEC =
      ByteBufCodecs.map(
          HashMap::new,
          ByteBufCodecs.STRING_UTF8,
          StreamCodec.composite(
              Identifier.STREAM_CODEC,
              Variant::model,
              COLORS_STREAM_CODEC,
              Variant::colors,
              Variant::new),
          MAXIMUM_VARIANTS);

  public MobCaptureCardDefinition(
      Identifier entity,
      Identifier model,
      Rarity rarity,
      float scale,
      boolean requiresKilledByPlayer,
      boolean requiresAnimationTick,
      boolean supportsKnifeEnhancement,
      Map<String, Variant> variants,
      Map<String, Color> colors) {
    this(
        entity,
        getEntityType(entity),
        getModelResourceLocation(model, rarity != null ? rarity : Rarity.COMMON),
        rarity != null ? rarity : Rarity.COMMON,
        scale >= 0 ? scale : 1.0F,
        requiresKilledByPlayer,
        requiresAnimationTick,
        supportsKnifeEnhancement,
        variants,
        colors);
  }

  public static MobCaptureCardDefinition decode(FriendlyByteBuf buffer) {
    // Read basic properties
    Identifier entity = buffer.readIdentifier();
    Identifier model = buffer.readIdentifier();
    Rarity rarity = buffer.readEnum(Rarity.class);
    float scale = buffer.readFloat();
    boolean requiresKilledByPlayer = buffer.readBoolean();
    boolean requiresAnimationTick = buffer.readBoolean();
    boolean supportsKnifeEnhancement = buffer.readBoolean();

    Map<String, Color> colors = COLORS_STREAM_CODEC.decode(buffer);
    Map<String, Variant> variants = VARIANTS_STREAM_CODEC.decode(buffer);

    return new MobCaptureCardDefinition(
        entity,
        model,
        rarity,
        scale,
        requiresKilledByPlayer,
        requiresAnimationTick,
        supportsKnifeEnhancement,
        variants,
        colors);
  }

  public static EntityType<?> getEntityType(Identifier resourceLocation) {
    return BuiltInRegistries.ENTITY_TYPE.getOptional(resourceLocation).orElse(null);
  }

  public static Identifier getModelResourceLocation(Identifier resourceLocation, Rarity rarity) {
    if (resourceLocation != null) {
      return resourceLocation;
    }
    switch (rarity) {
      case UNCOMMON -> {
        return Identifier.fromNamespaceAndPath(
            Constants.MOD_ID, "item/mob_capture_card/default_uncommon");
      }
      case RARE -> {
        return Identifier.fromNamespaceAndPath(
            Constants.MOD_ID, "item/mob_capture_card/default_rare");
      }
      case EPIC -> {
        return Identifier.fromNamespaceAndPath(
            Constants.MOD_ID, "item/mob_capture_card/default_epic");
      }
      default -> {
        return Identifier.fromNamespaceAndPath(Constants.MOD_ID, "item/mob_capture_card/default");
      }
    }
  }

  public MobCaptureCardDefinition withEntityType(EntityType<?> entityType) {
    return new MobCaptureCardDefinition(
        this.entity,
        entityType,
        this.model,
        this.rarity,
        this.scale,
        this.requiresKilledByPlayer,
        this.requiresAnimationTick,
        this.supportsKnifeEnhancement,
        this.variants,
        this.colors);
  }

  public void encode(FriendlyByteBuf buffer) {
    // Write basic properties
    buffer.writeIdentifier(entity);
    buffer.writeIdentifier(model);
    buffer.writeEnum(rarity);
    buffer.writeFloat(scale);
    buffer.writeBoolean(requiresKilledByPlayer);
    buffer.writeBoolean(requiresAnimationTick);
    buffer.writeBoolean(supportsKnifeEnhancement);

    COLORS_STREAM_CODEC.encode(buffer, colors);
    VARIANTS_STREAM_CODEC.encode(buffer, variants);
  }

  public record Variant(Identifier model, Map<String, Color> colors) {}

  public record Color(Identifier model) {}
}
