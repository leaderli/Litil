package io.leaderli.litil.type;

/**
 * a interface that give  a method {@code componentType} that
 * return the generic type you will use;
 *
 * @param <T> the type parameter  represent generic
 */
public interface ComponentType<T> {

    /**
     * @return the type parameter represented , it's should not be null
     */
    Class<T> componentType();

}
