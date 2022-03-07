package io.leaderli.litil.params;

import java.lang.annotation.*;


/**
 * @author leaderli
 * @since 2022/2/24 4:36 PM
 */

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LiArgumentsSource {

    @SuppressWarnings("rawtypes")
    Class<? extends LiArgumentsProvider> value();

}
