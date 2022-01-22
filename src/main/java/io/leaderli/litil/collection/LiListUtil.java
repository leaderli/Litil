package io.leaderli.litil.collection;

import java.util.*;

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
}
