package io.leaderli.litil.meta;

/**
 * @author leaderli
 * @since 2022/1/25 9:31 AM
 */
public interface LiFunction<T, R> {


    @SuppressWarnings("all")
    R apply(T t) throws Throwable;

}
