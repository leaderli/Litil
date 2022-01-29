package io.leaderli.litil.meta;

import io.leaderli.litil.exception.LiMonoRuntimeException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

public class LinoTest {


    @Test
    public void narrow() {

        Lino<CharSequence> narrow = Lino.narrow(Lino.<String>of(null));
        Assert.assertSame(narrow, Lino.none());
    }

    @Test
    public void of() {
        Assert.assertTrue(Lino.of(null).isEmpty());
        Assert.assertFalse(Lino.of(1).isEmpty());
    }

    @Test
    public void get() {
        Assert.assertNotNull(Lino.of(1).get());
        Assert.assertNull(Lino.of(null).get());
    }

    @Test
    public void getOrElse() {
        Assert.assertSame(1, Lino.of(null).getOrElse(1));
        Assert.assertSame(2, Lino.of(2).getOrElse(1));
        Assert.assertNotSame(1, Lino.of(2).getOrElse(1));
    }

    @Test
    public void testGetOrElse() {
        Assert.assertSame(1, Lino.of(null).getOrElse(() -> 1));
        Assert.assertSame(2, Lino.of(2).getOrElse(() -> 1));
        Assert.assertNotSame(1, Lino.of(2).getOrElse(() -> 1));
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(Lino.of(null).isEmpty());
        Assert.assertFalse(Lino.of(1).isEmpty());
    }

    @Test
    public void isPresent() {
        Assert.assertTrue(Lino.of(1).isPresent());
        Assert.assertFalse(Lino.of(null).isPresent());
    }

    @Test
    public void stringPrefix() {
        Assert.assertSame("None", Lino.of(null).stringPrefix());
    }

    @Test
    public void then() {
        Lino.of(null).then(e -> Assert.fail());

        Lino.of(null).then(e -> {
        }).error(() -> {
        }).isEmpty();

        thrown.expect(LiMonoRuntimeException.class);
        thrown.expectMessage("java.lang.ArithmeticException: / by zero");
        Lino.of(0).throwable(in -> {
            @SuppressWarnings("unused") int i = 1 / in;
            Assert.fail();

        });
    }

    @Test
    public void error() {
        Lino.of(1).error(Assert::fail);

    }

    @Test
    public void orOther() {

        Assert.assertSame(1, Lino.of(1).orOther(2).get());
        Assert.assertSame(1, Lino.of(1).orOther(Lino.of(2)).get());
        Assert.assertSame(2, Lino.of(null).orOther(2).get());
        Assert.assertSame(2, Lino.of(null).orOther(Lino.of(2)).get());

    }

    @Test
    public void filter() {
        Lino<String> mono = Lino.of(null);

        Assert.assertNull(mono.filter(Objects::nonNull).get());
        Assert.assertNull(mono.filter(str -> str.length() == 4).get());

        mono = Lino.of("123");
        Assert.assertNull(mono.filter(false).get());
        Assert.assertNotNull(mono.filter(true).get());
        Assert.assertNotNull(mono.filter(Objects::nonNull).get());
        Assert.assertNull(mono.filter(str -> str.length() == 4).get());

//
        mono = Lino.of("123");
        Assert.assertNotNull(mono.filter(Lino::of).get());


        Assert.assertNull(mono.filter(it -> null).get());
        Assert.assertNotNull(mono.filter(it -> 1).get());


        Assert.assertNull(mono.filter(it -> new ArrayList<>()).get());
        Assert.assertNotNull(mono.filter(it -> Arrays.asList(1, 2)).get());

        HashMap<Object, Object> test = new HashMap<>();
        Assert.assertNull(mono.filter(it -> test).get());
        test.put("key", "value");
        Assert.assertNotNull(mono.filter(it -> test).get());


        String s = "1";

        Assert.assertTrue(Lino.of(s).same(s).isPresent());
        //noinspection StringOperationCanBeSimplified
        Assert.assertTrue(Lino.of(s).same(new String("1")).isEmpty());
        Assert.assertTrue(Lino.of(s).equalsTo("1").isPresent());
    }

    @Test
    public void cast() {
        Lino.of(null).cast(String.class).cast(Integer.class);
        Object a = 1;
        Assert.assertSame(1, Lino.of(a).cast(Integer.class).get());
    }


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void cast_map() {
        Lino.of(null).cast_map(String.class, s -> null);
        Object a = 1;
        Assert.assertSame(2, Lino.of(a).cast_map(Integer.class, i -> ++i).get());
        Assert.assertNull(Lino.of(a).cast_map(String.class, i -> i).get());
        Assert.assertNull(Lino.of(a).cast_map(Integer.class, i -> null).get());


        Lino.of(0).safe_map(i -> 5 / i, false);
        Lino.of(0).safe_map(i -> 5 / i);
        thrown.expect(ArithmeticException.class);
        Lino.of(0).map(i -> 5 / i);

    }

    @Test
    public void lira() {

        Assert.assertSame(Lino.of(null).liraByArray(n -> new Object[]{n}), Lira.none());

        Assert.assertEquals(Lira.of(1), Lino.of(1).liraByArray(n -> new Integer[]{n}));


        Lino<List<Integer>> of = Lino.of(Arrays.asList(1, 2));
        Lira<Integer> lira = of.lira(integers -> integers);
        Assert.assertEquals(2, lira.size());
        lira = of.liraByIterator(List::iterator);
        Assert.assertEquals(2, lira.size());

        Assert.assertEquals("List[Some(1), Some(2)]", Lino.of(Arrays.asList(1, 2)).lira(Integer.class).toString());
        Assert.assertEquals("Empty[]", Lino.of(Arrays.asList(1, 2)).lira(String.class).toString());

    }

    @Test
    public void licase() {

        Assert.assertSame(LiCase.none(), Lino.none().toLiCase());

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void toMap() {

        Map map = new HashMap<>();
        map.put("1", "1");
        map.put("2", 1);
        map.put(3, "1");

        Assert.assertEquals(1, Lino.of(map).cast(String.class, String.class).get().size());
        Assert.assertTrue(Lino.of(map).cast(Boolean.class, String.class).isEmpty());
        Assert.assertEquals(Lino.none(), Lino.of(null).cast(Boolean.class, String.class));
    }

    @Test
    public void testToString() {
        Assert.assertSame("None", Lino.of(null).toString());
        Assert.assertEquals("Some(1)", Lino.of(1).toString());
    }

    @Test
    public void eq() {
        Assert.assertNotEquals(Lino.none(), null);
        Assert.assertNotEquals(Lino.none(), Lino.of(1));

        Assert.assertEquals(Lino.none(), Lino.of(null));
        Assert.assertEquals(Lino.of(null), Lino.of(null));

        Assert.assertEquals(Lino.of(1), Lino.of(1));

        Assert.assertEquals(Lino.of(1), Lino.of(1));
        Assert.assertEquals(Lino.of(null), Lino.of(null));
    }

    @Test
    public void some() {

        Assert.assertSame(Lino.of(null), Lino.none());
        Assert.assertSame(1, Lino.of(1).get());
    }
}
