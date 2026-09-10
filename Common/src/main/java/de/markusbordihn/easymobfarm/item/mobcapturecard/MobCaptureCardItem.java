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

package de.markusbordihn.easymobfarm.item.mobcapturecard;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.config.MobCaptureCardConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MobCaptureCardItem extends Item {

  public static final String ID = "mob_capture_card";
  public static final ResourceLocation RESOURCE_LOCATION =
      new ResourceLocation(Constants.MOD_ID, ID);
  public static final String TOOLTIP_PREFIX = Constants.TOOLTIP_PREFIX + ID + ".";

  public MobCaptureCardItem() {
    this(new Item.Properties());
  }

  public MobCaptureCardItem(Properties properties) {
    super(properties.fireResistant());
  }

  public static MobCaptureCardItem getMobCaptureCardItem() {
    if (BuiltInRegistries.ITEM.get(RESOURCE_LOCATION)
        instanceof MobCaptureCardItem mobCaptureCardItem) {
      return mobCaptureCardItem;
    }
    return null;
  }

  private static String getVariantName(String variant) {
    if (variant == null || variant.isEmpty()) {
      return "";
    }
    return Arrays.stream(variant.split("_"))
        .map(
            word ->
                word.substring(0, 1).toUpperCase(Locale.ROOT)
                    + word.substring(1).toLowerCase(Locale.ROOT))
        .collect(Collectors.joining(" "));
  }

  private static Component getTranslatedEntityName(MobCaptureData mobCaptureData) {
    if (mobCaptureData == null || mobCaptureData.name() == null) {
      return TextComponent.getText("Unknown");
    }
    return TextComponent.getTranslatedTextRaw(mobCaptureData.name());
  }

  private static void addSpecialMechanicsTooltips(
      final MobCaptureData mobCaptureData, final List<Component> tooltip) {
    if (mobCaptureData == null || mobCaptureData.name() == null) {
      return;
    }
    MutableComponent specialMechanicsTooltip =
        tryGetTranslation(
            TOOLTIP_PREFIX
                + mobCaptureData.name().replace("entity.", "").replace(":", ".")
                + ".special_mechanics");
    if (specialMechanicsTooltip != null) {
      tooltip.add(specialMechanicsTooltip.withStyle(ChatFormatting.GRAY));
    }
  }

  private static MutableComponent tryGetTranslation(final String translationKey) {
    try {
      MutableComponent translation = TextComponent.getTranslatedTextRaw(translationKey);
      if (translation.getString().equals(translationKey)) {
        return null;
      }
      return translation;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public boolean isEnchantable(ItemStack itemStack) {
    return false;
  }

  @Override
  public boolean isFireResistant() {
    return true;
  }

  @Override
  public boolean canAttackBlock(
      BlockState blockState, Level level, BlockPos blockPos, Player player) {
    return false;
  }

  @Override
  public void inventoryTick(
      ItemStack itemStack, Level level, Entity entity, int slotIndex, boolean isSelected) {
    if (!level.isClientSide && MobCaptureCardConfig.upgradeExistingMobCaptureCards) {
      MobCaptureManager.upgradeMobCaptureCard(itemStack);
    }
  }

  @Override
  public Component getName(ItemStack itemStack) {
    MobCaptureData mobCaptureData = MobCaptureManager.getMobCaptureData(itemStack);
    if (mobCaptureData == null) {
      return super.getName(itemStack);
    }

    // Generate name based on variant and color.
    String key = TOOLTIP_PREFIX + "card_name";
    Object[] args = new Object[] {getTranslatedEntityName(mobCaptureData)};
    if (mobCaptureData.variant() != null
        && !mobCaptureData.name().equalsIgnoreCase(mobCaptureData.variant())) {
      if (mobCaptureData.color() != null) {
        key += "_variant_color";
        args =
            new Object[] {
              getTranslatedEntityName(mobCaptureData),
              getVariantName(mobCaptureData.variant()),
              mobCaptureData.color().getName()
            };
      } else {
        key += "_variant";
        args =
            new Object[] {
              getTranslatedEntityName(mobCaptureData), getVariantName(mobCaptureData.variant())
            };
      }
    } else if (mobCaptureData.color() != null) {
      key += "_color";
      args =
          new Object[] {getTranslatedEntityName(mobCaptureData), mobCaptureData.color().getName()};
    }

    return TextComponent.getTranslatedTextRaw(key, args)
        .append(" ")
        .append(
            TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_PREFIX + ID)
                .withStyle(ChatFormatting.GRAY));
  }

  @Override
  public Rarity getRarity(ItemStack itemStack) {
    MobCaptureData mobCaptureData = MobCaptureManager.getMobCaptureData(itemStack);
    return mobCaptureData != null ? mobCaptureData.rarity() : Rarity.COMMON;
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    MobCaptureData mobCaptureData = MobCaptureManager.getMobCaptureData(itemStack);
    return mobCaptureData != null && mobCaptureData.isFoil();
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag flag) {
    MobCaptureData mobCaptureData = MobCaptureManager.getMobCaptureData(itemStack);
    if (mobCaptureData == null) {
      tooltip.add(TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "empty"));
      return;
    }
    tooltip.add(
        TextComponent.getTranslatedTextRaw(
            TOOLTIP_PREFIX + "name", getTranslatedEntityName(mobCaptureData)));
    if (flag.isAdvanced()) {
      tooltip.add(
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "description_id", mobCaptureData.name()));
      tooltip.add(
          TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "type", mobCaptureData.type()));
    }
    if (mobCaptureData.variant() != null) {
      tooltip.add(
          TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "variant", mobCaptureData.variant()));
    }
    if (mobCaptureData.color() != null) {
      tooltip.add(
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "color", mobCaptureData.color().getName()));
    }

    ChatFormatting rarityColor =
        switch (mobCaptureData.rarity()) {
          case UNCOMMON -> ChatFormatting.GREEN;
          case RARE -> ChatFormatting.BLUE;
          case EPIC -> ChatFormatting.DARK_PURPLE;
          default -> ChatFormatting.GRAY;
        };
    tooltip.add(
        TextComponent.getTranslatedTextRaw(
                TOOLTIP_PREFIX + "rarity", mobCaptureData.rarity().name())
            .withStyle(rarityColor));
    tooltip.add(
        TextComponent.getTranslatedTextRaw(
            TOOLTIP_PREFIX + "id", String.valueOf(mobCaptureData.getCardId())));

    addSpecialMechanicsTooltips(mobCaptureData, tooltip);
  }
}
