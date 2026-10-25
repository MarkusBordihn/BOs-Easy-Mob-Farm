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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.markusbordihn.easymobfarm.Constants;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemDefinitionResourcesTest {

  private static final String MODEL_KEY = "model";
  private static final String VANILLA_NAMESPACE = "minecraft";
  private static final String VANILLA_NAMESPACE_MOD_MODELS = "item/" + Constants.MOD_ID + "/";

  private static Path assetsRoot;

  @BeforeAll
  static void locateResources() throws URISyntaxException {
    assetsRoot =
      Path.of(ItemDefinitionResourcesTest.class.getResource("/assets/" + Constants.MOD_ID).toURI())
        .getParent();
  }

  private static List<Path> jsonFiles(Path folder) throws IOException {
    try (Stream<Path> files = Files.list(folder)) {
      return files.filter(file -> file.toString().endsWith(".json")).sorted().toList();
    }
  }

  private static void collectModelReferences(JsonElement element, List<String> modelReferences) {
    if (element.isJsonArray()) {
      element
        .getAsJsonArray()
        .forEach(arrayElement -> collectModelReferences(arrayElement, modelReferences));
    } else if (element.isJsonObject()) {
      for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
        JsonElement value = entry.getValue();
        if (MODEL_KEY.equals(entry.getKey())
          && value.isJsonPrimitive()
          && value.getAsJsonPrimitive().isString()) {
          modelReferences.add(value.getAsString());
        } else {
          collectModelReferences(value, modelReferences);
        }
      }
    }
  }

  private static boolean isShippedByThisMod(String namespace, String path) {
    return Constants.MOD_ID.equals(namespace)
      || (VANILLA_NAMESPACE.equals(namespace) && path.startsWith(VANILLA_NAMESPACE_MOD_MODELS));
  }

  @Test
  @DisplayName("Every model referenced by an item definition exists")
  void everyReferencedModelExists() throws IOException {
    TreeSet<String> missingModels = new TreeSet<>();
    for (Path itemDefinition : jsonFiles(assetsRoot.resolve(Constants.MOD_ID + "/items"))) {
      JsonObject json =
        JsonParser.parseString(Files.readString(itemDefinition, StandardCharsets.UTF_8))
          .getAsJsonObject();
      List<String> modelReferences = new ArrayList<>();
      collectModelReferences(json, modelReferences);
      for (String modelReference : modelReferences) {
        String[] namespaceAndPath =
          modelReference.contains(":")
            ? modelReference.split(":", 2)
            : new String[]{VANILLA_NAMESPACE, modelReference};
        if (isShippedByThisMod(namespaceAndPath[0], namespaceAndPath[1])
          && !Files.exists(
          assetsRoot.resolve(
            namespaceAndPath[0] + "/models/" + namespaceAndPath[1] + ".json"))) {
          missingModels.add(itemDefinition.getFileName() + " -> " + modelReference);
        }
      }
    }

    Assertions.assertTrue(missingModels.isEmpty(), "Missing item models: " + missingModels);
  }

  @Test
  @DisplayName("Every top-level item model has an item definition")
  void everyItemModelHasItemDefinition() throws IOException {
    Path itemDefinitions = assetsRoot.resolve(Constants.MOD_ID + "/items");
    TreeSet<String> missingItemDefinitions = new TreeSet<>();
    for (Path itemModel : jsonFiles(assetsRoot.resolve(Constants.MOD_ID + "/models/item"))) {
      if (!Files.exists(itemDefinitions.resolve(itemModel.getFileName()))) {
        missingItemDefinitions.add(itemModel.getFileName().toString());
      }
    }

    Assertions.assertTrue(
      missingItemDefinitions.isEmpty(), "Missing item definitions: " + missingItemDefinitions);
  }
}
