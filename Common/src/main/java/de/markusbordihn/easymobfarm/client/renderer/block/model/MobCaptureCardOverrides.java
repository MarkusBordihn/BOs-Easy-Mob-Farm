package de.markusbordihn.easymobfarm.client.renderer.block.model;

import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.client.model.ModelManager;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedOverrides;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MobCaptureCardOverrides extends BakedOverrides {

  public MobCaptureCardOverrides(ModelBaker modelBaker, List<ItemOverride> itemOverrides) {
    super(modelBaker, itemOverrides);
  }

  @Override
  public BakedModel findOverride(
      ItemStack itemStack, ClientLevel clientLevel, LivingEntity entity, int seed) {
    BakedModel originalModel = super.findOverride(itemStack, clientLevel, entity, seed);
    if (!MobCaptureManager.hasMobCaptureData(itemStack)) {
      return originalModel;
    }

    // Get the model manager to resolve the model for the mob capture card.
    return ModelManager.getModel(MobCaptureManager.getMobCaptureData(itemStack), originalModel);
  }
}
