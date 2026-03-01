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

package de.markusbordihn.easymobfarm.item.cardbinder;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.MobFarmItem;
import de.markusbordihn.easymobfarm.menu.CardBinderMenu;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.function.Consumer;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

public class CardBinderItem extends MobFarmItem {

  public static final String ID = "card_binder";
  public static final int CONTAINER_SIZE = 250;

  public CardBinderItem() {
    this(
        new Properties()
            .setId(
                ResourceKey.create(
                    Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, ID))));
  }

  public CardBinderItem(Properties properties) {
    super(properties.stacksTo(1));
  }

  public static SimpleContainer loadCards(ItemStack binderStack) {
    SimpleContainer container = new SimpleContainer(CONTAINER_SIZE);
    ItemContainerContents contents =
        binderStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    contents.copyInto(container.getItems());
    return container;
  }

  public static void saveCards(ItemStack binderStack, SimpleContainer container) {
    NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);
    for (int i = 0; i < container.getContainerSize(); i++) {
      items.set(i, container.getItem(i));
    }
    binderStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(items));
  }

  @Override
  public InteractionResult use(Level level, Player player, InteractionHand hand) {
    ItemStack itemStack = player.getItemInHand(hand);
    if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
      serverPlayer.openMenu(new CardBinderMenuProvider(itemStack));
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public void appendHoverText(
      ItemStack itemStack,
      TooltipContext tooltipContext,
      TooltipDisplay tooltipDisplay,
      Consumer<Component> tooltipConsumer,
      TooltipFlag tooltipFlag) {
    ItemContainerContents contents =
        itemStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    int cardCount = (int) contents.stream().filter(stack -> !stack.isEmpty()).count();
    addTooltip(
        tooltipConsumer,
        TextComponent.getTranslatedTextRaw(
            Constants.TOOLTIP_PREFIX + ID, new Object[] {cardCount, CONTAINER_SIZE}));
  }

  private record CardBinderMenuProvider(ItemStack binderStack) implements MenuProvider {

    @Override
    public Component getDisplayName() {
      return TextComponent.getTranslatedTextRaw(Constants.TEXT_PREFIX + CardBinderItem.ID);
    }

    @Override
    public AbstractContainerMenu createMenu(
        int containerId, Inventory playerInventory, Player player) {
      return new CardBinderMenu(
          CardBinderMenu.MENU_TYPE_SUPPLIER.get(), containerId, playerInventory, binderStack);
    }
  }
}
