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

package de.markusbordihn.easymobfarm.data.mobfarm;

public class MobFarmDataEntry {
  public static final int BLOCK_POS_X = 0;
  public static final int BLOCK_POS_Y = 1;
  public static final int BLOCK_POS_Z = 2;
  public static final int NUMBER_OF_OUTPUT_SLOTS = 3;
  public static final int FARM_PROGRESS = 4;
  public static final int FARM_STATUS = 5;
  public static final int FARM_TIER_LEVEL = 6;
  public static final int FARM_TYPE = 7;
  public static final int CAPTURED_MOB_EXPERIENCE = 8;

  private MobFarmDataEntry() {}
}
