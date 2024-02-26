/*
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


package de.markusbordihn.easymobfarm.loot;

import de.markusbordihn.easymobfarm.Constants;
import de.markusbordihn.easymobfarm.config.CommonConfig;
import de.markusbordihn.easymobfarm.config.mobs.BeeAnimal;
import de.markusbordihn.easymobfarm.config.mobs.BossMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileNetherMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileWaterMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;
import java.util.List;
import java.util.Random;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class LootManagerDrops {

  private static final CommonConfig.Config COMMON = CommonConfig.COMMON;
  private static final Random random = new Random();

  protected LootManagerDrops() {
  }

  public static final void processForcedDrops(List<ItemStack> lootDrops, String mobType) {
    // Ignore empty mob type.
    if (mobType == null || mobType.isEmpty()) {
      return;
    }

    // Bee drop support.
    if (mobType.equals(BeeAnimal.BEE) && COMMON.forceBeeDropHoneycombChance.get() > 0
        && random.nextInt(COMMON.forceBeeDropHoneycombChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.HONEYCOMB));
    }

    // Productive Bees drop support.
    else if (Constants.PRODUCTIVE_BEES_LOADED && BeeAnimal.ProductiveBees.contains(mobType)
        && COMMON.forceBeeDropHoneycombChance.get() > 0
        && random.nextInt(COMMON.forceBeeDropHoneycombChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.HONEYCOMB));
    }

    // Blaze drop support.
    else if (mobType.equals(HostileNetherMonster.BLAZE)
        && COMMON.forceBlazeDropBlazeRodChance.get() > 0
        && random.nextInt(COMMON.forceBlazeDropBlazeRodChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.BLAZE_ROD));
    }

    // Chicken drop support.
    else if (mobType.equals(PassiveAnimal.CHICKEN) && COMMON.forceChickenDropEggChance.get() > 0
        && random.nextInt(COMMON.forceChickenDropEggChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.EGG));
    }

    // Drowned drop support.
    else if (mobType.equals(HostileWaterMonster.DROWNED)) {
      if (COMMON.forceDrownedDropArmorChance.get() > 0
          && random.nextInt(COMMON.forceDrownedDropArmorChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomLeatherArmor());
      }
      if (COMMON.forceDrownedDropCopperIngotChance.get() > 0
          && random.nextInt(COMMON.forceDrownedDropCopperIngotChance.get()) == 0) {
        lootDrops.add(new ItemStack(Items.COPPER_INGOT));
      }
      if (COMMON.forceDrownedDropTridentChance.get() > 0
          && random.nextInt(COMMON.forceDrownedDropTridentChance.get()) == 0) {
        lootDrops.add(new ItemStack(Items.TRIDENT));
      }
      if (COMMON.forceDrownedDropNautilusShellChance.get() > 0
          && random.nextInt(COMMON.forceDrownedDropNautilusShellChance.get()) == 0) {
        lootDrops.add(new ItemStack(Items.NAUTILUS_SHELL));
      }
    }

    // Enderman drop support.
    else if (mobType.equals(HostileMonster.ENDERMAN)
        && COMMON.forceEndermanDropEnderPearlChance.get() > 0
        && random.nextInt(COMMON.forceEndermanDropEnderPearlChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.ENDER_PEARL));
    }

    // Slime drop support.
    else if (mobType.equals(HostileMonster.SLIME) && COMMON.forceSlimeDropSlimeBallChance.get() > 0
        && random.nextInt(COMMON.forceSlimeDropSlimeBallChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.SLIME_BALL));
    }

    // Magma Cube drop support.
    else if (mobType.equals(HostileNetherMonster.MAGMA_CUBE)
        && COMMON.forceMagmaCubeDropMagmaCreamChance.get() > 0
        && random.nextInt(COMMON.forceMagmaCubeDropMagmaCreamChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.MAGMA_CREAM));
    }

    // Piglin drop support.
    else if (mobType.equals(HostileNetherMonster.PIGLIN)) {
      if (COMMON.forcePiglinDropArmorChance.get() > 0
          && random.nextInt(COMMON.forcePiglinDropArmorChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomGoldenArmor());
      }
      if (COMMON.forcePiglinDropWeaponChance.get() > 0
          && random.nextInt(COMMON.forcePiglinDropWeaponChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomGoldenWeapon());
      }
    }

    // Pillager drop support.
    else if (mobType.equals(HostileMonster.PILLAGER)) {
      if (COMMON.forcePillagerDropArmorChance.get() > 0
          && random.nextInt(COMMON.forcePillagerDropArmorChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomIronArmor());
      }
      if (COMMON.forcePillagerDropEmeraldChance.get() > 0
          && random.nextInt(COMMON.forcePillagerDropEmeraldChance.get()) == 0) {
        lootDrops.add(new ItemStack(Items.EMERALD));
      }
      if (COMMON.forcePillagerDropEnchantedBookChance.get() > 0
          && random.nextInt(COMMON.forcePillagerDropEnchantedBookChance.get()) == 0) {
        lootDrops.add(new ItemStack(Items.ENCHANTED_BOOK));
      }
      if (COMMON.forcePillagerDropWeaponChance.get() > 0
          && random.nextInt(COMMON.forcePillagerDropWeaponChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomIronTool());
      }
    }

    // Wither drop support.
    else if (mobType.equals(BossMonster.WITHER) && COMMON.forceWitherDropNetherStarChance.get() > 0
        && random.nextInt(COMMON.forceWitherDropNetherStarChance.get()) == 0) {
      lootDrops.add(new ItemStack(Items.NETHER_STAR));
    }

    // Zombie drop support.
    else if (mobType.equals(HostileMonster.ZOMBIE)) {
      if (COMMON.forceZombieDropArmorChance.get() > 0
          && random.nextInt(COMMON.forceZombieDropArmorChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomArmor());
      }
      if (COMMON.forceZombieDropWeaponChance.get() > 0
          && random.nextInt(COMMON.forceZombieDropWeaponChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomWeapon());
      }
    }

    // Zombified Piglin drop support.
    else if (mobType.equals(HostileNetherMonster.ZOMBIFIED_PIGLIN)) {
      if (COMMON.forceZombifiedPiglinDropGoldIngotChance.get() > 0
          && random.nextInt(COMMON.forceZombifiedPiglinDropGoldIngotChance.get()) == 0) {
        lootDrops.add(new ItemStack(Items.GOLD_INGOT));
      }
      if (COMMON.forceZombifiedPiglinDropGoldNuggetChance.get() > 0
          && random.nextInt(COMMON.forceZombifiedPiglinDropGoldNuggetChance.get()) == 0) {
        lootDrops.add(new ItemStack(Items.GOLD_NUGGET));
      }
      if (COMMON.forceZombifiedPiglinDropWeaponChance.get() > 0
          && random.nextInt(COMMON.forceZombifiedPiglinDropWeaponChance.get()) == 0) {
        lootDrops.add(RandomLootItems.getRandomGoldenWeapon());
      }
    }
  }

}
