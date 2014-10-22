package org.rjung.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.junit.Test;
import org.rjung.util.gravatar.Default;
import org.rjung.util.gravatar.Rating;

public class GravatarTest extends TestCase {

    private static final String EXAMPLE_EMAIL = "example@example.com";
    private static final String GRAVATAR_URL_FOR_EXAMPLE_EMAIL = "http://www.gravatar.com/avatar/23463b99b62a72f26ed677cc556c44e8";

    @Test
    public void testInstanceDoesEncodeExampleEmailCorrectly() {
        assertThat(Gravatar.getInstance().imageUrl(EXAMPLE_EMAIL),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void testInstanceDoesTrimExampleEmail() {
        assertThat(
                Gravatar.getInstance().imageUrl(
                        "     example@example.com            "),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void testInstanceDoesLowercaseExampleEmail() {
        assertThat(Gravatar.getInstance().imageUrl("eXaMpLe@eXaMpLe.cOm"),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL));
    }

    @Test
    public void testNotDefinedEmailDoesNotRaiseExceptions() {
        Gravatar.getInstance().imageUrl(null);
    }

    @Test
    public void testGravatarWithSize() {
        assertThat(Gravatar.getInstance().imageUrl(EXAMPLE_EMAIL, 123),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?s=123"));
    }

    @Test
    public void testGravatarWithSizeAndDefault() {
        assertThat(
                Gravatar.getInstance().imageUrl(EXAMPLE_EMAIL, 123,
                        Default.MONSTERID),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL + "?s=123&d=monsterid"));
    }

    @Test
    public void testGravatarWithSizeDefaultAndRating() {
        assertThat(
                Gravatar.getInstance().imageUrl(EXAMPLE_EMAIL, 123,
                        Default.MONSTERID, Rating.X),
                equalTo(GRAVATAR_URL_FOR_EXAMPLE_EMAIL
                        + "?s=123&d=monsterid&r=x"));
    }
}
