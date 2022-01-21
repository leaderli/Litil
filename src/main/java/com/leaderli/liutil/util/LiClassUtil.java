package com.leaderli.liutil.util;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class LiClassUtil {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>();

    static {
        PRIMITIVE_WRAPPER_MAP.put(Boolean.TYPE, Boolean.class);
        PRIMITIVE_WRAPPER_MAP.put(Byte.TYPE, Byte.class);
        PRIMITIVE_WRAPPER_MAP.put(Character.TYPE, Character.class);
        PRIMITIVE_WRAPPER_MAP.put(Short.TYPE, Short.class);
        PRIMITIVE_WRAPPER_MAP.put(Integer.TYPE, Integer.class);
        PRIMITIVE_WRAPPER_MAP.put(Long.TYPE, Long.class);
        PRIMITIVE_WRAPPER_MAP.put(Double.TYPE, Double.class);
        PRIMITIVE_WRAPPER_MAP.put(Float.TYPE, Float.class);
        PRIMITIVE_WRAPPER_MAP.put(Void.TYPE, Void.class);
    }

    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        Class<?> convertedClass = cls;
        if (cls != null && cls.isPrimitive()) {
            convertedClass = PRIMITIVE_WRAPPER_MAP.get(cls);
        }
        return convertedClass;
    }

    public static boolean isAssignableFromOrIsWrapper(Class<?> father, Class<?> son) {

        if (father != null && son != null) {

            if (father.isAssignableFrom(son)) {
                return true;
            }
            return primitiveToWrapper(son) == primitiveToWrapper(father);
        }
        return false;
    }

    /**
     * @return the list of absolute jar path under webapp
     */
    public static List<String> getAppJars() {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = loader.getResources("META-INF");
            return Collections.list(resources).stream()
                    .filter(url -> "jar".equals(url.getProtocol()))
                    .map(URL::getFile)
                    .map(path -> path.replace("!/META-INF", "")
                            .replaceAll("^[^/]++/", ""))

                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();

    }
}
