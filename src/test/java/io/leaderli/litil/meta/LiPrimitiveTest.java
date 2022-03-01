package io.leaderli.litil.meta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LiPrimitiveTest {

    @Test
    @SuppressWarnings("all")
    public void test() {

        Assertions.assertEquals(0, (int) LiPrimitive.get(int.class));
        Assertions.assertEquals(0, (int) LiPrimitive.get(Integer.class));
        Assertions.assertEquals(0, (char) LiPrimitive.get(char.class));
        Assertions.assertEquals(0, (char) LiPrimitive.get(Character.class));
        Assertions.assertEquals(0, (byte) LiPrimitive.get(byte.class));
        Assertions.assertEquals(0, (byte) LiPrimitive.get(Byte.class));
        Assertions.assertEquals(0, (long) LiPrimitive.get(long.class));
        Assertions.assertEquals(0, (long) LiPrimitive.get(Long.class));

        Assertions.assertFalse(LiPrimitive.get(boolean.class));
        Assertions.assertFalse(LiPrimitive.get(Boolean.class));

        Assertions.assertEquals(0, LiPrimitive.get(double.class), 0.0);
        Assertions.assertEquals(0, LiPrimitive.get(Double.class), 0.0);

        Assertions.assertEquals(0, LiPrimitive.get(float.class), 0.0);
        Assertions.assertEquals(0, LiPrimitive.get(Float.class), 0.0);

        Assertions.assertEquals(0, (short) LiPrimitive.get(short.class));
        Assertions.assertEquals(0, (short) LiPrimitive.get(Short.class));

         Assertions.assertNull(LiPrimitive.get(Void.class));

         Assertions.assertNull(LiPrimitive.get(String.class));
    }

}
