package io.leaderli.litil.util;

import io.leaderli.litil.type.LiClassUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class LiClassUtilTest {
    @Test
    public void primitiveToWrapper() {
        Assertions.assertNull(LiClassUtil.primitiveToWrapper(null));
        Assertions.assertSame(LiClassUtil.primitiveToWrapper(int.class), Integer.class);
        Assertions.assertSame(LiClassUtil.primitiveToWrapper(void.class), Void.class);
        Assertions.assertSame(LiClassUtil.primitiveToWrapper(String.class), String.class);
        Assertions.assertNull(LiClassUtil.primitiveToWrapper(null));

        Assertions.assertSame(LiClassUtil.primitiveToWrapper(int[].class), Integer[].class);
        Assertions.assertSame(LiClassUtil.primitiveToWrapper(String[].class), String[].class);

    }

    @Test
    public void isAssignableFromOrIsWrapper() {

        Assertions.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(null, null));
        Assertions.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(null, String.class));
        Assertions.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String.class, null));
        Assertions.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String.class, CharSequence.class));
        Assertions.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(String[].class, CharSequence.class));

        Assertions.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(int.class, Integer.class));
        Assertions.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(Integer.class, int.class));
        Assertions.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(CharSequence.class, String.class));
        Assertions.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(CharSequence[].class, String[].class));
        Assertions.assertFalse(LiClassUtil.isAssignableFromOrIsWrapper(int[].class, Integer[].class));
        Assertions.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(int[].class, int[].class));


        System.out.println(int[].class.isAssignableFrom(Integer[].class));
        System.out.println(Integer[].class.isAssignableFrom(Integer[].class));
        System.out.println(Integer[].class.isAssignableFrom(int[].class));
    }

    @Test
    public void getAppJars() {

        Assertions.assertTrue("file:/".matches("^[^/]++/$"));
        Assertions.assertTrue("jar:file:/".matches("^[^/]++/$"));
        Assertions.assertFalse("/jar/".matches("^[^/]++/$"));
        Assertions.assertTrue(LiClassUtil.getAppJars().size() > 0);


    }

    @Test
    public void test() throws Throwable{

        for (String appJar : LiClassUtil.getAppJars()) {
            System.out.println(appJar);
        }

    }
    @Test
    public void array() {

        Assertions.assertSame(Integer[].class, LiClassUtil.array(Integer.class, 0).getClass());
        Assertions.assertSame(Integer[].class, LiClassUtil.array(int.class, 0).getClass());

        Assertions.assertTrue(LiClassUtil.isAssignableFromOrIsWrapper(CharSequence[].class, LiClassUtil.array(String.class, 0).getClass()));
    }

    @Test
    public void cast() {

        Object a = "123";
        Assertions.assertEquals("123", LiClassUtil.cast(a, String.class));
        Assertions.assertNull(LiClassUtil.cast(a, int.class));


        a = 1;
        Assertions.assertEquals(Integer.valueOf(1), LiClassUtil.cast(a, Integer.class));
        Assertions.assertEquals(Integer.valueOf(1), LiClassUtil.cast(a, int.class));
        System.out.println(LiClassUtil.cast(a,int.class).getClass());

        a = new int[]{1};

        System.out.println(LiClassUtil.cast(a, int[].class));


    }


    @Test
    public void filterCanCast() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add("2");
        list.add(3);
        Assertions.assertSame(2, LiClassUtil.filterCanCast(list, Integer.class).size());
        Assertions.assertSame(2, LiClassUtil.filterCanCast(list, int.class).size());
        Assertions.assertSame(3, LiClassUtil.filterCanCast(list, Object.class).size());

    }


    @Test
    public void filterCanCastMap() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", "1");
        map.put(2, 2);
        Assertions.assertEquals(1, LiClassUtil.filterCanCast(map, String.class, String.class).size());
        Assertions.assertEquals(1, LiClassUtil.filterCanCast(map, int.class, int.class).size());
        Assertions.assertEquals(0, LiClassUtil.filterCanCast(map, String.class, int.class).size());
    }


}
