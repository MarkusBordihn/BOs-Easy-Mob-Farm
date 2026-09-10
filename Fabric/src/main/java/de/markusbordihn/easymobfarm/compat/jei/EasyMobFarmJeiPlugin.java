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

package de.markusbordihn.easymobfarm.compat.jei;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.loot.MobFarmLootDisplay;
import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import de.markusbordihn.easymobfarm.tabs.CustomMobCaptureCards;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class EasyMobFarmJeiPlugin implements IModPlugin {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final ResourceLocation pluginId =
      new ResourceLocation(Constants.MOD_ID, "jei_plugin");
  private static final List<ItemStack> runtimeAddedCards = new ArrayList<>();
  private static final List<MobFarmLootDisplay> runtimeAddedLootDisplays = new ArrayList<>();
  private static volatile IJeiRuntime jeiRuntime;

  public static void refreshMobCaptureCards() {
    if (jeiRuntime == null) {
      log.debug("[JEI Compat] JEI runtime not available yet, skipping mob capture card refresh.");
      return;
    }

    IIngredientManager ingredientManager = jeiRuntime.getIngredientManager();
    if (!runtimeAddedCards.isEmpty()) {
      ingredientManager.removeIngredientsAtRuntime(
          VanillaTypes.ITEM_STACK, new ArrayList<>(runtimeAddedCards));
      runtimeAddedCards.clear();
    }

    Set<ItemStack> cards = CustomMobCaptureCards.getCustomMobCaptureCards(Items.MOB_CAPTURE_CARD);
    if (!cards.isEmpty()) {
      log.info("[JEI Compat] Adding {} mob capture card(s) to JEI at runtime.", cards.size());
      ingredientManager.addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, cards);
      runtimeAddedCards.addAll(cards);
    }

    refreshMobFarmLootDisplays();
  }

  private static void refreshMobFarmLootDisplays() {
    IRecipeManager recipeManager = jeiRuntime.getRecipeManager();
    if (!runtimeAddedLootDisplays.isEmpty()) {
      recipeManager.hideRecipes(
          MobFarmLootCategory.RECIPE_TYPE, new ArrayList<>(runtimeAddedLootDisplays));
      runtimeAddedLootDisplays.clear();
    }

    List<MobFarmLootDisplay> lootDisplays = MobFarmLootDisplay.createAll(Items.MOB_CAPTURE_CARD);
    if (!lootDisplays.isEmpty()) {
      recipeManager.addRecipes(MobFarmLootCategory.RECIPE_TYPE, lootDisplays);
      runtimeAddedLootDisplays.addAll(lootDisplays);
    }
  }

  @Override
  public @NotNull ResourceLocation getPluginUid() {
    return pluginId;
  }

  @Override
  public void onRuntimeAvailable(@NotNull IJeiRuntime runtime) {
    EasyMobFarmJeiPlugin.jeiRuntime = runtime;
  }

  @Override
  public void onRuntimeUnavailable() {
    EasyMobFarmJeiPlugin.jeiRuntime = null;
    EasyMobFarmJeiPlugin.runtimeAddedCards.clear();
    EasyMobFarmJeiPlugin.runtimeAddedLootDisplays.clear();
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    registration.addRecipeCategories(
        new MobFarmLootCategory(
            registration.getJeiHelpers().getGuiHelper(), ModBlockItems.ANIMAL_PLAINS_FARM));
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    registration.addRecipes(
        MobFarmLootCategory.RECIPE_TYPE, MobFarmLootDisplay.createAll(Items.MOB_CAPTURE_CARD));
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
    List<ItemLike> mobFarmItems =
        List.of(
            ModBlockItems.ANIMAL_PLAINS_FARM,
            ModBlockItems.BEE_HIVE_FARM,
            ModBlockItems.DESERT_FARM,
            ModBlockItems.END_FARM,
            ModBlockItems.IRON_GOLEM_FARM,
            ModBlockItems.JUNGLE_FARM,
            ModBlockItems.LUCKY_DROP_FARM,
            ModBlockItems.MONSTER_PLAINS_CAVE_FARM,
            ModBlockItems.NETHER_FORTRESS_FARM,
            ModBlockItems.NETHER_WASTES_FARM,
            ModBlockItems.OCEAN_FARM,
            ModBlockItems.SWAMP_FARM);
    for (ItemLike mobFarmItem : mobFarmItems) {
      registration.addRecipeCatalyst(new ItemStack(mobFarmItem), MobFarmLootCategory.RECIPE_TYPE);
    }
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registration) {
    registration.useNbtForSubtypes(Items.MOB_CAPTURE_CARD);
    registration.useNbtForSubtypes(ModBlockItems.ANIMAL_PLAINS_FARM);
    registration.useNbtForSubtypes(ModBlockItems.BEE_HIVE_FARM);
    registration.useNbtForSubtypes(ModBlockItems.DESERT_FARM);
    registration.useNbtForSubtypes(ModBlockItems.END_FARM);
    registration.useNbtForSubtypes(ModBlockItems.IRON_GOLEM_FARM);
    registration.useNbtForSubtypes(ModBlockItems.JUNGLE_FARM);
    registration.useNbtForSubtypes(ModBlockItems.LUCKY_DROP_FARM);
    registration.useNbtForSubtypes(ModBlockItems.MONSTER_PLAINS_CAVE_FARM);
    registration.useNbtForSubtypes(ModBlockItems.NETHER_FORTRESS_FARM);
    registration.useNbtForSubtypes(ModBlockItems.NETHER_WASTES_FARM);
    registration.useNbtForSubtypes(ModBlockItems.OCEAN_FARM);
    registration.useNbtForSubtypes(ModBlockItems.SWAMP_FARM);
  }
}
