package org.emgen.httpx.extensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @since 1.0.0
 */
public final class ListExtensions {

    private ListExtensions() {
        throw new InstantiationError("org.emgen.httpx.extensions.ListExtensions.class cannot be instantiated.");
    }

    /**
     * Checks is {@param list} is null or empty.
     *
     * @param list to test.
     * @return true in case {@param list} is null or is empty.
     */
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * Creates an {@link ArrayList} in case there are provided {@param components} and
     * empty list in case {@param components} are null or empty.
     *
     * @param components to create an {@link ArrayList} from.
     * @param <T>        type of components and {@link ArrayList}.
     * @return {@link ArrayList} of provided {@param components} and empty list
     * in case there are no {@param components}.
     */
    public static <T> List<T> create(T... components) {
        return components == null || components.length == 0 ? Collections.emptyList() : new ArrayList<>(Arrays.asList(components));
    }
}
