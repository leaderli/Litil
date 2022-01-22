package io.leaderli.litil.stream;

import io.leaderli.litil.collection.LiMoNo;

public abstract class LiSink<T, R> implements LiFunction<T, R> {

    public final LiMoNo<LiSink<T, R>> prevSink;
    protected LiMoNo<LiSink<T, R>> nextSink;


    protected LiSink(LiSink<T, R> prev) {
        this.prevSink = LiMoNo.of(prev);
        this.prevSink.then(sink -> sink.nextSink = LiMoNo.of(this));
        this.nextSink = LiMoNo.empty();
    }


    public final R next(T request, R lastValue) {
        return this.nextSink.map(sink -> sink.apply(request, lastValue)).getOrOther(lastValue);
    }


    public final R request(T request) {

        LiSink<T, R> prev = this;
        while (prev.prevSink.isPresent()) {
            prev = prev.prevSink.get();
        }
        return prev.apply(request, null);
    }

}
