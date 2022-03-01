package io.leaderli.litil.params;


import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.STABLE;

/**
 * @author leaderli
 * @since 2022/2/25 10:09 AM
 */
@Target( ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@LiArgumentsSource(LiValueArgumentsProvider.class)
public @interface LiValueSource {

    short[] shorts() default {};
    byte[] bytes() default {};
    int[] ints() default {};
    long[] longs() default {};
    float[] floats() default {};
    double[] doubles() default {};
    char[] chars() default {};
    boolean[] booleans() default {};
    String[] strings() default {};
}
