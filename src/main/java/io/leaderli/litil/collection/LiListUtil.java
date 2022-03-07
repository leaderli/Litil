package io.leaderli.litil.collection;

import io.leaderli.litil.meta.Lira;
import io.leaderli.litil.type.LiClassUtil;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class LiListUtil {

    /**
     * @param list a list
     * @param <T>  the componentType of list
     * @return the elements  which is duplicate at list
     */
    public static <T> List<T> getDuplicateElement(List<T> list) {

        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> duplicate = new ArrayList<>();

        Set<T> uniq = new HashSet<>();

        for (T t : list) {
            if (!uniq.add(t)) {
                duplicate.add(t);
            }
        }

        return duplicate;

    }


    @SafeVarargs
    public static List<List<Object>> cartesianProduct(List<Object>... elements) {

        if (elements == null || elements.length == 0) {
            return new ArrayList<>();
        }
        List<List<Object>> x = Lira.of(elements[0]).map(Arrays::asList).getRaw();

        for (int i = 1; i < elements.length; i++) {

            List<Object> y = elements[i];

            x = x.stream()
                    .map(li ->


                            Lira.of(y)
                                    .map(e -> {
                                        List<Object> raw = Lira.of(li).getRaw();
                                        raw.add(e);
                                        return raw;
                                    })
                                    .getRaw()
                    )
                    .flatMap(List::stream).collect(Collectors.toList());
        }

        return x;
    }


    /**
     * @param originalArray the arr object
     * @return return null if  originalArray is null or not arr ;
     * if arr is primitive arr , return the wrapper arr.
     * otherwise cast to arr
     */
    public static Object[] toWrapperArray(Object originalArray) {

        if (originalArray == null || !originalArray.getClass().isArray()) {
            return null;
        }
        Class<?> componentType = originalArray.getClass().getComponentType();
        if (componentType.isPrimitive()) {
            int length = Array.getLength(originalArray);
            Object[] array = LiClassUtil.newArray(componentType, length);

            for (int i = 0; i < length; i++) {
                array[i] = Array.get(originalArray, i);
            }

            return array;
        }
        return (Object[]) originalArray;
    }
}
