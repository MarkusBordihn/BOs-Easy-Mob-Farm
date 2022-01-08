/**
 * Copyright 2021 Markus Bordihn
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

package de.markusbordihn.easymobfarm.loot;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import de.markusbordihn.easymobfarm.item.CapturedMobItem;

@EventBusSubscriber
public class LootManager {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static boolean blazeDropBlazeRod = COMMON.blazeDropBlazeRod.get();
  private static boolean chickenDropEggs = COMMON.chickenDropEggs.get();
  private static boolean chickenDropRawChicken = COMMON.chickenDropRawChicken.get();

  private static FakePlayer fakePlayer;
  private static final GameProfile GAME_PROFILE =
      new GameProfile(UUID.randomUUID(), "[BOs_Easy_Mob_Farm]");

  protected LootManager() {}

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    blazeDropBlazeRod = COMMON.blazeDropBlazeRod.get();
    chickenDropEggs = COMMON.chickenDropEggs.get();
    chickenDropRawChicken = COMMON.chickenDropRawChicken.get();
  }

  public static FakePlayer getPlayer(ServerLevel level) {
    if (fakePlayer == null) {
      fakePlayer = new FakePlayer(level, GAME_PROFILE);
    }
    return fakePlayer;
  }

  public static List<String> getRandomLootDropOverview(ResourceLocation lootTableLocation,
      Level level, String mobType) {
    List<String> lootDropList = Lists.newArrayList();
    List<ItemStack> lootDrops = getFilteredRandomLootDrop(lootTableLocation, level, mobType);
    // Try a second time to get the loot drop overview.
    if (lootDrops.isEmpty()) {
      lootDrops = getFilteredRandomLootDrop(lootTableLocation, level, mobType);
    }
    for (ItemStack lootDrop : lootDrops) {
      lootDropList.add(lootDrop.getItem().getDescriptionId());
    }
    return lootDropList;
  }

  public static List<ItemStack> getRandomLootDrops(ResourceLocation lootTableLocation,
      Level level) {
    if (lootTableLocation == null || level == null || level.getServer() == null) {
      log.error("Unable to get loot drops for {} and {}", lootTableLocation, level);
      return Lists.newArrayList();
    }
    ServerLevel serverLevel = (ServerLevel) level;
    FakePlayer player = getPlayer(serverLevel);
    LootContext.Builder builder = new LootContext.Builder(serverLevel)
        .withParameter(LootContextParams.ORIGIN, player.position());
    builder.withLuck(0.5F).withParameter(LootContextParams.THIS_ENTITY, player)
        .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.playerAttack(player));
    LootTable lootTable = level.getServer().getLootTables().get(lootTableLocation);
    List<ItemStack> lootDrops =
        lootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY));
    if (lootDrops.isEmpty()) {
      log.warn("Loot drop for {} with loot table {} was empty!", player, lootTableLocation);
    }
    return lootDrops;
  }

  public static List<ItemStack> getFilteredRandomLootDrop(ItemStack itemStack, Level level) {
    ResourceLocation lootTable = null;
    String mobType = "";

    // Ignore empty items and request is not from server.
    if (itemStack.isEmpty() || !(level instanceof ServerLevel)) {
      return Lists.newArrayList();
    }

    // Process captured mob item.
    if (itemStack.getItem() instanceof CapturedMobItem capturedMobItem) {
      String lootTableLocation = capturedMobItem.getLootTable(itemStack);
      if (!lootTableLocation.isEmpty()) {
        lootTable = new ResourceLocation(lootTableLocation);
      }
      mobType = capturedMobItem.getCapturedMobType(itemStack);
    } else {
      log.error("Unable to process loot drop for {} in {}", itemStack, level);
      return Lists.newArrayList();
    }
    return getFilteredRandomLootDrop(lootTable, level, mobType);
  }

  public static List<ItemStack> getFilteredRandomLootDrop(ResourceLocation lootTableLocation,
      Level level, String mobType) {
    List<ItemStack> lootDrops = getRandomLootDrops(lootTableLocation, level);
    return getFilteredLootDrop(lootDrops, mobType);
  }

  public static List<ItemStack> getFilteredLootDrop(List<ItemStack> lootDrops, String mobType) {
    List<ItemStack> filteredLootDrops = Lists.newArrayList();

    // Adding additional drops for specific cases.
    if (blazeDropBlazeRod && mobType.equals(HostileMonster.BLAZE)) {
      lootDrops.add(new ItemStack(Items.BLAZE_ROD));
    }
    if (chickenDropEggs && mobType.equals(PassiveAnimal.CHICKEN)) {
      lootDrops.add(new ItemStack(Items.EGG));
    }

    // Check each single loot drop.
    for (ItemStack lootDrop : lootDrops) {
      if (lootDrop.isEmpty()) {
        continue;
      }

      // Filter Loot Drop, if drop is disabled.
      if (filterLootDrop(chickenDropRawChicken, PassiveAnimal.CHICKEN, Items.CHICKEN, mobType,
          lootDrop)) {
        continue;
      }

      filteredLootDrops.add(lootDrop);
    }

    return filteredLootDrops;
  }

  public static boolean filterLootDrop(boolean status, String blockedMobType, Item blockedLootDrop,
      String mobType, ItemStack lootDrop) {
    if (!status) {
      return false;
    }
    return mobType.equals(blockedMobType) && lootDrop.is(blockedLootDrop);
  }

}
