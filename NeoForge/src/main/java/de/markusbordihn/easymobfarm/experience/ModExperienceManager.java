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

import java.lang.reflect.Method;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;

public class ModExperienceManager implements ExperienceManagerInterface {

  @Override
  public int getExperienceReward(LivingEntity livingEntity) {
    if (livingEntity instanceof Animal animal) {
      try {
        Method getBaseExperienceRewardMethod =
            findMethodInHierarchy(animal.getClass(), "getBaseExperienceReward");
        getBaseExperienceRewardMethod.setAccessible(true);
        return (int) getBaseExperienceRewardMethod.invoke(livingEntity);
      } catch (Exception e) {
        // no base experience reward found
      }
    } else if (livingEntity instanceof Mob mob) {
      try {
        Method getBaseExperienceRewardMethod =
            findMethodInHierarchy(mob.getClass(), "getBaseExperienceReward");
        getBaseExperienceRewardMethod.setAccessible(true);
        return (int) getBaseExperienceRewardMethod.invoke(livingEntity);
      } catch (Exception e) {
        // no base experience reward found
      }
    }
    try {
      Method getBaseExperienceRewardMethod =
          findMethodInHierarchy(livingEntity.getClass(), "getBaseExperienceReward");
      getBaseExperienceRewardMethod.setAccessible(true);
      return (int) getBaseExperienceRewardMethod.invoke(livingEntity);
    } catch (Exception e) {
      return 0;
    }
  }

  private Method findMethodInHierarchy(Class<?> clazz, String methodName) {
    while (clazz != null) {
      try {
        return clazz.getDeclaredMethod(methodName);
      } catch (NoSuchMethodException e) {
        clazz = clazz.getSuperclass();
      }
    }
    return null;
  }
}
