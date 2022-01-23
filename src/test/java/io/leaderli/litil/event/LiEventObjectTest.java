package io.leaderli.litil.event;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LiEventObjectTest {


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test() {

        Assert.assertTrue(new LiEventObject<>(null).getSource().isEmpty());

    }


}
