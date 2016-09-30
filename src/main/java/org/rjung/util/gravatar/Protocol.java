package org.rjung.util.gravatar;

/**
 * Unencrypted http and encrypted https are the standard protocol-options for a
 * gravatar image.
 */
public enum Protocol {
    /**
     * The protocol will be non-encrypted http.
     */
    HTTP("http://"),
    /**
     * The protocol will be encrypted https.
     */
    HTTPS("https://"),
    /**
     * The protocol will be dependent on the protocol of the page that requests
     * the gravatar.
     */
    NONE("://");

    private final String prefix;

    Protocol(final String pPrefix) {
        this.prefix = pPrefix;
    }

    /**
     * The value to be prefixed to the URL before the hostname.
     *
     * @return protocol-prefix
     */
    public String getPrefix() {
        return prefix;
    }
}
