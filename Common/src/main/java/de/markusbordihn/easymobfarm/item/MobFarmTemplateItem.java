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

package de.markusbordihn.easymobfarm.item;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class MobFarmTemplateItem extends BlockItem {

  public static final String ID_TIER_0 = "tier0_mob_farm_template";
  public static final String ID_TIER_1 = "tier1_mob_farm_template";
  public static final String ID_TIER_2 = "tier2_mob_farm_template";
  public static final String ID_TIER_3 = "tier3_mob_farm_template";
  private final String tierLevel;

  public MobFarmTemplateItem(String tierLevel, Block block) {
    this(
        tierLevel,
        block,
        new Properties()
            .useBlockDescriptionPrefix()
            .setId(
                ResourceKey.create(
                    Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID,
                        switch (tierLevel) {
                          case ID_TIER_1 -> ID_TIER_1;
                          case ID_TIER_2 -> ID_TIER_2;
                          case ID_TIER_3 -> ID_TIER_3;
                          default -> ID_TIER_0;
                        }))));
  }

  public MobFarmTemplateItem(String tierLevel, Block block, Properties properties) {
    super(block, properties);
    this.tierLevel = tierLevel;
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext tooltipContext,
      List<Component> tooltip,
      TooltipFlag flag) {
    tooltip.add(
        TextComponent.getTranslatedText("mob_farm_template").withStyle(ChatFormatting.YELLOW));

    // Add Tier Level to tooltip
    Component tierLevelText =
        switch (tierLevel) {
          case ID_TIER_1 -> TextComponent.getTranslatedText("tier_level", 1, ChatFormatting.GREEN);
          case ID_TIER_2 -> TextComponent.getTranslatedText("tier_level", 2, ChatFormatting.YELLOW);
          case ID_TIER_3 -> TextComponent.getTranslatedText("tier_level", 3, ChatFormatting.RED);
          default -> TextComponent.getTranslatedText("tier_level", 0, ChatFormatting.WHITE);
        };
    tooltip.add(tierLevelText);
  }
}
