package com.leaderli.liutil.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("ConstantConditions")
public class LiCastUtilTest {

    @Test
    public void cast() {

        Object a = "123";
        Assert.assertEquals("123", LiCastUtil.cast(a, String.class));
        Assert.assertNull(LiCastUtil.cast(a, int.class));

        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add("2");
        list.add(3);
        a = list;

        assert LiCastUtil.cast(a, Integer.class) == null;
        assert LiCastUtil.cast(list, Integer.class).size() == 2;
        assert LiCastUtil.cast(a, int.class) == null;
    }


    @Test
    public void primitiveToWrapper() {
        Assert.assertNull(LiClassUtil.primitiveToWrapper(null));
    }

    @Test
    public void testCast() {
        Object a = 1;
        Assert.assertEquals(1, (int) LiCastUtil.cast(a, Integer.class));
        Assert.assertEquals(1, (int) LiCastUtil.cast(a, int.class));

    }


    @Test
    public void testCast1() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", "1");
        map.put(2, 2);
        Assert.assertEquals(1, LiCastUtil.cast(map, String.class, String.class).size());
    }
}
