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

package de.markusbordihn.easymobfarm.item.mobcatcher;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.MobFarmBlock;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.item.MobFarmItem;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class MobCatcherItem extends MobFarmItem {

  public static final String ID = "mob_catcher";
  public static final String MOB_CAPTURE_DATA_TAG = "MobCaptureData";
  public static final String CUSTOM_MODEL_DATA_TAG = "CustomModelData";
  public static final String TOOLTIP_PREFIX = Constants.TOOLTIP_PREFIX + ID + ".";

  private static final float REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE = 0.25f;
  private static final float MAX_ENTITY_HEIGHT_TO_CAPTURE = 2.0f;
  private static final float MAX_ENTITY_WIDTH_TO_CAPTURE = 1.5f;
  private static final int ITEM_DAMAGE_ON_USE = 1;

  public MobCatcherItem() {
    super(new Item.Properties());
  }

  public MobCatcherItem(Properties properties) {
    super(properties);
  }

  public boolean hasMobCaptureData(ItemStack itemStack) {
    return itemStack != null
        && itemStack.hasTag()
        && itemStack.getTag() != null
        && itemStack.getTag().contains(MOB_CAPTURE_DATA_TAG);
  }

  public float getRequiredHealthPercentageToCapture() {
    return REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE;
  }

  public float getMaxEntityHeightToCapture() {
    return MAX_ENTITY_HEIGHT_TO_CAPTURE;
  }

  public float getMaxEntityWidthToCapture() {
    return MAX_ENTITY_WIDTH_TO_CAPTURE;
  }

  public int getItemDamageOnUse() {
    return ITEM_DAMAGE_ON_USE;
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    return hasMobCaptureData(itemStack);
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    ItemStack itemStack = context.getItemInHand();

    // Check if we have any mob capture data.
    if (!hasMobCaptureData(itemStack)) {
      return InteractionResult.FAIL;
    }

    // Check if target block is a mob farm.
    if (level.getBlockState(blockPos).getBlock() instanceof MobFarmBlock
        && level.getBlockEntity(blockPos) instanceof MobFarmBlockEntity mobFarmBlockEntity
        && !mobFarmBlockEntity.hasCapturedMob()) {
      return InteractionResult.PASS;
    }

    // Release the mob and remove the capture data.
    if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
      MobCaptureData mobCaptureData =
          new MobCaptureData(itemStack.getTag().getCompound(MOB_CAPTURE_DATA_TAG));
      if (MobCaptureManager.releaseMob(mobCaptureData, blockPos, serverLevel)) {
        itemStack.getTag().remove(MOB_CAPTURE_DATA_TAG);
        itemStack.getOrCreateTag().putInt(CUSTOM_MODEL_DATA_TAG, 0);
        return InteractionResult.SUCCESS;
      }
    }

    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  @Override
  public InteractionResult interactLivingEntity(
      ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {

    // Ignore players and dead entities for capturing.
    if (livingEntity == null || livingEntity instanceof Player || livingEntity.isDeadOrDying()) {
      return InteractionResult.FAIL;
    }

    // Check if we already have a capture data.
    if (hasMobCaptureData(itemStack)) {
      return InteractionResult.FAIL;
    }

    // Check if item should be damaged on use.
    if (getItemDamageOnUse() > 0) {
      itemStack.hurtAndBreak(
          getItemDamageOnUse(), player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
    }

    // Check mob dimensions, if we have any restrictions.
    if (getMaxEntityHeightToCapture() > 0 || getMaxEntityWidthToCapture() > 0f) {
      EntityDimensions dimensions = livingEntity.getDimensions(livingEntity.getPose());
      if (dimensions.height > getMaxEntityHeightToCapture()
          || dimensions.width > getMaxEntityWidthToCapture()) {
        player.displayClientMessage(
            TextComponent.getTranslatedText(
                "too_large_to_capture", livingEntity.getDisplayName().getString()),
            true);
        return InteractionResult.FAIL;
      }
    }

    // Check mob health, if we have any restrictions.
    if (getRequiredHealthPercentageToCapture() > 0f) {
      float healthPercentage = livingEntity.getHealth() / livingEntity.getMaxHealth();
      if (healthPercentage > getRequiredHealthPercentageToCapture()) {
        player.displayClientMessage(
            TextComponent.getTranslatedText(
                "too_strong_to_capture", livingEntity.getDisplayName().getString()),
            true);
        return InteractionResult.FAIL;
      }
    }

    // Check if we are on the client side.
    Level level = livingEntity.getLevel();
    if (level.isClientSide) {
      return InteractionResult.SUCCESS;
    }

    // Capture the entity and store the data.
    MobCaptureData mobCaptureData = new MobCaptureData(livingEntity);
    itemStack.getOrCreateTag().put(MOB_CAPTURE_DATA_TAG, mobCaptureData.createTag());
    itemStack.getOrCreateTag().putInt(CUSTOM_MODEL_DATA_TAG, 1);
    player.setItemInHand(hand, itemStack);
    livingEntity.discard();

    // Notify player about the successful capture.
    player.displayClientMessage(
        TextComponent.getTranslatedText(
            "captured_mob", mobCaptureData.name(), mobCaptureData.type()),
        true);

    return InteractionResult.CONSUME;
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag flag) {
    if (hasMobCaptureData(itemStack)) {
      MobCaptureData mobCaptureData = MobCaptureManager.getMobCaptureData(itemStack);
      addTooltip(
          tooltip,
          TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "release_hint", mobCaptureData.name())
              .withStyle(ChatFormatting.DARK_RED));
      addTooltip(
          tooltip,
          TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "name", mobCaptureData.name()));
      if (flag.isAdvanced()) {
        addTooltip(
            tooltip,
            TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "type", mobCaptureData.type()));
      }
      if (mobCaptureData.variant() != null) {
        addTooltip(
            tooltip,
            TextComponent.getTranslatedTextRaw(
                TOOLTIP_PREFIX + "variant", mobCaptureData.variant()));
      }
      if (mobCaptureData.color() != null) {
        addTooltip(
            tooltip,
            TextComponent.getTranslatedTextRaw(
                TOOLTIP_PREFIX + "color", mobCaptureData.color().getName()));
      }
    } else {
      addTooltip(
          tooltip,
          TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "capture_hint")
              .withStyle(ChatFormatting.DARK_GREEN));
      addTooltip(
          tooltip,
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "max_size",
              getMaxEntityWidthToCapture() + "x" + getMaxEntityHeightToCapture()));
      addTooltip(
          tooltip,
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "min_health", getRequiredHealthPercentageToCapture() * 100 + "%"));
    }
    addTooltip(
        tooltip,
        TextComponent.getTranslatedTextRaw(
                TOOLTIP_PREFIX + "usage_left",
                "("
                    + (itemStack.getMaxDamage() - itemStack.getDamageValue())
                    + "/"
                    + itemStack.getMaxDamage()
                    + ")")
            .withStyle(ChatFormatting.GRAY));
  }
}
