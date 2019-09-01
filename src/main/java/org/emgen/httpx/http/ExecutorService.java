package org.emgen.httpx.http;

import org.emgen.httpx.extensions.InputStreamExtensions;
import org.emgen.httpx.extensions.MapExtensions;
import org.emgen.httpx.http.request.ProxySettings;
import org.emgen.httpx.http.request.Request;
import org.emgen.httpx.http.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @since 1.0.0
 */
public final class ExecutorService implements Executor {

    /**
     * Executes provided {@param request}.
     *
     * @param request to execute.
     * @return execution {@link Response}.
     */
    @Override
    public Response execute(Request request) {
        long starts = System.nanoTime();
        HttpURLConnection connection = createConnection(request);

        try {
            prepareConnection(connection, request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Request created in: " + TimeUnit.MILLISECONDS.convert(System.nanoTime() - starts, TimeUnit.NANOSECONDS) + " ms");
        System.out.println("Executing " + request.action().name() + " request to " + connection.getURL().toString());

        // execute & create response
        starts = System.nanoTime();
        int code;
        String message;
        String body;

        try {
            code = connection.getResponseCode();
            message = connection.getResponseMessage();
            InputStream inputStream = code < 300 ? connection.getInputStream() : connection.getErrorStream();
            body = InputStreamExtensions.read(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, List<String>> headers = connection.getHeaderFields();
        long duration = TimeUnit.MILLISECONDS.convert(System.nanoTime() - starts, TimeUnit.NANOSECONDS);
        connection.disconnect();

        return new Response(code, message, body, headers, duration);
    }

    private void prepareConnection(HttpURLConnection connection, Request request) throws IOException {
        connection.setRequestMethod(request.action().name());
        putConnectionHeaders(connection, request.headers());
        connection.setConnectTimeout(request.options().timeout());

        if (request.sendsBody()) {
            connection.setDoOutput(true);

            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(request.body().getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    private void putConnectionHeaders(HttpURLConnection connection, Map<String, List<String>> headers) {
        headers.forEach((name, values) -> values.forEach(value -> connection.setRequestProperty(name, value)));
    }

    private HttpURLConnection createConnection(Request request) {
        ProxySettings proxySettings = request.options().proxySettings();
        URL target = createURL(request);

        try {
            if (proxySettings == null) {
                return (HttpURLConnection) target.openConnection();
            } else {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxySettings.target(), proxySettings.port()));
                return (HttpURLConnection) target.openConnection(proxy);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL createURL(Request request) {
        String target = request.target();
        Map<String, List<String>> parameters = request.parameters();

        if (!MapExtensions.isEmpty(parameters)) {
            Map<String, List<String>> params = QueryParameters.mergeQueryParameters(QueryParameters.extractQueryParameters(target), parameters);
            target = QueryParameters.stripQueryString(target) + QueryParameters.createQueryString(params);
        }

        try {
            return new URL(target);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
