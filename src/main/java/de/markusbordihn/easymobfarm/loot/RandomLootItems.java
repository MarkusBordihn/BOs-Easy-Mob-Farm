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

import java.util.Random;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RandomLootItems {

  private static final Random random = new Random();

  public static final ItemStack getRandomArmor() {
    if (random.nextInt(50) == 0) {
      return getRandomDiamondArmor();
    } else if (random.nextInt(25) == 0) {
      return getRandomIronArmor();
    } else if (random.nextInt(10) == 0) {
      return getRandomGoldenArmor();
    } else {
      return getRandomLeatherArmor();
    }
  }

  public static final ItemStack getRandomDiamondArmor() {
    switch (random.nextInt(4)) {
      case 0:
        return new ItemStack(Items.DIAMOND_HELMET);
      case 1:
        return new ItemStack(Items.DIAMOND_CHESTPLATE);
      case 2:
        return new ItemStack(Items.DIAMOND_LEGGINGS);
      case 3:
        return new ItemStack(Items.DIAMOND_BOOTS);
      default:
        return new ItemStack(Items.DIAMOND_HELMET);
    }
  }

  public static final ItemStack getRandomIronArmor() {
    switch (random.nextInt(4)) {
      case 0:
        return new ItemStack(Items.IRON_HELMET);
      case 1:
        return new ItemStack(Items.IRON_CHESTPLATE);
      case 2:
        return new ItemStack(Items.IRON_LEGGINGS);
      case 3:
        return new ItemStack(Items.IRON_BOOTS);
      default:
        return new ItemStack(Items.IRON_HELMET);
    }
  }

  public static final ItemStack getRandomGoldenArmor() {
    switch (random.nextInt(4)) {
      case 0:
        return new ItemStack(Items.GOLDEN_HELMET);
      case 1:
        return new ItemStack(Items.GOLDEN_CHESTPLATE);
      case 2:
        return new ItemStack(Items.GOLDEN_LEGGINGS);
      case 3:
        return new ItemStack(Items.GOLDEN_BOOTS);
      default:
        return new ItemStack(Items.GOLDEN_HELMET);
    }
  }

  public static final ItemStack getRandomLeatherArmor() {
    switch (random.nextInt(4)) {
      case 0:
        return new ItemStack(Items.LEATHER_HELMET);
      case 1:
        return new ItemStack(Items.LEATHER_CHESTPLATE);
      case 2:
        return new ItemStack(Items.LEATHER_LEGGINGS);
      case 3:
        return new ItemStack(Items.LEATHER_BOOTS);
      default:
        return new ItemStack(Items.LEATHER_HELMET);
    }
  }

  public static final ItemStack getRandomWeapon() {
    if (random.nextInt(50) == 0) {
      return getRandomDiamondWeapon();
    } else if (random.nextInt(25) == 0) {
      return getRandomIronWeapon();
    } else if (random.nextInt(10) == 0) {
      return getRandomGoldenWeapon();
    } else {
      return getRandomStoneWeapon();
    }
  }

  public static final ItemStack getRandomDiamondWeapon() {
    switch (random.nextInt(2)) {
      case 0:
        return new ItemStack(Items.DIAMOND_SWORD);
      case 1:
        return new ItemStack(Items.DIAMOND_AXE);
      default:
        return new ItemStack(Items.DIAMOND_SWORD);
    }
  }

  public static final ItemStack getRandomIronWeapon() {
    switch (random.nextInt(2)) {
      case 0:
        return new ItemStack(Items.IRON_SWORD);
      case 1:
        return new ItemStack(Items.IRON_AXE);
      default:
        return new ItemStack(Items.IRON_SWORD);
    }
  }

  public static final ItemStack getRandomGoldenWeapon() {
    switch (random.nextInt(2)) {
      case 0:
        return new ItemStack(Items.GOLDEN_SWORD);
      case 1:
        return new ItemStack(Items.GOLDEN_AXE);
      default:
        return new ItemStack(Items.CROSSBOW);
    }
  }

  public static final ItemStack getRandomStoneWeapon() {
    switch (random.nextInt(2)) {
      case 0:
        return new ItemStack(Items.STONE_SWORD);
      case 1:
        return new ItemStack(Items.STONE_AXE);
      default:
        return new ItemStack(Items.STONE_SWORD);
    }
  }

  public static final ItemStack getRandomGoldenTool() {
    switch (random.nextInt(4)) {
      case 0:
        return new ItemStack(Items.GOLDEN_SWORD);
      case 1:
        return new ItemStack(Items.GOLDEN_AXE);
      case 2:
        return new ItemStack(Items.GOLDEN_SHOVEL);
      case 3:
        return new ItemStack(Items.GOLDEN_PICKAXE);
      default:
        return new ItemStack(Items.GOLDEN_SWORD);
    }
  }

  public static ItemStack getRandomIronTool() {
    switch (random.nextInt(4)) {
      case 0:
        return new ItemStack(Items.IRON_SWORD);
      case 1:
        return new ItemStack(Items.IRON_AXE);
      case 2:
        return new ItemStack(Items.IRON_SHOVEL);
      case 3:
        return new ItemStack(Items.IRON_PICKAXE);
      default:
        return new ItemStack(Items.IRON_SWORD);
    }
  }
}
