package org.rjung.util.gravatar;

public enum Protocol {

    HTTP("http://"), HTTPS("https://"), NONE("://");

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
