package com.leaderli.liutil.stream;

public interface LiFunction<T, R> {

    R apply(T request, R last);
}
