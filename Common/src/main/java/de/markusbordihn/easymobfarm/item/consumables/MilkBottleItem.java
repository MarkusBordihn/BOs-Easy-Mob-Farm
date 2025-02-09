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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class MilkBottleItem extends Item {

  public static final String ID = "milk_bottle";
  private static final int DRINK_DURATION = 32;

  public MilkBottleItem() {
    this(new Item.Properties());
  }

  public MilkBottleItem(Properties properties) {
    super(properties);
  }

  @Override
  public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
    if (level.isClientSide || !(livingEntity instanceof Player player)) {
      return itemStack;
    }

    List<MobEffect> negativeMobEffects =
        player.getActiveEffects().stream()
            .filter(effect -> !effect.getEffect().isBeneficial())
            .map(MobEffectInstance::getEffect)
            .toList();

    for (MobEffect mobEffect : negativeMobEffects) {
      player.removeEffect(mobEffect);
    }

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
  public int getUseDuration(ItemStack itemStack) {
    return DRINK_DURATION;
  }

  @Override
  public UseAnim getUseAnimation(ItemStack itemStack) {
    return UseAnim.DRINK;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(
      Level level, Player player, InteractionHand interactionHand) {
    return ItemUtils.startUsingInstantly(level, player, interactionHand);
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack, Level world, List<Component> tooltip, TooltipFlag flag) {
    addTooltip(
        tooltip,
        TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_PREFIX + ID),
        ChatFormatting.GRAY);
  }

  public void addTooltip(
      List<Component> tooltip, final Component component, final ChatFormatting formatting) {
    String componentString = component.getString();
    List<FormattedText> lines =
        Minecraft.getInstance().font.getSplitter().splitLines(componentString, 200, Style.EMPTY);
    for (FormattedText line : lines) {
      tooltip.add(TextComponent.getText(line.getString()).withStyle(formatting));
    }
  }
}
