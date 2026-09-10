/*
 * Copyright 2026 Markus Bordihn
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
import de.markusbordihn.easymobfarm.data.loot.LootPreviewCache;
import de.markusbordihn.easymobfarm.network.message.NetworkMessageRecord;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public record SyncLootPreviewBatchMessage(Map<EntityType<?>, List<ItemStack>> previews)
    implements NetworkMessageRecord {

  public static final Identifier MESSAGE_ID =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "sync_loot_preview_batch");
  public static final CustomPacketPayload.Type<SyncLootPreviewBatchMessage> PAYLOAD_TYPE =
      new Type<>(MESSAGE_ID);
  public static final StreamCodec<RegistryFriendlyByteBuf, SyncLootPreviewBatchMessage>
      STREAM_CODEC =
          StreamCodec.of(
              (buffer, message) -> message.write(buffer), SyncLootPreviewBatchMessage::create);

  public static BiConsumer<ServerPlayer, SyncLootPreviewBatchMessage> SENDER =
      (player, message) -> {};

  public static SyncLootPreviewBatchMessage create(FriendlyByteBuf buffer) {
    int numberOfPreviews = buffer.readVarInt();
    Map<EntityType<?>, List<ItemStack>> previews = new LinkedHashMap<>();
    for (int previewIndex = 0; previewIndex < numberOfPreviews; previewIndex++) {
      Optional<Holder.Reference<EntityType<?>>> entityTypeHolder =
          BuiltInRegistries.ENTITY_TYPE.get(buffer.readIdentifier());
      int numberOfItems = buffer.readVarInt();
      List<ItemStack> items = new ArrayList<>(numberOfItems);
      for (int itemIndex = 0; itemIndex < numberOfItems; itemIndex++) {
        Identifier itemId = buffer.readIdentifier();
        int count = buffer.readVarInt();
        BuiltInRegistries.ITEM
            .get(itemId)
            .ifPresentOrElse(
                itemHolder -> items.add(new ItemStack(itemHolder.value(), count)),
                () -> items.add(ItemStack.EMPTY));
      }
      entityTypeHolder.ifPresent(entityType -> previews.put(entityType.value(), items));
    }
    return new SyncLootPreviewBatchMessage(previews);
  }

  public static void sendToPlayer(
      ServerPlayer serverPlayer, Map<EntityType<?>, List<ItemStack>> previews) {
    if (previews.isEmpty()) {
      return;
    }

    SENDER.accept(serverPlayer, new SyncLootPreviewBatchMessage(previews));
  }

  @Override
  public void write(FriendlyByteBuf buffer) {
    buffer.writeVarInt(this.previews.size());
    this.previews.forEach(
        (entityType, items) -> {
          buffer.writeIdentifier(BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
          buffer.writeVarInt(items.size());
          for (ItemStack item : items) {
            buffer.writeIdentifier(BuiltInRegistries.ITEM.getKey(item.getItem()));
            buffer.writeVarInt(item.getCount());
          }
        });
  }

  @Override
  public Identifier id() {
    return MESSAGE_ID;
  }

  @Override
  public Type<SyncLootPreviewBatchMessage> type() {
    return PAYLOAD_TYPE;
  }

  @Override
  public void handleClient() {
    this.previews.forEach(LootPreviewCache::setLootPreview);
  }
}
