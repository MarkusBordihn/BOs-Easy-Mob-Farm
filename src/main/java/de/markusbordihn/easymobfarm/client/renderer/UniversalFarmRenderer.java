/*
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

package de.markusbordihn.easymobfarm.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.client.renderer.helper.RenderHelper;
import de.markusbordihn.easymobfarm.menu.MobFarmMenu;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.entity.EntityType;

public class UniversalFarmRenderer extends MobFarmRendererBase<MobFarmBlockEntity> {

  public UniversalFarmRenderer(BlockEntityRendererProvider.Context context) {
    super(context);
  }

  @Override
  public void render(MobFarmBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
      MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
    super.render(blockEntity, partialTicks, poseStack, buffer, combinedLight, combinedOverlay);

    // Get unique farm id for caching, the renderer itself is a single instance.
    int farmId = blockEntity.getFarmId();

    // Reset render helper if no mob is captured.
    if (!blockEntity.hasItem(MobFarmMenu.CAPTURED_MOB_SLOT)) {
      resetRenderHelper(farmId);
      return;
    }

    // Get Render Helper for better performance and more FPS for other things.
    RenderHelper renderHelper = getRenderHelper(farmId, blockEntity);
    String farmMobType = blockEntity.getFarmMobType();
    String farmMobSubType = blockEntity.getFarmMobSubType();
    EntityType<?> farmMobEntityType = blockEntity.getFarmMobEntityType();

    // Render individual mob types if possible, because custom entity renderer is not optimized.
    // This makes a huge different with up to 20% more fps with a larger farm.
    if (renderHelper.renderAnimal(poseStack, buffer, combinedLight, farmMobType)
        || renderHelper.renderBee(poseStack, buffer, combinedLight, farmMobType, farmMobSubType,
        farmMobEntityType)
        || renderHelper.renderMonster(poseStack, buffer, combinedLight, farmMobType)
        || renderHelper.renderSpecialEntity(poseStack, buffer, combinedLight, farmMobType)
        || renderHelper.renderWaterEntity(poseStack, buffer, combinedLight, farmMobType)) {
      return;
    }

    // Only render custom model if we are not able to render the model otherwise.
    if (farmMobEntityType != null) {
      renderHelper.renderLivingEntity(poseStack, buffer, combinedLight, farmMobEntityType);
    }
  }
}
