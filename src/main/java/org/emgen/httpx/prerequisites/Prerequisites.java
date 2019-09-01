package org.emgen.httpx.prerequisites;

import org.emgen.httpx.http.exceptions.ArgumentExistenceException;

/**
 * @since 1.0.0
 */
public final class Prerequisites {

    private Prerequisites() {
        throw new InstantiationError("org.emgen.httpx.prerequisites.Prerequisite.class cannot be instantiated.");
    }

    /**
     * Checks does {@param argument} is not null.
     * In case {@param argument} is null, {@link ArgumentExistenceException} is thrown.
     *
     * @param argument to test.
     * @param message  - {@link ArgumentExistenceException}'s error message.
     * @param <T>      - {@param argument}'s type.
     */
    public static <T> void exists(T argument, String message) {
        if (argument == null) {
            throw new ArgumentExistenceException(message);
        }
    }

    /**
     * Checks does {@param argument} is not null.
     * In case {@param argument} is null, {@link ArgumentExistenceException} is thrown.
     *
     * @param argument to test.
     * @param <T>      - {@param argument}'s type.
     */
    public static <T> void exists(T argument) {
        exists(argument, null);
    }
}
