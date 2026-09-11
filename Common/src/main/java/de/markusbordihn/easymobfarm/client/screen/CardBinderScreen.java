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

package de.markusbordihn.easymobfarm.client.screen;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.client.renderer.manager.EntityScalingManager;
import de.markusbordihn.easymobfarm.client.renderer.manager.RendererManager;
import de.markusbordihn.easymobfarm.client.screen.components.Graphics;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.menu.CardBinderMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.TagValueInput;

public class CardBinderScreen<T extends CardBinderMenu> extends AbstractContainerScreen<T> {

  private static final Identifier CHEST_GUI_TEXTURE =
      Identifier.fromNamespaceAndPath(
          Constants.MINECRAFT_PREFIX, "textures/gui/container/generic_54.png");

  private static final int BOOK_WIDTH = 280;
  private static final int BOOK_HEIGHT = 160;

  private static final int COLOR_BOOK_OUTER = 0xFF4A3728;
  private static final int COLOR_BOOK_INNER = 0xFF6B4C3B;
  private static final int COLOR_PAGE = 0xFFF5E6C8;
  private static final int COLOR_PAGE_TEXT = 0xFF4A3728;

  private int currentPage;
  private int selectedSlotIndex = -1;
  private Entity previewEntity;
  private int previewEntityHash;
  private Button prevPageButton;
  private Button nextPageButton;

  public CardBinderScreen(T menu, Inventory inventory, Component title) {
    super(menu, inventory, title, BOOK_WIDTH, 244);
  }

  @Override
  protected void init() {
    super.init();
    this.currentPage = 0;
    this.menu.setCurrentPage(0);

    int navY = this.topPos + 130;
    prevPageButton =
        Button.builder(Component.literal("<"), b -> changePage(-1))
            .pos(this.leftPos + 15, navY)
            .size(20, 20)
            .build();
    nextPageButton =
        Button.builder(Component.literal(">"), b -> changePage(1))
            .pos(this.leftPos + 105, navY)
            .size(20, 20)
            .build();
    this.addRenderableWidget(prevPageButton);
    this.addRenderableWidget(nextPageButton);
    updatePageButtons();
  }

  private void changePage(int delta) {
    int newPage = currentPage + delta;
    if (newPage >= 0 && newPage <= menu.getMaxPage()) {
      currentPage = newPage;
      selectedSlotIndex = -1;
      menu.setCurrentPage(currentPage);
      updatePageButtons();
    }
  }

  private void updatePageButtons() {
    prevPageButton.active = currentPage > 0;
    nextPageButton.active = currentPage < menu.getMaxPage();
  }

  @Override
  public void extractRenderState(
      GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
    this.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
    super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
    this.renderRightPagePreview(guiGraphics, mouseX, mouseY);
    this.extractTooltip(guiGraphics, mouseX, mouseY);
  }

  protected void renderBg(
      GuiGraphicsExtractor guiGraphics, float partialTicks, int mouseX, int mouseY) {
    int x = this.leftPos;
    int y = this.topPos;

    renderInventorySlotFrames(guiGraphics, x, y);

    // Book background
    guiGraphics.fill(x, y, x + BOOK_WIDTH, y + BOOK_HEIGHT, COLOR_BOOK_OUTER);
    guiGraphics.fill(x + 3, y + 3, x + BOOK_WIDTH - 3, y + BOOK_HEIGHT - 3, COLOR_BOOK_INNER);
    guiGraphics.fill(x + 8, y + 8, x + 133, y + BOOK_HEIGHT - 8, COLOR_PAGE);
    guiGraphics.fill(x + 147, y + 8, x + BOOK_WIDTH - 8, y + BOOK_HEIGHT - 8, COLOR_PAGE);

    // Spine gradient
    guiGraphics.fill(x + 133, y + 4, x + 136, y + BOOK_HEIGHT - 4, 0xFF4A3420);
    guiGraphics.fill(x + 136, y + 4, x + 138, y + BOOK_HEIGHT - 4, 0xFF3A2718);
    guiGraphics.fill(x + 138, y + 4, x + 142, y + BOOK_HEIGHT - 4, 0xFF2E1E12);
    guiGraphics.fill(x + 142, y + 4, x + 144, y + BOOK_HEIGHT - 4, 0xFF3A2718);
    guiGraphics.fill(x + 144, y + 4, x + 147, y + BOOK_HEIGHT - 4, 0xFF4A3420);

    renderCardSlotPockets(guiGraphics, x, y);
    renderSelectedHighlight(guiGraphics, x, y);
  }

  private void renderCardSlotPockets(GuiGraphicsExtractor guiGraphics, int x, int y) {
    Minecraft mc = Minecraft.getInstance();
    for (int i = 0; i < CardBinderMenu.CONTAINER_SIZE; i++) {
      Slot slot = this.menu.getSlot(i);
      if (!slot.isActive()) continue;
      int sx = x + slot.x - 1;
      int sy = y + slot.y - 1;

      int bgColor = 0xFFE0D0B0;
      int borderColor = 0xFFA08060;
      if (slot.hasItem() && mc.level != null) {
        MobCaptureData data = MobCaptureManager.getMobCaptureData(slot.getItem(), mc.level);
        if (data != null && data.rarity() != null) {
          switch (data.rarity()) {
            case UNCOMMON -> {
              bgColor = 0xFFD0E8C0;
              borderColor = 0xFF5A9040;
            }
            case RARE -> {
              bgColor = 0xFFC0D0E8;
              borderColor = 0xFF4060A0;
            }
            case EPIC -> {
              bgColor = 0xFFD8C0E8;
              borderColor = 0xFF8040A0;
            }
            default -> {}
          }
        }
      }

      guiGraphics.fill(sx, sy, sx + 18, sy + 18, bgColor);
      guiGraphics.fill(sx - 1, sy + 3, sx, sy + 19, borderColor);
      guiGraphics.fill(sx + 18, sy + 3, sx + 19, sy + 19, borderColor);
      guiGraphics.fill(sx - 1, sy + 18, sx + 19, sy + 19, borderColor);
    }
  }

  private void renderSelectedHighlight(GuiGraphicsExtractor guiGraphics, int x, int y) {
    if (selectedSlotIndex < 0 || selectedSlotIndex >= CardBinderMenu.CONTAINER_SIZE) return;
    Slot slot = this.menu.getSlot(selectedSlotIndex);
    if (!slot.isActive() || !slot.hasItem()) return;
    guiGraphics.fill(x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, 0x6055FFFF);
  }

  private void renderInventorySlotFrames(GuiGraphicsExtractor guiGraphics, int x, int y) {
    Graphics.blit(
        guiGraphics,
        CHEST_GUI_TEXTURE,
        x + CardBinderMenu.PLAYER_INV_X - 8,
        y + CardBinderMenu.PLAYER_INV_Y - 15,
        0,
        125,
        176,
        96);
  }

  @Override
  protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
    guiGraphics.text(this.font, this.title, 12, 12, COLOR_PAGE_TEXT, false);

    int cardCount = 0;
    for (int i = 0; i < CardBinderMenu.CONTAINER_SIZE; i++) {
      if (this.menu.getSlot(i).hasItem()) cardCount++;
    }
    String countText = cardCount + "/" + CardBinderMenu.CONTAINER_SIZE;
    guiGraphics.text(this.font, countText, 128 - this.font.width(countText), 12, 0xFF808080, false);

    String pageText = (currentPage + 1) + "/" + (menu.getMaxPage() + 1);
    guiGraphics.text(
        this.font, pageText, 70 - this.font.width(pageText) / 2, 136, COLOR_PAGE_TEXT, false);

    renderCardInfo(guiGraphics);
  }

  private void renderCardInfo(GuiGraphicsExtractor guiGraphics) {
    Minecraft mc = Minecraft.getInstance();
    if (selectedSlotIndex < 0 || selectedSlotIndex >= CardBinderMenu.CONTAINER_SIZE) {
      String hint = "Hover a card";
      guiGraphics.text(this.font, hint, 210 - this.font.width(hint) / 2, 75, 0xFFA09080, false);
      return;
    }
    Slot slot = this.menu.getSlot(selectedSlotIndex);
    if (!slot.hasItem()) return;

    ItemStack cardStack = slot.getItem();
    if (mc.level == null) return;
    MobCaptureData data = MobCaptureManager.getMobCaptureData(cardStack, mc.level);
    if (data == null) return;

    int rightX = 155;
    drawWordWrap(
        guiGraphics, cardStack.getHoverName().getString(), rightX, 14, 110, COLOR_PAGE_TEXT);

    int infoY = 110;
    if (data.rarity() != null) {
      int color =
          switch (data.rarity()) {
            case UNCOMMON -> 0xFF55FF55;
            case RARE -> 0xFF5555FF;
            case EPIC -> 0xFFAA00AA;
            default -> 0xFF808080;
          };
      guiGraphics.text(this.font, data.rarity().name(), rightX, infoY, color, false);
      infoY += 11;
    }
    if (data.type() != null) {
      String typeText =
          data.type().length() > 20 ? data.type().substring(0, 20) + "..." : data.type();
      guiGraphics.text(this.font, typeText, rightX, infoY, 0xFF808080, false);
      infoY += 11;
    }
    if (data.hasVariant()) {
      guiGraphics.text(this.font, data.variant(), rightX, infoY, 0xFF808080, false);
      infoY += 11;
    }
    if (data.hasColor()) {
      guiGraphics.text(this.font, data.color().getName(), rightX, infoY, 0xFF808080, false);
      infoY += 11;
    }
    guiGraphics.text(this.font, "#" + data.getCardId(), rightX, infoY, 0xFFA0A0A0, false);
  }

  private void drawWordWrap(
      GuiGraphicsExtractor guiGraphics, String text, int x, int y, int maxWidth, int color) {
    if (this.font.width(text) <= maxWidth) {
      guiGraphics.text(this.font, text, x, y, color, false);
      return;
    }
    StringBuilder line = new StringBuilder();
    int lineY = y;
    for (String word : text.split(" ")) {
      if (this.font.width(line + word) > maxWidth && !line.isEmpty()) {
        guiGraphics.text(this.font, line.toString().trim(), x, lineY, color, false);
        lineY += 10;
        line = new StringBuilder();
      }
      line.append(word).append(" ");
    }
    if (!line.isEmpty()) {
      guiGraphics.text(this.font, line.toString().trim(), x, lineY, color, false);
    }
  }

  private void renderRightPagePreview(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
    for (int i = 0; i < CardBinderMenu.CONTAINER_SIZE; i++) {
      Slot slot = this.menu.getSlot(i);
      if (slot.isActive() && slot.hasItem() && isHovering(slot.x, slot.y, 16, 16, mouseX, mouseY)) {
        selectedSlotIndex = i;
        break;
      }
    }

    if (selectedSlotIndex < 0 || selectedSlotIndex >= CardBinderMenu.CONTAINER_SIZE) return;
    Slot slot = this.menu.getSlot(selectedSlotIndex);
    if (!slot.hasItem()) {
      selectedSlotIndex = -1;
      return;
    }
    updatePreviewEntity(slot.getItem());
    if (previewEntity instanceof LivingEntity livingEntity) {
      float scale = EntityScalingManager.getUIScale(previewEntity) * 2.4f;
      int scaledSize = Math.round(scale);

      int entityAreaLeft = this.leftPos + 165;
      int entityAreaTop = this.topPos + 18;
      int entityAreaRight = this.leftPos + 255;
      int entityAreaBottom = this.topPos + 110;

      float entityHeight = livingEntity.getBbHeight();
      float yOffset = entityHeight < 1.5F ? 0.5F : 0.0625F;

      InventoryScreen.extractEntityInInventoryFollowsMouse(
          guiGraphics,
          entityAreaLeft,
          entityAreaTop,
          entityAreaRight,
          entityAreaBottom,
          scaledSize,
          yOffset,
          mouseX,
          mouseY,
          livingEntity);
    }
  }

  private void updatePreviewEntity(ItemStack cardStack) {
    int hash =
        cardStack.isEmpty()
            ? 0
            : System.identityHashCode(cardStack.getItem()) + cardStack.getComponents().hashCode();
    if (hash == previewEntityHash && previewEntity != null) return;

    if (previewEntity != null) {
      previewEntity.discard();
      previewEntity = null;
    }
    Minecraft mc = Minecraft.getInstance();
    if (mc.level == null) return;
    MobCaptureData data = MobCaptureManager.getMobCaptureData(cardStack, mc.level);
    if (data != null && data.entityType() != null) {
      previewEntity = data.entityType().create(mc.level, EntitySpawnReason.EVENT);
      if (previewEntity != null) {
        RendererManager.assignRenderEntityId(previewEntity);
        if (data.hasData()) {
          try {
            previewEntity.load(
                TagValueInput.create(
                    ProblemReporter.DISCARDING, mc.level.registryAccess(), data.data()));
          } catch (Exception ignored) {
          }
        }
      }
    }
    previewEntityHash = hash;
  }

  @Override
  public void removed() {
    super.removed();
    if (previewEntity != null) {
      previewEntity.discard();
      previewEntity = null;
    }
  }
}
