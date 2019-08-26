package org.emgen.http.request;

/**
 * @since 1.0.0
 */
public enum RequestAction {

    GET(false, true),
    HEAD(false, false),
    POST(true, true),
    PUT(true, true),
    DELETE(true, true),
    CONNECT(false, true),
    OPTIONS(false, true),
    TRACE(false, false),
    PATCH(true, true);

    private boolean supportsRequestBody;
    private boolean supportsResponseBody;

    RequestAction(boolean supportsRequestBody, boolean supportsResponseBody) {
        this.supportsRequestBody = supportsRequestBody;
        this.supportsResponseBody = supportsResponseBody;
    }

    public boolean supportsRequestBody() {
        return supportsRequestBody;
    }

    public boolean supportsResponseBody() {
        return supportsResponseBody;
    }
}
