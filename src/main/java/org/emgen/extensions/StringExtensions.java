package org.emgen.extensions;

import java.util.Arrays;

/**
 * @since 1.0.0
 */
public final class StringExtensions {

    private StringExtensions() {
        throw new AssertionError("StringExtensions::class cannot be instantiated.");
    }

    /**
     * Checks is {@param string} starts as any string (sequence) in {@param characterSequences}.
     * <p>
     * Use cases:
     * - Given {@param string} == null, returns FALSE;
     * - Given {@param string} == "", returns FALSE;
     * - Given {@param characterSequences} == null, returns FALSE;
     * - Given {@param characterSequences} is empty vararg, returns FALSE;
     * - Given non - empty {@param string} & non - empty {@param characterSequences},
     * but string does not start as any sequence, returns FALSE;
     * - Given non - empty {@param string} & non - empty {@param characterSequences},
     * & string starts as any sequence in {@param characterSequences} vararg, returns TRUE.
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
     * <p>
     * Use cases:
     * - Given {@param string} == null, returns TRUE;
     * - Given {@param string} == "", returns TRUE;
     * - Given {@param string} == " ", returns TRUE;
     * - Given non - empty string, e.g. {@param string} == "string", returns FALSE.
     *
     * @param string to test.
     * @return true in case string is null or string does not contain any characters besides spaces.
     */
    public static boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }
}
