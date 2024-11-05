package de.markusbordihn.easymobfarm.client.renderer.block.model;

import de.markusbordihn.easymobfarm.capture.MobCaptureManager;
import de.markusbordihn.easymobfarm.client.model.MobCaptureCardModel;
import de.markusbordihn.easymobfarm.config.MobCaptureCardModelsConfig;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MobCaptureCardOverrides extends ItemOverrides {

  public MobCaptureCardOverrides(
      ModelBaker modelBaker, BlockModel blockModel, List<ItemOverride> itemOverrides) {
    super(modelBaker, blockModel, itemOverrides);
  }

  private static boolean isFish(EntityType<?> entityType) {
    ResourceLocation entityId = EntityType.getKey(entityType);
    Item correspondingItem = BuiltInRegistries.ITEM.get(entityId);
    return correspondingItem != Items.AIR
        && correspondingItem.builtInRegistryHolder().is(ItemTags.FISHES);
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

    // Try to find the best matching model for the mob type.
    MobCaptureData mobCaptureData = MobCaptureManager.getMobCaptureData(itemStack);
    ModelResourceLocation modelResourceLocation =
        MobCaptureCardModelsConfig.getModelResourceLocation(mobCaptureData.type());
    if (mobCaptureData.hasColor() || mobCaptureData.hasVariant()) {
      modelResourceLocation =
          MobCaptureCardModelsConfig.getModelResourceLocation(
              mobCaptureData.type(), mobCaptureData.variant(), mobCaptureData.color());
    }

    // Use the custom model if available, otherwise use the original default model.
    if (modelResourceLocation != null) {
      BakedModel bakedModel =
          Minecraft.getInstance().getModelManager().getModel(modelResourceLocation);
      if (bakedModel != Minecraft.getInstance().getModelManager().getMissingModel()) {
        return bakedModel;
      }
    }

    // Use rarity based model if available.
    if (mobCaptureData.hasRarity()) {
      switch (mobCaptureData.rarity()) {
        case UNCOMMON:
          return Minecraft.getInstance()
              .getModelManager()
              .getModel(MobCaptureCardModel.DEFAULT_UNCOMMON_MODEL);
        case RARE:
          return Minecraft.getInstance()
              .getModelManager()
              .getModel(MobCaptureCardModel.DEFAULT_RARE_MODEL);
        case EPIC:
          return Minecraft.getInstance()
              .getModelManager()
              .getModel(MobCaptureCardModel.DEFAULT_EPIC_MODEL);
        default:
          break;
      }
    }

    // Check for specify tags and use the model if available.
    if (isFish(mobCaptureData.entityType())) {
      return Minecraft.getInstance()
          .getModelManager()
          .getModel(MobCaptureCardModel.DEFAULT_FISH_MODEL);
    }

    // Check if we have a valid original model to return.
    if (originalModel != Minecraft.getInstance().getModelManager().getMissingModel()
        && originalModel.getParticleIcon()
            != Minecraft.getInstance().getModelManager().getMissingModel().getParticleIcon()) {
      return originalModel;
    }

    // Return default model if nothing else is available.
    return Minecraft.getInstance().getModelManager().getModel(MobCaptureCardModel.DEFAULT_MODEL);
  }
}
