package io.leaderli.litil.event;

import io.leaderli.litil.type.GenericType;

/**
 * @param <T> the type parameter of  event source
 */
public interface ILiEventListener<T> extends GenericType<T> {

    void listen(T source);

    default boolean unRegisterListenerAfterListen() {
        return false;
    }

}
