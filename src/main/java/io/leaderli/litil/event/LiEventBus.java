package io.leaderli.litil.event;

import java.util.List;

/**
 * a component that  register {@link ILiEventListener} and unregister {@link ILiEventListener}
 * <p>
 * when  call {@link #push(LiEventObject)}  , the registered listeners will {@link ILiEventListener#listen(Object)}
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class LiEventBus {

    private final LiEventMap liEventMap = new LiEventMap();


    public void registerListener(ILiEventListener listener) {
        //noinspection unchecked
        liEventMap.put(listener.componentType(), listener);
    }

    public void unRegisterListener(ILiEventListener listener) {
        liEventMap.remove(listener);
    }

    /**
     * push event with message, trigger {@link ILiEventListener#listen(Object)},
     * if  {@link ILiEventListener#once()} predicate,
     * the listener will be removed
     *
     * @param liEvent the push message
     * @param <T>     the type parameter of LiEvent
     * @see ILiEventListener#listen(Object)
     * @see ILiEventListener#once()
     */
    public <T extends LiEventObject> void push(T liEvent) {
        if (liEvent == null) {
            return;
        }
        Class<T> cls = (Class<T>) liEvent.getClass();
        List<ILiEventListener<T>> listeners = liEventMap.get(cls);
        listeners.forEach(listener -> {
            listener.listen(liEvent);
            if (listener.once()) {
                liEventMap.remove(listener);
            }
        });
    }

}
