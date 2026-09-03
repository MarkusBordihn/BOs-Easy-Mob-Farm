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

package de.markusbordihn.easymobfarm.data.loot;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class LootPreviewCache {

  private static final long SERVER_CACHE_TTL_MS = 60_000L;
  private static final Map<EntityType<?>, ServerCacheEntry> serverCache = new ConcurrentHashMap<>();
  private static final Map<EntityType<?>, List<ItemStack>> clientCache = new ConcurrentHashMap<>();

  private LootPreviewCache() {}

  public static boolean isServerCacheValid(EntityType<?> entityType) {
    ServerCacheEntry entry = serverCache.get(entityType);
    return entry != null && (System.currentTimeMillis() - entry.timestamp()) < SERVER_CACHE_TTL_MS;
  }

  public static List<ItemStack> getServerCachedPreview(EntityType<?> entityType) {
    ServerCacheEntry entry = serverCache.get(entityType);
    return entry != null ? entry.items() : List.of();
  }

  public static void setServerCachedPreview(EntityType<?> entityType, List<ItemStack> items) {
    serverCache.put(entityType, new ServerCacheEntry(items, System.currentTimeMillis()));
  }

  public static void setLootPreview(EntityType<?> entityType, List<ItemStack> items) {
    clientCache.put(entityType, items);
  }

  public static List<ItemStack> getLootPreview(EntityType<?> entityType) {
    if (entityType == null) {
      return List.of();
    }

    return clientCache.getOrDefault(entityType, List.of());
  }

  public static boolean hasLootPreview(EntityType<?> entityType) {
    return entityType != null && clientCache.containsKey(entityType);
  }

  public static void clear() {
    clientCache.clear();
    serverCache.clear();
  }

  private record ServerCacheEntry(List<ItemStack> items, long timestamp) {}
}
