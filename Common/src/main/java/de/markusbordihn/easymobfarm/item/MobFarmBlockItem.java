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

import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmTierLevel;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MobFarmBlockItem extends BlockItem {

  private final String farmName;

  public MobFarmBlockItem(String farmName, Block block) {
    this(farmName, block, new Item.Properties());
  }

  public MobFarmBlockItem(String farmName, Block block, Item.Properties properties) {
    super(block, properties);
    this.farmName = farmName;
  }

  public static void setTierLevel(ItemStack itemStack, int tierLevel) {
    CompoundTag tag =
        itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getUnsafe();
    tag.putInt(MobFarmBlockEntity.TIER_LEVEL_TAG, tierLevel);
    itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    setCustomModelData(itemStack);
  }

  private static void setCustomModelData(ItemStack itemStack) {
    CompoundTag tag =
        itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).getUnsafe();
    if (tag.contains(MobFarmBlockEntity.TIER_LEVEL_TAG)) {
      int tierLevel = tag.getInt(MobFarmBlockEntity.TIER_LEVEL_TAG);
      if (tierLevel > 0) {
        itemStack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(tierLevel));
      }
    }
  }

  public static MobFarmTierLevel getTierLevel(ItemStack itemStack) {
    CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    int tierLevel = customData.getUnsafe().getInt(MobFarmBlockEntity.TIER_LEVEL_TAG);
    return MobFarmTierLevel.getTierLevel(tierLevel);
  }

  public String getFarmName() {
    return this.farmName;
  }

  @Override
  public Component getName(ItemStack itemStack) {
    CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    int tierLevel = customData.getUnsafe().getInt(MobFarmBlockEntity.TIER_LEVEL_TAG);
    return TextComponent.getTranslatedBlockText(this.farmName, tierLevel);
  }

  @Override
  public void onCraftedBy(ItemStack stack, Level level, Player player) {
    super.onCraftedBy(stack, level, player);
    setCustomModelData(stack);
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext tooltipContext,
      List<Component> tooltip,
      TooltipFlag flag) {
    super.appendHoverText(itemStack, tooltipContext, tooltip, flag);

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
    CustomData customData = itemStack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
    int tierLevel = customData.getUnsafe().getInt(MobFarmBlockEntity.TIER_LEVEL_TAG);
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
                  MobFarmBlockEntity.DEFAULT_PROCESSING_TICKS
                      + MobFarmConfig.tier0progressionUpgradeSpeed,
                  ChatFormatting.WHITE);
          case 1 ->
              TextComponent.getTranslatedText(
                  "tier_level_processing_speed",
                  MobFarmBlockEntity.DEFAULT_PROCESSING_TICKS
                      + MobFarmConfig.tier1progressionUpgradeSpeed,
                  ChatFormatting.GREEN);
          case 2 ->
              TextComponent.getTranslatedText(
                  "tier_level_processing_speed",
                  MobFarmBlockEntity.DEFAULT_PROCESSING_TICKS
                      + MobFarmConfig.tier2progressionUpgradeSpeed,
                  ChatFormatting.YELLOW);
          case 3 ->
              TextComponent.getTranslatedText(
                  "tier_level_processing_speed",
                  MobFarmBlockEntity.DEFAULT_PROCESSING_TICKS
                      + MobFarmConfig.tier3progressionUpgradeSpeed,
                  ChatFormatting.RED);
          default -> null;
        };
    if (processingSpeedText != null) {
      tooltip.add(processingSpeedText);
    }
  }
}
