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

package de.markusbordihn.easymobfarm.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import de.markusbordihn.easymobfarm.item.MobFarmBlockItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record MobFarmBlockItemTierLevel() implements RangeSelectItemModelProperty {

  public static final MapCodec<MobFarmBlockItemTierLevel> MAP_CODEC =
      MapCodec.unit(new MobFarmBlockItemTierLevel());

  @Override
  public float get(
      ItemStack itemStack,
      @Nullable ClientLevel clientLevel,
      @Nullable LivingEntity livingEntity,
      int i) {
    return MobFarmBlockItem.getTierLevel(itemStack).getTierLevel();
  }

  @Override
  public MapCodec<MobFarmBlockItemTierLevel> type() {
    return MAP_CODEC;
  }
}
