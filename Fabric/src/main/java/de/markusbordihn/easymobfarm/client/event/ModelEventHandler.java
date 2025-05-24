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
import de.markusbordihn.easymobfarm.client.model.ModelManagerInterface;
import de.markusbordihn.easymobfarm.client.model.UnbakedMobCaptureCardModel;
import java.util.Set;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModelEventHandler {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private ModelEventHandler() {}

  public static void registerModelEventHandler() {

    ModelLoadingRegistry.INSTANCE.registerModelProvider(
        (resourceManager, consumer) -> {
          log.info("Registering card models ...");

          // Pre-Loading default models for Mob Capture Card.
          Set.of(
                  UnbakedMobCaptureCardModel.MODEL,
                  ModelManagerInterface.DEFAULT_MODEL,
                  ModelManagerInterface.DEFAULT_UNCOMMON_MODEL,
                  ModelManagerInterface.DEFAULT_RARE_MODEL,
                  ModelManagerInterface.DEFAULT_EPIC_MODEL,
                  ModelManagerInterface.DEFAULT_FISH_MODEL)
              .forEach(
                  resourceLocation -> {
                    if (resourceLocation instanceof ModelResourceLocation modelResourceLocation) {
                      resourceLocation =
                          ModelManager.getRegistrationModelResourceLocation(modelResourceLocation);
                    }
                    log.info("Registering default model {} ...", resourceLocation);
                    consumer.accept(resourceLocation);
                  });

          // Pre-Loading additional models for Mob Capture Card from the resource folder.
          for (ResourceLocation location :
              resourceManager
                  .listResources(
                      "models/item/easy_mob_farm/mob_capture_card",
                      resourceLocation -> resourceLocation.getPath().endsWith(".json"))
                  .keySet()) {
            ModelResourceLocation modelResourceLocation = getModelResourceLocation(location);
            log.info(
                "Automatically registering model {} as {} ...", location, modelResourceLocation);
            consumer.accept(modelResourceLocation);
          }
        });

    ModelLoadingRegistry.INSTANCE.registerResourceProvider(
        resourceManager ->
            (location, context) -> {
              if (location.getNamespace().equals(Constants.MOD_ID)) {
                log.debug("Found unbaked model: {} ({})", location, location.getPath());
                if (location.getPath().equals("item/mob_capture_card")) {
                  log.info("Adjusting baked model for {} ...", location);
                  UnbakedModel unbakedModel = context.loadModel(UnbakedMobCaptureCardModel.MODEL);
                  if (unbakedModel == null) {
                    log.error(
                        "Unable to load unbaked model for resource location {} from {}",
                        location,
                        UnbakedMobCaptureCardModel.MODEL);
                    return null;
                  }
                  log.info("Baking unbaked model for {} ...", location);
                  return new UnbakedMobCaptureCardModel(unbakedModel);
                }
              }
              return null;
            });
  }

  public static ModelResourceLocation getModelResourceLocation(ResourceLocation fileLocation) {
    String modelPath =
        fileLocation.getPath().replaceFirst("^models/item/", "").replaceAll("\\.json$", "");
    return new ModelResourceLocation(
        new ResourceLocation(fileLocation.getNamespace(), modelPath), "inventory");
  }
}
