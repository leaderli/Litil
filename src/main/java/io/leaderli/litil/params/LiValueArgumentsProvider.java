package io.leaderli.litil.params;

import io.leaderli.litil.collection.LiListUtil;
import io.leaderli.litil.meta.Lira;
import io.leaderli.litil.type.LiClassUtil;

import java.lang.reflect.Array;

/**
 * @author leaderli
 * @since 2022/2/25 10:09 AM
 */
public class LiValueArgumentsProvider<T> extends LiArgumentsProvider<LiValueSource, T> {

    protected LiValueArgumentsProvider(LiValueSource source, Class<T> componentType) {
        super(source, componentType);
    }

    @Override
    public Lira<T> provideArguments(LiValueSource source, Class<T> type) {

        return Lira.<Object>of
                (
                        source.shorts(),
                        source.bytes(),
                        source.ints(),
                        source.longs(),
                        source.floats(),
                        source.doubles(),
                        source.chars(),
                        source.booleans(),
                        source.strings()
                )
                .filter(array -> Array.getLength(array) > 0)
                .filter(arr -> LiClassUtil.isAssignableFromOrIsWrapper(type, arr.getClass().getComponentType()))
                .first().liraByArray(LiListUtil::toWrapperArray).cast(type);
    }


}
