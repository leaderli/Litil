package io.leaderli.litil.util;

import io.leaderli.litil.collection.LiListUtil;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals("[apple, apple, banana]", LiListUtil.getDuplicateElement(items).toString());

    }

}
