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
    void test() throws Throwable {

        LiValueSource valueSources = this.getClass().getDeclaredField("ages").getAnnotation(LiValueSource.class);


        LiArgumentsProvider<LiValueSource, ?> provider = new LiValueArgumentsProvider<>(valueSources, int.class);

        System.out.println(provider.arguments);
        System.out.println(provider.arguments);
        Assertions.assertEquals("[1, 2]", provider.arguments.getRaw().toString());

    }
}
