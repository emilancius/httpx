package org.emgen.httpx.http;

import org.emgen.httpx.http.request.Request;
import org.emgen.httpx.http.response.Response;

/**
 * @since 1.0.0
 */
public interface Executor {

    Response execute(Request request);
}
