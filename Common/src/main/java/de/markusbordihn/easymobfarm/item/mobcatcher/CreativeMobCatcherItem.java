/*
 * Copyright 2022 Markus Bordihn
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

package de.markusbordihn.easymobfarm.item.mobcatcher;

import de.markusbordihn.easymobfarm.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class CreativeMobCatcherItem extends MobCatcherItem {

  public static final String ID = "creative_mob_catcher";

  public CreativeMobCatcherItem() {
    this(new Item.Properties());
  }

  public CreativeMobCatcherItem(Item.Properties properties) {
    super(
        properties.setId(
            ResourceKey.create(
                Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, ID))));
  }

  @Override
  public float getRequiredHealthPercentageToCapture() {
    return 0.0f;
  }

  @Override
  public float getMaxEntityHeightToCapture() {
    return 0.0f;
  }

  @Override
  public float getMaxEntityWidthToCapture() {
    return 0.0f;
  }

  @Override
  public int getItemDamageOnUse() {
    return 0;
  }
}
