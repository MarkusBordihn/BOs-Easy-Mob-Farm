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

package de.markusbordihn.easymobfarm.item.mobcapturecard;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MobCaptureCardIngredientItem extends Item {

  public static final String ID_PREFIX = "mob_capture_card_ingredient_";
  public static final String ID_FROG_COLD = ID_PREFIX + "frog_cold";
  public static final String ID_FROG_TEMPERATE = ID_PREFIX + "frog_temperate";
  public static final String ID_FROG_WARM = ID_PREFIX + "frog_warm";

  public MobCaptureCardIngredientItem(String id) {
    super(
        new Item.Properties()
            .setId(
                ResourceKey.create(
                    Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id))));
  }

  public MobCaptureCardIngredientItem(Properties properties) {
    super(properties);
  }

  public static ItemStack saveOriginalItemStack(
      ItemStack originalItemStack, ItemStack targetItemStack) {
    if (originalItemStack.has(DataComponents.MOB_CAPTURE_DATA)) {
      targetItemStack.set(
          DataComponents.MOB_CAPTURE_DATA, originalItemStack.get(DataComponents.MOB_CAPTURE_DATA));
    }
    return targetItemStack;
  }

  public static ItemStack restoreOriginalItemStack(ItemStack itemStack, ItemStack targetItemStack) {
    if (itemStack.has(DataComponents.MOB_CAPTURE_DATA)) {
      targetItemStack.set(
          DataComponents.MOB_CAPTURE_DATA, itemStack.get(DataComponents.MOB_CAPTURE_DATA));
    }
    return targetItemStack;
  }
}
