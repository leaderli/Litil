package com.leaderli.liutil.event;

import java.util.EventObject;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class LiEventStore {

    private final LiEventMap liEventMap = new LiEventMap();


    public void registerListener(ILiEventListener listener) {
        //noinspection unchecked
        liEventMap.put(listener.genericType(), listener);
    }

    public void unRegisterListener(ILiEventListener listener) {
        liEventMap.remove(listener);
    }

    /**
     * push event with message, trigger {@link ILiEventListener#listen(Object)},
     * if  {@link ILiEventListener#unRegisterListenerAfterListen()} predicate,
     * the listener will be removed
     *
     * @param liEvent the push message
     * @param <T>     the type parameter of LiEvent
     */
    public <T extends EventObject> void push(T liEvent) {
        Class<T> cls = (Class<T>) liEvent.getClass();
        List<ILiEventListener<T>> listeners = liEventMap.get(cls);
        listeners.forEach(listener -> {
            listener.listen(liEvent);
            if (listener.unRegisterListenerAfterListen()) {
                liEventMap.remove(listener);
            }
        });
    }

}
