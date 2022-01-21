package io.leaderli.litil.stream;

import java.util.function.Predicate;

public interface LinterOperationSink<T> {
    LinterPredicateSink<T> test(Predicate<T> predicate);
}
