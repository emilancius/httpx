package org.emgen.http.request;

/**
 * @since 1.0.0
 */
public final class ProxySettings {

    private final String target;
    private final int port;

    public ProxySettings(final String target, final int port) {
        this.target = target;
        this.port = port;
    }

    public String target() {
        return target;
    }

    public int port() {
        return port;
    }

    @Override
    public String toString() {
        return "ProxySettings{" +
            "target='" + target + '\'' +
            ", port=" + port +
            '}';
    }
}
