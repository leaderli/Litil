package io.leaderli.litil.event;

import java.util.*;

/**
 * a container that store  a collection of {@link ILiEventListener} by type which extends {@link LiEventObject}
 * and  {@link ILiEventListener}  , {@link LiEventObject} has same type parameters
 */
class LiEventMap {

    private final Map<Class<?>, List<ILiEventListener<?>>> eventListenerMap = new HashMap<>();

    /**
     * @param cls      the  type of {@link LiEventObject}
     * @param listener the listener of {@link ILiEventListener}
     * @param <T>      the type parameter of {@link ILiEventListener} and  {@link LiEventObject}
     */
    public <T extends LiEventObject<?>> void put(Class<T> cls, ILiEventListener<T> listener) {

        List<ILiEventListener<?>> listeners = this.eventListenerMap.computeIfAbsent(cls, c -> new ArrayList<>());

        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * @param cls the  type of {@link LiEventObject}
     * @param <T> the type parameter of {@link ILiEventListener} and  {@link LiEventObject}
     * @return a copy of the collection {@link ILiEventListener},
     * it's could return empty collection  if not find
     */
    @SuppressWarnings("unchecked")
    public <T> List<ILiEventListener<T>> get(Class<T> cls) {
        Object iLiEventListeners = this.eventListenerMap.computeIfAbsent(cls, c -> new ArrayList<>());
        return new ArrayList<>((List<ILiEventListener<T>>) iLiEventListeners);
    }


    /**
     * remove the listener from the collection ,when collection is empty, will remove the collection
     *
     * @param listener the listener of {@link ILiEventListener}
     * @param <T>      the type parameter of {@link ILiEventListener} and  {@link LiEventObject}
     */
    public <T> void remove(ILiEventListener<T> listener) {

        Class<T> cls = listener.componentType();

        List<ILiEventListener<?>> listeners = this.eventListenerMap.computeIfAbsent(cls, c -> new ArrayList<>());

        listeners.removeIf(item -> item == listener);

        if (listeners.isEmpty()) {
            this.eventListenerMap.remove(cls);
        }
    }


}
