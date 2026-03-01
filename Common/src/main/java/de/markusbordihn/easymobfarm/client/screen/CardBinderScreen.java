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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.client.renderer.manager.EntityScalingManager;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.menu.CardBinderMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CardBinderScreen<T extends CardBinderMenu> extends AbstractContainerScreen<T> {

  private static final ResourceLocation CHEST_GUI_TEXTURE =
      new ResourceLocation(Constants.MINECRAFT_PREFIX, "textures/gui/container/generic_54.png");

  private static final int BOOK_WIDTH = 280;
  private static final int BOOK_HEIGHT = 160;

  private static final int COLOR_BOOK_OUTER = 0xFF4A3728;
  private static final int COLOR_BOOK_INNER = 0xFF6B4C3B;
  private static final int COLOR_PAGE = 0xFFF5E6C8;
  private static final int COLOR_PAGE_TEXT = 0x4A3728;

  private int currentPage;
  private int selectedSlotIndex = -1;
  private Entity previewEntity;
  private int previewEntityHash;
  private Button prevPageButton;
  private Button nextPageButton;

  public CardBinderScreen(T menu, Inventory inventory, Component title) {
    super(menu, inventory, title);
    this.imageWidth = BOOK_WIDTH;
    this.imageHeight = 244;
  }

  @Override
  protected void init() {
    super.init();
    this.currentPage = 0;
    this.menu.setCurrentPage(0);

    int navY = this.topPos + 130;
    prevPageButton =
        new Button(
            this.leftPos + 15,
            navY,
            20,
            20,
            new net.minecraft.network.chat.TextComponent("<"),
            b -> changePage(-1));
    nextPageButton =
        new Button(
            this.leftPos + 105,
            navY,
            20,
            20,
            new net.minecraft.network.chat.TextComponent(">"),
            b -> changePage(1));
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
  public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
    this.renderBackground(poseStack);
    super.render(poseStack, mouseX, mouseY, partialTicks);
    renderRightPagePreview(poseStack, mouseX, mouseY);
    this.renderTooltip(poseStack, mouseX, mouseY);
  }

  @Override
  protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY) {
    int x = this.leftPos;
    int y = this.topPos;

    renderInventorySlotFrames(poseStack, x, y);

    // Book background
    fill(poseStack, x, y, x + BOOK_WIDTH, y + BOOK_HEIGHT, COLOR_BOOK_OUTER);
    fill(poseStack, x + 3, y + 3, x + BOOK_WIDTH - 3, y + BOOK_HEIGHT - 3, COLOR_BOOK_INNER);
    fill(poseStack, x + 8, y + 8, x + 133, y + BOOK_HEIGHT - 8, COLOR_PAGE);
    fill(poseStack, x + 147, y + 8, x + BOOK_WIDTH - 8, y + BOOK_HEIGHT - 8, COLOR_PAGE);

    // Spine gradient
    fill(poseStack, x + 133, y + 4, x + 136, y + BOOK_HEIGHT - 4, 0xFF4A3420);
    fill(poseStack, x + 136, y + 4, x + 138, y + BOOK_HEIGHT - 4, 0xFF3A2718);
    fill(poseStack, x + 138, y + 4, x + 142, y + BOOK_HEIGHT - 4, 0xFF2E1E12);
    fill(poseStack, x + 142, y + 4, x + 144, y + BOOK_HEIGHT - 4, 0xFF3A2718);
    fill(poseStack, x + 144, y + 4, x + 147, y + BOOK_HEIGHT - 4, 0xFF4A3420);

    renderCardSlotPockets(poseStack, x, y);
    renderSelectedHighlight(poseStack, x, y);
  }

  private void renderCardSlotPockets(PoseStack poseStack, int x, int y) {
    for (int i = 0; i < CardBinderMenu.CONTAINER_SIZE; i++) {
      Slot slot = this.menu.getSlot(i);
      if (!slot.isActive()) continue;
      int sx = x + slot.x - 1;
      int sy = y + slot.y - 1;

      int bgColor = 0xFFE0D0B0;
      int borderColor = 0xFFA08060;
      if (slot.hasItem()) {
        MobCaptureData data = MobCaptureManager.getMobCaptureData(slot.getItem());
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

      fill(poseStack, sx, sy, sx + 18, sy + 18, bgColor);
      fill(poseStack, sx - 1, sy + 3, sx, sy + 19, borderColor);
      fill(poseStack, sx + 18, sy + 3, sx + 19, sy + 19, borderColor);
      fill(poseStack, sx - 1, sy + 18, sx + 19, sy + 19, borderColor);
    }
  }

  private void renderSelectedHighlight(PoseStack poseStack, int x, int y) {
    if (selectedSlotIndex < 0 || selectedSlotIndex >= CardBinderMenu.CONTAINER_SIZE) return;
    Slot slot = this.menu.getSlot(selectedSlotIndex);
    if (!slot.isActive() || !slot.hasItem()) return;
    fill(poseStack, x + slot.x - 1, y + slot.y - 1, x + slot.x + 17, y + slot.y + 17, 0x6055FFFF);
  }

  private void renderInventorySlotFrames(PoseStack poseStack, int x, int y) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, CHEST_GUI_TEXTURE);
    GuiComponent.blit(poseStack, x + 51, y + 153, 0, 125, 176, 96, 256, 256);
  }

  @Override
  protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
    this.font.draw(poseStack, this.title, 12, 12, COLOR_PAGE_TEXT);

    int cardCount = 0;
    for (int i = 0; i < CardBinderMenu.CONTAINER_SIZE; i++) {
      if (this.menu.getSlot(i).hasItem()) cardCount++;
    }
    String countText = cardCount + "/" + CardBinderMenu.CONTAINER_SIZE;
    this.font.draw(poseStack, countText, 128 - this.font.width(countText), 12, 0x808080);

    String pageText = (currentPage + 1) + "/" + (menu.getMaxPage() + 1);
    this.font.draw(poseStack, pageText, 70 - this.font.width(pageText) / 2, 136, COLOR_PAGE_TEXT);

    renderCardInfo(poseStack);
  }

  private void renderCardInfo(PoseStack poseStack) {
    if (selectedSlotIndex < 0 || selectedSlotIndex >= CardBinderMenu.CONTAINER_SIZE) {
      String hint = "Hover a card";
      this.font.draw(poseStack, hint, 210 - this.font.width(hint) / 2, 75, 0xA09080);
      return;
    }
    Slot slot = this.menu.getSlot(selectedSlotIndex);
    if (!slot.hasItem()) return;

    ItemStack cardStack = slot.getItem();
    MobCaptureData data = MobCaptureManager.getMobCaptureData(cardStack);
    if (data == null) return;

    int rightX = 155;
    drawWordWrap(poseStack, cardStack.getHoverName().getString(), rightX, 14, 110, COLOR_PAGE_TEXT);

    int infoY = 110;
    if (data.rarity() != null) {
      int color =
          switch (data.rarity()) {
            case UNCOMMON -> 0x55FF55;
            case RARE -> 0x5555FF;
            case EPIC -> 0xAA00AA;
            default -> 0x808080;
          };
      this.font.draw(poseStack, data.rarity().name(), rightX, infoY, color);
      infoY += 11;
    }
    if (data.type() != null) {
      String typeText =
          data.type().length() > 20 ? data.type().substring(0, 20) + "..." : data.type();
      this.font.draw(poseStack, typeText, rightX, infoY, 0x808080);
      infoY += 11;
    }
    if (data.hasVariant()) {
      this.font.draw(poseStack, data.variant(), rightX, infoY, 0x808080);
      infoY += 11;
    }
    if (data.hasColor()) {
      this.font.draw(poseStack, data.color().getName(), rightX, infoY, 0x808080);
      infoY += 11;
    }
    this.font.draw(poseStack, "#" + data.getCardId(), rightX, infoY, 0xA0A0A0);
  }

  private void drawWordWrap(
      PoseStack poseStack, String text, int x, int y, int maxWidth, int color) {
    if (this.font.width(text) <= maxWidth) {
      this.font.draw(poseStack, text, x, y, color);
      return;
    }
    StringBuilder line = new StringBuilder();
    int lineY = y;
    for (String word : text.split(" ")) {
      if (this.font.width(line + word) > maxWidth && line.length() > 0) {
        this.font.draw(poseStack, line.toString().trim(), x, lineY, color);
        lineY += 10;
        line = new StringBuilder();
      }
      line.append(word).append(" ");
    }
    if (line.length() > 0) {
      this.font.draw(poseStack, line.toString().trim(), x, lineY, color);
    }
  }

  private void renderRightPagePreview(PoseStack poseStack, int mouseX, int mouseY) {
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
    if (previewEntity != null) {
      float scale = EntityScalingManager.getUIScale(previewEntity) * 2.6f;
      float time = (System.currentTimeMillis() % 6000L) / 6000.0f * 2 * (float) Math.PI;
      ScreenHelper.renderEntity(
          this.leftPos + 210,
          this.topPos + 100,
          (float) Math.sin(time) * 60,
          -10f,
          scale,
          previewEntity);
    }
  }

  private void updatePreviewEntity(ItemStack cardStack) {
    int hash =
        cardStack.isEmpty()
            ? 0
            : System.identityHashCode(cardStack.getItem())
                + (cardStack.hasTag() ? cardStack.getTag().hashCode() : 0);
    if (hash == previewEntityHash && previewEntity != null) return;

    if (previewEntity != null) {
      previewEntity.discard();
      previewEntity = null;
    }
    MobCaptureData data = MobCaptureManager.getMobCaptureData(cardStack);
    if (data != null && data.entityType() != null) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null) {
        previewEntity = data.entityType().create(mc.level);
        if (previewEntity != null && data.hasData()) {
          try {
            previewEntity.load(data.data());
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
