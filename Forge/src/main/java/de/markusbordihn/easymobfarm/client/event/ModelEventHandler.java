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
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelEventHandler {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static BakedModel mobCaptureCardItemBakedModel;

  private ModelEventHandler() {}

  @SuppressWarnings("unused")
  @SubscribeEvent
  public static void onModelRegistry(ModelEvent.RegisterAdditional event) {
    log.debug("Registering card models ...");

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
              log.debug("Registering default model {} ...", resourceLocation);
              event.register(resourceLocation);
            });

    // Pre-Loading additional models for Mob Capture Card from the resource folder.
    ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
    int additionalModels = 0;
    for (ResourceLocation location :
        resourceManager
            .listResources(
                "models/item/easy_mob_farm/mob_capture_card",
                resourceLocation -> resourceLocation.getPath().endsWith(".json"))
            .keySet()) {
      ModelResourceLocation modelResourceLocation =
          ModelManagerInterface.getModelResourceLocation(location);
      log.debug("Automatically registering model {} as {} ...", location, modelResourceLocation);
      event.register(modelResourceLocation);
      additionalModels++;
    }
    log.debug("Registered {} additional card models from resource folder.", additionalModels);
  }

  @SuppressWarnings("unused")
  @SubscribeEvent
  public static void onModelBake(ModelEvent.BakingCompleted event) {

    // Verify if Mob Capture Card model is available.
    ResourceLocation mobCaptureCardModelResourceLocation =
        new ModelResourceLocation(
            new ResourceLocation(Constants.MOD_ID, "mob_capture_card"), "inventory");

    // Getting unbaked model for Mob Capture Card.
    UnbakedModel unbakedModel =
        new UnbakedMobCaptureCardModel(
            event.getModelBakery().getModel(UnbakedMobCaptureCardModel.MODEL));

    // Bake unbaked model for Mob Capture Card.
    log.debug("Baking unbaked model for {} ...", mobCaptureCardModelResourceLocation);
    ModelBaker baker =
        event.getModelBakery()
        .new ModelBakerImpl(
            (modelLocation, material) -> material.sprite(), mobCaptureCardModelResourceLocation);
    BakedModel bakedModel =
        unbakedModel.bake(
            baker, Material::sprite, BlockModelRotation.X0_Y0, mobCaptureCardModelResourceLocation);
    event
        .getModelBakery()
        .getBakedTopLevelModels()
        .put(mobCaptureCardModelResourceLocation, bakedModel);
  }

  public static ModelResourceLocation getModelResourceLocation(ResourceLocation fileLocation) {
    String modelPath =
        fileLocation.getPath().replaceFirst("^models/item/", "").replaceAll("\\.json$", "");
    return new ModelResourceLocation(
        new ResourceLocation(fileLocation.getNamespace(), modelPath), "inventory");
  }
}
