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

package de.markusbordihn.easymobfarm.item.mobcatcher;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.item.MobCatcherItem;
import de.markusbordihn.easymobfarm.text.TranslatableText;
import java.util.List;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;

public class InsectNet extends MobCatcherItem {

  public static final String NAME = "Insect Net";

  public InsectNet(Item.Properties properties) {
    super(properties);
  }

  @Override
  public int getMobCatchingLuckConfig() {
    return COMMON.insectNetMobCatchingLuck.get();
  }

  @Override
  public String getMobCatcherItemName() {
    return NAME;
  }

  @Override
  public void appendHoverTextCatchableMobs(List<Component> tooltipList) {
    Set<String> acceptedMobTypes = getAcceptedMobTypes();
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

    // Display the catching luck.
    if (getMobCatchingLuckConfig() > 0) {
      TranslatableComponent catchingLuck = new TranslatableComponent(
          Constants.TEXT_PREFIX + "mob_catching_luck", getMobCatchingLuckConfig());
      tooltipList.add(catchingLuck);
    }
  }

}
