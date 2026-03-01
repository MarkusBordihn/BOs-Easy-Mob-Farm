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

package de.markusbordihn.easymobfarm.network;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.network.message.client.SyncLootPreviewMessage;
import de.markusbordihn.easymobfarm.network.message.client.SyncMobCaptureCardDefinitionsMessage;
import net.minecraft.resources.Identifier;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class NetworkHandler {
  private static final int PROTOCOL_VERSION = 2;

  public static final SimpleChannel INSTANCE =
      ChannelBuilder.named(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "network"))
          .networkProtocolVersion(PROTOCOL_VERSION)
          .simpleChannel();

  private static int packetId = 0;

  public static void registerClientNetworkMessageHandler() {
    INSTANCE
        .messageBuilder(
            SyncMobCaptureCardDefinitionsMessage.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(SyncMobCaptureCardDefinitionsMessage::write)
        .decoder(SyncMobCaptureCardDefinitionsMessage::create)
        .consumerNetworkThread(
            (message, context) -> {
              context.enqueueWork(
                  () -> {
                    if (FMLEnvironment.dist.isClient()) {
                      message.handleClient();
                    }
                  });
              context.setPacketHandled(true);
            })
        .add();

    INSTANCE
        .messageBuilder(SyncLootPreviewMessage.class, packetId++, NetworkDirection.PLAY_TO_CLIENT)
        .encoder(SyncLootPreviewMessage::write)
        .decoder(SyncLootPreviewMessage::create)
        .consumerNetworkThread(
            (message, context) -> {
              context.enqueueWork(
                  () -> {
                    if (FMLEnvironment.dist.isClient()) {
                      message.handleClient();
                    }
                  });
              context.setPacketHandled(true);
            })
        .add();
  }
}
