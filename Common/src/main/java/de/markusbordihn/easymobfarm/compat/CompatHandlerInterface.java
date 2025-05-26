/*
 * Copyright 2023 Markus Bordihn
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

package de.markusbordihn.easymobfarm.compat;

import de.markusbordihn.easymobfarm.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface CompatHandlerInterface {

  String COMPAT_LOG_PREFIX = "[Compat Manager]";
  Logger log = LogManager.getLogger(Constants.LOG_NAME);

  boolean isModLoaded(String modId);

  default void register() {
    CompatConstants.MOD_FARMERS_DELIGHT_LOADED =
        isModLoaded(CompatConstants.MOD_FARMERS_DELIGHT_ID);
    logFoundMod(
        CompatConstants.MOD_FARMERS_DELIGHT_NAME,
        CompatConstants.MOD_FARMERS_DELIGHT_ID,
        CompatConstants.MOD_FARMERS_DELIGHT_LOADED);

    CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED =
        isModLoaded(CompatConstants.MOD_SWAMPIER_SWAMPS_ID);
    logFoundMod(
        CompatConstants.MOD_SWAMPIER_SWAMPS_NAME,
        CompatConstants.MOD_SWAMPIER_SWAMPS_ID,
        CompatConstants.MOD_SWAMPIER_SWAMPS_LOADED);
  }

  default void logFoundMod(String modName, String modId, boolean modLoaded) {
    log.info("{} {} mod with id '{}' loaded: {}", COMPAT_LOG_PREFIX, modName, modId, modLoaded);
  }
}
