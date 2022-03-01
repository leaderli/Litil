package io.leaderli.litil.bit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @author leaderli
 * @since 2022/1/27
 */
public class BitUtilTest {


    @Test
    public void toMonospaceBinary() {


        Assertions.assertEquals("0001", BitUtil.toMonoSpaceBinary(4, 1));
        Assertions.assertEquals("0000", BitUtil.toMonoSpaceBinary(4, 0));
        Assertions.assertEquals("1111111111111111111111111111111", BitUtil.toMonoSpaceBinary(4, Integer.MAX_VALUE));

    }


    @Test
    public void getSetBinaries() {

        Assertions.assertEquals("[B1, B4, B5, B6, B13, B14]", BitUtil.getSetBinaries(12345).toString());

        Assertions.assertEquals("[B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28, B29, B30, B31]", BitUtil.getSetBinaries(Integer.MAX_VALUE).toString());

    }


    private interface TestMask {

        int A = 0b1;
        int B = 0b10;
        int C = 0b1000;
    }

    @Test
    public void transferSetBinariesToNames() {


        String[] stateNames = BitUtil.getBinaryStateNames(TestMask.class);
        Assertions.assertEquals("[A, B, null, C, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]", Arrays.toString(stateNames));
        Assertions.assertEquals("C", BitUtil.transferSetBinariesToNames(8, stateNames));

        Assertions.assertEquals("", BitUtil.transferSetBinariesToNames(16, stateNames));
        Assertions.assertEquals("", BitUtil.transferSetBinariesToNames(4, stateNames));
        Assertions.assertEquals("A|B|C", BitUtil.transferSetBinariesToNames(123, stateNames));
        Assertions.assertEquals("A", BitUtil.transferSetBinariesToNames(1, stateNames));
        Assertions.assertEquals("", BitUtil.transferSetBinariesToNames(0, stateNames));

    }


}
