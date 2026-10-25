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
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobCaptureCardResourcesTest {

  private static final String MOB_CAPTURE_CARD_FOLDER = "easy_mob_farm/mob_capture_card";
  private static final String MODEL_KEY = "model";
  private static final String TEXTURES_KEY = "textures";

  private static Path resourcesRoot;

  @BeforeAll
  static void locateResources() throws URISyntaxException {
    resourcesRoot =
        Path.of(MobCaptureCardResourcesTest.class.getResource("/data/" + Constants.MOD_ID).toURI())
            .getParent()
            .getParent();
  }

  private static JsonObject readJson(Path file) throws IOException {
    return JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
  }

  private static List<Path> listJsonFiles(Path folder) throws IOException {
    try (Stream<Path> files = Files.walk(folder)) {
      return files.filter(file -> file.toString().endsWith(".json")).toList();
    }
  }

  private static List<Path> listMobCaptureCardDefinitions() throws IOException {
    List<Path> definitions = new ArrayList<>();
    try (Stream<Path> namespaces = Files.list(resourcesRoot.resolve("data"))) {
      for (Path namespace : namespaces.toList()) {
        Path definitionFolder = namespace.resolve(MOB_CAPTURE_CARD_FOLDER);
        if (Files.isDirectory(definitionFolder)) {
          definitions.addAll(listJsonFiles(definitionFolder));
        }
      }
    }
    return definitions;
  }

  private static void collectModels(JsonElement element, List<String> models) {
    if (!element.isJsonObject()) {
      return;
    }

    for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
      if (MODEL_KEY.equals(entry.getKey()) && entry.getValue().isJsonPrimitive()) {
        models.add(entry.getValue().getAsString());
      } else {
        collectModels(entry.getValue(), models);
      }
    }
  }

  private static Path assetPath(String identifier, String folder, String extension) {
    Identifier location = Identifier.parse(identifier);
    return resourcesRoot
        .resolve("assets")
        .resolve(location.getNamespace())
        .resolve(folder)
        .resolve(location.getPath() + extension);
  }

  @Test
  @DisplayName("Every model referenced by a card definition exists")
  void everyModelReferencedByCardDefinitionExists() throws IOException {
    List<Path> definitions = listMobCaptureCardDefinitions();
    Assertions.assertFalse(definitions.isEmpty());

    List<String> missingModels = new ArrayList<>();
    for (Path definition : definitions) {
      List<String> models = new ArrayList<>();
      collectModels(readJson(definition), models);
      for (String model : models) {
        if (!Files.isRegularFile(assetPath(model, "models", ".json"))) {
          missingModels.add(definition.getFileName() + " -> " + model);
        }
      }
    }

    Assertions.assertEquals(List.of(), missingModels);
  }

  @Test
  @DisplayName("Every card model texture exists")
  void everyCardModelTextureExists() throws IOException {
    List<Path> models =
        listJsonFiles(
            resourcesRoot.resolve("assets/minecraft/models/item/" + MOB_CAPTURE_CARD_FOLDER));
    Assertions.assertFalse(models.isEmpty());

    List<String> missingTextures = new ArrayList<>();
    for (Path model : models) {
      JsonObject modelJson = readJson(model);
      if (!modelJson.has(TEXTURES_KEY)) {
        continue;
      }

      for (Map.Entry<String, JsonElement> texture :
          modelJson.getAsJsonObject(TEXTURES_KEY).entrySet()) {
        String textureLocation = texture.getValue().getAsString();
        if (!Files.isRegularFile(assetPath(textureLocation, "textures", ".png"))) {
          missingTextures.add(model.getFileName() + " -> " + textureLocation);
        }
      }
    }

    Assertions.assertEquals(List.of(), missingTextures);
  }
}
