package org.rjung.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * 
 *
 */
public class Gravatar {

    private static final String GRAVATAR_IMAGE_BASE_URL = "http://www.gravatar.com/avatar/";

    private static class GravatarHolder {
        public static Gravatar instance = new Gravatar();
    }

    /**
     * If you want to have a email converted to a Gravatar URL, you need to get
     * an instance of {@link Gravatar}.
     * 
     * Static access is not the best solution, though it might seem to be the
     * best solution to this simple task. Still, there will be some further
     * improvement to this class. And a pure static approach will not be the
     * only solution. Providing a Singleton Access is a good solution for now.
     * Accessing {@link Gravatar} via a internal static class
     * {@link GravatarHolder} prevents instantiation of the class durcing
     * compile time, but it will be available once {@link GravatarHolder} is
     * accessed.
     * 
     * @return The Singleton instance of {@link Gravatar}.
     */
    public static Gravatar getInstance() {
        return GravatarHolder.instance;
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @return Complete URL of the email-addresses {@link Gravatar}r-image
     *         (without any size or rating-information).
     */
    public String imageUrl(String email) {
        return pureImageUrl(email);
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
                    .toLowerCase(Locale.getDefault()).getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }
}