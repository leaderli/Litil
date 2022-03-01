package io.leaderli.litil.meta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author leaderli
 * @since 2022/1/23
 */
public class LiraTest {


    @Test
    public void isEmpty() {

        Assertions.assertFalse(Lira.of().isPresent());
        Assertions.assertFalse(Lira.of((Object) null).isPresent());
        Assertions.assertTrue(Lira.of(1).isPresent());
    }

    @Test
    public void of() {

        Assertions.assertSame(Lira.none(), Lira.of());
        Assertions.assertSame(Lira.none(), Lira.of((Object) null));
        Assertions.assertSame(Lira.none(), Lira.of(Collections.emptyIterator()));
        Assertions.assertSame(Lira.none(), Lira.of(Collections.emptyList()));
        Assertions.assertNotSame(Lira.none(), Lira.of(1));
        Assertions.assertNotSame(Lira.of(1), Lira.of(1));
    }


    @Test
    public void map() {

        Lira.<String>none().map(String::length);
        Assertions.assertSame(2, Lira.of(1, 2, 3).map(i -> i + 1).first().get());
        Lira.of(0).safe_map(i -> 5 / i, false).get();
        Lira.of(0).safe_map(i -> 5 / i).get();
        Assertions.assertThrows(ArithmeticException.class, () -> {
            Lira.of(0).map(i -> 5 / i);
        });
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void toMap() {

        Map map = new HashMap<>();
        map.put("1", "1");
        map.put("2", 1);
        map.put(3, "1");

        Assertions.assertEquals(1, Lira.of(map).cast(String.class, String.class).size());
        Assertions.assertEquals(0, Lira.of(map).cast(Boolean.class, String.class).size());
        Assertions.assertEquals(Lira.none(), Lira.none().cast(Boolean.class, String.class));
    }

    @Test
    public void cast_map() {
        List<Object> objs = Arrays.asList(1, "2", 3);
        Lira<Object> of = Lira.of(objs);


        Assertions.assertEquals("1a", of.cast_map(Integer.class, i -> i + "a").first().get());


    }

    @Test
    public void cast() {
        List<Object> objs = Arrays.asList(1, 2, 3);
        Lira<Object> of = Lira.of(objs);

        Lira<Integer> cast = of.cast(Integer.class);

        assertEquals(3, cast.size());


        objs = Arrays.asList(1, "2", 3);
        of = Lira.of(objs);

        assertEquals(2, of.cast(Integer.class).size());
    }

    @Test
    public void toArray() {

        Lira.none().forEach(no -> Assertions.fail());
        Lira.none().forEachLino(no -> Assertions.fail());
        Assertions.assertEquals(0, Lira.<Integer>none().stream().count());
        Assertions.assertSame(Integer[].class, Lira.<Integer>none().toArray(Integer.class).getClass());
    }

    @Test
    public void filter() {

        Assertions.assertTrue(Lira.of(1, 2, 3).filter(i -> i > 4).isEmpty());
        Assertions.assertSame(2, Lira.of(1, 2, 3).filter(i -> i > 1).size());
        Assertions.assertSame(2, Lira.of(1, 2, 3).filter(i -> i > 1).get().get(0).get());

        Assertions.assertEquals(3, Lira.of(1, 2, 3).trim().size());
        Assertions.assertEquals(2, Lira.of(1, null, 3).trim().size());
        Assertions.assertEquals(0, Lira.of((Object) null).trim().size());
    }

    @Test
    public void first() {

        Assertions.assertSame(Lira.none().first(), Lino.none());
        Assertions.assertSame(1, Lira.of(1).first().get());
        Assertions.assertSame(2, Lira.of(1, 2, 3).first(i -> i > 1).get());
    }

    @Test
    public void getOrOther() {

        Lira<String> none = Lira.none();

        Assertions.assertEquals("1", none.getOrOther("1").get(0).get());
        Assertions.assertEquals("1", Lira.of("1", "2").getOrOther(Arrays.asList("5", "4")).get(0).get());

    }

    @Test
    public void get() {

        Assertions.assertTrue(Lira.none().get().isEmpty());
        Assertions.assertFalse(Lira.of(1).get().isEmpty());
        Assertions.assertSame(2, Lira.of(1, 2).size());
    }


}
