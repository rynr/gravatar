package org.rjung.util;

/**
 * It's not likely to get an exception. This will include any other Exception
 * that occurred within the generation of the URL.
 */
public class GravatarException extends Exception {

    private static final long serialVersionUID = 4490273872457279555L;

    /**
     * Create a {@link GravatarException} without additional informations.
     */
    public GravatarException() {
        super();
    }

    /**
     * Create a {@link GravatarException} with message.
     *
     * @param message
     *            A message describing the reason for the
     *            {@link GravatarException}.
     */
    public GravatarException(final String message) {
        super(message);
    }

    /**
     * Create a {@link GravatarException} with message and cause.
     *
     * @param message
     *            A message describing the reason for the
     *            {@link GravatarException}.
     * @param cause
     *            A cause as reason for the {@link GravatarException}.
     */
    public GravatarException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a {@link GravatarException} with cause.
     *
     * @param cause
     *            A cause as reason for the {@link GravatarException}.
     */
    public GravatarException(final Throwable cause) {
        super(cause);
    }

}
