package com.leaderli.liutil.util;

import com.leaderli.liutil.exception.LiAssertException;

public class LiAssertUtil {

    public static void assertTrue(boolean assertTrue) {

        assertTrue(assertTrue,"");
    }
    public static void assertTrue(boolean assertTrue, String msg) {

        if (!assertTrue) {

            throw  new LiAssertException(msg);
        }
    }
    public static void assertFalse(boolean assertFalse) {

        assertFalse(assertFalse,"");
    }
    public static void assertFalse(boolean assertFalse, String msg) {

        if (assertFalse) {

            throw  new LiAssertException(msg);
        }
    }
}
