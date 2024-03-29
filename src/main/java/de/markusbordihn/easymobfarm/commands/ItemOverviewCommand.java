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

package de.markusbordihn.easymobfarm.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.markusbordihn.easymobfarm.Constants;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemOverviewCommand extends CustomCommand {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final ItemOverviewCommand command = new ItemOverviewCommand();
  private static final String NO_ITEMS_TEXT =
      "Unable to find any items. Server / World is not loaded?";

  public static ArgumentBuilder<CommandSourceStack, ?> register() {
    return Commands.literal("items").requires(cs -> cs.hasPermission(2)).executes(command);
  }

  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    Set<ResourceLocation> itemsKeys = ForgeRegistries.ITEMS.getKeys();
    if (itemsKeys.isEmpty()) {
      sendFeedback(context, NO_ITEMS_TEXT);
      return 0;
    }
    sendFeedback(context, String.format("Items registry (%s types)\n===", itemsKeys.size()));
    log.info("Items registry: {}", itemsKeys);
    for (ResourceLocation entityKey : itemsKeys) {
      sendFeedback(context, String.format("\u25CB %s", entityKey));
    }
    return 0;
  }
}
