package io.leaderli.litil.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LiMapUtilTest extends Assert {

    @Test
    public void test() {
        Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "1");
        map1.put("2", "1");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("1", "10");
        map2.put("3", "3");

        assertEquals("{1=1, 2=1}", map1.toString());
        Map<String, Object> override = LiMapUtil.override(map1, map2);
        assertEquals("{1=10, 2=1}", override.toString());
    }



    @Test
    public void getTypeList() {
        Map<String, Object> map = new HashMap<>();
        map.put("k1", "1");
        List<String> list = new ArrayList<>();
        list.add("1");
        map.put("k2", list);

        assertTrue( LiMapUtil.getTypeList(map, "k1", String.class).isEmpty());
        assertEquals(1, LiMapUtil.getTypeList(map, "k2", String.class).size());
        assertTrue( LiMapUtil.getTypeList(map, "k2", Integer.class).isEmpty());
        assertTrue( LiMapUtil.getTypeList(map, "k3", Integer.class).isEmpty());
        assertTrue( LiMapUtil.getTypeList(null, "k3", Integer.class).isEmpty());
    }


    @Test
    public void getTypeObject() {
        Map<String, Object> map = new HashMap<>();
        assertFalse(LiMapUtil.getTypeObject(map, "k1", String.class).isPresent());
        Map<String, String> map2 = new HashMap<>();
        assertFalse(LiMapUtil.getTypeObject(map2, "k1").isPresent());

        assertFalse(LiMapUtil.getTypeObject(null, "k1").isPresent());
    }

    @Test
    public void getTypeMap1(){

        Map<String, Object> map = new HashMap<>();
        assertTrue( LiMapUtil.getTypeMap(map, "k1").isEmpty());
    }

    @Test
    public void getTypeMap2(){

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> k1 = new HashMap<>();
        map.put("str_int", k1);
        k1.put("int", 1);
        assertTrue( LiMapUtil.getTypeMap(map, "str_int").isEmpty());
    }
    @Test
    public void getTypeMap3() {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> k2 = new HashMap<>();
        map.put("str_str", k2);
        k2.put("a", "a");
        assertEquals("a", LiMapUtil.getTypeMap(map, "str_str").get("a"));
    }
}
