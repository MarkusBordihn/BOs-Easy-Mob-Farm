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

package de.markusbordihn.easymobfarm.client.event;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.model.ModelManager;
import de.markusbordihn.easymobfarm.client.model.UnbakedMobCaptureCardModel;
import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import java.util.Set;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelEventHandler {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private ModelEventHandler() {}

  public static void registerModelEventHandler() {

    ModelLoadingPlugin.register(
        pluginContext -> {
          log.info("Registering card models ...");

          // Pre-Loading default models for Mob Capture Card.
          Set.of(
                  ModelManager.getModelManager().getDefaultModelLocation(),
                  ModelManager.getModelManager().getDefaultUncommonModelLocation(),
                  ModelManager.getModelManager().getDefaultRareModelLocation(),
                  ModelManager.getModelManager().getDefaultEpicModelLocation(),
                  ModelManager.getModelManager().getDefaultFishModelLocation())
              .forEach(
                  resourceLocation -> {
                    log.info("Registering default model {} ...", resourceLocation);
                    pluginContext.addModels(resourceLocation);
                  });

          // Pre-Loading additional models for Mob Capture Card from config file.
          MobCaptureCardModelsConfig.getMobCaptureCardModels()
              .forEach(
                  entityName -> {
                    ResourceLocation resourceLocation =
                        MobCaptureCardModelsConfig.getResourceLocation(entityName);
                    if (resourceLocation == null) {
                      log.error(
                          "Skipping model for entity {} because of invalid resource locations.",
                          entityName);
                      return;
                    }
                    log.info(
                        "Registering custom model {} for {} ...", resourceLocation, entityName);
                    pluginContext.addModels(resourceLocation);
                  });

          // Register custom model for Mob Capture Card.
          pluginContext
              .modifyModelOnLoad()
              .register(
                  (originalModel, onLoadContext) -> {
                    ModelResourceLocation modelResourceLocation = onLoadContext.topLevelId();
                    if (modelResourceLocation == null) {
                      return originalModel;
                    }
                    ResourceLocation resourceLocation = modelResourceLocation.id();
                    if (resourceLocation.getNamespace().equals(Constants.MOD_ID)) {
                      log.debug(
                          "Found unbaked model: {} ({})",
                          modelResourceLocation,
                          resourceLocation.getPath());
                      if (resourceLocation.getPath().equals("mob_capture_card")) {
                        log.info("Adjusting baked model for {} ...", modelResourceLocation);
                        UnbakedModel unbakedModel =
                            onLoadContext.getOrLoadModel(UnbakedMobCaptureCardModel.MODEL);
                        if (unbakedModel == null) {
                          log.error(
                              "Unable to load unbaked model for resource location {} from {}",
                              modelResourceLocation,
                              UnbakedMobCaptureCardModel.MODEL);
                          return originalModel;
                        }
                        log.info("Baking unbaked model for {} ...", modelResourceLocation);
                        return new UnbakedMobCaptureCardModel(unbakedModel);
                      }
                    }
                    return originalModel;
                  });
        });
  }
}
