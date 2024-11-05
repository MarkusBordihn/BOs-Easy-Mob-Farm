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

import de.markusbordihn.easymobfarm.Constants;
import java.util.Collection;
import java.util.function.Function;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class UnbakedMobCaptureCardModel implements UnbakedModel {

  public static final ResourceLocation MODEL =
      new ResourceLocation(Constants.MOD_ID, "item/mob_capture_card/dynamic");
  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private final UnbakedModel originalUnbakedModel;

  public UnbakedMobCaptureCardModel(UnbakedModel originalUnbakedModel) {
    this.originalUnbakedModel = originalUnbakedModel;
  }

  @Override
  public Collection<ResourceLocation> getDependencies() {
    return originalUnbakedModel.getDependencies();
  }

  @Override
  public void resolveParents(Function<ResourceLocation, UnbakedModel> function) {
    originalUnbakedModel.resolveParents(function);
  }

  @Override
  public @Nullable BakedModel bake(
      ModelBaker modelBaker,
      Function<Material, TextureAtlasSprite> textureGetter,
      ModelState modelState,
      ResourceLocation resourceLocation) {
    BakedModel originalBakedModel =
        originalUnbakedModel.bake(modelBaker, textureGetter, modelState, resourceLocation);
    if (originalBakedModel == null) {
      log.error("Unable to bake original model for resource location {}", resourceLocation);
      return null;
    }
    return new MobCaptureCardModel(originalBakedModel, modelBaker, resourceLocation);
  }
}
