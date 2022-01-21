package com.leaderli.liutil.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class LiIO {

    public static final Charset DEFAULT_CHARACTER_ENCODING = StandardCharsets.UTF_8;

    public static InputStream createContentStream(String content) {
        return (new ByteArrayInputStream(content.getBytes(LiIO.DEFAULT_CHARACTER_ENCODING)));
    }
}
