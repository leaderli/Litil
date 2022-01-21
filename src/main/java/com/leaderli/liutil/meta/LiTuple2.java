package com.leaderli.liutil.meta;

@SuppressWarnings("all")
public class LiTuple2<T1, T2> {

    public final T1 _1;
    public final T2 _2;

    public LiTuple2(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static <T1, T2> LiTuple2<T1, T2> of(T1 _1, T2 _2) {
        return new LiTuple2<>(_1, _2);
    }

}
