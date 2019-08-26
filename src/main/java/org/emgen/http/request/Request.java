package org.emgen.http.request;

import org.emgen.http.exceptions.RequestCreationException;
import org.emgen.extensions.StringExtensions;

import java.util.*;

/**
 * @since 1.0.0
 */
public final class Request {

    private final String target;
    private final RequestAction action;
    private final String body;
    private final Map<String, List<String>> parameters;
    private final Map<String, List<String>> headers;
    private final RequestOptions options;

    private Request(
        final String target,
        final RequestAction action,
        final String body,
        final Map<String, List<String>> parameters,
        final Map<String, List<String>> headers,
        final RequestOptions options
    ) {
        this.target = target;
        this.action = action;
        this.body = body;
        this.parameters = parameters;
        this.headers = headers;
        this.options = options;
    }

    public String target() {
        return target;
    }

    public RequestAction action() {
        return action;
    }

    public String body() {
        return body;
    }

    public Map<String, List<String>> parameters() {
        return new HashMap<>(parameters);
    }

    public Map<String, List<String>> headers() {
        return new HashMap<>(headers);
    }

    public RequestOptions options() {
        return options;
    }

    /**
     * Checks does request sends body during it's execution.
     * <p>
     * Use cases:
     * - Given request, that's {@link Request#body} == null, returns FALSE;
     * - Given request, that's {@link Request#body} == "", returns FALSE;
     * - Given request, that's {@link Request#body} == " ", returns FALSE;
     * - Given request, that's  {@link Request#action} does not support request body, returns FALSE;
     * - Given request, that has non - empty {@link Request#body} & {@link Request#action},
     * that supports request body, returns TRUE.
     *
     * @return true in case request contains body and request's {@code RequestAction} supports request body.
     */
    public boolean sendsBody() {
        return !StringExtensions.isEmpty(body) && action.supportsRequestBody();
    }

    @Override
    public String toString() {
        return "Request{" +
            "target='" + target + '\'' +
            ", action=" + action +
            ", body='" + body + '\'' +
            ", parameters=" + parameters +
            ", headers=" + headers +
            ", options=" + options +
            '}';
    }

    public static final class Creator {

        private String target;
        private RequestAction action;
        private String body;
        private Map<String, List<String>> parameters = new HashMap<>();
        private Map<String, List<String>> headers = new HashMap<>();
        private RequestOptions options = new RequestOptions.Creator().create();

        public Creator target(String target) {
            this.target = target;
            return this;
        }

        public Creator action(RequestAction action) {
            this.action = action;
            return this;
        }

        public Creator body(String body) {
            this.body = body;
            return this;
        }

        public Creator parameters(Map<String, List<String>> parameters) {
            this.parameters = parameters;
            return this;
        }

        /**
         * Puts parameter in parameter's map.
         * Parameter's map is instantiated in case it's null.
         * Parameter's map contains query parameters, therefore parameters, that has null or empty {@param name} are
         * ignored.
         * <p>
         * Use cases:
         * - Given {@param name} == null, parameter is ignored.
         * - Given {@param name} == "", parameter is ignored.
         * - Given {@param name} == " ", parameter is ignored.
         * - Given non - empty {@param name}, parameter is inserted into {@link Creator#parameters} map.
         *
         * @param name - parameter's to be inserted, name (key).
         * @param value - parameter's to be inserted, value.
         *
         * @return instance of {@link Creator}.
         */
        public Creator parameter(String name, String value) {
            if (parameters == null) {
                parameters = new HashMap<>();
            }

            if (!StringExtensions.isEmpty(name)) {
                insertToMap(parameters, name, value);
            }

            return this;
        }

        public Creator headers(Map<String, List<String>> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * Puts header in header's map.
         * Header's map is instantiated in case it's null.
         * <p>
         * User cases:
         * - Given {@param name} and {@param value}, header is inserted into {@link Creator#headers} map.
         *
         * @param name - header's to be inserted, name (key),
         * @param value - header's to be inseted, value.
         *
         * @return instance of {@link Creator}.
         */
        public Creator header(String name, String value) {
            if (headers == null) {
                headers = new HashMap<>();
            }

            insertToMap(headers, name, value);
            return this;
        }

        public Creator options(RequestOptions options) {
            this.options = options;
            return this;
        }

        /**
         * Creates {@link Request}.
         *
         * @return created and ready - to - execute {@link Request}.
         *
         * @throws RequestCreationException in case {@link Creator#target} is empty or does not contain
         * neither http://, nor https:// protocol.
         * @throws RequestCreationException in case {@link Creator#action} is null.
         */
        public Request create() {
            if (target == null) {
                throw new RequestCreationException("Request could not be created - parameter 'target' cannot be null");
            }

            if (target.trim().isEmpty()) {
                throw new RequestCreationException("Request could not be created - parameter 'target' cannot be empty");
            }

            if (!StringExtensions.startsAsAny(target, "http://", "https://")) {
                throw new RequestCreationException("Request could not be created - parameter 'target' has to contain http:// or https:// protocol");
            }

            if (action == null) {
                throw new RequestCreationException("Request could not be created - parameter 'action' cannot be null");
            }

            return new Request(target, action, body, parameters, headers, options);
        }

        private void insertToMap(Map<String, List<String>> map, String key, String value) {
            List<String> values = map.get(key);

            if (values == null) {
                map.put(key, new ArrayList<>(Collections.singletonList(value)));
            } else {
                values.add(value);
            }
        }
    }
}
