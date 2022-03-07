package io.leaderli.litil.params;

import java.lang.annotation.Annotation;

/**
 * @author leaderli
 * @since 2022/2/24 5:15 PM
 */
public interface LiAnnotationConsumer<A extends Annotation> {


    <T> void accept(LiValueSource source, Class<T> type);
}
