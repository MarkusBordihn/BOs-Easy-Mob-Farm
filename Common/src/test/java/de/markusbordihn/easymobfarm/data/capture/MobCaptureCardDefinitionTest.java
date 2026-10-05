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

import de.markusbordihn.easymobfarm.TestBootstrap;
import io.netty.buffer.Unpooled;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Rarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MobCaptureCardDefinitionTest {

  @BeforeAll
  static void bootstrap() {
    TestBootstrap.bootstrapWithBoundItemComponents();
  }

  private static MobCaptureCardDefinition.Color color(String path) {
    return new MobCaptureCardDefinition.Color(Identifier.withDefaultNamespace(path));
  }

  private static MobCaptureCardDefinition roundTrip(MobCaptureCardDefinition definition) {
    FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
    definition.encode(buffer);
    MobCaptureCardDefinition decoded = MobCaptureCardDefinition.decode(buffer);
    Assertions.assertEquals(0, buffer.readableBytes());
    return decoded;
  }

  @Test
  void definitionWithVariantsAndColorsSurvivesNetworkRoundTrip() {
    MobCaptureCardDefinition definition =
        new MobCaptureCardDefinition(
            Identifier.withDefaultNamespace("cat"),
            Identifier.withDefaultNamespace("item/easy_mob_farm/mob_capture_card/cat_tabby"),
            Rarity.RARE,
            1.5F,
            true,
            false,
            true,
            Map.of(
                "minecraft:british_shorthair",
                new MobCaptureCardDefinition.Variant(
                    Identifier.withDefaultNamespace(
                        "item/easy_mob_farm/mob_capture_card/cat_british_shorthair"),
                    Map.of("red", color("item/cat_red"), "blue", color("item/cat_blue"))),
                "minecraft:calico",
                new MobCaptureCardDefinition.Variant(
                    Identifier.withDefaultNamespace("item/easy_mob_farm/mob_capture_card/cat_calico"),
                    Map.of())),
            Map.of("white", color("item/sheep_white"), "black", color("item/sheep_black")));

    MobCaptureCardDefinition decoded = roundTrip(definition);

    Assertions.assertEquals(definition, decoded);
    Assertions.assertEquals(EntityTypes.CAT, decoded.entityType());
  }

  @Test
  void definitionWithoutVariantsAndColorsSurvivesNetworkRoundTrip() {
    MobCaptureCardDefinition definition =
        new MobCaptureCardDefinition(
            Identifier.withDefaultNamespace("cow"), null, Rarity.COMMON, 1.0F, false, false, false,
            Map.of(), Map.of());

    Assertions.assertEquals(definition, roundTrip(definition));
  }

  @Test
  void decodingMoreColorsThanAllowedFails() {
    Map<String, MobCaptureCardDefinition.Color> colors = new HashMap<>();
    for (int i = 0; i <= MobCaptureCardDefinition.MAXIMUM_COLORS; i++) {
      colors.put("color_" + i, color("item/color_" + i));
    }
    FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
    buffer.writeIdentifier(Identifier.withDefaultNamespace("sheep"));
    buffer.writeIdentifier(Identifier.withDefaultNamespace("item/sheep"));
    buffer.writeEnum(Rarity.COMMON);
    buffer.writeFloat(1.0F);
    buffer.writeBoolean(false);
    buffer.writeBoolean(false);
    buffer.writeBoolean(false);
    buffer.writeVarInt(colors.size());
    colors.forEach(
        (name, color) -> {
          buffer.writeUtf(name);
          buffer.writeIdentifier(color.model());
        });
    buffer.writeVarInt(0);

    Assertions.assertThrows(RuntimeException.class, () -> MobCaptureCardDefinition.decode(buffer));
  }
}
