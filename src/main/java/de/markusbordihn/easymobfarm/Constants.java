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

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;

public final class Constants {

  protected Constants() {}

  // General Mod definitions
  public static final String LOG_NAME = "Bo's Easy Mob Farm";
  public static final String LOG_REGISTER_PREFIX = "🪤 Register " + LOG_NAME;
  public static final String LOG_MOB_FARM_PREFIX = "🪤 Configure " + LOG_NAME;
  public static final String MOD_COMMAND = "easy_mob_farm";
  public static final String MOD_ID = "easy_mob_farm";
  public static final String MOD_NAME = "Bo's Easy Mob Farm";
  public static final String MOD_URL = "https://www.curseforge.com/minecraft/mc-mods/easy-mob-farm";

  // Prefixes
  public static final String LOOT_MANAGER_PREFIX = "[Loot Manager] ";
  public static final String MESSAGE_PREFIX = "message.easy_mob_farm.";
  public static final String MINECRAFT_PREFIX = "minecraft";
  public static final String TEXT_PREFIX = "text.easy_mob_farm.";
  public static final String BLOCK_TEXT_PREFIX = "block.easy_mob_farm.";

  // Colors
  public static final int FONT_COLOR_BLACK = 0;
  public static final int FONT_COLOR_DARK_GREEN = 43520;
  public static final int FONT_COLOR_GRAY = 11184810;
  public static final int FONT_COLOR_GREEN = 5635925;
  public static final int FONT_COLOR_RED = 16733525;
  public static final int FONT_COLOR_WARNING = FONT_COLOR_RED;
  public static final int FONT_COLOR_YELLOW = 16777045;
  public static final int FONT_COLOR_WHITE = 16777215;

  // Textures
  public static final ResourceLocation TEXTURE_GENERIC_54 =
      new ResourceLocation(MINECRAFT_PREFIX, "textures/gui/container/generic_54.png");
  public static final ResourceLocation TEXTURE_HOPPER =
      new ResourceLocation(MINECRAFT_PREFIX, "textures/gui/container/hopper.png");
  public static final ResourceLocation TEXTURE_HORSE =
      new ResourceLocation(MINECRAFT_PREFIX, "textures/gui/container/horse.png");
  public static final ResourceLocation TEXTURE_ICONS =
      new ResourceLocation(Constants.MOD_ID, "textures/container/icons.png");
  public static final ResourceLocation TEXTURE_INVENTORY =
      new ResourceLocation(MINECRAFT_PREFIX, "textures/gui/container/inventory.png");

  // Supported Mods
  public static final boolean CORAIL_SPAWNERS_LOADED = ModList.get().isLoaded("corail_spawners");
  public static final boolean CREATE_LOADED = ModList.get().isLoaded("create");
  public static final boolean CYCLIC_LOADED = ModList.get().isLoaded("cyclic");
  public static final boolean FORBIDDEN_ARCANUS_LOADED =
      ModList.get().isLoaded("forbidden_arcanus");
  public static final boolean MOBCATCHER_LOADED = ModList.get().isLoaded("mobcatcher");
  public static final boolean MOB_CAPTURING_TOOL_LOADED =
      ModList.get().isLoaded("mobcapturingtool");
  public static final boolean MOB_CATCHER_LOADED = ModList.get().isLoaded("mob_catcher");
  public static final boolean PRODUCTIVE_BEES_LOADED = ModList.get().isLoaded("productivebees");
}
