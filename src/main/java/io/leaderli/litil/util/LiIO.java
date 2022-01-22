package io.leaderli.litil.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * a util to operate io stream
 */
public class LiIO {

    public static final Charset DEFAULT_CHARACTER_ENCODING = StandardCharsets.UTF_8;

    /**
     * @param string a string content
     * @return a inputStream  converted by string
     */
    public static InputStream createContentStream(String string) {
        if (string == null) {
            string = "";
        }
        return (new ByteArrayInputStream(string.getBytes(LiIO.DEFAULT_CHARACTER_ENCODING)));
    }
}
