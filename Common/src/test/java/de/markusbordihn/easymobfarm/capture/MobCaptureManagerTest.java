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

package de.markusbordihn.easymobfarm.capture;

import de.markusbordihn.easymobfarm.data.capture.MobCaptureData;
import de.markusbordihn.easymobfarm.data.capture.MobEntityTypeData;
import java.util.UUID;
import net.minecraft.SharedConstants;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobCaptureManagerTest {

  @BeforeAll
  static void bootstrap() {
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
  }

  private static ItemStack createCaptureItem(final String entityTypeName) {
    CompoundTag captureData = new CompoundTag();
    captureData.putString(MobEntityTypeData.TYPE_TAG, entityTypeName);
    return createCaptureItem(captureData);
  }

  private static ItemStack createCaptureItem(final CompoundTag captureData) {
    ItemStack itemStack = new ItemStack(Items.PAPER);
    itemStack.getOrCreateTag().put(MobCaptureManager.MOB_CAPTURE_DATA_TAG, captureData);
    return itemStack;
  }

  private static ItemStack createLegacyPigCard(final UUID uuid, final int age) {
    CompoundTag pigData = new CompoundTag();
    pigData.putString("id", "minecraft:pig");
    pigData.putUUID("UUID", uuid);
    pigData.putInt("Age", age);
    ItemStack itemStack = new ItemStack(Items.PAPER);
    MobCaptureManager.writeMobCaptureData(
        itemStack, new MobCaptureData(EntityType.PIG).withFoil(false).withData(pigData));
    itemStack.getTag().remove(MobCaptureManager.CARD_DATA_VERSION_TAG);
    return itemStack;
  }

  @Test
  @DisplayName("Empty item stack has no entity type")
  void emptyItemStackHasNoEntityType() {
    Assertions.assertNull(MobCaptureManager.getEntityType(ItemStack.EMPTY));
  }

  @Test
  @DisplayName("Null item stack has no entity type")
  void nullItemStackHasNoEntityType() {
    Assertions.assertNull(MobCaptureManager.getEntityType(null));
  }

  @Test
  @DisplayName("Item stack without capture data has no entity type")
  void itemStackWithoutCaptureDataHasNoEntityType() {
    Assertions.assertNull(MobCaptureManager.getEntityType(new ItemStack(Items.PAPER)));
  }

  @Test
  @DisplayName("Item stack with empty capture data has no entity type")
  void itemStackWithEmptyCaptureDataHasNoEntityType() {
    Assertions.assertNull(MobCaptureManager.getEntityType(createCaptureItem(new CompoundTag())));
  }

  @Test
  @DisplayName("Capture data of an unknown entity has no entity type")
  void captureDataOfUnknownEntityHasNoEntityType() {
    Assertions.assertNull(
        MobCaptureManager.getEntityType(createCaptureItem("easy_mob_farm:does_not_exist")));
  }

  @Test
  @DisplayName("Capture data of a known entity is resolved")
  void captureDataOfKnownEntityIsResolved() {
    Assertions.assertEquals(
        EntityType.COW, MobCaptureManager.getEntityType(createCaptureItem("minecraft:cow")));
  }

  @Test
  @DisplayName("Legacy card is upgraded to the current card data version")
  void legacyCardIsUpgradedToCurrentCardDataVersion() {
    ItemStack legacyCard = createLegacyPigCard(UUID.randomUUID(), 6000);

    Assertions.assertTrue(MobCaptureManager.upgradeMobCaptureCard(legacyCard));
    Assertions.assertEquals(
        MobCaptureManager.CARD_DATA_VERSION,
        legacyCard.getTag().getInt(MobCaptureManager.CARD_DATA_VERSION_TAG));
    CompoundTag upgradedData = MobCaptureManager.getMobCaptureData(legacyCard).data();
    Assertions.assertFalse(upgradedData.contains("UUID"));
    Assertions.assertFalse(upgradedData.contains("Age"));
  }

  @Test
  @DisplayName("Upgraded legacy cards of the same mob stack with each other")
  void upgradedLegacyCardsOfTheSameMobStack() {
    ItemStack firstCard = createLegacyPigCard(UUID.randomUUID(), 0);
    ItemStack secondCard = createLegacyPigCard(UUID.randomUUID(), 6000);
    Assertions.assertFalse(ItemStack.isSameItemSameTags(firstCard, secondCard));

    MobCaptureManager.upgradeMobCaptureCard(firstCard);
    MobCaptureManager.upgradeMobCaptureCard(secondCard);

    Assertions.assertTrue(ItemStack.isSameItemSameTags(firstCard, secondCard));
  }

  @Test
  @DisplayName("Current card is not upgraded again")
  void currentCardIsNotUpgradedAgain() {
    ItemStack legacyCard = createLegacyPigCard(UUID.randomUUID(), 0);
    MobCaptureManager.upgradeMobCaptureCard(legacyCard);

    Assertions.assertFalse(MobCaptureManager.upgradeMobCaptureCard(legacyCard));
  }

  @Test
  @DisplayName("Item without capture data is not upgraded")
  void itemWithoutCaptureDataIsNotUpgraded() {
    Assertions.assertFalse(MobCaptureManager.upgradeMobCaptureCard(new ItemStack(Items.PAPER)));
  }
}
