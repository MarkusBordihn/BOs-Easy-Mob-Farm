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

package de.markusbordihn.easymobfarm.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.markusbordihn.easymobfarm.Constants;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobCaptureCardResourceManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final String LOG_PREFIX = "[Mob Capture Card Resource Manager] ";

  private static final Map<Integer, String> MOB_CAPTURE_CARD_MODELS = new HashMap<>();

  private MobCaptureCardResourceManager() {}

  public static void loadMobCaptureCardData(ResourceManager resourceManager) {
    ResourceLocation mobCaptureCardLocation =
        ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID, "models/item/mob_capture_card/mob_capture_cards.json");
    MOB_CAPTURE_CARD_MODELS.clear();

    resourceManager
        .listPacks()
        .forEach(
            packResources -> {
              if (packResources.getResource(PackType.CLIENT_RESOURCES, mobCaptureCardLocation)
                  != null) {
                processMobCaptureCardData(packResources, mobCaptureCardLocation);
              } else {
                log.debug(
                    "{} Skipping pack {} because it does not contain {}",
                    LOG_PREFIX,
                    packResources.packId(),
                    mobCaptureCardLocation);
              }
            });
  }

  public static void processMobCaptureCardData(
      PackResources packResources, ResourceLocation mobCaptureCardLocation) {
    try {
      InputStream inputStream =
          packResources.getResource(PackType.CLIENT_RESOURCES, mobCaptureCardLocation).get();
      InputStreamReader reader = new InputStreamReader(inputStream);
      JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
      JsonArray captureCards = jsonObject.getAsJsonArray("mob_capture_cards");

      captureCards.forEach(
          element -> {
            JsonObject card = element.getAsJsonObject();
            int cardId = card.get("card_id").getAsInt();
            String modelPath = card.get("model").getAsString();
            if (cardId <= 0 || modelPath == null || modelPath.isEmpty()) {
              log.error(
                  "{} Failed to load custom capture card with id {} and model {}",
                  LOG_PREFIX,
                  cardId,
                  modelPath);
              return;
            }
            log.debug(
                "{} Added custom mob capture card with id {} and model {}",
                LOG_PREFIX,
                cardId,
                modelPath);
            MOB_CAPTURE_CARD_MODELS.put(cardId, modelPath);
          });
      reader.close();
    } catch (Exception e) {
      log.error(
          "{} Failed to load custom capture cards for {}", LOG_PREFIX, packResources.packId(), e);
    }
  }

  public static boolean isSupportedCardId(int cardID) {
    return MOB_CAPTURE_CARD_MODELS.containsKey(cardID);
  }

  public static String getModelForCustomModelData(int cardID) {
    return MOB_CAPTURE_CARD_MODELS.getOrDefault(
        cardID, "easy_mob_farm:item/mob_capture_card/default");
  }

  public static ResourceLocation getResourceLocationForCardId(int cardID) {
    return ResourceLocation.parse(getModelForCustomModelData(cardID));
  }

  public static ModelResourceLocation getModelResourceLocationForCardId(int cardID) {
    String modelPath = getModelForCustomModelData(cardID);
    if (modelPath.contains(":item/")) {
      modelPath = modelPath.replace(":item/", ":");
    }
    return new ModelResourceLocation(ResourceLocation.parse(modelPath), "inventory");
  }

  public static Collection<String> getCustomModelPaths() {
    return MOB_CAPTURE_CARD_MODELS.values();
  }
}
