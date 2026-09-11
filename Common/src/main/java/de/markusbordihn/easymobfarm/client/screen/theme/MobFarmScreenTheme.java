/*
 * Copyright 2026 Markus Bordihn
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

package de.markusbordihn.easymobfarm.client.screen.theme;

import de.markusbordihn.easymobfarm.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public enum MobFarmScreenTheme {
  TECHNOLOGY(
      "technology",
      TextureThemeRenderer.of(
          "mob_farm_technology",
          ThemeProgressBar.horizontal("mob_farm_technology", 113, 78, 33, 7, 0, 244),
          EntityPreview.centered(55, 21, 30, 55, 5, 0.85F)),
      Items.REDSTONE_BLOCK),
  TECHNICAL(
      "technical",
      TextureThemeRenderer.of(
          "mob_farm",
          ThemeProgressBar.horizontal("mob_farm", 113, 78, 33, 9, 0, 244),
          new EntityPreview(52, 15, 40, 65, 72, 78, 1.0F)),
      Items.IRON_BLOCK),
  CLASSIC("classic", ClassicThemeRenderer.INSTANCE, Items.CHEST),
  RUSTIC(
      "rustic",
      TextureThemeRenderer.of(
          "mob_farm_rustic",
          ThemeProgressBar.horizontal("mob_farm_rustic", 119, 75, 88, 7, 0, 244),
          EntityPreview.centered(59, 35, 44, 38, 4, 0.7F)),
      Items.SPRUCE_PLANKS),
  DARK(
      "dark",
      TextureThemeRenderer.of(
          "mob_farm_dark",
          ThemeProgressBar.vertical("mob_farm_dark", 198, 32, 7, 48, 246, 0),
          EntityPreview.centered(45, 16, 66, 62, 5, 0.9F)),
      Items.DEEPSLATE_TILES),
  STEAMPUNK(
      "steampunk",
      TextureThemeRenderer.of(
          "mob_farm_steampunk",
          ThemeProgressBar.frames("mob_farm_steampunk_progress", 116, 60, 28, 26, 33, 8, 32, 28),
          EntityPreview.centered(56, 29, 42, 44, 5, 0.7F)),
      Items.COPPER_BLOCK),
  CREATE(
      "create",
      TextureThemeRenderer.of(
          "mob_farm_create",
          ThemeProgressBar.horizontal("mob_farm_create", 119, 75, 88, 7, 0, 244),
          EntityPreview.centered(47, 19, 64, 59, 5, 0.8F)),
      Items.ANDESITE);

  private final String id;
  private final String translationKey;
  private final MobFarmScreenThemeRenderer renderer;
  private final ItemStack icon;

  MobFarmScreenTheme(String id, MobFarmScreenThemeRenderer renderer, Item iconItem) {
    this.id = id;
    this.translationKey = Constants.TEXT_PREFIX + "theme." + id;
    this.renderer = renderer;
    this.icon = new ItemStack(iconItem);
  }

  public static MobFarmScreenTheme fromId(String id) {
    for (MobFarmScreenTheme theme : values()) {
      if (theme.id.equalsIgnoreCase(id)) {
        return theme;
      }
    }
    return TECHNOLOGY;
  }

  public MobFarmScreenTheme next() {
    return values()[(this.ordinal() + 1) % values().length];
  }

  public String getId() {
    return this.id;
  }

  public String getTranslationKey() {
    return this.translationKey;
  }

  public MobFarmScreenThemeRenderer getRenderer() {
    return this.renderer;
  }

  public ItemStack getIcon() {
    return this.icon;
  }
}
