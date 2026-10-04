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
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FishingEvents {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Random RANDOM = new Random();

  private FishingEvents() {}

  public static void handleItemFishedEvent(Player player, Collection<ItemStack> fishingLoot) {
    if (!MobCaptureCardConfig.dropMobCaptureCardOnFishing) {
      return;
    }

    EntityType<?> entityType = findFishedEntityType(fishingLoot);
    if (entityType == null) {
      return;
    }

    String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(entityType).toString();
    if (MobCaptureCardConfig.mobCaptureCardFishingDropDenyList.contains(entityName)
        || (!MobCaptureCardConfig.mobCaptureCardFishingDropAllowList.isEmpty()
            && !MobCaptureCardConfig.mobCaptureCardFishingDropAllowList.contains(entityName))) {
      log.debug("[Skip] Mob capture card fishing drop for {}.", entityName);
      return;
    }

    float dropChance = MobCaptureCardConfig.mobCaptureCardFishingDropChance;
    if (dropChance <= 0.0f || RANDOM.nextFloat() > dropChance) {
      return;
    }

    ItemStack itemStack = MobCaptureManager.getMobCaptureCardItem(entityType, player.level());
    if (itemStack != null) {
      log.debug("Dropped mob capture card {} for {}.", itemStack, entityType);
      player.spawnAtLocation(itemStack, 0.5F);
    } else {
      log.error("Failed to drop mob capture card for {}.", entityType);
    }
  }

  private static EntityType<?> findFishedEntityType(Collection<ItemStack> fishingLoot) {
    for (ItemStack itemStack : fishingLoot) {
      if (itemStack.isEmpty() || !itemStack.is(ItemTags.FISHES)) {
        continue;
      }
      Optional<EntityType<?>> entityType =
          BuiltInRegistries.ENTITY_TYPE.getOptional(
              BuiltInRegistries.ITEM.getKey(itemStack.getItem()));
      if (entityType.isPresent()) {
        return entityType.get();
      }
    }
    return null;
  }
}
