/**
 * Copyright 2022 Markus Bordihn
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

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.ChatFormatting;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntityData;
import de.markusbordihn.easymobfarm.client.renderer.helper.RenderModels;
import de.markusbordihn.easymobfarm.config.MobTypeManager;
import de.markusbordihn.easymobfarm.data.RedstoneMode;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.menu.slots.CapturedMobSlot;
import de.markusbordihn.easymobfarm.menu.slots.ExperienceSlot;
import de.markusbordihn.easymobfarm.menu.slots.WeaponSlot;
import de.markusbordihn.easymobfarm.network.NetworkMessage;
import de.markusbordihn.easymobfarm.text.TranslatableText;

public class MobFarmScreen<T extends MobFarmMenu> extends AbstractContainerScreen<T> {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ResourceLocation CUSTOM =
      new ResourceLocation(Constants.MOD_ID, "textures/container/custom.png");
  private static final ResourceLocation CUSTOM_SHADOW =
      new ResourceLocation(Constants.MOD_ID, "textures/container/custom_shadow.png");

  public static final int SNAP_WITH = 34;
  public static final int SNAP_HEIGHT = 53;
  public static final int MAX_HELP_TEXT_WIDTH = 300;

  private final MutableComponent warningDisabledText;
  private final MutableComponent warningFullText;
  private final T mobFarmMenu;

  private RenderModels renderModels;
  private float dropTimeLabelScale = 0.75F;
  private float nextDropTimeLabelScale = 0.75F;
  private int animationTicker = 0;
  private int dropTimeLabelX;
  private int dropTimeLabelY;
  private int nextDropTimeLabelX;
  private int nextDropTimeLabelY;
  private int ticker = 0;
  private StringSplitter stringSplitter;

  // Redstone switch button
  private int redstoneButtonX = 154;
  private int redstoneButtonY = 44;
  private int redstoneButtonModeOnY = redstoneButtonY;
  private int redstoneButtonModeDisabledY = redstoneButtonModeOnY + 10;
  private int redstoneButtonModeOffY = redstoneButtonModeDisabledY + 9;
  private int redstoneButtonWidth = 12;
  private int redstoneButtonHeight = 29;

  public MobFarmScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
    this.mobFarmMenu = menu;
    this.warningDisabledText = Component.translatable(Constants.TEXT_PREFIX + "warning_disabled");
    this.warningFullText = Component.translatable(Constants.TEXT_PREFIX + "warning_full");
  }

  protected void renderSnapshot(GuiGraphics guiGraphics, ResourceLocation mobFarmTypeSnapshot) {
    if (mobFarmTypeSnapshot != null) {
      // Scale snap image (34x53)
      guiGraphics.blit(mobFarmTypeSnapshot, leftPos + 7, topPos + 16, 0, 0, 49, 70, SNAP_WITH + 15,
          SNAP_HEIGHT + 17);
    }
  }

  private void renderEntityType(GuiGraphics guiGraphics, int x, int y) {
    // Render Mob Farm Snapshot or try to render entity.
    String mobFarmType = this.mobFarmMenu.getMobFarmType();
    if (!mobFarmType.isBlank()
        && this.mobFarmMenu.getMobFarmStatus() != MobFarmBlockEntityData.FARM_STATUS_WAITING) {
      ResourceLocation mobFarmTypeSnapshot = Snapshots.getSnapshot(mobFarmType);
      if (mobFarmTypeSnapshot != null) {
        // Render snapshot, if available.
        this.renderSnapshot(guiGraphics, mobFarmTypeSnapshot);
      } else {
        // Render model, if supported.
        LivingEntity livingEntity = renderModels.getEntityTypeModel(mobFarmType);
        if (livingEntity != null) {
          this.renderSnapshot(guiGraphics, CUSTOM_SHADOW);
          MobFarmScreenHelper.renderEntity(leftPos + 33, topPos + 65, leftPos + 33f - x,
              topPos + 65f - y, livingEntity);
        } else {
          this.renderSnapshot(guiGraphics, CUSTOM);
        }
      }
    }
  }

  protected void renderHelpText(GuiGraphics guiGraphics, int x, int y) {
    if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && !this.hoveredSlot.hasItem()
        && font != null && stringSplitter != null) {
      if (this.hoveredSlot instanceof CapturedMobSlot) {
        Set<String> acceptedMobTypes =
            MobTypeManager.getAcceptedMobTypes(this.menu.getAcceptedMobTypeName());
        if (acceptedMobTypes != null && !acceptedMobTypes.isEmpty()) {
          MutableComponent mobTypeOverview =
              Component.literal("").withStyle(ChatFormatting.DARK_GREEN);
          for (String acceptedMob : acceptedMobTypes) {
            Component acceptedMobName = TranslatableText.getEntityName(acceptedMob);
            if (!acceptedMobName.getString().isBlank()) {
              mobTypeOverview.append(acceptedMobName).append(", ")
                  .withStyle(ChatFormatting.DARK_GREEN);
            }
          }
          mobTypeOverview.append(Component.literal("..."));
          guiGraphics.renderComponentTooltip(font,
              stringSplitter.splitLines(Component
                  .translatable(Constants.HELP_TEXT_PREFIX + "capture_slot", mobTypeOverview),
                  MAX_HELP_TEXT_WIDTH, Style.EMPTY),
              x, y, ItemStack.EMPTY);
        } else {
          guiGraphics.renderComponentTooltip(font,
              stringSplitter.splitLines(
                  Component.translatable(Constants.HELP_TEXT_PREFIX + "capture_slot_all"),
                  MAX_HELP_TEXT_WIDTH, Style.EMPTY),
              x, y, ItemStack.EMPTY);
        }
      } else if (this.hoveredSlot instanceof WeaponSlot) {
        guiGraphics.renderComponentTooltip(font,
            stringSplitter.splitLines(
                Component.translatable(Constants.HELP_TEXT_PREFIX + "weapon_slot"),
                MAX_HELP_TEXT_WIDTH, Style.EMPTY),
            x, y, ItemStack.EMPTY);
      } else if (this.hoveredSlot instanceof ExperienceSlot) {
        guiGraphics.renderComponentTooltip(font,
            stringSplitter.splitLines(
                Component.translatable(Constants.HELP_TEXT_PREFIX + "experience_slot",
                    Items.GLASS_BOTTLE.getDescription(), Items.EXPERIENCE_BOTTLE.getDescription()),
                MAX_HELP_TEXT_WIDTH, Style.EMPTY),
            x, y, ItemStack.EMPTY);
      }
    }
  }

  public void renderRedstoneButton(GuiGraphics guiGraphics) {
    int redstoneButtonModeX = this.menu.isMobFarmPowered() ? 145 : 131;
    int redstoneButtonModeY = 1;
    switch (this.mobFarmMenu.getMobFarmRedstoneMode()) {
      case ON:
        redstoneButtonModeY += this.redstoneButtonHeight + 3;
        break;
      case OFF:
        redstoneButtonModeY += (this.redstoneButtonHeight + 3) * 2;
        break;
      default:
        redstoneButtonModeY = 1;
    }
    guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + this.redstoneButtonX,
        topPos + this.redstoneButtonY, redstoneButtonModeX, redstoneButtonModeY,
        this.redstoneButtonWidth, this.redstoneButtonHeight);
  }

  @Override
  protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
    int hoverPositionX = x - leftPos;
    int hoverPositionY = y - topPos;
    if (hoverPositionX > redstoneButtonX && hoverPositionX < redstoneButtonX + redstoneButtonWidth
        && hoverPositionY > redstoneButtonY
        && hoverPositionY < redstoneButtonY + redstoneButtonHeight) {
      if (hoverPositionY > redstoneButtonModeOnY && hoverPositionY < redstoneButtonModeOnY + 10) {
        guiGraphics.renderComponentTooltip(font,
            stringSplitter.splitLines(
                Component.translatable(Constants.HELP_TEXT_PREFIX + "redstone_button_on"),
                MAX_HELP_TEXT_WIDTH, Style.EMPTY),
            x, y, ItemStack.EMPTY);
      } else if (hoverPositionY > redstoneButtonModeDisabledY
          && hoverPositionY < redstoneButtonModeDisabledY + 9) {
        guiGraphics.renderComponentTooltip(font,
            stringSplitter.splitLines(
                Component.translatable(Constants.HELP_TEXT_PREFIX + "redstone_button_disabled"),
                MAX_HELP_TEXT_WIDTH, Style.EMPTY),
            x, y, ItemStack.EMPTY);
      } else if (hoverPositionY > redstoneButtonModeOffY
          && hoverPositionY < redstoneButtonModeOffY + 10) {
        guiGraphics.renderComponentTooltip(font,
            stringSplitter.splitLines(
                Component.translatable(Constants.HELP_TEXT_PREFIX + "redstone_button_off"),
                MAX_HELP_TEXT_WIDTH, Style.EMPTY),
            x, y, ItemStack.EMPTY);
      }
    } else {
      super.renderTooltip(guiGraphics, x, y);
    }
  }

  @Override
  public boolean mouseReleased(double x, double y, int button) {
    double clickedPositionX = x - leftPos;
    double clickedPositionY = y - topPos;

    // Redstone switch button
    if (button == 0 && clickedPositionX > redstoneButtonX
        && clickedPositionX < redstoneButtonX + redstoneButtonWidth
        && clickedPositionY > redstoneButtonY
        && clickedPositionY < redstoneButtonY + redstoneButtonHeight) {
      if (clickedPositionY > redstoneButtonModeOnY
          && clickedPositionY < redstoneButtonModeOnY + 10) {
        NetworkMessage.sendRedstoneModeChangeToServer(this.menu.getMobFarmPosition(),
            RedstoneMode.ON);
      } else if (clickedPositionY > redstoneButtonModeDisabledY
          && clickedPositionY < redstoneButtonModeDisabledY + 9) {
        NetworkMessage.sendRedstoneModeChangeToServer(this.menu.getMobFarmPosition(),
            RedstoneMode.DISABLED);
      } else if (clickedPositionY > redstoneButtonModeOffY
          && clickedPositionY < redstoneButtonModeOffY + 10) {
        NetworkMessage.sendRedstoneModeChangeToServer(this.menu.getMobFarmPosition(),
            RedstoneMode.OFF);
      }
    }
    return super.mouseReleased(x, y, button);
  }

  @Override
  public void init() {
    super.init();
    this.animationTicker = 0;
    this.ticker = 0;
    this.imageHeight = 221;
    this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    this.titleLabelY = 5;
    this.topPos = (this.height - this.imageHeight) / 2;
    this.inventoryLabelY = this.imageHeight - 92;
    this.dropTimeLabelX = Math.round(47 / dropTimeLabelScale);
    this.dropTimeLabelY = Math.round(119 / dropTimeLabelScale);
    this.nextDropTimeLabelX = Math.round(66 / nextDropTimeLabelScale);
    this.nextDropTimeLabelY = Math.round(91 / nextDropTimeLabelScale);
    this.renderModels = new RenderModels(this.minecraft);
    this.stringSplitter = this.font.getSplitter();
  }

  @Override
  public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
    // Update data cache only every 40 ticks to avoid expensive operations on hight fps.
    if ((this.ticker++ & (40 - 1)) == 0) {
      this.mobFarmMenu.updateMobFarmDataCache();
      if (ticker >= 40) {
        this.ticker = 0;
      }
    }

    // Animation Ticker
    if (this.animationTicker++ > 400) {
      this.animationTicker = 0;
    }

    // Render screen in three parts.
    this.renderBackground(guiGraphics, x, y, partialTicks);
    super.render(guiGraphics, x, y, partialTicks);
    this.renderRedstoneButton(guiGraphics);
    this.renderEntityType(guiGraphics, x, y);
    this.renderTooltip(guiGraphics, x, y);
    this.renderHelpText(guiGraphics, x, y);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
    super.renderLabels(guiGraphics, x, y);
    int mobFarmStatus = this.mobFarmMenu.getMobFarmStatus();

    // Show Farm Details like drop time, if not full or disabled.
    if (mobFarmStatus != MobFarmBlockEntityData.FARM_STATUS_FULL
        && mobFarmStatus != MobFarmBlockEntityData.FARM_STATUS_DISABLED) {
      guiGraphics.pose().pushPose();
      guiGraphics.pose().scale(dropTimeLabelScale, dropTimeLabelScale, dropTimeLabelScale);
      guiGraphics.drawString(this.font,
          Component.translatable(Constants.TEXT_PREFIX + "drop_time_secs",
              this.mobFarmMenu.getMobFarmTotalTime() / 20),
          this.dropTimeLabelX, this.dropTimeLabelY, Constants.FONT_COLOR_GRAY, false);
      guiGraphics.pose().popPose();
    }

    // Show Mob Details like name and entity type, if available.
    if (mobFarmStatus != MobFarmBlockEntityData.FARM_STATUS_WAITING) {
      guiGraphics.pose().pushPose();
      guiGraphics.drawString(this.font, this.mobFarmMenu.getMobFarmName(), 61, 18,
          Constants.FONT_COLOR_DARK_GREEN, false);
      guiGraphics.pose().popPose();

      guiGraphics.pose().pushPose();
      guiGraphics.pose().scale(0.65f, 0.65f, 0.65f);
      guiGraphics.drawString(this.font, this.mobFarmMenu.getMobFarmType(), Math.round(61 / 0.65f),
          Math.round(28 / 0.65f), Constants.FONT_COLOR_GRAY, false);
      guiGraphics.pose().popPose();
    }

    // Handle different kind of status messages and process status.
    switch (mobFarmStatus) {
      case MobFarmBlockEntityData.FARM_STATUS_DONE:
      case MobFarmBlockEntityData.FARM_STATUS_WORKING:
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(nextDropTimeLabelScale, nextDropTimeLabelScale,
            nextDropTimeLabelScale);
        guiGraphics.drawString(this.font,
            Component.translatable(Constants.TEXT_PREFIX + "next_drop_secs",
                this.mobFarmMenu.getMobFarmRemainingTime()),
            nextDropTimeLabelX, nextDropTimeLabelY, Constants.FONT_COLOR_BLACK, false);
        guiGraphics.pose().popPose();
        break;
      case MobFarmBlockEntityData.FARM_STATUS_FULL:
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(dropTimeLabelScale, dropTimeLabelScale, dropTimeLabelScale);
        guiGraphics.drawString(this.font, this.warningFullText, this.dropTimeLabelX,
            this.dropTimeLabelY, Constants.FONT_COLOR_WARNING, false);
        guiGraphics.pose().popPose();
        break;
      case MobFarmBlockEntityData.FARM_STATUS_DISABLED:
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(dropTimeLabelScale, dropTimeLabelScale, dropTimeLabelScale);
        guiGraphics.drawString(this.font, this.warningDisabledText, this.dropTimeLabelX,
            this.dropTimeLabelY, Constants.FONT_COLOR_WARNING, false);
        guiGraphics.pose().popPose();
        break;
      default:
    }
  }

  @Override
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {

    // Container Screen with Player Inventory and Hotbar
    guiGraphics.blit(Constants.TEXTURE_GENERIC_54, leftPos, topPos, 0, 0, this.imageWidth,
        this.imageHeight);
    guiGraphics.blit(Constants.TEXTURE_GENERIC_54, leftPos + 5, topPos + 5, 50, 50, 165, 100, 2000,
        2000);

    // Hopper slots and experience slot
    guiGraphics.blit(Constants.TEXTURE_HOPPER, leftPos + 2, topPos + 85, 2, 5, 170, 40);
    guiGraphics.blit(Constants.TEXTURE_HOPPER, leftPos + 151, topPos + 99, 43, 19, 18, 18);

    // Captured Mob Display, mob captured slot and weapon slot.
    guiGraphics.blit(Constants.TEXTURE_INVENTORY, leftPos + 6, topPos + 15, 25, 7, 51, 72);
    guiGraphics.blit(Constants.TEXTURE_INVENTORY, leftPos + 80, topPos + 50, 76, 61, 18, 18);
    guiGraphics.blit(Constants.TEXTURE_INVENTORY, leftPos + 130, topPos + 50, 76, 61, 18, 18);

    // Captured Mob name and additional details
    int mobDetailsLeftPos = leftPos + 58;
    int mobDetailsTopPos = topPos + 15;
    guiGraphics.blit(Constants.TEXTURE_HORSE, mobDetailsLeftPos, mobDetailsTopPos + 7, 79, 56, 90,
        15);
    guiGraphics.blit(Constants.TEXTURE_HORSE, mobDetailsLeftPos + 22, mobDetailsTopPos + 7, 80, 56,
        90, 15);
    guiGraphics.blit(Constants.TEXTURE_HORSE, mobDetailsLeftPos, mobDetailsTopPos, 79, 17, 90, 15);
    guiGraphics.blit(Constants.TEXTURE_HORSE, mobDetailsLeftPos + 22, mobDetailsTopPos, 80, 17, 90,
        15);

    // Hopper State Icon
    guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 20, topPos + 100, 40, 7, 20, 16);

    // Power status
    if (this.mobFarmMenu.isMobFarmPowered()) {
      guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 21, topPos + 100, 41, 45, 18, 16);
    }

    // Guiding Images
    if (!this.mobFarmMenu.hasMobFarmWeaponItem()) {
      guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 127, topPos + 60, 105, 3, 23, 54);
    } else {
      guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 127, topPos + 60, 81, 3, 23, 54);
    }
    if (!this.mobFarmMenu.hasMobFarmCapturedMob()) {
      // Rotate all 8 icons.
      int capturedMobIconsLeft = this.animationTicker / 50 * 16;
      guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 81, topPos + 50, capturedMobIconsLeft, 60,
          16, 16);
    }

    // Render Mob Farm Status
    switch (this.mobFarmMenu.getMobFarmStatus()) {
      case MobFarmBlockEntityData.FARM_STATUS_DONE:
      case MobFarmBlockEntityData.FARM_STATUS_WORKING:
        // Hopper progress animation
        guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 21, topPos + 100, 41, 26, 18,
            this.mobFarmMenu.getMobFarmProgressImage());
        break;
      case MobFarmBlockEntityData.FARM_STATUS_FULL:
        guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 23, topPos + 99, 65, 6, 16, 14);
        break;
      case MobFarmBlockEntityData.FARM_STATUS_WAITING:
        guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 12, topPos + 30, 1, 1, 40, 50);
        guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 100, topPos + 54, 62, 28, 20, 14);
        break;
      case MobFarmBlockEntityData.FARM_STATUS_DISABLED:
        guiGraphics.blit(Constants.TEXTURE_ICONS, leftPos + 23, topPos + 101, 66, 42, 16, 16);
        break;
      default:
    }
  }

}
