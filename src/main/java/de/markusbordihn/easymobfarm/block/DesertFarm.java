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
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.BlockPos;
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

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.farm.DesertFarmEntity;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

@EventBusSubscriber
public class DesertFarm extends MobFarmBlock {

  public static final String NAME = "desert_farm";

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static Set<String> acceptedMobTypes = new HashSet<>();

  public DesertFarm(BlockBehaviour.Properties properties) {
    super(properties);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    acceptedMobTypes = new HashSet<>(COMMON.desertFarmMobs.get());
    log.info("The {} will accept the following mobs: {}", NAME, acceptedMobTypes);
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
  public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new DesertFarmEntity(ModBlocks.DESERT_FARM_ENTITY.get(), blockPos, blockState);
  }

  @Override
  protected void openContainer(Level level, BlockPos blockPos, Player player) {
    if (level.getBlockEntity(blockPos) instanceof DesertFarmEntity desertFarmEntity) {
      player.openMenu(desertFarmEntity);
    }
  }

  @Override
  public InteractionResult consumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, ItemStack itemStack, UseOnContext context) {
    DesertFarmEntity desertMobFarmEntity = (DesertFarmEntity) blockEntity;
    desertMobFarmEntity.updateLevel(level);
    if (!desertMobFarmEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      desertMobFarmEntity.setItem(MobFarmMenu.CAPTURED_MOB_SLOT, itemStack);
      context.getPlayer().setItemInHand(context.getHand(), ItemStack.EMPTY);
      return InteractionResult.CONSUME;
    }
    return InteractionResult.PASS;
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState,
      BlockEntityType<T> blockEntityType) {
    return level.isClientSide ? null
        : createTickerHelper(blockEntityType, ModBlocks.DESERT_FARM_ENTITY.get(),
            DesertFarmEntity::serverTick);
  }

}
