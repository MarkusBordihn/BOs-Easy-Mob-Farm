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
import de.markusbordihn.easymobfarm.item.mobcatcher.EnduringCaptureNetItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.IronboundContainmentCageItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.MysticBindingCrystalItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.VoidBindingChainItem;
import java.util.Collection;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModRecipeManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final String LOG_PREFIX = "[Recipe Manager]";
  private static final String MOB_CAPTURE_PREFIX = "mob_catcher/";

  private ModRecipeManager() {}

  public static void register(MinecraftServer minecraftServer) {
    log.info("{} Mod Recipe Manager ...", Constants.LOG_REGISTER_PREFIX);

    // Disable recipes based on the given configuration.
    Collection<Recipe<?>> recipes = minecraftServer.getRecipeManager().getRecipes();
    removeRecipeIfDisabled(
        recipes, MobCatcherConfig.ENDURING_CAPTURE_NET_ENABLED, EnduringCaptureNetItem.ID);
    removeRecipeIfDisabled(
        recipes,
        MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_ENABLED,
        IronboundContainmentCageItem.ID);
    removeRecipeIfDisabled(
        recipes, MobCatcherConfig.MYSTIC_BINDING_CRYSTAL_ENABLED, MysticBindingCrystalItem.ID);
    removeRecipeIfDisabled(
        recipes, MobCatcherConfig.VOID_BINDING_CHAIN_ENABLED, VoidBindingChainItem.ID);

    if (minecraftServer.getRecipeManager().getRecipes().size() != recipes.size()) {
      // Replace recipes with adjusted recipes.
      log.info("Updating recipes with adjusted recipes ...");
      minecraftServer.getRecipeManager().replaceRecipes(recipes);

      // Sync recipes with connected clients.
      for (ServerPlayer player : minecraftServer.getPlayerList().getPlayers()) {
        log.info("{} Sync recipe to player {} ...", LOG_PREFIX, player.getName().getString());
        player.connection.send(
            new ClientboundUpdateRecipesPacket(minecraftServer.getRecipeManager().getRecipes()));
      }
      minecraftServer
          .getRecipeManager()
          .getRecipes()
          .forEach(recipe -> log.debug("{} Loaded recipe: {}", LOG_PREFIX, recipe.getId()));
    }
  }

  private static boolean removeRecipeIfDisabled(
      Collection<Recipe<?>> recipes, boolean isEnabled, String itemId) {
    if (isEnabled) {
      return false;
    }
    String recipePath = MOB_CAPTURE_PREFIX + itemId;
    boolean removed =
        recipes.removeIf(
            recipe ->
                recipe.getId().getNamespace().equals(Constants.MOD_ID)
                    && recipe.getId().getPath().equals(recipePath));
    if (removed) {
      log.info("{} Removed {} recipe ...", LOG_PREFIX, recipePath);
    } else {
      log.error("{} Failed to deactivate recipe {} !", LOG_PREFIX, recipePath);
    }
    return removed;
  }
}
