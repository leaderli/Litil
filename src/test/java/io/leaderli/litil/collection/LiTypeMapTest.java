package io.leaderli.litil.collection;

import io.leaderli.litil.type.LiTypeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LiTypeMapTest {


    @Test
    public void test() {

        LiTypeMap liTypeMap = new LiTypeMap();

        String value = liTypeMap.get(String.class).get();


        Assertions.assertNull(value);

        liTypeMap.put(String.class, "");
        value = liTypeMap.get(String.class).get();
        Assertions.assertNotNull(value);
    }

    @Test
    public void computeIfAbsent() {
        LiTypeMap liTypeMap = new LiTypeMap();

        String value = liTypeMap.computeIfAbsent(String.class, () -> "123").get();
        Assertions.assertEquals("123", value);
    }

    @Test
    public void getMono() {
        LiTypeMap liTypeMap = new LiTypeMap();

        Assertions.assertTrue(liTypeMap.get(String.class).isEmpty());
        liTypeMap.put(String.class, "");
        Assertions.assertTrue(liTypeMap.get(String.class).isPresent());
    }

    @Test
    public void remove() {
        LiTypeMap liTypeMap = new LiTypeMap();

        Assertions.assertTrue(liTypeMap.get(String.class).isEmpty());
        String v1 = "";
        liTypeMap.put(String.class, v1);
        liTypeMap.put(CharSequence.class, "");

        Assertions.assertEquals("", liTypeMap.get(String.class).get());
        Assertions.assertEquals("", liTypeMap.get(CharSequence.class).get());

        liTypeMap.remove(String.class);

        Assertions.assertNull(liTypeMap.get(String.class).get());
        Assertions.assertEquals("", liTypeMap.get(CharSequence.class).get());
    }
}
