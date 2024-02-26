# 🪤 Easy Mob Farm (1.20)

[![Easy Mob Farm Downloads](http://cf.way2muchnoise.eu/full_563464_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/easy-mob-farm)
[![Easy Mob Farm Versions](http://cf.way2muchnoise.eu/versions/Minecraft_563464_all.svg)](https://www.curseforge.com/minecraft/mc-mods/easy-mob-farm)

![Easy Mob Farm Logo][logo]

The Easy Mob Farm offers a server-friendly solution for capturing various animals and mobs to
harvest their valuable loot.
This mod offers multiple capturing items and mob farms to enhance the gameplay experience.

Additionally, for those seeking a more creative approach, there is a version of the mod that
effortlessly accommodates custom animals and monsters, requiring minimal effort on the player's
part.

## 🔮 Features

- Designed for server-friendly operation while optimizing performance on both the client and server
  sides.
- Enables loot drops upon `player kills`, ensuring that the dropped items correspond to the weapon
  used (e.g., swords).
- Supports automated import and export of items for seamless processing.
- Integrates with a redstone signal to enable or disable the mob farm as nee
- Provides clear visibility of potential loot drops for each captured mob.
- Offers distinct mob farms tailored to various biomes and mob types.
- Grants users the ability to toggle specific mob drops on or off, such as egg drops from chickens.
- Automatically notifies the owner when farms reach capacity to prevent hopper lag and other
  potential issues.
- Facilitates easy capture and release of mobs, streamlining the process.
- Allows for customization of supported mobs per farm and the item used for mob capture.

## User Interface

### Mob Farm Menu

![Example of mob farm menu][mob_menu]

### Item Overview

![Example of item overview][item_overview]

## Custom Loot tables 📦

The mod is using the provided loot tables from the mods.
This means if you adjust the loot tables for data files these changes are automatically considered
by this mod as well.

### 🤺 Killed by Player Loot Drops

The `killed_by_player` loot drops will be only considered if an weapon (sword), is placed in the
weapon slot.
The item will be consumed by each drop, but it will also increase the loot luck by 20%.

## Collect Experience 💡

Experience could be collected by placing a empty bottle or experience bottle into the experience
slot.
The experience will be generated randomly, so not on each drop.

## ℹ️ More Information

Please check the wiki <https://github.com/MarkusBordihn/BOs-Easy-Mob-Farm/wiki> for additional
information.

[item_overview]: examples/item_overview.png

[logo]: wiki/logo_header.png

[mob_menu]: examples/mob_farm_menu.png
