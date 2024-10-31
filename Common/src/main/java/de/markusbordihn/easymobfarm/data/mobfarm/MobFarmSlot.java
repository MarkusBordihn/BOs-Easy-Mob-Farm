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

public enum MobFarmSlot {
  CAPTURED_MOB(0),
  UPGRADE_ITEM_1(4),
  UPGRADE_ITEM_2(5),
  UPGRADE_ITEM_3(6),
  UPGRADE_ITEM_4(7),
  FILTER_ITEM_1(10),
  FILTER_ITEM_2(11),
  FILTER_ITEM_3(12),
  FILTER_ITEM_4(13),
  FILTER_ITEM_5(14),
  FILTER_ITEM_6(15),
  SLOT_UPGRADE_ITEM_1(20),
  SLOT_UPGRADE_ITEM_2(21),
  SLOT_UPGRADE_ITEM_3(22),
  RESULT_1(25),
  RESULT_2(26),
  RESULT_3(27),
  RESULT_4(28),
  RESULT_5(29),
  RESULT_6(30),
  RESULT_7(31),
  RESULT_8(32),
  RESULT_9(33),
  RESULT_10(34),
  RESULT_11(35),
  RESULT_12(36),
  RESULT_13(37),
  RESULT_14(38),
  RESULT_15(39),
  RESULT_16(40),
  RESULT_17(41),
  RESULT_18(42),
  RESULT_19(43),
  RESULT_20(44),
  RESULT_21(45),
  RESULT_22(46),
  RESULT_23(47),
  RESULT_24(48),
  RESULT_25(49),
  RESULT_26(50),
  RESULT_27(51);

  private final int slotIndex;

  MobFarmSlot(final int slotIndex) {
    this.slotIndex = slotIndex;
  }

  public int index() {
    return slotIndex;
  }
}
