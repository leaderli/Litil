package io.leaderli.litil.util;

import io.leaderli.litil.meta.Lino;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author leaderli
 * @since 2022/1/22
 */
public class LiBooleanUtilTest {

    @Test
    public void test() {

        Object a;

        a = Lino.of(null);
        Assert.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = Lino.of(1);
        Assert.assertTrue(LiBooleanUtil.parseBoolean(a));


        a = false;
        Assert.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = true;
        Assert.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = new ArrayList<>();
        Assert.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = Arrays.asList(1, 2);
        Assert.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = Collections.emptyIterator();
        Assert.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = Arrays.asList(1, 2).iterator();
        Assert.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = new HashMap<>();
        Assert.assertFalse(LiBooleanUtil.parseBoolean(a));
        //noinspection unchecked,rawtypes
        ((Map) a).put("1", "1");
        Assert.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = null;
        Assert.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = "1";
        Assert.assertTrue(LiBooleanUtil.parseBoolean(a));

    }

}
