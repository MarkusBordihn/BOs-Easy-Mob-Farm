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
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
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

    // Read colors
    Map<String, MobCaptureCardDefinition.Color> colors =
        buffer.readMap(
            FriendlyByteBuf::readUtf,
            buf -> new MobCaptureCardDefinition.Color(buf.readIdentifier()));

    // Read variants
    Map<String, MobCaptureCardDefinition.Variant> variants =
        buffer.readMap(
            FriendlyByteBuf::readUtf,
            buf -> {
              Identifier variantModel = buf.readIdentifier();
              Map<String, MobCaptureCardDefinition.Color> variantColors =
                  buf.readMap(
                      FriendlyByteBuf::readUtf,
                      b -> new MobCaptureCardDefinition.Color(b.readIdentifier()));
              return new MobCaptureCardDefinition.Variant(variantModel, variantColors);
            });

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
    return EntityType.byString(resourceLocation.toString()).orElse(null);
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

    // Write colors
    buffer.writeMap(
        colors, FriendlyByteBuf::writeUtf, (buf, color) -> buf.writeIdentifier(color.model()));

    // Write variants
    buffer.writeMap(
        variants,
        FriendlyByteBuf::writeUtf,
        (buf, variant) -> {
          buf.writeIdentifier(variant.model());
          buf.writeMap(
              variant.colors(),
              FriendlyByteBuf::writeUtf,
              (b, color) -> b.writeIdentifier(color.model()));
        });
  }

  public record Variant(Identifier model, Map<String, Color> colors) {}

  public record Color(Identifier model) {}
}
