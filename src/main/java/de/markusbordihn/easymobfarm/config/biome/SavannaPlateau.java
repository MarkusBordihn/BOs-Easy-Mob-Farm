package de.markusbordihn.easymobfarm.config.biome;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import de.markusbordihn.easymobfarm.config.mobs.PassiveAnimal;

public class SavannaPlateau {
  protected SavannaPlateau() {}

  public static final Set<String> Passive = new HashSet<>(Arrays.asList(
  // @formatter:off
    PassiveAnimal.COW,
    PassiveAnimal.DONKEY,
    PassiveAnimal.HORSE,
    PassiveAnimal.LAMA,
    PassiveAnimal.PIG,
    PassiveAnimal.SHEEP
    // @formatter:on
  ));
}
