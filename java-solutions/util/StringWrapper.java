package util;

public class StringWrapper {
    public static String wrap(final String s, final String prefix, final String suffix) {
        return prefix + s + suffix;
    }

    public static String wrapIf(final String s, final String prefix, final String suffix, final boolean condition) {
        if (condition) {
            return wrap(s, prefix, suffix);
        } else {
            return s;
        }
    }
}
