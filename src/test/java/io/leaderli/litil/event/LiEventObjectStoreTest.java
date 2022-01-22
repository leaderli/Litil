package io.leaderli.litil.event;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LiEventObjectStoreTest {


    private static class TestLiEventObject extends LiEventObject<String> {

        public TestLiEventObject(String source) {
            super(source);
        }
    }


    private static class TestLiEventListener implements ILiEventListener<TestLiEventObject> {


        @Override
        public void listen(TestLiEventObject source) {
            assert source.getSource().get().equals("123");

        }

        @Override
        public Class<TestLiEventObject> componentType() {
            return TestLiEventObject.class;
        }
    }


    @SuppressWarnings("all")
    private static class TestLiEventListener2 implements ILiEventListener<TestLiEventObject> {


        @Override
        public void listen(TestLiEventObject source) {

            Assert.assertEquals(source.getSource().get(), "123");

        }

        @Override
        public Class<TestLiEventObject> componentType() {
            return TestLiEventObject.class;
        }
    }

    @SuppressWarnings("all")
    private static class TestListenerLi implements ILiEventListener<String> {

        @Override
        public void listen(String source) {

            assert source.equals("123");
        }

        @Override
        public Class<String> componentType() {
            return String.class;
        }
    }


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void getPublisher() {


        LiEventBus eventStore = new LiEventBus();

        eventStore.registerListener(new TestLiEventListener());
        eventStore.registerListener(new TestLiEventListener2());

        thrown.expect(AssertionError.class);
        eventStore.push(new TestLiEventObject("456"));
        eventStore.push(null);

    }

    public static class TempListener implements ILiEventListener<TestLiEventObject> {

        int count;

        boolean remove;

        TempListener(boolean remove) {
            this.remove = remove;
        }

        @Override
        public Class<TestLiEventObject> componentType() {
            return TestLiEventObject.class;
        }

        @Override
        public void listen(TestLiEventObject source) {

            Assert.assertEquals("Some(123)",
                    source.getSource().toString());
            count++;

        }

        @Override
        public boolean once() {
            return remove;
        }
    }


    @Test
    public void test1() {

        LiEventBus liEventBus = new LiEventBus();

        TempListener listener = new TempListener(true);
        Assert.assertEquals(0, listener.count);
        liEventBus.registerListener(listener);
        liEventBus.push(new TestLiEventObject("123"));
        Assert.assertEquals(1, listener.count);
        liEventBus.push(new TestLiEventObject("123"));
        Assert.assertEquals(1, listener.count);

    }

    @Test
    public void test2() {
        LiEventBus liEventBus = new LiEventBus();


        TempListener listener = new TempListener(false);
        Assert.assertEquals(0, listener.count);
        liEventBus.registerListener(listener);
        liEventBus.push(new TestLiEventObject("123"));
        Assert.assertEquals(1, listener.count);
        liEventBus.push(new TestLiEventObject("123"));
        Assert.assertEquals(2, listener.count);
    }


}
