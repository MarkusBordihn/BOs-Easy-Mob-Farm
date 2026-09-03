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
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
  public static final String PROTOCOL_VERSION = "3.0";
  public static final SimpleChannel INSTANCE =
      NetworkRegistry.newSimpleChannel(
          new ResourceLocation(Constants.MOD_ID, "network"),
          () -> PROTOCOL_VERSION,
          PROTOCOL_VERSION::equals,
          PROTOCOL_VERSION::equals);
  private static int packetId = 0;

  public static void registerClientNetworkMessageHandler() {
    INSTANCE.registerMessage(
        packetId++,
        SyncMobCaptureCardDefinitionsMessage.class,
        SyncMobCaptureCardDefinitionsMessage::write,
        SyncMobCaptureCardDefinitionsMessage::create,
        (message, contextSupplier) -> {
          NetworkEvent.Context context = contextSupplier.get();
          context.enqueueWork(
              () -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> message::handleClient));
          context.setPacketHandled(true);
        });

    INSTANCE.registerMessage(
        packetId++,
        SyncLootPreviewMessage.class,
        SyncLootPreviewMessage::write,
        SyncLootPreviewMessage::create,
        (message, contextSupplier) -> {
          NetworkEvent.Context context = contextSupplier.get();
          context.enqueueWork(
              () -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> message::handleClient));
          context.setPacketHandled(true);
        });
  }
}
