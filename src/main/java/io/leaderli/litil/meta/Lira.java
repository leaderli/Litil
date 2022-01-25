package io.leaderli.litil.meta;

import io.leaderli.litil.exception.LiAssertUtil;
import io.leaderli.litil.type.LiClassUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author leaderli
 * @since 2022/1/23
 */
public interface Lira<T> extends LiValue {
    /**
     * return the single instance of {@code None}
     *
     * @param <T> component type
     * @return the single instance of {@code None}
     */

    static <T> Lira<T> none() {
        @SuppressWarnings("unchecked") final None<T> none = (None<T>) None.INSTANCE;
        return none;
    }

    /**
     * Narrows a widened {@code Lira<? extends T>} to {@code Lira<T>}
     * by performing a type-safe cast. this is eligible because immutable/read-only
     * collections are  covariant.
     *
     * @param value A {@code Lira}
     * @param <T>   Component type of {@code Lira}
     * @return the given {@code Lira} instance as narrowed type {@code Lira<T>}
     */
    @SuppressWarnings({"unchecked", "unused"})
    static <T> Lira<T> narrow(Lira<? extends T> value) {

        return (Lira<T>) value;

    }

    /**
     * @param elements a object array with same type
     * @param <T>      the componentType of lira
     * @return new Lira which have underlying list lino of the array
     */
    @SafeVarargs
    static <T> Lira<T> of(T... elements) {

        if (elements == null || elements.length == 0 || (elements.length == 1) && elements[0] == null) {
            return none();
        }

        return new Some<>(elements);

    }

    /**
     * @param iterator a iterator  can produce  the object array with same type
     * @param <T>      the componentType of lira
     * @return new Lira which have underlying list lino of the array
     */
    static <T> Lira<T> of(Iterator<T> iterator) {
        if (iterator == null || !iterator.hasNext()) {
            return none();
        }
        return new Some<>(iterator);
    }

    /**
     * @param iterable a iterable  can produce  the object array with same type
     * @param <T>      the componentType of lira
     * @return new Lira which have underlying list lino of the array
     * @see #of(Iterator)
     */
    static <T> Lira<T> of(Iterable<T> iterable) {
        if (iterable == null) {
            return none();
        }
        return of(iterable.iterator());
    }

    /**
     * @return the size of underlying list
     */
    int size();

    /**
     * @return the copy of underlying list lino
     */
    List<Lino<T>> get();

    /**
     * @return the copy of underlying list lino value
     */
    List<T> getRaw();

    @SuppressWarnings("unchecked")
    List<Lino<T>> getOrOther(T... others);


    List<Lino<T>> getOrOther(Iterator<T> others);

    default List<Lino<T>> getOrOther(Iterable<T> others) {
        return getOrOther(others.iterator());
    }


    /**
     * @return the first lino of underlying list
     */
    Lino<T> first();

    /**
     * @param filter {@link Lino#filter(Function)}
     * @return the first lino of  filtered underlying list
     */
    default Lino<T> first(Function<? super T, Object> filter) {

        return filter(filter).first();
    }

    /**
     * @param filter {@link Lino#filter(Function)}
     * @return filter underlying list lino by  {@code lino#filter(Function)}
     */
    Lira<T> filter(Function<? super T, Object> filter);

    default Lira<T> trim() {
        return filter(null);
    }

    /**
     * @param castType the type of underlying list element can casted
     * @param <R>      the type parameter of  underlying lino item
     * @return return new Lira of type R
     */
    <R> Lira<R> cast(Class<R> castType);

    /**
     * the underlying  list value is map
     *
     * @param keyType   type of map key
     * @param valueType type of  map value
     * @param <K>       type parameter of map key
     * @param <V>       type parameter of map value
     * @return lira of correct type declare
     */
    <K, V> Lira<Map<K, V>> cast(Class<K> keyType, Class<V> valueType);

    /**
     * @param mapping convert underlying list lino to other type
     * @param <R>     the type parameter of converted type
     * @return return new  Lira of type R
     * it's may be throw exception,when mapping throw some Exception
     */
    <R> Lira<R> map(Function<? super T, ? extends R> mapping);

    /**
     * @param mapping convert underlying list lino to other type
     * @param log     printStackTrace when  error occurs if true
     * @param <R>     the type parameter of converted type
     * @return return new  Lira of type R      , return {@code None} if throwable occurs
     * it's may be throw exception,when mapping throw some Exception
     */

    <R> Lira<R> safe_map(LiFunction<? super T, ? extends R> mapping, boolean log);

    /**
     * log = false;
     *
     * @see #safe_map(LiFunction, boolean)
     */
    default <R> Lira<R> safe_map(LiFunction<? super T, ? extends R> mapping) {
        return this.safe_map(mapping, false);
    }


    /**
     * @see #cast(Class)
     * @see #map(Function)
     * it's may be throw exception,when mapping throw some Exception
     */
    default <R, E> Lira<E> cast_map(Class<R> type, Function<? super R, ? extends E> mapping) {
        return cast(type).map(mapping);
    }

    default void forEach(Consumer<T> consumer) {
        getRaw().forEach(consumer);
    }

    default Stream<T> stream() {
        return getRaw().stream();
    }

    default T[] toArray(Class<T> type) {
        return getRaw().toArray(LiClassUtil.array(type, 0));
    }


    final class Some<T> implements Lira<T> {


        @SafeVarargs
        private Some(T... linos) {
            for (T value : linos) {
                this.linos.add(Lino.of(value));
            }
        }

        private Some(Iterator<T> iterator) {
            LiAssertUtil.assertTrue(iterator.hasNext(), stringPrefix() + " must have elements");
            iterator.forEachRemaining(value -> this.linos.add(Lino.of(value)));
        }

        public final List<Lino<T>> linos = new ArrayList<>();

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String stringPrefix() {
            return "List";
        }

        @Override
        public String toString() {
            return stringPrefix() + linos;
        }

        @Override
        public List<Lino<T>> get() {
            return new ArrayList<>(linos);
        }

        @Override
        public List<T> getRaw() {
            return linos.stream().map(Lino::get).collect(Collectors.toList());
        }

        @SafeVarargs
        @Override
        public final List<Lino<T>> getOrOther(T... others) {
            return this.get();
        }

        @Override
        public List<Lino<T>> getOrOther(Iterator<T> others) {
            return this.get();
        }


        @Override
        public Lino<T> first() {
            return linos.get(0);
        }

        @Override
        public Lira<T> filter(Function<? super T, Object> filter) {
            List<T> temp = this.linos.stream()
                    .map(lino -> lino.filter(filter).get())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return of(temp);
        }

        @Override
        public <R> Lira<R> cast(Class<R> castType) {
            List<R> collect = this.linos.stream()
                    .map(lino -> lino.cast(castType).get())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return of(collect);
        }

        @Override
        public <K, V> Lira<Map<K, V>> cast(Class<K> keyType, Class<V> valueType) {

            List<Map<K, V>> collect = this.linos.stream()
                    .map(lino -> lino.cast(keyType, valueType).get())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return of(collect);

        }

        @Override
        public <R> Lira<R> map(Function<? super T, ? extends R> mapping) {
            List<R> list = this.linos.stream()
                    .map(lino -> lino.map(mapping).get())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return of(list);
        }

        @Override
        public <R> Lira<R> safe_map(LiFunction<? super T, ? extends R> mapping, boolean log) {
            List<R> list = this.linos.stream()
                    .map(lino -> lino.safe_map(mapping, log).get())
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return of(list);
        }


        @Override
        public int size() {
            return linos.size();
        }

        /**
         * @param o An object
         * @return the underlying list lino is equals
         */
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            } else if (o instanceof Lira) {
                Lira<?> compare = (Lira<?>) o;
                return get().equals(compare.get());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(linos);
        }
    }

    /**
     * * None is a singleton representation of the undefined {@link Lino}.
     *
     * @param <T> The type of the optional value.
     */

    final class None<T> implements Lira<T> {
        /**
         * The singleton instance of None.
         */
        private static final None<?> INSTANCE = new None<>();

        /**
         * Hidden constructor.
         */
        private None() {
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public String stringPrefix() {
            return "Empty";
        }

        @Override
        public String toString() {
            return stringPrefix() + "[]";
        }

        @Override
        public List<Lino<T>> get() {
            return new ArrayList<>();
        }

        @Override
        public List<T> getRaw() {
            return new ArrayList<>();
        }

        @SafeVarargs
        @Override
        public final List<Lino<T>> getOrOther(T... others) {
            return of(others).get();
        }

        @Override
        public List<Lino<T>> getOrOther(Iterator<T> others) {
            return of(others).get();
        }


        @Override
        public Lino<T> first() {
            return Lino.none();
        }

        @Override
        public Lira<T> filter(Function<? super T, Object> filter) {
            return this;
        }

        @Override
        public <R> Lira<R> cast(Class<R> castType) {
            return none();
        }

        @Override
        public <K, V> Lira<Map<K, V>> cast(Class<K> keyType, Class<V> valueType) {
            return none();
        }

        @Override
        public <R> Lira<R> map(Function<? super T, ? extends R> mapping) {
            return none();
        }

        @Override
        public <R> Lira<R> safe_map(LiFunction<? super T, ? extends R> mapping, boolean log) {
            return Lira.none();
        }

        @Override
        public int size() {
            return 0;
        }
    }

}
