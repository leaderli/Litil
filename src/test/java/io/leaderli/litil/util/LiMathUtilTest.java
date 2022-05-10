package io.leaderli.litil.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author leaderli
 * @since 2022/4/21
 */
class LiMathUtilTest {


    @Test
    public void test() {


        Assertions.assertEquals(120, LiMathUtil.nextGap(111, 10));
        Assertions.assertEquals(120, LiMathUtil.nextGap(111, 60));


    }
}
