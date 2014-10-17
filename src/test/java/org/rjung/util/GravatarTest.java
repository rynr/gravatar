package org.rjung.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.junit.Test;

public class GravatarTest extends TestCase {

    private static final String GRAVATAR_URL_FOR_EXAMPLE_EMAIL = "http://www.gravatar.com/avatar/23463b99b62a72f26ed677cc556c44e8";

    @Test
    public void testInstanceDoesEncodeExampleEmailCorrectly() {
        assertThat(Gravatar.getInstance().imageUrl("example@example.com"),
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
}
