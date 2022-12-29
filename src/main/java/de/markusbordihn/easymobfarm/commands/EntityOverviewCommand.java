/**
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

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.easymobfarm.Constants;

public class EntityOverviewCommand extends CustomCommand {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final EntityOverviewCommand command = new EntityOverviewCommand();
  private static final String NO_ENTITIES_TEXT =
      "Unable to find any entities. Server / World is not loaded?";

  public static ArgumentBuilder<CommandSourceStack, ?> register() {
    return Commands.literal("entities").requires(cs -> cs.hasPermission(2)).executes(command);
  }

  @Override
  public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
    Set<ResourceLocation> entitiesKeys = ForgeRegistries.ENTITY_TYPES.getKeys();
    if (entitiesKeys.isEmpty()) {
      sendFeedback(context, NO_ENTITIES_TEXT);
      return 0;
    }
    sendFeedback(context, String.format("Entity registry (%s types)\n===", entitiesKeys.size()));
    log.info("Entity registry: {}", entitiesKeys);
    for (ResourceLocation entityKey : entitiesKeys) {
      sendFeedback(context, String.format("\u25CB %s", entityKey));
    }
    return 0;
  }
}