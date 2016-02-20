package org.rjung.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.DatatypeConverter;

import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Protocol;
import org.rjung.util.gravatar.Rating;

/**
 * {@link Gravatar} provides you a simple methods retrieve a Gravatar-URL. This
 * version uses a builder to generate the URL.<br>
 * The easiest way is to use:
 * <code>Gravatar.forEmail("example@example.com").toUrl();</code>
 */
public class Gravatar {

    static final String GRAVATAR_CHARSET = "CP1252";
    static final String GRAVATAR_IMAGE_BASE_URL = "s.gravatar.com/avatar/";
    // I do not want to use any further dependency (like slf4j) to log any
    // problems. For now I just use the simplest logger I can get. Any idea to
    // handle this differently?
    private static final Logger LOG = Logger.getAnonymousLogger();

    private static final String PARAM_DEFAULT = "d";
    private static final String PARAM_RATING = "r";
    private static final String PARAM_SIZE = "s";
    private String email;
    private Protocol protocol;
    private Map<String, Object> parameters;

    private Gravatar(String email) {
        this.email = email;
        this.protocol = Protocol.NONE;
        this.parameters = new HashMap<String, Object>();
    }

    /**
     * Start here to build a {@link Gravatar} to receive it's URL.
     */
    public static Gravatar forEmail(String email) {
        return new Gravatar(email);
    }

    /**
     * This method is just for eye-candy. You can build URLs like this:<br>
     * <code>Gravatar.forEmail("..").with(Rating.R).and().size(123)</code>
     *
     * @return {@link Gravatar}
     */
    public Gravatar and() {
        return this;
    }

    /**
     * If you need a {@link Protocol#HTTP}- or {@link Protocol#HTTPS}-URL you
     * can set this here. If you don't know which protocol a user is using, you
     * can also choose {@link Protocol#NONE}, in this case the URL will begin
     * with <code>://</code>.
     *
     * @param protocol
     *            One of {@link Protocol#HTTP}, {@link Protocol#HTTPS} and
     *            {@link Protocol#NONE}
     * @return {@link Gravatar}
     */
    public Gravatar with(Protocol protocol) {
        if (protocol != null)
            this.protocol = protocol;
        return this;
    }

    /**
     * If you may not display any kind of image (like the images could be seen
     * by children) you can set a Rating-level. The information will be
     * transferred to gravatar.org to decide if the image can be shown. You can
     * find more about this at
     * <a href="https://de.gravatar.com/site/implement/images/#rating">https://
     * de.gravatar.com/site/implement/images/#rating</a><br>
     * The user of the image has himself set the {@link Rating}, so you still
     * rely on this.
     *
     * @param rating
     *            The {@link Rating} to set for the {@link Gravatar}. A
     *            <code>null</code>-value removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar with(Rating rating) {
        if (rating == null) {
            this.parameters.remove(PARAM_RATING);
        } else {
            this.parameters.put(PARAM_RATING, rating);
        }
        return this;
    }

    /**
     * You can define the maximum size of the image in pixels. The default size
     * is 80 pixels. The values within 1 and 2048 are allowed.
     *
     * @param size
     *            The size of the image in pixels (1-2048). A <code>null</code>
     *            -value removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar size(Integer size) {
        if (size == null) {
            this.parameters.remove(PARAM_SIZE);
        } else if (size.intValue() < 1 || size.intValue() > 2048) {
            throw new IllegalArgumentException(
                    "size needs to be within 1 and 2048");
        } else {
            this.parameters.put(PARAM_SIZE, size);
        }
        return this;
    }

    /**
     * Set the URL of a default-image. If there's no image, you can define the
     * URL of an image to display.
     *
     * @param url
     *            The URL of an image to use if no image is available. A
     *            <code>null</code>-value removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar defaultImage(String url) {
        if (url == null) {
            this.parameters.remove(PARAM_DEFAULT);
        } else {
            this.parameters.put(PARAM_DEFAULT, url);
        }
        return this;
    }

    /**
     * Besides a self-defined default-image at a defined URL, gravatar provides
     * other images. The range starts with a 404-error to a generated
     * "monster"-image. See {@link Default} for the different values to choose
     * from.
     *
     * @param defaultImage
     *            One of the {@link Default}-values. A <code>null</code>-value
     *            removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar defaultImage(Default defaultImage) {
        if (defaultImage == null) {
            this.parameters.remove(PARAM_DEFAULT);
        } else {
            this.parameters.put(PARAM_DEFAULT, defaultImage);
        }
        return this;
    }

    /**
     * Retrieve the URL of the {@link Gravatar}-image.
     *
     * @return {@link Gravatar}-image-url
     * @throws GravatarException If some Character-encoding fails, you will receive a {@link GravatarException}.
     */
    public String toUrl() throws GravatarException {
        try {
            return appendParameters(protocol, Gravatar.pureImageUrl(email),
                    parameters);
        } catch (UnsupportedEncodingException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new GravatarException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.WARNING, e.getMessage(), e);
            throw new GravatarException(e.getMessage(), e);
        }
    }

    private static String pureImageUrl(String email) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return new StringBuilder().append(GRAVATAR_IMAGE_BASE_URL)
                .append(gravatarHex(email)).toString();
    }

    private static String hex(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    private static String gravatarHex(String email) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return hex(md.digest((email == null ? "" : email).trim()
                .toLowerCase(Locale.getDefault()).getBytes(GRAVATAR_CHARSET)))
                        .toLowerCase(Locale.getDefault());
    }

    private static String appendParameters(Protocol protocol, String url,
            Map<String, Object> parameters)
                    throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(protocol.getPrefix());
        result.append(url);
        Iterator<Entry<String, Object>> iterator = parameters.entrySet()
                .iterator();
        if (iterator.hasNext()) {
            result.append(url.contains("?") ? "&" : "?");
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                result.append(entry.getKey());
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue().toString(),
                        GRAVATAR_CHARSET));
                if (iterator.hasNext()) {
                    result.append("&");
                }
            }
        }
        return result.toString();
    }
}
