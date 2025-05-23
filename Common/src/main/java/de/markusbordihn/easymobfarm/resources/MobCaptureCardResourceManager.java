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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Rarity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobCaptureCardResourceManager extends SimpleJsonResourceReloadListener {

  public static final ResourceLocation RESOURCE_ID =
      new ResourceLocation(Constants.MOD_ID, "mob_capture_card_resource_loader");
  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  private static final String LOG_PREFIX = "[Mob Capture Card Resource Manager]";
  private static final String SEARCH_PATH = "easy_mob_farm/mob_capture_card";
  private static final String MODEL_TAG = "model";
  private static final String RARITY_TAG = "rarity";
  private static final String REQUIRES_ANIMATION_TICK_TAG = "requires_animation_tick";
  private static final String REQUIRES_KILLED_BY_PLAYER_TAG = "requires_killed_by_player";
  private static final String SCALE_TAG = "scale";
  private static final String COLORS_TAG = "colors";
  private static final String VARIANTS_TAG = "variants";

  public MobCaptureCardResourceManager() {
    super(GSON, SEARCH_PATH);
  }

  @Override
  protected void apply(
      Map<ResourceLocation, JsonElement> objectMap,
      ResourceManager resourceManager,
      ProfilerFiller profiler) {

    log.info("{} Loading definitions ... from {}", LOG_PREFIX, SEARCH_PATH);
    MobCaptureCardDefinitionManager.clear();

    objectMap.forEach(
        (location, element) -> {
          try {
            JsonObject json = GsonHelper.convertToJsonObject(element, "mob_capture_card");
            log.debug("{} Parsing definition: {}", LOG_PREFIX, location);

            ResourceLocation entity = new ResourceLocation(GsonHelper.getAsString(json, "entity"));
            ResourceLocation model =
                json.has(MODEL_TAG)
                    ? new ResourceLocation(GsonHelper.getAsString(json, MODEL_TAG))
                    : null;
            Rarity rarity =
                Rarity.valueOf(
                    GsonHelper.getAsString(json, RARITY_TAG, "common").toUpperCase(Locale.ROOT));
            float scale = GsonHelper.getAsFloat(json, SCALE_TAG, 1.0f);
            boolean requiresKilledByPlayer =
                GsonHelper.getAsBoolean(json, REQUIRES_KILLED_BY_PLAYER_TAG, false);
            boolean requiresAnimationTick =
                GsonHelper.getAsBoolean(json, REQUIRES_ANIMATION_TICK_TAG, false);

            Map<String, MobCaptureCardDefinition.Color> colors = parseColors(json);
            Map<String, MobCaptureCardDefinition.Variant> variants = parseVariants(json);

            MobCaptureCardDefinition definition =
                new MobCaptureCardDefinition(
                    entity,
                    model,
                    rarity,
                    scale,
                    requiresKilledByPlayer,
                    requiresAnimationTick,
                    variants,
                    colors);

            MobCaptureCardDefinitionManager.addDefinition(entity, definition);
          } catch (Exception e) {
            log.error(
                "{} Failed to parse mob_capture_card definition: {} → {}",
                LOG_PREFIX,
                location,
                e.getMessage());
          }
        });
  }

  private Map<String, MobCaptureCardDefinition.Color> parseColors(JsonObject json) {
    Map<String, MobCaptureCardDefinition.Color> colors = new HashMap<>();
    if (!json.has(COLORS_TAG)) {
      return colors;
    }

    JsonObject colorsJson = GsonHelper.getAsJsonObject(json, COLORS_TAG);
    colorsJson
        .entrySet()
        .forEach(
            entry -> {
              String color = entry.getKey();
              ResourceLocation colorModel =
                  json.has(MODEL_TAG)
                      ? new ResourceLocation(
                          GsonHelper.getAsString(entry.getValue().getAsJsonObject(), MODEL_TAG))
                      : null;
              colors.put(color, new MobCaptureCardDefinition.Color(colorModel));
            });

    return colors;
  }

  private Map<String, MobCaptureCardDefinition.Variant> parseVariants(JsonObject json) {
    Map<String, MobCaptureCardDefinition.Variant> variants = new HashMap<>();
    if (!json.has(VARIANTS_TAG)) {
      return variants;
    }

    JsonObject variantsJson = GsonHelper.getAsJsonObject(json, VARIANTS_TAG);
    variantsJson
        .entrySet()
        .forEach(
            entry -> {
              String variant = entry.getKey();
              JsonObject variantObj = entry.getValue().getAsJsonObject();
              ResourceLocation variantModel =
                  json.has(MODEL_TAG)
                      ? new ResourceLocation(GsonHelper.getAsString(variantObj, MODEL_TAG, null))
                      : null;

              Map<String, MobCaptureCardDefinition.Color> variantColors = new HashMap<>();
              if (variantObj.has(COLORS_TAG)) {
                JsonObject variantColorsJson = GsonHelper.getAsJsonObject(variantObj, COLORS_TAG);
                variantColorsJson
                    .entrySet()
                    .forEach(
                        colorEntry -> {
                          String color = colorEntry.getKey();
                          ResourceLocation colorModel =
                              json.has(MODEL_TAG)
                                  ? new ResourceLocation(
                                      GsonHelper.getAsString(
                                          colorEntry.getValue().getAsJsonObject(), MODEL_TAG))
                                  : null;
                          variantColors.put(color, new MobCaptureCardDefinition.Color(colorModel));
                        });
              }

              variants.put(
                  variant, new MobCaptureCardDefinition.Variant(variantModel, variantColors));
            });

    return variants;
  }
}
