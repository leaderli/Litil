package io.leaderli.litil.meta;

import org.junit.Assert;
import org.junit.Test;

public class LiPrimitiveTest extends Assert {

    @Test
    public void test() {

         Assert.assertEquals(0, (int) LiPrimitive.get(int.class));
         Assert.assertEquals(0, (int) LiPrimitive.get(Integer.class));
         Assert.assertEquals(0, (char) LiPrimitive.get(char.class));
         Assert.assertEquals(0, (char) LiPrimitive.get(Character.class));
         Assert.assertEquals(0, (byte) LiPrimitive.get(byte.class));
         Assert.assertEquals(0, (byte) LiPrimitive.get(Byte.class));
         Assert.assertEquals(0, (long) LiPrimitive.get(long.class));
         Assert.assertEquals(0, (long) LiPrimitive.get(Long.class));

         Assert.assertFalse(LiPrimitive.get(boolean.class));
         Assert.assertFalse(LiPrimitive.get(Boolean.class));

         Assert.assertEquals(0, LiPrimitive.get(double.class), 0.0);
         Assert.assertEquals(0, LiPrimitive.get(Double.class), 0.0);

         Assert.assertEquals(0, LiPrimitive.get(float.class), 0.0);
         Assert.assertEquals(0, LiPrimitive.get(Float.class), 0.0);

         Assert.assertEquals(0, (short) LiPrimitive.get(short.class));
         Assert.assertEquals(0, (short) LiPrimitive.get(Short.class));

         Assert.assertNull(LiPrimitive.get(void.class));
         Assert.assertNull(LiPrimitive.get(Void.class));

         Assert.assertNull(LiPrimitive.get(String.class));
    }

}
