package org.emgen.httpx.extensions;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @since 1.0.0
 */
public final class StringExtensions {

    private StringExtensions() {
        throw new InstantiationError("org.emgen.httpx.extensions.StringExtensions.class cannot be instantiated.");
    }

    /**
     * Checks does {@param string} starts as any character sequence in {@param characterSequences}.
     *
     * @param string             to test.
     * @param characterSequences sequences to test against {@param string}.
     * @return true in case string begins as any character sequence in {@param characterSequences} vararg.
     */
    public static boolean startsAsAny(String string, String... characterSequences) {
        return string != null
                && string.length() > 0
                && characterSequences != null
                && characterSequences.length > 0
                && Arrays.stream(characterSequences).anyMatch(sequence -> string.indexOf(sequence) == 0);
    }

    /**
     * Checks is {@param string} is empty. Any spaces are ignored.
     *
     * @param string to test.
     * @return true in case string is null or string does not contain any characters besides spaces.
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * Gets byte count for provided {@param string} parameter, using {@param charset}.
     * In case {@param charset} is not provided, function uses 'UTF-8'.
     * Supported charsets are provided in {@link java.nio.charset.StandardCharsets}.
     *
     * @param string  to test.
     * @param charset to use.
     * @return bytes count for provided {@param string}, using {@param charset}.
     * @throws RuntimeException in case provided {@param charset} is not supported.
     */
    public static long bytesCount(String string, String charset) {
        if (string == null || string.isEmpty()) {
            return 0L;
        }

        try {
            return string.getBytes(isEmpty(charset) ? StandardCharsets.UTF_8.name() : charset).length;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long bytesCount(String string) {
        return bytesCount(string, StandardCharsets.UTF_8.name());
    }
}
