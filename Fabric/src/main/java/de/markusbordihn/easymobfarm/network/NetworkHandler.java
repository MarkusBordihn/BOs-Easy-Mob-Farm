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
import de.markusbordihn.easymobfarm.network.message.client.SyncLootPreviewBatchMessage;
import de.markusbordihn.easymobfarm.network.message.client.SyncLootPreviewMessage;
import de.markusbordihn.easymobfarm.network.message.client.SyncMobCaptureCardDefinitionsMessage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class NetworkHandler {

  public static final ResourceLocation SYNC_MOB_CAPTURE_CARD_DEFINITIONS =
      new ResourceLocation(Constants.MOD_ID, "sync_mob_capture_card_definitions");

  public static final ResourceLocation SYNC_LOOT_PREVIEW =
      new ResourceLocation(Constants.MOD_ID, "sync_loot_preview");

  public static final ResourceLocation SYNC_LOOT_PREVIEW_BATCH =
      new ResourceLocation(Constants.MOD_ID, "sync_loot_preview_batch");

  @Environment(EnvType.CLIENT)
  public static void registerClientNetworkMessageHandler() {
    ClientPlayNetworking.registerGlobalReceiver(
        SYNC_MOB_CAPTURE_CARD_DEFINITIONS,
        (client, handler, buffer, responseSender) -> {
          SyncMobCaptureCardDefinitionsMessage message =
              SyncMobCaptureCardDefinitionsMessage.create(buffer);
          client.execute(message::handleClient);
        });

    ClientPlayNetworking.registerGlobalReceiver(
        SYNC_LOOT_PREVIEW,
        (client, handler, buffer, responseSender) -> {
          SyncLootPreviewMessage message = SyncLootPreviewMessage.create(buffer);
          client.execute(message::handleClient);
        });

    ClientPlayNetworking.registerGlobalReceiver(
        SYNC_LOOT_PREVIEW_BATCH,
        (client, handler, buffer, responseSender) -> {
          SyncLootPreviewBatchMessage message = SyncLootPreviewBatchMessage.create(buffer);
          client.execute(message::handleClient);
        });
  }
}
