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
import de.markusbordihn.easymobfarm.config.MobFarmBonusConfig;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.menu.MobFarmSlot;
import de.markusbordihn.easymobfarm.menu.slots.OutputSlot;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MobFarmScreen<T extends MobFarmMenu> extends ContainerScreen<T> {

  private static final ResourceLocation TEXTURE_UI =
      ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/mob_farm.png");
  private static final ResourceLocation TEXTURE_UI_IDLE =
      ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/mob_farm_idle.png");
  private static final ResourceLocation TEXTURE_ELEMENTS =
      ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/mob_farm_elements.png");
  protected float xMouse;
  protected float yMouse;
  protected Entity entity;
  protected int entityExperience;

  public MobFarmScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
  }

  public static MutableComponent getLocalizedRemainingTimeComponent(
      int totalTicks, int currentProgress, int speed, int bonus) {
    int remainingTicks = Math.max(0, totalTicks - currentProgress);
    float ticksPerSecond = Math.max(1f, speed + bonus);
    float secondsRemaining = remainingTicks / ticksPerSecond;

    int totalSeconds = Math.round(secondsRemaining);
    if (totalSeconds < 60) {
      return TextComponent.getTranslatedTextRaw(
          Constants.TOOLTIP_FARM_PREFIX + "next_drop.seconds", totalSeconds);
    } else {
      int minutes = totalSeconds / 60;
      int seconds = totalSeconds % 60;
      return TextComponent.getTranslatedTextRaw(
          Constants.TOOLTIP_FARM_PREFIX + "next_drop.full", new Object[] {minutes, seconds});
    }
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
    this.renderBackground(guiGraphics, x, y, partialTicks);
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
    int currentWidth = (mobFarmProgress * 32) / MobFarmConfig.farmProgressingTime;
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
          guiGraphics,
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

    MobFarmType mobFarmType = this.getMenu().getMobFarmType();
    boolean isAdvanced = Minecraft.getInstance().options.advancedItemTooltips;

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
      if (mobFarmType != null) {
        infoText.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.TOOLTIP_FARM_PREFIX + mobFarmType.getId(),
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
              Constants.TOOLTIP_FARM_PREFIX + "tier",
              new Object[] {this.getMenu().getMobFarmTierLevel()}));
      infoText.add(
          TextComponent.getTranslatedTextRaw(
              Constants.TOOLTIP_FARM_PREFIX + "status",
              TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "status_" + this.getMenu().getMobFarmStatus())));

      if (this.getMenu().getMobFarmStatus() == MobFarmStatus.WORKING) {
        infoText.add(
            getLocalizedRemainingTimeComponent(
                MobFarmConfig.farmProgressingTime,
                this.getMenu().getMobFarmProgress(),
                this.getMenu().getMobFarmProgressionSpeed(),
                this.getMenu().getMobFarmProgressionSpeedBonus()));
      }
      if (isAdvanced) {
        infoText.add(
            TextComponent.getTranslatedTextRaw(
                Constants.TOOLTIP_FARM_PREFIX + "progress",
                new Object[] {
                  this.getMenu().getMobFarmProgress(), MobFarmConfig.farmProgressingTime
                }));
      }

      infoText.add(
          TextComponent.getTranslatedText(
              "tier_level_processing_speed",
              MobFarmBlockEntity.getProcessingSpeed(
                  this.getMenu().getMobFarmTierLevel(),
                  this.getMenu().getMobFarmProgressionSpeedBonus())));
      if (isAdvanced) {
        infoText.add(
            TextComponent.getTranslatedTextRaw(
                Constants.TOOLTIP_FARM_PREFIX + "progression_speed",
                new Object[] {
                  this.getMenu().getMobFarmProgressionSpeed(),
                  this.getMenu().getMobFarmProgressionSpeedBonus()
                }));
      }

      infoText.add(
          TextComponent.getTranslatedTextRaw(
              Constants.TOOLTIP_FARM_PREFIX + "output_slots",
              new Object[] {this.getMenu().getMobFarmNumberOfOutputSlots()}));

      // Add additional information for special mob farms.
      if (mobFarmType == MobFarmType.LUCKY_DROP_FARM) {
        infoText.add(
            TextComponent.getTranslatedTextRaw(
                Constants.TOOLTIP_FARM_PREFIX + "lucky_drop_percentage",
                new Object[] {MobFarmConfig.luckyDropFarmLuckPercentage}));
        if (MobFarmConfig.luckyDropFarmLuckPercentage < 100) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "lucky_drop_warn")
                  .withStyle(ChatFormatting.RED));
        }
      }

      // Add entity information to the tooltip, if available.
      if (this.entity != null && this.getMenu().getMobFarmStatus() != MobFarmStatus.IDLE) {
        infoText.add(
            TextComponent.getTranslatedTextRaw(
                Constants.TOOLTIP_FARM_PREFIX + "entity_type",
                new Object[] {this.entity.getType().toString()}));

        // Add Requires "killed_by_player" information, if available.
        MobCaptureCardDefinition mobCaptureCardDefinition =
            MobCaptureCardDefinitionManager.get(this.entity.getType());
        if (mobCaptureCardDefinition != null && mobCaptureCardDefinition.requiresKilledByPlayer()) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "killed_by_player")
                  .withStyle(ChatFormatting.RED));
        }

        // Add experience information to the tooltip, if available.
        int capturedMobExperience = this.getMenu().getCapturedMobExperience();
        if (capturedMobExperience >= ExperienceEnhancementItem.MIN_EXPERIENCE_FOR_DROP) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "experience",
                      new Object[] {capturedMobExperience})
                  .withStyle(ChatFormatting.GREEN));
        } else if (capturedMobExperience > 0) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "low_experience",
                      new Object[] {capturedMobExperience})
                  .withStyle(ChatFormatting.YELLOW));
        } else if (capturedMobExperience == 0) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "no_experience")
                  .withStyle(ChatFormatting.RED));
        }

        // Add Bonus drop information, if available.
        ItemStack bonusLootDrop =
            MobFarmBonusConfig.getBonusDropEntry(
                this.getMenu().getMobFarmType(),
                this.getMenu().getMobFarmTierLevel(),
                this.entity.getType());
        if (!bonusLootDrop.isEmpty()) {
          infoText.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "bonus_drop",
                      new Object[] {bonusLootDrop.getDisplayName()})
                  .withStyle(ChatFormatting.GREEN));
        } else {
          infoText.add(
              TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "no_bonus_drop")
                  .withStyle(ChatFormatting.GRAY));
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
