package org.rjung.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.DatatypeConverter;

import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Rating;

/**
 * {@link Gravatar} provides you different methods to easily retrieve a
 * Gravatar-URL. This first version just allows Singleton-access to the methods.
 *
 */
public class Gravatar {

    private static final String GRAVATAR_IMAGE_BASE_URL = "http://www.gravatar.com/avatar/";
    // I do not want to use any further dependency (like slf4j) to log any
    // problems. For now I just use the simplest logger I can get. Any idea to
    // handle this differently?
    private static final Logger LOG = Logger.getAnonymousLogger();

    private static class GravatarHolder {
        public static final Gravatar INSTANCE = new Gravatar();

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
     * 
     * @return The Singleton instance of {@link Gravatar}.
     */
    public static Gravatar getInstance() {
        return GravatarHolder.INSTANCE;
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
     *            Depending on your visitors, you could allow other ratings (<a
     *            href="https://gravatar.com/site/implement/images/">https://gravatar.com/site/implement/images/</a>).
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
        return imageUrl(email) + "?d=" + def.getCode();
    }

    /**
     * 
     * @param email
     *            Provide the email-address that is wanted to be converted.
     * @param def
     *            Which default image should be used if no {@link Gravatar}
     *            -image is available.
     * @param rating
     *            Default Rating is
     *            "suitable for display on all websites with any audience type".
     *            Depending on your visitors, you could allow other ratings (<a
     *            href="https://gravatar.com/site/implement/images/">https://gravatar.com/site/implement/images/</a>).
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
     *            Depending on your visitors, you could allow other ratings (<a
     *            href="https://gravatar.com/site/implement/images/"https://gravatar.com/site/implement/images/</a>).
     * @return Complete URL of the email-addresses {@link Gravatar}-image.
     */
    public String imageUrl(String email, Rating rating) {
        return imageUrl(email) + "?r=" + rating.getCode();
    }

    private String pureImageUrl(String email) {
        return new StringBuilder().append(GRAVATAR_IMAGE_BASE_URL)
                .append(gravatarHex(email)).toString();
    }

    private String hex(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    private String gravatarHex(String email) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest((email == null ? "" : email).trim()
                    .toLowerCase(Locale.getDefault()).getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.CONFIG, e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOG.log(Level.CONFIG, e.getMessage(), e);
        }
        return null;
    }
}