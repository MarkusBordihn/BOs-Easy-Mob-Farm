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

package de.markusbordihn.easymobfarm.network.message.client;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.compat.CompatManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.network.message.NetworkMessageRecord;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record SyncMobCaptureCardDefinitionsMessage(
    Map<ResourceLocation, MobCaptureCardDefinition> definitions) implements NetworkMessageRecord {

  public static final ResourceLocation MESSAGE_ID =
      new ResourceLocation(Constants.MOD_ID, "sync_mob_capture_card_definitions");

  public static SyncMobCaptureCardDefinitionsMessage create(FriendlyByteBuf buffer) {
    int size = buffer.readVarInt();
    Map<ResourceLocation, MobCaptureCardDefinition> definitions = new HashMap<>();
    for (int i = 0; i < size; i++) {
      ResourceLocation entityId = buffer.readResourceLocation();
      MobCaptureCardDefinition definition = MobCaptureCardDefinition.decode(buffer);
      definitions.put(entityId, definition);
    }
    return new SyncMobCaptureCardDefinitionsMessage(definitions);
  }

  @Override
  public void write(FriendlyByteBuf buffer) {
    buffer.writeVarInt(definitions.size());
    for (Map.Entry<ResourceLocation, MobCaptureCardDefinition> entry : definitions.entrySet()) {
      buffer.writeResourceLocation(entry.getKey());
      entry.getValue().encode(buffer);
    }
  }

  @Override
  public ResourceLocation id() {
    return MESSAGE_ID;
  }

  @Override
  public void handleClient() {
    if (definitions == null || definitions.isEmpty()) {
      log.warn("No mob capture card definitions to sync!");
      return;
    }

    log.info("Syncing {} mob capture card definitions to client.", definitions.size());
    MobCaptureCardDefinitionManager.setDefinitions(definitions);

    if (CompatManager.getHandler() != null) {
      log.debug("Notifying compat handler about mob capture card definitions sync.");
      CompatManager.getHandler().onMobCaptureCardDefinitionsSynced();
    }
  }
}
