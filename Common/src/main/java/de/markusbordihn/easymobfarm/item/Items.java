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

package de.markusbordihn.easymobfarm.item;

import de.markusbordihn.easymobfarm.item.consumables.MilkBottleItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.BlankMobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.CreativeBlankMobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.CreativeMobCatcherItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.EnduringCaptureNetItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.IronboundContainmentCageItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.MysticBindingCrystalItem;
import de.markusbordihn.easymobfarm.item.mobcatcher.VoidBindingChainItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.CreativeSpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.EggCollectorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyHarvesterFrameEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LootEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.LuckEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.MilkExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.PollenTrapEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoFlowersFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoMeatFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.BigSlotUpgradeItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.SmallSlotUpgradeItem;
import net.minecraft.world.item.Item;

public class Items {

  public static final Item BLANK_MOB_CAPTURE_CARD = new BlankMobCaptureCardItem();
  public static final Item CREATIVE_MOB_CAPTURE_CARD = new CreativeBlankMobCaptureCardItem();
  public static final Item MOB_CAPTURE_CARD = new MobCaptureCardItem();

  public static final Item CREATIVE_SPEED_ENHANCEMENT = new CreativeSpeedEnhancementItem();
  public static final Item EGG_COLLECTOR_ENHANCEMENT = new EggCollectorEnhancementItem();
  public static final Item EXPERIENCE_ENHANCEMENT = new ExperienceEnhancementItem();
  public static final Item HONEY_EXTRACTOR_ENHANCEMENT = new HoneyExtractorEnhancementItem();
  public static final Item HONEY_HARVESTER_FRAME_ENHANCEMENT =
      new HoneyHarvesterFrameEnhancementItem();
  public static final Item LOOT_ENHANCEMENT = new LootEnhancementItem();
  public static final Item LUCK_ENHANCEMENT = new LuckEnhancementItem();
  public static final Item MILK_EXTRACTOR_ENHANCEMENT = new MilkExtractorEnhancementItem();
  public static final Item POLLEN_TRAP_ENHANCEMENT = new PollenTrapEnhancementItem();
  public static final Item SHEEP_ENHANCEMENT = new SheepEnhancementItem();
  public static final Item SPEED_ENHANCEMENT = new SpeedEnhancementItem();
  public static final Item SWORD_ENHANCEMENT = new SwordEnhancementItem();

  public static final Item NO_FLOWERS_FILTER = new NoFlowersFilterItem();
  public static final Item NO_MEAT_FILTER = new NoMeatFilterItem();

  public static final Item BIG_SLOT_UPGRADE = new BigSlotUpgradeItem();
  public static final Item SMALL_SLOT_UPGRADE = new SmallSlotUpgradeItem();

  public static final Item CREATIVE_MOB_CATCHER = new CreativeMobCatcherItem();
  public static final Item ENDURING_CAPTURE_NET = new EnduringCaptureNetItem();
  public static final Item IRONBOUND_CONTAINMENT_CAGE = new IronboundContainmentCageItem();
  public static final Item MYSTIC_BINDING_CRYSTAL = new MysticBindingCrystalItem();
  public static final Item VOID_BINDING_CHAIN = new VoidBindingChainItem();

  public static final Item MILK_BOTTLE = new MilkBottleItem();

  private Items() {}
}
