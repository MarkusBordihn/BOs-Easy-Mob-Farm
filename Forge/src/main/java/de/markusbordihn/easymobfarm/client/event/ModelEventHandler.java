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
import de.markusbordihn.easymobfarm.client.model.MobCaptureCardModel;
import de.markusbordihn.easymobfarm.client.model.UnbakedMobCaptureCardModel;
import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelEventHandler {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private ModelEventHandler() {}

  @SuppressWarnings("unused")
  @SubscribeEvent
  public static void onModelRegistry(ModelRegistryEvent event) {
    log.info("Registering custom models ...");

    // Pre-Loading default models for Mob Capture Card.
    ForgeModelBakery.addSpecialModel(UnbakedMobCaptureCardModel.MODEL);
    ForgeModelBakery.addSpecialModel(MobCaptureCardModel.DEFAULT_MODEL);
    ForgeModelBakery.addSpecialModel(MobCaptureCardModel.DEFAULT_UNCOMMON_MODEL);
    ForgeModelBakery.addSpecialModel(MobCaptureCardModel.DEFAULT_RARE_MODEL);
    ForgeModelBakery.addSpecialModel(MobCaptureCardModel.DEFAULT_EPIC_MODEL);
    ForgeModelBakery.addSpecialModel(MobCaptureCardModel.DEFAULT_FISH_MODEL);

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
              log.info("Registering custom model {} for {} ...", modelResourceLocation, entityName);
              ForgeModelBakery.addSpecialModel(modelResourceLocation);
            });
  }

  @SuppressWarnings("unused")
  @SubscribeEvent
  public static void onModelBake(ModelBakeEvent event) {

    // Checking for existing models.
    log.info("Adjusting {} models ...", Constants.MOD_ID);
    ResourceLocation mobCaptureCardItemLocation = null;
    for (ResourceLocation location : event.getModelRegistry().keySet()) {
      if (location.getNamespace().equals(Constants.MOD_ID)) {
        log.info("Found baked model: {} ({})", location, location.getPath());
        if (location.getPath().equals("mob_capture_card")) {
          mobCaptureCardItemLocation = location;
        }
      }
    }

    // Getting unbaked model for Mob Capture Card.
    UnbakedModel unbakedModel =
        new UnbakedMobCaptureCardModel(
            event
                .getModelLoader()
                .getModelOrLogError(
                    UnbakedMobCaptureCardModel.MODEL,
                    "Unable to load model " + UnbakedMobCaptureCardModel.MODEL + " :"));

    // Bake unbaked model for Mob Capture Card.
    log.info("Baking unbaked model for {} ...", mobCaptureCardItemLocation);
    BakedModel bakedModel =
        unbakedModel.bake(
            ForgeModelBakery.instance(),
            ForgeModelBakery.defaultTextureGetter(),
            BlockModelRotation.X0_Y0,
            mobCaptureCardItemLocation);
    event.getModelRegistry().put(mobCaptureCardItemLocation, bakedModel);
  }
}
