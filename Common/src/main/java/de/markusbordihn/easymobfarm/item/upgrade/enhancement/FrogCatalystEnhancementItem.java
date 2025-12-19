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

package de.markusbordihn.easymobfarm.item.upgrade.enhancement;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.enhancement.FrogCatalystType;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.function.Consumer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

public class FrogCatalystEnhancementItem extends EnhancementItem {

  private static final String ID = "frog_catalyst";
  private static final String ID_PREFIX = ID + "_";
  public static final String ID_COLD = ID_PREFIX + FrogCatalystType.COLD.getId() + ID_POSTFIX;
  public static final String ID_TEMPERATE =
      ID_PREFIX + FrogCatalystType.TEMPERATE.getId() + ID_POSTFIX;
  public static final String ID_WARM = ID_PREFIX + FrogCatalystType.WARM.getId() + ID_POSTFIX;
  public static final String ID_WHITE = ID_PREFIX + FrogCatalystType.WHITE.getId() + ID_POSTFIX;
  public static final String ID_ORANGE = ID_PREFIX + FrogCatalystType.ORANGE.getId() + ID_POSTFIX;
  public static final String ID_MAGENTA = ID_PREFIX + FrogCatalystType.MAGENTA.getId() + ID_POSTFIX;
  public static final String ID_LIGHT_BLUE =
      ID_PREFIX + FrogCatalystType.LIGHT_BLUE.getId() + ID_POSTFIX;
  public static final String ID_YELLOW = ID_PREFIX + FrogCatalystType.YELLOW.getId() + ID_POSTFIX;
  public static final String ID_LIME = ID_PREFIX + FrogCatalystType.LIME.getId() + ID_POSTFIX;
  public static final String ID_PINK = ID_PREFIX + FrogCatalystType.PINK.getId() + ID_POSTFIX;
  public static final String ID_GRAY = ID_PREFIX + FrogCatalystType.GRAY.getId() + ID_POSTFIX;
  public static final String ID_LIGHT_GRAY =
      ID_PREFIX + FrogCatalystType.LIGHT_GRAY.getId() + ID_POSTFIX;
  public static final String ID_CYAN = ID_PREFIX + FrogCatalystType.CYAN.getId() + ID_POSTFIX;
  public static final String ID_PURPLE = ID_PREFIX + FrogCatalystType.PURPLE.getId() + ID_POSTFIX;
  public static final String ID_BLUE = ID_PREFIX + FrogCatalystType.BLUE.getId() + ID_POSTFIX;
  public static final String ID_BROWN = ID_PREFIX + FrogCatalystType.BROWN.getId() + ID_POSTFIX;
  public static final String ID_GREEN = ID_PREFIX + FrogCatalystType.GREEN.getId() + ID_POSTFIX;
  public static final String ID_RED = ID_PREFIX + FrogCatalystType.RED.getId() + ID_POSTFIX;
  public static final String ID_BLACK = ID_PREFIX + FrogCatalystType.BLACK.getId() + ID_POSTFIX;

  private final FrogCatalystType frogCatalystType;

  public FrogCatalystEnhancementItem(FrogCatalystType frogCatalystType) {
    this(
        frogCatalystType,
        new Properties()
            .setId(
                ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(
                        Constants.MOD_ID, ID_PREFIX + frogCatalystType.getId() + ID_POSTFIX))));
  }

  public FrogCatalystEnhancementItem(FrogCatalystType frogCatalystType, Properties properties) {
    super(properties);
    this.frogCatalystType = frogCatalystType;
  }

  public FrogCatalystType getFrogCatalystType() {
    return frogCatalystType;
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext tooltipContext,
      TooltipDisplay tooltipDisplay,
      Consumer<Component> tooltipConsumer,
      TooltipFlag tooltipFlag) {
    addTooltip(
        tooltipConsumer,
        TextComponent.getTranslatedTextRaw(
            Constants.TOOLTIP_PREFIX + ID + "_" + frogCatalystType.getId() + ID_POSTFIX));
  }
}
