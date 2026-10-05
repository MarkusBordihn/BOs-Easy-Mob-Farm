/*
 * Copyright 2026 Markus Bordihn
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

package de.markusbordihn.easymobfarm.item.mobcapturecard;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobCaptureCardItemModelTest {

  private static final Path RESOURCES = Path.of("src/main/resources");
  private static final Path ITEM_MODEL =
      RESOURCES.resolve("assets/easy_mob_farm/items/mob_capture_card.json");

  private static JsonObject readJson(Path path) {
    try {
      return JsonParser.parseString(Files.readString(path)).getAsJsonObject();
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read " + path, e);
    }
  }

  private static List<JsonObject> readCardDefinitions() throws IOException {
    try (Stream<Path> paths = Files.walk(RESOURCES.resolve("data"))) {
      return paths
          .filter(path -> path.toString().endsWith(".json"))
          .filter(path -> path.getParent().getFileName().toString().equals("mob_capture_card"))
          .map(MobCaptureCardItemModelTest::readJson)
          .filter(definition -> definition.has("entity") && definition.has("model"))
          .toList();
    }
  }

  private static Map<String, String> selectionModels(JsonObject definition, String selection) {
    Map<String, String> models = new HashMap<>();
    if (!definition.has(selection)) {
      return models;
    }

    String baseModel = definition.get("model").getAsString();
    definition
        .getAsJsonObject(selection)
        .entrySet()
        .forEach(
            selectionEntry -> {
              JsonObject selectionDefinition = selectionEntry.getValue().getAsJsonObject();
              models.put(
                  selectionEntry.getKey(),
                  selectionDefinition.has("model")
                      ? selectionDefinition.get("model").getAsString()
                      : baseModel);
            });
    return models;
  }

  private static Map<String, JsonObject> casesByWhen(JsonObject selectModel) {
    Map<String, JsonObject> cases = new HashMap<>();
    for (JsonElement selectCase : selectModel.getAsJsonArray("cases")) {
      JsonObject caseObject = selectCase.getAsJsonObject();
      cases.put(caseObject.get("when").getAsString(), caseObject.getAsJsonObject("model"));
    }
    return cases;
  }

  private static String modelOf(JsonObject model) {
    return model.has("model") ? model.get("model").getAsString() : null;
  }

  @Test
  @DisplayName("Item model has a case for every card definition, variant and color")
  void itemModelMatchesCardDefinitions() throws IOException {
    Map<String, JsonObject> entityCases = casesByWhen(readJson(ITEM_MODEL).getAsJsonObject("model"));
    List<String> mismatches = new ArrayList<>();

    for (JsonObject definition : readCardDefinitions()) {
      String entity = definition.get("entity").getAsString();
      String baseModel = definition.get("model").getAsString();
      JsonObject entityModel = entityCases.get(entity);
      if (entityModel == null) {
        mismatches.add(entity + ": missing");
        continue;
      }

      Map<String, String> expectedModels = selectionModels(definition, "variants");
      if (expectedModels.isEmpty()) {
        expectedModels = selectionModels(definition, "colors");
      }
      if (expectedModels.isEmpty()) {
        if (!baseModel.equals(modelOf(entityModel))) {
          mismatches.add(entity + ": " + baseModel);
        }
        continue;
      }
      if (!entityModel.has("cases")) {
        mismatches.add(entity + ": has no variant or color select");
        continue;
      }

      Map<String, JsonObject> selectionCases = casesByWhen(entityModel);
      expectedModels.forEach(
          (selection, expectedModel) -> {
            JsonObject selectionCase = selectionCases.get(selection);
            if (selectionCase == null || !expectedModel.equals(modelOf(selectionCase))) {
              mismatches.add(entity + " " + selection + ": " + expectedModel);
            }
          });
      if (!baseModel.equals(modelOf(entityModel.getAsJsonObject("fallback")))) {
        mismatches.add(entity + " fallback: " + baseModel);
      }
    }

    Assertions.assertTrue(
        mismatches.isEmpty(),
        "Run generateMobCaptureCardModel, item model is out of sync: " + mismatches);
  }

  @Test
  @DisplayName("Every model referenced by a card definition exists")
  void everyReferencedModelExists() throws IOException {
    List<String> missingModels = new ArrayList<>();
    for (JsonObject definition : readCardDefinitions()) {
      List<String> models = new ArrayList<>();
      models.add(definition.get("model").getAsString());
      models.addAll(selectionModels(definition, "variants").values());
      models.addAll(selectionModels(definition, "colors").values());
      for (String model : models) {
        String[] namespaceAndPath = model.split(":", 2);
        Path modelFile =
            RESOURCES.resolve(
                "assets/" + namespaceAndPath[0] + "/models/" + namespaceAndPath[1] + ".json");
        if (!Files.exists(modelFile)) {
          missingModels.add(model);
        }
      }
    }

    Assertions.assertTrue(missingModels.isEmpty(), "Missing card models: " + missingModels);
  }
}
