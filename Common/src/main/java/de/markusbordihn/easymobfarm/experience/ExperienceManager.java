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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExperienceManager {

  public static final String LOG_PREFIX = "[Experience Manager]";
  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static ExperienceManagerInterface experienceManagerInterface;

  private ExperienceManager() {}

  public static void registerExperienceManager(
      ExperienceManagerInterface experienceManagerInterface) {
    log.info(
        "{} Experience Manager {}",
        Constants.LOG_REGISTER_PREFIX,
        experienceManagerInterface.getClass().getSimpleName());
    ExperienceManager.experienceManagerInterface = experienceManagerInterface;
  }

  public static ExperienceManagerInterface getExperienceManager() {
    return experienceManagerInterface;
  }

  public static int getExperienceReward(LivingEntity livingEntity, ServerLevel serverLevel) {
    if (getExperienceManager() != null) {
      return getExperienceManager().getExperienceReward(livingEntity, serverLevel);
    }
    return 0;
  }

  public static boolean shouldDropExperience(LivingEntity livingEntity) {
    if (getExperienceManager() != null) {
      return getExperienceManager().shouldDropExperience(livingEntity);
    }
    return false;
  }
}
