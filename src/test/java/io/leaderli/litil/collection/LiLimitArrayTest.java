package io.leaderli.litil.collection;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LiLimitArrayTest extends Assert{

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test() {
        thrown.expect(NegativeArraySizeException.class);
        new LiLimitArray<>(-1);

    }

    @Test
    public void test1() {
        LiLimitArray<Integer> limitArray = new LiLimitArray<>(10);

        limitArray.add(1);
        limitArray.add(2);

        assertTrue( limitArray.contains(1));
        assertTrue( limitArray.contains(2));

        limitArray.remove(2);

        assertTrue( limitArray.contains(1));
        assertFalse(limitArray.contains(2));

        for (int i = 0; i < 10; i++) {
            limitArray.add(i);
        }


        for (int i = 0; i < 10; i++) {
            limitArray.contains(i);
        }

        limitArray.add(4);
        limitArray.remove(4);
        limitArray.remove(3);
        assertFalse(limitArray.contains(3));
        assertFalse(limitArray.contains(4));

    }





}
