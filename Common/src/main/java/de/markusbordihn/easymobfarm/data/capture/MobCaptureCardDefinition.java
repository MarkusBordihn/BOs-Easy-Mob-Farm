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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Rarity;

public record MobCaptureCardDefinition(
    ResourceLocation entity,
    EntityType<?> entityType,
    ResourceLocation model,
    Rarity rarity,
    float scale,
    boolean requiresKilledByPlayer,
    boolean requiresAnimationTick,
    Map<String, Variant> variants,
    Map<String, Color> colors) {

  public MobCaptureCardDefinition(
      ResourceLocation entity,
      ResourceLocation model,
      Rarity rarity,
      float scale,
      boolean requiresKilledByPlayer,
      boolean requiresAnimationTick,
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
        variants,
        colors);
  }

  public static MobCaptureCardDefinition decode(FriendlyByteBuf buffer) {
    // Read basic properties
    ResourceLocation entity = buffer.readResourceLocation();
    ResourceLocation model = buffer.readResourceLocation();
    Rarity rarity = buffer.readEnum(Rarity.class);
    float scale = buffer.readFloat();
    boolean requiresKilledByPlayer = buffer.readBoolean();
    boolean requiresAnimationTick = buffer.readBoolean();

    // Read colors
    Map<String, MobCaptureCardDefinition.Color> colors =
        buffer.readMap(
            FriendlyByteBuf::readUtf,
            buf -> new MobCaptureCardDefinition.Color(buf.readResourceLocation()));

    // Read variants
    Map<String, MobCaptureCardDefinition.Variant> variants =
        buffer.readMap(
            FriendlyByteBuf::readUtf,
            buf -> {
              ResourceLocation variantModel = buf.readResourceLocation();
              Map<String, MobCaptureCardDefinition.Color> variantColors =
                  buf.readMap(
                      FriendlyByteBuf::readUtf,
                      b -> new MobCaptureCardDefinition.Color(b.readResourceLocation()));
              return new MobCaptureCardDefinition.Variant(variantModel, variantColors);
            });

    return new MobCaptureCardDefinition(
        entity,
        model,
        rarity,
        scale,
        requiresKilledByPlayer,
        requiresAnimationTick,
        variants,
        colors);
  }

  public static EntityType<?> getEntityType(ResourceLocation resourceLocation) {
    return EntityType.byString(resourceLocation.toString()).orElse(null);
  }

  public static ResourceLocation getModelResourceLocation(
      ResourceLocation resourceLocation, Rarity rarity) {
    if (resourceLocation != null) {
      return resourceLocation;
    }
    switch (rarity) {
      case UNCOMMON -> {
        return ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "item/mob_capture_card/default_uncommon");
      }
      case RARE -> {
        return ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "item/mob_capture_card/default_rare");
      }
      case EPIC -> {
        return ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "item/mob_capture_card/default_epic");
      }
      default -> {
        return ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "item/mob_capture_card/default");
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
        this.variants,
        this.colors);
  }

  public void encode(FriendlyByteBuf buffer) {
    // Write basic properties
    buffer.writeResourceLocation(entity);
    buffer.writeResourceLocation(model);
    buffer.writeEnum(rarity);
    buffer.writeFloat(scale);
    buffer.writeBoolean(requiresKilledByPlayer);
    buffer.writeBoolean(requiresAnimationTick);

    // Write colors
    buffer.writeMap(
        colors,
        FriendlyByteBuf::writeUtf,
        (buf, color) -> buf.writeResourceLocation(color.model()));

    // Write variants
    buffer.writeMap(
        variants,
        FriendlyByteBuf::writeUtf,
        (buf, variant) -> {
          buf.writeResourceLocation(variant.model());
          buf.writeMap(
              variant.colors(),
              FriendlyByteBuf::writeUtf,
              (b, color) -> b.writeResourceLocation(color.model()));
        });
  }

  public record Variant(ResourceLocation model, Map<String, Color> colors) {}

  public record Color(ResourceLocation model) {}
}
