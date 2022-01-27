package io.leaderli.litil.bit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author leaderli
 * @since 2022/1/28
 */
public class BitEnumTest {

    @Test
    public void test() {

        Assert.assertSame(0b1, BitEnum.values()[0].value);

        for (int i = 0; i < Integer.SIZE-1; i++) {
            Assert.assertEquals(1 << i, BitEnum.values()[i ].value);
        }
    }

}
