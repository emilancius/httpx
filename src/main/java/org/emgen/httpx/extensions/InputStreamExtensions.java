package org.emgen.httpx.extensions;

import org.emgen.httpx.prerequisites.Prerequisites;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @since 1.0.0
 */
public final class InputStreamExtensions {

    private static final int BUFFER_SIZE = 8 * 1024;

    private InputStreamExtensions() {
        throw new InstantiationError("org.emgen.httpx.extensions.InputStream.class cannot be instantiated.");
    }

    /**
     * Converts passed {@param inputStream} to {@link String}, using UTF-8 charset.
     * {@link org.emgen.httpx.http.exceptions.ArgumentExistenceException} is thrown in case
     * {@param inputStream} is null.
     *
     * @param inputStream to convert.
     * @return {@link String} representation for provided {@param inputStream}.
     * @throws IOException
     */
    public static String read(InputStream inputStream) throws IOException {
        Prerequisites.exists(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[BUFFER_SIZE];

        for (int length = inputStream.read(bytes); length != -1; length = inputStream.read(bytes)) {
            outputStream.write(bytes, 0, length);
        }

        return outputStream.toString(StandardCharsets.UTF_8.name());
    }
}
