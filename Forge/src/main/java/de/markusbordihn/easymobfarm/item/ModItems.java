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
import de.markusbordihn.easymobfarm.data.enhancement.FrogCatalystType;
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
import de.markusbordihn.easymobfarm.tabs.ModTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

  public static final RegistryObject<Item> BLANK_MOB_CAPTURE_CARD =
      ITEMS.register(
          BlankMobCaptureCardItem.ID,
          () ->
              new BlankMobCaptureCardItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_CAPTURE_CARDS)));

  public static final RegistryObject<Item> CREATIVE_MOB_CAPTURE_CARD =
      ITEMS.register(
          CreativeBlankMobCaptureCardItem.ID,
          () ->
              new CreativeBlankMobCaptureCardItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_CAPTURE_CARDS)));

  public static final RegistryObject<Item> MOB_CAPTURE_CARD =
      ITEMS.register(MobCaptureCardItem.ID, MobCaptureCardItemWrapper::new);

  public static final RegistryObject<Item> CREATIVE_SPEED_ENHANCEMENT =
      ITEMS.register(
          CreativeSpeedEnhancementItem.ID,
          () ->
              new CreativeSpeedEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> EGG_COLLECTOR_ENHANCEMENT =
      ITEMS.register(
          EggCollectorEnhancementItem.ID,
          () ->
              new EggCollectorEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> EXPERIENCE_ENHANCEMENT =
      ITEMS.register(
          ExperienceEnhancementItem.ID,
          () ->
              new ExperienceEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_COLD_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_COLD,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.COLD, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_TEMPERATE_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_TEMPERATE,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.TEMPERATE,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_WARM_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_WARM,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.WARM, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_WHITE_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_WHITE,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.WHITE,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_ORANGE_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_ORANGE,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.ORANGE,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_MAGENTA_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_MAGENTA,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.MAGENTA,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_LIGHT_BLUE_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_LIGHT_BLUE,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.LIGHT_BLUE,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_YELLOW_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_YELLOW,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.YELLOW,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_LIME_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_LIME,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.LIME, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_PINK_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_PINK,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.PINK, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_GRAY_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_GRAY,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.GRAY, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_LIGHT_GRAY_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_LIGHT_GRAY,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.LIGHT_GRAY,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_CYAN_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_CYAN,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.CYAN, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_PURPLE_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_PURPLE,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.PURPLE,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_BLUE_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_BLUE,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.BLUE, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_BROWN_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_BROWN,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.BROWN,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_GREEN_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_GREEN,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.GREEN,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_RED_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_RED,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.RED, new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> FROG_CATALYST_BLACK_ENHANCEMENT =
      ITEMS.register(
          FrogCatalystEnhancementItem.ID_BLACK,
          () ->
              new FrogCatalystEnhancementItem(
                  FrogCatalystType.BLACK,
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> HONEY_EXTRACTOR_ENHANCEMENT =
      ITEMS.register(
          HoneyExtractorEnhancementItem.ID,
          () ->
              new HoneyExtractorEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> HONEY_HARVESTER_FRAME_ENHANCEMENT =
      ITEMS.register(
          HoneyHarvesterFrameEnhancementItem.ID,
          () ->
              new HoneyHarvesterFrameEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> LOOT_ENHANCEMENT =
      ITEMS.register(
          LootEnhancementItem.ID,
          () -> new LootEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> LUCK_ENHANCEMENT =
      ITEMS.register(
          LuckEnhancementItem.ID,
          () -> new LuckEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> MILK_EXTRACTOR_ENHANCEMENT =
      ITEMS.register(
          MilkExtractorEnhancementItem.ID,
          () ->
              new MilkExtractorEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> POLLEN_TRAP_ENHANCEMENT =
      ITEMS.register(
          PollenTrapEnhancementItem.ID,
          () ->
              new PollenTrapEnhancementItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SHEEP_ENHANCEMENT =
      ITEMS.register(
          SheepEnhancementItem.ID,
          () -> new SheepEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SPEED_ENHANCEMENT =
      ITEMS.register(
          SpeedEnhancementItem.ID,
          () -> new SpeedEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SWORD_ENHANCEMENT =
      ITEMS.register(
          SwordEnhancementItem.ID,
          () -> new SwordEnhancementItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> NO_FLOWERS_FILTER =
      ITEMS.register(
          NoFlowersFilterItem.ID,
          () -> new NoFlowersFilterItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> NO_MEAT_FILTER =
      ITEMS.register(
          NoMeatFilterItem.ID,
          () -> new NoMeatFilterItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> BIG_SLOT_UPGRADE =
      ITEMS.register(
          BigSlotUpgradeItem.ID,
          () -> new BigSlotUpgradeItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> SMALL_SLOT_UPGRADE =
      ITEMS.register(
          SmallSlotUpgradeItem.ID,
          () -> new SmallSlotUpgradeItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_UPGRADES)));

  public static final RegistryObject<Item> CREATIVE_MOB_CATCHER =
      ITEMS.register(
          CreativeMobCatcherItem.ID,
          () ->
              new CreativeMobCatcherItem(
                  new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER).stacksTo(1)));

  public static final RegistryObject<Item> ENDURING_CAPTURE_NET =
      ITEMS.register(
          EnduringCaptureNetItem.ID,
          () -> new EnduringCaptureNetItem(new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER)));

  public static final RegistryObject<Item> IRONBOUND_CONTAINMENT_CAGE =
      ITEMS.register(
          IronboundContainmentCageItem.ID,
          () ->
              new IronboundContainmentCageItem(new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER)));

  public static final RegistryObject<Item> MYSTIC_BINDING_CRYSTAL =
      ITEMS.register(
          MysticBindingCrystalItem.ID,
          () -> new MysticBindingCrystalItem(new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER)));

  public static final RegistryObject<Item> VOID_BINDING_CHAIN =
      ITEMS.register(
          VoidBindingChainItem.ID,
          () -> new VoidBindingChainItem(new Item.Properties().tab(ModTabs.TAB_MOB_CATCHER)));

  public static final RegistryObject<Item> MILK_BOTTLE =
      ITEMS.register(
          MilkBottleItem.ID,
          () -> new MilkBottleItem(new Item.Properties().tab(ModTabs.TAB_MOB_FARM_CONSUMABLES)));

  protected ModItems() {}
}
