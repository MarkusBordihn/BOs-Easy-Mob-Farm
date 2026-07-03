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

package de.markusbordihn.easymobfarm.gametest;

import de.markusbordihn.easymobfarm.Constants;
import java.util.function.Consumer;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber()
public class ModGameTests {

  public static final DeferredRegister<Consumer<GameTestHelper>> TEST_FUNCTIONS =
      DeferredRegister.create(BuiltInRegistries.TEST_FUNCTION, Constants.MOD_ID);

  public static final DeferredHolder<Consumer<GameTestHelper>, Consumer<GameTestHelper>>
      ITEM_CAPABILITY_EXTRACTION =
          TEST_FUNCTIONS.register(
              "item_capability_extraction",
              () -> ItemCapabilityTest::testItemCapabilityExtraction);

  private static final Logger log = LogManager.getLogger(Constants.LOG_NAME);

  private static final Identifier GAME_TEST_STRUCTURE_3X3X3 =
      Identifier.fromNamespaceAndPath(Constants.MOD_ID, "gametest.3x3x3");

  @SubscribeEvent
  public static void registerGameTests(RegisterGameTestsEvent event) {
    log.debug("{} Game Tests for Easy Mob Farm", Constants.LOG_REGISTER_PREFIX);

    Holder<TestEnvironmentDefinition<?>> defaultEnvironment =
        event.registerEnvironment(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "default"));

    event.registerTest(
        ITEM_CAPABILITY_EXTRACTION.getId(),
        new FunctionGameTestInstance(
            ITEM_CAPABILITY_EXTRACTION.getKey(),
            new TestData<>(defaultEnvironment, GAME_TEST_STRUCTURE_3X3X3, 100, 0, true)));
  }
}
