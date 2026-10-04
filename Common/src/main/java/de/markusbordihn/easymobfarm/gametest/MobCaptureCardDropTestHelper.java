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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobCaptureCardConfig;
import de.markusbordihn.easymobfarm.entity.FishingEvents;
import de.markusbordihn.easymobfarm.entity.LivingEntityEvents;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobCaptureCardDropTestHelper {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final BlockPos SPAWN_POSITION = new BlockPos(1, 2, 1);
  private static final int ZERO_CHANCE_ATTEMPTS = 32;
  private static final float DEFAULT_KILL_DROP_CHANCE = 10 / 100f;
  private static final int MAX_KILLS_PER_MOB = 100;

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

  public static void testEveryDefinedMobDropsItsOwnCardOnKill(GameTestHelper helper) {
    boolean previousDropOnKill = MobCaptureCardConfig.dropMobCaptureCardOnKill;
    boolean previousRequirePlayerKill = MobCaptureCardConfig.requirePlayerKill;
    float previousDropChance = MobCaptureCardConfig.mobCaptureCardKillDropChance;
    Set<String> previousAllowList = MobCaptureCardConfig.mobCaptureCardKillDropAllowList;
    Set<String> previousDenyList = MobCaptureCardConfig.mobCaptureCardKillDropDenyList;
    List<String> mobsWithoutCard = new ArrayList<>();
    List<String> mobsWithWrongCard = new ArrayList<>();
    try {
      MobCaptureCardConfig.dropMobCaptureCardOnKill = true;
      MobCaptureCardConfig.requirePlayerKill = false;
      MobCaptureCardConfig.mobCaptureCardKillDropChance = DEFAULT_KILL_DROP_CHANCE;
      MobCaptureCardConfig.mobCaptureCardKillDropAllowList = Set.of();
      MobCaptureCardConfig.mobCaptureCardKillDropDenyList = Set.of();
      for (EntityType<?> entityType : GameTestHelpers.definedMobCaptureCardEntityTypes(helper)) {
        ItemStack droppedCard = killUntilCardDrops(helper, entityType);
        EntityType<?> cardEntityType = MobCaptureManager.getEntityType(droppedCard);
        if (droppedCard.isEmpty()) {
          mobsWithoutCard.add(entityType.toShortString());
        } else if (cardEntityType != entityType) {
          mobsWithWrongCard.add(entityType.toShortString() + " -> " + cardEntityType);
        }
      }
    } finally {
      MobCaptureCardConfig.dropMobCaptureCardOnKill = previousDropOnKill;
      MobCaptureCardConfig.requirePlayerKill = previousRequirePlayerKill;
      MobCaptureCardConfig.mobCaptureCardKillDropChance = previousDropChance;
      MobCaptureCardConfig.mobCaptureCardKillDropAllowList = previousAllowList;
      MobCaptureCardConfig.mobCaptureCardKillDropDenyList = previousDenyList;
    }

    if (!mobsWithoutCard.isEmpty()) {
      log.warn(
          "[Game Test] No mob capture card dropped within {} kills for: {}",
          MAX_KILLS_PER_MOB,
          mobsWithoutCard);
    }
    GameTestHelpers.assertTrue(
        helper,
        "Killed mobs dropped a mob capture card of another mob: " + mobsWithWrongCard,
        mobsWithWrongCard.isEmpty());
  }

  public static void testEveryFishDropsItsOwnCardOnFishing(GameTestHelper helper) {
    List<String> wrongFishingDrops = new ArrayList<>();
    withFishingConfig(
        true,
        1.0f,
        Set.of(),
        Set.of(),
        () -> {
          Player player = makeFishingPlayer(helper);
          for (Holder<Item> fish : BuiltInRegistries.ITEM.getTagOrEmpty(ItemTags.FISHES)) {
            ResourceLocation fishId = BuiltInRegistries.ITEM.getKey(fish.value());
            EntityType<?> expectedEntityType =
                BuiltInRegistries.ENTITY_TYPE.getOptional(fishId).orElse(null);
            EntityType<?> cardEntityType = fishCardEntityType(helper, player, fish.value());
            if (cardEntityType != expectedEntityType) {
              wrongFishingDrops.add(fishId + " -> " + cardEntityType);
            }
          }
          EntityType<?> junkCardEntityType = fishCardEntityType(helper, player, Items.STICK);
          if (junkCardEntityType != null) {
            wrongFishingDrops.add("minecraft:stick -> " + junkCardEntityType);
          }
        });

    GameTestHelpers.assertTrue(
        helper,
        "Fishing dropped no or the wrong mob capture card: " + wrongFishingDrops,
        wrongFishingDrops.isEmpty());
  }

  public static void testFishingCardDropRespectsConfiguration(GameTestHelper helper) {
    Player player = makeFishingPlayer(helper);
    withFishingConfig(
        false,
        1.0f,
        Set.of(),
        Set.of(),
        () -> assertFishingCard(helper, player, Items.COD, null, "Disabled fishing drops"));
    withFishingConfig(
        true,
        0.0f,
        Set.of(),
        Set.of(),
        () -> assertFishingCard(helper, player, Items.COD, null, "A fishing drop chance of 0.0"));
    withFishingConfig(
        true,
        1.0f,
        Set.of(),
        Set.of("minecraft:cod"),
        () -> assertFishingCard(helper, player, Items.COD, null, "A denied cod"));
    withFishingConfig(
        true,
        1.0f,
        Set.of("minecraft:salmon"),
        Set.of(),
        () -> {
          assertFishingCard(helper, player, Items.COD, null, "A cod missing in the allow list");
          assertFishingCard(helper, player, Items.SALMON, EntityType.SALMON, "An allowed salmon");
        });
  }

  private static void assertFishingCard(
      GameTestHelper helper,
      Player player,
      Item fish,
      EntityType<?> expectedEntityType,
      String scenario) {
    EntityType<?> cardEntityType = fishCardEntityType(helper, player, fish);
    GameTestHelpers.assertTrue(
        helper,
        scenario + " should drop a card for " + expectedEntityType + ", got: " + cardEntityType,
        cardEntityType == expectedEntityType);
  }

  private static void withFishingConfig(
      boolean dropOnFishing,
      float dropChance,
      Set<String> allowList,
      Set<String> denyList,
      Runnable fishingCheck) {
    boolean previousDropOnFishing = MobCaptureCardConfig.dropMobCaptureCardOnFishing;
    float previousDropChance = MobCaptureCardConfig.mobCaptureCardFishingDropChance;
    Set<String> previousAllowList = MobCaptureCardConfig.mobCaptureCardFishingDropAllowList;
    Set<String> previousDenyList = MobCaptureCardConfig.mobCaptureCardFishingDropDenyList;
    try {
      MobCaptureCardConfig.dropMobCaptureCardOnFishing = dropOnFishing;
      MobCaptureCardConfig.mobCaptureCardFishingDropChance = dropChance;
      MobCaptureCardConfig.mobCaptureCardFishingDropAllowList = allowList;
      MobCaptureCardConfig.mobCaptureCardFishingDropDenyList = denyList;
      fishingCheck.run();
    } finally {
      MobCaptureCardConfig.dropMobCaptureCardOnFishing = previousDropOnFishing;
      MobCaptureCardConfig.mobCaptureCardFishingDropChance = previousDropChance;
      MobCaptureCardConfig.mobCaptureCardFishingDropAllowList = previousAllowList;
      MobCaptureCardConfig.mobCaptureCardFishingDropDenyList = previousDenyList;
    }
  }

  private static Player makeFishingPlayer(GameTestHelper helper) {
    Player player = helper.makeMockPlayer(GameType.SURVIVAL);
    player.moveTo(Vec3.atBottomCenterOf(helper.absolutePos(SPAWN_POSITION)));
    return player;
  }

  private static EntityType<?> fishCardEntityType(GameTestHelper helper, Player player, Item fish) {
    FishingEvents.handleItemFishedEvent(player, List.of(new ItemStack(fish)));
    ItemStack droppedCard = collectDroppedCard(helper);
    if (droppedCard.isEmpty()) {
      return null;
    }

    return MobCaptureManager.getEntityType(droppedCard);
  }

  private static ItemStack killUntilCardDrops(GameTestHelper helper, EntityType<?> entityType) {
    if (!(entityType.create(helper.getLevel()) instanceof LivingEntity livingEntity)) {
      return ItemStack.EMPTY;
    }

    livingEntity.moveTo(Vec3.atBottomCenterOf(helper.absolutePos(SPAWN_POSITION)));
    DamageSource damageSource = helper.getLevel().damageSources().generic();
    try {
      for (int i = 0; i < MAX_KILLS_PER_MOB; i++) {
        LivingEntityEvents.handleLivingEntityDeathEvent(livingEntity, damageSource);
        ItemStack droppedCard = collectDroppedCard(helper);
        if (!droppedCard.isEmpty()) {
          return droppedCard;
        }
      }
      return ItemStack.EMPTY;
    } finally {
      livingEntity.discard();
    }
  }

  private static ItemStack collectDroppedCard(GameTestHelper helper) {
    AABB searchArea = new AABB(helper.absolutePos(SPAWN_POSITION)).inflate(2.0);
    ItemStack droppedCard = ItemStack.EMPTY;
    for (ItemEntity itemEntity :
        helper.getLevel().getEntitiesOfClass(ItemEntity.class, searchArea)) {
      if (droppedCard.isEmpty() && itemEntity.getItem().getItem() instanceof MobCaptureCardItem) {
        droppedCard = itemEntity.getItem().copy();
      }
      itemEntity.discard();
    }
    return droppedCard;
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
