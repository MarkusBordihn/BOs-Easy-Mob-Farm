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

import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.inventory.CraftingHandler;
import de.markusbordihn.easymobfarm.network.message.client.SyncMobCaptureCardDefinitionsMessage;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.inventory.CraftingMenu;

public class ServerEventHandler {

  private static final int PLAYER_INVENTORY_TICKS = 25;
  private static int playerInventoryTicker = 0;

  private ServerEventHandler() {}

  public static void registerServerEvents() {
    ServerLifecycleEvents.SERVER_STARTED.register(ServerEventHandler::onServerStarted);
    ServerLifecycleEvents.SERVER_STARTING.register(ServerEventHandler::onServerStarting);
    ServerTickEvents.END_SERVER_TICK.register(ServerEventHandler::onServerTick);
    ServerPlayConnectionEvents.JOIN.register(ServerEventHandler::onPlayerLogin);
  }

  private static void onServerStarted(MinecraftServer server) {
    ServerEvents.handleServerStartedEvent(server);
  }

  private static void onServerStarting(MinecraftServer server) {
    ServerEvents.handleServerStartingEvent(server);
  }

  private static void onServerTick(MinecraftServer minecraftServer) {
    for (ServerPlayer serverPlayer : minecraftServer.getPlayerList().getPlayers()) {
      if (serverPlayer.containerMenu instanceof CraftingMenu craftingMenu) {
        CraftingHandler.handleCraftingMenu(craftingMenu, craftingMenu.craftSlots);
      }

      if (playerInventoryTicker++ > PLAYER_INVENTORY_TICKS) {
        CraftingHandler.handlePlayerInventory(serverPlayer);
        playerInventoryTicker = 0;
      }
    }
  }

  private static void onPlayerLogin(
      ServerGamePacketListenerImpl serverGamePacketListener,
      PacketSender packetSender,
      MinecraftServer minecraftServer) {
    SyncMobCaptureCardDefinitionsMessage message =
        new SyncMobCaptureCardDefinitionsMessage(MobCaptureCardDefinitionManager.getAll());

    FriendlyByteBuf buffer = PacketByteBufs.create();
    message.write(buffer);

    ServerPlayNetworking.send(
        serverGamePacketListener.getPlayer(),
        SyncMobCaptureCardDefinitionsMessage.MESSAGE_ID,
        buffer);
  }
}
