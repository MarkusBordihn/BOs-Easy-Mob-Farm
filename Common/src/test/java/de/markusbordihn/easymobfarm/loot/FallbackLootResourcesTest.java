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

package de.markusbordihn.easymobfarm.loot;

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
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.resources.Identifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FallbackLootResourcesTest {

  private static final String TAG_REFERENCE_PREFIX = "#";
  private static final Set<String> ALWAYS_LOADED_NAMESPACES =
    Set.of(Identifier.DEFAULT_NAMESPACE, Constants.MOD_ID);

  private static Path modDataRoot;

  @BeforeAll
  static void locateResources() throws URISyntaxException {
    modDataRoot =
      Path.of(FallbackLootResourcesTest.class.getResource("/data/" + Constants.MOD_ID).toURI());
  }

  private static JsonObject readJson(Path file) throws IOException {
    return JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8))
      .getAsJsonObject();
  }

  private static List<Path> listJsonFiles(String folder) throws IOException {
    try (Stream<Path> files = Files.walk(modDataRoot.resolve(folder))) {
      return files.filter(file -> file.toString().endsWith(".json")).toList();
    }
  }

  private static void collectLootEntries(JsonElement element, List<JsonObject> lootEntries) {
    if (element.isJsonArray()) {
      element.getAsJsonArray().forEach(child -> collectLootEntries(child, lootEntries));
      return;
    }
    if (!element.isJsonObject()) {
      return;
    }

    JsonObject jsonObject = element.getAsJsonObject();
    if (jsonObject.has("type") && (jsonObject.has("name") || jsonObject.has("items"))) {
      lootEntries.add(jsonObject);
    }
    jsonObject.entrySet().forEach(entry -> collectLootEntries(entry.getValue(), lootEntries));
  }

  private static boolean isAlwaysLoaded(String identifier) {
    return ALWAYS_LOADED_NAMESPACES.contains(Identifier.parse(identifier).getNamespace());
  }

  private static String withoutTagPrefix(String reference) {
    return reference.replace(TAG_REFERENCE_PREFIX, "");
  }

  private static String withoutDefaultNamespace(String type) {
    return type.replace(Identifier.DEFAULT_NAMESPACE + ":", "");
  }

  private static Path tagFile(String tagName) {
    Identifier tag = Identifier.parse(tagName);
    return modDataRoot
      .getParent()
      .resolve(tag.getNamespace())
      .resolve("tags/item")
      .resolve(tag.getPath() + ".json");
  }

  @Test
  @DisplayName("Fallback loot tables reference optional mod items only through tags")
  void fallbackLootTablesReferenceOptionalModItemsOnlyThroughTags() throws IOException {
    List<String> invalidEntries = new ArrayList<>();
    for (Path lootTable : listJsonFiles("loot_table/entities/fallback")) {
      List<JsonObject> lootEntries = new ArrayList<>();
      collectLootEntries(readJson(lootTable), lootEntries);
      for (JsonObject lootEntry : lootEntries) {
        String type = withoutDefaultNamespace(lootEntry.get("type").getAsString());
        if ("item".equals(type)) {
          String name = lootEntry.get("name").getAsString();
          if (!isAlwaysLoaded(name)) {
            invalidEntries.add(lootTable.getFileName() + " -> item " + name);
          }
        } else if ("tag".equals(type)) {
          String tagName = withoutTagPrefix(lootEntry.get("items").getAsString());
          if (!Files.isRegularFile(tagFile(tagName))) {
            invalidEntries.add(lootTable.getFileName() + " -> missing tag " + tagName);
          }
        }
      }
    }

    Assertions.assertEquals(List.of(), invalidEntries);
  }

  @Test
  @DisplayName("Fallback tags mark optional mod items as not required")
  void fallbackTagsMarkOptionalModItemsAsNotRequired() throws IOException {
    List<String> requiredEntries = new ArrayList<>();
    for (Path tag : listJsonFiles("tags/item/fallback")) {
      for (JsonElement value : readJson(tag).getAsJsonArray("values")) {
        if (value.isJsonPrimitive()) {
          if (!isAlwaysLoaded(withoutTagPrefix(value.getAsString()))) {
            requiredEntries.add(tag.getFileName() + " -> " + value.getAsString());
          }
        } else {
          JsonObject entry = value.getAsJsonObject();
          String id = withoutTagPrefix(entry.get("id").getAsString());
          boolean required = !entry.has("required") || entry.get("required").getAsBoolean();
          if (required && !isAlwaysLoaded(id)) {
            requiredEntries.add(tag.getFileName() + " -> " + id);
          }
        }
      }
    }

    Assertions.assertEquals(List.of(), requiredEntries);
  }
}
