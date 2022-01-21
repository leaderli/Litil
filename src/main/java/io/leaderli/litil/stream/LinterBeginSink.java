package io.leaderli.litil.stream;

public interface LinterBeginSink<T> {

    LinterCombineOperationSink<T> begin();
}
