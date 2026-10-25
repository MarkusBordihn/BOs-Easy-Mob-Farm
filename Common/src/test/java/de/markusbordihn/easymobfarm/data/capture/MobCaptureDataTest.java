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

import io.netty.buffer.Unpooled;
import net.minecraft.SharedConstants;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Rarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MobCaptureDataTest {

  private static final int MAX_ID_LIMIT = 16777216;
  private static final String UNINSTALLED_MOD_TYPE = "uninstalled_mod:dragon";

  @BeforeAll
  static void bootstrap() {
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
  }

  private static MobCaptureData card(String type, String variant, boolean isFoil) {
    return new MobCaptureData(null, type, null, null, null, variant, Rarity.COMMON, isFoil);
  }

  private static MobCaptureData roundTrip(MobCaptureData mobCaptureData) {
    Tag tag = MobCaptureData.CODEC.encodeStart(NbtOps.INSTANCE, mobCaptureData).getOrThrow();
    return MobCaptureData.CODEC.parse(NbtOps.INSTANCE, tag).getOrThrow();
  }

  @Test
  void cardIdIsConsistentForSameInputs() {
    MobCaptureData data = card("minecraft:bee", null, false);
    Assertions.assertEquals(data.getCardId(), data.getCardId());
  }

  @Test
  void cardIdIsInValidRange() {
    Assertions.assertTrue(card("minecraft:bee", null, false).getCardId() >= 0);
    Assertions.assertTrue(card("minecraft:bee", null, false).getCardId() < MAX_ID_LIMIT);
  }

  @Test
  void differentTypesProduceDifferentIds() {
    Assertions.assertNotEquals(
        card("minecraft:bee", null, false).getCardId(),
        card("minecraft:cow", null, false).getCardId());
  }

  @Test
  void foilChangesCardId() {
    Assertions.assertNotEquals(
        card("minecraft:bee", null, false).getCardId(),
        card("minecraft:bee", null, true).getCardId());
  }

  @Test
  void variantChangesCardId() {
    Assertions.assertNotEquals(
        card("minecraft:pillager", null, false).getCardId(),
        card("minecraft:pillager", "leader", false).getCardId());
  }

  @Test
  void nullTypeProducesStableId() {
    MobCaptureData data = card(null, null, false);
    int id = data.getCardId();
    Assertions.assertTrue(id >= 0 && id < MAX_ID_LIMIT);
    Assertions.assertEquals(id, data.getCardId());
  }

  @Test
  void codecKeepsEmptyDataCompound() {
    MobCaptureData data =
        new MobCaptureData(
            "Bee",
            "minecraft:bee",
            EntityType.BEE,
            new CompoundTag(),
            MobColor.NONE,
            "",
            Rarity.COMMON,
            false);

    Assertions.assertEquals(data, roundTrip(data));
  }

  @Test
  void codecStoresAllPresentFields() {
    CompoundTag entityData = new CompoundTag();
    entityData.putString("id", "minecraft:sheep");
    MobCaptureData data =
        new MobCaptureData(
            "Sheep",
            "minecraft:sheep",
            EntityType.SHEEP,
            entityData,
            MobColor.BLACK,
            "leader",
            Rarity.RARE,
            true);

    Assertions.assertEquals(data, roundTrip(data));
  }

  @Test
  @DisplayName("Card of an uninstalled mod keeps its type through the codec")
  void codecKeepsCardOfUninstalledMod() {
    MobCaptureData data =
        new MobCaptureData(
            "Dragon",
            UNINSTALLED_MOD_TYPE,
            null,
            new CompoundTag(),
            MobColor.NONE,
            "",
            Rarity.COMMON,
            false);

    Assertions.assertEquals(UNINSTALLED_MOD_TYPE, roundTrip(data).type());
  }

  @Test
  @DisplayName("Stored unregistered entity type decodes without error")
  void codecDecodesUnregisteredEntityType() {
    CompoundTag tag = new CompoundTag();
    tag.putString("name", "Dragon");
    tag.putString("type", UNINSTALLED_MOD_TYPE);
    tag.putString("entityType", UNINSTALLED_MOD_TYPE);

    MobCaptureData data = MobCaptureData.CODEC.parse(NbtOps.INSTANCE, tag).getOrThrow();

    Assertions.assertEquals(UNINSTALLED_MOD_TYPE, data.type());
    Assertions.assertNull(data.entityType());
  }

  @Test
  @DisplayName("Missing entity type is resolved from the stored type")
  void codecResolvesMissingEntityTypeFromType() {
    CompoundTag tag = new CompoundTag();
    tag.putString("name", "Bee");
    tag.putString("type", "minecraft:bee");

    Assertions.assertEquals(
        EntityType.BEE, MobCaptureData.CODEC.parse(NbtOps.INSTANCE, tag).getOrThrow().entityType());
  }

  @Test
  @DisplayName("Card of an uninstalled mod survives network sync")
  void streamCodecKeepsCardOfUninstalledMod() {
    MobCaptureData data =
        new MobCaptureData(
            "Dragon",
            UNINSTALLED_MOD_TYPE,
            null,
            new CompoundTag(),
            MobColor.NONE,
            "",
            Rarity.COMMON,
            false);
    RegistryFriendlyByteBuf buffer =
        new RegistryFriendlyByteBuf(
            Unpooled.buffer(), RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY));

    MobCaptureData.STREAM_CODEC.encode(buffer, data);

    Assertions.assertEquals(data, MobCaptureData.STREAM_CODEC.decode(buffer));
  }
}
