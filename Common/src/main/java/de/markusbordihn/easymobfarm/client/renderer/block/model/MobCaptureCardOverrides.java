package de.markusbordihn.easymobfarm.client.renderer.block.model;

import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.client.model.ModelManager;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MobCaptureCardOverrides extends ItemOverrides {

  public MobCaptureCardOverrides(
      ModelBaker modelBaker, BlockModel blockModel, List<ItemOverride> itemOverrides) {
    super(modelBaker, blockModel, itemOverrides);
  }

  @Override
  public BakedModel resolve(
      BakedModel originalModel,
      ItemStack itemStack,
      ClientLevel clientLevel,
      LivingEntity entity,
      int seed) {
    if (!MobCaptureManager.hasMobCaptureData(itemStack)) {
      return originalModel;
    }

    // Get the model manager to resolve the model for the mob capture card.
    return ModelManager.getModel(MobCaptureManager.getMobCaptureData(itemStack), originalModel);
  }
}
