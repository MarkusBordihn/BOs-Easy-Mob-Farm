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

package de.markusbordihn.easymobfarm.server;

import de.markusbordihn.easymobfarm.inventory.CraftingHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ServerEventHandler {

  private static final int PLAYER_INVENTORY_TICKS = 20;
  private static int playerInventoryTicker = 0;

  private ServerEventHandler() {}

  @SubscribeEvent
  public static void onServerStarted(ServerStartedEvent event) {
    ServerEvents.handleServerStartedEvent(event.getServer());
  }

  @SubscribeEvent
  public static void onServerStarting(ServerStartingEvent event) {
    ServerEvents.handleServerStartingEvent(event.getServer());
  }

  @SubscribeEvent
  public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
    if (event.phase == TickEvent.Phase.END && event.player instanceof ServerPlayer serverPlayer) {
      if (serverPlayer.containerMenu instanceof CraftingMenu craftingMenu) {
        CraftingHandler.handleCraftingMenu(craftingMenu, craftingMenu.craftSlots);
      }

      if (playerInventoryTicker++ > PLAYER_INVENTORY_TICKS) {
        CraftingHandler.handlePlayerInventory(serverPlayer);
        playerInventoryTicker = 0;
      }
    }
  }
}
