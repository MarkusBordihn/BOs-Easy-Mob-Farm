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

package de.markusbordihn.easymobfarm.config.mobs;

import java.util.Set;

public final class PassiveAnimal {

  public static final String BAT = "minecraft:bat";
  public static final String CAT = "minecraft:cat";
  public static final String CHICKEN = "minecraft:chicken";
  public static final String COW = "minecraft:cow";
  public static final String DONKEY = "minecraft:donkey";
  public static final String FLY = "alexsmobs:fly";
  public static final String HORSE = "minecraft:horse";
  public static final String LAMA = "minecraft:lama";
  public static final String PANDA = "minecraft:panda";
  public static final String PARROT = "minecraft:parrot";
  public static final String PIG = "minecraft:pig";
  public static final String RABBIT = "minecraft:rabbit";
  public static final String SHEEP = "minecraft:sheep";
  public static final Set<String> All =
      Set.of(BAT, CAT, CHICKEN, COW, DONKEY, HORSE, LAMA, PANDA, PARROT, PIG, RABBIT, SHEEP);

  protected PassiveAnimal() {}
}
