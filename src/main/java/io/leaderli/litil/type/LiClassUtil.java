package io.leaderli.litil.type;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * provide some useful method operate classes
 *
 * @author leaderli
 * @since 2022-01-22
 */
public class LiClassUtil {

    /**
     * Maps names of primitives to their corresponding primitive {@code Class}es.
     */
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_MAP = new HashMap<>();
    private static final Map<Class<?>, Class<?>> WRAPPER_PRIMITIVE_MAP = new HashMap<>();

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

        PRIMITIVE_WRAPPER_MAP.forEach((k, v) -> WRAPPER_PRIMITIVE_MAP.put(v, k));
    }

    /**
     * @param cls the class to convert, may be null,may be array class
     * @return the wrapper class for {@code cls} or {@code cls} if
     * {@code cls} is not primitive. {@code null} if null input.
     */
    public static Class<?> primitiveToWrapper(final Class<?> cls) {
        Class<?> convertedClass = cls;
        if (cls != null) {

            if (cls.isPrimitive()) {
                convertedClass = PRIMITIVE_WRAPPER_MAP.get(cls);
            } else if (cls.isArray()) {
                convertedClass = LiClassUtil.newArray(primitiveToWrapper(cls.getComponentType()), 0).getClass();
            }
        }
        return convertedClass;
    }


    /**
     * @param father the superclass
     * @param son    the subclass
     * @return the {@code boolean} value indicating  {@code son} can be assigned to {@code father}
     */
    public static boolean isAssignableFromOrIsWrapper(Class<?> father, Class<?> son) {


        if (father != null && son != null) {

            if (father.isArray()) {

                if (son.isArray()) {

                    //对于数组，基础类型的数组无法进行强转
                    father = father.getComponentType();
                    son = son.getComponentType();
                    if (father.isPrimitive() || son.isPrimitive()) {
                        return father == son;
                    }
                    return father.isAssignableFrom(son);
                }
            } else {

                if (father.isAssignableFrom(son)) {
                    return true;
                }
                return primitiveToWrapper(son) == primitiveToWrapper(father);
            }
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

        return new ArrayList<>();
    }

    /**
     * @param componentType the {@code Class} object representing the
     *                      component type of the new array
     * @param length        the length of the new array
     * @param <T>           the type parameter of componentType
     * @return the new array
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(Class<? extends T> componentType, int length) {
        return (T[]) Array.newInstance(primitiveToWrapper(componentType), length);
    }

    /**
     * @param obj      A object
     * @param castType the type that obj can cast to
     * @param <T>      the type parameter of castType
     * @return the object after casting, or null if obj is null
     * or castType is null or  obj can not assigned  to {@code T}
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj, Class<T> castType) {
//        castType = (Class<T>) primitiveToWrapper(castType);
        if (obj == null || castType == null) {
            return null;
        }

        if (isAssignableFromOrIsWrapper(castType, obj.getClass())) {

            return (T) obj;
        }

        return null;
    }

    /**
     * @param list     An list object
     * @param castType the componentType that obj can cast to
     * @param <T>      the type parameter of castType
     * @return the list mapping by {@link #cast(Object, Class)} and filter not null
     */
    public static <T> List<T> filterCanCast(List<?> list, Class<T> castType) {

        if (list == null || castType == null) {
            return new ArrayList<>();
        }
        return list.stream()
                .map(item -> cast(item, castType))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * @param map       An map object
     * @param keyType   the type of map key can cast to
     * @param valueType the type of map value can cast to
     * @param <K>       the type parameter of  keyType
     * @param <V>       the type parameter of valueType
     * @return the map  cast key and value type and filter the element can not cast
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> filterCanCast(Map<?, ?> map, Class<K> keyType, Class<V> valueType) {

        if (map == null || keyType == null || valueType == null) {
            return new HashMap<>();
        }
        return map.entrySet().stream()
                .filter(entry -> isAssignableFromOrIsWrapper(keyType, entry.getKey().getClass())
                        && isAssignableFromOrIsWrapper(valueType, entry.getValue().getClass())
                )
                .collect(Collectors.toMap(
                        entry -> (K) entry.getKey(),
                        entry -> (V) entry.getValue())
                );
    }


}
