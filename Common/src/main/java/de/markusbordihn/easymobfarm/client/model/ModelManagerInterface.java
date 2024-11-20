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
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Registry;
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
  String LOG_PREFIX = "[ModelManager]";

  ResourceLocation DEFAULT_MODEL_LOCATION =
      new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default");
  ResourceLocation DEFAULT_UNCOMMON_MODEL_LOCATION =
      new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_uncommon");
  ResourceLocation DEFAULT_RARE_MODEL_LOCATION =
      new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_rare");
  ResourceLocation DEFAULT_EPIC_MODEL_LOCATION =
      new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_epic");
  ResourceLocation DEFAULT_FISH_MODEL_LOCATION =
      new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_fish");

  ModelResourceLocation DEFAULT_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default"), "inventory");
  ModelResourceLocation DEFAULT_UNCOMMON_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_uncommon"),
          "inventory");
  ModelResourceLocation DEFAULT_RARE_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_rare"),
          "inventory");
  ModelResourceLocation DEFAULT_EPIC_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_epic"),
          "inventory");
  ModelResourceLocation DEFAULT_FISH_MODEL =
      new ModelResourceLocation(
          new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/default_fish"),
          "inventory");

  private static boolean isFish(EntityType<?> entityType) {
    ResourceLocation entityId = EntityType.getKey(entityType);
    Item correspondingItem = Registry.ITEM.get(entityId);
    return correspondingItem != Items.AIR
        && correspondingItem.builtInRegistryHolder().is(ItemTags.FISHES);
  }

  default ResourceLocation getDefaultModelLocation() {
    return DEFAULT_MODEL_LOCATION;
  }

  default ResourceLocation getDefaultUncommonModelLocation() {
    return DEFAULT_UNCOMMON_MODEL_LOCATION;
  }

  default ResourceLocation getDefaultRareModelLocation() {
    return DEFAULT_RARE_MODEL_LOCATION;
  }

  default ResourceLocation getDefaultEpicModelLocation() {
    return DEFAULT_EPIC_MODEL_LOCATION;
  }

  default ResourceLocation getDefaultFishModelLocation() {
    return DEFAULT_FISH_MODEL_LOCATION;
  }

  default ModelResourceLocation getDefaultModelResourceLocation() {
    return DEFAULT_MODEL;
  }

  default ModelResourceLocation getDefaultUncommonModelResourceLocation() {
    return DEFAULT_UNCOMMON_MODEL;
  }

  default ModelResourceLocation getDefaultRareModelResourceLocation() {
    return DEFAULT_RARE_MODEL;
  }

  default ModelResourceLocation getDefaultEpicModelResourceLocation() {
    return DEFAULT_EPIC_MODEL;
  }

  default ModelResourceLocation getDefaultFishModelResourceLocation() {
    return DEFAULT_FISH_MODEL;
  }

  default BakedModel getModel(String type, String variant, DyeColor color) {
    BakedModel bakedModel =
        Minecraft.getInstance()
            .getModelManager()
            .getModel(MobCaptureCardModelsConfig.getModelResourceLocation(type, variant, color));
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }

  default BakedModel getModel(String type) {
    BakedModel bakedModel =
        Minecraft.getInstance()
            .getModelManager()
            .getModel(MobCaptureCardModelsConfig.getModelResourceLocation(type));
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }

  default BakedModel getModel(Rarity rarity) {
    BakedModel bakedModel =
        switch (rarity) {
          case COMMON -> null;
          case UNCOMMON ->
              Minecraft.getInstance()
                  .getModelManager()
                  .getModel(getDefaultUncommonModelResourceLocation());
          case RARE ->
              Minecraft.getInstance()
                  .getModelManager()
                  .getModel(getDefaultRareModelResourceLocation());
          case EPIC ->
              Minecraft.getInstance()
                  .getModelManager()
                  .getModel(getDefaultEpicModelResourceLocation());
        };
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }

  default BakedModel getModel(EntityType<?> entityType) {
    return isFish(entityType)
        ? Minecraft.getInstance().getModelManager().getModel(getDefaultFishModelResourceLocation())
        : null;
  }

  default BakedModel getDefaultModel() {
    BakedModel bakedModel =
        Minecraft.getInstance().getModelManager().getModel(getDefaultModelResourceLocation());
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }
}
