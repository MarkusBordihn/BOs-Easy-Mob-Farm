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
import de.markusbordihn.easymobfarm.client.renderer.block.model.MobCaptureCardOverrides;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.client.renderer.block.model.BakedOverrides;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class MobCaptureCardModel implements BakedModel {

  private final BakedModel originalModel;
  private final MobCaptureCardOverrides overrides;

  public MobCaptureCardModel(
      BakedModel originalModel,
      ModelBaker modelBaker,
      Function<ResourceLocation, UnbakedModel> modelFunction) {
    this.originalModel = originalModel;
    BlockModel blockModel =
        new BlockModel(
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card"),
            List.of(),
            Map.of(),
            false,
            null,
            ItemTransforms.NO_TRANSFORMS,
            List.of());
    this.overrides = new MobCaptureCardOverrides(modelBaker, List.of());
  }

  public MobCaptureCardModel(BakedModel originalModel, ModelBaker modelBaker) {
    this(originalModel, modelBaker, null);
  }

  @Override
  public List<BakedQuad> getQuads(
      BlockState blockState, Direction direction, RandomSource randomSource) {
    return originalModel.getQuads(blockState, direction, randomSource);
  }

  @Override
  public boolean useAmbientOcclusion() {
    return false;
  }

  @Override
  public boolean isGui3d() {
    return false;
  }

  @Override
  public boolean usesBlockLight() {
    return false;
  }

  @Override
  public boolean isCustomRenderer() {
    return false;
  }

  @Override
  public TextureAtlasSprite getParticleIcon() {
    return originalModel.getParticleIcon();
  }

  @Override
  public ItemTransforms getTransforms() {
    return ItemTransforms.NO_TRANSFORMS;
  }

  @Override
  public BakedOverrides overrides() {
    return this.overrides;
  }
}
