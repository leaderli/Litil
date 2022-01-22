package io.leaderli.litil.meta;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author leaderli
 * @since 2022/1/23
 */
public class LiraTest {


    @Test
    public void isEmpty() {

        Assert.assertFalse(Lira.of().isPresent());
        Assert.assertFalse(Lira.of((Object) null).isPresent());
        Assert.assertTrue(Lira.of(1).isPresent());
    }

    @Test
    public void of() {

        Assert.assertSame(Lira.none(), Lira.of());
        Assert.assertSame(Lira.none(), Lira.of((Object) null));
        Assert.assertSame(Lira.none(), Lira.of(Collections.emptyIterator()));
        Assert.assertSame(Lira.none(), Lira.of(Collections.emptyList()));
        Assert.assertNotSame(Lira.none(), Lira.of(1));
        Assert.assertNotSame(Lira.of(1), Lira.of(1));
    }

    @Test
    public void toArray() {

        Lira.none().forEach(no -> {
            Assert.fail();
        });
        Assert.assertEquals(0, Lira.<Integer>none().stream().count());
        Assert.assertSame(Integer[].class, Lira.<Integer>none().toArray(Integer.class).getClass());
    }

    @Test
    public void filter() {

        Assert.assertTrue(Lira.of(1, 2, 3).filter(i -> i > 4).isEmpty());
        Assert.assertSame(2, Lira.of(1, 2, 3).filter(i -> i > 1).size());
        Assert.assertSame(2, Lira.of(1, 2, 3).filter(i -> i > 1).get().get(0).get());

        Assert.assertEquals(3, Lira.of(1, 2, 3).trim().size());
        Assert.assertEquals(2, Lira.of(1, null, 3).trim().size());
        Assert.assertEquals(0, Lira.of((Object) null).trim().size());
    }

    @Test
    public void first() {

        Assert.assertSame(Lira.none().first(), Lino.none());
        Assert.assertSame(1, Lira.of(1).first().get());
        Assert.assertSame(2, Lira.of(1, 2, 3).first(i -> i > 1).get());
    }

    @Test
    public void getOrOther() {

        Lira<String> none = Lira.none();

        Assert.assertEquals("1", none.getOrOther("1").get(0).get());
        Assert.assertEquals("1", Lira.of("1", "2").getOrOther(Arrays.asList("5", "4")).get(0).get());

    }

    @Test
    public void get() {

        Assert.assertTrue(Lira.none().get().isEmpty());
        Assert.assertFalse(Lira.of(1).get().isEmpty());
        Assert.assertSame(2, Lira.of(1, 2).size());
    }

    @Test
    public void append_remove() {
        Assert.assertSame(Lira.none(), Lira.none());

        Assert.assertSame(Lira.of(1).remove(1), Lira.none());
        Assert.assertSame(1, Lira.of(1).remove(2).first().get());

        Assert.assertSame(2, Lira.of(1).remove(1).append(2).first().get());
    }


}
