package org.emgen.httpx.http.request;

/**
 * @since 1.0.0
 */
public final class RequestOptions {

    private final int timeout;
    private final ProxySettings proxySettings;

    public RequestOptions(final int timeout, final ProxySettings proxySettings) {
        this.timeout = timeout;
        this.proxySettings = proxySettings;
    }

    public int timeout() {
        return timeout;
    }

    public ProxySettings proxySettings() {
        return proxySettings;
    }

    @Override
    public String toString() {
        return "RequestOptions{" +
                "timeout=" + timeout +
                ", proxySettings=" + proxySettings +
                '}';
    }

    public static final class Creator {

        private int timeout = 5000;
        private ProxySettings proxySettings;

        public Creator timeout(final int timeout) {
            this.timeout = timeout;
            return this;
        }

        public Creator proxySettings(ProxySettings proxySettings) {
            this.proxySettings = proxySettings;
            return this;
        }

        public RequestOptions create() {
            return new RequestOptions(timeout, proxySettings);
        }
    }
}
