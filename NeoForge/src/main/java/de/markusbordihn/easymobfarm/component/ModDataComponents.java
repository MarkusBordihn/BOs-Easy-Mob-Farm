/*
 * Copyright 2025 Markus Bordihn
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

package de.markusbordihn.easymobfarm.component;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmData;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber()
public class ModDataComponents {

  public static final DeferredRegister.DataComponents DATA_COMPONENTS =
      DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);
  public static final Supplier<DataComponentType<MobCaptureData>> MOB_CAPTURE_DATA =
      DATA_COMPONENTS.registerComponentType(
          MobCaptureData.ID,
          builder ->
              builder
                  .persistent(MobCaptureData.CODEC)
                  .networkSynchronized(MobCaptureData.STREAM_CODEC));
  public static final Supplier<DataComponentType<MobFarmData>> MOB_FARM_DATA =
      DATA_COMPONENTS.registerComponentType(
          MobFarmData.ID,
          builder ->
              builder.persistent(MobFarmData.CODEC).networkSynchronized(MobFarmData.STREAM_CODEC));

  private ModDataComponents() {}

  @SubscribeEvent
  public static void onCommonSetup(FMLCommonSetupEvent event) {
    event.enqueueWork(
        () -> {
          DataComponents.registerMobCaptureData(MOB_CAPTURE_DATA);
          DataComponents.registerMobFarmData(MOB_FARM_DATA);
        });
  }
}
