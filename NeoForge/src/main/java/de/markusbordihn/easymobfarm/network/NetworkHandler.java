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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final String PROTOCOL_VERSION = "1";

  @SubscribeEvent
  public static void registerClientNetworkMessageHandler(final RegisterPayloadHandlersEvent event) {
    final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);

    log.info("{} Network Handler with {} ...", Constants.LOG_REGISTER_PREFIX, PROTOCOL_VERSION);

    registrar.playToClient(
        SyncMobCaptureCardDefinitionsMessage.PAYLOAD_TYPE,
        SyncMobCaptureCardDefinitionsMessage.STREAM_CODEC,
        (customPacketPayload, playPayloadContext) -> {
          customPacketPayload.handleClient();
        });

    registrar.playToClient(
        SyncLootPreviewMessage.PAYLOAD_TYPE,
        SyncLootPreviewMessage.STREAM_CODEC,
        (customPacketPayload, playPayloadContext) -> {
          customPacketPayload.handleClient();
        });
  }
}
