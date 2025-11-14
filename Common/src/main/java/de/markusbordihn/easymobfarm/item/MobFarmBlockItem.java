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
import de.markusbordihn.easymobfarm.component.DataComponents;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmTierLevel;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

public class MobFarmBlockItem extends BlockItem {

  public static final String ID = "mob_farm_block_item";
  private final String farmName;
  private final MobFarmType mobFarmType;

  public MobFarmBlockItem(MobFarmType mobFarmType, Block block) {
    this(
        mobFarmType,
        block,
        new Item.Properties()
            .useBlockDescriptionPrefix()
            .setId(
                ResourceKey.create(
                    Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, mobFarmType.getId()))));
  }

  public MobFarmBlockItem(MobFarmType mobFarmType, Block block, Item.Properties properties) {
    super(block, properties);
    this.mobFarmType = mobFarmType;
    this.farmName = mobFarmType.getId();
  }

  public static MobFarmTierLevel getTierLevel(ItemStack itemStack) {
    MobFarmData mobFarmData =
        itemStack.getOrDefault(DataComponents.MOB_FARM_DATA, MobFarmData.EMPTY);
    return mobFarmData.tierLevel();
  }

  public String getFarmName() {
    return this.farmName;
  }

  public MobFarmType getMobFarmType() {
    return this.mobFarmType;
  }

  @Override
  public Component getName(ItemStack itemStack) {
    MobFarmData mobFarmData =
        itemStack.getOrDefault(DataComponents.MOB_FARM_DATA, MobFarmData.EMPTY);
    int tierLevel = mobFarmData.tierLevel().getTierLevel();
    return TextComponent.getTranslatedBlockText(this.farmName, tierLevel);
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext tooltipContext,
      TooltipDisplay tooltipDisplay,
      Consumer<Component> tooltipConsumer,
      TooltipFlag tooltipFlag) {
    super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, tooltipConsumer, tooltipFlag);

    // Add farm description
    Component farmDescription = TextComponent.getTranslatedText(this.farmName);
    List<FormattedText> lines =
        Minecraft.getInstance().font.getSplitter().splitLines(farmDescription, 200, Style.EMPTY);
    for (FormattedText line : lines) {
      tooltipConsumer.accept(Component.literal(line.getString()).withStyle(ChatFormatting.GRAY));
    }

    // Add tier level
    MobFarmData mobFarmData =
        itemStack.getOrDefault(DataComponents.MOB_FARM_DATA, MobFarmData.EMPTY);
    int tierLevel = mobFarmData.tierLevel().getTierLevel();
    Component tierLevelText =
        switch (tierLevel) {
          case 0 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.WHITE);
          case 1 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.GREEN);
          case 2 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.YELLOW);
          case 3 -> TextComponent.getTranslatedText("tier_level", tierLevel, ChatFormatting.RED);
          default -> null;
        };
    if (tierLevelText != null) {
      tooltipConsumer.accept(tierLevelText);
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
      tooltipConsumer.accept(processingSpeedText);
    }

    // Add additional information for special mob farms.
    if (MobFarmType.LUCKY_DROP_FARM.getId().equals(farmName)) {
      tooltipConsumer.accept(
          TextComponent.getTranslatedTextRaw(
              Constants.TOOLTIP_FARM_PREFIX + "lucky_drop_percentage",
              new Object[] {MobFarmConfig.luckyDropFarmLuckPercentage}));
      if (MobFarmConfig.luckyDropFarmLuckPercentage < 100) {
        tooltipConsumer.accept(
            TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "lucky_drop_warn")
                .withStyle(ChatFormatting.RED));
      }
    }
  }
}
