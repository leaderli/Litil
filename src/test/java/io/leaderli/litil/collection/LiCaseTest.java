package io.leaderli.litil.collection;

import io.leaderli.litil.meta.LiCase;
import io.leaderli.litil.meta.Lino;
import org.junit.Assert;
import org.junit.Test;

public class LiCaseTest {

    @Test
    public void test() {

        Object a = "1";
        LiCase<Object, Integer> liCase = LiCase.of(Lino.of(a));

        liCase
                .case_map(Integer.class, s -> s)
                .case_map(String.class, String::length);

        Lino<Integer> of = liCase.lino();
        Assert.assertEquals(1, of.get().intValue());

        a = 100;
        liCase = LiCase.of(Lino.of(a));

        liCase
                .case_map(Integer.class, s -> s)
                .case_map(String.class, String::length);


        of = liCase.lino();
        Assert.assertEquals(100, of.get().intValue());

        liCase = LiCase.of(Lino.of("abc"));

        liCase
                .case_map(String.class, String::getBytes, bytes -> bytes.length).lino();


        of = liCase.lino();
        Assert.assertEquals(3, of.get().intValue());

        liCase = LiCase.of(Lino.of(123));

        Assert.assertEquals(Lino.none(), liCase.filter_map(String.class::isInstance, ln -> ((String) ln).length()).lino());
        liCase = LiCase.of(Lino.of("123"));
        Assert.assertEquals(3, liCase.filter_map(String.class::isInstance, ln -> ((String) ln).length()).lino().get().intValue());


    }
}
