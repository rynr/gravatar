package org.rjung.util.gravatar;

public enum Default {
    FOUR_O_FOUR("404"), MM("mm"), IDENTICON("identicon"), MONSTERID(
            "monsterid"), WAVATAR("wavatar"), RETRO("retro"), BLANK("blank");

    String code;

    Default(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
