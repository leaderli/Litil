package io.leaderli.litil.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class LiClassUtilTest {
    @Test
    public void primitiveToWrapper() {
        Assert.assertNull(LiClassUtil.primitiveToWrapper(null));
        Assert.assertSame(LiClassUtil.primitiveToWrapper(int.class), Integer.class);
        Assert.assertSame(LiClassUtil.primitiveToWrapper(void.class), Void.class);
        Assert.assertSame(LiClassUtil.primitiveToWrapper(String.class), String.class);
        Assert.assertNull(LiClassUtil.primitiveToWrapper(null));

        Assert.assertSame(LiClassUtil.primitiveToWrapper(int[].class), Integer[].class);
        Assert.assertSame(LiClassUtil.primitiveToWrapper(String[].class), String[].class);

    }

    @Test
    public void isAssignableFromOrIsWrapper() {

        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(null, null));
        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(null, String.class));
        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String.class, null));
        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String.class, CharSequence.class));
        Assert.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String[].class, CharSequence.class));

        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(int.class, Integer.class));
        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(Integer.class, int.class));
        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(CharSequence.class, String.class));
        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(CharSequence[].class, String[].class));
        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(int[].class, Integer[].class));

    }

    @Test
    public void getAppJars() {

        Assert.assertTrue("file:/".matches("^[^/]++/$"));
        Assert.assertTrue("jar:file:/".matches("^[^/]++/$"));
        Assert.assertFalse("/jar/".matches("^[^/]++/$"));
        Assert.assertTrue(LiClassUtil.getAppJars().size() > 0);


    }

    @Test
    public void array() {

        Assert.assertSame(Integer[].class, LiClassUtil.array(Integer.class, 0).getClass());
        Assert.assertSame(Integer[].class, LiClassUtil.array(int.class, 0).getClass());

        Assert.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(CharSequence[].class, LiClassUtil.array(String.class, 0).getClass()));
    }

    @Test
    public void cast() {

        Object a = "123";
        Assert.assertEquals("123", LiClassUtil.cast(a, String.class));
        Assert.assertNull(LiClassUtil.cast(a, int.class));


        a = 1;
        Assert.assertEquals(Integer.valueOf(1), LiClassUtil.cast(a, Integer.class));
        Assert.assertEquals(Integer.valueOf(1), LiClassUtil.cast(a, int.class));
    }


    @Test
    public void filterCanCast() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add("2");
        list.add(3);
        Assert.assertSame(2, LiClassUtil.filterCanCast(list, Integer.class).size());
        Assert.assertSame(2, LiClassUtil.filterCanCast(list, int.class).size());
        Assert.assertSame(3, LiClassUtil.filterCanCast(list, Object.class).size());

    }


    @Test
    public void filterCanCastMap() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", "1");
        map.put(2, 2);
        Assert.assertEquals(1, LiClassUtil.filterCanCast(map, String.class, String.class).size());
        Assert.assertEquals(1, LiClassUtil.filterCanCast(map, int.class, int.class).size());
        Assert.assertEquals(0, LiClassUtil.filterCanCast(map, String.class, int.class).size());
    }


}
