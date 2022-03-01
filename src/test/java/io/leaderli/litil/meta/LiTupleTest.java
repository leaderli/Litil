package io.leaderli.litil.meta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author leaderli
 * @since 2022/1/22
 */
public class LiTupleTest {

    @Test
    public void test() {

        Assertions.assertEquals(1, LiTuple.of(1, 2)._1.intValue());
        Assertions.assertEquals(2, LiTuple.of(1, 2).swap()._1.intValue());
        Assertions.assertEquals(2, LiTuple.of(1, 2)._2.intValue());
        Assertions.assertEquals(20, LiTuple.of(1, 2).update2(20)._2.intValue());
        Assertions.assertEquals(20, LiTuple.of(1, 2).update1(20)._1.intValue());
        Assertions.assertEquals("1", LiTuple.of(1, 2).map((i1, i2) -> LiTuple.of(i1 + "", i2 + ""))._1);
        Assertions.assertEquals("1", LiTuple.of(1, 2).map(String::valueOf, String::valueOf)._1);
        Assertions.assertEquals("1", LiTuple.of(1, 2).apply((i1, i2) -> i1 + ""));
        Assertions.assertEquals("(1,2)", LiTuple.of(1, 2).toString());
        Assertions.assertEquals(LiTuple.of(1, 2), LiTuple.of(1, 2));
        Assertions.assertNotEquals(LiTuple.of(1, 2), LiTuple.of(1, 3));
        Assertions.assertNotEquals(LiTuple.of(1, 2), LiTuple.of(null, 3));
        Assertions.assertNotEquals(LiTuple.of(1, 2), null);

    }
}
