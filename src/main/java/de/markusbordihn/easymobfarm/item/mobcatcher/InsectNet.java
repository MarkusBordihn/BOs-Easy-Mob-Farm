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

package de.markusbordihn.easymobfarm.item.mobcatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.item.MobCatcherItem;
import de.markusbordihn.easymobfarm.text.TranslatableText;

@EventBusSubscriber
public class InsectNet extends MobCatcherItem {

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;
  private static int mobCatchingLuck = COMMON.insectNetMobCatchingLuck.get();
  private static Set<String> acceptedMobTypes = new HashSet<>(COMMON.insectNetMobs.get());

  public InsectNet(Item.Properties properties) {
    super(properties);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    mobCatchingLuck = COMMON.insectNetMobCatchingLuck.get();
    acceptedMobTypes = new HashSet<>(COMMON.insectNetMobs.get());
    log.info("The insect net require {} luck and is able to catch the following mobs: {}",
        mobCatchingLuck, acceptedMobTypes);
  }

  @Override
  public Set<String> getAcceptedMobTypes() {
    return acceptedMobTypes;
  }

  @Override
  public boolean canCatchMobType(String mobType) {
    return acceptedMobTypes.contains(mobType);
  }

  @Override
  public int getMobCatchingLuck() {
    return mobCatchingLuck > 0 ? this.random.nextInt(mobCatchingLuck) : 0;
  }

  @Override
  public void appendHoverTextCatchableMobs(List<Component> tooltipList) {
    if (!acceptedMobTypes.isEmpty()) {
      // List each single possible mob types (incl. modded mobs types).
      TranslatableComponent mobTypeOverview = (TranslatableComponent) new TranslatableComponent("")
          .withStyle(ChatFormatting.DARK_GREEN);
      for (String acceptedMob : acceptedMobTypes) {
        // We will skip he entries in the following cases:
        // 1.) Productive Bees is not loaded but entry starts with productive bees
        // 2.) Accepted Mob Types Size > 16 and Productive Bees is loaded and entry starts with
        // productive bees
        if (!((!Constants.PRODUCTIVE_BEES_LOADED && acceptedMob.startsWith("productivebees:"))
            || (acceptedMobTypes.size() >= 16 && Constants.PRODUCTIVE_BEES_LOADED
                && acceptedMob.startsWith("productivebees:")))) {
          TranslatableComponent acceptedMobName = TranslatableText.getEntityName(acceptedMob);
          if (!acceptedMobName.getString().isBlank()) {
            mobTypeOverview.append(acceptedMobName).append(", ")
                .withStyle(ChatFormatting.DARK_GREEN);
          }
        }
      }
      // If productive bees is loaded and list is larger then 16 add a place holder instead.
      if (Constants.PRODUCTIVE_BEES_LOADED && acceptedMobTypes.size() >= 16) {
        mobTypeOverview.append("Productive Bees").append(", ").withStyle(ChatFormatting.DARK_GREEN);
      }
      if (!mobTypeOverview.getString().isBlank()) {
        TranslatableComponent acceptedMobsOverview =
            (TranslatableComponent) new TranslatableComponent(
                Constants.TEXT_PREFIX + "catchable_mobs").append(" ")
                    .withStyle(ChatFormatting.GREEN);
        acceptedMobsOverview.append(mobTypeOverview).append("...");
        tooltipList.add(acceptedMobsOverview);
      }
    }
  }

}
