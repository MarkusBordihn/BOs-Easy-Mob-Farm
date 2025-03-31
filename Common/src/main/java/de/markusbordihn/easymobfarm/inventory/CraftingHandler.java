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

package de.markusbordihn.easymobfarm.inventory;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardIngredientItem;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import java.util.Optional;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CraftingHandler {

  private CraftingHandler() {}

  public static void handleCraftingMenu(
      CraftingMenu craftingMenu, CraftingContainer craftingContainer) {

    // Check if player is using a crafting table and replace mob capture card with mob capture card
    // ingredient for crafting recipe.
    for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
      ItemStack itemStack = craftingContainer.getItem(i);
      if (itemStack.isEmpty() || !(itemStack.getItem() instanceof MobCaptureCardItem)) {
        continue;
      }
      MobCaptureData mobCaptureData = MobCaptureManager.getMobCaptureData(itemStack, null);
      if (mobCaptureData == null) {
        continue;
      }
      if (mobCaptureData.entityType() == EntityType.FROG) {

        String frogVariant = mobCaptureData.variant();
        Optional<Reference<Item>> ingredientItem = null;
        switch (frogVariant) {
          case "cold":
            ingredientItem =
                BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID, MobCaptureCardIngredientItem.ID_FROG_COLD));
            break;
          case "temperate":
            ingredientItem =
                BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID, MobCaptureCardIngredientItem.ID_FROG_TEMPERATE));
            break;
          case "warm":
            ingredientItem =
                BuiltInRegistries.ITEM.get(
                    ResourceLocation.fromNamespaceAndPath(
                        Constants.MOD_ID, MobCaptureCardIngredientItem.ID_FROG_WARM));
            break;
        }
        if (ingredientItem != null && ingredientItem.isPresent()) {
          ItemStack ingredientItemStack =
              MobCaptureCardIngredientItem.saveOriginalItemStack(
                  itemStack, new ItemStack(ingredientItem.get().value()));
          if (!ingredientItemStack.isEmpty()) {
            craftingContainer.setItem(i, ingredientItemStack);
            craftingMenu.slotsChanged(craftingContainer);
          }
        }
      }
    }
  }

  public static void handlePlayerInventory(ServerPlayer serverPlayer) {
    // Check if we have transformed items and replace it with the original item.
    Inventory playerInventory = serverPlayer.getInventory();
    int containerSize =
        playerInventory.getContainerSize() - Inventory.EQUIPMENT_SLOT_MAPPING.size();
    for (int i = 0; i < containerSize; i++) {
      ItemStack itemStack = playerInventory.getItem(i);
      if (itemStack.isEmpty() || !(itemStack.getItem() instanceof MobCaptureCardIngredientItem)) {
        continue;
      }
      Optional<Reference<Item>> orginalItem =
          BuiltInRegistries.ITEM.get(
              ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, MobCaptureCardItem.ID));
      if (orginalItem.isPresent()) {
        ItemStack originalItemStack =
            MobCaptureCardIngredientItem.restoreOriginalItemStack(
                itemStack, new ItemStack(orginalItem.get().value()));
        if (!originalItemStack.isEmpty()) {
          playerInventory.setItem(i, originalItemStack);
          playerInventory.setChanged();
        }
      }
    }
  }
}
