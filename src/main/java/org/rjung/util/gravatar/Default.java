package org.rjung.util.gravatar;

/**
 * In addition to allowing you to use your own image, Gravatar has a number of
 * built in options which you can also use as defaults. Most of these work by
 * taking the requested email hash and using it to generate a themed image that
 * is unique to that email address.
 */
public enum Default {
    /**
     * do not load any image if none is associated with the email hash, instead
     * return an HTTP 404 (File Not Found) response.
     */
    FOUR_O_FOUR("404"),
    /**
     * (mystery-man) a simple, cartoon-style silhouetted outline of a person
     * (does not vary by email hash).
     */
    MM("mm"),
    /**
     * a geometric pattern based on an email hash.
     */
    IDENTICON("identicon"),
    /**
     * a generated 'monster' with different colors, faces, etc.
     */
    MONSTERID("monsterid"),
    /**
     * generated faces with differing features and backgrounds.
     */
    WAVATAR("wavatar"),
    /**
     * awesome generated, 8-bit arcade-style pixelated faces.
     */
    RETRO("retro"),
    /**
     * a transparent PNG image.
     */
    BLANK("blank");

    String code;

    private Default(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
