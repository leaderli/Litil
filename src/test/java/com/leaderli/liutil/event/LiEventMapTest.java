package com.leaderli.liutil.event;

import org.junit.Assert;
import org.junit.Test;

public class LiEventMapTest {

    private static class TempEvent extends LiEvent<String> {

        public TempEvent(String source) {
            super(source);
        }
    }

    private static class Temp implements ILiEventListener<TempEvent> {

        @Override
        public void listen(TempEvent source) {

        }

        @Override
        public Class<TempEvent> genericType() {
            return TempEvent.class;
        }
    }

    @Test
    public void test() {

        LiEventMap liEventMap = new LiEventMap();

        Temp listener1 = new Temp();
        Temp listener2 = new Temp();

        liEventMap.put(TempEvent.class, listener1);
        liEventMap.put(TempEvent.class, listener1);
        Assert.assertEquals(1, liEventMap.get(TempEvent.class).size());

        liEventMap.put(TempEvent.class, listener2);
        Assert.assertEquals(2, liEventMap.get(TempEvent.class).size());

        liEventMap.remove(listener2);
        Assert.assertEquals(1, liEventMap.get(TempEvent.class).size());
    }

    @Test
    public void get() {
        LiEventMap liEventMap = new LiEventMap();

        Assert.assertNotNull(liEventMap.get(TempEvent.class));

        Temp listener1 = new Temp();
        Temp listener2 = new Temp();

        liEventMap.put(TempEvent.class, listener1);
        liEventMap.put(TempEvent.class, listener2);
        Assert.assertEquals(2, liEventMap.get(TempEvent.class).size());
        liEventMap.get(TempEvent.class).remove(listener1);
        Assert.assertEquals(2, liEventMap.get(TempEvent.class).size());

        liEventMap.remove(listener2);
        Assert.assertEquals(1, liEventMap.get(TempEvent.class).size());


    }


}
