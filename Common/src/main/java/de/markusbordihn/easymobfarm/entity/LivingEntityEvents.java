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
import java.util.Random;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LivingEntityEvents {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final Random RANDOM = new Random();

  private LivingEntityEvents() {}

  public static void handleLivingEntityDeathEvent(
      LivingEntity livingEntity, DamageSource damageSource) {
    // Check if mob capture card drop is enabled.
    if (!MobCaptureCardConfig.dropMobCaptureCardOnKill) {
      return;
    }

    // Check if we require a player kill to drop the mob capture card.
    if (MobCaptureCardConfig.requirePlayerKill
        && (damageSource.getEntity() == null || !(damageSource.getEntity() instanceof Player))) {
      return;
    }

    // Check allow and deny list for drops.
    String entityName = BuiltInRegistries.ENTITY_TYPE.getKey(livingEntity.getType()).toString();
    if (MobCaptureCardConfig.mobCaptureCardKillDropDenyList.contains(entityName)
        || (!MobCaptureCardConfig.mobCaptureCardKillDropAllowList.isEmpty()
            && !MobCaptureCardConfig.mobCaptureCardKillDropAllowList.contains(entityName))) {
      log.debug("[Skip] Mob capture card kill drop for {}.", entityName);
      return;
    }

    // Check the drop chance for the mob capture card.
    float dropChance = MobCaptureCardConfig.mobCaptureCardKillDropChance;
    if (dropChance <= 0.0f || RANDOM.nextFloat() > dropChance) {
      return;
    }

    // Drop the mob capture card.
    ItemStack itemStack = MobCaptureManager.getMobCaptureCardItem(livingEntity);
    if (itemStack != null && livingEntity.level() instanceof ServerLevel serverLevel) {
      log.debug("Dropped mob capture card {} for {}.", itemStack, livingEntity);
      livingEntity.spawnAtLocation(serverLevel, itemStack, 0.5F);
    } else {
      log.error("Failed to drop mob capture card for {}.", livingEntity);
    }
  }
}
