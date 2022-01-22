package io.leaderli.litil.util;

import io.leaderli.litil.exception.LiAssertException;
import io.leaderli.litil.exception.LiAssertUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author leaderli
 * @since 2022/1/22
 */
public class LiAssertUtilTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @SuppressWarnings("all")
    @Test
    public void test() {

        thrown.expect(LiAssertException.class);
        thrown.expectMessage("");
        LiAssertUtil.assertTrue(false);

        thrown.expect(LiAssertException.class);
        thrown.expectMessage("123");
        LiAssertUtil.assertTrue(false,"123");


        thrown.expect(LiAssertException.class);
        thrown.expectMessage("");
        LiAssertUtil.assertFalse(true);

        thrown.expect(LiAssertException.class);
        thrown.expectMessage("123");
        LiAssertUtil.assertFalse(true,"123");
    }
}
