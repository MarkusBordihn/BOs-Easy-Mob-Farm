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

package de.markusbordihn.easymobfarm.data.mobfarm;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class MobFarmSlots {

  public static final List<MobFarmSlot> ENHANCEMENT_ITEM_SLOTS =
      new ArrayList<>(
          EnumSet.of(
              MobFarmSlot.UPGRADE_ITEM_1,
              MobFarmSlot.UPGRADE_ITEM_2,
              MobFarmSlot.UPGRADE_ITEM_3,
              MobFarmSlot.UPGRADE_ITEM_4));

  public static final List<MobFarmSlot> FILTER_ITEM_SLOTS =
      new ArrayList<>(
          EnumSet.of(
              MobFarmSlot.FILTER_ITEM_1,
              MobFarmSlot.FILTER_ITEM_2,
              MobFarmSlot.FILTER_ITEM_3,
              MobFarmSlot.FILTER_ITEM_4,
              MobFarmSlot.FILTER_ITEM_5,
              MobFarmSlot.FILTER_ITEM_6));

  public static final List<MobFarmSlot> SLOT_UPGRADE_ITEM_SLOTS =
      new ArrayList<>(
          EnumSet.of(
              MobFarmSlot.SLOT_UPGRADE_ITEM_1,
              MobFarmSlot.SLOT_UPGRADE_ITEM_2,
              MobFarmSlot.SLOT_UPGRADE_ITEM_3));

  public static final List<MobFarmSlot> RESULT_SLOTS =
      new ArrayList<>(
          EnumSet.of(
              MobFarmSlot.RESULT_1,
              MobFarmSlot.RESULT_2,
              MobFarmSlot.RESULT_3,
              MobFarmSlot.RESULT_4,
              MobFarmSlot.RESULT_5,
              MobFarmSlot.RESULT_6,
              MobFarmSlot.RESULT_7,
              MobFarmSlot.RESULT_8,
              MobFarmSlot.RESULT_9,
              MobFarmSlot.RESULT_10,
              MobFarmSlot.RESULT_11,
              MobFarmSlot.RESULT_12,
              MobFarmSlot.RESULT_13,
              MobFarmSlot.RESULT_14,
              MobFarmSlot.RESULT_15,
              MobFarmSlot.RESULT_16,
              MobFarmSlot.RESULT_17,
              MobFarmSlot.RESULT_18,
              MobFarmSlot.RESULT_19,
              MobFarmSlot.RESULT_20,
              MobFarmSlot.RESULT_21,
              MobFarmSlot.RESULT_22,
              MobFarmSlot.RESULT_23,
              MobFarmSlot.RESULT_24,
              MobFarmSlot.RESULT_25,
              MobFarmSlot.RESULT_26,
              MobFarmSlot.RESULT_27));

  private MobFarmSlots() {}
}
