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

package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.item.ModRecipeManager;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import net.minecraft.world.level.storage.loot.LootTable;

public class ServerDataTestHelper {

  private static final ResourceKey<Recipe<?>> SPEED_ENHANCEMENT_RECIPE =
      ResourceKey.create(
          Registries.RECIPE,
          Identifier.fromNamespaceAndPath(
              Constants.MOD_ID, "upgrade/enhancement/speed_enhancement"));

  private ServerDataTestHelper() {}

  private static List<Identifier> modDataIds(
      MinecraftServer server, FileToIdConverter fileToIdConverter) {
    return fileToIdConverter.listMatchingResources(server.getResourceManager()).keySet().stream()
        .filter(file -> file.getNamespace().equals(Constants.MOD_ID))
        .map(fileToIdConverter::fileToId)
        .sorted()
        .toList();
  }

  private static void assertEveryModDataIsLoaded(
      GameTestHelper helper,
      FileToIdConverter fileToIdConverter,
      String dataName,
      Predicate<Identifier> isLoaded) {
    List<Identifier> dataIds = modDataIds(helper.getLevel().getServer(), fileToIdConverter);
    helper.assertTrue(!dataIds.isEmpty(), "No " + dataName + " files found");
    List<Identifier> notLoaded =
        dataIds.stream().filter(dataId -> !isLoaded.test(dataId)).toList();
    GameTestHelpers.assertTrue(
        helper,
        notLoaded.size() + " of " + dataIds.size() + " " + dataName + " not loaded: " + notLoaded,
        notLoaded.isEmpty());
  }

  public static void testEveryModAdvancementIsLoaded(GameTestHelper helper) {
    MinecraftServer server = helper.getLevel().getServer();
    assertEveryModDataIsLoaded(
        helper,
        FileToIdConverter.registry(Registries.ADVANCEMENT),
        "advancements",
        advancementId -> server.getAdvancements().get(advancementId) != null);
  }

  public static void testEveryModLootTableIsLoaded(GameTestHelper helper) {
    MinecraftServer server = helper.getLevel().getServer();
    assertEveryModDataIsLoaded(
        helper,
        FileToIdConverter.registry(Registries.LOOT_TABLE),
        "loot tables",
        lootTableId ->
            server
                    .reloadableRegistries()
                    .getLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTableId))
                != LootTable.EMPTY);
  }

  public static void testEveryModRecipeIsLoaded(GameTestHelper helper) {
    RecipeManager recipeManager = helper.getLevel().getServer().getRecipeManager();
    assertEveryModDataIsLoaded(
        helper,
        FileToIdConverter.registry(Registries.RECIPE),
        "recipes",
        recipeId -> recipeManager.byKey(ResourceKey.create(Registries.RECIPE, recipeId)).isPresent());
  }

  public static void testDisabledEnhancementRecipeIsRemoved(GameTestHelper helper) {
    MinecraftServer server = helper.getLevel().getServer();
    RecipeManager recipeManager = server.getRecipeManager();
    helper.assertTrue(
        recipeManager.byKey(SPEED_ENHANCEMENT_RECIPE).isPresent(),
        "Speed enhancement recipe is not loaded");

    Field recipesField = recipeMapField();
    RecipeMap originalRecipes = getRecipeMap(recipesField, recipeManager);
    boolean speedEnhancementEnabled = MobFarmConfig.enableSpeedEnhancement;
    try {
      MobFarmConfig.enableSpeedEnhancement = false;
      ModRecipeManager.register(server);

      GameTestHelpers.assertTrue(
          helper,
          "Disabled speed enhancement recipe is still available",
          recipeManager.byKey(SPEED_ENHANCEMENT_RECIPE).isEmpty());
    } finally {
      MobFarmConfig.enableSpeedEnhancement = speedEnhancementEnabled;
      setRecipeMap(recipesField, recipeManager, originalRecipes);
    }
  }

  private static Field recipeMapField() {
    try {
      Field recipesField = RecipeManager.class.getDeclaredField("recipes");
      recipesField.setAccessible(true);
      return recipesField;
    } catch (NoSuchFieldException e) {
      throw new IllegalStateException("RecipeManager has no recipes field", e);
    }
  }

  private static RecipeMap getRecipeMap(Field recipesField, RecipeManager recipeManager) {
    try {
      return (RecipeMap) recipesField.get(recipeManager);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Unable to read recipe map", e);
    }
  }

  private static void setRecipeMap(
      Field recipesField, RecipeManager recipeManager, RecipeMap recipeMap) {
    try {
      recipesField.set(recipeManager, recipeMap);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Unable to restore recipe map", e);
    }
  }
}
