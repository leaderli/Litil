package io.leaderli.litil.meta;

/**
 * @author leaderli
 * @since 2022/1/22
 */
public interface LiValue {

    /**
     * Checks , this {@code LiValue} is empty, i.e. if the underlying value is absent.
     *
     * @return false if no underlying value is present , true otherwise.
     */
    boolean isEmpty();


    /**
     * Checks , this {@code LiValue} is present , i.e. if the underlying value is present.
     *
     * @return true if no underlying value is present , false otherwise.
     */
    default boolean isPresent() {
        return !isEmpty();
    }

    /**
     * return the name of this Value type
     *
     * @return this type name
     */
    String stringPrefix();


    String toString();
}
