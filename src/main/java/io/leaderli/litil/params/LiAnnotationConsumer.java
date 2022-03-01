package io.leaderli.litil.params;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

/**
 * @author leaderli
 * @since 2022/2/24 5:15 PM
 */
public interface LiAnnotationConsumer<A extends Annotation> extends Consumer<A> {

}
