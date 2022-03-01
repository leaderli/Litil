package io.leaderli.litil.params;

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


        LiValueArgumentsProvider provider = new LiValueArgumentsProvider();
        provider.accept(valueSources);
        System.out.println(provider.provideArguments().toString());

    }
}
