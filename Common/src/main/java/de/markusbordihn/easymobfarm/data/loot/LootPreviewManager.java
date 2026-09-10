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

package de.markusbordihn.easymobfarm.data.loot;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.loot.LootManager;
import de.markusbordihn.easymobfarm.network.message.client.SyncLootPreviewBatchMessage;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootPreviewManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final int FARM_SAMPLE_ROLLS = 3;
  private static final int PREVIEWS_PER_SYNC_MESSAGE = 16;

  private static final Deque<EntityType<?>> warmupQueue = new ArrayDeque<>();
  private static boolean warmupQueueFilled = false;
  private static int ticksSinceLastWarmupBatch = 0;

  private LootPreviewManager() {}

  public static List<ItemStack> getOrCompute(
      final MobCaptureData mobCaptureData, final Level level) {
    if (mobCaptureData == null || mobCaptureData.entityType() == null) {
      return List.of();
    }

    EntityType<?> entityType = mobCaptureData.entityType();
    if (LootPreviewCache.isServerCacheValid(entityType, FARM_SAMPLE_ROLLS)) {
      return LootPreviewCache.getServerCachedPreview(entityType);
    }

    List<ItemStack> preview =
        LootManager.getEntityLootPreview(mobCaptureData, level, FARM_SAMPLE_ROLLS);
    LootPreviewCache.setServerCachedPreview(entityType, preview, FARM_SAMPLE_ROLLS);
    return preview;
  }

  public static void handleServerTick(final MinecraftServer minecraftServer) {
    if (!MobFarmConfig.lootPreviewWarmupEnabled || minecraftServer.getPlayerCount() == 0) {
      return;
    }
    if (++ticksSinceLastWarmupBatch < MobFarmConfig.lootPreviewBatchIntervalTicks) {
      return;
    }
    ticksSinceLastWarmupBatch = 0;

    if (!warmupQueueFilled) {
      fillWarmupQueue();
    }
    if (warmupQueue.isEmpty()) {
      return;
    }

    Map<EntityType<?>, List<ItemStack>> previews =
        computeWarmupBatch(minecraftServer.overworld(), MobFarmConfig.lootPreviewBatchSize);
    if (previews.isEmpty()) {
      return;
    }

    for (ServerPlayer serverPlayer : minecraftServer.getPlayerList().getPlayers()) {
      SyncLootPreviewBatchMessage.sendToPlayer(serverPlayer, previews);
    }
  }

  public static void sendCachedPreviews(final ServerPlayer serverPlayer) {
    Map<EntityType<?>, List<ItemStack>> cachedPreviews = LootPreviewCache.getServerCachedPreviews();
    Map<EntityType<?>, List<ItemStack>> messagePreviews = new LinkedHashMap<>();
    for (Map.Entry<EntityType<?>, List<ItemStack>> cachedPreview : cachedPreviews.entrySet()) {
      messagePreviews.put(cachedPreview.getKey(), cachedPreview.getValue());
      if (messagePreviews.size() >= PREVIEWS_PER_SYNC_MESSAGE) {
        SyncLootPreviewBatchMessage.sendToPlayer(serverPlayer, messagePreviews);
        messagePreviews = new LinkedHashMap<>();
      }
    }
    SyncLootPreviewBatchMessage.sendToPlayer(serverPlayer, messagePreviews);
  }

  public static void reset() {
    warmupQueue.clear();
    warmupQueueFilled = false;
    ticksSinceLastWarmupBatch = 0;
    LootPreviewCache.clear();
  }

  private static Map<EntityType<?>, List<ItemStack>> computeWarmupBatch(
      final ServerLevel serverLevel, final int batchSize) {
    Map<EntityType<?>, List<ItemStack>> previews = new LinkedHashMap<>();
    for (int batchIndex = 0; batchIndex < batchSize; batchIndex++) {
      EntityType<?> entityType = warmupQueue.poll();
      if (entityType == null) {
        break;
      }
      if (LootPreviewCache.isServerCacheValid(entityType, MobFarmConfig.lootPreviewSampleRolls)) {
        continue;
      }

      List<ItemStack> preview =
          LootManager.getEntityLootPreview(
              new MobCaptureData(entityType), serverLevel, MobFarmConfig.lootPreviewSampleRolls);
      LootPreviewCache.setServerCachedPreview(
          entityType, preview, MobFarmConfig.lootPreviewSampleRolls);
      previews.put(entityType, preview);
    }
    return previews;
  }

  private static void fillWarmupQueue() {
    Set<EntityType<?>> entityTypes = MobCaptureCardDefinitionManager.getDefinedEntityTypes();
    if (entityTypes.isEmpty()) {
      return;
    }

    warmupQueue.addAll(entityTypes);
    warmupQueueFilled = true;
    log.info(
        "Pre-calculating loot preview for {} mob(s) in the background ...", warmupQueue.size());
  }
}
