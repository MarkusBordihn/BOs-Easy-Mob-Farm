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

package de.markusbordihn.easymobfarm.client.item;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.CapturedMob;
import de.markusbordihn.easymobfarm.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModItemProperties {

  public static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected ModItemProperties() {}

  public static void registerItemProperties(final FMLClientSetupEvent event) {
    log.info("{} Client setup ...", Constants.LOG_REGISTER_PREFIX);

    event.enqueueWork(
        () -> {
          ItemProperties.register(
              ModItems.CATCH_CAGE.get(),
              new ResourceLocation(Constants.MOD_ID, "open"),
              (itemStack, clientLevel, livingEntity, id) ->
                  (livingEntity == null
                          || !itemStack.is(ModItems.CATCH_CAGE.get())
                          || livingEntity.getMainHandItem().isEmpty()
                          || itemStack != livingEntity.getMainHandItem()
                          || CapturedMob.hasCapturedMob(itemStack))
                      ? 0.0F
                      : 1.0F);
          ItemProperties.register(
              ModItems.CATCH_CAGE_SMALL.get(),
              new ResourceLocation(Constants.MOD_ID, "open"),
              (itemStack, clientLevel, livingEntity, id) ->
                  (livingEntity == null
                          || !itemStack.is(ModItems.CATCH_CAGE_SMALL.get())
                          || livingEntity.getMainHandItem().isEmpty()
                          || itemStack != livingEntity.getMainHandItem()
                          || CapturedMob.hasCapturedMob(itemStack))
                      ? 0.0F
                      : 1.0F);
          ItemProperties.register(
              ModItems.URN_SMALL.get(),
              new ResourceLocation(Constants.MOD_ID, "open"),
              (itemStack, clientLevel, livingEntity, id) ->
                  (livingEntity == null
                          || !itemStack.is(ModItems.URN_SMALL.get())
                          || livingEntity.getMainHandItem().isEmpty()
                          || itemStack != livingEntity.getMainHandItem()
                          || CapturedMob.hasCapturedMob(itemStack))
                      ? 0.0F
                      : 1.0F);
        });
  }
}
