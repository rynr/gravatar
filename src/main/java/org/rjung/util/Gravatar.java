package org.rjung.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Rating;

/**
 * {@link Gravatar} provides you different methods to easily retrieve a
 * Gravatar-URL. This first version just allows Singleton-access to the methods.
 *
 */
public class Gravatar {

    private static final String GRAVATAR_IMAGE_BASE_URL = "http://www.gravatar.com/avatar/";

    private static class GravatarHolder {
        public static final Gravatar instance = new Gravatar();

        private GravatarHolder() {
        }
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
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email) {
        return pureImageUrl(email);
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @param size
     *            The size of the gravatar image
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email, int size) {
        return imageUrl(email) + "?s=" + size;
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @param size
     *            The size of the gravatar image
     * @param def
     *            Which default image should be used if no {@link Gravatar}
     *            -image is available.
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email, int size, Default def) {
        return imageUrl(email, size) + "&d=" + def.getCode();
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @param size
     *            The size of the gravatar image
     * @param def
     *            Which default image should be used if no {@link Gravatar}
     *            -image is available.
     * @param rating
     *            Default Rating is
     *            "suitable for display on all websites with any audience type".
     *            Depending on your visitors, you could allow other ratings (
     *            {@link https://gravatar.com/site/implement/images/}).
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email, int size, Default def, Rating rating) {
        return imageUrl(email, size, def) + "&r=" + rating.getCode();
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @param def
     *            Which default image should be used if no {@link Gravatar}
     *            -image is available.
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email, Default def) {
        return imageUrl(email) + "&d=" + def.getCode();
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @param def
     *            Which default image should be used if no {@link Gravatar}
     *            -image is available.
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email, Default def, Rating rating) {
        return imageUrl(email, def) + "&r=" + rating.getCode();
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @param rating
     *            Default Rating is
     *            "suitable for display on all websites with any audience type".
     *            Depending on your visitors, you could allow other ratings (
     *            {@link https://gravatar.com/site/implement/images/}).
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email, Rating rating) {
        return imageUrl(email) + "&r=" + rating.getCode();
    }

    private String pureImageUrl(String email) {
        return new StringBuilder().append(GRAVATAR_IMAGE_BASE_URL)
                .append(gravatarHex(email)).toString();
    }

    private String hex(byte[] array) {
        StringBuilder sb = new StringBuilder();
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