/*
 * Copyright 2025 Markus Bordihn
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

package de.markusbordihn.easymobfarm.item;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.MobCatcherConfig;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.item.mobcatcher.EnduringCaptureNetItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.IronboundContainmentCageItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.MysticBindingCrystalItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.VoidBindingChainItem;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModRecipeManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final String LOG_PREFIX = "[Recipe Manager]";
  private static final String MOB_CAPTURE_PREFIX = "mob_catcher/";
  private static final String MOB_FARM_TEMPLATE_PREFIX = "mob_farm_template/";
  private static final String MOB_FARM_PREFIX = "mob_farm/";
  private static final String MOB_FARM_UPGRADE_SUFFIX = "_upgrade";
  private static final String ENHANCEMENT_PREFIX = "upgrade/enhancement/";

  private ModRecipeManager() {}

  public static void register(MinecraftServer minecraftServer) {
    log.debug("{} Mod Recipe Manager ...", Constants.LOG_REGISTER_PREFIX);

    // Disable recipes based on the given configuration.
    Collection<RecipeHolder<?>> recipes =
        new ArrayList<>(minecraftServer.getRecipeManager().getRecipes());

    // Disable mob catcher recipes if needed
    removeRecipeIfDisabled(
        recipes,
        MobCatcherConfig.ENDURING_CAPTURE_NET_ENABLED,
        EnduringCaptureNetItem.ID,
        MOB_CAPTURE_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_ENABLED,
        IronboundContainmentCageItem.ID,
        MOB_CAPTURE_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobCatcherConfig.MYSTIC_BINDING_CRYSTAL_ENABLED,
        MysticBindingCrystalItem.ID,
        MOB_CAPTURE_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobCatcherConfig.VOID_BINDING_CHAIN_ENABLED,
        VoidBindingChainItem.ID,
        MOB_CAPTURE_PREFIX);

    // Disable mob farm template recipes if logical tier progression is enforced
    removeRecipeIfDisabled(
        recipes,
        !MobFarmConfig.enforceLogicalTierProgression,
        "tier1_mob_farm_template",
        MOB_FARM_TEMPLATE_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        !MobFarmConfig.enforceLogicalTierProgression,
        "tier2_mob_farm_template",
        MOB_FARM_TEMPLATE_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        !MobFarmConfig.enforceLogicalTierProgression,
        "tier3_mob_farm_template",
        MOB_FARM_TEMPLATE_PREFIX);

    // Disable mob farm upgrade recipes if disabled in config
    if (!MobFarmConfig.enableMobFarmUpgradeRecipes) {
      recipes.removeIf(
          recipeHolder ->
              recipeHolder.id().identifier().getNamespace().equals(Constants.MOD_ID)
                  && recipeHolder.id().identifier().getPath().startsWith(MOB_FARM_PREFIX)
                  && recipeHolder.id().identifier().getPath().contains(MOB_FARM_UPGRADE_SUFFIX));
      log.info("{} Disabled mob farm upgrade recipes", LOG_PREFIX);
    }

    // Disable individual enhancement recipes if disabled in config
    removeRecipeIfDisabled(
        recipes, MobFarmConfig.enableSpeedEnhancement, "speed_enhancement", ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes, MobFarmConfig.enableLootEnhancement, "loot_enhancement", ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes, MobFarmConfig.enableLuckEnhancement, "luck_enhancement", ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes, MobFarmConfig.enableSwordEnhancement, "sword_enhancement", ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes, MobFarmConfig.enableKnifeEnhancement, "knife_enhancement", ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobFarmConfig.enableExperienceEnhancement,
        "experience_enhancement",
        ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobFarmConfig.enableEggCollectorEnhancement,
        "egg_collector_enhancement",
        ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobFarmConfig.enableHoneyExtractorEnhancement,
        "honey_extractor_enhancement",
        ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobFarmConfig.enableHoneyHarvesterFrameEnhancement,
        "honey_harvester_frame_enhancement",
        ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobFarmConfig.enableMilkExtractorEnhancement,
        "milk_extractor_enhancement",
        ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes,
        MobFarmConfig.enablePollenTrapEnhancement,
        "pollen_trap_enhancement",
        ENHANCEMENT_PREFIX);
    removeRecipeIfDisabled(
        recipes, MobFarmConfig.enableSheepEnhancement, "sheep_enhancement", ENHANCEMENT_PREFIX);

    if (minecraftServer.getRecipeManager().getRecipes().size() != recipes.size()) {
      // Replace recipes with adjusted recipes.
      log.info("Updating recipes with adjusted recipes ...");
      RecipeMap recipeMap = RecipeMap.create(recipes);

      // Use reflection to set the recipes.
      setRecipes(minecraftServer.getRecipeManager(), recipeMap);

      // Sync recipes with connected clients.
      for (ServerPlayer player : minecraftServer.getPlayerList().getPlayers()) {
        log.info("{} Sync recipe to player {} ...", LOG_PREFIX, player.getName().getString());
        player.connection.send(
            new ClientboundUpdateRecipesPacket(
                minecraftServer.getRecipeManager().getSynchronizedItemProperties(),
                minecraftServer.getRecipeManager().getSynchronizedStonecutterRecipes()));
      }
      minecraftServer
          .getRecipeManager()
          .getRecipes()
          .forEach(
              recipeHolder -> log.debug("{} Loaded recipe: {}", LOG_PREFIX, recipeHolder.id()));
    }
  }

  private static void setRecipes(RecipeManager recipeManager, RecipeMap recipeMap) {
    String[] possibleFieldNames = {"recipes", "f_346395_", "field_54638", "preparedRecipes"};
    for (String fieldName : possibleFieldNames) {
      try {
        Field recipesField = RecipeManager.class.getDeclaredField(fieldName);
        recipesField.setAccessible(true);
        recipesField.set(recipeManager, recipeMap);
        log.info("{} Updated recipe map using field '{}'!", LOG_PREFIX, fieldName);
        return;
      } catch (NoSuchFieldException e) {
        log.debug("{} Field '{}' not found, trying next...", LOG_PREFIX, fieldName);
      } catch (IllegalAccessException e) {
        log.error("{} Failed to set recipe map for field '{}'!", LOG_PREFIX, fieldName, e);
        return;
      }
    }
    log.error("{} Failed to update recipe map! No valid field names found.", LOG_PREFIX);
  }

  private static boolean removeRecipeIfDisabled(
      Collection<RecipeHolder<?>> recipes, boolean isEnabled, String itemId, String recipePrefix) {
    if (isEnabled) {
      return false;
    }
    String recipePath = Constants.MOD_ID + ":" + recipePrefix + itemId;
    boolean removed =
        recipes.removeIf(
            recipeHolder -> recipeHolder.id().identifier().toString().equals(recipePath));
    if (removed) {
      log.info("{} Removed {} recipe ...", LOG_PREFIX, recipePath);
    } else {
      log.error("{} Failed to deactivate recipe {} !", LOG_PREFIX, recipePath);
    }
    return removed;
  }
}
