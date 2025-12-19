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

public class VoidBindingChainItem extends MobCatcherItem {

  public static final String ID = "void_binding_chain";

  public VoidBindingChainItem() {
    this(
        new Properties()
            .setId(
                ResourceKey.create(
                    Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, ID))));
  }

  public VoidBindingChainItem(Properties properties) {
    super(properties.durability(MobCatcherConfig.VOID_BINDING_CHAIN_MAX_DURABILITY));
  }

  @Override
  public float getRequiredHealthPercentageToCapture() {
    return MobCatcherConfig.VOID_BINDING_CHAIN_REQUIRED_HEALTH_PERCENTAGE_TO_CAPTURE;
  }

  @Override
  public float getMaxEntityHeightToCapture() {
    return MobCatcherConfig.VOID_BINDING_CHAIN_MAX_ENTITY_HEIGHT_TO_CAPTURE;
  }

  @Override
  public float getMaxEntityWidthToCapture() {
    return MobCatcherConfig.VOID_BINDING_CHAIN_MAX_ENTITY_WIDTH_TO_CAPTURE;
  }

  @Override
  public Set<String> getAllowList() {
    return MobCatcherConfig.VOID_BINDING_CHAIN_ALLOW_LIST;
  }

  @Override
  public Set<String> getDenyList() {
    return MobCatcherConfig.VOID_BINDING_CHAIN_DENY_LIST;
  }
}
