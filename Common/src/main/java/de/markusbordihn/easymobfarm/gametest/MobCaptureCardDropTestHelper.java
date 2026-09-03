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

package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.config.MobCaptureCardConfig;
import de.markusbordihn.easymobfarm.entity.LivingEntityEvents;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

public class MobCaptureCardDropTestHelper {

  private static final BlockPos SPAWN_POSITION = new BlockPos(1, 2, 1);
  private static final int ZERO_CHANCE_ATTEMPTS = 32;

  private MobCaptureCardDropTestHelper() {}

  public static void testNoCardDropWithZeroChance(GameTestHelper helper) {
    boolean previousRequirePlayerKill = MobCaptureCardConfig.requirePlayerKill;
    float previousDropChance = MobCaptureCardConfig.mobCaptureCardKillDropChance;
    try {
      MobCaptureCardConfig.requirePlayerKill = false;
      MobCaptureCardConfig.mobCaptureCardKillDropChance = 0.0f;
      int droppedCards = countDroppedCards(helper, ZERO_CHANCE_ATTEMPTS);
      GameTestHelpers.assertTrue(
          helper,
          "A drop chance of 0.0 should never drop a mob capture card, got: " + droppedCards,
          droppedCards == 0);
    } finally {
      MobCaptureCardConfig.requirePlayerKill = previousRequirePlayerKill;
      MobCaptureCardConfig.mobCaptureCardKillDropChance = previousDropChance;
    }
  }

  public static void testSingleCardDropWithFullChance(GameTestHelper helper) {
    boolean previousRequirePlayerKill = MobCaptureCardConfig.requirePlayerKill;
    float previousDropChance = MobCaptureCardConfig.mobCaptureCardKillDropChance;
    try {
      MobCaptureCardConfig.requirePlayerKill = false;
      MobCaptureCardConfig.mobCaptureCardKillDropChance = 1.0f;
      int droppedCards = countDroppedCards(helper, 1);
      GameTestHelpers.assertTrue(
          helper,
          "A drop chance of 1.0 should drop exactly one mob capture card, got: " + droppedCards,
          droppedCards == 1);
    } finally {
      MobCaptureCardConfig.requirePlayerKill = previousRequirePlayerKill;
      MobCaptureCardConfig.mobCaptureCardKillDropChance = previousDropChance;
    }
  }

  private static int countDroppedCards(GameTestHelper helper, int attempts) {
    LivingEntity livingEntity = helper.spawn(EntityType.COW, SPAWN_POSITION);
    DamageSource damageSource = helper.getLevel().damageSources().generic();
    for (int i = 0; i < attempts; i++) {
      LivingEntityEvents.handleLivingEntityDeathEvent(livingEntity, damageSource);
    }

    AABB searchArea = new AABB(helper.absolutePos(SPAWN_POSITION)).inflate(2.0);
    List<ItemEntity> itemEntities =
        helper.getLevel().getEntitiesOfClass(ItemEntity.class, searchArea);
    int droppedCards = 0;
    for (ItemEntity itemEntity : itemEntities) {
      if (itemEntity.getItem().getItem() instanceof MobCaptureCardItem) {
        droppedCards++;
      }
      itemEntity.discard();
    }
    livingEntity.discard();
    return droppedCards;
  }
}
