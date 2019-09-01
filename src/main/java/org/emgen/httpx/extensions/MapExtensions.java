package org.emgen.httpx.extensions;

import org.emgen.httpx.prerequisites.Prerequisites;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @since 1.0.0
 */
public final class MapExtensions {

    private MapExtensions() {
        throw new InstantiationError("org.emgen.httpx.extensions.MapExtensions.class cannot be instantiated.");
    }

    /**
     * Check is provided {@param map} null or is empty.
     *
     * @param map to test.
     * @return true in case {@param map} is null or empty.
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * Removes entries from {@param map} by provided key {@param predicate}.
     * {@param map} is not mutated.
     *
     * @param map       to update.
     * @param predicate to update by.
     * @param <K>       {@link Map}'s key type.
     * @param <V>       {@link Map}'s value type.
     * @return Updated {@link Map} by key's {@param predicate}.
     */
    public static <K, V> Map<K, V> removeByKeyPredicate(Map<K, V> map, Predicate<K> predicate) {
        Prerequisites.exists(map);
        Prerequisites.exists(predicate);

        Map<K, V> res = new HashMap<>();

        map.forEach((key, value) -> {
            if (!predicate.test(key)) {
                res.put(key, value);
            }
        });

        return res;
    }
}
