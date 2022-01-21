package com.leaderli.liutil.stream;

import org.junit.Test;

import java.util.function.Predicate;

public class LiLogicPipeLineTest {

    @Test
    public void test() {


        assert !LiLogicPipeLine.instance().begin().test(str -> true).and().test(str -> false).apply("");


        assert !LiLogicPipeLine.instance().begin().test(str -> false).and().test(str -> false).apply("hello");


        assert !LiLogicPipeLine.instance().begin().test(str -> false).and().test(str -> true).apply("hello");


        assert LiLogicPipeLine.instance().begin().test(str -> true).and().test(str -> true).apply("hello");


        assert LiLogicPipeLine.instance().begin().test(str -> true).or().test(str -> false).apply("hello");


        assert !LiLogicPipeLine.instance().begin().test(str -> false).or().test(str -> false).apply("hello");


        assert LiLogicPipeLine.instance().begin().test(str -> false).or().test(str -> true).apply("hello");


        assert LiLogicPipeLine.instance().begin().test(str -> true).or().test(str -> true).apply("hello");


        assert LiLogicPipeLine.instance().begin().test(str -> true).apply("hello");


        assert !LiLogicPipeLine.instance().begin().test(str -> false).apply("hello");


        assert !LiLogicPipeLine.instance().begin().not().test(str -> true).apply("hello");


        assert LiLogicPipeLine.instance().begin().not().test(str -> false).apply("hello");


        LiLogicPipeLine.instance().begin().test(str -> false);
        assert LiLogicPipeLine.instance().begin().not().test(str -> false).and().not().test(str -> false).apply("hello");

    }


    private interface MyLinterPredicateSink extends LinterPredicateSink<String> {
        MyLinterCombineOperationSink and();

        MyLinterCombineOperationSink or();

    }

    private interface MyLinterOperationSink extends LinterOperationSink<String> {
        MyLinterPredicateSink len(int size);

        MyLinterPredicateSink test(Predicate<String> predicate);
    }

    private interface MyLinterNotOperationSink extends LinterNotOperationSink<String> {
        MyLinterOperationSink not();
    }

    private interface MyLinterCombineOperationSink extends MyLinterOperationSink, MyLinterNotOperationSink, LinterCombineOperationSink<String> {
    }


    private interface MyLinterLogicPipeLineSink extends MyLinterCombineOperationSink, MyLinterPredicateSink {

    }

    private static class MyLiLogicPipeLine implements MyLinterLogicPipeLineSink {

        private final LiLogicPipeLine<String> proxy = (LiLogicPipeLine<String>) LiLogicPipeLine.<String>instance();

        private MyLiLogicPipeLine() {

        }

        public static MyLiLogicPipeLine instance() {

            MyLiLogicPipeLine myLiLogicPipeLine = new MyLiLogicPipeLine();
            myLiLogicPipeLine.proxy.begin();
            return myLiLogicPipeLine;
        }

        @Override
        public MyLinterCombineOperationSink and() {
            proxy.and();
            return this;
        }

        @Override
        public MyLinterCombineOperationSink or() {
            proxy.or();
            return this;
        }

        @Override
        public MyLinterOperationSink not() {
            proxy.not();
            return this;
        }

        @Override
        public MyLinterPredicateSink test(Predicate<String> predicate) {
            proxy.test(predicate);
            return this;
        }

        @Override
        public Boolean apply(String s) {
            return proxy.apply(s);
        }

        @Override
        public MyLinterPredicateSink len(int size) {
            test(str -> size == str.length());
            return this;
        }


    }


    @Test
    public void test2() {
        assert !MyLiLogicPipeLine.instance().test(str -> true).and().test(str -> false).apply("1");
        assert !MyLiLogicPipeLine.instance().len(1).and().len(2).apply("1");
        assert MyLiLogicPipeLine.instance().len(1).or().len(2).apply("1");
    }
}
