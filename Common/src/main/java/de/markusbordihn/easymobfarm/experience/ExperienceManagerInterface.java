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

package de.markusbordihn.easymobfarm.experience;

import de.markusbordihn.easymobfarm.Constants;
import java.lang.reflect.Method;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface ExperienceManagerInterface {

  Logger log = LogManager.getLogger(Constants.LOG_NAME);

  default int getExperienceReward(LivingEntity livingEntity, ServerLevel serverLevel) {
    return getExperienceReward(livingEntity, serverLevel, null);
  }

  default boolean shouldDropExperience(LivingEntity livingEntity) {
    try {
      return livingEntity.shouldDropExperience();
    } catch (Exception e) {
      log.error(
          "{} Failed to check if entity should drop experience for {}",
          ExperienceManager.LOG_PREFIX,
          livingEntity.getClass().getSimpleName(),
          e);
      return false;
    }
  }

  default int getExperienceReward(
      LivingEntity livingEntity, ServerLevel serverLevel, String methodName) {
    try {
      Method getBaseExperienceRewardMethod =
          LivingEntity.class.getDeclaredMethod("getBaseExperienceReward", ServerLevel.class);
      getBaseExperienceRewardMethod.setAccessible(true);
      return (int) getBaseExperienceRewardMethod.invoke(livingEntity, serverLevel);
    } catch (NoSuchMethodException e) {
      if (methodName != null && !methodName.isEmpty()) {
        try {
          Method getBaseExperienceRewardMethod =
              LivingEntity.class.getDeclaredMethod(methodName, ServerLevel.class);
          getBaseExperienceRewardMethod.setAccessible(true);
          return (int) getBaseExperienceRewardMethod.invoke(livingEntity, serverLevel);
        } catch (Exception ex) {
          log.error(
              "{} Failed to get experience reward for {} with method {}",
              ExperienceManager.LOG_PREFIX,
              livingEntity.getClass().getSimpleName(),
              methodName,
              ex);
        }
      } else {
        log.error(
            "{} Failed to get experience reward for {}",
            ExperienceManager.LOG_PREFIX,
            livingEntity.getClass().getSimpleName(),
            e);
      }
    } catch (Exception e) {
      log.error(
          "{} Failed to get experience reward for {}",
          ExperienceManager.LOG_PREFIX,
          livingEntity.getClass().getSimpleName(),
          e);
      return 0;
    }
    log.error(
        "{} Failed to get experience reward for {}",
        ExperienceManager.LOG_PREFIX,
        livingEntity.getClass().getSimpleName());
    return 0;
  }
}
