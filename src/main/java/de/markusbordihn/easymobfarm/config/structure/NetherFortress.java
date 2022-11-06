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

package de.markusbordihn.easymobfarm.config.structure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;
import de.markusbordihn.easymobfarm.config.mobs.HostileNetherMonster;

public class NetherFortress {

  // Ref: https://minecraft.fandom.com/wiki/Nether_Fortress
  protected NetherFortress() {}

  public static final Set<String> Hostile = new HashSet<>(Arrays.asList(
  // @formatter:off
    HostileNetherMonster.BLAZE,
    HostileNetherMonster.MAGMA_CUBE,
    HostileMonster.SKELETON,
    HostileNetherMonster.WITHER_SKELETON,
    HostileNetherMonster.ZOMBIFIED_PIGLIN
  // @formatter:on
  ));

  public static final Set<String> All = Hostile;
}
