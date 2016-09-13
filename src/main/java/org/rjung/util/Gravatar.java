package org.rjung.util;

import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Protocol;
import org.rjung.util.gravatar.Rating;

import javax.xml.bind.DatatypeConverter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * {@link Gravatar} provides you a simple methods retrieve a Gravatar-URL. This
 * version uses a builder to generate the URL.<br>
 * The easiest way is to use:
 * <code>Gravatar.forEmail("example@example.com").toUrl();</code>
 */
public final class Gravatar {

    private static final int GRAVATAR_SIZE_MIN = 1;
    private static final int GRAVATAR_SIZE_MAX = 2048;
    private static final String GRAVATAR_CHARSET = "CP1252";
    private static final String GRAVATAR_IMAGE_BASE_URL = "s.gravatar.com/avatar/";
    private static final String PARAM_DEFAULT = "d";
    private static final String PARAM_RATING = "r";
    private static final String PARAM_SIZE = "s";

    private String email;
    private Protocol protocol;
    private Map<String, Object> parameters;

    private Gravatar(final String pEmail) {
        this(pEmail, Protocol.NONE, new HashMap<String, Object>());
    }

    private Gravatar(final String pEmail, final Protocol pProtocol,
            final Map<String, Object> pParameters) {
        this.email = pEmail;
        this.protocol = pProtocol;
        this.parameters = pParameters;
    }

    /**
     * Start here to build a {@link Gravatar} to receive it's URL.
     *
     * @param pEmail
     *            the email address to be encoded
     * @return {@link Gravatar} instance for the given email-address
     */
    public static Gravatar forEmail(final String pEmail) {
        return new Gravatar(pEmail);
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
     * @param pProtocol
     *            One of {@link Protocol#HTTP}, {@link Protocol#HTTPS} and
     *            {@link Protocol#NONE}
     * @return {@link Gravatar}
     */
    public Gravatar with(final Protocol pProtocol) {
        if (pProtocol != null) {
            this.protocol = pProtocol;
        }
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
     * @param pRating
     *            The {@link Rating} to set for the {@link Gravatar}. A
     *            <code>null</code>-value removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar with(final Rating pRating) {
        if (pRating == null) {
            this.parameters.remove(PARAM_RATING);
        } else {
            this.parameters.put(PARAM_RATING, pRating);
        }
        return this;
    }

    /**
     * You can define the maximum size of the image in pixels. The default size
     * is 80 pixels. The values within 1 and 2048 are allowed.
     *
     * @param pSize
     *            The size of the image in pixels (1-2048). A <code>null</code>
     *            -value removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar size(final Integer pSize) {
        if (pSize == null) {
            this.parameters.remove(PARAM_SIZE);
        } else if (pSize.intValue() < GRAVATAR_SIZE_MIN
                || pSize.intValue() > GRAVATAR_SIZE_MAX) {
            throw new IllegalArgumentException(
                    "size needs to be within 1 and 2048");
        } else {
            this.parameters.put(PARAM_SIZE, pSize);
        }
        return this;
    }

    /**
     * Set the URL of a default-image. If there's no image, you can define the
     * URL of an image to display.
     *
     * @param pUrl
     *            The URL of an image to use if no image is available. A
     *            <code>null</code>-value removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar defaultImage(final String pUrl) {
        if (pUrl == null) {
            this.parameters.remove(PARAM_DEFAULT);
        } else {
            this.parameters.put(PARAM_DEFAULT, pUrl);
        }
        return this;
    }

    /**
     * Besides a self-defined default-image at a defined URL, gravatar provides
     * other images. The range starts with a 404-error to a generated
     * "monster"-image. See {@link Default} for the different values to choose
     * from.
     *
     * @param pDefaultImage
     *            One of the {@link Default}-values. A <code>null</code>-value
     *            removes the definition.
     * @return {@link Gravatar}
     */
    public Gravatar defaultImage(final Default pDefaultImage) {
        if (pDefaultImage == null) {
            this.parameters.remove(PARAM_DEFAULT);
        } else {
            this.parameters.put(PARAM_DEFAULT, pDefaultImage);
        }
        return this;
    }

    /**
     * Retrieve the URL of the {@link Gravatar}-image.
     *
     * @return {@link Gravatar}-image-url
     * @throws GravatarException
     *             If some Character-encoding fails, you will receive a
     *             {@link GravatarException}.
     */
    public String toUrl() throws GravatarException {
        try {
            return appendParameters(protocol, Gravatar.pureImageUrl(email),
                    parameters);
        } catch (UnsupportedEncodingException e) {
            throw new GravatarException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            throw new GravatarException(e.getMessage(), e);
        }
    }

    private static String pureImageUrl(final String pEmail)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return new StringBuilder().append(GRAVATAR_IMAGE_BASE_URL)
                .append(gravatarHex(pEmail)).toString();
    }

    private static String hex(final byte[] pArray) {
        return DatatypeConverter.printHexBinary(pArray);
    }

    private static String gravatarHex(final String pEmail)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return hex(md.digest((pEmail == null ? "" : pEmail).trim()
                .toLowerCase(Locale.getDefault()).getBytes(GRAVATAR_CHARSET)))
                        .toLowerCase(Locale.getDefault());
    }

    private static String appendParameters(final Protocol pProtocol,
            final String pUrl, final Map<String, Object> pParameters)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder(pProtocol.getPrefix());
        result.append(pUrl);
        Iterator<Entry<String, Object>> iterator = pParameters.entrySet()
                .iterator();
        if (iterator.hasNext()) {
            result.append(pUrl.contains("?") ? "&" : "?");
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
