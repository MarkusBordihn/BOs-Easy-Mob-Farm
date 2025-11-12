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
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreativeBlankMobCaptureCardItem extends BlankMobCaptureCardItem {

  public static final String ID = "creative_blank_mob_capture_card";
  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public CreativeBlankMobCaptureCardItem() {
    super(
        new Properties()
            .setId(
                ResourceKey.create(
                    Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, ID))));
  }

  public CreativeBlankMobCaptureCardItem(Properties properties) {
    super(properties);
  }

  @Override
  public InteractionResult interactLivingEntity(
      ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand hand) {

    // Ignore players and dead entities for capturing.
    if (livingEntity == null || livingEntity instanceof Player || livingEntity.isDeadOrDying()) {
      return InteractionResult.FAIL;
    }

    // Ignore client side.
    Level level = livingEntity.level();
    if (level.isClientSide()) {
      return InteractionResult.SUCCESS;
    }

    // Capture entity and check if it was successful.
    ItemStack mobCaptureCardItemStack = MobCaptureManager.getMobCaptureCardItem(livingEntity);
    if (mobCaptureCardItemStack == null || mobCaptureCardItemStack.isEmpty()) {
      log.error("Failed to capture entity {}", livingEntity);
      return InteractionResult.FAIL;
    }

    // Verify captured entity.
    MobCaptureData mobCaptureData =
        MobCaptureManager.getMobCaptureData(mobCaptureCardItemStack, level);
    if (mobCaptureData == null || mobCaptureData.entityType() == null) {
      log.error("Failed to get mob capture data for entity {}", livingEntity);
      return InteractionResult.FAIL;
    }

    // Give captured mob card to player.
    if (player.getAbilities().instabuild) {
      ItemStack mobCaptureCardItemStackCopy = mobCaptureCardItemStack.copy();
      if (!player.addItem(mobCaptureCardItemStack)) {
        log.error(
            "Failed to add mob capture card {} to player {}", mobCaptureCardItemStack, player);
        return InteractionResult.FAIL;
      } else {
        log.info(
            "Added mob capture card {} to players inventory {}",
            mobCaptureCardItemStackCopy,
            player);
      }
    } else {
      player.setItemInHand(hand, mobCaptureCardItemStack);
      log.info("Set mob capture card {} to players hand {}", player.getItemInHand(hand), player);
    }

    // Remove the mob from the world.
    livingEntity.remove(Entity.RemovalReason.KILLED);

    return InteractionResult.CONSUME;
  }
}
