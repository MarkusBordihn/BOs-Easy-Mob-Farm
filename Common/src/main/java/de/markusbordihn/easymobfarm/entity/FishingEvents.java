/*
 * Copyright 2024 Markus Bordihn
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

package de.markusbordihn.easymobfarm.entity;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobCaptureCardConfig;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FishingEvents {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Random RANDOM = new Random();

  private FishingEvents() {}

  public static void handleItemFishedEvent(ServerPlayer serverPlayer, List<ItemStack> lootList) {
    // Check if mob capture card drop is enabled.
    if (!MobCaptureCardConfig.dropMobCaptureCardOnFishing) {
      return;
    }

    // Check if we got any fish loot
    Item fishLoot = null;
    for (ItemStack itemStack : lootList) {
      if (itemStack.isEmpty()) {
        continue;
      }
      if (itemStack.is(ItemTags.FISHES)) {
        fishLoot = itemStack.getItem();
        break;
      }
    }
    if (fishLoot == null) {
      return;
    }

    // Check if the fish loot could be translated to an entity.
    ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(fishLoot);
    Optional<Holder.Reference<EntityType<?>>> entityType =
        BuiltInRegistries.ENTITY_TYPE.get(resourceLocation);
    if (entityType.isEmpty()) {
      return;
    }

    // Check allow and deny list for drops.
    String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entityType.get().value()).toString();
    if (MobCaptureCardConfig.mobCaptureCardFishingDropDenyList.contains(entityName)
        || (!MobCaptureCardConfig.mobCaptureCardFishingDropAllowList.isEmpty()
            && !MobCaptureCardConfig.mobCaptureCardFishingDropAllowList.contains(entityName))) {
      log.debug("[Skip] Mob capture card fishing drop for {}.", entityName);
      return;
    }

    // Check the drop chance for the mob capture card.
    if (MobCaptureCardConfig.mobCaptureCardFishingDropChance > 0.0f
        && RANDOM.nextFloat() > MobCaptureCardConfig.mobCaptureCardFishingDropChance) {
      return;
    }

    // Drop the mob capture card.
    ItemStack itemStack =
        MobCaptureManager.getMobCaptureCardItem(entityType.get().value(), serverPlayer.level());
    if (itemStack != null) {
      log.debug("Dropped mob capture card {} for {}.", itemStack, entityType);
      serverPlayer.spawnAtLocation(serverPlayer.serverLevel(), itemStack, 0.5F);
    } else {
      log.error("Failed to drop mob capture card for {}.", entityType);
    }
  }
}
