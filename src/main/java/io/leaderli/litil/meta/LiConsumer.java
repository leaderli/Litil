package io.leaderli.litil.meta;

/**
 * @author leaderli
 * @since 2022/1/28
 */
public interface LiConsumer<T> {

    @SuppressWarnings("all")
    public void accept(T t) throws Throwable;
}
