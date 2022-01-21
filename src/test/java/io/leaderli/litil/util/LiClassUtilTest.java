package io.leaderli.litil.util;

import org.junit.Assert;
import org.junit.Test;

public class LiClassUtilTest {


    @Test
    public void testPrimitiveToWrapper() {

        Assert.assertSame(LiClassUtil.primitiveToWrapper(int.class), Integer.class);
        Assert.assertSame(LiClassUtil.primitiveToWrapper(void.class), Void.class);
        Assert.assertSame(LiClassUtil.primitiveToWrapper(String.class), String.class);
        Assert.assertNull(LiClassUtil.primitiveToWrapper(null));
    }

    @Test
    public void testIsAssignableFromOrIsWrapper() {

        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(null, null));
        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(null, String.class));
        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String.class, null));
        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(int.class, Integer.class));
        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(Integer.class, int.class));
        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(CharSequence.class, String.class));
        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String.class, CharSequence.class));

    }

    @Test
    public void getAppJars() {

        Assert.assertTrue("file:/".matches("^[^/]++/$"));
        Assert.assertTrue("jar:file:/".matches("^[^/]++/$"));
        Assert.assertFalse("/jar/".matches("^[^/]++/$"));
        Assert.assertTrue(LiClassUtil.getAppJars().size() > 0);


    }
}
