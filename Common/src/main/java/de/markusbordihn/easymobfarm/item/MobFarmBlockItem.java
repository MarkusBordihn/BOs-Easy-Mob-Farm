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
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MobFarmBlockItem extends BlockItem {

  public static final String CUSTOM_MODEL_DATA_TAG = "CustomModelData";

  private final String farmName;

  public MobFarmBlockItem(String farmName, Block block) {
    this(farmName, block, new Item.Properties());
  }

  public MobFarmBlockItem(String farmName, Block block, Item.Properties properties) {
    super(block, properties);
    this.farmName = farmName;
  }

  public static void setTierLevel(ItemStack itemStack, int tierLevel) {
    itemStack.getOrCreateTag().putInt(MobFarmBlockEntity.TIER_LEVEL_TAG, tierLevel);
    setCustomModelData(itemStack);
  }

  private static void setCustomModelData(ItemStack itemStack) {
    var tag = itemStack.getOrCreateTag();
    if (tag.contains(MobFarmBlockEntity.TIER_LEVEL_TAG) && !tag.contains(CUSTOM_MODEL_DATA_TAG)) {
      tag.putInt(CUSTOM_MODEL_DATA_TAG, tag.getInt(MobFarmBlockEntity.TIER_LEVEL_TAG));
    }
  }

  @Override
  public Component getName(ItemStack stack) {
    int tierLevel = stack.getOrCreateTag().getInt(MobFarmBlockEntity.TIER_LEVEL_TAG);
    return TextComponent.getTranslatedBlockText(this.farmName, tierLevel);
  }

  @Override
  public void onCraftedBy(ItemStack stack, Level level, Player player) {
    super.onCraftedBy(stack, level, player);
    setCustomModelData(stack);
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag flag) {
    super.appendHoverText(itemStack, level, tooltip, flag);

    // Add farm description
    Component farmDescription = TextComponent.getTranslatedText(this.farmName);
    List<FormattedText> lines =
        Minecraft.getInstance()
            .font
            .getSplitter()
            .splitLines(farmDescription.getString(), 200, Style.EMPTY);
    for (FormattedText line : lines) {
      tooltip.add(TextComponent.getText(line.getString()).withStyle(ChatFormatting.GRAY));
    }

    // Add tier level
    int tierLevel = itemStack.getOrCreateTag().getInt(MobFarmBlockEntity.TIER_LEVEL_TAG);
    Component tierLevelText =
        switch (tierLevel) {
          case 0 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.WHITE);
          case 1 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.GREEN);
          case 2 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.YELLOW);
          case 3 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.RED);
          default -> null;
        };
    if (tierLevelText != null) {
      tooltip.add(tierLevelText);
    }

    // Add processing speed
    Component processingSpeedText =
        switch (tierLevel) {
          case 0 ->
              TextComponent.getTranslatedText(
                  "tier_level_processing_speed",
                  MobFarmBlockEntity.getProcessingSpeed(tierLevel),
                  ChatFormatting.WHITE);
          case 1 ->
              TextComponent.getTranslatedText(
                  "tier_level_processing_speed",
                  MobFarmBlockEntity.getProcessingSpeed(tierLevel),
                  ChatFormatting.GREEN);
          case 2 ->
              TextComponent.getTranslatedText(
                  "tier_level_processing_speed",
                  MobFarmBlockEntity.getProcessingSpeed(tierLevel),
                  ChatFormatting.YELLOW);
          case 3 ->
              TextComponent.getTranslatedText(
                  "tier_level_processing_speed",
                  MobFarmBlockEntity.getProcessingSpeed(tierLevel),
                  ChatFormatting.RED);
          default -> null;
        };
    if (processingSpeedText != null) {
      tooltip.add(processingSpeedText);
    }

    // Add additional information for special mob farms.
    if (Objects.equals(farmName, MobFarmType.LUCKY_DROP_FARM.getId())) {
      tooltip.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "lucky_drop_percentage",
                  new Object[] {MobFarmConfig.luckyDropFarmLuckPercentage})
              .withStyle(ChatFormatting.DARK_GREEN));
      if (MobFarmConfig.luckyDropFarmLuckPercentage < 100) {
        tooltip.add(
            TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "lucky_drop_warn")
                .withStyle(ChatFormatting.RED));
      }
    }
  }
}
