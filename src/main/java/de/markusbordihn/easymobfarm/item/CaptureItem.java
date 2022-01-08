/**
 * Copyright 2021 Markus Bordihn
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

import java.util.List;
import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.CapturedMobCompatible;
import de.markusbordihn.easymobfarm.config.CommonConfig;

@EventBusSubscriber
public class CaptureItem extends CapturedMobItem {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static int mobCatchingLuck = COMMON.mobCatchingLuck.get();

  public CaptureItem(Item.Properties properties) {
    super(properties);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    mobCatchingLuck = COMMON.mobCatchingLuck.get();
  }

  public boolean canCatchMob(LivingEntity livingEntity) {
    return true;
  }

  @Override
  public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos blockPos = context.getClickedPos();
    BlockState blockState = level.getBlockState(blockPos);
    Block block = blockState.getBlock();
    ItemStack itemStack = context.getItemInHand();
    Player player = context.getPlayer();

    // Interact with the block directly, if it is Captured Mob Compatible.
    if (block instanceof CapturedMobCompatible capturedMobCompatible) {
      BlockEntity blockEntity = level.getBlockEntity(blockPos);
      // Make sure that the block can consume the token, we only accept server-side confirmations.
      if (!level.isClientSide && capturedMobCompatible.canConsumeCapturedMob(level, blockPos,
          blockState, blockEntity, player, itemStack)) {
        return capturedMobCompatible.consumeCapturedMob(level, blockPos, blockState, blockEntity,
            itemStack, context);
      }
      return InteractionResult.sidedSuccess(level.isClientSide);
    }

    // Check if we can release the captured mob on an empty block.
    if (hasCapturedMob(itemStack) && !level.isClientSide) {
      // Place directly on grass or similar blocks.
      if ((blockState.is(Blocks.GRASS) || blockState.is(Blocks.SEAGRASS))
          && releaseCapturedMob(itemStack, blockPos, level)) {
        return InteractionResult.CONSUME;
      }

      // Check if we can release the captured mob above.
      BlockPos blockPosAbove = blockPos.above();
      BlockState blockStateBlockAbove = level.getBlockState(blockPosAbove);
      if ((blockStateBlockAbove.is(Blocks.AIR) || blockStateBlockAbove.is(Blocks.WATER))
          && releaseCapturedMob(itemStack, blockPosAbove, level)) {
        return InteractionResult.CONSUME;
      }
    }
    return InteractionResult.sidedSuccess(level.isClientSide);
  }

  @Override
  public InteractionResult interactLivingEntity(ItemStack itemStack, Player player,
      LivingEntity livingEntity, InteractionHand hand) {

    // Ignore players and dead entities for capturing.
    if (livingEntity == null || livingEntity instanceof Player || livingEntity.isDeadOrDying()) {
      return InteractionResult.FAIL;
    }
    Level level = livingEntity.getLevel();

    // Try to check if we could catching the targeted mob.

    if (!hasCapturedMob(itemStack)) {
      // Handle sounds and empty hand, if item will break.
      if (willItemBreak(itemStack, 1)) {
        if (level.isClientSide) {
          player.playSound(SoundEvents.ITEM_BREAK, 1.0F, 0F);
        } else {
          damageItem(itemStack, 1);
          player.setItemInHand(hand, ItemStack.EMPTY);
        }
        return InteractionResult.FAIL;
      }

      // Play capture sound.
      if (level.isClientSide) {
        player.playSound(SoundEvents.EGG_THROW, 1.0F, 0F);
        return InteractionResult.SUCCESS;
      }

      // Handle mob catching luck.
      if (mobCatchingLuck > 0 && this.random.nextInt(mobCatchingLuck) != 0) {
        livingEntity.playSound(SoundEvents.DISPENSER_FAIL, 1.0F, 0F);
        damageItem(itemStack, 1);
        player.setItemInHand(hand, itemStack);
        return InteractionResult.FAIL;
      }

      // Capture mob inside item.
      ItemStack capturedMobItem = setCapturedMob(livingEntity, itemStack);
      damageItem(capturedMobItem, 1);
      player.setItemInHand(hand, capturedMobItem);

      InteractionResult.sidedSuccess(level.isClientSide);
    }

    return InteractionResult.PASS;
  }

  @Override
  public void appendHoverText(ItemStack itemStack, @Nullable Level level,
      List<Component> tooltipList, TooltipFlag tooltipFlag) {
    String entityName = getCapturedMob(itemStack);
    if (entityName.isEmpty()) {
      tooltipList.add(new TranslatableComponent(Constants.TEXT_PREFIX + "capture"));
    } else {
      Float entityHealth = getCapturedMobHealth(itemStack);
      tooltipList.add(new TranslatableComponent(""));
      tooltipList.add(new TranslatableComponent(Constants.TEXT_PREFIX + "release", entityName)
          .withStyle(ChatFormatting.YELLOW));
      tooltipList.add(new TranslatableComponent(""));

      // Display possible loot
      List<String> possibleLoot = getPossibleLoot(itemStack);
      if (!possibleLoot.isEmpty()) {
        TranslatableComponent possibleLootOverview =
            (TranslatableComponent) new TranslatableComponent(
                Constants.TEXT_PREFIX + "possible_loot").append(" ")
                    .withStyle(ChatFormatting.GREEN);
        for (String drop : possibleLoot) {
          possibleLootOverview.append(
              new TranslatableComponent(drop).append(", ").withStyle(ChatFormatting.DARK_GREEN));
        }
        tooltipList.add(possibleLootOverview.append("..."));
      }

      tooltipList.add(new TranslatableComponent(Constants.TEXT_PREFIX + "health", entityHealth));
    }
  }

  @Override
  public boolean isFoil(ItemStack itemStack) {
    return hasCapturedMob(itemStack);
  }

  @Override
  public boolean canAttackBlock(BlockState blockState, Level level, BlockPos blockPos,
      Player player) {
    return false;
  }
}
