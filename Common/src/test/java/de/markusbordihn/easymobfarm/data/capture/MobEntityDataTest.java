/*
 * Copyright 2026 Markus Bordihn
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

package de.markusbordihn.easymobfarm.data.capture;

import de.markusbordihn.easymobfarm.config.MobCaptureCardConfig;
import java.util.Set;
import java.util.UUID;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.Bootstrap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MobEntityDataTest {

  @BeforeAll
  static void bootstrap() {
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
  }

  private static CompoundTag pig(int age, UUID uuid) {
    CompoundTag compoundTag = new CompoundTag();
    compoundTag.putString("id", "minecraft:pig");
    compoundTag.putInt("Age", age);
    compoundTag.putUUID("UUID", uuid);
    compoundTag.putFloat("Health", 10.0F);
    compoundTag.putShort("TicksFrozen", (short) 42);
    compoundTag.putBoolean("InLove", false);
    return compoundTag;
  }

  @AfterEach
  void resetConfig() {
    MobCaptureCardConfig.additionalCardTagsToRemove = Set.of();
  }

  @Test
  void adultPigsWithDifferentAgeAndUuidProduceIdenticalData() {
    CompoundTag firstPig =
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(pig(0, UUID.randomUUID()));
    CompoundTag secondPig =
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(pig(6000, UUID.randomUUID()));

    Assertions.assertEquals(firstPig, secondPig);
    Assertions.assertFalse(firstPig.contains("Age"));
    Assertions.assertFalse(firstPig.contains("UUID"));
  }

  @Test
  void babyPigsWithDifferentAgeProduceIdenticalData() {
    CompoundTag firstPiglet =
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(pig(-24000, UUID.randomUUID()));
    CompoundTag secondPiglet =
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(pig(-1200, UUID.randomUUID()));

    Assertions.assertEquals(firstPiglet, secondPiglet);
    Assertions.assertTrue(firstPiglet.getInt("Age") < 0);
  }

  @Test
  void babyDataDiffersFromAdultData() {
    Assertions.assertNotEquals(
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(pig(-1200, UUID.randomUUID())),
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(pig(0, UUID.randomUUID())));
  }

  @Test
  void customNameIsKept() {
    CompoundTag namedPig = pig(0, UUID.randomUUID());
    namedPig.putString("CustomName", "{\"text\":\"Rosi\"}");

    Assertions.assertTrue(
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(namedPig).contains("CustomName"));
  }

  @Test
  void emptyInventoryIsRemovedAndFilledInventoryIsKept() {
    CompoundTag emptyInventory = pig(0, UUID.randomUUID());
    emptyInventory.put("Inventory", new ListTag());

    CompoundTag filledInventory = pig(0, UUID.randomUUID());
    ListTag items = new ListTag();
    items.add(new CompoundTag());
    filledInventory.put("Inventory", items);

    Assertions.assertFalse(
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(emptyInventory).contains("Inventory"));
    Assertions.assertTrue(
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(filledInventory)
            .contains("Inventory", Tag.TAG_LIST));
  }

  @Test
  void additionalConfiguredTagsAreRemoved() {
    MobCaptureCardConfig.additionalCardTagsToRemove = Set.of("SomeModTimer");
    CompoundTag moddedPig = pig(0, UUID.randomUUID());
    moddedPig.putInt("SomeModTimer", 17);

    Assertions.assertFalse(
        MobEntityData.removeSafeToRemoveMobCaptureCardTags(moddedPig).contains("SomeModTimer"));
  }
}
