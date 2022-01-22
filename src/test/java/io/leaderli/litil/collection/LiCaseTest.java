package io.leaderli.litil.collection;

import org.junit.Assert;
import org.junit.Test;

public class LiCaseTest {

    @Test
    public void test() {

        Object a = "1";
        LiCase<Object, Integer> liCase = LiCase.of(LiMoNo.of(a));

        liCase
                .case_map(Integer.class, s -> s)
                .case_map(String.class, String::length);

        LiMoNo<Integer> of = liCase.mono();
        Assert.assertEquals(1, of.get().intValue());

        a = 100;
        liCase = LiCase.of(LiMoNo.of(a));

        liCase
                .case_map(Integer.class, s -> s)
                .case_map(String.class, String::length);


        of = liCase.mono();
        Assert.assertEquals(100, of.get().intValue());

        liCase = LiCase.of(LiMoNo.of("abc"));

        liCase
                .case_map(String.class, String::getBytes, bytes -> bytes.length).mono();


        of = liCase.mono();
        Assert.assertEquals(3, of.get().intValue());
    }
}
