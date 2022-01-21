package com.leaderli.liutil.event;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LiEventStoreTest {


    private static class TestLiEvent extends LiEvent<String> {

        public TestLiEvent(String source) {
            super(source);
        }
    }


    private static class TestLiEventListener implements ILiEventListener<TestLiEvent> {


        @Override
        public void listen(TestLiEvent source) {
            assert source.getSource().equals("123");

        }

        @Override
        public Class<TestLiEvent> genericType() {
            return TestLiEvent.class;
        }
    }


    @SuppressWarnings("all")
    private static class TestLiEventListener2 implements ILiEventListener<TestLiEvent> {


        @Override
        public void listen(TestLiEvent source) {
            assert source.getSource().equals("123");

        }

        @Override
        public Class<TestLiEvent> genericType() {
            return TestLiEvent.class;
        }
    }

    @SuppressWarnings("all")
    private static class TestListenerLi implements ILiEventListener<String> {

        @Override
        public void listen(String source) {

            assert source.equals("123");
        }

        @Override
        public Class<String> genericType() {
            return String.class;
        }
    }


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void getPublisher() {


        LiEventStore eventStore = new LiEventStore();

        eventStore.registerListener(new TestLiEventListener());
        eventStore.registerListener(new TestLiEventListener2());

        eventStore.push(new TestLiEvent("123"));
        thrown.expect(NullPointerException.class);
        //noinspection ConstantConditions
        eventStore.push(null);

    }

    public static class TempListener implements ILiEventListener<TestLiEvent> {

        int count;

        boolean remove;

        TempListener(boolean remove) {
            this.remove = remove;
        }

        @Override
        public Class<TestLiEvent> genericType() {
            return TestLiEvent.class;
        }

        @Override
        public void listen(TestLiEvent source) {

            Assert.assertEquals("123",
                    source.getSource());
            count++;

        }

        @Override
        public boolean unRegisterListenerAfterListen() {
            return remove;
        }
    }


    @Test
    public void test1() {

        LiEventStore liEventStore = new LiEventStore();

        TempListener listener = new TempListener(true);
        Assert.assertEquals(0, listener.count);
        liEventStore.registerListener(listener);
        liEventStore.push(new TestLiEvent("123"));
        Assert.assertEquals(1, listener.count);
        liEventStore.push(new TestLiEvent("123"));
        Assert.assertEquals(1, listener.count);

    }

    @Test
    public void test2() {
        LiEventStore liEventStore = new LiEventStore();


        TempListener listener = new TempListener(false);
        Assert.assertEquals(0, listener.count);
        liEventStore.registerListener(listener);
        liEventStore.push(new TestLiEvent("123"));
        Assert.assertEquals(1, listener.count);
        liEventStore.push(new TestLiEvent("123"));
        Assert.assertEquals(2, listener.count);
    }


}
