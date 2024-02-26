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

package de.markusbordihn.easymobfarm.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.network.message.MessageRedstoneModeChange;

@EventBusSubscriber
public class NetworkHandler {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final int PROTOCOL_VERSION = 1;
  private static final SimpleChannel SIMPLE_CHANNEL =
      ChannelBuilder.named(new ResourceLocation(Constants.MOD_ID, "network"))
          .networkProtocolVersion(PROTOCOL_VERSION)
          .simpleChannel();
  private static int id = 0;

  public static void registerNetworkHandler(final FMLCommonSetupEvent event) {

    log.info(
        "{} Network Handler for {} with version {} ...",
        Constants.LOG_REGISTER_PREFIX,
        SIMPLE_CHANNEL,
        PROTOCOL_VERSION);

    event.enqueueWork(
        () -> {

          // Redstone Mode Change: Client -> Server
          SIMPLE_CHANNEL
              .messageBuilder(
                  MessageRedstoneModeChange.class, id++, NetworkDirection.PLAY_TO_SERVER)
              .decoder(MessageRedstoneModeChange::decode)
              .encoder(MessageRedstoneModeChange::encode)
              .consumerNetworkThread(MessageRedstoneModeChange::handle)
              .add();
        });
  }

  public static <M> void sendToServer(M message) {
    try {
      SIMPLE_CHANNEL.send(message, PacketDistributor.SERVER.noArg());
    } catch (Exception e) {
      log.error("Failed to send {} to server, got error: {}", message, e.getMessage());
    }
  }
}
