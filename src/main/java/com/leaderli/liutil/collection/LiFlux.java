package com.leaderli.liutil.collection;

import com.leaderli.liutil.util.LiCastUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A container object which contain a collection value
 *
 * @param <T> the type parameter of  collection data
 */
public class LiFlux<T> {


    private final List<LiMono<T>> monos;

    private LiFlux(List<LiMono<T>> monos) {
        this.monos = monos;
    }


    @SafeVarargs
    public static <T> LiFlux<T> of(T... elements) {
        if (elements == null) {
            return LiFlux.empty();
        }
        List<LiMono<T>> monos = new ArrayList<>();
        for (T element : elements) {
            monos.add(LiMono.of(element));
        }
        return new LiFlux<>(monos);
    }

    /**
     * @param elements the origin collection data
     * @param <T>      the type parameter of collection data
     * @return new LiFlux with a copy of the origin collection data
     */
    public static <T> LiFlux<T> of(Iterator<T> elements) {
        if (elements == null) {
            return LiFlux.empty();
        }
        List<LiMono<T>> monos = new ArrayList<>();
        elements.forEachRemaining(element -> monos.add(LiMono.of(element)));
        return new LiFlux<>(monos);
    }

    /**
     * @param elements the origin collection data
     * @param <T>      the type parameter of collection data
     * @return new LiFlux with a copy of the origin collection data
     */
    public static <T> LiFlux<T> of(Iterable<T> elements) {

        if (elements == null) {
            return LiFlux.empty();
        }
        List<LiMono<T>> monos = new ArrayList<>();
        elements.forEach(element -> monos.add(LiMono.of(element)));
        return new LiFlux<>(monos);
    }

    /**
     *
     * @param elements  appended elements of monos
     * @return this
     */
    @SafeVarargs
    public final LiFlux<T> add(T... elements) {
        if (elements != null) {
            for (T element : elements) {
                this.monos.add(LiMono.of(element));
            }
        }
        return this;
    }
    /**
     *
     * @param elements  appended elements of monos
     * @return this
     */
    public final LiFlux<T> add(Iterable<T> elements) {
        if (elements != null) {
            elements.forEach(element -> monos.add(LiMono.of(element)));
        }
        return this;
    }
    /**
     *
     * @param elements  appended elements of monos
     * @return this
     */
    public final LiFlux<T> add(Iterator<T> elements) {
        if (elements != null) {
            elements.forEachRemaining(element -> monos.add(LiMono.of(element)));
        }
        return this;
    }

    /**
     *
     * @param <T>      the type parameter of collection data
     * @return the empty LiFlux
     */
    public static <T> LiFlux<T> empty() {
        return new LiFlux<>(new ArrayList<>());
    }

    /**
     * @param mapping convert monos value to other type
     * @param <R>     the type parameter of converted type
     * @return return new  LiFlux of type R
     */
    public <R> LiFlux<R> map(Function<? super T, ? extends R> mapping) {
        if (mapping != null) {
            //noinspection unchecked
            List<LiMono<R>> new_monos = this.monos.stream().map(mono -> (LiMono<R>) mono.map(mapping)).collect(Collectors.toList());
            return new LiFlux<>(new_monos);
        }
        return LiFlux.empty();
    }

    public boolean notEmpty() {
        return !monos.isEmpty();
    }

    public boolean isEmpty() {
        return monos.isEmpty();
    }

    public int size() {
        return monos.size();
    }

    /**
     * @return get a copy of the collection data, because the collection should not
     * changed by outside
     */
    public List<LiMono<T>> getMonoCopy() {
        return new ArrayList<>(monos);
    }

    /**
     * @return get a copy of the collection raw data , filter not present mono data
     */
    public List<T> getRawCopy() {
        return trim().monos.stream()
                .map(LiMono::get)
                .collect(Collectors.toList());
    }

    /**
     * @return return the first element of collection data if collection is present
     * otherwise return {@link LiMono#empty()}
     */
    public LiMono<T> getFirst() {

        List<T> rawCopy = getRawCopy();
        if (rawCopy.isEmpty()) {
            return LiMono.empty();
        }
        return LiMono.of(rawCopy.get(0));
    }

    /**
     * @param function {@link LiMono#filter(Function)}
     * @return return the first element which is predicate true of collection data
     * otherwise return {@link LiMono#empty()}
     */
    public LiMono<T> getFirst(Function<? super T, Object> function) {
        LiFlux<T> filter = filter(function);
        return filter.getFirst();
    }


    /**
     * @param others a collection of data when not present
     * @return return {@link #getMonoCopy()} if present otherwise return {@link #getMonoCopy()}  of  the new LiFlux with others data
     */
    @SafeVarargs
    public final List<LiMono<T>> getMonoCopyOrOther(T... others) {
        return or(others).getMonoCopy();
    }

    @SafeVarargs
    public final LiFlux<T> or(T... others) {
        if (notEmpty()) {
            return this;
        }
        return LiFlux.of(others);
    }


    /**
     * @return return new LiFlux which filter monos {@link LiMono#isPresent()}
     */
    public LiFlux<T> trim() {
        return filter(null);
    }

    /**
     * @param function {@link LiMono#filter(Function)}
     * @return return new LiFlux which filter monos by function function
     */
    public LiFlux<T> filter(Function<? super T, Object> function) {

        List<LiMono<T>> filtered = this.monos.stream()
                .map(mono -> mono.filter(function))
                .filter(LiMono::isPresent)
                .collect(Collectors.toList());
        return new LiFlux<>(filtered);
    }


    /**
     * @param t the element that will be removed from monos
     * @return this
     */
    public LiFlux<T> remove(T t) {
        this.monos.removeIf(mono -> mono.get() == t);
        return this;
    }

    /**
     * @param type the type of monos item can casted
     * @param <R>  the type parameter of  monos item
     * @return return new LiFlux of type R
     */
    public <R> LiFlux<R> cast(Class<R> type) {
        List<LiMono<R>> castMonos = this.monos.stream()
                .map(mono -> mono.cast(type))
                .collect(Collectors.toList());
        return new LiFlux<>(castMonos);
    }


    /**
     * the monos item is map
     *
     * @param keyType   the type of map key
     * @param valueType the type of map value
     * @param <K>       the type parameter of map key
     * @param <V>       the type parameter of map value
     * @return return new LiFlux of type Map<K,V>
     */
    public <K, V> LiFlux<Map<K, V>> cast(Class<K> keyType, Class<V> valueType) {
        return cast(Map.class)
                .map(map -> LiCastUtil.cast(map, keyType, valueType));
    }

    /**
     * @see #cast(Class, Class)
     * @see #map(Function)
     */
    public <K, V, R> LiFlux<R> cast_map(Class<K> keyType, Class<V> valueType, Function<Map<K, V>, R> mapping) {
        return cast(keyType, valueType).map(mapping);
    }

    /**
     * @see #cast(Class)
     * @see #map(Function)
     */
    public <R, E> LiFlux<E> cast_map(Class<R> type, Function<R, E> mapping) {
        return cast(type).map(mapping);
    }


    public void forEach(Consumer<T> consumer) {
        getRawCopy().forEach(consumer);
    }


}
