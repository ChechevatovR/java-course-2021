package util;

public class StringWrapper {
    public static String wrap(String s, String prefix, String suffix) {
        return prefix + s + suffix;
    }

    public static String wrapIf(String s, String prefix, String suffix, boolean condition) {
        if (condition) {
            return wrap(s, prefix, suffix);
        } else {
            return s;
        }
    }
}
