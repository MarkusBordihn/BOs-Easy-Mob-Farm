# 🪤 Easy Mob Farm (1.19.2)

The easy mob farm is a server friendly way to capture different kind of animals and mobs to gather their loot.
This mod provides serval catching items and mob farms to make it more entertaining.

But there is also a creative version which is able to handle custom animals and monsters without any effort.

![Easy Mob Farm][logo]

## 🔮 Features

- Server friendly and optimized for the best performance on the client and server side.
- Supporting `killed_by_player` loot drops with corresponding weapon (sword).
- Displays possible loot from each catched mob.
- Unique mob farms for the different kind of biomes and mobs.
- Possibility to enable / disable specific drops from mobs like egg drops from chicken.
- Automatic informs owner about full farms to make sure that there is no hopper lag or so.
- Easy catching and releasing of mobs.
- Configurable list of supported mobs per farm and mob catching item.

## User Interface

### Mob Farm Menu

![Example of mob farm menu][mob_menu]

### Item Overview

![Example of item overview][item_overview]

## Custom Loot tables 📦

The mod is using the provided loot tables from the mods.
This means if you adjust the loot tables for data files these changes are automatically considered by this mod as well.

### 🤺 Killed by Player Loot Drops

The `killed_by_player` loot drops will be only considered if an weapon (sword), is placed in the weapon slot.
The item will be consumed by each drop, but it will also increase the loot luck by 20%.

## Collect Experience

Experience could be collected by placing a empty bottle or experience bottle into the experience slot.
The experience will be generated randomly, so not on each drop.

## Supported Minecraft Mobs

List of currently supported mobs and their corresponding catch item and mob farm.

| Mob Name                   | Catch Item                      | Mob Farm                                                    | Requires       |
| -------------------------- | ------------------------------- | ----------------------------------------------------------- | -------------- |
| minecraft:bee              | Insect net                      | Bee Hive Farm                                               |                |
| minecraft:blaze            |                                 | Nether Fortress Farm                                        | Weapon         |
| minecraft:cave_spider      | Urn small                       | Monster Plains Cave Farm                                    |                |
| minecraft:chicken          | Catch Cage small, Collar small  | Animal Plains Farms, Swamp Farm, Jungle Farm                |                |
| minecraft:cod              | Fishing bowl, Fishing net small | Ocean Farm                                                  |                |
| minecraft:cow              | Collar small                    | Animal Plains Farms, Swamp Farm, Jungle Farm                |                |
| minecraft:creeper          | Urn small                       | Desert Farm, Monster Plains Cave Farm                       |                |
| minecraft:donkey           | Collar small                    | Animal Plains Farms                                         |                |
| minecraft:drowned          | Fishing net small               | Ocean Farm                                                  |                |
| minecraft:enderman         | Witch Bottle                    | Desert Farm                                                 |                |
| minecraft:evoker           |                                 |                                                             |                |
| minecraft:ghast            |                                 |                                                             |                |
| minecraft:glow_squid       | Fishing net small               | Ocean Farm                                                  |                |
| minecraft:guardian         |                                 |                                                             |                |
| minecraft:hoglin           |                                 |                                                             |                |
| minecraft:horse            | Collar small                    | Animal Plains Farms                                         |                |
| minecraft:husk             | Urn small                       | Desert Farm                                                 |                |
| minecraft:iron_golem       |                                 |                                                             |                |
| minecraft:lama             |                                 |                                                             |                |
| minecraft:magma_cube       |                                 | Nether Fortress Farm                                        | Big Magma Cube |
| minecraft:mooshroom        |                                 |                                                             |                |
| minecraft:mule             |                                 |                                                             |                |
| minecraft:panda            | Catch Cage                      | Jungle Farm                                                 |                |
| minecraft:parrot           | Catch Cage small                | Jungle Farm                                                 |                |
| minecraft:phantom          |                                 |                                                             |                |
| minecraft:pig              | Collar small                    | Animal Plains Farms, Swamp Farm, Jungle Farm                |                |
| minecraft:piglin_brute     |                                 |                                                             |                |
| minecraft:pillager         |                                 |                                                             |                |
| minecraft:polar_bear       | Catch Cage                      |                                                             |                |
| minecraft:pufferfish       |                                 |                                                             |                |
| minecraft:rabbit           | Catch Cage small                | Desert Farm                                                 |                |
| minecraft:ravager          |                                 |                                                             |                |
| minecraft:salmon           | Fishing bowl, Fishing net small | Ocean Farm                                                  |                |
| minecraft:sheep            | Collar small                    | Animal Plains Farms, Swamp Farm, Jungle Farm                |                |
| minecraft:shulker          |                                 |                                                             |                |
| minecraft:skeleton         | Urn small                       | Desert Farm, Monster Plains Cave Farm, Nether Fortress Farm |                |
| minecraft:skeleton_horse   |                                 |                                                             |                |
| minecraft:slime            | Witch Bottle                    | Swamp Farm                                                  |                |
| minecraft:spider           | Urn small                       | Desert Farm, Swamp Farm                                     |                |
| minecraft:squid            | Fishing net small               | Ocean Farm                                                  |                |
| minecraft:stray            |                                 |                                                             |                |
| minecraft:strider          |                                 |                                                             |                |
| minecraft:tropical_fish    |                                 |                                                             |                |
| minecraft:turtle           |                                 |                                                             |                |
| minecraft:witch            | Witch Bottle                    | Desert Farm, Swamp Farm                                     |                |
| minecraft:wither_skeleton  |                                 | Nether Fortress Farm                                        |                |
| minecraft:zombie           | Urn small                       | Desert Farm, Monster Plains Cave Farm,Swamp Farm            |                |
| minecraft:zombie_villager  | Urn small                       | Desert Farm, Monster Plains Cave Farm,Swamp Farm            |                |
| minecraft:zombified_piglin |                                 | Nether Fortress Farm                                        |                |

**Note: If a mob is missing a catch item, mob farm or both it's WIP and could not be used without the creative items.**

## Supported 3rd Party Mobs (from other Mods)

List of currently supported mobs and their corresponding catch item and mob farm.

| Mob Name                            | Catch Item   | Mob Farm      |
| ----------------------------------- | ------------ | ------------- |
| aquaculture:atlantic_cod            | Fishing bowl | Ocean Farm    |
| aquaculture:atlantic_halibut        | Fishing bowl | Ocean Farm    |
| aquaculture:atlantic_herring        | Fishing bowl | Ocean Farm    |
| aquaculture:blackfish               | Fishing bowl | Ocean Farm    |
| aquaculture:pacific_halibut         | Fishing bowl | Ocean Farm    |
| aquaculture:pink_salmon             | Fishing bowl | Ocean Farm    |
| aquaculture:pollock                 | Fishing bowl | Ocean Farm    |
| aquaculture:rainbow_trout           | Fishing bowl | Ocean Farm    |
| productivebees:\*                   | Insect net   |               |
| productivebees:amethyst_bee         | Insect net   | Bee Hive Farm |
| productivebees:ametrine_bee         | Insect net   | Bee Hive Farm |
| productivebees:blazing_bee          | Insect net   | Bee Hive Farm |
| productivebees:brass_bee            | Insect net   | Bee Hive Farm |
| productivebees:brown_shroom_bee     | Insect net   | Bee Hive Farm |
| productivebees:chocolate_mining_bee | Insect net   | Bee Hive Farm |
| productivebees:coal_bee             | Insect net   | Bee Hive Farm |
| productivebees:copper_bee           | Insect net   | Bee Hive Farm |
| productivebees:creeper_bee          | Insect net   | Bee Hive Farm |
| productivebees:crystalline_bee      | Insect net   | Bee Hive Farm |
| productivebees:diamond_bee          | Insect net   | Bee Hive Farm |
| productivebees:draconic_bee         | Insect net   | Bee Hive Farm |
| productivebees:elementium_bee       | Insect net   | Bee Hive Farm |
| productivebees:emerald_bee          | Insect net   | Bee Hive Farm |
| productivebees:ender_bee            | Insect net   | Bee Hive Farm |
| productivebees:experience_bee       | Insect net   | Bee Hive Farm |
| productivebees:frosty_bee           | Insect net   | Bee Hive Farm |
| productivebees:ghostly_bee          | Insect net   | Bee Hive Farm |
| productivebees:glowing_bee          | Insect net   | Bee Hive Farm |
| productivebees:gold_bee             | Insect net   | Bee Hive Farm |
| productivebees:iron_bee             | Insect net   | Bee Hive Farm |
| productivebees:lapis_bee            | Insect net   | Bee Hive Farm |
| productivebees:magmatic_bee         | Insect net   | Bee Hive Farm |
| productivebees:obsidian_bee         | Insect net   | Bee Hive Farm |
| productivebees:pink_slimy_bee       | Insect net   | Bee Hive Farm |
| productivebees:plastic_bee          | Insect net   | Bee Hive Farm |
| productivebees:prismarine_bee       | Insect net   | Bee Hive Farm |
| productivebees:radioactive_bee      | Insect net   | Bee Hive Farm |
| productivebees:rancher_bee          | Insect net   | Bee Hive Farm |
| productivebees:redstone_bee         | Insect net   | Bee Hive Farm |
| productivebees:red_shroom_bee       | Insect net   | Bee Hive Farm |
| productivebees:salty_bee            | Insect net   | Bee Hive Farm |
| productivebees:silky_bee            | Insect net   | Bee Hive Farm |
| productivebees:skeletal_bee         | Insect net   | Bee Hive Farm |
| productivebees:slimy_bee            | Insect net   | Bee Hive Farm |
| productivebees:steel_bee            | Insect net   | Bee Hive Farm |
| productivebees:tea_bee              | Insect net   | Bee Hive Farm |
| productivebees:terrasteel_bee       | Insect net   | Bee Hive Farm |
| productivebees:withered_bee         | Insect net   | Bee Hive Farm |
| productivebees:zinc_bee             | Insect net   | Bee Hive Farm |
| productivebees:zombie_bee           | Insect net   | Bee Hive Farm |

**Note: If a mob is missing a catch item, mob farm or both it's WIP and could not be used without the creative items.**

## Support for additional mods ℹ️

You can easily add new mobs to the corresponding catch item and mob farm over the config file.
Each mob needs to have a catch item and a mob farm.

Most of the mobs should already work out of the box with the creative farm, so you can use the creative farm for testing.

## Mob Catching Item Support

The mod has integrated support for mob catching items from other mods and items, to allow more ways of capturing and using them inside the easy mob farm.
Please note that the support is limited and it could happen that not all mobs are rendered correctly inside the farm by using such items.
Furthermore I'm not able to display the possible lot drop for catching items from other mods.

- Spawn Eggs (variants from SpawnEggItem)
- [Create](https://www.curseforge.com/minecraft/mc-mods/create): Blaze Burner
- [Cyclic](https://www.curseforge.com/minecraft/mc-mods/cyclic): Monster Ball
- [MobCatcher](https://www.curseforge.com/minecraft/mc-mods/mob-catcher)
- [Mob Catcher](https://www.curseforge.com/minecraft/mc-mods/mob-catcher-fabric)
- [Mob Capturing Tool](https://www.curseforge.com/minecraft/mc-mods/mob-capturing-tool)
- [Corail Spawners](https://www.curseforge.com/minecraft/mc-mods/corail-spawners)

## Type of Mob Farms

### 🐄🌿 Animal Plains Farm

![Example of animal plains farm][animal_plains_farm]

### Bee Hive Farm

![Example of bee hive farm][bee_hive_farm]

### 🏜️ Desert Farm

![Example desert farm][desert_farm]

### Jungle Farm

![Example of jungle farm][jungle_farm]

### ☠️🪨 Monster Plains Cave Farm

![Example monster plains cave farm][monster_plains_cave_farm]

### Nether Fortress Farm

![Example of nether fortress farm][nether_fortress_farm]

### 🌊 Ocean Farm

![Example ocean farm][ocean_farm]

### ☠️ Swamp Farm

![Example swamp farm][swamp_farm]

### 🪄 Creative Mob Farm

The creative mob farm has not restriction and is mostly for testing.
It could theoretical process all kind of living entities, but not all of them are dropping loot.

## Type of Mob Catching Items

### 🪤 Catch Cage

The catch cage is used to catch the bigger wilder animals.

### 🪤 Catch Cage (small)

The catch cage small used is to catch the standard animals.

### 🪢Collar (small)

The collar is used to catch the standard animals from a plains biome.

### 🐟 Fishing Bowl

The fishing bowl is only able to catch standard fish.

### 🎣 Fishing Net (small)

The fishing net is able to capture standard ocean animals and monsters from the ocean.

### 🕸️ Insect Net

The insect net is used to capture smaller insects and bees.

### ⚱️ Urn (small)

The urn is used to catch the standard monsters from a plains cave biome.

### 🍶 Witch Bottle

The witch bottle is used to capture magical creatures and witches as well.

### 🪄 Creative Mob Catching Item

The creative mob catching item could catch all kind of living entities.

## Version Status Overview 🛠️

| Version        | Status                |
| -------------- | --------------------- |
| Fabric Version | ❌ Not planned        |
| Forge 1.16.5   | ❌ Not planned        |
| Forge 1.17.1   | ❌ Not planned        |
| Forge 1.18.1   | ⚠️ Deprecated         |
| Forge 1.18.2   | ⚠️ Maintenance only   |
| Forge 1.19     | ⚠️ Deprecated         |
| Forge 1.19.1   | ⚠️ Deprecated         |
| Forge 1.19.2   | ✔️ Active development |

## License

The MIT [LICENSE.md](LICENSE.md) applies only to the code in this repository. Images, models and other assets are explicitly excluded.

## Note

Please only download the mod from the official CurseForge page or with the official CurseForge launcher like:
🧪 [Easy Mob Farm][mod_page]

If you are downloading this mod from other sources we could not make sure that it works as expected or does not includes any unwanted modification (e.g. adware, malware, ...).

[animal_plains_farm]: examples/farms/animal_plains_farm.png
[bee_hive_farm]: examples/farms/bee_hive_farm.png
[desert_farm]: examples/farms/desert_farm.png
[item_overview]: examples/item_overview.png
[jungle_farm]: examples/farms/jungle_farm.png
[logo]: examples/logo.png
[mob_menu]: examples/mob_farm_menu.png
[mod_page]: https://www.curseforge.com/minecraft/mc-mods/easy-mob-farm
[monster_plains_cave_farm]: examples/farms/monster_plains_cave_farm.png
[nether_fortress_farm]: examples/farms/nether_fortress_farm.png
[ocean_farm]: examples/farms/ocean_farm.png
[swamp_farm]: examples/farms/swamp_farm.png
