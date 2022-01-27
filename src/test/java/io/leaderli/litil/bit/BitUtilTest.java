package io.leaderli.litil.bit;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author leaderli
 * @since 2022/1/27
 */
public class BitUtilTest {


    @Test
    public void toMonospaceBinary() {


        Assert.assertEquals("0001", BitUtil.toMonoSpaceBinary(4, 1));
        Assert.assertEquals("0000", BitUtil.toMonoSpaceBinary(4, 0));
        Assert.assertEquals("1111111111111111111111111111111", BitUtil.toMonoSpaceBinary(4, Integer.MAX_VALUE));

    }


    @Test
    public void getSetBinaries() {

        Assert.assertEquals("[B1, B4, B5, B6, B13, B14]", BitUtil.getSetBinaries(12345).toString());

        Assert.assertEquals("[B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, B13, B14, B15, B16, B17, B18, B19, B20, B21, B22, B23, B24, B25, B26, B27, B28, B29, B30, B31]", BitUtil.getSetBinaries(Integer.MAX_VALUE).toString());

    }


    private interface TestMask {

        int A = 0b1;
        int B = 0b10;
        int C = 0b100;
    }

    @Test
    public void transferSetBinariesToNames() {


        Assert.assertEquals("A|B", BitUtil.transferSetBinariesToNames(123, TestMask.class));
        Assert.assertEquals("A", BitUtil.transferSetBinariesToNames(1, TestMask.class));
        Assert.assertEquals("", BitUtil.transferSetBinariesToNames(0, TestMask.class));

    }


}
