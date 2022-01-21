package com.leaderli.liutil.util;

import com.leaderli.liutil.collection.LiMono;

import java.util.*;

public class LiMapUtil {

    public static <K, V> Map<K, V> override(Map<K, V> origin, Map<K, V> override) {

        return _override(origin, override);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> T _override(T origin, T override) {

        if (origin == null) {
            return override;
        }

        if (override == null) {
            return origin;
        }

        if (origin instanceof Map && override instanceof Map) {

            Map _return = (Map) origin;
            _return.forEach((k, v) -> _return.put(k, _override(v, ((Map) override).get(k))));
            return (T) _return;
        }

        return override;

    }


    /**
     * 根据key，查询一个list，筛选list中满足指定class类型的数据。当查询不到或者无满足class类型的数据，会返回一个空的集合
     */
    public static <T> List<T> getTypeList(Map<String, ?> map, String key, Class<T> listItemType) {

        if (map == null) {
            return Collections.emptyList();
        }
        Object value = map.get(key);

        if (value instanceof List) {

            //noinspection
            return LiCastUtil.cast((List<?>) value, listItemType);
        }
        return Collections.emptyList();
    }

    public static List<String> getTypeList(Map<String, ?> map, String key) {
        return getTypeList(map, key, String.class);
    }

    /**
     * 根据key，查询一个Map，筛选map中满足指定class类型的数据。当查询不到或者无满足class类型的数据，会返回一个空的集合
     */
    public static <K, V> Map<K, V> getTypeMap(Map<String, ?> map, String key, Class<K> keyType, Class<V> valueType) {

        if (map == null) {
            return new HashMap<>();
        }
        Object value = map.get(key);

        if (value instanceof Map) {
            return LiCastUtil.cast((Map<?, ?>) value, keyType, valueType);
        }

        return new HashMap<>();
    }

    public static <V> Map<String, V> getTypeMap(Map<String, ?> map, String key, Class<V> valueType) {
        return getTypeMap(map, key, String.class, valueType);
    }

    public static Map<String, String> getTypeMap(Map<String, ?> map, String key) {
        return getTypeMap(map, key, String.class, String.class);
    }

    /**
     * 根据key，查询指定class类型的值，当查询不到或类型不匹配时，返回空
     */
    public static <T> LiMono<T> getTypeObject(Map<String, ?> map, String key, Class<T> itemType) {
        return LiMono.of(map).map(to->LiCastUtil.cast(map.get(key), itemType));
    }

    public static LiMono<String> getTypeObject(Map<String, String> map, String key) {
        return getTypeObject(map, key, String.class);
    }

}
