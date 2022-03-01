package io.leaderli.litil.util;

import io.leaderli.litil.collection.LiListUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author leaderli
 * @since 2022/1/22
 */
public class LiListUtilTest {

    @Test

    public void test() {
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Assertions.assertEquals("[apple, apple, banana]", LiListUtil.getDuplicateElement(items).toString());

    }


    @Test
    public void testCartesianProduct() throws Throwable {
        List<List<Object>> lists = LiListUtil.cartesianProduct(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
        Assertions.assertEquals(lists.toString(), "[[1, 4], [1, 5], [1, 6], [2, 4], [2, 5], [2, 6], [3, 4], [3, 5], [3, 6]]");

        List<List<Object>> lists2 = LiListUtil.cartesianProduct(Arrays.asList(1, 2), Arrays.asList("-", "*"), Arrays.asList("a", "b"));
        Assertions.assertEquals("[[1, -, a], [1, -, b], [1, *, a], [1, *, b], [2, -, a], [2, -, b], [2, *, a], [2, *, b]]", lists2.toString());
    }
}
