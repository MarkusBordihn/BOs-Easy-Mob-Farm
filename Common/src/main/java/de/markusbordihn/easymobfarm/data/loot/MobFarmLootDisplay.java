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

package de.markusbordihn.easymobfarm.data.loot;

import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobFarmBonusConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmTierLevel;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.tabs.CustomMobCaptureCards;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;

public record MobFarmLootDisplay(
    EntityType<?> entityType,
    List<ItemStack> captureCards,
    List<ItemStack> spawnEggs,
    boolean requiresKilledByPlayer,
    boolean supportsKnifeEnhancement,
    List<BonusDrop> bonusDrops) {

  public static List<MobFarmLootDisplay> createAll(final ItemLike mobCaptureCardItem) {
    Map<EntityType<?>, List<ItemStack>> captureCardsByEntityType = new LinkedHashMap<>();
    for (ItemStack captureCard :
        CustomMobCaptureCards.getCustomMobCaptureCards(mobCaptureCardItem)) {
      MobCaptureData captureData = MobCaptureManager.getMobCaptureData(captureCard);
      if (captureData == null || captureData.entityType() == null) {
        continue;
      }

      captureCardsByEntityType
          .computeIfAbsent(captureData.entityType(), entityType -> new ArrayList<>())
          .add(captureCard);
    }

    List<MobFarmLootDisplay> displays = new ArrayList<>();
    captureCardsByEntityType.forEach(
        (entityType, captureCards) -> {
          MobCaptureCardDefinition definition = MobCaptureCardDefinitionManager.get(entityType);
          if (definition == null) {
            return;
          }

          displays.add(
              new MobFarmLootDisplay(
                  entityType,
                  List.copyOf(captureCards),
                  getSpawnEggs(entityType),
                  definition.requiresKilledByPlayer(),
                  definition.supportsKnifeEnhancement(),
                  getBonusDrops(entityType)));
        });
    return displays;
  }

  private static List<BonusDrop> getBonusDrops(final EntityType<?> entityType) {
    Map<String, ItemStack> itemsByKey = new LinkedHashMap<>();
    Map<String, List<MobFarmType>> mobFarmTypesByKey = new LinkedHashMap<>();
    for (MobFarmType mobFarmType : MobFarmBonusConfig.getMobFarmTypesWithBonusDrop(entityType)) {
      for (MobFarmTierLevel tierLevel : MobFarmTierLevel.values()) {
        for (ItemStack item :
            MobFarmBonusConfig.getBonusDropEntries(
                mobFarmType, tierLevel.getTierLevel(), entityType)) {
          String key = getItemKey(item);
          itemsByKey.putIfAbsent(key, item);
          List<MobFarmType> mobFarmTypes =
              mobFarmTypesByKey.computeIfAbsent(key, itemKey -> new ArrayList<>());
          if (!mobFarmTypes.contains(mobFarmType)) {
            mobFarmTypes.add(mobFarmType);
          }
        }
      }
    }

    List<BonusDrop> bonusDrops = new ArrayList<>();
    itemsByKey.forEach(
        (key, item) ->
            bonusDrops.add(new BonusDrop(item, List.copyOf(mobFarmTypesByKey.get(key)))));
    return bonusDrops;
  }

  private static String getItemKey(final ItemStack itemStack) {
    return BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString()
        + itemStack.getComponentsPatch();
  }

  private static List<ItemStack> getSpawnEggs(final EntityType<?> entityType) {
    ItemStack spawnEgg = SpawnEggs.BY_ENTITY_TYPE.get(entityType);
    return spawnEgg != null ? List.of(spawnEgg) : List.of();
  }

  public List<ItemStack> baseDrops() {
    return LootPreviewCache.getLootPreview(this.entityType);
  }

  public boolean hasBaseDrops() {
    return LootPreviewCache.hasLootPreview(this.entityType);
  }

  public List<ItemStack> inputItems() {
    List<ItemStack> inputItems = new ArrayList<>(this.captureCards);
    inputItems.addAll(this.spawnEggs);
    return inputItems;
  }

  public record BonusDrop(ItemStack item, List<MobFarmType> mobFarmTypes) {}

  private static final class SpawnEggs {

    private static final Map<EntityType<?>, ItemStack> BY_ENTITY_TYPE = byEntityType();

    private SpawnEggs() {}

    private static Map<EntityType<?>, ItemStack> byEntityType() {
      Map<EntityType<?>, ItemStack> spawnEggs = new HashMap<>();
      for (Item item : BuiltInRegistries.ITEM) {
        if (item instanceof SpawnEggItem spawnEggItem) {
          ItemStack spawnEgg = new ItemStack(item);
          EntityType<?> entityType = spawnEggItem.getType(spawnEgg);
          if (entityType != null) {
            spawnEggs.putIfAbsent(entityType, spawnEgg);
          }
        }
      }
      return Map.copyOf(spawnEggs);
    }
  }
}
