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

package de.markusbordihn.easymobfarm.network.codec;

import com.mojang.datafixers.util.Function8;
import java.util.function.Function;
import net.minecraft.network.codec.StreamCodec;

public interface ModStreamCodec<B, T> extends StreamCodec<B, T> {
  static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite(
      StreamCodec<? super B, T1> codec1,
      Function<C, T1> getter1,
      StreamCodec<? super B, T2> codec2,
      Function<C, T2> getter2,
      StreamCodec<? super B, T3> codec3,
      Function<C, T3> getter3,
      StreamCodec<? super B, T4> codec4,
      Function<C, T4> getter4,
      StreamCodec<? super B, T5> codec5,
      Function<C, T5> getter5,
      StreamCodec<? super B, T6> codec6,
      Function<C, T6> getter6,
      StreamCodec<? super B, T7> codec7,
      Function<C, T7> getter7,
      StreamCodec<? super B, T8> codec8,
      Function<C, T8> getter8,
      Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> constructor) {
    return new StreamCodec<B, C>() {
      @Override
      public void encode(B buffer, C value) {
        codec1.encode(buffer, getter1.apply(value));
        codec2.encode(buffer, getter2.apply(value));
        codec3.encode(buffer, getter3.apply(value));
        codec4.encode(buffer, getter4.apply(value));
        codec5.encode(buffer, getter5.apply(value));
        codec6.encode(buffer, getter6.apply(value));
        codec7.encode(buffer, getter7.apply(value));
        codec8.encode(buffer, getter8.apply(value));
      }

      @Override
      public C decode(B buffer) {
        T1 value1 = codec1.decode(buffer);
        T2 value2 = codec2.decode(buffer);
        T3 value3 = codec3.decode(buffer);
        T4 value4 = codec4.decode(buffer);
        T5 value5 = codec5.decode(buffer);
        T6 value6 = codec6.decode(buffer);
        T7 value7 = codec7.decode(buffer);
        T8 value8 = codec8.decode(buffer);
        return constructor.apply(value1, value2, value3, value4, value5, value6, value7, value8);
      }
    };
  }
}
