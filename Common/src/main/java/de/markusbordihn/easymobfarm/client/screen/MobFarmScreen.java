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
import de.markusbordihn.easymobfarm.client.renderer.manager.RendererManager;
import de.markusbordihn.easymobfarm.client.screen.components.Graphics;
import de.markusbordihn.easymobfarm.client.screen.components.SlotHintRenderer;
import de.markusbordihn.easymobfarm.client.screen.theme.MobFarmScreenTheme;
import de.markusbordihn.easymobfarm.config.ClientConfig;
import de.markusbordihn.easymobfarm.config.MobFarmBonusConfig;
import de.markusbordihn.easymobfarm.config.MobFarmConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.data.loot.LootPreviewCache;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import de.markusbordihn.easymobfarm.data.mobfarm.RedstoneMode;
import de.markusbordihn.easymobfarm.item.upgrade.EnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.EggCollectorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.ExperienceEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.HoneyHarvesterFrameEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.KnifeEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.MilkExtractorEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.PollenTrapEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SheepEnhancementItem;
import de.markusbordihn.easymobfarm.item.upgrade.enhancement.SwordEnhancementItem;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import de.markusbordihn.easymobfarm.menu.MobFarmSlot;
import de.markusbordihn.easymobfarm.menu.slots.OutputSlot;
import de.markusbordihn.easymobfarm.network.components.TextComponent;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MobFarmScreen<T extends MobFarmMenu> extends ContainerScreen<T> {

  private static final Identifier TEXTURE_ELEMENTS =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/mob_farm_elements.png");
  private static final int STATUS_COLUMN_X = 21;
  private static final int STATUS_COLUMN_STEP = 18;
  private static final int FARM_TYPE_ICON_Y = 12;
  private static final int LOOT_ICON_Y = FARM_TYPE_ICON_Y + STATUS_COLUMN_STEP;
  private static final int REDSTONE_MODE_BUTTON_Y = LOOT_ICON_Y + 2 * STATUS_COLUMN_STEP;
  private static final String FARM_TYPE_ICON_BADGE = "i";
  private static final ItemStack LOOT_ICON = new ItemStack(Items.BUNDLE);
  private static final float LOOT_ICON_SCALE = 0.75F;
  private static final int THEME_BUTTON_X = 219;
  private static final int THEME_BUTTON_Y = 126;
  private static final ItemStack REDSTONE_MODE_ICON_DISABLE_ON_SIGNAL =
      new ItemStack(Items.REDSTONE_TORCH);
  private static final ItemStack REDSTONE_MODE_ICON_ENABLE_ON_SIGNAL = new ItemStack(Items.LEVER);
  private static final ItemStack REDSTONE_MODE_ICON_IGNORE = new ItemStack(Items.BARRIER);
  private final SlotHintRenderer slotHints = new SlotHintRenderer();
  protected float xMouse;
  protected float yMouse;
  protected Entity entity;
  protected int entityExperience;
  private List<Component> cachedLootTooltip;
  private Entity cachedLootEntity;
  private int cachedEnhancementHash;
  private MobFarmScreenTheme theme;
  private MobFarmType cachedFarmTypeIconType;
  private ItemStack cachedFarmTypeIcon = ItemStack.EMPTY;

  public MobFarmScreen(T menu, Inventory inventory, Component component) {
    super(menu, inventory, component);
    this.theme = MobFarmScreenTheme.fromId(ClientConfig.mobFarmScreenTheme);
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

  private static ItemStack getRedstoneModeIcon(RedstoneMode redstoneMode) {
    return switch (redstoneMode) {
      case ENABLE_ON_SIGNAL -> REDSTONE_MODE_ICON_ENABLE_ON_SIGNAL;
      case IGNORE -> REDSTONE_MODE_ICON_IGNORE;
      default -> REDSTONE_MODE_ICON_DISABLE_ON_SIGNAL;
    };
  }

  @Override
  protected void renderDefaultScreenBg(GuiGraphics guiGraphics, int leftPos, int topPos) {
    int mobFarmStatus = this.menu.getMobFarmStatus();
    this.theme
        .getRenderer()
        .renderBackground(
            guiGraphics,
            leftPos,
            topPos,
            mobFarmStatus == MobFarmStatus.IDLE || mobFarmStatus == MobFarmStatus.ERROR,
            this.menu.slots);
    this.slotHints.render(guiGraphics, this.menu.slots, this.entity, this.leftPos, this.topPos);
  }

  @Override
  public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
    this.xMouse = x;
    this.yMouse = y;
    super.render(guiGraphics, x, y, partialTicks);
    this.renderLockedSlot(guiGraphics);
    this.renderEntityType(guiGraphics);
    this.renderMobFarmProgress(guiGraphics);
    this.renderRedstoneModeButton(guiGraphics);
    this.renderFarmTypeIcon(guiGraphics);
    this.renderLootIcon(guiGraphics);
    this.renderThemeButton(guiGraphics);
    this.renderTooltip(guiGraphics, x, y);
  }

  @Override
  public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
    double mouseX = mouseButtonEvent.x();
    double mouseY = mouseButtonEvent.y();
    if (isHovering(THEME_BUTTON_X, THEME_BUTTON_Y, 16, 16, mouseX, mouseY)) {
      this.theme = this.theme.next();
      ClientConfig.setMobFarmScreenTheme(this.theme.getId());
      this.minecraftInstance
          .getSoundManager()
          .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      return true;
    }

    if (isHovering(STATUS_COLUMN_X, REDSTONE_MODE_BUTTON_Y, 16, 16, mouseX, mouseY)
        && this.minecraftInstance.gameMode != null) {
      this.minecraftInstance.gameMode.handleInventoryButtonClick(
          this.menu.containerId, MobFarmMenu.TOGGLE_REDSTONE_MODE_BUTTON);
      this.minecraftInstance
          .getSoundManager()
          .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      return true;
    }

    return super.mouseClicked(mouseButtonEvent, doubleClick);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {
    this.theme.getRenderer().renderLabels(guiGraphics, this.font, this.title);
  }

  private void renderRedstoneModeButton(GuiGraphics guiGraphics) {
    guiGraphics.renderItem(
        getRedstoneModeIcon(this.getMenu().getRedstoneMode()),
        this.leftPos + STATUS_COLUMN_X,
        this.topPos + REDSTONE_MODE_BUTTON_Y);
  }

  private void renderFarmTypeIcon(GuiGraphics guiGraphics) {
    MobFarmType mobFarmType = this.getMenu().getMobFarmType();
    if (mobFarmType == null) {
      return;
    }

    if (mobFarmType != this.cachedFarmTypeIconType) {
      this.cachedFarmTypeIconType = mobFarmType;
      this.cachedFarmTypeIcon =
          BuiltInRegistries.ITEM
              .getOptional(Identifier.fromNamespaceAndPath(Constants.MOD_ID, mobFarmType.getId()))
              .map(ItemStack::new)
              .orElse(ItemStack.EMPTY);
    }
    if (!this.cachedFarmTypeIcon.isEmpty()) {
      int iconX = this.leftPos + STATUS_COLUMN_X;
      int iconY = this.topPos + FARM_TYPE_ICON_Y;
      guiGraphics.renderItem(this.cachedFarmTypeIcon, iconX, iconY);
      guiGraphics.renderItemDecorations(
          this.font, this.cachedFarmTypeIcon, iconX, iconY, FARM_TYPE_ICON_BADGE);
    }
  }

  private void renderLootIcon(GuiGraphics guiGraphics) {
    float inset = (16.0F - 16.0F * LOOT_ICON_SCALE) / 2.0F;
    guiGraphics.pose().pushMatrix();
    try {
      guiGraphics
          .pose()
          .translate(this.leftPos + STATUS_COLUMN_X + inset, this.topPos + LOOT_ICON_Y + inset);
      guiGraphics.pose().scale(LOOT_ICON_SCALE, LOOT_ICON_SCALE);
      guiGraphics.renderItem(LOOT_ICON, 0, 0);
    } finally {
      guiGraphics.pose().popMatrix();
    }
  }

  private void renderThemeButton(GuiGraphics guiGraphics) {
    guiGraphics.renderItem(
        this.theme.getIcon(), this.leftPos + THEME_BUTTON_X, this.topPos + THEME_BUTTON_Y);
  }

  private void renderMobFarmProgress(GuiGraphics guiGraphics) {
    float progress =
        MobFarmConfig.farmProgressingTime > 0
            ? (float) this.getMenu().getMobFarmProgress() / MobFarmConfig.farmProgressingTime
            : 0.0F;
    this.theme.getRenderer().renderProgress(guiGraphics, this.leftPos, this.topPos, progress);
  }

  private void renderEntityType(GuiGraphics guiGraphics) {
    // Verify block position.
    BlockPos blockPos = this.getMenu().getMobFarmBlockPos();
    if (blockPos == null || blockPos.equals(BlockPos.ZERO)) {
      this.entity = null;
      return;
    }

    // Check mob farm status and render it on the screen.
    int mobFarmStatus = this.getMenu().getMobFarmStatus();
    if (mobFarmStatus == MobFarmStatus.IDLE) {
      this.entity = null;
      return;
    }

    // Get entity from block position and render it on the screen.
    this.entity = RendererManager.getEntity(blockPos);
    if (this.entity != null) {
      this.theme
          .getRenderer()
          .entityPreview()
          .render(guiGraphics, this.entity, this.leftPos, this.topPos, this.xMouse, this.yMouse);
    } else {
      if (this.entityExperience > 0) {
        this.entityExperience = 0;
      }
    }
  }

  private void renderLockedSlot(GuiGraphics guiGraphics) {
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

    if (this.theme.getRenderer().isHoveringProgress(mouseX - this.leftPos, mouseY - this.topPos)) {
      List<Component> progressText = new ArrayList<>();
      this.appendProgressInfo(progressText, isAdvanced);
      guiGraphics.setComponentTooltipForNextFrame(this.font, progressText, mouseX, mouseY);
      return;
    }

    // Render tooltip for the "info" button.
    if (isHovering(STATUS_COLUMN_X, FARM_TYPE_ICON_Y, 16, 16, mouseX, mouseY)) {
      List<Component> infoText = new ArrayList<>();
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
      this.appendProgressInfo(infoText, isAdvanced);

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

      // Add buffer information
      int bufferSize = this.getMenu().getBufferSize();
      int bufferMaxSize = this.getMenu().getBufferMaxSize();
      int bufferPercentage = bufferMaxSize > 0 ? (bufferSize * 100 / bufferMaxSize) : 0;
      ChatFormatting bufferColor =
          bufferPercentage >= 80
              ? ChatFormatting.RED
              : bufferPercentage >= 50 ? ChatFormatting.YELLOW : ChatFormatting.GREEN;
      infoText.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "buffer",
                  new Object[] {bufferPercentage, bufferSize, bufferMaxSize})
              .withStyle(bufferColor));

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
          if (hasPlayerKillEnhancement()) {
            infoText.add(
                TextComponent.getTranslatedTextRaw(
                        Constants.TOOLTIP_FARM_PREFIX + "killed_by_player_fulfilled")
                    .withStyle(ChatFormatting.GREEN));
          } else {
            infoText.add(
                TextComponent.getTranslatedTextRaw(
                        Constants.TOOLTIP_FARM_PREFIX + "killed_by_player")
                    .withStyle(ChatFormatting.RED));
          }
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
        List<ItemStack> bonusLootDrops =
            MobFarmBonusConfig.getBonusDropEntries(
                this.getMenu().getMobFarmType(),
                this.getMenu().getMobFarmTierLevel(),
                this.entity.getType());

        if (!bonusLootDrops.isEmpty()) {
          List<String> bonusLootDropNames =
              bonusLootDrops.stream().map(drop -> drop.getDisplayName().getString()).toList();
          int itemsPerLine = 4;
          String firstChunk =
              String.join(
                  ", ",
                  bonusLootDropNames.subList(0, Math.min(itemsPerLine, bonusLootDropNames.size())));
          if (bonusLootDropNames.size() > itemsPerLine) {
            firstChunk += ",";
          }

          infoText.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "bonus_drop", new Object[] {firstChunk})
                  .withStyle(ChatFormatting.GREEN));

          for (int i = itemsPerLine; i < bonusLootDropNames.size(); i += itemsPerLine) {
            String nextChunk =
                String.join(
                    ", ",
                    bonusLootDropNames.subList(
                        i, Math.min(i + itemsPerLine, bonusLootDropNames.size())));
            if (i + itemsPerLine < bonusLootDropNames.size()) {
              nextChunk += ",";
            }

            infoText.add(Component.literal("      " + nextChunk).withStyle(ChatFormatting.GREEN));
          }

        } else {
          infoText.add(
              TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "no_bonus_drop")
                  .withStyle(ChatFormatting.GRAY));
        }
      }
      guiGraphics.setComponentTooltipForNextFrame(this.font, infoText, mouseX, mouseY);
    } else if (isHovering(STATUS_COLUMN_X, LOOT_ICON_Y, 16, 16, mouseX, mouseY)
        && this.getMenu().getMobFarmStatus() != MobFarmStatus.IDLE) {
      renderLootInfoTooltip(guiGraphics, mouseX, mouseY);
    } else if (isHovering(STATUS_COLUMN_X, REDSTONE_MODE_BUTTON_Y, 16, 16, mouseX, mouseY)) {
      renderRedstoneModeTooltip(guiGraphics, mouseX, mouseY);
    } else if (isHovering(THEME_BUTTON_X, THEME_BUTTON_Y, 16, 16, mouseX, mouseY)) {
      renderThemeTooltip(guiGraphics, mouseX, mouseY);
    }
  }

  private void appendProgressInfo(List<Component> infoText, boolean isAdvanced) {
    MutableComponent statusText =
        TextComponent.getTranslatedTextRaw(
            Constants.TOOLTIP_FARM_PREFIX + "status",
            TextComponent.getTranslatedTextRaw(
                Constants.TOOLTIP_FARM_PREFIX + "status_" + this.getMenu().getMobFarmStatus()));
    if (this.getMenu().getMobFarmStatus() == MobFarmStatus.ERROR) {
      statusText = statusText.withStyle(ChatFormatting.RED);
    }
    infoText.add(statusText);

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
  }

  private void renderThemeTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    guiGraphics.setComponentTooltipForNextFrame(
        this.font,
        List.of(
            TextComponent.getTranslatedTextRaw(
                Constants.TOOLTIP_FARM_PREFIX + "theme",
                TextComponent.getTranslatedTextRaw(this.theme.getTranslationKey())),
            TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "theme_hint")
                .withStyle(ChatFormatting.GRAY)),
        mouseX,
        mouseY);
  }

  private void renderRedstoneModeTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    RedstoneMode redstoneMode = this.getMenu().getRedstoneMode();
    guiGraphics.setComponentTooltipForNextFrame(
        this.font,
        List.of(
            TextComponent.getTranslatedTextRaw(
                Constants.TOOLTIP_FARM_PREFIX + "redstone_mode_" + redstoneMode.getId()),
            TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "redstone_mode_hint")
                .withStyle(ChatFormatting.GRAY)),
        mouseX,
        mouseY);
  }

  private void renderLootInfoTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    // Use cached tooltip if entity and enhancements haven't changed
    int enhancementHash = computeEnhancementHash();
    if (cachedLootTooltip == null
        || cachedLootEntity != this.entity
        || cachedEnhancementHash != enhancementHash) {
      cachedLootTooltip = buildLootInfoTooltip();
      cachedLootEntity = this.entity;
      cachedEnhancementHash = enhancementHash;
    }
    guiGraphics.setComponentTooltipForNextFrame(this.font, cachedLootTooltip, mouseX, mouseY);
  }

  private int computeEnhancementHash() {
    int hash = 0;
    for (Slot menuSlot : this.menu.slots) {
      if (menuSlot instanceof de.markusbordihn.easymobfarm.menu.slots.EnhancementSlot
          && menuSlot.hasItem()) {
        hash = hash * 31 + menuSlot.getItem().getItem().hashCode();
      }
    }
    // Include entity identity and loot preview cache state for invalidation
    if (this.entity != null) {
      hash = hash * 31 + this.entity.getType().hashCode();
      List<ItemStack> preview = LootPreviewCache.getLootPreview(this.entity.getType());
      hash =
          hash * 31
              + (LootPreviewCache.hasLootPreview(this.entity.getType()) ? preview.size() + 1 : 0);
    }
    return hash;
  }

  private List<Component> buildLootInfoTooltip() {
    List<Component> lootInfo = new ArrayList<>();
    lootInfo.add(
        TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "loot_info_title")
            .withStyle(ChatFormatting.GOLD));

    // If entity not yet loaded by renderer, show hint to re-open
    if (this.entity == null) {
      lootInfo.add(
          TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "loot_preview_hint")
              .withStyle(ChatFormatting.DARK_GRAY));
      return lootInfo;
    }

    // Fetch capture card definition once for reuse
    MobCaptureCardDefinition mobCaptureCardDefinition =
        MobCaptureCardDefinitionManager.get(this.entity.getType());

    // Show base loot drops from server preview (EntityType-based cache, shared across all farms)
    if (!LootPreviewCache.hasLootPreview(this.entity.getType())) {
      lootInfo.add(
          TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "loot_preview_hint")
              .withStyle(ChatFormatting.DARK_GRAY));
    } else {
      List<ItemStack> lootPreview = LootPreviewCache.getLootPreview(this.entity.getType());
      if (!lootPreview.isEmpty()) {
        lootInfo.add(
            TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "loot_base_drops")
                .withStyle(ChatFormatting.WHITE));
        for (ItemStack item : lootPreview) {
          lootInfo.add(
              Component.literal("  \u2022 ")
                  .append(item.getHoverName())
                  .withStyle(ChatFormatting.GRAY));
        }
      }
    }

    // Show bonus drops with chance
    MobFarmType mobFarmType = this.getMenu().getMobFarmType();
    int tierLevel = this.getMenu().getMobFarmTierLevel();

    List<MobFarmBonusConfig.BonusDrop> bonusDrops =
        MobFarmBonusConfig.getConfiguredBonusDrops(mobFarmType, tierLevel, this.entity.getType());

    for (MobFarmBonusConfig.BonusDrop drop : bonusDrops) {
      if (!drop.itemStack().isEmpty() && drop.chance() > 0) {
        lootInfo.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.TOOLTIP_FARM_PREFIX + "loot_bonus_chance",
                    new Object[] {drop.itemStack().getDisplayName(), drop.chance()})
                .withStyle(ChatFormatting.GREEN));
      }
    }

    if (bonusDrops.isEmpty()) {
      for (MobFarmType bonusDropFarmType :
          MobFarmBonusConfig.getMobFarmTypesWithBonusDrop(this.entity.getType())) {
        if (bonusDropFarmType == mobFarmType) {
          continue;
        }
        lootInfo.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.TOOLTIP_FARM_PREFIX + "loot_bonus_other_farm",
                    new Object[] {
                      Component.translatable(Constants.BLOCK_PREFIX + bonusDropFarmType.getId())
                    })
                .withStyle(ChatFormatting.YELLOW));
      }
    }

    // Build active enhancements once and reuse for suggestions
    List<EnhancementItem> activeEnhancements = getActiveEnhancements();
    if (!activeEnhancements.isEmpty()) {
      lootInfo.add(
          TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "active_enhancements")
              .withStyle(ChatFormatting.AQUA));

      for (EnhancementItem enhancement : activeEnhancements) {
        if (enhancement instanceof HoneyHarvesterFrameEnhancementItem
            && this.entity instanceof Bee) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_honey_harvester")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof HoneyExtractorEnhancementItem
            && this.entity instanceof Bee) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_honey_extractor")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof PollenTrapEnhancementItem && this.entity instanceof Bee) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(Constants.TOOLTIP_FARM_PREFIX + "loot_pollen_trap")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof MilkExtractorEnhancementItem
            && this.entity instanceof Cow) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_milk_extractor")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof EggCollectorEnhancementItem
            && this.entity instanceof Chicken) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_egg_collector")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof SheepEnhancementItem && this.entity instanceof Sheep) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_sheep_enhancement")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof SwordEnhancementItem
            && mobCaptureCardDefinition != null
            && mobCaptureCardDefinition.requiresKilledByPlayer()) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_sword_enhancement")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof KnifeEnhancementItem
            && mobCaptureCardDefinition != null
            && mobCaptureCardDefinition.supportsKnifeEnhancement()) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_knife_enhancement")
                  .withStyle(ChatFormatting.YELLOW));
        } else if (enhancement instanceof ExperienceEnhancementItem) {
          lootInfo.add(
              TextComponent.getTranslatedTextRaw(
                      Constants.TOOLTIP_FARM_PREFIX + "loot_experience",
                      new Object[] {MobFarmConfig.experienceDropChance})
                  .withStyle(ChatFormatting.YELLOW));
        }
      }
    }

    // Show enhancement suggestions, reusing the already-fetched active list
    List<Component> suggestions = getEnhancementSuggestions(activeEnhancements);
    if (!suggestions.isEmpty()) {
      lootInfo.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "enhancement_suggestions")
              .withStyle(ChatFormatting.GRAY));
      lootInfo.addAll(suggestions);
    }

    return lootInfo;
  }

  private List<EnhancementItem> getActiveEnhancements() {
    List<EnhancementItem> enhancements = new ArrayList<>();
    for (Slot menuSlot : this.menu.slots) {
      if (menuSlot instanceof de.markusbordihn.easymobfarm.menu.slots.EnhancementSlot
          && menuSlot.hasItem()
          && menuSlot.getItem().getItem() instanceof EnhancementItem enhancementItem) {
        enhancements.add(enhancementItem);
      }
    }
    return enhancements;
  }

  private boolean hasPlayerKillEnhancement() {
    return getActiveEnhancements().stream()
        .anyMatch(
            enhancement ->
                enhancement instanceof SwordEnhancementItem
                    || enhancement instanceof KnifeEnhancementItem);
  }

  private List<Component> getEnhancementSuggestions(List<EnhancementItem> active) {
    List<Component> suggestions = new ArrayList<>();
    if (this.entity == null) {
      return suggestions;
    }
    boolean hasEggCollector =
        active.stream().anyMatch(EggCollectorEnhancementItem.class::isInstance);
    boolean hasMilkExtractor =
        active.stream().anyMatch(MilkExtractorEnhancementItem.class::isInstance);
    boolean hasHoneyHarvester =
        active.stream().anyMatch(HoneyHarvesterFrameEnhancementItem.class::isInstance);
    boolean hasHoneyExtractor =
        active.stream().anyMatch(HoneyExtractorEnhancementItem.class::isInstance);
    boolean hasPollenTrap = active.stream().anyMatch(PollenTrapEnhancementItem.class::isInstance);
    boolean hasSheepEnhancement = active.stream().anyMatch(SheepEnhancementItem.class::isInstance);
    boolean hasSwordEnhancement = active.stream().anyMatch(SwordEnhancementItem.class::isInstance);
    boolean hasKnifeEnhancement = active.stream().anyMatch(KnifeEnhancementItem.class::isInstance);
    boolean hasExperienceEnhancement =
        active.stream().anyMatch(ExperienceEnhancementItem.class::isInstance);

    // Suggest sword enhancement for mobs that require killed-by-player loot
    MobCaptureCardDefinition mobCaptureCardDefinition =
        MobCaptureCardDefinitionManager.get(this.entity.getType());
    if (!hasSwordEnhancement
        && mobCaptureCardDefinition != null
        && mobCaptureCardDefinition.requiresKilledByPlayer()) {
      suggestions.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "suggest_sword_enhancement")
              .withStyle(ChatFormatting.DARK_GRAY));
    }

    // Suggest knife enhancement for mobs that support it (e.g. mobs that drop meat/fish)
    if (!hasKnifeEnhancement
        && mobCaptureCardDefinition != null
        && mobCaptureCardDefinition.supportsKnifeEnhancement()) {
      suggestions.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "suggest_knife_enhancement")
              .withStyle(ChatFormatting.DARK_GRAY));
    }

    // Suggest experience enhancement for mobs that yield enough experience
    int capturedMobExperience = this.getMenu().getCapturedMobExperience();
    if (!hasExperienceEnhancement
        && capturedMobExperience >= ExperienceEnhancementItem.MIN_EXPERIENCE_FOR_DROP) {
      suggestions.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "suggest_experience_enhancement")
              .withStyle(ChatFormatting.DARK_GRAY));
    }

    // Use individual if-checks instead of else-if to allow multiple suggestions at once
    if (this.entity instanceof Chicken && !hasEggCollector) {
      suggestions.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "suggest_egg_collector")
              .withStyle(ChatFormatting.DARK_GRAY));
    }
    if (this.entity instanceof Cow && !hasMilkExtractor) {
      suggestions.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "suggest_milk_extractor")
              .withStyle(ChatFormatting.DARK_GRAY));
    }
    if (this.entity instanceof Bee) {
      if (!hasHoneyHarvester) {
        suggestions.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.TOOLTIP_FARM_PREFIX + "suggest_honey_harvester")
                .withStyle(ChatFormatting.DARK_GRAY));
      }
      if (!hasHoneyExtractor) {
        suggestions.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.TOOLTIP_FARM_PREFIX + "suggest_honey_extractor")
                .withStyle(ChatFormatting.DARK_GRAY));
      }
      if (!hasPollenTrap) {
        suggestions.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.TOOLTIP_FARM_PREFIX + "suggest_pollen_trap")
                .withStyle(ChatFormatting.DARK_GRAY));
      }
    }
    if (this.entity instanceof Sheep && !hasSheepEnhancement) {
      suggestions.add(
          TextComponent.getTranslatedTextRaw(
                  Constants.TOOLTIP_FARM_PREFIX + "suggest_sheep_enhancement")
              .withStyle(ChatFormatting.DARK_GRAY));
    }
    return suggestions;
  }

  private void renderSlotTooltip(
      GuiGraphics guiGraphics, MobFarmSlot mobFarmSlot, int mouseX, int mouseY) {
    Component component = mobFarmSlot.getTooltip();
    if (component == null) {
      return;
    }
    guiGraphics.setComponentTooltipForNextFrame(this.font, List.of(component), mouseX, mouseY);
  }
}
