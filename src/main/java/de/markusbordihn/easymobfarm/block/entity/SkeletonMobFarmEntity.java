
package de.markusbordihn.easymobfarm.block.entity;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.config.CommonConfig;

@EventBusSubscriber
public class SkeletonMobFarmEntity extends MobFarmBlockEntity {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  // Config settings
  private static int skeletonFarmProcessTime = COMMON.skeletonFarmProcessTimeProcessTime.get();
  private static int farmProcessingTime = 0;

  public SkeletonMobFarmEntity(BlockPos blockPos, BlockState blockState) {
    super(ModBlocks.SKELETON_MOB_FARM_ENTITY.get(), blockPos, blockState);
  }

  public SkeletonMobFarmEntity(BlockEntityType<?> blockEntity, BlockPos blockPos,
      BlockState blockState) {
    super(blockEntity, blockPos, blockState);
  }

  @Override
  protected Component getDefaultName() {
    return new TranslatableComponent("container.easy_mob_farm.skeleton_mob_farm");
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    skeletonFarmProcessTime = COMMON.skeletonFarmProcessTimeProcessTime.get();
    farmProcessingTime = skeletonFarmProcessTime * 20;
    log.info("{}: Skeleton Mob Farm with drops every {}s", Constants.LOG_MOB_FARM_PREFIX,
        skeletonFarmProcessTime);
  }

  @Override
  public int getFarmProcessingTime() {
    return farmProcessingTime;
  }

  @Override
  public void processAdditionalEffects(Level level, BlockPos blockPos,
      MobFarmBlockEntity blockEntity, ItemStack capturedMob) {
    super.processAdditionalEffects(level, blockPos, blockEntity, capturedMob);
    level.playSound(null, blockPos, SoundEvents.SKELETON_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
  }

}
