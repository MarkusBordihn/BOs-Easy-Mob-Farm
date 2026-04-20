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
import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import de.markusbordihn.easymobfarm.tabs.CustomMobCaptureCards;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.ISubtypeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class EasyMobFarmJeiPlugin implements IModPlugin {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final ResourceLocation pluginId =
      new ResourceLocation(Constants.MOD_ID, "jei_plugin");
  private static final List<ItemStack> runtimeAddedCards = new ArrayList<>();
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
  }

  @Override
  public void registerItemSubtypes(ISubtypeRegistration registration) {
    registration.useNbtForSubtypes(Items.MOB_CAPTURE_CARD);
    registration.useNbtForSubtypes(ModBlockItems.ANIMAL_PLAINS_FARM);
    registration.useNbtForSubtypes(ModBlockItems.BEE_HIVE_FARM);
    registration.useNbtForSubtypes(ModBlockItems.DESERT_FARM);
    registration.useNbtForSubtypes(ModBlockItems.IRON_GOLEM_FARM);
    registration.useNbtForSubtypes(ModBlockItems.JUNGLE_FARM);
    registration.useNbtForSubtypes(ModBlockItems.LUCKY_DROP_FARM);
    registration.useNbtForSubtypes(ModBlockItems.MONSTER_PLAINS_CAVE_FARM);
    registration.useNbtForSubtypes(ModBlockItems.NETHER_FORTRESS_FARM);
    registration.useNbtForSubtypes(ModBlockItems.OCEAN_FARM);
    registration.useNbtForSubtypes(ModBlockItems.SWAMP_FARM);
  }
}
