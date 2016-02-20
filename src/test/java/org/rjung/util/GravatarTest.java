package org.rjung.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Protocol;
import org.rjung.util.gravatar.Rating;

public class GravatarTest {

    private static final String EXAMPLE_DEFAULT_URL = "http://some.url/to/image.png";
    private static final String EXAMPLE_EMAIL = "example@example.com";
    private static final String GRAVATAR_URL_FOR_EXAMPLE_EMAIL = "://s.gravatar.com/avatar/23463b99b62a72f26ed677cc556c44e8";

    @Test
    public void verifySimpleAvatarUrlEncodesCorrectly() throws GravatarException {
        assertThat("Generated URL does not match expected URL",
                Gravatar.forEmail(EXAMPLE_EMAIL).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void verifyNoEmailDoesNotRaiseException() throws GravatarException {
        Gravatar.forEmail(null).toUrl();
    }

    @Test
    public void verifyProtocolCanBeChanged() throws GravatarException {
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).with(Protocol.HTTP).toUrl(),
                equalTo("http" + GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL).with(Protocol.HTTPS).toUrl(),
                equalTo("https" + GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).with(Protocol.NONE).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void verifyNoProtocolDoesNotRaiseException() throws GravatarException {
        Gravatar.forEmail(EXAMPLE_EMAIL).with((Protocol) null).toUrl();
    }

    @Test
    public void verifyRatingCanBeSet() throws GravatarException {
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).with(Rating.G).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?r=g"));
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).with(Rating.PG).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?r=pg"));
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).with(Rating.R).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?r=r"));
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).with(Rating.X).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?r=x"));
    }

    @Test
    public void verifyRatingCanBeCleared() throws GravatarException {
        Gravatar builder = Gravatar.forEmail(EXAMPLE_EMAIL).with(Rating.G);
        assertThat(builder.toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?r=g"));
        assertThat(builder.with((Rating) null).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void verifySizeCanBeSet() throws GravatarException {
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).size(1).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?s=1"));
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).size(80).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?s=80"));
        assertThat(Gravatar.forEmail(EXAMPLE_EMAIL).size(2048).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?s=2048"));
    }

    @Test
    public void verifySizeCanBeCleared() throws GravatarException {
        Gravatar builder = Gravatar.forEmail(EXAMPLE_EMAIL).size(1);
        assertThat(builder.toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?s=1"));
        assertThat(builder.size(null).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyToSmallSizeRaises() {
        Gravatar.forEmail(EXAMPLE_EMAIL).size(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyToBigSizeRaises() {
        Gravatar.forEmail(EXAMPLE_EMAIL).size(2049);
    }

    @Test
    public void verifyDefaultCanBeSet() throws GravatarException {
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL)
                        .defaultImage(Default.FOUR_O_FOUR).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d=404"));
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL).defaultImage(Default.IDENTICON)
                        .toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d=identicon"));
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL).defaultImage(Default.MM)
                        .toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d=mm"));
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL).defaultImage(Default.MONSTERID)
                        .toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d=monsterid"));
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL).defaultImage(Default.RETRO)
                        .toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d=retro"));
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL).defaultImage(Default.WAVATAR)
                        .toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d=wavatar"));
    }

    @Test
    public void verifyDefaultCanBeCleared() throws GravatarException {
        Gravatar builder = Gravatar.forEmail(EXAMPLE_EMAIL)
                .defaultImage(Default.FOUR_O_FOUR);
        assertThat(builder.toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d=404"));
        assertThat(builder.defaultImage((Default) null).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void verifyDefaultUrlCanBeSet() throws UnsupportedEncodingException, GravatarException {
        assertThat(
                Gravatar.forEmail(EXAMPLE_EMAIL)
                        .defaultImage(EXAMPLE_DEFAULT_URL).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d="
                        + URLEncoder.encode(EXAMPLE_DEFAULT_URL,
                                Gravatar.GRAVATAR_CHARSET)));
    }

    @Test
    public void verifyDefaultUrlCanBeCleared()
            throws UnsupportedEncodingException, GravatarException {
        Gravatar builder = Gravatar.forEmail(EXAMPLE_EMAIL)
                .defaultImage(EXAMPLE_DEFAULT_URL);
        assertThat(builder.toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?d="
                        + URLEncoder.encode(EXAMPLE_DEFAULT_URL,
                                Gravatar.GRAVATAR_CHARSET)));
        assertThat(builder.defaultImage((String) null).toUrl(),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void verifyCompleteExample()
 throws MalformedURLException,
            UnsupportedEncodingException, GravatarException {
        final Map<String, String> queryParameters = new HashMap<String, String>();
        String[] pairs = new URL(Gravatar.forEmail(EXAMPLE_EMAIL)
                .defaultImage(Default.FOUR_O_FOUR).size(80).with(Protocol.HTTPS)
                .with(Rating.PG).toUrl()).getQuery().split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(
                    pair.substring(0, idx), Gravatar.GRAVATAR_CHARSET) : pair;
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder
                    .decode(pair.substring(idx + 1), Gravatar.GRAVATAR_CHARSET)
                    : null;
            queryParameters.put(key, value);
        }

        assertThat(queryParameters.size(), equalTo(3));
        assertThat(queryParameters.get("r"), equalTo("pg"));
        assertThat(queryParameters.get("s"), equalTo("80"));
        assertThat(queryParameters.get("d"), equalTo("404"));
    }
}
