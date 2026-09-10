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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

public class LootPreviewCache {

  private static final long CACHE_TTL_MS = 24L * 60L * 60L * 1000L;
  private static final Map<EntityType<?>, CacheEntry> serverCache = new ConcurrentHashMap<>();
  private static final Map<EntityType<?>, CacheEntry> clientCache = new ConcurrentHashMap<>();

  private LootPreviewCache() {}

  public static boolean isServerCacheValid(EntityType<?> entityType, int requiredSampleRolls) {
    CacheEntry entry = serverCache.get(entityType);
    return isValid(entry) && entry.sampleRolls() >= requiredSampleRolls;
  }

  public static List<ItemStack> getServerCachedPreview(EntityType<?> entityType) {
    CacheEntry entry = serverCache.get(entityType);
    return entry != null ? entry.items() : List.of();
  }

  public static void setServerCachedPreview(
      EntityType<?> entityType, List<ItemStack> items, int sampleRolls) {
    serverCache.put(entityType, new CacheEntry(items, System.currentTimeMillis(), sampleRolls));
  }

  public static Map<EntityType<?>, List<ItemStack>> getServerCachedPreviews() {
    Map<EntityType<?>, List<ItemStack>> previews = new HashMap<>();
    serverCache.forEach(
        (entityType, entry) -> {
          if (isValid(entry)) {
            previews.put(entityType, entry.items());
          }
        });
    return previews;
  }

  public static void setLootPreview(EntityType<?> entityType, List<ItemStack> items) {
    clientCache.put(entityType, new CacheEntry(items, System.currentTimeMillis(), 0));
  }

  public static List<ItemStack> getLootPreview(EntityType<?> entityType) {
    if (entityType == null) {
      return List.of();
    }

    CacheEntry entry = clientCache.get(entityType);
    return isValid(entry) ? entry.items() : List.of();
  }

  public static boolean hasLootPreview(EntityType<?> entityType) {
    return entityType != null && isValid(clientCache.get(entityType));
  }

  public static void clear() {
    clientCache.clear();
    serverCache.clear();
  }

  private static boolean isValid(CacheEntry entry) {
    return entry != null && (System.currentTimeMillis() - entry.timestamp()) < CACHE_TTL_MS;
  }

  private record CacheEntry(List<ItemStack> items, long timestamp, int sampleRolls) {}
}
