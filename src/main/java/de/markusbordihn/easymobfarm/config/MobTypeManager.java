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

package de.markusbordihn.easymobfarm.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.copper.CopperSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.gold.GoldSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.iron.IronSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteAnimalPlainsFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteBeeHiveFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteDesertFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteJungleFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteMonsterPlainsCaveFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteNetherFortressFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteOceanFarm;
import de.markusbordihn.easymobfarm.block.farm.netherite.NetheriteSwampFarm;
import de.markusbordihn.easymobfarm.block.farm.special.IronGolemFarm;
import de.markusbordihn.easymobfarm.item.mobcatcher.CatchCage;
import de.markusbordihn.easymobfarm.item.mobcatcher.CatchCageSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.CollarSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.EnderLasso;
import de.markusbordihn.easymobfarm.item.mobcatcher.FishingBowl;
import de.markusbordihn.easymobfarm.item.mobcatcher.FishingNetSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.GoldenLasso;
import de.markusbordihn.easymobfarm.item.mobcatcher.InsectNet;
import de.markusbordihn.easymobfarm.item.mobcatcher.NetheriteLasso;
import de.markusbordihn.easymobfarm.item.mobcatcher.PoppyBouquet;
import de.markusbordihn.easymobfarm.item.mobcatcher.UrnSmall;
import de.markusbordihn.easymobfarm.item.mobcatcher.WitchBottle;

import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.event.world.WorldEvent;

@EventBusSubscriber
public class MobTypeManager {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  protected static final CommonConfig.Config COMMON = CommonConfig.COMMON;

  protected static final Set<String> EMPTY_LIST = Collections.emptySet();

  private static Map<String, Set<String>> acceptedMobTypesMap = new HashMap<>();
  private static Map<String, Set<String>> deniedMobTypesMap = new HashMap<>();

  private static Set<String> generalAllowedMobTypesSet = Collections.emptySet();
  private static Set<String> generalDeniedMobTypesSet = Collections.emptySet();

  protected MobTypeManager() {}

  @SubscribeEvent
  public static void handleServerAboutToStartEvent(ServerAboutToStartEvent event) {
    handleMobTypeMapping();
  }

  @SubscribeEvent
  public static void handleWorldEventLoad(WorldEvent.Load event) {
    if (event.getWorld().isClientSide()) {
      handleMobTypeMapping();
    }
  }

  public static void handleMobTypeMapping() {
    // General allowed mobs
    if (generalAllowedMobTypesSet.isEmpty()) {
      generalAllowedMobTypesSet = new HashSet<>(COMMON.generalAllowedMobs.get());
    }
    if (generalDeniedMobTypesSet.isEmpty()) {
      generalDeniedMobTypesSet = new HashSet<>(COMMON.generalDeniedMobs.get());
    }

    // Mob Catcher Types
    updateMobTypesForKey(CatchCage.NAME, COMMON.catchCageAllowedMobs.get(),
        COMMON.catchCageDeniedMobs.get());
    updateMobTypesForKey(CatchCageSmall.NAME, COMMON.catchCageSmallAllowedMobs.get(),
        COMMON.catchCageSmallDeniedMobs.get());
    updateMobTypesForKey(CollarSmall.NAME, COMMON.collarSmallAllowedMobs.get(),
        COMMON.collarSmallDeniedMobs.get());
    updateMobTypesForKey(EnderLasso.NAME, COMMON.enderLassoAllowedMobs.get(),
        COMMON.enderLassoDeniedMobs.get());
    updateMobTypesForKey(FishingBowl.NAME, COMMON.fishingBowlAllowedMobs.get(),
        COMMON.fishingBowlDeniedMobs.get());
    updateMobTypesForKey(FishingNetSmall.NAME, COMMON.fishingNetSmallAllowedMobs.get(),
        COMMON.fishingNetSmallDeniedMobs.get());
    updateMobTypesForKey(GoldenLasso.NAME, COMMON.goldenLassoAllowedMobs.get(),
        COMMON.goldenLassoDeniedMobs.get());
    updateMobTypesForKey(InsectNet.NAME, COMMON.insectNetAllowedMobs.get(),
        COMMON.insectNetDeniedMobs.get());
    updateMobTypesForKey(NetheriteLasso.NAME, COMMON.netheriteLassoAllowedMobs.get(),
        COMMON.netheriteLassoDeniedMobs.get());
    updateMobTypesForKey(PoppyBouquet.NAME, COMMON.poppyBouquetAllowedMobs.get(),
        COMMON.poppyBouquetDeniedMobs.get());
    updateMobTypesForKey(UrnSmall.NAME, COMMON.urnSmallAllowedMobs.get(),
        COMMON.urnSmallDeniedMobs.get());
    updateMobTypesForKey(WitchBottle.NAME, COMMON.witchBottleAllowedMobs.get(),
        COMMON.witchBottleDeniedMobs.get());

    // Copper Mob Farm Types
    updateMobTypesForKey(CopperAnimalPlainsFarm.NAME,
        COMMON.copperAnimalPlainsFarmAllowedMobs.get(),
        COMMON.copperAnimalPlainsFarmDeniedMobs.get());
    updateMobTypesForKey(CopperBeeHiveFarm.NAME, COMMON.copperBeeHiveFarmAllowedMobs.get(),
        COMMON.copperBeeHiveFarmDeniedMobs.get());
    updateMobTypesForKey(CopperDesertFarm.NAME, COMMON.copperDesertFarmAllowedMobs.get(),
        COMMON.copperDesertFarmDeniedMobs.get());
    updateMobTypesForKey(CopperJungleFarm.NAME, COMMON.copperJungleFarmAllowedMobs.get(),
        COMMON.copperJungleFarmDeniedMobs.get());
    updateMobTypesForKey(CopperMonsterPlainsCaveFarm.NAME,
        COMMON.copperMonsterPlainsCaveFarmAllowedMobs.get(),
        COMMON.copperMonsterPlainsCaveFarmDeniedMobs.get());
    updateMobTypesForKey(CopperNetherFortressFarm.NAME,
        COMMON.copperNetherFortressFarmAllowedMobs.get(),
        COMMON.copperNetherFortressFarmDeniedMobs.get());
    updateMobTypesForKey(CopperOceanFarm.NAME, COMMON.copperOceanFarmAllowedMobs.get(),
        COMMON.copperOceanFarmDeniedMobs.get());
    updateMobTypesForKey(CopperSwampFarm.NAME, COMMON.copperSwampFarmAllowedMobs.get(),
        COMMON.copperSwampFarmDeniedMobs.get());

    // Iron Mob Farm Types
    updateMobTypesForKey(IronAnimalPlainsFarm.NAME, COMMON.ironAnimalPlainsFarmAllowedMobs.get(),
        COMMON.ironAnimalPlainsFarmDeniedMobs.get());
    updateMobTypesForKey(IronBeeHiveFarm.NAME, COMMON.ironBeeHiveFarmAllowedMobs.get(),
        COMMON.ironBeeHiveFarmDeniedMobs.get());
    updateMobTypesForKey(IronDesertFarm.NAME, COMMON.ironDesertFarmAllowedMobs.get(),
        COMMON.ironDesertFarmDeniedMobs.get());
    updateMobTypesForKey(IronJungleFarm.NAME, COMMON.ironJungleFarmAllowedMobs.get(),
        COMMON.ironJungleFarmDeniedMobs.get());
    updateMobTypesForKey(IronMonsterPlainsCaveFarm.NAME,
        COMMON.ironMonsterPlainsCaveFarmAllowedMobs.get(),
        COMMON.ironMonsterPlainsCaveFarmDeniedMobs.get());
    updateMobTypesForKey(IronNetherFortressFarm.NAME,
        COMMON.ironNetherFortressFarmAllowedMobs.get(),
        COMMON.ironNetherFortressFarmDeniedMobs.get());
    updateMobTypesForKey(IronOceanFarm.NAME, COMMON.ironOceanFarmAllowedMobs.get(),
        COMMON.ironOceanFarmDeniedMobs.get());
    updateMobTypesForKey(IronSwampFarm.NAME, COMMON.ironSwampFarmAllowedMobs.get(),
        COMMON.ironSwampFarmDeniedMobs.get());

    // Gold Mob Farm Types
    updateMobTypesForKey(GoldAnimalPlainsFarm.NAME, COMMON.goldAnimalPlainsFarmAllowedMobs.get(),
        COMMON.goldAnimalPlainsFarmDeniedMobs.get());
    updateMobTypesForKey(GoldBeeHiveFarm.NAME, COMMON.goldBeeHiveFarmAllowedMobs.get(),
        COMMON.goldBeeHiveFarmDeniedMobs.get());
    updateMobTypesForKey(GoldDesertFarm.NAME, COMMON.goldDesertFarmAllowedMobs.get(),
        COMMON.goldDesertFarmDeniedMobs.get());
    updateMobTypesForKey(GoldJungleFarm.NAME, COMMON.goldJungleFarmAllowedMobs.get(),
        COMMON.goldJungleFarmDeniedMobs.get());
    updateMobTypesForKey(GoldMonsterPlainsCaveFarm.NAME,
        COMMON.goldMonsterPlainsCaveFarmAllowedMobs.get(),
        COMMON.goldMonsterPlainsCaveFarmDeniedMobs.get());
    updateMobTypesForKey(GoldNetherFortressFarm.NAME,
        COMMON.goldNetherFortressFarmAllowedMobs.get(),
        COMMON.goldNetherFortressFarmDeniedMobs.get());
    updateMobTypesForKey(GoldOceanFarm.NAME, COMMON.goldOceanFarmAllowedMobs.get(),
        COMMON.goldOceanFarmDeniedMobs.get());
    updateMobTypesForKey(GoldSwampFarm.NAME, COMMON.goldSwampFarmAllowedMobs.get(),
        COMMON.goldSwampFarmDeniedMobs.get());

    // Netherite Mob Farm Types
    updateMobTypesForKey(NetheriteAnimalPlainsFarm.NAME,
        COMMON.netheriteAnimalPlainsFarmAllowedMobs.get(),
        COMMON.netheriteAnimalPlainsFarmDeniedMobs.get());
    updateMobTypesForKey(NetheriteBeeHiveFarm.NAME, COMMON.netheriteBeeHiveFarmAllowedMobs.get(),
        COMMON.netheriteBeeHiveFarmDeniedMobs.get());
    updateMobTypesForKey(NetheriteDesertFarm.NAME, COMMON.netheriteDesertFarmAllowedMobs.get(),
        COMMON.netheriteDesertFarmDeniedMobs.get());
    updateMobTypesForKey(NetheriteJungleFarm.NAME, COMMON.netheriteJungleFarmAllowedMobs.get(),
        COMMON.netheriteJungleFarmDeniedMobs.get());
    updateMobTypesForKey(NetheriteMonsterPlainsCaveFarm.NAME,
        COMMON.netheriteMonsterPlainsCaveFarmAllowedMobs.get(),
        COMMON.netheriteMonsterPlainsCaveFarmDeniedMobs.get());
    updateMobTypesForKey(NetheriteNetherFortressFarm.NAME,
        COMMON.netheriteNetherFortressFarmAllowedMobs.get(),
        COMMON.netheriteNetherFortressFarmDeniedMobs.get());
    updateMobTypesForKey(NetheriteOceanFarm.NAME, COMMON.netheriteOceanFarmAllowedMobs.get(),
        COMMON.netheriteOceanFarmDeniedMobs.get());
    updateMobTypesForKey(NetheriteSwampFarm.NAME, COMMON.netheriteSwampFarmAllowedMobs.get(),
        COMMON.netheriteSwampFarmDeniedMobs.get());

    // Special Mob Farm Types
    updateMobTypesForKey(IronGolemFarm.NAME, COMMON.ironGolemFarmAllowedMobs.get(),
        COMMON.ironGolemFarmDeniedMobs.get());
  }

  public static Set<String> getGeneralAllowedMobTypes() {
    return generalAllowedMobTypesSet;
  }

  public static Set<String> getGeneralDeniedMobTypes() {
    return generalDeniedMobTypesSet;
  }

  public static Set<String> getAcceptedMobTypes(String key) {
    return acceptedMobTypesMap.getOrDefault(key, EMPTY_LIST);
  }

  public static Set<String> getDeniedMobTypes(String key) {
    return deniedMobTypesMap.getOrDefault(key, EMPTY_LIST);
  }

  public static boolean isAcceptedMobType(String key, String mobType) {
    if (mobType == null || mobType.isEmpty()) {
      return false;
    }
    if (getGeneralDeniedMobTypes().contains(mobType)) {
      return false;
    }
    if (getGeneralAllowedMobTypes().contains(mobType)) {
      return true;
    }
    Set<String> deniedMobTypes = getDeniedMobTypes(key);
    if (deniedMobTypes.contains(mobType)) {
      return false;
    }
    Set<String> acceptedMobTypes = getAcceptedMobTypes(key);
    return acceptedMobTypes.isEmpty() || acceptedMobTypes.contains(mobType);
  }

  public static void updateMobTypesForKey(String key, List<String> acceptedMobs,
      List<String> deniedMobs) {
    if (key == null) {
      return;
    }
    boolean hasUpdate = false;
    if (acceptedMobs != null && !acceptedMobs.isEmpty()) {
      acceptedMobTypesMap.computeIfAbsent(key, k -> new HashSet<>(acceptedMobs));
      hasUpdate = true;
    }
    if (deniedMobs != null && !deniedMobs.isEmpty()) {
      deniedMobTypesMap.computeIfAbsent(key, k -> new HashSet<>(deniedMobs));
      hasUpdate = true;
    }
    if (hasUpdate) {
      logSupportedMobs(key, getAcceptedMobTypes(key), getDeniedMobTypes(key));
    }
  }


  public static void logSupportedMobs(String key, Set<String> acceptedMobTypes,
      Set<String> deniedMobTypes) {
    if (!acceptedMobTypes.isEmpty() && !deniedMobTypes.isEmpty()) {
      log.info("The {} accepts the following mobs: {}, excluding the following mobs: {}", key,
          deniedMobTypes, deniedMobTypes);
    } else if (!acceptedMobTypes.isEmpty()) {
      log.info("The {} accepts the following mobs: {}", key, acceptedMobTypes);
    } else if (!deniedMobTypes.isEmpty()) {
      log.info("The {} accepts all mobs, excluding the following mobs: {}", key, deniedMobTypes);
    } else {
      log.info("The {} accepts all mobs.", key);
    }
  }
}
