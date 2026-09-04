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

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.entity.EntityTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MobCaptureDataSupportTest {

  @BeforeAll
  static void bootstrap() {
    SharedConstants.tryDetectVersion();
    Bootstrap.bootStrap();
  }

  @Test
  void knownEntityNameIsResolved() {
    Assertions.assertEquals(EntityTypes.COW, MobCaptureDataSupport.getEntityType("minecraft:cow"));
  }

  @Test
  void unknownEntityNameReturnsNull() {
    Assertions.assertNull(MobCaptureDataSupport.getEntityType("minecraft:does_not_exist"));
  }

  @Test
  void malformedEntityNameReturnsNull() {
    Assertions.assertNull(MobCaptureDataSupport.getEntityType("Not A Resource Location"));
  }

  @Test
  void nullEntityNameReturnsNull() {
    Assertions.assertNull(MobCaptureDataSupport.getEntityType((String) null));
  }

  @Test
  void emptyEntityNameReturnsNull() {
    Assertions.assertNull(MobCaptureDataSupport.getEntityType(""));
  }
}
