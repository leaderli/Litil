package io.leaderli.litil.util;

import io.leaderli.litil.meta.LiValue;

import java.util.Iterator;
import java.util.Map;

/**
 * a tool parse object to boolean
 *
 * @author leaderli
 * @since 2022/1/21 8:59 AM
 */
public class LiBooleanUtil {


    /**
     * parse object to boolean by below list type, or the {@code object != null}
     *
     * <ul>
     *     <li> {@link #parseBoolean(LiValue)}</li>
     *     <li> {@link #parseBoolean(Boolean)}</li>
     *     <li> {@link #parseBoolean(Iterator)}</li>
     *     <li> {@link #parseBoolean(Iterable)}</li>
     *     <li> {@link #parseBoolean(Map)}</li>
     * </ul>
     *
     * @param obj a obj
     * @return the value of boolean
     */
    public static boolean parseBoolean(Object obj) {

        if (obj == null) {
            return false;
        }
        if (obj instanceof LiValue) {
            return parseBoolean((LiValue) obj);
        }
        if (obj instanceof Boolean) {
            return parseBoolean((Boolean) obj);
        }
        if (obj instanceof Iterable) {
            return parseBoolean((Iterable<?>) obj);
        }
        if (obj instanceof Iterator) {
            return parseBoolean((Iterator<?>) obj);
        }
        if (obj instanceof Map) {
            return parseBoolean((Map<?, ?>) obj);
        }

        return true;
    }


    /**
     * @param value the LiValue
     * @return true if value not null and value {@code isPresent() == true } otherwise false
     */
    public static boolean parseBoolean(LiValue value) {
        return value != null && value.isPresent();
    }

    /**
     * @param value the Boolean value
     * @return value
     */
    public static boolean parseBoolean(Boolean value) {
        return value != null && value;
    }

    /**
     * @param iterable the value of iterable
     * @return true if value  not null and value {@code iterator().hasNext() == true} otherwise false
     */
    public static boolean parseBoolean(Iterable<?> iterable) {
        return iterable != null && iterable.iterator().hasNext();
    }
    /**
     * @param iterator the value of iterable
     * @return true if value  not null and value {@code hasNext() == true} otherwise false
     */
    public static boolean parseBoolean(Iterator<?> iterator) {
        return iterator != null && iterator.hasNext();
    }
    /**
     * @param map the value of iterable
     * @return true if value  not null and value {@code isEmpty() != true} otherwise false
     */
    public static boolean parseBoolean(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }
}
