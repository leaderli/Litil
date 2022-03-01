package io.leaderli.litil.meta;


import io.leaderli.litil.exception.LiMonoRuntimeException;
import io.leaderli.litil.type.LiClassUtil;
import io.leaderli.litil.util.LiBooleanUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Functional programming is all about values and transformation of values use functions.
 * The Value type reflects the values in function setting. It can be seen as the result of a
 * partial function application.Hence the result may be undefined.if value is undefined,we say it is empty.
 * <p>
 * How the empty state is interpreted depends on the context,i.e.it may be <em>undefined</em>,<em>failed</em>,<em>no elements</em>,etc.
 * <p>
 * <p>
 * Static instance
 *
 * <ul>
 *     <li>{@link #none()}</li>
 *     <li>{@link #of(Object)}</li>
 * </ul>
 * <p>
 * Basic Operations
 * <ul>
 *     <li>{@link #get()}</li>
 *     <li>{@link #getOrElse(Object)} ()}</li>
 *     <li>{@link #getOrElse(Supplier)} ()}</li>
 *     <li>{@link #then(Consumer)}</li>
 *     <li>{@link #throwable(LiConsumer)}</li>
 *     <li>{@link #error(Runnable)}</li>
 * </ul>
 * <p>
 * Type conversion:
 * <ul>
 *     <li> {@link #orOther(Object)}</li>
 *     <li> {@link #orOther(Lino)}</li>
 *     <li> {@link #filter(Function)}</li>
 *     <li> {@link #cast(Class)} }</li>
 *     <li> {@link #map(Function)}</li>
 *     <li> {@link #safe_map(LiFunction)}</li>
 *     <li> {@link #safe_map(LiFunction, boolean)}</li>
 * </ul>
 *
 * <ul>
 *
 * </ul>
 * <p>
 * Tests:
 *
 * <ul>
 *     <li> {@link #isEmpty()}</li>
 *     <li> {@link #isPresent()}</li>
 * </ul>
 * <p>
 *
 * @param <T> The type of the wrapped value.
 * @author leaderli
 * @since 2022/1/21 4:15 PM
 */
public interface Lino<T> extends LiValue {


    /**
     * Narrows a widened {@code Lino<? extends T>} to {@code Lino<T>}
     * by performing a type-safe cast. this is eligible because immutable/read-only
     * collections are  covariant.
     *
     * @param value A {@code Lino}
     * @param <T>   Component type of {@code Lino}
     * @return the given {@code Lino} instance as narrowed type {@code Lino<T>}
     */
    @SuppressWarnings("unchecked")
    static <T> Lino<T> narrow(Lino<? extends T> value) {

        return (Lino<T>) value;

    }

    /**
     * return the single instance of {@code None}
     *
     * @param <T> component type
     * @return the single instance of {@code None}
     */
    static <T> Lino<T> none() {
        @SuppressWarnings("unchecked") final None<T> none = (None<T>) None.INSTANCE;
        return none;
    }


    static <T> Lino<T> of(T value) {
        if (value == null) {
            return none();
        }
        return new Some<>(value);
    }

    /**
     * Get the underlying value  if present , otherwise {@code null}
     *
     * @return A value of type {@code T} or {@code null}.
     */
    T get();

    /**
     * return the underlying value if present ,otherwise {@code other}
     *
     * @param other An alternative value
     * @return A value of type {@code T}
     */
    default T getOrElse(T other) {

        return isEmpty() ? other : get();
    }

    /**
     * return the underlying value if present ,otherwise {@code other}
     *
     * @param supplier An alternative value supplier.
     * @return A value of type {@code T}
     */
    default T getOrElse(Supplier<? extends T> supplier) {

        return isEmpty() && supplier != null ? supplier.get() : get();
    }


    /**
     * @param action The action that will be performed if underlying value {@code isPresent()}
     * @return this
     */
    Lino<T> then(Consumer<? super T> action);

    /**
     * @param action The action that will be performed if underlying value {@code isPresent()}
     * @return this
     * @throws Throwable the consumer may throw exception
     */
    @SuppressWarnings("all")
    Lino<T> throwable(LiConsumer<? super T> action);

    /**
     * @param action The action that will be performed if underlying value {@code isEmpty()}
     * @return this
     */
    Lino<T> error(Runnable action);


    /**
     * @param other a  value
     * @return return {@code of(other)} if {@code isEmpty} otherwise return this
     */
    Lino<T> orOther(T other);

    /**
     * @param other a supplier return a value
     * @return return {@code of(other.get())} if {@code isEmpty} otherwise return this
     */
    Lino<T> orOther(Supplier<T> other);

    /**
     * @param other a Lino
     * @return return {@code other} if {@code isEmpty} otherwise return this
     */
    Lino<T> orOther(Lino<T> other);

    /**
     * @param filter the function return a object value that object type decide the Lino value should remain
     * @return return this if the value of {@code filter#apply(object)} parse to true by {@code LiBooleanUtil#parseBoolean(object)}
     * return this if filter is null
     * otherwise {@code None}
     * @see LiBooleanUtil#parseBoolean(Object)
     */
    Lino<T> filter(Function<? super T, Object> filter);


    /**
     * @param other a value
     * @return this if value.equals(this.value) otherwise None
     */
    Lino<T> same(T other);

    default Lino<T> filter(boolean remain) {
        return filter(t -> remain);
    }


    /**
     * @param castType the type of value can be cast
     * @param <R>      the type parameter to the value
     * @return new LiMono with value of casted type
     */
    <R> Lino<R> cast(Class<R> castType);

    /**
     * @param mapping a function to apply value
     * @param <R>     the type parameter of mapped value
     * @return the new Lino with componentType {@code R}
     * it's may be throw exception,when apply function throw some Exception
     */
    <R> Lino<R> map(Function<? super T, ? extends R> mapping);

    /**
     * log = false;
     *
     * @see #safe_map(LiFunction, boolean)
     */
    default <R> Lino<R> safe_map(LiFunction<? super T, ? extends R> mapping) {
        return this.safe_map(mapping, false);
    }

    /**
     * @param mapping a function to apply value
     * @param <R>     the type parameter of mapped value
     * @param log     printStackTrace when  error occurs if true
     * @return the new Lino with componentType {@code R} , return {@code None} if throwable occurs
     */
    <R> Lino<R> safe_map(LiFunction<? super T, ? extends R> mapping, boolean log);

    /**
     * @see #cast(Class)
     * @see #map(Function)
     * it's may be throw exception,when apply function throw some Exception
     */
    <R1, R2> Lino<R2> cast_map(Class<R1> castType, Function<? super R1, ? extends R2> mapping);


    /**
     * @param castType the type of value lira can be cast
     * @param <R>      the type parameter to the lira
     * @return a lira produced by lino that have underlying list value
     * @see #cast(Class)
     * @see #lira(Function)
     */
    <R> Lira<R> lira(Class<R> castType);

    /**
     * @param mapping the function convert lino to Iterable
     * @param <R>     the type parameter of lira componentType
     * @return a lira produced by lino
     */
    <R> Lira<R> lira(Function<T, Iterable<R>> mapping);

    /**
     * @param mapping the function convert lino to  array
     * @param <R>     the type parameter of lira componentType
     * @return a lira produced by lino
     */
    <R> Lira<R> liraByArray(Function<T, R[]> mapping);


    /**
     * @param mapping the function convert lino to Iterator
     * @param <R>     the type parameter of lira componentType
     * @return a lira produced by lino
     */
    <R> Lira<R> liraByIterator(Function<T, Iterator<R>> mapping);

    /**
     * @param <E> the type parameter of LiCase 2nd componentType
     * @return a new LiCase<T,E>
     */
    <E> LiCase<T, E> toLiCase();

    /**
     * the underlying  value is map
     *
     * @param keyType   type of map key
     * @param valueType type of  map value
     * @param <K>       type parameter of map key
     * @param <V>       type parameter of map value
     * @return lino of correct type declare
     */
    <K, V> Lino<Map<K, V>> cast(Class<K> keyType, Class<V> valueType);

    /**
     * Some represent a defined {@link Lino} . It contains a value which can not be null
     *
     * @param <T> the type of the optional value.
     */
    final class Some<T> implements Lino<T> {

        private final T value;

        /**
         * Create a new Some containing the given value.
         *
         * @param value A value, can not be null
         */
        private Some(@NotNull T value) {
            Objects.requireNonNull(value, "Some containing must have non null value");
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public Lino<T> then(Consumer<? super T> action) {
            action.accept(value);
            return this;
        }

        @Override
        public Lino<T> throwable(LiConsumer<? super T> action) {
            try {
                action.accept(value);
            } catch (Throwable e) {
                throw new LiMonoRuntimeException(e);
            }
            return this;
        }

        @Override
        public Lino<T> error(Runnable action) {
            return this;
        }

        @Override
        public <R> Lino<R> map(Function<? super T, ? extends R> mapping) {
            return Lino.of(mapping.apply(this.value));
        }

        @Override
        public <R> Lino<R> safe_map(LiFunction<? super T, ? extends R> mapping, boolean log) {
            try {
                return of(mapping.apply(this.value));
            } catch (Throwable throwable) {

                if (log) {
                    throwable.printStackTrace();
                }
            }
            return Lino.none();
        }

        @Override
        public <R1, R2> Lino<R2> cast_map(Class<R1> castType, Function<? super R1, ? extends R2> mapping) {
            return cast(castType).map(mapping);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Lira<R> lira(Class<R> castType) {
            Lira<Object> lira = cast(List.class).lira(li -> li);
            return lira.cast(castType);
        }

        @Override
        public <R> Lira<R> lira(Function<T, Iterable<R>> mapping) {
            if (mapping == null) {
                return Lira.none();
            }
            return Lira.of(mapping.apply(this.value));
        }

        @Override
        public <R> Lira<R> liraByArray(Function<T, R[]> mapping) {
            if (mapping == null) {
                return Lira.none();
            }
            return Lira.of(mapping.apply(this.value));
        }

        @Override
        public <R> Lira<R> liraByIterator(Function<T, Iterator<R>> mapping) {
            if (mapping == null) {
                return Lira.none();
            }
            return Lira.of(mapping.apply(this.value));
        }


        @Override
        public <E> LiCase<T, E> toLiCase() {
            return LiCase.of(this);
        }

        @Override
        public <K, V> Lino<Map<K, V>> cast(Class<K> keyType, Class<V> valueType) {
            Map<K, V> kvMap = LiClassUtil.filterCanCast(cast(Map.class).get(), keyType, valueType);

            if (kvMap.isEmpty()) {
                return Lino.none();
            }
            return Lino.of(kvMap);
        }


        @Override
        public Lino<T> orOther(T other) {
            return this;
        }

        @Override
        public Lino<T> orOther(Supplier<T> other) {
            return this;
        }

        @Override
        public Lino<T> orOther(Lino<T> other) {
            return this;
        }


        @Override
        public Lino<T> filter(Function<? super T, Object> filter) {
            if (filter == null || LiBooleanUtil.parseBoolean(filter.apply(value))) {
                return this;
            }
            return none();
        }


        @Override
        public Lino<T> same(T other) {
            if (this.value.equals(other)) {
                return this;
            }
            return none();
        }

        @Override
        public <R> Lino<R> cast(Class<R> castType) {
            if (LiClassUtil.isAssignableFromOrIsWrapper(castType, this.value.getClass())) {
                //noinspection unchecked
                return map(e -> LiClassUtil.cast(e, castType));
            }
            return none();

        }


        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public String stringPrefix() {
            return "Some";
        }

        @Override
        public String toString() {
            return stringPrefix() + "(" + value + ")";
        }


        /**
         * @param o An object
         * @return the underlying value is equals
         */
        public boolean equals(Object o) {

            if (o == this) {
                return true;
            } else if (o instanceof Lino) {
                Lino<?> compare = (Lino<?>) o;
                return get().equals(compare.get());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    /**
     * * None is a singleton representation of the undefined {@link Lino}.
     *
     * @param <T> The type of the optional value.
     */
    @SuppressWarnings("unchecked")
    final class None<T> implements Lino<T> {
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
        public T get() {
            return null;
        }

        @Override
        public Lino<T> then(Consumer<? super T> action) {
            return this;
        }

        @Override
        public Lino<T> throwable(LiConsumer<? super T> action) {
            return this;
        }

        @Override
        public Lino<T> error(Runnable action) {
            action.run();
            return this;
        }

        @Override
        public <R> Lino<R> map(Function<? super T, ? extends R> mapping) {
            return (Lino<R>) this;
        }

        @Override
        public <R> Lino<R> safe_map(LiFunction<? super T, ? extends R> mapping, boolean log) {
            return (Lino<R>) this;
        }

        @Override
        public <R1, R2> Lino<R2> cast_map(Class<R1> castType, Function<? super R1, ? extends R2> mapping) {
            return (Lino<R2>) this;
        }

        @Override
        public <R> Lira<R> lira(Class<R> castType) {
            return Lira.none();
        }

        @Override
        public <R> Lira<R> lira(Function<T, Iterable<R>> mapping) {
            return Lira.none();
        }

        @Override
        public <R> Lira<R> liraByArray(Function<T, R[]> mapping) {
            return Lira.none();
        }

        @Override
        public <R> Lira<R> liraByIterator(Function<T, Iterator<R>> mapping) {
            return Lira.none();
        }


        @Override
        public <E> LiCase<T, E> toLiCase() {
            return LiCase.none();
        }

        @Override
        public <K, V> Lino<Map<K, V>> cast(Class<K> keyType, Class<V> valueType) {
            return Lino.none();
        }


        @Override
        public Lino<T> orOther(T other) {
            return of(other);
        }

        @Override
        public Lino<T> orOther(Supplier<T> other) {
            return of(other.get());
        }

        @Override
        public Lino<T> orOther(Lino<T> other) {
            return other;
        }

        @Override
        public Lino<T> filter(Function<? super T, Object> filter) {
            return this;
        }


        @Override
        public Lino<T> same(T other) {
            return this;
        }

        @Override
        public <R> Lino<R> cast(Class<R> castType) {
            return none();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public String stringPrefix() {
            return "None";
        }

        @Override
        public String toString() {
            return stringPrefix();
        }
    }
}
