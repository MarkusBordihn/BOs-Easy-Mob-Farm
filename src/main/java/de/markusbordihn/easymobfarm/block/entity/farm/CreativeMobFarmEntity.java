
package de.markusbordihn.easymobfarm.block.entity.farm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.menu.farm.CreativeMobFarmMenu;

@EventBusSubscriber
public class CreativeMobFarmEntity extends MobFarmBlockEntity {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  // Config settings
  private static int creativeMobFarmProcessTime = COMMON.creativeMobFarmProcessTime.get();
  private static int farmProcessingTime = 0;

  public CreativeMobFarmEntity(BlockPos blockPos, BlockState blockState) {
    super(ModBlocks.CREATIVE_MOB_FARM_ENTITY.get(), blockPos, blockState);
  }

  public CreativeMobFarmEntity(BlockEntityType<?> blockEntity, BlockPos blockPos,
      BlockState blockState) {
    super(blockEntity, blockPos, blockState);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    creativeMobFarmProcessTime = COMMON.creativeMobFarmProcessTime.get();
    farmProcessingTime = creativeMobFarmProcessTime * 20;
    log.info("{}: CreativeMobFarm Entity with drops every {}s", Constants.LOG_MOB_FARM_PREFIX,
        creativeMobFarmProcessTime);
  }

  @Override
  protected Component getDefaultName() {
    return new TranslatableComponent("container.easy_mob_farm.creative_mob_farm");
  }

  @Override
  protected AbstractContainerMenu createMenu(int windowId, Inventory inventory) {
    return new CreativeMobFarmMenu(windowId, inventory, this, this.dataAccess);
  }

  @Override
  public int getFarmProcessingTime() {
    return farmProcessingTime;
  }

  @Override
  public void processAdditionalEffects(Level level, BlockPos blockPos,
      MobFarmBlockEntity blockEntity, ItemStack capturedMob) {
    super.processAdditionalEffects(level, blockPos, blockEntity, capturedMob);
    level.playSound(null, blockPos, SoundEvents.CHICKEN_EGG, SoundSource.BLOCKS, 1.0F, 1.0F);
  }

}
