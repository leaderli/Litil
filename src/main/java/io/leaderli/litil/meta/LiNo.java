package io.leaderli.litil.meta;


import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
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
 *
 * <ul>
 *     <li>{@link #get()}</li>
 *     <li>{@link #getOrElse(Object)} ()}</li>
 *     <li>{@link #getOrElse(Supplier)} ()}</li>
 *     <li>{@link #then(Consumer)}</li>
 *     <li>{@link #error(Runnable)}</li>
 * </ul>
 * <p>
 * <p>
 * Type conversion:
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
public abstract class LiNo<T> implements LiValue {


    /**
     * Narrows a widened {@code LiNo<? extends T>} to {@code LiNo<T>}
     * by performing a type-safe cast. this is eligible becase immutable/read-only
     * collections are  covariant.
     *
     * @param value A {@code LiNo}
     * @param <T>   Component type of {@code LiNo}
     * @return the given {@code LiNo} instance as narrowed type {@code LiNo<T>}
     */
    @SuppressWarnings("unchecked")
    public static <T> LiNo<T> narrow(LiNo<? extends T> value) {

        return (LiNo<T>) value;

    }

    /**
     * return the single instance of {@code None}
     *
     * @param <T> component type
     * @return the single instance of {@code None}
     */
    public static <T> LiNo<T> none() {
        @SuppressWarnings("unchecked") final None<T> none = (None<T>) None.INSTANCE;
        return none;
    }


    public static <T> LiNo<T> of(T value) {
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
    public abstract LiNo<T> then(Consumer<? super T> action);

    /**
     * @param action The action that will be performed if underlying value {@code isEmpty()}
     * @return this
     */
    public abstract LiNo<T> error(Runnable action);


    /**
     * @param o An object
     * @return the underlying value is equals
     */
    public boolean eq(Object o) {

        if (o == this) {
            return true;
        } else if (o instanceof LiNo) {
            LiNo<?> compare = (LiNo<?>) o;
            return isEmpty() ? compare.isEmpty() : get().equals(compare.get());
        } else {
            return false;
        }
    }


    /**
     * Some represent a defined {@link LiNo} . It contains a value which can not be null
     *
     * @param <T> the type of the optional value.
     */
    static final class Some<T> extends LiNo<T> {

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
        public LiNo<T> then(Consumer<? super T> action) {
            action.accept(value);
            return this;
        }

        @Override
        public LiNo<T> error(Runnable action) {
            return this;
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
     * * None is a singleton representation of the undefined {@link LiNo}.
     *
     * @param <T> The type of the optional value.
     */
    static final class None<T> extends LiNo<T> {
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
        public LiNo<T> then(Consumer<? super T> action) {
            return this;
        }

        @Override
        public LiNo<T> error(Runnable action) {
            action.run();
            return this;
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
