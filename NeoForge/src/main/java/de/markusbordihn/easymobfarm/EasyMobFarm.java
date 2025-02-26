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

package de.markusbordihn.easymobfarm;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment;
import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.compat.CompatHandler;
import de.markusbordihn.easymobfarm.compat.CompatManager;
import de.markusbordihn.easymobfarm.component.ModDataComponents;
import de.markusbordihn.easymobfarm.config.Config;
import de.markusbordihn.easymobfarm.debug.DebugManager;
import de.markusbordihn.easymobfarm.experience.ExperienceManager;
import de.markusbordihn.easymobfarm.experience.ModExperienceManager;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import de.markusbordihn.easymobfarm.item.ModItems;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;
import java.util.Optional;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Constants.MOD_ID)
public class EasyMobFarm {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @SuppressWarnings({"java:S1118", "java:S2440"})
  public EasyMobFarm(IEventBus modEventBus) {
    log.info("Initializing {} (NeoForge) ...", Constants.MOD_NAME);

    log.info("{} Debug Manager ...", Constants.LOG_REGISTER_PREFIX);
    Optional<String> version =
        Launcher.INSTANCE.environment().getProperty(IEnvironment.Keys.VERSION.get());
    if (version.isPresent() && "MOD_DEV".equals(version.get())) {
      DebugManager.setDevelopmentEnvironment(true);
    }
    DebugManager.checkForDebugLogging(Constants.LOG_NAME);

    log.info("{} Constants ...", Constants.LOG_REGISTER_PREFIX);
    Constants.GAME_DIR = FMLPaths.GAMEDIR.get();
    Constants.CONFIG_DIR = FMLPaths.CONFIGDIR.get();

    log.info("{} Configuration ...", Constants.LOG_REGISTER_PREFIX);
    Config.register(FMLEnvironment.dist == Dist.DEDICATED_SERVER);

    log.info("{} Compatibility Handler ...", Constants.LOG_REGISTER_PREFIX);
    CompatManager.registerCompatHandler(new CompatHandler());

    log.info("{} Experience Manager ...", Constants.LOG_REGISTER_PREFIX);
    ExperienceManager.registerExperienceManager(new ModExperienceManager());

    log.info("{} Blocks ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.BLOCKS.register(modEventBus);

    log.info("{} Blocks Entities ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.BLOCK_ENTITY_TYPES.register(modEventBus);

    log.info("{} Block Items ...", Constants.LOG_REGISTER_PREFIX);
    ModBlockItems.ITEMS.register(modEventBus);

    log.info("{} Items ...", Constants.LOG_REGISTER_PREFIX);
    ModItems.ITEMS.register(modEventBus);

    log.info("{} Menu Types ...", Constants.LOG_REGISTER_PREFIX);
    ModMenuTypes.MENU_TYPES.register(modEventBus);

    log.info("{} Mod Data Components ...", Constants.LOG_REGISTER_PREFIX);
    ModDataComponents.DATA_COMPONENTS.register(modEventBus);
  }
}
