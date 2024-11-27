# Changelog for Easy Mob Farm

All notable changes to this project will be documented in this file.

# v8.6.0

- Fixed #86 by adding Fabric NBT support for mob farm recipes.
- Fixed #85 by adding correct quickMoveStack definition for the mob farm.
- Fixed #84 by allowing drops regardless of mob size, but the correct mob size will increase the
  drop rate.
- Fixed #81 by adding void binding chain for capturing very large mobs.
- Fixed #79 by adding configurable mob farm bonus drops for matching mobs e.g. bee farm for bees.
- Fixed Mob Catcher tooltips for Fabric.
- Fixed smaller translation issues.
- Fixed broken advancements.
- Added magma cube custom mob cards.
- Added visible size category for slimes and magma cubes.
- Added foil effect for mob capture items and captured mobs.
- Added new Iron Golem Farm.
- Added new Lucky Drop Farm (be careful with this one).
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
