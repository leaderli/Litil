package io.leaderli.litil.params;

import io.leaderli.litil.exception.LiAssertUtil;
import io.leaderli.litil.meta.Lira;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author leaderli
 * @since 2022/2/25 10:09 AM
 */
public class LiValueArgumentsProvider implements LiArgumentsProvider, LiAnnotationConsumer<LiValueSource> {
    private Lira arguments;

    @Override
    public Lira provideArguments() {
        return this.arguments;
    }

    @Override
    public void accept(LiValueSource source) {

        Lira<Object> values = Lira.<Object>of(source.shorts(),
                source.bytes(),
                source.ints(),
                source.longs(),
                source.floats(),
                source.doubles(),
                source.chars(),
                source.booleans(),
                source.strings())
                .filter(array -> Array.getLength(array) > 0);

        LiAssertUtil.assertTrue(values.size() == 1, () -> "Exactly one type of input must be provided in the @"
                + ValueSource.class.getSimpleName() + " annotation, but there were " + values.size());
        System.out.println(values);
        this.arguments = values.first().liraByArray(e->(Object[]) e);

    }

    public static void main(String[] args) {
        int a =1;
        Object o = a;

        int [] arr = {1};

    }

}
