# 🪤 Easy Mob Farm (1.19.2)

The easy mob farm is a server friendly way to capture different kind of animals and mobs to gather their loot.
This mod provides serval catching items and mob farms to make it more entertaining.

But there is also a creative version which is able to handle custom animals and monsters without any effort.

![Easy Mob Farm][logo]

## 🔮 Features

- Server friendly and optimized for the best performance on the client and server side
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

## Support mobs Overview

List of currently supported mobs and their corresponding catch item and mob farm.

| Mob Name                     | Catch Item                      | Mob Farm                                         |
| ---------------------------- | ------------------------------- | ------------------------------------------------ |
| aquaculture:atlantic_cod     | Fishing bowl                    | Ocean Farm                                       |
| aquaculture:atlantic_halibut | Fishing bowl                    | Ocean Farm                                       |
| aquaculture:atlantic_herring | Fishing bowl                    | Ocean Farm                                       |
| aquaculture:blackfish        | Fishing bowl                    | Ocean Farm                                       |
| aquaculture:pacific_halibut  | Fishing bowl                    | Ocean Farm                                       |
| aquaculture:pink_salmon      | Fishing bowl                    | Ocean Farm                                       |
| aquaculture:pollock          | Fishing bowl                    | Ocean Farm                                       |
| aquaculture:rainbow_trout    | Fishing bowl                    | Ocean Farm                                       |
| minecraft:blaze              |                                 |                                                  |
| minecraft:cave_spider        | Urn small                       | Monster Plains Cave Farm                         |
| minecraft:chicken            | Catch Cage small, Collar small  | Animal Plains Farms, Swamp Farm, Jungle Farm     |
| minecraft:cod                | Fishing bowl, Fishing net small | Ocean Farm                                       |
| minecraft:cow                | Collar small                    | Animal Plains Farms, Swamp Farm, Jungle Farm     |
| minecraft:creeper            | Urn small                       | Desert Farm, Monster Plains Cave Farm            |
| minecraft:donkey             | Collar small                    | Animal Plains Farms                              |
| minecraft:drowned            | Fishing net small               | Ocean Farm                                       |
| minecraft:enderman           | Witch Bottle                    | Desert Farm                                      |
| minecraft:evoker             |                                 |                                                  |
| minecraft:ghast              |                                 |                                                  |
| minecraft:glow_squid         | Fishing net small               | Ocean Farm                                       |
| minecraft:guardian           |                                 |                                                  |
| minecraft:hoglin             |                                 |                                                  |
| minecraft:horse              | Collar small                    | Animal Plains Farms                              |
| minecraft:husk               | Urn small                       | Desert Farm                                      |
| minecraft:iron_golem         |                                 |                                                  |
| minecraft:lama               |                                 |                                                  |
| minecraft:magma_cube         |                                 |                                                  |
| minecraft:mooshroom          |                                 |                                                  |
| minecraft:mule               |                                 |                                                  |
| minecraft:panda              | Catch Cage                      | Jungle Farm                                      |
| minecraft:parrot             | Catch Cage small                | Jungle Farm                                      |
| minecraft:phantom            |                                 |                                                  |
| minecraft:pig                | Collar small                    | Animal Plains Farms, Swamp Farm, Jungle Farm     |
| minecraft:piglin_brute       |                                 |                                                  |
| minecraft:pillager           |                                 |                                                  |
| minecraft:polar_bear         | Catch Cage                      |                                                  |
| minecraft:pufferfish         |                                 |                                                  |
| minecraft:rabbit             | Catch Cage small                | Desert Farm                                      |
| minecraft:ravager            |                                 |                                                  |
| minecraft:salmon             | Fishing bowl, Fishing net small | Ocean Farm                                       |
| minecraft:sheep              | Collar small                    | Animal Plains Farms, Swamp Farm, Jungle Farm     |
| minecraft:shulker            |                                 |                                                  |
| minecraft:skeleton           | Urn small                       | Desert Farm, Monster Plains Cave Farm            |
| minecraft:skeleton_horse     |                                 |                                                  |
| minecraft:slime              | Witch Bottle                    | Swamp Farm                                       |
| minecraft:spider             | Urn small                       | Desert Farm, Swamp Farm                          |
| minecraft:squid              | Fishing net small               | Ocean Farm                                       |
| minecraft:stray              |                                 |                                                  |
| minecraft:strider            |                                 |                                                  |
| minecraft:tropical_fish      |                                 |                                                  |
| minecraft:turtle             |                                 |                                                  |
| minecraft:witch              | Witch Bottle                    | Desert Farm, Swamp Farm                          |
| minecraft:zombie             | Urn small                       | Desert Farm, Monster Plains Cave Farm,Swamp Farm |
| minecraft:zombie_villager    | Urn small                       | Desert Farm, Monster Plains Cave Farm,Swamp Farm |
| minecraft:zombified_piglin   |                                 |                                                  |

**Note: If a mob is missing a catch item, mob farm or both it's WIP and could not be used without the creative items.**

## Support for additional mods / mobs ℹ️

You can easily add new mobs to the corresponding catch item and mob farm over the config file.
Each mob needs to have a catch item and a mob farm.

Most of the mobs should already work out of the box with the creative farm, so you can use the creative farm for testing.

## Requires

- [Bo's Material and Elements][material-elements]

## Type of Mob Farms

### 🐄 Animal Farms

List of animals farms which are will be released over time.

#### 🌿 Animal Plains Farm

![Example of animal plains farm][animal_plains_farm]

### ☠️ Monster Farm

List of monster farms which are will be released over time.

#### 🪨 Monster Plains Cave Farm

![Example monster plains cave farm][monster_plains_cave_farm]

#### Monster Swamp Farm

### Special Farms

#### 🏜️ Desert Farm

![Example desert farm][desert_farm]

#### 🌊 Ocean Farm

![Example ocean farm][ocean_farm]

### 🪄 Creative Mob Farm

The creative mob farm has not restriction and is mostly for testing.
It could theoretical process all kind of living entities, but not all of them are dropping loot.

## Type of Mob Catching Items

### 🪢Collar (small)

The collar is used to catch the standard animals from a plains biome.

### ⚱️ Urn (small)

The urn is used to catch the standard monsters from a plains cave biome.

### 🐟 Fishing Bowl

The fishing bowl is only able to catch standard fish.

### 🎣 Fishing Net (small)

The fishing net is able to capture standard ocean animals and monsters from the ocean.

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

[animal_plains_farm]: https://raw.githubusercontent.com/MarkusBordihn/BOs-Easy-Mob-Farm/main/assets/animal_plains_farm.png
[desert_farm]: https://raw.githubusercontent.com/MarkusBordihn/BOs-Easy-Mob-Farm/main/assets/desert_farm.png
[item_overview]: https://raw.githubusercontent.com/MarkusBordihn/BOs-Easy-Mob-Farm/main/assets/item_overview.png
[logo]: https://raw.githubusercontent.com/MarkusBordihn/BOs-Easy-Mob-Farm/main/src/main/resources/logo.png
[material-elements]: https://www.curseforge.com/minecraft/mc-mods/material-elements
[mob_menu]: https://raw.githubusercontent.com/MarkusBordihn/BOs-Easy-Mob-Farm/main/assets/mob_menu.png
[mod_page]: https://www.curseforge.com/minecraft/mc-mods/easy-mob-farm
[monster_plains_cave_farm]: https://raw.githubusercontent.com/MarkusBordihn/BOs-Easy-Mob-Farm/main/assets/monster_plains_cave_farm.png
[ocean_farm]: https://raw.githubusercontent.com/MarkusBordihn/BOs-Easy-Mob-Farm/main/assets/ocean_farm.png
