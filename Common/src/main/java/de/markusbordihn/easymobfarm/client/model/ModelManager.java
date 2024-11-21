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
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static ModelManagerInterface modelManager;

  private ModelManager() {}

  public static void registerModelManager(ModelManagerInterface modelManagerInterface) {
    log.info(
        "{} Model Manager {}",
        Constants.LOG_REGISTER_PREFIX,
        modelManagerInterface.getClass().getName());
    ModelManager.modelManager = modelManagerInterface;
  }

  public static ModelManagerInterface getModelManager() {
    return modelManager;
  }

  public static BakedModel getModel(MobCaptureData mobCaptureData, BakedModel originalModel) {
    if (mobCaptureData == null
        || mobCaptureData.type() == null
        || mobCaptureData.type().isEmpty()
        || modelManager == null) {
      return originalModel;
    }

    // Check for specific model based on type, variant and color.
    BakedModel bakedModel = null;
    if (mobCaptureData.hasColor() || mobCaptureData.hasVariant()) {
      bakedModel =
          modelManager.getModel(
              mobCaptureData.type(), mobCaptureData.variant(), mobCaptureData.color());
    } else {
      bakedModel = modelManager.getModel(mobCaptureData.type());
    }

    // Return specific model if available.
    if (bakedModel != null) {
      return bakedModel;
    }

    // Use rarity based model if available.
    if (mobCaptureData.hasRarity()) {
      bakedModel = modelManager.getModel(mobCaptureData.rarity());
      if (bakedModel != null) {
        return bakedModel;
      }
    }

    // Check for specify tags and use the model if available.
    bakedModel = modelManager.getModel(mobCaptureData.entityType());
    if (bakedModel != null) {
      return bakedModel;
    }

    // Check if we have a valid original model to return.
    if (originalModel != Minecraft.getInstance().getModelManager().getMissingModel()
        && originalModel.getParticleIcon()
            != Minecraft.getInstance().getModelManager().getMissingModel().getParticleIcon()) {
      return originalModel;
    }

    // Return default model if nothing else is available.
    return modelManager.getDefaultModel();
  }

  public static ModelResourceLocation getRegistrationModelResourceLocation(
      final ModelResourceLocation modelResourceLocation) {
    return new ModelResourceLocation(
        ResourceLocation.fromNamespaceAndPath(
            modelResourceLocation.id().getNamespace(),
            modelResourceLocation.id().getPath().replace("item/", "")),
        modelResourceLocation.getVariant());
  }
}
