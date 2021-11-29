package util;

public class StringPadder {
    static public String pad(String s, String fill, int length) {
        int charToAdd = Math.max(0, length - s.length());
        int repeatTimes = (charToAdd + fill.length() - 1) / fill.length();
        return fill.repeat(repeatTimes) + s;
    }
}
