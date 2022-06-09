/**
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

package de.markusbordihn.easymobfarm;

import net.minecraft.ChatFormatting;

public final class Constants {

  protected Constants() {}

  // General Mod definitions
  public static final String LOG_NAME = "Bo's Easy Mob Farm";
  public static final String LOG_REGISTER_PREFIX = "🪤 Register " + LOG_NAME;
  public static final String LOG_MOB_FARM_PREFIX = "🪤 Configure " + LOG_NAME;
  public static final String MOD_COMMAND = "easy_mob_farm";
  public static final String MOD_ID = "easy_mob_farm";
  public static final String MOD_NAME = "Bo's Easy Mob Farm";

  // Prefixes
  public static final String TEXT_PREFIX = "text.easy_mob_farm.";
  public static final String MESSAGE_PREFIX = "message.easy_mob_farm.";

  // Colors
  public static final int FONT_COLOR_BLACK = ChatFormatting.BLACK.getColor();
  public static final int FONT_COLOR_DARK_GREEN = ChatFormatting.DARK_GREEN.getColor();
  public static final int FONT_COLOR_GRAY = ChatFormatting.GRAY.getColor();
  public static final int FONT_COLOR_GREEN = ChatFormatting.GREEN.getColor();
  public static final int FONT_COLOR_WARNING = ChatFormatting.RED.getColor();
  public static final int FONT_COLOR_YELLOW = ChatFormatting.YELLOW.getColor();
}
