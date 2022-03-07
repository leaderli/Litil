package io.leaderli.litil.params;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author leaderli
 * @since 2022/2/25 1:18 PM
 */

class LiValueArgumentsProviderTest {

    @LiValueSource(ints = {1, 2})
    private int ages;

    @Test
    public void test() throws Throwable {

        LiValueSource valueSources = this.getClass().getDeclaredField("ages").getAnnotation(LiValueSource.class);


        LiArgumentsProvider<?, ?> provider = new LiValueArgumentsProvider<>(valueSources, int.class);

        System.out.println(provider.arguments);

        Assertions.assertEquals(provider.arguments.getRaw().toString(), "[1, 2]");

    }
}
