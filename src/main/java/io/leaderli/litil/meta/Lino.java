package io.leaderli.litil.meta;


import io.leaderli.litil.type.LiClassUtil;
import io.leaderli.litil.util.LiBooleanUtil;
import org.jetbrains.annotations.NotNull;

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
 * Equality checks:
 *
 * <ul>
 *     <li>{@link #eq(Object)} </li>
 * </ul>
 *
 * @param <T> The type of the wrapped value.
 * @author leaderli
 * @since 2022/1/21 4:15 PM
 */
public abstract class Lino<T> implements LiValue {


    /**
     * Narrows a widened {@code Lino<? extends T>} to {@code Lino<T>}
     * by performing a type-safe cast. this is eligible becase immutable/read-only
     * collections are  covariant.
     *
     * @param value A {@code Lino}
     * @param <T>   Component type of {@code Lino}
     * @return the given {@code Lino} instance as narrowed type {@code Lino<T>}
     */
    @SuppressWarnings("unchecked")
    public static <T> Lino<T> narrow(Lino<? extends T> value) {

        return (Lino<T>) value;

    }

    /**
     * return the single instance of {@code None}
     *
     * @param <T> component type
     * @return the single instance of {@code None}
     */
    public static <T> Lino<T> none() {
        @SuppressWarnings("unchecked") final None<T> none = (None<T>) None.INSTANCE;
        return none;
    }


    public static <T> Lino<T> of(T value) {
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
    public abstract T get();

    /**
     * return the underlying value if present ,otherwise {@code other}
     *
     * @param other An alternative value
     * @return A value of type {@code T}
     */
    public final T getOrElse(T other) {

        return isEmpty() ? other : get();
    }

    /**
     * return the underlying value if present ,otherwise {@code other}
     *
     * @param supplier An alternative value supplier.
     * @return A value of type {@code T}
     */
    public final T getOrElse(Supplier<? extends T> supplier) {

        return isEmpty() && supplier != null ? supplier.get() : get();
    }


    /**
     * @param action The action that will be performed if underlying value {@code isPresent()}
     * @return this
     */
    public abstract Lino<T> then(Consumer<? super T> action);

    /**
     * @param action The action that will be performed if underlying value {@code isEmpty()}
     * @return this
     */
    public abstract Lino<T> error(Runnable action);


    /**
     * @param other a  value
     * @return return {@code of(other)} if {@code Node} otherwise return this
     */
    public abstract Lino<T> orOther(T other);

    /**
     * @param other a Lino
     * @return return other if {@code Node} otherwise return this
     */
    public abstract Lino<T> orOther(Lino<T> other);

    /**
     * @param filter the function return a object value that object type decide the Lino value should remain
     * @return return this if the value of {@code filter#apply(object)} parse to true by {@code LiBooleanUtil#parseBoolean(object)}
     * return this if filter is null
     * otherwise {@code None}
     * @see LiBooleanUtil#parseBoolean(Object)
     */
    public abstract Lino<T> filter(Function<? super T, Object> filter);

    public final Lino<T> filter(boolean remain) {
        return filter(t -> remain);
    }


    /**
     * @param castType the type of value can be cast
     * @param <R>      the type parameter to the value
     * @return new LiMono with value of casted type
     */
    public abstract <R> Lino<R> cast(Class<R> castType);

    /**
     * @param mapping a function to apply value
     * @param <R>     the type parameter of mapped value
     * @return the new Lino with componentType {@code R}
     */
    public abstract <R> Lino<R> map(Function<? super T, ? extends R> mapping);

    /**
     * @see #cast(Class)
     * @see #map(Function)
     */
    public abstract <R1, R2> Lino<R2> cast_map(Class<R1> castType, Function<? super R1, ? extends R2> mapping);

    /**
     * @param o An object
     * @return the underlying value is equals
     */
    public boolean eq(Object o) {

        if (o == this) {
            return true;
        } else if (o instanceof Lino) {
            Lino<?> compare = (Lino<?>) o;
            return isEmpty() ? compare.isEmpty() : get().equals(compare.get());
        } else {
            return false;
        }
    }


    /**
     * Some represent a defined {@link Lino} . It contains a value which can not be null
     *
     * @param <T> the type of the optional value.
     */
    static final class Some<T> extends Lino<T> {

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
        public Lino<T> error(Runnable action) {
            return this;
        }

        @Override
        public <R> Lino<R> map(Function<? super T, ? extends R> mapping) {
            return of(mapping.apply(this.value));
        }

        @Override
        public <R1, R2> Lino<R2> cast_map(Class<R1> castType, Function<? super R1, ? extends R2> mapping) {
            return cast(castType).map(mapping);
        }


        @Override
        public Lino<T> orOther(T other) {
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
        public <R> Lino<R> cast(Class<R> castType) {
            if (LiClassUtil.isAssignableFromOrIsWrapper(castType, this.value.getClass())) {
                //noinspection unchecked
                return (Lino<R>) this;
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
    }

    /**
     * * None is a singleton representation of the undefined {@link Lino}.
     *
     * @param <T> The type of the optional value.
     */
    @SuppressWarnings("unchecked")
    static final class None<T> extends Lino<T> {
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
        public Lino<T> error(Runnable action) {
            action.run();
            return this;
        }

        @Override
        public <R> Lino<R> map(Function<? super T, ? extends R> mapping) {
            return (Lino<R>) this;
        }

        @Override
        public <R1, R2> Lino<R2> cast_map(Class<R1> castType, Function<? super R1, ? extends R2> mapping) {
            return (Lino<R2>) this;
        }


        @Override
        public Lino<T> orOther(T other) {
            return of(other);
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
        public <R> Lino<R> cast(Class<R> castType) {
            return (Lino<R>) this;
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
