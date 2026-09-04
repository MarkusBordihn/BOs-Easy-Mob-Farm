/*
 * Copyright 2025 Markus Bordihn
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

package de.markusbordihn.easymobfarm.data.mobfarm;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobFarmTypeTest {

  @Test
  @DisplayName("Ids are unique and lower case")
  void idsAreUniqueAndLowerCase() {
    long uniqueIds = Arrays.stream(MobFarmType.values()).map(MobFarmType::getId).distinct().count();
    Assertions.assertEquals(MobFarmType.values().length, uniqueIds);
    for (MobFarmType mobFarmType : MobFarmType.values()) {
      Assertions.assertEquals(mobFarmType.getId().toLowerCase(Locale.ROOT), mobFarmType.getId());
    }
  }

  @Test
  @DisplayName("Serialized name matches id")
  void serializedNameMatchesId() {
    for (MobFarmType mobFarmType : MobFarmType.values()) {
      Assertions.assertEquals(mobFarmType.getId(), mobFarmType.getSerializedName());
    }
  }

  @Test
  @DisplayName("Enum name round trips through valueOf")
  void valueOfRoundTrip() {
    for (MobFarmType mobFarmType : MobFarmType.values()) {
      Assertions.assertSame(mobFarmType, MobFarmType.valueOf(mobFarmType.name()));
    }
  }

  @Test
  @DisplayName("End and Nether Wastes farms are appended after Swamp farm")
  void newFarmsAreAppendedAfterSwampFarm() {
    Assertions.assertTrue(MobFarmType.END_FARM.ordinal() > MobFarmType.SWAMP_FARM.ordinal());
    Assertions.assertTrue(
        MobFarmType.NETHER_WASTES_FARM.ordinal() > MobFarmType.END_FARM.ordinal());
    Assertions.assertEquals("end_farm", MobFarmType.END_FARM.getId());
    Assertions.assertEquals("nether_wastes_farm", MobFarmType.NETHER_WASTES_FARM.getId());
  }

  @Test
  @DisplayName("Every farm type resolves from its id")
  void everyFarmTypeResolvesFromId() {
    var idToType =
        Arrays.stream(MobFarmType.values())
            .collect(Collectors.toMap(MobFarmType::getId, mobFarmType -> mobFarmType));
    Assertions.assertSame(MobFarmType.END_FARM, idToType.get("end_farm"));
    Assertions.assertSame(MobFarmType.NETHER_WASTES_FARM, idToType.get("nether_wastes_farm"));
  }
}
