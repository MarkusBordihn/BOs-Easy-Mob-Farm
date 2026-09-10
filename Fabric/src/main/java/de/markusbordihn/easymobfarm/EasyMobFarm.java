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
import de.markusbordihn.easymobfarm.menu.CardBinderMenu;
import de.markusbordihn.easymobfarm.menu.ModMenuTypes;
import de.markusbordihn.easymobfarm.network.message.client.SyncLootPreviewBatchMessage;
import de.markusbordihn.easymobfarm.network.message.client.SyncLootPreviewMessage;
import de.markusbordihn.easymobfarm.resources.MobCaptureCardResourceManagerWrapper;
import de.markusbordihn.easymobfarm.server.ServerEventHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.packs.PackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EasyMobFarm implements ModInitializer {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  @Override
  public void onInitialize() {
    log.info("Initializing {} (Fabric) ...", Constants.MOD_NAME);

    log.debug("{} Debug Manager ...", Constants.LOG_REGISTER_PREFIX);
    if (System.getProperty("fabric.development") != null) {
      DebugManager.setDevelopmentEnvironment(true);
    }
    DebugManager.checkForDebugLogging(Constants.LOG_NAME);

    log.debug("{} Constants ...", Constants.LOG_REGISTER_PREFIX);
    Constants.GAME_DIR = FabricLoader.getInstance().getGameDir();
    Constants.CONFIG_DIR = FabricLoader.getInstance().getConfigDir();

    log.debug("{} Configuration ...", Constants.LOG_REGISTER_PREFIX);
    Config.register(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER);

    log.debug("{} Compatibility Handler ...", Constants.LOG_REGISTER_PREFIX);
    CompatManager.registerCompatHandler(new CompatHandler());

    log.debug("{} server-side resource listener ...", Constants.LOG_REGISTER_PREFIX);
    ResourceManagerHelper.get(PackType.SERVER_DATA)
        .registerReloadListener(new MobCaptureCardResourceManagerWrapper());

    log.debug("{} Experience Manager ...", Constants.LOG_REGISTER_PREFIX);
    ExperienceManager.registerExperienceManager(new ModExperienceManager());

    log.debug("{} Blocks ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.registerModBlocks();

    log.debug("{} Blocks Entities ...", Constants.LOG_REGISTER_PREFIX);
    ModBlocks.registerModBlockEntities();

    log.debug("{} Block Items ...", Constants.LOG_REGISTER_PREFIX);
    ModBlockItems.registerModBlockItems();

    log.debug("{} Items ...", Constants.LOG_REGISTER_PREFIX);
    ModItems.registerModItems();

    log.debug("{} Command register event ...", Constants.LOG_REGISTER_PREFIX);
    CommandRegistrationCallback.EVENT.register(
        (dispatcher, commandBuildContext, commandSelection) ->
            CommandManager.registerCommands(dispatcher, commandBuildContext));

    log.debug("{} Menu Types ...", Constants.LOG_REGISTER_PREFIX);
    ModMenuTypes.register();
    CardBinderMenu.MENU_TYPE_SUPPLIER = () -> ModMenuTypes.CARD_BINDER_MENU;
    SyncLootPreviewMessage.SENDER =
        (player, msg) -> {
          FriendlyByteBuf buffer = PacketByteBufs.create();
          msg.write(buffer);
          ServerPlayNetworking.send(player, SyncLootPreviewMessage.MESSAGE_ID, buffer);
        };
    SyncLootPreviewBatchMessage.SENDER =
        (player, msg) -> {
          FriendlyByteBuf buffer = PacketByteBufs.create();
          msg.write(buffer);
          ServerPlayNetworking.send(player, SyncLootPreviewBatchMessage.MESSAGE_ID, buffer);
        };

    log.debug("{} Server Event Handler ...", Constants.LOG_REGISTER_PREFIX);
    ServerEventHandler.registerServerEvents();
  }
}
