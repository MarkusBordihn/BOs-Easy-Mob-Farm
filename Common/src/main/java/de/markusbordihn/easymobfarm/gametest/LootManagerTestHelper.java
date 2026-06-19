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

import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.loot.LootManager;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LootManagerTestHelper {

  private LootManagerTestHelper() {}

  public static void testHoneyExtractorConvertsBonusHoneycomb(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));
    List<EnhancementItem> enhancements =
        List.of(
            (EnhancementItem) de.markusbordihn.easymobfarm.item.Items.HONEY_EXTRACTOR_ENHANCEMENT);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityTypes.BEE);

    GameTestHelpers.assertTrue(
        helper,
        "Honey Extractor should convert bonus honeycomb to honey bottle, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEY_BOTTLE));
  }

  public static void testBonusHoneycombPassesThroughWithoutExtractor(GameTestHelper helper) {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityTypes.BEE);

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

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityTypes.COW);

    GameTestHelpers.assertTrue(
        helper,
        "Honey Extractor should not convert honeycombs for non-bee entities, got: "
            + (drops.isEmpty() ? "empty" : drops.get(0).getItem()),
        !drops.isEmpty() && drops.get(0).is(Items.HONEYCOMB));
  }
}
