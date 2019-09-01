package org.emgen.httpx.extensions;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @since 1.0.0
 */
public final class URLEncodingExtensions {

    public static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.name();

    private URLEncodingExtensions() {
        throw new InstantiationError("org.emgen.httpx.extensions.URLEncodingExceptions::class cannot be instantiated.");
    }

    /**
     * Encodes {@param string}, using provided {@param charset}.
     * In case {@param charset} is not provided, function uses {@link StandardCharsets#UTF_8}.
     *
     * @param string  to encode.
     * @param charset to encode string by.
     * @return encoded {@param string}, using {@param charset}.
     */
    public static String encode(String string, String charset) {
        try {
            return URLEncoder.encode(string, StringExtensions.isEmpty(charset) ? DEFAULT_CHARSET : charset);
        } catch (Exception e) {
            return string;
        }
    }

    /**
     * Encodes {@param string}, using {@link StandardCharsets#UTF_8} charset.
     *
     * @param string to encode.
     * @return {@link StandardCharsets#UTF_8} encoded {@param string}.
     */
    public static String encode(String string) {
        return encode(string, DEFAULT_CHARSET);
    }

    /**
     * Decodes {@param string}, using provided {@param charset}.
     * In case {@param charset} is not provided, function uses {@link StandardCharsets#UTF_8}.
     *
     * @param string  to decode.
     * @param charset to decode string by.
     * @return decoded {@param string}, using {@param charset}.
     */
    public static String decode(String string, String charset) {
        try {
            return URLDecoder.decode(string, StringExtensions.isEmpty(charset) ? DEFAULT_CHARSET : charset);
        } catch (Exception e) {
            return string;
        }
    }

    /**
     * Decodes {@param string}, using {@link StandardCharsets#UTF_8} charset.
     *
     * @param string to decode.
     * @return {@link StandardCharsets#UTF_8} decoded {@param string}.
     */
    public static String decode(String string) {
        return decode(string, DEFAULT_CHARSET);
    }
}
