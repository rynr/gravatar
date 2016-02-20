package org.rjung.util.gravatar;

public enum Protocol {

    HTTP("http://"), HTTPS("https://"), NONE("://");

    private final String prefix;

    private Protocol(String prefix) {
        this.prefix = prefix;
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
