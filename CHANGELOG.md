# Changelog for Easy Mob Farm

## Note

This change log includes the summarized changes.
For the full changelog, please go to the [Git Hub History][history] instead.

### 2023.10.23

Thanks a lot to cringoleg for all of the valuable and detailed bug reports. 👍

- Fixed unstackable item drops from mob farms #45
- Fixed Magma Slimes mot working with Creative or Netherite Mob Farms #44
- Added Experience Drop Config Options #43
- Added Slime and Magma Cube render with size support.
- Improved loot table and drop prediction for mob farms.
- Improved logging and removed some debug messages.

### 2023.10.20

- Fixed Allow dynamic filter options for Iron Golem Farm. #38
- Fixed Fishing Net (Small) catches all mobs #39
- Fixed Curseforge Client Changelog link is invalid  #40
- Fixed Advancements and Recipes don't work as expected #41
- Fixed Change naming convention for config variables #42

### 2023.10.18

- Added Iron Golem farm and mob catching item.
- Improved render performance by adding additional simplified entity models.
- Improved config file options.

### 2023.10.16

- Added support for multiple drops for looting enchantment.
- Added config option to force ender pearl drop from enderman.

### 2023.09.24

- Fixed wrong item drop from breaking monster plains cave farms.
- Added safety checks to avoid potential crashes with slow loaded chunks.

### 2023.09.13

- Added config option to enable nether star drop for the wither and redstone control. (Thanks x-j0nnay-x)
- Added enchantment support for mending, unbreaking, sharpness and looting.
- Improved import and export of items from/to slots for auto processing.

### 2023.08.22

- Improved startup performance and general memory usage.
- Adjusted mob catching luck and durability for new mob catching lassos to be more balanced with the rest of the mob catching items.

### 2023.08.19

Note: Please make sure to backup your world before updating to this version!
The version tries automatically to convert existing mob farms to the new mob farms.
It's recommended to backup your world before updating to make sure that nothing gets lost in the case of an error.

- Added deny list for mob farms to allow better customization.
- Moved documentation to wiki.
- Fixed smaller issues.

### 2023.08.16

- Added full support for [Productive Bees](https://www.curseforge.com/minecraft/mc-mods/productivebees) for the Bee Hive Farm.
- Added additional duplication checks for mob farms.
- Added German translation.
- Fixed issue with mixed mob farm tiers and limited mobs.
- Improved render performance for mob farm models.

### 2023.08.04

Note: Please make sure to backup your world before updating to this version!
In some cases you might need to remove your config file to get an updated version.

- Removed Material Elements from the mod dependencies!
- Added support for [Cyclic](https://www.curseforge.com/minecraft/mc-mods/cyclic) Monster Ball.
- Added basic support for [Alex's Mobs](https://www.curseforge.com/minecraft/mc-mods/alexs-mobs).
- Added cooper, iron, gold and netherite mob farms.
- Added golden lasso, ender lasso and netherite lasso as mob catcher items.
- Added support for remove mob limitation to catch and farm all mobs.
- Balanced mob farms and catching items to provide end content.
- Simplified mob farm recipes.

### 2023.06.23

- Refactored code to 1.20-46.0.14

### 2023.06.17

- Refactored code to 1.19.4-45.1.0

### 2023.06.11

- Added support for [Forbidden Arcanus](https://www.curseforge.com/minecraft/mc-mods/forbidden-arcanus) Quantum Catcher
- Improved Productive Bees support.

### 2023.06.08

- Replaced hard links to supported mods with custom links to allow better mod support.
- Added support for [MobCatcher](https://www.curseforge.com/minecraft/mc-mods/mob-catcher).
- Added support for sheared entities like sheep.
- Fixed issue with broken weapons in weapon slot.

### 2023.03.13

- Added config options `playDropSound` to general disable mob drop sounds.
- Added config options `...FarmDropSound` to define individual mob drop sound for each mob farm.

### 2023.02.04

- Refactored code to 1.19.3-44.1.8
- Added German translation.

### 2022.12.30

- Added `killed_by_player` loot drop support with sword item.
- Added possible to collect experience by placing a empty or experience bottle into the experience slot.
- Added additional animations and hits for the new weapon and experience slot.

### 2022.12.29

- Improved Mob Farm UI to allow better resource pack support and more space for next update.

### 2022.12.17

- Added insect net capture item.
- Added bee hive mob farm with experimental support for [Productive Bees](https://www.curseforge.com/minecraft/mc-mods/productivebees)
- Improved existing items.

### 2022.11.12

- Optimized all mob farm models and fixed visual glitches for better performance.
- Added experimental spawn egg support.
- Added experimental support for [Corail Spawners](https://www.curseforge.com/minecraft/mc-mods/corail-spawners).
- Fixed potential null pointer errors.

### 2022.11.06

- Added nether fortress farm
- Added experimental support for mob catching items from other mods
  - [Create](https://www.curseforge.com/minecraft/mc-mods/create): Blaze Burner
  - [Mob Catcher](https://www.curseforge.com/minecraft/mc-mods/mob-catcher-fabric)
  - [Mob Capturing Tool](https://www.curseforge.com/minecraft/mc-mods/mob-capturing-tool)
- Improved UI and UI elements
- Fixed smaller issues

### 2022.08.22

- Added Jungle Farm for jungle creatures.
- Added Catch Cage and Catch Cage small for additional mob support.
- Adjusted supported mobs per mob farm for better support.
- Simplify some recipes to avoid the need of silk touch.
- Fixed advancements and other smaller performance related stuff.

### 2022.05.10

- Added witch bottle for swamp farm.
- Moved accepted mob lists to the config file, to allow better customization.
- Optimized images and other models.

### 2022.05.09

- Added additional swamp farm for slimes and witches for #1.

### 2022.04.17

- Fixed spawning issue with flying / falling entities.
- Fixed missing translation keys.
- Added additional ambient water animals to the supported list.
- Updated references to the latest version.

### 2022.03.03

- Refactored code for version 1.18.2-40.0.2

### 2022.03.01

- Fixed air block check to consider cave air as well.

### 2022.02.20

- Fixed issue were entity get stucked on spawn point by shifting spawnpoint by 0.5.

### 2022.01.19

- Added desert farm and list of possible desert mobs.
- Simplified menu's and added missing loot / break tables.

### 2022.01.13

- Added fishing net and fishing bowl for the aqua farm.
- Added relevant advancements and crafting recipes.
- Improved loot predication cache by caching the farm drops as well.
- Added translation and basic mod support for mod like aquaculture.

### 2022.01.10

- Added creative mob farm for testing.

### 2022.01.07

- Added skeleton mob farm.

### 2022.01.06

- Initial check-in of working preview version.

[history]: https://github.com/MarkusBordihn/BOs-Easy-Mob-Farm/commits/
