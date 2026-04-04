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

package de.markusbordihn.easymobfarm.client.renderer.item.properties;

import com.mojang.serialization.MapCodec;
import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.conditional.MobCatcherHasMobCaptureData;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.numeric.MobFarmBlockItemTierLevel;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardEntityColor;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardEntityType;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardEntityVariant;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardRarity;
import java.lang.reflect.Field;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModItemProperties {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @SuppressWarnings("unchecked")
  public static void registerItemProperties() {
    try {
      Field conditionalMapperField =
          ConditionalItemModelProperties.class.getDeclaredField("ID_MAPPER");
      conditionalMapperField.setAccessible(true);
      ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends ConditionalItemModelProperty>>
          conditionalMapper =
              (ExtraCodecs.LateBoundIdMapper<
                      Identifier, MapCodec<? extends ConditionalItemModelProperty>>)
                  conditionalMapperField.get(null);
      conditionalMapper.put(
          Identifier.fromNamespaceAndPath(Constants.MOD_ID, "has_mob_capture_data"),
          MobCatcherHasMobCaptureData.MAP_CODEC);

      Field rangeMapperField = RangeSelectItemModelProperties.class.getDeclaredField("ID_MAPPER");
      rangeMapperField.setAccessible(true);
      ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends RangeSelectItemModelProperty>>
          rangeMapper =
              (ExtraCodecs.LateBoundIdMapper<
                      Identifier, MapCodec<? extends RangeSelectItemModelProperty>>)
                  rangeMapperField.get(null);
      rangeMapper.put(
          Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_farm_block_item_tier_level"),
          MobFarmBlockItemTierLevel.MAP_CODEC);

      Field selectMapperField = SelectItemModelProperties.class.getDeclaredField("ID_MAPPER");
      selectMapperField.setAccessible(true);
      ExtraCodecs.LateBoundIdMapper<Identifier, SelectItemModelProperty.Type<?, ?>> selectMapper =
          (ExtraCodecs.LateBoundIdMapper<Identifier, SelectItemModelProperty.Type<?, ?>>)
              selectMapperField.get(null);
      selectMapper.put(
          Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_entity_color"),
          MobCaptureCardEntityColor.TYPE);
      selectMapper.put(
          Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_entity_type"),
          MobCaptureCardEntityType.TYPE);
      selectMapper.put(
          Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_entity_variant"),
          MobCaptureCardEntityVariant.TYPE);
      selectMapper.put(
          Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_rarity"),
          MobCaptureCardRarity.TYPE);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      log.error("Failed to register item model properties via reflection: {}", e.getMessage());
    }
  }
}
