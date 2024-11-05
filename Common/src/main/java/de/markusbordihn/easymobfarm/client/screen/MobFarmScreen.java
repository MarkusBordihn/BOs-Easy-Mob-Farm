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

package de.markusbordihn.easymobfarm.client.screen;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.client.renderer.manager.EntityScalingManager;
import de.markusbordihn.easymobfarm.client.renderer.manager.RendererManager;
import de.markusbordihn.easymobfarm.client.screen.components.Graphics;
import de.markusbordihn.easymobfarm.client.screen.components.Text;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.menu.MobFarmSlot;
import de.markusbordihn.easymobfarm.menu.slots.OutputSlot;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class MobFarmScreen<T extends MobFarmMenu> extends ContainerScreen<T> {

  private static final ResourceLocation TEXTURE_UI =
      new ResourceLocation(Constants.MOD_ID, "textures/gui/mob_farm.png");
  private static final ResourceLocation TEXTURE_UI_IDLE =
      new ResourceLocation(Constants.MOD_ID, "textures/gui/mob_farm_idle.png");
  private static final ResourceLocation TEXTURE_ELEMENTS =
      new ResourceLocation(Constants.MOD_ID, "textures/gui/mob_farm_elements.png");
  protected float xMouse;
  protected float yMouse;

  public MobFarmScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
  }

  @Override
  protected void renderDefaultScreenBg(GuiGraphics guiGraphics, int leftPos, int topPos) {
    Graphics.blit(
        guiGraphics,
        this.menu.getMobFarmStatus() == MobFarmStatus.IDLE ? TEXTURE_UI_IDLE : TEXTURE_UI,
        leftPos,
        topPos,
        0,
        0,
        256,
        243);
  }

  @Override
  public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
    this.xMouse = x;
    this.yMouse = y;
    this.renderBackground(guiGraphics);
    super.render(guiGraphics, x, y, partialTicks);
    this.renderLockedSlot(guiGraphics, x, y);
    this.renderEntityType(guiGraphics, x, y);
    this.renderNumberOfOutputSlot(guiGraphics, x, y);
    this.renderMobFarmProgress(guiGraphics, x, y);
    this.renderTooltip(guiGraphics, x, y);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
    // No labels to render.
  }

  private void renderMobFarmProgress(GuiGraphics guiGraphics, int x, int y) {
    int mobFarmProgress = this.getMenu().getMobFarmProgress();
    int currentWidth = (mobFarmProgress * 32) / MobFarmBlockEntity.DEFAULT_FARM_PROCESSING_TIME;
    Graphics.blit(
        guiGraphics,
        TEXTURE_ELEMENTS,
        this.leftPos + 113,
        this.topPos + 78,
        0,
        36,
        currentWidth,
        16);
  }

  private void renderNumberOfOutputSlot(GuiGraphics guiGraphics, int x, int y) {
    // Get number of output slots.
    int numberOfOutputSlots = this.getMenu().getMobFarmNumberOfOutputSlots();
    if (numberOfOutputSlots == 0) {
      return;
    }

    int mobFarmTierLevel = this.getMenu().getMobFarmTierLevel();
    if (mobFarmTierLevel >= 0) {
      Text.drawString(
          guiGraphics,
          this.font,
          "t" + mobFarmTierLevel,
          this.leftPos + 20,
          this.topPos + 55,
          0x404040);
    }

    // Render number of output slots on the screen.
    Text.drawString(
        guiGraphics,
        this.font,
        numberOfOutputSlots + "s",
        this.leftPos + 20,
        this.topPos + 70,
        0x404040);
  }

  private void renderEntityType(GuiGraphics guiGraphics, int x, int y) {
    // Verify block position.
    BlockPos blockPos = this.getMenu().getMobFarmBlockPos();
    if (blockPos == null || blockPos.equals(BlockPos.ZERO)) {
      return;
    }

    // Check mob farm status and render it on the screen.
    int mobFarmStatus = this.getMenu().getMobFarmStatus();
    if (mobFarmStatus == MobFarmStatus.IDLE) {
      return;
    }

    // Get entity from block position and render it on the screen.
    Entity entity = RendererManager.getEntity(blockPos);
    if (entity != null) {
      ScreenHelper.renderEntity(
          this.leftPos + 72,
          this.topPos + 80,
          this.leftPos + 70 - this.xMouse,
          this.topPos + 40 - this.yMouse,
          EntityScalingManager.getUIScale(entity),
          entity);
    }
  }

  private void renderLockedSlot(GuiGraphics guiGraphics, int x, int y) {
    for (Slot slot : this.menu.slots) {
      if (slot instanceof OutputSlot outputSlot && !outputSlot.isActive()) {
        Graphics.blit(
            guiGraphics,
            TEXTURE_ELEMENTS,
            this.leftPos + slot.x - 1,
            this.topPos + slot.y - 1,
            0,
            18,
            18,
            18);
      }
    }
  }

  @Override
  public void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    super.renderTooltip(guiGraphics, mouseX, mouseY);

    for (Slot slot : this.menu.slots) {
      if (slot instanceof MobFarmSlot mobFarmSlot
          && mobFarmSlot.getTooltip() != null
          && isHovering(slot.x, slot.y, 16, 16, mouseX, mouseY)
          && !slot.hasItem()) {
        renderSlotTooltip(guiGraphics, mobFarmSlot, mouseX, mouseY);
      }
    }
  }

  private void renderSlotTooltip(
      GuiGraphics guiGraphics, MobFarmSlot mobFarmSlot, int mouseX, int mouseY) {
    Component component = mobFarmSlot.getTooltip();
    if (component == null) {
      return;
    }
    List<FormattedCharSequence> wrappedText = this.font.split(component, 150);
    guiGraphics.renderTooltip(this.font, wrappedText, mouseX, mouseY);
  }
}
