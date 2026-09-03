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

package de.markusbordihn.easymobfarm.data.mobfarm;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import net.minecraft.world.inventory.ContainerData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobFarmContainerData implements ContainerData {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private final MobFarmBlockEntity mobFarmEntity;

  public MobFarmContainerData(MobFarmBlockEntity mobFarmEntity) {
    this.mobFarmEntity = mobFarmEntity;
  }

  @Override
  public int get(final int index) {
    return switch (index) {
      case MobFarmDataEntry.BLOCK_POS_X -> mobFarmEntity.getBlockPos().getX();
      case MobFarmDataEntry.BLOCK_POS_Y -> mobFarmEntity.getBlockPos().getY();
      case MobFarmDataEntry.BLOCK_POS_Z -> mobFarmEntity.getBlockPos().getZ();
      case MobFarmDataEntry.NUMBER_OF_OUTPUT_SLOTS -> mobFarmEntity.getNumberOfOutputSlots();
      case MobFarmDataEntry.FARM_PROGRESS -> mobFarmEntity.getFarmProgress();
      case MobFarmDataEntry.FARM_PROGRESSION_SPEED -> mobFarmEntity.getFarmProgressionSpeed();
      case MobFarmDataEntry.FARM_PROGRESSION_SPEED_BONUS ->
          mobFarmEntity.getFarmProgressionSpeedBonus();
      case MobFarmDataEntry.FARM_STATUS -> mobFarmEntity.getFarmStatus();
      case MobFarmDataEntry.FARM_TIER_LEVEL -> mobFarmEntity.getFarmTierLevel();
      case MobFarmDataEntry.FARM_TYPE -> mobFarmEntity.getFarmType().ordinal();
      case MobFarmDataEntry.CAPTURED_MOB_EXPERIENCE -> mobFarmEntity.getCapturedMobExperience();
      case MobFarmDataEntry.BUFFER_SIZE -> mobFarmEntity.getBufferSize();
      case MobFarmDataEntry.BUFFER_MAX_SIZE ->
          de.markusbordihn.easymobfarm.config.MobFarmConfig.maxBufferSize;
      default -> 0;
    };
  }

  @Override
  public void set(final int index, final int value) {
    switch (index) {
      case MobFarmDataEntry.BLOCK_POS_X,
          MobFarmDataEntry.BLOCK_POS_Y,
          MobFarmDataEntry.BLOCK_POS_Z,
          MobFarmDataEntry.FARM_TIER_LEVEL,
          MobFarmDataEntry.FARM_TYPE,
          MobFarmDataEntry.CAPTURED_MOB_EXPERIENCE,
          MobFarmDataEntry.FARM_PROGRESSION_SPEED,
          MobFarmDataEntry.FARM_PROGRESSION_SPEED_BONUS,
          MobFarmDataEntry.BUFFER_SIZE,
          MobFarmDataEntry.BUFFER_MAX_SIZE ->
          log.warn("Trying to set read-only data index {} for {}", index, this);
      case MobFarmDataEntry.NUMBER_OF_OUTPUT_SLOTS -> mobFarmEntity.setNumberOfOutputSlots(value);
      case MobFarmDataEntry.FARM_PROGRESS -> mobFarmEntity.setFarmProgress(value);
      case MobFarmDataEntry.FARM_STATUS -> mobFarmEntity.setFarmStatus(value);
      default -> log.error("Unknown data index {}", index);
    }
  }

  @Override
  public int getCount() {
    return MobFarmMenu.CONTAINER_DATA_SIZE;
  }
}
