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

package de.markusbordihn.easymobfarm.client.model;

import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Rarity;

public class ModModelManager implements ModelManagerInterface {

  @Override
  public BakedModel getModel(String type, String variant, DyeColor color) {
    BakedModel bakedModel =
        Minecraft.getInstance()
            .getModelManager()
            .getModel(MobCaptureCardModelsConfig.getResourceLocation(type, variant, color));
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }

  @Override
  public BakedModel getModel(String type) {
    BakedModel bakedModel =
        Minecraft.getInstance()
            .getModelManager()
            .getModel(MobCaptureCardModelsConfig.getResourceLocation(type));
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }

  @Override
  public BakedModel getModel(Rarity rarity) {
    BakedModel bakedModel =
        switch (rarity) {
          case COMMON -> null;
          case UNCOMMON ->
              Minecraft.getInstance().getModelManager().getModel(getDefaultUncommonModelLocation());
          case RARE ->
              Minecraft.getInstance().getModelManager().getModel(getDefaultRareModelLocation());
          case EPIC ->
              Minecraft.getInstance().getModelManager().getModel(getDefaultEpicModelLocation());
        };
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }

  @Override
  public BakedModel getModel(EntityType<?> entityType) {
    return isFish(entityType)
        ? Minecraft.getInstance().getModelManager().getModel(getDefaultFishModelLocation())
        : null;
  }

  @Override
  public BakedModel getDefaultModel() {
    BakedModel bakedModel =
        Minecraft.getInstance().getModelManager().getModel(getDefaultModelLocation());
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }
}
