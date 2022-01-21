package io.leaderli.litil.stream;

import java.util.function.Function;

public interface LinterPredicateSink<T> extends Function<T,Boolean> {
    LinterCombineOperationSink<T> and();
    LinterCombineOperationSink<T> or();
}
