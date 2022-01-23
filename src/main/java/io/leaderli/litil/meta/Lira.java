package io.leaderli.litil.meta;

import io.leaderli.litil.exception.LiAssertUtil;
import io.leaderli.litil.type.LiClassUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author leaderli
 * @since 2022/1/23
 */
public abstract class Lira<T> implements LiValue {
    /**
     * return the single instance of {@code None}
     *
     * @param <T> component type
     * @return the single instance of {@code None}
     */
    public static <T> Lira<T> none() {
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
    public static <T> Lira<T> narrow(Lira<? extends T> value) {

        return (Lira<T>) value;

    }

    /**
     * @param elements a object array with same type
     * @param <T>      the componentType of lira
     * @return new Lira which have underlying list lino of the array
     */
    @SafeVarargs
    public static <T> Lira<T> of(T... elements) {

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
    public static <T> Lira<T> of(Iterator<T> iterator) {
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
    public static <T> Lira<T> of(Iterable<T> iterable) {
        if (iterable == null) {
            return none();
        }
        return of(iterable.iterator());
    }

    /**
     * @return the size of underlying list
     */
    public abstract int size();

    /**
     * @return the copy of underlying list lino
     */
    public abstract List<Lino<T>> get();

    /**
     * @return the copy of underlying list lino value
     */
    public abstract List<T> getRaw();

    @SuppressWarnings("unchecked")
    public abstract List<Lino<T>> getOrOther(T... others);


    public abstract List<Lino<T>> getOrOther(Iterator<T> others);

    public final List<Lino<T>> getOrOther(Iterable<T> others) {
        return getOrOther(others.iterator());
    }


    /**
     * @return the first lino of underlying list
     */
    public abstract Lino<T> first();

    /**
     * @param filter {@link Lino#filter(Function)}
     * @return the first lino of  filtered underlying list
     */
    public Lino<T> first(Function<? super T, Object> filter) {

        return filter(filter).first();
    }

    /**
     * @param filter {@link Lino#filter(Function)}
     * @return filter underlying list lino by  {@code lino#filter(Function)}
     */
    public abstract Lira<T> filter(Function<? super T, Object> filter);

    public Lira<T> trim() {
        return filter(null);
    }

    /**
     * @param castType the type of underlying list element can casted
     * @param <R>      the type parameter of  underlying lino item
     * @return return new Lira of type R
     */
    public abstract <R> Lira<R> cast(Class<R> castType);

    /**
     * @param mapping convert underlying list lino to other type
     * @param <R>     the type parameter of converted type
     * @return return new  Lira of type R
     */
    public abstract <R> Lira<R> map(Function<? super T, ? extends R> mapping);

    /**
     * @see #cast(Class)
     * @see #map(Function)
     */
    public <R, E> Lira<E> cast_map(Class<R> type, Function<? super R, ? extends E> mapping) {
        return cast(type).map(mapping);
    }

    public void forEach(Consumer<T> consumer) {
        getRaw().forEach(consumer);
    }

    public Stream<T> stream() {
        return getRaw().stream();
    }

    public final T[] toArray(Class<T> type) {
        return getRaw().toArray(LiClassUtil.array(type, 0));
    }


    static final class Some<T> extends Lira<T> {


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
        public <R> Lira<R> map(Function<? super T, ? extends R> mapping) {
            List<R> list = this.linos.stream()
                    .map(lino -> lino.map(mapping).get())
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

    @SuppressWarnings("unchecked")
    static final class None<T> extends Lira<T> {
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
            return (Lira<R>) this;
        }

        @Override
        public <R> Lira<R> map(Function<? super T, ? extends R> mapping) {
            return (Lira<R>) this;
        }

        @Override
        public int size() {
            return 0;
        }
    }

}
