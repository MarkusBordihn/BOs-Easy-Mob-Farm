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
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.menu.MobFarmSlot;
import de.markusbordihn.easymobfarm.menu.slots.OutputSlot;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import net.minecraft.ChatFormatting;
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
  private static final String TOOLTIP_PREFIX = Constants.TOOLTIP_PREFIX + "farm.";
  protected float xMouse;
  protected float yMouse;
  protected Entity entity;
  protected int entityExperience;

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
    this.entity = RendererManager.getEntity(blockPos);
    if (this.entity != null) {
      ScreenHelper.renderEntity(
          this.leftPos + 72,
          this.topPos + 80,
          this.leftPos + 70 - this.xMouse,
          this.topPos + 40 - this.yMouse,
          EntityScalingManager.getUIScale(this.entity),
          this.entity);
    } else {
      if (this.entityExperience > 0) {
        this.entityExperience = 0;
      }
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

    // Render tooltip for different kind of slots.
    for (Slot slot : this.menu.slots) {
      if (slot instanceof MobFarmSlot mobFarmSlot
          && mobFarmSlot.getTooltip() != null
          && isHovering(slot.x, slot.y, 16, 16, mouseX, mouseY)
          && !slot.hasItem()) {
        renderSlotTooltip(guiGraphics, mobFarmSlot, mouseX, mouseY);
      }
    }

    // Render tooltip for the "info" button.
    if (isHovering(24, 17, 10, 13, mouseX, mouseY)) {
      List<Component> infoText = new java.util.ArrayList<>(List.of());
      if (this.getMenu().getMobFarmType() != null) {
        infoText.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.BLOCK_PREFIX + this.getMenu().getMobFarmType().getId(),
                    String.valueOf(this.getMenu().getMobFarmTierLevel()))
                .withStyle(
                    switch (this.getMenu().getMobFarmTierLevel()) {
                      case 1 -> ChatFormatting.GREEN;
                      case 2 -> ChatFormatting.YELLOW;
                      case 3 -> ChatFormatting.RED;
                      default -> ChatFormatting.WHITE;
                    }));
      }
      infoText.add(
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "tier", new Object[] {this.getMenu().getMobFarmTierLevel()}));
      infoText.add(
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "status", new Object[] {this.getMenu().getMobFarmStatus()}));
      infoText.add(
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "progress",
              new Object[] {
                this.getMenu().getMobFarmProgress(), MobFarmBlockEntity.DEFAULT_FARM_PROCESSING_TIME
              }));
      infoText.add(
          TextComponent.getTranslatedTextRaw(
              TOOLTIP_PREFIX + "output_slots",
              new Object[] {this.getMenu().getMobFarmNumberOfOutputSlots()}));

      // Add entity information to the tooltip, if available.
      if (this.entity != null && this.getMenu().getMobFarmStatus() != MobFarmStatus.IDLE) {
        infoText.add(
            TextComponent.getTranslatedTextRaw(
                TOOLTIP_PREFIX + "entity_type", new Object[] {this.entity.getType()}));

        // Add experience information to the tooltip, if available.
        int capturedMobExperience = this.getMenu().getCapturedMobExperience();
        if (capturedMobExperience >= ExperienceEnhancementItem.MIN_EXPERIENCE_FOR_DROP) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(
                      TOOLTIP_PREFIX + "experience", new Object[] {capturedMobExperience})
                  .withStyle(ChatFormatting.GREEN));
        } else if (capturedMobExperience > 0) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(
                      TOOLTIP_PREFIX + "low_experience", new Object[] {capturedMobExperience})
                  .withStyle(ChatFormatting.YELLOW));
        } else if (capturedMobExperience == 0) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(TOOLTIP_PREFIX + "no_experience")
                  .withStyle(ChatFormatting.RED));
        }
      }
      guiGraphics.renderComponentTooltip(this.font, infoText, mouseX, mouseY);
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
