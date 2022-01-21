package io.leaderli.litil.meta;


/**
 * @param <T> The type of the wrapped value.
 * @author leaderli
 * @since 2022/1/21 4:15 PM
 */
public interface LiValue<T> {


    @SuppressWarnings("unchecked")
    static <T> LiValue<T> narrow(LiValue<? extends T> value) {

        return (LiValue<T>) value;

    }
}
