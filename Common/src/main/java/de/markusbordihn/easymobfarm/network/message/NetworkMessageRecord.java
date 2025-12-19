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

package de.markusbordihn.easymobfarm.network.message;

import de.markusbordihn.easymobfarm.Constants;
import io.netty.buffer.Unpooled;
import java.util.Random;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface NetworkMessageRecord extends CustomPacketPayload {

  Logger log = LogManager.getLogger(Constants.LOG_NAME);

  Random RANDOM = new Random();

  Identifier id();

  void write(FriendlyByteBuf friendlyByteBuf);

  default FriendlyByteBuf payload() {
    FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(Unpooled.buffer());
    write(friendlyByteBuf);
    return friendlyByteBuf;
  }

  default void handleClient() {
    log.error("Network message client handler not implemented for {}", this);
  }

  default void handleServer(ServerPlayer serverPlayer) {
    log.error("Network message server handler not implemented for {}", this);
  }
}
