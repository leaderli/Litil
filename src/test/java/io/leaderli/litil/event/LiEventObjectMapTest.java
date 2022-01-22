package io.leaderli.litil.event;

import org.junit.Assert;
import org.junit.Test;

public class LiEventObjectMapTest {

    private static class TempEventObject extends LiEventObject<String> {

        public TempEventObject(String source) {
            super(source);
        }
    }

    private static class Temp implements ILiEventListener<TempEventObject> {

        @Override
        public void listen(TempEventObject source) {

        }

        @Override
        public Class<TempEventObject> componentType() {
            return TempEventObject.class;
        }
    }

    @Test
    public void test() {

        LiEventMap liEventMap = new LiEventMap();

        Temp listener1 = new Temp();
        Temp listener2 = new Temp();

        liEventMap.put(TempEventObject.class, listener1);
        liEventMap.put(TempEventObject.class, listener1);
        Assert.assertEquals(1, liEventMap.get(TempEventObject.class).size());

        liEventMap.put(TempEventObject.class, listener2);
        Assert.assertEquals(2, liEventMap.get(TempEventObject.class).size());

        liEventMap.remove(listener2);
        Assert.assertEquals(1, liEventMap.get(TempEventObject.class).size());
    }

    @Test
    public void get() {
        LiEventMap liEventMap = new LiEventMap();

        Assert.assertNotNull(liEventMap.get(TempEventObject.class));

        Temp listener1 = new Temp();
        Temp listener2 = new Temp();

        liEventMap.put(TempEventObject.class, listener1);
        liEventMap.put(TempEventObject.class, listener2);
        Assert.assertEquals(2, liEventMap.get(TempEventObject.class).size());
        liEventMap.get(TempEventObject.class).remove(listener1);
        Assert.assertEquals(2, liEventMap.get(TempEventObject.class).size());

        liEventMap.remove(listener2);
        Assert.assertEquals(1, liEventMap.get(TempEventObject.class).size());


    }


}
