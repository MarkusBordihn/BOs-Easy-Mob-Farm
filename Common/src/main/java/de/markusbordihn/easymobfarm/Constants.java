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

package de.markusbordihn.easymobfarm;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Constants {

  public static final String MOD_ID = "easy_mob_farm";
  public static final String MOD_NAME = "Easy Mob Farm";
  public static final String MOD_COMMAND = MOD_ID;
  public static final String MOD_PREFIX = MOD_ID + ".";
  public static final String TEXT_PREFIX = "text." + MOD_PREFIX;
  public static final String TEXT_CONFIG_PREFIX = TEXT_PREFIX + "config.";
  public static final String BLOCK_PREFIX = "block." + MOD_PREFIX;
  public static final String MINECRAFT_PREFIX = "minecraft";
  public static final String MOD_ID_PREFIX = MOD_ID + ":";
  public static final String TOOLTIP_PREFIX = "tooltip." + MOD_ID + ".";
  public static final String TOOLTIP_FARM_PREFIX = Constants.TOOLTIP_PREFIX + "farm.";
  public static final String LOG_NAME = MOD_NAME;
  public static final String LOG_REGISTER_PREFIX = "Register " + MOD_NAME;

  public static final int FONT_COLOR_DEFAULT = 4210752;
  public static final int FONT_COLOR_RED = 16733525;

  public static boolean EXPERIMENTAL_MODE = true;

  public static Path GAME_DIR = Paths.get("").toAbsolutePath();
  public static Path CONFIG_DIR = GAME_DIR.resolve("config");

  private Constants() {}
}
