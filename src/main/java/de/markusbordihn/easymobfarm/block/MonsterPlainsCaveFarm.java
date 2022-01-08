
package de.markusbordihn.easymobfarm.block;

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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.farm.MonsterPlainsCaveFarmEntity;
import de.markusbordihn.easymobfarm.config.biome.PlainsCave;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;

public class MonsterPlainsCaveFarm extends MobFarmBlock {

  public static final String NAME = "monster_plains_cave_farm";

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final Set<String> acceptedMobTypes = PlainsCave.Hostile;

  public MonsterPlainsCaveFarm(BlockBehaviour.Properties properties) {
    super(properties);
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
    return new MonsterPlainsCaveFarmEntity(ModBlocks.MONSTER_PLAINS_CAVE_FARM_ENTITY.get(), blockPos, blockState);
  }

  @Override
  protected void openContainer(Level level, BlockPos blockPos, Player player) {
    if (level.getBlockEntity(blockPos) instanceof MonsterPlainsCaveFarmEntity monsterPlainsCaveFarmEntity) {
      player.openMenu(monsterPlainsCaveFarmEntity);
    }
  }

  @Override
  public InteractionResult consumeCapturedMob(Level level, BlockPos blockPos, BlockState blockState,
      BlockEntity blockEntity, ItemStack itemStack, UseOnContext context) {
    MonsterPlainsCaveFarmEntity chickenMobFarmEntity = (MonsterPlainsCaveFarmEntity) blockEntity;
    chickenMobFarmEntity.updateLevel(level);
    if (!chickenMobFarmEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      chickenMobFarmEntity.setItem(MobFarmMenu.CAPTURED_MOB_SLOT,itemStack);
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
        : createTickerHelper(blockEntityType, ModBlocks.MONSTER_PLAINS_CAVE_FARM_ENTITY.get(),
            MonsterPlainsCaveFarmEntity::serverTick);
  }

}
