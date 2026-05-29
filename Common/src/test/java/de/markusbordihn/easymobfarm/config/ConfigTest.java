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

package de.markusbordihn.easymobfarm.config;

import java.util.Properties;
import java.util.Set;
import net.minecraft.world.item.Rarity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigTest {

  @Test
  void stringValueTrimsWhitespace() {
    Properties props = new Properties();
    props.setProperty("key", "  hello  ");
    Assertions.assertEquals("hello", Config.parseConfigValue(props, "key", "default"));
  }

  @Test
  void missingStringKeyReturnsDefaultAndStoresIt() {
    Properties props = new Properties();
    String result = Config.parseConfigValue(props, "key", "fallback");
    Assertions.assertEquals("fallback", result);
    Assertions.assertEquals("fallback", props.getProperty("key"));
  }

  @Test
  void validIntegerIsParsed() {
    Properties props = new Properties();
    props.setProperty("key", "42");
    Assertions.assertEquals(42, Config.parseConfigValue(props, "key", 0));
  }

  @Test
  void invalidIntegerReturnsDefault() {
    Properties props = new Properties();
    props.setProperty("key", "not_a_number");
    Assertions.assertEquals(99, Config.parseConfigValue(props, "key", 99));
  }

  @Test
  void validFloatIsParsed() {
    Properties props = new Properties();
    props.setProperty("key", "1.5");
    Assertions.assertEquals(1.5f, Config.parseConfigValue(props, "key", 0.0f));
  }

  @Test
  void invalidFloatReturnsDefault() {
    Properties props = new Properties();
    props.setProperty("key", "abc");
    Assertions.assertEquals(2.0f, Config.parseConfigValue(props, "key", 2.0f));
  }

  @Test
  void trueStringIsParsed() {
    Properties props = new Properties();
    props.setProperty("key", "true");
    Assertions.assertTrue(Config.parseConfigValue(props, "key", false));
  }

  @Test
  void falseStringIsParsed() {
    Properties props = new Properties();
    props.setProperty("key", "false");
    Assertions.assertFalse(Config.parseConfigValue(props, "key", true));
  }

  @Test
  void commaSeparatedValuesParsedToSet() {
    Properties props = new Properties();
    props.setProperty("key", "a,b,c");
    Assertions.assertEquals(Set.of("a", "b", "c"), Config.parseConfigValue(props, "key", Set.of()));
  }

  @Test
  void spacesAroundCommasTrimmed() {
    Properties props = new Properties();
    props.setProperty("key", "a, b, c");
    Assertions.assertEquals(Set.of("a", "b", "c"), Config.parseConfigValue(props, "key", Set.of()));
  }

  @Test
  void emptyStringReturnsEmptySet() {
    Properties props = new Properties();
    props.setProperty("key", "");
    Assertions.assertEquals(Set.of(), Config.parseConfigValue(props, "key", Set.of("default")));
  }

  @Test
  void rarityParsedCaseInsensitive() {
    Properties props = new Properties();
    props.setProperty("key", "common");
    Assertions.assertEquals(Rarity.COMMON, Config.parseConfigValue(props, "key", Rarity.EPIC));
  }

  @Test
  void invalidRarityReturnsDefault() {
    Properties props = new Properties();
    props.setProperty("key", "legendary");
    Assertions.assertEquals(
        Rarity.UNCOMMON, Config.parseConfigValue(props, "key", Rarity.UNCOMMON));
  }
}
