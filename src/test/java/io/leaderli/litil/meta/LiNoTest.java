package io.leaderli.litil.meta;

import org.junit.Assert;
import org.junit.Test;

public class LiNoTest {


    @Test
    public void narrow() {

        LiNo<CharSequence> narrow = LiNo.narrow(LiNo.<String>of(null));
        Assert.assertSame(narrow, LiNo.none());
    }

    @Test
    public void of() {
        Assert.assertTrue(LiNo.of(null).isEmpty());
        Assert.assertFalse(LiNo.of(1).isEmpty());
    }

    @Test
    public void get() {
        Assert.assertNotNull(LiNo.of(1).get());
        Assert.assertNull(LiNo.of(null).get());
    }

    @Test
    public void getOrElse() {
        Assert.assertSame(1, LiNo.of(null).getOrElse(1));
        Assert.assertSame(2, LiNo.of(2).getOrElse(1));
        Assert.assertNotSame(1, LiNo.of(2).getOrElse(1));
    }

    @Test
    public void testGetOrElse() {
        Assert.assertSame(1, LiNo.of(null).getOrElse(() -> 1));
        Assert.assertSame(2, LiNo.of(2).getOrElse(() -> 1));
        Assert.assertNotSame(1, LiNo.of(2).getOrElse(() -> 1));
    }

    @Test
    public void isEmpty() {
        Assert.assertTrue(LiNo.of(null).isEmpty());
        Assert.assertFalse(LiNo.of(1).isEmpty());
    }

    @Test
    public void isPresent() {
        Assert.assertTrue(LiNo.of(1).isPresent());
        Assert.assertFalse(LiNo.of(null).isPresent());
    }

    @Test
    public void stringPrefix() {
        Assert.assertSame("None", LiNo.of(null).stringPrefix());
    }

    @Test
    public void then() {
        LiNo.of(null).then(e -> Assert.fail());

        LiNo.of(null).then(e -> {
        }).error(() -> {
        }).isEmpty();

    }

    @Test
    public void error() {
        LiNo.of(1).error(Assert::fail);

    }

    @Test
    public void testToString() {
        Assert.assertSame("None", LiNo.of(null).toString());
        Assert.assertEquals("Some(1)", LiNo.of(1).toString());
    }

    @Test
    public void eq() {
        Assert.assertFalse(LiNo.none().eq(null));
        Assert.assertFalse(LiNo.none().eq(1));
        Assert.assertFalse(LiNo.none().eq(LiNo.of(1)));

        Assert.assertTrue(LiNo.none().eq(LiNo.of(null)));
        Assert.assertTrue(LiNo.of(null).eq(LiNo.of(null)));

        Assert.assertTrue(LiNo.of(1).eq(LiNo.of(1)));
    }

    @Test
    public void some() {

        Assert.assertSame(LiNo.of(null), LiNo.none());
        Assert.assertSame(1, LiNo.of(1).get());
    }
}
