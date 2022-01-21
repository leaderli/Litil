package com.leaderli.liutil.event;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LiEventTest {


    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Test
    public void test(){

        thrown.expect(IllegalArgumentException.class);
        new LiEvent<>(null);

    }



}