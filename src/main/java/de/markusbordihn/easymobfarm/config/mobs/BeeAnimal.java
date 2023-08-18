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

package de.markusbordihn.easymobfarm.config.mobs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeeAnimal {

  protected BeeAnimal() {}

  public static final Set<String> Normal = new HashSet<>(Arrays.asList(
  // @formatter:off
    BeeAnimal.BEE
  // @formatter:on
  ));

  public static final Set<String> ProductiveBees = new HashSet<>(Arrays.asList(
  // @formatter:off
    BeeAnimal.AGATE_BEE,
    BeeAnimal.AIR_CRYSTAL_BEE,
    BeeAnimal.ALEXANDRITE_BEE,
    BeeAnimal.ALFSTEEL_BEE,
    BeeAnimal.ALLTHEMODIUM_BEE,
    BeeAnimal.ALUMINUM_BEE,
    BeeAnimal.AMBER_BEE,
    BeeAnimal.AMETHYST_BEE,
    BeeAnimal.AMETHYST_BRONZE_BEE,
    BeeAnimal.AMETRINE_BEE,
    BeeAnimal.AMMOLITE_BEE,
    BeeAnimal.APATITE_BEE,
    BeeAnimal.AQUAMARINE_BEE,
    BeeAnimal.ARCANE_BEE,
    BeeAnimal.ASHY_MINING_BEE,
    BeeAnimal.AWAKENED_BEE,
    BeeAnimal.AWAKENED_SUPREMIUM_BEE,
    BeeAnimal.BASALZ_BEE,
    BeeAnimal.BENITOITE_BEE,
    BeeAnimal.BISMUTH_BEE,
    BeeAnimal.BLACK_DIAMOND_BEE,
    BeeAnimal.BLACK_OPAL_BEE,
    BeeAnimal.BLAZING_BEE,
    BeeAnimal.BLITZ_BEE,
    BeeAnimal.BLIZZ_BEE,
    BeeAnimal.BLOODY_BEE,
    BeeAnimal.BLUE_BANDED_BEE,
    BeeAnimal.BRASS_BEE,
    BeeAnimal.BRONZE_BEE,
    BeeAnimal.BROWN_SHROOM_BEE,
    BeeAnimal.BUMBLE,
    BeeAnimal.BUMBLE_BEE,
    BeeAnimal.CALORITE_BEE,
    BeeAnimal.CARNELIAN_BEE,
    BeeAnimal.CATS_EYE_BEE,
    BeeAnimal.CHOCOLATE_BEE,
    BeeAnimal.CHOCOLATE_MINING_BEE,
    BeeAnimal.CHRYSOPRASE_BEE,
    BeeAnimal.CINNABAR_BEE,
    BeeAnimal.CITRINE_BEE,
    BeeAnimal.COAL_BEE,
    BeeAnimal.COBALT_BEE,
    BeeAnimal.COLLECTOR_BEE,
    BeeAnimal.COMMON_SALVAGE_BEE,
    BeeAnimal.COMPRESSED_IRON_BEE,
    BeeAnimal.CONFIGURABLE_BEE,
    BeeAnimal.CONSTANTAN_BEE,
    BeeAnimal.COPPER_BEE,
    BeeAnimal.CORAL_BEE,
    BeeAnimal.COSMIC_DUST_BEE,
    BeeAnimal.CREEPER_BEE,
    BeeAnimal.CRIMSON_BEE,
    BeeAnimal.CRYSTALLINE_BEE,
    BeeAnimal.CUPID_BEE,
    BeeAnimal.DARK_GEM_BEE,
    BeeAnimal.DESH_BEE,
    BeeAnimal.DESTABILIZED_REDSTONE_BEE,
    BeeAnimal.DIAMOND_BEE,
    BeeAnimal.DIGGER_BEE,
    BeeAnimal.DRACONIC_BEE,
    BeeAnimal.DRACONIUM_BEE,
    BeeAnimal.DYE_BEE,
    BeeAnimal.EARTH_CRYSTAL_BEE,
    BeeAnimal.ELECTRUM_BEE,
    BeeAnimal.ELEMENTIUM_BEE,
    BeeAnimal.EMERALDITE_BEE,
    BeeAnimal.EMERALD_BEE,
    BeeAnimal.ENDERIUM_BEE,
    BeeAnimal.ENDER_BEE,
    BeeAnimal.ENDER_SLIMY_BEE,
    BeeAnimal.END_GOBBER_BEE,
    BeeAnimal.ENERGIZED_GLOWSTONE_BEE,
    BeeAnimal.ENRICHED_IRON_BEE,
    BeeAnimal.EPIC_SALVAGE_BEE,
    BeeAnimal.EUCLASE_BEE,
    BeeAnimal.EXPERIENCE_BEE,
    BeeAnimal.FARMER_BEE,
    BeeAnimal.FEY_BEE,
    BeeAnimal.FIRE_CRYSTAL_BEE,
    BeeAnimal.FLUIX_BEE,
    BeeAnimal.FLUORITE_BEE,
    BeeAnimal.FROSTY_BEE,
    BeeAnimal.GARNET_BEE,
    BeeAnimal.GEODE_BEE,
    BeeAnimal.GHOSTLY_BEE,
    BeeAnimal.GLOWING_BEE,
    BeeAnimal.GOBBER_BEE,
    BeeAnimal.GOLD_BEE,
    BeeAnimal.GRAVE_BEE,
    BeeAnimal.GREEN_CARPENTER_BEE,
    BeeAnimal.GREEN_SAPPHIRE_BEE,
    BeeAnimal.HELIODOR_BEE,
    BeeAnimal.HEMATOPHAGOUS_BEE,
    BeeAnimal.HEPATIZON_BEE,
    BeeAnimal.HOARDER_BEE,
    BeeAnimal.ICHOR_SLIMY_BEE,
    BeeAnimal.IESNIUM_BEE,
    BeeAnimal.IMPERIUM_BEE,
    BeeAnimal.INERT_CRYSTAL_BEE,
    BeeAnimal.INFERIUM_BEE,
    BeeAnimal.INSANIUM_BEE,
    BeeAnimal.INVAR_BEE,
    BeeAnimal.IOLITE_BEE,
    BeeAnimal.IRIDIUM_BEE,
    BeeAnimal.IRON_BEE,
    BeeAnimal.JADE_BEE,
    BeeAnimal.JASPER_BEE,
    BeeAnimal.KAMIKAZ_BEE,
    BeeAnimal.KNIGHTSLIME_BEE,
    BeeAnimal.KUNZITE_BEE,
    BeeAnimal.KYANITE_BEE,
    BeeAnimal.LAPIS_BEE,
    BeeAnimal.LEAD_BEE,
    BeeAnimal.LEAFCUTTER_BEE,
    BeeAnimal.LEGENDARY_SALVAGE_BEE,
    BeeAnimal.LEPIDOLITE_BEE,
    BeeAnimal.LUMBER_BEE,
    BeeAnimal.LUMIUM_BEE,
    BeeAnimal.MAGMATIC_BEE,
    BeeAnimal.MALACHITE_BEE,
    BeeAnimal.MANASTEEL_BEE,
    BeeAnimal.MANYULLYN_BEE,
    BeeAnimal.MASON_BEE,
    BeeAnimal.MENRIL_BEE,
    BeeAnimal.MOLDAVITE_BEE,
    BeeAnimal.MOONSTONE_BEE,
    BeeAnimal.MORGANITE_BEE,
    BeeAnimal.NEON_CUCKOO_BEE,
    BeeAnimal.NETHERITE_BEE,
    BeeAnimal.NETHER_GOBBER_BEE,
    BeeAnimal.NICKEL_BEE,
    BeeAnimal.NITER_BEE,
    BeeAnimal.NOMAD_BEE,
    BeeAnimal.OBSIDIAN_BEE,
    BeeAnimal.OILY_BEE,
    BeeAnimal.ONYX_BEE,
    BeeAnimal.OPAL_BEE,
    BeeAnimal.OSMIUM_BEE,
    BeeAnimal.OSTRUM_BEE,
    BeeAnimal.PATRICK_BEE,
    BeeAnimal.PEARL_BEE,
    BeeAnimal.PENDORITE_BEE,
    BeeAnimal.PEPTO_BISMOL_BEE,
    BeeAnimal.PERIDOT_BEE,
    BeeAnimal.PHOSPHOPHYLLITE_BEE,
    BeeAnimal.PIG_IRON_BEE,
    BeeAnimal.PINK_SLIMY_BEE,
    BeeAnimal.PLASTIC_BEE,
    BeeAnimal.PLATINUM_BEE,
    BeeAnimal.PRISMARINE_BEE,
    BeeAnimal.PROSPERITY_BEE,
    BeeAnimal.PRUDENTIUM_BEE,
    BeeAnimal.PURE_BEE,
    BeeAnimal.PYROPE_BEE,
    BeeAnimal.QUARRY_BEE,
    BeeAnimal.QUEENS_SLIME_BEE,
    BeeAnimal.RADIOACTIVE_BEE,
    BeeAnimal.RANCHER_BEE,
    BeeAnimal.RARE_SALVAGE_BEE,
    BeeAnimal.REDSTONE_BEE,
    BeeAnimal.RED_SHROOM_BEE,
    BeeAnimal.REED_BEE,
    BeeAnimal.REFINED_GLOWSTONE_BEE,
    BeeAnimal.REFINED_OBSIDIAN_BEE,
    BeeAnimal.REGENERATIVE_BEE_BEE,
    BeeAnimal.RESIN_BEE,
    BeeAnimal.RESONANT_ENDER_BEE,
    BeeAnimal.ROCK_CRYSTAL_BEE,
    BeeAnimal.ROSE_GOLD_BEE,
    BeeAnimal.ROSE_QUARTZ_BEE,
    BeeAnimal.RUBY_BEE,
    BeeAnimal.SALTY_BEE,
    BeeAnimal.SAPPHIRE_BEE,
    BeeAnimal.SIGNALUM_BEE,
    BeeAnimal.SILICON_BEE,
    BeeAnimal.SILKY_BEE,
    BeeAnimal.SILVER_BEE,
    BeeAnimal.SKELETAL_BEE,
    BeeAnimal.SKY_SLIMY_BEE,
    BeeAnimal.SLIMESTEEL_BEE,
    BeeAnimal.SLIMY_BEE,
    BeeAnimal.SODALITE_BEE,
    BeeAnimal.SOULIUM_BEE,
    BeeAnimal.SOULSTEEL_BEE,
    BeeAnimal.SOUL_LAVA_BEE,
    BeeAnimal.SPACIAL_BEE,
    BeeAnimal.SPECTRUM_BEE,
    BeeAnimal.SPINEL_BEE,
    BeeAnimal.SPIRIT_BEE,
    BeeAnimal.STARMETAL_BEE,
    BeeAnimal.STARRY_BEE,
    BeeAnimal.STEEL_BEE,
    BeeAnimal.SUGARBAG_BEE,
    BeeAnimal.SULFUR_BEE,
    BeeAnimal.SUNSTONE_BEE,
    BeeAnimal.SUPREMIUM_BEE,
    BeeAnimal.SWEAT_BEE,
    BeeAnimal.TANZANITE_BEE,
    BeeAnimal.TEA_BEE,
    BeeAnimal.TEKTITE_BEE,
    BeeAnimal.TERRASTEEL_BEE,
    BeeAnimal.TERTIUM_BEE,
    BeeAnimal.TIN_BEE,
    BeeAnimal.TITANIUM_BEE,
    BeeAnimal.TOPAZ_BEE,
    BeeAnimal.TOURMALINE_BEE,
    BeeAnimal.TUNGSTEN_BEE,
    BeeAnimal.TURQUOISE_BEE,
    BeeAnimal.UNCOMMON_SALVAGE_BEE,
    BeeAnimal.UNOBTAINIUM_BEE,
    BeeAnimal.URANINITE_BEE,
    BeeAnimal.VIBRANIUM_BEE,
    BeeAnimal.WARPED_BEE,
    BeeAnimal.WASTED_RADIOACTIVE_BEE,
    BeeAnimal.WATER_CRYSTAL_BEE,
    BeeAnimal.WHITE_DIAMOND_BEE,
    BeeAnimal.WITHERED_BEE,
    BeeAnimal.YELLOW_BLACK_CARPENTER_BEE,
    BeeAnimal.ZINC_BEE,
    BeeAnimal.ZIRCON_BEE,
    BeeAnimal.ZOMBIE_BEE
  // @formatter:on
  ));

  public static final Set<String> ProductiveBeesLootable = new HashSet<>(Arrays.asList(
  // @formatter:off
    BeeAnimal.AMETHYST_BEE,
    BeeAnimal.AMETRINE_BEE,
    BeeAnimal.BLAZING_BEE,
    BeeAnimal.BRASS_BEE,
    BeeAnimal.BROWN_SHROOM_BEE,
    BeeAnimal.CHOCOLATE_MINING_BEE,
    BeeAnimal.COAL_BEE,
    BeeAnimal.CONFIGURABLE_BEE,
    BeeAnimal.COPPER_BEE,
    BeeAnimal.CREEPER_BEE,
    BeeAnimal.CRYSTALLINE_BEE,
    BeeAnimal.DIAMOND_BEE,
    BeeAnimal.DRACONIC_BEE,
    BeeAnimal.ELEMENTIUM_BEE,
    BeeAnimal.EMERALD_BEE,
    BeeAnimal.ENDER_BEE,
    BeeAnimal.EXPERIENCE_BEE,
    BeeAnimal.FROSTY_BEE,
    BeeAnimal.GHOSTLY_BEE,
    BeeAnimal.GLOWING_BEE,
    BeeAnimal.GOLD_BEE,
    BeeAnimal.IRON_BEE,
    BeeAnimal.LAPIS_BEE,
    BeeAnimal.MAGMATIC_BEE,
    BeeAnimal.OBSIDIAN_BEE,
    BeeAnimal.PINK_SLIMY_BEE,
    BeeAnimal.PLASTIC_BEE,
    BeeAnimal.PRISMARINE_BEE,
    BeeAnimal.RADIOACTIVE_BEE,
    BeeAnimal.RANCHER_BEE,
    BeeAnimal.REDSTONE_BEE,
    BeeAnimal.RED_SHROOM_BEE,
    BeeAnimal.SALTY_BEE,
    BeeAnimal.SILKY_BEE,
    BeeAnimal.SKELETAL_BEE,
    BeeAnimal.SLIMY_BEE,
    BeeAnimal.SPAWN_EGG_CONFIGURABLE_BEE,
    BeeAnimal.STEEL_BEE,
    BeeAnimal.TEA_BEE,
    BeeAnimal.TERRASTEEL_BEE,
    BeeAnimal.WITHERED_BEE,
    BeeAnimal.ZINC_BEE,
    BeeAnimal.ZOMBIE_BEE
  // @formatter:on
  ));

  public static final Set<String> All =
      Stream.concat(Normal.stream(), ProductiveBees.stream()).collect(Collectors.toSet());

  public static final Set<String> AllLootable =
      Stream.concat(Normal.stream(), ProductiveBeesLootable.stream()).collect(Collectors.toSet());

  public static final String BEE = "minecraft:bee";

  public static final String AGATE_BEE = "productivebees:agate_bee";
  public static final String AIR_CRYSTAL_BEE = "productivebees:air_crystal_bee";
  public static final String ALEXANDRITE_BEE = "productivebees:alexandrite_bee";
  public static final String ALFSTEEL_BEE = "productivebees:alfsteel_bee";
  public static final String ALLTHEMODIUM_BEE = "productivebees:allthemodium_bee";
  public static final String ALUMINUM_BEE = "productivebees:aluminum_bee";
  public static final String AMBER_BEE = "productivebees:amber_bee";
  public static final String AMETHYST_BEE = "productivebees:amethyst_bee";
  public static final String AMETHYST_BRONZE_BEE = "productivebees:amethyst_bronze_bee";
  public static final String AMETRINE_BEE = "productivebees:ametrine_bee";
  public static final String AMMOLITE_BEE = "productivebees:ammolite_bee";
  public static final String APATITE_BEE = "productivebees:apatite_bee";
  public static final String AQUAMARINE_BEE = "productivebees:aquamarine_bee";
  public static final String ARCANE_BEE = "productivebees:arcane_bee";
  public static final String ASHY_MINING_BEE = "productivebees:ashy_mining_bee";
  public static final String AWAKENED_BEE = "productivebees:awakened_bee";
  public static final String AWAKENED_SUPREMIUM_BEE = "productivebees:awakened_supremium_bee";
  public static final String BASALZ_BEE = "productivebees:basalz_bee";
  public static final String BENITOITE_BEE = "productivebees:benitoite_bee";
  public static final String BISMUTH_BEE = "productivebees:bismuth_bee";
  public static final String BLACK_DIAMOND_BEE = "productivebees:black_diamond_bee";
  public static final String BLACK_OPAL_BEE = "productivebees:black_opal_bee";
  public static final String BLAZING_BEE = "productivebees:blazing_bee";
  public static final String BLITZ_BEE = "productivebees:blitz_bee";
  public static final String BLIZZ_BEE = "productivebees:blizz_bee";
  public static final String BLOODY_BEE = "productivebees:bloody_bee";
  public static final String BLUE_BANDED_BEE = "productivebees:blue_banded_bee";
  public static final String BRASS_BEE = "productivebees:brass_bee";
  public static final String BRONZE_BEE = "productivebees:bronze_bee";
  public static final String BROWN_SHROOM_BEE = "productivebees:brown_shroom_bee";
  public static final String BUMBLE = "productivebees:bumble_bee";
  public static final String BUMBLE_BEE = "productivebees:bumble_bee";
  public static final String CALORITE_BEE = "productivebees:calorite_bee";
  public static final String CARNELIAN_BEE = "productivebees:carnelian_bee";
  public static final String CATS_EYE_BEE = "productivebees:cats_eye_bee";
  public static final String CHOCOLATE_BEE = "productivebees:chocolate_bee";
  public static final String CHOCOLATE_MINING_BEE = "productivebees:chocolate_mining_bee";
  public static final String CHRYSOPRASE_BEE = "productivebees:chrysoprase_bee";
  public static final String CINNABAR_BEE = "productivebees:cinnabar_bee";
  public static final String CITRINE_BEE = "productivebees:citrine_bee";
  public static final String COAL_BEE = "productivebees:coal_bee";
  public static final String COBALT_BEE = "productivebees:cobalt_bee";
  public static final String COLLECTOR_BEE = "productivebees:collector_bee";
  public static final String COMMON_SALVAGE_BEE = "productivebees:common_salvage_bee";
  public static final String COMPRESSED_IRON_BEE = "productivebees:compressed_iron_bee";
  public static final String CONFIGURABLE_BEE = "productivebees:configurable_bee";
  public static final String CONSTANTAN_BEE = "productivebees:constantan_bee";
  public static final String COPPER_BEE = "productivebees:copper_bee";
  public static final String CORAL_BEE = "productivebees:coral_bee";
  public static final String COSMIC_DUST_BEE = "productivebees:cosmic_dust_bee";
  public static final String CREEPER_BEE = "productivebees:creeper_bee";
  public static final String CRIMSON_BEE = "productivebees:crimson_bee";
  public static final String CRYSTALLINE_BEE = "productivebees:crystalline_bee";
  public static final String CUPID_BEE = "productivebees:cupid_bee";
  public static final String DARK_GEM_BEE = "productivebees:dark_gem_bee";
  public static final String DESH_BEE = "productivebees:desh_bee";
  public static final String DESTABILIZED_REDSTONE_BEE = "productivebees:destabilized_redstone_bee";
  public static final String DIAMOND_BEE = "productivebees:diamond_bee";
  public static final String DIGGER_BEE = "productivebees:digger_bee";
  public static final String DRACONIC_BEE = "productivebees:draconic_bee";
  public static final String DRACONIUM_BEE = "productivebees:draconium_bee";
  public static final String DYE_BEE = "productivebees:dye_bee";
  public static final String EARTH_CRYSTAL_BEE = "productivebees:earth_crystal_bee";
  public static final String ELECTRUM_BEE = "productivebees:electrum_bee";
  public static final String ELEMENTIUM_BEE = "productivebees:elementium_bee";
  public static final String EMERALDITE_BEE = "productivebees:emeraldite_bee";
  public static final String EMERALD_BEE = "productivebees:emerald_bee";
  public static final String ENDERIUM_BEE = "productivebees:enderium_bee";
  public static final String ENDER_BEE = "productivebees:ender_bee";
  public static final String ENDER_SLIMY_BEE = "productivebees:ender_slimy_bee";
  public static final String END_GOBBER_BEE = "productivebees:end_gobber_bee";
  public static final String ENERGIZED_GLOWSTONE_BEE = "productivebees:energized_glowstone_bee";
  public static final String ENRICHED_IRON_BEE = "productivebees:quartz_enriched_iron_bee";
  public static final String EPIC_SALVAGE_BEE = "productivebees:epic_salvage_bee";
  public static final String EUCLASE_BEE = "productivebees:euclase_bee";
  public static final String EXPERIENCE_BEE = "productivebees:experience_bee";
  public static final String FARMER_BEE = "productivebees:farmer_bee";
  public static final String FEY_BEE = "productivebees:fey_bee";
  public static final String FIRE_CRYSTAL_BEE = "productivebees:fire_crystal_bee";
  public static final String FLUIX_BEE = "productivebees:fluix_bee";
  public static final String FLUORITE_BEE = "productivebees:fluorite_bee";
  public static final String FROSTY_BEE = "productivebees:frosty_bee";
  public static final String GARNET_BEE = "productivebees:garnet_bee";
  public static final String GEODE_BEE = "productivebees:geode_bee";
  public static final String GHOSTLY_BEE = "productivebees:ghostly_bee";
  public static final String GLOWING_BEE = "productivebees:glowing_bee";
  public static final String GOBBER_BEE = "productivebees:gobber_bee";
  public static final String GOLD_BEE = "productivebees:gold_bee";
  public static final String GRAVE_BEE = "productivebees:grave_bee";
  public static final String GREEN_CARPENTER_BEE = "productivebees:green_carpenter_bee";
  public static final String GREEN_SAPPHIRE_BEE = "productivebees:green_sapphire_bee";
  public static final String HELIODOR_BEE = "productivebees:heliodor_bee";
  public static final String HEMATOPHAGOUS_BEE = "productivebees:hematophagous_bee";
  public static final String HEPATIZON_BEE = "productivebees:hepatizon_bee";
  public static final String HOARDER_BEE = "productivebees:hoarder_bee";
  public static final String ICHOR_SLIMY_BEE = "productivebees:ichor_slimy_bee";
  public static final String IESNIUM_BEE = "productivebees:iesnium_bee";
  public static final String IMPERIUM_BEE = "productivebees:imperium_bee";
  public static final String INERT_CRYSTAL_BEE = "productivebees:inert_crystal_bee";
  public static final String INFERIUM_BEE = "productivebees:inferium_bee";
  public static final String INSANIUM_BEE = "productivebees:insanium_bee";
  public static final String INVAR_BEE = "productivebees:invar_bee";
  public static final String IOLITE_BEE = "productivebees:iolite_bee";
  public static final String IRIDIUM_BEE = "productivebees:iridium_bee";
  public static final String IRON_BEE = "productivebees:iron_bee";
  public static final String JADE_BEE = "productivebees:jade_bee";
  public static final String JASPER_BEE = "productivebees:jasper_bee";
  public static final String KAMIKAZ_BEE = "productivebees:kamikaz_bee";
  public static final String KNIGHTSLIME_BEE = "productivebees:knightslime_bee";
  public static final String KUNZITE_BEE = "productivebees:kunzite_bee";
  public static final String KYANITE_BEE = "productivebees:kyanite_bee";
  public static final String LAPIS_BEE = "productivebees:lapis_bee";
  public static final String LEAD_BEE = "productivebees:lead_bee";
  public static final String LEAFCUTTER_BEE = "productivebees:leafcutter_bee";
  public static final String LEGENDARY_SALVAGE_BEE = "productivebees:legendary_salvage_bee";
  public static final String LEPIDOLITE_BEE = "productivebees:lepidolite_bee";
  public static final String LUMBER_BEE = "productivebees:lumber_bee";
  public static final String LUMIUM_BEE = "productivebees:lumium_bee";
  public static final String MAGMATIC_BEE = "productivebees:magmatic_bee";
  public static final String MALACHITE_BEE = "productivebees:malachite_bee";
  public static final String MANASTEEL_BEE = "productivebees:manasteel_bee";
  public static final String MANYULLYN_BEE = "productivebees:manyullyn_bee";
  public static final String MASON_BEE = "productivebees:mason_bee";
  public static final String MENRIL_BEE = "productivebees:menril_bee";
  public static final String MOLDAVITE_BEE = "productivebees:moldavite_bee";
  public static final String MOONSTONE_BEE = "productivebees:moonstone_bee";
  public static final String MORGANITE_BEE = "productivebees:morganite_bee";
  public static final String NEON_CUCKOO_BEE = "productivebees:neon_cuckoo_bee";
  public static final String NETHERITE_BEE = "productivebees:netherite_bee";
  public static final String NETHER_GOBBER_BEE = "productivebees:nether_gobber_bee";
  public static final String NICKEL_BEE = "productivebees:nickel_bee";
  public static final String NITER_BEE = "productivebees:niter_bee";
  public static final String NOMAD_BEE = "productivebees:nomad_bee";
  public static final String OBSIDIAN_BEE = "productivebees:obsidian_bee";
  public static final String OILY_BEE = "productivebees:oily_bee";
  public static final String ONYX_BEE = "productivebees:onyx_bee";
  public static final String OPAL_BEE = "productivebees:opal_bee";
  public static final String OSMIUM_BEE = "productivebees:osmium_bee";
  public static final String OSTRUM_BEE = "productivebees:ostrum_bee";
  public static final String PATRICK_BEE = "productivebees:patrick_bee";
  public static final String PEARL_BEE = "productivebees:pearl_bee";
  public static final String PENDORITE_BEE = "productivebees:pendorite_bee";
  public static final String PEPTO_BISMOL_BEE = "productivebees:pepto_bismol_bee";
  public static final String PERIDOT_BEE = "productivebees:peridot_bee";
  public static final String PHOSPHOPHYLLITE_BEE = "productivebees:phosphophyllite_bee";
  public static final String PIG_IRON_BEE = "productivebees:pig_iron_bee";
  public static final String PINK_SLIMY_BEE = "productivebees:pink_slimy_bee";
  public static final String PLASTIC_BEE = "productivebees:plastic_bee";
  public static final String PLATINUM_BEE = "productivebees:platinum_bee";
  public static final String PRISMARINE_BEE = "productivebees:prismarine_bee";
  public static final String PRODUCTIVE_BEES_CONFIGURABLE = "productivebees:configurable_bee";
  public static final String PROSPERITY_BEE = "productivebees:prosperity_bee";
  public static final String PRUDENTIUM_BEE = "productivebees:prudentium_bee";
  public static final String PURE_BEE = "productivebees:pure_bee";
  public static final String PYROPE_BEE = "productivebees:pyrope_bee";
  public static final String QUARRY_BEE = "productivebees:quarry_bee";
  public static final String QUEENS_SLIME_BEE = "productivebees:queens_slime_bee";
  public static final String RADIOACTIVE_BEE = "productivebees:radioactive_bee";
  public static final String RANCHER_BEE = "productivebees:rancher_bee";
  public static final String RARE_SALVAGE_BEE = "productivebees:rare_salvage_bee";
  public static final String REDSTONE_BEE = "productivebees:redstone_bee";
  public static final String RED_SHROOM_BEE = "productivebees:red_shroom_bee";
  public static final String REED_BEE = "productivebees:reed_bee";
  public static final String REFINED_GLOWSTONE_BEE = "productivebees:refined_glowstone_bee";
  public static final String REFINED_OBSIDIAN_BEE = "productivebees:refined_obsidian_bee";
  public static final String REGENERATIVE_BEE_BEE = "productivebees:regenerative_bee";
  public static final String RESIN_BEE = "productivebees:resin_bee";
  public static final String RESONANT_ENDER_BEE = "productivebees:resonant_ender_bee";
  public static final String ROCK_CRYSTAL_BEE = "productivebees:rock_crystal_bee";
  public static final String ROSE_GOLD_BEE = "productivebees:rose_gold_bee";
  public static final String ROSE_QUARTZ_BEE = "productivebees:rose_quartz_bee";
  public static final String RUBY_BEE = "productivebees:ruby_bee";
  public static final String SALTY_BEE = "productivebees:salty_bee";
  public static final String SAPPHIRE_BEE = "productivebees:sapphire_bee";
  public static final String SIGNALUM_BEE = "productivebees:signalum_bee";
  public static final String SILICON_BEE = "productivebees:silicon_bee";
  public static final String SILKY_BEE = "productivebees:silky_bee";
  public static final String SILVER_BEE = "productivebees:silver_bee";
  public static final String SKELETAL_BEE = "productivebees:skeletal_bee";
  public static final String SKY_SLIMY_BEE = "productivebees:sky_slimy_bee";
  public static final String SLIMESTEEL_BEE = "productivebees:slimesteel_bee";
  public static final String SLIMY_BEE = "productivebees:slimy_bee";
  public static final String SODALITE_BEE = "productivebees:sodalite_bee";
  public static final String SOULIUM_BEE = "productivebees:soulium_bee";
  public static final String SOULSTEEL_BEE = "productivebees:soulsteel_bee";
  public static final String SOUL_LAVA_BEE = "productivebees:soul_lava_bee";
  public static final String SPACIAL_BEE = "productivebees:spacial_bee";
  public static final String SPAWN_EGG_CONFIGURABLE_BEE = "productivebees:spawn_egg_configurable_bee";
  public static final String SPECTRUM_BEE = "productivebees:spectrum_bee";
  public static final String SPINEL_BEE = "productivebees:spinel_bee";
  public static final String SPIRIT_BEE = "productivebees:spirit_bee";
  public static final String STARMETAL_BEE = "productivebees:starmetal_bee";
  public static final String STARRY_BEE = "productivebees:starry_bee";
  public static final String STEEL_BEE = "productivebees:steel_bee";
  public static final String SUGARBAG_BEE = "productivebees:sugarbag_bee";
  public static final String SULFUR_BEE = "productivebees:sulfur_bee";
  public static final String SUNSTONE_BEE = "productivebees:sunstone_bee";
  public static final String SUPREMIUM_BEE = "productivebees:supremium_bee";
  public static final String SWEAT_BEE = "productivebees:sweat_bee";
  public static final String TANZANITE_BEE = "productivebees:tanzanite_bee";
  public static final String TEA_BEE = "productivebees:tea_bee";
  public static final String TEKTITE_BEE = "productivebees:tektite_bee";
  public static final String TERRASTEEL_BEE = "productivebees:terrasteel_bee";
  public static final String TERTIUM_BEE = "productivebees:tertium_bee";
  public static final String TIN_BEE = "productivebees:tin_bee";
  public static final String TITANIUM_BEE = "productivebees:titanium_bee";
  public static final String TOPAZ_BEE = "productivebees:topaz_bee";
  public static final String TOURMALINE_BEE = "productivebees:tourmaline_bee";
  public static final String TUNGSTEN_BEE = "productivebees:tungsten_bee";
  public static final String TURQUOISE_BEE = "productivebees:turquoise_bee";
  public static final String UNCOMMON_SALVAGE_BEE = "productivebees:uncommon_salvage_bee";
  public static final String UNOBTAINIUM_BEE = "productivebees:unobtainium_bee";
  public static final String URANINITE_BEE = "productivebees:uraninite_bee";
  public static final String VIBRANIUM_BEE = "productivebees:vibranium_bee";
  public static final String WARPED_BEE = "productivebees:warped_bee";
  public static final String WASTED_RADIOACTIVE_BEE = "productivebees:wasted_radioactive_bee";
  public static final String WATER_CRYSTAL_BEE = "productivebees:water_crystal_bee";
  public static final String WHITE_DIAMOND_BEE = "productivebees:white_diamond_bee";
  public static final String WITHERED_BEE = "productivebees:withered_bee";
  public static final String YELLOW_BLACK_CARPENTER_BEE = "productivebees:yellow_black_carpenter_bee";
  public static final String ZINC_BEE = "productivebees:zinc_bee";
  public static final String ZIRCON_BEE = "productivebees:zircon_bee";
  public static final String ZOMBIE_BEE = "productivebees:zombie_bee";

}
