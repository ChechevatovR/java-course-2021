package util;

public class StringPadder {
    public static String pad(final String s, final String fill, final int length) {
        final int charToAdd = Math.max(0, length - s.length());
        final int repeatTimes = (charToAdd + fill.length() - 1) / fill.length();
        return fill.repeat(repeatTimes) + s;
    }
}
