/**
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

import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;

public class Desert {

  // Ref: https://minecraft.fandom.com/wiki/Desert
  protected Desert() {}

  public static final Set<String> All = new HashSet<>(Arrays.asList(
  // @formatter:off
    PassiveAnimal.RABBIT,
    HostileMonster.SPIDER,
    HostileMonster.CREEPER,
    HostileMonster.SKELETON,
    HostileMonster.ZOMBIE,
    HostileMonster.ENDERMAN,
    HostileMonster.HUSK,
    HostileMonster.WITCH
    // @formatter:on
  ));

  public static final Set<String> Passive = new HashSet<>(Arrays.asList(
  // @formatter:off
    PassiveAnimal.RABBIT
    // @formatter:on
  ));

  public static final Set<String> Hostile = new HashSet<>(Arrays.asList(
  // @formatter:off
    HostileMonster.SPIDER,
    HostileMonster.CREEPER,
    HostileMonster.SKELETON,
    HostileMonster.ZOMBIE,
    HostileMonster.ENDERMAN,
    HostileMonster.HUSK,
    HostileMonster.WITCH
    // @formatter:on
  ));

}
