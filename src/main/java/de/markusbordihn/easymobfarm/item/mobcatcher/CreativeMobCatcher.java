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

package de.markusbordihn.easymobfarm.item.mobcatcher;

import de.markusbordihn.easymobfarm.item.MobCatcherItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class CreativeMobCatcher extends MobCatcherItem {

  public static final String NAME = "Creative Mob Catcher";

  public CreativeMobCatcher(Item.Properties properties) {
    super(properties);
  }

  @Override
  public boolean isAcceptedMob(LivingEntity livingEntity) {
    if (livingEntity instanceof LivingEntity) {
      return true;
    }
    log.warn("Mob {} is not supported by {}.", livingEntity, this);
    return false;
  }

  @Override
  public boolean isAcceptedMobType(String mobType) {
    return true;
  }

  @Override
  public int getMobCatchingLuck() {
    return 0;
  }

  @Override
  public int getMobCatchingLuckConfig() {
    return 0;
  }

  @Override
  public UseAnim getUseAnimation(ItemStack itemStack) {
    return UseAnim.CROSSBOW;
  }

  @Override
  public String getMobCatcherItemName() {
    return NAME;
  }

}
