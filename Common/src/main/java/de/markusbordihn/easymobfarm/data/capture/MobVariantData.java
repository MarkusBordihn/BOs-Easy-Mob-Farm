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

import de.markusbordihn.easymobfarm.compat.CompatConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.npc.Villager;

public class MobVariantData {

  public static final String VARIANT_TAG = "Variant";
  public static final String LARGE_VARIANT = "large";
  public static final String MEDIUM_VARIANT = "medium";
  public static final String SMALL_VARIANT = "small";
  public static final String TINY_VARIANT = "tiny";

  private static final Map<CatVariant, String> CAT_VARIANT_MAP = new HashMap<>();
  private static final Map<FrogVariant, String> FROG_VARIANT_MAP = new HashMap<>();
  private static final Map<ResourceLocation, String> SWAMPIER_SWAMPS_FROG_VARIANT_MAP =
      new HashMap<>();

  static {
    // Cat variant map
    CAT_VARIANT_MAP.put(CatVariant.TABBY, "tabby");
    CAT_VARIANT_MAP.put(CatVariant.BLACK, "black");
    CAT_VARIANT_MAP.put(CatVariant.RED, "red");
    CAT_VARIANT_MAP.put(CatVariant.SIAMESE, "siamese");
    CAT_VARIANT_MAP.put(CatVariant.BRITISH_SHORTHAIR, "british_shorthair");
    CAT_VARIANT_MAP.put(CatVariant.CALICO, "calico");
    CAT_VARIANT_MAP.put(CatVariant.PERSIAN, "persian");
    CAT_VARIANT_MAP.put(CatVariant.RAGDOLL, "ragdoll");
    CAT_VARIANT_MAP.put(CatVariant.WHITE, "white");
    CAT_VARIANT_MAP.put(CatVariant.JELLIE, "jellie");
    CAT_VARIANT_MAP.put(CatVariant.ALL_BLACK, "all_black");

    // Frog variant map
    FROG_VARIANT_MAP.put(FrogVariant.COLD, "cold");
    FROG_VARIANT_MAP.put(FrogVariant.WARM, "warm");
    FROG_VARIANT_MAP.put(FrogVariant.TEMPERATE, "temperate");

    if (CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_white.png"),
          "white");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_magenta.png"),
          "magenta");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_light_blue.png"),
          "light_blue");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_yellow.png"),
          "yellow");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_lime.png"),
          "lime");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_pink.png"),
          "pink");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_gray.png"),
          "gray");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_cyan.png"),
          "cyan");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_purple.png"),
          "purple");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_blue.png"),
          "blue");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_brown.png"),
          "brown");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_red.png"),
          "red");
      SWAMPIER_SWAMPS_FROG_VARIANT_MAP.put(
          new ResourceLocation(
              CompatConstants.MOD_SWAMPIER_SWAMPS_ID, "textures/entity/frog/frog_black.png"),
          "black");
    }
  }

  private MobVariantData() {}

  public static String getVariant(final EntityType<?> entityType) {
    return null;
  }

  public static String getVariant(final LivingEntity livingEntity) {
    if (livingEntity instanceof Cat cat) {
      return CAT_VARIANT_MAP.get(cat.getCatVariant());
    } else if (livingEntity instanceof Villager villager) {
      return villager.getVillagerData().getProfession().name();
    } else if (livingEntity instanceof MagmaCube magmaCube) {
      return getSizeVariant(magmaCube.getSize());
    } else if (livingEntity instanceof Slime slime) {
      return getSizeVariant(slime.getSize());
    } else if (livingEntity instanceof Frog frog) {
      String frogVariant = FROG_VARIANT_MAP.get(frog.getVariant());
      if (frogVariant != null) {
        return frogVariant;
      }

      // Swampier Swamps mod support
      if (CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED) {
        return SWAMPIER_SWAMPS_FROG_VARIANT_MAP.get(frog.getVariant().texture());
      }
    }
    return null;
  }

  public static String getVariant(final CompoundTag compoundTag) {
    if (compoundTag == null) {
      return null;
    }
    if (compoundTag.contains(VARIANT_TAG)) {
      return compoundTag.getString(VARIANT_TAG);
    }
    if (compoundTag.contains(VARIANT_TAG.toLowerCase(Locale.ROOT))) {
      return compoundTag.getString(VARIANT_TAG.toLowerCase(Locale.ROOT));
    }
    return null;
  }

  public static String getSizeVariant(float size) {
    if (size <= 1.0f) {
      return TINY_VARIANT;
    }
    if (size <= 2.0f) {
      return SMALL_VARIANT;
    }
    if (size < 4.0f) {
      return MEDIUM_VARIANT;
    }
    if (size >= 4.0f) {
      return LARGE_VARIANT;
    }
    return "";
  }

  public static CatVariant getCatVariant(final String variant) {
    for (Map.Entry<CatVariant, String> entry : CAT_VARIANT_MAP.entrySet()) {
      if (entry.getValue().equals(variant)) {
        return entry.getKey();
      }
    }
    return CatVariant.BLACK;
  }

  public static FrogVariant getFrogVariant(final String variant) {
    for (Map.Entry<FrogVariant, String> entry : FROG_VARIANT_MAP.entrySet()) {
      if (entry.getValue().equals(variant)) {
        return entry.getKey();
      }
    }
    return FrogVariant.TEMPERATE;
  }
}
