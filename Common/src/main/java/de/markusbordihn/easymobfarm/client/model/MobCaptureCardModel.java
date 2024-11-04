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
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class MobCaptureCardModel implements BakedModel {

  public static final String INVENTORY_LOCATION = "inventory";
  public static final ModelResourceLocation DEFAULT_MODEL =
      new ModelResourceLocation(Constants.MOD_ID + ":mob_capture_card/default", INVENTORY_LOCATION);
  public static final ModelResourceLocation DEFAULT_UNCOMMON_MODEL =
      new ModelResourceLocation(
          Constants.MOD_ID + ":mob_capture_card/default_uncommon", INVENTORY_LOCATION);
  public static final ModelResourceLocation DEFAULT_RARE_MODEL =
      new ModelResourceLocation(
          Constants.MOD_ID + ":mob_capture_card/default_rare", INVENTORY_LOCATION);
  public static final ModelResourceLocation DEFAULT_EPIC_MODEL =
      new ModelResourceLocation(
          Constants.MOD_ID + ":mob_capture_card/default_epic", INVENTORY_LOCATION);
  public static final ModelResourceLocation DEFAULT_FISH_MODEL =
      new ModelResourceLocation(
          Constants.MOD_ID + ":mob_capture_card/default_fish", INVENTORY_LOCATION);
  private final BakedModel originalModel;
  private final MobCaptureCardOverrides overrides;

  public MobCaptureCardModel(
      BakedModel originalModel,
      ModelBakery modelBakery,
      Function<ResourceLocation, UnbakedModel> modelFunction,
      ResourceLocation resourceLocation) {
    this.originalModel = originalModel;
    BlockModel blockModel =
        new BlockModel(
            resourceLocation,
            List.of(),
            Map.of(),
            false,
            null,
            ItemTransforms.NO_TRANSFORMS,
            List.of());
    this.overrides = new MobCaptureCardOverrides(modelBakery, blockModel, modelFunction, List.of());
  }

  public MobCaptureCardModel(
      BakedModel originalModel, ModelBakery modelBakery, ResourceLocation resourceLocation) {
    this(originalModel, modelBakery, null, resourceLocation);
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
  public ItemOverrides getOverrides() {
    return this.overrides;
  }
}
