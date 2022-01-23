package io.leaderli.litil.type;

import io.leaderli.litil.meta.Lino;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * store value by it's type
 */
public class LiTypeMap {

    private final Map<Class<?>, Object> proxy = new HashMap<>();

    /**
     * @param type  map key, it's a generic type
     * @param value map value, it's a generic value
     * @param <T>   the type parameter of key and value
     * @return map value
     */
    public <T> T put(Class<T> type, T value) {
        proxy.put(LiClassUtil.primitiveToWrapper(type), value);
        return value;
    }


    /**
     * @param type     map key, it's a generic type
     * @param supplier if map value don not exits, will use supplier
     * @param <T>      the type parameter of key and value
     * @return map value or  supplier.get()
     */
    public <T> Lino<T> computeIfAbsent(Class<T> type, Supplier<T> supplier) {
        return get(type).orOther(() -> put(type, supplier.get()));
    }


    /**
     * @param type the map key
     * @param <T>  the type parameter of key and value
     * @return return {@link Lino#isEmpty()} if map don not contains the value of type
     * otherwise return new lino with  the value of type
     */
    @SuppressWarnings("unchecked")
    public <T> Lino<T> get(Class<T> type) {
        return Lino.of((T) proxy.get(LiClassUtil.primitiveToWrapper(type)));
    }


    public <T> void remove(Class<T> type) {
        this.proxy.remove(type);
    }


}
