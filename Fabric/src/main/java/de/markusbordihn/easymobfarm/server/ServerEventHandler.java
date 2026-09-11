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
import de.markusbordihn.easymobfarm.network.message.client.SyncMobCaptureCardDefinitionsMessage;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class ServerEventHandler {

  private ServerEventHandler() {}

  public static void registerServerEvents() {
    ServerLifecycleEvents.SERVER_STARTED.register(ServerEventHandler::onServerStarted);
    ServerLifecycleEvents.SERVER_STARTING.register(ServerEventHandler::onServerStarting);
    ServerPlayConnectionEvents.JOIN.register(ServerEventHandler::onPlayerLogin);
    ServerTickEvents.END_SERVER_TICK.register(ServerEvents::handleServerTickEvent);
  }

  private static void onServerStarted(MinecraftServer server) {
    ServerEvents.handleServerStartedEvent(server);
  }

  private static void onServerStarting(MinecraftServer server) {
    ServerEvents.handleServerStartingEvent(server);
  }

  private static void onPlayerLogin(
      ServerGamePacketListenerImpl serverGamePacketListener,
      PacketSender packetSender,
      MinecraftServer minecraftServer) {
    SyncMobCaptureCardDefinitionsMessage message =
        new SyncMobCaptureCardDefinitionsMessage(MobCaptureCardDefinitionManager.getAll());

    ServerPlayNetworking.send(serverGamePacketListener.getPlayer(), message);

    ServerEvents.handlePlayerLoginEvent(serverGamePacketListener.getPlayer());
  }
}
