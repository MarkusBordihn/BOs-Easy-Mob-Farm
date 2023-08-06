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
import java.util.Set;

import net.minecraft.world.item.Item;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.item.MobCatcherItem;

@EventBusSubscriber
public class UrnSmall extends MobCatcherItem {

  private static Set<String> acceptedMobTypes = new HashSet<>();

  public UrnSmall(Item.Properties properties) {
    super(properties);
  }

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    acceptedMobTypes = new HashSet<>(COMMON.urnSmallMobs.get());
    log.info("The urn small requires {} luck and is able to catch the following mobs: {}",
        COMMON.urnSmallMobCatchingLuck.get(), acceptedMobTypes);
  }

  @SubscribeEvent
  public static void handleWorldEventLoad(WorldEvent.Load event) {
    if (event.getWorld().isClientSide() && acceptedMobTypes.isEmpty()) {
      acceptedMobTypes = new HashSet<>(COMMON.urnSmallMobs.get());
    }
  }

  @Override
  public Set<String> getAcceptedMobTypes() {
    return acceptedMobTypes;
  }

  @Override
  public boolean canCatchMobType(String mobType) {
    return acceptedMobTypes == null || acceptedMobTypes.isEmpty()
        || acceptedMobTypes.contains(mobType);
  }

  @Override
  public int getMobCatchingLuckConfig() {
    return COMMON.urnSmallMobCatchingLuck.get();
  }

}
