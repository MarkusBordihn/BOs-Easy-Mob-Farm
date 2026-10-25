/*
 * Copyright 2024 Markus Bordihn
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

package de.markusbordihn.easymobfarm.data.loot;

import de.markusbordihn.easymobfarm.TestBootstrap;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinition;
import de.markusbordihn.easymobfarm.data.capture.MobCaptureCardDefinitionManager;
import de.markusbordihn.easymobfarm.item.mobcapturecard.MobCaptureCardItem;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MobFarmLootDisplayTest {

  private static MobCaptureCardItem mobCaptureCardItem;

  @BeforeAll
  static void bootstrap() {
    TestBootstrap.bootstrapWithBoundItemComponents();
    mobCaptureCardItem = Mockito.mock(MobCaptureCardItem.class);
    Mockito.when(mobCaptureCardItem.asItem()).thenReturn(Items.PAPER);
  }

  private static MobCaptureCardDefinition definition(
    String entity, Map<String, MobCaptureCardDefinition.Variant> variants) {
    return new MobCaptureCardDefinition(
      Identifier.withDefaultNamespace(entity),
      Identifier.withDefaultNamespace("item/easy_mob_farm/mob_capture_card/" + entity),
      Rarity.COMMON,
      1.0F,
      false,
      false,
      false,
      variants,
      Map.of());
  }

  private static MobCaptureCardDefinition.Variant variant(String model) {
    return new MobCaptureCardDefinition.Variant(
      Identifier.withDefaultNamespace("item/easy_mob_farm/mob_capture_card/" + model), Map.of());
  }

  @AfterEach
  void clearDefinitions() {
    MobCaptureCardDefinitionManager.clear();
  }

  @Test
  @DisplayName("Variant cards share one loot display per mob")
  void variantCardsShareOneLootDisplayPerMob() {
    MobCaptureCardDefinitionManager.addDefinition(
      Identifier.withDefaultNamespace("cow"), definition("cow", Map.of()));
    MobCaptureCardDefinitionManager.addDefinition(
      Identifier.withDefaultNamespace("cat"),
      definition(
        "cat",
        Map.of(
          "british_shorthair", variant("cat_british_shorthair"),
          "calico", variant("cat_calico"))));

    List<MobFarmLootDisplay> lootDisplays = MobFarmLootDisplay.createAll(mobCaptureCardItem);

    Map<EntityType<?>, Integer> captureCardCountByEntityType =
      lootDisplays.stream()
        .collect(
          Collectors.toMap(
            MobFarmLootDisplay::entityType,
            lootDisplay -> lootDisplay.captureCards().size()));
    Assertions.assertEquals(
      Map.of(EntityTypes.COW, 1, EntityTypes.CAT, 3), captureCardCountByEntityType);
  }

  @Test
  @DisplayName("Without card definitions no loot display is created")
  void withoutCardDefinitionsNoLootDisplayIsCreated() {
    Assertions.assertEquals(List.of(), MobFarmLootDisplay.createAll(mobCaptureCardItem));
  }
}
