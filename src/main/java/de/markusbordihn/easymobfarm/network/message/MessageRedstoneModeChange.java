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

package de.markusbordihn.easymobfarm.network.message;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.entity.MobFarmBlockEntity;
import de.markusbordihn.easymobfarm.data.RedstoneMode;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageRedstoneModeChange {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected final BlockPos blockPos;
  protected final RedstoneMode redstoneMode;

  public MessageRedstoneModeChange(BlockPos blockPos, RedstoneMode redstoneMode) {
    this.blockPos = blockPos;
    this.redstoneMode = redstoneMode;
  }

  public static void handle(
      MessageRedstoneModeChange message, Supplier<NetworkEvent.Context> contextSupplier) {
    NetworkEvent.Context context = contextSupplier.get();
    context.enqueueWork(() -> handlePacket(message, context));
    context.setPacketHandled(true);
  }

  public static void handlePacket(MessageRedstoneModeChange message, NetworkEvent.Context context) {
    ServerPlayer serverPlayer = context.getSender();
    if (serverPlayer == null) {
      return;
    }

    // Validate block position
    BlockPos blockPos = message.getBlockPos();
    if (blockPos == null) {
      log.error("Received invalid block position for redstone mode change!");
      return;
    }

    // Validate block entity by block position
    BlockEntity blockEntity = serverPlayer.getLevel().getBlockEntity(blockPos);
    if (blockEntity == null) {
      log.error("Received invalid block entity for redstone mode change!");
      return;
    }

    // Validate block entity type
    if (!(blockEntity instanceof MobFarmBlockEntity)) {
      log.error("Received invalid block entity type for redstone mode change!");
      return;
    }

    // Validate redstone mode
    RedstoneMode redstoneMode = message.getRedstoneMode();
    if (redstoneMode == null) {
      log.error("Received invalid redstone mode for redstone mode change!");
      return;
    }

    // Change redstone mode
    MobFarmBlockEntity mobFarmBlockEntity = (MobFarmBlockEntity) blockEntity;
    log.debug("Change redstone mode for {} to {}", mobFarmBlockEntity, redstoneMode);
    mobFarmBlockEntity.setRedstoneMode(redstoneMode);
  }

  public RedstoneMode getRedstoneMode() {
    return this.redstoneMode;
  }

  public BlockPos getBlockPos() {
    return this.blockPos;
  }
}
