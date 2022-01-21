package com.leaderli.liutil.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class LiIOTest extends Assert {

    @Test
    public void testCreateContentStream() {

        String content = "123";
        InputStream inputStream = LiIO.createContentStream(content);
        String text = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        assertEquals(content, text);
    }
}
