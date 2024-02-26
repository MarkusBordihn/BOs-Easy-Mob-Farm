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

package de.markusbordihn.easymobfarm.config.biome;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;

public class Desert {

  public static final Set<String> Passive =
      new HashSet<>(
          Arrays.asList(
              // @formatter:off
              PassiveAnimal.RABBIT
              // @formatter:on
              ));
  public static final Set<String> Hostile =
      new HashSet<>(
          Arrays.asList(
              // @formatter:off
              HostileMonster.CREEPER,
              HostileMonster.ENDERMAN,
              HostileMonster.HUSK,
              HostileMonster.SKELETON,
              HostileMonster.SPIDER,
              HostileMonster.WITCH,
              HostileMonster.ZOMBIE,
              HostileMonster.ZOMBIE_VILLAGER
              // @formatter:on
              ));

  // Ref: https://minecraft.fandom.com/wiki/Desert
  protected Desert() {}

  public static final Set<String> All =
      Stream.concat(Passive.stream(), Hostile.stream()).collect(Collectors.toSet());
}
