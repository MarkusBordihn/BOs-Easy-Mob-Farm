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

import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class CustomMobCaptureCards {

  private CustomMobCaptureCards() {}

  public static Set<ItemStack> getCustomMobCaptureCards(ItemLike mobCaptureCardItem) {
    if (!(mobCaptureCardItem instanceof MobCaptureCardItem)) {
      return Collections.emptySet();
    }

    // Extract custom mob capture cards from configuration.
    Set<ItemStack> result = new HashSet<>();
    MobCaptureCardModelsConfig.getMobCaptureCardModels()
        .forEach(
            modelKey -> {
              Object[] modelKeyData = MobCaptureCardModelsConfig.extractEntityKeyData(modelKey);
              if (modelKeyData[0] == null) {
                return;
              }
              EntityType<?> entityType =
                  Registry.ENTITY_TYPE.get(new ResourceLocation((String) modelKeyData[0]));
              ItemStack itemStack =
                  MobCaptureManager.createMobCaptureCard(
                      mobCaptureCardItem,
                      entityType,
                      (String) modelKeyData[1],
                      (DyeColor) modelKeyData[2]);
              if (itemStack != null) {
                result.add(itemStack);
              }
            });

    return result;
  }
}
