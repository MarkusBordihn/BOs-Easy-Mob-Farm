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
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
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
import net.minecraftforge.registries.ForgeRegistries;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.config.mobs.BeeAnimal;
import de.markusbordihn.easymobfarm.config.mobs.HostileNetherMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import de.markusbordihn.easymobfarm.item.CapturedMob;
import de.markusbordihn.easymobfarm.item.CapturedMobVirtual;

@EventBusSubscriber
public class LootManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);
  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  private static final Random random = new Random();

  // Configuration
  private static boolean beeDropHoneycomb = COMMON.beeDropHoneycomb.get();
  private static boolean blazeDropBlazeRod = COMMON.blazeDropBlazeRod.get();
  private static boolean cowDropRawBeef = COMMON.cowDropRawBeef.get();
  private static boolean chickenDropEggs = COMMON.chickenDropEggs.get();
  private static boolean chickenDropRawChicken = COMMON.chickenDropRawChicken.get();
  private static boolean sheepDropRawMutton = COMMON.sheepDropRawMutton.get();
  private static int lootPreviewRolls = COMMON.lootPreviewRolls.get();

  // Fake Player
  private static FakePlayer fakePlayer;
  private static final GameProfile GAME_PROFILE =
      new GameProfile(UUID.randomUUID(), "[BOs_Easy_Mob_Farm]");

  // Loot Table Cache
  private static Map<ResourceLocation, List<String>> lootTableDropListCache =
      new ConcurrentHashMap<>();

  // Mod Items
  private static final String PRODUCTIVE_BEES_CONFIGURABLE_HONEYCOMB =
      "productivebees:configurable_honeycomb";
  private static final String PRODUCTIVE_BEES_HONEYCOMB_GHOSTLY =
      "productivebees:honeycomb_ghostly";
  private static final String PRODUCTIVE_BEES_HONEYCOMB_MILKY = "productivebees:honeycomb_milky";
  private static final String PRODUCTIVE_BEES_HONEYCOMB_POWDERY =
      "productivebees:honeycomb_powdery";


  protected LootManager() {}

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    beeDropHoneycomb = COMMON.beeDropHoneycomb.get();
    blazeDropBlazeRod = COMMON.blazeDropBlazeRod.get();
    cowDropRawBeef = COMMON.cowDropRawBeef.get();
    chickenDropEggs = COMMON.chickenDropEggs.get();
    chickenDropRawChicken = COMMON.chickenDropRawChicken.get();
    sheepDropRawMutton = COMMON.sheepDropRawMutton.get();
    lootPreviewRolls = COMMON.lootPreviewRolls.get();
    lootTableDropListCache = new ConcurrentHashMap<>();
  }

  public static List<String> getRandomLootDropOverview(ResourceLocation lootTableLocation,
      Level level, String mobType, String mobSubType) {
    // Roll's the loot a specific time (default: 2) to get more accurate results.
    List<String> lootDropList = Lists.newArrayList();
    for (int i = 0; i < lootPreviewRolls; i++) {
      List<ItemStack> lootDrops =
          getFilteredRandomLootDrop(lootTableLocation, ItemStack.EMPTY, level, mobType, mobSubType);

      // Use a internal cache to improve loot prediction over time.
      lootDropList = cacheLootDrops(lootTableLocation, lootDrops);
    }
    log.debug(Constants.LOOT_MANAGER_PREFIX + "Loot for {} with {} roll {} result: {}", mobType,
        lootTableLocation, lootPreviewRolls, lootDropList);
    return lootDropList;
  }

  public static List<ItemStack> getFilteredRandomLootDrop(ItemStack capturedMob,
      ItemStack weaponItem, Level level) {
    // Load corresponding loot table for captured mob.
    ResourceLocation lootTable = getLootTable(capturedMob, level);
    if (lootTable == null) {
      return Lists.newArrayList();
    } else {
      String mobType = "";
      String mobSubType = "";
      if (capturedMob.getItem() instanceof CapturedMob) {
        mobType = CapturedMob.getCapturedMobType(capturedMob);
        mobSubType = CapturedMob.getCapturedMobSubType(capturedMob);
      } else if (CapturedMobVirtual.isSupported(capturedMob)) {
        mobType = CapturedMobVirtual.getCapturedMobType(capturedMob);
        mobSubType = CapturedMobVirtual.getCapturedMobSubType(capturedMob);
      }
      return getFilteredRandomLootDrop(lootTable, weaponItem, level, mobType, mobSubType);
    }
  }

  public static List<ItemStack> getFilteredRandomLootDrop(ResourceLocation lootTableLocation,
      ItemStack weaponItem, Level level, String mobType, String mobSubType) {

    List<ItemStack> lootDrops =
        getRandomLootDrops(lootTableLocation, weaponItem, level, mobType, mobSubType);
    List<ItemStack> filteredLootDrops = filterLootDrop(lootDrops, mobType);

    // Cache each loot drop for more accurate loot preview
    cacheLootDrops(lootTableLocation, filteredLootDrops);

    return filteredLootDrops;
  }

  public static List<ItemStack> getRandomLootDrops(ResourceLocation lootTableLocation,
      ItemStack weaponItem, Level level, String mobType, String mobSubType) {
    if (lootTableLocation == null || level == null || level.getServer() == null) {
      log.error(Constants.LOOT_MANAGER_PREFIX + "Unable to get loot drops for loot table {} in {}",
          lootTableLocation, level);
      return Lists.newArrayList();
    }
    MinecraftServer server = level.getServer();
    if (server != null) {
      ServerLevel serverLevel = (ServerLevel) level;
      FakePlayer player = getPlayer(serverLevel);

      // Handles if loot should be "killed_by_player" or just normal loot.
      LootContext.Builder lootBuilder = null;
      if (weaponItem != null && !weaponItem.isEmpty()) {
        // Kills with weapons has automatically a higher luck.
        lootBuilder = new LootContext.Builder(serverLevel).withLuck(0.6F)
            .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.playerAttack(player))
            .withParameter(LootContextParams.DIRECT_KILLER_ENTITY, player)
            .withParameter(LootContextParams.KILLER_ENTITY, player)
            .withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player)
            .withParameter(LootContextParams.ORIGIN, player.position())
            .withParameter(LootContextParams.THIS_ENTITY, player);
      } else {
        lootBuilder = new LootContext.Builder(serverLevel).withLuck(0.5F)
            .withParameter(LootContextParams.DAMAGE_SOURCE, DamageSource.playerAttack(player))
            .withParameter(LootContextParams.ORIGIN, player.position())
            .withParameter(LootContextParams.THIS_ENTITY, player);
      }
      LootTable lootTable = server.getLootTables().get(lootTableLocation);
      List<ItemStack> lootDrops =
          lootTable.getRandomItems(lootBuilder.create(LootContextParamSets.ENTITY));
      if (lootDrops.isEmpty() && !lootTableLocation.equals(new ResourceLocation("minecraft:empty"))
          && !lootTableLocation.equals(new ResourceLocation("minecraft:entities/bee"))
          && !lootTableLocation.equals(new ResourceLocation("minecraft:entities/blaze"))
          && !"productivebees".equals(lootTableLocation.getNamespace())
          && !"alexsmobs".equals(lootTableLocation.getNamespace())) {
        log.debug(Constants.LOOT_MANAGER_PREFIX + "Loot drop for {} with loot table {} was empty!",
            player, lootTableLocation);
      }

      // Productive Bees support (1 out of 3)
      if (lootDrops.isEmpty() && Constants.PRODUCTIVE_BEES_LOADED
          && "productivebees:entities/configurable_bee".equals(lootTableLocation.toString())
          && random.nextInt(3) == 0) {

        // Getting typical honeycomb from the registry.
        Item honeyCombGhostly =
            ForgeRegistries.ITEMS.getValue(new ResourceLocation(PRODUCTIVE_BEES_HONEYCOMB_GHOSTLY));
        Item honeyCombMilky =
            ForgeRegistries.ITEMS.getValue(new ResourceLocation(PRODUCTIVE_BEES_HONEYCOMB_MILKY));
        Item honeyCombPowdery =
            ForgeRegistries.ITEMS.getValue(new ResourceLocation(PRODUCTIVE_BEES_HONEYCOMB_POWDERY));
        Item configurableHoneycomb = ForgeRegistries.ITEMS
            .getValue(new ResourceLocation(PRODUCTIVE_BEES_CONFIGURABLE_HONEYCOMB));

        // Productive bees honeycomb support.
        if (mobType.equals(BeeAnimal.GHOSTLY_BEE) && honeyCombGhostly != null) {
          lootDrops.add(new ItemStack(honeyCombGhostly));
        } else if (mobType.equals(BeeAnimal.RANCHER_BEE) && honeyCombMilky != null) {
          lootDrops.add(new ItemStack(honeyCombMilky));
        } else if (mobType.equals(BeeAnimal.CREEPER_BEE) && honeyCombPowdery != null) {
          lootDrops.add(new ItemStack(honeyCombPowdery));
        } else if (mobSubType != null && !mobSubType.isEmpty() && configurableHoneycomb != null) {
          ItemStack honeyComb = new ItemStack(configurableHoneycomb);
          CompoundTag compoundTag = honeyComb.getOrCreateTagElement("EntityTag");
          compoundTag.putString("type", mobSubType);
          lootDrops.add(honeyComb);
        }
      }

      return lootDrops;
    }
    return Lists.newArrayList();
  }

  public static List<ItemStack> filterLootDrop(List<ItemStack> lootDrops, String mobType) {
    List<ItemStack> filteredLootDrops = Lists.newArrayList();

    // Blaze rod drop support.
    if (blazeDropBlazeRod && mobType.equals(HostileNetherMonster.BLAZE)) {
      lootDrops.add(new ItemStack(Items.BLAZE_ROD));
    }

    // Chicken egg drop support.
    if (chickenDropEggs && mobType.equals(PassiveAnimal.CHICKEN)) {
      lootDrops.add(new ItemStack(Items.EGG));
    }

    // Bee and Productive Bees drop support.
    if (beeDropHoneycomb
        && (mobType.equals(BeeAnimal.BEE)
            || (Constants.PRODUCTIVE_BEES_LOADED && BeeAnimal.ProductiveBees.contains(mobType)))
        && random.nextInt(3) == 0) {
      lootDrops.add(new ItemStack(Items.HONEYCOMB));
    }

    // Check each single loot drop.
    for (ItemStack lootDrop : lootDrops) {
      // Ignore empty stacks and filter loot drop, if specific drop is disabled.
      if (lootDrop.isEmpty()
          || filter(chickenDropRawChicken, PassiveAnimal.CHICKEN, Items.CHICKEN, mobType, lootDrop)
          || filter(cowDropRawBeef, PassiveAnimal.COW, Items.BEEF, mobType, lootDrop)
          || filter(sheepDropRawMutton, PassiveAnimal.SHEEP, Items.MUTTON, mobType, lootDrop)) {
        continue;
      }
      filteredLootDrops.add(lootDrop);
    }

    return filteredLootDrops;
  }

  private static FakePlayer getPlayer(ServerLevel level) {
    if (fakePlayer == null) {
      fakePlayer = new FakePlayer(level, GAME_PROFILE);
    }
    return fakePlayer;
  }

  private static ResourceLocation getLootTable(ItemStack itemStack, Level level) {
    // Ignore empty items and request is not from server.
    if (itemStack.isEmpty() || !(level instanceof ServerLevel)) {
      return null;
    }

    // Get loot table based on captured mob item.
    if (itemStack.getItem() instanceof CapturedMob) {
      String lootTableLocation = CapturedMob.getLootTable(itemStack);
      if (!lootTableLocation.isEmpty()) {
        return new ResourceLocation(lootTableLocation);
      }
    } else if (CapturedMobVirtual.isSupported(itemStack)) {
      String lootTableLocation = CapturedMobVirtual.getLootTable(itemStack);
      if (!lootTableLocation.isEmpty()) {
        return new ResourceLocation(lootTableLocation);
      }
    } else {
      log.error(Constants.LOOT_MANAGER_PREFIX + "Unable to get loot table for {} in {}", itemStack,
          level);
    }
    return null;
  }

  private static boolean filter(boolean status, String blockedMobType, Item blockedLootDrop,
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
    if (lootTableLocation == null) {
      return lootDropList;
    }
    List<String> lootDropListCache = lootTableDropListCache.getOrDefault(lootTableLocation, null);
    if (lootDropListCache != null) {
      lootDropList.addAll(lootDropListCache);
    }
    for (ItemStack lootDrop : lootDrops) {
      String itemDescriptionId = lootDrop.getItem().getDescriptionId();
      if (itemDescriptionId == null || itemDescriptionId.isEmpty()) {
        continue;
      }

      // Productive Bees comb support.
      if ("item.productivebees.configurable_honeycomb".equals(itemDescriptionId)) {
        CompoundTag compoundTag = lootDrop.getOrCreateTagElement("EntityTag");
        String combType = compoundTag.getString("type");
        if (combType != null && !combType.isEmpty() && combType.contains(":")) {
          itemDescriptionId = "item.productivebees.honeycomb_" + combType.split(":")[1];
        }
      }
      lootDropList.add(itemDescriptionId);
    }

    // Sort and remove duplicates from loot drop list.
    List<String> sortedLootDropList = lootDropList.stream().distinct().sorted().toList();
    lootTableDropListCache.put(lootTableLocation, sortedLootDropList);
    return sortedLootDropList;
  }

}
