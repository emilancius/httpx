package org.emgen.httpx.http.response;

import org.emgen.httpx.extensions.StringExtensions;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @since 1.0.0
 */
public final class Response {

    private final int code;
    private final String message;
    private final String body;
    private final Map<String, List<String>> headers;
    private final long duration;

    public Response(
            final int code,
            final String message,
            final String body,
            final Map<String, List<String>> headers,
            final long duration
    ) {
        this.code = code;
        this.message = message;
        this.body = body;
        this.headers = headers;
        this.duration = duration;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

    public String body() {
        return body;
    }

    public Map<String, List<String>> headers() {
        return new HashMap<>(headers);
    }

    public long duration() {
        return duration;
    }

    public List<HttpCookie> cookies() {
        return HttpCookie.parse(String.join("; ", headers.get("Set-Cookie")));
    }

    /**
     * @return bytes count for {@link Response#body}, using UTF-8 character encoding.
     */
    public long bodyBytesCount() {
        return StringExtensions.bytesCount(body);
    }

    /**
     * @return bytes count for {@link Response#headers} map, using UTF-8 character encoding.
     */
    public long headersBytesCount() {
        if (headers == null || headers.isEmpty()) {
            return 0L;
        }

        return headers.entrySet().stream().mapToLong(this::headerBytesCount).sum();
    }

    /**
     * @return return bytes count for {@link Response} - this includes {@link Response#body} & {@link Response#headers},
     * using UTF-8 character encoding.
     */
    public long bytesCount() {
        return bodyBytesCount() + headersBytesCount();
    }

    private long headerBytesCount(Map.Entry<String, List<String>> header) {
        long keyBytesCount = StringExtensions.bytesCount(header.getKey());
        return keyBytesCount + header.getValue().stream().mapToLong(StringExtensions::bytesCount).sum();
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", body='" + body + '\'' +
                ", headers=" + headers +
                ", duration=" + duration +
                '}';
    }
}
