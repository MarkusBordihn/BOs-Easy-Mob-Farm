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

package de.markusbordihn.easymobfarm.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.markusbordihn.easymobfarm.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RecipeAdvancementTest {

  private static final String NAMESPACE_PREFIX = Constants.MOD_ID + ":";
  private static Path dataDirectory;
  private static Path assetsDirectory;

  @BeforeAll
  static void resolveDataDirectory() throws Exception {
    URL dataUrl = RecipeAdvancementTest.class.getResource("/data/" + Constants.MOD_ID);
    Assertions.assertNotNull(dataUrl, "Unable to find the data directory of " + Constants.MOD_ID);
    dataDirectory = Path.of(dataUrl.toURI());

    URL assetsUrl = RecipeAdvancementTest.class.getResource("/assets/" + Constants.MOD_ID);
    Assertions.assertNotNull(
        assetsUrl, "Unable to find the assets directory of " + Constants.MOD_ID);
    assetsDirectory = Path.of(assetsUrl.toURI());
  }

  private static Set<String> recipeIds() throws IOException {
    Path recipeDirectory = dataDirectory.resolve("recipes");

    return jsonFiles(recipeDirectory).stream()
        .map(recipe -> NAMESPACE_PREFIX + toResourcePath(recipeDirectory, recipe))
        .collect(Collectors.toCollection(TreeSet::new));
  }

  private static Set<String> unlockedRecipeIds() throws IOException {
    Set<String> unlockedRecipes = new TreeSet<>();
    for (Path advancement : jsonFiles(dataDirectory.resolve("advancements"))) {
      JsonObject rewards = readJsonObject(advancement).getAsJsonObject("rewards");
      if (rewards == null) {
        continue;
      }

      JsonArray recipes = rewards.getAsJsonArray("recipes");
      if (recipes == null) {
        continue;
      }

      for (JsonElement recipe : recipes) {
        unlockedRecipes.add(recipe.getAsString());
      }
    }

    return unlockedRecipes;
  }

  private static String toResourcePath(Path directory, Path file) {
    String relativePath = directory.relativize(file).toString().replace('\\', '/');
    return relativePath.substring(0, relativePath.length() - ".json".length());
  }

  private static JsonObject readJsonObject(Path file) throws IOException {
    try (BufferedReader reader = Files.newBufferedReader(file)) {
      return JsonParser.parseReader(reader).getAsJsonObject();
    }
  }

  private static Set<Path> jsonFiles(Path directory) throws IOException {
    Assertions.assertTrue(Files.isDirectory(directory), "Missing directory " + directory);

    try (Stream<Path> files = Files.walk(directory)) {
      return files
          .filter(file -> file.getFileName().toString().endsWith(".json"))
          .collect(Collectors.toCollection(TreeSet::new));
    }
  }

  @Test
  @DisplayName("Every recipe is unlocked by an advancement")
  void everyRecipeIsUnlockedByAnAdvancement() throws IOException {
    Set<String> recipesWithoutAdvancement = new TreeSet<>(recipeIds());
    recipesWithoutAdvancement.removeAll(unlockedRecipeIds());

    Assertions.assertEquals(
        Set.of(),
        recipesWithoutAdvancement,
        "Recipes without an unlock advancement never show up in the recipe book");
  }

  @Test
  @DisplayName("Every unlocked recipe exists")
  void everyUnlockedRecipeExists() throws IOException {
    Set<String> unknownRecipes = new TreeSet<>(unlockedRecipeIds());
    unknownRecipes.removeAll(recipeIds());

    Assertions.assertEquals(
        Set.of(), unknownRecipes, "Advancements unlock recipes which do not exist");
  }

  @Test
  @DisplayName("Every advancement parent of this mod exists")
  void everyAdvancementParentOfThisModExists() throws IOException {
    Set<String> missingParents = new TreeSet<>();
    for (Path advancement : jsonFiles(dataDirectory.resolve("advancements"))) {
      JsonElement parent = readJsonObject(advancement).get("parent");
      if (parent == null || !parent.getAsString().startsWith(NAMESPACE_PREFIX)) {
        continue;
      }

      String relativePath = parent.getAsString().substring(NAMESPACE_PREFIX.length()) + ".json";
      if (!Files.exists(dataDirectory.resolve("advancements").resolve(relativePath))) {
        missingParents.add(parent.getAsString());
      }
    }

    Assertions.assertEquals(Set.of(), missingParents, "Advancements refer to a missing parent");
  }

  @Test
  @DisplayName("Every visible advancement is translated in every language")
  void everyVisibleAdvancementIsTranslatedInEveryLanguage() throws IOException {
    Set<String> displayedKeys = new TreeSet<>();
    for (Path advancement : jsonFiles(dataDirectory.resolve("advancements"))) {
      JsonObject display = readJsonObject(advancement).getAsJsonObject("display");
      if (display == null) {
        continue;
      }

      for (String field : List.of("title", "description")) {
        JsonElement translation = display.getAsJsonObject(field).get("translate");
        if (translation != null) {
          displayedKeys.add(translation.getAsString());
        }
      }
    }

    Set<String> missingTranslations = new TreeSet<>();
    for (Path language : jsonFiles(assetsDirectory.resolve("lang"))) {
      JsonObject translations = readJsonObject(language);
      for (String displayedKey : displayedKeys) {
        if (!translations.has(displayedKey)) {
          missingTranslations.add(language.getFileName() + ": " + displayedKey);
        }
      }
    }

    Assertions.assertEquals(
        Set.of(), missingTranslations, "Advancements use untranslated title or description keys");
  }
}
