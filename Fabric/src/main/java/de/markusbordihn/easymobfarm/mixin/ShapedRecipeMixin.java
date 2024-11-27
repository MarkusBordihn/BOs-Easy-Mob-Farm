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

import de.markusbordihn.easymobfarm.Constants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public abstract class ShapedRecipeMixin implements CraftingRecipe {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @Inject(
      method = "getResultItem()Lnet/minecraft/world/item/ItemStack;",
      at = @At("RETURN"),
      cancellable = true)
  public void easyMobFarmAssemble(CallbackInfoReturnable<ItemStack> callbackInfo) {
    // Make sure that we only modify our own recipes.
    ItemStack result = callbackInfo.getReturnValue();
    ShapedRecipe shapedRecipe = (ShapedRecipe) (Object) this;
    if (result == null
        || result.isEmpty()
        || shapedRecipe == null
        || shapedRecipe.getId() == null
        || !shapedRecipe.getId().getNamespace().equals(Constants.MOD_ID)) {
      return;
    }

    // We are only interested in mob farm recipes.
    String path = shapedRecipe.getId().getPath();
    if (!path.startsWith("mob_farm/")) {
      return;
    }
    int tierLevel = -1;

    // Get tier level from recipe path.
    if (path.contains("/tier0_")) {
      tierLevel = 0;
    } else if (path.contains("/tier1_")) {
      tierLevel = 1;
    } else if (path.contains("/tier2_")) {
      tierLevel = 2;
    } else if (path.contains("/tier3_")) {
      tierLevel = 3;
    }

    // Add custom model data and tier level to the item.
    if (tierLevel != -1) {
      CompoundTag tag = result.getOrCreateTag();
      if (!tag.contains("TierLevel")) {
        tag.putInt("TierLevel", tierLevel);
      }
      if (!tag.contains("CustomModelData")) {
        tag.putInt("CustomModelData", tierLevel);
      }
      callbackInfo.setReturnValue(result.copy());
    }
  }
}
