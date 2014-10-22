package org.rjung.util.gravatar;

public enum Rating {
    G("g"), PG("pg"), R("r"), X("x");

    String code;

    Rating(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "<" + name() + " code=\"" + getCode() + "\">";
    }
}
