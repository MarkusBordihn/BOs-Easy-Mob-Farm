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

package de.markusbordihn.easymobfarm.client.screen.components;

import de.markusbordihn.easymobfarm.item.Items;
import de.markusbordihn.easymobfarm.menu.slots.CapturedMobSlot;
import de.markusbordihn.easymobfarm.menu.slots.EnhancementSlot;
import de.markusbordihn.easymobfarm.menu.slots.FilterSlot;
import de.markusbordihn.easymobfarm.menu.slots.SlotUpgradeSlot;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import net.minecraft.util.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class SlotHintRenderer {

  private static final int HINT_SIZE = 16;
  private static final int HINT_FADE_OVERLAY_COLOR = 0x9AFFFFFF;
  private static final List<ItemStack> CAPTURED_MOB_HINTS =
      hintStacks(
          Items.MOB_CAPTURE_CARD,
          net.minecraft.world.item.Items.PIG_SPAWN_EGG,
          Items.ENDURING_CAPTURE_NET,
          Items.IRONBOUND_CONTAINMENT_CAGE,
          Items.MYSTIC_BINDING_CRYSTAL,
          Items.VOID_BINDING_CHAIN);
  private static final List<ItemStack> ENHANCEMENT_HINTS =
      hintStacks(
          Items.SPEED_ENHANCEMENT,
          Items.LOOT_ENHANCEMENT,
          Items.LUCK_ENHANCEMENT,
          Items.EXPERIENCE_ENHANCEMENT,
          Items.SWORD_ENHANCEMENT,
          Items.KNIFE_ENHANCEMENT);
  private static final List<ItemStack> BEE_ENHANCEMENT_HINTS =
      entityEnhancementHintStacks(
          Items.HONEY_HARVESTER_FRAME_ENHANCEMENT,
          Items.HONEY_EXTRACTOR_ENHANCEMENT,
          Items.POLLEN_TRAP_ENHANCEMENT);
  private static final List<ItemStack> CHICKEN_ENHANCEMENT_HINTS =
      entityEnhancementHintStacks(Items.EGG_COLLECTOR_ENHANCEMENT);
  private static final List<ItemStack> COW_ENHANCEMENT_HINTS =
      entityEnhancementHintStacks(Items.MILK_EXTRACTOR_ENHANCEMENT);
  private static final List<ItemStack> SHEEP_ENHANCEMENT_HINTS =
      entityEnhancementHintStacks(Items.SHEEP_ENHANCEMENT);
  private static final List<ItemStack> FILTER_HINTS =
      hintStacks(Items.NO_MEAT_FILTER, Items.NO_FLOWERS_FILTER);
  private static final List<ItemStack> SLOT_UPGRADE_HINTS =
      hintStacks(Items.SMALL_SLOT_UPGRADE, Items.BIG_SLOT_UPGRADE);

  private final SlotHintCycle cycle =
      new SlotHintCycle(Util.getMillis(), ThreadLocalRandom.current().nextInt());

  private static List<ItemStack> getEnhancementHints(Entity entity) {
    if (entity instanceof Bee) {
      return BEE_ENHANCEMENT_HINTS;
    }
    if (entity instanceof Chicken) {
      return CHICKEN_ENHANCEMENT_HINTS;
    }
    if (entity instanceof Cow) {
      return COW_ENHANCEMENT_HINTS;
    }
    if (entity instanceof Sheep) {
      return SHEEP_ENHANCEMENT_HINTS;
    }
    return ENHANCEMENT_HINTS;
  }

  private static List<ItemStack> hintStacks(Item... items) {
    return Stream.of(items).map(ItemStack::new).toList();
  }

  private static List<ItemStack> entityEnhancementHintStacks(Item... entityItems) {
    return Stream.concat(hintStacks(entityItems).stream(), ENHANCEMENT_HINTS.stream()).toList();
  }

  public void render(
      GuiGraphics guiGraphics, List<Slot> slots, Entity entity, int leftPos, int topPos) {
    Set<HintGroup> occupiedGroups = HintGroup.occupiedGroups(slots);
    long now = Util.getMillis();
    for (int index = 0; index < slots.size(); index++) {
      Slot slot = slots.get(index);
      HintGroup group = HintGroup.of(slot);
      if (group == null || !slot.isActive() || slot.hasItem() || occupiedGroups.contains(group)) {
        continue;
      }

      List<ItemStack> hints = group.hints(entity);
      ItemStack hint = hints.get(this.cycle.index(index, hints.size(), now));
      int hintX = leftPos + slot.x;
      int hintY = topPos + slot.y;
      guiGraphics.renderFakeItem(hint, hintX, hintY);
      guiGraphics.fill(
          hintX, hintY, hintX + HINT_SIZE, hintY + HINT_SIZE, HINT_FADE_OVERLAY_COLOR);
    }
  }

  enum HintGroup {
    CAPTURED_MOB,
    ENHANCEMENT,
    FILTER,
    SLOT_UPGRADE;

    static Set<HintGroup> occupiedGroups(List<Slot> slots) {
      Set<HintGroup> groups = EnumSet.noneOf(HintGroup.class);
      for (Slot slot : slots) {
        HintGroup group = of(slot);
        if (group != null && slot.hasItem()) {
          groups.add(group);
        }
      }
      return groups;
    }

    private static HintGroup of(Slot slot) {
      if (slot instanceof CapturedMobSlot) {
        return CAPTURED_MOB;
      }
      if (slot instanceof EnhancementSlot) {
        return ENHANCEMENT;
      }
      if (slot instanceof FilterSlot) {
        return FILTER;
      }
      if (slot instanceof SlotUpgradeSlot) {
        return SLOT_UPGRADE;
      }
      return null;
    }

    private List<ItemStack> hints(Entity entity) {
      return switch (this) {
        case CAPTURED_MOB -> CAPTURED_MOB_HINTS;
        case ENHANCEMENT -> getEnhancementHints(entity);
        case FILTER -> FILTER_HINTS;
        case SLOT_UPGRADE -> SLOT_UPGRADE_HINTS;
      };
    }
  }
}
