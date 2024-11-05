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

import net.minecraft.world.item.Item;

public class MysticBindingCrystalItem extends MobCatcherItem {

  public static final String ID = "mystic_binding_crystal";
  public static final int MAX_DURABILITY = 32;
  private static final float REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE = 0.25f;
  private static final float MAX_ENTITY_HEIGHT_TO_CAPTURE = 4f;
  private static final float MAX_ENTITY_WIDTH_TO_CAPTURE = 3f;

  public MysticBindingCrystalItem() {
    this(new Item.Properties());
  }

  public MysticBindingCrystalItem(Item.Properties properties) {
    super(properties.defaultDurability(MAX_DURABILITY));
  }

  @Override
  public float getRequiredHealthPercentageToCapture() {
    return REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE;
  }

  @Override
  public float getMaxEntityHeightToCapture() {
    return MAX_ENTITY_HEIGHT_TO_CAPTURE;
  }

  @Override
  public float getMaxEntityWidthToCapture() {
    return MAX_ENTITY_WIDTH_TO_CAPTURE;
  }
}
