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

package de.markusbordihn.easymobfarm.loot;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
import de.markusbordihn.easymobfarm.item.CapturedMob;

@EventBusSubscriber
public class LootManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static FakePlayer fakePlayer;
  private static final GameProfile GAME_PROFILE =
      new GameProfile(UUID.randomUUID(), "[BOs_Easy_Mob_Farm]");

  private static Map<ResourceLocation, List<String>> lootTableDropListCache =
      new ConcurrentHashMap<>();

  protected LootManager() {}

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    lootTableDropListCache = new ConcurrentHashMap<>();
  }

  public static FakePlayer getPlayer(ServerLevel level) {
    if (fakePlayer == null) {
      fakePlayer = new FakePlayer(level, GAME_PROFILE);
    }
    return fakePlayer;
  }

  public static List<String> getRandomLootDropOverview(ResourceLocation lootTableLocation,
      Level level, String mobType) {
    // Roll's the loot a specific time (default: 2) to get more accurate results.
    List<String> lootDropList = Lists.newArrayList();
    for (int i = 0; i < COMMON.lootPreviewRolls.get(); i++) {
      List<ItemStack> lootDrops = getFilteredRandomLootDrop(lootTableLocation, level, mobType);

      // Use a internal cache to improve loot prediction over time.
      lootDropList = cacheLootDrops(lootTableLocation, lootDrops);
    }
    log.info("Loot for {} with {} roll {} result: {}", mobType, lootTableLocation,
        COMMON.lootPreviewRolls.get(), lootDropList);
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
    if (itemStack.getItem() instanceof CapturedMob capturedMobItem) {
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
    List<ItemStack> filteredLootDrops = getFilteredLootDrop(lootDrops, mobType);

    // Cache each loot drop for more accurate loot preview
    cacheLootDrops(lootTableLocation, filteredLootDrops);

    return filteredLootDrops;
  }

  public static List<ItemStack> getFilteredLootDrop(List<ItemStack> lootDrops, String mobType) {
    List<ItemStack> filteredLootDrops = Lists.newArrayList();

    // Adding additional drops for specific cases.
    if (Boolean.TRUE.equals(COMMON.blazeDropBlazeRod.get()) && mobType.equals(HostileMonster.BLAZE)) {
      lootDrops.add(new ItemStack(Items.BLAZE_ROD));
    }
    if (Boolean.TRUE.equals(COMMON.chickenDropEggs.get()) && mobType.equals(PassiveAnimal.CHICKEN)) {
      lootDrops.add(new ItemStack(Items.EGG));
    }

    // Check each single loot drop.
    for (ItemStack lootDrop : lootDrops) {
      // Ignore empty stacks and filter loot drop, if specific drop is disabled.
      if (lootDrop.isEmpty()
          || filter(COMMON.chickenDropRawChicken.get(), PassiveAnimal.CHICKEN, Items.CHICKEN,
              mobType, lootDrop)
          || filter(COMMON.cowDropRawBeef.get(), PassiveAnimal.COW, Items.BEEF, mobType, lootDrop)
          || filter(COMMON.sheepDropRawMutton.get(), PassiveAnimal.SHEEP, Items.MUTTON, mobType,
              lootDrop)) {
        continue;
      }
      filteredLootDrops.add(lootDrop);
    }

    // Add additional loot drops, if loot drops are empty.
    if (Boolean.TRUE.equals(filteredLootDrops.isEmpty() && COMMON.slimeDropSlime.get())
        && mobType.equals(HostileMonster.SLIME)) {
      filteredLootDrops.add(new ItemStack(Items.SLIME_BALL));
    }

    return filteredLootDrops;
  }

  public static boolean filter(boolean status, String blockedMobType, Item blockedLootDrop,
      String mobType, ItemStack lootDrop) {
    // Filter only if loot drop is disabled = false.
    if (status) {
      return false;
    }
    return mobType.equals(blockedMobType) && lootDrop.is(blockedLootDrop);
  }

  private static List<String> cacheLootDrops(ResourceLocation lootTableLocation,
      List<ItemStack> lootDrops) {
    List<String> lootDropList = Lists.newArrayList();
    List<String> lootDropListCache = lootTableDropListCache.getOrDefault(lootTableLocation, null);
    if (lootDropListCache != null) {
      lootDropList.addAll(lootDropListCache);
    }
    for (ItemStack lootDrop : lootDrops) {
      lootDropList.add(lootDrop.getItem().getDescriptionId());
    }
    return lootTableDropListCache.put(lootTableLocation, lootDropList.stream().distinct().toList());
  }

}
