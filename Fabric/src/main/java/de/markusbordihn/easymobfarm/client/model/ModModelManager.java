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

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class ModModelManager implements ModelManagerInterface {

  /**
   * This is needed to make sure we are using the modified Fabric Model Manager instead of the
   * vanilla one, which is not able to handle the custom models in 1.21.1 and newer.
   */
  @Override
  public BakedModel getModel(ModelResourceLocation modelResourceLocation) {
    if (modelResourceLocation == null) {
      return null;
    }
    BakedModel bakedModel =
        Minecraft.getInstance().getModelManager().getModel(modelResourceLocation.id());
    if (bakedModel == Minecraft.getInstance().getModelManager().getMissingModel()) {
      if (KNOWN_MISSING_MODELS.add(modelResourceLocation.id().toString())) {
        log.error("{} Missing model for '{}'", LOG_PREFIX, modelResourceLocation.id());
      }
      return null;
    }
    return bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()
        ? bakedModel
        : null;
  }
}
