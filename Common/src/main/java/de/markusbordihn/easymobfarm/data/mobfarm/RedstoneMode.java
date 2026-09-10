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

package de.markusbordihn.easymobfarm.data.mobfarm;

import java.util.Locale;
import net.minecraft.util.StringRepresentable;

public enum RedstoneMode implements StringRepresentable {
  DISABLE_ON_SIGNAL,
  ENABLE_ON_SIGNAL,
  IGNORE;

  public static final RedstoneMode DEFAULT = DISABLE_ON_SIGNAL;

  private final String name;

  RedstoneMode() {
    this.name = this.name().toLowerCase(Locale.ROOT);
  }

  public static RedstoneMode byName(String name) {
    if (name == null || name.isEmpty()) {
      return DEFAULT;
    }
    for (RedstoneMode redstoneMode : values()) {
      if (redstoneMode.name().equals(name)) {
        return redstoneMode;
      }
    }
    return DEFAULT;
  }

  public static RedstoneMode byOrdinal(int ordinal) {
    if (ordinal < 0 || ordinal >= values().length) {
      return DEFAULT;
    }
    return values()[ordinal];
  }

  public String getId() {
    return this.name;
  }

  public RedstoneMode next() {
    return values()[(this.ordinal() + 1) % values().length];
  }

  public boolean allowsProcessing(boolean isPowered) {
    return switch (this) {
      case DISABLE_ON_SIGNAL -> !isPowered;
      case ENABLE_ON_SIGNAL -> isPowered;
      case IGNORE -> true;
    };
  }

  @Override
  public String getSerializedName() {
    return this.name;
  }
}
