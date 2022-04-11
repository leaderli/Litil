package io.leaderli.litil.params;

import io.leaderli.litil.meta.Lira;

import java.lang.annotation.Annotation;

/**
 * @author leaderli
 * @since 2022/2/24 5:03 PM
 */
public abstract class LiArgumentsProvider<A extends Annotation, T> {

    public final Lira<T> arguments;

    protected LiArgumentsProvider(A source, Class<T> componentType) {
        this.arguments = provideArguments(source, componentType);
    }


    protected abstract Lira<T> provideArguments(A source, Class<T> componentType);


    public static <T extends Annotation> LiArgumentsProvider<T, ?> getInstance(T annotation) {
        return null;
    }
}
