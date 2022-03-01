package io.leaderli.litil.util;

import io.leaderli.litil.meta.Lino;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        Assertions.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = Lino.of(1);
        Assertions.assertTrue(LiBooleanUtil.parseBoolean(a));


        a = false;
        Assertions.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = true;
        Assertions.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = new ArrayList<>();
        Assertions.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = Arrays.asList(1, 2);
        Assertions.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = Collections.emptyIterator();
        Assertions.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = Arrays.asList(1, 2).iterator();
        Assertions.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = new HashMap<>();
        Assertions.assertFalse(LiBooleanUtil.parseBoolean(a));
        //noinspection unchecked,rawtypes
        ((Map) a).put("1", "1");
        Assertions.assertTrue(LiBooleanUtil.parseBoolean(a));

        a = null;
        Assertions.assertFalse(LiBooleanUtil.parseBoolean(a));
        a = "1";
        Assertions.assertTrue(LiBooleanUtil.parseBoolean(a));

    }

}
