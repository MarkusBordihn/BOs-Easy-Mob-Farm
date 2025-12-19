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

package de.markusbordihn.easymobfarm.item.consumables;

import com.mojang.blaze3d.systems.RenderSystem;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

public class MilkBottleItem extends Item {

  public static final String ID = "milk_bottle";
  private static final int DRINK_DURATION = 32;

  public MilkBottleItem() {
    this(
        new Item.Properties()
            .setId(
                ResourceKey.create(
                    Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, ID))));
  }

  public MilkBottleItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
    if (level.isClientSide() || !(livingEntity instanceof Player player)) {
      return itemStack;
    }

    player.removeAllEffects();
    level.playSound(
        null,
        player.getX(),
        player.getY(),
        player.getZ(),
        SoundEvents.GENERIC_DRINK,
        SoundSource.PLAYERS,
        1.0F,
        1.0F);

    if (!player.getAbilities().instabuild) {
      itemStack.shrink(1);
    }

    return itemStack.isEmpty() ? new ItemStack(Items.AIR) : itemStack;
  }

  @Override
  public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
    return DRINK_DURATION;
  }

  @Override
  public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
    return ItemUseAnimation.DRINK;
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
    return ItemUtils.startUsingInstantly(level, player, interactionHand);
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
        TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_PREFIX + ID),
        ChatFormatting.GRAY);
  }

  public void addTooltip(
      Consumer<Component> tooltipConsumer,
      final Component component,
      final ChatFormatting formatting) {
    if (RenderSystem.isOnRenderThread()) {
      List<FormattedText> lines =
          Minecraft.getInstance().font.getSplitter().splitLines(component, 200, Style.EMPTY);
      for (FormattedText line : lines) {
        tooltipConsumer.accept(Component.literal(line.getString()).withStyle(formatting));
      }
    } else {
      tooltipConsumer.accept(component.copy().withStyle(formatting));
    }
  }
}
