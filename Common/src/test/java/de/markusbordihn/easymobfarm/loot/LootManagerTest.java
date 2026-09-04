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

package de.markusbordihn.easymobfarm.loot;

import de.markusbordihn.easymobfarm.TestBootstrap;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyExtractorEnhancementItem;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

class LootManagerTest {

  private static final HoneyExtractorEnhancementItem HONEY_EXTRACTOR;

  static {
    TestBootstrap.bootstrapWithBoundItemComponents();
    // The item registry is frozen after Bootstrap, so new Item instances cannot be created
    // via the normal constructor (createIntrusiveHolder would fail). Use Unsafe to allocate
    // an instance without invoking any constructor — sufficient for instanceof checks.
    try {
      Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
      unsafeField.setAccessible(true);
      Unsafe unsafe = (Unsafe) unsafeField.get(null);
      HONEY_EXTRACTOR =
          (HoneyExtractorEnhancementItem)
              unsafe.allocateInstance(HoneyExtractorEnhancementItem.class);
    } catch (ReflectiveOperationException e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  @Test
  void honeyExtractorConvertsBonusHoneycomb() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));
    List<EnhancementItem> enhancements = List.of(HONEY_EXTRACTOR);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityType.BEE, null);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.HONEY_BOTTLE));
  }

  @Test
  void bonusHoneycombPassesThroughWithoutExtractor() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.BEE, null);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.HONEYCOMB));
  }

  @Test
  void honeyExtractorDoesNotConvertForNonBee() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.HONEYCOMB));
    List<EnhancementItem> enhancements = List.of(HONEY_EXTRACTOR);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityType.COW, null);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.HONEYCOMB));
  }

  @Test
  void emptyBonusDropsProducesNoDrops() {
    NonNullList<ItemStack> drops = NonNullList.create();

    LootManager.addBonusDrops(drops, List.of(), List.of(HONEY_EXTRACTOR), EntityType.BEE, null);

    Assertions.assertTrue(drops.isEmpty());
  }

  @Test
  void nonHoneycombItemPassesThroughWithExtractor() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.STICK));
    List<EnhancementItem> enhancements = List.of(HONEY_EXTRACTOR);

    LootManager.addBonusDrops(drops, bonusDrops, enhancements, EntityType.BEE, null);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.STICK));
  }

  @Test
  void sheepColorReplacesBonusWhiteWool() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.WHITE_WOOL, 3));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.SHEEP, DyeColor.BLACK);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.BLACK_WOOL));
    Assertions.assertEquals(3, drops.get(0).getCount());
  }

  @Test
  void sheepWithoutColorKeepsBonusWhiteWool() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.WHITE_WOOL));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.SHEEP, null);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.WHITE_WOOL));
  }

  @Test
  void sheepColorKeepsExplicitlyConfiguredWoolColor() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.BLACK_WOOL));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.SHEEP, DyeColor.RED);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.BLACK_WOOL));
  }

  @Test
  void sheepColorKeepsNonWoolBonusDrops() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.STICK));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.SHEEP, DyeColor.BLACK);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.STICK));
  }

  @Test
  void colorDoesNotReplaceWhiteWoolForNonSheep() {
    NonNullList<ItemStack> drops = NonNullList.create();
    List<ItemStack> bonusDrops = List.of(new ItemStack(Items.WHITE_WOOL));

    LootManager.addBonusDrops(drops, bonusDrops, List.of(), EntityType.COW, DyeColor.BLACK);

    Assertions.assertEquals(1, drops.size());
    Assertions.assertTrue(drops.get(0).is(Items.WHITE_WOOL));
  }
}
