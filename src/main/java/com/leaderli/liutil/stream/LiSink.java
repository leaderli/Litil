package com.leaderli.liutil.stream;

import com.leaderli.liutil.collection.LiMono;

public abstract class LiSink<T, R> implements LiFunction<T, R> {

    public final LiMono<LiSink<T, R>> prevSink;
    protected LiMono<LiSink<T, R>> nextSink;


    protected LiSink(LiSink<T, R> prev) {
        this.prevSink = LiMono.of(prev);
        this.prevSink.then(sink -> sink.nextSink = LiMono.of(this));
        this.nextSink = LiMono.empty();
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
