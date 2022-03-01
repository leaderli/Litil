package io.leaderli.litil.bit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author leaderli
 * @since 2022/1/28
 */
public class BitEnumTest {

    @Test
    public void test() {

        Assertions.assertSame(0b1, BitEnum.values()[0].value);

        for (int i = 0; i < Integer.SIZE-1; i++) {
            Assertions.assertEquals(1 << i, BitEnum.values()[i ].value);
        }
    }

}
