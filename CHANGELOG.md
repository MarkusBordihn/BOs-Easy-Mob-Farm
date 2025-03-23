# Changelog for Easy Mob Farm

All notable changes to this project will be documented in this file.

# v9.6.1

- Fixed #147 by changing loading order of configuration files.
- Added additional pre-check for Mob Farm Bonus configuration.

# v9.6.0

- Fixed #135 by adding frog light extractor Enhancement.
- Fixed #113 by adding known requires "killed_by_player" entities as tooltip.
- Added Korean translation. Thanks to `@thecats1105` for the contribution.
- Added different frog cards for cold, temperate and warm variants.
- Smaller bug fixes and improvements.

# v9.5.0

- Fixed #137 by adjusting crafting recipe to use `MobFarmData` instead of
  `DataComponents.CUSTOM_DATA`.

# v9.4.0

- Fixed #134 by adding JEI support for virtual items and blocks.
- Fixed #133 by adding virtual Mob Farm Block Tier Items for creative tab.
- Fixed #132 by using vanilla style rotation, scaling and default models for items.
- Fixed #130 by adding support for Farmer's Delight `Milk Bottle`.
- Fixed #128 by making luck for Lucky Drop Farm configurable over `luckyDropFarmLuckPercentage`.
- Added Japanese translation. Thanks to `@twister716` for the contribution.

# v9.3.0

- Fixed #123 by adding milk extractor enhancement.
- Fixed #122 by adding configuration for processing mob farms only if owner is online.
- Added Milk Bottle item to allow players to get milk from cows.
- Added Milk Bottle to Milk Bucket recipe (4 milk bottles + 1 empty bucket = 1 milk bucket).
- Added Owner information to mob farm screen.
- Smaller bug fixes and improvements.

# v8.11.0

- Added possibility to disable Mob Catcher recipes per configuration.
- Updated wiki and documentation.

# v8.10.0

- Fixed #114 by adjusting cull-faces to make sure that sides are rendered correctly with shaders.
- Fixed #111 by fixing typo with block registry name.
- Fixed #110 by adding additional checks to avoid crashes on server side.
- Fixed #107 by changing the way entities are downscaled in the mob farm and mob farm screen.
- Fixed #106 by adding additional test to make sure breaking the mob farm works as expected.
- Fixed #83 by reducing the encapsulation of translated text components between server and client.
- Fixed Monster Plains Cave Farm for Fabric.
- Fixed unbreakable issues with Iron Golem Farm and Lucky Drop Farm.
- Fixed mob farm block name for Jade and similar mods.
- Added additional automatic tests for mob farm blocks, items and loot tables.
- Cleanup assets and models.
- Smaller performance improvements and optimizations.

# v8.9.1

- Fixed #105 by correcting .isCreative() condition.

# v8.9.0

- Fixed #101 by making sure tooltips and wiki are up to date.
- Fixed #100 by making enhancements stackable and consider instant build.
- Fixed #99 by improve wiki and documentation and adding human-readable mob farm status.
- Fixed #98 by adding allow and deny list for the Mob Catcher Items.
- Fixed #96 by adding squid, warden, wither and zombified piglin custom mob cards.
- Fixed issue with `toLowerCase` and `toUpperCase` language sensitive operations.
- Fixed loot tables for mob farm templates, so that they could be pickup after breaking.
- Added configuration file for the Mob Catcher items to adjust max durability, health and size
  requirements.
- Added additional user messages in the case mob are not on the allow list or on the deny list.
- Added bonus drop overview to the mob farm screen.
- Optimized Mob Capture Card meta-data for better performance and stackability.

# v8.8.0

- Fixed #95 by adding allow and deny list for the Mob Capture Cards.
- Improved config file list support.

# v8.7.0

- Fixed #92 by using List instead of Set for mob farm enhancements.
- Added speed and bonus speed to Mob Farm Screen info for better usability.

# v8.6.4

- Fixed #94 by adding NeoForge 'Capabilities.ItemHandler.BLOCK' to the mob farms.
- Fixed issue with Forge only allowing extraction from the bottom.

# v8.6.3

- Fixed #90 by using correct tier reference.
- Added automatic game tests for mob farm tiers to catch issues early.

# v8.6.1

- Fixed #88 by making sure ItemStack size is considered for Create Funnels.
- Fixed #87 by adding egg collector upgrade for chicken.
- Fixed #83 by translating first the name instead of relying on the translation library.
- Added bonus drops for eggs and chicken in the animal plains farm.

# v8.6.0

- Fixed #86 by adding Fabric NBT support for mob farm recipes.
- Fixed #85 by adding correct quickMoveStack definition for the mob farm.
- Fixed #84 by allowing drops regardless of mob size, but the correct mob size will increase the
  drop rate.
- Fixed #81 by adding void binding chain for capturing very large mobs.
- Fixed #79 by adding configurable mob farm bonus drops for matching mobs e.g. bee farm for bees.
- Fixed possible null pointer in Mob Farm Screen.
- Fixed Mob Catcher tooltips for Fabric.
- Fixed smaller translation issues.
- Fixed broken advancements.
- Added magma cube, evoker, frog, glow_squid, piglin, piglin_brute, rabbit and turtle custom mob
  cards.
- Added visible size category for slimes and magma cubes.
- Added foil effect for mob capture items and captured mobs.
- Added new Iron Golem Farm.
- Added new Lucky Drop Farm (be careful with this one).
- Improved Mob Farm UI.
- Improved Lucky Drop Farm model.
- Improved captured mob experience detection and caching.
- Improved entity scaling for captured mobs.

A big thank you to `@Routhinator` for the detailed bug reports and valuable feedback!

# v8.5.1

- Fixed #80 crash by adding additional checks for mob capture cards.
- Fixed issues with custom spawn eggs not recognized by the mob farm in some cases.
- Optimized model baking process and model management.

# v8.5.0

- Fixed #78 by using more universal approach for getting relevant experience.
- Fixed #77 typo in the experience enhancement item description.
- Fixed #76 by adding Honey Extractor Enhancement, Harvester Frame Enhancement and Pollen Trap
  Enhancement for bee farms.
- Fixed #75 by making less restrictive requirements for experience drops.
- Fixed mob card model issue with other mods which manipulate / breaking the model baking process.
- Added entity_type and experience to mob farm info for better usability.
- Added bee, guardian and wither skeleton custom mob cards.
- Increased the drop change for the experience enhancement item.
- Improved Loot Manager for better handling of loot tables.

# v8.4.0

- Fixed #73 with better description for the Enchantment Items.
- Fixed #72 by adding missing loot tables for the mob farms.
- Fixed missing translation for the Monster Plains Cave Farm.
- Added automatic ejecting of items from the mob farm when breaking it.
- Improved automatic description text split for Fabric.
- Added Mob Farm Configuration to adjust the mob farm settings.
- Updated wiki and documentation.

# v8.3.0

- Fixed Mob Capture Card config not working.
- Fixed issue with distributed processing ticks are negative.
- Fixed Tabby Card Mob Card.
- Improved text formatting for Mob Cards.
- Smaller bug fixes and improvements.

# v8.2.0

- Fixed #70 and added support for the latest version of Minecraft.
- Fixed #68, #69 by adding enchantment items for experience bottle drops.
- Fixed #67 by moving most of the logging to debug mode.
- Fixed #65 by removing player specific messages.
- Fixed #63 by reworked mob capture data.
- Fixed #61 by providing specific enchantment items for the mob farm.
- Fixed #57 by allow item extraction from the bottom and sides.
- Fixed #55 by adding support for Supplementaries `Jar` and `Cage`.
- Fixed #53 by only requiring iron pickaxe for breaking the mob farm.
- Fixed #52 by adding sword enchantment item for player based drops.
- Fixed #50, #64 by remove any restrictions for the mob farm.
- Fixed #47 by adding slot upgrades for the output slots.
- Added additional mob capture cards.
- Added support for Create `Blaze Burner`.
- Added support for Mob Capturing Tool.
- Added support for Mob Catcher.
- Added support for Productive Bees `Bee Cage` and `Bee Jar`.
- Added support for Storage Drawers.
- Added Model Manager for easier model management across platforms.
- Distributed processing ticks for better performance and less lag.
- Improved text and translation support.
- Improved logging and move messages to debug mode only.

# v8.1.0

- Added debug mode to help with troubleshooting and lower logging outputs.
- Added Creative Mob Capture item to capture mobs in creative mode.
- Added Enduring Capture Net to capture mobs with a higher durability.
- Added Ironbound Containment Cage to capture mobs larger mobs.
- Added Mystic Binding Crystal to capture very large and boss mobs.
- Added wither specific drop like nether star and wither rose.
- Improved blank mob capture card texture.
- Improved translation and tooltip support.

# v8.0.0

First beta release of the next Easy Mob Farm 8.x for Fabric and Forge.

🚨 **Warning:** Easy Mob Farm version **8.x** introduces significant changes and **is NOT
backward-compatible** with previous versions. ⚠️

If you are upgrading from an older version, please be aware:

- 🛑 **Existing setups may break** and require reconfiguration.
- 🔄 **Data, items, and settings from previous versions** might not work as expected.
- 💾 **Back up** your world or test on a separate instance before using 8.x in your main game!
