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
import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileWaterMonster;
import de.markusbordihn.easymobfarm.config.mobs.NeutralAnimal;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import de.markusbordihn.easymobfarm.config.mobs.PassiveWaterAnimal;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CommonConfig {

  protected static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public static final ForgeConfigSpec commonSpec;
  public static final Config COMMON;

  private static final String MOB_CATCHING_LUCK_TEXT =
      "Defines the luck of capturing the mob e.g. luck of 3 means a change of 1 to 3 to capture a mob. Higher numbers requires more luck. 0 = disable luck.";
  private static final String PROCESS_TIME_TEXT =
      "Defines after how many seconds a loot drop is performed.";

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

    public final ForgeConfigSpec.BooleanValue informOwnerAboutFullStorage;
    public final ForgeConfigSpec.BooleanValue logFullStorage;
    public final ForgeConfigSpec.IntValue lootPreviewRolls;

    public final ForgeConfigSpec.IntValue animalPlainsFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<List<String>> animalPlainsFarmMobs;

    public final ForgeConfigSpec.IntValue creativeMobFarmProcessTime;

    public final ForgeConfigSpec.IntValue desertFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<List<String>> desertFarmMobs;

    public final ForgeConfigSpec.IntValue jungleFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<List<String>> jungleFarmMobs;

    public final ForgeConfigSpec.IntValue monsterPlainsCaveFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<List<String>> monsterPlainsCaveFarmMobs;

    public final ForgeConfigSpec.IntValue oceanFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<List<String>> oceanFarmMobs;

    public final ForgeConfigSpec.IntValue swampFarmProcessTime;
    public final ForgeConfigSpec.ConfigValue<List<String>> swampFarmMobs;

    public final ForgeConfigSpec.IntValue catchCageMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> catchCageMobs;

    public final ForgeConfigSpec.IntValue catchCageSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> catchCageSmallMobs;

    public final ForgeConfigSpec.IntValue collarSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> collarSmallMobs;

    public final ForgeConfigSpec.IntValue fishingBowlMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> fishingBowlMobs;

    public final ForgeConfigSpec.IntValue fishingNetSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> fishingNetSmallMobs;

    public final ForgeConfigSpec.IntValue urnSmallMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> urnSmallMobs;

    public final ForgeConfigSpec.IntValue witchBottleMobCatchingLuck;
    public final ForgeConfigSpec.ConfigValue<List<String>> witchBottleMobs;

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
      builder.pop();

      builder.push("Animal Plains Farms");
      animalPlainsFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("animalPlainsFarmProcessTime", 300, 10, 3600);
      animalPlainsFarmMobs = builder.comment("Supported Mobs for the animal plains farm.")
          .define("animalPlainsFarmMobs", new ArrayList<String>(Plains.Passive));
      builder.pop();

      builder.push("Creative Mob Farm");
      creativeMobFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("creativeMobFarmProcessTime", 60, 10, 3600);
      builder.pop();

      builder.push("Desert Farm");
      desertFarmProcessTime = builder.comment("Defines after how many seconds a drop is performed.")
          .defineInRange("desertFarmProcessTime", 300, 10, 3600);
      desertFarmMobs = builder.comment("Supported Mobs for the dessert farm.")
          .define("desertFarmMobs", new ArrayList<String>(Desert.All));
      builder.pop();

      builder.push("Jungle Farm");
      jungleFarmProcessTime = builder.comment("Defines after how many seconds a drop is performed.")
          .defineInRange("jungleFarmProcessTime", 300, 10, 3600);
      jungleFarmMobs = builder.comment("Supported Mobs for the dessert farm.")
          .define("jungleFarmMobs", new ArrayList<String>(Jungle.All));
      builder.pop();

      builder.push("Monster Plains Cave Farm");
      monsterPlainsCaveFarmProcessTime = builder.comment(PROCESS_TIME_TEXT)
          .defineInRange("monsterPlainsCaveFarmProcessTime", 300, 10, 3600);
      monsterPlainsCaveFarmMobs =
          builder.comment("Supported Mobs for the monster plains cave farm.")
              .define("monsterPlainsCaveFarmMobs", new ArrayList<String>(PlainsCave.Hostile));
      builder.pop();

      builder.push("Ocean Farm");
      oceanFarmProcessTime =
          builder.comment(PROCESS_TIME_TEXT).defineInRange("oceanFarmProcessTime", 300, 10, 3600);
      oceanFarmMobs = builder.comment("Supported Mobs for the ocean farm.").define("oceanFarmMobs",
          new ArrayList<String>(Ocean.All));
      builder.pop();

      builder.push("Swamp Farm");
      swampFarmProcessTime =
          builder.comment(PROCESS_TIME_TEXT).defineInRange("swampFarmProcessTime", 300, 10, 3600);
      swampFarmMobs = builder.comment("Supported Mobs for the swamp farm.").define("swampFarmMobs",
          new ArrayList<String>(Swamp.All));
      builder.pop();

      builder.push("Catch Cage (Mob Catching Item)");
      catchCageMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("catchCageMobCatchingLuck", 6, 0, 100);
      catchCageMobs = builder.comment("Catchable mobs with the catch cage.").define("catchCageMobs",
          new ArrayList<String>(Arrays.asList(PassiveAnimal.PANDA, NeutralAnimal.POLAR_BEAR)));
      builder.pop();

      builder.push("Catch Cage small (Mob Catching Item)");
      catchCageSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("catchCageSmallMobCatchingLuck", 3, 0, 100);
      catchCageSmallMobs = builder.comment("Catchable mobs with the catch cage small.")
          .define("catchCageSmallMobs", new ArrayList<String>(
              Arrays.asList(PassiveAnimal.CHICKEN, PassiveAnimal.RABBIT, PassiveAnimal.PARROT)));
      builder.pop();

      builder.push("Collar small (Mob Catching Item)");
      collarSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("collarSmallMobCatchingLuck", 3, 0, 100);
      collarSmallMobs =
          builder.comment("Catchable mobs with the collar small.").define("collarSmallMobs",
              new ArrayList<String>(
                  Arrays.asList(PassiveAnimal.CHICKEN, PassiveAnimal.COW, PassiveAnimal.DONKEY,
                      PassiveAnimal.HORSE, PassiveAnimal.PIG, PassiveAnimal.SHEEP)));
      builder.pop();

      builder.push("Fishing bowl (Mob Catching Item)");
      fishingBowlMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("fishingBowlMobCatchingLuck", 3, 0, 100);
      fishingBowlMobs =
          builder.comment("Catchable mobs with the fishing bowl.").define("fishingBowlMobs",
              new ArrayList<String>(Arrays.asList(AmbientWaterAnimal.COD, AmbientWaterAnimal.SALMON,
                  AmbientWaterAnimal.ATLANTIC_COD, AmbientWaterAnimal.ATLANTIC_HALIBUT,
                  AmbientWaterAnimal.ATLANTIC_HERRING, AmbientWaterAnimal.BLACKFISH,
                  AmbientWaterAnimal.PACIFIC_HALIBUT, AmbientWaterAnimal.PINK_SALMON,
                  AmbientWaterAnimal.POLLOCK, AmbientWaterAnimal.RAINBOW_TROUT)));
      builder.pop();

      builder.push("Fishing net small (Mob Catching Item)");
      fishingNetSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("fishingNetSmallMobCatchingLuck", 3, 0, 100);
      fishingNetSmallMobs = builder.comment("Catchable mobs with the fishing net small.").define(
          "fishingNetSmallMobs",
          new ArrayList<String>(Arrays.asList(AmbientWaterAnimal.COD, AmbientWaterAnimal.SALMON,
              PassiveWaterAnimal.SQUID, PassiveWaterAnimal.GLOW_SQUID,
              HostileWaterMonster.DROWNED)));
      builder.pop();

      builder.push("Urn small (Mob Catching Item)");
      urnSmallMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("urnSmallMobCatchingLuck", 3, 0, 100);
      urnSmallMobs = builder.comment("Catchable mobs with the urn small.").define("urnSmallMobs",
          new ArrayList<String>(Arrays.asList(HostileMonster.CAVE_SPIDER, HostileMonster.CREEPER,
              HostileMonster.SPIDER, HostileMonster.SKELETON, HostileMonster.HUSK,
              HostileMonster.ZOMBIE, HostileMonster.ZOMBIE_VILLAGER)));
      builder.pop();

      builder.push("Witch Bottle (Mob Catching Item)");
      witchBottleMobCatchingLuck = builder.comment(MOB_CATCHING_LUCK_TEXT)
          .defineInRange("witchBottleMobCatchingLuck", 10, 0, 100);
      witchBottleMobs = builder.comment("Catchable mobs with the witch bottle.")
          .define("witchBottleMobs", new ArrayList<String>(
              Arrays.asList(HostileMonster.ENDERMAN, HostileMonster.SLIME, HostileMonster.WITCH)));
      builder.pop();

      // @TemplateEntryPoint("Register Forge Config Spec")

      builder.push("Blaze Settings");
      blazeDropBlazeRod =
          builder.comment("Enable/Disable blaze rod drops.").define("blazeDropBlazeRod", true);
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
