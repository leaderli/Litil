package io.leaderli.litil.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * when match lino real type R , apply store function<R,E> to transfer lino<T> to lino<E>.
 * only execute function when call {@link #lino()}
 *
 * @param <T> the type parameter of lino
 * @param <E> the type parameter of mapping lino
 */
public interface LiCase<T, E> {

    /**
     * @param lino the origin  lino
     * @param <T>  the type parameter of lino
     * @param <E>  the type parameter of transferred  lino
     * @return new LiCase
     */
    static <T, E> LiCase<T, E> of(Lino<T> lino) {
        if (lino == null || lino.isEmpty()) {
            return none();
        }
        return new Some<>(lino);
    }

    static <T, E> LiCase<T, E> none() {
        @SuppressWarnings("unchecked") final LiCase<T, E> none = (None<T, E>) None.INSTANCE;
        return none;
    }


    /**
     * not really  apply function<T,E>  ,just cache it
     *
     * @param filter  only apply function when filtered lino is {@code isPresent()}
     * @param mapping function<T,E> to convert Lino<T> to lino<E>
     * @return this
     */
    LiCase<T, E> filter_map(Function<? super T, Object> filter, Function<? super T, ? extends E> mapping);

    /**
     * not really  apply function<R,E>  ,just cache it
     *
     * @param keyType matched type
     * @param mapping function<R,E> to convert Lino<R> to lino<E>
     * @param <R>     the type parameter of real lino type
     * @return this
     */
    public abstract <R> LiCase<T, E> case_map(Class<R> keyType, Function<? super R, ? extends E> mapping);

    /**
     * not really  apply function<R,F1> and function<F1,E> ,just cache it
     * the lino<R> can not direct transfer to lino<E> , should use to step to convert
     *
     * @param keyType matched type
     * @param f1      function<F1,F2> to convert F1 to lino<F2>
     * @param f2      function<F2,E> to convert F2 to lino<E>
     * @param <F1>    the type parameter of real lino type
     * @param <F2>    the type parameter of middle function
     * @return this
     */
    public abstract <F1, F2> LiCase<T, E> case_map(Class<F1> keyType, Function<? super F1, ? extends F2> f1, Function<? super F2, ? extends E> f2);

    /**
     * @return return first match case_map function
     */
    public abstract Lino<E> lino();


    static final class Some<T, E> implements LiCase<T, E> {

        private final Lino<T> lino;

        private final List<Function<Lino<T>, ? extends Lino<E>>> mappings = new ArrayList<>();

        private Some(Lino<T> lino) {
            this.lino = lino;
        }


        public LiCase<T, E> filter_map(Function<? super T, Object> filter, Function<? super T, ? extends E> mapping) {


            this.mappings.add(ln -> ln.filter(filter).map(mapping));

            return this;
        }


        public <R> LiCase<T, E> case_map(Class<R> keyType, Function<? super R, ? extends E> mapping) {

            this.mappings.add(ln -> Lino.narrow(ln.cast(keyType).map(mapping)));
            return this;
        }


        public <F1, F2> LiCase<T, E> case_map(Class<F1> keyType, Function<? super F1, ? extends F2> f1, Function<? super F2, ? extends E> f2) {
            this.mappings.add(ln -> Lino.narrow(ln.cast(keyType).map(f1)).map(f2));
            return this;
        }


        public Lino<E> lino() {
            for (Function<Lino<T>, ? extends Lino<E>> mapping : this.mappings) {

                Lino<E> apply = mapping.apply(lino);

                if (apply.isPresent()) {
                    return apply;
                }
            }

            return Lino.none();
        }

    }


    static final class None<T, E> implements LiCase<T, E> {
        /**
         * The singleton instance of None.
         */
        private static final None<?, ?> INSTANCE = new None<>();

        @Override
        public LiCase<T, E> filter_map(Function<? super T, Object> filter, Function<? super T, ? extends E> mapping) {
            return this;
        }

        @Override
        public <R> LiCase<T, E> case_map(Class<R> keyType, Function<? super R, ? extends E> mapping) {
            return this;
        }

        @Override
        public <F1, F2> LiCase<T, E> case_map(Class<F1> keyType, Function<? super F1, ? extends F2> f1, Function<? super F2, ? extends E> f2) {
            return this;
        }

        @Override
        public Lino<E> lino() {
            return Lino.none();
        }
    }
}
