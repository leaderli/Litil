package io.leaderli.litil.util;

/**
 * @author leaderli
 * @since 2022/4/21
 */
public class LiMathUtil {


    public static long nextGap(long num, long gap) {
        return (num / gap + 1) * gap;
    }
}
