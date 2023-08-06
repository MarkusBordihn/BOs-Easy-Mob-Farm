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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.biome.Desert;
import de.markusbordihn.easymobfarm.config.biome.Jungle;
import de.markusbordihn.easymobfarm.config.biome.Ocean;
import de.markusbordihn.easymobfarm.config.biome.Plains;
import de.markusbordihn.easymobfarm.config.biome.PlainsCave;
import de.markusbordihn.easymobfarm.config.biome.Swamp;
import de.markusbordihn.easymobfarm.config.mobs.AmbientWaterAnimal;
import de.markusbordihn.easymobfarm.config.mobs.BeeAnimal;
import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileWaterMonster;
import de.markusbordihn.easymobfarm.config.mobs.NeutralAnimal;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import de.markusbordihn.easymobfarm.config.mobs.PassiveWaterAnimal;
import de.markusbordihn.easymobfarm.config.structure.NetherFortress;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CommonConfig {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public static final ForgeConfigSpec commonSpec;
  public static final Config COMMON;

  private static final String MOB_CATCHING_LUCK_TEXT =
      "Defines the luck of capturing the mob e.g. luck of 3 means a change of 1 to 3 to capture a mob. Higher numbers requires more luck. 0 = disable luck.";
  private static final String PROCESS_TIME_TEXT =
      "Defines after how many seconds a loot drop is performed.";
  private static final String DROP_SOUND_TEXT =
      "Defines the sound which is played when a loot drop is performed.";
  private static final String SUPPORTED_MOBS_ANIMAL_PLANS_FARM =
      "Supported Mobs for the animal plains farm. (Use empty list to allow all mobs.)";
  private static final String SUPPORTED_MOBS_BEE_HIVE_FARM =
      "Supported Mobs for the bee hive farm. (Use empty list to allow all mobs.)";
  private static final String SUPPORTED_MOBS_DESERT_FARM =
      "Supported Mobs for the desert farm. (Use empty list to allow all mobs.)";
  private static final String SUPPORTED_MOBS_JUNGLE_FARM =
      "Supported Mobs for the jungle farm. (Use empty list to allow all mobs.)";
  private static final String SUPPORTED_MOBS_NETHER_FARM =
      "Supported Mobs for the nether farm. (Use empty list to allow all mobs.)";
  private static final String SUPPORTED_MOBS_OCEAN_FARM =
      "Supported Mobs for the ocean farm. (Use empty list to allow all mobs.)";
  private static final String SUPPORTED_MOBS_PLAINS_CAVE_FARM =
      "Supported Mobs for the plains cave farm. (Use empty list to allow all mobs.)";
  private static final String SUPPORTED_MOBS_SWAMP_FARM =
      "Supported Mobs for the swamp farm. (Use empty list to allow all mobs.)";

  protected CommonConfig() {}

  static {
    com.electronwill.nightconfig.core.Config.setInsertionOrderPreserved(true);
    final Pair<Config, ForgeConfigSpec> specPair =
        new ForgeConfigSpec.Builder().configure(Config::new);
    commonSpec = specPair.getRight();
    COMMON = specPair.getLeft();
    log.info("{} Common config ...", Constants.LOG_REGISTER_PREFIX);
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, commonSpec);
  }

  public static class Config {

    private static final String getCatchableMobsText(String itemName) {
      return "The following mobs can be catched with the " + itemName
          + ". (Use empty list to allow all mobs.)";
    }

    public final ForgeConfigSpec.BooleanValue informOwnerAboutFullStorage;
    public final ForgeConfigSpec.BooleanValue logFullStorage;
    public final ForgeConfigSpec.IntValue lootPreviewRolls;
    public final ForgeConfigSpec.BooleanValue playDropSound;

    public final ForgeConfigSpec.IntValue creativeMobFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> creativeMobFarmDropSound;

    // Copper Mob Farm
    public final ForgeConfigSpec.IntValue copperAnimalPlainsFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperAnimalPlainsFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperAnimalPlainsFarmMobs;

    public final ForgeConfigSpec.IntValue copperBeeHiveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperBeeHiveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperBeeHiveFarmMobs;

    public final ForgeConfigSpec.IntValue copperDesertFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperDesertFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperDesertFarmMobs;

    public final ForgeConfigSpec.IntValue copperJungleFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperJungleFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperJungleFarmMobs;

    public final ForgeConfigSpec.IntValue copperMonsterPlainsCaveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperMonsterPlainsCaveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperMonsterPlainsCaveFarmMobs;

    public final ForgeConfigSpec.IntValue copperNetherFortressFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperNetherFortressFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperNetherFortressFarmMobs;

    public final ForgeConfigSpec.IntValue copperOceanFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperOceanFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperOceanFarmMobs;

    public final ForgeConfigSpec.IntValue copperSwampFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> copperSwampFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> copperSwampFarmMobs;

    // Iron Mob Farm
    public final ForgeConfigSpec.IntValue ironAnimalPlainsFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironAnimalPlainsFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironAnimalPlainsFarmMobs;

    public final ForgeConfigSpec.IntValue ironBeeHiveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironBeeHiveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironBeeHiveFarmMobs;

    public final ForgeConfigSpec.IntValue ironDesertFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironDesertFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironDesertFarmMobs;

    public final ForgeConfigSpec.IntValue ironJungleFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironJungleFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironJungleFarmMobs;

    public final ForgeConfigSpec.IntValue ironMonsterPlainsCaveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironMonsterPlainsCaveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironMonsterPlainsCaveFarmMobs;

    public final ForgeConfigSpec.IntValue ironNetherFortressFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironNetherFortressFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironNetherFortressFarmMobs;

    public final ForgeConfigSpec.IntValue ironOceanFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironOceanFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironOceanFarmMobs;

    public final ForgeConfigSpec.IntValue ironSwampFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> ironSwampFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> ironSwampFarmMobs;

    // Gold Mob Farm
    public final ForgeConfigSpec.IntValue goldAnimalPlainsFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldAnimalPlainsFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldAnimalPlainsFarmMobs;

    public final ForgeConfigSpec.IntValue goldBeeHiveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldBeeHiveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldBeeHiveFarmMobs;

    public final ForgeConfigSpec.IntValue goldDesertFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldDesertFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldDesertFarmMobs;

    public final ForgeConfigSpec.IntValue goldJungleFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldJungleFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldJungleFarmMobs;

    public final ForgeConfigSpec.IntValue goldMonsterPlainsCaveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldMonsterPlainsCaveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldMonsterPlainsCaveFarmMobs;

    public final ForgeConfigSpec.IntValue goldNetherFortressFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldNetherFortressFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldNetherFortressFarmMobs;

    public final ForgeConfigSpec.IntValue goldOceanFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldOceanFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldOceanFarmMobs;

    public final ForgeConfigSpec.IntValue goldSwampFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> goldSwampFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldSwampFarmMobs;

    // Netherite Mob Farm
    public final ForgeConfigSpec.IntValue netheriteAnimalPlainsFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteAnimalPlainsFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteAnimalPlainsFarmMobs;

    public final ForgeConfigSpec.IntValue netheriteBeeHiveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteBeeHiveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteBeeHiveFarmMobs;

    public final ForgeConfigSpec.IntValue netheriteDesertFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteDesertFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteDesertFarmMobs;

    public final ForgeConfigSpec.IntValue netheriteJungleFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteJungleFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteJungleFarmMobs;

    public final ForgeConfigSpec.IntValue netheriteMonsterPlainsCaveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteMonsterPlainsCaveFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteMonsterPlainsCaveFarmMobs;

    public final ForgeConfigSpec.IntValue netheriteNetherFortressFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteNetherFortressFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteNetherFortressFarmMobs;

    public final ForgeConfigSpec.IntValue netheriteOceanFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteOceanFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteOceanFarmMobs;

    public final ForgeConfigSpec.IntValue netheriteSwampFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<String> netheriteSwampFarmDropSound;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteSwampFarmMobs;

    // Mob Catcher
    public final ForgeConfigSpec.IntValue catchCageMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> catchCageMobs;

    public final ForgeConfigSpec.IntValue catchCageSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> catchCageSmallMobs;

    public final ForgeConfigSpec.IntValue collarSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> collarSmallMobs;

    public final ForgeConfigSpec.IntValue enderLassoMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> enderLassoMobs;

    public final ForgeConfigSpec.IntValue fishingBowlMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> fishingBowlMobs;

    public final ForgeConfigSpec.IntValue fishingNetSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> fishingNetSmallMobs;

    public final ForgeConfigSpec.IntValue goldenLassoMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> goldenLassoMobs;

    public final ForgeConfigSpec.IntValue insectNetMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> insectNetMobs;

    public final ForgeConfigSpec.IntValue netheriteLassoMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> netheriteLassoMobs;

    public final ForgeConfigSpec.IntValue urnSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> urnSmallMobs;

    public final ForgeConfigSpec.IntValue witchBottleMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> witchBottleMobs;

    // Drop Settings
    public final ForgeConfigSpec.BooleanValue beeDropHoneycomb;
    public final ForgeConfigSpec.BooleanValue blazeDropBlazeRod;
    public final ForgeConfigSpec.BooleanValue cowDropRawBeef;
    public final ForgeConfigSpec.BooleanValue chickenDropEggs;
    public final ForgeConfigSpec.BooleanValue chickenDropRawChicken;
    public final ForgeConfigSpec.BooleanValue sheepDropRawMutton;

    Config(ForgeConfigSpec.Builder builder) {
      builder.comment(Constants.MOD_NAME);

      builder.push("General");
      informOwnerAboutFullStorage = builder
          .comment("Enable/Disable owner messages about full storage to avoid lagging systems.")
          .define("informOwnerAboutFullStorage", true);
      logFullStorage =
          builder.comment("Enable/Disable full storage log messages for the server logs.")
              .define("logFullStorage", true);
      lootPreviewRolls = builder.comment(
          "Number of roll's to get the loot preview for a captured mob. Higher numbers require more server load, but giving a more completed overview.")
          .defineInRange("lootPreviewRolls", 2, 1, 5);
      playDropSound = builder.comment("Enable/Disable the drop sound for the loot drops.")
          .define("playDropSound", true);
      builder.pop();

      // Copper Mob Farms
      builder.push("Copper Mob Farms");
      builder.comment("Configuration for the copper mob farms.");

      builder.push("Copper Animal Plains Farms");
      copperAnimalPlainsFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("copperAnimalPlainsFarmProcessTime", 600, 10, 3600);
      copperAnimalPlainsFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("copperAnimalPlainsFarmDropSound", "minecraft:entity.chicken.egg");
      copperAnimalPlainsFarmMobs = builder.comment(SUPPORTED_MOBS_ANIMAL_PLANS_FARM)
          .define("copperAnimalPlainsFarmMobs", new ArrayList<String>(Plains.Passive));
      builder.pop();

      builder.push("Copper Bee Hive Farms");
      copperBeeHiveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("copperBeeHiveFarmProcessTime", 600, 10, 3600);
      copperBeeHiveFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("copperBeeHiveFarmDropSound", "minecraft:block.beehive.exit");
      copperBeeHiveFarmMobs = builder.comment(SUPPORTED_MOBS_BEE_HIVE_FARM)
          .define("copperBeeHiveFarmMobs", new ArrayList<String>(BeeAnimal.AllLootable));
      builder.pop();

      builder.push("Copper Desert Farm");
      copperDesertFarmProcessTime =
          builder.comment("Defines after how many seconds a drop is performed.")
              .defineInRange("copperDesertFarmProcessTime", 600, 10, 3600);
      copperDesertFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("copperDesertFarmDropSound", "minecraft:block.sand.hit");
      copperDesertFarmMobs = builder.comment(SUPPORTED_MOBS_DESERT_FARM)
          .define("copperDesertFarmMobs", new ArrayList<String>(Desert.All));
      builder.pop();

      builder.push("Copper Jungle Farm");
      copperJungleFarmProcessTime =
          builder.comment("Defines after how many seconds a drop is performed.")
              .defineInRange("copperJungleFarmProcessTime", 600, 10, 3600);
      copperJungleFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("copperJungleFarmDropSound", "minecraft:block.azalea.hit");
      copperJungleFarmMobs = builder.comment(SUPPORTED_MOBS_JUNGLE_FARM)
          .define("copperJungleFarmMobs", new ArrayList<String>(Jungle.All));
      builder.pop();

      builder.push("Copper Monster Plains Cave Farm");
      copperMonsterPlainsCaveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("copperMonsterPlainsCaveFarmProcessTime", 600, 10, 3600);
      copperMonsterPlainsCaveFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("copperMonsterPlainsCaveFarmDropSound", "minecraft:block.cave_vines.fall");
      copperMonsterPlainsCaveFarmMobs = builder.comment(SUPPORTED_MOBS_PLAINS_CAVE_FARM)
          .define("copperMonsterPlainsCaveFarmMobs", new ArrayList<String>(PlainsCave.Hostile));
      builder.pop();

      builder.push("Copper Nether Fortress Farm");
      copperNetherFortressFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("copperNetherFortressFarmProcessTime", 600, 10, 3600);
      copperNetherFortressFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("copperNetherFortressFarmDropSound", "minecraft:block.netherrack.fall");
      copperNetherFortressFarmMobs = builder.comment(SUPPORTED_MOBS_NETHER_FARM)
          .define("copperNetherFortressFarmMobs", new ArrayList<String>(NetherFortress.All));
      builder.pop();

      builder.push("Copper Ocean Farm");
      copperOceanFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("copperOceanFarmProcessTime", 600, 10, 3600);
      copperOceanFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("copperOceanFarmDropSound",
          "minecraft:entity.fish.swim");
      copperOceanFarmMobs = builder.comment(SUPPORTED_MOBS_OCEAN_FARM).define("copperOceanFarmMobs",
          new ArrayList<String>(Ocean.All));
      builder.pop();

      builder.push("Copper Swamp Farm");
      copperSwampFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("copperSwampFarmProcessTime", 600, 10, 3600);
      copperSwampFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("copperSwampFarmDropSound",
          "minecraft:entity.slime.squish");
      copperSwampFarmMobs = builder.comment(SUPPORTED_MOBS_SWAMP_FARM).define("copperSwampFarmMobs",
          new ArrayList<String>(Swamp.All));
      builder.pop();

      builder.pop();

      // Iron Mob Farms
      builder.push("Iron Mob Farms");
      builder.comment("Configuration for the iron mob farms.");

      builder.push("Iron Animal Plains Farms");
      ironAnimalPlainsFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironAnimalPlainsFarmProcessTime", 300, 10, 3600);
      ironAnimalPlainsFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("ironAnimalPlainsFarmDropSound", "minecraft:entity.chicken.egg");
      ironAnimalPlainsFarmMobs = builder.comment(SUPPORTED_MOBS_ANIMAL_PLANS_FARM)
          .define("ironAnimalPlainsFarmMobs", new ArrayList<String>(Plains.Passive));
      builder.pop();

      builder.push("Iron Bee Hive Farms");
      ironBeeHiveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironBeeHiveFarmProcessTime", 300, 10, 3600);
      ironBeeHiveFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("ironBeeHiveFarmDropSound",
          "minecraft:block.beehive.exit");
      ironBeeHiveFarmMobs = builder.comment(SUPPORTED_MOBS_BEE_HIVE_FARM)
          .define("ironBeeHiveFarmMobs", new ArrayList<String>(BeeAnimal.AllLootable));
      builder.pop();

      builder.push("Iron Desert Farm");
      ironDesertFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironDesertFarmProcessTime", 300, 10, 3600);
      ironDesertFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("ironDesertFarmDropSound",
          "minecraft:block.sand.hit");
      ironDesertFarmMobs = builder.comment(SUPPORTED_MOBS_DESERT_FARM).define("ironDesertFarmMobs",
          new ArrayList<String>(Desert.All));
      builder.pop();

      builder.push("Iron Jungle Farm");
      ironJungleFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironJungleFarmProcessTime", 300, 10, 3600);
      ironJungleFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("ironJungleFarmDropSound",
          "minecraft:block.azalea.hit");
      ironJungleFarmMobs = builder.comment(SUPPORTED_MOBS_JUNGLE_FARM).define("ironJungleFarmMobs",
          new ArrayList<String>(Jungle.All));
      builder.pop();

      builder.push("Iron Monster Plains Cave Farm");
      ironMonsterPlainsCaveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironMonsterPlainsCaveFarmProcessTime", 300, 10, 3600);
      ironMonsterPlainsCaveFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("ironMonsterPlainsCaveFarmDropSound", "minecraft:block.cave_vines.fall");
      ironMonsterPlainsCaveFarmMobs = builder.comment(SUPPORTED_MOBS_PLAINS_CAVE_FARM)
          .define("ironMonsterPlainsCaveFarmMobs", new ArrayList<String>(PlainsCave.Hostile));
      builder.pop();

      builder.push("Iron Nether Fortress Farm");
      ironNetherFortressFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironNetherFortressFarmProcessTime", 300, 10, 3600);
      ironNetherFortressFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("ironNetherFortressFarmDropSound", "minecraft:block.netherrack.fall");
      ironNetherFortressFarmMobs = builder.comment(SUPPORTED_MOBS_NETHER_FARM)
          .define("ironNetherFortressFarmMobs", new ArrayList<String>(NetherFortress.All));
      builder.pop();

      builder.push("Iron Ocean Farm");
      ironOceanFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironOceanFarmProcessTime", 300, 10, 3600);
      ironOceanFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("ironOceanFarmDropSound",
          "minecraft:entity.fish.swim");
      ironOceanFarmMobs = builder.comment(SUPPORTED_MOBS_OCEAN_FARM).define("ironOceanFarmMobs",
          new ArrayList<String>(Ocean.All));
      builder.pop();

      builder.push("Iron Swamp Farm");
      ironSwampFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("ironSwampFarmProcessTime", 300, 10, 3600);
      ironSwampFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("ironSwampFarmDropSound",
          "minecraft:entity.slime.squish");
      ironSwampFarmMobs = builder.comment(SUPPORTED_MOBS_SWAMP_FARM).define("ironSwampFarmMobs",
          new ArrayList<String>(Swamp.All));
      builder.pop();

      builder.pop();

      // Gold Mob Farms
      builder.push("Gold Mob Farms");
      builder.comment("Configuration for the gold mob farms.");

      builder.push("Gold Animal Plains Farms");
      goldAnimalPlainsFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldAnimalPlainsFarmProcessTime", 150, 10, 3600);
      goldAnimalPlainsFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("goldAnimalPlainsFarmDropSound", "minecraft:entity.chicken.egg");
      goldAnimalPlainsFarmMobs = builder.comment(SUPPORTED_MOBS_ANIMAL_PLANS_FARM)
          .define("goldAnimalPlainsFarmMobs", new ArrayList<String>(Plains.Passive));
      builder.pop();

      builder.push("Gold Bee Hive Farms");
      goldBeeHiveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldBeeHiveFarmProcessTime", 150, 10, 3600);
      goldBeeHiveFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("goldBeeHiveFarmDropSound",
          "minecraft:block.beehive.exit");
      goldBeeHiveFarmMobs = builder.comment(SUPPORTED_MOBS_BEE_HIVE_FARM)
          .define("goldBeeHiveFarmMobs", new ArrayList<String>(BeeAnimal.AllLootable));
      builder.pop();

      builder.push("Gold Desert Farm");
      goldDesertFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldDesertFarmProcessTime", 150, 10, 3600);
      goldDesertFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("goldDesertFarmDropSound",
          "minecraft:block.sand.hit");
      goldDesertFarmMobs = builder.comment(SUPPORTED_MOBS_DESERT_FARM).define("goldDesertFarmMobs",
          new ArrayList<String>(Desert.All));
      builder.pop();

      builder.push("Gold Jungle Farm");
      goldJungleFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldJungleFarmProcessTime", 150, 10, 3600);
      goldJungleFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("goldJungleFarmDropSound",
          "minecraft:block.azalea.hit");
      goldJungleFarmMobs = builder.comment(SUPPORTED_MOBS_JUNGLE_FARM).define("goldJungleFarmMobs",
          new ArrayList<String>(Jungle.All));
      builder.pop();

      builder.push("Gold Monster Plains Cave Farm");
      goldMonsterPlainsCaveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldMonsterPlainsCaveFarmProcessTime", 150, 10, 3600);
      goldMonsterPlainsCaveFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("goldMonsterPlainsCaveFarmDropSound", "minecraft:block.cave_vines.fall");
      goldMonsterPlainsCaveFarmMobs = builder.comment(SUPPORTED_MOBS_PLAINS_CAVE_FARM)
          .define("goldMonsterPlainsCaveFarmMobs", new ArrayList<String>(PlainsCave.Hostile));
      builder.pop();

      builder.push("Gold Nether Fortress Farm");
      goldNetherFortressFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldNetherFortressFarmProcessTime", 150, 10, 3600);
      goldNetherFortressFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("goldNetherFortressFarmDropSound", "minecraft:block.netherrack.fall");
      goldNetherFortressFarmMobs = builder.comment(SUPPORTED_MOBS_NETHER_FARM)
          .define("goldNetherFortressFarmMobs", new ArrayList<String>(NetherFortress.All));
      builder.pop();

      builder.push("Gold Ocean Farm");
      goldOceanFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldOceanFarmProcessTime", 150, 10, 3600);
      goldOceanFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("goldOceanFarmDropSound",
          "minecraft:entity.fish.swim");
      goldOceanFarmMobs = builder.comment(SUPPORTED_MOBS_OCEAN_FARM).define("goldOceanFarmMobs",
          new ArrayList<String>(Ocean.All));
      builder.pop();

      builder.push("Gold Swamp Farm");
      goldSwampFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("goldSwampFarmProcessTime", 150, 10, 3600);
      goldSwampFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("goldSwampFarmDropSound",
          "minecraft:entity.slime.squish");
      goldSwampFarmMobs = builder.comment(SUPPORTED_MOBS_SWAMP_FARM).define("goldSwampFarmMobs",
          new ArrayList<String>(Swamp.All));
      builder.pop();

      builder.pop();

      // Netherite Mob Farms
      builder.push("Netherite Mob Farms");
      builder.comment("Configuration for the netherite mob farms.");

      builder.push("Netherite Animal Plains Farms");
      netheriteAnimalPlainsFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteAnimalPlainsFarmProcessTime", 75, 10, 3600);
      netheriteAnimalPlainsFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteAnimalPlainsFarmDropSound", "minecraft:entity.chicken.egg");
      netheriteAnimalPlainsFarmMobs = builder.comment(SUPPORTED_MOBS_ANIMAL_PLANS_FARM)
          .define("netheriteAnimalPlainsFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Netherite Bee Hive Farms");
      netheriteBeeHiveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteBeeHiveFarmProcessTime", 75, 10, 3600);
      netheriteBeeHiveFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteBeeHiveFarmDropSound", "minecraft:block.beehive.exit");
      netheriteBeeHiveFarmMobs = builder.comment(SUPPORTED_MOBS_BEE_HIVE_FARM)
          .define("netheriteBeeHiveFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Netherite Desert Farms");
      netheriteDesertFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteDesertFarmProcessTime", 75, 10, 3600);
      netheriteDesertFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteDesertFarmDropSound", "minecraft:block.sand.hit");
      netheriteDesertFarmMobs = builder.comment(SUPPORTED_MOBS_DESERT_FARM)
          .define("netheriteDesertFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Netherite Jungle Farm");
      netheriteJungleFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteJungleFarmProcessTime", 75, 10, 3600);
      netheriteJungleFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteJungleFarmDropSound", "minecraft:block.bamboo.break");
      netheriteJungleFarmMobs = builder.comment(SUPPORTED_MOBS_JUNGLE_FARM)
          .define("netheriteJungleFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Netherite Monster Plains Cave Farms");
      netheriteMonsterPlainsCaveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteMonsterPlainsCaveFarmProcessTime", 75, 10, 3600);
      netheriteMonsterPlainsCaveFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteMonsterPlainsCaveFarmDropSound", "minecraft:block.netherrack.fall");
      netheriteMonsterPlainsCaveFarmMobs = builder.comment(SUPPORTED_MOBS_PLAINS_CAVE_FARM)
          .define("netheriteMonsterPlainsCaveFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Netherite Nether Fortress Farm");
      netheriteNetherFortressFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteNetherFortressFarmProcessTime", 75, 10, 3600);
      netheriteNetherFortressFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteNetherFortressFarmDropSound", "minecraft:block.nether_wart.break");
      netheriteNetherFortressFarmMobs = builder.comment(SUPPORTED_MOBS_NETHER_FARM)
          .define("netheriteNetherFortressFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Netherite Ocean Farm");
      netheriteOceanFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteOceanFarmProcessTime", 75, 10, 3600);
      netheriteOceanFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteOceanFarmDropSound", "minecraft:block.bubble_column.bubble_pop");
      netheriteOceanFarmMobs = builder.comment(SUPPORTED_MOBS_OCEAN_FARM)
          .define("netheriteOceanFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Netherite Swamp Farm");
      netheriteSwampFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("netheriteSwampFarmProcessTime", 75, 10, 3600);
      netheriteSwampFarmDropSound = builder.comment(DROP_SOUND_TEXT)
          .define("netheriteSwampFarmDropSound", "minecraft:block.slime_block.break");
      netheriteSwampFarmMobs = builder.comment(SUPPORTED_MOBS_SWAMP_FARM)
          .define("netheriteSwampFarmMobs", new ArrayList<String>());
      builder.pop();

      builder.pop();

      // Creative Mob Farm
      builder.push("Creative Mob Farm");
      creativeMobFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("creativeMobFarmProcessTime", 60, 10, 3600);
      creativeMobFarmDropSound = builder.comment(DROP_SOUND_TEXT).define("creativeMobFarmDropSound",
          "minecraft:entity.chicken.egg");
      builder.pop();

      // Mob Catching Item

      builder.push("Catch Cage (Mob Catching Item)");
      catchCageMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("catchCageMobCatchingLuck", 6, 0, 100);
      catchCageMobs = builder.comment(getCatchableMobsText("catch cage")).define("catchCageMobs",
          new ArrayList<String>(Arrays.asList(PassiveAnimal.PANDA, NeutralAnimal.POLAR_BEAR)));
      builder.pop();

      builder.push("Catch Cage small (Mob Catching Item)");
      catchCageSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("catchCageSmallMobCatchingLuck", 3, 0, 100);
      catchCageSmallMobs = builder.comment(getCatchableMobsText("catch cage small"))
          .define("catchCageSmallMobs", new ArrayList<String>(
              Arrays.asList(PassiveAnimal.CHICKEN, PassiveAnimal.RABBIT, PassiveAnimal.PARROT)));
      builder.pop();

      builder.push("Collar small (Mob Catching Item)");
      collarSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("collarSmallMobCatchingLuck", 3, 0, 100);
      collarSmallMobs =
          builder.comment(getCatchableMobsText("collar small")).define("collarSmallMobs",
              new ArrayList<String>(
                  Arrays.asList(PassiveAnimal.CHICKEN, PassiveAnimal.COW, PassiveAnimal.DONKEY,
                      PassiveAnimal.HORSE, PassiveAnimal.PIG, PassiveAnimal.SHEEP)));
      builder.pop();

      builder.push("Ender Lasso (Mob Catching Item)");
      enderLassoMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("enderLassoMobCatchingLuck", 10, 0, 100);
      enderLassoMobs = builder.comment(getCatchableMobsText("ender lasso")).define("enderLassoMobs",
          new ArrayList<String>(HostileMonster.All));

      builder.push("Fishing bowl (Mob Catching Item)");
      fishingBowlMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("fishingBowlMobCatchingLuck", 3, 0, 100);
      fishingBowlMobs =
          builder.comment(getCatchableMobsText("fishing bowl")).define("fishingBowlMobs",
              new ArrayList<String>(Arrays.asList(AmbientWaterAnimal.COD, AmbientWaterAnimal.SALMON,
                  AmbientWaterAnimal.ATLANTIC_COD, AmbientWaterAnimal.ATLANTIC_HALIBUT,
                  AmbientWaterAnimal.ATLANTIC_HERRING, AmbientWaterAnimal.BLACKFISH,
                  AmbientWaterAnimal.PACIFIC_HALIBUT, AmbientWaterAnimal.PINK_SALMON,
                  AmbientWaterAnimal.POLLOCK, AmbientWaterAnimal.RAINBOW_TROUT)));
      builder.pop();

      builder.push("Fishing net small (Mob Catching Item)");
      fishingNetSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("fishingNetSmallMobCatchingLuck", 3, 0, 100);
      fishingNetSmallMobs =
          builder
              .comment(
                  getCatchableMobsText("fishing net small"))
              .define("fishingNetSmallMobs",
                  new ArrayList<String>(
                      Stream
                          .concat(PassiveWaterAnimal.All.stream(),
                              Arrays.asList(AmbientWaterAnimal.COD, AmbientWaterAnimal.SALMON,
                                  HostileWaterMonster.DROWNED).stream())
                          .collect(Collectors.toList())));
      builder.pop();

      builder.push("Golden Lasso (Mob Catching Item)");
      goldenLassoMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("goldenLassoMobCatchingLuck", 10, 0, 100);
      goldenLassoMobs = builder.comment(getCatchableMobsText("golden lasso"))
          .define("goldenLassoMobs", new ArrayList<String>(PassiveAnimal.All));
      builder.pop();

      builder.push("Insect net (Mob Catching Item)");
      insectNetMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("insectNetMobCatchingLuck", 3, 0, 100);
      insectNetMobs = builder.comment(getCatchableMobsText("insect net")).define("insectNetMobs",
          new ArrayList<String>(
              Stream.concat(BeeAnimal.All.stream(), Arrays.asList(PassiveAnimal.FLY).stream())
                  .collect(Collectors.toList())));
      builder.pop();

      builder.push("Netherite Lasso (Mob Catching Item)");
      netheriteLassoMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("netheriteLassoMobCatchingLuck", 2, 0, 100);
      netheriteLassoMobs = builder.comment(getCatchableMobsText("netherite lasso"))
          .define("netheriteLassoMobs", new ArrayList<String>());
      builder.pop();

      builder.push("Urn small (Mob Catching Item)");
      urnSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("urnSmallMobCatchingLuck", 3, 0, 100);
      urnSmallMobs = builder.comment(getCatchableMobsText("urn small")).define("urnSmallMobs",
          new ArrayList<String>(Arrays.asList(HostileMonster.CAVE_SPIDER, HostileMonster.CREEPER,
              HostileMonster.SPIDER, HostileMonster.SKELETON, HostileMonster.HUSK,
              HostileMonster.ZOMBIE, HostileMonster.ZOMBIE_VILLAGER)));
      builder.pop();

      builder.push("Witch Bottle (Mob Catching Item)");
      witchBottleMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("witchBottleMobCatchingLuck", 10, 0, 100);
      witchBottleMobs = builder.comment(getCatchableMobsText("witch bottle"))
          .define("witchBottleMobs", new ArrayList<String>(
              Arrays.asList(HostileMonster.ENDERMAN, HostileMonster.SLIME, HostileMonster.WITCH)));
      builder.pop();

      builder.push("Bee Settings");
      beeDropHoneycomb =
          builder.comment("Enable/Disable honeycomb drops.").define("beeDropHoneycomb", true);
      builder.pop();

      builder.push("Blaze Settings");
      blazeDropBlazeRod =
          builder.comment("Enable/Disable blaze rod drops.").define("blazeDropBlazeRod", false);
      builder.pop();

      builder.push("Cow Settings");
      cowDropRawBeef =
          builder.comment("Enable/Disable cow raw beef drops.").define("cowDropRawBeef", true);
      builder.pop();

      builder.push("Chicken Settings");
      chickenDropEggs =
          builder.comment("Enable/Disable chicken egg drops.").define("chickenDropEggs", true);
      chickenDropRawChicken = builder.comment("Enable/Disable raw chicken drops.")
          .define("chickenDropRawChicken", true);
      builder.pop();

      builder.push("Sheep Settings");
      sheepDropRawMutton = builder.comment("Enable/Disable sheep raw mutton drops.")
          .define("sheepDropRawMutton", true);
      builder.pop();
    }
  }
}
