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

import de.markusbordihn.easymobfarm.Constants;
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
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.FrogCatalystEnhancementItem;
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
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModItems {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private ModItems() {}

  public static void registerModItems() {
    log.info("{} Mob Capture Card items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(BlankMobCaptureCardItem.ID, Items.BLANK_MOB_CAPTURE_CARD);
    registerItem(CreativeBlankMobCaptureCardItem.ID, Items.CREATIVE_MOB_CAPTURE_CARD);
    registerItem(MobCaptureCardItem.ID, Items.MOB_CAPTURE_CARD);

    log.info("{} Enhancement items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(CreativeSpeedEnhancementItem.ID, Items.CREATIVE_SPEED_ENHANCEMENT);
    registerItem(EggCollectorEnhancementItem.ID, Items.EGG_COLLECTOR_ENHANCEMENT);
    registerItem(ExperienceEnhancementItem.ID, Items.EXPERIENCE_ENHANCEMENT);
    registerItem(FrogCatalystEnhancementItem.ID_COLD, Items.FROG_CATALYST_COLD_ENHANCEMENT);
    registerItem(
        FrogCatalystEnhancementItem.ID_TEMPERATE, Items.FROG_CATALYST_TEMPERATE_ENHANCEMENT);
    registerItem(FrogCatalystEnhancementItem.ID_WARM, Items.FROG_CATALYST_WARM_ENHANCEMENT);
    registerItem(HoneyExtractorEnhancementItem.ID, Items.HONEY_EXTRACTOR_ENHANCEMENT);
    registerItem(HoneyHarvesterFrameEnhancementItem.ID, Items.HONEY_HARVESTER_FRAME_ENHANCEMENT);
    registerItem(LootEnhancementItem.ID, Items.LOOT_ENHANCEMENT);
    registerItem(LuckEnhancementItem.ID, Items.LUCK_ENHANCEMENT);
    registerItem(MilkExtractorEnhancementItem.ID, Items.MILK_EXTRACTOR_ENHANCEMENT);
    registerItem(PollenTrapEnhancementItem.ID, Items.POLLEN_TRAP_ENHANCEMENT);
    registerItem(SheepEnhancementItem.ID, Items.SHEEP_ENHANCEMENT);
    registerItem(SpeedEnhancementItem.ID, Items.SPEED_ENHANCEMENT);
    registerItem(SwordEnhancementItem.ID, Items.SWORD_ENHANCEMENT);

    log.info("{} Filter items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(NoFlowersFilterItem.ID, Items.NO_FLOWERS_FILTER);
    registerItem(NoMeatFilterItem.ID, Items.NO_MEAT_FILTER);

    log.info("{} Slot upgrade items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(BigSlotUpgradeItem.ID, Items.BIG_SLOT_UPGRADE);
    registerItem(SmallSlotUpgradeItem.ID, Items.SMALL_SLOT_UPGRADE);

    log.info("{} Mob Catcher items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(CreativeMobCatcherItem.ID, Items.CREATIVE_MOB_CATCHER);
    registerItem(EnduringCaptureNetItem.ID, Items.ENDURING_CAPTURE_NET);
    registerItem(IronboundContainmentCageItem.ID, Items.IRONBOUND_CONTAINMENT_CAGE);
    registerItem(MysticBindingCrystalItem.ID, Items.MYSTIC_BINDING_CRYSTAL);
    registerItem(VoidBindingChainItem.ID, Items.VOID_BINDING_CHAIN);

    log.info("{} Consumables items ...", Constants.LOG_REGISTER_PREFIX);
    registerItem(MilkBottleItem.ID, Items.MILK_BOTTLE);
  }

  private static void registerItem(final String id, final Item item) {
    Registry.register(Registry.ITEM, Constants.MOD_ID + ":" + id, item);
  }
}
