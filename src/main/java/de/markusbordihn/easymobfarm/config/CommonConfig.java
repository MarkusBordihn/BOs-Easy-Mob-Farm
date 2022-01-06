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

package de.markusbordihn.easymobfarm.config;

import org.apache.commons.lang3.tuple.Pair;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

import de.markusbordihn.easymobfarm.Constants;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CommonConfig {

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  public static final ForgeConfigSpec commonSpec;
  public static final Config COMMON;

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

    public final ForgeConfigSpec.IntValue mobCatchingLuck;

    public final ForgeConfigSpec.IntValue chickenFarmProcessTime;
    public final ForgeConfigSpec.BooleanValue chickenFarmDropEggs;
    public final ForgeConfigSpec.BooleanValue chickenFarmDropRawChicken;

    Config(ForgeConfigSpec.Builder builder) {
      builder.comment(Constants.MOD_NAME);

      builder.push("General");
      informOwnerAboutFullStorage = builder
          .comment("Enable/Disable owner messages about full storage to avoid lagging systems.")
          .define("informOwnerAboutFullStorage", true);
      logFullStorage = builder
          .comment("Enable/Disable full storage log messages for the server logs.")
          .define("logFullStorage", true);
      builder.pop();

      builder.push("Mob Catching");
      mobCatchingLuck = builder.comment(
          "Defines the luck of capturing the mob e.g. luck of 3 means a change of 1 to 3 to capture a mob. Higher numbers requires more luck. 0 = disable luck.")
          .defineInRange("mobCatchingLuck", 3, 0, 100);
      builder.pop();

      builder.push("Chicken Farm");
      chickenFarmProcessTime =
          builder.comment("Defines after how many seconds a drop is performed.")
              .defineInRange("chickenFarmProcessTime", 300, 10, 3600);
      chickenFarmDropEggs =
          builder.comment("Enable/Disable egg drops.").define("chickenFarmDropEggs", true);
      chickenFarmDropRawChicken =
          builder.comment("Enable/Disable raw chicken drops.").define("chickenFarmDropRawChicken", false);
      builder.pop();
    }
  }
}
