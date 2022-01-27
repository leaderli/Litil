package io.leaderli.litil.bit;

import io.leaderli.litil.meta.Lino;
import io.leaderli.litil.meta.Lira;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

/**
 * @author leaderli
 * @since 2022/1/27
 */
public class BitUtil {

    /**
     * @param min_width the min min_width of binary string
     * @param value     a int value
     * @return a binary string value of int with specific min_width
     */
    @SuppressWarnings("all")
    public static String toMonoSpaceBinary(int min_width, int value) {
        return String.format("%" + min_width + "s", Integer.toBinaryString(value)).replace(' ', '0');

    }


    /**
     * @param value a int value that is positive
     * @return get the position of binary value  has value
     * i.e. 0b1001 -->  [0b1000,0b1]
     */
    public static List<BitEnum> getSetBinaries(int value) {

        if (value < 1) {
            return new ArrayList<>();
        }
        List<BitEnum> result = new ArrayList<>();

        for (int i = 0; i < Integer.SIZE; i++) {
            int set = value & (1 << i);
            if (set > 0) {

                result.add(BitEnum.values()[i]);
            }
        }
        return result;
    }

    /**
     * @param value  a value use bit mark to represent state
     * @param states the state name
     * @return the states of exists bit mark
     * i.e   ["a","b","c"]  0b110 -->   'b|c'
     */
    public static String transferSetBinariesToNames(int value, List<String> states) {

        return String.join("|", Lira.of(getSetBinaries(value)).safe_map(bi -> states.get(bi.length)).getRaw());

    }


    public static String transferSetBinariesToNames(int value, Class<?> stateClass) {


        ToIntFunction<Field> toInt = field -> {
            try {
                if (field.getType() == Integer.class || field.getType() == int.class) {

                    return (Integer) field.get(null);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return 0;
        };


        Map<Integer, String> map = new HashMap<>();
        for (Field field : stateClass.getFields()) {
            map.put(toInt.applyAsInt(field), field.getName());
        }

        List<String> states = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            Lino.of(map.get(1 << i)).filter(v -> v.trim().length() > 0).then(states::add);
        }
        return transferSetBinariesToNames(value, states);

    }
}
