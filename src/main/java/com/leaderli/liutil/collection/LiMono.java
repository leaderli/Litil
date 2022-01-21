package com.leaderli.liutil.collection;

import com.leaderli.liutil.exception.LiMonoRuntimeException;
import com.leaderli.liutil.util.LiCastUtil;
import com.leaderli.liutil.util.LiClassUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A container object which contain a value
 *
 * @param <T> the type parameter of value
 */
public class LiMono<T> {

    private final T value;


    private LiMono(T value) {
        this.value = value;
    }


    public static <T> LiMono<T> of(T t) {

        return new LiMono<>(t);
    }


    public static <T> LiMono<T> empty() {

        return new LiMono<>(null);
    }

    /**
     * @param mapping a function to apply value
     * @param <R>     the type parameter of mapped value
     * @return the new LiMono with type <R>
     */
    public <R> LiMono<R> map(Function<? super T, ? extends R> mapping) {
        if (value != null && mapping != null) {
            return LiMono.of(mapping.apply(this.value));
        }
        return LiMono.empty();
    }

    /**
     * @param consumer apply  value when  value {@link #isPresent()}
     * @return this
     */
    public LiMono<T> then(Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
        return this;
    }

    /**
     * @param runnable run when value {@link #notPresent()}
     * @return this
     */
    public LiMono<T> error(Runnable runnable) {
        if (value == null) {
            runnable.run();
        }
        return this;
    }

    /**
     * @param supplier a {@link Supplier} whose result is returned if no value is present
     * @return this if value present otherwise the new LiMono with result of {@link Supplier#get()}
     */
    public LiMono<T> error(Supplier<T> supplier) {
        if (value == null) {
            return LiMono.of(supplier.get());
        }
        return this;
    }

    /**
     * @return this
     * @throws RuntimeException - when value is null
     */
    public LiMono<T> exception() {
        if (value == null) {
            throw new LiMonoRuntimeException("value is null ");
        }
        return this;
    }

    public boolean isPresent() {
        return value != null;
    }

    public boolean notPresent() {
        return value == null;
    }


    public T get() {
        return value;
    }


    /**
     * @param def the value is returned if no value is present
     * @return value if present otherwise the def
     */
    public T getOrOther(T def) {
        return or(def).get();
    }

    /**
     * @param other the value is returned if no value is present
     * @return this if value is present otherwise a new LiMono with value of other
     */
    public LiMono<T> or(T other) {
        if (isPresent()) {
            return this;
        }
        return LiMono.of(other);
    }

    /**
     * @param other the value is returned if no value is present
     * @return this if value is present otherwise return other
     */
    public LiMono<T> or(LiMono<T> other) {
        if (isPresent()) {
            return this;
        }
        return other;
    }

    /**
     * @param function the function return a object value that object type decide the mono value should remain
     * @return if function is null, return this;
     * return this when function return LiMono and  {@link LiMono#isPresent()} is true
     * return this when function return LiFLux and  {@link LiFlux#notEmpty()} ()} is true
     * return this when function return Collection and object is not empty
     * return this when function return Map and object is not empty
     * return this when function return other object and   object is not null
     * otherwise return {@link #empty()}
     */
    public LiMono<T> filter(Function<? super T, Object> function) {

        if (function == null) {
            return this;
        }
        if (notPresent()) {
            return LiMono.empty();
        }
        Object apply = function.apply(this.value);
        if (apply instanceof Boolean) {
            return filter((Boolean) apply);
        } else if (apply instanceof LiMono) {
            return filter(((LiMono<?>) apply).isPresent());
        } else if (apply instanceof LiFlux) {
            return filter(((LiFlux<?>) apply).notEmpty());
        } else if (apply instanceof Iterable) {
            return filter(!((Collection<?>) apply).isEmpty());
        } else if (apply instanceof Map) {
            return filter(!((Map<?, ?>) apply).isEmpty());
        } else {
            return filter(apply != null);
        }

    }

    /**
     * @param filter if the value should remain
     * @return return this if filter is true otherwise  {@link #empty()}
     */
    public LiMono<T> filter(boolean filter) {
        return filter ? this : LiMono.empty();
    }


    /**
     * @param type the type of the list item value can be cast
     * @param <R>  the type parameter of the list item
     * @return return {@link LiFlux#empty()} if value type is not  {@link List}
     * otherwise return new LiFlux with type value
     */
    public <R> LiFlux<R> flux(Class<R> type) {

        @SuppressWarnings("rawtypes")
        LiMono<List> listMono = cast(List.class);
        return LiFlux.of(LiCastUtil.cast(listMono.getOrOther(null), type));
    }

    /**
     * @param mapping the function convert mono to flux
     * @param <R>     the type parameter of flux
     * @return the flux
     */
    public <R> LiFlux<R> fluxByArray(Function<T, R[]> mapping) {
        if (mapping == null) {
            return LiFlux.empty();
        }
        return map(mapping).map(LiFlux::of).getOrOther(LiFlux.empty());
    }

    /**
     * @param mapping the function convert mono to flux
     * @param <R>     the type parameter of flux
     * @return the flux
     */
    public <R> LiFlux<R> fluxByIterator(Function<T, Iterator<R>> mapping) {
        if (mapping == null) {
            return LiFlux.empty();
        }
        return map(mapping).map(LiFlux::of).getOrOther(LiFlux.empty());
    }


    /**
     * @param mapping the function convert mono to flux
     * @param <R>     the type parameter of flux
     * @return the flux
     */
    public <R> LiFlux<R> flux(Function<T, Iterable<R>> mapping) {
        if (mapping == null) {
            return LiFlux.empty();
        }
        return map(mapping).map(LiFlux::of).getOrOther(LiFlux.empty());
    }

    /**
     * @param type the type of value can be cast
     * @param <R>  the type parameter to the value
     * @return new LiMono with value of casted type
     */
    public <R> LiMono<R> cast(Class<R> type) {
        if (this.isPresent() && LiClassUtil.isAssignableFromOrIsWrapper(type, this.value.getClass())) {
            //noinspection unchecked
            return (LiMono<R>) this;
        }
        return LiMono.empty();
    }

    /**
     * @param keyType   the type of map key
     * @param valueType the type of map value
     * @param <K>       the type parameter of map key
     * @param <V>       the type parameter of map value
     * @return return {@link #empty()} if value is not {@link Map}
     * otherwise return new LiMono with Map<K,V>
     */
    public <K, V> LiMono<Map<K, V>> cast(Class<K> keyType, Class<V> valueType) {

        @SuppressWarnings("rawtypes")
        LiMono<Map> listMono = cast(Map.class);
        return LiMono.of(LiCastUtil.cast(listMono.getOrOther(null), keyType, valueType));
    }


    /**
     * @see #cast(Class, Class)
     * @see #map(Function)
     */
    public <K, V, R> LiMono<R> cast_map(Class<K> keyType, Class<V> valueType, Function<Map<K, V>, ? extends R> mapping) {
        return cast(keyType, valueType).map(mapping);
    }

    /**
     * @see #cast(Class)
     * @see #map(Function)
     */
    public <R, E> LiMono<E> cast_map(Class<R> type, Function<? super R, ? extends E> mapping) {
        return cast(type).map(mapping);
    }

    public <E> LiCase<T, E> toCase() {
        return LiCase.of(this);
    }

    @Override
    public String toString() {
        return "mono:" + value;
    }
}
