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

package de.markusbordihn.easymobfarm.client.model;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ModelManagerInterface {

  Logger log = LogManager.getLogger(Constants.LOG_NAME);
  Set<String> KNOWN_MISSING_MODELS = new HashSet<>();
  String LOG_PREFIX = "[Model Manager]";
  String MODEL_SEARCH_PATH = "/easy_mob_farm/mob_capture_card/";

  ModelResourceLocation DEFAULT_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "mob_capture_card/default"), "inventory");
  ModelResourceLocation DEFAULT_UNCOMMON_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "mob_capture_card/default_uncommon"), "inventory");
  ModelResourceLocation DEFAULT_RARE_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "mob_capture_card/default_rare"), "inventory");
  ModelResourceLocation DEFAULT_EPIC_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "mob_capture_card/default_epic"), "inventory");
  ModelResourceLocation DEFAULT_FISH_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "mob_capture_card/default_fish"), "inventory");

  private static boolean isFish(EntityType<?> entityType) {
    ResourceLocation entityId = EntityType.getKey(entityType);
    Item correspondingItem = BuiltInRegistries.ITEM.get(entityId);
    return correspondingItem != Items.AIR
        && correspondingItem.builtInRegistryHolder().is(ItemTags.FISHES);
  }

  static ModelResourceLocation getModelResourceLocation(final ResourceLocation resourceLocation) {
    return new ModelResourceLocation(
        new ResourceLocation(
            resourceLocation.getNamespace(),
            resourceLocation
                .getPath()
                .replaceFirst("^models/", "")
                .replaceFirst("^item/", "")
                .replaceFirst("\\.json$", "")),
        "inventory");
  }

  static boolean isMobCaptureCardModel(ResourceLocation resourceLocation) {
    return resourceLocation != null && resourceLocation.getPath().contains(MODEL_SEARCH_PATH);
  }

  default BakedModel getModel(ModelResourceLocation modelResourceLocation) {
    if (modelResourceLocation == null) {
      return null;
    }
    BakedModel bakedModel =
        Minecraft.getInstance().getModelManager().getModel(modelResourceLocation);
    if (bakedModel == Minecraft.getInstance().getModelManager().getMissingModel()) {
      if (KNOWN_MISSING_MODELS.add(modelResourceLocation.toString())) {
        log.error("{} Missing model for '{}'", LOG_PREFIX, modelResourceLocation);
      }
      return null;
    }
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }

  default BakedModel getModel(String type, String variant, DyeColor color) {
    MobCaptureCardDefinition mobCaptureCardDefinition =
        MobCaptureCardDefinitionManager.get(new ResourceLocation(type));
    if (mobCaptureCardDefinition == null) {
      return null;
    }

    // Check if the variant and color are valid.
    ModelResourceLocation modelResourceLocation = null;
    if (variant != null && !variant.isBlank() && color != null) {
      if (mobCaptureCardDefinition.variants().containsKey(variant)
          && mobCaptureCardDefinition.variants().get(variant).colors().containsKey(color)) {
        modelResourceLocation =
            getModelResourceLocation(
                mobCaptureCardDefinition.variants().get(variant).colors().get(color).model());
      }
    } else if (variant != null && !variant.isBlank()) {
      if (mobCaptureCardDefinition.variants().containsKey(variant)) {
        modelResourceLocation =
            getModelResourceLocation(mobCaptureCardDefinition.variants().get(variant).model());
      }
    } else if (color != null) {
      String colorName = color.getName().toLowerCase(Locale.ROOT);
      if (mobCaptureCardDefinition.colors().containsKey(colorName)) {
        modelResourceLocation =
            getModelResourceLocation(mobCaptureCardDefinition.colors().get(colorName).model());
      }
    }

    // Fallback to default model if no model is found or if the model is missing.
    ModelResourceLocation defaultModelResourceLocation =
        getModelResourceLocation(mobCaptureCardDefinition.model());
    if (modelResourceLocation == null) {
      modelResourceLocation = defaultModelResourceLocation;
    }
    BakedModel bakedModel = this.getModel(modelResourceLocation);
    if (bakedModel == Minecraft.getInstance().getModelManager().getMissingModel()) {
      bakedModel = Minecraft.getInstance().getModelManager().getModel(defaultModelResourceLocation);
    }

    return bakedModel;
  }

  default BakedModel getModel(String type) {
    return this.getModel(type, null, null);
  }

  default BakedModel getModel(Rarity rarity) {
    if (rarity == null) {
      return null;
    }
    ModelResourceLocation modelResourceLocation =
        switch (rarity) {
          case COMMON -> DEFAULT_MODEL;
          case UNCOMMON -> DEFAULT_UNCOMMON_MODEL;
          case RARE -> DEFAULT_RARE_MODEL;
          case EPIC -> DEFAULT_EPIC_MODEL;
        };
    return this.getModel(modelResourceLocation);
  }

  default BakedModel getModel(EntityType<?> entityType) {
    if (entityType == null || !isFish(entityType)) {
      return null;
    }
    return this.getModel(DEFAULT_FISH_MODEL);
  }

  default BakedModel getDefaultModel() {
    return this.getModel(DEFAULT_MODEL);
  }
}
