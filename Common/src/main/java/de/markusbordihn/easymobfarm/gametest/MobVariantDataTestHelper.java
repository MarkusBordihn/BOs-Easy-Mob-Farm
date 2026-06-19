/*
 * Copyright 2025 Markus Bordihn
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

package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.data.capture.MobVariantData;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.illager.Pillager;

public class MobVariantDataTestHelper {

  private MobVariantDataTestHelper() {}

  public static void testPillagerVariantDetection(GameTestHelper helper) {
    BlockPos blockPos = new BlockPos(1, 2, 1);

    Pillager pillager = (Pillager) helper.spawn(EntityTypes.PILLAGER, blockPos);
    GameTestHelpers.assertFalse(
        helper,
        "Regular Pillager should not return leader variant, got: "
            + MobVariantData.getVariant(pillager),
        MobVariantData.LEADER_VARIANT.equals(MobVariantData.getVariant(pillager)));
    pillager.discard();

    Pillager patrolLeader = (Pillager) helper.spawn(EntityTypes.PILLAGER, blockPos);
    patrolLeader.setPatrolLeader(true);
    GameTestHelpers.assertTrue(
        helper,
        "Patrol Leader Pillager should return '"
            + MobVariantData.LEADER_VARIANT
            + "' variant, got: "
            + MobVariantData.getVariant(patrolLeader),
        MobVariantData.LEADER_VARIANT.equals(MobVariantData.getVariant(patrolLeader)));
    patrolLeader.discard();
  }
}
