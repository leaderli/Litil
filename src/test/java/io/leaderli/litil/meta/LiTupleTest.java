package io.leaderli.litil.meta;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author leaderli
 * @since 2022/1/22
 */
public class LiTupleTest {

    @Test
    public void test() {

        Assert.assertEquals(1, LiTuple.of(1, 2)._1.intValue());
        Assert.assertEquals(2, LiTuple.of(1, 2).swap()._1.intValue());
        Assert.assertEquals(2, LiTuple.of(1, 2)._2.intValue());
        Assert.assertEquals(20, LiTuple.of(1, 2).update2(20)._2.intValue());
        Assert.assertEquals(20, LiTuple.of(1, 2).update1(20)._1.intValue());
        Assert.assertEquals("1", LiTuple.of(1, 2).map((i1, i2) -> LiTuple.of(i1 + "", i2 + ""))._1);
        Assert.assertEquals("1", LiTuple.of(1, 2).map(String::valueOf, String::valueOf)._1);
        Assert.assertEquals("1", LiTuple.of(1, 2).apply((i1, i2) -> i1 + ""));
        Assert.assertEquals("(1,2)", LiTuple.of(1, 2).toString());
        Assert.assertEquals(LiTuple.of(1, 2), LiTuple.of(1, 2));
        Assert.assertNotEquals(LiTuple.of(1, 2), LiTuple.of(1, 3));
        Assert.assertNotEquals(LiTuple.of(1, 2), LiTuple.of(null, 3));
        Assert.assertNotEquals(LiTuple.of(1, 2), null);
        
    }
}
