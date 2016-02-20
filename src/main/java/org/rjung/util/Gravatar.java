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
 *
 */
public class Gravatar {

    static final String GRAVATAR_CHARSET = "CP1252";
    static final String GRAVATAR_IMAGE_BASE_URL = "s.gravatar.com/avatar/";
    // I do not want to use any further dependency (like slf4j) to log any
    // problems. For now I just use the simplest logger I can get. Any idea to
    // handle this differently?
    private static final Logger LOG = Logger.getAnonymousLogger();

    /**
     * Start here to build a Gravatar-URL.
     */
    public static Builder forEmail(String email) {
        return new Builder(email);
    }

    private static String pureImageUrl(String email) {
        return new StringBuilder().append(GRAVATAR_IMAGE_BASE_URL)
                .append(gravatarHex(email)).toString();
    }

    private static String hex(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    private static String gravatarHex(String email) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest((email == null ? "" : email).trim()
                    .toLowerCase(Locale.getDefault())
                    .getBytes(GRAVATAR_CHARSET)))
                            .toLowerCase(Locale.getDefault());
        } catch (NoSuchAlgorithmException e) {
            LOG.log(Level.CONFIG, e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            LOG.log(Level.CONFIG, e.getMessage(), e);
        }
        return null;
    }

    public static class Builder {
        private static final String PARAM_DEFAULT = "d";
        private static final String PARAM_RATING = "r";
        private static final String PARAM_SIZE = "s";
        private String email;
        private Protocol protocol;
        private Map<String, Object> parameters;

        public Builder(String email) {
            this.email = email;
            this.protocol = Protocol.NONE;
            this.parameters = new HashMap<String, Object>();
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

        public Builder with(Protocol protocol) {
            if (protocol != null)
                this.protocol = protocol;
            return this;
        }

        public Builder with(Rating rating) {
            if (rating == null) {
                this.parameters.remove(PARAM_RATING);
            } else {
                this.parameters.put(PARAM_RATING, rating);
            }
            return this;
        }

        public Builder size(Integer size) {
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

        public Builder defaultImage(String url) {
            if (url == null) {
                this.parameters.remove(PARAM_DEFAULT);
            } else {
                this.parameters.put(PARAM_DEFAULT, url);
            }
            return this;
        }

        public Builder defaultImage(Default defaultImage) {
            if (defaultImage == null) {
                this.parameters.remove(PARAM_DEFAULT);
            } else {
                this.parameters.put(PARAM_DEFAULT, defaultImage);
            }
            return this;
        }

        public String toUrl() {
            try {
                return appendParameters(protocol, Gravatar.pureImageUrl(email),
                        parameters);
            } catch (UnsupportedEncodingException e) {
                LOG.log(Level.WARNING, e.getMessage(), e);
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }
}