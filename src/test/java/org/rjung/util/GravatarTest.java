package org.rjung.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.junit.Test;

public class GravatarTest extends TestCase {

    @Test
    public void testInstanceDoesEncodeExampleEmailCorrectly() {
        assertThat(
                Gravatar.getInstance().imageUrl("example@example.com"),
                equalTo("http://www.gravatar.com/avatar/23463b99b62a72f26ed677cc556c44e8"));
    }

    @Test
    public void testInstanceDoesTrimExampleEmail() {
        assertThat(
                Gravatar.getInstance().imageUrl(
                        "     example@example.com            "),
                equalTo("http://www.gravatar.com/avatar/23463b99b62a72f26ed677cc556c44e8"));
    }

    @Test
    public void testInstanceDoesLowercaseExampleEmail() {
        assertThat(
                Gravatar.getInstance().imageUrl(
                        "eXaMpLe@eXaMpLe.cOm"),
                equalTo("http://www.gravatar.com/avatar/23463b99b62a72f26ed677cc556c44e8"));
    }
}
