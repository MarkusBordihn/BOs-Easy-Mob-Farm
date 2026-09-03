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

import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.loot.LootManager;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class LootManagerTestHelper {

  private static final int SPECIAL_DROP_RUNS = 200;
  private static final int LOOT_ENHANCEMENT_COUNT = 4;

  private LootManagerTestHelper() {}

  public static void testHoneyExtractorConvertsBonusHoneycomb(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));
    List<EnhancementItem> enhancements =
        List.of(
            (EnhancementItem) de.markusbordihn.easymobfarm.item.Items.HONEY_EXTRACTOR_ENHANCEMENT);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityType.BEE, null);

    GameTestHelpers.assertTrue(
        helper,
        "Honey Extractor should convert bonus honeycomb to honey bottle, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEY_BOTTLE));
  }

  public static void testBonusHoneycombPassesThroughWithoutExtractor(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.BEE, null);

    GameTestHelpers.assertTrue(
        helper,
        "Bonus honeycomb should pass through without extractor, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEYCOMB));
  }

  public static void testHoneyExtractorDoesNotConvertForNonBee(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));
    List<EnhancementItem> enhancements =
        List.of(
            (EnhancementItem) de.markusbordihn.easymobfarm.item.Items.HONEY_EXTRACTOR_ENHANCEMENT);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityType.COW, null);

    GameTestHelpers.assertTrue(
        helper,
        "Honey Extractor should not convert honeycombs for non-bee entities, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEYCOMB));
  }

  public static void testWitherSpecialDropsAreNotMultiplied(GameTestHelper helper) {
    ServerLevel serverLevel = helper.getLevel();
    Entity wither = EntityType.WITHER.create(serverLevel);
    if (wither == null) {
      helper.fail("Unable to create a Wither for the special drop test.");
      return;
    }

    List<EnhancementItem> enhancements =
        Collections.nCopies(
            LOOT_ENHANCEMENT_COUNT,
            (EnhancementItem) de.markusbordihn.easymobfarm.item.Items.LOOT_ENHANCEMENT);
    int maxNetherStars = 0;
    int maxWitherRoses = 0;
    int runsWithNetherStar = 0;
    try {
      for (int run = 0; run < SPECIAL_DROP_RUNS; run++) {
        NonNullList<ItemStack> drops = LootManager.getEntityLoot(wither, enhancements, serverLevel);
        int netherStars = countItems(drops, Items.NETHER_STAR);
        maxNetherStars = Math.max(maxNetherStars, netherStars);
        maxWitherRoses = Math.max(maxWitherRoses, countItems(drops, Items.WITHER_ROSE));
        if (netherStars > 0) {
          runsWithNetherStar++;
        }
      }
    } finally {
      wither.discard();
    }

    GameTestHelpers.assertTrue(
        helper,
        "Wither special drops must happen but not scale with loot enhancements, got up to "
            + maxNetherStars
            + " nether stars and "
            + maxWitherRoses
            + " wither roses in "
            + runsWithNetherStar
            + " of "
            + SPECIAL_DROP_RUNS
            + " runs",
        runsWithNetherStar > 0 && maxNetherStars <= 1 && maxWitherRoses <= 1);
  }

  public static void testMalformedCaptureDataDoesNotThrow(GameTestHelper helper) {
    CompoundTag entityData = new CompoundTag();
    entityData.putString("id", "minecraft:cow");
    entityData.put("Pos", invalidPositionTag());
    MobCaptureData mobCaptureData =
        new MobCaptureData(
            "Cow", "minecraft:cow", EntityType.COW, entityData, null, null, Rarity.COMMON, false);

    NonNullList<ItemStack> drops =
        LootManager.getEntityLoot(mobCaptureData, List.of(), helper.getLevel());

    GameTestHelpers.assertTrue(
        helper,
        "Malformed mob capture data must return empty loot instead of throwing.",
        drops != null && drops.isEmpty());
  }

  private static ListTag invalidPositionTag() {
    ListTag positionTag = new ListTag();
    positionTag.add(DoubleTag.valueOf(Double.NaN));
    positionTag.add(DoubleTag.valueOf(Double.NaN));
    positionTag.add(DoubleTag.valueOf(Double.NaN));
    return positionTag;
  }

  private static int countItems(NonNullList<ItemStack> drops, Item item) {
    int count = 0;
    for (ItemStack itemStack : drops) {
      if (itemStack.is(item)) {
        count += itemStack.getCount();
      }
    }
    return count;
  }
}
