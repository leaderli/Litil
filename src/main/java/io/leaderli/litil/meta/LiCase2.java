package io.leaderli.litil.meta;

import io.leaderli.litil.collection.LiFlux;
import io.leaderli.litil.collection.LiMoNo;

import java.util.function.Function;

/**
 * when match lino real type R , apply store function<R,E> to transfer lino<T> to lino<E>.
 * only execute function when call {@link #lino()}
 *
 * @param <T> the type parameter of mono
 * @param <E> the type parameter of mapping mono
 */
public class LiCase2<T, E> {

    private final Lino<T> mono;

    private final LiFlux<LiTuple<Class<?>, Function<Object, ? extends E>>> caseWhen = LiFlux.empty();

    private LiCase2(Lino<T> mono) {
        this.mono = mono;
    }

    /**
     * @param liNo the origin  liNo
     * @param <T>  the type parameter of liNo
     * @param <E>  the type parameter of transferred  liNo
     * @return new LiCase
     */
    public static <T, E> LiCase2<T, E> of(Lino<T> liNo) {
        return new LiCase2<>(liNo);
    }

    public <R> Lino<R> filter_map(Function<T, Object> filter, Function<? super T, ? extends R> mapping) {
        return mono.filter(filter).map(mapping);
    }


    /**
     * not really  apply function<R,T>  ,just cache it
     *
     * @param keyType matched type
     * @param mapping function<R,T> to convert R to mono<T>
     * @param <R>     the type parameter of real mono type
     * @return this
     */
    @SuppressWarnings("unchecked")
    public <R> LiCase2<T, E> case_map(Class<R> keyType, Function<? super R, ? extends E> mapping) {
        this.caseWhen.add(LiTuple.of(keyType, (Function<Object, ? extends E>) mapping));
        return this;
    }

    /**
     * not really  apply function<R,M> and function<M,R> ,just cache it
     * the mono<T> can not direct transfer to mono<E> , should use to step to convert
     *
     * @param keyType  matched type
     * @param mapping1 function<R,M> to convert R to mono<M>
     * @param mapping2 function<M,E> to convert M to mono<E>
     * @param <R>      the type parameter of real mono type
     * @param <M>      the type parameter of middle function
     * @return this
     */
    @SuppressWarnings("unchecked")
    public <R, M> LiCase2<T, E> case_map(Class<R> keyType, Function<? super R, ? extends M> mapping1, Function<? super M, ? extends E> mapping2) {
        Function<Object, ? extends E> mapping = (Function<Object, E>) o -> LiMoNo.of(mapping1.apply((R) o)).map(mapping2).get();
        this.caseWhen.add(LiTuple.of(keyType, mapping));
        return this;
    }

//
//    /**
//     * @return return first match case_map function
//     */
//    public Lino<E> lino() {
//        return this.caseWhen.map(cw -> (E) mono.cast(cw._1).map(cw._2).get()).getFirst();
//    }
}
