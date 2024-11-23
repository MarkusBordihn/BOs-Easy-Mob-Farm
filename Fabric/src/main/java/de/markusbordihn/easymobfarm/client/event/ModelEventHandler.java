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
import de.markusbordihn.easymobfarm.client.model.ModelManagerInterface;
import de.markusbordihn.easymobfarm.client.model.UnbakedMobCaptureCardModel;
import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import java.util.Set;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
                  ModelManagerInterface.DEFAULT_MODEL,
                  ModelManagerInterface.DEFAULT_UNCOMMON_MODEL,
                  ModelManagerInterface.DEFAULT_RARE_MODEL,
                  ModelManagerInterface.DEFAULT_EPIC_MODEL,
                  ModelManagerInterface.DEFAULT_FISH_MODEL)
              .forEach(
                  modelResourceLocation -> {
                    log.info("Registering default model {} ...", modelResourceLocation.id());
                    pluginContext.addModels(modelResourceLocation.id());
                  });

          // Pre-Loading additional models for Mob Capture Card from config file.
          MobCaptureCardModelsConfig.getMobCaptureCardModels()
              .forEach(
                  entityName -> {
                    ModelResourceLocation modelResourceLocation =
                        MobCaptureCardModelsConfig.getModelResourceLocation(entityName);
                    if (modelResourceLocation == null) {
                      log.error(
                          "Skipping model for entity {} because of invalid resource locations.",
                          entityName);
                      return;
                    }
                    log.info(
                        "Registering custom model {} for {} ...",
                        modelResourceLocation.id(),
                        entityName);
                    pluginContext.addModels(modelResourceLocation.id());
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
                        return new UnbakedMobCaptureCardModel(originalModel);
                      }
                    }
                    return originalModel;
                  });
        });
  }
}
