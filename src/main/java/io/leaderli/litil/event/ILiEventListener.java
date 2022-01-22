package io.leaderli.litil.event;

import io.leaderli.litil.type.ComponentType;

/**
 * @param <T> the type parameter of  LiEventObject componentType
 */
public interface ILiEventListener<T> extends ComponentType<T> {

    /**
     * @param source the object that  {@link LiEventObject} contained
     * @see LiEventBus#push(LiEventObject)
     */
    void listen(T source);

    /**
     * @return unRegister this listener after  {@code listen}
     * @see LiEventBus#unRegisterListener(ILiEventListener)
     * @see LiEventBus#push(LiEventObject)
     */
    default boolean once() {
        return false;
    }

}
