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

package de.markusbordihn.easymobfarm.mixin;

import de.markusbordihn.easymobfarm.entity.FishingEvents;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingHook.class)
public class FishingHookMixin {

  @Inject(
      method = "retrieve",
      at =
          @At(
              value = "INVOKE",
              target =
                  "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Ljava/util/List;",
              shift = At.Shift.AFTER))
  private void easyMobFarmOnRetrieve(
      ItemStack itemStack, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
    if (itemStack == null || itemStack.isEmpty()) {
      return;
    }

    FishingHook fishingHook = (FishingHook) (Object) this;
    if (!(fishingHook.getPlayerOwner() instanceof ServerPlayer serverPlayer)
        || serverPlayer.level.isClientSide) {
      return;
    }

    // Because we have no direct access to the loot table, we have to reproduce the loot table
    // behavior to get the similar loot.
    LootContext.Builder builder =
        new LootContext.Builder((ServerLevel) fishingHook.level)
            .withParameter(LootContextParams.ORIGIN, fishingHook.position())
            .withParameter(LootContextParams.TOOL, itemStack)
            .withParameter(LootContextParams.THIS_ENTITY, fishingHook)
            .withRandom(fishingHook.random)
            .withLuck(fishingHook.luck + serverPlayer.getLuck());

    LootTable lootTable =
        fishingHook.level.getServer().getLootTables().get(BuiltInLootTables.FISHING);
    List<ItemStack> reproducedLoot =
        lootTable.getRandomItems(builder.create(LootContextParamSets.FISHING));
    if (!reproducedLoot.isEmpty()) {
      FishingEvents.handleItemFishedEvent(serverPlayer, reproducedLoot);
    }
  }
}
