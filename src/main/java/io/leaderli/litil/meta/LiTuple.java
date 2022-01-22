package io.leaderli.litil.meta;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * a tuple of two elements
 *
 * @param <T1> type of the 1st element
 * @param <T2> type of the 2nd element
 * @author leaderli
 */
@SuppressWarnings("all")
public final class LiTuple<T1, T2> {

    public final T1 _1;
    public final T2 _2;

    /**
     * Constructs a tuple of two elements.
     *
     * @param t1 the 1st element
     * @param t2 the 2nd element
     */
    public LiTuple(T1 _1, T2 _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static <T1, T2> LiTuple<T1, T2> of(T1 _1, T2 _2) {
        return new LiTuple<>(_1, _2);
    }

    /**
     * getter of the 1nd element of this tuple
     *
     * @return the Lino value of 1nd element of this tuple
     */
    public Lino<T1> _1() {
        return Lino.of(_1);
    }

    /**
     * getter of the 2nd element of this tuple
     *
     * @return the Lino value of 2nd element of this tuple
     */
    public Lino<T2> _2() {
        return Lino.of(_2);
    }

    /**
     * swaps the elements of this {@code LiTuple}
     *
     * @return A new LiTuple where the first element is the second element of this LiTuple
     * and the second element is the first element of this LiTuple.
     */
    public LiTuple<T2, T1> swap() {
        return of(_2, _1);
    }

    /**
     * sets the 1st element of this tuple to the given {@code value}
     *
     * @param t1 the new value
     * @return a new LiTuple where first element is the new value and the second elemnt is current 2nd element
     */
    public LiTuple<T1, T2> update1(T1 t1) {

        return of(t1, _2);
    }
    /**
     * sets the 2nd element of this tuple to the given {@code value}
     *
     * @param t2 the new value
     * @return a new LiTuple where first element is the current first element and the second elemnt is the new value
     */
    public LiTuple<T1, T2> update2(T2 t2) {

        return of(_1, t2);
    }
    /**
     * Maps the components of this tuple using a mapper function.
     *
     * @param mapper the mapper function
     * @param <U1>   new type of the 1st component
     * @param <U2>   new type of the 2nd component
     * @return A new Tuple of same arity.
     * @throws NullPointerException if {@code mapper} is null
     */
    public <U1, U2> LiTuple<U1, U2> map(BiFunction<? super T1, ? super T2, LiTuple<U1, U2>> mapper) {
        Objects.requireNonNull(mapper, "mapper is null");
        return mapper.apply(_1, _2);
    }

    /**
     * Maps the components of this tuple using a mapper function for each component.
     *
     * @param f1   the mapper function of the 1st component
     * @param f2   the mapper function of the 2nd component
     * @param <U1> new type of the 1st component
     * @param <U2> new type of the 2nd component
     * @return A new Tuple of same arity.
     * @throws NullPointerException if one of the arguments is null
     */
    public <U1, U2> LiTuple<U1, U2> map(Function<? super T1, ? extends U1> f1, Function<? super T2, ? extends U2> f2) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        return of(f1.apply(_1), f2.apply(_2));
    }



    /**
     * Transforms this tuple to an object of type U.
     *
     * @param f Transformation which creates a new object of type U based on this tuple's contents.
     * @param <U> type of the transformation result
     * @return An object of type U
     * @throws NullPointerException if {@code f} is null
     */
    public <U> U apply(BiFunction<? super T1, ? super T2, ? extends U> f) {
        Objects.requireNonNull(f, "f is null");
        return f.apply(_1, _2);
    }
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LiTuple<?, ?> liTuple = (LiTuple<?, ?>) o;
        return Objects.equals(_1, liTuple._1) && Objects.equals(_2, liTuple._2);
    }


    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }

    @Override
    public String toString() {
        return "(" + _1 + "," + _2 + ")";
    }
}
