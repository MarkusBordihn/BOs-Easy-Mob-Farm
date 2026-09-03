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
import de.markusbordihn.easymobfarm.data.loot.LootPreviewCache;
import de.markusbordihn.easymobfarm.network.message.NetworkMessageRecord;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public record SyncLootPreviewMessage(
    BlockPos blockPos, EntityType<?> entityType, List<ItemStack> items)
    implements NetworkMessageRecord {

  public static final ResourceLocation MESSAGE_ID =
      new ResourceLocation(Constants.MOD_ID, "sync_loot_preview");

  public static BiConsumer<ServerPlayer, SyncLootPreviewMessage> SENDER = (player, msg) -> {};

  public static SyncLootPreviewMessage create(FriendlyByteBuf buffer) {
    BlockPos pos = buffer.readBlockPos();
    ResourceLocation entityTypeId = buffer.readResourceLocation();
    EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.get(entityTypeId);
    int size = buffer.readVarInt();
    List<ItemStack> items = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      items.add(buffer.readItem());
    }
    return new SyncLootPreviewMessage(pos, type, items);
  }

  public static void sendToPlayer(
      ServerPlayer player, BlockPos pos, EntityType<?> entityType, List<ItemStack> items) {
    SENDER.accept(player, new SyncLootPreviewMessage(pos, entityType, items));
  }

  @Override
  public void write(FriendlyByteBuf buffer) {
    buffer.writeBlockPos(blockPos);
    buffer.writeResourceLocation(BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
    buffer.writeVarInt(items.size());
    for (ItemStack item : items) {
      buffer.writeItem(item);
    }
  }

  @Override
  public ResourceLocation id() {
    return MESSAGE_ID;
  }

  @Override
  public void handleClient() {
    if (entityType == null) {
      return;
    }
    LootPreviewCache.setLootPreview(entityType, items != null ? items : List.of());
  }
}
