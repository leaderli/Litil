package io.leaderli.litil.stream;

public interface LiFunction<T, R> {

    R apply(T request, R last);
}
