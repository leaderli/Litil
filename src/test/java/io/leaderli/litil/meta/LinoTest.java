package io.leaderli.litil.meta;

import io.leaderli.litil.exception.LiMonoRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class LinoTest {


    @Test
    public void narrow() {

        Lino<CharSequence> narrow = Lino.narrow(Lino.<String>of(null));
        Assertions.assertSame(narrow, Lino.none());
    }

    @Test
    public void of() {
        Assertions.assertTrue(Lino.of(null).isEmpty());
        Assertions.assertFalse(Lino.of(1).isEmpty());
    }

    @Test
    public void get() {
        Assertions.assertNotNull(Lino.of(1).get());
        Assertions.assertNull(Lino.of(null).get());
    }

    @Test
    public void getOrElse() {
        Assertions.assertSame(1, Lino.of(null).getOrElse(1));
        Assertions.assertSame(2, Lino.of(2).getOrElse(1));
        Assertions.assertNotSame(1, Lino.of(2).getOrElse(1));
    }

    @Test
    public void testGetOrElse() {
        Assertions.assertSame(1, Lino.of(null).getOrElse(() -> 1));
        Assertions.assertSame(2, Lino.of(2).getOrElse(() -> 1));
        Assertions.assertNotSame(1, Lino.of(2).getOrElse(() -> 1));
    }

    @Test
    public void isEmpty() {
        Assertions.assertTrue(Lino.of(null).isEmpty());
        Assertions.assertFalse(Lino.of(1).isEmpty());
    }

    @Test
    public void isPresent() {
        Assertions.assertTrue(Lino.of(1).isPresent());
        Assertions.assertFalse(Lino.of(null).isPresent());
    }

    @Test
    public void stringPrefix() {
        Assertions.assertSame("None", Lino.of(null).stringPrefix());
    }

    @Test
    public void then() {
        Lino.of(null).then(e -> Assertions.fail());

        Lino.of(null).then(e -> {
        }).error(() -> {
        }).isEmpty();

        LiMonoRuntimeException thrown = Assertions.assertThrows(LiMonoRuntimeException.class, () -> {

            Lino.of(0).throwable(in -> {
                @SuppressWarnings("unused") int i = 1 / in;
                Assertions.fail();

            });
        });
        Assertions.assertEquals(thrown.getMessage(), "java.lang.ArithmeticException: / by zero");
    }

    @Test
    public void error() {
        Lino.of(1).error(Assertions::fail);
    }

    @Test
    public void orOther() {

        Assertions.assertSame(1, Lino.of(1).orOther(2).get());
        Assertions.assertSame(1, Lino.of(1).orOther(Lino.of(2)).get());
        Assertions.assertSame(2, Lino.of(null).orOther(2).get());
        Assertions.assertSame(2, Lino.of(null).orOther(Lino.of(2)).get());

    }

    @Test
    public void filter() {
        Lino<String> mono = Lino.of(null);

        Assertions.assertNull(mono.filter(Objects::nonNull).get());
        Assertions.assertNull(mono.filter(str -> str.length() == 4).get());

        mono = Lino.of("123");
        Assertions.assertNull(mono.filter(false).get());
        Assertions.assertNotNull(mono.filter(true).get());
        Assertions.assertNotNull(mono.filter(Objects::nonNull).get());
        Assertions.assertNull(mono.filter(str -> str.length() == 4).get());

//
        mono = Lino.of("123");
        Assertions.assertNotNull(mono.filter(Lino::of).get());


        Assertions.assertNull(mono.filter(it -> null).get());
        Assertions.assertNotNull(mono.filter(it -> 1).get());


        Assertions.assertNull(mono.filter(it -> new ArrayList<>()).get());
        Assertions.assertNotNull(mono.filter(it -> Arrays.asList(1, 2)).get());

        HashMap<Object, Object> test = new HashMap<>();
        Assertions.assertNull(mono.filter(it -> test).get());
        test.put("key", "value");
        Assertions.assertNotNull(mono.filter(it -> test).get());


        Assertions.assertTrue(Lino.of("1").same("1").isPresent());
        Assertions.assertTrue(Lino.of("2").same("1").isEmpty());
    }

    @Test
    public void cast() {
        Lino.of(null).cast(String.class).cast(Integer.class);
        Object a = 1;
        Assertions.assertSame(1, Lino.of(a).cast(Integer.class).get());

        Assertions.assertEquals("[1]", Arrays.toString(Lino.of(new String[]{"1"}).cast(Object[].class).get()));
    }


    @Test
    public void cast_map() {
        Lino.of(null).cast_map(String.class, s -> null);
        Object a = 1;
        Assertions.assertSame(2, Lino.of(a).cast_map(Integer.class, i -> ++i).get());
        Assertions.assertNull(Lino.of(a).cast_map(String.class, i -> i).get());
        Assertions.assertNull(Lino.of(a).cast_map(Integer.class, i -> null).get());


        Lino.of(0).safe_map(i -> 5 / i, false);
        Lino.of(0).safe_map(i -> 5 / i);
        Assertions.assertThrows(ArithmeticException.class, () -> {

            Lino.of(0).map(i -> 5 / i);
        });
    }

    @Test
    public void lira() {

        Assertions.assertSame(Lino.of(null).liraByArray(n -> new Object[]{n}), Lira.none());

        Assertions.assertEquals(Lira.of(1), Lino.of(1).liraByArray(n -> new Integer[]{n}));


        Lino<List<Integer>> of = Lino.of(Arrays.asList(1, 2));
        Lira<Integer> lira = of.lira(integers -> integers);
        Assertions.assertEquals(2, lira.size());
        lira = of.liraByIterator(List::iterator);
        Assertions.assertEquals(2, lira.size());

        Assertions.assertEquals("List[Some(1), Some(2)]", Lino.of(Arrays.asList(1, 2)).lira(Integer.class).toString());
        Assertions.assertEquals("Empty[]", Lino.of(Arrays.asList(1, 2)).lira(String.class).toString());

    }

    @Test
    public void licase() {

        Assertions.assertSame(LiCase.none(), Lino.none().toLiCase());

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void toMap() {

        Map map = new HashMap<>();
        map.put("1", "1");
        map.put("2", 1);
        map.put(3, "1");

        Assertions.assertEquals(1, Lino.of(map).cast(String.class, String.class).get().size());
        Assertions.assertTrue(Lino.of(map).cast(Boolean.class, String.class).isEmpty());
        Assertions.assertEquals(Lino.none(), Lino.of(null).cast(Boolean.class, String.class));
    }

    @Test
    public void testToString() {
        Assertions.assertSame("None", Lino.of(null).toString());
        Assertions.assertEquals("Some(1)", Lino.of(1).toString());
    }

    @Test
    public void eq() {
        Assertions.assertNotEquals(Lino.none(), null);
        Assertions.assertNotEquals(Lino.none(), Lino.of(1));

        Assertions.assertEquals(Lino.none(), Lino.of(null));
        Assertions.assertEquals(Lino.of(null), Lino.of(null));

        Assertions.assertEquals(Lino.of(1), Lino.of(1));

        Assertions.assertEquals(Lino.of(1), Lino.of(1));
        Assertions.assertEquals(Lino.of(null), Lino.of(null));
    }

    @Test
    public void some() {

        Assertions.assertSame(Lino.of(null), Lino.none());
        Assertions.assertSame(1, Lino.of(1).get());
    }


}
