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

package de.markusbordihn.easymobfarm.component;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmData;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataComponents {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public static DataComponentType<MobCaptureData> MOB_CAPTURE_DATA;
  public static DataComponentType<MobFarmData> MOB_FARM_DATA;

  private DataComponents() {}

  public static void registerMobCaptureData() {
    log.info("{} Data Components ...", Constants.MOD_NAME);
    MOB_CAPTURE_DATA =
        Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, MobCaptureData.ID),
            DataComponentType.<MobCaptureData>builder()
                .persistent(MobCaptureData.CODEC)
                .networkSynchronized(MobCaptureData.STREAM_CODEC)
                .build());

    MOB_FARM_DATA =
        Registry.register(
            BuiltInRegistries.DATA_COMPONENT_TYPE,
            ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, MobFarmData.ID),
            DataComponentType.<MobFarmData>builder()
                .persistent(MobFarmData.CODEC)
                .networkSynchronized(MobFarmData.STREAM_CODEC)
                .build());
  }

  public static void registerMobCaptureData(Supplier<DataComponentType<MobCaptureData>> supplier) {
    log.info("{} Mob Capture Data Component {} ...", Constants.MOD_NAME, supplier.get());
    MOB_CAPTURE_DATA = supplier.get();
  }

  public static void registerMobFarmData(Supplier<DataComponentType<MobFarmData>> supplier) {
    log.info("{} Mob Farm Data Component {} ...", Constants.MOD_NAME, supplier.get());
    MOB_FARM_DATA = supplier.get();
  }
}
