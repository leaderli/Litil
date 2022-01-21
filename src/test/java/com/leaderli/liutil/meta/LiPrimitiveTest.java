package com.leaderli.liutil.meta;

import org.junit.Assert;
import org.junit.Test;

import static com.leaderli.liutil.meta.LiPrimitive.get;

public class LiPrimitiveTest extends Assert {

    @Test
    public void test() {

         Assert.assertEquals(0, (int) get(int.class));
         Assert.assertEquals(0, (int) get(Integer.class));
         Assert.assertEquals(0, (char) get(char.class));
         Assert.assertEquals(0, (char) get(Character.class));
         Assert.assertEquals(0, (byte) get(byte.class));
         Assert.assertEquals(0, (byte) get(Byte.class));
         Assert.assertEquals(0, (long) get(long.class));
         Assert.assertEquals(0, (long) get(Long.class));

         Assert.assertFalse(get(boolean.class));
         Assert.assertFalse(get(Boolean.class));

         Assert.assertEquals(0, get(double.class), 0.0);
         Assert.assertEquals(0, get(Double.class), 0.0);

         Assert.assertEquals(0, get(float.class), 0.0);
         Assert.assertEquals(0, get(Float.class), 0.0);

         Assert.assertEquals(0, (short) get(short.class));
         Assert.assertEquals(0, (short) get(Short.class));

         Assert.assertNull(get(void.class));
         Assert.assertNull(get(Void.class));

         Assert.assertNull(get(String.class));
    }

}
