package io.leaderli.litil.event;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LiEventObjectTest {


    @Test
    public void test() {

        Assertions.assertTrue(new LiEventObject<>(null).getSource().isEmpty());

    }


}
