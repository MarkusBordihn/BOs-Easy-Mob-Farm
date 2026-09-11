/*
 * Copyright 2026 Markus Bordihn
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

package de.markusbordihn.easymobfarm.compat.jei;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.data.loot.MobFarmLootDisplay;
import de.markusbordihn.easymobfarm.data.loot.MobFarmLootDisplay.BonusDrop;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmTierLevel;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import java.util.ArrayList;
import java.util.List;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class MobFarmLootCategory implements IRecipeCategory<MobFarmLootDisplay> {

  public static final IRecipeType<MobFarmLootDisplay> RECIPE_TYPE =
      IRecipeType.create(Constants.MOD_ID, "mob_farm", MobFarmLootDisplay.class);

  private static final int WIDTH = 162;
  private static final int HEIGHT = 134;
  private static final int SLOT_SIZE = 18;
  private static final int DROPS_PER_ROW = 8;
  private static final int MAX_BASE_DROPS = DROPS_PER_ROW * 2;
  private static final int MAX_BONUS_DROPS = DROPS_PER_ROW;
  private static final int HEADER_TEXT_X = 23;
  private static final int TITLE_TOP = 3;
  private static final int PROCESSING_SPEED_TOP = 14;
  private static final int BASE_DROP_LABEL_TOP = 24;
  private static final int BASE_DROP_TOP = 34;
  private static final int BONUS_DROP_LABEL_TOP = 72;
  private static final int BONUS_DROP_TOP = 82;
  private static final int HINT_TOP = 103;
  private static final int MAX_HINT_LINES = 3;
  private static final int LINE_HEIGHT = 9;
  private static final int TEXT_COLOR = 0xFF404040;

  private final IDrawable slotBackground;
  private final IDrawable icon;

  public MobFarmLootCategory(final IGuiHelper guiHelper, final ItemLike iconItem) {
    this.slotBackground = guiHelper.getSlotDrawable();
    this.icon = guiHelper.createDrawableItemStack(new ItemStack(iconItem));
  }

  private static int getDropX(final int index) {
    return 1 + (index % DROPS_PER_ROW) * SLOT_SIZE;
  }

  private static Component getMobFarmTypeNames(final List<MobFarmType> mobFarmTypes) {
    MutableComponent mobFarmTypeNames = Component.empty();
    for (int i = 0; i < mobFarmTypes.size(); i++) {
      if (i > 0) {
        mobFarmTypeNames.append(", ");
      }
      mobFarmTypeNames.append(
          Component.translatable(Constants.BLOCK_PREFIX + mobFarmTypes.get(i).getId()));
    }
    return mobFarmTypeNames;
  }

  private static List<MobFarmType> getMobFarmTypesWithBonusDrop(
      final MobFarmLootDisplay mobFarmLootDisplay) {
    List<MobFarmType> mobFarmTypes = new ArrayList<>();
    for (BonusDrop bonusDrop : mobFarmLootDisplay.bonusDrops()) {
      for (MobFarmType mobFarmType : bonusDrop.mobFarmTypes()) {
        if (!mobFarmTypes.contains(mobFarmType)) {
          mobFarmTypes.add(mobFarmType);
        }
      }
    }
    return mobFarmTypes;
  }

  private static List<Component> getHints(final MobFarmLootDisplay mobFarmLootDisplay) {
    List<Component> hints = new ArrayList<>();
    if (mobFarmLootDisplay.requiresKilledByPlayer()) {
      hints.add(
          Component.translatable(Constants.TOOLTIP_FARM_PREFIX + "killed_by_player")
              .withStyle(ChatFormatting.RED));
    }
    if (!mobFarmLootDisplay.bonusDrops().isEmpty()) {
      hints.add(
          Component.translatable(
                  Constants.TOOLTIP_FARM_PREFIX + "loot_bonus_other_farm",
                  getMobFarmTypeNames(getMobFarmTypesWithBonusDrop(mobFarmLootDisplay)))
              .withStyle(ChatFormatting.GOLD));
    }
    if (mobFarmLootDisplay.supportsKnifeEnhancement()) {
      hints.add(
          Component.translatable(Constants.TEXT_PREFIX + "jei.knife_enhancement")
              .withStyle(ChatFormatting.DARK_GREEN));
    }
    return hints;
  }

  @Override
  public IRecipeType<MobFarmLootDisplay> getRecipeType() {
    return RECIPE_TYPE;
  }

  @Override
  public int getWidth() {
    return WIDTH;
  }

  @Override
  public int getHeight() {
    return HEIGHT;
  }

  @Override
  public Component getTitle() {
    return Component.translatable(Constants.TEXT_PREFIX + "jei.mob_farm");
  }

  @Override
  public IDrawable getIcon() {
    return this.icon;
  }

  @Override
  public void setRecipe(
      final IRecipeLayoutBuilder builder,
      final MobFarmLootDisplay mobFarmLootDisplay,
      final IFocusGroup focusGroup) {
    builder
        .addSlot(RecipeIngredientRole.INPUT, 1, 1)
        .setBackground(this.slotBackground, -1, -1)
        .addItemStacks(mobFarmLootDisplay.inputItems());

    List<ItemStack> baseDrops = mobFarmLootDisplay.baseDrops();
    for (int i = 0; i < Math.min(baseDrops.size(), MAX_BASE_DROPS); i++) {
      builder
          .addSlot(
              RecipeIngredientRole.OUTPUT,
              getDropX(i),
              BASE_DROP_TOP + (i / DROPS_PER_ROW) * SLOT_SIZE)
          .setBackground(this.slotBackground, -1, -1)
          .add(baseDrops.get(i));
    }

    List<BonusDrop> bonusDrops = mobFarmLootDisplay.bonusDrops();
    for (int i = 0; i < Math.min(bonusDrops.size(), MAX_BONUS_DROPS); i++) {
      BonusDrop bonusDrop = bonusDrops.get(i);
      builder
          .addSlot(RecipeIngredientRole.OUTPUT, getDropX(i), BONUS_DROP_TOP)
          .setBackground(this.slotBackground, -1, -1)
          .add(bonusDrop.item())
          .addRichTooltipCallback(
              (recipeSlotView, tooltip) ->
                  tooltip.add(
                      Component.translatable(
                              Constants.TEXT_PREFIX + "jei.bonus_drop",
                              getMobFarmTypeNames(bonusDrop.mobFarmTypes()))
                          .withStyle(ChatFormatting.GOLD)));
    }
  }

  @Override
  public void draw(
      final MobFarmLootDisplay mobFarmLootDisplay,
      final IRecipeSlotsView recipeSlotsView,
      final GuiGraphicsExtractor guiGraphics,
      final double mouseX,
      final double mouseY) {
    Font font = Minecraft.getInstance().font;

    guiGraphics.text(
        font,
        mobFarmLootDisplay.entityType().getDescription(),
        HEADER_TEXT_X,
        TITLE_TOP,
        TEXT_COLOR,
        false);
    guiGraphics.text(
        font,
        Component.translatable(
            Constants.TEXT_PREFIX + "jei.processing_speed",
            MobFarmBlockEntity.getProcessingSpeed(MobFarmTierLevel.TIER_0.getTierLevel()),
            MobFarmBlockEntity.getProcessingSpeed(MobFarmTierLevel.TIER_3.getTierLevel())),
        HEADER_TEXT_X,
        PROCESSING_SPEED_TOP,
        TEXT_COLOR,
        false);

    if (mobFarmLootDisplay.hasBaseDrops()) {
      guiGraphics.text(
          font,
          Component.translatable(Constants.TOOLTIP_FARM_PREFIX + "loot_base_drops"),
          1,
          BASE_DROP_LABEL_TOP,
          TEXT_COLOR,
          false);
    } else {
      drawWrappedText(
          guiGraphics,
          font,
          List.of(
              Component.translatable(Constants.TEXT_PREFIX + "jei.loot_preview_pending")
                  .withStyle(ChatFormatting.DARK_GRAY)),
          BASE_DROP_LABEL_TOP,
          MAX_HINT_LINES);
    }

    if (!mobFarmLootDisplay.bonusDrops().isEmpty()) {
      guiGraphics.text(
          font,
          Component.translatable(Constants.TEXT_PREFIX + "jei.bonus_drops"),
          1,
          BONUS_DROP_LABEL_TOP,
          TEXT_COLOR,
          false);
    }

    drawWrappedText(guiGraphics, font, getHints(mobFarmLootDisplay), HINT_TOP, MAX_HINT_LINES);
  }

  private void drawWrappedText(
      final GuiGraphicsExtractor guiGraphics,
      final Font font,
      final List<Component> lines,
      final int top,
      final int maxLines) {
    int drawnLines = 0;
    for (Component line : lines) {
      for (FormattedCharSequence wrappedLine : font.split(line, WIDTH - 2)) {
        if (drawnLines >= maxLines) {
          return;
        }

        guiGraphics.text(
            font, wrappedLine, 1, top + drawnLines * LINE_HEIGHT, TEXT_COLOR, false);
        drawnLines++;
      }
    }
  }
}
