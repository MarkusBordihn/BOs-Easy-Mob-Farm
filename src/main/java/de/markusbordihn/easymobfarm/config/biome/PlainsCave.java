package de.markusbordihn.easymobfarm.config.biome;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.markusbordihn.easymobfarm.config.mobs.HostileMonster;

public class PlainsCave {

  protected PlainsCave() {}

  public static final Set<String> Hostile = new HashSet<>(Arrays.asList(
  // @formatter:off
    HostileMonster.CAVE_SPIDER,
    HostileMonster.CREEPER,
    HostileMonster.SKELETON,
    HostileMonster.ZOMBIE
    // @formatter:on
  ));

}
