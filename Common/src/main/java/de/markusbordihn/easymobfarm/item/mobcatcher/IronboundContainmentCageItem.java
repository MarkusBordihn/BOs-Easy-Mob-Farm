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

package de.markusbordihn.easymobfarm.item.mobcatcher;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.MobCatcherConfig;
import java.util.Set;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class IronboundContainmentCageItem extends MobCatcherItem {

  public static final String ID = "ironbound_containment_cage";

  public IronboundContainmentCageItem() {
    this(
        new Item.Properties()
            .setId(
                ResourceKey.create(
                    Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, ID))));
  }

  public IronboundContainmentCageItem(Item.Properties properties) {
    super(properties.durability(MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_MAX_DURABILITY));
  }

  @Override
  public float getRequiredHealthPercentageToCapture() {
    return MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE;
  }

  @Override
  public float getMaxEntityHeightToCapture() {
    return MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_HEIGHT_TO_CAPTURE;
  }

  @Override
  public float getMaxEntityWidthToCapture() {
    return MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_MAX_ENTITY_WIDTH_TO_CAPTURE;
  }

  @Override
  public Set<String> getAllowList() {
    return MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_ALLOW_LIST;
  }

  @Override
  public Set<String> getDenyList() {
    return MobCatcherConfig.IRONBOUND_CONTAINMENT_CAGE_DENY_LIST;
  }
}
