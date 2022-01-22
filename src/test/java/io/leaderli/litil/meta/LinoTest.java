package io.leaderli.litil.meta;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

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
    }

    @Test
    public void cast() {
        Lino.of(null).cast(String.class).cast(Integer.class);
        Object a = 1;
        Assert.assertSame(1, Lino.of(a).cast(Integer.class).get());
    }

    @Test
    public void cast_map() {
        Lino.of(null).cast_map(String.class, s -> null);
        Object a = 1;
        Assert.assertSame(2, Lino.of(a).cast_map(Integer.class, i -> ++i).get());
        Assert.assertNull(Lino.of(a).cast_map(String.class, i -> i).get());
        Assert.assertNull(Lino.of(a).cast_map(Integer.class, i -> null).get());
    }

    @Test
    public void testToString() {
        Assert.assertSame("None", Lino.of(null).toString());
        Assert.assertEquals("Some(1)", Lino.of(1).toString());
    }

    @Test
    public void eq() {
        Assert.assertFalse(Lino.none().eq(null));
        Assert.assertFalse(Lino.none().eq(1));
        Assert.assertFalse(Lino.none().eq(Lino.of(1)));

        Assert.assertTrue(Lino.none().eq(Lino.of(null)));
        Assert.assertTrue(Lino.of(null).eq(Lino.of(null)));

        Assert.assertTrue(Lino.of(1).eq(Lino.of(1)));
    }

    @Test
    public void some() {

        Assert.assertSame(Lino.of(null), Lino.none());
        Assert.assertSame(1, Lino.of(1).get());
    }
}
