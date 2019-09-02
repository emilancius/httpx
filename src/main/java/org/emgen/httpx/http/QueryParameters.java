package org.emgen.httpx.http;

import org.emgen.httpx.extensions.ListExtensions;
import org.emgen.httpx.extensions.MapExtensions;
import org.emgen.httpx.extensions.StringExtensions;
import org.emgen.httpx.extensions.URLEncodingExtensions;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 1.0.0
 */
public final class QueryParameters {

    public static final String QUERY_STRING_PREFIX = "?";
    public static final String QUERY_PARAMETERS_DELIMITER = "&";

    private QueryParameters() {
        throw new InstantiationError("org.emgen.httpx.http.QueryParameters.class cannot be instantiated.");
    }

    /**
     * Crates query string from {@param parameters}.
     * In case {@param parameters} are empty, empty string is returned.
     *
     * @param parameters - {@link Map} of query parameters to create query string from.
     * @return query string in case {@param parameters} exists and are not empty. In case there are
     * no {@param parameters} provided, empty string is returned.
     */
    public static String createQueryString(Map<String, List<String>> parameters) {
        if (MapExtensions.isEmpty(parameters)) {
            return "";
        }

        return QUERY_STRING_PREFIX + MapExtensions
                .removeByPredicate(parameters, StringExtensions::isEmpty)
                .entrySet()
                .stream()
                .map(entry -> entry.getValue()
                        .stream()
                        .map(value -> encodeQueryParameter(entry.getKey(), value == null ? "" : value))
                        .collect(Collectors.joining(QUERY_PARAMETERS_DELIMITER)))
                .collect(Collectors.joining(QUERY_PARAMETERS_DELIMITER));
    }

    /**
     * Extracts query parameters as a {@link Map} from {@param string} in case it contains query string.
     *
     * @param string to extract query parameters from.
     * @return {@link Map} of query parameters or empty {@link Map}, in case {@param string} does not
     * contain query string.
     */
    public static Map<String, List<String>> extractQueryParameters(String string) {
        Map<String, List<String>> parameters = new HashMap<>();

        for (String parameter : extractQueryString(string).substring(1).split(QUERY_PARAMETERS_DELIMITER)) {
            int index = parameter.indexOf('=');
            String key = index > 0 ? URLEncodingExtensions.decode(parameter.substring(0, index)) : null;

            if (key == null) {
                continue;
            }

            String value = parameter.length() > index + 1 ? URLEncodingExtensions.decode(parameter.substring(index + 1)) : null;
            List<String> values = parameters.get(key);

            if (values == null) {
                parameters.put(key, ListExtensions.create(value));
            } else {
                values.add(value);
            }
        }

        return parameters;
    }

    /**
     * Gets query string from {@param string} in case it contains one.
     *
     * @param string to extract query string from.
     * @return query string in case {@param string} contains one.
     * In case there is no query string - empty string is returned.
     */
    public static String extractQueryString(String string) {
        return containsQueryString(string) ? string.substring(queryStringIndex(string)) : "";
    }

    /**
     * Strips query string from {@param string} in case it contains it.
     *
     * @param string to strip query string from.
     * @return string, that has no query string.
     */
    public static String stripQueryString(String string) {
        return containsQueryString(string) ? string.substring(0, queryStringIndex(string)) : string;
    }

    public static Map<String, List<String>> mergeQueryParameters(Map<String, List<String>>... maps) {
        if (maps == null) {
            return Collections.emptyMap();
        }

        Map<String, List<String>> res = new HashMap<>();

        for (Map<String, List<String>> map : maps) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                List<String> curr = res.get(key);

                if (curr == null) {
                    res.put(key, value);
                } else {
                    value.forEach(curr::add);
                }
            }
        }

        return res;
    }

    private static boolean containsQueryString(String string) {
        return !StringExtensions.isEmpty(string) && string.contains(QUERY_STRING_PREFIX);
    }

    private static int queryStringIndex(String string) {
        return string.indexOf(QUERY_STRING_PREFIX);
    }

    private static String encodeQueryParameter(String name, String value) {
        return URLEncodingExtensions.encode(name) + "=" + URLEncodingExtensions.encode(value);
    }
}
