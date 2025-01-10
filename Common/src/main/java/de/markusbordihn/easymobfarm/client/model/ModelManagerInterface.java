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
import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import de.markusbordihn.easymobfarm.data.capture.MobColor;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ModelManagerInterface {

  Logger log = LogManager.getLogger(Constants.LOG_NAME);
  Set<String> KNOWN_MISSING_MODELS = new HashSet<>();
  String LOG_PREFIX = "[Model Manager]";

  ModelResourceLocation DEFAULT_MODEL =
      new ModelResourceLocation(
          ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "item/mob_capture_card/default"),
          "standalone");
  ModelResourceLocation DEFAULT_UNCOMMON_MODEL =
      new ModelResourceLocation(
          ResourceLocation.fromNamespaceAndPath(
              Constants.MOD_ID, "item/mob_capture_card/default_uncommon"),
          "standalone");
  ModelResourceLocation DEFAULT_RARE_MODEL =
      new ModelResourceLocation(
          ResourceLocation.fromNamespaceAndPath(
              Constants.MOD_ID, "item/mob_capture_card/default_rare"),
          "standalone");
  ModelResourceLocation DEFAULT_EPIC_MODEL =
      new ModelResourceLocation(
          ResourceLocation.fromNamespaceAndPath(
              Constants.MOD_ID, "item/mob_capture_card/default_epic"),
          "standalone");
  ModelResourceLocation DEFAULT_FISH_MODEL =
      new ModelResourceLocation(
          ResourceLocation.fromNamespaceAndPath(
              Constants.MOD_ID, "item/mob_capture_card/default_fish"),
          "standalone");

  default boolean isFish(EntityType<?> entityType) {
    return BuiltInRegistries.ITEM
        .get(EntityType.getKey(entityType))
        .map(Reference::value)
        .filter(item -> item != Items.AIR)
        .map(item -> item.builtInRegistryHolder().is(ItemTags.FISHES))
        .orElse(false);
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

  default BakedModel getModel(String type, String variant, MobColor color) {
    ModelResourceLocation modelResourceLocation =
        MobCaptureCardModelsConfig.getModelResourceLocation(type, variant, color);
    if (modelResourceLocation == null) {
      if (KNOWN_MISSING_MODELS.add(type)) {
        log.warn(
            "{} Missing custom model for type '{}', variant '{}' with color '{}'",
            LOG_PREFIX,
            type,
            variant,
            color);
      }
      return null;
    }
    return this.getModel(modelResourceLocation);
  }

  default BakedModel getModel(String type) {
    ModelResourceLocation modelResourceLocation =
        MobCaptureCardModelsConfig.getModelResourceLocation(type);
    if (modelResourceLocation == null) {
      if (KNOWN_MISSING_MODELS.add(type)) {
        log.warn("{} Missing custom model for type '{}'", LOG_PREFIX, type);
      }
      return null;
    }
    return this.getModel(modelResourceLocation);
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
