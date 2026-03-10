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
import de.markusbordihn.easymobfarm.data.loot.LootPreviewCache;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmStatus;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
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
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MobFarmScreen<T extends MobFarmMenu> extends ContainerScreen<T> {

  private static final Identifier TEXTURE_UI =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/mob_farm.png");
  private static final Identifier TEXTURE_UI_IDLE =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/mob_farm_idle.png");
  private static final Identifier TEXTURE_ELEMENTS =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/gui/mob_farm_elements.png");
  protected float xMouse;
  protected float yMouse;
  protected Entity entity;
  protected int entityExperience;

  private List<Component> cachedLootTooltip;
  private Entity cachedLootEntity;
  private int cachedEnhancementHash;

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
  protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
    Graphics.blit(
        guiGraphics,
        this.menu.getMobFarmStatus() == MobFarmStatus.IDLE ? TEXTURE_UI_IDLE : TEXTURE_UI,
        leftPos,
        topPos,
        256,
        243,
        0,
        0);
  }

  @Override
  public void render(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
    this.xMouse = x;
    this.yMouse = y;
    super.render(guiGraphics, x, y, partialTicks);
    this.renderLockedSlot(guiGraphics, x, y);
    this.renderEntityType(guiGraphics, x, y);
    this.renderMobFarmProgress(guiGraphics, x, y);
    this.renderTooltip(guiGraphics, x, y);
  }

  @Override
  protected void renderLabels(GuiGraphics guiGraphics, int x, int y) {}

  private void renderMobFarmProgress(GuiGraphics guiGraphics, int x, int y) {
    int mobFarmProgress = this.getMenu().getMobFarmProgress();
    int currentWidth = (mobFarmProgress * 32) / MobFarmConfig.farmProgressingTime;
    Graphics.blit(
        guiGraphics,
        TEXTURE_ELEMENTS,
        this.leftPos + 113,
        this.topPos + 78,
        currentWidth,
        16,
        0,
        36,
        256,
        256);
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
    if (this.entity != null && this.entity instanceof LivingEntity livingEntity) {
      int entityAreaLeft = this.leftPos + 50;
      int entityAreaTop = this.topPos + 30;
      int entityAreaRight = this.leftPos + 94;
      int entityAreaBottom = this.topPos + 85;

      float entityScale = EntityScalingManager.getUIScale(this.entity);
      int scaledSize = Math.round(entityScale);

      float entityHeight = livingEntity.getBbHeight();
      float yOffset;

      if (entityHeight < 1.5F) {
        float renderAreaHeight = entityAreaBottom - entityAreaTop;
        yOffset = (renderAreaHeight * 0.8F) / scaledSize;
        yOffset = Math.max(0.0F, Math.min(yOffset, 1.0F));
      } else {
        yOffset = 0.0625F;
      }

      InventoryScreen.renderEntityInInventoryFollowsMouse(
          guiGraphics,
          entityAreaLeft,
          entityAreaTop,
          entityAreaRight,
          entityAreaBottom,
          scaledSize,
          yOffset,
          this.xMouse,
          this.yMouse,
          livingEntity);
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
            18,
            18,
            0,
            18,
            256,
            256);
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
      guiGraphics.setComponentTooltipForNextFrame(this.font, infoText, mouseX, mouseY);
    } else if (isHovering(22, 35, 15, 14, mouseX, mouseY)
        && this.getMenu().getMobFarmStatus() != MobFarmStatus.IDLE) {
      renderLootInfoTooltip(guiGraphics, mouseX, mouseY);
    }
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
      List<ItemStack> preview = LootPreviewCache.getLootPreview(null, this.entity.getType());
      hash =
          hash * 31
              + (LootPreviewCache.hasLootPreview(this.entity.getType()) ? preview.size() + 1 : 0);
    }
    return hash;
  }

  private List<Component> buildLootInfoTooltip() {
    List<Component> lootInfo = new java.util.ArrayList<>();
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
      List<ItemStack> lootPreview = LootPreviewCache.getLootPreview(null, this.entity.getType());
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

    // Show bonus drop with chance
    MobFarmType mobFarmType = this.getMenu().getMobFarmType();
    int tierLevel = this.getMenu().getMobFarmTierLevel();
    ItemStack bonusDrop =
        MobFarmBonusConfig.getBonusDropEntry(mobFarmType, tierLevel, this.entity.getType());
    if (!bonusDrop.isEmpty()) {
      int chance =
          MobFarmBonusConfig.getBonusDropChance(mobFarmType, tierLevel, this.entity.getType());
      if (chance > 0) {
        lootInfo.add(
            TextComponent.getTranslatedTextRaw(
                    Constants.TOOLTIP_FARM_PREFIX + "loot_bonus_chance",
                    new Object[] {bonusDrop.getDisplayName(), chance})
                .withStyle(ChatFormatting.GREEN));
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
    List<EnhancementItem> enhancements = new java.util.ArrayList<>();
    for (Slot menuSlot : this.menu.slots) {
      if (menuSlot instanceof de.markusbordihn.easymobfarm.menu.slots.EnhancementSlot
          && menuSlot.hasItem()
          && menuSlot.getItem().getItem() instanceof EnhancementItem enhancementItem) {
        enhancements.add(enhancementItem);
      }
    }
    return enhancements;
  }

  private List<Component> getEnhancementSuggestions(List<EnhancementItem> active) {
    List<Component> suggestions = new java.util.ArrayList<>();
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
