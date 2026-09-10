/*
 * Copyright 2024 Markus Bordihn
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

import de.markusbordihn.easymobfarm.data.mobfarm.MobFarmType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.StringJoiner;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MobFarmBonusConfig extends Config {

  public static final String CONFIG_FILE_NAME = "mob_farm_bonus.cfg";
  public static final String CONFIG_FILE_HEADER =
"""
 Mob Farm Bonus Configuration

 This configuration file allows you to define the bonus drops for the Mob Farms.

 Configuration Format:
 --------------------
 <mob_farm_name>::<tier_level>::<entity_type> = <item_name>::<amount>::<chance 1 of x>

 Available Mob Farm Types:
 ------------------------
 - animal_plains_farm: For animals like cows, sheep, chickens, pigs
 - bee_hive_farm: For bees and honey production
 - desert_farm: For desert mobs like husks, rabbits, camels
 - end_farm: For end mobs like endermen, shulkers, endermites
 - iron_golem_farm: For iron golems and poppy drops
 - jungle_farm: For jungle mobs like parrots, pandas, ocelots
 - monster_plains_cave_farm: For common monsters like zombies, skeletons, spiders
 - nether_fortress_farm: For nether mobs like blazes, magma cubes, wither skeletons
 - nether_wastes_farm: For nether mobs like piglins, hoglins, striders, ghasts
 - ocean_farm: For ocean mobs like cod, salmon, squid, guardians
 - swamp_farm: For swamp mobs like frogs, slimes, witches

 Tier Levels (better farms = better bonus chances):
 -------------------------------------------------
 - 0: Basic tier (lowest bonus chance)
 - 1: Improved tier (better bonus chance)
 - 2: Advanced tier (good bonus chance)
 - 3: Elite tier (highest bonus chance)

 Configuration Examples:
 ----------------------
 Basic bee farm with 1 in 20 chance for honeycomb:
   bee_hive_farm::0::minecraft:bee = minecraft:honeycomb::1::20

 Elite bee farm with 1 in 5 chance for honeycomb:
   bee_hive_farm::3::minecraft:bee = minecraft:honeycomb::1::5

 Iron golem farm with bonus iron ingots:
   iron_golem_farm::2::minecraft:iron_golem = minecraft:iron_ingot::2::8

 Multiple bonus items for the same mob (using list syntax):
   ocean_farm::1::minecraft:cod = [minecraft:cod::1::10, minecraft:bone_meal::1::25]

 Important Notes:
 ---------------
 - Lower chance numbers = higher drop probability (1 = always, 100 = 1% chance)
 - To disable a bonus drop, set the amount to 0
 - Each mob farm type targets specific biome-appropriate mobs
 - Higher tier farms have better default bonus chances

""";
  public static final String LOG_PREFIX = "[MobFarmBonusConfig]";

  private static final Random random = new Random();
  private static final HashMap<String, List<BonusDrop>> mobFarmBonusMap = new HashMap<>();
  private static final HashMap<String, List<BonusDrop>> defaultMobFarmBonusMap = new HashMap<>();

  static {
    // Animal Plains Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:cow",
        List.of(new BonusDrop(20, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:cow",
        List.of(new BonusDrop(15, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:cow",
        List.of(new BonusDrop(10, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:cow",
        List.of(new BonusDrop(5, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:sheep",
        List.of(new BonusDrop(20, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:sheep",
        List.of(new BonusDrop(15, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:sheep",
        List.of(new BonusDrop(10, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:sheep",
        List.of(new BonusDrop(5, new ItemStack(Items.WHITE_WOOL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::0::minecraft:chicken",
        List.of(new BonusDrop(20, new ItemStack(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::1::minecraft:chicken",
        List.of(new BonusDrop(15, new ItemStack(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::2::minecraft:chicken",
        List.of(new BonusDrop(10, new ItemStack(Items.EGG, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.ANIMAL_PLAINS_FARM.getId() + "::3::minecraft:chicken",
        List.of(new BonusDrop(5, new ItemStack(Items.EGG, 1))));

    // Bee Hive Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::0::minecraft:bee",
        List.of(new BonusDrop(20, new ItemStack(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::1::minecraft:bee",
        List.of(new BonusDrop(15, new ItemStack(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::2::minecraft:bee",
        List.of(new BonusDrop(10, new ItemStack(Items.HONEYCOMB, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.BEE_HIVE_FARM.getId() + "::3::minecraft:bee",
        List.of(new BonusDrop(5, new ItemStack(Items.HONEYCOMB, 1))));

    // Desert Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::0::minecraft:husk",
        List.of(new BonusDrop(20, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::1::minecraft:husk",
        List.of(new BonusDrop(15, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::2::minecraft:husk",
        List.of(new BonusDrop(10, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::3::minecraft:husk",
        List.of(new BonusDrop(5, new ItemStack(Items.SAND, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::0::minecraft:rabbit",
        List.of(new BonusDrop(20, new ItemStack(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::1::minecraft:rabbit",
        List.of(new BonusDrop(15, new ItemStack(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::2::minecraft:rabbit",
        List.of(new BonusDrop(10, new ItemStack(Items.RABBIT_HIDE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.DESERT_FARM.getId() + "::3::minecraft:rabbit",
        List.of(new BonusDrop(5, new ItemStack(Items.RABBIT_HIDE, 1))));

    // Iron Golem Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::0::minecraft:iron_golem",
        List.of(new BonusDrop(20, new ItemStack(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::1::minecraft:iron_golem",
        List.of(new BonusDrop(15, new ItemStack(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::2::minecraft:iron_golem",
        List.of(new BonusDrop(10, new ItemStack(Items.IRON_INGOT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.IRON_GOLEM_FARM.getId() + "::3::minecraft:iron_golem",
        List.of(new BonusDrop(5, new ItemStack(Items.IRON_INGOT, 1))));

    // Jungle Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::0::minecraft:parrot",
        List.of(new BonusDrop(20, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::1::minecraft:parrot",
        List.of(new BonusDrop(15, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::2::minecraft:parrot",
        List.of(new BonusDrop(10, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::3::minecraft:parrot",
        List.of(new BonusDrop(5, new ItemStack(Items.FEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::0::minecraft:panda",
        List.of(new BonusDrop(20, new ItemStack(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::1::minecraft:panda",
        List.of(new BonusDrop(15, new ItemStack(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::2::minecraft:panda",
        List.of(new BonusDrop(10, new ItemStack(Items.BAMBOO, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.JUNGLE_FARM.getId() + "::3::minecraft:panda",
        List.of(new BonusDrop(5, new ItemStack(Items.BAMBOO, 1))));

    // Monster Plains Cave Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::0::minecraft:zombie",
        List.of(
            new BonusDrop(20, new ItemStack(Items.ROTTEN_FLESH, 1)),
            new BonusDrop(40, new ItemStack(Items.IRON_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::1::minecraft:zombie",
        List.of(
            new BonusDrop(15, new ItemStack(Items.ROTTEN_FLESH, 1)),
            new BonusDrop(25, new ItemStack(Items.IRON_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::2::minecraft:zombie",
        List.of(
            new BonusDrop(10, new ItemStack(Items.ROTTEN_FLESH, 1)),
            new BonusDrop(15, new ItemStack(Items.IRON_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.MONSTER_PLAINS_CAVE_FARM.getId() + "::3::minecraft:zombie",
        List.of(
            new BonusDrop(5, new ItemStack(Items.ROTTEN_FLESH, 1)),
            new BonusDrop(10, new ItemStack(Items.IRON_NUGGET, 1))));

    // Nether Fortress Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::0::minecraft:blaze",
        List.of(new BonusDrop(20, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::1::minecraft:blaze",
        List.of(new BonusDrop(15, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::2::minecraft:blaze",
        List.of(new BonusDrop(10, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::3::minecraft:blaze",
        List.of(new BonusDrop(5, new ItemStack(Items.BLAZE_ROD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::0::minecraft:magma_cube",
        List.of(new BonusDrop(20, new ItemStack(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::1::minecraft:magma_cube",
        List.of(new BonusDrop(15, new ItemStack(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::2::minecraft:magma_cube",
        List.of(new BonusDrop(10, new ItemStack(Items.MAGMA_CREAM, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_FORTRESS_FARM.getId() + "::3::minecraft:magma_cube",
        List.of(new BonusDrop(5, new ItemStack(Items.MAGMA_CREAM, 1))));

    // Ocean Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::0::minecraft:cod",
        List.of(new BonusDrop(20, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::1::minecraft:cod",
        List.of(new BonusDrop(15, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::2::minecraft:cod",
        List.of(new BonusDrop(10, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::3::minecraft:cod",
        List.of(new BonusDrop(5, new ItemStack(Items.COD, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::0::minecraft:squid",
        List.of(new BonusDrop(20, new ItemStack(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::1::minecraft:squid",
        List.of(new BonusDrop(15, new ItemStack(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::2::minecraft:squid",
        List.of(new BonusDrop(10, new ItemStack(Items.INK_SAC, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.OCEAN_FARM.getId() + "::3::minecraft:squid",
        List.of(new BonusDrop(5, new ItemStack(Items.INK_SAC, 1))));

    // Swamp Farm Bonus
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:frog",
        List.of(new BonusDrop(20, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:frog",
        List.of(new BonusDrop(15, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:frog",
        List.of(new BonusDrop(10, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:frog",
        List.of(new BonusDrop(5, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:slime",
        List.of(new BonusDrop(20, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:slime",
        List.of(new BonusDrop(15, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:slime",
        List.of(new BonusDrop(10, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:slime",
        List.of(new BonusDrop(5, new ItemStack(Items.SLIME_BALL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::0::minecraft:witch",
        List.of(new BonusDrop(20, new ItemStack(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::1::minecraft:witch",
        List.of(new BonusDrop(15, new ItemStack(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::2::minecraft:witch",
        List.of(new BonusDrop(10, new ItemStack(Items.REDSTONE, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.SWAMP_FARM.getId() + "::3::minecraft:witch",
        List.of(new BonusDrop(5, new ItemStack(Items.REDSTONE, 1))));

    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::0::minecraft:enderman",
        List.of(new BonusDrop(20, new ItemStack(Items.ENDER_PEARL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::1::minecraft:enderman",
        List.of(new BonusDrop(15, new ItemStack(Items.ENDER_PEARL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::2::minecraft:enderman",
        List.of(new BonusDrop(10, new ItemStack(Items.ENDER_PEARL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::3::minecraft:enderman",
        List.of(new BonusDrop(5, new ItemStack(Items.ENDER_PEARL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::0::minecraft:shulker",
        List.of(new BonusDrop(20, new ItemStack(Items.SHULKER_SHELL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::1::minecraft:shulker",
        List.of(new BonusDrop(15, new ItemStack(Items.SHULKER_SHELL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::2::minecraft:shulker",
        List.of(new BonusDrop(10, new ItemStack(Items.SHULKER_SHELL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::3::minecraft:shulker",
        List.of(new BonusDrop(5, new ItemStack(Items.SHULKER_SHELL, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::0::minecraft:endermite",
        List.of(new BonusDrop(20, new ItemStack(Items.CHORUS_FRUIT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::1::minecraft:endermite",
        List.of(new BonusDrop(15, new ItemStack(Items.CHORUS_FRUIT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::2::minecraft:endermite",
        List.of(new BonusDrop(10, new ItemStack(Items.CHORUS_FRUIT, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.END_FARM.getId() + "::3::minecraft:endermite",
        List.of(new BonusDrop(5, new ItemStack(Items.CHORUS_FRUIT, 1))));

    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::0::minecraft:piglin",
        List.of(new BonusDrop(20, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::1::minecraft:piglin",
        List.of(new BonusDrop(15, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::2::minecraft:piglin",
        List.of(new BonusDrop(10, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::3::minecraft:piglin",
        List.of(new BonusDrop(5, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::0::minecraft:zombified_piglin",
        List.of(new BonusDrop(20, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::1::minecraft:zombified_piglin",
        List.of(new BonusDrop(15, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::2::minecraft:zombified_piglin",
        List.of(new BonusDrop(10, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::3::minecraft:zombified_piglin",
        List.of(new BonusDrop(5, new ItemStack(Items.GOLD_NUGGET, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::0::minecraft:hoglin",
        List.of(new BonusDrop(20, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::1::minecraft:hoglin",
        List.of(new BonusDrop(15, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::2::minecraft:hoglin",
        List.of(new BonusDrop(10, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::3::minecraft:hoglin",
        List.of(new BonusDrop(5, new ItemStack(Items.LEATHER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::0::minecraft:strider",
        List.of(new BonusDrop(20, new ItemStack(Items.STRING, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::1::minecraft:strider",
        List.of(new BonusDrop(15, new ItemStack(Items.STRING, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::2::minecraft:strider",
        List.of(new BonusDrop(10, new ItemStack(Items.STRING, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::3::minecraft:strider",
        List.of(new BonusDrop(5, new ItemStack(Items.STRING, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::0::minecraft:ghast",
        List.of(new BonusDrop(20, new ItemStack(Items.GUNPOWDER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::1::minecraft:ghast",
        List.of(new BonusDrop(15, new ItemStack(Items.GUNPOWDER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::2::minecraft:ghast",
        List.of(new BonusDrop(10, new ItemStack(Items.GUNPOWDER, 1))));
    defaultMobFarmBonusMap.put(
        MobFarmType.NETHER_WASTES_FARM.getId() + "::3::minecraft:ghast",
        List.of(new BonusDrop(5, new ItemStack(Items.GUNPOWDER, 1))));
  }

  static List<BonusDrop> getDefaultBonusDrops(String mobFarmId, int tierLevel, String entityId) {
    return defaultMobFarmBonusMap.getOrDefault(
        mobFarmId + "::" + tierLevel + "::" + entityId, List.of());
  }

  public static void registerConfig() {
    registerConfigFile(CONFIG_FILE_NAME, CONFIG_FILE_HEADER);
    parseConfigFile();
  }

  public static void parseConfigFile() {
    File configFile = getConfigFile(CONFIG_FILE_NAME);
    Properties properties = readConfigFile(configFile);
    Properties unmodifiedProperties = (Properties) properties.clone();

    mobFarmBonusMap.clear();

    // Add default values to config file, handling the new List syntax
    defaultMobFarmBonusMap.forEach(
        (mobFarmName, bonusList) -> {
          if (!properties.containsKey(mobFarmName)) {
            if (bonusList.size() == 1) {
              BonusDrop drop = bonusList.getFirst();
              String itemName =
                  BuiltInRegistries.ITEM.getKey(drop.itemStack().getItem()).toString();
              String value = itemName + "::" + drop.itemStack().getCount() + "::" + drop.chance();
              properties.setProperty(mobFarmName, value);
            } else {
              StringJoiner joiner = new StringJoiner(", ", "[", "]");
              for (BonusDrop drop : bonusList) {
                String itemName =
                    BuiltInRegistries.ITEM.getKey(drop.itemStack().getItem()).toString();
                joiner.add(itemName + "::" + drop.itemStack().getCount() + "::" + drop.chance());
              }
              properties.setProperty(mobFarmName, joiner.toString());
            }
          }
        });

    // Parse config file supporting both plain string and list [item1, item2] syntax
    properties.forEach(
        (key, value) -> {
          String[] keyParts = parseKey((String) key);
          if (keyParts == null) return;

          String valStr = ((String) value).trim();

          if (valStr.startsWith("[") && valStr.endsWith("]")) {
            valStr = valStr.substring(1, valStr.length() - 1).trim();
            for (String itemStr : valStr.split("\\s*,\\s*")) {
              parseAndAddDrop(keyParts, itemStr);
            }
          } else {
            parseAndAddDrop(keyParts, valStr);
          }
        });

    // Update config file if needed
    updateConfigFileIfChanged(configFile, CONFIG_FILE_HEADER, properties, unmodifiedProperties);

    log.info(
        "{} Loaded {} with {} bonus drop entries.",
        LOG_PREFIX,
        CONFIG_FILE_NAME,
        mobFarmBonusMap.values().stream().mapToInt(List::size).sum());
  }

  private static void parseAndAddDrop(String[] keyParts, String valueStr) {
    String[] valueParts = parseValue(valueStr);
    if (valueParts == null) {
      return;
    }

    String itemName = valueParts[0];
    if (itemName.isEmpty()) {
      log.error("{} Missing item name in config entry: {}", LOG_PREFIX, valueStr);
      return;
    }

    try {
      addBonusDropEntry(
          keyParts[0],
          Integer.parseInt(keyParts[1]),
          keyParts[2],
          Integer.parseInt(valueParts[2]),
          itemName,
          Integer.parseInt(valueParts[1]));
    } catch (NumberFormatException e) {
      log.error(
          "{} Invalid number format in config file {}: key={}, value={}",
          LOG_PREFIX,
          CONFIG_FILE_NAME,
          String.join("::", keyParts),
          valueStr);
    }
  }

  public static String getMobFarmKey(String mobFarmName, int tierLevel, String entityType) {
    return mobFarmName + "::" + tierLevel + "::" + entityType;
  }

  public static void addBonusDropEntry(
      String mobFarmName,
      int tierLevel,
      String entityType,
      int chance,
      String itemName,
      int amount) {

    Optional<Item> item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(itemName));
    if (item.isEmpty() || item.get() == Items.AIR) {
      log.error(
          "{} Invalid item name {} in config file {}", LOG_PREFIX, itemName, CONFIG_FILE_NAME);
      return;
    }

    try {
      MobFarmType mobFarmType = MobFarmType.valueOf(mobFarmName.toUpperCase(Locale.ROOT));
      addBonusDropEntry(
          mobFarmType, tierLevel, entityType, chance, new ItemStack(item.get(), amount));
    } catch (IllegalArgumentException e) {
      log.error(
          "{} Invalid mob farm name {} in config file {}",
          LOG_PREFIX,
          mobFarmName,
          CONFIG_FILE_NAME);
    }
  }

  public static void addBonusDropEntry(
      final MobFarmType mobFarmType,
      int tierLevel,
      final String entityType,
      int chance,
      final ItemStack itemStack) {

    // Check if item stack amount is valid
    if (itemStack.isEmpty()) {
      log.error(
          "{} Invalid item stack {} in config file {}", LOG_PREFIX, itemStack, CONFIG_FILE_NAME);
      return;
    }

    // Check if entity type is valid
    if (BuiltInRegistries.ENTITY_TYPE
        .getOptional(ResourceLocation.tryParse(entityType))
        .isEmpty()) {
      log.error(
          "{} Invalid entity type {} in config file {}", LOG_PREFIX, entityType, CONFIG_FILE_NAME);
      return;
    }

    // Validate tier level (0-3)
    if (tierLevel < 0 || tierLevel > 3) {
      log.warn(
          "{} Tier level {} is outside valid range (0-3) for {} in config file {}",
          LOG_PREFIX,
          tierLevel,
          entityType,
          CONFIG_FILE_NAME);
      tierLevel = 0;
    }

    // Validate chance value (must be >= 1)
    if (chance < 1) {
      log.warn(
          "{} Invalid chance value {} (must be >= 1) for {} in config file {}, using default of 5",
          LOG_PREFIX,
          chance,
          entityType,
          CONFIG_FILE_NAME);
      chance = 5;
    }

    // Validate drop amount against item max stack size
    int maxStackSize = itemStack.getMaxStackSize();
    int configuredAmount = itemStack.getCount();
    int maxAllowedAmount = maxStackSize * MobFarmConfig.maxBonusDropMultiplier;

    if (configuredAmount > maxAllowedAmount) {
      log.warn(
          "{} Configured amount {} exceeds maximum allowed {} ({}x stack size of {}) for {} in config file {}, capping to maximum",
          LOG_PREFIX,
          configuredAmount,
          maxAllowedAmount,
          MobFarmConfig.maxBonusDropMultiplier,
          maxStackSize,
          entityType,
          CONFIG_FILE_NAME);
      itemStack.setCount(maxAllowedAmount);
    } else if (configuredAmount > maxStackSize * 10) {
      log.warn(
          "{} High drop amount {} configured ({}x stack size of {}) for {} - ensure your modpack supports this with storage mods",
          LOG_PREFIX,
          configuredAmount,
          configuredAmount / maxStackSize,
          maxStackSize,
          entityType);
    }

    // Add bonus drop entry to the map
    String mobFarmKey = getMobFarmKey(mobFarmType.getId(), tierLevel, entityType);
    log.debug(
        "{} Add {} with a chance of 1 of {} for {}.", LOG_PREFIX, mobFarmKey, chance, itemStack);

    mobFarmBonusMap
        .computeIfAbsent(mobFarmKey, k -> new ArrayList<>())
        .add(new BonusDrop(chance, itemStack));
  }

  public static List<ItemStack> getBonusDrop(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    return getBonusDrop(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static List<ItemStack> getBonusDrop(String mobFarmName, int tierLevel, String entityType) {
    List<ItemStack> drops = new ArrayList<>();
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return List.of();
    }
    for (BonusDrop drop : mobFarmBonusMap.get(getMobFarmKey(mobFarmName, tierLevel, entityType))) {
      if (random.nextInt(drop.chance()) == 0) {
        drops.add(drop.itemStack().copy());
      }
    }

    return drops;
  }

  public static List<ItemStack> getBonusDropEntries(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    if (mobFarmType == null || entityType == null) {
      return List.of();
    }

    return getBonusDropEntries(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static List<BonusDrop> getConfiguredBonusDrops(
      MobFarmType mobFarmType, int tierLevel, EntityType<?> entityType) {
    if (mobFarmType == null || entityType == null) {
      return List.of();
    }

    return getConfiguredBonusDrops(
        mobFarmType.getId(),
        tierLevel,
        String.valueOf(BuiltInRegistries.ENTITY_TYPE.getKey(entityType)));
  }

  public static List<BonusDrop> getConfiguredBonusDrops(
      String mobFarmName, int tierLevel, String entityType) {
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return List.of();
    }
    return List.copyOf(mobFarmBonusMap.get(getMobFarmKey(mobFarmName, tierLevel, entityType)));
  }

  public static List<MobFarmType> getMobFarmTypesWithBonusDrop(EntityType<?> entityType) {
    if (entityType == null) {
      return List.of();
    }

    String mobFarmKeySuffix = "::" + BuiltInRegistries.ENTITY_TYPE.getKey(entityType);
    List<MobFarmType> mobFarmTypes = new ArrayList<>();
    for (MobFarmType mobFarmType : MobFarmType.values()) {
      String mobFarmKeyPrefix = mobFarmType.getId() + "::";
      boolean hasBonusDrop =
          mobFarmBonusMap.keySet().stream()
              .anyMatch(
                  mobFarmKey ->
                      mobFarmKey.startsWith(mobFarmKeyPrefix)
                          && mobFarmKey.endsWith(mobFarmKeySuffix));
      if (hasBonusDrop) {
        mobFarmTypes.add(mobFarmType);
      }
    }
    return mobFarmTypes;
  }

  public static List<ItemStack> getBonusDropEntries(
      String mobFarmName, int tierLevel, String entityType) {
    if (!hasBonusDrop(mobFarmName, tierLevel, entityType)) {
      return List.of();
    }
    return mobFarmBonusMap.get(getMobFarmKey(mobFarmName, tierLevel, entityType)).stream()
        .map(drop -> drop.itemStack().copy())
        .toList();
  }

  public static boolean hasBonusDrop(String mobFarmName, int tierLevel, String entityType) {
    return mobFarmBonusMap.containsKey(getMobFarmKey(mobFarmName, tierLevel, entityType));
  }

  private static String[] parseKey(String key) {
    String[] keyParts = key.split("\\s*::\\s*");
    if (keyParts.length != 3) {
      log.error("Invalid key format in config file: {}", key);
      return null;
    }
    return keyParts;
  }

  private static String[] parseValue(String value) {
    String[] valueParts = value.split("\\s*::\\s*");
    if (valueParts.length != 3) {
      log.error("Invalid value format in config file: {}", value);
      return null;
    }
    return valueParts;
  }

  public record BonusDrop(int chance, ItemStack itemStack) {}
}
