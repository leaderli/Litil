package com.leaderli.liutil.collection;

import com.leaderli.liutil.meta.LiTuple2;

import java.util.function.Function;

/**
 * when match mono real type R , apply store function<R,E> to transfer mono<T> to mono<E>.
 * only execute function when call {@link #mono()}
 *
 * @param <T> the type parameter of mono
 * @param <E> the type parameter of mapping mono
 */
public class LiCase<T, E> {

    private final LiMono<T> mono;

    private final LiFlux<LiTuple2<Class<?>, Function<Object, ? extends E>>> caseWhen = LiFlux.empty();

    private LiCase(LiMono<T> mono) {
        this.mono = mono;
    }

    /**
     * @param mono the origin  mono
     * @param <T>  the type parameter of mono
     * @param <E>  the type parameter of transferred  mono
     * @return new LiCase
     */
    public static <T, E> LiCase<T, E> of(LiMono<T> mono) {
        return new LiCase<>(mono);
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
    public <R> LiCase<T, E> case_map(Class<R> keyType, Function<? super R, ? extends E> mapping) {
        this.caseWhen.add(LiTuple2.of(keyType, (Function<Object, ? extends E>) mapping));
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
    public <R, M> LiCase<T, E> case_map(Class<R> keyType, Function<? super R, ? extends M> mapping1, Function<? super M, ? extends E> mapping2) {
        Function<Object, ? extends E> mapping = (Function<Object, E>) o -> LiMono.of(mapping1.apply((R) o)).map(mapping2).get();
        this.caseWhen.add(LiTuple2.of(keyType, mapping));
        return this;
    }

    /**
     * @return return first match case_map function
     */
    public LiMono<E> mono() {
        return this.caseWhen.map(cw -> (E) mono.cast(cw._1).map(cw._2).get()).getFirst();
    }
}
