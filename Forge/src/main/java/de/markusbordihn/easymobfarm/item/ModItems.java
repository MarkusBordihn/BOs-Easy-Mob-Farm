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
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.PollenTrapEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SpeedEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoFlowersFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.filter.NoMeatFilterItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.BigSlotUpgradeItem;
import de.markusbordihn.easymobfarm.item.upgrade.slot.SmallSlotUpgradeItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  public static final RegistryObject<Item> BLANK_MOB_CAPTURE_CARD =
      ITEMS.register(BlankMobCaptureCardItem.ID, () -> Items.BLANK_MOB_CAPTURE_CARD);

  public static final RegistryObject<Item> CREATIVE_MOB_CAPTURE_CARD =
      ITEMS.register(CreativeBlankMobCaptureCardItem.ID, () -> Items.CREATIVE_MOB_CAPTURE_CARD);

  public static final RegistryObject<Item> MOB_CAPTURE_CARD =
      ITEMS.register(MobCaptureCardItem.ID, () -> Items.MOB_CAPTURE_CARD);

  public static final RegistryObject<Item> CREATIVE_SPEED_ENHANCEMENT =
      ITEMS.register(CreativeSpeedEnhancementItem.ID, () -> Items.CREATIVE_SPEED_ENHANCEMENT);

  public static final RegistryObject<Item> EGG_COLLECTOR_ENHANCEMENT =
      ITEMS.register(EggCollectorEnhancementItem.ID, () -> Items.EGG_COLLECTOR_ENHANCEMENT);

  public static final RegistryObject<Item> EXPERIENCE_ENHANCEMENT =
      ITEMS.register(ExperienceEnhancementItem.ID, () -> Items.EXPERIENCE_ENHANCEMENT);

  public static final RegistryObject<Item> HONEY_EXTRACTOR_ENHANCEMENT =
      ITEMS.register(HoneyExtractorEnhancementItem.ID, () -> Items.HONEY_EXTRACTOR_ENHANCEMENT);

  public static final RegistryObject<Item> HONEY_HARVESTER_FRAME_ENHANCEMENT =
      ITEMS.register(
          HoneyHarvesterFrameEnhancementItem.ID, () -> Items.HONEY_HARVESTER_FRAME_ENHANCEMENT);

  public static final RegistryObject<Item> LOOT_ENHANCEMENT =
      ITEMS.register(LootEnhancementItem.ID, () -> Items.LOOT_ENHANCEMENT);

  public static final RegistryObject<Item> LUCK_ENHANCEMENT =
      ITEMS.register(LuckEnhancementItem.ID, () -> Items.LUCK_ENHANCEMENT);

  public static final RegistryObject<Item> POLLEN_TRAP_ENHANCEMENT =
      ITEMS.register(PollenTrapEnhancementItem.ID, () -> Items.POLLEN_TRAP_ENHANCEMENT);

  public static final RegistryObject<Item> SHEEP_ENHANCEMENT =
      ITEMS.register(SheepEnhancementItem.ID, () -> Items.SHEEP_ENHANCEMENT);

  public static final RegistryObject<Item> SPEED_ENHANCEMENT =
      ITEMS.register(SpeedEnhancementItem.ID, () -> Items.SPEED_ENHANCEMENT);

  public static final RegistryObject<Item> SWORD_ENHANCEMENT =
      ITEMS.register(SwordEnhancementItem.ID, () -> Items.SWORD_ENHANCEMENT);

  public static final RegistryObject<Item> NO_FLOWERS_FILTER =
      ITEMS.register(NoFlowersFilterItem.ID, () -> Items.NO_FLOWERS_FILTER);

  public static final RegistryObject<Item> NO_MEAT_FILTER =
      ITEMS.register(NoMeatFilterItem.ID, () -> Items.NO_MEAT_FILTER);

  public static final RegistryObject<Item> BIG_SLOT_UPGRADE =
      ITEMS.register(BigSlotUpgradeItem.ID, () -> Items.BIG_SLOT_UPGRADE);

  public static final RegistryObject<Item> SMALL_SLOT_UPGRADE =
      ITEMS.register(SmallSlotUpgradeItem.ID, () -> Items.SMALL_SLOT_UPGRADE);

  public static final RegistryObject<Item> CREATIVE_MOB_CATCHER =
      ITEMS.register(CreativeMobCatcherItem.ID, () -> Items.CREATIVE_MOB_CATCHER);

  public static final RegistryObject<Item> ENDURING_CAPTURE_NET =
      ITEMS.register(EnduringCaptureNetItem.ID, () -> Items.ENDURING_CAPTURE_NET);

  public static final RegistryObject<Item> IRONBOUND_CONTAINMENT_CAGE =
      ITEMS.register(IronboundContainmentCageItem.ID, () -> Items.IRONBOUND_CONTAINMENT_CAGE);

  public static final RegistryObject<Item> MYSTIC_BINDING_CRYSTAL =
      ITEMS.register(MysticBindingCrystalItem.ID, () -> Items.MYSTIC_BINDING_CRYSTAL);

  public static final RegistryObject<Item> VOID_BINDING_CHAIN =
      ITEMS.register(VoidBindingChainItem.ID, () -> Items.VOID_BINDING_CHAIN);

  protected ModItems() {}
}
