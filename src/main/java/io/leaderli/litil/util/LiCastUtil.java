package io.leaderli.litil.util;

import java.util.*;
import java.util.stream.Collectors;

public class LiCastUtil {


    @SuppressWarnings("unchecked")
    public static <T> List<T> cast(List<?> origin, Class<T> castType) {

        if (origin == null || castType == null) {
            return Collections.emptyList();
        }
        return origin.stream()
                .filter(Objects::nonNull)
                .filter(item -> LiClassUtil.isAssignableFromOrIsWrapper(castType, item.getClass()))
                .map(item -> (T) item)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> cast(Map<?, ?> map, Class<K> keyType, Class<V> valueType) {

        if (map == null || keyType == null || valueType == null) {
            return new HashMap<>();
        }
        return map.entrySet().stream()
                .filter(entry -> LiClassUtil.isAssignableFromOrIsWrapper(keyType, entry.getKey().getClass())
                        && LiClassUtil.isAssignableFromOrIsWrapper(valueType, entry.getValue().getClass())

                )
                .collect(Collectors.toMap(
                        entry -> (K) entry.getKey(),
                        entry -> (V) entry.getValue())
                );
    }


    @SuppressWarnings("unchecked")
    public static <T> T cast(Object origin, Class<T> castType) {
        castType = (Class<T>) LiClassUtil.primitiveToWrapper(castType);
        if (origin == null || castType == null) {
            return null;
        }
        if (LiClassUtil.isAssignableFromOrIsWrapper(castType, origin.getClass())) {
            //noinspection unchecked
            return (T) origin;
        }
        return null;
    }

}
