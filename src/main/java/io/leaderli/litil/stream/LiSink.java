package io.leaderli.litil.stream;

import io.leaderli.litil.meta.Lino;

public abstract class LiSink<T, R> implements LiFunction<T, R> {

    public final Lino<LiSink<T, R>> prevSink;
    protected Lino<LiSink<T, R>> nextSink;


    protected LiSink(LiSink<T, R> prev) {
        this.prevSink = Lino.of(prev);
        this.prevSink.then(sink -> sink.nextSink = Lino.of(this));
        this.nextSink = Lino.none();
    }


    public final R next(T request, R lastValue) {
        return this.nextSink.map(sink -> sink.apply(request, lastValue)).getOrElse(lastValue);
    }


    public final R request(T request) {

        LiSink<T, R> prev = this;
        while (prev.prevSink.isPresent()) {
            prev = prev.prevSink.get();
        }
        return prev.apply(request, null);
    }

}
