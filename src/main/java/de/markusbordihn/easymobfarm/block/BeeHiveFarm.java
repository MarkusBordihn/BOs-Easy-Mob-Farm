/**
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

package de.markusbordihn.easymobfarm.block;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.farm.BeeHiveFarmEntity;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.text.TranslatableText;

@EventBusSubscriber
public class BeeHiveFarm extends MobFarmBlock {

  public static final String NAME = "bee_hive_farm";

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static Set<String> acceptedMobTypes = new HashSet<>();

  public BeeHiveFarm(BlockBehaviour.Properties properties) {
    super(properties);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    acceptedMobTypes = new HashSet<>(COMMON.beeHiveFarmMobs.get());
    log.info("The {} will accept the following mobs: {}", NAME, acceptedMobTypes);
  }

  @SubscribeEvent
  public static void handleWorldEventLoad(LevelEvent.Load event) {
    if (event.getLevel().isClientSide() && acceptedMobTypes.isEmpty()) {
      acceptedMobTypes = new HashSet<>(COMMON.beeHiveFarmMobs.get());
    }
  }

  public static boolean isAcceptedCapturedMobType(String mobType) {
    return acceptedMobTypes.contains(mobType);
  }

  @Override
  public Set<String> getAcceptedMobTypes() {
    return acceptedMobTypes;
  }

  @Override
  public boolean isAcceptedMobType(String mobType) {
    return acceptedMobTypes.contains(mobType);
  }

  @Override
  public String getFarmDescriptionId() {
    return "supported_animals";
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new BeeHiveFarmEntity(ModBlocks.BEE_HIVE_FARM_ENTITY.get(), blockPos, blockState);
  }

  @Override
  protected void openContainer(Level level, BlockPos blockPos, Player player) {
    if (level.getBlockEntity(blockPos) instanceof BeeHiveFarmEntity beeHiveFarmEntity) {
      player.openMenu(beeHiveFarmEntity);
    }
  }

  @Override
  public InteractionResult consumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, ItemStack itemStack, UseOnContext context) {
    BeeHiveFarmEntity beeHiveMobFarmEntity = (BeeHiveFarmEntity) blockEntity;
    beeHiveMobFarmEntity.updateLevel(level);
    if (!beeHiveMobFarmEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      beeHiveMobFarmEntity.setItem(MobFarmMenu.CAPTURED_MOB_SLOT, itemStack);
      Player player = context.getPlayer();
      if (player != null) {
        player.setItemInHand(context.getHand(), ItemStack.EMPTY);
      }
      return InteractionResult.CONSUME;
    }
    return InteractionResult.PASS;
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
      BlockEntityType<T> blockEntityType) {
    return level.isClientSide ? null
        : createTickerHelper(blockEntityType, ModBlocks.BEE_HIVE_FARM_ENTITY.get(),
            BeeHiveFarmEntity::serverTick);
  }

  @Override
  public void appendHoverTextAcceptedMobs(List<Component> tooltipList) {
    if (acceptedMobTypes != null && !acceptedMobTypes.isEmpty()) {
      // List each single possible mob types (incl. modded mobs types).
      MutableComponent mobTypeOverview = Component.literal("").withStyle(ChatFormatting.DARK_GREEN);
      for (String acceptedMob : acceptedMobTypes) {
        // We will skip he entries in the following cases:
        // 1.) Productive Bees is not loaded but entry starts with productive bees
        // 2.) Accepted Mob Types Size > 16 and Productive Bees is loaded and entry starts with
        // productive bees
        if (!((!Constants.PRODUCTIVE_BEES_LOADED && acceptedMob.startsWith("productivebees:"))
            || (acceptedMobTypes.size() >= 16 && Constants.PRODUCTIVE_BEES_LOADED
                && acceptedMob.startsWith("productivebees:")))) {
          Component acceptedMobName = TranslatableText.getEntityName(acceptedMob);
          if (!acceptedMobName.getString().isBlank()) {
            mobTypeOverview.append(acceptedMobName).append(", ")
                .withStyle(ChatFormatting.DARK_GREEN);
          }
        }
      }
      // If productive bees is loaded and list is larger then 16 add a place holder instead.
      if (Constants.PRODUCTIVE_BEES_LOADED && acceptedMobTypes.size() >= 16) {
        mobTypeOverview.append("Productive Bees").append(", ").withStyle(ChatFormatting.DARK_GREEN);
      }
      if (!mobTypeOverview.getString().isBlank()) {
        MutableComponent supportedMobsOverview =
            Component.translatable(Constants.TEXT_PREFIX + getFarmDescriptionId()).append(" ")
                .withStyle(ChatFormatting.GREEN);
        supportedMobsOverview.append(mobTypeOverview).append("...");
        tooltipList.add(supportedMobsOverview);
      }
    }
  }

}
