package org.rjung.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Gravatar {

    private static final String GRAVATAR_IMAGE_BASE_URL = "http://www.gravatar.com/avatar/";

    private static class GravatarHolder {
        public static Gravatar instance = new Gravatar();
    }

    // There's a default instance (singleton style) that can be used by default.
    // If you what to set a default, create your own instance.
    public static Gravatar getInstance() {
        return GravatarHolder.instance;
    }

    public String imageUrl(String email) {
        return pureImageUrl(email).toString();
    }

    public String imageUrl(String email, int size) {
        String url = pureImageUrl(email);
        return url;
    }

    private String pureImageUrl(String email) {
        return new StringBuilder().append(GRAVATAR_IMAGE_BASE_URL)
                .append(gravatarHex(email)).toString();
    }

    private String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(
                    1, 3));
        }
        return sb.toString();
    }

    private String gravatarHex(String email) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest((email == null ? "" : email).trim()
                    .toLowerCase().getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}