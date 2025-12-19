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

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.conditional.MobCatcherHasMobCaptureData;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.numeric.MobFarmBlockItemTierLevel;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardEntityColor;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardEntityType;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardEntityVariant;
import de.markusbordihn.easymobfarm.client.renderer.item.properties.select.MobCaptureCardRarity;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;

public class ModItemProperties {

  public static void registerItemProperties() {
    ConditionalItemModelProperties.ID_MAPPER.put(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "has_mob_capture_data"),
        MobCatcherHasMobCaptureData.MAP_CODEC);

    RangeSelectItemModelProperties.ID_MAPPER.put(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_farm_block_item_tier_level"),
        MobFarmBlockItemTierLevel.MAP_CODEC);

    SelectItemModelProperties.ID_MAPPER.put(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_entity_color"),
        MobCaptureCardEntityColor.TYPE);

    SelectItemModelProperties.ID_MAPPER.put(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_entity_type"),
        MobCaptureCardEntityType.TYPE);

    SelectItemModelProperties.ID_MAPPER.put(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_entity_variant"),
        MobCaptureCardEntityVariant.TYPE);

    SelectItemModelProperties.ID_MAPPER.put(
        Identifier.fromNamespaceAndPath(Constants.MOD_ID, "mob_capture_card_rarity"),
        MobCaptureCardRarity.TYPE);
  }
}
