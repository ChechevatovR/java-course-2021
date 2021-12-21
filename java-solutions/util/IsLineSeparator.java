package util;

import java.util.function.Predicate;

public class IsLineSeparator implements Predicate<Character> {
    public boolean test(final Character c) {
        // https://en.wikipedia.org/wiki/Newline#Unicode
        return c == 0x000A // LF | Line feed
            || c == 0x000B // VT | Vertical tab
            || c == 0x000C // FF | Form feed
            || c == 0x000D // CR | Carriage return
            || c == 0x0085 // NL | Next line
            || c == 0x2028 // LS | Line separator
            || c == 0x2029;   // PS | Paragraph separator
    }

}
