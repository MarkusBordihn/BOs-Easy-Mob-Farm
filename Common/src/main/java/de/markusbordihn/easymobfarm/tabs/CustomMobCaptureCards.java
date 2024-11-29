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

package de.markusbordihn.easymobfarm.tabs;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomMobCaptureCards {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private CustomMobCaptureCards() {}

  public static Set<ItemStack> getCustomMobCaptureCards(ItemLike mobCaptureCardItem) {
    if (!(mobCaptureCardItem instanceof MobCaptureCardItem)) {
      return Collections.emptySet();
    }
    Set<ItemStack> result = new LinkedHashSet<>();

    // Add default mob capture cards.
    result.add(
        MobCaptureManager.createMobCaptureCard(
            mobCaptureCardItem,
            new MobCaptureData("Common Card", EntityType.ARMOR_STAND, Rarity.COMMON)));
    result.add(
        MobCaptureManager.createMobCaptureCard(
            mobCaptureCardItem,
            new MobCaptureData("Uncommon Card", EntityType.ARMOR_STAND, Rarity.UNCOMMON)));
    result.add(
        MobCaptureManager.createMobCaptureCard(
            mobCaptureCardItem,
            new MobCaptureData("Rare Card", EntityType.ARMOR_STAND, Rarity.RARE)));
    result.add(
        MobCaptureManager.createMobCaptureCard(
            mobCaptureCardItem,
            new MobCaptureData("Epic Card", EntityType.ARMOR_STAND, Rarity.EPIC)));

    // Extract custom mob capture cards from configuration.
    MobCaptureCardModelsConfig.getMobCaptureCardModels()
        .forEach(
            modelKey -> {
              Object[] modelKeyData = MobCaptureCardModelsConfig.extractEntityKeyData(modelKey);
              if (modelKeyData[0] == null) {
                return;
              }
              String entityName = (String) modelKeyData[0];
              if (entityName.isEmpty()) {
                return;
              }
              Optional<EntityType<?>> entityType =
                  BuiltInRegistries.ENTITY_TYPE.getOptional(new ResourceLocation(entityName));
              if (entityType.isEmpty()) {
                if (entityName.startsWith("minecraft:")) {
                  log.error("Unknown entity type {} for mob capture card!", entityName);
                } else {
                  log.warn("Unknown entity type {} for mob capture card!", entityName);
                }
                return;
              }
              ItemStack itemStack =
                  MobCaptureManager.createMobCaptureCard(
                      mobCaptureCardItem,
                      entityType.get(),
                      (String) modelKeyData[1],
                      (DyeColor) modelKeyData[2]);
              if (itemStack != null) {
                result.add(itemStack);
              }
            });

    return result;
  }
}
