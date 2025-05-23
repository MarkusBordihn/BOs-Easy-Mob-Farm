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

import de.markusbordihn.easymobfarm.block.ModBlocks;
import de.markusbordihn.easymobfarm.commands.manager.CommandManager;
import de.markusbordihn.easymobfarm.compat.CompatHandler;
import de.markusbordihn.easymobfarm.compat.CompatManager;
import de.markusbordihn.easymobfarm.config.Config;
import de.markusbordihn.easymobfarm.debug.DebugManager;
import de.markusbordihn.easymobfarm.experience.ExperienceManager;
import de.markusbordihn.easymobfarm.experience.ModExperienceManager;
import de.markusbordihn.easymobfarm.item.ModBlockItems;
import de.markusbordihn.easymobfarm.item.ModItems;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;
import de.markusbordihn.easymobfarm.resources.MobCaptureCardResourceManagerWrapper;
import de.markusbordihn.easymobfarm.server.ServerEventHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyMobFarm implements ModInitializer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @Override
  public void onInitialize() {
    log.info("Initializing {} (Fabric) ...", Constants.MOD_NAME);

    log.info("{} Debug Manager ...", Constants.LOG_REGISTER_PREFIX);
    if (System.getProperty("fabric.development") != null) {
      DebugManager.setDevelopmentEnvironment(true);
    }
    DebugManager.checkForDebugLogging(Constants.LOG_NAME);

    log.info("{} Constants ...", Constants.LOG_REGISTER_PREFIX);
    Constants.GAME_DIR = FabricLoader.getInstance().getGameDir();
    Constants.CONFIG_DIR = FabricLoader.getInstance().getConfigDir();

    log.info("{} Configuration ...", Constants.LOG_REGISTER_PREFIX);
    Config.register(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER);

    log.info("{} Compatibility Handler ...", Constants.LOG_REGISTER_PREFIX);
    CompatManager.registerCompatHandler(new CompatHandler());

    log.info("{} server-side resource listener ...", Constants.LOG_REGISTER_PREFIX);
    ResourceManagerHelper.get(PackType.SERVER_DATA)
        .registerReloadListener(new MobCaptureCardResourceManagerWrapper());

    log.info("{} Experience Manager ...", Constants.LOG_REGISTER_PREFIX);
    ExperienceManager.registerExperienceManager(new ModExperienceManager());

    log.info("{} Blocks ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.registerModBlocks();

    log.info("{} Blocks Entities ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.registerModBlockEntities();

    log.info("{} Block Items ...", Constants.LOG_REGISTER_PREFIX);
    ModBlockItems.registerModBlockItems();

    log.info("{} Items ...", Constants.LOG_REGISTER_PREFIX);
    ModItems.registerModItems();

    log.info("{} Command register event ...", Constants.LOG_REGISTER_PREFIX);
    CommandRegistrationCallback.EVENT.register(
        (dispatcher, dedicated) -> CommandManager.registerCommands(dispatcher));

    log.info("{} Menu Types ...", Constants.LOG_REGISTER_PREFIX);
    ModMenuTypes.register();

    log.info("{} Server Event Handler ...", Constants.LOG_REGISTER_PREFIX);
    ServerEventHandler.registerServerEvents();
  }
}
