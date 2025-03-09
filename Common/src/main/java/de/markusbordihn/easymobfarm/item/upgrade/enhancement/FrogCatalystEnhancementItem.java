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
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class FrogCatalystEnhancementItem extends EnhancementItem {

  private static final String ID = "frog_catalyst";
  private static final String ID_PREFIX = ID + "_";
  public static final String ID_COLD = ID_PREFIX + FrogCatalystType.COLD.getId() + ID_POSTFIX;
  public static final String ID_TEMPERATE =
      ID_PREFIX + FrogCatalystType.TEMPERATE.getId() + ID_POSTFIX;
  public static final String ID_WARM = ID_PREFIX + FrogCatalystType.WARM.getId() + ID_POSTFIX;

  private final FrogCatalystType frogCatalystType;

  public FrogCatalystEnhancementItem(FrogCatalystType frogCatalystType) {
    this(frogCatalystType, new Properties());
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
      List<Component> tooltip,
      TooltipFlag flag) {
    addTooltip(
        tooltip,
        TextComponent.getTranslatedTextRaw(
            Constants.TOOLTIP_PREFIX + ID + "_" + frogCatalystType.getId() + ID_POSTFIX));
  }
}
