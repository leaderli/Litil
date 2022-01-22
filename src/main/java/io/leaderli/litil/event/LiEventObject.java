package io.leaderli.litil.event;


import io.leaderli.litil.meta.LiNo;

/**
 * All Events are constructed with a reference to the object, the "source",
 * that is logically deemed to be the object upon which the Event in question
 * initially occurred upon.
 */
public class LiEventObject<T> {

    /**
     * the object on which the event initially occurred
     */
    private final T source;

    public LiEventObject(T source) {
        this.source = source;
    }

    public final LiNo<T> getSource() {
        return LiNo.of(source);
    }


    /**
     * Returns a String representation of this LiEvent.
     *
     * @return A a String representation of this LiEvent.
     */
    public final String toString() {

        return getClass().getName() + "[source=" + source + "]";
    }

}
