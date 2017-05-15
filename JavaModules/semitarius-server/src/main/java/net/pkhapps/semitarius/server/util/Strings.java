package net.pkhapps.semitarius.server.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility methods for working with strings.
 */
public final class Strings {

    private Strings() {
    }

    /**
     * Checks that the given string is not {@code null} and not empty. Any leading or trailing whitespace is excluded.
     *
     * @param s the string to check.
     * @return the non-empty string.
     * @throws IllegalArgumentException if the string is empty.
     * @see #requireNonEmpty(String, String)
     */
    @NotNull
    public static String requireNonEmpty(@Nullable String s) {
        return requireNonEmpty(s, "String cannot be null nor empty");
    }

    /**
     * Checks that the given string is not {@code null} and not empty. Any leading or trailing whitespace is excluded.
     *
     * @param s       the string to check.
     * @param message the message to throw if the string is empty.
     * @return the non-empty string.
     * @throws IllegalArgumentException if the string is empty.
     * @see #requireNonEmpty(String)
     */
    @NotNull
    public static String requireNonEmpty(@Nullable String s, @NotNull String message) {
        if (s == null || s.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return s;
    }
}
